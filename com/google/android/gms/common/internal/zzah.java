package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzg;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class zzah<T extends IInterface> extends zzk<T> {
    private final zzg<T> zn;

    public zzah(Context context, Looper looper, int i, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzg com_google_android_gms_common_internal_zzg, zzg<T> com_google_android_gms_common_api_Api_zzg_T) {
        super(context, looper, i, com_google_android_gms_common_internal_zzg, connectionCallbacks, onConnectionFailedListener);
        this.zn = com_google_android_gms_common_api_Api_zzg_T;
    }

    public zzg<T> zzatn() {
        return this.zn;
    }

    protected T zzbb(IBinder iBinder) {
        return this.zn.zzbb(iBinder);
    }

    protected void zzc(int i, T t) {
        this.zn.zza(i, t);
    }

    protected String zzqz() {
        return this.zn.zzqz();
    }

    protected String zzra() {
        return this.zn.zzra();
    }
}
