package com.google.android.gms.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public abstract class zzamv {
    public boolean getAsBoolean() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double getAsDouble() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int getAsInt() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long getAsLong() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            Writer stringWriter = new StringWriter();
            zzaoo com_google_android_gms_internal_zzaoo = new zzaoo(stringWriter);
            com_google_android_gms_internal_zzaoo.setLenient(true);
            zzanw.zzb(this, com_google_android_gms_internal_zzaoo);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public Number zzcze() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String zzczf() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public boolean zzczg() {
        return this instanceof zzams;
    }

    public boolean zzczh() {
        return this instanceof zzamy;
    }

    public boolean zzczi() {
        return this instanceof zzanb;
    }

    public boolean zzczj() {
        return this instanceof zzamx;
    }

    public zzamy zzczk() {
        if (zzczh()) {
            return (zzamy) this;
        }
        String valueOf = String.valueOf(this);
        throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 19).append("Not a JSON Object: ").append(valueOf).toString());
    }

    public zzams zzczl() {
        if (zzczg()) {
            return (zzams) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public zzanb zzczm() {
        if (zzczi()) {
            return (zzanb) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    Boolean zzczn() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }
}
