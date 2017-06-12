package com.google.android.gms.internal;

import java.util.Map.Entry;
import java.util.Set;

public final class zzamy extends zzamv {
    private final zzant<String, zzamv> bej;

    public zzamy() {
        this.bej = new zzant();
    }

    public Set<Entry<String, zzamv>> entrySet() {
        return this.bej.entrySet();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzamy) && ((zzamy) obj).bej.equals(this.bej));
    }

    public boolean has(String str) {
        return this.bej.containsKey(str);
    }

    public int hashCode() {
        return this.bej.hashCode();
    }

    public void zza(String str, zzamv com_google_android_gms_internal_zzamv) {
        Object obj;
        if (com_google_android_gms_internal_zzamv == null) {
            obj = zzamx.bei;
        }
        this.bej.put(str, obj);
    }

    public zzamv zzto(String str) {
        return (zzamv) this.bej.get(str);
    }
}
