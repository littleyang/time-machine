package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DaliyIPJobControllerCount {

	// 首先实现是mapper类
	static class ChechIPMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// 如果包含API调用，则纪录签到一次
			//url: http://api.4009515151.com/api/zhuzher/users/me/badges, 
			//account: 10885667, args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None
			
			Text word = new Text();
			String values = value.toString();
			String[] arrays = values.split(" ");
			String ip = arrays[0];
			if(null!=ip){
				System.out.println("====== After mapper : " + ip);
				word.set(ip);
				context.write(word, one);
			}
		}
	}

	static class CheckIpReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
			System.out.println("======" + "After reduce :" + new Text(key.toString()) + ", " + sum);
		}
	}
	
	static class SortMapper extends Mapper<Object, Text, IntWritable, Text>{
		
		public void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			
			Text ipAddressValue = new Text();
			IntWritable ipAddressCountKey = new IntWritable();
			
			String[] arrays =  value.toString().split("\t");
			
			ipAddressCountKey.set(Integer.parseInt(arrays[1]));
			ipAddressValue.set(arrays[0]);
			
			context.write(ipAddressCountKey, ipAddressValue);
			
			System.out.println("====== After mapper count : " + ipAddressCountKey + "ip: " + ipAddressValue);
			
		}
		
	}
	
	static class SortReducer extends Reducer<IntWritable, Text, Text, IntWritable>{
		
		public void reduce(IntWritable key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {
			
			 for (Text text : values) {
	                context.write(text, key);
	         }
			
		}
	}
	
	
	static class CheckTotalIpMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Text word = new Text();
			IntWritable data = new IntWritable(1);
			word.set("ip");
			// 如果包含API调用，则纪录签到一次
			context.write(word, data);
			System.out.println("======" + "After Mapper:" + word + ", " + data);
		}
	}
	
	
	static class CheckTotalIpReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
			System.out.println("======" + "After reduce :" + new Text(key.toString()) + ", " + sum);
		}
	}
	
	
	static class IntValueDescComparator extends WritableComparator{
		
		protected IntValueDescComparator() {
		    super(IntWritable.class, true);

		}
		
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
		    return super.compare(b, a);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 //输入路径
        String dst = "hdfs://10.0.58.21:9000/nginx/2016/06/11/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/outputip611a";
        
        String sourtOut = "hdfs://10.0.58.21:9000/result/outputsortedip611a";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/output611ipcounta";
        
        
        JobConf conf = new JobConf(DaliyIPJobControllerCount.class);
        Job jobCheckIn = Job.getInstance(conf, "IpCheckInCount");
        jobCheckIn.setJarByClass(DaliyIPJobControllerCount.class);
        jobCheckIn.setMapperClass(ChechIPMapper.class);
        jobCheckIn.setReducerClass(CheckIpReducer.class);
        jobCheckIn.setOutputKeyClass(Text.class);
        jobCheckIn.setOutputValueClass(IntWritable.class);
        
        ControlledJob jobCheckInCtrl=new  ControlledJob(conf);   
        jobCheckInCtrl.setJob(jobCheckIn);
        FileInputFormat.addInputPath(jobCheckIn, new Path(dst));
        FileOutputFormat.setOutputPath(jobCheckIn, new Path(dstOut));
        
        
        Job jobSortIpJob = Job.getInstance(conf, "IpValueSortjob");
        jobSortIpJob.setJarByClass(DaliyIPJobControllerCount.class);
        jobSortIpJob.setMapperClass(SortMapper.class);
        jobSortIpJob.setReducerClass(SortReducer.class);
        jobSortIpJob.setMapOutputKeyClass(IntWritable.class);
        jobSortIpJob.setMapOutputValueClass(Text.class);
        jobSortIpJob.setOutputKeyClass(Text.class);
        jobSortIpJob.setOutputValueClass(IntWritable.class);
        jobSortIpJob.setSortComparatorClass(IntValueDescComparator.class);
        
        ControlledJob jobSortIpJobCtrl=new  ControlledJob(conf);   
        jobSortIpJobCtrl.setJob(jobSortIpJob);
        FileInputFormat.addInputPath(jobSortIpJob, new Path(dstOut));
        FileOutputFormat.setOutputPath(jobSortIpJob, new Path(sourtOut));
        
        // dependence on jobCheckInCtrl
        jobSortIpJobCtrl.addDependingJob(jobCheckInCtrl);
        

        Job jobCountUser = Job.getInstance(conf, "IpCountjob");
        jobCountUser.setJarByClass(DaliyIPJobControllerCount.class);
        jobCountUser.setMapperClass(CheckTotalIpMapper.class);
        jobCountUser.setReducerClass(CheckTotalIpReducer.class);
        jobCountUser.setOutputKeyClass(Text.class);
        jobCountUser.setOutputValueClass(IntWritable.class);
        
        
        
        ControlledJob jobCountUserCtlr=new  ControlledJob(conf);   
        jobCountUserCtlr.setJob(jobCountUser);
        jobCountUserCtlr.addDependingJob(jobSortIpJobCtrl);
        
        FileInputFormat.addInputPath(jobCountUser, new Path(sourtOut));
        FileOutputFormat.setOutputPath(jobCountUser, new Path(dstOutCount));
        
        
        JobControl jobCtrl=new JobControl("myctrl");
        jobCtrl.addJob(jobCheckInCtrl);  
        jobCtrl.addJob(jobSortIpJobCtrl);   
        jobCtrl.addJob(jobCountUserCtlr);
        
        Thread  t=new Thread(jobCtrl);   
        t.start(); 
        
        while(true){
        	if(jobCtrl.allFinished()){//如果作业成功完成，就打印成功作业的信息   
        		System.out.println(jobCtrl.getSuccessfulJobList());   
        		jobCtrl.stop();   
        		break;   
        	}  
        }
	}
}
