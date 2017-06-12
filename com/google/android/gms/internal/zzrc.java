package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;

public final class zzrc extends LruCache<Object, Drawable> {
    public zzrc() {
        super(10);
    }
}
