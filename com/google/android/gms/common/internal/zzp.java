package com.google.android.gms.common.internal;

import android.util.Log;

public final class zzp {
    public static final int za;
    private static final String zb;
    private final String zc;
    private final String zd;

    static {
        za = 23 - " PII_LOG".length();
        zb = null;
    }

    public zzp(String str) {
        this(str, null);
    }

    public zzp(String str, String str2) {
        zzab.zzb((Object) str, (Object) "log tag cannot be null");
        zzab.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", str, Integer.valueOf(23));
        this.zc = str;
        if (str2 == null || str2.length() <= 0) {
            this.zd = null;
        } else {
            this.zd = str2;
        }
    }

    private String zzhp(String str) {
        return this.zd == null ? str : this.zd.concat(str);
    }

    public void zzae(String str, String str2) {
        if (zzgg(3)) {
            Log.d(str, zzhp(str2));
        }
    }

    public void zzaf(String str, String str2) {
        if (zzgg(5)) {
            Log.w(str, zzhp(str2));
        }
    }

    public void zzag(String str, String str2) {
        if (zzgg(6)) {
            Log.e(str, zzhp(str2));
        }
    }

    public void zzb(String str, String str2, Throwable th) {
        if (zzgg(4)) {
            Log.i(str, zzhp(str2), th);
        }
    }

    public void zzc(String str, String str2, Throwable th) {
        if (zzgg(5)) {
            Log.w(str, zzhp(str2), th);
        }
    }

    public void zzd(String str, String str2, Throwable th) {
        if (zzgg(6)) {
            Log.e(str, zzhp(str2), th);
        }
    }

    public void zze(String str, String str2, Throwable th) {
        if (zzgg(7)) {
            Log.e(str, zzhp(str2), th);
            Log.wtf(str, zzhp(str2), th);
        }
    }

    public boolean zzgg(int i) {
        return Log.isLoggable(this.zc, i);
    }
}
