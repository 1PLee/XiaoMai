package main.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyipeng on 2018/2/28.
 */
public class Compile {


    public static List<Integer> getAllInteger(String str) { //将字符串中的数字全部提取出来
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);

        List<Integer> allInt = new ArrayList<Integer>();

        while (matcher.find()) {
            allInt.add(Integer.parseInt(matcher.group(0)));
        }

        return allInt;
    }
}
