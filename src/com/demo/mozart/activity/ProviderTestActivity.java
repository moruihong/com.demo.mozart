/***********************************************************
 * * Copyright (C), 2008-2018, OPPO Mobile Comm Corp., Ltd.
 * * VENDOR_EDIT
 * * File: - XXX.java
 * * Description: XXX
 * * Version: 1.0
 * * Date : 2018/1/27
 * * Author: MoRuihong@System.Frameworks.GameSpace
 * *
 * * ---------------------Revision History: ---------------------
 * *  <author>        <data>      <version>     <desc>
 * *  MoRuihong     2018/1/27       1.0        build this module
 ****************************************************************/
package com.demo.mozart.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.IContentProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.mozart.R;

import java.util.ArrayList;
import java.util.Set;

public class ProviderTestActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final Uri TEST_URI = Uri.parse("content://com.coloros.safecenter.security.InterfaceProvider");
    public static final String METHOD_GET_SYS_FLOAT_WINDOW_STATE = "getSysFloatWindowState";
    public static final String METHOD_SET_SYS_FLOAT_WINDOW_STATE = "setSysFloatWindowState";
    public static final String METHOD_GET_STARTUP_STATE = "getStartupState";
    public static final String METHOD_SET_STARTUP_STATE = "setStartupState";
    public static final String METHOD_GET_ASSOCIATE_START_STATE = "getAssociateStartState";
    public static final String METHOD_SET_ASSOCIATE_START_STATE = "setAssociateStartState";
    private String[] testArray = {"com.tencent.research.drop", "com.baidu.BaiduMap", "com.coloros.safecenter",
            "com.UCMobile", "com.youku.phone", "com.tencent.qqlive",
            "com.taobao.taobao", "com.tencent.reading", "com.sina.weibo"
    };

    private Toast mToast;
    private ContentResolver mCR;
    private boolean mSwitchChecked = true;
    //private Switch mySwitchRead;
    private Switch mySwitchWrite;
    private TextView mResultText;
    private TextView mResultTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_test);
        mToast = Toast.makeText(this, " ", Toast.LENGTH_LONG);
        mCR = getContentResolver();
        /*mySwitchRead = (Switch) findViewById(R.id.my_switch_read);
        mySwitchRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });*/
        mySwitchWrite = (Switch) findViewById(R.id.my_switch_write);
        mySwitchWrite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSwitchChecked = isChecked;
            }
        });
        mResultText = (TextView) findViewById(R.id.result_text);
        mResultTitle = (TextView) findViewById(R.id.result_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                mResultTitle.setText("读取的悬浮窗的状态如下：");
                testGetSysFloatWindowState(TEST_URI, testArray);
                break;
            case R.id.b2:
                mResultTitle.setText("写入的悬浮窗的状态如下：");
                testSetSysFloatWindowState(TEST_URI, testArray);
                break;
            case R.id.b3:
                mResultTitle.setText("读取的自启动的状态如下：");
                testGetStartupState(TEST_URI, testArray);
                break;
            case R.id.b4:
                mResultTitle.setText("写入的自启动的状态如下：");
                testSetStartupState(TEST_URI, testArray);
                break;
            case R.id.b5:
                mResultTitle.setText("读取的关联启动的状态如下：");
                testGetAssociateStartState(TEST_URI, testArray);
                break;
            case R.id.b6:
                mResultTitle.setText("写入的关联启动的状态如下：");
                testSetAssociateStartState(TEST_URI, testArray);
                break;
        }
    }

    private void testGetAssociateStartState(Uri uri, String[] pkgArray) {
        testGetState(uri, pkgArray, METHOD_GET_ASSOCIATE_START_STATE);
    }

    private void testSetAssociateStartState(Uri uri, String[] pkgArray) {
        testSetState(uri, pkgArray, METHOD_SET_ASSOCIATE_START_STATE);
    }

    private void testGetStartupState(Uri uri, String[] pkgArray) {
        testGetState(uri, pkgArray, METHOD_GET_STARTUP_STATE);
    }

    private void testSetStartupState(Uri uri, String[] pkgArray) {
        testSetState(uri, pkgArray, METHOD_SET_STARTUP_STATE);
    }

    private void testGetSysFloatWindowState(Uri uri, String[] pkgArray) {
        testGetState(uri, pkgArray, METHOD_GET_SYS_FLOAT_WINDOW_STATE);
    }

    private void testSetSysFloatWindowState(Uri uri, String[] pkgArray) {
        testSetState(uri, pkgArray, METHOD_SET_SYS_FLOAT_WINDOW_STATE);
    }

    private void testGetState(Uri uri, String[] pkgArray, String method) {
        Bundle data = new Bundle();
        IContentProvider contentProvider = null;
        StringBuilder builder = new StringBuilder(uri.getAuthority());
        try {
            contentProvider = mCR.acquireUnstableProvider(uri);
            builder.append("\n").append((contentProvider == null ? "null" : contentProvider.toString()));
            if (contentProvider != null) {
                prepareDataForGet(data, pkgArray);

                Bundle result = contentProvider.call(getPackageName(), method, "", data);

                printResultForGet(result, pkgArray, builder);
            }
        } catch (Exception e) {
            Log.e(TAG, "###providersTest e=" + e);
        } finally {
            if (contentProvider != null) {
                mCR.releaseUnstableProvider(contentProvider);
            }
        }
    }

    private void testSetState(Uri uri, String[] pkgArray, String method) {
        Bundle data = new Bundle();
        IContentProvider contentProvider = null;
        StringBuilder builder = new StringBuilder(uri.getAuthority());
        try {
            contentProvider = mCR.acquireUnstableProvider(uri);
            builder.append("\n").append((contentProvider == null ? "null" : contentProvider.toString()));
            if (contentProvider != null) {
                prepareDataForSet(data, pkgArray, builder);

                contentProvider.call(getPackageName(), method, "", data);

                printResultForSet(builder);
            }
        } catch (Exception e) {
            Log.e(TAG, "###providersTest e=" + e);
        } finally {
            if (contentProvider != null) {
                mCR.releaseUnstableProvider(contentProvider);
            }
        }
    }

    private void prepareDataForSet(Bundle data, String[] pkgArray, StringBuilder builder) {
        ArrayList<String> pkgArrayList = new ArrayList<>(pkgArray.length);
        for (int i = 0; i < pkgArray.length; i++) {
            pkgArrayList.add(pkgArray[i]);
        }
        data.putStringArrayList("packageList", pkgArrayList);

        int count = 0;
        for (String pkg : pkgArray) {
            data.putBoolean(pkg, mSwitchChecked);
            builder.append("\n").append(++count).append("  ").append(pkg).append("  ").append(mSwitchChecked);
        }
    }

    private void printResultForSet(StringBuilder builder) {
        String text = builder.toString();
        mResultText.setText(text);
        mToast.setText(text);
        mToast.show();
    }

    private void prepareDataForGet(Bundle data, String[] pkgArray) {
        ArrayList<String> pkgArrayList = new ArrayList<>(pkgArray.length);
        for (int i = 0; i < pkgArray.length; i++) {
            pkgArrayList.add(pkgArray[i]);
        }
        data.putStringArrayList("packageList", pkgArrayList);
    }

    private void printResultForGet(Bundle result, String[] pkgArray, StringBuilder builder) {
        Set<String> keys = result.keySet();
        int count = 0;
        for (String pkg : pkgArray) {
            if (keys.contains(pkg)) {
                builder.append("\n").append(++count).append("  ").append(pkg).append("  ").append(result.get(pkg));
            } else {
                builder.append("\n").append(++count).append("  ").append(pkg).append("  ").append("null");
            }
        }

        String text = builder.toString();
        mResultText.setText(text);
        mToast.setText(text);
        mToast.show();
    }

}
