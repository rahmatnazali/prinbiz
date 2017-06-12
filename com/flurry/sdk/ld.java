package com.flurry.sdk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ld {
    private static final List<Class<?>> f390b;
    private final String f391a;
    private final Map<Class<?>, Object> f392c;

    static {
        f390b = new ArrayList();
    }

    public static void m265a(Class<?> cls) {
        synchronized (f390b) {
            f390b.add(cls);
        }
    }

    public static void m266b(Class<?> cls) {
        synchronized (f390b) {
            f390b.remove(cls);
        }
    }

    public ld() {
        this.f391a = ld.class.getSimpleName();
        this.f392c = new LinkedHashMap();
        synchronized (f390b) {
            List<Class> arrayList = new ArrayList(f390b);
        }
        for (Class cls : arrayList) {
            try {
                Object newInstance = cls.newInstance();
                synchronized (this.f392c) {
                    this.f392c.put(cls, newInstance);
                }
            } catch (Throwable e) {
                kf.m183a(5, this.f391a, "Module data " + cls + " is not available:", e);
            }
        }
    }

    public final Object m267c(Class<?> cls) {
        Object obj;
        synchronized (this.f392c) {
            obj = this.f392c.get(cls);
        }
        return obj;
    }
}
