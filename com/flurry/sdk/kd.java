package com.flurry.sdk;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class kd<V> extends FutureTask<V> {
    private final WeakReference<Callable<V>> f317a;
    private final WeakReference<Runnable> f318b;

    public kd(Runnable runnable, V v) {
        super(runnable, v);
        this.f317a = new WeakReference(null);
        this.f318b = new WeakReference(runnable);
    }

    public final Runnable m168a() {
        return (Runnable) this.f318b.get();
    }
}
