package com.time.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间格式化类
 * @author yangyang
 *
 */
public class TimeDateUtil {
    
	/**
	 * 格式化某个日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
    public static String format(Date date)throws ParseException{
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return sdf.format(date);
    }
    
    /**
     * 转换某个字符串到日期格式
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException{
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return sdf.parse(strDate);
    }
    
    /**
     * 某个格式的特殊
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseSpecialFormat(String strDate) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss a");
    	return format.parse(strDate);
   }
    
   /**
    * 获取某个日期的前一天0点
    * @param specifiedDay
    * @return yyyy-MM-dd 00:000:00
    * @throws ParseException
    */
   public static Date getSpecifiedDayZeroHourBefore(Date date) throws ParseException {
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(c.getTime()));
    }  
   
   
   public static Date getSpecifiedDayNDayAfter(Date date,int n) throws ParseException {
       Calendar c = Calendar.getInstance();  
       c.setTime(date);  
       int day = c.get(Calendar.DATE);  
       c.set(Calendar.DATE, day + n);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       return sdf.parse(sdf.format(c.getTime()));
   }  
  
   
   /**
    * 
    * @param date
    * @return
    * @throws ParseException
    */
   public static Date getSpecifiedDay24HourBefore(Date date) throws ParseException {
       Calendar c = Calendar.getInstance();  
       c.setTime(date);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       return sdf.parse(sdf.format(c.getTime()));
   }  
   
}