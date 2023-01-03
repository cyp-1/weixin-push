package usi.weixinpush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static usi.weixinpush.util.CalendarUtil.calculateLeave;
import static usi.weixinpush.util.CalendarUtil.solarToLunar;


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
     * 描述: 生日还有多少天  阳历
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

    /**
     * 描述: 生日还有多少天  阴历
     * @Author wangxianlin
     * @Date 22:21 2022/8/20
     * @param: day
     * @Return java.lang.String
     */
    public static String calculateLunarBirth(String day) throws Exception {
        day = solarToLunar(day.replaceAll("-", ""));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String day1 = day.replaceFirst("\\d{4}",year+"");
        int leave1 = calculateLeave(CalendarUtil.lunarToSolar(day1,false));

        String day2 = day.replaceFirst("\\d{4}",year-1+"");
        int leave2 = calculateLeave(CalendarUtil.lunarToSolar(day2,false));
        if(leave1<0){
            leave1 = 999;
        }
        if(leave2<0){
            leave2 = 999;
        }
        return Math.min(leave1,leave2)+"";
    }
    public static void main(String[] args) throws Exception {
        System.out.println(calculateLunarBirth("2020-01-03"));
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
