package com.hywisdom.platform.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
* @Class_name: DateUtil
* @Package: com.hywisdom.hyzlutil.geneUtil
* @Version: v1.0
* @Description:   日期常用类
* @Author: xfl
* @Date: 2018/7/24
**/
public class DateUtil {

    static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    static SimpleDateFormat monFormat = new SimpleDateFormat("yyyyMM");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat timeFormatSd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat dateFormatSd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @Method formateDate
     * @Description:  yyyy-MM-dd转为yyyyMMdd
     * @param date
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2018/8/31
    **/
    public static String formateDate(String date){
        try {
            return dateFormat.format(dateFormatSd.parse(date));

        }catch (Exception e){

        }
    return date;
    }

    /**
     * @Method formateDateSD
     * @Description:  yyyyMMdd转为yyyy-MM-dd
     * @param date
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2018/8/31
    **/
    public static String formateDateSD(String date){
        try {
            return dateFormatSd.format(dateFormat.parse(date));

        }catch (Exception e){

        }
        return date;
    }

	/**
	 * 当前系统时间戳
	 * @return
	 */
	public static String currentTimestamp() {
		return String.valueOf(new Date().getTime());
	}

	/**
	 * 当前系统年（yyyy）
	 * @return
	 */
	public static String currentYear() {
		return yearFormat.format(new Date());
	}

	/**
	 * 当前系统月（yyyyMM）
	 * @return
	 */
	public static String currentMon() {
		return monFormat.format(new Date());
	}

	/**
	 * 当前系统日期（yyyyMMdd）
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(new Date());
	}

	/**
	 * 当前系统时间（yyyyMMddHHmmss）
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(new Date());
	}


	/**
	 * <获取当前时间,格式"yyyy-MM-dd HH:mm:ss">
	 *
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getCurrentTime()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @Method compareYear
	 * @Description:  比较年，，若year1在year2之前，则返回-1，同一天返回0，之后返回1,其他无法比较
	 * @param year1
	 * @param year2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/30
	**/
	public static int compareYear(String year1,String year2){
		return compareTimes(year1,year2,yearFormat);
	}

	/**
	 * @Method compareMon
	 * @Description:  比较月份，若mon1在mon2之前，则返回-1，同一天返回0，之后返回1,其他无法比较
	 * @param mon1
	 * @param mon2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/30
	**/
	public static int compareMon(String mon1,String mon2){
		return compareTimes(mon1,mon2,monFormat);
	}

	/**
	 * @Method compareWeek
	 * @Description:  date1在date2所在周之前，返回-1，在date2所在周内，返回0，在date2所在周后，返回1
	 * @param date1
	 * @param date2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/31
	**/
    public static int compareWeek(String date1,String date2){
	    List<String> week=getWeekFirstLastDay(date2);
	    if(compareTimes(date1,week.get(0).replace("-",""),dateFormat)==-1){
	        return -1;
        }else if(compareTimes(date1,week.get(1).replace("-",""),dateFormat)==1){
	        return 1;
        }else{
	        return 0;
        }
    }


	/**
	 * @Method compareDate
	 * @Description:  日期比较，若date1在date2之前，则返回-1，同一天返回0，之后返回1,其他无法比较
	 * @param date1
	 * @param date2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/23
	**/
	public static int compareDate(String date1,String date2){
		return compareTimes(date1,date2,dateFormat);
	}

	/**
	 * @Method compareDate
	 * @Description:  日期比较，若date1在date2之前，则返回-1，同一天返回0，之后返回1,其他无法比较
	 * @param date1
	 * @param date2
	 * @param type
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/11/30
	**/
	public static int compareDate(String date1,String date2,String type){
		if(StringUtil.isStringNullorEmpty(type)) {//标准格式
			return compareTimes(date1, date2, dateFormatSd);
		}else{
			return compareTimes(date1, date2, dateFormat);
		}
	}
	/**
	 * @Method compareTime
	 * @Description:  方法1：时间比较，若date1在date2之前，则返回-1，同一时间返回0，之后返回1,其他无法比较
	 * @param date1
	 * @param date2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/24
	**/
	public static int compareTime(String date1,String date2){
		return compareTimes(date1,date2,timeFormat);
	}

