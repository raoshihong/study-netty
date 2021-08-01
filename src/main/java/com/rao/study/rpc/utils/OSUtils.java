package com.rao.study.rpc.utils;

/**
 * @author raoshihong
 * @date 2021-07-31 12:05
 */
public class OSUtils {

    public static String getOSName(){
        return System.getProperty("os.name");
    }

    public static boolean isWindows(){
        return getOSName().startsWith("windows");
    }

    public static boolean isLinux(){
        return getOSName().toLowerCase().contains("linux");
    }

}
