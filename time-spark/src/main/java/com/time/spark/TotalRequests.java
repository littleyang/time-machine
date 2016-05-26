package com.time.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class TotalRequests {
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		String logFile = "hdfs://10.0.58.21:9000/nginx/2016/05/24/*.log";
		SparkConf conf = new SparkConf().setMaster("spark://10.0.58.21:7077").setAppName("NetworkWordCount");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> distFile = sc.textFile(logFile);
		System.out.println(distFile.count());
	}

}
