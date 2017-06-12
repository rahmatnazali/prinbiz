package com.flurry.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class kh {
    private static final String f331a;
    private static final Map<Class<? extends ki>, kg> f332b;
    private final Map<Class<? extends ki>, ki> f333c;

    static {
        f331a = kh.class.getSimpleName();
        f332b = new LinkedHashMap();
    }

    public static void m197a(Class<? extends ki> cls) {
        if (cls != null) {
            synchronized (f332b) {
                f332b.put(cls, new kg(cls));
            }
        }
    }

    public kh() {
        this.f333c = new LinkedHashMap();
    }

    public final synchronized void m200a(Context context) {
        if (context == null) {
            kf.m182a(5, f331a, "Null context.");
        } else {
            synchronized (f332b) {
                List<kg> arrayList = new ArrayList(f332b.values());
            }
            for (kg kgVar : arrayList) {
                try {
                    Object obj;
                    if (kgVar.f329a == null || VERSION.SDK_INT < kgVar.f330b) {
                        obj = null;
                    } else {
                        obj = 1;
                    }
                    if (obj != null) {
                        ki kiVar = (ki) kgVar.f329a.newInstance();
                        kiVar.m202a(context);
                        this.f333c.put(kgVar.f329a, kiVar);
                    }
                } catch (Throwable e) {
                    kf.m183a(5, f331a, "Flurry Module for class " + kgVar.f329a + " is not available:", e);
                }
            }
            lf.m651a().m660a(context);
            jv.m136a();
        }
    }

    public final synchronized void m199a() {
        jv.m137b();
        lf.m655b();
        List b = m198b();
        for (int size = b.size() - 1; size >= 0; size--) {
            try {
                ((ki) this.f333c.remove(((ki) b.get(size)).getClass())).m203b();
            } catch (Throwable e) {
                kf.m183a(5, f331a, "Error destroying module:", e);
            }
        }
    }

    public final ki m201b(Class<? extends ki> cls) {
        if (cls == null) {
            return null;
        }
        synchronized (this.f333c) {
            ki kiVar = (ki) this.f333c.get(cls);
        }
        if (kiVar != null) {
            return kiVar;
        }
        throw new IllegalStateException("Module was not registered/initialized. " + cls);
    }

    private List<ki> m198b() {
        List<ki> arrayList = new ArrayList();
        synchronized (this.f333c) {
            arrayList.addAll(this.f333c.values());
        }
        return arrayList;
    }
}
