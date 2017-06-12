package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzf;
import java.util.regex.Pattern;

public class zzw {
    private static final Pattern Bl;

    static {
        Bl = Pattern.compile("\\$\\{(.*?)\\}");
    }

    public static boolean zzib(String str) {
        return str == null || zzf.xN.zzb(str);
    }
}
