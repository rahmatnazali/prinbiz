package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzab;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> vU;
    protected int vV;

    public zzb(DataBuffer<T> dataBuffer) {
        this.vU = (DataBuffer) zzab.zzy(dataBuffer);
        this.vV = -1;
    }

    public boolean hasNext() {
        return this.vV < this.vU.getCount() + -1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer dataBuffer = this.vU;
            int i = this.vV + 1;
            this.vV = i;
            return dataBuffer.get(i);
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.vV);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
