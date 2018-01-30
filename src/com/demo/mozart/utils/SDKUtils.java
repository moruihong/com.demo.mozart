package com.demo.mozart.utils;

import android.os.Build;

/**
 * Created by 80055004 on 2017/3/31.
 */

public class SDKUtils {
    /**
     * android 6.0
     */
    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= 23/*Build.VERSION_CODES.M*/;
    }

    /**
     * android 5.1
     */
    public static boolean hasLollipop_MR1() {
        return Build.VERSION.SDK_INT >= 22/*Build.VERSION_CODES.LOLLIPOP_MR1*/;
    }

    /**
     * android 5.0
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= 21/* Build.VERSION_CODES.LOLLIPOP */;
    }

    /**
     * android 4.4W(KitKat for Wearables Only)
     */
    public static boolean hasKitKat1() {
        return Build.VERSION.SDK_INT >= 20/* Build.VERSION_CODES.KITKAT_WATCH */;
    }

    /**
     * android 4.4
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19/* Build.VERSION_CODES.KITKAT */;
    }

    /**
     * android 4.3
     */
    public static boolean hasJellyBeanMr2() {
        return Build.VERSION.SDK_INT >= 18/* Build.VERSION_CODES.JELLY_BEAN_MR2 */;
    }

    /**
     * android 4.2
     */
    public static boolean hasJellyBeanMr1() {
        return Build.VERSION.SDK_INT >= 17/* Build.VERSION_CODES.JELLY_BEAN_MR1 */;
    }

    /**
     * android 4.1
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16/* Build.VERSION_CODES.JELLY_BEAN */;
    }

    /**
     * android 4.0
     */
    public static boolean hasJellyBeanMraa2() {
        return Build.VERSION.SDK_INT >= 14/* Build.VERSION_CODES.ICE_CREAM_SANDWICH */;
    }
}
