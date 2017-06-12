package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public final class zzaok {
    public static final zzanh<Class> bfX;
    public static final zzani bfY;
    public static final zzanh<BitSet> bfZ;
    public static final zzani bgA;
    public static final zzanh<URI> bgB;
    public static final zzani bgC;
    public static final zzanh<InetAddress> bgD;
    public static final zzani bgE;
    public static final zzanh<UUID> bgF;
    public static final zzani bgG;
    public static final zzani bgH;
    public static final zzanh<Calendar> bgI;
    public static final zzani bgJ;
    public static final zzanh<Locale> bgK;
    public static final zzani bgL;
    public static final zzanh<zzamv> bgM;
    public static final zzani bgN;
    public static final zzani bgO;
    public static final zzani bga;
    public static final zzanh<Boolean> bgb;
    public static final zzanh<Boolean> bgc;
    public static final zzani bgd;
    public static final zzanh<Number> bge;
    public static final zzani bgf;
    public static final zzanh<Number> bgg;
    public static final zzani bgh;
    public static final zzanh<Number> bgi;
    public static final zzani bgj;
    public static final zzanh<Number> bgk;
    public static final zzanh<Number> bgl;
    public static final zzanh<Number> bgm;
    public static final zzanh<Number> bgn;
    public static final zzani bgo;
    public static final zzanh<Character> bgp;
    public static final zzani bgq;
    public static final zzanh<String> bgr;
    public static final zzanh<BigDecimal> bgs;
    public static final zzanh<BigInteger> bgt;
    public static final zzani bgu;
    public static final zzanh<StringBuilder> bgv;
    public static final zzani bgw;
    public static final zzanh<StringBuffer> bgx;
    public static final zzani bgy;
    public static final zzanh<URL> bgz;

    /* renamed from: com.google.android.gms.internal.zzaok.26 */
    static /* synthetic */ class AnonymousClass26 {
        static final /* synthetic */ int[] bfK;

        static {
            bfK = new int[zzaon.values().length];
            try {
                bfK[zzaon.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bfK[zzaon.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bfK[zzaon.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bfK[zzaon.NULL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                bfK[zzaon.BEGIN_ARRAY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                bfK[zzaon.BEGIN_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                bfK[zzaon.END_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                bfK[zzaon.NAME.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                bfK[zzaon.END_OBJECT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                bfK[zzaon.END_ARRAY.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.1 */
    static class C06961 extends zzanh<Class> {
        C06961() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Class cls) throws IOException {
            if (cls == null) {
                com_google_android_gms_internal_zzaoo.m379l();
            } else {
                String valueOf = String.valueOf(cls.getName());
                throw new UnsupportedOperationException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Attempted to serialize java.lang.Class: ").append(valueOf).append(". Forgot to register a type adapter?").toString());
            }
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzo(com_google_android_gms_internal_zzaom);
        }

        public Class zzo(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.20 */
    static class AnonymousClass20 implements zzani {
        final /* synthetic */ zzaol beT;
        final /* synthetic */ zzanh bgR;

        AnonymousClass20(zzaol com_google_android_gms_internal_zzaol, zzanh com_google_android_gms_internal_zzanh) {
            this.beT = com_google_android_gms_internal_zzaol;
            this.bgR = com_google_android_gms_internal_zzanh;
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            return com_google_android_gms_internal_zzaol_T.equals(this.beT) ? this.bgR : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.21 */
    static class AnonymousClass21 implements zzani {
        final /* synthetic */ zzanh bgR;
        final /* synthetic */ Class bgS;

        AnonymousClass21(Class cls, zzanh com_google_android_gms_internal_zzanh) {
            this.bgS = cls;
            this.bgR = com_google_android_gms_internal_zzanh;
        }

        public String toString() {
            String valueOf = String.valueOf(this.bgS.getName());
            String valueOf2 = String.valueOf(this.bgR);
            return new StringBuilder((String.valueOf(valueOf).length() + 23) + String.valueOf(valueOf2).length()).append("Factory[type=").append(valueOf).append(",adapter=").append(valueOf2).append("]").toString();
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            return com_google_android_gms_internal_zzaol_T.m359m() == this.bgS ? this.bgR : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.22 */
    static class AnonymousClass22 implements zzani {
        final /* synthetic */ zzanh bgR;
        final /* synthetic */ Class bgT;
        final /* synthetic */ Class bgU;

        AnonymousClass22(Class cls, Class cls2, zzanh com_google_android_gms_internal_zzanh) {
            this.bgT = cls;
            this.bgU = cls2;
            this.bgR = com_google_android_gms_internal_zzanh;
        }

        public String toString() {
            String valueOf = String.valueOf(this.bgU.getName());
            String valueOf2 = String.valueOf(this.bgT.getName());
            String valueOf3 = String.valueOf(this.bgR);
            return new StringBuilder(((String.valueOf(valueOf).length() + 24) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()).append("Factory[type=").append(valueOf).append("+").append(valueOf2).append(",adapter=").append(valueOf3).append("]").toString();
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            Class m = com_google_android_gms_internal_zzaol_T.m359m();
            return (m == this.bgT || m == this.bgU) ? this.bgR : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.24 */
    static class AnonymousClass24 implements zzani {
        final /* synthetic */ zzanh bgR;
        final /* synthetic */ Class bgV;
        final /* synthetic */ Class bgW;

        AnonymousClass24(Class cls, Class cls2, zzanh com_google_android_gms_internal_zzanh) {
            this.bgV = cls;
            this.bgW = cls2;
            this.bgR = com_google_android_gms_internal_zzanh;
        }

        public String toString() {
            String valueOf = String.valueOf(this.bgV.getName());
            String valueOf2 = String.valueOf(this.bgW.getName());
            String valueOf3 = String.valueOf(this.bgR);
            return new StringBuilder(((String.valueOf(valueOf).length() + 24) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()).append("Factory[type=").append(valueOf).append("+").append(valueOf2).append(",adapter=").append(valueOf3).append("]").toString();
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            Class m = com_google_android_gms_internal_zzaol_T.m359m();
            return (m == this.bgV || m == this.bgW) ? this.bgR : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.25 */
    static class AnonymousClass25 implements zzani {
        final /* synthetic */ zzanh bgR;
        final /* synthetic */ Class bgX;

        AnonymousClass25(Class cls, zzanh com_google_android_gms_internal_zzanh) {
            this.bgX = cls;
            this.bgR = com_google_android_gms_internal_zzanh;
        }

        public String toString() {
            String valueOf = String.valueOf(this.bgX.getName());
            String valueOf2 = String.valueOf(this.bgR);
            return new StringBuilder((String.valueOf(valueOf).length() + 32) + String.valueOf(valueOf2).length()).append("Factory[typeHierarchy=").append(valueOf).append(",adapter=").append(valueOf2).append("]").toString();
        }

        public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
            return this.bgX.isAssignableFrom(com_google_android_gms_internal_zzaol_T.m359m()) ? this.bgR : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.2 */
    static class C06972 extends zzanh<Number> {
        C06972() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
            com_google_android_gms_internal_zzaoo.zza(number);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzg(com_google_android_gms_internal_zzaom);
        }

        public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return Double.valueOf(com_google_android_gms_internal_zzaom.nextDouble());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.3 */
    static class C06983 extends zzanh<Number> {
        C06983() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
            com_google_android_gms_internal_zzaoo.zza(number);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzg(com_google_android_gms_internal_zzaom);
        }

        public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            zzaon b = com_google_android_gms_internal_zzaom.m370b();
            switch (AnonymousClass26.bfK[b.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    return new zzans(com_google_android_gms_internal_zzaom.nextString());
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                default:
                    String valueOf = String.valueOf(b);
                    throw new zzane(new StringBuilder(String.valueOf(valueOf).length() + 23).append("Expecting number, got: ").append(valueOf).toString());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.4 */
    static class C06994 extends zzanh<Character> {
        C06994() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, Character ch) throws IOException {
            com_google_android_gms_internal_zzaoo.zzts(ch == null ? null : String.valueOf(ch));
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzp(com_google_android_gms_internal_zzaom);
        }

        public Character zzp(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            String nextString = com_google_android_gms_internal_zzaom.nextString();
            if (nextString.length() == 1) {
                return Character.valueOf(nextString.charAt(0));
            }
            String str = "Expecting character, got: ";
            nextString = String.valueOf(nextString);
            throw new zzane(nextString.length() != 0 ? str.concat(nextString) : new String(str));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.5 */
    static class C07005 extends zzanh<String> {
        C07005() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, String str) throws IOException {
            com_google_android_gms_internal_zzaoo.zzts(str);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzq(com_google_android_gms_internal_zzaom);
        }

        public String zzq(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            zzaon b = com_google_android_gms_internal_zzaom.m370b();
            if (b != zzaon.NULL) {
                return b == zzaon.BOOLEAN ? Boolean.toString(com_google_android_gms_internal_zzaom.nextBoolean()) : com_google_android_gms_internal_zzaom.nextString();
            } else {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.6 */
    static class C07016 extends zzanh<BigDecimal> {
        C07016() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, BigDecimal bigDecimal) throws IOException {
            com_google_android_gms_internal_zzaoo.zza(bigDecimal);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzr(com_google_android_gms_internal_zzaom);
        }

        public BigDecimal zzr(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            try {
                return new BigDecimal(com_google_android_gms_internal_zzaom.nextString());
            } catch (Throwable e) {
                throw new zzane(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.7 */
    static class C07027 extends zzanh<BigInteger> {
        C07027() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, BigInteger bigInteger) throws IOException {
            com_google_android_gms_internal_zzaoo.zza(bigInteger);
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzs(com_google_android_gms_internal_zzaom);
        }

        public BigInteger zzs(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
            try {
                return new BigInteger(com_google_android_gms_internal_zzaom.nextString());
            } catch (Throwable e) {
                throw new zzane(e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.8 */
    static class C07038 extends zzanh<StringBuilder> {
        C07038() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, StringBuilder stringBuilder) throws IOException {
            com_google_android_gms_internal_zzaoo.zzts(stringBuilder == null ? null : stringBuilder.toString());
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzt(com_google_android_gms_internal_zzaom);
        }

        public StringBuilder zzt(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return new StringBuilder(com_google_android_gms_internal_zzaom.nextString());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaok.9 */
    static class C07049 extends zzanh<StringBuffer> {
        C07049() {
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, StringBuffer stringBuffer) throws IOException {
            com_google_android_gms_internal_zzaoo.zzts(stringBuffer == null ? null : stringBuffer.toString());
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzu(com_google_android_gms_internal_zzaom);
        }

        public StringBuffer zzu(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return new StringBuffer(com_google_android_gms_internal_zzaom.nextString());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }
    }

    private static final class zza<T extends Enum<T>> extends zzanh<T> {
        private final Map<String, T> bgY;
        private final Map<T, String> bgZ;

        public zza(Class<T> cls) {
            this.bgY = new HashMap();
            this.bgZ = new HashMap();
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    String name = enumR.name();
                    zzank com_google_android_gms_internal_zzank = (zzank) cls.getField(name).getAnnotation(zzank.class);
                    if (com_google_android_gms_internal_zzank != null) {
                        name = com_google_android_gms_internal_zzank.value();
                        for (Object put : com_google_android_gms_internal_zzank.zzczs()) {
                            this.bgY.put(put, enumR);
                        }
                    }
                    String str = name;
                    this.bgY.put(str, enumR);
                    this.bgZ.put(enumR, str);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }

        public void zza(zzaoo com_google_android_gms_internal_zzaoo, T t) throws IOException {
            com_google_android_gms_internal_zzaoo.zzts(t == null ? null : (String) this.bgZ.get(t));
        }

        public T zzaf(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                return (Enum) this.bgY.get(com_google_android_gms_internal_zzaom.nextString());
            }
            com_google_android_gms_internal_zzaom.nextNull();
            return null;
        }

        public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
            return zzaf(com_google_android_gms_internal_zzaom);
        }
    }

    static {
        bfX = new C06961();
        bfY = zza(Class.class, bfX);
        bfZ = new zzanh<BitSet>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, BitSet bitSet) throws IOException {
                if (bitSet == null) {
                    com_google_android_gms_internal_zzaoo.m379l();
                    return;
                }
                com_google_android_gms_internal_zzaoo.m375h();
                for (int i = 0; i < bitSet.length(); i++) {
                    com_google_android_gms_internal_zzaoo.zzcr((long) (bitSet.get(i) ? 1 : 0));
                }
                com_google_android_gms_internal_zzaoo.m376i();
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzx(com_google_android_gms_internal_zzaom);
            }

            public BitSet zzx(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                String valueOf;
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                BitSet bitSet = new BitSet();
                com_google_android_gms_internal_zzaom.beginArray();
                zzaon b = com_google_android_gms_internal_zzaom.m370b();
                int i = 0;
                while (b != zzaon.END_ARRAY) {
                    boolean z;
                    switch (AnonymousClass26.bfK[b.ordinal()]) {
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            if (com_google_android_gms_internal_zzaom.nextInt() == 0) {
                                z = false;
                                break;
                            }
                            z = true;
                            break;
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            z = com_google_android_gms_internal_zzaom.nextBoolean();
                            break;
                        case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                            Object nextString = com_google_android_gms_internal_zzaom.nextString();
                            try {
                                if (Integer.parseInt(nextString) == 0) {
                                    z = false;
                                    break;
                                }
                                z = true;
                                break;
                            } catch (NumberFormatException e) {
                                String str = "Error: Expecting: bitset number value (1, 0), Found: ";
                                valueOf = String.valueOf(nextString);
                                throw new zzane(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                            }
                        default:
                            valueOf = String.valueOf(b);
                            throw new zzane(new StringBuilder(String.valueOf(valueOf).length() + 27).append("Invalid bitset value type: ").append(valueOf).toString());
                    }
                    if (z) {
                        bitSet.set(i);
                    }
                    i++;
                    b = com_google_android_gms_internal_zzaom.m370b();
                }
                com_google_android_gms_internal_zzaom.endArray();
                return bitSet;
            }
        };
        bga = zza(BitSet.class, bfZ);
        bgb = new zzanh<Boolean>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Boolean bool) throws IOException {
                if (bool == null) {
                    com_google_android_gms_internal_zzaoo.m379l();
                } else {
                    com_google_android_gms_internal_zzaoo.zzda(bool.booleanValue());
                }
            }

            public Boolean zzae(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                    return com_google_android_gms_internal_zzaom.m370b() == zzaon.STRING ? Boolean.valueOf(Boolean.parseBoolean(com_google_android_gms_internal_zzaom.nextString())) : Boolean.valueOf(com_google_android_gms_internal_zzaom.nextBoolean());
                } else {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzae(com_google_android_gms_internal_zzaom);
            }
        };
        bgc = new zzanh<Boolean>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Boolean bool) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(bool == null ? "null" : bool.toString());
            }

            public Boolean zzae(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                    return Boolean.valueOf(com_google_android_gms_internal_zzaom.nextString());
                }
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzae(com_google_android_gms_internal_zzaom);
            }
        };
        bgd = zza(Boolean.TYPE, Boolean.class, bgb);
        bge = new zzanh<Number>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
                com_google_android_gms_internal_zzaoo.zza(number);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzg(com_google_android_gms_internal_zzaom);
            }

            public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                try {
                    return Byte.valueOf((byte) com_google_android_gms_internal_zzaom.nextInt());
                } catch (Throwable e) {
                    throw new zzane(e);
                }
            }
        };
        bgf = zza(Byte.TYPE, Byte.class, bge);
        bgg = new zzanh<Number>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
                com_google_android_gms_internal_zzaoo.zza(number);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzg(com_google_android_gms_internal_zzaom);
            }

            public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                try {
                    return Short.valueOf((short) com_google_android_gms_internal_zzaom.nextInt());
                } catch (Throwable e) {
                    throw new zzane(e);
                }
            }
        };
        bgh = zza(Short.TYPE, Short.class, bgg);
        bgi = new zzanh<Number>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
                com_google_android_gms_internal_zzaoo.zza(number);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzg(com_google_android_gms_internal_zzaom);
            }

            public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                try {
                    return Integer.valueOf(com_google_android_gms_internal_zzaom.nextInt());
                } catch (Throwable e) {
                    throw new zzane(e);
                }
            }
        };
        bgj = zza(Integer.TYPE, Integer.class, bgi);
        bgk = new zzanh<Number>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
                com_google_android_gms_internal_zzaoo.zza(number);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzg(com_google_android_gms_internal_zzaom);
            }

            public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                try {
                    return Long.valueOf(com_google_android_gms_internal_zzaom.nextLong());
                } catch (Throwable e) {
                    throw new zzane(e);
                }
            }
        };
        bgl = new zzanh<Number>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Number number) throws IOException {
                com_google_android_gms_internal_zzaoo.zza(number);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzg(com_google_android_gms_internal_zzaom);
            }

            public Number zzg(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                    return Float.valueOf((float) com_google_android_gms_internal_zzaom.nextDouble());
                }
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
        };
        bgm = new C06972();
        bgn = new C06983();
        bgo = zza(Number.class, bgn);
        bgp = new C06994();
        bgq = zza(Character.TYPE, Character.class, bgp);
        bgr = new C07005();
        bgs = new C07016();
        bgt = new C07027();
        bgu = zza(String.class, bgr);
        bgv = new C07038();
        bgw = zza(StringBuilder.class, bgv);
        bgx = new C07049();
        bgy = zza(StringBuffer.class, bgx);
        bgz = new zzanh<URL>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, URL url) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(url == null ? null : url.toExternalForm());
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzv(com_google_android_gms_internal_zzaom);
            }

            public URL zzv(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                String nextString = com_google_android_gms_internal_zzaom.nextString();
                return !"null".equals(nextString) ? new URL(nextString) : null;
            }
        };
        bgA = zza(URL.class, bgz);
        bgB = new zzanh<URI>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, URI uri) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(uri == null ? null : uri.toASCIIString());
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzw(com_google_android_gms_internal_zzaom);
            }

            public URI zzw(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                URI uri = null;
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                } else {
                    try {
                        String nextString = com_google_android_gms_internal_zzaom.nextString();
                        if (!"null".equals(nextString)) {
                            uri = new URI(nextString);
                        }
                    } catch (Throwable e) {
                        throw new zzamw(e);
                    }
                }
                return uri;
            }
        };
        bgC = zza(URI.class, bgB);
        bgD = new zzanh<InetAddress>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, InetAddress inetAddress) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(inetAddress == null ? null : inetAddress.getHostAddress());
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzy(com_google_android_gms_internal_zzaom);
            }

            public InetAddress zzy(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                    return InetAddress.getByName(com_google_android_gms_internal_zzaom.nextString());
                }
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
        };
        bgE = zzb(InetAddress.class, bgD);
        bgF = new zzanh<UUID>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, UUID uuid) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(uuid == null ? null : uuid.toString());
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzz(com_google_android_gms_internal_zzaom);
            }

            public UUID zzz(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() != zzaon.NULL) {
                    return UUID.fromString(com_google_android_gms_internal_zzaom.nextString());
                }
                com_google_android_gms_internal_zzaom.nextNull();
                return null;
            }
        };
        bgG = zza(UUID.class, bgF);
        bgH = new zzani() {

            /* renamed from: com.google.android.gms.internal.zzaok.15.1 */
            class C06951 extends zzanh<Timestamp> {
                final /* synthetic */ zzanh bgP;
                final /* synthetic */ AnonymousClass15 bgQ;

                C06951(AnonymousClass15 anonymousClass15, zzanh com_google_android_gms_internal_zzanh) {
                    this.bgQ = anonymousClass15;
                    this.bgP = com_google_android_gms_internal_zzanh;
                }

                public void zza(zzaoo com_google_android_gms_internal_zzaoo, Timestamp timestamp) throws IOException {
                    this.bgP.zza(com_google_android_gms_internal_zzaoo, timestamp);
                }

                public Timestamp zzaa(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                    Date date = (Date) this.bgP.zzb(com_google_android_gms_internal_zzaom);
                    return date != null ? new Timestamp(date.getTime()) : null;
                }

                public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                    return zzaa(com_google_android_gms_internal_zzaom);
                }
            }

            public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
                return com_google_android_gms_internal_zzaol_T.m359m() != Timestamp.class ? null : new C06951(this, com_google_android_gms_internal_zzamp.zzk(Date.class));
            }
        };
        bgI = new zzanh<Calendar>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Calendar calendar) throws IOException {
                if (calendar == null) {
                    com_google_android_gms_internal_zzaoo.m379l();
                    return;
                }
                com_google_android_gms_internal_zzaoo.m377j();
                com_google_android_gms_internal_zzaoo.zztr("year");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(1));
                com_google_android_gms_internal_zzaoo.zztr("month");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(2));
                com_google_android_gms_internal_zzaoo.zztr("dayOfMonth");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(5));
                com_google_android_gms_internal_zzaoo.zztr("hourOfDay");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(11));
                com_google_android_gms_internal_zzaoo.zztr("minute");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(12));
                com_google_android_gms_internal_zzaoo.zztr("second");
                com_google_android_gms_internal_zzaoo.zzcr((long) calendar.get(13));
                com_google_android_gms_internal_zzaoo.m378k();
            }

            public Calendar zzab(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                int i = 0;
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                com_google_android_gms_internal_zzaom.beginObject();
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                while (com_google_android_gms_internal_zzaom.m370b() != zzaon.END_OBJECT) {
                    String nextName = com_google_android_gms_internal_zzaom.nextName();
                    int nextInt = com_google_android_gms_internal_zzaom.nextInt();
                    if ("year".equals(nextName)) {
                        i6 = nextInt;
                    } else if ("month".equals(nextName)) {
                        i5 = nextInt;
                    } else if ("dayOfMonth".equals(nextName)) {
                        i4 = nextInt;
                    } else if ("hourOfDay".equals(nextName)) {
                        i3 = nextInt;
                    } else if ("minute".equals(nextName)) {
                        i2 = nextInt;
                    } else if ("second".equals(nextName)) {
                        i = nextInt;
                    }
                }
                com_google_android_gms_internal_zzaom.endObject();
                return new GregorianCalendar(i6, i5, i4, i3, i2, i);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzab(com_google_android_gms_internal_zzaom);
            }
        };
        bgJ = zzb(Calendar.class, GregorianCalendar.class, bgI);
        bgK = new zzanh<Locale>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, Locale locale) throws IOException {
                com_google_android_gms_internal_zzaoo.zzts(locale == null ? null : locale.toString());
            }

            public Locale zzac(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                if (com_google_android_gms_internal_zzaom.m370b() == zzaon.NULL) {
                    com_google_android_gms_internal_zzaom.nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(com_google_android_gms_internal_zzaom.nextString(), "_");
                String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
                return (nextToken2 == null && nextToken3 == null) ? new Locale(nextToken) : nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzac(com_google_android_gms_internal_zzaom);
            }
        };
        bgL = zza(Locale.class, bgK);
        bgM = new zzanh<zzamv>() {
            public void zza(zzaoo com_google_android_gms_internal_zzaoo, zzamv com_google_android_gms_internal_zzamv) throws IOException {
                if (com_google_android_gms_internal_zzamv == null || com_google_android_gms_internal_zzamv.zzczj()) {
                    com_google_android_gms_internal_zzaoo.m379l();
                } else if (com_google_android_gms_internal_zzamv.zzczi()) {
                    zzanb zzczm = com_google_android_gms_internal_zzamv.zzczm();
                    if (zzczm.zzczp()) {
                        com_google_android_gms_internal_zzaoo.zza(zzczm.zzcze());
                    } else if (zzczm.zzczo()) {
                        com_google_android_gms_internal_zzaoo.zzda(zzczm.getAsBoolean());
                    } else {
                        com_google_android_gms_internal_zzaoo.zzts(zzczm.zzczf());
                    }
                } else if (com_google_android_gms_internal_zzamv.zzczg()) {
                    com_google_android_gms_internal_zzaoo.m375h();
                    Iterator it = com_google_android_gms_internal_zzamv.zzczl().iterator();
                    while (it.hasNext()) {
                        zza(com_google_android_gms_internal_zzaoo, (zzamv) it.next());
                    }
                    com_google_android_gms_internal_zzaoo.m376i();
                } else if (com_google_android_gms_internal_zzamv.zzczh()) {
                    com_google_android_gms_internal_zzaoo.m377j();
                    for (Entry entry : com_google_android_gms_internal_zzamv.zzczk().entrySet()) {
                        com_google_android_gms_internal_zzaoo.zztr((String) entry.getKey());
                        zza(com_google_android_gms_internal_zzaoo, (zzamv) entry.getValue());
                    }
                    com_google_android_gms_internal_zzaoo.m378k();
                } else {
                    String valueOf = String.valueOf(com_google_android_gms_internal_zzamv.getClass());
                    throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 15).append("Couldn't write ").append(valueOf).toString());
                }
            }

            public zzamv zzad(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                zzamv com_google_android_gms_internal_zzams;
                switch (AnonymousClass26.bfK[com_google_android_gms_internal_zzaom.m370b().ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        return new zzanb(new zzans(com_google_android_gms_internal_zzaom.nextString()));
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        return new zzanb(Boolean.valueOf(com_google_android_gms_internal_zzaom.nextBoolean()));
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        return new zzanb(com_google_android_gms_internal_zzaom.nextString());
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                        com_google_android_gms_internal_zzaom.nextNull();
                        return zzamx.bei;
                    case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                        com_google_android_gms_internal_zzams = new zzams();
                        com_google_android_gms_internal_zzaom.beginArray();
                        while (com_google_android_gms_internal_zzaom.hasNext()) {
                            com_google_android_gms_internal_zzams.zzc((zzamv) zzb(com_google_android_gms_internal_zzaom));
                        }
                        com_google_android_gms_internal_zzaom.endArray();
                        return com_google_android_gms_internal_zzams;
                    case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                        com_google_android_gms_internal_zzams = new zzamy();
                        com_google_android_gms_internal_zzaom.beginObject();
                        while (com_google_android_gms_internal_zzaom.hasNext()) {
                            com_google_android_gms_internal_zzams.zza(com_google_android_gms_internal_zzaom.nextName(), (zzamv) zzb(com_google_android_gms_internal_zzaom));
                        }
                        com_google_android_gms_internal_zzaom.endObject();
                        return com_google_android_gms_internal_zzams;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            public /* synthetic */ Object zzb(zzaom com_google_android_gms_internal_zzaom) throws IOException {
                return zzad(com_google_android_gms_internal_zzaom);
            }
        };
        bgN = zzb(zzamv.class, bgM);
        bgO = new zzani() {
            public <T> zzanh<T> zza(zzamp com_google_android_gms_internal_zzamp, zzaol<T> com_google_android_gms_internal_zzaol_T) {
                Class m = com_google_android_gms_internal_zzaol_T.m359m();
                if (!Enum.class.isAssignableFrom(m) || m == Enum.class) {
                    return null;
                }
                if (!m.isEnum()) {
                    m = m.getSuperclass();
                }
                return new zza(m);
            }
        };
    }

    public static <TT> zzani zza(zzaol<TT> com_google_android_gms_internal_zzaol_TT, zzanh<TT> com_google_android_gms_internal_zzanh_TT) {
        return new AnonymousClass20(com_google_android_gms_internal_zzaol_TT, com_google_android_gms_internal_zzanh_TT);
    }

    public static <TT> zzani zza(Class<TT> cls, zzanh<TT> com_google_android_gms_internal_zzanh_TT) {
        return new AnonymousClass21(cls, com_google_android_gms_internal_zzanh_TT);
    }

    public static <TT> zzani zza(Class<TT> cls, Class<TT> cls2, zzanh<? super TT> com_google_android_gms_internal_zzanh__super_TT) {
        return new AnonymousClass22(cls, cls2, com_google_android_gms_internal_zzanh__super_TT);
    }

    public static <TT> zzani zzb(Class<TT> cls, zzanh<TT> com_google_android_gms_internal_zzanh_TT) {
        return new AnonymousClass25(cls, com_google_android_gms_internal_zzanh_TT);
    }

    public static <TT> zzani zzb(Class<TT> cls, Class<? extends TT> cls2, zzanh<? super TT> com_google_android_gms_internal_zzanh__super_TT) {
        return new AnonymousClass24(cls, cls2, com_google_android_gms_internal_zzanh__super_TT);
    }
}
