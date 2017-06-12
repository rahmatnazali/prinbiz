package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    private static final Object yq;
    private static ClassLoader yr;
    private static Integer ys;
    private boolean yt;

    static {
        yq = new Object();
        yr = null;
        ys = null;
    }

    public DowngradeableSafeParcel() {
        this.yt = false;
    }

    protected static ClassLoader zzass() {
        synchronized (yq) {
        }
        return null;
    }

    protected static Integer zzast() {
        synchronized (yq) {
        }
        return null;
    }

    private static boolean zzd(Class<?> cls) {
        boolean z = false;
        try {
            z = SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
        }
        return z;
    }

    protected static boolean zzhk(String str) {
        ClassLoader zzass = zzass();
        if (zzass == null) {
            return true;
        }
        try {
            return zzd(zzass.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean zzasu() {
        return false;
    }
}
