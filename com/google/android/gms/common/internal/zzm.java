package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzm {
    private static final Object yL;
    private static zzm yM;

    static {
        yL = new Object();
    }

    public static zzm zzce(Context context) {
        synchronized (yL) {
            if (yM == null) {
                yM = new zzn(context.getApplicationContext());
            }
        }
        return yM;
    }

    public abstract boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract boolean zza(String str, String str2, ServiceConnection serviceConnection, String str3);

    public abstract void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str);

    public abstract void zzb(String str, String str2, ServiceConnection serviceConnection, String str3);
}
