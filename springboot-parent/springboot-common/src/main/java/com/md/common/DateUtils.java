package com.md.common;

import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * 日期转换工具类
 */
@Configuration
public class DateUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //将数字转换为时间格式
    public static String getDate(){
        String format = sdf.format(1601652395);
        return format;
    }
    public static String getDate(Integer time){
        String format = sdf.format(time);
        return format;
    }
    //获取数据格式时间
    public static Integer getInteDate(){
        return 1601652395;
    }

    public static void main(String[] args) {
        System.out.println(getDate());
    }
}
