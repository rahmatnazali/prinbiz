package com.flurry.sdk;

import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.widget.Toast;
import com.flurry.sdk.im.C0584a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.xmlpull.v1.XmlPullParser;

public class il {
    public static int f132a;
    public static int f133b;
    public static AtomicInteger f134c;
    static jy<List<im>> f135d;
    private static il f136f;
    private static Map<Integer, im> f137g;
    private String f138e;
    private final AtomicInteger f139h;
    private long f140i;
    private ka<jj> f141j;

    /* renamed from: com.flurry.sdk.il.2 */
    class C01312 implements Runnable {
        final /* synthetic */ il f127a;

        C01312(il ilVar) {
            this.f127a = ilVar;
        }

        public final void run() {
            this.f127a.m35k();
        }
    }

    /* renamed from: com.flurry.sdk.il.3 */
    class C01323 implements Runnable {
        final /* synthetic */ il f128a;

        C01323(il ilVar) {
            this.f128a = ilVar;
        }

        public final void run() {
            this.f128a.m35k();
        }
    }

    /* renamed from: com.flurry.sdk.il.4 */
    class C01334 implements Runnable {
        final /* synthetic */ ij f129a;
        final /* synthetic */ il f130b;

        C01334(il ilVar, ij ijVar) {
            this.f130b = ilVar;
            this.f129a = ijVar;
        }

        public final void run() {
            Toast.makeText(jr.m120a().f284a, "PulseCallbackReportInfo HTTP Response Code: " + this.f129a.f112e + " for url: " + this.f129a.f119l.f357r, 1).show();
        }
    }

    /* renamed from: com.flurry.sdk.il.5 */
    class C01345 implements Runnable {
        final /* synthetic */ il f131a;

        C01345(il ilVar) {
            this.f131a = ilVar;
        }

        public final void run() {
            il.m20a();
            List c = il.m26c();
            if (il.f135d == null) {
                il.m37m();
            }
            il.f135d.m152a(c);
        }
    }

    /* renamed from: com.flurry.sdk.il.1 */
    class C05821 implements ka<jj> {
        final /* synthetic */ il f521a;

        /* renamed from: com.flurry.sdk.il.1.1 */
        class C01301 implements Runnable {
            final /* synthetic */ C05821 f126a;

            C01301(C05821 c05821) {
                this.f126a = c05821;
            }

            public final void run() {
                in.m64a().m71b();
            }
        }

        C05821(il ilVar) {
            this.f521a = ilVar;
        }

        public final /* synthetic */ void m480a(jz jzVar) {
            jj jjVar = (jj) jzVar;
            kf.m182a(4, this.f521a.f138e, "onNetworkStateChanged : isNetworkEnable = " + jjVar.f612a);
            if (jjVar.f612a) {
                jr.m120a().m126b(new C01301(this));
            }
        }
    }

    /* renamed from: com.flurry.sdk.il.6 */
    class C05836 implements lc<List<im>> {
        C05836() {
        }

        public final kz<List<im>> m481a(int i) {
            return new ky(new C0584a());
        }
    }

