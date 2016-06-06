package com.time.mapreduce.test.data;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.lib.ChainMapper;
import org.apache.hadoop.mapred.lib.ChainReducer;

/**
 * 
 * 
 * 
 * 
 * 
 * ***/
public class CopyOfHaoopChain {

	/**
	 * 需求： 在第一个Mapper里面过滤大于10000万的数据 第二个Mapper里面过滤掉大于100-10000的数据
	 * Reduce里面进行分类汇总并输出 Reduce后的Mapper里过滤掉商品名长度大于3的数据
	 */

	/**
	 * 
	 * 过滤掉大于10000万的数据
	 * 
	 * */
	private static class AMapper01 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

		@Override
		public void map(LongWritable key, Text value,OutputCollector<Text, Text> output, Reporter reporter)throws IOException {
			String text = value.toString();
			String texts[] = text.split(" ");

			System.out.println("AMapper01里面的数据: " + text);
			if (texts[1] != null && texts[1].length() > 0) {
				int count = Integer.parseInt(texts[1]);
				if (count > 10000) {
					System.out.println("AMapper01过滤掉大于10000数据:  "
							+ value.toString());
					return;
				} else {
					output.collect(new Text(texts[0]), new Text(texts[1]));

				}

			}
		}
	}

	/**
	 * 
	 * 过滤掉大于100-10000的数据
	 * 
	 * */
	private static class AMapper02 extends MapReduceBase implements Mapper<Text, Text, Text, Text> {

		@Override
		public void map(Text key, Text value,OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			int count = Integer.parseInt(value.toString());
			if (count >= 100 && count <= 10000) {
				System.out.println("AMapper02过滤掉的小于10000大于100的数据: " + key
						+ "    " + value);
				return;
			} else {

				output.collect(key, value);
			}

		}
	}

	/**
	 * Reuduce里面对同种商品的 数量相加数据即可
	 * 
	 * **/
	private static class AReducer03 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

		@Override
		public void reduce(Text key, Iterator<Text> values,OutputCollector<Text, Text> output, Reporter reporter)throws IOException {
			int sum = 0;
			System.out.println("进到Reduce里了");

			while (values.hasNext()) {

				Text t = values.next();
				sum += Integer.parseInt(t.toString());

			}

			// 旧API的集合，不支持foreach迭代
			// for(Text t:values){
			// sum+=Integer.parseInt(t.toString());
			// }

			output.collect(key, new Text(sum + ""));

		}

	}

	/***
	 * 
	 * Reduce之后的Mapper过滤 过滤掉长度大于3的商品名
	 * 
	 * **/

	private static class AMapper04 extends MapReduceBase implements Mapper<Text, Text, Text, Text> {

		@Override
		public void map(Text key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

			int len = key.toString().trim().length();

			if (len >= 3) {
				System.out.println("Reduce后的Mapper过滤掉长度大于3的商品名： "
						+ key.toString() + "   " + value.toString());
				return;
			} else {
				output.collect(key, value);
			}

		}

	}

	/***
	 * 驱动主类
	 * **/
	public static void main(String[] args) throws Exception {
		// Job job=new Job(conf,"myjoin");
		JobConf conf = new JobConf(CopyOfHaoopChain.class);
		conf.set("mapred.job.tracker", "192.168.75.130:9001");
		conf.setJobName("t7");
		conf.setJar("tt.jar");
		conf.setJarByClass(CopyOfHaoopChain.class);

		// Job job=new Job(conf, "2222222");
		// job.setJarByClass(HaoopChain.class);
		System.out.println("模式：  " + conf.get("mapred.job.tracker"));
		;

		// job.setMapOutputKeyClass(Text.class);
		// job.setMapOutputValueClass(Text.class);

		// Map1的过滤
		JobConf mapA01 = new JobConf(false);
		ChainMapper.addMapper(conf, AMapper01.class, LongWritable.class,
				Text.class, Text.class, Text.class, false, mapA01);

		// Map2的过滤
		JobConf mapA02 = new JobConf(false);
		ChainMapper.addMapper(conf, AMapper02.class, Text.class, Text.class,
				Text.class, Text.class, false, mapA02);

		// 设置Reduce
		JobConf recduceFinallyConf = new JobConf(false);
		ChainReducer.setReducer(conf, AReducer03.class, Text.class, Text.class,
				Text.class, Text.class, false, recduceFinallyConf);

		// Reduce过后的Mapper过滤
		JobConf reduceA01 = new JobConf(false);
		ChainReducer.addMapper(conf, AMapper04.class, Text.class, Text.class,
				Text.class, Text.class, true, reduceA01);

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setInputFormat(org.apache.hadoop.mapred.TextInputFormat.class);
		conf.setOutputFormat(org.apache.hadoop.mapred.TextOutputFormat.class);

		FileSystem fs = FileSystem.get(conf);
		//
		Path op = new Path("hdfs://192.168.75.130:9000/root/outputchain");
		if (fs.exists(op)) {
			fs.delete(op, true);
			System.out.println("存在此输出路径，已删除！！！");
		}
		//
		//

		org.apache.hadoop.mapred.FileInputFormat.setInputPaths(conf, new Path(
				"hdfs://192.168.75.130:9000/root/inputchain"));
		org.apache.hadoop.mapred.FileOutputFormat.setOutputPath(conf, op);
		// System.exit(conf.waitForCompletion(true)?0:1);
		JobClient.runJob(conf);

	}

}
