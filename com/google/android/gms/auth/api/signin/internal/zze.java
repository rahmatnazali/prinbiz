package com.google.android.gms.auth.api.signin.internal;

public class zze {
    static int ef;
    private int eg;

    static {
        ef = 31;
    }

    public zze() {
        this.eg = 1;
    }

    public int zzagc() {
        return this.eg;
    }

    public zze zzba(boolean z) {
        this.eg = (z ? 1 : 0) + (this.eg * ef);
        return this;
    }

    public zze zzq(Object obj) {
        this.eg = (obj == null ? 0 : obj.hashCode()) + (this.eg * ef);
        return this;
    }
}
