package com.flurry.sdk;

import com.flurry.sdk.ii.C0580a;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public final class im {
    private static final String f144e;
    public long f145a;
    int f146b;
    public String f147c;
    Map<Long, ii> f148d;
    private long f149f;
    private long f150g;
    private iq f151h;
    private boolean f152i;
    private int f153j;
    private AtomicInteger f154k;

    /* renamed from: com.flurry.sdk.im.a */
    public static class C0584a implements kz<im> {
        ky<ii> f522a;

        /* renamed from: com.flurry.sdk.im.a.1 */
        class C01351 extends DataOutputStream {
            final /* synthetic */ C0584a f142a;

            C01351(C0584a c0584a, OutputStream outputStream) {
                this.f142a = c0584a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.im.a.2 */
        class C01362 extends DataInputStream {
            final /* synthetic */ C0584a f143a;

            C01362(C0584a c0584a, InputStream inputStream) {
                this.f143a = c0584a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ Object m482a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01362 = new C01362(this, inputStream);
            long readLong = c01362.readLong();
            long readLong2 = c01362.readLong();
            long readLong3 = c01362.readLong();
            iq a = iq.m73a(c01362.readInt());
            boolean readBoolean = c01362.readBoolean();
            int readInt = c01362.readInt();
            String readUTF = c01362.readUTF();
            int readInt2 = c01362.readInt();
            int readInt3 = c01362.readInt();
            im imVar = new im(readUTF, readBoolean, readLong, readLong3, a, null);
            imVar.f149f = readLong2;
            imVar.f146b = readInt;
            imVar.f153j = readInt2;
            imVar.f154k = new AtomicInteger(readInt3);
            List<ii> b = this.f522a.m642b(inputStream);
            if (b != null) {
                imVar.f148d = new HashMap();
                for (ii iiVar : b) {
                    iiVar.f518m = imVar;
                    imVar.f148d.put(Long.valueOf(iiVar.f507b), iiVar);
                }
            }
            return imVar;
        }

        public final /* synthetic */ void m483a(OutputStream outputStream, Object obj) throws IOException {
            im imVar = (im) obj;
            if (outputStream != null && imVar != null) {
                DataOutputStream c01351 = new C01351(this, outputStream);
                c01351.writeLong(imVar.f145a);
                c01351.writeLong(imVar.f149f);
                c01351.writeLong(imVar.f150g);
                c01351.writeInt(imVar.f151h.f175e);
                c01351.writeBoolean(imVar.f152i);
                c01351.writeInt(imVar.f146b);
                if (imVar.f147c != null) {
                    c01351.writeUTF(imVar.f147c);
                } else {
                    c01351.writeUTF(XmlPullParser.NO_NAMESPACE);
                }
                c01351.writeInt(imVar.f153j);
                c01351.writeInt(imVar.f154k.intValue());
                c01351.flush();
                this.f522a.m641a(outputStream, imVar.m60a());
            }
        }

        public C0584a() {
            this.f522a = new ky(new C0580a());
        }
    }

    static {
        f144e = in.class.getName();
    }

    public im(String str, boolean z, long j, long j2, iq iqVar, Map<Long, ii> map) {
        this.f147c = str;
        this.f152i = z;
        this.f145a = j;
        this.f150g = j2;
        this.f151h = iqVar;
        this.f149f = System.currentTimeMillis();
        this.f148d = map;
        if (map != null) {
            for (Object obj : map.keySet()) {
                ((ii) map.get(obj)).f518m = this;
            }
            this.f153j = map.size();
        } else {
            this.f153j = 0;
        }
        this.f154k = new AtomicInteger(0);
    }

    public final List<ii> m60a() {
        if (this.f148d != null) {
            return new ArrayList(this.f148d.values());
        }
        return Collections.emptyList();
    }

    public final synchronized boolean m61b() {
        return this.f154k.intValue() >= this.f153j;
    }

    public final synchronized void m62c() {
        this.f154k.incrementAndGet();
    }

    public final byte[] m63d() throws IOException {
        Throwable e;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.f151h.f175e);
                dataOutputStream.writeLong(this.f145a);
                dataOutputStream.writeLong(this.f150g);
                dataOutputStream.writeBoolean(this.f152i);
                if (this.f152i) {
                    dataOutputStream.writeShort(this.f146b);
                    dataOutputStream.writeUTF(this.f147c);
                }
                dataOutputStream.writeShort(this.f148d.size());
                if (this.f148d != null) {
                    for (Entry entry : this.f148d.entrySet()) {
                        ii iiVar = (ii) entry.getValue();
                        dataOutputStream.writeLong(((Long) entry.getKey()).longValue());
                        dataOutputStream.writeUTF(iiVar.f357r);
                        dataOutputStream.writeShort(iiVar.f506a.size());
                        Iterator it = iiVar.f506a.iterator();
                        while (it.hasNext()) {
                            ij ijVar = (ij) it.next();
                            dataOutputStream.writeShort(ijVar.f108a);
                            dataOutputStream.writeLong(ijVar.f109b);
                            dataOutputStream.writeLong(ijVar.f110c);
                            dataOutputStream.writeBoolean(ijVar.f111d);
                            dataOutputStream.writeShort(ijVar.f112e);
                            dataOutputStream.writeShort(ijVar.f113f.f125e);
                            if ((ijVar.f112e < NNTPReply.SERVER_READY_POSTING_ALLOWED || ijVar.f112e >= NNTPReply.SERVICE_DISCONTINUED) && ijVar.f114g != null) {
                                byte[] bytes = ijVar.f114g.getBytes();
                                dataOutputStream.writeShort(bytes.length);
                                dataOutputStream.write(bytes);
                            }
                            dataOutputStream.writeShort(ijVar.f115h);
                            dataOutputStream.writeInt((int) ijVar.f118k);
                        }
                    }
                }
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                lr.m311a(dataOutputStream);
                return toByteArray;
            } catch (IOException e2) {
                e = e2;
                closeable = dataOutputStream;
                try {
                    kf.m183a(6, f144e, "Error when generating report", e);
                    throw e;
                } catch (Throwable th) {
                    e = th;
                    dataOutputStream = closeable;
                    lr.m311a(dataOutputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                lr.m311a(dataOutputStream);
                throw e;
            }
        } catch (IOException e3) {
            e = e3;
            kf.m183a(6, f144e, "Error when generating report", e);
            throw e;
        } catch (Throwable th3) {
            e = th3;
            dataOutputStream = null;
            lr.m311a(dataOutputStream);
            throw e;
        }
    }
}
