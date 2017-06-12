package com.flurry.sdk;

import com.flurry.sdk.ii.C0580a;
import com.flurry.sdk.kl.C0157a;
import com.flurry.sdk.kn.C0161a;
import com.hiti.printerprotocol.RequestState;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.nntp.NNTPReply;

public class ih extends kq<ii> {
    public static long f502a;
    private static final String f503e;

    /* renamed from: com.flurry.sdk.ih.1 */
    class C05781 implements lc<List<ii>> {
        final /* synthetic */ ih f498a;

        C05781(ih ihVar) {
            this.f498a = ihVar;
        }

        public final kz<List<ii>> m450a(int i) {
            return new ky(new C0580a());
        }
    }

    /* renamed from: com.flurry.sdk.ih.2 */
    class C05792 implements C0157a<byte[], String> {
        final /* synthetic */ ii f499a;
        final /* synthetic */ ij f500b;
        final /* synthetic */ ih f501c;

        C05792(ih ihVar, ii iiVar, ij ijVar) {
            this.f501c = ihVar;
            this.f499a = iiVar;
            this.f500b = ijVar;
        }

        public final /* synthetic */ void m451a(kl klVar, Object obj) {
            Object obj2 = null;
            String str = (String) obj;
            kf.m182a(3, ih.f503e, "Pulse report to " + this.f499a.f516k + " for " + this.f499a.f518m.f147c + ", HTTP status code is: " + klVar.f729p);
            int i = klVar.f729p;
            ij ijVar = this.f500b;
            int i2 = (int) klVar.f727n;
            if (i2 >= 0) {
                ijVar.f118k = ((long) i2) + ijVar.f118k;
            } else if (ijVar.f118k <= 0) {
                ijVar.f118k = 0;
            }
            this.f500b.f112e = i;
            if (!klVar.m696c()) {
                Exception exception = klVar.f728o;
                Object obj3;
                if (klVar.f728o == null || !(klVar.f728o instanceof SocketTimeoutException)) {
                    obj3 = null;
                } else {
                    obj3 = 1;
                }
                if (klVar.f733t || r0 != null) {
                    obj2 = 1;
                }
                if (obj2 != null) {
                    if (klVar.m698e()) {
                        kf.m182a(3, ih.f503e, "Timeout occured when trying to connect to: " + this.f499a.f516k + ". Exception: " + klVar.f728o.getMessage());
                    } else {
                        kf.m182a(3, ih.f503e, "Manually managed http request timeout occured for: " + this.f499a.f516k);
                    }
                    ih.m452a(this.f501c, this.f500b, this.f499a);
                    return;
                }
                kf.m182a(3, ih.f503e, "Error occured when trying to connect to: " + this.f499a.f516k + ". Exception: " + exception.getMessage());
                ih.m454a(this.f501c, this.f500b, this.f499a, str);
            } else if (i >= NNTPReply.SERVER_READY_POSTING_ALLOWED && i < RequestState.REQUEST_CHECK_PRINT_COMPLETE) {
                ih.m456b(this.f501c, this.f500b, this.f499a);
            } else if (i < RequestState.REQUEST_CHECK_PRINT_COMPLETE || i >= NNTPReply.SERVICE_DISCONTINUED) {
                kf.m182a(3, ih.f503e, this.f499a.f518m.f147c + " report failed sending to : " + this.f499a.f516k);
                ih.m454a(this.f501c, this.f500b, this.f499a, str);
            } else {
                ih.m453a(this.f501c, this.f500b, this.f499a, klVar);
            }
        }
    }

