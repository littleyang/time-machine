package com.time.study.leetcode;

public class ReverseWord {
	
	public static void main(String[] args){
		
		String str = "the sky is blue";
		
		String[] arrays = str.split(" ");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i= arrays.length-1;i>-1;i--){
			if (!arrays[i].equals("")) {
                sb.append(arrays[i]).append(" ");
            }
		}
		sb.substring(0, sb.length() - 1);
		
		System.out.println(str);
		
		System.out.println(sb);
		
	}

}
