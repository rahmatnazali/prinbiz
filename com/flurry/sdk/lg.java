package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

final class lg {
    private Timer f401a;
    private C0174a f402b;

    /* renamed from: com.flurry.sdk.lg.a */
    class C0174a extends TimerTask {
        final /* synthetic */ lg f400a;

        C0174a(lg lgVar) {
            this.f400a = lgVar;
        }

        public final void run() {
            this.f400a.m269a();
            kb.m157a().m161a(new lh());
        }
    }

    lg() {
    }

    public final synchronized void m270a(long j) {
        Object obj;
        if (this.f401a != null) {
            obj = 1;
        } else {
            obj = null;
        }
        if (obj != null) {
            m269a();
        }
        this.f401a = new Timer("FlurrySessionTimer");
        this.f402b = new C0174a(this);
        this.f401a.schedule(this.f402b, j);
    }

    public final synchronized void m269a() {
        if (this.f401a != null) {
            this.f401a.cancel();
            this.f401a = null;
        }
        this.f402b = null;
    }
}
