package com.flurry.sdk;

import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;

public class js {
    private static final String f290a;

    static {
        f290a = js.class.getSimpleName();
    }

    public static int m128a() {
        int intValue = ((Integer) li.m668a().m272a("AgentVersion")).intValue();
        kf.m182a(4, f290a, "getAgentVersion() = " + intValue);
        return intValue;
    }

    private static String m130c() {
        return (String) li.m668a().m272a("ReleaseBetaVersion");
    }

    public static String m129b() {
        Object obj;
        if (m130c().length() > 0) {
            obj = ".";
        } else {
            String str = XmlPullParser.NO_NAMESPACE;
        }
        return String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", new Object[]{Integer.valueOf(m128a()), Integer.valueOf(((Integer) li.m668a().m272a("ReleaseMajorVersion")).intValue()), Integer.valueOf(((Integer) li.m668a().m272a("ReleaseMinorVersion")).intValue()), Integer.valueOf(((Integer) li.m668a().m272a("ReleasePatchVersion")).intValue()), obj, m130c()});
    }
}
