package com.time.study.leetcode;

public class PowerXN {
	
	public static void main(String[] args){
		
		double x = 100;
		int n = 2;
		System.out.println( x+" power " + n + " ===== "+ pow(x,n));

		double x1 = 100;
		int n1 = -2;
		System.out.println( x1 +" power " + n1 + " ===== "+ pow(x1,n1));

	}
	
	public static double pow(double x, int n){
		
		double result = 0;
		
		if(n == 0){
			result = 1;
		}
		
		if(n<0){
			result = 1/power(x,n);
		}else{
			result = power(x,n);
		}
		
		return result;
		
	}
	
	public static double power(double x, int n){
		
		if(n == 0){
			return 1;
		}
		
		double v = power(x,n/2);
		
		if(n%2==0){
			return v*v;
		}else{
			return v*v*x;
		}
		
	}

}
