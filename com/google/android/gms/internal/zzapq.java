package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zzapq<M extends zzapp<M>, T> {
    protected final Class<T> baj;
    protected final boolean bjy;
    public final int tag;
    protected final int type;

    private zzapq(int i, Class<T> cls, int i2, boolean z) {
        this.type = i;
        this.baj = cls;
        this.tag = i2;
        this.bjy = z;
    }

    public static <M extends zzapp<M>, T extends zzapv> zzapq<M, T> zza(int i, Class<T> cls, long j) {
        return new zzapq(i, cls, (int) j, false);
    }

    private T zzaw(List<zzapx> list) {
        int i;
        int i2 = 0;
        List arrayList = new ArrayList();
        for (i = 0; i < list.size(); i++) {
            zzapx com_google_android_gms_internal_zzapx = (zzapx) list.get(i);
            if (com_google_android_gms_internal_zzapx.apf.length != 0) {
                zza(com_google_android_gms_internal_zzapx, arrayList);
            }
        }
        i = arrayList.size();
        if (i == 0) {
            return null;
        }
        T cast = this.baj.cast(Array.newInstance(this.baj.getComponentType(), i));
        while (i2 < i) {
            Array.set(cast, i2, arrayList.get(i2));
            i2++;
        }
        return cast;
    }

    private T zzax(List<zzapx> list) {
        if (list.isEmpty()) {
            return null;
        }
        return this.baj.cast(zzcg(zzapn.zzbd(((zzapx) list.get(list.size() - 1)).apf)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzapq)) {
            return false;
        }
        zzapq com_google_android_gms_internal_zzapq = (zzapq) obj;
        return this.type == com_google_android_gms_internal_zzapq.type && this.baj == com_google_android_gms_internal_zzapq.baj && this.tag == com_google_android_gms_internal_zzapq.tag && this.bjy == com_google_android_gms_internal_zzapq.bjy;
    }

    public int hashCode() {
        return (this.bjy ? 1 : 0) + ((((((this.type + 1147) * 31) + this.baj.hashCode()) * 31) + this.tag) * 31);
    }

    protected void zza(zzapx com_google_android_gms_internal_zzapx, List<Object> list) {
        list.add(zzcg(zzapn.zzbd(com_google_android_gms_internal_zzapx.apf)));
    }

    void zza(Object obj, zzapo com_google_android_gms_internal_zzapo) throws IOException {
        if (this.bjy) {
            zzc(obj, com_google_android_gms_internal_zzapo);
        } else {
            zzb(obj, com_google_android_gms_internal_zzapo);
        }
    }

    final T zzav(List<zzapx> list) {
        return list == null ? null : this.bjy ? zzaw(list) : zzax(list);
    }

    protected void zzb(Object obj, zzapo com_google_android_gms_internal_zzapo) {
        try {
            com_google_android_gms_internal_zzapo.zzagb(this.tag);
            switch (this.type) {
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    zzapv com_google_android_gms_internal_zzapv = (zzapv) obj;
                    int zzagj = zzapy.zzagj(this.tag);
                    com_google_android_gms_internal_zzapo.zzb(com_google_android_gms_internal_zzapv);
                    com_google_android_gms_internal_zzapo.zzai(zzagj, 4);
                    return;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    com_google_android_gms_internal_zzapo.zzc((zzapv) obj);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        throw new IllegalStateException(e);
    }

    protected void zzc(Object obj, zzapo com_google_android_gms_internal_zzapo) {
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                zzb(obj2, com_google_android_gms_internal_zzapo);
            }
        }
    }

    protected Object zzcg(zzapn com_google_android_gms_internal_zzapn) {
        String valueOf;
        Class componentType = this.bjy ? this.baj.getComponentType() : this.baj;
        try {
            zzapv com_google_android_gms_internal_zzapv;
            switch (this.type) {
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    com_google_android_gms_internal_zzapv = (zzapv) componentType.newInstance();
                    com_google_android_gms_internal_zzapn.zza(com_google_android_gms_internal_zzapv, zzapy.zzagj(this.tag));
                    return com_google_android_gms_internal_zzapv;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    com_google_android_gms_internal_zzapv = (zzapv) componentType.newInstance();
                    com_google_android_gms_internal_zzapn.zza(com_google_android_gms_internal_zzapv);
                    return com_google_android_gms_internal_zzapv;
                default:
                    throw new IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (Throwable e) {
            valueOf = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Error creating instance of class ").append(valueOf).toString(), e2);
        } catch (Throwable e22) {
            throw new IllegalArgumentException("Error reading extension field", e22);
        }
    }

    int zzcp(Object obj) {
        return this.bjy ? zzcq(obj) : zzcr(obj);
    }

    protected int zzcq(Object obj) {
        int i = 0;
        int length = Array.getLength(obj);
        for (int i2 = 0; i2 < length; i2++) {
            if (Array.get(obj, i2) != null) {
                i += zzcr(Array.get(obj, i2));
            }
        }
        return i;
    }

    protected int zzcr(Object obj) {
        int zzagj = zzapy.zzagj(this.tag);
        switch (this.type) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return zzapo.zzb(zzagj, (zzapv) obj);
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return zzapo.zzc(zzagj, (zzapv) obj);
            default:
                throw new IllegalArgumentException("Unknown type " + this.type);
        }
    }
}
