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

public class DaliyTotalNetData {

	// 首先实现是mapper类
	static class ChechInMapper extends Mapper<Object, Text, Text, IntWritable> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Text word = new Text();
			IntWritable data = new IntWritable(1);
			data.set(Integer.parseInt(value.toString().split(" ")[9]));
			word.set("requestData");
			// 如果包含API调用，则纪录签到一次
			context.write(word, data);
			System.out.println("======" + "After Mapper:" + word + ", " + data);
		}

	}

	static class CheckInReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum = sum + Integer.parseInt(val.toString());
				System.out.println("data 3333 " + val);
			}
			System.out.println("data sum " + sum);
			result.set(sum);
			context.write(key, result);
			System.out.println("======" + "After reduce :" + new Text(key.toString()) + ", " + sum);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 //输入路径
        String dst = "hdfs://10.0.58.21:9000/nginx/2016/05/26/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/outputdata26f";

        Configuration hadoopConfig = new Configuration();
        //hadoopConfig.
        
        Job job = Job.getInstance(hadoopConfig, "total data request");
        
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
