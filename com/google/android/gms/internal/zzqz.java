package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzqz<T> {
    private static zza vN;
    private static int vO;
    private static String vP;
    private static final Object zzamr;
    private T vQ;
    protected final String zzaxp;
    protected final T zzaxq;

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zzb(String str, Float f);

        Integer zzb(String str, Integer num);
    }

    /* renamed from: com.google.android.gms.internal.zzqz.1 */
    class C07181 extends zzqz<Boolean> {
        C07181(String str, Boolean bool) {
            super(str, bool);
        }

        protected /* synthetic */ Object zzgy(String str) {
            return zzgz(str);
        }

        protected Boolean zzgz(String str) {
            return null.zza(this.zzaxp, (Boolean) this.zzaxq);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqz.2 */
    class C07192 extends zzqz<Long> {
        C07192(String str, Long l) {
            super(str, l);
        }

        protected /* synthetic */ Object zzgy(String str) {
            return zzha(str);
        }

        protected Long zzha(String str) {
            return null.getLong(this.zzaxp, (Long) this.zzaxq);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqz.3 */
    class C07203 extends zzqz<Integer> {
        C07203(String str, Integer num) {
            super(str, num);
        }

        protected /* synthetic */ Object zzgy(String str) {
            return zzhb(str);
        }

        protected Integer zzhb(String str) {
            return null.zzb(this.zzaxp, (Integer) this.zzaxq);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqz.4 */
    class C07214 extends zzqz<Float> {
        C07214(String str, Float f) {
            super(str, f);
        }

        protected /* synthetic */ Object zzgy(String str) {
            return zzhc(str);
        }

        protected Float zzhc(String str) {
            return null.zzb(this.zzaxp, (Float) this.zzaxq);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzqz.5 */
    class C07225 extends zzqz<String> {
        C07225(String str, String str2) {
            super(str, str2);
        }

        protected /* synthetic */ Object zzgy(String str) {
            return zzhd(str);
        }

        protected String zzhd(String str) {
            return null.getString(this.zzaxp, (String) this.zzaxq);
        }
    }

    static {
        zzamr = new Object();
        vN = null;
        vO = 0;
        vP = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzqz(String str, T t) {
        this.vQ = null;
        this.zzaxp = str;
        this.zzaxq = t;
    }

    public static zzqz<Float> zza(String str, Float f) {
        return new C07214(str, f);
    }

    public static zzqz<Integer> zza(String str, Integer num) {
        return new C07203(str, num);
    }

    public static zzqz<Long> zza(String str, Long l) {
        return new C07192(str, l);
    }

    public static zzqz<String> zzab(String str, String str2) {
        return new C07225(str, str2);
    }

    public static zzqz<Boolean> zzm(String str, boolean z) {
        return new C07181(str, Boolean.valueOf(z));
    }

    public final T get() {
        T zzgy;
        long clearCallingIdentity;
        try {
            zzgy = zzgy(this.zzaxp);
        } catch (SecurityException e) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            zzgy = zzgy(this.zzaxp);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return zzgy;
    }

    protected abstract T zzgy(String str);
}
