package com.time.mapreduce.test.data;

public class WorkTe {
	
	public static void main(String[] args){
		String str = "2016-06-03 14:25:17,685 - falcon - INFO - 220.178.77.82 request: [200 OK] GET, "
				+ "url: http://api.4009515151.com/api/zhuzher/users/me/badges, account: 10885667, "
				+ "args: ImmutableMultiDict([]), form: ImmutableMultiDict([]), json: None";
		//String[] strs = str.split(", ");
		//System.out.println(strs[2]);
		System.out.println(str.split(", ")[2].split(": ")[1]);
	}

}
