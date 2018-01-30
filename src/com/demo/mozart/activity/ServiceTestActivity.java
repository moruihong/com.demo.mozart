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
package com.demo.mozart.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demo.mozart.R;
import com.demo.mozart.services.IMyService;
import com.demo.mozart.services.MyService;

import java.util.List;


public class ServiceTestActivity extends Activity implements View.OnClickListener {
    private IMyService mService;
    private TextView mServiceText;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_myactivity);

        mServiceText = (TextView) findViewById(R.id.myactivity_t3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myactivity_b1:
                bindMyService();
                break;
            case R.id.myactivity_b2:
                unBindMyService();
                break;
            case R.id.myactivity_b3:
                lookMyService();
                break;
            default:
                break;
        }
    }

    private ServiceConnection mSC = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("morh", "onServiceConnected!!! componentName = " + componentName);
            mService = IMyService.Stub.asInterface(iBinder);
            Log.d("morh", "   mService = " + mService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("morh", "onServiceDisconnected!!!!!!!");
            mService = null;
        }
    };

    private void bindMyService() {
        if (mService == null) {
            Log.d("morh", "bindMyService!!!");
            Intent intent = new Intent();
            /*intent.setAction("com.demo.mozart.services.MyService");
            intent.setPackage("com.demo.mozart");*/
            intent.setClass(this, MyService.class);
            bindService(intent, mSC, Service.BIND_AUTO_CREATE);
        } else {
            Log.d("morh", "bindMyService has done, do nothing!!!");
        }
    }

    private void unBindMyService() {
        if (mService != null) {
            Log.d("morh", "unBindMyService!!!");
            unbindService(mSC);
            mService = null;
        } else {
            Log.d("morh", "bindMyService is null, do nothing!!!");
        }
    }

    private void lookMyService() {
        boolean isExist = false;

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = am.getRunningAppProcesses();
        String myProcessName = "com.demo.mozart:service";
        for (ActivityManager.RunningAppProcessInfo info : runningAppProcessesList) {
            if (info.processName.contains("mozart")) {
                Log.d("morh", "lookMyService!!! processName = " + info.processName);
                if (info.processName.equals(myProcessName)) {
                    isExist = true;
                    break;
                }
            }
        }

        if (isExist) {
            mServiceText.setText("service process exist!!!");
        } else {
            mServiceText.setText("service process not exist!!!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Process.killProcess(Process.myPid());
    }
}
