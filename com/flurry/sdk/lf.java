package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import com.flurry.sdk.ju.C0153a;
import com.flurry.sdk.le.C0172a;
import com.flurry.sdk.lj.C0175a;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import org.apache.commons.net.ftp.FTPClient;

public class lf implements C0175a {
    private static lf f680b;
    private static final String f681c;
    public long f682a;
    private final Map<Context, ld> f683d;
    private final lg f684e;
    private final Object f685f;
    private long f686g;
    private ld f687h;
    private ka<lh> f688i;
    private ka<ju> f689j;

    /* renamed from: com.flurry.sdk.lf.5 */
    static /* synthetic */ class C01735 {
        static final /* synthetic */ int[] f399a;

        static {
            f399a = new int[C0153a.values().length];
            try {
                f399a[C0153a.kStarted.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f399a[C0153a.kStopped.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f399a[C0153a.kDestroyed.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.lf.1 */
    class C06391 implements ka<lh> {
        final /* synthetic */ lf f675a;

        C06391(lf lfVar) {
            this.f675a = lfVar;
        }

        public final /* bridge */ /* synthetic */ void m647a(jz jzVar) {
            this.f675a.m659h();
        }
    }

    /* renamed from: com.flurry.sdk.lf.2 */
    class C06402 implements ka<ju> {
        final /* synthetic */ lf f676a;

        C06402(lf lfVar) {
            this.f676a = lfVar;
        }

        public final /* synthetic */ void m648a(jz jzVar) {
            ju juVar = (ju) jzVar;
            Context context = (Activity) juVar.f621a.get();
            if (context == null) {
                kf.m184a(lf.f681c, "Activity has been destroyed, can't pass ActivityLifecycleEvent to adobject.");
                return;
            }
            switch (C01735.f399a[juVar.f622b.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    kf.m182a(3, lf.f681c, "Automatic onStartSession for context:" + juVar.f621a);
                    this.f676a.m656e(context);
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    kf.m182a(3, lf.f681c, "Automatic onEndSession for context:" + juVar.f621a);
                    this.f676a.m665d(context);
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    kf.m182a(3, lf.f681c, "Automatic onEndSession (destroyed) for context:" + juVar.f621a);
                    this.f676a.m665d(context);
                default:
            }
        }
    }

    /* renamed from: com.flurry.sdk.lf.3 */
    class C06413 extends lw {
        final /* synthetic */ lf f677a;

        C06413(lf lfVar) {
            this.f677a = lfVar;
        }

        public final void m649a() {
            this.f677a.m659h();
        }
    }

    /* renamed from: com.flurry.sdk.lf.4 */
    class C06424 extends lw {
        final /* synthetic */ ld f678a;
        final /* synthetic */ lf f679b;

        C06424(lf lfVar, ld ldVar) {
            this.f679b = lfVar;
            this.f678a = ldVar;
        }

        public final void m650a() {
            lf.m654a(this.f679b, this.f678a);
        }
    }

    public static synchronized lf m651a() {
        lf lfVar;
        synchronized (lf.class) {
            if (f680b == null) {
                f680b = new lf();
            }
            lfVar = f680b;
        }
        return lfVar;
    }

    public static synchronized void m655b() {
        synchronized (lf.class) {
            if (f680b != null) {
                kb.m157a().m162a(f680b.f688i);
                kb.m157a().m162a(f680b.f689j);
                li.m668a().m275b("ContinueSessionMillis", f680b);
            }
            f680b = null;
        }
    }

    static {
        f681c = lf.class.getSimpleName();
    }

    private lf() {
        this.f683d = new WeakHashMap();
        this.f684e = new lg();
        this.f685f = new Object();
        this.f688i = new C06391(this);
        this.f689j = new C06402(this);
        lj a = li.m668a();
        this.f682a = 0;
        this.f686g = ((Long) a.m272a("ContinueSessionMillis")).longValue();
        a.m273a("ContinueSessionMillis", (C0175a) this);
        kf.m182a(4, f681c, "initSettings, ContinueSessionMillis = " + this.f686g);
        kb.m157a().m164a("com.flurry.android.sdk.ActivityLifecycleEvent", this.f689j);
        kb.m157a().m164a("com.flurry.android.sdk.FlurrySessionTimerEvent", this.f688i);
    }

    private synchronized int m658g() {
        return this.f683d.size();
    }

    public final ld m663c() {
        ld ldVar;
        synchronized (this.f685f) {
            ldVar = this.f687h;
        }
        return ldVar;
    }

    public final synchronized void m660a(Context context) {
        if (context instanceof Activity) {
            if (jv.m136a().m139c()) {
                kf.m182a(3, f681c, "bootstrap for context:" + context);
                m656e(context);
            }
        }
    }

    public final synchronized void m662b(Context context) {
        if (!(jv.m136a().m139c() && (context instanceof Activity))) {
            kf.m182a(3, f681c, "Manual onStartSession for context:" + context);
            m656e(context);
        }
    }

    public final synchronized void m664c(Context context) {
        if (!(jv.m136a().m139c() && (context instanceof Activity))) {
            kf.m182a(3, f681c, "Manual onEndSession for context:" + context);
            m665d(context);
        }
    }

    public final synchronized boolean m666d() {
        boolean z;
        if (m663c() == null) {
            kf.m182a(2, f681c, "Session not found. No active session");
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public final synchronized void m667e() {
        for (Entry entry : this.f683d.entrySet()) {
            le leVar = new le();
            leVar.f671a = new WeakReference(entry.getKey());
            leVar.f672b = (ld) entry.getValue();
            leVar.f673c = C0172a.f396d;
            jd.m552a();
            leVar.f674d = jd.m554d();
            leVar.m155b();
        }
        this.f683d.clear();
        jr.m120a().m126b(new C06413(this));
    }

    private synchronized void m656e(Context context) {
        if (((ld) this.f683d.get(context)) == null) {
            le leVar;
            this.f684e.m269a();
            ld c = m663c();
            if (c == null) {
                c = new ld();
                kf.m196e(f681c, "Flurry session started for context:" + context);
                leVar = new le();
                leVar.f671a = new WeakReference(context);
                leVar.f672b = c;
                leVar.f673c = C0172a.f393a;
                leVar.m155b();
            }
            this.f683d.put(context, c);
            synchronized (this.f685f) {
                this.f687h = c;
            }
            kf.m196e(f681c, "Flurry session resumed for context:" + context);
            leVar = new le();
            leVar.f671a = new WeakReference(context);
            leVar.f672b = c;
            leVar.f673c = C0172a.f395c;
            leVar.m155b();
            this.f682a = 0;
        } else if (jv.m136a().m139c()) {
            kf.m182a(3, f681c, "Session already started with context:" + context);
        } else {
            kf.m196e(f681c, "Session already started with context:" + context);
        }
    }

    final synchronized void m665d(Context context) {
        ld ldVar = (ld) this.f683d.remove(context);
        if (ldVar != null) {
            kf.m196e(f681c, "Flurry session paused for context:" + context);
            le leVar = new le();
            leVar.f671a = new WeakReference(context);
            leVar.f672b = ldVar;
            jd.m552a();
            leVar.f674d = jd.m554d();
            leVar.f673c = C0172a.f396d;
            leVar.m155b();
            if (m658g() == 0) {
                this.f684e.m270a(this.f686g);
                this.f682a = System.currentTimeMillis();
            } else {
                this.f682a = 0;
            }
        } else if (jv.m136a().m139c()) {
            kf.m182a(3, f681c, "Session cannot be ended, session not found for context:" + context);
        } else {
            kf.m196e(f681c, "Session cannot be ended, session not found for context:" + context);
        }
    }

    private synchronized void m659h() {
        int g = m658g();
        if (g > 0) {
            kf.m182a(5, f681c, "Session cannot be finalized, sessionContextCount:" + g);
        } else {
            ld c = m663c();
            if (c == null) {
                kf.m182a(5, f681c, "Session cannot be finalized, current session not found");
            } else {
                kf.m196e(f681c, "Flurry session ended");
                le leVar = new le();
                leVar.f672b = c;
                leVar.f673c = C0172a.f397e;
                jd.m552a();
                leVar.f674d = jd.m554d();
                leVar.m155b();
                jr.m120a().m126b(new C06424(this, c));
            }
        }
    }

    public final void m661a(String str, Object obj) {
        if (str.equals("ContinueSessionMillis")) {
            this.f686g = ((Long) obj).longValue();
            kf.m182a(4, f681c, "onSettingUpdate, ContinueSessionMillis = " + this.f686g);
            return;
        }
        kf.m182a(6, f681c, "onSettingUpdate internal error!");
    }

    static /* synthetic */ void m654a(lf lfVar, ld ldVar) {
        synchronized (lfVar.f685f) {
            if (lfVar.f687h == ldVar) {
                lfVar.f687h = null;
            }
        }
    }
}
