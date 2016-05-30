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

public class TotalNetWorkDataCount{
	
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
				//System.out.println(tuple2._2());
		        return tuple2._2();
		      }
		    });
		
		JavaDStream<Integer> datas = lines.map(new Function<String,Integer>(){
			public Integer call(String str) throws Exception {
				// TODO Auto-generated method stub
				//System.out.println(Integer.parseInt(str.split(" ")[9]));
				return Integer.parseInt(str.split(" ")[9]);
			}
		});
		
		JavaDStream<Integer> result = datas.reduce(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2;
			}
		});
		
		result.print();
		
		jssc.start();
		jssc.awaitTermination();
		
	}
	
}