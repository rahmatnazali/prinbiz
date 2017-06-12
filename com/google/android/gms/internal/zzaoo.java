package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;

public class zzaoo implements Closeable, Flushable {
    private static final String[] bhA;
    private static final String[] bhB;
    private boolean bdS;
    private boolean bdT;
    private String bhC;
    private String bhD;
    private boolean bhd;
    private int[] bhl;
    private int bhm;
    private final Writer out;
    private String separator;

    static {
        bhA = new String[TransportMediator.FLAG_KEY_MEDIA_NEXT];
        for (int i = 0; i <= 31; i++) {
            bhA[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        bhA[34] = "\\\"";
        bhA[92] = "\\\\";
        bhA[9] = "\\t";
        bhA[8] = "\\b";
        bhA[10] = "\\n";
        bhA[13] = "\\r";
        bhA[12] = "\\f";
        bhB = (String[]) bhA.clone();
        bhB[60] = "\\u003c";
        bhB[62] = "\\u003e";
        bhB[38] = "\\u0026";
        bhB[61] = "\\u003d";
        bhB[39] = "\\u0027";
    }

    public zzaoo(Writer writer) {
        this.bhl = new int[32];
        this.bhm = 0;
        zzafl(6);
        this.separator = ":";
        this.bdS = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.out = writer;
    }

    private void m371A() throws IOException {
        if (this.bhD != null) {
            m373C();
            zztv(this.bhD);
            this.bhD = null;
        }
    }

    private void m372B() throws IOException {
        if (this.bhC != null) {
            this.out.write("\n");
            int i = this.bhm;
            for (int i2 = 1; i2 < i; i2++) {
                this.out.write(this.bhC);
            }
        }
    }

    private void m373C() throws IOException {
        int z = m374z();
        if (z == 5) {
            this.out.write(44);
        } else if (z != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        m372B();
        zzafn(4);
    }

    private int m374z() {
        if (this.bhm != 0) {
            return this.bhl[this.bhm - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void zzafl(int i) {
        if (this.bhm == this.bhl.length) {
            Object obj = new int[(this.bhm * 2)];
            System.arraycopy(this.bhl, 0, obj, 0, this.bhm);
            this.bhl = obj;
        }
        int[] iArr = this.bhl;
        int i2 = this.bhm;
        this.bhm = i2 + 1;
        iArr[i2] = i;
    }

    private void zzafn(int i) {
        this.bhl[this.bhm - 1] = i;
    }

    private zzaoo zzc(int i, int i2, String str) throws IOException {
        int z = m374z();
        if (z != i2 && z != i) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.bhD != null) {
            String str2 = "Dangling name: ";
            String valueOf = String.valueOf(this.bhD);
            throw new IllegalStateException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else {
            this.bhm--;
            if (z == i2) {
                m372B();
            }
            this.out.write(str);
            return this;
        }
    }

    private void zzde(boolean z) throws IOException {
        switch (m374z()) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                zzafn(2);
                m372B();
                return;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                this.out.append(',');
                m372B();
                return;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.out.append(this.separator);
                zzafn(5);
                return;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                break;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                if (!this.bhd) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
                break;
            default:
                throw new IllegalStateException("Nesting problem.");
        }
        if (this.bhd || z) {
            zzafn(7);
            return;
        }
        throw new IllegalStateException("JSON must start with an array or an object.");
    }

    private zzaoo zzq(int i, String str) throws IOException {
        zzde(true);
        zzafl(i);
        this.out.write(str);
        return this;
    }

    private void zztv(String str) throws IOException {
        int i = 0;
        String[] strArr = this.bdT ? bhB : bhA;
        this.out.write("\"");
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            String str2;
            if (charAt < '\u0080') {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
                if (i < i2) {
                    this.out.write(str, i, i2 - i);
                }
                this.out.write(str2);
                i = i2 + 1;
            } else {
                if (charAt == '\u2028') {
                    str2 = "\\u2028";
                } else if (charAt == '\u2029') {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    this.out.write(str, i, i2 - i);
                }
                this.out.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.out.write(str, i, length - i);
        }
        this.out.write("\"");
    }

    public void close() throws IOException {
        this.out.close();
        int i = this.bhm;
        if (i > 1 || (i == 1 && this.bhl[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.bhm = 0;
    }

    public void flush() throws IOException {
        if (this.bhm == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public zzaoo m375h() throws IOException {
        m371A();
        return zzq(1, "[");
    }

    public zzaoo m376i() throws IOException {
        return zzc(1, 2, "]");
    }

    public boolean isLenient() {
        return this.bhd;
    }

    public zzaoo m377j() throws IOException {
        m371A();
        return zzq(3, "{");
    }

    public zzaoo m378k() throws IOException {
        return zzc(3, 5, "}");
    }

    public zzaoo m379l() throws IOException {
        if (this.bhD != null) {
            if (this.bdS) {
                m371A();
            } else {
                this.bhD = null;
                return this;
            }
        }
        zzde(false);
        this.out.write("null");
        return this;
    }

    public final void setIndent(String str) {
        if (str.length() == 0) {
            this.bhC = null;
            this.separator = ":";
            return;
        }
        this.bhC = str;
        this.separator = ": ";
    }

    public final void setLenient(boolean z) {
        this.bhd = z;
    }

    public final boolean m380x() {
        return this.bdT;
    }

    public final boolean m381y() {
        return this.bdS;
    }

    public zzaoo zza(Number number) throws IOException {
        if (number == null) {
            return m379l();
        }
        m371A();
        CharSequence obj = number.toString();
        if (this.bhd || !(obj.equals("-Infinity") || obj.equals("Infinity") || obj.equals("NaN"))) {
            zzde(false);
            this.out.append(obj);
            return this;
        }
        String valueOf = String.valueOf(number);
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 39).append("Numeric values must be finite, but was ").append(valueOf).toString());
    }

    public zzaoo zzcr(long j) throws IOException {
        m371A();
        zzde(false);
        this.out.write(Long.toString(j));
        return this;
    }

    public zzaoo zzda(boolean z) throws IOException {
        m371A();
        zzde(false);
        this.out.write(z ? "true" : "false");
        return this;
    }

    public final void zzdc(boolean z) {
        this.bdT = z;
    }

    public final void zzdd(boolean z) {
        this.bdS = z;
    }

    public zzaoo zztr(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.bhD != null) {
            throw new IllegalStateException();
        } else if (this.bhm == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            this.bhD = str;
            return this;
        }
    }

    public zzaoo zzts(String str) throws IOException {
        if (str == null) {
            return m379l();
        }
        m371A();
        zzde(false);
        zztv(str);
        return this;
    }
}
