package com.zhangpk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created By zhangpk On 2019/6/19
 **/
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
