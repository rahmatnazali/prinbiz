package com.flurry.sdk;

import android.telephony.TelephonyManager;

public class jl {
    private static jl f260a;
    private static final String f261b;

    public static synchronized jl m105a() {
        jl jlVar;
        synchronized (jl.class) {
            if (f260a == null) {
                f260a = new jl();
            }
            jlVar = f260a;
        }
        return jlVar;
    }

    public static void m106b() {
        f260a = null;
    }

    static {
        f261b = jl.class.getSimpleName();
    }

    private jl() {
    }

    public static String m107c() {
        TelephonyManager telephonyManager = (TelephonyManager) jr.m120a().f284a.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    public static String m108d() {
        TelephonyManager telephonyManager = (TelephonyManager) jr.m120a().f284a.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getNetworkOperator();
    }
}
