package main.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by liyipeng on 2018/2/22.
 */
public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper(){
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
