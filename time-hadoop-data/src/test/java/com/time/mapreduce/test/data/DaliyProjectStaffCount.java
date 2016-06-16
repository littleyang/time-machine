package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
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
import com.time.mapreduce.test.data.DaliyTotalRequest.ChechInMapper;
import com.time.mapreduce.test.data.DaliyTotalRequest.CheckInReducer;

public class DaliyProjectStaffCount {

	// 首先实现是mapper类
	static class JobMapper extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// 如果包含API调用，则纪录签到一次
			//url: http://api.4009515151.com/api/zhuzher/users/me/badges, 
			//account: 10885667, args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None
			
			System.out.println(value.toString());
			Text mWord = new Text();
			Text mValue = new Text();
			if(value.toString().contains("LB")){
				String values = value.toString().split("LB")[0];
				if(values.length()>19){
					String project = values.substring(values.length()-9, values.length());
					String jobId = values.substring(0,values.length()-18);
					mWord.set(jobId);
					mValue.set(project);
					context.write(mWord, mValue);
					System.out.println("======" + "After mapper :" + project + ", " + jobId);
				}
			}
			//System.out.println("======" + "After mapper :" + new Text(key.toString()) + ", " + values);
			
		}
	}

	static class JobReducer extends Reducer<Text, Text, Text, Text> {
	
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
			 for (Text text : values) {
	                context.write(text, key);
	                System.out.println("======" + "After reducer :" + text + ", " + key);
	         }
			
		}
	}
	
	// 首先实现是mapper类
	static class StaffAndJobMapper extends Mapper<Object, Text, Text, Text> {

			public void map(Object key, Text value, Context context)
					throws IOException, InterruptedException {
				// 如果包含API调用，则纪录签到一次
				//url: http://api.4009515151.com/api/zhuzher/users/me/badges, 
				//account: 10885667, args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None
				
				System.out.println(value.toString());
				Text mWord = new Text();
				Text mValue = new Text();
				
				String values = value.toString().split("-")[0];
				String subTemp = "";
				String staffId = "";
				String jobId = "";
				if(values.contains("true")||values.contains("null")){
					subTemp = values.substring(0, values.length()-9);
				}
				
				if(values.contains("false")){
					subTemp = values.substring(0, values.length()-10);
				}
				
				if(values.startsWith("10")){
					String subTempString = "10" + subTemp.split("10", subTemp.length())[1];
					String finalSubString = subTemp.substring(subTempString.length()-1, subTemp.length());
					staffId = finalSubString.substring(0, 8);
					jobId = finalSubString.substring(8, finalSubString.length());
				}else{
					String subTempString = subTemp.split("10", subTemp.length())[0];
					String finalSubString = subTemp.substring(subTempString.length()-1, subTemp.length());
					staffId = finalSubString.substring(0, 8);
					jobId = finalSubString.substring(8, finalSubString.length());
				}	
				
				mWord.set(staffId);
				mValue.set(jobId);
				
				context.write(mWord, mValue);
				//System.out.println("======" + "After mapper :" + new Text(key.toString()) + ", " + values);
				
			}
		}

		static class StaffAndJobReducer extends Reducer<Text, Text, Text, Text> {
		
			public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
				
				 for (Text text : values) {
		                context.write(text, key);
		                System.out.println("======" + "After reducer :" + text + ", " + key);
		         }
				
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
        String jobPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/job/*";
        
        String jobResultPath = "hdfs://10.0.58.21:9000/result/jobResultPath0006";

        //输出路径，必须是不存在的，空文件加也不行。
        String jobAndStaffPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/staff_and_job/*";
        
        String jobAndStaffResultPath = "hdfs://10.0.58.21:9000/result/jobAndStaffResultPath0004";
        
        
        
        String projectAndStaffOut = "hdfs://10.0.58.21:9000/result/projectAndStaffOut001";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/outputstaff612Counta";
        
//        Configuration hadoopConfig = new Configuration();
//        //hadoopConfig.
//        Job job = Job.getInstance(hadoopConfig, "ProjecetAndStaffJob");
//        //job.setJarByClass(DaliyProjectStaffCount.class);
//        job.setMapperClass(JobMapper.class);
//        job.setReducerClass(JobReducer.class);
//        job.setMapOutputKeyClass(Text.class);  
//        job.setMapOutputValueClass(Text.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
//        FileInputFormat.addInputPath(job, new Path(jobPath));
//        FileOutputFormat.setOutputPath(job, new Path(jobResultPath));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        Configuration hadoopConfig = new Configuration();
        //hadoopConfig.
        Job job = Job.getInstance(hadoopConfig, "ProjecetAndStaffJob");
        //job.setJarByClass(DaliyProjectStaffCount.class);
        job.setMapperClass(StaffAndJobMapper.class);
        job.setReducerClass(StaffAndJobReducer.class);
        job.setMapOutputKeyClass(Text.class);  
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(jobAndStaffPath));
        FileOutputFormat.setOutputPath(job, new Path(jobAndStaffResultPath));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        
//        JobConf conf = new JobConf(DaliyProjectStaffCount.class);
//        Job jobCheckIn = Job.getInstance(conf, "staffcheckincount");
//        jobCheckIn.setJarByClass(DaliyProjectStaffCount.class);
//        jobCheckIn.setMapperClass(ChechInMapper.class);
//        jobCheckIn.setReducerClass(CheckInReducer.class);
//        jobCheckIn.setOutputKeyClass(Text.class);
//        jobCheckIn.setOutputValueClass(IntWritable.class);
//        
//        ControlledJob jobCheckInCtrl=new  ControlledJob(conf);   
//        jobCheckInCtrl.setJob(jobCheckIn);
//        FileInputFormat.addInputPath(jobCheckIn, new Path(dst));
//        FileOutputFormat.setOutputPath(jobCheckIn, new Path(dstOut));
//        
//        Job jobSortStaffJob = Job.getInstance(conf, "staffSortjob");
//        jobSortStaffJob.setJarByClass(DaliyProjectStaffCount.class);
//        jobSortStaffJob.setMapperClass(SortMapper.class);
//        jobSortStaffJob.setReducerClass(SortReducer.class);
//        jobSortStaffJob.setMapOutputKeyClass(IntWritable.class);
//        jobSortStaffJob.setMapOutputValueClass(Text.class);
//        jobSortStaffJob.setOutputKeyClass(Text.class);
//        jobSortStaffJob.setOutputValueClass(IntWritable.class);
//        jobSortStaffJob.setSortComparatorClass(IntValueDescComparator.class);
//        
//        ControlledJob jobSortStaffJobCtrl=new  ControlledJob(conf);   
//        jobSortStaffJobCtrl.setJob(jobSortStaffJob);
//        FileInputFormat.addInputPath(jobSortStaffJob, new Path(dstOut));
//        FileOutputFormat.setOutputPath(jobSortStaffJob, new Path(sortOut));
//        
//        // dependence on jobCheckInCtrl
//        jobSortStaffJobCtrl.addDependingJob(jobCheckInCtrl);
//        
//
//        Job jobCountUser = Job.getInstance(conf, "staffTotalCountjob");
//        jobCountUser.setJarByClass(DaliyProjectStaffCount.class);
//        jobCountUser.setMapperClass(CheckTotalStaffAccountMapper.class);
//        jobCountUser.setReducerClass(CheckTotalStaffAccountReducer.class);
//        jobCountUser.setOutputKeyClass(Text.class);
//        jobCountUser.setOutputValueClass(IntWritable.class);
//        
//        ControlledJob jobCountUserCtlr=new  ControlledJob(conf);   
//        jobCountUserCtlr.setJob(jobCountUser);
//        jobCountUserCtlr.addDependingJob(jobSortStaffJobCtrl);
//        
//        FileInputFormat.addInputPath(jobCountUser, new Path(sortOut));
//        FileOutputFormat.setOutputPath(jobCountUser, new Path(dstOutCount));
//        
//        
//        JobControl jobCtrl=new JobControl("myctrl");
//        jobCtrl.addJob(jobCheckInCtrl); 
//        jobCtrl.addJob(jobSortStaffJobCtrl);   
//        jobCtrl.addJob(jobCountUserCtlr);
//        
//        Thread  t=new Thread(jobCtrl);   
//        t.start(); 
//        
//        while(true){
//        	if(jobCtrl.allFinished()){//如果作业成功完成，就打印成功作业的信息   
//        		System.out.println(jobCtrl.getSuccessfulJobList());   
//        		jobCtrl.stop();   
//        		break;   
//        	}  
//        }
	}
}
