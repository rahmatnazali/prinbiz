package com.flurry.sdk;

import java.util.concurrent.ThreadFactory;

public final class lk implements ThreadFactory {
    private final ThreadGroup f405a;
    private final int f406b;

    public lk(String str) {
        this.f405a = new ThreadGroup(str);
        this.f406b = 1;
    }

    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f405a, runnable);
        thread.setName(this.f405a.getName() + ":" + thread.getId());
        thread.setPriority(this.f406b);
        return thread;
    }
}
