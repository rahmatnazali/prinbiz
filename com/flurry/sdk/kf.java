package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Log;

public final class kf {
    private static boolean f326a;
    private static int f327b;
    private static boolean f328c;

    static {
        f326a = false;
        f327b = 5;
        f328c = false;
    }

    public static void m180a() {
        f326a = true;
    }

    public static void m187b() {
        f326a = false;
    }

    public static int m190c() {
        return f327b;
    }

    public static boolean m195d() {
        return f328c;
    }

    public static void m181a(int i) {
        f327b = i;
    }

    public static void m186a(boolean z) {
        f328c = z;
    }

    public static void m184a(String str, String str2) {
        m188b(3, str, str2);
    }

    public static void m189b(String str, String str2) {
        m188b(6, str, str2);
    }

    public static void m192c(String str, String str2) {
        m188b(4, str, str2);
    }

    public static void m194d(String str, String str2) {
        m188b(2, str, str2);
    }

    public static void m196e(String str, String str2) {
        m188b(5, str, str2);
    }

    public static void m182a(int i, String str, String str2) {
        m191c(i, str, str2);
    }

    private static void m188b(int i, String str, String str2) {
        if (!f326a && f327b <= i) {
            m193d(i, str, str2);
        }
    }

    private static void m191c(int i, String str, String str2) {
        if (f328c) {
            m193d(i, str, str2);
        }
    }

    private static void m193d(int i, String str, String str2) {
        if (!f328c) {
            str = "FlurryAgent";
        }
        int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = 4000 > length - i2 ? length : i2 + 4000;
            if (Log.println(i, str, str2.substring(i2, i3)) > 0) {
                i2 = i3;
            } else {
                return;
            }
        }
    }

    public static void m185a(String str, String str2, Throwable th) {
        m188b(6, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    public static void m183a(int i, String str, String str2, Throwable th) {
        m191c(i, str, str2 + '\n' + Log.getStackTraceString(th));
    }
}
