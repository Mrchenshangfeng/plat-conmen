package com.hywisdom.platform.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * Title: 周计算类，星期一为一周的开始，星期日为一周的结束
 * </p>
 */
public class WeekUtil {
	/**
	 * 取得给定日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某一年周的总数
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得给定日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得给定日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int year = 2017;
		int week = 1;

		// 以2017-11-14为例
		Calendar c = new GregorianCalendar();
		c.set(2018, 01, 14);
		Date d = c.getTime();

		System.out.println("current date = " + d.toLocaleString());
		System.out.println("取得给定日期是多少周 = " + getWeekOfYear(d));
		System.out.println("得到某一年周的总数 = " + getMaxWeekNumOfYear(year));
		System.out.println("得到某年某周的第一天 = " + getFirstDayOfWeek(year, week).toLocaleString());
		System.out.println("得到某年某周的最后一天 = " + getLastDayOfWeek(year, week).toLocaleString());
		System.out.println("取得给定日期所在周的第一天 = " + getFirstDayOfWeek(d).toLocaleString());
		System.out.println("取得给定日期所在周的最后一天 = " + getLastDayOfWeek(d).toLocaleString());
	}
}
