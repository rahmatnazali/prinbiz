package com.google.android.gms.common.stats;

import android.content.ComponentName;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class zzd {
    public static int AA;
    public static int AB;
    public static int AC;
    public static int AD;
    public static int AE;
    public static int AF;
    public static int AG;
    public static final ComponentName Az;
    public static int LOG_LEVEL_OFF;

    static {
        Az = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.common.stats.GmsCoreStatsService");
        LOG_LEVEL_OFF = 0;
        AA = 1;
        AB = 2;
        AC = 4;
        AD = 8;
        AE = 16;
        AF = 32;
        AG = 1;
    }
}
