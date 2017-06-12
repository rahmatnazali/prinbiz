package com.flurry.sdk;

import com.flurry.sdk.kn.C0162c;
import java.io.InputStream;
import java.io.OutputStream;

public final class kl<RequestObjectType, ResponseObjectType> extends kn {
    public C0157a<RequestObjectType, ResponseObjectType> f737a;
    public RequestObjectType f738b;
    public kz<RequestObjectType> f739c;
    public kz<ResponseObjectType> f740d;
    private ResponseObjectType f741x;

    /* renamed from: com.flurry.sdk.kl.a */
    public interface C0157a<RequestObjectType, ResponseObjectType> {
        void m211a(kl<RequestObjectType, ResponseObjectType> klVar, ResponseObjectType responseObjectType);
    }

    /* renamed from: com.flurry.sdk.kl.1 */
    class C06121 implements C0162c {
        final /* synthetic */ kl f634a;

        C06121(kl klVar) {
            this.f634a = klVar;
        }

        public final void m604a(OutputStream outputStream) throws Exception {
            if (this.f634a.f738b != null && this.f634a.f739c != null) {
                this.f634a.f739c.m263a(outputStream, this.f634a.f738b);
            }
        }

        public final void m603a(kn knVar, InputStream inputStream) throws Exception {
            if (knVar.m697d() && this.f634a.f740d != null) {
                this.f634a.f741x = this.f634a.f740d.m262a(inputStream);
            }
        }

        public final void m602a(kn knVar) {
            kl.m708d(this.f634a);
        }
    }

    public final void m709a() {
        this.f724k = new C06121(this);
        super.m693a();
    }

    static /* synthetic */ void m708d(kl klVar) {
        if (klVar.f737a != null && !klVar.m695b()) {
            klVar.f737a.m211a(klVar, klVar.f741x);
        }
    }
}
