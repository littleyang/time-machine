package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DaliyStaffCheckIn {

	// 首先实现是mapper类
	static class ChechInMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			// 如果包含API调用，则纪录签到一次
			Text word = new Text();
			
			if (value.toString().contains("POST /api/lebang/staffs/me/work")) {
				word.set("Begin Job: POST /api/lebang/staffs/me/work");
				context.write(word, one);
				System.out.println("======" + "After Mapper:" + word + ", " + one);
	            //System.out.println("======" + "After Mapper:" + new Text(value.toString()) + ", " + one);
			}
			if (value.toString().contains("PATCH /api/lebang/staffs/me/work")) {
				word.set("Add Job Postion: PATCH /api/lebang/staffs/me/work");
				context.write(word, one);
				System.out.println("======" + "After Mapper:" + word + ", " + one);
	            //System.out.println("======" + "After Mapper:" + new Text(value.toString()) + ", " + one);
			}
			if (value.toString().contains("PUT /api/lebang/staffs/me/work")) {
				word.set("Finish Job: PUT /api/lebang/staffs/me/work");
				context.write(word, one);
				System.out.println("======" + "After Mapper:" + word + ", " + one);
	            //System.out.println("======" + "After Mapper:" + new Text(value.toString()) + ", " + one);
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
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 //输入路径
        String dst = "hdfs://10.0.58.21:9000/nginx/2016/05/23/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/output3";

        Configuration hadoopConfig = new Configuration();
        //hadoopConfig.
        
        Job job = Job.getInstance(hadoopConfig, "staff check in count");
        
        //job.setJarByClass(DaliyStaffCheckIn.class);
        
        job.setMapperClass(ChechInMapper.class);

        job.setReducerClass(CheckInReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(dst));

        FileOutputFormat.setOutputPath(job, new Path(dstOut));
      
        System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
