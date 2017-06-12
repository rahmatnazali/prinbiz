package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;

public class jt {
    private static jt f291a;
    private static final String f292b;
    private static final HashMap<String, Map<String, String>> f293c;

    public static synchronized jt m131a() {
        jt jtVar;
        synchronized (jt.class) {
            if (f291a == null) {
                f291a = new jt();
            }
            jtVar = f291a;
        }
        return jtVar;
    }

    public static synchronized void m132b() {
        synchronized (jt.class) {
            f291a = null;
        }
    }

    static {
        f292b = jt.class.getSimpleName();
        f293c = new HashMap();
    }

    public final synchronized void m133a(String str, String str2, Map<String, String> map) {
        if (map == null) {
            map = new HashMap();
        }
        if (map.size() >= 10) {
            kf.m196e(f292b, "MaxOriginParams exceeded: " + map.size());
        } else {
            map.put("flurryOriginVersion", str2);
            synchronized (f293c) {
                if (f293c.size() < 10 || f293c.containsKey(str)) {
                    f293c.put(str, map);
                } else {
                    kf.m196e(f292b, "MaxOrigins exceeded: " + f293c.size());
                }
            }
        }
    }

    public final synchronized HashMap<String, Map<String, String>> m134c() {
        HashMap<String, Map<String, String>> hashMap;
        synchronized (f293c) {
            hashMap = new HashMap(f293c);
        }
        return hashMap;
    }
}
