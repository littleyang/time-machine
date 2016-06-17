package com.time.mapreduce.test.data;

public class WorkTe {
	
	public static void main(String[] args){
//		String str = "2016-06-03 14:25:17,685 - falcon - INFO - 220.178.77.82 request: [200 OK] GET, "
//				+ "url: http://api.4009515151.com/api/zhuzher/users/me/badges, account: 10885667, "
//				+ "args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None";
//		//String[] strs = str.split(", ");
//		//System.out.println(strs[2]);
//		System.out.println(str.split(", ")[2].split(": ")[1]);
//		
//		String string = "219.142.245.18 - - [12/Jun/2016:11:23:46 +0800] "
//				+ "\"GET /api/lebang/staffs/me/modules HTTP/1.1\" 200 1067 \"-\" "
//				+ "\"VKStaffAssistant/3.0.7 (iPad; iOS 8.2; Scale/2.00)\" ";
//		
//		System.out.println(string.split(" ")[0]);
		
		
		
		
		//String string = "43024403001344030013LB10021null";
		
//		String string = "43194403002344030023LB100041";
//		
//		String temp = string.split("LB")[0];
//		
//		System.out.println("temp : " + temp);
//		
//		String project_code = temp.substring(temp.length()-9, temp.length());
//		
//		System.out.println("project_code : " + project_code);
//		
//		String jobId = temp.substring(0,temp.length()-18);
//		
//		System.out.println("job_id : " + jobId);
		
		
		
		
		
		//String string = "7510527901101true2016-05-12 11:17:49.02016-06-15 10:06:23.0";
		String string = "3501010532661083true2016-05-12 11:17:49.02016-06-16 08:44:41.0";
		
		String temp = string.split("-")[0];
		
		String subTemp = "";
		
		System.out.println("temp : " + temp);
		
		if(temp.contains("true")||temp.contains("null")){
			subTemp = temp.substring(0, temp.length()-9);
		}
		
		if(temp.contains("false")){
			subTemp = temp.substring(0, temp.length()-10);
		}
		
		if(temp.startsWith("10")){
			String subTempString = "10" + subTemp.split("10", subTemp.length())[1];
			String finalSubString = subTemp.substring(subTempString.length()-1, subTemp.length());
			System.out.println("subTempString : " + finalSubString);
			String staffId = finalSubString.substring(0, 8);
			System.out.println("staffId : " + staffId);
			String jobId = finalSubString.substring(8, finalSubString.length());
			System.out.println("jobId : " + jobId);
		}else{
			String subTempString = subTemp.split("10", subTemp.length())[0];
			String finalSubString = subTemp.substring(subTempString.length()-1, subTemp.length());
			System.out.println("subTempString : " + finalSubString);
			String staffId = finalSubString.substring(0, 8);
			System.out.println("staffId : " + staffId);
			String jobId = finalSubString.substring(8, finalSubString.length());
			System.out.println("jobId : " + jobId);
		}	
		
		
	}

}
