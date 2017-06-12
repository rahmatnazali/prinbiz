package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzpj;

public class zzb implements Result {
    private final Status bY;
    private final ArrayMap<zzpj<?>, ConnectionResult> rG;

    public zzb(Status status, ArrayMap<zzpj<?>, ConnectionResult> arrayMap) {
        this.bY = status;
        this.rG = arrayMap;
    }

    public Status getStatus() {
        return this.bY;
    }
}
