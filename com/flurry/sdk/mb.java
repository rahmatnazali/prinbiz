package com.flurry.sdk;

public class mb {
    private static final String f438a;
    private static boolean f439b;

    static {
        f438a = mb.class.getSimpleName();
    }

    public static synchronized void m358a() {
        synchronized (mb.class) {
            if (!f439b) {
                kh.m197a(jd.class);
                try {
                    kh.m197a(hk.class);
                } catch (NoClassDefFoundError e) {
                    kf.m182a(3, f438a, "Analytics module not available");
                }
                try {
                    kh.m197a(lz.class);
                } catch (NoClassDefFoundError e2) {
                    kf.m182a(3, f438a, "Crash module not available");
                }
                try {
                    kh.m197a(Class.forName("com.flurry.sdk.i"));
                } catch (NoClassDefFoundError e3) {
                    kf.m182a(3, f438a, "Ads module not available");
                    f439b = true;
                } catch (ClassNotFoundException e4) {
                    kf.m182a(3, f438a, "Ads module not available");
                    f439b = true;
                }
                f439b = true;
            }
        }
    }
}
