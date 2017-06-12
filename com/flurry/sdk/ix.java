package com.flurry.sdk;

import android.widget.Toast;
import com.flurry.sdk.kl.C0157a;
import com.flurry.sdk.kn.C0161a;
import com.flurry.sdk.lj.C0175a;
import java.util.Arrays;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;

public class ix extends kr implements C0175a {
    private static final String f532a;
    private static String f533f;
    private static String f534g;
    private String f535h;
    private boolean f536i;

    /* renamed from: com.flurry.sdk.ix.1 */
    class C05881 implements C0157a<byte[], Void> {
        final /* synthetic */ String f527a;
        final /* synthetic */ String f528b;
        final /* synthetic */ ix f529c;

        /* renamed from: com.flurry.sdk.ix.1.1 */
        class C01401 implements Runnable {
            final /* synthetic */ int f193a;
            final /* synthetic */ C05881 f194b;

            C01401(C05881 c05881, int i) {
                this.f194b = c05881;
                this.f193a = i;
            }

            public final void run() {
                Toast.makeText(jr.m120a().f284a, "SD HTTP Response Code: " + this.f193a, 0).show();
            }
        }

        C05881(ix ixVar, String str, String str2) {
            this.f529c = ixVar;
            this.f527a = str;
            this.f528b = str2;
        }

        public final /* synthetic */ void m498a(kl klVar, Object obj) {
            int i = klVar.f729p;
            if (i > 0) {
                kf.m196e(ix.f532a, "Analytics report sent.");
                kf.m182a(3, ix.f532a, "FlurryDataSender: report " + this.f527a + " sent. HTTP response: " + i);
                if (kf.m190c() <= 3 && kf.m195d()) {
                    jr.m120a().m124a(new C01401(this, i));
                }
                this.f529c.m505a(this.f527a, this.f528b, i);
                this.f529c.m240b();
                return;
            }
            this.f529c.m237a(this.f527a);
        }
    }

    /* renamed from: com.flurry.sdk.ix.2 */
    class C05892 extends lw {
        final /* synthetic */ int f530a;
        final /* synthetic */ ix f531b;

        C05892(ix ixVar, int i) {
            this.f531b = ixVar;
            this.f530a = i;
        }

        public final void m499a() {
            if (this.f530a == NNTPReply.SERVER_READY_POSTING_ALLOWED) {
                hk.m392a();
                ja c = hk.m394c();
                if (c != null) {
                    c.f575j = true;
                }
            }
        }
    }

    static {
        f532a = ix.class.getSimpleName();
        f533f = "http://data.flurry.com/aap.do";
        f534g = "https://data.flurry.com/aap.do";
    }

    public ix() {
        this((byte) 0);
    }

    private ix(byte b) {
        super("Analytics", ix.class.getSimpleName());
        this.e = "AnalyticsData_";
        lj a = li.m668a();
        this.f536i = ((Boolean) a.m272a("UseHttps")).booleanValue();
        a.m273a("UseHttps", (C0175a) this);
        kf.m182a(4, f532a, "initSettings, UseHttps = " + this.f536i);
        String str = (String) a.m272a("ReportUrl");
        a.m273a("ReportUrl", (C0175a) this);
        m503b(str);
        kf.m182a(4, f532a, "initSettings, ReportUrl = " + str);
        m240b();
    }

    public final void m504a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -239660092:
                if (str.equals("UseHttps")) {
                    obj2 = null;
                    break;
                }
                break;
            case 1650629499:
                if (str.equals("ReportUrl")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.f536i = ((Boolean) obj).booleanValue();
                kf.m182a(4, f532a, "onSettingUpdate, UseHttps = " + this.f536i);
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                String str2 = (String) obj;
                m503b(str2);
                kf.m182a(4, f532a, "onSettingUpdate, ReportUrl = " + str2);
            default:
                kf.m182a(6, f532a, "onSettingUpdate internal error!");
        }
    }

    private void m503b(String str) {
        if (!(str == null || str.endsWith(".do"))) {
            kf.m182a(5, f532a, "overriding analytics agent report URL without an endpoint, are you sure?");
        }
        this.f535h = str;
    }

    protected final void m505a(String str, String str2, int i) {
        jr.m120a().m126b(new C05892(this, i));
        super.m238a(str, str2, i);
    }

    protected final void m506a(byte[] bArr, String str, String str2) {
        String str3;
        if (this.f535h != null) {
            str3 = this.f535h;
        } else if (this.f536i) {
            str3 = f534g;
        } else {
            str3 = f533f;
        }
        kf.m182a(4, f532a, "FlurryDataSender: start upload data " + Arrays.toString(bArr) + " with id = " + str + " to " + str3);
        lx klVar = new kl();
        klVar.f719f = str3;
        klVar.f710w = 100000;
        klVar.f720g = C0161a.kPost;
        klVar.m694a("Content-Type", "application/octet-stream");
        klVar.f739c = new kv();
        klVar.f738b = bArr;
        klVar.f737a = new C05881(this, str, str2);
        jp.m593a().m177a((Object) this, klVar);
    }
}
