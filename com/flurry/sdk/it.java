package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.xmlpull.v1.XmlPullParser;

public final class it {
    public String f177a;
    private int f178b;
    private long f179c;
    private String f180d;
    private String f181e;
    private Throwable f182f;

    public it(int i, long j, String str, String str2, String str3, Throwable th) {
        this.f178b = i;
        this.f179c = j;
        this.f177a = str;
        this.f180d = str2;
        this.f181e = str3;
        this.f182f = th;
    }

    public final byte[] m74a() {
        byte[] bytes;
        Throwable th;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.f178b);
                dataOutputStream.writeLong(this.f179c);
                dataOutputStream.writeUTF(this.f177a);
                dataOutputStream.writeUTF(this.f180d);
                dataOutputStream.writeUTF(this.f181e);
                if (this.f182f != null) {
                    if ("uncaught".equals(this.f177a)) {
                        dataOutputStream.writeByte(3);
                    } else {
                        dataOutputStream.writeByte(2);
                    }
                    dataOutputStream.writeByte(2);
                    StringBuilder stringBuilder = new StringBuilder(XmlPullParser.NO_NAMESPACE);
                    String property = System.getProperty("line.separator");
                    for (Object append : this.f182f.getStackTrace()) {
                        stringBuilder.append(append);
                        stringBuilder.append(property);
                    }
                    if (this.f182f.getCause() != null) {
                        stringBuilder.append(property);
                        stringBuilder.append("Caused by: ");
                        for (Object append2 : this.f182f.getCause().getStackTrace()) {
                            stringBuilder.append(append2);
                            stringBuilder.append(property);
                        }
                    }
                    bytes = stringBuilder.toString().getBytes();
                    dataOutputStream.writeInt(bytes.length);
                    dataOutputStream.write(bytes);
                } else {
                    dataOutputStream.writeByte(1);
                    dataOutputStream.writeByte(0);
                }
                dataOutputStream.flush();
                bytes = byteArrayOutputStream.toByteArray();
                lr.m311a(dataOutputStream);
            } catch (IOException e) {
                try {
                    bytes = new byte[0];
                    lr.m311a(dataOutputStream);
                    return bytes;
                } catch (Throwable th2) {
                    th = th2;
                    lr.m311a(dataOutputStream);
                    throw th;
                }
            }
        } catch (IOException e2) {
            dataOutputStream = null;
            bytes = new byte[0];
            lr.m311a(dataOutputStream);
            return bytes;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            dataOutputStream = null;
            th = th4;
            lr.m311a(dataOutputStream);
            throw th;
        }
        return bytes;
    }
}
