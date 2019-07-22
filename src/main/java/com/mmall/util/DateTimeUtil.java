package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by JayJ on 2018/4/26.
 **/
public class DateTimeUtil {

    //joda - time

    private static final String STANDAED_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if (date==null){return StringUtils.EMPTY;}
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDAED_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if (date==null){return StringUtils.EMPTY;}
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDAED_FORMAT);
    }

    public static void main(String[] args){
        System.out.println(dateToStr(new Date()));
        System.out.println(strToDate("2018-04-26 06:09:55"));
    }
}
