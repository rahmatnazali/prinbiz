package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaog.zza;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class zzaoj<T> extends zzanh<T> {
    private final zzanh<T> bdZ;
    private final zzamp bfV;
    private final Type bfW;

    zzaoj(zzamp com_google_android_gms_internal_zzamp, zzanh<T> com_google_android_gms_internal_zzanh_T, Type type) {
        this.bfV = com_google_android_gms_internal_zzamp;
        this.bdZ = com_google_android_gms_internal_zzanh_T;
        this.bfW = type;
    }

    private Type zzb(Type type, Object obj) {
        return obj != null ? (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type : type;
    }

    public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
        zzanh com_google_android_gms_internal_zzanh = this.bdZ;
        Type zzb = zzb(this.bfW, t);
        if (zzb != this.bfW) {
            com_google_android_gms_internal_zzanh = this.bfV.zza(zzaol.zzl(zzb));
            if ((com_google_android_gms_internal_zzanh instanceof zza) && !(this.bdZ instanceof zza)) {
                com_google_android_gms_internal_zzanh = this.bdZ;
            }
        }
        com_google_android_gms_internal_zzanh.zza(com_google_android_gms_internal_zzaoo, t);
    }

    public T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        return this.bdZ.zzb(com_google_android_gms_internal_zzaom);
    }
}
