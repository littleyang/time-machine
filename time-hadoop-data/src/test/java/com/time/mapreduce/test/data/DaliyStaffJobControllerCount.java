package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.time.mapreduce.test.data.DaliyIPJobControllerCount.IntValueDescComparator;
import com.time.mapreduce.test.data.DaliyIPJobControllerCount.SortMapper;
import com.time.mapreduce.test.data.DaliyIPJobControllerCount.SortReducer;

public class DaliyStaffJobControllerCount {

	// 首先实现是mapper类
	static class ChechInMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// 如果包含API调用，则纪录签到一次
			//url: http://api.4009515151.com/api/zhuzher/users/me/badges, 
			//account: 10885667, args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None
			
			Text word = new Text();
			String values = value.toString();
			String[] arrays = values.split(", ");
			if(arrays.length>3&&arrays[2].contains("account")){
				//System.out.println(arrays[2]);
				String[] accountA = arrays[2].split(": ");
				if(accountA.length>1){
					String account = accountA[1];
					if(!account.equals("None")&&account.length()==7){
						// 统计staff的
					//if(!account.equals("None")&&account.length()==8){
						// 统计user
						System.out.println(account);
						word.set(account);
						context.write(word, one);
					}
				}
			}
		}
	}

	static class CheckInReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		
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
	
	
	static class CheckTotalStaffAccountMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Text word = new Text();
			IntWritable data = new IntWritable(1);
			
			if(key.toString().length()==7){
				System.out.println("++++++" + key.toString());
				word.set("staff");
				// 如果包含API调用，则纪录签到一次
			}else{
				word.set("user");
			}
			context.write(word, data);
			//System.out.println("======" + "After Mapper:" + word + ", " + data);
		}
	}
	
	
	static class CheckTotalStaffAccountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
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
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		
		//输入路径
        String dst = "hdfs://10.0.58.21:9000/falcon/2016/06/12/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/outputstaff612a";
        
        String sortOut = "hdfs://10.0.58.21:9000/result/outputsortedstaff612a";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/outputstaff612Counta";
        
        
        JobConf conf = new JobConf(DaliyStaffJobControllerCount.class);
        Job jobCheckIn = Job.getInstance(conf, "staffcheckincount");
        jobCheckIn.setJarByClass(DaliyStaffJobControllerCount.class);
        jobCheckIn.setMapperClass(ChechInMapper.class);
        jobCheckIn.setReducerClass(CheckInReducer.class);
        jobCheckIn.setOutputKeyClass(Text.class);
        jobCheckIn.setOutputValueClass(IntWritable.class);
        
        ControlledJob jobCheckInCtrl=new  ControlledJob(conf);   
        jobCheckInCtrl.setJob(jobCheckIn);
        FileInputFormat.addInputPath(jobCheckIn, new Path(dst));
        FileOutputFormat.setOutputPath(jobCheckIn, new Path(dstOut));
        
        Job jobSortStaffJob = Job.getInstance(conf, "staffSortjob");
        jobSortStaffJob.setJarByClass(DaliyStaffJobControllerCount.class);
        jobSortStaffJob.setMapperClass(SortMapper.class);
        jobSortStaffJob.setReducerClass(SortReducer.class);
        jobSortStaffJob.setMapOutputKeyClass(IntWritable.class);
        jobSortStaffJob.setMapOutputValueClass(Text.class);
        jobSortStaffJob.setOutputKeyClass(Text.class);
        jobSortStaffJob.setOutputValueClass(IntWritable.class);
        jobSortStaffJob.setSortComparatorClass(IntValueDescComparator.class);
        
        ControlledJob jobSortStaffJobCtrl=new  ControlledJob(conf);   
        jobSortStaffJobCtrl.setJob(jobSortStaffJob);
        FileInputFormat.addInputPath(jobSortStaffJob, new Path(dstOut));
        FileOutputFormat.setOutputPath(jobSortStaffJob, new Path(sortOut));
        
        // dependence on jobCheckInCtrl
        jobSortStaffJobCtrl.addDependingJob(jobCheckInCtrl);
        

        Job jobCountUser = Job.getInstance(conf, "staffTotalCountjob");
        jobCountUser.setJarByClass(DaliyStaffJobControllerCount.class);
        jobCountUser.setMapperClass(CheckTotalStaffAccountMapper.class);
        jobCountUser.setReducerClass(CheckTotalStaffAccountReducer.class);
        jobCountUser.setOutputKeyClass(Text.class);
        jobCountUser.setOutputValueClass(IntWritable.class);
        
        ControlledJob jobCountUserCtlr=new  ControlledJob(conf);   
        jobCountUserCtlr.setJob(jobCountUser);
        jobCountUserCtlr.addDependingJob(jobSortStaffJobCtrl);
        
        FileInputFormat.addInputPath(jobCountUser, new Path(sortOut));
        FileOutputFormat.setOutputPath(jobCountUser, new Path(dstOutCount));
        
        
        JobControl jobCtrl=new JobControl("myctrl");
        jobCtrl.addJob(jobCheckInCtrl); 
        jobCtrl.addJob(jobSortStaffJobCtrl);   
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
