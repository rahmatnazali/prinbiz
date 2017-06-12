package com.google.android.gms.internal;

public final class zzann {
    public static void zzbo(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T zzy(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
