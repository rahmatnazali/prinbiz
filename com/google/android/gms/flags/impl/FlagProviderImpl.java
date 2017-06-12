package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza.zzb;
import com.google.android.gms.flags.impl.zza.zzc;
import com.google.android.gms.flags.impl.zza.zzd;
import com.google.android.gms.internal.zzty.zza;

@DynamiteApi
public class FlagProviderImpl extends zza {
    private boolean zzamt;
    private SharedPreferences zzaxu;

    public FlagProviderImpl() {
        this.zzamt = false;
    }

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return !this.zzamt ? z : zza.zza.zza(this.zzaxu, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return !this.zzamt ? i : zzb.zza(this.zzaxu, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return !this.zzamt ? j : zzc.zza(this.zzaxu, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return !this.zzamt ? str2 : zzd.zza(this.zzaxu, str, str2);
    }

    public void init(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        Context context = (Context) zze.zzad(com_google_android_gms_dynamic_zzd);
        if (!this.zzamt) {
            try {
                this.zzaxu = zzb.zzn(context.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 0));
                this.zzamt = true;
            } catch (NameNotFoundException e) {
            }
        }
    }
}
