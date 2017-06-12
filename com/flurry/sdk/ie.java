package com.flurry.sdk;

import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ie extends kk {
    private static final String f460a;

    static {
        f460a = ie.class.getSimpleName();
    }

    public final String m399a(String str, Map<String, String> map) {
        CharSequence a = m210a(str);
        while (a != null) {
            String valueOf;
            if (kk.m209a("timestamp_epoch_millis", a)) {
                valueOf = String.valueOf(System.currentTimeMillis());
                kf.m182a(3, f460a, "Replacing param timestamp_epoch_millis with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("session_duration_millis", a)) {
                jd.m552a();
                valueOf = Long.toString(jd.m556f());
                kf.m182a(3, f460a, "Replacing param session_duration_millis with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("fg_timespent_millis", a)) {
                jd.m552a();
                valueOf = Long.toString(jd.m556f());
                kf.m182a(3, f460a, "Replacing param fg_timespent_millis with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("install_referrer", a)) {
                valueOf = new hl().m15b();
                if (valueOf == null) {
                    valueOf = XmlPullParser.NO_NAMESPACE;
                }
                kf.m182a(3, f460a, "Replacing param install_referrer with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("geo_latitude", a)) {
                r2 = ji.m569a().m580e();
                valueOf = XmlPullParser.NO_NAMESPACE;
                if (r2 != null) {
                    valueOf = valueOf + lr.m306a(r2.getLatitude());
                }
                kf.m182a(3, f460a, "Replacing param geo_latitude with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("geo_longitude", a)) {
                r2 = ji.m569a().m580e();
                valueOf = XmlPullParser.NO_NAMESPACE;
                if (r2 != null) {
                    valueOf = valueOf + lr.m306a(r2.getLongitude());
                }
                kf.m182a(3, f460a, "Replacing param geo_longitude with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("publisher_user_id", a)) {
                valueOf = (String) li.m668a().m272a("UserId");
                kf.m182a(3, f460a, "Replacing param publisher_user_id with: " + valueOf);
                valueOf = str.replace(a, lr.m319c(valueOf));
            } else if (kk.m209a("event_name", a)) {
                if (map.containsKey("event_name")) {
                    kf.m182a(3, f460a, "Replacing param event_name with: " + ((String) map.get("event_name")));
                    valueOf = str.replace(a, lr.m319c((String) map.get("event_name")));
                } else {
                    kf.m182a(3, f460a, "Replacing param event_name with empty string");
                    valueOf = str.replace(a, XmlPullParser.NO_NAMESPACE);
                }
            } else if (!kk.m209a("event_time_millis", a)) {
                kf.m182a(3, f460a, "Unknown param: " + a);
                valueOf = str.replace(a, XmlPullParser.NO_NAMESPACE);
            } else if (map.containsKey("event_time_millis")) {
                kf.m182a(3, f460a, "Replacing param event_time_millis with: " + ((String) map.get("event_time_millis")));
                valueOf = str.replace(a, lr.m319c((String) map.get("event_time_millis")));
            } else {
                kf.m182a(3, f460a, "Replacing param event_time_millis with empty string");
                valueOf = str.replace(a, XmlPullParser.NO_NAMESPACE);
            }
            a = m210a(valueOf);
            str = valueOf;
        }
        return str;
    }
}
