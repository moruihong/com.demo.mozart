package com.demo.mozart.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.demo.mozart.R;
import com.oppo.os.OppoUsbEnvironment;

public class DirListUtil {

    private static final String TAG = "DirListUtil";

    public static int XML_BLACK_RES_ID = R.raw.default_dir_black_list;

    public static String readXMLFromResource(Context context, int xmlResId) {
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(xmlResId);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            return new String(buffer);
        } catch (IOException ioe) {
            Log.w(TAG, "readXMLFromResource:", ioe);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static ArrayList<String> parseFilterBlock(Context context, String xml) throws XmlPullParserException,
            IOException {
        if (TextUtils.isEmpty(xml)) {
            return null;
        }
        // create XML pull parser
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(new StringReader(xml));
        parser.nextTag();
        ArrayList<String> list = new ArrayList<String>();
        int evenType = parser.getEventType();
        while (evenType != XmlPullParser.END_DOCUMENT) {
            if (XmlPullParser.START_TAG == evenType) {
                final String tagName = parser.getName();
                if ("item".equals(tagName)) {
                    String values = parser.nextText();
                    list.add(values);
                }
            }
            evenType = parser.next();
        }
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * @param toLowerCase
     *            true means paths will to lower case
     * @return
     */
    public static LinkedHashMap<String, String> parseXmlList(final Context context, String xml, boolean toLowerCase) {
        ArrayList<String> paths = null;
        try {
            if (null != xml) {
                paths = parseFilterBlock(context, xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseXmlList(context, paths, toLowerCase);
    }

    private static LinkedHashMap<String, String> parseXmlList(final Context context, ArrayList<String> paths,
            boolean toLowerCase) {
        if (paths == null || paths.size() == 0) {
            return null;
        }

        LinkedHashMap<String, String> fullList = new LinkedHashMap<String, String>();
        String internalSdPath = OppoUsbEnvironment.getInternalPath(context);
        String externalSdPath = OppoUsbEnvironment.getExternalPath(context);
        boolean inSdMounted = OppoUsbEnvironment.isVolumeMounted(context, internalSdPath);
        boolean exSdMounted = OppoUsbEnvironment.isVolumeMounted(context, externalSdPath);
        if (internalSdPath == null && externalSdPath == null) {
            return null;
        }
        if (externalSdPath != null && exSdMounted) {
            addPathList(fullList, toLowerCase ? externalSdPath.toLowerCase() : externalSdPath, paths);
        }
        if (internalSdPath != null && inSdMounted) {
            addPathList(fullList, toLowerCase ? internalSdPath.toLowerCase() : internalSdPath, paths);
        }

        return fullList;
    }

    private static void addPathList(LinkedHashMap<String, String> fullList, String rootDirectory,
            ArrayList<String> paths) {
        if (fullList == null || rootDirectory == null || paths == null) {
            return;
        }

        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            if (path.length() == 0) {
                continue;
            }
            path = rootDirectory + path;
            path = path.replace("%", "t");
            Log.v(TAG, "addPathList, regular path=" + path);
            fullList.put(path, "");
        }
    }

}
