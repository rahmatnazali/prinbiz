package com.flurry.sdk;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;

public class iy {
    private static final String f197b;
    byte[] f198a;

    /* renamed from: com.flurry.sdk.iy.a */
    public static class C0590a implements kz<iy> {

        /* renamed from: com.flurry.sdk.iy.a.1 */
        class C01411 extends DataOutputStream {
            final /* synthetic */ C0590a f195a;

            C01411(C0590a c0590a, OutputStream outputStream) {
                this.f195a = c0590a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.iy.a.2 */
        class C01422 extends DataInputStream {
            final /* synthetic */ C0590a f196a;

            C01422(C0590a c0590a, InputStream inputStream) {
                this.f196a = c0590a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ void m508a(OutputStream outputStream, Object obj) throws IOException {
            iy iyVar = (iy) obj;
            if (outputStream != null && iyVar != null) {
                DataOutputStream c01411 = new C01411(this, outputStream);
                int i = 0;
                if (iyVar.f198a != null) {
                    i = iyVar.f198a.length;
                }
                c01411.writeShort(i);
                if (i > 0) {
                    c01411.write(iyVar.f198a);
                }
                c01411.flush();
            }
        }

        public final /* synthetic */ Object m507a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01422 = new C01422(this, inputStream);
            Object iyVar = new iy();
            int readUnsignedShort = c01422.readUnsignedShort();
            if (readUnsignedShort <= 0) {
                return iyVar;
            }
            byte[] bArr = new byte[readUnsignedShort];
            c01422.readFully(bArr);
            iyVar.f198a = bArr;
            return iyVar;
        }
    }

    static {
        f197b = iy.class.getSimpleName();
    }

    private iy() {
    }

    public iy(byte[] bArr) {
        this.f198a = bArr;
    }

    public iy(iz izVar) throws IOException {
        Throwable e;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                int i;
                int i2;
                dataOutputStream.writeShort(7);
                dataOutputStream.writeUTF(izVar.f199a);
                dataOutputStream.writeLong(izVar.f200b);
                dataOutputStream.writeLong(izVar.f201c);
                dataOutputStream.writeLong(izVar.f202d);
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeByte(-1);
                if (TextUtils.isEmpty(izVar.f204f)) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeUTF(izVar.f204f);
                }
                if (TextUtils.isEmpty(izVar.f205g)) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeUTF(izVar.f205g);
                }
                Map map = izVar.f206h;
                if (map == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(map.size());
                    for (Entry entry : map.entrySet()) {
                        dataOutputStream.writeUTF((String) entry.getKey());
                        dataOutputStream.writeUTF((String) entry.getValue());
                    }
                }
                map = izVar.f203e;
                if (map == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(map.size());
                    for (Entry entry2 : map.entrySet()) {
                        dataOutputStream.writeUTF((String) entry2.getKey());
                        dataOutputStream.writeUTF((String) entry2.getValue());
                        dataOutputStream.writeByte(0);
                    }
                }
                dataOutputStream.writeUTF(izVar.f207i);
                dataOutputStream.writeUTF(izVar.f208j);
                dataOutputStream.writeByte(izVar.f209k);
                dataOutputStream.writeByte(izVar.f210l);
                dataOutputStream.writeUTF(izVar.f211m);
                if (izVar.f212n == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeDouble(lr.m306a(izVar.f212n.getLatitude()));
                    dataOutputStream.writeDouble(lr.m306a(izVar.f212n.getLongitude()));
                    dataOutputStream.writeFloat(izVar.f212n.getAccuracy());
                }
                dataOutputStream.writeInt(izVar.f213o);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(izVar.f214p);
                if (izVar.f215q == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeLong(izVar.f215q.longValue());
                }
                map = izVar.f216r;
                if (map == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(map.size());
                    for (Entry entry22 : map.entrySet()) {
                        dataOutputStream.writeUTF((String) entry22.getKey());
                        dataOutputStream.writeInt(((iu) entry22.getValue()).f183a);
                    }
                }
                List<iv> list = izVar.f217s;
                if (list == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(list.size());
                    for (iv b : list) {
                        dataOutputStream.write(b.m79b());
                    }
                }
                dataOutputStream.writeBoolean(izVar.f218t);
                List list2 = izVar.f220v;
                if (list2 != null) {
                    i = 0;
                    int i3 = 0;
                    for (i2 = 0; i2 < list2.size(); i2++) {
                        i3 += ((it) list2.get(i2)).m74a().length;
                        if (i3 > 160000) {
                            kf.m182a(5, f197b, "Error Log size exceeded. No more event details logged.");
                            i2 = i;
                            break;
                        }
                        i++;
                    }
                    i2 = i;
                } else {
                    i2 = 0;
                }
                dataOutputStream.writeInt(izVar.f219u);
                dataOutputStream.writeShort(i2);
                for (i = 0; i < i2; i++) {
                    dataOutputStream.write(((it) list2.get(i)).m74a());
                }
                dataOutputStream.writeInt(-1);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                this.f198a = byteArrayOutputStream.toByteArray();
                lr.m311a(dataOutputStream);
            } catch (IOException e2) {
                e = e2;
                closeable = dataOutputStream;
                try {
                    kf.m183a(6, f197b, XmlPullParser.NO_NAMESPACE, e);
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
            kf.m183a(6, f197b, XmlPullParser.NO_NAMESPACE, e);
            throw e;
        } catch (Throwable th3) {
            e = th3;
            dataOutputStream = null;
            lr.m311a(dataOutputStream);
            throw e;
        }
    }
}
