package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzapv {
    protected volatile int bjG;

    public zzapv() {
        this.bjG = -1;
    }

    public static final <T extends zzapv> T zza(T t, byte[] bArr) throws zzapu {
        return zzb(t, bArr, 0, bArr.length);
    }

    public static final void zza(zzapv com_google_android_gms_internal_zzapv, byte[] bArr, int i, int i2) {
        try {
            zzapo zzc = zzapo.zzc(bArr, i, i2);
            com_google_android_gms_internal_zzapv.zza(zzc);
            zzc.az();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends zzapv> T zzb(T t, byte[] bArr, int i, int i2) throws zzapu {
        try {
            zzapn zzb = zzapn.zzb(bArr, i, i2);
            t.zzb(zzb);
            zzb.zzafo(0);
            return t;
        } catch (zzapu e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final byte[] zzf(zzapv com_google_android_gms_internal_zzapv) {
        byte[] bArr = new byte[com_google_android_gms_internal_zzapv.aM()];
        zza(com_google_android_gms_internal_zzapv, bArr, 0, bArr.length);
        return bArr;
    }

    public zzapv aB() throws CloneNotSupportedException {
        return (zzapv) super.clone();
    }

    public int aL() {
        if (this.bjG < 0) {
            aM();
        }
        return this.bjG;
    }

    public int aM() {
        int zzx = zzx();
        this.bjG = zzx;
        return zzx;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return aB();
    }

    public String toString() {
        return zzapw.zzg(this);
    }

    public void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
    }

    public abstract zzapv zzb(zzapn com_google_android_gms_internal_zzapn) throws IOException;

    protected int zzx() {
        return 0;
    }
}
