package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ks {
    private static final String f376c;
    String f377a;
    byte[] f378b;

    /* renamed from: com.flurry.sdk.ks.a */
    public static class C0630a implements kz<ks> {

        /* renamed from: com.flurry.sdk.ks.a.1 */
        class C01641 extends DataOutputStream {
            final /* synthetic */ C0630a f374a;

            C01641(C0630a c0630a, OutputStream outputStream) {
                this.f374a = c0630a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.ks.a.2 */
        class C01652 extends DataInputStream {
            final /* synthetic */ C0630a f375a;

            C01652(C0630a c0630a, InputStream inputStream) {
                this.f375a = c0630a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ Object m624a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01652 = new C01652(this, inputStream);
            ks ksVar = new ks();
            short readShort = c01652.readShort();
            if (readShort == (short) 0) {
                return null;
            }
            ksVar.f378b = new byte[readShort];
            c01652.readFully(ksVar.f378b);
            c01652.readUnsignedShort();
            return ksVar;
        }

        public final /* synthetic */ void m625a(OutputStream outputStream, Object obj) throws IOException {
            ks ksVar = (ks) obj;
            if (outputStream != null && ksVar != null) {
                DataOutputStream c01641 = new C01641(this, outputStream);
                c01641.writeShort(ksVar.f378b.length);
                c01641.write(ksVar.f378b);
                c01641.writeShort(0);
                c01641.flush();
            }
        }
    }

    static {
        f376c = ks.class.getSimpleName();
    }

    private ks() {
        this.f377a = null;
        this.f378b = null;
    }

    public ks(byte[] bArr) {
        this.f377a = null;
        this.f378b = null;
        this.f377a = UUID.randomUUID().toString();
        this.f378b = bArr;
    }

    public static String m243a(String str) {
        return ".yflurrydatasenderblock." + str;
    }
}
