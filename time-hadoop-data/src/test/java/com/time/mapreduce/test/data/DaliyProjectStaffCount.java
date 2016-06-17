package com.time.mapreduce.test.data;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
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
			
			//System.out.println(value.toString());
			Text mWord = new Text();
			Text mValue = new Text();
			String[] arrays =  value.toString().split("\t");
			System.out.println("======" + "After mapper :" + arrays[0] +   " ==" + arrays[1]);
			mWord.set(arrays[0]);
			mValue.set(arrays[1]);
			context.write(mWord, mValue);
			//System.out.println("======" + "After mapper :" + mWord + ", " + mValue);
			
		}
	}

	static class JobReducer extends Reducer<Text, Text, Text, Text> {
	
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
			 for (Text text : values) {
	                context.write(key, text);
	                System.out.println("======" + "After reducer :" + key + ", " + text);
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
				
				//System.out.println(value.toString());
				
				Text mWord = new Text();
				Text mValue = new Text();
				
				String[] arrays =  value.toString().split("\t");
				
				mWord.set(arrays[2]);
				mValue.set(arrays[1]);
				context.write(mWord, mValue);
				System.out.println("======" + "After mapper :" + mWord + ", " + mValue);
		
			}
		}

		static class StaffAndJobReducer extends Reducer<Text, Text, Text, Text> {
		
			public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
				
				for (Text text : values) {
					context.write(key, text);
		            System.out.println("======" + "After reducer :" + key + ", " + text);
		         }
				
			}
		}
		
		
	// 首先实现是mapper类
	static class ProjectAndJobMaperOne extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			System.out.println(value.toString());
			
			Text mWord = new Text();
			Text mValue = new Text();

			String[] arrays = value.toString().split("\t");
			
			if(arrays.length>1){
				String jobId = arrays[0];
				String projectCode = arrays[1];
				
				if(null!=jobId&&null!=projectCode){	
					mWord.set(jobId);
					mValue.set("project="+projectCode);
					context.write(mWord,mValue);
				}
			}
			//System.out.println("======" + "After mapper :" + arrays[0].trim() + ", " + arrays[1].trim());

		}
	}
	
	// 首先实现是mapper类
	static class ProjectAndJobMaperTwo extends Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			//System.out.println(value.toString());

			String[] arrays = value.toString().split("\t");
			
			Text mWord = new Text();
			Text mValue = new Text();
			
			if(arrays.length>1){
				String jobId = arrays[0];
				String staffId = arrays[1];
				
				 if(null!=jobId&&null!=staffId){
					mWord.set(jobId);
					mValue.set("staff="+staffId);
					context.write(mWord,mValue);
				 }
			}
			//System.out.println("======" + "After mapper :" + arrays[1].trim() + ", " + arrays[0].trim());

		}
	}
	
	static class ProjectAndJobReducer extends Reducer<Text, Text, NullWritable, Text> {
		
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
			LinkedList<String> linkProjets = new LinkedList<String>();  //projects值
            LinkedList<String> linkStaffs = new LinkedList<String>();  //staffs值
            
            for (Text tval : values) {
                String val = tval.toString();  
                if(val.startsWith("project")) {
                	linkProjets.add(val.split("=")[1]);
                } else if(val.startsWith("staff")) {
                	linkStaffs.add(val.split("=")[1]);
                }
            }
            
            for (String u : linkProjets) {
                for (String l : linkStaffs) {
                    context.write(NullWritable.get(), new Text(u + "\t" + l));
                }
            }
            
		}
	}
	
	
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
		
		
		// 首先实现是mapper类
		static class ProjectAndStaffCheckInMaperOne extends Mapper<Object, Text, Text, Text> {

			public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
				
				System.out.println(value.toString());
				
				Text mWord = new Text();
				Text mValue = new Text();

				String[] arrays = value.toString().split("\t");
				
				if(arrays.length>1){
					String staffId = arrays[1];
					String projectCode = arrays[0];
					
					if(null!=staffId&&null!=projectCode){	
						mWord.set(staffId);
						mValue.set("project="+projectCode);
						context.write(mWord,mValue);
					}
				}
				//System.out.println("======" + "After mapper :" + arrays[0].trim() + ", " + arrays[1].trim());

			}
		}
		
		// 首先实现是mapper类
		static class ProjectAndStaffCheckInMaperTwo extends Mapper<Object, Text, Text, Text> {

			public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
				
				//System.out.println(value.toString());

				String[] arrays = value.toString().split("\t");
				
				Text mWord = new Text();
				Text mValue = new Text();
				
				if(arrays.length>1){
					String staffId = arrays[0];
					String staffVewCount = arrays[1];
					
					 if(null!=staffId&&null!=staffVewCount){
						mWord.set(staffId);
						mValue.set("view="+staffVewCount);
						context.write(mWord,mValue);
					 }
				}
				//System.out.println("======" + "After mapper :" + arrays[1].trim() + ", " + arrays[0].trim());

			}
		}
		
		static class ProjectAndStaffViewCountReducer extends Reducer<Text, Text, NullWritable, Text> {
			
			public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
				
				LinkedList<String> linkProjets = new LinkedList<String>();  //projects值
	            LinkedList<String> linkStaffViews = new LinkedList<String>();  //staffs值
	            
	            for (Text tval : values) {
	                String val = tval.toString();  
	                if(val.startsWith("project")) {
	                	linkProjets.add(val.split("=")[1]);
	                } else if(val.startsWith("view")) {
	                	linkStaffViews.add(val.split("=")[1]);
	                }
	            }
	            
	            for (String u : linkProjets) {
	                for (String l : linkStaffViews) {
	                    context.write(NullWritable.get(), new Text(u + "\t" + l));
	                }
	            }
	            
			}
		}
		
		static class CheckProjectAndStaffAccountMapper extends Mapper<Object, Text, Text, IntWritable>{
			
			public void map(Object key, Text value, Context context)
					throws IOException, InterruptedException {
				Text word = new Text();
				IntWritable data = new IntWritable();
				
				String[] arrays = value.toString().split("\t");
				if(arrays.length>1){
					System.out.println(arrays[0] + "=====" + arrays[1]);
					word.set(arrays[0]);
					data.set(Integer.parseInt(arrays[1]));
					context.write(word, data);
				}
				
				//System.out.println("======" + "After Mapper:" + word + ", " + data);
			}
		}
		
		
		static class CheckProjectAndStaffAccountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
			
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
		
		String tempPath = "1002";
		
		 //输入路径
        String jobPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/job/part-m-00000";
        String jobResultPath = "hdfs://10.0.58.21:9000/result/jobResultPath"+tempPath;

        //输出路径，必须是不存在的，空文件加也不行。
        String jobAndStaffPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/staff_and_job/part-m-00000";
        String jobAndStaffResultPath = "hdfs://10.0.58.21:9000/result/jobAndStaffResultPath"+tempPath;
        
        String projectAndStaffOut = "hdfs://10.0.58.21:9000/result/projectAndStaffOut"+tempPath;
 
        //输入路径
        String dst = "hdfs://10.0.58.21:9000/falcon/2016/06/*/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/outputstaff612a"+tempPath;
        
        // staff sorted
        String sortOut = "hdfs://10.0.58.21:9000/result/outputsortedstaff612a"+tempPath;
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/outputstaff612Counta"+tempPath;
        
        String dstProjectStaffView = "hdfs://10.0.58.21:9000/result/dstProjectStaffView"+tempPath;
        
        String dstProjectStaffViewReduslt = "hdfs://10.0.58.21:9000/result/dstProjectStaffViewReduslt"+tempPath;
        
        String dstProjectStaffViewSortedReduslt = "hdfs://10.0.58.21:9000/result/dstProjectStaffViewSortedReduslt"+tempPath;
        
        
        JobConf conf = new JobConf(DaliyProjectStaffCount.class);
        
        Job jobCheckIn = Job.getInstance(conf, "staffcheckincount");
        jobCheckIn.setJarByClass(DaliyProjectStaffCount.class);
        jobCheckIn.setMapperClass(ChechInMapper.class);
        jobCheckIn.setReducerClass(CheckInReducer.class);
        jobCheckIn.setOutputKeyClass(Text.class);
        jobCheckIn.setOutputValueClass(IntWritable.class);
        
        ControlledJob jobCheckInCtrl=new  ControlledJob(conf);   
        jobCheckInCtrl.setJob(jobCheckIn);
        FileInputFormat.addInputPath(jobCheckIn, new Path(dst));
        FileOutputFormat.setOutputPath(jobCheckIn, new Path(dstOut));
        
        Job jobSortStaffJob = Job.getInstance(conf, "staffSortjob");
        jobSortStaffJob.setJarByClass(DaliyProjectStaffCount.class);
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
        jobCountUser.setJarByClass(DaliyProjectStaffCount.class);
        jobCountUser.setMapperClass(CheckTotalStaffAccountMapper.class);
        jobCountUser.setReducerClass(CheckTotalStaffAccountReducer.class);
        jobCountUser.setOutputKeyClass(Text.class);
        jobCountUser.setOutputValueClass(IntWritable.class);
        
        ControlledJob jobCountUserCtlr=new  ControlledJob(conf);   
        jobCountUserCtlr.setJob(jobCountUser);
        jobCountUserCtlr.addDependingJob(jobSortStaffJobCtrl);
        
        FileInputFormat.addInputPath(jobCountUser, new Path(sortOut));
        FileOutputFormat.setOutputPath(jobCountUser, new Path(dstOutCount));
        
 
		Job JobJob = Job.getInstance(conf, "JobJob");
		JobJob.setMapperClass(JobMapper.class);
		JobJob.setReducerClass(JobReducer.class);
		JobJob.setMapOutputKeyClass(Text.class);
		JobJob.setMapOutputValueClass(Text.class);
		JobJob.setOutputKeyClass(Text.class);
		JobJob.setOutputValueClass(Text.class);
		
		ControlledJob JobJobCtlr=new  ControlledJob(conf);   
		JobJobCtlr.setJob(JobJob);
		JobJobCtlr.addDependingJob(jobCountUserCtlr);
		
		FileInputFormat.addInputPath(JobJob, new Path(jobPath));
		FileOutputFormat.setOutputPath(JobJob, new Path(jobResultPath));
		

		Job projectAndStaffJob = Job.getInstance(conf, "ProjecetAndStaffJob");
		projectAndStaffJob.setMapperClass(StaffAndJobMapper.class);
		projectAndStaffJob.setReducerClass(StaffAndJobReducer.class);
		projectAndStaffJob.setMapOutputKeyClass(Text.class);
		projectAndStaffJob.setMapOutputValueClass(Text.class);
		projectAndStaffJob.setOutputKeyClass(Text.class);
		projectAndStaffJob.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(projectAndStaffJob, new Path(jobAndStaffPath));
		FileOutputFormat.setOutputPath(projectAndStaffJob, new Path(jobAndStaffResultPath));
		
		ControlledJob projectAndStaffJobCtlr=new  ControlledJob(conf);   
		projectAndStaffJobCtlr.setJob(projectAndStaffJob);
		projectAndStaffJobCtlr.addDependingJob(JobJobCtlr);
	

		Job MapProjectStaffJob = Job.getInstance(conf, "MapProjectStaffJob");
		MapProjectStaffJob.setMapOutputKeyClass(Text.class);
		MapProjectStaffJob.setMapOutputValueClass(Text.class);
		MapProjectStaffJob.setReducerClass(ProjectAndJobReducer.class);
		MapProjectStaffJob.setOutputKeyClass(Text.class);
		MapProjectStaffJob.setOutputValueClass(Text.class);
		
		MultipleInputs.addInputPath(MapProjectStaffJob, new Path(jobResultPath),TextInputFormat.class, ProjectAndJobMaperOne.class);
		MultipleInputs.addInputPath(MapProjectStaffJob, new Path(jobAndStaffResultPath),TextInputFormat.class, ProjectAndJobMaperTwo.class);
		FileOutputFormat.setOutputPath(MapProjectStaffJob, new Path(projectAndStaffOut));
		
		ControlledJob MapProjectStaffJobCtlr=new  ControlledJob(conf);   
		MapProjectStaffJobCtlr.setJob(MapProjectStaffJob);
		MapProjectStaffJobCtlr.addDependingJob(projectAndStaffJobCtlr);
		
		
		Job	ProjectStaffViewsJob = Job.getInstance(conf, "ProjectStaffViewsJob");
		ProjectStaffViewsJob.setMapOutputKeyClass(Text.class);
		ProjectStaffViewsJob.setMapOutputValueClass(Text.class);
		ProjectStaffViewsJob.setReducerClass(ProjectAndStaffViewCountReducer.class);
		ProjectStaffViewsJob.setOutputKeyClass(Text.class);
		ProjectStaffViewsJob.setOutputValueClass(Text.class);
		
		MultipleInputs.addInputPath(ProjectStaffViewsJob, new Path(projectAndStaffOut),TextInputFormat.class, ProjectAndStaffCheckInMaperOne.class);
		MultipleInputs.addInputPath(ProjectStaffViewsJob, new Path(sortOut),TextInputFormat.class, ProjectAndStaffCheckInMaperTwo.class);
		FileOutputFormat.setOutputPath(ProjectStaffViewsJob, new Path(dstProjectStaffView));
		
		ControlledJob ProjectStaffViewsJobCtlr=new  ControlledJob(conf);   
		ProjectStaffViewsJobCtlr.setJob(ProjectStaffViewsJob);
		ProjectStaffViewsJobCtlr.addDependingJob(MapProjectStaffJobCtlr);
		
		
        Job	ProjectStaffCountJob = Job.getInstance(conf, "ProjectStaffCountJob");
		ProjectStaffCountJob.setMapOutputKeyClass(Text.class);
		ProjectStaffCountJob.setMapOutputValueClass(IntWritable.class);
		ProjectStaffCountJob.setMapperClass(CheckProjectAndStaffAccountMapper.class);
		ProjectStaffCountJob.setReducerClass(CheckProjectAndStaffAccountReducer.class);
		ProjectStaffCountJob.setOutputKeyClass(Text.class);
		ProjectStaffCountJob.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(ProjectStaffCountJob, new Path(dstProjectStaffView));
		FileOutputFormat.setOutputPath(ProjectStaffCountJob, new Path(dstProjectStaffViewReduslt));
		
		ControlledJob ProjectStaffCountJobCtlr=new  ControlledJob(conf);   
		ProjectStaffCountJobCtlr.setJob(ProjectStaffCountJob);
		ProjectStaffCountJobCtlr.addDependingJob(ProjectStaffViewsJobCtlr);
	
		
		Job jobSortProjectStaffViewCountJob = Job.getInstance(conf, "jobSortProjectStaffViewCountJob");
		jobSortProjectStaffViewCountJob.setJarByClass(DaliyUserJobControllerCount.class);
		jobSortProjectStaffViewCountJob.setMapperClass(SortMapper.class);
		jobSortProjectStaffViewCountJob.setReducerClass(SortReducer.class);
		jobSortProjectStaffViewCountJob.setMapOutputKeyClass(IntWritable.class);
		jobSortProjectStaffViewCountJob.setMapOutputValueClass(Text.class);
		jobSortProjectStaffViewCountJob.setOutputKeyClass(Text.class);
		jobSortProjectStaffViewCountJob.setOutputValueClass(IntWritable.class);
		jobSortProjectStaffViewCountJob.setSortComparatorClass(IntValueDescComparator.class);
		
		
		FileInputFormat.addInputPath(jobSortProjectStaffViewCountJob, new Path(dstProjectStaffViewReduslt));
		FileOutputFormat.setOutputPath(jobSortProjectStaffViewCountJob, new Path(dstProjectStaffViewSortedReduslt));
		
		ControlledJob jobSortProjectStaffViewCountJobCtlr=new  ControlledJob(conf);   
		jobSortProjectStaffViewCountJobCtlr.setJob(jobSortProjectStaffViewCountJob);
		jobSortProjectStaffViewCountJobCtlr.addDependingJob(ProjectStaffCountJobCtlr);
		
		
        
        JobControl jobCtrl=new JobControl("myctrl");
        jobCtrl.addJob(jobCheckInCtrl); 
        jobCtrl.addJob(jobSortStaffJobCtrl);   
        jobCtrl.addJob(jobCountUserCtlr);
        jobCtrl.addJob(JobJobCtlr);
        jobCtrl.addJob(projectAndStaffJobCtlr);
        jobCtrl.addJob(MapProjectStaffJobCtlr);
        jobCtrl.addJob(ProjectStaffViewsJobCtlr);
        jobCtrl.addJob(ProjectStaffCountJobCtlr);
        jobCtrl.addJob(jobSortProjectStaffViewCountJobCtlr);
       
        
        
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
