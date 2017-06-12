package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.C0178R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzai;
import com.google.android.gms.common.internal.zzz;

@Deprecated
public final class zzqf {
    private static zzqf vd;
    private static Object zzamr;
    private final String ve;
    private final Status vf;
    private final String vg;
    private final String vh;
    private final String vi;
    private final boolean vj;
    private final boolean vk;
    private final String zzcjf;

    static {
        zzamr = new Object();
    }

    zzqf(Context context) {
        boolean z = true;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(C0178R.string.common_google_play_services_unknown_issue));
        if (identifier != 0) {
            boolean z2 = resources.getInteger(identifier) != 0;
            if (z2) {
                z = false;
            }
            this.vk = z;
            z = z2;
        } else {
            this.vk = false;
        }
        this.vj = z;
        zzai com_google_android_gms_common_internal_zzai = new zzai(context);
        this.vg = com_google_android_gms_common_internal_zzai.getString("firebase_database_url");
        this.vi = com_google_android_gms_common_internal_zzai.getString("google_storage_bucket");
        this.vh = com_google_android_gms_common_internal_zzai.getString("gcm_defaultSenderId");
        this.ve = com_google_android_gms_common_internal_zzai.getString("google_api_key");
        Object zzcf = zzz.zzcf(context);
        if (zzcf == null) {
            zzcf = com_google_android_gms_common_internal_zzai.getString("google_app_id");
        }
        if (TextUtils.isEmpty(zzcf)) {
            this.vf = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzcjf = null;
            return;
        }
        this.zzcjf = zzcf;
        this.vf = Status.sq;
    }

    zzqf(String str, boolean z) {
        this(str, z, null, null, null);
    }

    zzqf(String str, boolean z, String str2, String str3, String str4) {
        this.zzcjf = str;
        this.ve = null;
        this.vf = Status.sq;
        this.vj = z;
        this.vk = !z;
        this.vg = str2;
        this.vh = str4;
        this.vi = str3;
    }

    public static String zzaqo() {
        return zzgx("getGoogleAppId").zzcjf;
    }

    public static boolean zzaqp() {
        return zzgx("isMeasurementExplicitlyDisabled").vk;
    }

    public static Status zzc(Context context, String str, boolean z) {
        Status zzgw;
        zzab.zzb((Object) context, (Object) "Context must not be null.");
        zzab.zzh(str, "App ID must be nonempty.");
        synchronized (zzamr) {
            if (vd != null) {
                zzgw = vd.zzgw(str);
            } else {
                vd = new zzqf(str, z);
                zzgw = vd.vf;
            }
        }
        return zzgw;
    }

    public static Status zzcb(Context context) {
        Status status;
        zzab.zzb((Object) context, (Object) "Context must not be null.");
        synchronized (zzamr) {
            if (vd == null) {
                vd = new zzqf(context);
            }
            status = vd.vf;
        }
        return status;
    }

    private static zzqf zzgx(String str) {
        zzqf com_google_android_gms_internal_zzqf;
        synchronized (zzamr) {
            if (vd == null) {
                throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 34).append("Initialize must be called before ").append(str).append(".").toString());
            }
            com_google_android_gms_internal_zzqf = vd;
        }
        return com_google_android_gms_internal_zzqf;
    }

    Status zzgw(String str) {
        if (this.zzcjf == null || this.zzcjf.equals(str)) {
            return Status.sq;
        }
        String str2 = this.zzcjf;
        return new Status(10, new StringBuilder(String.valueOf(str2).length() + 97).append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '").append(str2).append("'.").toString());
    }
}
