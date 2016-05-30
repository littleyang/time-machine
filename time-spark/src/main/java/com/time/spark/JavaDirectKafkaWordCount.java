package com.time.spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

public class JavaDirectKafkaWordCount{
	
	public static void main(String[] args){
		
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
		
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(10));
		
		String zkKeeperHost = "10.0.58.21:2181";
		
		String groupId = "test";
		
		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		
		topicMap.put("nginx", 1);
		
		JavaPairReceiverInputDStream<String, String> nginxStream = KafkaUtils.createStream(jssc, zkKeeperHost, groupId, topicMap);
		
		JavaDStream<String> lines = nginxStream.map(new Function<Tuple2<String, String>, String>() {
			
			private static final long serialVersionUID = 1L;

			public String call(Tuple2<String, String> tuple2) {
				System.out.println(tuple2._2());
		        return tuple2._2();
		      }
		    });
		 
		//lines.print();
		
		JavaDStream<String> postString = lines.filter(new Function<String,Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String x) throws Exception {
				// 查看首页的功能的
				if(x.contains("/api/zhuzher/users/me/index"))
					return true;
				return false;
			}
		}); 
		postString.print();
		
		/**
		 * 分离出每一行的内容
		 */
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			private static final long serialVersionUID = 1L;
			public List<String> call(String x) {
		        return Arrays.asList(x.split(" "));
		      }
		 });
		
		JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
				new PairFunction<String, String, Integer>() {
					public Tuple2<String, Integer> call(String s) {
						return new Tuple2<String, Integer>(s, 1);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
					}
				}
		);
		
		 //wordCounts.print();
		 jssc.start();
		 jssc.awaitTermination();
		
	}
	
}