package com.google.android.gms.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class zzanx {

    /* renamed from: com.google.android.gms.internal.zzanx.1 */
    static class C06851 extends zzanx {
        final /* synthetic */ Method bfq;
        final /* synthetic */ Object bfr;

        C06851(Method method, Object obj) {
            this.bfq = method;
            this.bfr = obj;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bfq.invoke(this.bfr, new Object[]{cls});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanx.2 */
    static class C06862 extends zzanx {
        final /* synthetic */ Method bfs;
        final /* synthetic */ int bft;

        C06862(Method method, int i) {
            this.bfs = method;
            this.bft = i;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bfs.invoke(null, new Object[]{cls, Integer.valueOf(this.bft)});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanx.3 */
    static class C06873 extends zzanx {
        final /* synthetic */ Method bfs;

        C06873(Method method) {
            this.bfs = method;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bfs.invoke(null, new Object[]{cls, Object.class});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanx.4 */
    static class C06884 extends zzanx {
        C06884() {
        }

        public <T> T zzf(Class<T> cls) {
            String valueOf = String.valueOf(cls);
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf(valueOf).length() + 16).append("Cannot allocate ").append(valueOf).toString());
        }
    }

    public static zzanx zzczz() {
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            return new C06851(cls.getMethod("allocateInstance", new Class[]{Class.class}), declaredField.get(null));
        } catch (Exception e) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new C06862(declaredMethod2, intValue);
            } catch (Exception e2) {
                try {
                    Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod3.setAccessible(true);
                    return new C06873(declaredMethod3);
                } catch (Exception e3) {
                    return new C06884();
                }
            }
        }
    }

    public abstract <T> T zzf(Class<T> cls) throws Exception;
}
