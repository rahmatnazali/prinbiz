package com.flurry.sdk;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.id.C0566a;
import com.flurry.sdk.im.C0584a;
import com.flurry.sdk.kl.C0157a;
import com.flurry.sdk.kn.C0161a;
import com.flurry.sdk.lj.C0175a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.tftp.TFTP;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: com.flurry.sdk.if */
public class C0577if implements C0175a {
    private static final String f476e;
    private static String f477f;
    public final Runnable f478a;
    public final ka<jf> f479b;
    public final ka<jg> f480c;
    public final ka<jj> f481d;
    private final kj<hr> f482g;
    private final kj<hs> f483h;
    private jy<id> f484i;
    private jy<List<im>> f485j;
    private final ie f486k;
    private final jw<String, hv> f487l;
    private final List<im> f488m;
    private boolean f489n;
    private String f490o;
    private boolean f491p;
    private boolean f492q;
    private long f493r;
    private long f494s;
    private boolean f495t;
    private hs f496u;
    private boolean f497v;

    /* renamed from: com.flurry.sdk.if.1 */
    class C05671 extends lw {
        final /* synthetic */ C0577if f463a;

        C05671(C0577if c0577if) {
            this.f463a = c0577if;
        }

        public final void m402a() {
            this.f463a.m430f();
        }
    }

    /* renamed from: com.flurry.sdk.if.2 */
    class C05692 implements C0157a<byte[], byte[]> {
        final /* synthetic */ long f466a;
        final /* synthetic */ boolean f467b;
        final /* synthetic */ C0577if f468c;

        /* renamed from: com.flurry.sdk.if.2.1 */
        class C05681 extends lw {
            final /* synthetic */ byte[] f464a;
            final /* synthetic */ C05692 f465b;

            C05681(C05692 c05692, byte[] bArr) {
                this.f465b = c05692;
                this.f464a = bArr;
            }

            public final void m403a() {
                this.f465b.f468c.m414a(this.f465b.f466a, this.f465b.f467b, this.f464a);
            }
        }

        C05692(C0577if c0577if, long j, boolean z) {
            this.f468c = c0577if;
            this.f466a = j;
            this.f467b = z;
        }

        public final /* synthetic */ void m404a(kl klVar, Object obj) {
            hs hsVar = null;
            byte[] bArr = (byte[]) obj;
            int i = klVar.f729p;
            kf.m182a(3, C0577if.f476e, "Proton config request: HTTP status code is:" + i);
            if (i == NNTPReply.SERVICE_DISCONTINUED || i == 406 || i == NNTPReply.NO_NEWSGROUP_SELECTED || i == 415) {
                this.f468c.f493r = 10000;
                return;
            }
            if (klVar.m696c() && bArr != null) {
                hs hsVar2;
                jr.m120a().m126b(new C05681(this, bArr));
                try {
                    hsVar2 = (hs) this.f468c.f483h.m208b(bArr);
                } catch (Exception e) {
                    kf.m182a(5, C0577if.f476e, "Failed to decode proton config response: " + e);
                    hsVar2 = null;
                }
                if (C0577if.m424b(hsVar2)) {
                    hsVar = hsVar2;
                }
                if (hsVar != null) {
                    this.f468c.f493r = 10000;
                    this.f468c.f494s = this.f466a;
                    this.f468c.f495t = this.f467b;
                    this.f468c.f496u = hsVar;
                    this.f468c.m434h();
                    if (!this.f468c.f497v) {
                        this.f468c.f497v = true;
                        this.f468c.m423b("flurry.session_start", null);
                    }
                    this.f468c.m429e();
                }
            }
            if (hsVar == null) {
                long parseLong;
                long j = this.f468c.f493r << 1;
                if (i == 429) {
                    List a = klVar.m692a("Retry-After");
                    if (!a.isEmpty()) {
                        String str = (String) a.get(0);
                        kf.m182a(3, C0577if.f476e, "Server returned retry time: " + str);
                        try {
                            parseLong = Long.parseLong(str) * 1000;
                        } catch (NumberFormatException e2) {
                            kf.m182a(3, C0577if.f476e, "Server returned nonsensical retry time");
                        }
                        this.f468c.f493r = parseLong;
                        kf.m182a(3, C0577if.f476e, "Proton config request failed, backing off: " + this.f468c.f493r + "ms");
                        jr.m120a().m125a(this.f468c.f478a, this.f468c.f493r);
                    }
                }
                parseLong = j;
                this.f468c.f493r = parseLong;
                kf.m182a(3, C0577if.f476e, "Proton config request failed, backing off: " + this.f468c.f493r + "ms");
                jr.m120a().m125a(this.f468c.f478a, this.f468c.f493r);
            }
        }
    }

