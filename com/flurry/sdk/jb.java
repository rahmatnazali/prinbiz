package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class jb {
    private static final String f226d;
    boolean f227a;
    long f228b;
    final List<iy> f229c;

    /* renamed from: com.flurry.sdk.jb.a */
    public static class C0600a implements kz<jb> {

        /* renamed from: com.flurry.sdk.jb.a.1 */
        class C01451 extends DataInputStream {
            final /* synthetic */ C0600a f225a;

            C01451(C0600a c0600a, InputStream inputStream) {
                this.f225a = c0600a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ Object m550a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01451 = new C01451(this, inputStream);
            jb jbVar = new jb();
            c01451.readUTF();
            c01451.readUTF();
            jbVar.f227a = c01451.readBoolean();
            jbVar.f228b = c01451.readLong();
            while (true) {
                int readUnsignedShort = c01451.readUnsignedShort();
                if (readUnsignedShort == 0) {
                    return jbVar;
                }
                byte[] bArr = new byte[readUnsignedShort];
                c01451.readFully(bArr);
                jbVar.f229c.add(0, new iy(bArr));
            }
        }

        public final /* synthetic */ void m551a(OutputStream outputStream, Object obj) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }
    }

    static {
        f226d = jb.class.getSimpleName();
    }

    public jb() {
        this.f229c = new ArrayList();
    }
}
