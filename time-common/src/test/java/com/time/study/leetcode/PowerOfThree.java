package com.time.study.leetcode;

public class PowerOfThree {
	
	public static void main(String[] args){
		
		System.out.println("isPowerOfThree(0) " + isPowerOfThree(0));
		
		System.out.println("isPowerOfThree(2) " + isPowerOfThree(2));
		
		System.out.println("isPowerOfThree(3) " + isPowerOfThree(3));
		
		System.out.println("isPowerOfThree(8) " + isPowerOfThree(8));
		
		System.out.println("isPowerOfThree(9) " + isPowerOfThree(9));
		
		System.out.println("isPowerOfThree(18) " + isPowerOfThree(18));
		
		System.out.println("isPowerOfThree(27) " + isPowerOfThree(27));
		
	}

	public static boolean isPowerOfThree(int n){
		if(n==0||n<0){
			return false;
		}
		
		return (Math.log10(n)/Math.log10(3)) %1 == 0;
	}
}

