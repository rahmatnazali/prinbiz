package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class zzanz implements zzani {
    private final zzanp bdR;

    private static final class zza<E> extends zzanh<Collection<E>> {
        private final zzanh<E> bfx;
        private final zzanu<? extends Collection<E>> bfy;

        public zza(zzamp com_google_android_gms_internal_zzamp, Type type, zzanh<E> com_google_android_gms_internal_zzanh_E, zzanu<? extends Collection<E>> com_google_android_gms_internal_zzanu__extends_java_util_Collection_E) {
            this.bfx = new zzaoj(com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzanh_E, type);
            this.bfy = com_google_android_gms_internal_zzanu__extends_java_util_Collection_E;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Collection<E> collection) throws IOException {
            if (collection == null) {
                com_google_android_gms_internal_zzaoo.m379l();
                return;
            }
            com_google_android_gms_internal_zzaoo.m375h();
            for (E zza : collection) {
                this.bfx.zza(com_google_android_gms_internal_zzaoo, zza);
            }
            com_google_android_gms_internal_zzaoo.m376i();
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzj(com_google_android_gms_internal_zzaom);
        }

        public Collection<E> zzj(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            Collection<E> collection = (Collection) this.bfy.zzczu();
            com_google_android_gms_internal_zzaom.beginArray();
            while (com_google_android_gms_internal_zzaom.hasNext()) {
                collection.add(this.bfx.zzb(com_google_android_gms_internal_zzaom));
            }
            com_google_android_gms_internal_zzaom.endArray();
            return collection;
        }
    }

    public zzanz(zzanp com_google_android_gms_internal_zzanp) {
        this.bdR = com_google_android_gms_internal_zzanp;
    }

    public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Type n = com_google_android_gms_internal_zzaol_T.m360n();
        Class m = com_google_android_gms_internal_zzaol_T.m359m();
        if (!Collection.class.isAssignableFrom(m)) {
            return null;
        }
        Type zza = zzano.zza(n, m);
        return new zza(com_google_android_gms_internal_zzamp, zza, com_google_android_gms_internal_zzamp.zza(zzaol.zzl(zza)), this.bdR.zzb(com_google_android_gms_internal_zzaol_T));
    }
}
