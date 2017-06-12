package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    protected final DataHolder tu;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.tu = dataHolder;
        if (this.tu == null) {
        }
    }

    @Deprecated
    public final void close() {
        release();
    }

    public abstract T get(int i);

    public int getCount() {
        return this.tu == null ? 0 : this.tu.getCount();
    }

    @Deprecated
    public boolean isClosed() {
        return this.tu == null || this.tu.isClosed();
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public void release() {
        if (this.tu != null) {
            this.tu.close();
        }
    }

    public Iterator<T> singleRefIterator() {
        return new zzg(this);
    }

    public Bundle zzarc() {
        return this.tu.zzarc();
    }
}
