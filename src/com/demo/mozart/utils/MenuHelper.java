package com.demo.mozart.utils;

import java.io.Closeable;

import android.content.Context;

import com.demo.mozart.R;

public class MenuHelper {
    public static final int GENERIC_ITEM = 1;
    public static final int IMAGE_SAVING_ITEM = 2;
    public static final int VIDEO_SAVING_ITEM = 3;
    public static final int IMAGE_MODE_ITEM = 4;
    public static final int VIDEO_MODE_ITEM = 5;
    public static final int MENU_ITEM_MAX = 5;

    public static final int INCLUDE_ALL = 0xFFFFFFFF;
    public static final int INCLUDE_VIEWPLAY_MENU = (1 << 0);
    public static final int INCLUDE_SHARE_MENU = (1 << 1);
    public static final int INCLUDE_SET_MENU = (1 << 2);
    public static final int INCLUDE_CROP_MENU = (1 << 3);
    public static final int INCLUDE_DELETE_MENU = (1 << 4);
    public static final int INCLUDE_ROTATE_MENU = (1 << 5);
    public static final int INCLUDE_DETAILS_MENU = (1 << 6);
    public static final int INCLUDE_RENAME_MENU = (1 << 7);
    public static final int MENU_SWITCH_CAMERA_MODE = 0;
    public static final int MENU_CAPTURE_PICTURE = 1;
    public static final int MENU_CAPTURE_VIDEO = 2;
    public static final int MENU_IMAGE_SHARE = 10;
    public static final int MENU_IMAGE_SET = 14;
    public static final int MENU_IMAGE_SET_WALLPAPER = 15;
    public static final int MENU_IMAGE_SET_CONTACT = 16;
    public static final int MENU_IMAGE_SET_MYFAVE = 17;
    public static final int MENU_IMAGE_CROP = 18;
    public static final int MENU_IMAGE_ROTATE = 19;
    public static final int MENU_IMAGE_ROTATE_LEFT = 20;
    public static final int MENU_IMAGE_ROTATE_RIGHT = 21;
    public static final int MENU_IMAGE_TOSS = 22;
    public static final int MENU_VIDEO_PLAY = 23;
    public static final int MENU_VIDEO_SHARE = 24;
    public static final int MENU_VIDEO_TOSS = 27;

    public static final long SHARE_FILE_LENGTH_LIMIT = 3L * 1024L * 1024L;

    public static final int NO_STORAGE_ERROR = -1;
    public static final int CANNOT_STAT_ERROR = -2;

    public static final int DELETE_TYPE_SINGLE = 1;
    public static final int DELETE_TYPE_MARK = 2;
    public static final int DELETE_TYPE_ALL = 3;
    /**
     * Activity result code used to report crop results.
     */
    public static final int RESULT_COMMON_MENU_CROP = 490;

    public static void closeSilently(Closeable target) {
        try {
            if (target != null) {
                target.close();
            }
        } catch (Throwable t) {
            // ignore all exceptions, that's what silently means
        }
    }

    public static String formatDuration(final Context context, int durationMs) {
        int duration = durationMs / 1000;
        int h = duration / 3600;
        int m = (duration - h * 3600) / 60;
        int s = duration - (h * 3600 + m * 60);
        String durationValue;
        if (h == 0) {
            // set seconds to be 1s at least if durationMs > 0
            if (durationMs > 0 && duration <= 0) {
                s = 1;
            }
            // set time to be m:s if hours == 0
            durationValue = String.format(context.getString(R.string.details_ms), m, s);
        } else {
            // set time to be h:m:s if hours > 0
            durationValue = String.format(context.getString(R.string.details_hms), h, m, s);
        }
        return durationValue;
    }

    public static String formatDuration2(final Context context, int durationMs) {
        int duration = durationMs / 1000;
        int h = duration / 3600;
        int m = (duration - h * 3600) / 60;
        int s = duration - (h * 3600 + m * 60);
        String durationValue;
        durationValue = String.format(context.getString(R.string.details_hms_2), h, m, s);
        return durationValue;
    }

    public static String parseString(String text) {
        try {
            boolean isGBK = false;
            byte[] data = text.getBytes("iso8859-1");
            int firsthalf = 0;
            int secondhalf = 0;
            int thirdhalf = 0;
            int fourthhalf = 0;
            for (int i = 0; i < data.length; i++) {
                firsthalf = data[i] & 0xF0;
                secondhalf = 0;
                thirdhalf = 0;
                fourthhalf = 0;
                if (firsthalf == 0xA0 || firsthalf == 0xB0) {
                    isGBK = true;
                    break;
                } else if (firsthalf == 0xC0 || firsthalf == 0xD0) {
                    if (i + 1 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        if (secondhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                } else if (firsthalf == 0xE0) {
                    if (i + 2 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        thirdhalf = data[i + 2] & 0xC0;
                        if (secondhalf != 0x80 || thirdhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                } else if (firsthalf == 0xF0) {
                    if (i + 3 > data.length - 1) {
                        isGBK = true;
                        break;
                    } else {
                        secondhalf = data[i + 1] & 0xC0;
                        thirdhalf = data[i + 2] & 0xC0;
                        fourthhalf = data[i + 3] & 0xC0;
                        if (secondhalf != 0x80 || thirdhalf != 0x80 || fourthhalf != 0x80) {
                            isGBK = true;
                            break;
                        }
                    }
                }
            }
            if (isGBK) {
                text = new String(data, "GBK");
            }
        } catch (Exception e) {
        }
        return text;
    }

}
