package com.time.study.leetcode;

public class FabRecursive {
	
	public static void main(String[] args){
		
		for(int i=0;i<=20;i++){
			System.out.println(fab(i));
		}
		
	}
	
	public static long fab(int n){
		long result = 0L;
		if(n==0)
			result = 1;
		if(n==1)
			result = 1;
		if(n>1)
			result = fab(n-1)+ fab(n-2);
		return result;
	}

}
