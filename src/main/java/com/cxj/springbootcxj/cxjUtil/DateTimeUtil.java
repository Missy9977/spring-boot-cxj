/**
 * @author ChuXianJie
 */
package com.cxj.springbootcxj.cxjUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ChuXianJie
 * @date 2019年1月1日
 */
public class DateTimeUtil {

	private static final String DATESTYLE = "yyyyMMdd";

	/**
	 * 将日期转为Timestamp类型
	 * 
	 * @author ChuXianJie
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		Timestamp ts = new Timestamp(date.getTime());
		;
		return ts;
	}

	/**
	 * 将日期字符串转为Timestamp类型
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp stringToTimestamp(String date) throws ParseException {
		Date uitlDate = stringToDate(date);
		return dateToTimestamp(uitlDate);
	}

	/**
	 * 将时间Timestamp类型转换为yyyyMMdd格式的String类型
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timestampToString(Timestamp timestamp) {
		return dateToString(timestamp);
	}

	/**
	 * util日期转换sql日期
	 * 
	 * @param Date utilDate
	 * @return sqlDate
	 */
	public static java.sql.Date utilDateTosqlDate(Date utilDate) {
		if (utilDate == null) {
			return null;
		} else {
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			return sqlDate;
		}
	}

	/**
	 * 日期字符串转换sql日期
	 * 
	 * @param Date utilDate
	 * @return sqlDate
	 * @throws ParseException
	 */
	public static java.sql.Date stringToSqlDate(String dateStr) throws ParseException {
		Date date = stringToDate(dateStr);
		if (date != null) {
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		}
		return null;
	}

