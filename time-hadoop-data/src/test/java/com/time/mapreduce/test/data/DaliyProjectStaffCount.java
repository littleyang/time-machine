package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
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
			String[] arrays =  value.toString().split("\t");
			mWord.set(arrays[0]);
			mValue.set(arrays[1]);
			context.write(mWord, mValue);
			System.out.println("======" + "After mapper :" + mWord + ", " + mValue);
			
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
				
				String[] arrays =  value.toString().split("\t");
				
				mWord.set(arrays[1]);
				mValue.set(arrays[2]);
				context.write(mWord, mValue);
				System.out.println("======" + "After mapper :" + mWord + ", " + mValue);
		
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
		
		
	// 首先实现是mapper类
	static class ProjectAndJobMaperOne extends Mapper<Object, Text, Text, ProjectAndStaffString> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			//System.out.println(value.toString());

			String[] arrays = value.toString().split("\t");

			context.write(new Text(arrays[0].trim()), new ProjectAndStaffString(arrays[1].trim(), 0));

			System.out.println("======" + "After mapper :" + arrays[0].trim() + ", " + arrays[1].trim());

		}
	}
	
	// 首先实现是mapper类
	static class ProjectAndJobMaperTwo extends Mapper<Object, Text, Text, ProjectAndStaffString> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			//System.out.println(value.toString());

			String[] arrays = value.toString().split("\t");

			context.write(new Text(arrays[1].trim()), new ProjectAndStaffString(arrays[0].trim(), 1));
			
			//System.out.println("======" + "After mapper :" + arrays[1].trim() + ", " + arrays[0].trim());

		}
	}
	
	static class ProjectAndJobReducer extends Reducer<Text, ProjectAndStaffString, NullWritable, Text> {
		
		public void reduce(Text key, Iterable<ProjectAndStaffString> values, Context context) throws IOException, InterruptedException {
			
			String phoneValue = "";  
	        String userValue = "";  
	        int num = 0;  
	        for (ProjectAndStaffString value : values) {
	            if (num == 0) {  
	                phoneValue = value.getValue();  
	                num++;  
	            } else {  
	                userValue = value.getValue();  
	                context.write(NullWritable.get(), new Text(userValue +"\t" + key.toString() +"\t" + phoneValue)); 
	                //System.out.println("======" + "After reducer  :" + userValue + ", " + key.toString() + ", " +  phoneValue);
	            }  
	        }
		}
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 //输入路径
        String jobPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/job/part-m-00000";
        
        String jobResultPath = "hdfs://10.0.58.21:9000/result/jobResultPath0007";

        //输出路径，必须是不存在的，空文件加也不行。
        String jobAndStaffPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/staff_and_job/part-m-00000";
        
        String jobAndStaffResultPath = "hdfs://10.0.58.21:9000/result/jobAndStaffResultPath0009";
        
        String projectAndStaffOut = "hdfs://10.0.58.21:9000/result/projectAndStaffOut016";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/outputstaff612Counta";
        
//        Configuration hadoopConfig = new Configuration();
//        //hadoopConfig.
//        Job job = Job.getInstance(hadoopConfig, "JobJob");
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
        
//        Configuration hadoopConfig = new Configuration();
//        //hadoopConfig.
//        Job job = Job.getInstance(hadoopConfig, "ProjecetAndStaffJob");
//        //job.setJarByClass(DaliyProjectStaffCount.class);
//        job.setMapperClass(StaffAndJobMapper.class);
//        job.setReducerClass(StaffAndJobReducer.class);
//        job.setMapOutputKeyClass(Text.class);  
//        job.setMapOutputValueClass(Text.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
//        FileInputFormat.addInputPath(job, new Path(jobAndStaffPath));
//        FileOutputFormat.setOutputPath(job, new Path(jobAndStaffResultPath));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        
	      Configuration hadoopConfig = new Configuration();
	      //hadoopConfig.
	      Job job = Job.getInstance(hadoopConfig, "MapProjectStaffJob");
	      job.setJarByClass(DaliyProjectStaffCount.class);
	      
	      job.setMapperClass(ProjectAndJobMaperOne.class);
	      job.setMapperClass(ProjectAndJobMaperTwo.class);
	      job.setMapOutputKeyClass(Text.class);  
	      job.setMapOutputValueClass(ProjectAndStaffString.class);
	      
	      job.setReducerClass(ProjectAndJobReducer.class);
	      job.setOutputKeyClass(NullWritable.class);  
	      job.setOutputValueClass(Text.class);
	      
	      FileInputFormat.addInputPath(job, new Path(jobResultPath));
	      FileInputFormat.addInputPath(job, new Path(jobAndStaffResultPath));
	      FileOutputFormat.setOutputPath(job, new Path(projectAndStaffOut));
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
