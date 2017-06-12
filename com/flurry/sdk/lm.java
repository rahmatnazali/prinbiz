package com.flurry.sdk;

public final class lm {
    private static long f407a;
    private static lm f408b;
    private final ln f409c;

    static {
        f407a = 100;
        f408b = null;
    }

    public static synchronized lm m277a() {
        lm lmVar;
        synchronized (lm.class) {
            if (f408b == null) {
                f408b = new lm();
            }
            lmVar = f408b;
        }
        return lmVar;
    }

    public static synchronized void m278b() {
        synchronized (lm.class) {
            if (f408b != null) {
                f408b.m279c();
                f408b = null;
            }
        }
    }

    public lm() {
        this.f409c = new ln();
        this.f409c.f411a = f407a;
        this.f409c.f412b = true;
    }

    public final synchronized void m280a(ka<ll> kaVar) {
        kb.m157a().m164a("com.flurry.android.sdk.TickEvent", kaVar);
        if (kb.m157a().m165b("com.flurry.android.sdk.TickEvent") > 0) {
            this.f409c.m286a();
        }
    }

    public final synchronized void m281b(ka<ll> kaVar) {
        kb.m157a().m166b("com.flurry.android.sdk.TickEvent", kaVar);
        if (kb.m157a().m165b("com.flurry.android.sdk.TickEvent") == 0) {
            this.f409c.m287b();
        }
    }

    private synchronized void m279c() {
        kb.m157a().m163a("com.flurry.android.sdk.TickEvent");
        this.f409c.m287b();
    }
}
