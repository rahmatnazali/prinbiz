package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

abstract class zzrh<R extends Result> extends com.google.android.gms.internal.zzpm.zza<R, zzri> {

    static abstract class zza extends zzrh<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public Status zzb(Status status) {
            return status;
        }

        public /* synthetic */ Result zzc(Status status) {
            return zzb(status);
        }
    }

    public zzrh(GoogleApiClient googleApiClient) {
        super(zzre.API, googleApiClient);
    }
}
