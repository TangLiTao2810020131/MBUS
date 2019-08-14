package com.ets.utils;

import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 11:39
 */
public class DateTimeUtils {

    private static final Logger logger = Logger.getLogger(DateTimeUtils.class);


    public final static String danwei="";
    final static String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };

    public int  getDateNo()
    {
        Date date = new Date();
        int nian = date.getYear()+1900;
        int yue  = date.getMonth();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,nian);
        cal.set(Calendar.MONTH, yue);//Java月份才0开始算
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
        return dateOfMonth;
    }

    public static String getZhou() //获取星期几
    {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek <0)dayOfWeek=0;
        return dayNames[dayOfWeek];
    }

    public static Timestamp getTimestamp()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    public static int getYear()
    {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    public static String getnewdate()
    {
        Calendar   ctime   =   Calendar.getInstance();
        SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd hh:mm");
        Date date   =   ctime.getTime();
        String   sDate   =   fymd.format(date);
        return sDate;
    }

    public static String getnowdate()
    {
        Calendar   ctime   =   Calendar.getInstance();
        SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd HH:mm:ss");
        Date   date   =   ctime.getTime();
        String   sDate   =   fymd.format(date);
        return sDate;
    }


    public static String getnewsdate()
    {
        Calendar   ctime   =   Calendar.getInstance();
        SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd");
        Date   date   =   ctime.getTime();
        String   sDate   =   fymd.format(date);
        return sDate;
    }

    public static String datecode()
    {
        Calendar   ctime   =   Calendar.getInstance();
        SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyyMMdd");
        Date   date   =   ctime.getTime();
        String   sDate   =   fymd.format(date);
        return sDate;
    }

    public static String getMonth()
    {
        Calendar   ctime   =   Calendar.getInstance();
        SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM");
        Date   date   =   ctime.getTime();
        String   sDate   =   fymd.format(date);
        return sDate;
    }

    public static String lastMonth(String payoffYearMonth){
        SimpleDateFormat  sd = new    SimpleDateFormat("yyyy-MM");
        try {
            Date  currdate = sd.parse(payoffYearMonth);
            Calendar   calendar= Calendar.getInstance();
            calendar.setTime(currdate);
            calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
            System.out.println(sd.format(calendar.getTime()));
            String gtimelast = sd.format(calendar.getTime());
            return gtimelast;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static List<String> getDateList(){
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 15; i++) {
            list.add(i+"");
        }
        return list;
    }


    public static String getTime(String value){

        try {
            String datetime =  value.replace("T", "").replace("Z", "");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime ldt = LocalDateTime.parse(datetime,dtf);

            DateTimeFormatter fa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String datetime2 = ldt.format(fa);

            Calendar ca = Calendar.getInstance();
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=format.parse(datetime2);
            ca.setTime(date);
            ca.add(Calendar.HOUR_OF_DAY, 8);

            return format.format(ca.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTime7(String time){
        String day = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            Date d = c.getTime();
            day = format.format(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;

    }

    public static String getTZTime(String time){
        String timeTZ = "";

        try {
            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = sdfs.parse(time);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

            timeTZ = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return timeTZ;
    }

    public static String getDate(Date strDate) {
        String date = null;
        if (strDate!= null) {
            Calendar startTime = Calendar.getInstance();
            int year = startTime.get(Calendar.YEAR) - 20;
            // 这里初始化时间，然后设置年份。只以年份为基准，不看时间
            startTime.clear();
            startTime.set(Calendar.YEAR, year);
            SimpleDateFormat formatter = new SimpleDateFormat("yy");
            formatter.setLenient(false);
            formatter.set2DigitYearStart(startTime.getTime());
            try {
                date = formatter.format(strDate);
            }
            catch (Exception e) {
            }
        }
        return date;
    }

    /**
     * @author 宋晨
     * @param dateString
     * @return
     */
    public static Date getDateByString(String dateString){

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(dateString);
        } catch (ParseException e) {
            logger.error("调用日期工具类转换日期报错！", e);
        }

        return date;
    }

    /**
     * @author 宋晨
     * @param dateString
     * @return
     */
    public static String getMonthByString(String dateString){

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = format1.parse(dateString);
            dateString = format1.format(date);
        } catch (ParseException e) {
            logger.error("调用日期工具类转换日期报错！", e);
        }

        return dateString;
    }

    /**
     * @author 宋晨
     * @param dateString
     * @return
     */
    public static String getMonthDayByString(String dateString){

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format1.parse(dateString);
            dateString = format1.format(date);
        } catch (ParseException e) {
            logger.error("调用日期工具类转换日期报错！", e);
        }

        return dateString;
    }


}
