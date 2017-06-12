package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzapp<M extends zzapp<M>> extends zzapv {
    protected zzapr bjx;

    public M aA() throws CloneNotSupportedException {
        zzapp com_google_android_gms_internal_zzapp = (zzapp) super.aB();
        zzapt.zza(this, com_google_android_gms_internal_zzapp);
        return com_google_android_gms_internal_zzapp;
    }

    public /* synthetic */ zzapv aB() throws CloneNotSupportedException {
        return (zzapp) clone();
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return aA();
    }

    public final <T> T zza(zzapq<M, T> com_google_android_gms_internal_zzapq_M__T) {
        if (this.bjx == null) {
            return null;
        }
        zzaps zzagf = this.bjx.zzagf(zzapy.zzagj(com_google_android_gms_internal_zzapq_M__T.tag));
        return zzagf != null ? zzagf.zzb(com_google_android_gms_internal_zzapq_M__T) : null;
    }

    public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
        if (this.bjx != null) {
            for (int i = 0; i < this.bjx.size(); i++) {
                this.bjx.zzagg(i).zza(com_google_android_gms_internal_zzapo);
            }
        }
    }

    protected final boolean zza(zzapn com_google_android_gms_internal_zzapn, int i) throws IOException {
        int position = com_google_android_gms_internal_zzapn.getPosition();
        if (!com_google_android_gms_internal_zzapn.zzafp(i)) {
            return false;
        }
        int zzagj = zzapy.zzagj(i);
        zzapx com_google_android_gms_internal_zzapx = new zzapx(i, com_google_android_gms_internal_zzapn.zzad(position, com_google_android_gms_internal_zzapn.getPosition() - position));
        zzaps com_google_android_gms_internal_zzaps = null;
        if (this.bjx == null) {
            this.bjx = new zzapr();
        } else {
            com_google_android_gms_internal_zzaps = this.bjx.zzagf(zzagj);
        }
        if (com_google_android_gms_internal_zzaps == null) {
            com_google_android_gms_internal_zzaps = new zzaps();
            this.bjx.zza(zzagj, com_google_android_gms_internal_zzaps);
        }
        com_google_android_gms_internal_zzaps.zza(com_google_android_gms_internal_zzapx);
        return true;
    }

    protected int zzx() {
        int i = 0;
        if (this.bjx == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.bjx.size()) {
            i2 += this.bjx.zzagg(i).zzx();
            i++;
        }
        return i2;
    }
}
