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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyReceiver extends BroadcastReceiver {
    private static final String ACTION_OPPO_BOOT_COMPLETED = "android.intent.action.OPPO_BOOT_COMPLETED";
    private static final String IS_GAME_SPACE_DATABASE_FIRST_INIT = "is_game_space_database_first_init";
    private static final String ACTION_CREATE_APP_LIST_DATABASE = "action_create_app_list_database";
    private static final String ACTION_UPGRADE_APP_LIST_DATABASE = "action_upgrade_app_list_database";

    // OTA intent action
    private static final String ACTION_OTA_SUCCESSED = "android.intent.action.OPPO_OTA_UPDATE_SUCCESSED";
    private static final String ACTION_RECOVER_SUCCESSED = "android.intent.action.OPPO_RECOVER_UPDATE_SUCCESSED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("morh", "onReceive(): " + action);
    }
}
