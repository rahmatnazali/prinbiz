package com.flurry.sdk;

import com.flurry.sdk.ij.C0581a;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jmdns.impl.constants.DNSConstants;
import org.xmlpull.v1.XmlPullParser;

public class ii extends kp {
    private static final String f505t;
    public ArrayList<ij> f506a;
    final long f507b;
    final int f508c;
    final int f509d;
    final ip f510e;
    final Map<String, String> f511f;
    long f512g;
    int f513h;
    int f514i;
    String f515j;
    String f516k;
    boolean f517l;
    public im f518m;
    private final int f519u;
    private final int f520v;

    /* renamed from: com.flurry.sdk.ii.a */
    public static class C0580a implements kz<ii> {
        ky<ij> f504a;

        /* renamed from: com.flurry.sdk.ii.a.1 */
        class C01261 extends DataOutputStream {
            final /* synthetic */ C0580a f103a;

            C01261(C0580a c0580a, OutputStream outputStream) {
                this.f103a = c0580a;
                super(outputStream);
            }

            public final void close() {
            }
        }

        /* renamed from: com.flurry.sdk.ii.a.2 */
        class C01272 extends DataInputStream {
            final /* synthetic */ C0580a f104a;

            C01272(C0580a c0580a, InputStream inputStream) {
                this.f104a = c0580a;
                super(inputStream);
            }

            public final void close() {
            }
        }

        public final /* synthetic */ void m462a(OutputStream outputStream, Object obj) throws IOException {
            ii iiVar = (ii) obj;
            if (outputStream != null && iiVar != null) {
                DataOutputStream c01261 = new C01261(this, outputStream);
                if (iiVar.f516k != null) {
                    c01261.writeUTF(iiVar.f516k);
                } else {
                    c01261.writeUTF(XmlPullParser.NO_NAMESPACE);
                }
                if (iiVar.f357r != null) {
                    c01261.writeUTF(iiVar.f357r);
                } else {
                    c01261.writeUTF(XmlPullParser.NO_NAMESPACE);
                }
                c01261.writeLong(iiVar.f353n);
                c01261.writeInt(iiVar.f355p);
                c01261.writeLong(iiVar.f507b);
                c01261.writeInt(iiVar.f508c);
                c01261.writeInt(iiVar.f509d);
                c01261.writeInt(iiVar.f510e.f169e);
                Map f = iiVar.f511f;
                if (f != null) {
                    c01261.writeInt(iiVar.f511f.size());
                    for (String str : iiVar.f511f.keySet()) {
                        c01261.writeUTF(str);
                        c01261.writeUTF((String) f.get(str));
                    }
                } else {
                    c01261.writeInt(0);
                }
                c01261.writeLong(iiVar.f512g);
                c01261.writeInt(iiVar.f513h);
                c01261.writeInt(iiVar.f514i);
                if (iiVar.f515j != null) {
                    c01261.writeUTF(iiVar.f515j);
                } else {
                    c01261.writeUTF(XmlPullParser.NO_NAMESPACE);
                }
                c01261.writeBoolean(iiVar.f517l);
                c01261.flush();
                this.f504a.m641a(outputStream, iiVar.f506a);
            }
        }

        public C0580a() {
            this.f504a = new ky(new C0581a());
        }

        public final /* synthetic */ Object m461a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c01272 = new C01272(this, inputStream);
            String readUTF = c01272.readUTF();
            if (readUTF.equals(XmlPullParser.NO_NAMESPACE)) {
                readUTF = null;
            }
            String readUTF2 = c01272.readUTF();
            long readLong = c01272.readLong();
            int readInt = c01272.readInt();
            long readLong2 = c01272.readLong();
            int readInt2 = c01272.readInt();
            int readInt3 = c01272.readInt();
            ip a = ip.m72a(c01272.readInt());
            Map map = null;
            int readInt4 = c01272.readInt();
            if (readInt4 != 0) {
                map = new HashMap();
                for (int i = 0; i < readInt4; i++) {
                    map.put(c01272.readUTF(), c01272.readUTF());
                }
            }
            long readLong3 = c01272.readLong();
            readInt4 = c01272.readInt();
            int readInt5 = c01272.readInt();
            String readUTF3 = c01272.readUTF();
            if (readUTF3.equals(XmlPullParser.NO_NAMESPACE)) {
                readUTF3 = null;
            }
            boolean readBoolean = c01272.readBoolean();
            ii iiVar = new ii(readUTF, readLong2, readUTF2, readLong, readInt2, readInt3, a, map, readInt4, readInt5, readUTF3);
            iiVar.f512g = readLong3;
            iiVar.f517l = readBoolean;
            iiVar.f355p = readInt;
            iiVar.f506a = (ArrayList) this.f504a.m642b(inputStream);
            iiVar.m477d();
            return iiVar;
        }
    }

    static {
        f505t = ii.class.getName();
    }

    public ii(String str, long j, String str2, long j2, int i, int i2, ip ipVar, Map<String, String> map, int i3, int i4, String str3) {
        this.f519u = DNSConstants.PROBE_CONFLICT_INTERVAL;
        this.f520v = 30000;
        m220a(str2);
        this.f353n = j2;
        a_();
        this.f516k = str;
        this.f507b = j;
        this.f358s = i;
        this.f508c = i;
        this.f509d = i2;
        this.f510e = ipVar;
        this.f511f = map;
        this.f513h = i3;
        this.f514i = i4;
        this.f515j = str3;
        this.f512g = 30000;
        this.f506a = new ArrayList();
    }

    public final void a_() {
        super.a_();
        if (this.f355p != 1) {
            this.f512g *= 3;
        }
    }

    public final synchronized void m476c() {
        this.f518m.m62c();
    }

    public final void m477d() {
        Iterator it = this.f506a.iterator();
        while (it.hasNext()) {
            ((ij) it.next()).f119l = this;
        }
    }
}
