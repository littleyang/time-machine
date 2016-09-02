package com.time.study.leetcode;

public class SumTwo {
	
	public static void main(String[] args){
		
		int a = 3, b =1;
		
		int r1 = getSum(a, b);
		
		System.out.println(r1);
		
		int r2 = getSumTwo(a,b);
		
		
		System.out.println(r2);
		
	}
	
	
	public static int getSum(int a, int b){
		
		return a + b;
	}

	
	public static int getSumTwo(int a, int b){
		
		while(b != 0){
			int c = a^b;
			b = (a&b)<<1;
			a = c;
		}
		return a;
	}


}
