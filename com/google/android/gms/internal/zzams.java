package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzams extends zzamv implements Iterable<zzamv> {
    private final List<zzamv> aFj;

    public zzams() {
        this.aFj = new ArrayList();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof zzams) && ((zzams) obj).aFj.equals(this.aFj));
    }

    public boolean getAsBoolean() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    public double getAsDouble() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).getAsDouble();
        }
        throw new IllegalStateException();
    }

    public int getAsInt() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).getAsInt();
        }
        throw new IllegalStateException();
    }

    public long getAsLong() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).getAsLong();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.aFj.hashCode();
    }

    public Iterator<zzamv> iterator() {
        return this.aFj.iterator();
    }

    public void zzc(zzamv com_google_android_gms_internal_zzamv) {
        Object obj;
        if (com_google_android_gms_internal_zzamv == null) {
            obj = zzamx.bei;
        }
        this.aFj.add(obj);
    }

    public Number zzcze() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).zzcze();
        }
        throw new IllegalStateException();
    }

    public String zzczf() {
        if (this.aFj.size() == 1) {
            return ((zzamv) this.aFj.get(0)).zzczf();
        }
        throw new IllegalStateException();
    }
}
