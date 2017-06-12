package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zzaoe implements zzani {
    private final zzanp bdR;
    private final boolean bfG;

    private final class zza<K, V> extends zzanh<Map<K, V>> {
        private final zzanh<K> bfH;
        private final zzanh<V> bfI;
        final /* synthetic */ zzaoe bfJ;
        private final zzanu<? extends Map<K, V>> bfy;

        public zza(zzaoe com_google_android_gms_internal_zzaoe, zzamp com_google_android_gms_internal_zzamp, Type type, zzanh<K> com_google_android_gms_internal_zzanh_K, Type type2, zzanh<V> com_google_android_gms_internal_zzanh_V, zzanu<? extends Map<K, V>> com_google_android_gms_internal_zzanu__extends_java_util_Map_K__V) {
            this.bfJ = com_google_android_gms_internal_zzaoe;
            this.bfH = new zzaoj(com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzanh_K, type);
            this.bfI = new zzaoj(com_google_android_gms_internal_zzamp, com_google_android_gms_internal_zzanh_V, type2);
            this.bfy = com_google_android_gms_internal_zzanu__extends_java_util_Map_K__V;
        }

        private String zze(zzamv com_google_android_gms_internal_zzamv) {
            if (com_google_android_gms_internal_zzamv.zzczi()) {
                zzanb zzczm = com_google_android_gms_internal_zzamv.zzczm();
                if (zzczm.zzczp()) {
                    return String.valueOf(zzczm.zzcze());
                }
                if (zzczm.zzczo()) {
                    return Boolean.toString(zzczm.getAsBoolean());
                }
                if (zzczm.zzczq()) {
                    return zzczm.zzczf();
                }
                throw new AssertionError();
            } else if (com_google_android_gms_internal_zzamv.zzczj()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Map<K, V> map) throws IOException {
            int i = 0;
            if (map == null) {
                com_google_android_gms_internal_zzaoo.m379l();
            } else if (this.bfJ.bfG) {
                List arrayList = new ArrayList(map.size());
                List arrayList2 = new ArrayList(map.size());
                int i2 = 0;
                for (Entry entry : map.entrySet()) {
                    zzamv zzcj = this.bfH.zzcj(entry.getKey());
                    arrayList.add(zzcj);
                    arrayList2.add(entry.getValue());
                    int i3 = (zzcj.zzczg() || zzcj.zzczh()) ? 1 : 0;
                    i2 = i3 | i2;
                }
                if (i2 != 0) {
                    com_google_android_gms_internal_zzaoo.m375h();
                    while (i < arrayList.size()) {
                        com_google_android_gms_internal_zzaoo.m375h();
                        zzanw.zzb((zzamv) arrayList.get(i), com_google_android_gms_internal_zzaoo);
                        this.bfI.zza(com_google_android_gms_internal_zzaoo, arrayList2.get(i));
                        com_google_android_gms_internal_zzaoo.m376i();
                        i++;
                    }
                    com_google_android_gms_internal_zzaoo.m376i();
                    return;
                }
                com_google_android_gms_internal_zzaoo.m377j();
                while (i < arrayList.size()) {
                    com_google_android_gms_internal_zzaoo.zztr(zze((zzamv) arrayList.get(i)));
                    this.bfI.zza(com_google_android_gms_internal_zzaoo, arrayList2.get(i));
                    i++;
                }
                com_google_android_gms_internal_zzaoo.m378k();
            } else {
                com_google_android_gms_internal_zzaoo.m377j();
                for (Entry entry2 : map.entrySet()) {
                    com_google_android_gms_internal_zzaoo.zztr(String.valueOf(entry2.getKey()));
                    this.bfI.zza(com_google_android_gms_internal_zzaoo, entry2.getValue());
                }
                com_google_android_gms_internal_zzaoo.m378k();
            }
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzl(com_google_android_gms_internal_zzaom);
        }

        public Map<K, V> zzl(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            zzaon b = com_google_android_gms_internal_zzaom.m370b();
            if (b == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            Map<K, V> map = (Map) this.bfy.zzczu();
            Object zzb;
            if (b == zzaon.BEGIN_ARRAY) {
                com_google_android_gms_internal_zzaom.beginArray();
                while (com_google_android_gms_internal_zzaom.hasNext()) {
                    com_google_android_gms_internal_zzaom.beginArray();
                    zzb = this.bfH.zzb(com_google_android_gms_internal_zzaom);
                    if (map.put(zzb, this.bfI.zzb(com_google_android_gms_internal_zzaom)) != null) {
                        String valueOf = String.valueOf(zzb);
                        throw new zzane(new StringBuilder(String.valueOf(valueOf).length() + 15).append("duplicate key: ").append(valueOf).toString());
                    }
                    com_google_android_gms_internal_zzaom.endArray();
                }
                com_google_android_gms_internal_zzaom.endArray();
                return map;
            }
            com_google_android_gms_internal_zzaom.beginObject();
            while (com_google_android_gms_internal_zzaom.hasNext()) {
                zzanr.beV.zzi(com_google_android_gms_internal_zzaom);
                zzb = this.bfH.zzb(com_google_android_gms_internal_zzaom);
                if (map.put(zzb, this.bfI.zzb(com_google_android_gms_internal_zzaom)) != null) {
                    valueOf = String.valueOf(zzb);
                    throw new zzane(new StringBuilder(String.valueOf(valueOf).length() + 15).append("duplicate key: ").append(valueOf).toString());
                }
            }
            com_google_android_gms_internal_zzaom.endObject();
            return map;
        }
    }

    public zzaoe(zzanp com_google_android_gms_internal_zzanp, boolean z) {
        this.bdR = com_google_android_gms_internal_zzanp;
        this.bfG = z;
    }

    private zzanh<?> zza(zzamp com_google_android_gms_internal_zzamp, Type type) {
        return (type == Boolean.TYPE || type == Boolean.class) ? zzaok.bgc : com_google_android_gms_internal_zzamp.zza(zzaol.zzl(type));
    }

    public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Type n = com_google_android_gms_internal_zzaol_T.m360n();
        if (!Map.class.isAssignableFrom(com_google_android_gms_internal_zzaol_T.m359m())) {
            return null;
        }
        Type[] zzb = zzano.zzb(n, zzano.zzf(n));
        zzanh zza = zza(com_google_android_gms_internal_zzamp, zzb[0]);
        zzanh zza2 = com_google_android_gms_internal_zzamp.zza(zzaol.zzl(zzb[1]));
        zzanu zzb2 = this.bdR.zzb(com_google_android_gms_internal_zzaol_T);
        return new zza(this, com_google_android_gms_internal_zzamp, zzb[0], zza, zzb[1], zza2, zzb2);
    }
}
