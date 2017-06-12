package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.xmlpull.v1.XmlPullParser;

public class ij {
    private static final String f107m;
    public int f108a;
    public long f109b;
    public long f110c;
    public boolean f111d;
    public int f112e;
    public ik f113f;
    public String f114g;
    public int f115h;
    public long f116i;
    public boolean f117j;
    public long f118k;
    public ii f119l;

    /* renamed from: com.flurry.sdk.ij.a */
    public static class C0581a implements kz<ij> {

        /* renamed from: com.flurry.sdk.ij.a.1 */
        class C01281 extends DataOutputStream {
            final /* synthetic */ C0581a f105a;

            C01281(C0581a c0581a, OutputStream outputStream) {
                this.f105a = c0581a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.ij.a.2 */
        class C01292 extends DataInputStream {
            final /* synthetic */ C0581a f106a;

            C01292(C0581a c0581a, InputStream inputStream) {
                this.f106a = c0581a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ void m479a(OutputStream outputStream, Object obj) throws IOException {
            ij ijVar = (ij) obj;
            if (outputStream != null && ijVar != null) {
                DataOutputStream c01281 = new C01281(this, outputStream);
                c01281.writeInt(ijVar.f108a);
                c01281.writeLong(ijVar.f109b);
                c01281.writeLong(ijVar.f110c);
                c01281.writeBoolean(ijVar.f111d);
                c01281.writeInt(ijVar.f112e);
                c01281.writeInt(ijVar.f113f.f125e);
                if (ijVar.f114g != null) {
                    c01281.writeUTF(ijVar.f114g);
                } else {
                    c01281.writeUTF(XmlPullParser.NO_NAMESPACE);
                }
                c01281.writeInt(ijVar.f115h);
                c01281.writeLong(ijVar.f116i);
                c01281.writeBoolean(ijVar.f117j);
                c01281.writeLong(ijVar.f118k);
                c01281.flush();
            }
        }

        public final /* synthetic */ Object m478a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01292 = new C01292(this, inputStream);
            int readInt = c01292.readInt();
            long readLong = c01292.readLong();
            long readLong2 = c01292.readLong();
            boolean readBoolean = c01292.readBoolean();
            int readInt2 = c01292.readInt();
            ik a = ik.m19a(c01292.readInt());
            String readUTF = c01292.readUTF();
            int readInt3 = c01292.readInt();
            long readLong3 = c01292.readLong();
            boolean readBoolean2 = c01292.readBoolean();
            long readLong4 = c01292.readLong();
            Object ijVar = new ij(null, readLong, readLong2, readInt);
            ijVar.f111d = readBoolean;
            ijVar.f112e = readInt2;
            ijVar.f113f = a;
            ijVar.f114g = readUTF;
            ijVar.f115h = readInt3;
            ijVar.f116i = readLong3;
            ijVar.f117j = readBoolean2;
            ijVar.f118k = readLong4;
            return ijVar;
        }
    }

    static {
        f107m = ij.class.getName();
    }

    public ij(ii iiVar, long j, long j2, int i) {
        this.f118k = 0;
        this.f119l = iiVar;
        this.f109b = j;
        this.f110c = j2;
        this.f108a = i;
        this.f112e = 0;
        this.f113f = ik.PENDING_COMPLETION;
    }

    public final void m18a() {
        this.f119l.f506a.add(this);
        if (this.f111d) {
            this.f119l.f517l = true;
        }
    }
}
