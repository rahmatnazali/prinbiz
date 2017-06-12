package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.sdk.lj.C0175a;
import java.util.Map;

public class hk implements ki {
    private static final String f454d;
    public C0577if f455a;
    public ix f456b;
    public ih f457c;

    static {
        f454d = hk.class.getSimpleName();
    }

    public static synchronized hk m392a() {
        hk hkVar;
        synchronized (hk.class) {
            hkVar = (hk) jr.m120a().m123a(hk.class);
        }
        return hkVar;
    }

    public final void m395a(Context context) {
        ld.m265a(ja.class);
        this.f456b = new ix();
        this.f455a = new C0577if();
        this.f457c = new ih();
        if (!lr.m313a(context, "android.permission.INTERNET")) {
            kf.m189b(f454d, "Application must declare permission: android.permission.INTERNET");
        }
        if (!lr.m313a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            kf.m196e(f454d, "It is highly recommended that the application declare permission: android.permission.ACCESS_NETWORK_STATE");
        }
    }

    public final void m396b() {
        if (this.f457c != null) {
            this.f457c.m233c();
            this.f457c = null;
        }
        if (this.f456b != null) {
            C0175a c0175a = this.f456b;
            li.m668a().m275b("UseHttps", c0175a);
            li.m668a().m275b("ReportUrl", c0175a);
            this.f456b = null;
        }
        if (this.f455a != null) {
            c0175a = this.f455a;
            jr.m120a().m127c(c0175a.f478a);
            kb.m157a().m166b("com.flurry.android.sdk.NetworkStateEvent", c0175a.f481d);
            kb.m157a().m166b("com.flurry.android.sdk.IdProviderUpdatedAdvertisingId", c0175a.f480c);
            kb.m157a().m166b("com.flurry.android.sdk.IdProviderFinishedEvent", c0175a.f479b);
            il.m23b();
            li.m668a().m275b("ProtonEnabled", c0175a);
            this.f455a = null;
        }
        ld.m266b(ja.class);
    }

    public static FlurryEventRecordStatus m391a(String str, String str2, Map<String, String> map) {
        ja c = m394c();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (c != null) {
            return c.m537a(str, jc.m83a(str2), (Map) map);
        }
        return flurryEventRecordStatus;
    }

    public static void m393a(String str, String str2, Throwable th) {
        ja c = m394c();
        if (c != null) {
            c.m544a(str, str2, th.getClass().getName(), th);
        }
    }

    public static ja m394c() {
        ld c = lf.m651a().m663c();
        if (c == null) {
            return null;
        }
        return (ja) c.m267c(ja.class);
    }
}
