package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.TextUtils;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.sdk.iy.C0590a;
import com.flurry.sdk.le.C0172a;
import com.flurry.sdk.lj.C0175a;
import com.google.android.gms.common.ConnectionResult;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class ja implements C0175a {
    static final String f559a;
    static int f560b;
    static int f561c;
    static int f562d;
    static int f563e;
    static int f564f;
    private final List<iv> f565A;
    private boolean f566B;
    private int f567C;
    private final List<it> f568D;
    private int f569E;
    private int f570F;
    private final hl f571G;
    WeakReference<ld> f572g;
    File f573h;
    jy<List<iy>> f574i;
    public boolean f575j;
    boolean f576k;
    String f577l;
    byte f578m;
    Long f579n;
    boolean f580o;
    final ka<jf> f581p;
    private final AtomicInteger f582q;
    private final AtomicInteger f583r;
    private final AtomicInteger f584s;
    private final ka<le> f585t;
    private long f586u;
    private int f587v;
    private final List<iy> f588w;
    private final Map<String, List<String>> f589x;
    private final Map<String, String> f590y;
    private final Map<String, iu> f591z;

    /* renamed from: com.flurry.sdk.ja.7 */
    class C01437 implements Runnable {
        final /* synthetic */ String f221a;
        final /* synthetic */ Map f222b;
        final /* synthetic */ ja f223c;

        C01437(ja jaVar, String str, Map map) {
            this.f223c = jaVar;
            this.f221a = str;
            this.f222b = map;
        }

        public final void run() {
            hk.m392a().f455a.m447a(this.f221a, this.f222b);
        }
    }

    /* renamed from: com.flurry.sdk.ja.8 */
    static /* synthetic */ class C01448 {
        static final /* synthetic */ int[] f224a;

        static {
            f224a = new int[C0172a.m268a().length];
            try {
                f224a[C0172a.f393a - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f224a[C0172a.f395c - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f224a[C0172a.f396d - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f224a[C0172a.f397e - 1] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.ja.1 */
    class C05911 implements ka<le> {
        final /* synthetic */ ja f544a;

        C05911(ja jaVar) {
            this.f544a = jaVar;
        }

        public final /* synthetic */ void m516a(jz jzVar) {
            le leVar = (le) jzVar;
            if (this.f544a.f572g == null || leVar.f672b == this.f544a.f572g.get()) {
                ja jaVar;
                switch (C01448.f224a[leVar.f673c - 1]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        C0175a c0175a = this.f544a;
                        Context context = (Context) leVar.f671a.get();
                        c0175a.f572g = new WeakReference(leVar.f672b);
                        lj a = li.m668a();
                        c0175a.f576k = ((Boolean) a.m272a("LogEvents")).booleanValue();
                        a.m273a("LogEvents", c0175a);
                        kf.m182a(4, ja.f559a, "initSettings, LogEvents = " + c0175a.f576k);
                        c0175a.f577l = (String) a.m272a("UserId");
                        a.m273a("UserId", c0175a);
                        kf.m182a(4, ja.f559a, "initSettings, UserId = " + c0175a.f577l);
                        c0175a.f578m = ((Byte) a.m272a("Gender")).byteValue();
                        a.m273a("Gender", c0175a);
                        kf.m182a(4, ja.f559a, "initSettings, Gender = " + c0175a.f578m);
                        c0175a.f579n = (Long) a.m272a("Age");
                        a.m273a("Age", c0175a);
                        kf.m182a(4, ja.f559a, "initSettings, BirthDate = " + c0175a.f579n);
                        c0175a.f580o = ((Boolean) a.m272a("analyticsEnabled")).booleanValue();
                        a.m273a("analyticsEnabled", c0175a);
                        kf.m182a(4, ja.f559a, "initSettings, AnalyticsEnabled = " + c0175a.f580o);
                        c0175a.f573h = context.getFileStreamPath(".flurryagent." + Integer.toString(jr.m120a().f287d.hashCode(), 16));
                        c0175a.f574i = new jy(context.getFileStreamPath(".yflurryreport." + Long.toString(lr.m325i(jr.m120a().f287d), 16)), ".yflurryreport.", 1, new lc<List<iy>>() {
                            final /* synthetic */ ja f537a;

                            {
                                this.f537a = r1;
                            }

                            public final kz<List<iy>> m509a(int i) {
                                return new ky(new C0590a());
                            }
                        });
                        c0175a.m542a(context);
                        c0175a.m546a(true);
                        if (hk.m392a().f455a != null) {
                            jr.m120a().m126b(new lw() {
                                final /* synthetic */ ja f538a;

                                {
                                    this.f538a = r1;
                                }

                                public final void m510a() {
                                    hk.m392a().f455a.m444a();
                                }
                            });
                        }
                        jr.m120a().m126b(new lw() {
                            final /* synthetic */ ja f539a;

                            {
                                this.f539a = r1;
                            }

                            public final void m511a() {
                                this.f539a.m532e();
                            }
                        });
                        jr.m120a().m126b(new lw() {
                            final /* synthetic */ ja f540a;

                            {
                                this.f540a = r1;
                            }

                            public final void m512a() {
                                ja.m531d(this.f540a);
                            }
                        });
                        if (je.m85a().m94c()) {
                            jr.m120a().m126b(new lw() {
                                final /* synthetic */ ja f541a;

                                {
                                    this.f541a = r1;
                                }

                                public final void m513a() {
                                    ja jaVar = this.f541a;
                                    jd.m552a();
                                    jaVar.m527a(true, jd.m554d());
                                }
                            });
                        } else {
                            kb.m157a().m164a("com.flurry.android.sdk.IdProviderFinishedEvent", c0175a.f581p);
                        }
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        jaVar = this.f544a;
                        leVar.f671a.get();
                        jaVar.m540a();
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        jaVar = this.f544a;
                        leVar.f671a.get();
                        jaVar.m547b();
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                        kb.m157a().m166b("com.flurry.android.sdk.FlurrySessionEvent", this.f544a.f585t);
                        this.f544a.m541a(leVar.f674d);
                    default:
                }
            }
        }
    }

    /* renamed from: com.flurry.sdk.ja.2 */
    class C05922 extends lw {
        final /* synthetic */ long f545a;
        final /* synthetic */ ja f546b;

        C05922(ja jaVar, long j) {
            this.f546b = jaVar;
            this.f545a = j;
        }

        public final void m517a() {
            hk.m392a().f455a.m445a(this.f545a);
        }
    }

    /* renamed from: com.flurry.sdk.ja.3 */
    class C05933 extends lw {
        final /* synthetic */ ja f547a;

        C05933(ja jaVar) {
            this.f547a = jaVar;
        }

        public final void m518a() {
            this.f547a.m535f();
        }
    }

    /* renamed from: com.flurry.sdk.ja.4 */
    class C05944 extends lw {
        final /* synthetic */ long f548a;
        final /* synthetic */ long f549b;
        final /* synthetic */ long f550c;
        final /* synthetic */ int f551d;
        final /* synthetic */ ja f552e;

        C05944(ja jaVar, long j, long j2, long j3, int i) {
            this.f552e = jaVar;
            this.f548a = j;
            this.f549b = j2;
            this.f550c = j3;
            this.f551d = i;
        }

        public final void m519a() {
            iy a = this.f552e.m539a(this.f548a, this.f549b, this.f550c, this.f551d);
            this.f552e.f588w.clear();
            this.f552e.f588w.add(a);
            this.f552e.m549d();
        }
    }

    /* renamed from: com.flurry.sdk.ja.5 */
    class C05965 extends lw {
        final /* synthetic */ ja f554a;

        /* renamed from: com.flurry.sdk.ja.5.1 */
        class C05951 extends lw {
            final /* synthetic */ C05965 f553a;

            C05951(C05965 c05965) {
                this.f553a = c05965;
            }

            public final void m520a() {
                hk.m392a().f457c.f361c = true;
            }
        }

        C05965(ja jaVar) {
            this.f554a = jaVar;
        }

        public final void m521a() {
            if (this.f554a.f580o && hk.m392a().f455a != null) {
                hk.m392a().f455a.m449c();
            }
            if (hk.m392a().f457c != null) {
                jr.m120a().m126b(new C05951(this));
            }
        }
    }

    /* renamed from: com.flurry.sdk.ja.6 */
    class C05976 extends lw {
        final /* synthetic */ long f555a;
        final /* synthetic */ ja f556b;

        C05976(ja jaVar, long j) {
            this.f556b = jaVar;
            this.f555a = j;
        }

        public final void m522a() {
            this.f556b.m527a(false, this.f555a);
        }
    }

    /* renamed from: com.flurry.sdk.ja.9 */
    class C05999 implements ka<jf> {
        final /* synthetic */ ja f558a;

        /* renamed from: com.flurry.sdk.ja.9.1 */
        class C05981 extends lw {
            final /* synthetic */ C05999 f557a;

            C05981(C05999 c05999) {
                this.f557a = c05999;
            }

            public final void m523a() {
                ja jaVar = this.f557a.f558a;
                jd.m552a();
                jaVar.m527a(true, jd.m554d());
            }
        }

        C05999(ja jaVar) {
            this.f558a = jaVar;
        }

        public final /* synthetic */ void m524a(jz jzVar) {
            jr.m120a().m126b(new C05981(this));
        }
    }

    static {
        f559a = ja.class.getSimpleName();
        f560b = 100;
        f561c = 10;
        f562d = DNSConstants.PROBE_CONFLICT_INTERVAL;
        f563e = 160000;
        f564f = 50;
    }

    public ja() {
        this.f582q = new AtomicInteger(0);
        this.f583r = new AtomicInteger(0);
        this.f584s = new AtomicInteger(0);
        this.f585t = new C05911(this);
        this.f587v = -1;
        this.f588w = new ArrayList();
        this.f589x = new HashMap();
        this.f590y = new HashMap();
        this.f591z = new HashMap();
        this.f565A = new ArrayList();
        this.f566B = true;
        this.f567C = 0;
        this.f568D = new ArrayList();
        this.f569E = 0;
        this.f570F = 0;
        this.f580o = true;
        this.f571G = new hl();
        this.f581p = new C05999(this);
        kb.m157a().m164a("com.flurry.android.sdk.FlurrySessionEvent", this.f585t);
    }

    public final synchronized void m540a() {
        this.f587v = lp.m299e();
        if (hk.m392a().f457c != null) {
            jr.m120a().m126b(new lw() {
                final /* synthetic */ ja f542a;

                {
                    this.f542a = r1;
                }

                public final void m514a() {
                    hk.m392a().f457c.m235d();
                }
            });
        }
        if (this.f580o && hk.m392a().f455a != null) {
            jr.m120a().m126b(new lw() {
                final /* synthetic */ ja f543a;

                {
                    this.f543a = r1;
                }

                public final void m515a() {
                    hk.m392a().f455a.m448b();
                }
            });
        }
    }

    public final synchronized void m547b() {
        m546a(false);
        jd.m552a();
        long d = jd.m554d();
        jd.m552a();
        long f = jd.m556f();
        jd.m552a();
        long j = 0;
        jq i = jd.m559i();
        if (i != null) {
            j = i.f276f;
        }
        jd.m552a();
        int i2 = jd.m558h().f255e;
        jd.m552a();
        m529b(jd.m556f());
        if (this.f580o && hk.m392a().f455a != null) {
            jr.m120a().m126b(new C05922(this, d));
        }
        jr.m120a().m126b(new C05933(this));
        if (je.m85a().m94c()) {
            jr.m120a().m126b(new C05944(this, d, f, j, i2));
        }
    }

    public final synchronized void m541a(long j) {
        kb.m157a().m162a(this.f581p);
        jr.m120a().m126b(new C05965(this));
        if (je.m85a().m94c()) {
            jr.m120a().m126b(new C05976(this, j));
        }
        li.m668a().m275b("Gender", this);
        li.m668a().m275b("UserId", this);
        li.m668a().m275b("Age", this);
        li.m668a().m275b("LogEvents", this);
    }

    public final void m543a(String str, Object obj) {
        int i = -1;
        switch (str.hashCode()) {
            case -1752163738:
                if (str.equals("UserId")) {
                    i = 1;
                    break;
                }
                break;
            case -1720015653:
                if (str.equals("analyticsEnabled")) {
                    i = 4;
                    break;
                }
                break;
            case -738063011:
                if (str.equals("LogEvents")) {
                    i = 0;
                    break;
                }
                break;
            case 65759:
                if (str.equals("Age")) {
                    i = 3;
                    break;
                }
                break;
            case 2129321697:
                if (str.equals("Gender")) {
                    i = 2;
                    break;
                }
                break;
        }
        switch (i) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.f576k = ((Boolean) obj).booleanValue();
                kf.m182a(4, f559a, "onSettingUpdate, LogEvents = " + this.f576k);
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.f577l = (String) obj;
                kf.m182a(4, f559a, "onSettingUpdate, UserId = " + this.f577l);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.f578m = ((Byte) obj).byteValue();
                kf.m182a(4, f559a, "onSettingUpdate, Gender = " + this.f578m);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.f579n = (Long) obj;
                kf.m182a(4, f559a, "onSettingUpdate, Birthdate = " + this.f579n);
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.f580o = ((Boolean) obj).booleanValue();
                kf.m182a(4, f559a, "onSettingUpdate, AnalyticsEnabled = " + this.f580o);
            default:
                kf.m182a(6, f559a, "onSettingUpdate internal error!");
        }
    }

    final void m542a(Context context) {
        if (context instanceof Activity) {
            Bundle extras = ((Activity) context).getIntent().getExtras();
            if (extras != null) {
                kf.m182a(3, f559a, "Launch Options Bundle is present " + extras.toString());
                for (String str : extras.keySet()) {
                    if (str != null) {
                        Object obj = extras.get(str);
                        this.f589x.put(str, new ArrayList(Arrays.asList(new String[]{obj != null ? obj.toString() : "null"})));
                        kf.m182a(3, f559a, "Launch options Key: " + str + ". Its value: " + r1);
                    }
                }
            }
        }
    }

    @TargetApi(18)
    final void m546a(boolean z) {
        boolean z2;
        int intExtra;
        Exception exception;
        int i;
        Object obj;
        float f;
        int i2 = -1;
        if (z) {
            this.f590y.put("boot.time", Long.toString(System.currentTimeMillis() - SystemClock.elapsedRealtime()));
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                this.f590y.put("disk.size.total.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.f590y.put("disk.size.available.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.f590y.put("disk.size.total.external", Long.toString(statFs2.getAvailableBlocksLong()));
                this.f590y.put("disk.size.available.external", Long.toString(statFs2.getAvailableBlocksLong()));
            } else {
                this.f590y.put("disk.size.total.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.f590y.put("disk.size.available.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.f590y.put("disk.size.total.external", Long.toString((long) statFs2.getAvailableBlocks()));
                this.f590y.put("disk.size.available.external", Long.toString((long) statFs2.getAvailableBlocks()));
            }
            jl.m105a();
            this.f590y.put("carrier.name", jl.m107c());
            jl.m105a();
            this.f590y.put("carrier.details", jl.m108d());
        }
        ActivityManager activityManager = (ActivityManager) jr.m120a().f284a.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        this.f590y.put("memory.available" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        if (VERSION.SDK_INT >= 16) {
            this.f590y.put("memory.total" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        }
        try {
            Intent registerReceiver = jr.m120a().f284a.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (registerReceiver != null) {
                int intExtra2 = registerReceiver.getIntExtra(NotificationCompatApi24.CATEGORY_STATUS, -1);
                z2 = intExtra2 == 2 || intExtra2 == 5;
                try {
                    intExtra = registerReceiver.getIntExtra("level", -1);
                } catch (Exception e) {
                    exception = e;
                    i = -1;
                    kf.m182a(5, f559a, "Error getting battery status: " + obj);
                    i2 = i;
                    i = -1;
                    f = ((float) i2) / ((float) i);
                    this.f590y.put("battery.charging" + (z ? ".end" : ".start"), Boolean.toString(z2));
                    this.f590y.put("battery.remaining" + (z ? ".end" : ".start"), Float.toString(f));
                }
                try {
                    i = registerReceiver.getIntExtra("scale", -1);
                    i2 = intExtra;
                } catch (Exception e2) {
                    Exception exception2 = e2;
                    i = intExtra;
                    exception = exception2;
                    kf.m182a(5, f559a, "Error getting battery status: " + obj);
                    i2 = i;
                    i = -1;
                    f = ((float) i2) / ((float) i);
                    if (z) {
                    }
                    this.f590y.put("battery.charging" + (z ? ".end" : ".start"), Boolean.toString(z2));
                    if (z) {
                    }
                    this.f590y.put("battery.remaining" + (z ? ".end" : ".start"), Float.toString(f));
                }
            }
            z2 = false;
            i = -1;
        } catch (Exception e3) {
            obj = e3;
            z2 = false;
            i = -1;
            kf.m182a(5, f559a, "Error getting battery status: " + obj);
            i2 = i;
            i = -1;
            f = ((float) i2) / ((float) i);
            if (z) {
            }
            this.f590y.put("battery.charging" + (z ? ".end" : ".start"), Boolean.toString(z2));
            if (z) {
            }
            this.f590y.put("battery.remaining" + (z ? ".end" : ".start"), Float.toString(f));
        }
        f = ((float) i2) / ((float) i);
        if (z) {
        }
        this.f590y.put("battery.charging" + (z ? ".end" : ".start"), Boolean.toString(z2));
        if (z) {
        }
        this.f590y.put("battery.remaining" + (z ? ".end" : ".start"), Float.toString(f));
    }

    private synchronized void m529b(long j) {
        for (iv ivVar : this.f565A) {
            if (ivVar.f185b && !ivVar.f186c) {
                ivVar.m76a(j);
            }
        }
    }

    final synchronized iy m539a(long j, long j2, long j3, int i) {
        iy iyVar;
        String d;
        Map f;
        int i2;
        iz izVar = new iz();
        izVar.f199a = jn.m581a().m592i();
        izVar.f200b = j;
        izVar.f201c = j2;
        izVar.f202d = j3;
        izVar.f203e = this.f590y;
        jd.m552a();
        jq i3 = jd.m559i();
        if (i3 != null) {
            d = i3.m117d();
        } else {
            d = null;
        }
        izVar.f204f = d;
        jd.m552a();
        i3 = jd.m559i();
        if (i3 != null) {
            d = i3.m118e();
        } else {
            d = null;
        }
        izVar.f205g = d;
        jd.m552a();
        i3 = jd.m559i();
        if (i3 != null) {
            f = i3.m119f();
        } else {
            f = null;
        }
        izVar.f206h = f;
        jh.m96a();
        izVar.f207i = jh.m97b();
        jh.m96a();
        izVar.f208j = TimeZone.getDefault().getID();
        izVar.f209k = i;
        if (this.f587v != -1) {
            i2 = this.f587v;
        } else {
            i2 = lp.m299e();
        }
        izVar.f210l = i2;
        if (this.f577l == null) {
            d = XmlPullParser.NO_NAMESPACE;
        } else {
            d = this.f577l;
        }
        izVar.f211m = d;
        izVar.f212n = ji.m569a().m580e();
        izVar.f213o = this.f570F;
        izVar.f214p = this.f578m;
        izVar.f215q = this.f579n;
        izVar.f216r = this.f591z;
        izVar.f217s = this.f565A;
        izVar.f218t = this.f566B;
        izVar.f220v = this.f568D;
        izVar.f219u = this.f569E;
        try {
            iyVar = new iy(izVar);
        } catch (IOException e) {
            kf.m182a(5, f559a, "Error creating analytics session report: " + e);
            iyVar = null;
        }
        if (iyVar == null) {
            kf.m196e(f559a, "New session report wasn't created");
        }
        return iyVar;
    }

    public final synchronized void m548c() {
        this.f570F++;
    }

    public final synchronized FlurryEventRecordStatus m537a(String str, String str2, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (map != null) {
            if (!TextUtils.isEmpty(str2)) {
                map.put("\ue8ffsid+Tumblr", str2);
                flurryEventRecordStatus = m538a(str, (Map) map, false);
                kf.m182a(5, f559a, "logEvent status for syndication:" + flurryEventRecordStatus);
            }
        }
        return flurryEventRecordStatus;
    }

    public final synchronized FlurryEventRecordStatus m538a(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        FlurryEventRecordStatus flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventRecorded;
        if (this.f580o) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            jd.m552a();
            long e = elapsedRealtime - jd.m555e();
            String b = lr.m316b(str);
            if (b.length() == 0) {
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
            } else {
                iu iuVar = (iu) this.f591z.get(b);
                if (iuVar != null) {
                    iuVar.f183a++;
                    kf.m196e(f559a, "Event count incremented: " + b);
                    flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
                } else if (this.f591z.size() < f560b) {
                    iuVar = new iu();
                    iuVar.f183a = 1;
                    this.f591z.put(b, iuVar);
                    kf.m196e(f559a, "Event count started: " + b);
                    flurryEventRecordStatus = flurryEventRecordStatus2;
                } else {
                    kf.m196e(f559a, "Too many different events. Event not counted: " + b);
                    flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventUniqueCountExceeded;
                }
                if (!this.f576k || this.f565A.size() >= f562d || this.f567C >= f563e) {
                    this.f566B = false;
                } else {
                    Map emptyMap;
                    if (map == null) {
                        emptyMap = Collections.emptyMap();
                    } else {
                        Map<String, String> map2 = map;
                    }
                    if (emptyMap.size() > f561c) {
                        kf.m196e(f559a, "MaxEventParams exceeded: " + emptyMap.size());
                        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventParamsCountExceeded;
                    } else {
                        iv ivVar = new iv(this.f582q.incrementAndGet(), b, emptyMap, e, z);
                        if (ivVar.m79b().length + this.f567C <= f563e) {
                            this.f565A.add(ivVar);
                            this.f567C = ivVar.m79b().length + this.f567C;
                            flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
                            if (this.f580o && hk.m392a().f455a != null) {
                                jr.m120a().m126b(new C01437(this, b, emptyMap));
                            }
                        } else {
                            this.f567C = f563e;
                            this.f566B = false;
                            kf.m196e(f559a, "Event Log size exceeded. No more event details logged.");
                            flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventLogCountExceeded;
                        }
                    }
                }
            }
        } else {
            flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventAnalyticsDisabled;
            kf.m196e(f559a, "Analytics has been disabled, not logging event.");
        }
        return flurryEventRecordStatus;
    }

    public final synchronized void m545a(String str, Map<String, String> map) {
        for (iv ivVar : this.f565A) {
            Object obj;
            if (ivVar.f185b && ivVar.f187d == 0 && ivVar.f184a.equals(str)) {
                obj = 1;
                continue;
            } else {
                obj = null;
                continue;
            }
            if (obj != null) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                jd.m552a();
                elapsedRealtime -= jd.m555e();
                if (map != null && map.size() > 0 && this.f567C < f563e) {
                    int length = this.f567C - ivVar.m79b().length;
                    Map hashMap = new HashMap(ivVar.m75a());
                    ivVar.m77a((Map) map);
                    if (ivVar.m79b().length + length > f563e) {
                        ivVar.m78b(hashMap);
                        this.f566B = false;
                        this.f567C = f563e;
                        kf.m196e(f559a, "Event Log size exceeded. No more event details logged.");
                    } else if (ivVar.m75a().size() > f561c) {
                        kf.m196e(f559a, "MaxEventParams exceeded on endEvent: " + ivVar.m75a().size());
                        ivVar.m78b(hashMap);
                    } else {
                        this.f567C = length + ivVar.m79b().length;
                    }
                }
                ivVar.m76a(elapsedRealtime);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void m544a(java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.Throwable r13) {
        /*
        r9 = this;
        r0 = 0;
        monitor-enter(r9);
        if (r10 == 0) goto L_0x0055;
    L_0x0004:
        r1 = "uncaught";
        r1 = r1.equals(r10);	 Catch:{ all -> 0x0099 }
        if (r1 == 0) goto L_0x0055;
    L_0x000c:
        r1 = 1;
    L_0x000d:
        r2 = r9.f569E;	 Catch:{ all -> 0x0099 }
        r2 = r2 + 1;
        r9.f569E = r2;	 Catch:{ all -> 0x0099 }
        r2 = r9.f568D;	 Catch:{ all -> 0x0099 }
        r2 = r2.size();	 Catch:{ all -> 0x0099 }
        r3 = f564f;	 Catch:{ all -> 0x0099 }
        if (r2 >= r3) goto L_0x0057;
    L_0x001d:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x0099 }
        r2 = java.lang.Long.valueOf(r0);	 Catch:{ all -> 0x0099 }
        r0 = new com.flurry.sdk.it;	 Catch:{ all -> 0x0099 }
        r1 = r9.f583r;	 Catch:{ all -> 0x0099 }
        r1 = r1.incrementAndGet();	 Catch:{ all -> 0x0099 }
        r2 = r2.longValue();	 Catch:{ all -> 0x0099 }
        r4 = r10;
        r5 = r11;
        r6 = r12;
        r7 = r13;
        r0.<init>(r1, r2, r4, r5, r6, r7);	 Catch:{ all -> 0x0099 }
        r1 = r9.f568D;	 Catch:{ all -> 0x0099 }
        r1.add(r0);	 Catch:{ all -> 0x0099 }
        r1 = f559a;	 Catch:{ all -> 0x0099 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0099 }
        r3 = "Error logged: ";
        r2.<init>(r3);	 Catch:{ all -> 0x0099 }
        r0 = r0.f177a;	 Catch:{ all -> 0x0099 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x0099 }
        r0 = r0.toString();	 Catch:{ all -> 0x0099 }
        com.flurry.sdk.kf.m196e(r1, r0);	 Catch:{ all -> 0x0099 }
    L_0x0053:
        monitor-exit(r9);
        return;
    L_0x0055:
        r1 = r0;
        goto L_0x000d;
    L_0x0057:
        if (r1 == 0) goto L_0x00a0;
    L_0x0059:
        r8 = r0;
    L_0x005a:
        r0 = r9.f568D;	 Catch:{ all -> 0x0099 }
        r0 = r0.size();	 Catch:{ all -> 0x0099 }
        if (r8 >= r0) goto L_0x0053;
    L_0x0062:
        r0 = r9.f568D;	 Catch:{ all -> 0x0099 }
        r0 = r0.get(r8);	 Catch:{ all -> 0x0099 }
        r0 = (com.flurry.sdk.it) r0;	 Catch:{ all -> 0x0099 }
        r1 = r0.f177a;	 Catch:{ all -> 0x0099 }
        if (r1 == 0) goto L_0x009c;
    L_0x006e:
        r1 = "uncaught";
        r0 = r0.f177a;	 Catch:{ all -> 0x0099 }
        r0 = r1.equals(r0);	 Catch:{ all -> 0x0099 }
        if (r0 != 0) goto L_0x009c;
    L_0x0078:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x0099 }
        r2 = java.lang.Long.valueOf(r0);	 Catch:{ all -> 0x0099 }
        r0 = new com.flurry.sdk.it;	 Catch:{ all -> 0x0099 }
        r1 = r9.f583r;	 Catch:{ all -> 0x0099 }
        r1 = r1.incrementAndGet();	 Catch:{ all -> 0x0099 }
        r2 = r2.longValue();	 Catch:{ all -> 0x0099 }
        r4 = r10;
        r5 = r11;
        r6 = r12;
        r7 = r13;
        r0.<init>(r1, r2, r4, r5, r6, r7);	 Catch:{ all -> 0x0099 }
        r1 = r9.f568D;	 Catch:{ all -> 0x0099 }
        r1.set(r8, r0);	 Catch:{ all -> 0x0099 }
        goto L_0x0053;
    L_0x0099:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x009c:
        r0 = r8 + 1;
        r8 = r0;
        goto L_0x005a;
    L_0x00a0:
        r0 = f559a;	 Catch:{ all -> 0x0099 }
        r1 = "Max errors logged. No more errors logged.";
        com.flurry.sdk.kf.m196e(r0, r1);	 Catch:{ all -> 0x0099 }
        goto L_0x0053;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.ja.a(java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m527a(boolean r21, long r22) {
        /*
        r20 = this;
        monitor-enter(r20);
        r0 = r20;
        r2 = r0.f580o;	 Catch:{ all -> 0x008c }
        if (r2 != 0) goto L_0x0011;
    L_0x0007:
        r2 = 3;
        r3 = f559a;	 Catch:{ all -> 0x008c }
        r4 = "Analytics disabled, not sending agent report.";
        com.flurry.sdk.kf.m182a(r2, r3, r4);	 Catch:{ all -> 0x008c }
    L_0x000f:
        monitor-exit(r20);
        return;
    L_0x0011:
        if (r21 != 0) goto L_0x001d;
    L_0x0013:
        r0 = r20;
        r2 = r0.f588w;	 Catch:{ all -> 0x008c }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x008c }
        if (r2 != 0) goto L_0x000f;
    L_0x001d:
        r2 = 3;
        r3 = f559a;	 Catch:{ all -> 0x008c }
        r4 = "generating agent report";
        com.flurry.sdk.kf.m182a(r2, r3, r4);	 Catch:{ all -> 0x008c }
        r19 = 0;
        r3 = new com.flurry.sdk.iw;	 Catch:{ Exception -> 0x008f }
        r2 = com.flurry.sdk.jr.m120a();	 Catch:{ Exception -> 0x008f }
        r4 = r2.f287d;	 Catch:{ Exception -> 0x008f }
        r2 = com.flurry.sdk.jn.m581a();	 Catch:{ Exception -> 0x008f }
        r5 = r2.m592i();	 Catch:{ Exception -> 0x008f }
        r0 = r20;
        r6 = r0.f575j;	 Catch:{ Exception -> 0x008f }
        r2 = com.flurry.sdk.je.m85a();	 Catch:{ Exception -> 0x008f }
        r7 = r2.m95d();	 Catch:{ Exception -> 0x008f }
        r0 = r20;
        r8 = r0.f586u;	 Catch:{ Exception -> 0x008f }
        r0 = r20;
        r12 = r0.f588w;	 Catch:{ Exception -> 0x008f }
        r2 = com.flurry.sdk.je.m85a();	 Catch:{ Exception -> 0x008f }
        r2 = r2.f242a;	 Catch:{ Exception -> 0x008f }
        r13 = java.util.Collections.unmodifiableMap(r2);	 Catch:{ Exception -> 0x008f }
        r0 = r20;
        r2 = r0.f571G;	 Catch:{ Exception -> 0x008f }
        r14 = r2.m13a();	 Catch:{ Exception -> 0x008f }
        r0 = r20;
        r15 = r0.f589x;	 Catch:{ Exception -> 0x008f }
        r2 = com.flurry.sdk.jt.m131a();	 Catch:{ Exception -> 0x008f }
        r16 = r2.m134c();	 Catch:{ Exception -> 0x008f }
        r17 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x008f }
        r10 = r22;
        r3.<init>(r4, r5, r6, r7, r8, r10, r12, r13, r14, r15, r16, r17);	 Catch:{ Exception -> 0x008f }
        r2 = r3.f192a;	 Catch:{ Exception -> 0x008f }
    L_0x0074:
        if (r2 != 0) goto L_0x00a7;
    L_0x0076:
        r2 = f559a;	 Catch:{ all -> 0x008c }
        r3 = "Error generating report";
        com.flurry.sdk.kf.m196e(r2, r3);	 Catch:{ all -> 0x008c }
    L_0x007d:
        r0 = r20;
        r2 = r0.f588w;	 Catch:{ all -> 0x008c }
        r2.clear();	 Catch:{ all -> 0x008c }
        r0 = r20;
        r2 = r0.f574i;	 Catch:{ all -> 0x008c }
        r2.m153b();	 Catch:{ all -> 0x008c }
        goto L_0x000f;
    L_0x008c:
        r2 = move-exception;
        monitor-exit(r20);
        throw r2;
    L_0x008f:
        r2 = move-exception;
        r3 = f559a;	 Catch:{ all -> 0x008c }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008c }
        r5 = "Exception while generating report: ";
        r4.<init>(r5);	 Catch:{ all -> 0x008c }
        r2 = r4.append(r2);	 Catch:{ all -> 0x008c }
        r2 = r2.toString();	 Catch:{ all -> 0x008c }
        com.flurry.sdk.kf.m196e(r3, r2);	 Catch:{ all -> 0x008c }
        r2 = r19;
        goto L_0x0074;
    L_0x00a7:
        r3 = 3;
        r4 = f559a;	 Catch:{ all -> 0x008c }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008c }
        r6 = "generated report of size ";
        r5.<init>(r6);	 Catch:{ all -> 0x008c }
        r6 = r2.length;	 Catch:{ all -> 0x008c }
        r5 = r5.append(r6);	 Catch:{ all -> 0x008c }
        r6 = " with ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x008c }
        r0 = r20;
        r6 = r0.f588w;	 Catch:{ all -> 0x008c }
        r6 = r6.size();	 Catch:{ all -> 0x008c }
        r5 = r5.append(r6);	 Catch:{ all -> 0x008c }
        r6 = " reports.";
        r5 = r5.append(r6);	 Catch:{ all -> 0x008c }
        r5 = r5.toString();	 Catch:{ all -> 0x008c }
        com.flurry.sdk.kf.m182a(r3, r4, r5);	 Catch:{ all -> 0x008c }
        r3 = com.flurry.sdk.hk.m392a();	 Catch:{ all -> 0x008c }
        r3 = r3.f456b;	 Catch:{ all -> 0x008c }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008c }
        r4.<init>();	 Catch:{ all -> 0x008c }
        r5 = com.flurry.sdk.js.m128a();	 Catch:{ all -> 0x008c }
        r4 = r4.append(r5);	 Catch:{ all -> 0x008c }
        r4 = r4.toString();	 Catch:{ all -> 0x008c }
        r5 = com.flurry.sdk.jr.m120a();	 Catch:{ all -> 0x008c }
        r5 = r5.f287d;	 Catch:{ all -> 0x008c }
        r3.m241b(r2, r5, r4);	 Catch:{ all -> 0x008c }
        goto L_0x007d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.ja.a(boolean, long):void");
    }

    public final synchronized void m549d() {
        kf.m182a(4, f559a, "Saving persistent agent data.");
        this.f574i.m152a(this.f588w);
    }

    private synchronized void m532e() {
        kf.m182a(4, f559a, "Loading persistent session report data.");
        List list = (List) this.f574i.m151a();
        if (list != null) {
            this.f588w.addAll(list);
        } else if (this.f573h.exists()) {
            kf.m182a(4, f559a, "Legacy persistent agent data found, converting.");
            jb a = hn.m17a(this.f573h);
            if (a != null) {
                boolean z = a.f227a;
                long j = a.f228b;
                if (j <= 0) {
                    jd.m552a();
                    j = jd.m554d();
                }
                this.f575j = z;
                this.f586u = j;
                m535f();
                Collection unmodifiableList = Collections.unmodifiableList(a.f229c);
                if (unmodifiableList != null) {
                    this.f588w.addAll(unmodifiableList);
                }
            }
            this.f573h.delete();
            m549d();
        }
    }

    private void m535f() {
        Editor edit = jr.m120a().f284a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putBoolean("com.flurry.sdk.previous_successful_report", this.f575j);
        edit.putLong("com.flurry.sdk.initial_run_time", this.f586u);
        edit.commit();
    }

    static /* synthetic */ void m531d(ja jaVar) {
        SharedPreferences sharedPreferences = jr.m120a().f284a.getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
        jaVar.f575j = sharedPreferences.getBoolean("com.flurry.sdk.previous_successful_report", false);
        jd.m552a();
        jaVar.f586u = sharedPreferences.getLong("com.flurry.sdk.initial_run_time", jd.m554d());
    }
}
