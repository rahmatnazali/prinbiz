package com.flurry.sdk;

import android.os.Build;
import android.os.Build.VERSION;
import com.flurry.sdk.io.C0587a;
import com.flurry.sdk.kl.C0157a;
import com.flurry.sdk.kn.C0161a;
import com.hiti.printerprotocol.RequestState;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.CRC32;
import org.apache.commons.net.nntp.NNTPReply;

public class in {
    public static final String f155b;
    private static in f156c;
    public String f157a;
    private jy<List<io>> f158d;
    private List<io> f159e;
    private boolean f160f;

    /* renamed from: com.flurry.sdk.in.1 */
    class C05851 implements lc<List<io>> {
        final /* synthetic */ in f523a;

        C05851(in inVar) {
            this.f523a = inVar;
        }

        public final kz<List<io>> m484a(int i) {
            return new ky(new C0587a());
        }
    }

    /* renamed from: com.flurry.sdk.in.2 */
    class C05862 implements C0157a<byte[], Void> {
        final /* synthetic */ in f524a;

        C05862(in inVar) {
            this.f524a = inVar;
        }

        public final /* synthetic */ void m485a(kl klVar, Object obj) {
            int i = klVar.f729p;
            if (i <= 0) {
                kf.m196e(in.f155b, "Server Error: " + i);
            } else if (i < NNTPReply.SERVER_READY_POSTING_ALLOWED || i >= RequestState.REQUEST_CHECK_PRINT_COMPLETE) {
                kf.m182a(3, in.f155b, "Pulse logging report sent unsuccessfully, HTTP response:" + i);
            } else {
                kf.m182a(3, in.f155b, "Pulse logging report sent successfully HTTP response:" + i);
                this.f524a.f159e.clear();
                this.f524a.f158d.m152a(this.f524a.f159e);
            }
        }
    }

    static {
        f155b = in.class.getName();
        f156c = null;
    }

    private in() {
    }

    public static synchronized in m64a() {
        in inVar;
        synchronized (in.class) {
            if (f156c == null) {
                in inVar2 = new in();
                f156c = inVar2;
                inVar2.f158d = new jy(jr.m120a().f284a.getFileStreamPath(".yflurrypulselogging." + Long.toString(lr.m325i(jr.m120a().f287d), 16)), ".yflurrypulselogging.", 1, new C05851(inVar2));
                inVar2.f160f = ((Boolean) li.m668a().m272a("UseHttps")).booleanValue();
                kf.m182a(4, f155b, "initSettings, UseHttps = " + inVar2.f160f);
                inVar2.f159e = (List) inVar2.f158d.m151a();
                if (inVar2.f159e == null) {
                    inVar2.f159e = new ArrayList();
                }
            }
            inVar = f156c;
        }
        return inVar;
    }

    public final synchronized void m70a(im imVar) {
        try {
            this.f159e.add(new io(imVar.m63d()));
            kf.m182a(4, f155b, "Saving persistent Pulse logging data.");
            this.f158d.m152a(this.f159e);
        } catch (IOException e) {
            kf.m182a(6, f155b, "Error when generating pulse log report in addReport part");
        }
    }

