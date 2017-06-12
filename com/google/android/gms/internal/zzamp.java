package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzamp {
    private final ThreadLocal<Map<zzaol<?>, zza<?>>> bdO;
    private final Map<zzaol<?>, zzanh<?>> bdP;
    private final List<zzani> bdQ;
    private final zzanp bdR;
    private final boolean bdS;
    private final boolean bdT;
    private final boolean bdU;
    private final boolean bdV;
    final zzamt bdW;
    final zzanc bdX;

    /* renamed from: com.google.android.gms.internal.zzamp.1 */
    class C06661 implements zzamt {
        final /* synthetic */ zzamp bdY;

        C06661(zzamp com_google_android_gms_internal_zzamp) {
            this.bdY = com_google_android_gms_internal_zzamp;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzamp.2 */
    class C06672 implements zzanc {
        final /* synthetic */ zzamp bdY;

        C06672(zzamp com_google_android_gms_internal_zzamp) {
            this.bdY = com_google_android_gms_internal_zzamp;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzamp.3 */
    class C06683 extends zzanh<Number> {
        final /* synthetic */ zzamp bdY;

        C06683(zzamp com_google_android_gms_internal_zzamp) {
            this.bdY = com_google_android_gms_internal_zzamp;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
            if (number == null) {
                com_google_android_gms_internal_zzaoo.m379l();
                return;
            }
            this.bdY.zzn(number.doubleValue());
            com_google_android_gms_internal_zzaoo.zza(number);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zze(com_google_android_gms_internal_zzaom);
        }

        public Double zze(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return Double.valueOf(com_google_android_gms_internal_zzaom.nextDouble());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzamp.4 */
    class C06694 extends zzanh<Number> {
        final /* synthetic */ zzamp bdY;

        C06694(zzamp com_google_android_gms_internal_zzamp) {
            this.bdY = com_google_android_gms_internal_zzamp;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
            if (number == null) {
                com_google_android_gms_internal_zzaoo.m379l();
                return;
            }
            this.bdY.zzn((double) number.floatValue());
            com_google_android_gms_internal_zzaoo.zza(number);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzf(com_google_android_gms_internal_zzaom);
        }

        public Float zzf(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return Float.valueOf((float) com_google_android_gms_internal_zzaom.nextDouble());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzamp.5 */
    class C06705 extends zzanh<Number> {
        final /* synthetic */ zzamp bdY;

        C06705(zzamp com_google_android_gms_internal_zzamp) {
            this.bdY = com_google_android_gms_internal_zzamp;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
            if (number == null) {
                com_google_android_gms_internal_zzaoo.m379l();
            } else {
                com_google_android_gms_internal_zzaoo.zzts(number.toString());
            }
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzg(com_google_android_gms_internal_zzaom);
        }

        public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return Long.valueOf(com_google_android_gms_internal_zzaom.nextLong());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    static class zza<T> extends zzanh<T> {
        private zzanh<T> bdZ;

        zza() {
        }

        public void zza(zzanh<T> com_google_android_gms_internal_zzanh_T) {
            if (this.bdZ != null) {
                throw new AssertionError();
            }
            this.bdZ = com_google_android_gms_internal_zzanh_T;
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
            if (this.bdZ == null) {
                throw new IllegalStateException();
            }
            this.bdZ.zza(com_google_android_gms_internal_zzaoo, t);
        }

        public T zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (this.bdZ != null) {
                return this.bdZ.zzb(com_google_android_gms_internal_zzaom);
            }
            throw new IllegalStateException();
        }
    }

    public zzamp() {
        this(zzanq.beK, zzamn.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, zzanf.DEFAULT, Collections.emptyList());
    }

    zzamp(zzanq com_google_android_gms_internal_zzanq, zzamo com_google_android_gms_internal_zzamo, Map<Type, zzamr<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, zzanf com_google_android_gms_internal_zzanf, List<zzani> list) {
        this.bdO = new ThreadLocal();
        this.bdP = Collections.synchronizedMap(new HashMap());
        this.bdW = new C06661(this);
        this.bdX = new C06672(this);
        this.bdR = new zzanp(map);
        this.bdS = z;
        this.bdU = z3;
        this.bdT = z4;
        this.bdV = z5;
        List arrayList = new ArrayList();
        arrayList.add(zzaok.bgN);
        arrayList.add(zzaof.bfu);
        arrayList.add(com_google_android_gms_internal_zzanq);
        arrayList.addAll(list);
        arrayList.add(zzaok.bgu);
        arrayList.add(zzaok.bgj);
        arrayList.add(zzaok.bgd);
        arrayList.add(zzaok.bgf);
        arrayList.add(zzaok.bgh);
        arrayList.add(zzaok.zza(Long.TYPE, Long.class, zza(com_google_android_gms_internal_zzanf)));
        arrayList.add(zzaok.zza(Double.TYPE, Double.class, zzcy(z6)));
        arrayList.add(zzaok.zza(Float.TYPE, Float.class, zzcz(z6)));
        arrayList.add(zzaok.bgo);
        arrayList.add(zzaok.bgq);
        arrayList.add(zzaok.bgw);
        arrayList.add(zzaok.bgy);
        arrayList.add(zzaok.zza(BigDecimal.class, zzaok.bgs));
        arrayList.add(zzaok.zza(BigInteger.class, zzaok.bgt));
        arrayList.add(zzaok.bgA);
        arrayList.add(zzaok.bgC);
        arrayList.add(zzaok.bgG);
        arrayList.add(zzaok.bgL);
        arrayList.add(zzaok.bgE);
        arrayList.add(zzaok.bga);
        arrayList.add(zzaoa.bfu);
        arrayList.add(zzaok.bgJ);
        arrayList.add(zzaoi.bfu);
        arrayList.add(zzaoh.bfu);
        arrayList.add(zzaok.bgH);
        arrayList.add(zzany.bfu);
        arrayList.add(zzaok.bfY);
        arrayList.add(new zzanz(this.bdR));
        arrayList.add(new zzaoe(this.bdR, z2));
        arrayList.add(new zzaob(this.bdR));
        arrayList.add(zzaok.bgO);
        arrayList.add(new zzaog(this.bdR, com_google_android_gms_internal_zzamo, com_google_android_gms_internal_zzanq));
        this.bdQ = Collections.unmodifiableList(arrayList);
    }

    private zzanh<Number> zza(zzanf com_google_android_gms_internal_zzanf) {
        return com_google_android_gms_internal_zzanf == zzanf.DEFAULT ? zzaok.bgk : new C06705(this);
    }

    private static void zza(Object obj, zzaom com_google_android_gms_internal_zzaom) {
        if (obj != null) {
            try {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.END_DOCUMENT) {
                    throw new zzamw("JSON document was not fully consumed.");
                }
            } catch (Throwable e) {
                throw new zzane(e);
            } catch (Throwable e2) {
                throw new zzamw(e2);
            }
        }
    }

    private zzanh<Number> zzcy(boolean z) {
        return z ? zzaok.bgm : new C06683(this);
    }

    private zzanh<Number> zzcz(boolean z) {
        return z ? zzaok.bgl : new C06694(this);
    }

    private void zzn(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.bdS + "factories:" + this.bdQ + ",instanceCreators:" + this.bdR + "}";
    }

    public <T> zzanh<T> zza(zzani com_google_android_gms_internal_zzani, zzaol<T> com_google_android_gms_internal_zzaol_T) {
        Object obj = null;
        if (!this.bdQ.contains(com_google_android_gms_internal_zzani)) {
            obj = 1;
        }
        Object obj2 = obj;
        for (zzani com_google_android_gms_internal_zzani2 : this.bdQ) {
            if (obj2 != null) {
                zzanh<T> zza = com_google_android_gms_internal_zzani2.zza(this, com_google_android_gms_internal_zzaol_T);
                if (zza != null) {
                    return zza;
                }
            } else if (com_google_android_gms_internal_zzani2 == com_google_android_gms_internal_zzani) {
                obj2 = 1;
            }
        }
        String valueOf = String.valueOf(com_google_android_gms_internal_zzaol_T);
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 22).append("GSON cannot serialize ").append(valueOf).toString());
    }

    public <T> zzanh<T> zza(zzaol<T> com_google_android_gms_internal_zzaol_T) {
        zzanh<T> com_google_android_gms_internal_zzanh_T = (zzanh) this.bdP.get(com_google_android_gms_internal_zzaol_T);
        if (com_google_android_gms_internal_zzanh_T == null) {
            Map map;
            Map map2 = (Map) this.bdO.get();
            Object obj = null;
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                this.bdO.set(hashMap);
                map = hashMap;
                obj = 1;
            } else {
                map = map2;
            }
            zza com_google_android_gms_internal_zzamp_zza = (zza) map.get(com_google_android_gms_internal_zzaol_T);
            if (com_google_android_gms_internal_zzamp_zza == null) {
                try {
                    zza com_google_android_gms_internal_zzamp_zza2 = new zza();
                    map.put(com_google_android_gms_internal_zzaol_T, com_google_android_gms_internal_zzamp_zza2);
                    for (zzani zza : this.bdQ) {
                        com_google_android_gms_internal_zzanh_T = zza.zza(this, com_google_android_gms_internal_zzaol_T);
                        if (com_google_android_gms_internal_zzanh_T != null) {
                            com_google_android_gms_internal_zzamp_zza2.zza(com_google_android_gms_internal_zzanh_T);
                            this.bdP.put(com_google_android_gms_internal_zzaol_T, com_google_android_gms_internal_zzanh_T);
                            map.remove(com_google_android_gms_internal_zzaol_T);
                            if (obj != null) {
                                this.bdO.remove();
                            }
                        }
                    }
                    String valueOf = String.valueOf(com_google_android_gms_internal_zzaol_T);
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 19).append("GSON cannot handle ").append(valueOf).toString());
                } catch (Throwable th) {
                    map.remove(com_google_android_gms_internal_zzaol_T);
                    if (obj != null) {
                        this.bdO.remove();
                    }
                }
            }
        }
        return com_google_android_gms_internal_zzanh_T;
    }

    public zzaoo zza(Writer writer) throws IOException {
        if (this.bdU) {
            writer.write(")]}'\n");
        }
        zzaoo com_google_android_gms_internal_zzaoo = new zzaoo(writer);
        if (this.bdV) {
            com_google_android_gms_internal_zzaoo.setIndent("  ");
        }
        com_google_android_gms_internal_zzaoo.zzdd(this.bdS);
        return com_google_android_gms_internal_zzaoo;
    }

    public <T> T zza(zzamv com_google_android_gms_internal_zzamv, Class<T> cls) throws zzane {
        return zzanv.zzp(cls).cast(zza(com_google_android_gms_internal_zzamv, (Type) cls));
    }

    public <T> T zza(zzamv com_google_android_gms_internal_zzamv, Type type) throws zzane {
        return com_google_android_gms_internal_zzamv == null ? null : zza(new zzaoc(com_google_android_gms_internal_zzamv), type);
    }

    public <T> T zza(zzaom com_google_android_gms_internal_zzaom, Type type) throws zzamw, zzane {
        boolean z = true;
        boolean isLenient = com_google_android_gms_internal_zzaom.isLenient();
        com_google_android_gms_internal_zzaom.setLenient(true);
        try {
            com_google_android_gms_internal_zzaom.m370b();
            z = false;
            T zzb = zza(zzaol.zzl(type)).zzb(com_google_android_gms_internal_zzaom);
            com_google_android_gms_internal_zzaom.setLenient(isLenient);
            return zzb;
        } catch (Throwable e) {
            if (z) {
                com_google_android_gms_internal_zzaom.setLenient(isLenient);
                return null;
            }
            throw new zzane(e);
        } catch (Throwable e2) {
            throw new zzane(e2);
        } catch (Throwable e22) {
            throw new zzane(e22);
        } catch (Throwable th) {
            com_google_android_gms_internal_zzaom.setLenient(isLenient);
        }
    }

    public <T> T zza(Reader reader, Type type) throws zzamw, zzane {
        zzaom com_google_android_gms_internal_zzaom = new zzaom(reader);
        Object zza = zza(com_google_android_gms_internal_zzaom, type);
        zza(zza, com_google_android_gms_internal_zzaom);
        return zza;
    }

    public <T> T zza(String str, Type type) throws zzane {
        return str == null ? null : zza(new StringReader(str), type);
    }

    public void zza(zzamv com_google_android_gms_internal_zzamv, zzaoo com_google_android_gms_internal_zzaoo) throws zzamw {
        boolean isLenient = com_google_android_gms_internal_zzaoo.isLenient();
        com_google_android_gms_internal_zzaoo.setLenient(true);
        boolean x = com_google_android_gms_internal_zzaoo.m380x();
        com_google_android_gms_internal_zzaoo.zzdc(this.bdT);
        boolean y = com_google_android_gms_internal_zzaoo.m381y();
        com_google_android_gms_internal_zzaoo.zzdd(this.bdS);
        try {
            zzanw.zzb(com_google_android_gms_internal_zzamv, com_google_android_gms_internal_zzaoo);
            com_google_android_gms_internal_zzaoo.setLenient(isLenient);
            com_google_android_gms_internal_zzaoo.zzdc(x);
            com_google_android_gms_internal_zzaoo.zzdd(y);
        } catch (Throwable e) {
            throw new zzamw(e);
        } catch (Throwable th) {
            com_google_android_gms_internal_zzaoo.setLenient(isLenient);
            com_google_android_gms_internal_zzaoo.zzdc(x);
            com_google_android_gms_internal_zzaoo.zzdd(y);
        }
    }

    public void zza(zzamv com_google_android_gms_internal_zzamv, Appendable appendable) throws zzamw {
        try {
            zza(com_google_android_gms_internal_zzamv, zza(zzanw.zza(appendable)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void zza(Object obj, Type type, zzaoo com_google_android_gms_internal_zzaoo) throws zzamw {
        zzanh zza = zza(zzaol.zzl(type));
        boolean isLenient = com_google_android_gms_internal_zzaoo.isLenient();
        com_google_android_gms_internal_zzaoo.setLenient(true);
        boolean x = com_google_android_gms_internal_zzaoo.m380x();
        com_google_android_gms_internal_zzaoo.zzdc(this.bdT);
        boolean y = com_google_android_gms_internal_zzaoo.m381y();
        com_google_android_gms_internal_zzaoo.zzdd(this.bdS);
        try {
            zza.zza(com_google_android_gms_internal_zzaoo, obj);
            com_google_android_gms_internal_zzaoo.setLenient(isLenient);
            com_google_android_gms_internal_zzaoo.zzdc(x);
            com_google_android_gms_internal_zzaoo.zzdd(y);
        } catch (Throwable e) {
            throw new zzamw(e);
        } catch (Throwable th) {
            com_google_android_gms_internal_zzaoo.setLenient(isLenient);
            com_google_android_gms_internal_zzaoo.zzdc(x);
            com_google_android_gms_internal_zzaoo.zzdd(y);
        }
    }

    public void zza(Object obj, Type type, Appendable appendable) throws zzamw {
        try {
            zza(obj, type, zza(zzanw.zza(appendable)));
        } catch (Throwable e) {
            throw new zzamw(e);
        }
    }

    public String zzb(zzamv com_google_android_gms_internal_zzamv) {
        Appendable stringWriter = new StringWriter();
        zza(com_google_android_gms_internal_zzamv, stringWriter);
        return stringWriter.toString();
    }

    public String zzc(Object obj, Type type) {
        Appendable stringWriter = new StringWriter();
        zza(obj, type, stringWriter);
        return stringWriter.toString();
    }

    public String zzch(Object obj) {
        return obj == null ? zzb(zzamx.bei) : zzc(obj, obj.getClass());
    }

    public <T> T zzf(String str, Class<T> cls) throws zzane {
        return zzanv.zzp(cls).cast(zza(str, (Type) cls));
    }

    public <T> zzanh<T> zzk(Class<T> cls) {
        return zza(zzaol.zzr(cls));
    }
}
