package com.flurry.sdk;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParser;

public abstract class lw implements Runnable {
    private static final String f430a;
    PrintStream f431u;
    PrintWriter f432v;

    public abstract void m342a();

    static {
        f430a = lw.class.getSimpleName();
    }

    public final void run() {
        try {
            m342a();
        } catch (Throwable th) {
            if (this.f431u != null) {
                th.printStackTrace(this.f431u);
            } else if (this.f432v != null) {
                th.printStackTrace(this.f432v);
            } else {
                th.printStackTrace();
            }
            kf.m183a(6, f430a, XmlPullParser.NO_NAMESPACE, th);
        }
    }
}
