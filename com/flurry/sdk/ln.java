package com.flurry.sdk;

public class ln {
    private static final String f410c;
    long f411a;
    boolean f412b;
    private boolean f413d;
    private lw f414e;

    /* renamed from: com.flurry.sdk.ln.1 */
    class C06431 extends lw {
        final /* synthetic */ ln f709a;

        C06431(ln lnVar) {
            this.f709a = lnVar;
        }

        public final void m670a() {
            kb.m157a().m161a(new ll());
            if (this.f709a.f412b && this.f709a.f413d) {
                jr.m120a().m125a(this.f709a.f414e, this.f709a.f411a);
            }
        }
    }

    static {
        f410c = ln.class.getSimpleName();
    }

    public ln() {
        this.f411a = 1000;
        this.f412b = true;
        this.f413d = false;
        this.f414e = new C06431(this);
    }

    public final synchronized void m286a() {
        if (!this.f413d) {
            jr.m120a().m125a(this.f414e, this.f411a);
            this.f413d = true;
        }
    }

    public final synchronized void m287b() {
        if (this.f413d) {
            jr.m120a().m127c(this.f414e);
            this.f413d = false;
        }
    }
}
