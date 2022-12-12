package usi.weixinpush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonnUtil {

    public static void main(String[] args) throws ParseException {
        System.out.println(calculateFutureDays("2022-09-17"));
        System.out.println(calculatePastDays("2022-08-16"));
    }

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
     * 描述: 计算还有多少天
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
     * 描述: 计算已经过去了多少天
     * @Author wangxianlin
     * @Date 22:21 2022/8/20
     * @param: day
     * @Return java.lang.String
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
