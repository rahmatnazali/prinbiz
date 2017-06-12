package com.google.android.gms.gcm;

import android.os.Bundle;
import javax.jmdns.impl.constants.DNSConstants;

public class zzc {
    public static final zzc ZC;
    public static final zzc ZD;
    private final int ZE;
    private final int ZF;
    private final int ZG;

    static {
        ZC = new zzc(0, 30, DNSConstants.DNS_TTL);
        ZD = new zzc(1, 30, DNSConstants.DNS_TTL);
    }

    private zzc(int i, int i2, int i3) {
        this.ZE = i;
        this.ZF = i2;
        this.ZG = i3;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc com_google_android_gms_gcm_zzc = (zzc) obj;
        return com_google_android_gms_gcm_zzc.ZE == this.ZE && com_google_android_gms_gcm_zzc.ZF == this.ZF && com_google_android_gms_gcm_zzc.ZG == this.ZG;
    }

    public int hashCode() {
        return (((((this.ZE + 1) ^ 1000003) * 1000003) ^ this.ZF) * 1000003) ^ this.ZG;
    }

    public String toString() {
        int i = this.ZE;
        int i2 = this.ZF;
        return "policy=" + i + " initial_backoff=" + i2 + " maximum_backoff=" + this.ZG;
    }

    public Bundle zzai(Bundle bundle) {
        bundle.putInt("retry_policy", this.ZE);
        bundle.putInt("initial_backoff_seconds", this.ZF);
        bundle.putInt("maximum_backoff_seconds", this.ZG);
        return bundle;
    }

    public int zzblj() {
        return this.ZE;
    }

    public int zzblk() {
        return this.ZF;
    }

    public int zzbll() {
        return this.ZG;
    }
}
