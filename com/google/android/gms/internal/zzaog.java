package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class zzaog implements zzani {
    private final zzanp bdR;
    private final zzanq bea;
    private final zzamo bec;

    static abstract class zzb {
        final boolean bfS;
        final boolean bfT;
        final String name;

        protected zzb(String str, boolean z, boolean z2) {
            this.name = str;
            this.bfS = z;
            this.bfT = z2;
        }

        abstract void zza(zzaom com_google_android_gms_internal_zzaom, Object obj) throws IOException, IllegalAccessException;

        abstract void zza(zzaoo com_google_android_gms_internal_zzaoo, Object obj) throws IOException, IllegalAccessException;

        abstract boolean zzco(Object obj) throws IOException, IllegalAccessException;
    }

    /* renamed from: com.google.android.gms.internal.zzaog.1 */
    class C06921 extends zzb {
        final zzanh<?> bfL;
        final /* synthetic */ zzamp bfM;
        final /* synthetic */ Field bfN;
        final /* synthetic */ zzaol bfO;
        final /* synthetic */ boolean bfP;
        final /* synthetic */ zzaog bfQ;

        C06921(zzaog com_google_android_gms_internal_zzaog, String str, boolean z, boolean z2, zzamp com_google_android_gms_internal_zzamp, Field field, zzaol com_google_android_gms_internal_zzaol, boolean z3) {
            this.bfQ = com_google_android_gms_internal_zzaog;
            this.bfM = com_google_android_gms_internal_zzamp;
            this.bfN = field;
            this.bfO = com_google_android_gms_internal_zzaol;
            this.bfP = z3;
            super(str, z, z2);
            this.bfL = this.bfQ.zza(this.bfM, this.bfN, this.bfO);
        }

        void zza(zzaom com_google_android_gms_internal_zzaom, Object obj) throws IOException, IllegalAccessException {
            Object zzb = this.bfL.zzb(com_google_android_gms_internal_zzaom);
            if (zzb != null || !this.bfP) {
                this.bfN.set(obj, zzb);
            }
        }

        void zza(zzaoo com_google_android_gms_internal_zzaoo, Object obj) throws IOException, IllegalAccessException {
            new zzaoj(this.bfM, this.bfL, this.bfO.m360n()).zza(com_google_android_gms_internal_zzaoo, this.bfN.get(obj));
        }

        public boolean zzco(Object obj) throws IOException, IllegalAccessException {
            return this.bfS && this.bfN.get(obj) != obj;
        }
    }

    public static final class zza<T> extends zzanh<T> {
        private final Map<String, zzb> bfR;
        private final zzanu<T> bfy;

        private zza(zzanu<T> com_google_android_gms_internal_zzanu_T, Map<String, zzb> map) {
            this.bfy = com_google_android_gms_internal_zzanu_T;
            this.bfR = map;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
            if (t == null) {
                com_google_android_gms_internal_zzaoo.m379l();
                return;
            }
            com_google_android_gms_internal_zzaoo.m377j();
            try {
                for (zzb com_google_android_gms_internal_zzaog_zzb : this.bfR.values()) {
                    if (com_google_android_gms_internal_zzaog_zzb.zzco(t)) {
                        com_google_android_gms_internal_zzaoo.zztr(com_google_android_gms_internal_zzaog_zzb.name);
                        com_google_android_gms_internal_zzaog_zzb.zza(com_google_android_gms_internal_zzaoo, (Object) t);
                    }
                }
                com_google_android_gms_internal_zzaoo.m378k();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }

        public T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            T zzczu = this.bfy.zzczu();
            try {
                com_google_android_gms_internal_zzaom.beginObject();
                while (com_google_android_gms_internal_zzaom.hasNext()) {
                    zzb com_google_android_gms_internal_zzaog_zzb = (zzb) this.bfR.get(com_google_android_gms_internal_zzaom.nextName());
                    if (com_google_android_gms_internal_zzaog_zzb == null || !com_google_android_gms_internal_zzaog_zzb.bfT) {
                        com_google_android_gms_internal_zzaom.skipValue();
                    } else {
                        com_google_android_gms_internal_zzaog_zzb.zza(com_google_android_gms_internal_zzaom, (Object) zzczu);
                    }
                }
                com_google_android_gms_internal_zzaom.endObject();
                return zzczu;
            } catch (Throwable e) {
                throw new zzane(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    public zzaog(zzanp com_google_android_gms_internal_zzanp, zzamo com_google_android_gms_internal_zzamo, zzanq com_google_android_gms_internal_zzanq) {
        this.bdR = com_google_android_gms_internal_zzanp;
        this.bec = com_google_android_gms_internal_zzamo;
        this.bea = com_google_android_gms_internal_zzanq;
    }

    private zzanh<?> zza(zzamp com_google_android_gms_internal_zzamp, Field field, zzaol<?> com_google_android_gms_internal_zzaol_) {
        zzanj com_google_android_gms_internal_zzanj = (zzanj) field.getAnnotation(zzanj.class);
        if (com_google_android_gms_internal_zzanj != null) {
            zzanh<?> zza = zzaob.zza(this.bdR, com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzaol_, com_google_android_gms_internal_zzanj);
            if (zza != null) {
                return zza;
            }
        }
        return com_google_android_gms_internal_zzamp.zza((zzaol) com_google_android_gms_internal_zzaol_);
    }

    private zzb zza(zzamp com_google_android_gms_internal_zzamp, Field field, String str, zzaol<?> com_google_android_gms_internal_zzaol_, boolean z, boolean z2) {
        return new C06921(this, str, z, z2, com_google_android_gms_internal_zzamp, field, com_google_android_gms_internal_zzaol_, zzanv.zzk(com_google_android_gms_internal_zzaol_.m359m()));
    }

    static List<String> zza(zzamo com_google_android_gms_internal_zzamo, Field field) {
        zzank com_google_android_gms_internal_zzank = (zzank) field.getAnnotation(zzank.class);
        List<String> linkedList = new LinkedList();
        if (com_google_android_gms_internal_zzank == null) {
            linkedList.add(com_google_android_gms_internal_zzamo.zzc(field));
        } else {
            linkedList.add(com_google_android_gms_internal_zzank.value());
            for (Object add : com_google_android_gms_internal_zzank.zzczs()) {
                linkedList.add(add);
            }
        }
        return linkedList;
    }

    private Map<String, zzb> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<?> com_google_android_gms_internal_zzaol_, Class<?> cls) {
        Map<String, zzb> linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type n = com_google_android_gms_internal_zzaol_.m360n();
        Class m;
        while (m != Object.class) {
            for (Field field : m.getDeclaredFields()) {
                boolean zza = zza(field, true);
                boolean zza2 = zza(field, false);
                if (zza || zza2) {
                    field.setAccessible(true);
                    Type zza3 = zzano.zza(r19.m360n(), m, field.getGenericType());
                    List zzd = zzd(field);
                    zzb com_google_android_gms_internal_zzaog_zzb = null;
                    int i = 0;
                    while (i < zzd.size()) {
                        String str = (String) zzd.get(i);
                        if (i != 0) {
                            zza = false;
                        }
                        zzb com_google_android_gms_internal_zzaog_zzb2 = (zzb) linkedHashMap.put(str, zza(com_google_android_gms_internal_zzamp, field, str, zzaol.zzl(zza3), zza, zza2));
                        if (com_google_android_gms_internal_zzaog_zzb != null) {
                            com_google_android_gms_internal_zzaog_zzb2 = com_google_android_gms_internal_zzaog_zzb;
                        }
                        i++;
                        com_google_android_gms_internal_zzaog_zzb = com_google_android_gms_internal_zzaog_zzb2;
                    }
                    if (com_google_android_gms_internal_zzaog_zzb != null) {
                        String valueOf = String.valueOf(n);
                        String str2 = com_google_android_gms_internal_zzaog_zzb.name;
                        throw new IllegalArgumentException(new StringBuilder((String.valueOf(valueOf).length() + 37) + String.valueOf(str2).length()).append(valueOf).append(" declares multiple JSON fields named ").append(str2).toString());
                    }
                }
            }
            zzaol zzl = zzaol.zzl(zzano.zza(zzl.m360n(), m, m.getGenericSuperclass()));
            m = zzl.m359m();
        }
        return linkedHashMap;
    }

    static boolean zza(Field field, boolean z, zzanq com_google_android_gms_internal_zzanq) {
        return (com_google_android_gms_internal_zzanq.zza(field.getType(), z) || com_google_android_gms_internal_zzanq.zza(field, z)) ? false : true;
    }

    private List<String> zzd(Field field) {
        return zza(this.bec, field);
    }

    public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Class m = com_google_android_gms_internal_zzaol_T.m359m();
        return !Object.class.isAssignableFrom(m) ? null : new zza(zza(com_google_android_gms_internal_zzamp, (zzaol) com_google_android_gms_internal_zzaol_T, m), null);
    }

    public boolean zza(Field field, boolean z) {
        return zza(field, z, this.bea);
    }
}
