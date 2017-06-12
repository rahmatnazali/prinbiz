package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public final class ky<T> implements kz<List<T>> {
    kz<T> f667a;

    /* renamed from: com.flurry.sdk.ky.1 */
    class C01681 extends DataOutputStream {
        final /* synthetic */ ky f386a;

        C01681(ky kyVar, OutputStream outputStream) {
            this.f386a = kyVar;
            super(outputStream);
        }

        public final void close() {
        }
    }

    /* renamed from: com.flurry.sdk.ky.2 */
    class C01692 extends DataInputStream {
        final /* synthetic */ ky f387a;

        C01692(ky kyVar, InputStream inputStream) {
            this.f387a = kyVar;
            super(inputStream);
        }

        public final void close() {
        }
    }

    public final /* synthetic */ Object m639a(InputStream inputStream) throws IOException {
        return m642b(inputStream);
    }

    public ky(kz<T> kzVar) {
        this.f667a = kzVar;
    }

    public final void m641a(OutputStream outputStream, List<T> list) throws IOException {
        int i = 0;
        if (outputStream != null) {
            int size;
            DataOutputStream c01681 = new C01681(this, outputStream);
            if (list != null) {
                size = list.size();
            } else {
                size = 0;
            }
            c01681.writeInt(size);
            while (i < size) {
                this.f667a.m263a(outputStream, list.get(i));
                i++;
            }
            c01681.flush();
        }
    }

    public final List<T> m642b(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int readInt = new C01692(this, inputStream).readInt();
        List<T> arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            Object a = this.f667a.m262a(inputStream);
            if (a == null) {
                throw new IOException("Missing record.");
            }
            arrayList.add(a);
        }
        return arrayList;
    }
}
