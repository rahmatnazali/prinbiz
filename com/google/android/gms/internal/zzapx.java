package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

final class zzapx {
    final byte[] apf;
    final int tag;

    zzapx(int i, byte[] bArr) {
        this.tag = i;
        this.apf = bArr;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzapx)) {
            return false;
        }
        zzapx com_google_android_gms_internal_zzapx = (zzapx) obj;
        return this.tag == com_google_android_gms_internal_zzapx.tag && Arrays.equals(this.apf, com_google_android_gms_internal_zzapx.apf);
    }

    public int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.apf);
    }

    void zza(zzapo com_google_android_gms_internal_zzapo) throws IOException {
        com_google_android_gms_internal_zzapo.zzagb(this.tag);
        com_google_android_gms_internal_zzapo.zzbh(this.apf);
    }

    int zzx() {
        return (zzapo.zzagc(this.tag) + 0) + this.apf.length;
    }
}
