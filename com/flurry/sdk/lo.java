package com.flurry.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import org.xmlpull.v1.XmlPullParser;

public final class lo {
    private static final String f415a;

    static {
        f415a = lo.class.getSimpleName();
    }

    private static PackageInfo m291d(Context context) {
        PackageInfo packageInfo = null;
        if (context != null) {
            try {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 20815);
            } catch (NameNotFoundException e) {
                kf.m184a(f415a, "Cannot find package info for package: " + context.getPackageName());
            }
        }
        return packageInfo;
    }

    private static ApplicationInfo m292e(Context context) {
        ApplicationInfo applicationInfo = null;
        if (context != null) {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), TransportMediator.FLAG_KEY_MEDIA_NEXT);
            } catch (NameNotFoundException e) {
                kf.m184a(f415a, "Cannot find application info for package: " + context.getPackageName());
            }
        }
        return applicationInfo;
    }

    public static String m288a(Context context) {
        PackageInfo d = m291d(context);
        if (d == null || d.packageName == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return d.packageName;
    }

    public static String m289b(Context context) {
        PackageInfo d = m291d(context);
        if (d == null || d.versionName == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return d.versionName;
    }

    public static Bundle m290c(Context context) {
        ApplicationInfo e = m292e(context);
        return (e == null || e.metaData == null) ? Bundle.EMPTY : e.metaData;
    }
}
