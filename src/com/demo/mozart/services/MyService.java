/***********************************************************
 * * Copyright (C), 2008-2017, OPPO Mobile Comm Corp., Ltd.
 * * VENDOR_EDIT
 * * File: - XXX.java
 * * Description: XXX
 * * Version: 1.0
 * * Date : 2017/11/9
 * * Author: MoRuihong@System.Frameworks.GameSpace
 * *
 * * ---------------------Revision History: ---------------------
 * *  <author>  <data>    <version >       <desc>
 * *  Mo Ruihong       2017/11/9     1.0     build this module
 ****************************************************************/
package com.demo.mozart.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import java.lang.ref.WeakReference;


public class MyService extends Service {
    private static final int MSG_KILL_PID = 100;
    private MyHandler mHandler;

    private final IMyService.Stub mBinder = new IMyService.Stub() {
        @Override
        public void fun1() throws RemoteException {
            Log.d("morh", "MyService fun1!!!");
        }
    };

    private static class MyHandler extends Handler {
        WeakReference<MyService> wrf;

        public MyHandler(MyService service) {
            wrf = new WeakReference<MyService>(service);
        }

        @Override
        public void handleMessage(Message message) {
            MyService service = null;
            if (wrf != null) {
                service = wrf.get();
            }

            if (service == null) {
                Log.d("morh", "handleMessage service is null!!!");
                return;
            }

            if (message.what == MSG_KILL_PID) {
                Log.d("morh", " now kill self!!!");
                Process.killProcess(Process.myPid());
            }

            super.handleMessage(message);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("morh", "MyService onCreate!!!");
        mHandler = new MyHandler(this);
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i1) {
        Log.d("morh", "MyService onStartCommand!!!");
        return super.onStartCommand(intent, i, i1);
    }

    @Override
    public void onDestroy() {
        Log.d("morh", "MyService onDestroy!!!");
        Log.d("morh", " send message to kill self!!!");
        mHandler.sendEmptyMessageDelayed(MSG_KILL_PID, 5000);
        //Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
