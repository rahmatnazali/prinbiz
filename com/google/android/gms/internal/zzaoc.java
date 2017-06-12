package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class zzaoc extends zzaom {
    private static final Object bfA;
    private static final Reader bfz;
    private final List<Object> bfB;

    /* renamed from: com.google.android.gms.internal.zzaoc.1 */
    static class C02041 extends Reader {
        C02041() {
        }

        public void close() throws IOException {
            throw new AssertionError();
        }

        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }
    }

    static {
        bfz = new C02041();
        bfA = new Object();
    }

    public zzaoc(zzamv com_google_android_gms_internal_zzamv) {
        super(bfz);
        this.bfB = new ArrayList();
        this.bfB.add(com_google_android_gms_internal_zzamv);
    }

    private Object m676c() {
        return this.bfB.get(this.bfB.size() - 1);
    }

    private Object m677d() {
        return this.bfB.remove(this.bfB.size() - 1);
    }

    private void zza(zzaon com_google_android_gms_internal_zzaon) throws IOException {
        if (m678b() != com_google_android_gms_internal_zzaon) {
            String valueOf = String.valueOf(com_google_android_gms_internal_zzaon);
            String valueOf2 = String.valueOf(m678b());
            throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 18) + String.valueOf(valueOf2).length()).append("Expected ").append(valueOf).append(" but was ").append(valueOf2).toString());
        }
    }

    public zzaon m678b() throws IOException {
        if (this.bfB.isEmpty()) {
            return zzaon.END_DOCUMENT;
        }
        Object c = m676c();
        if (c instanceof Iterator) {
            boolean z = this.bfB.get(this.bfB.size() - 2) instanceof zzamy;
            Iterator it = (Iterator) c;
            if (!it.hasNext()) {
                return z ? zzaon.END_OBJECT : zzaon.END_ARRAY;
            } else {
                if (z) {
                    return zzaon.NAME;
                }
                this.bfB.add(it.next());
                return m678b();
            }
        } else if (c instanceof zzamy) {
            return zzaon.BEGIN_OBJECT;
        } else {
            if (c instanceof zzams) {
                return zzaon.BEGIN_ARRAY;
            }
            if (c instanceof zzanb) {
                zzanb com_google_android_gms_internal_zzanb = (zzanb) c;
                if (com_google_android_gms_internal_zzanb.zzczq()) {
                    return zzaon.STRING;
                }
                if (com_google_android_gms_internal_zzanb.zzczo()) {
                    return zzaon.BOOLEAN;
                }
                if (com_google_android_gms_internal_zzanb.zzczp()) {
                    return zzaon.NUMBER;
                }
                throw new AssertionError();
            } else if (c instanceof zzamx) {
                return zzaon.NULL;
            } else {
                if (c == bfA) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    public void beginArray() throws IOException {
        zza(zzaon.BEGIN_ARRAY);
        this.bfB.add(((zzams) m676c()).iterator());
    }

    public void beginObject() throws IOException {
        zza(zzaon.BEGIN_OBJECT);
        this.bfB.add(((zzamy) m676c()).entrySet().iterator());
    }

    public void close() throws IOException {
        this.bfB.clear();
        this.bfB.add(bfA);
    }

    public void m679e() throws IOException {
        zza(zzaon.NAME);
        Entry entry = (Entry) ((Iterator) m676c()).next();
        this.bfB.add(entry.getValue());
        this.bfB.add(new zzanb((String) entry.getKey()));
    }

    public void endArray() throws IOException {
        zza(zzaon.END_ARRAY);
        m677d();
        m677d();
    }

    public void endObject() throws IOException {
        zza(zzaon.END_OBJECT);
        m677d();
        m677d();
    }

    public boolean hasNext() throws IOException {
        zzaon b = m678b();
        return (b == zzaon.END_OBJECT || b == zzaon.END_ARRAY) ? false : true;
    }

    public boolean nextBoolean() throws IOException {
        zza(zzaon.BOOLEAN);
        return ((zzanb) m677d()).getAsBoolean();
    }

    public double nextDouble() throws IOException {
        zzaon b = m678b();
        if (b == zzaon.NUMBER || b == zzaon.STRING) {
            double asDouble = ((zzanb) m676c()).getAsDouble();
            if (isLenient() || !(Double.isNaN(asDouble) || Double.isInfinite(asDouble))) {
                m677d();
                return asDouble;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + asDouble);
        }
        String valueOf = String.valueOf(zzaon.NUMBER);
        String valueOf2 = String.valueOf(b);
        throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 18) + String.valueOf(valueOf2).length()).append("Expected ").append(valueOf).append(" but was ").append(valueOf2).toString());
    }

    public int nextInt() throws IOException {
        zzaon b = m678b();
        if (b == zzaon.NUMBER || b == zzaon.STRING) {
            int asInt = ((zzanb) m676c()).getAsInt();
            m677d();
            return asInt;
        }
        String valueOf = String.valueOf(zzaon.NUMBER);
        String valueOf2 = String.valueOf(b);
        throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 18) + String.valueOf(valueOf2).length()).append("Expected ").append(valueOf).append(" but was ").append(valueOf2).toString());
    }

    public long nextLong() throws IOException {
        zzaon b = m678b();
        if (b == zzaon.NUMBER || b == zzaon.STRING) {
            long asLong = ((zzanb) m676c()).getAsLong();
            m677d();
            return asLong;
        }
        String valueOf = String.valueOf(zzaon.NUMBER);
        String valueOf2 = String.valueOf(b);
        throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 18) + String.valueOf(valueOf2).length()).append("Expected ").append(valueOf).append(" but was ").append(valueOf2).toString());
    }

    public String nextName() throws IOException {
        zza(zzaon.NAME);
        Entry entry = (Entry) ((Iterator) m676c()).next();
        this.bfB.add(entry.getValue());
        return (String) entry.getKey();
    }

    public void nextNull() throws IOException {
        zza(zzaon.NULL);
        m677d();
    }

    public String nextString() throws IOException {
        zzaon b = m678b();
        if (b == zzaon.STRING || b == zzaon.NUMBER) {
            return ((zzanb) m677d()).zzczf();
        }
        String valueOf = String.valueOf(zzaon.STRING);
        String valueOf2 = String.valueOf(b);
        throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 18) + String.valueOf(valueOf2).length()).append("Expected ").append(valueOf).append(" but was ").append(valueOf2).toString());
    }

    public void skipValue() throws IOException {
        if (m678b() == zzaon.NAME) {
            nextName();
        } else {
            m677d();
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
