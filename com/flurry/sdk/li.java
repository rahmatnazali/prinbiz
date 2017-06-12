package com.flurry.sdk;

import android.location.Criteria;
import android.location.Location;
import org.apache.commons.net.ftp.FTPReply;
import org.xmlpull.v1.XmlPullParser;

public final class li extends lj {
    public static final Integer f690a;
    public static final Integer f691b;
    public static final Integer f692c;
    public static final Integer f693d;
    public static final String f694e;
    public static final Boolean f695f;
    public static final Boolean f696g;
    public static final String f697h;
    public static final Boolean f698i;
    public static final Criteria f699j;
    public static final Location f700k;
    public static final Long f701l;
    public static final Boolean f702m;
    public static final Long f703n;
    public static final Byte f704o;
    public static final Boolean f705p;
    public static final String f706q;
    public static final Boolean f707r;
    private static li f708s;

    public static synchronized li m668a() {
        li liVar;
        synchronized (li.class) {
            if (f708s == null) {
                f708s = new li();
            }
            liVar = f708s;
        }
        return liVar;
    }

    public static synchronized void m669b() {
        synchronized (li.class) {
            if (f708s != null) {
                f708s.m276c();
            }
            f708s = null;
        }
    }

    static {
        f690a = Integer.valueOf(FTPReply.CLOSING_DATA_CONNECTION);
        f691b = Integer.valueOf(6);
        f692c = Integer.valueOf(3);
        f693d = Integer.valueOf(1);
        f694e = null;
        f695f = Boolean.valueOf(true);
        f696g = Boolean.valueOf(true);
        f697h = null;
        f698i = Boolean.valueOf(true);
        f699j = null;
        f700k = null;
        f701l = Long.valueOf(10000);
        f702m = Boolean.valueOf(true);
        f703n = null;
        f704o = Byte.valueOf((byte) -1);
        f705p = Boolean.valueOf(false);
        f706q = null;
        f707r = Boolean.valueOf(true);
    }

    private li() {
        m274a("AgentVersion", (Object) f690a);
        m274a("ReleaseMajorVersion", (Object) f691b);
        m274a("ReleaseMinorVersion", (Object) f692c);
        m274a("ReleasePatchVersion", (Object) f693d);
        m274a("ReleaseBetaVersion", (Object) XmlPullParser.NO_NAMESPACE);
        m274a("VersionName", (Object) f694e);
        m274a("CaptureUncaughtExceptions", (Object) f695f);
        m274a("UseHttps", (Object) f696g);
        m274a("ReportUrl", (Object) f697h);
        m274a("ReportLocation", (Object) f698i);
        m274a("ExplicitLocation", (Object) f700k);
        m274a("ContinueSessionMillis", (Object) f701l);
        m274a("LogEvents", (Object) f702m);
        m274a("Age", (Object) f703n);
        m274a("Gender", (Object) f704o);
        m274a("UserId", (Object) XmlPullParser.NO_NAMESPACE);
        m274a("ProtonEnabled", (Object) f705p);
        m274a("ProtonConfigUrl", (Object) f706q);
        m274a("analyticsEnabled", (Object) f707r);
    }
}
