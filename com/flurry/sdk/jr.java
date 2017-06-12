package com.flurry.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

public class jr {
    private static final String f282e;
    private static jr f283f;
    public final Context f284a;
    public final Handler f285b;
    public final Handler f286c;
    public final String f287d;
    private final HandlerThread f288g;
    private final kh f289h;

    static {
        f282e = jr.class.getSimpleName();
    }

    public static jr m120a() {
        return f283f;
    }

    public static synchronized void m121a(Context context, String str) {
        synchronized (jr.class) {
            if (f283f != null) {
                if (f283f.f287d.equals(str)) {
                    kf.m196e(f282e, "Flurry is already initialized");
                } else {
                    throw new IllegalStateException("Only one API key per application is supported!");
                }
            } else if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            } else if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("API key must be specified");
            } else {
                jr jrVar = new jr(context, str);
                f283f = jrVar;
                jrVar.f289h.m200a(context);
            }
        }
    }

    public static synchronized void m122b() {
        synchronized (jr.class) {
            if (f283f != null) {
                jr jrVar = f283f;
                jrVar.f289h.m199a();
                jrVar.f288g.quit();
                f283f = null;
            }
        }
    }

    private jr(Context context, String str) {
        this.f284a = context.getApplicationContext();
        this.f285b = new Handler(Looper.getMainLooper());
        this.f288g = new HandlerThread("FlurryAgent");
        this.f288g.start();
        this.f286c = new Handler(this.f288g.getLooper());
        this.f287d = str;
        this.f289h = new kh();
    }

    public final void m124a(Runnable runnable) {
        this.f285b.post(runnable);
    }

    public final void m126b(Runnable runnable) {
        this.f286c.post(runnable);
    }

    public final void m125a(Runnable runnable, long j) {
        if (runnable != null) {
            this.f286c.postDelayed(runnable, j);
        }
    }

    public final void m127c(Runnable runnable) {
        if (runnable != null) {
            this.f286c.removeCallbacks(runnable);
        }
    }

    public final ki m123a(Class<? extends ki> cls) {
        return this.f289h.m201b(cls);
    }
}