    /* renamed from: com.flurry.sdk.if.3 */
    class C05703 extends lw {
        final /* synthetic */ C0577if f469a;

        C05703(C0577if c0577if) {
            this.f469a = c0577if;
        }

        public final void m405a() {
            this.f469a.m430f();
        }
    }

    /* renamed from: com.flurry.sdk.if.4 */
    class C05714 implements ka<jf> {
        final /* synthetic */ C0577if f470a;

        C05714(C0577if c0577if) {
            this.f470a = c0577if;
        }

        public final /* bridge */ /* synthetic */ void m406a(jz jzVar) {
            this.f470a.m430f();
        }
    }

    /* renamed from: com.flurry.sdk.if.5 */
    class C05725 implements ka<jg> {
        final /* synthetic */ C0577if f471a;

        C05725(C0577if c0577if) {
            this.f471a = c0577if;
        }

        public final /* bridge */ /* synthetic */ void m407a(jz jzVar) {
            this.f471a.m430f();
        }
    }

    /* renamed from: com.flurry.sdk.if.6 */
    class C05736 implements ka<jj> {
        final /* synthetic */ C0577if f472a;

        C05736(C0577if c0577if) {
            this.f472a = c0577if;
        }

        public final /* bridge */ /* synthetic */ void m408a(jz jzVar) {
            if (((jj) jzVar).f612a) {
                this.f472a.m430f();
            }
        }
    }

    /* renamed from: com.flurry.sdk.if.7 */
    class C05747 implements lc<id> {
        final /* synthetic */ C0577if f473a;

        C05747(C0577if c0577if) {
            this.f473a = c0577if;
        }

        public final kz<id> m409a(int i) {
            return new C0566a();
        }
    }

    /* renamed from: com.flurry.sdk.if.8 */
    class C05758 implements lc<List<im>> {
        final /* synthetic */ C0577if f474a;

        C05758(C0577if c0577if) {
            this.f474a = c0577if;
        }

        public final kz<List<im>> m410a(int i) {
            return new ky(new C0584a());
        }
    }

    /* renamed from: com.flurry.sdk.if.9 */
    class C05769 extends lw {
        final /* synthetic */ C0577if f475a;

        C05769(C0577if c0577if) {
            this.f475a = c0577if;
        }

        public final void m411a() {
            this.f475a.m436i();
        }
    }

    static {
        f476e = C0577if.class.getSimpleName();
        f477f = "https://proton.flurry.com/sdk/v1/config";
    }

    public C0577if() {
        this.f478a = new C05671(this);
        this.f479b = new C05714(this);
        this.f480c = new C05725(this);
        this.f481d = new C05736(this);
        this.f482g = new kj("proton config request", new ir());
        this.f483h = new kj("proton config response", new is());
        this.f486k = new ie();
        this.f487l = new jw();
        this.f488m = new ArrayList();
        this.f491p = true;
        this.f493r = 10000;
        lj a = li.m668a();
        this.f489n = ((Boolean) a.m272a("ProtonEnabled")).booleanValue();
        a.m273a("ProtonEnabled", (C0175a) this);
        kf.m182a(4, f476e, "initSettings, protonEnabled = " + this.f489n);
        this.f490o = (String) a.m272a("ProtonConfigUrl");
        a.m273a("ProtonConfigUrl", (C0175a) this);
        kf.m182a(4, f476e, "initSettings, protonConfigUrl = " + this.f490o);
        this.f491p = ((Boolean) a.m272a("analyticsEnabled")).booleanValue();
        a.m273a("analyticsEnabled", (C0175a) this);
        kf.m182a(4, f476e, "initSettings, AnalyticsEnabled = " + this.f491p);
        kb.m157a().m164a("com.flurry.android.sdk.IdProviderFinishedEvent", this.f479b);
        kb.m157a().m164a("com.flurry.android.sdk.IdProviderUpdatedAdvertisingId", this.f480c);
        kb.m157a().m164a("com.flurry.android.sdk.NetworkStateEvent", this.f481d);
        this.f484i = new jy(jr.m120a().f284a.getFileStreamPath(".yflurryprotonconfig." + Long.toString(lr.m325i(jr.m120a().f287d), 16)), ".yflurryprotonconfig.", 1, new C05747(this));
        this.f485j = new jy(jr.m120a().f284a.getFileStreamPath(".yflurryprotonreport." + Long.toString(lr.m325i(jr.m120a().f287d), 16)), ".yflurryprotonreport.", 1, new C05758(this));
        jr.m120a().m126b(new C05769(this));
        jr.m120a().m126b(new lw() {
            final /* synthetic */ C0577if f461a;

            {
                this.f461a = r1;
            }

            public final void m400a() {
                this.f461a.m441k();
            }
        });
    }