	/**
	 * @Method compareTime
	 * @Description:  时间比较，若date1在date2之前，则返回-1，同一时间返回0，之后返回1,其他无法比较
	 * @param dt1
	 * @param dt2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2019/5/23
	**/
	public static int compareTime(Date dt1,Date dt2){
		try{
			if(dt1.getTime()<dt2.getTime()){
				return -1;
			}
			if(dt1.getTime()==dt2.getTime()){
				return 0;
			}
			if(dt1.getTime()>dt2.getTime()){
				return 1;
			}

		}catch (Exception e){

		}
		return 2;
	}
	/**
	 * @Method compareTime
	 * @Description:  方法2：时间比较，若date1在date2之前，则返回-1，同一时间返回0，之后返回1,其他无法比较
	 * @param date1
	 * @param date2
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/27
	**/
	public static int compareTime(Date date1,String date2){
		return compareTimes(timeFormat.format(date1),date2,timeFormat);
	}

	public static int compareTime(Date date1,String date2,String type){
		if(StringUtil.isStringNullorEmpty(type)){//标准格式
			return compareTimes(timeFormatSd.format(date1),date2,timeFormatSd);
		}else{
			return compareTimes(timeFormat.format(date1),date2,timeFormat);
		}
	}

	/**
	 * @Method compareTimes
	 * @Description:  日期或时间比较
	 * @param date1
	 * @param date2
	 * @param dt
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/24
	**/
	public static int compareTimes(String date1,String date2,SimpleDateFormat dt){
		try{
			Date dt1 = dt.parse(date1);
			Date dt2 = dt.parse(date2);
			if(dt1.getTime()<dt2.getTime()){
				return -1;
			}
			if(dt1.getTime()==dt2.getTime()){
				return 0;
			}
			if(dt1.getTime()>dt2.getTime()){
				return 1;
			}

		}catch (Exception e){

		}
		return 2;
	}

