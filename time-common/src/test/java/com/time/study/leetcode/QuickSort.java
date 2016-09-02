package com.time.study.leetcode;

public class QuickSort {
	
	public static void main(String[] args){
		
		int[] arrays = {8,9,6,4,10,12,1};
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
		quickSort(arrays,0,arrays.length-1);
		
		System.out.println("=======================");
		
		
		for(int i=0;i<arrays.length;i++){
			System.out.println(arrays[i]);
		}
		
		
	}
	
	public static void quickSort(int[] arrays, int low, int high){
		
		if(low<high){
			int middle = findMiddle(arrays,low,high);
			quickSort(arrays,0,middle-1);
			quickSort(arrays,middle+1,high);
		}
		
	}
	
	public static int findMiddle(int[] arrays,int low,int high){
		
		int povit = arrays[low];
		
		if(low<high){
			
			while(low<high&&arrays[high]>povit){
				high--;
			}
			
			if(low<high){
				arrays[low] = arrays[high];
			}
			
			while(low<high&&arrays[low]<=povit){
				low++;
			}
			
			if(low<high){
				arrays[high] = arrays[low];
			}
		}
		arrays[low] = povit;
		return low;
	}
}


