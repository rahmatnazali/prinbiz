package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.lj.C0175a;
import java.lang.Thread.UncaughtExceptionHandler;
import org.xmlpull.v1.XmlPullParser;

public class lz implements ki, C0175a, UncaughtExceptionHandler {
    private static final String f711a;
    private boolean f712b;

    static {
        f711a = lz.class.getSimpleName();
    }

    public final void m672a(Context context) {
        lj a = li.m668a();
        this.f712b = ((Boolean) a.m272a("CaptureUncaughtExceptions")).booleanValue();
        a.m273a("CaptureUncaughtExceptions", (C0175a) this);
        kf.m182a(4, f711a, "initSettings, CrashReportingEnabled = " + this.f712b);
        ma a2 = ma.m355a();
        synchronized (a2.f437b) {
            a2.f437b.put(this, null);
        }
    }

    public final void m674b() {
        ma.m356b();
        li.m668a().m275b("CaptureUncaughtExceptions", this);
    }

    public final void m673a(String str, Object obj) {
        if (str.equals("CaptureUncaughtExceptions")) {
            this.f712b = ((Boolean) obj).booleanValue();
            kf.m182a(4, f711a, "onSettingUpdate, CrashReportingEnabled = " + this.f712b);
            return;
        }
        kf.m182a(6, f711a, "onSettingUpdate internal error!");
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace();
        if (this.f712b) {
            String str = XmlPullParser.NO_NAMESPACE;
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                if (th.getMessage() != null) {
                    stringBuilder.append(" (" + th.getMessage() + ")\n");
                }
                str = stringBuilder.toString();
            } else if (th.getMessage() != null) {
                str = th.getMessage();
            }
            hk.m392a();
            hk.m393a("uncaught", str, th);
        }
        lf.m651a().m667e();
        ji.m569a().m579d();
    }
}
