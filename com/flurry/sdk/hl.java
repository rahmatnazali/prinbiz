package com.flurry.sdk;

import android.content.Context;
import java.io.File;
import java.util.List;
import java.util.Map;

public class hl {
    private static final String f22b;
    boolean f23a;
    private final hm f24c;
    private final File f25d;
    private String f26e;

    static {
        f22b = hl.class.getSimpleName();
    }

    public hl() {
        this(jr.m120a().f284a);
    }

    public hl(Context context) {
        this.f24c = new hm();
        this.f25d = context.getFileStreamPath(".flurryinstallreceiver.");
        kf.m182a(3, f22b, "Referrer file name if it exists:  " + this.f25d);
    }

    public final synchronized Map<String, List<String>> m13a() {
        m12c();
        return hm.m16a(this.f26e);
    }

    public final synchronized String m15b() {
        m12c();
        return this.f26e;
    }

    public final synchronized void m14a(String str) {
        this.f23a = true;
        m11b(str);
        lq.m301a(this.f25d, this.f26e);
    }

    private void m11b(String str) {
        if (str != null) {
            this.f26e = str;
        }
    }

    private void m12c() {
        if (!this.f23a) {
            this.f23a = true;
            kf.m182a(4, f22b, "Loading referrer info from file: " + this.f25d.getAbsolutePath());
            String c = lq.m305c(this.f25d);
            kf.m184a(f22b, "Referrer file contents: " + c);
            m11b(c);
        }
    }
}
