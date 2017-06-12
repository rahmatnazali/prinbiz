package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.zzqn.zzb;

public abstract class zzps<L> implements zzb<L> {
    private final DataHolder tu;

    protected zzps(DataHolder dataHolder) {
        this.tu = dataHolder;
    }

    protected abstract void zza(L l, DataHolder dataHolder);

    public void zzapj() {
        if (this.tu != null) {
            this.tu.close();
        }
    }

    public final void zzt(L l) {
        zza(l, this.tu);
    }
}
