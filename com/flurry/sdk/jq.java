package com.flurry.sdk;

import android.content.Context;
import android.os.SystemClock;
import com.flurry.sdk.le.C0172a;
import com.google.android.gms.common.ConnectionResult;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.net.ftp.FTPClient;

public class jq {
    static final String f271a;
    WeakReference<ld> f272b;
    public volatile long f273c;
    public volatile long f274d;
    public volatile long f275e;
    public volatile long f276f;
    private final ka<le> f277g;
    private volatile long f278h;
    private String f279i;
    private String f280j;
    private Map<String, String> f281k;

    /* renamed from: com.flurry.sdk.jq.2 */
    class C01512 extends LinkedHashMap<String, String> {
        final /* synthetic */ jq f269a;

        C01512(jq jqVar) {
            this.f269a = jqVar;
        }

        protected final boolean removeEldestEntry(Entry<String, String> entry) {
            return size() > 10;
        }
    }

    /* renamed from: com.flurry.sdk.jq.4 */
    static /* synthetic */ class C01524 {
        static final /* synthetic */ int[] f270a;

        static {
            f270a = new int[C0172a.m268a().length];
            try {
                f270a[C0172a.f393a - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f270a[C0172a.f395c - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f270a[C0172a.f396d - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f270a[C0172a.f397e - 1] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.jq.1 */
    class C06051 implements ka<le> {
        final /* synthetic */ jq f619a;

        C06051(jq jqVar) {
            this.f619a = jqVar;
        }

        public final /* synthetic */ void m595a(jz jzVar) {
            le leVar = (le) jzVar;
            if (this.f619a.f272b == null || leVar.f672b == this.f619a.f272b.get()) {
                jq jqVar;
                switch (C01524.f270a[leVar.f673c - 1]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        jq jqVar2 = this.f619a;
                        ld ldVar = leVar.f672b;
                        Context context = (Context) leVar.f671a.get();
                        jqVar2.f272b = new WeakReference(ldVar);
                        jqVar2.f273c = System.currentTimeMillis();
                        jqVar2.f274d = SystemClock.elapsedRealtime();
                        if (ldVar == null || context == null) {
                            kf.m182a(3, jq.f271a, "Flurry session id cannot be created.");
                        } else {
                            kf.m182a(3, jq.f271a, "Flurry session id started:" + jqVar2.f273c);
                            le leVar2 = new le();
                            leVar2.f671a = new WeakReference(context);
                            leVar2.f672b = ldVar;
                            leVar2.f673c = C0172a.f394b;
                            leVar2.m155b();
                        }
                        jr.m120a().m126b(new C06063(jqVar2));
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        jqVar = this.f619a;
                        leVar.f671a.get();
                        jqVar.m112a();
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        jqVar = this.f619a;
                        leVar.f671a.get();
                        jqVar.f275e = SystemClock.elapsedRealtime() - jqVar.f274d;
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                        kb.m157a().m166b("com.flurry.android.sdk.FlurrySessionEvent", this.f619a.f277g);
                        jq.m111b();
                    default:
                }
            }
        }
    }

    /* renamed from: com.flurry.sdk.jq.3 */
    class C06063 extends lw {
        final /* synthetic */ jq f620a;

        C06063(jq jqVar) {
            this.f620a = jqVar;
        }

        public final void m596a() {
            ji.m569a().m578c();
        }
    }

    static {
        f271a = jq.class.getSimpleName();
    }

    public jq() {
        this.f277g = new C06051(this);
        this.f273c = 0;
        this.f274d = 0;
        this.f275e = -1;
        this.f276f = 0;
        this.f278h = 0;
        kb.m157a().m164a("com.flurry.android.sdk.FlurrySessionEvent", this.f277g);
        this.f281k = new C01512(this);
    }

    public final synchronized void m112a() {
        long j = lf.m651a().f682a;
        if (j > 0) {
            this.f276f = (System.currentTimeMillis() - j) + this.f276f;
        }
    }

    public static void m111b() {
    }

    public final synchronized long m116c() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.f274d;
        if (elapsedRealtime <= this.f278h) {
            elapsedRealtime = this.f278h + 1;
            this.f278h = elapsedRealtime;
        }
        this.f278h = elapsedRealtime;
        return this.f278h;
    }

    public final synchronized void m113a(String str) {
        this.f279i = str;
    }

    public final synchronized void m115b(String str) {
        this.f280j = str;
    }

    public final synchronized String m117d() {
        return this.f279i;
    }

    public final synchronized String m118e() {
        return this.f280j;
    }

    public final synchronized void m114a(String str, String str2) {
        this.f281k.put(str, str2);
    }

    public final synchronized Map<String, String> m119f() {
        return this.f281k;
    }
}
