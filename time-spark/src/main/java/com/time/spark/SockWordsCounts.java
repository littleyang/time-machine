package com.time.spark;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class SockWordsCounts {
	
	public static void main(String[] args){
		
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
		
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
		
		JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
		
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String,String>(){

			public Iterable<String> call(String arg0) throws Exception {
				// TODO Auto-generated method stub
				return Arrays.asList(arg0.split(" "));
			}
			
		});
		
		// map to key - value pairs
		JavaPairDStream<String,Integer> wordsMap = words.mapToPair(new PairFunction<String,String,Integer>(){
			public Tuple2<String, Integer> call(String str) throws Exception {
				// TODO Auto-generated method stub
				//return null;
				return new Tuple2<String, Integer>(str,1);
			}	
		});
		
		//reduce and count the words
		JavaPairDStream<String,Integer> reduceWords = wordsMap.reduceByKey(new Function2<Integer,Integer,Integer>(){

			public Integer call(Integer i1, Integer i2) throws Exception {
				// TODO Auto-generated method stub
				return i1+i2;
			}
			
		});
		
		reduceWords.print();
		
		jssc.start();
		
		jssc.awaitTermination();
		
	}

}
