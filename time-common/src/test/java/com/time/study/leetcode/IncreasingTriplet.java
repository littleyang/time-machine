package com.time.study.leetcode;

public class IncreasingTriplet {
	
	public static void main(String[] args){
		
		int[] arrays = {1, 2, 3, 4, 5};
		
		boolean result = increasingTriplet(arrays);
		
		System.out.println(result);
		
	}
	
	public static boolean increasingTriplet(int[] arrays){
		
		// 维护最小的连续两个数，如果有第三个数比这两个数大，且最近，则true
		// o(n)
		// space(1) 不需要额外的空间开销
		
		int n1 = Integer.MAX_VALUE;
		
		int n2 = Integer.MAX_VALUE;
		
		for(int n=0;n<arrays.length;n++){
			
			if(arrays[n]<=n1){
				n1 = arrays[n];
			}else if(arrays[n]<=n2){
				n2 = arrays[n];
			}else{
				return true;
			}
		}
		return false;
	}

}
