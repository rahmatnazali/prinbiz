package com.flurry.sdk;

import com.flurry.sdk.le.C0172a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.net.ftp.FTPClient;

public class je {
    private static final String f240b;
    private static je f241c;
    public final Map<jm, byte[]> f242a;
    private final Set<String> f243d;
    private final ka<le> f244e;
    private C0148a f245f;
    private jo f246g;
    private String f247h;

    /* renamed from: com.flurry.sdk.je.3 */
    class C01463 implements FilenameFilter {
        final /* synthetic */ je f231a;

        C01463(je jeVar) {
            this.f231a = jeVar;
        }

        public final boolean accept(File file, String str) {
            return str.startsWith(".flurryagent.");
        }
    }

    /* renamed from: com.flurry.sdk.je.4 */
    static /* synthetic */ class C01474 {
        static final /* synthetic */ int[] f232a;
        static final /* synthetic */ int[] f233b;

        static {
            f233b = new int[C0148a.values().length];
            try {
                f233b[C0148a.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f233b[C0148a.ADVERTISING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f233b[C0148a.DEVICE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f233b[C0148a.REPORTED_IDS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            f232a = new int[C0172a.m268a().length];
            try {
                f232a[C0172a.f393a - 1] = 1;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.je.a */
    enum C0148a {
        NONE,
        ADVERTISING,
        DEVICE,
        REPORTED_IDS,
        FINISHED
    }

    /* renamed from: com.flurry.sdk.je.1 */
    class C06021 implements ka<le> {
        final /* synthetic */ je f594a;

        /* renamed from: com.flurry.sdk.je.1.1 */
        class C06011 extends lw {
            final /* synthetic */ C06021 f593a;

            C06011(C06021 c06021) {
                this.f593a = c06021;
            }

            public final void m562a() {
                this.f593a.f594a.m90e();
            }
        }

        C06021(je jeVar) {
            this.f594a = jeVar;
        }

        public final /* synthetic */ void m563a(jz jzVar) {
            switch (C01474.f232a[((le) jzVar).f673c - 1]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (this.f594a.m94c()) {
                        jr.m120a().m126b(new C06011(this));
                    }
                default:
            }
        }
    }

    /* renamed from: com.flurry.sdk.je.2 */
    class C06032 extends lw {
        final /* synthetic */ je f595a;

        C06032(je jeVar) {
            this.f595a = jeVar;
        }

        public final void m564a() {
            je.m89b(this.f595a);
        }
    }

    static {
        f240b = je.class.getSimpleName();
    }

    public static synchronized je m85a() {
        je jeVar;
        synchronized (je.class) {
            if (f241c == null) {
                f241c = new je();
            }
            jeVar = f241c;
        }
        return jeVar;
    }

    public static void m88b() {
        f241c = null;
    }

    private je() {
        Set hashSet = new HashSet();
        hashSet.add("null");
        hashSet.add("9774d56d682e549c");
        hashSet.add("dead00beef");
        this.f243d = Collections.unmodifiableSet(hashSet);
        this.f242a = new HashMap();
        this.f244e = new C06021(this);
        this.f245f = C0148a.NONE;
        kb.m157a().m164a("com.flurry.android.sdk.FlurrySessionEvent", this.f244e);
        jr.m120a().m126b(new C06032(this));
    }

    public final boolean m94c() {
        return C0148a.FINISHED.equals(this.f245f);
    }

    public final boolean m95d() {
        if (this.f246g != null && this.f246g.f268b) {
            return false;
        }
        return true;
    }

    private void m90e() {
        lr.m318b();
        if (ls.m327a(jr.m120a().f284a)) {
            this.f246g = ls.m329b(jr.m120a().f284a);
            if (m94c()) {
                m93h();
                kb.m157a().m161a(new jg());
            }
        }
    }

    private static void m87a(String str, File file) {
        Closeable dataOutputStream;
        Throwable th;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            try {
                dataOutputStream.writeInt(1);
                dataOutputStream.writeUTF(str);
                lr.m311a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                try {
                    kf.m183a(6, f240b, "Error when saving deviceId", th);
                    lr.m311a(dataOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    lr.m311a(dataOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            dataOutputStream = null;
            lr.m311a(dataOutputStream);
            throw th;
        }
    }

    private static String m91f() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File fileStreamPath = jr.m120a().f284a.getFileStreamPath(".flurryb.");
        if (fileStreamPath != null && fileStreamPath.exists()) {
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    if (1 == dataInputStream.readInt()) {
                        str = dataInputStream.readUTF();
                    }
                    lr.m311a(dataInputStream);
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        kf.m183a(6, f240b, "Error when loading deviceId", th);
                        lr.m311a(dataInputStream);
                        return str;
                    } catch (Throwable th4) {
                        th2 = th4;
                        lr.m311a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = str;
                th2 = th5;
                lr.m311a(dataInputStream);
                throw th2;
            }
        }
        return str;
    }

    private String m92g() {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        String str = null;
        File filesDir = jr.m120a().f284a.getFilesDir();
        if (filesDir != null) {
            String[] list = filesDir.list(new C01463(this));
            if (!(list == null || list.length == 0)) {
                filesDir = jr.m120a().f284a.getFileStreamPath(list[0]);
                if (filesDir != null && filesDir.exists()) {
                    try {
                        dataInputStream = new DataInputStream(new FileInputStream(filesDir));
                        try {
                            if (46586 == dataInputStream.readUnsignedShort()) {
                                if (2 == dataInputStream.readUnsignedShort()) {
                                    dataInputStream.readUTF();
                                    str = dataInputStream.readUTF();
                                }
                            }
                            lr.m311a(dataInputStream);
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                kf.m183a(6, f240b, "Error when loading deviceId", th);
                                lr.m311a(dataInputStream);
                                return str;
                            } catch (Throwable th4) {
                                th2 = th4;
                                lr.m311a(dataInputStream);
                                throw th2;
                            }
                        }
                    } catch (Throwable th5) {
                        dataInputStream = null;
                        th2 = th5;
                        lr.m311a(dataInputStream);
                        throw th2;
                    }
                }
            }
        }
        return str;
    }

    private void m93h() {
        String str;
        if (this.f246g == null) {
            str = null;
        } else {
            str = this.f246g.f267a;
        }
        if (str != null) {
            kf.m182a(3, f240b, "Fetched advertising id");
            this.f242a.put(jm.AndroidAdvertisingId, lr.m321e(str));
        }
        str = this.f247h;
        if (str != null) {
            kf.m182a(3, f240b, "Fetched device id");
            this.f242a.put(jm.DeviceId, lr.m321e(str));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void m89b(com.flurry.sdk.je r10) {
        /*
        r8 = 37;
        r1 = 0;
    L_0x0003:
        r0 = com.flurry.sdk.je.C0148a.FINISHED;
        r2 = r10.f245f;
        r0 = r0.equals(r2);
        if (r0 != 0) goto L_0x0115;
    L_0x000d:
        r0 = com.flurry.sdk.je.C01474.f233b;
        r2 = r10.f245f;
        r2 = r2.ordinal();
        r0 = r0[r2];
        switch(r0) {
            case 1: goto L_0x004f;
            case 2: goto L_0x0054;
            case 3: goto L_0x0059;
            case 4: goto L_0x005e;
            default: goto L_0x001a;
        };
    L_0x001a:
        r0 = com.flurry.sdk.je.C01474.f233b;	 Catch:{ Exception -> 0x002c }
        r2 = r10.f245f;	 Catch:{ Exception -> 0x002c }
        r2 = r2.ordinal();	 Catch:{ Exception -> 0x002c }
        r0 = r0[r2];	 Catch:{ Exception -> 0x002c }
        switch(r0) {
            case 2: goto L_0x0028;
            case 3: goto L_0x0063;
            case 4: goto L_0x0110;
            default: goto L_0x0027;
        };	 Catch:{ Exception -> 0x002c }
    L_0x0027:
        goto L_0x0003;
    L_0x0028:
        r10.m90e();	 Catch:{ Exception -> 0x002c }
        goto L_0x0003;
    L_0x002c:
        r0 = move-exception;
        r2 = 4;
        r3 = f240b;
        r4 = new java.lang.StringBuilder;
        r5 = "Exception during id fetch:";
        r4.<init>(r5);
        r5 = r10.f245f;
        r4 = r4.append(r5);
        r5 = ", ";
        r4 = r4.append(r5);
        r0 = r4.append(r0);
        r0 = r0.toString();
        com.flurry.sdk.kf.m182a(r2, r3, r0);
        goto L_0x0003;
    L_0x004f:
        r0 = com.flurry.sdk.je.C0148a.ADVERTISING;
        r10.f245f = r0;
        goto L_0x001a;
    L_0x0054:
        r0 = com.flurry.sdk.je.C0148a.DEVICE;
        r10.f245f = r0;
        goto L_0x001a;
    L_0x0059:
        r0 = com.flurry.sdk.je.C0148a.REPORTED_IDS;
        r10.f245f = r0;
        goto L_0x001a;
    L_0x005e:
        r0 = com.flurry.sdk.je.C0148a.FINISHED;
        r10.f245f = r0;
        goto L_0x001a;
    L_0x0063:
        com.flurry.sdk.lr.m318b();	 Catch:{ Exception -> 0x002c }
        r0 = com.flurry.sdk.jr.m120a();	 Catch:{ Exception -> 0x002c }
        r0 = r0.f284a;	 Catch:{ Exception -> 0x002c }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x002c }
        r2 = "android_id";
        r2 = android.provider.Settings.Secure.getString(r0, r2);	 Catch:{ Exception -> 0x002c }
        r0 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x002c }
        if (r0 == 0) goto L_0x008a;
    L_0x007c:
        r0 = r1;
    L_0x007d:
        if (r0 != 0) goto L_0x009c;
    L_0x007f:
        r0 = 0;
    L_0x0080:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x002c }
        if (r2 != 0) goto L_0x00ac;
    L_0x0086:
        r10.f247h = r0;	 Catch:{ Exception -> 0x002c }
        goto L_0x0003;
    L_0x008a:
        r0 = java.util.Locale.US;	 Catch:{ Exception -> 0x002c }
        r0 = r2.toLowerCase(r0);	 Catch:{ Exception -> 0x002c }
        r3 = r10.f243d;	 Catch:{ Exception -> 0x002c }
        r0 = r3.contains(r0);	 Catch:{ Exception -> 0x002c }
        if (r0 == 0) goto L_0x009a;
    L_0x0098:
        r0 = r1;
        goto L_0x007d;
    L_0x009a:
        r0 = 1;
        goto L_0x007d;
    L_0x009c:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002c }
        r3 = "AND";
        r0.<init>(r3);	 Catch:{ Exception -> 0x002c }
        r0 = r0.append(r2);	 Catch:{ Exception -> 0x002c }
        r0 = r0.toString();	 Catch:{ Exception -> 0x002c }
        goto L_0x0080;
    L_0x00ac:
        r0 = m91f();	 Catch:{ Exception -> 0x002c }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x002c }
        if (r2 == 0) goto L_0x0086;
    L_0x00b6:
        r0 = r10.m92g();	 Catch:{ Exception -> 0x002c }
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x002c }
        if (r2 == 0) goto L_0x00f3;
    L_0x00c0:
        r2 = java.lang.Math.random();	 Catch:{ Exception -> 0x002c }
        r2 = java.lang.Double.doubleToLongBits(r2);	 Catch:{ Exception -> 0x002c }
        r4 = java.lang.System.nanoTime();	 Catch:{ Exception -> 0x002c }
        r0 = com.flurry.sdk.jr.m120a();	 Catch:{ Exception -> 0x002c }
        r0 = r0.f284a;	 Catch:{ Exception -> 0x002c }
        r0 = com.flurry.sdk.lo.m288a(r0);	 Catch:{ Exception -> 0x002c }
        r6 = com.flurry.sdk.lr.m325i(r0);	 Catch:{ Exception -> 0x002c }
        r6 = r6 * r8;
        r4 = r4 + r6;
        r4 = r4 * r8;
        r2 = r2 + r4;
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002c }
        r4 = "ID";
        r0.<init>(r4);	 Catch:{ Exception -> 0x002c }
        r4 = 16;
        r2 = java.lang.Long.toString(r2, r4);	 Catch:{ Exception -> 0x002c }
        r0 = r0.append(r2);	 Catch:{ Exception -> 0x002c }
        r0 = r0.toString();	 Catch:{ Exception -> 0x002c }
    L_0x00f3:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x002c }
        if (r2 != 0) goto L_0x0086;
    L_0x00f9:
        r2 = com.flurry.sdk.jr.m120a();	 Catch:{ Exception -> 0x002c }
        r2 = r2.f284a;	 Catch:{ Exception -> 0x002c }
        r3 = ".flurryb.";
        r2 = r2.getFileStreamPath(r3);	 Catch:{ Exception -> 0x002c }
        r3 = com.flurry.sdk.lq.m302a(r2);	 Catch:{ Exception -> 0x002c }
        if (r3 == 0) goto L_0x0086;
    L_0x010b:
        m87a(r0, r2);	 Catch:{ Exception -> 0x002c }
        goto L_0x0086;
    L_0x0110:
        r10.m93h();	 Catch:{ Exception -> 0x002c }
        goto L_0x0003;
    L_0x0115:
        r0 = new com.flurry.sdk.jf;
        r0.<init>();
        r1 = com.flurry.sdk.kb.m157a();
        r1.m161a(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.je.b(com.flurry.sdk.je):void");
    }
}
