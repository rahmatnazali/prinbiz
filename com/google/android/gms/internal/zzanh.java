package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzanh<T> {
    public abstract void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException;

    public abstract T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException;

    public final zzamv zzcj(T t) {
        try {
            zzaoo com_google_android_gms_internal_zzaod = new zzaod();
            zza(com_google_android_gms_internal_zzaod, t);
            return com_google_android_gms_internal_zzaod.m681f();
        } catch (Throwable e) {
            throw new zzamw(e);
        }
    }
}
