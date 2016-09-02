package com.time.study.leetcode;

public class PowerOfTwo {
	
	public static void main(String[] args){
		
		System.out.println("isPowerOfTwo(0) " + isPowerOfTwo(0));
		
		System.out.println("isPowerOfTwo(2) " + isPowerOfTwo(2));
		
		System.out.println("isPowerOfTwo(4) " + isPowerOfTwo(4));
		
		System.out.println("isPowerOfTwo(6) " + isPowerOfTwo(6));
		
		System.out.println("isPowerOfTwo(8) " + isPowerOfTwo(8));
		
		System.out.println("isPowerOfTwo(10) " + isPowerOfTwo(10));
		
		System.out.println("isPowerOfTwo(12) " + isPowerOfTwo(14));
		
		System.out.println("isPowerOfTwo(16) " + isPowerOfTwo(16));
		
		System.out.println("isPowerOfTwo(18) " + isPowerOfTwo(18));
		
		
		System.out.println("isPowerOfTwoAnother(0) " + isPowerOfTwoAnother(0));
		
		System.out.println("isPowerOfTwoAnother(2) " + isPowerOfTwoAnother(2));
		
		System.out.println("isPowerOfTwoAnother(4) " + isPowerOfTwoAnother(4));
		
		System.out.println("isPowerOfTwoAnother(6) " + isPowerOfTwoAnother(6));
		
		System.out.println("isPowerOfTwoAnother(8) " + isPowerOfTwoAnother(8));
		
		System.out.println("isPowerOfTwoAnother(10) " + isPowerOfTwoAnother(10));
		
		System.out.println("isPowerOfTwoAnother(12) " + isPowerOfTwoAnother(14));
		
		System.out.println("isPowerOfTwoAnother(16) " + isPowerOfTwoAnother(16));
		
		System.out.println("isPowerOfTwoAnother(18) " + isPowerOfTwoAnother(18));
	}
	
	public static boolean isPowerOfTwo(int n){
		
		if(n==0||n<0){
			return false;
		}
		
		return (n&(n-1))==0?true:false;
	}
	
	public static boolean isPowerOfTwoAnother(int n){
		
		if(n==0||n<0){
			return false;
		}
		
		return (Math.log10(n)/Math.log10(2))%1==0?true:false;
	}
	
	
	public  static boolean isPowerOfTwoRecursion(int n){
		
		if(n==0||n<0){
			return false;
		}
		
		if(n==1){
			return true;
		}else if(n%2==0){
			return isPowerOfTwoRecursion(n/2);
		}else{
			return false;
		}
		
	}
	
	public  static boolean isPowerOfTwoLoop(int n){
		
		if(n==0||n<0){
			return false;
		}
		
		while(n!=1){
			if(n%2!=0) break;
			n = n / 2;
		}
		return n==1;
		
	}

}
