package com.flurry.sdk;

import android.content.Context;

public class ls {
    private static final String f418a;
    private static String f419b;
    private static String f420c;
    private static String f421d;

    static {
        f418a = ls.class.getSimpleName();
        f419b = "com.google.android.gms.common.GoogleApiAvailability";
        f420c = "com.google.android.gms.common.GooglePlayServicesUtil";
        f421d = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    }

    public static boolean m327a(Context context) {
        try {
            return m328a(context, f419b);
        } catch (Exception e) {
            try {
                return m328a(context, f420c);
            } catch (Exception e2) {
                kf.m189b(f418a, "GOOGLE PLAY SERVICES EXCEPTION: " + e2.getMessage());
                kf.m189b(f418a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
                return false;
            }
        }
    }

    private static boolean m328a(Context context, String str) throws Exception {
        Object a = lu.m337a(null, "isGooglePlayServicesAvailable").m338a(Class.forName(str)).m339a(Context.class, context).m340a();
        return a != null && ((Integer) a).intValue() == 0;
    }

    public static jo m329b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            Object a = lu.m337a(null, "getAdvertisingIdInfo").m338a(Class.forName(f421d)).m339a(Context.class, context).m340a();
            return new jo(m326a(a), m330b(a));
        } catch (Exception e) {
            kf.m189b(f418a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            kf.m189b(f418a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return null;
        }
    }

    private static String m326a(Object obj) {
        try {
            return (String) lu.m337a(obj, "getId").m340a();
        } catch (Exception e) {
            kf.m189b(f418a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            kf.m189b(f418a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return null;
        }
    }

    private static boolean m330b(Object obj) {
        try {
            Boolean bool = (Boolean) lu.m337a(obj, "isLimitAdTrackingEnabled").m340a();
            return bool != null ? bool.booleanValue() : false;
        } catch (Exception e) {
            kf.m189b(f418a, "GOOGLE PLAY SERVICES ERROR: " + e.getMessage());
            kf.m189b(f418a, "There is a problem with the Google Play Services library, which is required for Android Advertising ID support. The Google Play Services library should be integrated in any app shipping in the Play Store that uses analytics or advertising.");
            return false;
        }
    }
}
