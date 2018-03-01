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
}
