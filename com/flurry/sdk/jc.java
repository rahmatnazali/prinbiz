package com.flurry.sdk;

import android.text.TextUtils;
import java.util.Arrays;

public class jc {
    private static String f230a;

    static {
        f230a = jc.class.getName();
    }

    public static String m83a(String str) {
        String str2 = "a=" + jr.m120a().f287d;
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        String str3 = "cid=" + m84b(str);
        return String.format("%s&%s", new Object[]{str2, str3});
    }

    private static String m84b(String str) {
        byte[] bArr;
        if (str == null || str.trim().length() <= 0) {
            bArr = null;
        } else {
            try {
                bArr = lr.m322f(str);
                if (bArr == null || bArr.length != 20) {
                    kf.m182a(6, f230a, "sha1 is not 20 bytes long: " + Arrays.toString(bArr));
                    bArr = null;
                } else {
                    try {
                        kf.m182a(5, f230a, "syndication hashedId is:" + new String(bArr));
                    } catch (Exception e) {
                        kf.m182a(6, f230a, "Exception in getHashedSyndicationIdString()");
                        return lr.m309a(bArr);
                    }
                }
            } catch (Exception e2) {
                bArr = null;
                kf.m182a(6, f230a, "Exception in getHashedSyndicationIdString()");
                return lr.m309a(bArr);
            }
        }
        return lr.m309a(bArr);
    }
}
