package com.google.android.gms.common.util;

import java.util.regex.Pattern;
import javax.jmdns.impl.constants.DNSConstants;

public final class zzl {
    private static Pattern Be;

    static {
        Be = null;
    }

    public static int zzha(int i) {
        return i / DNSConstants.PROBE_CONFLICT_INTERVAL;
    }
}
