package com.time.spark;

import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class SparkSQLOne {
	
	public static void main(String[] args){
		
		//String[] jars = {"mysql-connector-java-5.1.38.jar"};
		//SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkSQLTest").setJars(jars);
		
		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("SparkSQLTest");
		
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		SQLContext sqlCtx = new SQLContext(sc);
		
//		//String url = "jdbc:mysql://10.0.72.51:3306/falcon";
//		
//		String userName = "wy_app";
//		
//		String pwd = "V0tkEIve2";
		
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		
		String user = "root";
		
		String pwd = "640228";	
		
		Properties props = new Properties();
		props.put("user", user);
	    props.put("password", pwd);
	    
	    //String[] predicates = new String[]{"name"};
		
		//DataFrame df = sqlCtx.read().jdbc(url, "users", predicates, props);
		
		//DataFrame df = sqlCtx.read().jdbc(url, "staff", props);
		
		//DataFrame df = sqlCtx.read().jdbc(url, "staff", props);
		
		//df.show();
		
		//df.printSchema();
		
		//df.select("name").show();
		
		//df.filter(df.col("name").equalTo("test")).show();
		
		//df.groupBy("name").count().show();
		
		//df.registerTempTable("staff");
		
		
		//DataFrame dfStaff = sqlCtx.sql("select * from staff");
		
		//dfStaff.show();
	    
	    DataFrame df = sqlCtx.read().format("jdbc").option("url", url).option("user", user).option("dbtable", "staff").option("password", pwd).load();
	    
	    df.printSchema();
	    
	    df.registerTempTable("staff");
	    
	    df.show();
		
	}

}
