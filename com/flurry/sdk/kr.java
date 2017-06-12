package com.flurry.sdk;

import com.flurry.sdk.ks.C0630a;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class kr {
    private ka<jj> f369a;
    public final String f370b;
    public Set<String> f371c;
    public kt f372d;
    public String f373e;

    /* renamed from: com.flurry.sdk.kr.a */
    public interface C0163a {
    }

    /* renamed from: com.flurry.sdk.kr.1 */
    class C06221 implements ka<jj> {
        final /* synthetic */ kr f643a;

        C06221(kr krVar) {
            this.f643a = krVar;
        }

        public final /* synthetic */ void m616a(jz jzVar) {
            jj jjVar = (jj) jzVar;
            kf.m182a(4, this.f643a.f370b, "onNetworkStateChanged : isNetworkEnable = " + jjVar.f612a);
            if (jjVar.f612a) {
                this.f643a.m240b();
            }
        }
    }

    /* renamed from: com.flurry.sdk.kr.2 */
    class C06232 extends lw {
        final /* synthetic */ String f644a;
        final /* synthetic */ kr f645b;

        C06232(kr krVar, String str) {
            this.f645b = krVar;
            this.f644a = str;
        }

        public final void m617a() {
            this.f645b.f372d = new kt(this.f644a);
        }
    }

    /* renamed from: com.flurry.sdk.kr.3 */
    class C06243 extends lw {
        final /* synthetic */ byte[] f646a;
        final /* synthetic */ String f647b;
        final /* synthetic */ String f648c;
        final /* synthetic */ kr f649d;

        C06243(kr krVar, byte[] bArr, String str, String str2) {
            this.f649d = krVar;
            this.f646a = bArr;
            this.f647b = str;
            this.f648c = str2;
        }

        public final void m618a() {
            kr krVar = this.f649d;
            byte[] bArr = this.f646a;
            String str = this.f647b;
            str = krVar.f373e + str + "_" + this.f648c;
            ks ksVar = new ks(bArr);
            String str2 = ksVar.f377a;
            new jy(jr.m120a().f284a.getFileStreamPath(ks.m243a(str2)), ".yflurrydatasenderblock.", 1, new C06265(krVar)).m152a(ksVar);
            kf.m182a(5, krVar.f370b, "Saving Block File " + str2 + " at " + jr.m120a().f284a.getFileStreamPath(ks.m243a(str2)));
            krVar.f372d.m260a(ksVar, str);
        }
    }

    /* renamed from: com.flurry.sdk.kr.4 */
    class C06254 extends lw {
        final /* synthetic */ C0163a f650a;
        final /* synthetic */ kr f651b;

        C06254(kr krVar) {
            this.f651b = krVar;
            this.f650a = null;
        }

        public final void m619a() {
            kr krVar = this.f651b;
            if (jk.m98a().f258b) {
                List<String> arrayList = new ArrayList(krVar.f372d.f382c.keySet());
                if (arrayList.isEmpty()) {
                    kf.m182a(4, krVar.f370b, "No more reports to send.");
                    return;
                }
                for (String str : arrayList) {
                    if (krVar.m242c()) {
                        List a = krVar.f372d.m259a(str);
                        kf.m182a(4, krVar.f370b, "Number of not sent blocks = " + a.size());
                        for (int i = 0; i < a.size(); i++) {
                            String str2 = (String) a.get(i);
                            if (!krVar.f371c.contains(str2)) {
                                if (!krVar.m242c()) {
                                    break;
                                }
                                ks ksVar = (ks) new jy(jr.m120a().f284a.getFileStreamPath(ks.m243a(str2)), ".yflurrydatasenderblock.", 1, new C06276(krVar)).m151a();
                                if (ksVar == null) {
                                    kf.m182a(6, krVar.f370b, "Internal ERROR! Cannot read!");
                                    krVar.f372d.m261a(str2, str);
                                } else {
                                    byte[] bArr = ksVar.f378b;
                                    if (bArr == null || bArr.length == 0) {
                                        kf.m182a(6, krVar.f370b, "Internal ERROR! Report is empty!");
                                        krVar.f372d.m261a(str2, str);
                                    } else {
                                        kf.m182a(5, krVar.f370b, "Reading block info " + str2);
                                        krVar.f371c.add(str2);
                                        krVar.m239a(bArr, str2, str);
                                    }
                                }
                            }
                        }
                    } else {
                        return;
                    }
                }
                return;
            }
            kf.m182a(5, krVar.f370b, "Reports were not sent! No Internet connection!");
        }
    }

    /* renamed from: com.flurry.sdk.kr.5 */
    class C06265 implements lc<ks> {
        final /* synthetic */ kr f652a;

        C06265(kr krVar) {
            this.f652a = krVar;
        }

        public final kz<ks> m620a(int i) {
            return new C0630a();
        }
    }

    /* renamed from: com.flurry.sdk.kr.6 */
    class C06276 implements lc<ks> {
        final /* synthetic */ kr f653a;

        C06276(kr krVar) {
            this.f653a = krVar;
        }

        public final kz<ks> m621a(int i) {
            return new C0630a();
        }
    }

    /* renamed from: com.flurry.sdk.kr.7 */
    class C06287 extends lw {
        final /* synthetic */ String f654a;
        final /* synthetic */ String f655b;
        final /* synthetic */ kr f656c;

        C06287(kr krVar, String str, String str2) {
            this.f656c = krVar;
            this.f654a = str;
            this.f655b = str2;
        }

        public final void m622a() {
            if (!this.f656c.f372d.m261a(this.f654a, this.f655b)) {
                kf.m182a(6, this.f656c.f370b, "Internal error. Block wasn't deleted with id = " + this.f654a);
            }
            if (!this.f656c.f371c.remove(this.f654a)) {
                kf.m182a(6, this.f656c.f370b, "Internal error. Block with id = " + this.f654a + " was not in progress state");
            }
        }
    }

    /* renamed from: com.flurry.sdk.kr.8 */
    class C06298 extends lw {
        final /* synthetic */ String f657a;
        final /* synthetic */ kr f658b;

        C06298(kr krVar, String str) {
            this.f658b = krVar;
            this.f657a = str;
        }

        public final void m623a() {
            if (!this.f658b.f371c.remove(this.f657a)) {
                kf.m182a(6, this.f658b.f370b, "Internal error. Block with id = " + this.f657a + " was not in progress state");
            }
        }
    }

    public abstract void m239a(byte[] bArr, String str, String str2);

    public kr(String str, String str2) {
        this.f371c = new HashSet();
        this.f373e = "defaultDataKey_";
        this.f369a = new C06221(this);
        this.f370b = str2;
        kb.m157a().m164a("com.flurry.android.sdk.NetworkStateEvent", this.f369a);
        jr.m120a().m126b(new C06232(this, str));
    }

    public final void m240b() {
        jr.m120a().m126b(new C06254(this));
    }

    public void m238a(String str, String str2, int i) {
        jr.m120a().m126b(new C06287(this, str, str2));
    }

    public final void m237a(String str) {
        jr.m120a().m126b(new C06298(this, str));
    }

    public final void m241b(byte[] bArr, String str, String str2) {
        if (bArr == null || bArr.length == 0) {
            kf.m182a(6, this.f370b, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        jr.m120a().m126b(new C06243(this, bArr, str, str2));
        m240b();
    }

    final boolean m242c() {
        return this.f371c.size() <= 5;
    }
}
