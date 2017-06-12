package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzp;
import org.xmlpull.v1.XmlPullParser;

public class zzrl {
    private final String mTag;
    private final String zd;
    private final zzp zw;
    private final int zzcze;

    private zzrl(String str, String str2) {
        this.zd = str2;
        this.mTag = str;
        this.zw = new zzp(str);
        this.zzcze = getLogLevel();
    }

    public zzrl(String str, String... strArr) {
        this(str, zzc(strArr));
    }

    private int getLogLevel() {
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        return i;
    }

    private static String zzc(String... strArr) {
        if (strArr.length == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (String str : strArr) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append(str);
        }
        stringBuilder.append(']').append(' ');
        return stringBuilder.toString();
    }

    protected String format(String str, Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(str, objArr);
        }
        return this.zd.concat(str);
    }

    public void zza(String str, Object... objArr) {
        if (zzaz(2)) {
            Log.v(this.mTag, format(str, objArr));
        }
    }

    public boolean zzaz(int i) {
        return this.zzcze <= i;
    }
}
