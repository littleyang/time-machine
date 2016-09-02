package com.time.study.leetcode;

public class StringReverse {
	
	public static void main(String[] args){
		
		String str = "hello";
		StringBuilder temp = new StringBuilder("");
		
		for(int i=str.length()-1;i>=0;i--){
			temp.append(str.charAt(i));
		}
		
//		StringBuffer strb = new StringBuffer(str);
//		strb.reverse();
		
		
		System.out.println(temp.toString());
	}

}
