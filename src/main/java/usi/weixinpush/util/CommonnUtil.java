package usi.weixinpush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommonnUtil {

    /**
     * 描述: 获取当前日期
     * @Author wangxianlin
     * @Date 23:20 2022/8/20
     * @param:
     * @Return java.lang.String
     */
    public static String getNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        return today;

    }

    /**
     * 描述: 获取今天是星期几
     */
    public static String time() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        String  week_day= weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        return week_day;
    }


    /**
     * 描述: 指定日期距今还有多少天
     * @Author wangxianlin
     * @Date 22:21 2022/8/20
     * @param: day
     * @Return java.lang.String
     */
    public static String calculateFutureDays(String day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDay = sdf.parse(day);
        long targetTime = targetDay.getTime();
        long todaytime = System.currentTimeMillis();
        long time =Math.abs(targetTime-todaytime);
        return String.valueOf(time/1000/60/60/24);
    }

    /**
     * 描述: 生日还有多少天
     * @Author wangxianlin
     * @Date 22:21 2022/8/20
     * @param: day
     * @Return java.lang.String
     */
    public static String calculateBirth(String day) throws ParseException {
        if(!day.endsWith("00:00:00")){
            day += " 00:00:00";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeDate = LocalDateTime.parse(day, formater);
        while(now.compareTo(timeDate)>0){
            timeDate = timeDate.plusYears(1);
        }
        long interval = Duration.between(now,timeDate).toMillis();
        return String.valueOf(interval/1000/60/60/24);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(calculateBirth(("2021-12-04 00:00:00")));
    }

    /**
     * 描述: 计算已经过去了多少天
     */
    public static String calculatePastDays(String day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDay = sdf.parse(day);
        long targetTime = targetDay.getTime();
        long todaytime = System.currentTimeMillis();
        long time =Math.abs(todaytime - targetTime);
        return String.valueOf(time/1000/60/60/24);
    }

}
