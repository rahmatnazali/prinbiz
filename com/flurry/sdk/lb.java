package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class lb<T> implements kz<T> {
    private final String f668a;
    private final int f669b;
    private final lc<T> f670c;

    /* renamed from: com.flurry.sdk.lb.1 */
    class C01701 extends DataOutputStream {
        final /* synthetic */ lb f388a;

        C01701(lb lbVar, OutputStream outputStream) {
            this.f388a = lbVar;
            super(outputStream);
        }

        public final void close() {
        }
    }

    /* renamed from: com.flurry.sdk.lb.2 */
    class C01712 extends DataInputStream {
        final /* synthetic */ lb f389a;

        C01712(lb lbVar, InputStream inputStream) {
            this.f389a = lbVar;
            super(inputStream);
        }

        public final void close() {
        }
    }

    public lb(String str, int i, lc<T> lcVar) {
        this.f668a = str;
        this.f669b = i;
        this.f670c = lcVar;
    }

    public final void m646a(OutputStream outputStream, T t) throws IOException {
        if (outputStream != null && this.f670c != null) {
            OutputStream c01701 = new C01701(this, outputStream);
            c01701.writeUTF(this.f668a);
            c01701.writeInt(this.f669b);
            this.f670c.m264a(this.f669b).m263a(c01701, t);
            c01701.flush();
        }
    }

    public final T m645a(InputStream inputStream) throws IOException {
        if (inputStream == null || this.f670c == null) {
            return null;
        }
        InputStream c01712 = new C01712(this, inputStream);
        String readUTF = c01712.readUTF();
        if (this.f668a.equals(readUTF)) {
            return this.f670c.m264a(c01712.readInt()).m262a(c01712);
        }
        throw new IOException("Signature: " + readUTF + " is invalid");
    }
}
