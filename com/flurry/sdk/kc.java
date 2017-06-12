package com.flurry.sdk;

import java.util.Comparator;

public class kc implements Comparator<Runnable> {
    private static final String f316a;

    public /* synthetic */ int compare(Object obj, Object obj2) {
        Runnable runnable = (Runnable) obj2;
        int a = m167a((Runnable) obj);
        int a2 = m167a(runnable);
        if (a < a2) {
            return -1;
        }
        if (a > a2) {
            return 1;
        }
        return 0;
    }

    static {
        f316a = kc.class.getSimpleName();
    }

    private static int m167a(Runnable runnable) {
        if (runnable == null) {
            return Integer.MAX_VALUE;
        }
        if (runnable instanceof kd) {
            int i;
            lx lxVar = (lx) ((kd) runnable).m168a();
            if (lxVar != null) {
                i = lxVar.f710w;
            } else {
                i = Integer.MAX_VALUE;
            }
            return i;
        } else if (runnable instanceof lx) {
            return ((lx) runnable).f710w;
        } else {
            kf.m182a(6, f316a, "Unknown runnable class: " + runnable.getClass().getName());
            return Integer.MAX_VALUE;
        }
    }
}
