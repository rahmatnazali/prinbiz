package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class zzany<E> extends zzanh<Object> {
    public static final zzani bfu;
    private final Class<E> bfv;
    private final zzanh<E> bfw;

    /* renamed from: com.google.android.gms.internal.zzany.1 */
    static class C06891 implements zzani {
        C06891() {
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            Type n = com_google_android_gms_internal_zzaol_T.m360n();
            if (!(n instanceof GenericArrayType) && (!(n instanceof Class) || !((Class) n).isArray())) {
                return null;
            }
            n = zzano.zzh(n);
            return new zzany(com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzamp.zza(zzaol.zzl(n)), zzano.zzf(n));
        }
    }

    static {
        bfu = new C06891();
    }

    public zzany(zzamp com_google_android_gms_internal_zzamp, zzanh<E> com_google_android_gms_internal_zzanh_E, Class<E> cls) {
        this.bfw = new zzaoj(com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzanh_E, cls);
        this.bfv = cls;
    }

    public void zza(zzaoo com_google_android_gms_internal_zzaoo, Object obj) throws IOException {
        if (obj == null) {
            com_google_android_gms_internal_zzaoo.m379l();
            return;
        }
        com_google_android_gms_internal_zzaoo.m375h();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.bfw.zza(com_google_android_gms_internal_zzaoo, Array.get(obj, i));
        }
        com_google_android_gms_internal_zzaoo.m376i();
    }

    public Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
        if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
        List arrayList = new ArrayList();
        com_google_android_gms_internal_zzaom.beginArray();
        while (com_google_android_gms_internal_zzaom.hasNext()) {
            arrayList.add(this.bfw.zzb(com_google_android_gms_internal_zzaom));
        }
        com_google_android_gms_internal_zzaom.endArray();
        Object newInstance = Array.newInstance(this.bfv, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }
}
