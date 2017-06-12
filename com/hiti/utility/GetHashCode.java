package com.hiti.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class GetHashCode {
    public static String GetHashcode(Context context) {
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pkgInfo == null) {
            return null;
        }
        return String.valueOf(pkgInfo.signatures[0].hashCode());
    }
}
