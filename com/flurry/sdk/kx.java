package com.flurry.sdk;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class kx<ObjectType> extends kw<ObjectType> {
    public kx(kz<ObjectType> kzVar) {
        super(kzVar);
    }

    public final void m703a(OutputStream outputStream, ObjectType objectType) throws IOException {
        Throwable th;
        if (outputStream != null) {
            Closeable gZIPOutputStream;
            try {
                gZIPOutputStream = new GZIPOutputStream(outputStream);
                try {
                    super.m638a(gZIPOutputStream, objectType);
                    lr.m311a(gZIPOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    lr.m311a(gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                gZIPOutputStream = null;
                lr.m311a(gZIPOutputStream);
                throw th;
            }
        }
    }

    public final ObjectType m702a(InputStream inputStream) throws IOException {
        Closeable gZIPInputStream;
        Throwable th;
        ObjectType objectType = null;
        if (inputStream != null) {
            try {
                gZIPInputStream = new GZIPInputStream(inputStream);
                try {
                    objectType = super.m637a(gZIPInputStream);
                    lr.m311a(gZIPInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    lr.m311a(gZIPInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                gZIPInputStream = null;
                th = th4;
                lr.m311a(gZIPInputStream);
                throw th;
            }
        }
        return objectType;
    }
}
