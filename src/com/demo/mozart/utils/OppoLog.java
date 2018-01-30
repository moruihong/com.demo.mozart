/***********************************************************
 * * Copyright (C), 2008-2018, OPPO Mobile Comm Corp., Ltd.
 * * VENDOR_EDIT
 * * File: - XXX.java
 * * Description: XXX
 * * Version: 1.0
 * * Date : 2018/1/29
 * * Author: MoRuihong@System.Frameworks.GameSpace
 * *
 * * ---------------------Revision History: ---------------------
 * *  <author>        <data>      <version>     <desc>
 * *  MoRuihong     2018/1/29       1.0        build this module
 ****************************************************************/
package com.demo.mozart.utils;

import android.util.Log;


public class OppoLog {
    private static final String TAG = "Mozart";
    private static final String DOT = ".";

    public static void v(String tag, String msg) {
        Log.v(TAG + DOT + tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(TAG + DOT + tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(TAG + DOT + tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(TAG + DOT + tag, msg);
    }

    public static void w(String tag, String msg, Throwable ex) {
        Log.w(TAG + DOT + tag, msg, ex);
    }

    public static void e(String tag, String msg) {
        Log.e(TAG + DOT + tag, msg);
    }

    public static void e(String tag, String msg, Throwable ex) {
        Log.e(TAG + DOT + tag, msg, ex);
    }

}
