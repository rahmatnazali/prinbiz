package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.internal.zzpm.zza;

public class zzqd<O extends ApiOptions> extends zzpu {
    private final zzc<O> vb;

    public zzqd(zzc<O> com_google_android_gms_common_api_zzc_O) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.vb = com_google_android_gms_common_api_zzc_O;
    }

    public Looper getLooper() {
        return this.vb.getLooper();
    }

    public void zza(zzqx com_google_android_gms_internal_zzqx) {
        this.vb.zzanx();
    }

    public void zzb(zzqx com_google_android_gms_internal_zzqx) {
        this.vb.zzany();
    }

    public <A extends zzb, R extends Result, T extends zza<R, A>> T zzc(@NonNull T t) {
        return this.vb.zza((zza) t);
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzd(@NonNull T t) {
        return this.vb.zzb((zza) t);
    }
}
