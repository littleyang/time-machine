package com.time.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class StaffCheckInStatic {
	
	public static void main(String[] args){
		
		String logFile = "hdfs://10.0.58.21:9000/nginx/2016/05/24/*.log";
		
		//SparkConf conf = new SparkConf().setMaster("spark://10.0.58.21:7077").setAppName("StaffCheckInStatic");
		SparkConf conf = new SparkConf().setMaster("spark://10.0.58.21:7077").setAppName("StaffCheckInStatic");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> distFile = sc.textFile(logFile);
		
		//System.out.println("员工check in数据 " + distFile.count());
		
		// 读取到check in的数据
		JavaRDD<String> staffCheckIns = distFile.filter(new Function<String,Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String x) throws Exception {
				// 查看首页的功能的
				if(x.contains("/api/lebang/staffs/me/work"))
					return true;
				return false;
			}
		});
		System.out.println("员工check in数据" + staffCheckIns.count());
		
		// 员工开始工作数据
		JavaRDD<String> staffBeginWorkRdd = staffCheckIns.filter(new Function<String,Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String x) throws Exception {
				// 查看首页的功能的
				if(x.contains("POST /api/lebang/staffs/me/work"))
					return true;
				return false;
			}
		});
		System.out.println("员工开始工作数据" + staffBeginWorkRdd.count());
		
		// 员工结束工作数据
		JavaRDD<String> staffEndWorkRdd = staffCheckIns.filter(new Function<String,Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String x) throws Exception {
				// 查看首页的功能的
				if(x.contains("POST /api/lebang/staffs/me/work"))
					return true;
				return false;
			}
		});
		System.out.println("员工结束工作数据" + staffEndWorkRdd.count());
		
		// 员工上报位置数据
		JavaRDD<String> staffPositionRdd = staffCheckIns.filter(new Function<String,Boolean>(){
			private static final long serialVersionUID = 1L;
			public Boolean call(String x) throws Exception {
			// 查看首页的功能的
				if(x.contains("POST /api/lebang/staffs/me/work"))
					return true;
				return false;
			}
		});
		System.out.println("员工上报位置数据" + staffPositionRdd.count());
		
	}
}
