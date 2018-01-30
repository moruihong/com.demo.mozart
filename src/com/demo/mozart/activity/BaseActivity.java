package com.demo.mozart.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;

import com.demo.mozart.utils.OppoLog;


public class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";

    public boolean mHasPermission = true;
    public static String SELF_PACKAGE_NAME;
    static final String[] mPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SELF_PACKAGE_NAME = getPackageName();

        /*if (SDKUtils.hasM()) {
            checkPermissions();
        }*/

    }

    public void checkPermissions() {
        OppoLog.d(TAG, "checkPermissions, start");
        if (!checkShowRequestPermission(mPermissions)) {
            mHasPermission = false;
            grantRuntimePermissions(this);
            return;
        }

        int status = PackageManager.PERMISSION_GRANTED;
        for (String permission : mPermissions) {
            status = checkSelfPermission(permission);
            OppoLog.d(TAG, "checkPermissions, permission:" + permission + ", status=" + status);
            if (status != PackageManager.PERMISSION_GRANTED) {
                break;
            }
        }
        if (status != PackageManager.PERMISSION_GRANTED) {
            OppoLog.d(TAG, "checkPermissions, begin to requestPermissions!");
            mHasPermission = false;
            goSettingRuntimePermission();
        }
    }

    // set permission silently
    private void grantRuntimePermissions(Context context) {
        PackageManager pm = context.getPackageManager();
        for (String permission : mPermissions) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                pm.grantRuntimePermission(SELF_PACKAGE_NAME, permission, UserHandle.OWNER);
            }
        }
    }

    private boolean checkShowRequestPermission(String[] permissions) {
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(permission)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void goSettingRuntimePermission() {
        OppoLog.d(TAG, "goSettingRuntimePermission");
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MANAGE_APP_PERMISSIONS");
            intent.putExtra(
                    "android.intent.extra.PACKAGE_NAME",
                    SELF_PACKAGE_NAME);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
