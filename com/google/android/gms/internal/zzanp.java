package com.google.android.gms.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class zzanp {
    private final Map<Type, zzamr<?>> bed;

    /* renamed from: com.google.android.gms.internal.zzanp.1 */
    class C06731 implements zzanu<T> {
        final /* synthetic */ zzamr beD;
        final /* synthetic */ Type beE;
        final /* synthetic */ zzanp beF;

        C06731(zzanp com_google_android_gms_internal_zzanp, zzamr com_google_android_gms_internal_zzamr, Type type) {
            this.beF = com_google_android_gms_internal_zzanp;
            this.beD = com_google_android_gms_internal_zzamr;
            this.beE = type;
        }

        public T zzczu() {
            return this.beD.zza(this.beE);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.2 */
    class C06742 implements zzanu<T> {
        final /* synthetic */ zzanp beF;

        C06742(zzanp com_google_android_gms_internal_zzanp) {
            this.beF = com_google_android_gms_internal_zzanp;
        }

        public T zzczu() {
            return new LinkedHashMap();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.3 */
    class C06753 implements zzanu<T> {
        final /* synthetic */ zzanp beF;

        C06753(zzanp com_google_android_gms_internal_zzanp) {
            this.beF = com_google_android_gms_internal_zzanp;
        }

        public T zzczu() {
            return new zzant();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.4 */
    class C06764 implements zzanu<T> {
        final /* synthetic */ Type beE;
        final /* synthetic */ zzanp beF;
        private final zzanx beG;
        final /* synthetic */ Class beH;

        C06764(zzanp com_google_android_gms_internal_zzanp, Class cls, Type type) {
            this.beF = com_google_android_gms_internal_zzanp;
            this.beH = cls;
            this.beE = type;
            this.beG = zzanx.zzczz();
        }

        public T zzczu() {
            try {
                return this.beG.zzf(this.beH);
            } catch (Throwable e) {
                String valueOf = String.valueOf(this.beE);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 116).append("Unable to invoke no-args constructor for ").append(valueOf).append(". ").append("Register an InstanceCreator with Gson for this type may fix this problem.").toString(), e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.5 */
    class C06775 implements zzanu<T> {
        final /* synthetic */ Type beE;
        final /* synthetic */ zzanp beF;
        final /* synthetic */ zzamr beI;

        C06775(zzanp com_google_android_gms_internal_zzanp, zzamr com_google_android_gms_internal_zzamr, Type type) {
            this.beF = com_google_android_gms_internal_zzanp;
            this.beI = com_google_android_gms_internal_zzamr;
            this.beE = type;
        }

        public T zzczu() {
            return this.beI.zza(this.beE);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.6 */
    class C06786 implements zzanu<T> {
        final /* synthetic */ zzanp beF;
        final /* synthetic */ Constructor beJ;

        C06786(zzanp com_google_android_gms_internal_zzanp, Constructor constructor) {
            this.beF = com_google_android_gms_internal_zzanp;
            this.beJ = constructor;
        }

        public T zzczu() {
            String valueOf;
            try {
                return this.beJ.newInstance(null);
            } catch (Throwable e) {
                valueOf = String.valueOf(this.beJ);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 30).append("Failed to invoke ").append(valueOf).append(" with no args").toString(), e);
            } catch (InvocationTargetException e2) {
                valueOf = String.valueOf(this.beJ);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 30).append("Failed to invoke ").append(valueOf).append(" with no args").toString(), e2.getTargetException());
            } catch (IllegalAccessException e3) {
                throw new AssertionError(e3);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.7 */
    class C06797 implements zzanu<T> {
        final /* synthetic */ zzanp beF;

        C06797(zzanp com_google_android_gms_internal_zzanp) {
            this.beF = com_google_android_gms_internal_zzanp;
        }

        public T zzczu() {
            return new TreeSet();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.8 */
    class C06808 implements zzanu<T> {
        final /* synthetic */ Type beE;
        final /* synthetic */ zzanp beF;

        C06808(zzanp com_google_android_gms_internal_zzanp, Type type) {
            this.beF = com_google_android_gms_internal_zzanp;
            this.beE = type;
        }

        public T zzczu() {
            if (this.beE instanceof ParameterizedType) {
                Type type = ((ParameterizedType) this.beE).getActualTypeArguments()[0];
                if (type instanceof Class) {
                    return EnumSet.noneOf((Class) type);
                }
                String str = "Invalid EnumSet type: ";
                String valueOf = String.valueOf(this.beE.toString());
                throw new zzamw(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
            str = "Invalid EnumSet type: ";
            valueOf = String.valueOf(this.beE.toString());
            throw new zzamw(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzanp.9 */
    class C06819 implements zzanu<T> {
        final /* synthetic */ zzanp beF;

        C06819(zzanp com_google_android_gms_internal_zzanp) {
            this.beF = com_google_android_gms_internal_zzanp;
        }

        public T zzczu() {
            return new LinkedHashSet();
        }
    }

    public zzanp(Map<Type, zzamr<?>> map) {
        this.bed = map;
    }

    private <T> zzanu<T> zzc(Type type, Class<? super T> cls) {
        return Collection.class.isAssignableFrom(cls) ? SortedSet.class.isAssignableFrom(cls) ? new C06797(this) : EnumSet.class.isAssignableFrom(cls) ? new C06808(this, type) : Set.class.isAssignableFrom(cls) ? new C06819(this) : Queue.class.isAssignableFrom(cls) ? new zzanu<T>() {
            final /* synthetic */ zzanp beF;

            {
                this.beF = r1;
            }

            public T zzczu() {
                return new LinkedList();
            }
        } : new zzanu<T>() {
            final /* synthetic */ zzanp beF;

            {
                this.beF = r1;
            }

            public T zzczu() {
                return new ArrayList();
            }
        } : Map.class.isAssignableFrom(cls) ? SortedMap.class.isAssignableFrom(cls) ? new zzanu<T>() {
            final /* synthetic */ zzanp beF;

            {
                this.beF = r1;
            }

            public T zzczu() {
                return new TreeMap();
            }
        } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(zzaol.zzl(((ParameterizedType) type).getActualTypeArguments()[0]).m359m())) ? new C06753(this) : new C06742(this) : null;
    }

    private <T> zzanu<T> zzd(Type type, Class<? super T> cls) {
        return new C06764(this, cls, type);
    }

    private <T> zzanu<T> zzl(Class<? super T> cls) {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new C06786(this, declaredConstructor);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public String toString() {
        return this.bed.toString();
    }

    public <T> zzanu<T> zzb(zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Type n = com_google_android_gms_internal_zzaol_T.m360n();
        Class m = com_google_android_gms_internal_zzaol_T.m359m();
        zzamr com_google_android_gms_internal_zzamr = (zzamr) this.bed.get(n);
        if (com_google_android_gms_internal_zzamr != null) {
            return new C06731(this, com_google_android_gms_internal_zzamr, n);
        }
        com_google_android_gms_internal_zzamr = (zzamr) this.bed.get(m);
        if (com_google_android_gms_internal_zzamr != null) {
            return new C06775(this, com_google_android_gms_internal_zzamr, n);
        }
        zzanu<T> zzl = zzl(m);
        if (zzl != null) {
            return zzl;
        }
        zzl = zzc(n, m);
        return zzl == null ? zzd(n, m) : zzl;
    }
}
