package com.linkhand.fenxiao.utils;

import android.util.Log;

public class Logger {
    public static final boolean FlAG = true;

    public static void i(String tag, String message) {
        if (FlAG) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (FlAG) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (FlAG) {
            Log.e(tag, message);
        }
    }
}
