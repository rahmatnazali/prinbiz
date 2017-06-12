package com.flurry.sdk;

import java.util.Locale;

public final class jh {
    public static jh f248a;

    public static synchronized jh m96a() {
        jh jhVar;
        synchronized (jh.class) {
            if (f248a == null) {
                f248a = new jh();
            }
            jhVar = f248a;
        }
        return jhVar;
    }

    private jh() {
    }

    public static String m97b() {
        return Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
    }
}