    protected final /* synthetic */ void m458a(kp kpVar) {
        ii iiVar = (ii) kpVar;
        kf.m182a(3, f503e, "Sending next pulse report to " + iiVar.f516k + " at: " + iiVar.f357r);
        jd.m552a();
        long d = jd.m554d();
        if (d == 0) {
            d = f502a;
        }
        jd.m552a();
        long g = jd.m557g();
        if (g == 0) {
            g = System.currentTimeMillis() - d;
        }
        ij ijVar = new ij(iiVar, d, g, iiVar.f355p);
        lx klVar = new kl();
        klVar.f719f = iiVar.f357r;
        klVar.f710w = 100000;
        if (iiVar.f510e.equals(ip.POST)) {
            klVar.f739c = new kv();
            if (iiVar.f515j != null) {
                klVar.f738b = iiVar.f515j.getBytes();
            }
            klVar.f720g = C0161a.kPost;
        } else {
            klVar.f720g = C0161a.kGet;
        }
        klVar.f721h = iiVar.f513h * DNSConstants.PROBE_CONFLICT_INTERVAL;
        klVar.f722i = iiVar.f514i * DNSConstants.PROBE_CONFLICT_INTERVAL;
        klVar.f725l = true;
        klVar.f731r = true;
        klVar.f732s = (iiVar.f513h + iiVar.f514i) * DNSConstants.PROBE_CONFLICT_INTERVAL;
        Map map = iiVar.f511f;
        if (map != null) {
            for (String str : iiVar.f511f.keySet()) {
                klVar.m694a(str, (String) map.get(str));
            }
        }
        klVar.f723j = false;
        klVar.f737a = new C05792(this, iiVar, ijVar);
        jp.m593a().m177a((Object) this, klVar);
    }

    static {
        f503e = ih.class.getSimpleName();
    }

    public ih() {
        kq.f360b = 30000;
        this.f362d = kq.f360b;
    }

    protected final jy<List<ii>> m457a() {
        return new jy(jr.m120a().f284a.getFileStreamPath(".yflurryanpulsecallbackreporter"), ".yflurryanpulsecallbackreporter", 2, new C05781(this));
    }

    protected final synchronized void m459a(List<ii> list) {
        il.m20a().m44d();
    }

    protected final synchronized void m460b(List<ii> list) {
        il.m20a();
        List<im> e = il.m29e();
        if (e != null) {
            if (e.size() != 0) {
                kf.m182a(3, f503e, "Restoring " + e.size() + " from report queue.");
                for (im b : e) {
                    il.m20a().m42b(b);
                }
                il.m20a();
                for (im a : il.m26c()) {
                    for (ii iiVar : a.m60a()) {
                        if (!iiVar.f517l) {
                            kf.m182a(3, f503e, "Callback for " + iiVar.f518m.f147c + " to " + iiVar.f516k + " not completed.  Adding to reporter queue.");
                            list.add(iiVar);
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void m452a(ih ihVar, ij ijVar, ii iiVar) {
        il.m20a().m41b(ijVar);
        ihVar.m234c((kp) iiVar);
    }

    static /* synthetic */ void m454a(ih ihVar, ij ijVar, ii iiVar, String str) {
        boolean b = il.m20a().m43b(ijVar, str);
        kf.m182a(3, f503e, "Failed report retrying: " + b);
        if (b) {
            ihVar.m236d(iiVar);
        } else {
            ihVar.m234c((kp) iiVar);
        }
    }

    static /* synthetic */ void m456b(ih ihVar, ij ijVar, ii iiVar) {
        kf.m182a(3, f503e, iiVar.f518m.f147c + " report sent successfully to : " + iiVar.f516k);
        il.m20a().m38a(ijVar);
        ihVar.m234c((kp) iiVar);
    }

    static /* synthetic */ void m453a(ih ihVar, ij ijVar, ii iiVar, kl klVar) {
        String str = null;
        List a = klVar.m692a("Location");
        if (a != null && a.size() > 0) {
            str = ly.m347b((String) a.get(0), iiVar.f356q);
        }
        boolean a2 = il.m20a().m40a(ijVar, str);
        if (a2) {
            kf.m182a(3, f503e, "Received redirect url. Retrying: " + str);
        } else {
            kf.m182a(3, f503e, "Received redirect url. Retrying: false");
        }
        if (a2) {
            iiVar.f357r = str;
            klVar.f719f = str;
            str = "Location";
            if (klVar.f730q != null && klVar.f730q.f306a.containsKey(str)) {
                klVar.f730q.m146b(str);
            }
            jp.m593a().m177a((Object) ihVar, (lx) klVar);
            return;
        }
        ihVar.m234c((kp) iiVar);
    }
}
