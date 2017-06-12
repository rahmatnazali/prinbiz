package com.google.android.gms.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class zzaol<T> {
    final Type bfW;
    final Class<? super T> bha;
    final int bhb;

    protected zzaol() {
        this.bfW = zzq(getClass());
        this.bha = zzano.zzf(this.bfW);
        this.bhb = this.bfW.hashCode();
    }

    zzaol(Type type) {
        this.bfW = zzano.zze((Type) zzann.zzy(type));
        this.bha = zzano.zzf(this.bfW);
        this.bhb = this.bfW.hashCode();
    }

    public static zzaol<?> zzl(Type type) {
        return new zzaol(type);
    }

    static Type zzq(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return zzano.zze(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public static <T> zzaol<T> zzr(Class<T> cls) {
        return new zzaol(cls);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzaol) && zzano.zza(this.bfW, ((zzaol) obj).bfW);
    }

    public final int hashCode() {
        return this.bhb;
    }

    public final Class<? super T> m359m() {
        return this.bha;
    }

    public final Type m360n() {
        return this.bfW;
    }

    public final String toString() {
        return zzano.zzg(this.bfW);
    }
}