    private byte[] m69d() throws IOException {
        Throwable e;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                byte[] toByteArray;
                if (this.f159e == null || this.f159e.isEmpty()) {
                    toByteArray = byteArrayOutputStream.toByteArray();
                    lr.m311a(dataOutputStream);
                } else {
                    dataOutputStream.writeShort(1);
                    dataOutputStream.writeShort(1);
                    dataOutputStream.writeLong(System.currentTimeMillis());
                    dataOutputStream.writeUTF(jr.m120a().f287d);
                    dataOutputStream.writeUTF(jn.m581a().m592i());
                    dataOutputStream.writeShort(js.m128a());
                    dataOutputStream.writeShort(3);
                    jn.m581a();
                    dataOutputStream.writeUTF(jn.m585d());
                    dataOutputStream.writeBoolean(je.m85a().m95d());
                    List<ht> arrayList = new ArrayList();
                    for (Entry entry : Collections.unmodifiableMap(je.m85a().f242a).entrySet()) {
                        ht htVar = new ht();
                        htVar.f67a = ((jm) entry.getKey()).f265c;
                        if (((jm) entry.getKey()).f266d) {
                            htVar.f68b = new String((byte[]) entry.getValue());
                        } else {
                            htVar.f68b = lr.m317b((byte[]) entry.getValue());
                        }
                        arrayList.add(htVar);
                    }
                    dataOutputStream.writeShort(arrayList.size());
                    for (ht htVar2 : arrayList) {
                        dataOutputStream.writeShort(htVar2.f67a);
                        toByteArray = htVar2.f68b.getBytes();
                        dataOutputStream.writeShort(toByteArray.length);
                        dataOutputStream.write(toByteArray);
                    }
                    dataOutputStream.writeShort(6);
                    dataOutputStream.writeShort(ig.MODEL.f102h);
                    dataOutputStream.writeUTF(Build.MODEL);
                    dataOutputStream.writeShort(ig.BRAND.f102h);
                    dataOutputStream.writeUTF(Build.BOARD);
                    dataOutputStream.writeShort(ig.ID.f102h);
                    dataOutputStream.writeUTF(Build.ID);
                    dataOutputStream.writeShort(ig.DEVICE.f102h);
                    dataOutputStream.writeUTF(Build.DEVICE);
                    dataOutputStream.writeShort(ig.PRODUCT.f102h);
                    dataOutputStream.writeUTF(Build.PRODUCT);
                    dataOutputStream.writeShort(ig.VERSION_RELEASE.f102h);
                    dataOutputStream.writeUTF(VERSION.RELEASE);
                    dataOutputStream.writeShort(this.f159e.size());
                    for (io ioVar : this.f159e) {
                        dataOutputStream.write(ioVar.f163a);
                    }
                    toByteArray = byteArrayOutputStream.toByteArray();
                    CRC32 crc32 = new CRC32();
                    crc32.update(toByteArray);
                    dataOutputStream.writeInt((int) crc32.getValue());
                    toByteArray = byteArrayOutputStream.toByteArray();
                    lr.m311a(dataOutputStream);
                }
                return toByteArray;
            } catch (IOException e2) {
                e = e2;
                closeable = dataOutputStream;
            } catch (Throwable th) {
                e = th;
            }
        } catch (IOException e3) {
            e = e3;
            try {
                kf.m183a(6, f155b, "Error when generating report", e);
                throw e;
            } catch (Throwable th2) {
                e = th2;
                dataOutputStream = closeable;
                lr.m311a(dataOutputStream);
                throw e;
            }
        } catch (Throwable th3) {
            e = th3;
            dataOutputStream = null;
            lr.m311a(dataOutputStream);
            throw e;
        }
    }

    private synchronized void m66a(byte[] bArr) {
        if (jk.m98a().f258b) {
            if (bArr != null) {
                if (bArr.length != 0) {
                    String str;
                    if (this.f157a != null) {
                        str = this.f157a;
                    } else {
                        str = "https://data.flurry.com/pcr.do";
                    }
                    kf.m182a(4, f155b, "PulseLoggingManager: start upload data " + Arrays.toString(bArr) + " to " + str);
                    lx klVar = new kl();
                    klVar.f719f = str;
                    klVar.f710w = 100000;
                    klVar.f720g = C0161a.kPost;
                    klVar.f723j = true;
                    klVar.m694a("Content-Type", "application/octet-stream");
                    klVar.f739c = new kv();
                    klVar.f738b = bArr;
                    klVar.f737a = new C05862(this);
                    jp.m593a().m177a((Object) this, klVar);
                }
            }
            kf.m182a(3, f155b, "No report need be sent");
        } else {
            kf.m182a(5, f155b, "Reports were not sent! No Internet connection!");
        }
    }

    public final synchronized void m71b() {
        try {
            m66a(m69d());
        } catch (IOException e) {
            kf.m182a(6, f155b, "Report not send due to exception in generate data");
        }
    }
}
