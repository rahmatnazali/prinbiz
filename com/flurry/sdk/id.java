package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class id {
    long f91a;
    boolean f92b;
    byte[] f93c;

    /* renamed from: com.flurry.sdk.id.a */
    public static class C0566a implements kz<id> {

        /* renamed from: com.flurry.sdk.id.a.1 */
        class C01241 extends DataOutputStream {
            final /* synthetic */ C0566a f89a;

            C01241(C0566a c0566a, OutputStream outputStream) {
                this.f89a = c0566a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.id.a.2 */
        class C01252 extends DataInputStream {
            final /* synthetic */ C0566a f90a;

            C01252(C0566a c0566a, InputStream inputStream) {
                this.f90a = c0566a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ void m398a(OutputStream outputStream, Object obj) throws IOException {
            id idVar = (id) obj;
            if (outputStream != null && idVar != null) {
                DataOutputStream c01241 = new C01241(this, outputStream);
                c01241.writeLong(idVar.f91a);
                c01241.writeBoolean(idVar.f92b);
                c01241.writeInt(idVar.f93c.length);
                c01241.write(idVar.f93c);
                c01241.flush();
            }
        }

        public final /* synthetic */ Object m397a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01252 = new C01252(this, inputStream);
            Object idVar = new id();
            idVar.f91a = c01252.readLong();
            idVar.f92b = c01252.readBoolean();
            idVar.f93c = new byte[c01252.readInt()];
            c01252.readFully(idVar.f93c);
            return idVar;
        }
    }
}
