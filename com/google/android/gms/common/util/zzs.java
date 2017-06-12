package com.google.android.gms.common.util;

import android.os.Build.VERSION;

public final class zzs {
    public static boolean zzavn() {
        return zzhb(11);
    }

    public static boolean zzavo() {
        return zzhb(12);
    }

    public static boolean zzavp() {
        return zzhb(13);
    }

    public static boolean zzavq() {
        return zzhb(14);
    }

    public static boolean zzavr() {
        return zzhb(16);
    }

    public static boolean zzavs() {
        return zzhb(17);
    }

    public static boolean zzavt() {
        return zzhb(18);
    }

    public static boolean zzavu() {
        return zzhb(19);
    }

    public static boolean zzavv() {
        return zzhb(20);
    }

    @Deprecated
    public static boolean zzavw() {
        return zzavx();
    }

    public static boolean zzavx() {
        return zzhb(21);
    }

    public static boolean zzavy() {
        return zzhb(23);
    }

    private static boolean zzhb(int i) {
        return VERSION.SDK_INT >= i;
    }
}
