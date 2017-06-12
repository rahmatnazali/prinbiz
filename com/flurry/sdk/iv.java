package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class iv {
    public String f184a;
    public boolean f185b;
    public boolean f186c;
    public long f187d;
    private final Map<String, String> f188e;
    private int f189f;
    private long f190g;

    public iv(int i, String str, Map<String, String> map, long j, boolean z) {
        this.f188e = new HashMap();
        this.f189f = i;
        this.f184a = str;
        if (map != null) {
            this.f188e.putAll(map);
        }
        this.f190g = j;
        this.f185b = z;
        if (this.f185b) {
            this.f186c = false;
        } else {
            this.f186c = true;
        }
    }

    public final void m76a(long j) {
        this.f186c = true;
        this.f187d = j - this.f190g;
        kf.m182a(3, "FlurryAgent", "Ended event '" + this.f184a + "' (" + this.f190g + ") after " + this.f187d + "ms");
    }

    public final synchronized void m77a(Map<String, String> map) {
        if (map != null) {
            this.f188e.putAll(map);
        }
    }

    public final synchronized Map<String, String> m75a() {
        return new HashMap(this.f188e);
    }

    public final synchronized void m78b(Map<String, String> map) {
        this.f188e.clear();
        this.f188e.putAll(map);
    }

    public final synchronized byte[] m79b() {
        Closeable dataOutputStream;
        byte[] toByteArray;
        Closeable closeable;
        Throwable th;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.f189f);
                dataOutputStream.writeUTF(this.f184a);
                dataOutputStream.writeShort(this.f188e.size());
                for (Entry entry : this.f188e.entrySet()) {
                    dataOutputStream.writeUTF(lr.m316b((String) entry.getKey()));
                    dataOutputStream.writeUTF(lr.m316b((String) entry.getValue()));
                }
                dataOutputStream.writeLong(this.f190g);
                dataOutputStream.writeLong(this.f187d);
                dataOutputStream.flush();
                toByteArray = byteArrayOutputStream.toByteArray();
                lr.m311a(dataOutputStream);
            } catch (IOException e) {
                closeable = dataOutputStream;
                try {
                    toByteArray = new byte[0];
                    lr.m311a(closeable);
                    return toByteArray;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = closeable;
                    lr.m311a(dataOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                lr.m311a(dataOutputStream);
                throw th;
            }
        } catch (IOException e2) {
            closeable = null;
            toByteArray = new byte[0];
            lr.m311a(closeable);
            return toByteArray;
        } catch (Throwable th4) {
            dataOutputStream = null;
            th = th4;
            lr.m311a(dataOutputStream);
            throw th;
        }
        return toByteArray;
    }
}
