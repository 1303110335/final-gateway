/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author xuleyan
 * @version DateTimeUtil.java, v 0.1 2021-07-17 10:22 上午
 */
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    // 日期格式
    public static final String ACCOUNT_DATETIME_PATTERN = "yy-MM-dd HH:mm";
    public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORMAL_DATETIME_PATTERN_EXT = "MM-dd HH:mm";
    public static final String NORMAL_TIME_PATTERN = "HH:mm:ss";
    public static final String NORMAL_TIME_MINUTE = "HH:mm";
    public static final String NORMAL_DATETIME_MILLI_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String LL_DATETIME_PATTERN = "yyyyMMddHHmmss";
    public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    public static final String MONTH_PATTERN = "MM";
    public static final String DAILY_DATE_PATTERN = "yyyyMMdd";
    public static final String CHINESE_DATE_PATTERN = "MM月dd日";
    public static final String CHINESE_YEAR_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_YEAR_DATE_PATTERN_MINUTE = "yyyy年MM月dd日 HH:mm";
    public static final String NORMAL_YEAR_YY = "yyyy";


    /**
     * 日期格式化
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        try {
            result = sdf.format(date);
        } catch (Exception e) {
           logger.error("日期转换异常", e);
        }
        return result;
    }
}