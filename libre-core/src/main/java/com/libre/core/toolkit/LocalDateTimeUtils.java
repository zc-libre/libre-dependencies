package com.libre.core.toolkit;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * @author zhao.cheng
 * @date 2021/2/4 15:30
 */
public class LocalDateTimeUtils extends LocalDateTimeUtil {

    public static LocalDateTime beginOfMouth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    public static LocalDateTime endOfMouth() {
        return LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    public static LocalDateTime beginOfMouth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    public static LocalDateTime endOfMouth(LocalDateTime localDateTime) {
        return LocalDateTime.of(LocalDate.from(localDateTime.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    public static LocalDateTime beginOfToDay() {
        return beginOfDay(LocalDateTime.now());
    }

    public static LocalDateTime endOfToDay() {
        return endOfDay(LocalDateTime.now());
    }
}
