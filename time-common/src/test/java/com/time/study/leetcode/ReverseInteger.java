package com.time.study.leetcode;

/**
 * @description: reverse integer
 * @author: yang.zhou
 * @version: 1.0.0
 * @since: 2016-09-09 3:38 PM
 */

public class ReverseInteger {

    public static void main(String[] args){

        int s = 123;
        System.out.println(reverseInteger(s));
    }

    public static int reverseInteger(int x){

        if(x>=Integer.MAX_VALUE)
            return 0;

        int num = Math.abs(x);
        int res = 0;
        while(num!=0){
            if(res>(Integer.MAX_VALUE-num%10)/10)
                return 0;
            res = res*10+num%10;
            num /= 10;
        }
        return x>0?res:-res;
    }

}
