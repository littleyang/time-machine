package com.time.study.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yangyang on 8/2/16.
 */
public class Intersection {

    public static void main(String[] args){

        int[] a = {1,2,3,4};
        int[] b = {2,3};

//        Arrays.sort(a);
//        Arrays.sort(b);
//
//        ArrayList<Integer> result = new ArrayList<Integer>();
//
//        for(int i=0;i<a.length;i++){
//
//            if(i==0 || (i>0 && a[i]!=a[i-1])){
//
//                if(binarySearch(b,a[i])){
//                    result.add(a[i]);
//                }
//
////                if(Arrays.binarySearch(b, a[i])>-1){
////                    result.add(a[i]);
////                }
//            }
//        }
//
//        System.out.println(result.size());

           System.out.print(intersection(a,b).length);
    }

    public static int[] intersection(int[] nums1, int[] nums2){

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        ArrayList<Integer> result = new ArrayList<Integer>();

        for(int i=0;i<nums1.length;i++){

            if(i==0 || (i>0 && nums1[i]!=nums1[i-1])){

                if(binarySearch(nums2,nums1[i])){
                    result.add(nums1[i]);
                }
            }
        }
        //return result.toArray();

        int[] res = new int[result.size()];
        int k=0;
        for(int i: result){
            res[k++] = i;
        }
        return res;
    }

    public static boolean binarySearch(int[] arrays, int target){


        int left = 0;
        int right = arrays.length-1;

        while(left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arrays[mid] == target) {
                return true;
            } else if (arrays[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;


//        int low = 0;
//        int high = arrays.length-1;
//
//        while (low <= high) {
//            int mid = (low + high) >>> 1;
//            int midVal = arrays[mid];
//
//            if (midVal < target)
//                low = mid + 1;
//            else if (midVal > target)
//                high = mid - 1;
//            else
//                return true;
//        }
//        return false;
    }

}