	/**
	 * 将日期String类型转换为Date类型
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date) throws ParseException {
		if (date == null || "".equals(date)) {
			return null;
		} else if (date.length() == 8) {
			DateFormat sdf = new SimpleDateFormat(DATESTYLE);
			Date utilDate = sdf.parse(date);
			return utilDate;
		} else {
			return null;
		}
	}

	/**
	 * 将Date日期类型转换为指定的String字符串类型
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat sdf = new SimpleDateFormat(DATESTYLE);
			String dateStr = sdf.format(date);
			return dateStr;
		}
	}

	/**
	 * 获取上月最后一天
	 * 
	 * @return
	 */
	public static String getLastMonthDay() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date time = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(time);
	}

	/**
	 * 根据日期date获取上月最后一天
	 * 
	 * @return
	 */
	public static Date getLastMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date time = calendar.getTime();
		return time;
	}

	/**
	 * 根据输入的日期date获取本月最后一天yyyy-mm-dd
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getLastMonthDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date time = DateTimeUtil
				.utilDateTosqlDate(DateTimeUtil.stringToDate(format.format(calendar.getTime())));
		return time;
	}

	/**
	 * 根据输入的日期date获取本月最后一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMonthDayString(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		java.sql.Date time = DateTimeUtil
				.utilDateTosqlDate(DateTimeUtil.stringToDate(format.format(calendar.getTime())));
		String lastDate = time.toString() + " 23:59:59";
		return lastDate;
	}

	/**
	 * 获取某个月份的最后一天
	 * 
	 * @return
	 */
	public static String getNowMaxMonthDate(String date) {
		try {
			SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return formatMonth.format(calendar.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取上个月的最后一天
	 * 
	 * @return
	 */
	public static String getLastMaxMonthDate() {
		try {
			SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return formatMonth.format(calendar.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取下月的第一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMonthFirstDay(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return sdf.format(calendar.getTime());
	}

	/**
	 * 根据输入的日期date获取本月第一天yyyy-mm-dd
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getStartMonthDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		java.sql.Date time = DateTimeUtil
				.utilDateTosqlDate(DateTimeUtil.stringToDate(format.format(calendar.getTime())));
		return time;
	}

	/**
	 * 根据输入的日期date获取本月最后一天yyyymmdd
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getLastMonthDayTo(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat defDate = new SimpleDateFormat("yyyyMMdd");
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String time = defDate.format(calendar.getTime());
		return time;
	}

	/**
	 * 根据输入的日期date获取本月第一天yyyymmdd
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static String getStartMonthDayTo(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat defDate = new SimpleDateFormat("yyyyMMdd");
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		String time = defDate.format(calendar.getTime());
		return time;
	}

	/**
	 * 获取上月YYYMM
	 * 
	 * @return
	 */
	public static String getTopMonth() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat defMoth = new SimpleDateFormat("yyyyMM");
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		String time = defMoth.format(calendar.getTime());
		return time;
	}

	/**
	 * 根据输入的日期date获取输入月份至当前日期上一个月每月最后一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static List<java.sql.Date> getMonthDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<String>();
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		int month1 = calendar.get(Calendar.MONTH);
		Date sqlDate = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(date));
		calendar.setTime(sqlDate);
		int month2 = calendar.get(Calendar.MONTH);
		int num = Math.abs(month2 - month1);
		if (num == 0) {
			num = 12;
		}
		for (int i = 0; i > -num; i--) {
			calendar.setTime(new Date());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, i);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			list.add(format.format(calendar.getTime()));
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			java.sql.Date dates = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(list.get(i)));
			result.add(dates);
		}
		return result;
	}

	/**
	 * 根据输入的日期date获取输入月份至输入结束日期上一个月每月最后一天
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static List<java.sql.Date> getMonthDay(String startDate, String endDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<String>();
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		Date sqlDate1 = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(startDate));
		calendar.setTime(sqlDate1);
		int month1 = calendar.get(Calendar.MONTH);
		int year1 = Integer.parseInt(startDate.substring(0, 4));
		Date sqlDate2 = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(endDate));
		calendar.setTime(sqlDate2);
		int month2 = calendar.get(Calendar.MONTH);
		int year2 = Integer.parseInt(endDate.substring(0, 4));
		int num = Math.abs(month2 - month1);
		if (year2 - year1 > 0) {
			for (int i = 0; i < year2 - year1; i++) {
				num = num + 12;
			}
		}
		for (int i = 1; i > -num; i--) {
			calendar.setTime(sqlDate2);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, i);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			list.add(format.format(calendar.getTime()));
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			java.sql.Date dates = DateTimeUtil.utilDateTosqlDate(DateTimeUtil.stringToDate(list.get(i)));
			result.add(dates);
		}
		return result;
	}

	/**
	 * 获取去年最后一天
	 * 
	 * @return
	 */
	public static String getLastYearDay() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.MONTH, 1 - 1);
		calendar.add(Calendar.MONTH, -1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), maxDay, 23, 59, 59);
		return format.format(calendar.getTime());
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 计算某天与今天的天数差
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String dateStr) throws ParseException {
		Date todate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date compareDate = sdf.parse(dateStr);

		Calendar cal = Calendar.getInstance();
		cal.setTime(compareDate);
		long time1 = cal.getTimeInMillis();

		cal.setTime(todate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time1 - time2) / (1000 * 3600 * 24);
		// 不足一天按一天处理
		if ((time1 - time2) > 0 && (time1 - time2) < 86400000) {
			between_days = 1;
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 将8位日期字符串转换成10位日期字符串
	 * 
	 * @param string
	 * @return
	 */
	public static String StringToDate(String string) {
		StringBuffer buffer = new StringBuffer(string);
		buffer.insert(4, "-");
		buffer.insert(7, "-");
		return buffer.toString();
	}

	/**
	 * 获取当前日期的前一天
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String beforeDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(date);
		return day;
	}

	/**
	 * 获取当前日期前后的某一天
	 * 
	 * @param distance
	 * @return
	 * @throws ParseException
	 */
	public static String distanceDay(int distance) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, distance);
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(date);
		return day;
	}

	/**
	 * 获取某日期前后的某一天
	 * 
	 * @param distance
	 * @return
	 * @throws ParseException
	 */
	public static String distanceDay(Date date, int distance) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, distance);
		Date dateTime = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(dateTime);
		return day;
	}

	/**
	 * 时间格式转换yyyy-MM-dd
	 * 
	 */
	public static String formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(date);
		return day;
	}

	/**
	 * 时间格式自定义转换
	 * 
	 * @param distance
	 * @return
	 */
	public static String formatDate(String date, String dateStyle) {
		SimpleDateFormat format = new SimpleDateFormat(dateStyle);
		String day = format.format(date);
		return day;
	}

}