	/**
	 * @Method computeAllDateTime
	 * @Description:  日期计算
	 * @param date	日期
	 * @param param
	 * @param sdf
	 * @param num 5表示天加减，2表示月
	 * @return：java.lang.String
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/24
	 **/
	public static String computeAllDateTime(String date,int param,SimpleDateFormat sdf,int num){
		GregorianCalendar gc=new GregorianCalendar();
		try {
			gc.setTime(sdf.parse(date));
			gc.add(num, param);//5表示天加减
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(gc.getTime());
	}

	/**
	 * @Method reBigTime
	 * @Description:  返回较大的时间
	 * @param time1
	 * @param time2
	 * @return：java.util.Date
	 * @exception
	 * @Author: xfl
	 * @Date: 2019/5/29
	**/
	public static Date reBigTime(Date time1,Date time2){
		if(time1.getTime()<time2.getTime()){
			return time2;
		}else{
			return time1;
		}
	}
	/**
	 * @Method computeDateTimeHour
	 * @Description:  时间计算，按小时
	 * @param date
	 * @param hour
	 * @return：java.util.Date
	 * @exception
	 * @Author: xfl
	 * @Date: 2019/5/24
	**/
	public static Date computeDateTimeHour(Date date,double hour){
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTime(date);
		//向上取整
		int minute=(int)Math.ceil(hour*60);
		gc.add(Calendar.MINUTE, minute);
		return gc.getTime();
	}

	/**
	 * @Method computeDay
	 * @Description:  日期计算，增加多少天或者减少多少天后的日期
	 * @param date	日期，格式为yyyyMMdd
	 * @param param
	 * @return：java.lang.String
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/24
	**/
	public static String computeDay(String date,int param){
		return computeAllDateTime(date,param,dateFormat,5);
	}

	/**
	 * @Method computeDay
	 * @Description:  日期计算，增加多少天或者减少多少天后的日期
	 * @param date	日期，格式为yyyy-MM-dd
	 * @param param
	 * @param type
	 * @return：java.lang.String
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/11/30
	**/
	public static String computeDay(String date,int param,String type){
		if(StringUtil.isStringNullorEmpty(type)){//标准格式
			return computeAllDateTime(date, param, dateFormatSd, 5);
		}else {
			return computeAllDateTime(date, param, dateFormat, 5);
		}
	}
	/**
	 * @Method computeDay
	 * @Description:  日期计算，增加多少月或者减少多少月后的日期
	 * @param date	日期，格式为yyyyMMdd
	 * @param param
	 * @return：java.lang.String
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/24
	 **/
	public static String computeMon(String date,int param){
		return computeAllDateTime(date,param,monFormat,2);
	}

	/**
	 * @Method countDays
	 * @Description:  计算天数,起止日期相减
	 * @param stDate
	 * @param endDate
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/30
	**/
	public static int countDays(Date stDate,Date endDate){
		return  countDays(dateFormat.format(stDate),dateFormat.format(endDate));
	}

	/**
	 * @Method countDays
	 * @Description:  计算天数,起止日期相减,格式yyyy-MM-dd
	 * @param stDate
	 * @param endDate
	 * @param type
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/11/30
	**/
	public static int countDays(Date stDate,Date endDate,String type){
		if(StringUtil.isStringNullorEmpty(type)){//标准格式
			return countDays(dateFormatSd.format(stDate), dateFormatSd.format(endDate));
		}else {
			return countDays(dateFormat.format(stDate), dateFormat.format(endDate));
		}
	}

	/**
	 * @Method countMons
	 * @Description:  计算月数
	 * @param stMon
	 * @param endMon
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/9/3
	**/
    public static int countMons(String stMon,String endMon){
        stMon=stMon.substring(4);
        endMon=endMon.substring(4);
        return  Math.abs(Integer.parseInt(endMon)-Integer.parseInt(stMon));
    }

	/**
	 * @Method countDays
	 * @Description:  计算天数
	 * @param stDate
	 * @param endDate
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/30
	**/
	public static int countDays(String stDate,String endDate){
		return countDays(stDate,endDate,dateFormat);
	}

	public static int countDays(String stDate,String endDate,SimpleDateFormat df){
		Calendar cal = Calendar.getInstance();
		long between_days = 0;
		try {
			cal.setTime(df.parse(stDate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(df.parse(endDate));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		}catch (Exception e){}

		return Math.abs(Integer.parseInt(String.valueOf(between_days)));
	}

	/**
	 * @Method countDays
	 * @Description:  计算天数
	 * @param stDate
	 * @param endDate
	 * @param type
	 * @return：int
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/11/30
	**/
	public static int countDays(String stDate,String endDate,String type){
		if(StringUtil.isStringNullorEmpty(type)){//标准格式
			return countDays(stDate,endDate,dateFormatSd);
		}else{
			return countDays(stDate,endDate);
		}
	}

	/**
	 * @Method getWeekFirstLastDay
	 * @Description:  获取所在日期周的周一和周日,string,yyyy-MM-dd
	 * @param date yyyyMMdd
	 * @return：java.util.List<java.lang.String>
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/31
	**/
	public static List<String> getWeekFirstLastDay(String date){
	    //yyyyMMdd格式化为yyyy-MM-dd
        date=formateDateSD(date);
	    List<String> week=new ArrayList<>();
		LocalDate inputDate = LocalDate.parse(date);
		TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue()-DayOfWeek.MONDAY.getValue()));
        week.add(inputDate.with(FIRST_OF_WEEK).toString());
		TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        week.add(inputDate.with(LAST_OF_WEEK).toString());
        return week;
	}

	/**
	 * @Method getWeekFirstLastDayLong
	 * @Description:  获取所在日期周的周一和周日,Long,yyyyMMdd
	 * @param date yyyyMMdd
	 * @return：java.util.List<java.lang.Long>
	 * @exception
	 * @Author: xfl
	 * @Date: 2018/8/31
	**/
    public static List<Long> getWeekFirstLastDayLong(String date){
        List<String> week=getWeekFirstLastDay(date);
        List<Long> weekLong=new ArrayList<>();
        weekLong.add(Long.parseLong(week.get(0).replace("-","")));
        weekLong.add(Long.parseLong(week.get(1).replace("-","")));
        return weekLong;
    }

    /**
     * @Method getWeekDays
     * @Description:  返回一周内所有的日期
     * @param date 格式yyyymmdd或yyyy-mm-dd
     * @return：java.util.List<java.lang.String> 格式yyyy-mm-dd
     * @exception
     * @Author: xfl
     * @Date: 2018/9/12
    **/
	public static List<String> getWeekDays(String date) {
		List<String> week=getWeekFirstLastDay(date);
		String firstDay=week.get(0);
		week.clear();
		week.add(firstDay);
		for(int i=1;i<7;i++){
			week.add(formateDateSD(computeDay(formateDate(firstDay),i)));
		}
		return week;
	}

    /**
     * @Method dateToWeek
     * @Description:  日期转星期，返回汉字或者数字,默认返回数字
     * @param datetime
     * @param type 默认为数字，"CN"表示返回汉字
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2018/9/12
    **/
	public static String dateToWeek(String datetime,String type) {
		String[] weekDaysNo = { "7", "1", "2", "3", "4", "5", "6" };
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = dateFormat.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0) {
			w = 0;
		}
		if("CN".equals(type)){
			return weekDays[w];
		}else {
			return weekDaysNo[w];
		}
	}

