package com.time.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDateUtil {
    
    public static String format(Date date)throws ParseException{
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return sdf.format(date);
    }
    
    public static Date parse(String strDate) throws ParseException{
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return sdf.parse(strDate);
    }
    
    public static Date parseSpecialFormat(String strDate) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss a");
    	return format.parse(strDate);
   }
}