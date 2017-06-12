package com.flurry.sdk;

import com.flurry.sdk.jb.C0600a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public final class hn {
    private static final String f28a;

    static {
        f28a = hn.class.getSimpleName();
    }

    public static jb m17a(File file) {
        Closeable fileInputStream;
        Throwable e;
        if (file == null || !file.exists()) {
            return null;
        }
        kz c0600a = new C0600a();
        Closeable dataInputStream;
        jb jbVar;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
            } catch (Exception e2) {
                e = e2;
                dataInputStream = null;
                try {
                    kf.m183a(3, f28a, "Error loading legacy agent data.", e);
                    lr.m311a(dataInputStream);
                    lr.m311a(fileInputStream);
                    jbVar = null;
                    return jbVar;
                } catch (Throwable th) {
                    e = th;
                    lr.m311a(dataInputStream);
                    lr.m311a(fileInputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                dataInputStream = null;
                lr.m311a(dataInputStream);
                lr.m311a(fileInputStream);
                throw e;
            }
            try {
                if (dataInputStream.readUnsignedShort() != 46586) {
                    kf.m182a(3, f28a, "Unexpected file type");
                    lr.m311a(dataInputStream);
                    lr.m311a(fileInputStream);
                    return null;
                }
                int readUnsignedShort = dataInputStream.readUnsignedShort();
                if (readUnsignedShort != 2) {
                    kf.m182a(6, f28a, "Unknown agent file version: " + readUnsignedShort);
                    lr.m311a(dataInputStream);
                    lr.m311a(fileInputStream);
                    return null;
                }
                jbVar = (jb) c0600a.m262a(dataInputStream);
                lr.m311a(dataInputStream);
                lr.m311a(fileInputStream);
                return jbVar;
            } catch (Exception e3) {
                e = e3;
                kf.m183a(3, f28a, "Error loading legacy agent data.", e);
                lr.m311a(dataInputStream);
                lr.m311a(fileInputStream);
                jbVar = null;
                return jbVar;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            fileInputStream = null;
            kf.m183a(3, f28a, "Error loading legacy agent data.", e);
            lr.m311a(dataInputStream);
            lr.m311a(fileInputStream);
            jbVar = null;
            return jbVar;
        } catch (Throwable th3) {
            e = th3;
            dataInputStream = null;
            fileInputStream = null;
            lr.m311a(dataInputStream);
            lr.m311a(fileInputStream);
            throw e;
        }
    }
}