    public final void m446a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -1720015653:
                if (str.equals("analyticsEnabled")) {
                    obj2 = 2;
                    break;
                }
                break;
            case 640941243:
                if (str.equals("ProtonEnabled")) {
                    obj2 = null;
                    break;
                }
                break;
            case 1591403975:
                if (str.equals("ProtonConfigUrl")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.f489n = ((Boolean) obj).booleanValue();
                kf.m182a(4, f476e, "onSettingUpdate, protonEnabled = " + this.f489n);
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.f490o = (String) obj;
                kf.m182a(4, f476e, "onSettingUpdate, protonConfigUrl = " + this.f490o);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.f491p = ((Boolean) obj).booleanValue();
                kf.m182a(4, f476e, "onSettingUpdate, AnalyticsEnabled = " + this.f491p);
            default:
                kf.m182a(6, f476e, "onSettingUpdate internal error!");
        }
    }

    public final synchronized void m444a() {
        if (this.f489n) {
            lr.m318b();
            jd.m552a();
            ih.f502a = jd.m554d();
            this.f497v = false;
            m430f();
        }
    }

    public final synchronized void m448b() {
        if (this.f489n) {
            lr.m318b();
            jd.m552a();
            m421b(jd.m554d());
            m439j();
        }
    }

    public final synchronized void m445a(long j) {
        if (this.f489n) {
            lr.m318b();
            m421b(j);
            m423b("flurry.session_end", null);
            jr.m120a().m126b(new lw() {
                final /* synthetic */ C0577if f462a;

                {
                    this.f462a = r1;
                }

                public final void m401a() {
                    this.f462a.m442l();
                }
            });
        }
    }

    public final synchronized void m449c() {
        if (this.f489n) {
            lr.m318b();
            m439j();
        }
    }

    public final synchronized void m447a(String str, Map<String, String> map) {
        if (this.f489n) {
            lr.m318b();
            m423b(str, (Map) map);
        }
    }

    private synchronized void m429e() {
        if (this.f489n) {
            lr.m318b();
            SharedPreferences sharedPreferences = jr.m120a().f284a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
            if (sharedPreferences.getBoolean("com.flurry.android.flurryAppInstall", true)) {
                m423b("flurry.app_install", null);
                Editor edit = sharedPreferences.edit();
                edit.putBoolean("com.flurry.android.flurryAppInstall", false);
                edit.apply();
            }
        }
    }

    private synchronized void m430f() {
        if (this.f489n) {
            lr.m318b();
            if (this.f492q && je.m85a().m94c()) {
                boolean z;
                long currentTimeMillis = System.currentTimeMillis();
                if (je.m85a().m95d()) {
                    z = false;
                } else {
                    z = true;
                }
                if (this.f496u != null) {
                    if (this.f495t != z) {
                        kf.m182a(3, f476e, "Limit ad tracking value has changed, purging");
                        this.f496u = null;
                    } else if (System.currentTimeMillis() < this.f494s + (this.f496u.f62b * 1000)) {
                        kf.m182a(3, f476e, "Cached Proton config valid, no need to refresh");
                        if (!this.f497v) {
                            this.f497v = true;
                            m423b("flurry.session_start", null);
                        }
                    } else if (System.currentTimeMillis() >= this.f494s + (this.f496u.f63c * 1000)) {
                        kf.m182a(3, f476e, "Cached Proton config expired, purging");
                        this.f496u = null;
                        this.f487l.m142a();
                    }
                }
                jp.m593a().m176a((Object) this);
                kf.m182a(3, f476e, "Requesting proton config");
                byte[] g = m433g();
                if (g != null) {
                    String str;
                    lx klVar = new kl();
                    if (TextUtils.isEmpty(this.f490o)) {
                        str = f477f;
                    } else {
                        str = this.f490o;
                    }
                    klVar.f719f = str;
                    klVar.f710w = TFTP.DEFAULT_TIMEOUT;
                    klVar.f720g = C0161a.kPost;
                    klVar.m694a("Content-Type", "application/x-flurry;version=2");
                    klVar.m694a("Accept", "application/x-flurry;version=2");
                    klVar.m694a("FM-Checksum", Integer.toString(kj.m204a(g)));
                    klVar.f739c = new kv();
                    klVar.f740d = new kv();
                    klVar.f738b = g;
                    klVar.f737a = new C05692(this, currentTimeMillis, z);
                    jp.m593a().m177a((Object) this, klVar);
                }
            }
        }
    }

    private byte[] m433g() {
        try {
            Object hrVar = new hr();
            hrVar.f50a = jr.m120a().f287d;
            hrVar.f51b = lo.m288a(jr.m120a().f284a);
            hrVar.f52c = lo.m289b(jr.m120a().f284a);
            hrVar.f53d = js.m128a();
            hrVar.f54e = 3;
            jn.m581a();
            hrVar.f55f = jn.m584c();
            hrVar.f56g = !je.m85a().m95d();
            hrVar.f57h = new hu();
            hrVar.f57h.f69a = new ho();
            hrVar.f57h.f69a.f29a = Build.MODEL;
            hrVar.f57h.f69a.f30b = Build.BRAND;
            hrVar.f57h.f69a.f31c = Build.ID;
            hrVar.f57h.f69a.f32d = Build.DEVICE;
            hrVar.f57h.f69a.f33e = Build.PRODUCT;
            hrVar.f57h.f69a.f34f = VERSION.RELEASE;
            hrVar.f58i = new ArrayList();
            for (Entry entry : Collections.unmodifiableMap(je.m85a().f242a).entrySet()) {
                ht htVar = new ht();
                htVar.f67a = ((jm) entry.getKey()).f265c;
                if (((jm) entry.getKey()).f266d) {
                    htVar.f68b = new String((byte[]) entry.getValue());
                } else {
                    htVar.f68b = lr.m317b((byte[]) entry.getValue());
                }
                hrVar.f58i.add(htVar);
            }
            Location e = ji.m569a().m580e();
            if (e != null) {
                hrVar.f59j = new hy();
                hrVar.f59j.f75a = new hx();
                hrVar.f59j.f75a.f72a = lr.m306a(e.getLatitude());
                hrVar.f59j.f75a.f73b = lr.m306a(e.getLongitude());
                hrVar.f59j.f75a.f74c = (float) lr.m306a((double) e.getAccuracy());
            }
            String str = (String) li.m668a().m272a("UserId");
            if (!str.equals(XmlPullParser.NO_NAMESPACE)) {
                hrVar.f60k = new ib();
                hrVar.f60k.f86a = str;
            }
            return this.f482g.m207a(hrVar);
        } catch (Exception e2) {
            kf.m182a(5, f476e, "Proton config request failed with exception: " + e2);
            return null;
        }
    }

    private static boolean m424b(hs hsVar) {
        if (hsVar == null) {
            return false;
        }
        boolean z;
        hq hqVar = hsVar.f65e;
        if (!(hqVar == null || hqVar.f45a == null)) {
            for (int i = 0; i < hqVar.f45a.size(); i++) {
                hp hpVar = (hp) hqVar.f45a.get(i);
                if (hpVar != null) {
                    if (!(hpVar.f36b.equals(XmlPullParser.NO_NAMESPACE) || hpVar.f35a == -1 || hpVar.f39e.equals(XmlPullParser.NO_NAMESPACE))) {
                        List<hv> list = hpVar.f37c;
                        if (list != null) {
                            for (hv hvVar : list) {
                                if (!hvVar.f70a.equals(XmlPullParser.NO_NAMESPACE)) {
                                    if ((hvVar instanceof hw) && ((hw) hvVar).f458c.equals(XmlPullParser.NO_NAMESPACE)) {
                                        kf.m182a(3, f476e, "An event trigger is missing a param name");
                                        z = false;
                                        break;
                                    }
                                } else {
                                    kf.m182a(3, f476e, "An event is missing a name");
                                    z = false;
                                    break;
                                }
                            }
                        }
                        z = true;
                        if (z) {
                        }
                    }
                    kf.m182a(3, f476e, "A callback template is missing required values");
                    z = false;
                    break;
                }
            }
        }
        z = true;
        if (z && (hsVar.f65e == null || hsVar.f65e.f49e == null || !hsVar.f65e.f49e.equals(XmlPullParser.NO_NAMESPACE))) {
            return true;
        }
        kf.m182a(3, f476e, "Config response is missing required values.");
        return false;
    }

    private void m434h() {
        if (this.f496u != null) {
            kf.m182a(5, f476e, "Processing config response");
            il.m22a(this.f496u.f65e.f47c);
            il.m24b(this.f496u.f65e.f48d * DNSConstants.PROBE_CONFLICT_INTERVAL);
            in a = in.m64a();
            String str = this.f496u.f65e.f49e;
            if (!(str == null || str.endsWith(".do"))) {
                kf.m182a(5, in.f155b, "overriding analytics agent report URL without an endpoint, are you sure?");
            }
            a.f157a = str;
            if (this.f489n) {
                li.m668a().m274a("analyticsEnabled", (Object) Boolean.valueOf(this.f496u.f66f.f88b));
            }
            this.f487l.m142a();
            hq hqVar = this.f496u.f65e;
            if (hqVar != null) {
                List<hp> list = hqVar.f45a;
                if (list != null) {
                    for (hp hpVar : list) {
                        if (hpVar != null) {
                            List<Object> list2 = hpVar.f37c;
                            if (list2 != null) {
                                for (Object obj : list2) {
                                    if (!(obj == null || TextUtils.isEmpty(obj.f70a))) {
                                        obj.f71b = hpVar;
                                        this.f487l.m144a(obj.f70a, obj);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private synchronized void m423b(String str, Map<String, String> map) {
        kf.m182a(3, f476e, "Event triggered: " + str);
        if (!this.f491p) {
            kf.m196e(f476e, "Analytics and pulse have been disabled.");
        } else if (this.f496u == null) {
            kf.m182a(3, f476e, "Config response is empty. No events to fire.");
        } else {
            lr.m318b();
            if (!TextUtils.isEmpty(str)) {
                List<hv> a = this.f487l.m140a((Object) str);
                if (a == null) {
                    kf.m182a(3, f476e, "No events to fire. Returning.");
                } else if (a.size() == 0) {
                    kf.m182a(3, f476e, "No events to fire. Returning.");
                } else {
                    iq iqVar;
                    long currentTimeMillis = System.currentTimeMillis();
                    boolean z = map != null;
                    Object obj = -1;
                    switch (str.hashCode()) {
                        case 645204782:
                            if (str.equals("flurry.session_end")) {
                                obj = 1;
                                break;
                            }
                            break;
                        case 1371447545:
                            if (str.equals("flurry.app_install")) {
                                obj = 2;
                                break;
                            }
                            break;
                        case 1579613685:
                            if (str.equals("flurry.session_start")) {
                                obj = null;
                                break;
                            }
                            break;
                    }
                    switch (obj) {
                        case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                            iqVar = iq.SESSION_START;
                            break;
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            iqVar = iq.SESSION_END;
                            break;
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            iqVar = iq.INSTALL;
                            break;
                        default:
                            iqVar = iq.APPLICATION_EVENT;
                            break;
                    }
                    Map hashMap = new HashMap();
                    for (hv hvVar : a) {
                        Object obj2 = null;
                        if (hvVar instanceof hw) {
                            kf.m182a(4, f476e, "Event contains triggers.");
                            String[] strArr = ((hw) hvVar).f459d;
                            if (strArr == null) {
                                kf.m182a(4, f476e, "Template does not contain trigger values. Firing.");
                                obj2 = 1;
                            } else if (strArr.length == 0) {
                                kf.m182a(4, f476e, "Template does not contain trigger values. Firing.");
                                obj2 = 1;
                            } else if (map == null) {
                                kf.m182a(4, f476e, "Publisher has not passed in params list. Not firing.");
                            }
                            String str2 = (String) map.get(((hw) hvVar).f458c);
                            if (str2 == null) {
                                kf.m182a(4, f476e, "Publisher params has no value associated with proton key. Not firing.");
                            } else {
                                int i = 0;
                                while (i < strArr.length) {
                                    if (strArr[i].equals(str2)) {
                                        obj = 1;
                                        if (obj != null) {
                                            kf.m182a(4, f476e, "Publisher params list does not match proton param values. Not firing.");
                                        } else {
                                            kf.m182a(4, f476e, "Publisher params match proton values. Firing.");
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                                obj = obj2;
                                if (obj != null) {
                                    kf.m182a(4, f476e, "Publisher params match proton values. Firing.");
                                } else {
                                    kf.m182a(4, f476e, "Publisher params list does not match proton param values. Not firing.");
                                }
                            }
                        }
                        hp hpVar = hvVar.f71b;
                        if (hpVar == null) {
                            kf.m182a(3, f476e, "Template is empty. Not firing current event.");
                        } else {
                            kf.m182a(3, f476e, "Creating callback report for partner: " + hpVar.f36b);
                            Map hashMap2 = new HashMap();
                            hashMap2.put("event_name", str);
                            hashMap2.put("event_time_millis", String.valueOf(currentTimeMillis));
                            String a2 = this.f486k.m399a(hpVar.f39e, hashMap2);
                            String str3 = null;
                            if (hpVar.f40f != null) {
                                str3 = this.f486k.m399a(hpVar.f40f, hashMap2);
                            }
                            hashMap.put(Long.valueOf(hpVar.f35a), new ii(hpVar.f36b, hpVar.f35a, a2, System.currentTimeMillis() + 259200000, this.f496u.f65e.f46b, hpVar.f41g, hpVar.f38d, hpVar.f44j, hpVar.f43i, hpVar.f42h, str3));
                        }
                    }
                    if (hashMap.size() != 0) {
                        jd.m552a();
                        long d = jd.m554d();
                        jd.m552a();
                        im imVar = new im(str, z, d, jd.m557g(), iqVar, hashMap);
                        if ("flurry.session_end".equals(str)) {
                            kf.m182a(3, f476e, "Storing Pulse callbacks for event: " + str);
                            this.f488m.add(imVar);
                        } else {
                            kf.m182a(3, f476e, "Firing Pulse callbacks for event: " + str);
                            il.m20a().m39a(imVar);
                        }
                    }
                }
            }
        }
    }

    private synchronized void m421b(long j) {
        Iterator it = this.f488m.iterator();
        while (it.hasNext()) {
            if (j == ((im) it.next()).f145a) {
                it.remove();
            }
        }
    }

    private synchronized void m436i() {
        id idVar = (id) this.f484i.m151a();
        if (idVar != null) {
            hs hsVar;
            try {
                hsVar = (hs) this.f483h.m208b(idVar.f93c);
            } catch (Exception e) {
                kf.m182a(5, f476e, "Failed to decode saved proton config response: " + e);
                this.f484i.m153b();
                hsVar = null;
            }
            if (!C0577if.m424b(hsVar)) {
                hsVar = null;
            }
            if (hsVar != null) {
                kf.m182a(4, f476e, "Loaded saved proton config response");
                this.f493r = 10000;
                this.f494s = idVar.f91a;
                this.f495t = idVar.f92b;
                this.f496u = hsVar;
                m434h();
            }
        }
        this.f492q = true;
        jr.m120a().m126b(new C05703(this));
    }

    private synchronized void m414a(long j, boolean z, byte[] bArr) {
        if (bArr != null) {
            kf.m182a(4, f476e, "Saving proton config response");
            id idVar = new id();
            idVar.f91a = j;
            idVar.f92b = z;
            idVar.f93c = bArr;
            this.f484i.m152a(idVar);
        }
    }

    private synchronized void m439j() {
        if (this.f491p) {
            kf.m182a(4, f476e, "Sending " + this.f488m.size() + " queued reports.");
            for (im imVar : this.f488m) {
                kf.m182a(3, f476e, "Firing Pulse callbacks for event: " + imVar.f147c);
                il.m20a().m39a(imVar);
            }
            m443m();
        } else {
            kf.m196e(f476e, "Analytics disabled, not sending pulse reports.");
        }
    }

    private synchronized void m441k() {
        kf.m182a(4, f476e, "Loading queued report data.");
        List list = (List) this.f485j.m151a();
        if (list != null) {
            this.f488m.addAll(list);
        }
    }

    private synchronized void m442l() {
        kf.m182a(4, f476e, "Saving queued report data.");
        this.f485j.m152a(this.f488m);
    }

    private synchronized void m443m() {
        this.f488m.clear();
        this.f485j.m153b();
    }
}
