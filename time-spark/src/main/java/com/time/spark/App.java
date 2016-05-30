package com.time.spark;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String str = "182.200.1.174 - - [30/May/2016:09:41:27 +0800] \"GET /api/zhuzher/users/me/index?express=yes HTTP/1.1\" 200 5253 \"-\" \"-\"";
        String[] strs = str.split(" ");
        System.out.println(strs[9]);
        
    }
}
