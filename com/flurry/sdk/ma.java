package com.flurry.sdk;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class ma {
    private static ma f435c;
    final UncaughtExceptionHandler f436a;
    final Map<UncaughtExceptionHandler, Void> f437b;

    /* renamed from: com.flurry.sdk.ma.a */
    final class C0177a implements UncaughtExceptionHandler {
        final /* synthetic */ ma f434a;

        private C0177a(ma maVar) {
            this.f434a = maVar;
        }

        public final void uncaughtException(Thread thread, Throwable th) {
            for (UncaughtExceptionHandler uncaughtException : this.f434a.m357c()) {
                try {
                    uncaughtException.uncaughtException(thread, th);
                } catch (Throwable th2) {
                }
            }
            ma maVar = this.f434a;
            if (maVar.f436a != null) {
                try {
                    maVar.f436a.uncaughtException(thread, th);
                } catch (Throwable th3) {
                }
            }
        }
    }

    public static synchronized ma m355a() {
        ma maVar;
        synchronized (ma.class) {
            if (f435c == null) {
                f435c = new ma();
            }
            maVar = f435c;
        }
        return maVar;
    }

    public static synchronized void m356b() {
        synchronized (ma.class) {
            if (f435c != null) {
                Thread.setDefaultUncaughtExceptionHandler(f435c.f436a);
            }
            f435c = null;
        }
    }

    final Set<UncaughtExceptionHandler> m357c() {
        Set<UncaughtExceptionHandler> keySet;
        synchronized (this.f437b) {
            keySet = this.f437b.keySet();
        }
        return keySet;
    }

    private ma() {
        this.f437b = new WeakHashMap();
        this.f436a = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new C0177a());
    }
}
