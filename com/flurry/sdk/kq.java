package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class kq<ReportInfo extends kp> {
    private static final String f359a;
    public static long f360b;
    public boolean f361c;
    public long f362d;
    private final int f363e;
    private final jy<List<ReportInfo>> f364f;
    private final List<ReportInfo> f365g;
    private int f366h;
    private final Runnable f367i;
    private final ka<jj> f368j;

    /* renamed from: com.flurry.sdk.kq.1 */
    class C06141 extends lw {
        final /* synthetic */ kq f635a;

        C06141(kq kqVar) {
            this.f635a = kqVar;
        }

        public final void m608a() {
            this.f635a.m223b();
        }
    }

    /* renamed from: com.flurry.sdk.kq.2 */
    class C06152 implements ka<jj> {
        final /* synthetic */ kq f636a;

        C06152(kq kqVar) {
            this.f636a = kqVar;
        }

        public final /* bridge */ /* synthetic */ void m609a(jz jzVar) {
            if (((jj) jzVar).f612a) {
                this.f636a.m223b();
            }
        }
    }

    /* renamed from: com.flurry.sdk.kq.3 */
    class C06163 extends lw {
        final /* synthetic */ kq f637a;

        C06163(kq kqVar) {
            this.f637a = kqVar;
        }

        public final void m610a() {
            this.f637a.m232b(this.f637a.f365g);
            this.f637a.m223b();
        }
    }

    /* renamed from: com.flurry.sdk.kq.4 */
    class C06174 extends lw {
        final /* synthetic */ kq f638a;

        C06174(kq kqVar) {
            this.f638a = kqVar;
        }

        public final void m611a() {
            this.f638a.m223b();
        }
    }

    /* renamed from: com.flurry.sdk.kq.5 */
    class C06185 extends lw {
        final /* synthetic */ kq f639a;

        C06185(kq kqVar) {
            this.f639a = kqVar;
        }

        public final void m612a() {
            this.f639a.m223b();
        }
    }

    /* renamed from: com.flurry.sdk.kq.6 */
    class C06196 extends lw {
        final /* synthetic */ kq f640a;

        C06196(kq kqVar) {
            this.f640a = kqVar;
        }

        public final void m613a() {
            this.f640a.m225e();
        }
    }

    /* renamed from: com.flurry.sdk.kq.7 */
    class C06207 extends lw {
        final /* synthetic */ kq f641a;

        C06207(kq kqVar) {
            this.f641a = kqVar;
        }

        public final void m614a() {
            this.f641a.m225e();
        }
    }

    /* renamed from: com.flurry.sdk.kq.8 */
    class C06218 extends lw {
        final /* synthetic */ kq f642a;

        C06218(kq kqVar) {
            this.f642a = kqVar;
        }

        public final void m615a() {
            this.f642a.m225e();
        }
    }

    public abstract jy<List<ReportInfo>> m228a();

    public abstract void m229a(ReportInfo reportInfo);

    static {
        f359a = kq.class.getSimpleName();
        f360b = 10000;
    }

    public kq() {
        this.f363e = Integer.MAX_VALUE;
        this.f365g = new ArrayList();
        this.f367i = new C06141(this);
        this.f368j = new C06152(this);
        kb.m157a().m164a("com.flurry.android.sdk.NetworkStateEvent", this.f368j);
        this.f364f = m228a();
        this.f362d = f360b;
        this.f366h = -1;
        jr.m120a().m126b(new C06163(this));
    }

    public final void m233c() {
        jr.m120a().m127c(this.f367i);
        kb.m157a().m166b("com.flurry.android.sdk.NetworkStateEvent", this.f368j);
    }

    public final void m235d() {
        this.f361c = false;
        jr.m120a().m126b(new C06174(this));
    }

    public final synchronized void m231b(ReportInfo reportInfo) {
        if (reportInfo != null) {
            this.f365g.add(reportInfo);
            jr.m120a().m126b(new C06185(this));
        }
    }

    public final synchronized void m234c(ReportInfo reportInfo) {
        reportInfo.f354o = true;
        jr.m120a().m126b(new C06196(this));
    }

    public final synchronized void m236d(ReportInfo reportInfo) {
        reportInfo.a_();
        jr.m120a().m126b(new C06207(this));
    }

    private synchronized void m223b() {
        if (!this.f361c) {
            if (this.f366h >= 0) {
                kf.m182a(3, f359a, "Transmit is in progress");
            } else {
                m227g();
                if (this.f365g.isEmpty()) {
                    this.f362d = f360b;
                    this.f366h = -1;
                } else {
                    this.f366h = 0;
                    jr.m120a().m126b(new C06218(this));
                }
            }
        }
    }

    private synchronized void m225e() {
        kp kpVar;
        lr.m318b();
        if (jk.m98a().f258b) {
            while (this.f366h < this.f365g.size()) {
                List list = this.f365g;
                int i = this.f366h;
                this.f366h = i + 1;
                kpVar = (kp) list.get(i);
                if (!kpVar.f354o) {
                    break;
                }
            }
            kpVar = null;
        } else {
            kf.m182a(3, f359a, "Network is not available, aborting transmission");
            kpVar = null;
        }
        if (kpVar == null) {
            m226f();
        } else {
            m229a(kpVar);
        }
    }

    private synchronized void m226f() {
        m227g();
        m230a(this.f365g);
        if (this.f361c) {
            kf.m182a(3, f359a, "Reporter paused");
            this.f362d = f360b;
        } else if (this.f365g.isEmpty()) {
            kf.m182a(3, f359a, "All reports sent successfully");
            this.f362d = f360b;
        } else {
            this.f362d <<= 1;
            kf.m182a(3, f359a, "One or more reports failed to send, backing off: " + this.f362d + "ms");
            jr.m120a().m125a(this.f367i, this.f362d);
        }
        this.f366h = -1;
    }

    public synchronized void m232b(List<ReportInfo> list) {
        lr.m318b();
        List list2 = (List) this.f364f.m151a();
        if (list2 != null) {
            list.addAll(list2);
        }
    }

    public synchronized void m230a(List<ReportInfo> list) {
        lr.m318b();
        this.f364f.m152a(new ArrayList(list));
    }

    private synchronized void m227g() {
        Iterator it = this.f365g.iterator();
        while (it.hasNext()) {
            kp kpVar = (kp) it.next();
            if (kpVar.f354o) {
                kf.m182a(3, f359a, "Url transmitted - " + kpVar.f356q + " Attempts: " + kpVar.f355p);
                it.remove();
            } else if (kpVar.f355p > kpVar.m219a()) {
                kf.m182a(3, f359a, "Exceeded max no of attempts - " + kpVar.f356q + " Attempts: " + kpVar.f355p);
                it.remove();
            } else if (System.currentTimeMillis() > kpVar.f353n && kpVar.f355p > 0) {
                kf.m182a(3, f359a, "Expired: Time expired - " + kpVar.f356q + " Attempts: " + kpVar.f355p);
                it.remove();
            }
        }
    }
}
