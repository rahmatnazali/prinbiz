package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ku {
    String f385a;

    /* renamed from: com.flurry.sdk.ku.a */
    public static class C0638a implements kz<ku> {

        /* renamed from: com.flurry.sdk.ku.a.1 */
        class C01661 extends DataOutputStream {
            final /* synthetic */ C0638a f383a;

            C01661(C0638a c0638a, OutputStream outputStream) {
                this.f383a = c0638a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.ku.a.2 */
        class C01672 extends DataInputStream {
            final /* synthetic */ C0638a f384a;

            C01672(C0638a c0638a, InputStream inputStream) {
                this.f384a = c0638a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ void m634a(OutputStream outputStream, Object obj) throws IOException {
            ku kuVar = (ku) obj;
            if (outputStream != null && kuVar != null) {
                DataOutputStream c01661 = new C01661(this, outputStream);
                c01661.writeUTF(kuVar.f385a);
                c01661.flush();
            }
        }

        public final /* synthetic */ Object m633a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01672 = new C01672(this, inputStream);
            Object kuVar = new ku();
            kuVar.f385a = c01672.readUTF();
            return kuVar;
        }
    }

    private ku() {
    }

    public ku(String str) {
        this.f385a = str;
    }
}
