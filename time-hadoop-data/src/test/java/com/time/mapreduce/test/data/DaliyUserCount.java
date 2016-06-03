package com.time.mapreduce.test.data;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DaliyUserCount {

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
				String[] accountA = arrays[2].split(": ");
				if(accountA.length>1){
					String account = accountA[1];
					if(!account.equals("None")){
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
	
	
	static class CheckTotalUserAccountMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			Text word = new Text();
			IntWritable data = new IntWritable(1);
			if(key.toString().length()>7){
				word.set("user");
				// 如果包含API调用，则纪录签到一次
			}else{
				word.set("staff");
			}
			context.write(word, data);
			System.out.println("======" + "After Mapper:" + word + ", " + data);
		}
	}
	
	
	static class CheckTotalUserAccountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
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
        String dst = "hdfs://10.0.58.21:9000/falcon/2016/06/02/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/output602e";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/output602Count";

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
        
//        Configuration hadoopConfigNew = new Configuration();
//        Job userTotalCountjob = Job.getInstance(hadoopConfigNew, "userTotalCountjob");
//        userTotalCountjob.setMapperClass(CheckTotalUserAccountMapper.class);
//        userTotalCountjob.setReducerClass(CheckTotalUserAccountReducer.class);
//        userTotalCountjob.setOutputKeyClass(Text.class);
//        userTotalCountjob.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(userTotalCountjob, new Path(dstOut));
//        FileOutputFormat.setOutputPath(userTotalCountjob, new Path(dstOutCount));
        
      
        System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
