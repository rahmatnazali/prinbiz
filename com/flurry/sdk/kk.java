package com.flurry.sdk;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class kk {
    private final Pattern f338a;

    public kk() {
        this.f338a = Pattern.compile(".*?(%\\{\\w+\\}).*?");
    }

    public final String m210a(String str) {
        Matcher matcher = this.f338a.matcher(str);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    public static boolean m209a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        return str2.equals("%{" + str + "}");
    }
}
