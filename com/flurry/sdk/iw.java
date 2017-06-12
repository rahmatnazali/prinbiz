package com.flurry.sdk;

import android.os.Build;
import android.os.Build.VERSION;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;

public class iw {
    private static final String f191b;
    public byte[] f192a;

    static {
        f191b = iw.class.getSimpleName();
    }

    public iw(String str, String str2, boolean z, boolean z2, long j, long j2, List<iy> list, Map<jm, byte[]> map, Map<String, List<String>> map2, Map<String, List<String>> map3, Map<String, Map<String, String>> map4, long j3) throws IOException {
        Closeable dataOutputStream;
        byte[] bArr;
        Throwable th;
        this.f192a = null;
        Closeable closeable = null;
        try {
            MessageDigest jxVar = new jx();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, jxVar);
            dataOutputStream = new DataOutputStream(digestOutputStream);
            try {
                String str3;
                int size;
                dataOutputStream.writeShort(30);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeLong(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(3);
                dataOutputStream.writeShort(js.m128a());
                dataOutputStream.writeLong(j3);
                dataOutputStream.writeUTF(str);
                dataOutputStream.writeUTF(str2);
                dataOutputStream.writeShort(map.size());
                for (Entry entry : map.entrySet()) {
                    dataOutputStream.writeShort(((jm) entry.getKey()).f265c);
                    bArr = (byte[]) entry.getValue();
                    dataOutputStream.writeShort(bArr.length);
                    dataOutputStream.write(bArr);
                }
                dataOutputStream.writeByte(0);
                dataOutputStream.writeBoolean(z);
                dataOutputStream.writeBoolean(z2);
                dataOutputStream.writeLong(j);
                dataOutputStream.writeLong(j2);
                dataOutputStream.writeShort(6);
                dataOutputStream.writeUTF("device.model");
                dataOutputStream.writeUTF(Build.MODEL);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.brand");
                dataOutputStream.writeUTF(Build.BRAND);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.id");
                dataOutputStream.writeUTF(Build.ID);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("version.release");
                dataOutputStream.writeUTF(VERSION.RELEASE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.device");
                dataOutputStream.writeUTF(Build.DEVICE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.product");
                dataOutputStream.writeUTF(Build.PRODUCT);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeShort(map2 != null ? map2.keySet().size() : 0);
                if (map2 != null) {
                    kf.m182a(3, f191b, "sending referrer values because it exists");
                    for (Entry entry2 : map2.entrySet()) {
                        kf.m182a(3, f191b, "Referrer Entry:  " + ((String) entry2.getKey()) + "=" + entry2.getValue());
                        dataOutputStream.writeUTF((String) entry2.getKey());
                        kf.m182a(3, f191b, "referrer key is :" + ((String) entry2.getKey()));
                        dataOutputStream.writeShort(((List) entry2.getValue()).size());
                        for (String str32 : (List) entry2.getValue()) {
                            dataOutputStream.writeUTF(str32);
                            kf.m182a(3, f191b, "referrer value is :" + str32);
                        }
                    }
                }
                dataOutputStream.writeBoolean(false);
                if (map3 != null) {
                    size = map3.keySet().size();
                } else {
                    size = 0;
                }
                kf.m182a(3, f191b, "optionsMapSize is:  " + size);
                dataOutputStream.writeShort(size);
                if (map3 != null) {
                    kf.m182a(3, f191b, "sending launch options");
                    for (Entry entry22 : map3.entrySet()) {
                        kf.m182a(3, f191b, "Launch Options Key:  " + ((String) entry22.getKey()));
                        dataOutputStream.writeUTF((String) entry22.getKey());
                        dataOutputStream.writeShort(((List) entry22.getValue()).size());
                        for (String str322 : (List) entry22.getValue()) {
                            dataOutputStream.writeUTF(str322);
                            kf.m182a(3, f191b, "Launch Options value is :" + str322);
                        }
                    }
                }
                int size2 = map4 != null ? map4.keySet().size() : 0;
                kf.m182a(3, f191b, "numOriginAttributions is:  " + size);
                dataOutputStream.writeShort(size2);
                if (map4 != null) {
                    for (Entry entry3 : map4.entrySet()) {
                        kf.m182a(3, f191b, "Origin Atttribute Key:  " + ((String) entry3.getKey()));
                        dataOutputStream.writeUTF((String) entry3.getKey());
                        dataOutputStream.writeShort(((Map) entry3.getValue()).size());
                        kf.m182a(3, f191b, "Origin Attribute Map Size for " + ((String) entry3.getKey()) + ":  " + ((Map) entry3.getValue()).size());
                        for (Entry entry4 : ((Map) entry3.getValue()).entrySet()) {
                            kf.m182a(3, f191b, "Origin Atttribute for " + ((String) entry3.getKey()) + ":  " + ((String) entry4.getKey()) + ":" + ((String) entry4.getValue()));
                            dataOutputStream.writeUTF(entry4.getKey() != null ? (String) entry4.getKey() : XmlPullParser.NO_NAMESPACE);
                            if (entry4.getValue() != null) {
                                str322 = (String) entry4.getValue();
                            } else {
                                str322 = XmlPullParser.NO_NAMESPACE;
                            }
                            dataOutputStream.writeUTF(str322);
                        }
                    }
                }
                dataOutputStream.writeUTF(lo.m288a(jr.m120a().f284a));
                size = list.size();
                dataOutputStream.writeShort(size);
                for (int i = 0; i < size; i++) {
                    dataOutputStream.write(((iy) list.get(i)).f198a);
                }
                dataOutputStream.writeShort(0);
                digestOutputStream.on(false);
                dataOutputStream.write(jxVar.m149a());
                dataOutputStream.close();
                bArr = byteArrayOutputStream.toByteArray();
                lr.m311a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                lr.m311a(dataOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            lr.m311a(dataOutputStream);
            throw th;
        }
        this.f192a = bArr;
    }
}
