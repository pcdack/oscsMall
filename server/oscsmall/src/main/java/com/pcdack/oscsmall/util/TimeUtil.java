package com.pcdack.oscsmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by pcdack on 17-9-10.
 *
 */
public class TimeUtil {
    public static final String NORMAL_FORMAT="yyyy-MM-dd hh:mm:ss";

    public static Date strTodate(String data,String format){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(format);
        DateTime dateTime=dateTimeFormatter.parseDateTime(data);
        return dateTime.toDate();
    }
    public static String dateTostr(Date date,String format){
        if (date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(DateTimeFormat.forPattern(format));
    }
    public static Date strTodate(String data){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(NORMAL_FORMAT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(data);
        return dateTime.toDate();
    }
    public static String dateTostr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DateTimeFormat.forPattern(NORMAL_FORMAT));
    }

}
