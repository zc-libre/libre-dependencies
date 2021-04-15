package com.libre.core.toolkit;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * @author zhao.cheng
 * @date 2021/2/4 15:30
 */
public class LocalDateTimeUtils extends LocalDateTimeUtil {

	/**
	 * 获取当前月份的第一天
	 * @return /
	 */
    public static LocalDateTime beginOfMouth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

	/**
	 * 获取当前月份的最后一天
	 * @return /
	 */
    public static LocalDateTime endOfMouth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }


	/**
	 * 获取指定月份的第一天
	 * @return /
	 */
    public static LocalDateTime beginOfMouth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

	/**
	 * 获取指定月份的最后一天
	 * @return /
	 */
    public static LocalDateTime endOfMouth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

	/**
	 * 获取一天的开始时间
	 * @return /
	 */
	public static LocalDateTime beginOfToDay() {
        return beginOfDay(LocalDateTime.now());
    }

	/**
	 * 获取一天的结束时间
	 * @return /
	 */
    public static LocalDateTime endOfToDay() {
        return endOfDay(LocalDateTime.now());
    }

	/**
	 * 判断是否是周末
	 * @param localDateTime /
	 * @return /
	 */
	public static boolean isWeekend(LocalDateTime localDateTime) {
		if (Objects.isNull(localDateTime)) {
			return false;
		}
		DayOfWeek week = localDateTime.getDayOfWeek();
		return week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY;
	}

	/**
	 * 获取指定时间的周一
	 * @param time /
	 * @return /
	 */
	public static LocalDateTime beginOfWeek(LocalDateTime time) {
		return LocalDateTime.of(time.toLocalDate(), LocalTime.MIN).with(DayOfWeek.MONDAY);
	}

	/**
	 * 获取指定时间的周末
	 * @param time /
	 * @return /
	 */
	public static LocalDateTime endOfWeek(LocalDateTime time) {
		return LocalDateTime.of(time.toLocalDate(), LocalTime.MAX).with(DayOfWeek.SUNDAY);
	}
}
