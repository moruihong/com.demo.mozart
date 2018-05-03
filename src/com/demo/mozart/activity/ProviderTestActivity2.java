/***********************************************************
 * * Copyright (C), 2008-2018, OPPO Mobile Comm Corp., Ltd.
 * * VENDOR_EDIT
 * * File: - XXX.java
 * * Description: XXX
 * * Version: 1.0
 * * Date : 2018/4/27
 * * Author: MoRuihong@System.Frameworks.GameSpace
 * *
 * * ---------------------Revision History: ---------------------
 * *  <author>        <data>      <version>     <desc>
 * *  MoRuihong     2018/4/27       1.0        build this module
 ****************************************************************/
package com.demo.mozart.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.demo.mozart.R;
import com.demo.mozart.utils.OppoLog;

public class ProviderTestActivity2 extends Activity {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PKG_NAME = "pkg_name";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_TIME_OUT = "timeout";
    public static final String COLUMN_PKG_TYPE = "pkg_type";
    public static final String COLUMN_SWITCH_ON_TIME = "switch_on_time";
    public static final Uri URI_GAME_SPACE_APP_LIST = Uri.parse("content://com.coloros.provider.AppListProvider/app_list");
    public static final Uri URI_GAME_SPACE_APP_LIST2 = Uri.parse("content://com.coloros.provider.AppListProvider/app_list/dummy");
    public static final int MSG_WHEN_DATABASE_CHANGES = 100;

    private ContentResolver mContentResolver;
    private Uri mUri;
    private Context mContext;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHEN_DATABASE_CHANGES: {
                    if (mUri != null) {
                        Cursor cursor = mContentResolver.query(mUri, null, null, null, null);
                        if (null != cursor) {
                            OppoLog.d("cursor count = " + cursor.getCount());
                            cursor.moveToNext();
                            int type = cursor.getInt(cursor.getColumnIndex(COLUMN_PKG_TYPE));
                            String pkgName = cursor.getString(cursor.getColumnIndex(COLUMN_PKG_NAME));
                            OppoLog.d("type=" + type + " ; pkgName=" + pkgName);
                        }
                    } else {
                        OppoLog.d("Changes in data");
                    }
                }
                default:
                    break;
            }

            return false;
        }
    });

    /**
     * 内容提供者监听类
     */
    private ContentObserver contentObserver = new ContentObserver(mHandler) {
        @Override
        public boolean deliverSelfNotifications() {
            OppoLog.i("deliverSelfNotifications");
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            OppoLog.i("onChange-------->" + selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            OppoLog.i("onChange-------->" + uri + " " + selfChange);
            mUri = uri;
            mHandler.sendEmptyMessage(0x123);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_provider_test2);

        mContentResolver = getContentResolver();
        mContentResolver.registerContentObserver(URI_GAME_SPACE_APP_LIST, true, contentObserver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mContentResolver.unregisterContentObserver(contentObserver);
    }
}
