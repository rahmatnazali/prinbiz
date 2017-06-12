package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

public final class zzi {
    private static Boolean AX;
    private static Boolean AY;
    private static Boolean AZ;
    private static Boolean Ba;

    public static boolean zzb(Resources resources) {
        boolean z = false;
        if (resources == null) {
            return false;
        }
        if (AX == null) {
            boolean z2 = (resources.getConfiguration().screenLayout & 15) > 3;
            if ((zzs.zzavn() && z2) || zzc(resources)) {
                z = true;
            }
            AX = Boolean.valueOf(z);
        }
        return AX.booleanValue();
    }

    @TargetApi(13)
    private static boolean zzc(Resources resources) {
        if (AY == null) {
            Configuration configuration = resources.getConfiguration();
            boolean z = zzs.zzavp() && (configuration.screenLayout & 15) <= 3 && configuration.smallestScreenWidthDp >= 600;
            AY = Boolean.valueOf(z);
        }
        return AY.booleanValue();
    }

    @TargetApi(20)
    public static boolean zzck(Context context) {
        if (AZ == null) {
            boolean z = zzs.zzavv() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            AZ = Boolean.valueOf(z);
        }
        return AZ.booleanValue();
    }

    @TargetApi(21)
    public static boolean zzcl(Context context) {
        if (Ba == null) {
            boolean z = zzs.zzavx() && context.getPackageManager().hasSystemFeature("cn.google");
            Ba = Boolean.valueOf(z);
        }
        return Ba.booleanValue();
    }
}
