package com.flurry.sdk;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class lv {

    /* renamed from: com.flurry.sdk.lv.a */
    public static class C0176a {
        private final Object f423a;
        private final String f424b;
        private Class<?> f425c;
        private List<Class<?>> f426d;
        private List<Object> f427e;
        private boolean f428f;
        private boolean f429g;

        public C0176a(Object obj, String str) {
            this.f423a = obj;
            this.f424b = str;
            this.f426d = new ArrayList();
            this.f427e = new ArrayList();
            this.f425c = obj != null ? obj.getClass() : null;
        }

        public final <T> C0176a m339a(Class<T> cls, T t) {
            this.f426d.add(cls);
            this.f427e.add(t);
            return this;
        }

        public final C0176a m338a(Class<?> cls) {
            this.f429g = true;
            this.f425c = cls;
            return this;
        }

        public final Object m340a() throws Exception {
            Method a = lv.m341a(this.f425c, this.f424b, (Class[]) this.f426d.toArray(new Class[this.f426d.size()]));
            if (this.f428f) {
                a.setAccessible(true);
            }
            Object[] toArray = this.f427e.toArray();
            return this.f429g ? a.invoke(null, toArray) : a.invoke(this.f423a, toArray);
        }
    }

    public static Method m341a(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException {
        while (cls != null) {
            try {
                return cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new NoSuchMethodException();
    }
}
