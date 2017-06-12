package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;

public class zzpp implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> pN;
    private final int tf;
    private zzqa tg;

    public zzpp(Api<?> api, int i) {
        this.pN = api;
        this.tf = i;
    }

    private void zzapa() {
        zzab.zzb(this.tg, (Object) "Callbacks must be attached to a GoogleApiClient instance before connecting the client.");
    }

    public void onConnected(@Nullable Bundle bundle) {
        zzapa();
        this.tg.onConnected(bundle);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzapa();
        this.tg.zza(connectionResult, this.pN, this.tf);
    }

    public void onConnectionSuspended(int i) {
        zzapa();
        this.tg.onConnectionSuspended(i);
    }

    public void zza(zzqa com_google_android_gms_internal_zzqa) {
        this.tg = com_google_android_gms_internal_zzqa;
    }
}
