package app.util.normal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MyTimeUtil {
    private static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static boolean compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    //获取今天日期yyyy-MM-dd
    public static String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    //获取最近七天日期yyyy-MM-dd
    public static ArrayList<String> getWeekDates() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 7; i++) {
            Date date = new Date(System.currentTimeMillis()-i*(1000*60*60*24));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            list.add(sdf.format(date));
        }
        return list;
    }
    // 获取当前时间 yyyy-MM-dd HH:mm:ss
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String finishTime = formatter.format(curDate);
        return finishTime;
    }
    // 获取当前时间 yyyy-MM-dd HH:mm:ss
    public static String getCurrentTimeWithDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String finishTime = formatter.format(curDate);
        return finishTime;
    }
    // 获取当前时间 yyyy/MM/dd hh:mm AM/PM
    public static String getCurrentTimeWithAmPm(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String finishTime = formatter.format(curDate);
        String am_pm = Calendar.getInstance().getTime().getHours() >= 12 ? "PM" : "AM";
        return finishTime+" "+am_pm;
    }
    //获取指定日期是星期几
    public static String getWeekDate(Date dt){
        Calendar cal = Calendar.getInstance();
        if (dt == null){
            dt = new Date(System.currentTimeMillis());
        }
//        cal.setTimeInMillis(System.currentTimeMillis());
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    //获取指定日期是星期几
    public static int getWeekDateNumber(Date dt){
        Calendar cal = Calendar.getInstance();
        if (dt == null){
            dt = new Date(System.currentTimeMillis());
        }
//        cal.setTimeInMillis(System.currentTimeMillis());
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    //转换时间格式
    public static String timeFormat(int time){
        int second = time % 60;
        int buff = time / 60;
        int min = buff % 60;
        int hour = buff / 60;
        return ""+hour+"时"+min+"分"+second+"秒";
    }
    public static String getTimeWithDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getTimeWithMin(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    public static String getTimeWithDay(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeOnlyMin(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String getTimeOnlyYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getMin(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = null;
        try {
            time = format.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeOnlyMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }
}
