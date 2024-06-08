package com.mzl.insta360demo.util;

import java.time.LocalTime;

/**
 * @Description: 时间日期工具类
 * @Author: mzl
 */
public class DateUtil {

    /**
     * 判断当前时间是否在指定的时间范围内
     *
     * @param startHour 开始的时
     * @param startMinute 开始的分
     * @param endHour 结束的时
     * @param endMinute 结束的分
     * @return 当前时间是否在指定的时间范围内, true：是， false：否
     */
    public static boolean isAtTimeScope(int startHour, int startMinute, int endHour, int endMinute){
        LocalTime currentTime = LocalTime.now();
        // 范围开始时间
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        // 范围结束时间
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
    }

}