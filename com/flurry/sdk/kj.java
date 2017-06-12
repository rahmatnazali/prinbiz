package com.flurry.sdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.telnet.TelnetCommand;

public class kj<ObjectType> {
    private static final String f334a;
    private static final byte[] f335b;
    private String f336c;
    private kz<ObjectType> f337d;

    static {
        f334a = kj.class.getSimpleName();
        f335b = new byte[]{(byte) 113, (byte) -92, (byte) -8, (byte) 125, (byte) 121, (byte) 107, (byte) -65, (byte) -61, (byte) -74, (byte) -114, (byte) -32, (byte) 0, (byte) -57, (byte) -87, (byte) -35, (byte) -56, (byte) -6, (byte) -52, (byte) 51, (byte) 126, (byte) -104, (byte) 49, (byte) 79, (byte) -52, (byte) 118, (byte) -84, (byte) 99, (byte) -52, (byte) -14, (byte) -126, (byte) -27, (byte) -64};
    }

    private static void m205c(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int length2 = f335b.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) ((bArr[i] ^ f335b[i % length2]) ^ ((i * 31) % TelnetCommand.WILL));
            }
        }
    }

    private static void m206d(byte[] bArr) {
        m205c(bArr);
    }

    public static int m204a(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        jx jxVar = new jx();
        jxVar.update(bArr);
        return jxVar.m150b();
    }

    public kj(String str, kz<ObjectType> kzVar) {
        this.f336c = str;
        this.f337d = kzVar;
    }

    public final byte[] m207a(ObjectType objectType) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f337d.m263a(byteArrayOutputStream, objectType);
        Object toByteArray = byteArrayOutputStream.toByteArray();
        kf.m182a(3, f334a, "Encoding " + this.f336c + ": " + new String(toByteArray));
        kz kxVar = new kx(new kv());
        OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        kxVar.m263a(byteArrayOutputStream2, toByteArray);
        byte[] toByteArray2 = byteArrayOutputStream2.toByteArray();
        m205c(toByteArray2);
        return toByteArray2;
    }

    public final ObjectType m208b(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new IOException("Decoding: " + this.f336c + ": Nothing to decode");
        }
        m206d(bArr);
        byte[] bArr2 = (byte[]) new kx(new kv()).m262a(new ByteArrayInputStream(bArr));
        kf.m182a(3, f334a, "Decoding: " + this.f336c + ": " + new String(bArr2));
        return this.f337d.m262a(new ByteArrayInputStream(bArr2));
    }
}
