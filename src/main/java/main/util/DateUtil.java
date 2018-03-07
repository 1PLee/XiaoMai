package main.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liyipeng on 2018/2/28.
 */
public class DateUtil {

    public static Timestamp String2Timestamp(String dateString){ // 将yyyy.MM.dd格式转换为timestamp
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        try {
            System.out.println("the dateString: " + dateString);
            date = dateFormat.parse(dateString);
            System.out.println("the date:" + date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());

        return timestamp;
    }


    /*将一个 yyyy.MM.dd格式的字符串时间的两个礼拜前的时间戳返回*/
    public static Timestamp beforeTwoWeek(String dateString){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day = 14;
        int hour = 24;
        int minute = 60;
        int second = 60;
        int ms = 1000;


        Date twoWeekBefore = new Date(date.getTime() - (day * hour * minute * second * ms));

        Timestamp twoWeekBeforeTimestamp = new Timestamp(twoWeekBefore.getTime());

        return  twoWeekBeforeTimestamp;
    }



}
