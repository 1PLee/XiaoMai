package main.util;

import java.util.HashMap;

/**
 * Created by liyipeng on 2018/2/22.
 */
public class MailCodeMap {
    private static final MailCodeMap instance = new MailCodeMap();
    private static final HashMap<String,Integer> userCode = new HashMap<String, Integer>();

    private MailCodeMap(){}

    public static MailCodeMap getInstance(){
        return instance;
    }

    public void put(String userID,int mailCode){ //mailCode 代表邮箱激活码

            userCode.put(userID,mailCode);
    }

    public int getCode(String userID){
        return userCode.get(userID);
    }

    public void remove(String userID){

        userCode.remove(userID);
    }



}
