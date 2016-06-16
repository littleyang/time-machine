package com.time.mapreduce.test.data;

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.io.NullWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;  
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.util.Tool;  
import org.apache.hadoop.util.ToolRunner;  

import com.time.mapreduce.test.data.DaliyProjectStaffCount.ProjectAndJobMaperOne;
import com.time.mapreduce.test.data.DaliyProjectStaffCount.ProjectAndJobMaperTwo;
import com.time.mapreduce.test.data.DaliyProjectStaffCount.ProjectAndJobReducer;

public class MultiMapMain extends Configuration implements Tool{

	@Override
	public void setConf(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return new Configuration();
	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		 //输入路径
        String jobPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/job/part-m-00000";
        
        String jobResultPath = "hdfs://10.0.58.21:9000/result/jobResultPath0007";

        //输出路径，必须是不存在的，空文件加也不行。
        String jobAndStaffPath = "hdfs://10.0.58.21:9000/user/hive/warehouse/falcon.db/staff_and_job/part-m-00000";
        
        String jobAndStaffResultPath = "hdfs://10.0.58.21:9000/result/jobAndStaffResultPath0009";
        
        String projectAndStaffOut = "hdfs://10.0.58.21:9000/result/projectAndStaffOut015";
        
        String dstOutCount = "hdfs://10.0.58.21:9000/result/outputstaff612Counta";
        
        Configuration hadoopConfig = new Configuration();
	      //hadoopConfig.
		Job job = Job.getInstance(hadoopConfig, "MapProjectStaffJob");
		job.setJarByClass(DaliyProjectStaffCount.class);

		job.setMapperClass(ProjectAndJobMaperOne.class);
		job.setMapperClass(ProjectAndJobMaperTwo.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ProjectAndStaffString.class);

		job.setReducerClass(ProjectAndJobReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(jobResultPath));
		FileInputFormat.addInputPath(job, new Path(jobAndStaffResultPath));
		FileOutputFormat.setOutputPath(job, new Path(projectAndStaffOut));
		
		job.waitForCompletion(true);  

		return 0;
	}
	
	public static void main(String[] args) throws Exception {  
        Configuration conf = new Configuration();  
        ToolRunner.run(conf, new MultiMapMain(), args); // 调用run方法  
    } 

}
