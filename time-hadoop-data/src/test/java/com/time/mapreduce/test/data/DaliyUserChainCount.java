package com.time.mapreduce.test.data;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;



public class DaliyUserChainCount {

	// 首先实现是mapper类
	// Mapper<K1, V1, K2, V2>
	static class ChechInMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);

		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			// TODO Auto-generated method stub
			// 如果包含API调用，则纪录签到一次
			//url: http://api.4009515151.com/api/zhuzher/users/me/badges, 
			//account: 10885667, args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None
			Text word = new Text();
			String values = value.toString();
			String[] arrays = values.split(", ");
			if (arrays.length > 3 && arrays[2].contains("account")) {
				// System.out.println(arrays[2]);
				String[] accountA = arrays[2].split(": ");
				if (accountA.length > 1) {
					String account = accountA[1];
					// if(!account.equals("None")&&account.length()==7){
					// 统计staff的
					if (!account.equals("None") && account.length() == 8) {
						// 统计user
						System.out.println(account);
						word.set(account);
						//context.write(word, one);
						output.collect(word, one);
					}
				}
			}
		}
	}
	
	static class CheckInReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		
		private IntWritable result = new IntWritable();

		@Override
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			// TODO Auto-generated method stub
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			result.set(sum);
			output.collect(key, result);
			System.out.println("======" + "After reduce :" + new Text(key.toString()) + ", " + sum);
		}
	}

	
	static class CheckTotalUserAccountMapper extends MapReduceBase implements Mapper<Text, IntWritable, Text, IntWritable>{

		@Override
		public void map(Text key, IntWritable value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			// TODO Auto-generated method stub
			Text word = new Text();
			IntWritable data = new IntWritable(1);
			
			if(key.toString().length()==7){
				System.out.println("++++++" + key.toString());
				word.set("staff");
				// 如果包含API调用，则纪录签到一次
			}else{
				word.set("user");
			}
			output.collect(word, data);
		}
	}
	
	
	
	
	static class CheckTotalUserAccountReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>{
		
		private IntWritable result = new IntWritable();

		@Override
		public void reduce(Text key, Iterator<IntWritable> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			// TODO Auto-generated method stub
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			result.set(sum);
			output.collect(key, result);
			System.out.println("======" + "After reduce :" + new Text(key.toString()) + ", " + sum);
		}
		
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		 //输入路径
        String dst = "hdfs://10.0.58.21:9000/falcon/2016/06/03/*.log";

        //输出路径，必须是不存在的，空文件加也不行。
        String dstOut = "hdfs://10.0.58.21:9000/result/output602j";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/output603Countb";

//        Configuration hadoopConfig = new Configuration();
//        //hadoopConfig.
//        Job job = Job.getInstance(hadoopConfig, "staff check in count");
//        //job.setJarByClass(DaliyStaffCheckIn.class);
//        job.setMapperClass(ChechInMapper.class);
//        job.setReducerClass(CheckInReducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(job, new Path(dst));
//        FileOutputFormat.setOutputPath(job, new Path(dstOut));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
        
        
        JobConf conf = new JobConf(DaliyUserChainCount.class);
        conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setJobName("DaliyUserChainCount");
        
        JobConf mapUserCount = new JobConf(false);
        ChainMapper.addMapper(conf, ChechInMapper.class, LongWritable.class, Text.class, Text.class, IntWritable.class, false, mapUserCount);
        
        JobConf recduceConf = new JobConf(false);
        ChainReducer.setReducer(conf, CheckInReducer.class, Text.class, IntWritable.class, Text.class, IntWritable.class, true, recduceConf);
        
        JobConf reduceMapperOne = new JobConf(false);
        ChainReducer.addMapper(conf, CheckTotalUserAccountMapper.class, Text.class, IntWritable.class, 
        		Text.class, IntWritable.class, false, reduceMapperOne);
        
        FileInputFormat.addInputPath(conf, new Path(dst));
        FileOutputFormat.setOutputPath(conf, new Path(dstOutCount));
        JobClient.runJob(conf);
       
        
	}
}
