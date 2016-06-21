package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DaliyRequestResponeTime {

	// 首先实现是mapper类
	static class ChechInMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// 如果包含API调用，则纪录签到一次
			Text word = new Text();
			String values = value.toString();
			if(values.contains("INFO")){
				word.set("request");
				context.write(word, one);
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
	
	// 首先实现是mapper类
		static class ChechInReponseMapper extends Mapper<Object, Text, Text, IntWritable> {

			//private final static IntWritable one = new IntWritable(1);
			public void map(Object key, Text value, Context context)
					throws IOException, InterruptedException {
				// 如果包含API调用，则纪录签到一次
				Text word = new Text();
				IntWritable one = new IntWritable();
				String values = value.toString();
				if(values.contains("INFO")){
					String timeAndDate = values.split(" - ")[0];
					System.out.println(timeAndDate);
					if(timeAndDate.length()>0&&timeAndDate.length()<25){
						String time = timeAndDate.split(",")[1];
						word.set("requestime");
						one.set(Integer.parseInt(time));
						context.write(word, one);
					}
				}
			}
		}

		static class CheckInReponseReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
			
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
        String dst = "hdfs://10.0.58.21:9000/falcon/2016/06/17/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/outputreqtotal6173";
        
        String dstOutResponseTime = "hdfs://10.0.58.21:9000/result/outputdstOutResponseTime6173";

        Configuration conf = new Configuration();
        //hadoopConfig.
        
        Job totalRequetsCountJob = Job.getInstance(conf, "TotalRequetsCount");
        totalRequetsCountJob.setMapperClass(ChechInMapper.class);
        totalRequetsCountJob.setReducerClass(CheckInReducer.class);
        totalRequetsCountJob.setOutputKeyClass(Text.class);
        totalRequetsCountJob.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(totalRequetsCountJob, new Path(dst));
        FileOutputFormat.setOutputPath(totalRequetsCountJob, new Path(dstOut));
        
        ControlledJob totalRequetsCountJobCtrl=new  ControlledJob(conf);  
        totalRequetsCountJobCtrl.setJob(totalRequetsCountJob);
        
        
        Job totalRequetsResponeseTimeJob = Job.getInstance(conf, "TotalRequetsResponeseTime");
        totalRequetsResponeseTimeJob.setMapperClass(ChechInReponseMapper.class);
        totalRequetsResponeseTimeJob.setReducerClass(CheckInReponseReducer.class);
        totalRequetsResponeseTimeJob.setOutputKeyClass(Text.class);
        totalRequetsResponeseTimeJob.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(totalRequetsResponeseTimeJob, new Path(dst));
        FileOutputFormat.setOutputPath(totalRequetsResponeseTimeJob, new Path(dstOutResponseTime));
        
        ControlledJob totalRequetsResponeseTimeCtrl=new  ControlledJob(conf);  
        totalRequetsResponeseTimeCtrl.setJob(totalRequetsResponeseTimeJob);
        
        totalRequetsResponeseTimeCtrl.addDependingJob(totalRequetsCountJobCtrl);
        
        JobControl jobCtrl=new JobControl("myctrl");
        jobCtrl.addJob(totalRequetsCountJobCtrl);
        jobCtrl.addJob(totalRequetsResponeseTimeCtrl);
        
        
        Thread  t=new Thread(jobCtrl);   
        t.start(); 
        
        while(true){
        	if(jobCtrl.allFinished()){//如果作业成功完成，就打印成功作业的信息   
        		System.out.println(jobCtrl.getSuccessfulJobList());   
        		jobCtrl.stop();   
        		break;   
        	}  
        }
      
        //System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
