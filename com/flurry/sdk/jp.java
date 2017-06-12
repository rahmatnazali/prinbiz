package com.flurry.sdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class jp extends ke<kn> {
    private static jp f618a;

    static {
        f618a = null;
    }

    public static synchronized jp m593a() {
        jp jpVar;
        synchronized (jp.class) {
            if (f618a == null) {
                f618a = new jp();
            }
            jpVar = f618a;
        }
        return jpVar;
    }

    public static synchronized void m594b() {
        synchronized (jp.class) {
            if (f618a != null) {
                f618a.m179c();
            }
            f618a = null;
        }
    }

    protected jp() {
        super(jp.class.getName(), TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new kc()));
    }
}
