package com.time.study.leetcode;

public class LeetcodeGuessNumber {
	
	public static void main(String[] args){
		
		int n =10;
		
		int result = guess(n);
		
		System.out.println(result);
		
	}
	
	
	public static int guess(int num){
	
		int L = 1,R = num;
        while(L <= R){
            int mid = L + ((R - L) >> 1);
            int res = guessNumber(mid);
            if(res == 0) return mid;
            else if(res == 1)  L = mid + 1;
            else R = mid - 1;
        }
        return L;
		
	}


	private static int guessNumber(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

}
