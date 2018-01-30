package com.demo.mozart.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Video;

public class Utils {

    public static final Uri EXTERNAL_FILE_NEW_URI = Uri.parse("content://media/external/file_new");
    public static final Uri EXTERNAL_VIDEO_URI = EXTERNAL_FILE_NEW_URI;//Video.Media.EXTERNAL_CONTENT_URI;

    public static final String[] PROJECTION = new String[] {
            Video.Media._ID,
            Video.Media.DATA,
            // Video.Media.DATE_TAKEN,
            Video.Media.TITLE,
            // Video.Media.DISPLAY_NAME,
            Video.Media.DURATION,
            Video.Media.BOOKMARK,
            // Video.Media.DATE_TAKEN,
            Video.Media.MIME_TYPE,
            Video.Media.SIZE,
            Video.Media.HEIGHT,
            Video.Media.WIDTH
    };

    public static final int ID_COL_INDEX = 0;
    public static final int DATA_COL_INDEX = 1;
    // public static final int DATA_TAKEN_COL_INDEX = 2;
    public static final int TITLE_COL_INDEX = 2;
    // public static final int DISPLAY_NAME_COL_INDEX = 4;
    public static final int DURATION_COL_INDEX = 3;
    public static final int BOOKMARK_COL_INDEX = 4;
    // public static final int DATE_TAKEN_COL_INDEX = 7;
    public static final int MIME_TYPE_COL_INDEX = 5;
    public static final int SIZE_COL_INDEX = 6;
    public static final int HEIGHT_COL_INDEX = 7;
    public static final int WIDTH_COL_INDEX = 8;

    /**
     * get cursor of all video
     */
    public static Cursor query(Context context, Uri specifyUri) {
        Cursor cursor = null;
        if (context != null && specifyUri != null) {
            StringBuilder where = new StringBuilder();
            where.append(Video.Media.DATA).append(" not null AND ").append(Video.Media.SIZE).append(" > '0'");

            cursor = context.getContentResolver().query(specifyUri, PROJECTION, where.toString(), null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
        }
        return cursor;
    }

    public static String getFileNameNoSuffix(Cursor cursor) {
        if (!checkCursorValid(cursor)) {
            return null;
        }
        String wholeFilePath = cursor.getString(Utils.DATA_COL_INDEX);
        if (null != wholeFilePath) {
            return getFileName(wholeFilePath, false);
        }
        return null;
    }

    public static String getSuffix(String wholeFilePath) {
        String suffix = null;
        if (null != wholeFilePath) {
            int start = wholeFilePath.lastIndexOf('.');
            if (start >= 0) {
                suffix = wholeFilePath.substring(start + 1);
            }
        }
        return suffix;
    }

    public static String getFileName(String wholeFilePath, boolean hasSuffix) {
        if (null != wholeFilePath) {
            int start = wholeFilePath.lastIndexOf('/');
            if (start >= 0) {
                if (hasSuffix) {
                    return wholeFilePath.substring(start + 1);
                } else {
                    int end = wholeFilePath.lastIndexOf('.');
                    if (end > start) {
                        return wholeFilePath.substring(start + 1, end);
                    }
                }
            }
        }
        return null;
    }

    public static boolean checkCursorValid(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public static void closeCursor(Cursor cursor) {

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
    }

    public static Cursor getCursorFromFile(Context context, String file) {
        Cursor cursor = null;
        StringBuilder where = new StringBuilder(Video.Media.DATA);
        where.append(" IN (\"").append(file).append("\") ");
        cursor = context.getContentResolver().query(EXTERNAL_VIDEO_URI, PROJECTION, where.toString(), null,
                null);
        if (checkCursorValid(cursor)) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public static Uri getUriFromCursor(Cursor cursor) {
        Uri uri = null;
        if (checkCursorValid(cursor)) {
            long videoId = cursor.getLong(ID_COL_INDEX);
            uri = ContentUris.withAppendedId(EXTERNAL_VIDEO_URI, videoId);
        }
        return uri;
    }

}
