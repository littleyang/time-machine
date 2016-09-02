package com.time.study.leetcode;

/**
 * @description:
 * @author: yang.zhou
 * @version: 1.0.0
 * @since: 2016-09-02 3:21 PM
 */

public class LengthOfLastWords {

    public static void main(String[] args){

        String words = "hello, world";
        System.out.println(lengthOfLastWords(words));
    }

    public static int lengthOfLastWords(String words){

        String[] wordsArrays = words.split(" ");
        if(wordsArrays.length>0){
            return wordsArrays[wordsArrays.length-1].length();
        }else{
            return 0;
        }

    }
}
