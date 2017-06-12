package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

public class zzg<T> extends zzb<T> {
    private T wq;

    public zzg(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public T next() {
        if (hasNext()) {
            this.vV++;
            if (this.vV == 0) {
                this.wq = this.vU.get(0);
                if (!(this.wq instanceof zzc)) {
                    String valueOf = String.valueOf(this.wq.getClass());
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 44).append("DataBuffer reference of type ").append(valueOf).append(" is not movable").toString());
                }
            }
            ((zzc) this.wq).zzfq(this.vV);
            return this.wq;
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.vV);
    }
}