	/**
	 * 月初时间戳
	 * @param time
	 * @return
	 */
	public static String monthFirstday(String time) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date(Long.valueOf(time)));
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY, 0);  
        cale.set(Calendar.MINUTE, 0);  
        cale.set(Calendar.SECOND, 0);
        return String.valueOf(cale.getTimeInMillis());
	}
	
	/**
	 * 月末时间戳
	 * @param time
	 * @return
	 */
	public static String monthLastday(String time) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date(Long.valueOf(time)));
		cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);  
        cale.set(Calendar.MINUTE, 59);  
        cale.set(Calendar.SECOND, 59);
        return String.valueOf(cale.getTimeInMillis());
	}
	
	/**
	 * 这天的开始
	 * @param time
	 * @return
	 */
	public static String daystart(String time) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date(Long.valueOf(time)));
		cale.set(Calendar.HOUR_OF_DAY, 0);  
        cale.set(Calendar.MINUTE, 0);  
        cale.set(Calendar.SECOND, 0);
        return String.valueOf(cale.getTimeInMillis());
	}
	
	/**
	 * 这天的结束
	 * @param time
	 * @return
	 */
	public static String dayend(String time) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date(Long.valueOf(time)));
		cale.set(Calendar.HOUR_OF_DAY, 23);  
        cale.set(Calendar.MINUTE, 59);  
        cale.set(Calendar.SECOND, 59);
        return String.valueOf(cale.getTimeInMillis());
	}

	public static void main(String[] args) {
		List<Long> curRoles=new ArrayList<>();
		curRoles.add(new Long(1));
		curRoles.add(new Long(1));
		curRoles.add(new Long(1));
		curRoles.add(new Long(1));
        System.out.println(getWeekDays("20180924"));
		System.out.println(computeAllDateTime("201808",-1,monFormat,2)) ;
		System.out.println(compareYear("2018","2019")) ;
		System.out.println(compareYear("2019","2019")) ;
		System.out.println(compareMon("201808","201808")) ;
		System.out.println(computeDay("20180701",-1)) ;
		System.out.println(computeDay("20180832",1)) ;
	}
	
}
