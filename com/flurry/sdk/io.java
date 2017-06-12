package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class io {
    byte[] f163a;

    /* renamed from: com.flurry.sdk.io.a */
    public static class C0587a implements kz<io> {

        /* renamed from: com.flurry.sdk.io.a.1 */
        class C01371 extends DataOutputStream {
            final /* synthetic */ C0587a f161a;

            C01371(C0587a c0587a, OutputStream outputStream) {
                this.f161a = c0587a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.io.a.2 */
        class C01382 extends DataInputStream {
            final /* synthetic */ C0587a f162a;

            C01382(C0587a c0587a, InputStream inputStream) {
                this.f162a = c0587a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ Object m486a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01382 = new C01382(this, inputStream);
            short readShort = c01382.readShort();
            if (readShort == (short) 0) {
                return null;
            }
            Object ioVar = new io();
            ioVar.f163a = new byte[readShort];
            c01382.readFully(ioVar.f163a);
            c01382.readUnsignedShort();
            return ioVar;
        }

        public final /* synthetic */ void m487a(OutputStream outputStream, Object obj) throws IOException {
            io ioVar = (io) obj;
            if (outputStream != null && ioVar != null) {
                DataOutputStream c01371 = new C01371(this, outputStream);
                c01371.writeShort(ioVar.f163a.length);
                c01371.write(ioVar.f163a);
                c01371.writeShort(0);
                c01371.flush();
            }
        }
    }

    public io(byte[] bArr) {
        this.f163a = bArr;
    }
}
