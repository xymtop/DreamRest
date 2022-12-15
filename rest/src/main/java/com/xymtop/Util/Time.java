package com.xymtop.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public  static  String getUnixTime(){
        Date date = new Date();//获取当前的日期
        Long unix =  System.currentTimeMillis();
       String str =  unix.toString();
        return  str;
    }

    public  static  String getTimeByUnix(String unix){
        Long timestamp =Long.valueOf(unix);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timestamp));
    }
}
