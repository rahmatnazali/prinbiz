package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.jk.C0150a;

public class jd implements ki {
    private static final String f592a;

    public static synchronized jd m552a() {
        jd jdVar;
        synchronized (jd.class) {
            jdVar = (jd) jr.m120a().m123a(jd.class);
        }
        return jdVar;
    }

    static {
        f592a = jd.class.getSimpleName();
    }

    public final void m560a(Context context) {
        ld.m265a(jq.class);
        kb.m157a();
        lm.m277a();
        li.m668a();
        jt.m131a();
        jk.m98a();
        je.m85a();
        jl.m105a();
        ji.m569a();
        je.m85a();
        jn.m581a();
        jh.m96a();
        jp.m593a();
    }

    public final void m561b() {
        jp.m594b();
        jh.f248a = null;
        jn.m583b();
        je.m88b();
        ji.m571b();
        jl.m106b();
        je.m88b();
        jk.m100b();
        jt.m132b();
        li.m669b();
        lm.m278b();
        kb.m158b();
        ld.m266b(jq.class);
    }

    public static String m553c() {
        jq i = m559i();
        if (i != null) {
            return Long.toString(i.f273c);
        }
        return null;
    }

    public static long m554d() {
        jq i = m559i();
        if (i != null) {
            return i.f273c;
        }
        return 0;
    }

    public static long m555e() {
        jq i = m559i();
        if (i != null) {
            return i.f274d;
        }
        return 0;
    }

    public static long m556f() {
        jq i = m559i();
        if (i != null) {
            return i.f275e;
        }
        return -1;
    }

    public static long m557g() {
        jq i = m559i();
        if (i != null) {
            return i.m116c();
        }
        return 0;
    }

    public static C0150a m558h() {
        return jk.m98a().m104c();
    }

    public static jq m559i() {
        ld c = lf.m651a().m663c();
        if (c == null) {
            return null;
        }
        return (jq) c.m267c(jq.class);
    }
}
