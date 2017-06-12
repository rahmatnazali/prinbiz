package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import com.flurry.sdk.lj.C0175a;
import org.xmlpull.v1.XmlPullParser;

public class jn implements C0175a {
    private static jn f614a;
    private static final String f615b;
    private String f616c;
    private String f617d;

    public static synchronized jn m581a() {
        jn jnVar;
        synchronized (jn.class) {
            if (f614a == null) {
                f614a = new jn();
            }
            jnVar = f614a;
        }
        return jnVar;
    }

    public static void m583b() {
        if (f614a != null) {
            li.m668a().m275b("VersionName", f614a);
        }
        f614a = null;
    }

    static {
        f615b = jn.class.getSimpleName();
    }

    private jn() {
        lj a = li.m668a();
        this.f616c = (String) a.m272a("VersionName");
        a.m273a("VersionName", (C0175a) this);
        kf.m182a(4, f615b, "initSettings, VersionName = " + this.f616c);
    }

    public static String m584c() {
        return VERSION.RELEASE;
    }

    public static String m585d() {
        return Build.DEVICE;
    }

    public static String m586e() {
        return Build.ID;
    }

    public static String m587f() {
        return Build.MANUFACTURER;
    }

    public static String m588g() {
        return Build.MODEL;
    }

    public static String m589h() {
        return XmlPullParser.NO_NAMESPACE;
    }

    public static String m582a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public final synchronized String m592i() {
        String str;
        if (!TextUtils.isEmpty(this.f616c)) {
            str = this.f616c;
        } else if (TextUtils.isEmpty(this.f617d)) {
            this.f617d = m590j();
            str = this.f617d;
        } else {
            str = this.f617d;
        }
        return str;
    }

    private static String m590j() {
        try {
            Context context = jr.m120a().f284a;
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                return Integer.toString(packageInfo.versionCode);
            }
            return "Unknown";
        } catch (Throwable th) {
            kf.m183a(6, f615b, XmlPullParser.NO_NAMESPACE, th);
        }
    }

    public final void m591a(String str, Object obj) {
        if (str.equals("VersionName")) {
            this.f616c = (String) obj;
            kf.m182a(4, f615b, "onSettingUpdate, VersionName = " + this.f616c);
            return;
        }
        kf.m182a(6, f615b, "onSettingUpdate internal error!");
    }
}
