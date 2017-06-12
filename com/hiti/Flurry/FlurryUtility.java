package com.hiti.Flurry;

import android.content.Context;
import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryAgent.Builder;
import java.util.HashMap;
import java.util.Map;

public class FlurryUtility {
    public static final String FLURRY_API_KEY_PRINBIZ = "5RCTKD4G8SQ5HPHFVTPX";
    public static final String FLURRY_API_KEY_PRINGO = "XDY56Q9PWG3VMB8HCFBM";
    public static final String FLURRY_API_KEY_PRINHOME = "BX82R67GCZ7643V7MT5M";

    public static void init(Context context, String strAPIKey) {
        FlurryAgent.init(context, strAPIKey);
        new Builder().withLogEnabled(true).build(context, strAPIKey);
    }

    public static void setLogEnabled(boolean boEnable) {
        FlurryAgent.setLogEnabled(boEnable);
    }

    public static void onStartSession(Context context, String strAPIKey) {
        FlurryAgent.onStartSession(context, strAPIKey);
    }

    public static void onStartSession(Context context) {
        FlurryAgent.onStartSession(context);
    }

    public static void onEndSession(Context context) {
        FlurryAgent.onEndSession(context);
    }

    public static void logEvent(String strEvent) {
        FlurryAgent.logEvent(strEvent);
    }

    public static void logEvent(String strEvent, String strKey, String strValue) {
        if (strKey != null && strValue != null && strKey.length() > 0) {
            Map params = new HashMap();
            params.put(strKey, strValue);
            FlurryAgent.logEvent(strEvent, params, true);
        }
    }

    public static void endTimedEvent(String strEvent) {
        FlurryAgent.endTimedEvent(strEvent);
    }
}
