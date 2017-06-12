package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzpj;

public class zza extends zzb {
    private final ConnectionResult rF;

    public zza(Status status, ArrayMap<zzpj<?>, ConnectionResult> arrayMap) {
        super(status, arrayMap);
        this.rF = (ConnectionResult) arrayMap.get(arrayMap.keyAt(0));
    }
}