    private il() {
        this.f138e = il.class.getSimpleName();
        this.f141j = new C05821(this);
        f137g = new HashMap();
        this.f139h = new AtomicInteger(0);
        f134c = new AtomicInteger(0);
        if (f133b == 0) {
            f133b = 600000;
        }
        if (f132a == 0) {
            f132a = 15;
        }
        this.f140i = jr.m120a().f284a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).getLong("timeToSendNextPulseReport", 0);
        if (f135d == null) {
            m37m();
        }
        kb.m157a().m164a("com.flurry.android.sdk.NetworkStateEvent", this.f141j);
    }

    public static synchronized il m20a() {
        il ilVar;
        synchronized (il.class) {
            if (f136f == null) {
                f136f = new il();
            }
            ilVar = f136f;
        }
        return ilVar;
    }

    public static void m23b() {
        if (f136f != null) {
            kb.m157a().m166b("com.flurry.android.sdk.NetworkStateEvent", f136f.f141j);
            f137g.clear();
            f137g = null;
            f136f = null;
        }
    }

    public static void m22a(int i) {
        f132a = i;
    }

    public static void m24b(int i) {
        f133b = i;
    }

    public final synchronized void m39a(im imVar) {
        if (imVar == null) {
            kf.m182a(3, this.f138e, "Must add valid PulseCallbackAsyncReportInfo");
        } else {
            kf.m182a(3, this.f138e, "Adding and sending " + imVar.f147c + " report to PulseCallbackManager.");
            if (imVar.m60a().size() != 0) {
                if (this.f140i == 0) {
                    this.f140i = System.currentTimeMillis() + ((long) f133b);
                    jr.m120a().m126b(new C01312(this));
                }
                int l = m36l();
                imVar.f146b = l;
                f137g.put(Integer.valueOf(l), imVar);
                for (kp b : imVar.m60a()) {
                    hk.m392a().f457c.m231b(b);
                }
            }
        }
    }

    public final synchronized void m42b(im imVar) {
        if (imVar == null) {
            kf.m182a(3, this.f138e, "Must add valid PulseCallbackAsyncReportInfo");
        } else {
            if (this.f140i == 0) {
                this.f140i = System.currentTimeMillis() + ((long) f133b);
                jr.m120a().m126b(new C01323(this));
            }
            int l = m36l();
            imVar.f146b = l;
            f137g.put(Integer.valueOf(l), imVar);
            for (ii iiVar : imVar.m60a()) {
                Iterator it = iiVar.f506a.iterator();
                while (it.hasNext()) {
                    it.next();
                    f134c.incrementAndGet();
                    if (m32h()) {
                        kf.m182a(3, this.f138e, "Max Callback Attempts threshold reached. Sending callback logging reports");
                        m34j();
                    }
                }
            }
            if (m33i()) {
                kf.m182a(3, this.f138e, "Time threshold reached. Sending callback logging reports");
                m34j();
            }
            kf.m182a(3, this.f138e, "Restoring " + imVar.f147c + " report to PulseCallbackManager. Number of stored completed callbacks: " + f134c.get());
        }
    }

    private synchronized void m27c(int i) {
        kf.m182a(3, this.f138e, "Removing report " + i + " from PulseCallbackManager");
        f137g.remove(Integer.valueOf(i));
    }

    public static List<im> m26c() {
        return new ArrayList(f137g.values());
    }

    public final synchronized void m38a(ij ijVar) {
        kf.m182a(3, this.f138e, ijVar.f119l.f518m.f147c + " report sent successfully to " + ijVar.f119l.f516k);
        ijVar.f113f = ik.COMPLETE;
        ijVar.f114g = XmlPullParser.NO_NAMESPACE;
        m28c(ijVar);
        if (kf.m190c() <= 3 && kf.m195d()) {
            jr.m120a().m124a(new C01334(this, ijVar));
        }
    }

    public final synchronized boolean m40a(ij ijVar, String str) {
        boolean z = true;
        synchronized (this) {
            boolean z2;
            ijVar.f115h++;
            ijVar.f116i = System.currentTimeMillis();
            if (ijVar.f115h > ijVar.f119l.f509d) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 || TextUtils.isEmpty(str)) {
                kf.m182a(3, this.f138e, "Maximum number of redirects attempted. Aborting: " + ijVar.f119l.f518m.f147c + " report to " + ijVar.f119l.f516k);
                ijVar.f113f = ik.INVALID_RESPONSE;
                ijVar.f114g = XmlPullParser.NO_NAMESPACE;
                m28c(ijVar);
                z = false;
            } else {
                kf.m182a(3, this.f138e, "Report to " + ijVar.f119l.f516k + " redirecting to url: " + str);
                ijVar.f119l.f357r = str;
                m44d();
            }
        }
        return z;
    }

    public final synchronized void m41b(ij ijVar) {
        kf.m182a(3, this.f138e, "Maximum number of attempts reached. Aborting: " + ijVar.f119l.f518m.f147c);
        ijVar.f113f = ik.TIMEOUT;
        ijVar.f116i = System.currentTimeMillis();
        ijVar.f114g = XmlPullParser.NO_NAMESPACE;
        m28c(ijVar);
    }

    public final synchronized boolean m43b(ij ijVar, String str) {
        boolean z = false;
        synchronized (this) {
            boolean z2;
            ijVar.f113f = ik.INVALID_RESPONSE;
            ijVar.f116i = System.currentTimeMillis();
            if (str == null) {
                str = XmlPullParser.NO_NAMESPACE;
            }
            ijVar.f114g = str;
            kp kpVar = ijVar.f119l;
            if (kpVar.f355p >= kpVar.f508c) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                kf.m182a(3, this.f138e, "Maximum number of attempts reached. Aborting: " + ijVar.f119l.f518m.f147c + " report to " + ijVar.f119l.f516k);
                m28c(ijVar);
            } else if (ly.m353h(ijVar.f119l.f357r)) {
                kf.m182a(3, this.f138e, "Retrying callback to " + ijVar.f119l.f518m.f147c + " in: " + (ijVar.f119l.f512g / 1000) + " seconds.");
                ijVar.m18a();
                f134c.incrementAndGet();
                m44d();
                m31g();
                z = true;
            } else {
                kf.m182a(3, this.f138e, "Url: " + ijVar.f119l.f357r + " is invalid.");
                m28c(ijVar);
            }
        }
        return z;
    }

    public final void m44d() {
        jr.m120a().m126b(new C01345(this));
    }

    public static List<im> m29e() {
        if (f135d == null) {
            m37m();
        }
        return (List) f135d.m151a();
    }

    private void m28c(ij ijVar) {
        ijVar.f111d = true;
        ijVar.m18a();
        f134c.incrementAndGet();
        ijVar.f119l.m476c();
        kf.m182a(3, this.f138e, ijVar.f119l.f518m.f147c + " report to " + ijVar.f119l.f516k + " finalized.");
        m44d();
        m31g();
    }

    private void m31g() {
        if (m32h() || m33i()) {
            kf.m182a(3, this.f138e, "Threshold reached. Sending callback logging reports");
            m34j();
        }
    }

    private static boolean m32h() {
        return f134c.intValue() >= f132a;
    }

    private boolean m33i() {
        return System.currentTimeMillis() > this.f140i;
    }

    private void m34j() {
        for (im imVar : m26c()) {
            int i = 0;
            for (ii iiVar : imVar.m60a()) {
                Iterator it = iiVar.f506a.iterator();
                while (it.hasNext()) {
                    ij ijVar = (ij) it.next();
                    if (ijVar.f117j) {
                        it.remove();
                    } else if (!ijVar.f113f.equals(ik.PENDING_COMPLETION)) {
                        ijVar.f117j = true;
                        i = true;
                    }
                }
            }
            if (i != 0) {
                in.m64a().m70a(imVar);
            }
        }
        in.m64a().m71b();
        this.f140i = System.currentTimeMillis() + ((long) f133b);
        m35k();
        List c = m26c();
        for (int i2 = 0; i2 < c.size(); i2++) {
            im imVar2 = (im) c.get(i2);
            if (imVar2.m61b()) {
                m27c(imVar2.f146b);
            } else {
                List a = imVar2.m60a();
                for (i = 0; i < a.size(); i++) {
                    ii iiVar2 = (ii) a.get(i);
                    if (iiVar2.f517l) {
                        imVar2.f148d.remove(Long.valueOf(iiVar2.f507b));
                    } else {
                        it = iiVar2.f506a.iterator();
                        while (it.hasNext()) {
                            if (((ij) it.next()).f117j) {
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
        f134c = new AtomicInteger(0);
        m44d();
    }

    private void m35k() {
        Editor edit = jr.m120a().f284a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putLong("timeToSendNextPulseReport", this.f140i);
        edit.commit();
    }

    private synchronized int m36l() {
        return this.f139h.incrementAndGet();
    }

    private static void m37m() {
        f135d = new jy(jr.m120a().f284a.getFileStreamPath(".yflurryanongoingpulsecallbackreporter"), ".yflurryanongoingpulsecallbackreporter", 2, new C05836());
    }
}
