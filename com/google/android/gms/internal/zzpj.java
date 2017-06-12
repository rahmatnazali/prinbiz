package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.internal.zzaa;

public final class zzpj<O extends ApiOptions> {
    private final Api<O> pN;
    private final O rP;

    public zzpj(Api<O> api, O o) {
        this.pN = api;
        this.rP = o;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzpj)) {
            return false;
        }
        zzpj com_google_android_gms_internal_zzpj = (zzpj) obj;
        return zzaa.equal(this.pN, com_google_android_gms_internal_zzpj.pN) && zzaa.equal(this.rP, com_google_android_gms_internal_zzpj.rP);
    }

    public int hashCode() {
        return zzaa.hashCode(this.pN, this.rP);
    }

    public zzc<?> zzans() {
        return this.pN.zzans();
    }

    public String zzaon() {
        return this.pN.getName();
    }
}
