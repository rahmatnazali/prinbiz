package com.google.android.gms.internal;

import java.io.IOException;

final class zzang<T> extends zzanh<T> {
    private zzanh<T> bdZ;
    private final zzand<T> beo;
    private final zzamu<T> bep;
    private final zzamp beq;
    private final zzaol<T> ber;
    private final zzani bes;

    private static class zza implements zzani {
        private final zzand<?> beo;
        private final zzamu<?> bep;
        private final zzaol<?> bet;
        private final boolean beu;
        private final Class<?> bev;

        private zza(Object obj, zzaol<?> com_google_android_gms_internal_zzaol_, boolean z, Class<?> cls) {
            this.beo = obj instanceof zzand ? (zzand) obj : null;
            this.bep = obj instanceof zzamu ? (zzamu) obj : null;
            boolean z2 = (this.beo == null && this.bep == null) ? false : true;
            zzann.zzbo(z2);
            this.bet = com_google_android_gms_internal_zzaol_;
            this.beu = z;
            this.bev = cls;
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            boolean isAssignableFrom = this.bet != null ? this.bet.equals(com_google_android_gms_internal_zzaol_T) || (this.beu && this.bet.m360n() == com_google_android_gms_internal_zzaol_T.m359m()) : this.bev.isAssignableFrom(com_google_android_gms_internal_zzaol_T.m359m());
            return isAssignableFrom ? new zzang(this.bep, com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzaol_T, this, null) : null;
        }
    }

    private zzang(zzand<T> com_google_android_gms_internal_zzand_T, zzamu<T> com_google_android_gms_internal_zzamu_T, zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T, zzani com_google_android_gms_internal_zzani) {
        this.beo = com_google_android_gms_internal_zzand_T;
        this.bep = com_google_android_gms_internal_zzamu_T;
        this.beq = com_google_android_gms_internal_zzamp;
        this.ber = com_google_android_gms_internal_zzaol_T;
        this.bes = com_google_android_gms_internal_zzani;
    }

    public static zzani zza(zzaol<?> com_google_android_gms_internal_zzaol_, Object obj) {
        return new zza(com_google_android_gms_internal_zzaol_, false, null, null);
    }

    public static zzani zzb(zzaol<?> com_google_android_gms_internal_zzaol_, Object obj) {
        return new zza(com_google_android_gms_internal_zzaol_, com_google_android_gms_internal_zzaol_.m360n() == com_google_android_gms_internal_zzaol_.m359m(), null, null);
    }

    private zzanh<T> zzczr() {
        zzanh<T> com_google_android_gms_internal_zzanh_T = this.bdZ;
        if (com_google_android_gms_internal_zzanh_T != null) {
            return com_google_android_gms_internal_zzanh_T;
        }
        com_google_android_gms_internal_zzanh_T = this.beq.zza(this.bes, this.ber);
        this.bdZ = com_google_android_gms_internal_zzanh_T;
        return com_google_android_gms_internal_zzanh_T;
    }

    public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
        if (this.beo == null) {
            zzczr().zza(com_google_android_gms_internal_zzaoo, t);
        } else if (t == null) {
            com_google_android_gms_internal_zzaoo.m379l();
        } else {
            zzanw.zzb(this.beo.zza(t, this.ber.m360n(), this.beq.bdX), com_google_android_gms_internal_zzaoo);
        }
    }

    public T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        if (this.bep == null) {
            return zzczr().zzb(com_google_android_gms_internal_zzaom);
        }
        zzamv zzh = zzanw.zzh(com_google_android_gms_internal_zzaom);
        if (zzh.zzczj()) {
            return null;
        }
        try {
            return this.bep.zzb(zzh, this.ber.m360n(), this.beq.bdW);
        } catch (zzamz e) {
            throw e;
        } catch (Throwable e2) {
            throw new zzamz(e2);
        }
    }
}
