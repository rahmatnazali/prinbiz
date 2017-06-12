package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzanq implements zzani, Cloneable {
    public static final zzanq beK;
    private double beL;
    private int beM;
    private boolean beN;
    private List<zzaml> beO;
    private List<zzaml> beP;

    /* renamed from: com.google.android.gms.internal.zzanq.1 */
    class C06821 extends zzanh<T> {
        private zzanh<T> bdZ;
        final /* synthetic */ boolean beQ;
        final /* synthetic */ boolean beR;
        final /* synthetic */ zzamp beS;
        final /* synthetic */ zzaol beT;
        final /* synthetic */ zzanq beU;

        C06821(zzanq com_google_android_gms_internal_zzanq, boolean z, boolean z2, zzamp com_google_android_gms_internal_zzamp, zzaol com_google_android_gms_internal_zzaol) {
            this.beU = com_google_android_gms_internal_zzanq;
            this.beQ = z;
            this.beR = z2;
            this.beS = com_google_android_gms_internal_zzamp;
            this.beT = com_google_android_gms_internal_zzaol;
        }

        private zzanh<T> zzczr() {
            zzanh<T> com_google_android_gms_internal_zzanh_T = this.bdZ;
            if (com_google_android_gms_internal_zzanh_T != null) {
                return com_google_android_gms_internal_zzanh_T;
            }
            com_google_android_gms_internal_zzanh_T = this.beS.zza(this.beU, this.beT);
            this.bdZ = com_google_android_gms_internal_zzanh_T;
            return com_google_android_gms_internal_zzanh_T;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
            if (this.beR) {
                com_google_android_gms_internal_zzaoo.m379l();
            } else {
                zzczr().zza(com_google_android_gms_internal_zzaoo, t);
            }
        }

        public T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (!this.beQ) {
                return zzczr().zzb(com_google_android_gms_internal_zzaom);
            }
            com_google_android_gms_internal_zzaom.skipValue();
            return null;
        }
    }

    static {
        beK = new zzanq();
    }

    public zzanq() {
        this.beL = -1.0d;
        this.beM = 136;
        this.beN = true;
        this.beO = Collections.emptyList();
        this.beP = Collections.emptyList();
    }

    private boolean zza(zzanl com_google_android_gms_internal_zzanl) {
        return com_google_android_gms_internal_zzanl == null || com_google_android_gms_internal_zzanl.zzczt() <= this.beL;
    }

    private boolean zza(zzanl com_google_android_gms_internal_zzanl, zzanm com_google_android_gms_internal_zzanm) {
        return zza(com_google_android_gms_internal_zzanl) && zza(com_google_android_gms_internal_zzanm);
    }

    private boolean zza(zzanm com_google_android_gms_internal_zzanm) {
        return com_google_android_gms_internal_zzanm == null || com_google_android_gms_internal_zzanm.zzczt() > this.beL;
    }

    private boolean zzm(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean zzn(Class<?> cls) {
        return cls.isMemberClass() && !zzo(cls);
    }

    private boolean zzo(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    protected /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzczv();
    }

    public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Class m = com_google_android_gms_internal_zzaol_T.m359m();
        boolean zza = zza(m, true);
        boolean zza2 = zza(m, false);
        return (zza || zza2) ? new C06821(this, zza2, zza, com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzaol_T) : null;
    }

    public zzanq zza(zzaml com_google_android_gms_internal_zzaml, boolean z, boolean z2) {
        zzanq zzczv = zzczv();
        if (z) {
            zzczv.beO = new ArrayList(this.beO);
            zzczv.beO.add(com_google_android_gms_internal_zzaml);
        }
        if (z2) {
            zzczv.beP = new ArrayList(this.beP);
            zzczv.beP.add(com_google_android_gms_internal_zzaml);
        }
        return zzczv;
    }

    public boolean zza(Class<?> cls, boolean z) {
        if (this.beL != -1.0d && !zza((zzanl) cls.getAnnotation(zzanl.class), (zzanm) cls.getAnnotation(zzanm.class))) {
            return true;
        }
        if (!this.beN && zzn(cls)) {
            return true;
        }
        if (zzm(cls)) {
            return true;
        }
        for (zzaml zzh : z ? this.beO : this.beP) {
            if (zzh.zzh(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(Field field, boolean z) {
        if ((this.beM & field.getModifiers()) != 0) {
            return true;
        }
        if (this.beL != -1.0d && !zza((zzanl) field.getAnnotation(zzanl.class), (zzanm) field.getAnnotation(zzanm.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (!this.beN && zzn(field.getType())) {
            return true;
        }
        if (zzm(field.getType())) {
            return true;
        }
        List<zzaml> list = z ? this.beO : this.beP;
        if (!list.isEmpty()) {
            zzamm com_google_android_gms_internal_zzamm = new zzamm(field);
            for (zzaml zza : list) {
                if (zza.zza(com_google_android_gms_internal_zzamm)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected zzanq zzczv() {
        try {
            return (zzanq) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public zzanq zzg(int... iArr) {
        int i = 0;
        zzanq zzczv = zzczv();
        zzczv.beM = 0;
        int length = iArr.length;
        while (i < length) {
            zzczv.beM = iArr[i] | zzczv.beM;
            i++;
        }
        return zzczv;
    }
}
