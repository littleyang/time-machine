package com.time.study.leetcode;

/**
 * Created by yangyang on 8/2/16.
 */
public class SuperPower {

    private static int mod = 1337;

    public static void main(String[] args){

        int a = 2;
        int[] b = {3};

        int result =  superPow(a, b);

        System.out.println(result);

    }


    public static int superPow(int a, int[] b) {
        int n = b.length;
        int ans = 1;
        for (int i = n - 1; i >= 0; i--) {
            ans = ans * quick_pow(a, b[i]) % mod;
            a = quick_pow(a, 10);
        }
        return ans;
    }

    public static int quick_pow(int a, int b) {
        int ans = 1;
        a %= mod;
        while (b > 0) {
            if ((b & 1) !=0) ans = ans * a % mod;
            a = a * a % mod;
            b >>= 1;
        }
        return ans;

    }

}
