package com.hywisdom.platform.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期和cron表达式互转的工具类
 * @author yyh
 * @date 2019/9/23 11:19
 */
public class DateCronUtil {

    /**带年月日*/
    private static final String DATE_FORMAT_DATE_TIME = "ss mm HH dd MM ? yyyy";

    /**不带年月日*/
    private static final String DATE_FORMAT_TIME = "ss mm HH";

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * @param cron
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String cron, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        if (cron != null) {
            date = sdf.parse(cron);
        }
        return date;
    }

    /***
     * convert Date to cron ,eg.  "08 22 11 23 09 ? 2019"
     * @param date  : 时间点(有年月日)
     * @return
     */
    public static String getDATETIMECron(Date date) {
        return formatDateByPattern(date, DATE_FORMAT_DATE_TIME);
    }

    /***
     * convert Date to cron ,eg.  "08 22 11"
     * @param date  : 时间点
     * @return
     */
    public static String getTIMECron(Date date) {
        return formatDateByPattern(date, DATE_FORMAT_TIME);
    }

    /***
     * convert cron to Date
     * @param cron  : cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDateByDATETIME(String cron) throws ParseException {
        return parseStringToDate(cron, DATE_FORMAT_DATE_TIME);
    }

    /***
     * convert cron to Date
     * @param cron  : cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDateByTIME(String cron) throws ParseException {
        return parseStringToDate(cron, DATE_FORMAT_TIME);
    }

    public static void main(String[] args) {
        System.out.println(getDATETIMECron(new Date()));
        System.out.println(getTIMECron(new Date()));
    }
}
