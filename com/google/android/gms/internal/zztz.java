package com.google.android.gms.internal;

public final class zztz {
    private static zztz OT;
    private final zztw OU;
    private final zztx OV;

    static {
        zza(new zztz());
    }

    private zztz() {
        this.OU = new zztw();
        this.OV = new zztx();
    }

    protected static void zza(zztz com_google_android_gms_internal_zztz) {
        synchronized (zztz.class) {
            OT = com_google_android_gms_internal_zztz;
        }
    }

    private static zztz zzbes() {
        zztz com_google_android_gms_internal_zztz;
        synchronized (zztz.class) {
            com_google_android_gms_internal_zztz = OT;
        }
        return com_google_android_gms_internal_zztz;
    }

    public static zztw zzbet() {
        return zzbes().OU;
    }

    public static zztx zzbeu() {
        return zzbes().OV;
    }
}
