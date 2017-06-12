package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

public class km {
    private static final String f340a;
    private Timer f341b;
    private C0158a f342c;
    private kn f343d;

    /* renamed from: com.flurry.sdk.km.a */
    class C0158a extends TimerTask {
        final /* synthetic */ km f339a;

        private C0158a(km kmVar) {
            this.f339a = kmVar;
        }

        public final void run() {
            kf.m182a(3, km.f340a, "HttpRequest timed out. Cancelling.");
            kn a = this.f339a.f343d;
            kf.m182a(3, kn.f713e, "Timeout (" + (System.currentTimeMillis() - a.f726m) + "MS) for url: " + a.f719f);
            a.f729p = 629;
            a.f733t = true;
            a.m701h();
            a.m699f();
        }
    }

    static {
        f340a = km.class.getSimpleName();
    }

    public km(kn knVar) {
        this.f343d = knVar;
    }

    public final synchronized void m215a(long j) {
        Object obj = null;
        synchronized (this) {
            if (this.f341b != null) {
                obj = 1;
            }
            if (obj != null) {
                m214a();
            }
            this.f341b = new Timer("HttpRequestTimeoutTimer");
            this.f342c = new C0158a();
            this.f341b.schedule(this.f342c, j);
            kf.m182a(3, f340a, "HttpRequestTimeoutTimer started: " + j + "MS");
        }
    }

    public final synchronized void m214a() {
        if (this.f341b != null) {
            this.f341b.cancel();
            this.f341b = null;
            kf.m182a(3, f340a, "HttpRequestTimeoutTimer stopped.");
        }
        this.f342c = null;
    }
}
