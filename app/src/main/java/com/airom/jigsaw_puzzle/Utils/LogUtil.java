package com.airom.jigsaw_puzzle.Utils;

import android.util.Log;

public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    /**控制想要打印的日志级别
     * 等于VERBOSE，则就会打印所有级别的日志
     * 等于WARN，则只会打印警告级别以上的日志
     * 等于NOTHING，则会屏蔽掉所有日志*/
    public static final int LEVEL = VERBOSE;


    public static void v(String tag, String msg){
        if(LEVEL <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(LEVEL <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(LEVEL <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(LEVEL <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(LEVEL <= ERROR){
            Log.e(tag, msg);
        }
    }

}
