package com.demo.mozart.share;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class PullAppNameParser implements AppNameParser {

    @Override
    public List<AppName> parse(InputStream is) throws Exception {
        List<AppName> appNames = null;
        AppName appName = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
            case XmlPullParser.START_DOCUMENT:
                appNames = new ArrayList<AppName>();
                break;

            case XmlPullParser.START_TAG:
                if (parser.getName().equals("appName")) {
                    appName = new AppName();
                } else if (parser.getName().equals("packageName")) {
                    eventType = parser.next();
                    appName.setPackageName(parser.getText());
                } else if (parser.getName().equals("className")) {
                    eventType = parser.next();
                    appName.setClassName(parser.getText());
                } else if (parser.getName().equals("labelName")) {
                    eventType = parser.next();
                    appName.setLabelName(parser.getText());
                }
                break;

            case XmlPullParser.END_TAG:
                if (parser.getName().equals("appName")) {
                    appNames.add(appName);
                    appName = null;
                }
                break;

            default:
                break;
            }
            eventType = parser.next();
        }
        return appNames;
    }

}
