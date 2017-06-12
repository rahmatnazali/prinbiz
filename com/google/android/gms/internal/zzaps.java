package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class zzaps implements Cloneable {
    private zzapq<?, ?> bjD;
    private List<zzapx> bjE;
    private Object value;

    zzaps() {
        this.bjE = new ArrayList();
    }

    private byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzx()];
        zza(zzapo.zzbe(bArr));
        return bArr;
    }

    public final zzaps aD() {
        zzaps com_google_android_gms_internal_zzaps = new zzaps();
        try {
            com_google_android_gms_internal_zzaps.bjD = this.bjD;
            if (this.bjE == null) {
                com_google_android_gms_internal_zzaps.bjE = null;
            } else {
                com_google_android_gms_internal_zzaps.bjE.addAll(this.bjE);
            }
            if (this.value != null) {
                if (this.value instanceof zzapv) {
                    com_google_android_gms_internal_zzaps.value = (zzapv) ((zzapv) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    com_google_android_gms_internal_zzaps.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    r4 = new byte[bArr.length][];
                    com_google_android_gms_internal_zzaps.value = r4;
                    for (r2 = 0; r2 < bArr.length; r2++) {
                        r4[r2] = (byte[]) bArr[r2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    com_google_android_gms_internal_zzaps.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    com_google_android_gms_internal_zzaps.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    com_google_android_gms_internal_zzaps.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    com_google_android_gms_internal_zzaps.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    com_google_android_gms_internal_zzaps.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzapv[]) {
                    zzapv[] com_google_android_gms_internal_zzapvArr = (zzapv[]) this.value;
                    r4 = new zzapv[com_google_android_gms_internal_zzapvArr.length];
                    com_google_android_gms_internal_zzaps.value = r4;
                    for (r2 = 0; r2 < com_google_android_gms_internal_zzapvArr.length; r2++) {
                        r4[r2] = (zzapv) com_google_android_gms_internal_zzapvArr[r2].clone();
                    }
                }
            }
            return com_google_android_gms_internal_zzaps;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return aD();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzaps)) {
            return false;
        }
        zzaps com_google_android_gms_internal_zzaps = (zzaps) obj;
        if (this.value != null && com_google_android_gms_internal_zzaps.value != null) {
            return this.bjD == com_google_android_gms_internal_zzaps.bjD ? !this.bjD.baj.isArray() ? this.value.equals(com_google_android_gms_internal_zzaps.value) : this.value instanceof byte[] ? Arrays.equals((byte[]) this.value, (byte[]) com_google_android_gms_internal_zzaps.value) : this.value instanceof int[] ? Arrays.equals((int[]) this.value, (int[]) com_google_android_gms_internal_zzaps.value) : this.value instanceof long[] ? Arrays.equals((long[]) this.value, (long[]) com_google_android_gms_internal_zzaps.value) : this.value instanceof float[] ? Arrays.equals((float[]) this.value, (float[]) com_google_android_gms_internal_zzaps.value) : this.value instanceof double[] ? Arrays.equals((double[]) this.value, (double[]) com_google_android_gms_internal_zzaps.value) : this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) com_google_android_gms_internal_zzaps.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) com_google_android_gms_internal_zzaps.value) : false;
        } else {
            if (this.bjE != null && com_google_android_gms_internal_zzaps.bjE != null) {
                return this.bjE.equals(com_google_android_gms_internal_zzaps.bjE);
            }
            try {
                return Arrays.equals(toByteArray(), com_google_android_gms_internal_zzaps.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
        if (this.value != null) {
            this.bjD.zza(this.value, com_google_android_gms_internal_zzapo);
            return;
        }
        for (zzapx zza : this.bjE) {
            zza.zza(com_google_android_gms_internal_zzapo);
        }
    }

    void zza(zzapx com_google_android_gms_internal_zzapx) {
        this.bjE.add(com_google_android_gms_internal_zzapx);
    }

    <T> T zzb(zzapq<?, T> com_google_android_gms_internal_zzapq___T) {
        if (this.value == null) {
            this.bjD = com_google_android_gms_internal_zzapq___T;
            this.value = com_google_android_gms_internal_zzapq___T.zzav(this.bjE);
            this.bjE = null;
        } else if (!this.bjD.equals(com_google_android_gms_internal_zzapq___T)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }

    int zzx() {
        if (this.value != null) {
            return this.bjD.zzcp(this.value);
        }
        int i = 0;
        for (zzapx zzx : this.bjE) {
            i = zzx.zzx() + i;
        }
        return i;
    }
}
