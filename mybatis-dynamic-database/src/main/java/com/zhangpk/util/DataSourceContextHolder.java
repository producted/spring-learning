package com.zhangpk.util;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/7/23 10:33
 */
public class DataSourceContextHolder {
    public static final String oneDs = "master";
    public static final String twoDs = "nacos";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String name){
        contextHolder.set(name);
    }

    public static String getDataSource(){
        return contextHolder.get();
    }

    public static void cleanDataSource(){
        contextHolder.remove();
    }

}
