package com.flurry.sdk;

import android.content.Context;
import java.lang.ref.WeakReference;

public final class le extends jz {
    public WeakReference<Context> f671a;
    public ld f672b;
    public int f673c;
    public long f674d;

    /* renamed from: com.flurry.sdk.le.a */
    public enum C0172a {
        ;

        public static int[] m268a() {
            return (int[]) f398f.clone();
        }

        static {
            f393a = 1;
            f394b = 2;
            f395c = 3;
            f396d = 4;
            f397e = 5;
            f398f = new int[]{f393a, f394b, f395c, f396d, f397e};
        }
    }

    public le() {
        super("com.flurry.android.sdk.FlurrySessionEvent");
    }
}
