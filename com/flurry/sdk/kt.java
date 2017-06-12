package com.flurry.sdk;

import com.flurry.sdk.ks.C0630a;
import com.flurry.sdk.ku.C0638a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class kt {
    public static final Integer f379a;
    private static final String f380d;
    String f381b;
    LinkedHashMap<String, List<String>> f382c;

    /* renamed from: com.flurry.sdk.kt.1 */
    class C06311 implements lc<List<ku>> {
        final /* synthetic */ kt f659a;

        C06311(kt ktVar) {
            this.f659a = ktVar;
        }

        public final kz<List<ku>> m626a(int i) {
            return new ky(new C0638a());
        }
    }

    /* renamed from: com.flurry.sdk.kt.2 */
    class C06322 implements lc<ks> {
        final /* synthetic */ kt f660a;

        C06322(kt ktVar) {
            this.f660a = ktVar;
        }

        public final kz<ks> m627a(int i) {
            return new C0630a();
        }
    }

    /* renamed from: com.flurry.sdk.kt.3 */
    class C06333 implements lc<List<ku>> {
        final /* synthetic */ kt f661a;

        C06333(kt ktVar) {
            this.f661a = ktVar;
        }

        public final kz<List<ku>> m628a(int i) {
            return new ky(new C0638a());
        }
    }

    /* renamed from: com.flurry.sdk.kt.4 */
    class C06344 implements lc<List<ku>> {
        final /* synthetic */ kt f662a;

        C06344(kt ktVar) {
            this.f662a = ktVar;
        }

        public final kz<List<ku>> m629a(int i) {
            return new ky(new C0638a());
        }
    }

    /* renamed from: com.flurry.sdk.kt.5 */
    class C06355 implements lc<List<ku>> {
        final /* synthetic */ kt f663a;

        C06355(kt ktVar) {
            this.f663a = ktVar;
        }

        public final kz<List<ku>> m630a(int i) {
            return new ky(new C0638a());
        }
    }

    /* renamed from: com.flurry.sdk.kt.6 */
    class C06366 implements lc<ks> {
        final /* synthetic */ kt f664a;

        C06366(kt ktVar) {
            this.f664a = ktVar;
        }

        public final kz<ks> m631a(int i) {
            return new C0630a();
        }
    }

    /* renamed from: com.flurry.sdk.kt.7 */
    class C06377 implements lc<List<ku>> {
        final /* synthetic */ kt f665a;

        C06377(kt ktVar) {
            this.f665a = ktVar;
        }

        public final kz<List<ku>> m632a(int i) {
            return new ky(new C0638a());
        }
    }

    static {
        f380d = kt.class.getSimpleName();
        f379a = Integer.valueOf(50);
    }

    public kt(String str) {
        this.f381b = str + "Main";
        m249b(this.f381b);
    }

    private void m249b(String str) {
        this.f382c = new LinkedHashMap();
        List<String> arrayList = new ArrayList();
        if (m256i(str)) {
            Collection j = m257j(str);
            if (j != null && j.size() > 0) {
                arrayList.addAll(j);
                for (String c : arrayList) {
                    m250c(c);
                }
            }
            m255h(str);
        } else {
            List<ku> list = (List) new jy(jr.m120a().f284a.getFileStreamPath(m251d(this.f381b)), str, 1, new C06311(this)).m151a();
            if (list == null) {
                kf.m192c(f380d, "New main file also not found. returning..");
                return;
            }
            for (ku kuVar : list) {
                arrayList.add(kuVar.f385a);
            }
        }
        for (String c2 : arrayList) {
            this.f382c.put(c2, m254g(c2));
        }
    }

    private void m250c(String str) {
        List<String> j = m257j(str);
        if (j == null) {
            kf.m192c(f380d, "No old file to replace");
            return;
        }
        for (String str2 : j) {
            byte[] k = m258k(str2);
            if (k == null) {
                kf.m182a(6, f380d, "File does not exist");
            } else {
                m248a(str2, k);
                lr.m318b();
                kf.m182a(5, f380d, "Deleting  block File for " + str2 + " file name:" + jr.m120a().f284a.getFileStreamPath(".flurrydatasenderblock." + str2));
                File fileStreamPath = jr.m120a().f284a.getFileStreamPath(".flurrydatasenderblock." + str2);
                if (fileStreamPath.exists()) {
                    kf.m182a(5, f380d, "Found file for " + str2 + ". Deleted - " + fileStreamPath.delete());
                }
            }
        }
        if (j != null) {
            m247a(str, j, ".YFlurrySenderIndex.info.");
            m255h(str);
        }
    }

    private static String m251d(String str) {
        return ".YFlurrySenderIndex.info." + str;
    }

    private synchronized void m246a() {
        List linkedList = new LinkedList(this.f382c.keySet());
        new jy(jr.m120a().f284a.getFileStreamPath(m251d(this.f381b)), ".YFlurrySenderIndex.info.", 1, new C06344(this)).m153b();
        if (!linkedList.isEmpty()) {
            m247a(this.f381b, linkedList, this.f381b);
        }
    }

    public final synchronized void m260a(ks ksVar, String str) {
        Object obj = null;
        synchronized (this) {
            List linkedList;
            kf.m182a(4, f380d, "addBlockInfo" + str);
            String str2 = ksVar.f377a;
            List list = (List) this.f382c.get(str);
            if (list == null) {
                kf.m182a(4, f380d, "New Data Key");
                linkedList = new LinkedList();
                obj = 1;
            } else {
                linkedList = list;
            }
            linkedList.add(str2);
            if (linkedList.size() > f379a.intValue()) {
                m252e((String) linkedList.get(0));
                linkedList.remove(0);
            }
            this.f382c.put(str, linkedList);
            m247a(str, linkedList, ".YFlurrySenderIndex.info.");
            if (obj != null) {
                m246a();
            }
        }
    }

    private boolean m252e(String str) {
        return new jy(jr.m120a().f284a.getFileStreamPath(ks.m243a(str)), ".yflurrydatasenderblock.", 1, new C06322(this)).m153b();
    }

    public final boolean m261a(String str, String str2) {
        List list = (List) this.f382c.get(str2);
        boolean z = false;
        if (list != null) {
            m252e(str);
            z = list.remove(str);
        }
        if (list == null || list.isEmpty()) {
            m253f(str2);
        } else {
            this.f382c.put(str2, list);
            m247a(str2, list, ".YFlurrySenderIndex.info.");
        }
        return z;
    }

    public final List<String> m259a(String str) {
        return (List) this.f382c.get(str);
    }

    private synchronized boolean m253f(String str) {
        boolean b;
        lr.m318b();
        jy jyVar = new jy(jr.m120a().f284a.getFileStreamPath(m251d(str)), ".YFlurrySenderIndex.info.", 1, new C06333(this));
        List<String> a = m259a(str);
        if (a != null) {
            kf.m182a(4, f380d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + a.size());
            for (String str2 : a) {
                m252e(str2);
                kf.m182a(4, f380d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
            }
        }
        this.f382c.remove(str);
        b = jyVar.m153b();
        m246a();
        return b;
    }

    private synchronized List<String> m254g(String str) {
        List<String> arrayList;
        lr.m318b();
        kf.m182a(5, f380d, "Reading Index File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(m251d(str)));
        List<ku> list = (List) new jy(jr.m120a().f284a.getFileStreamPath(m251d(str)), ".YFlurrySenderIndex.info.", 1, new C06355(this)).m151a();
        arrayList = new ArrayList();
        for (ku kuVar : list) {
            arrayList.add(kuVar.f385a);
        }
        return arrayList;
    }

    private synchronized void m248a(String str, byte[] bArr) {
        lr.m318b();
        kf.m182a(5, f380d, "Saving Block File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(ks.m243a(str)));
        new jy(jr.m120a().f284a.getFileStreamPath(ks.m243a(str)), ".yflurrydatasenderblock.", 1, new C06366(this)).m152a(new ks(bArr));
    }

    private static void m255h(String str) {
        lr.m318b();
        kf.m182a(5, f380d, "Deleting Index File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(".FlurrySenderIndex.info." + str));
        File fileStreamPath = jr.m120a().f284a.getFileStreamPath(".FlurrySenderIndex.info." + str);
        if (fileStreamPath.exists()) {
            kf.m182a(5, f380d, "Found file for " + str + ". Deleted - " + fileStreamPath.delete());
        }
    }

    private synchronized void m247a(String str, List<String> list, String str2) {
        lr.m318b();
        kf.m182a(5, f380d, "Saving Index File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(m251d(str)));
        jy jyVar = new jy(jr.m120a().f284a.getFileStreamPath(m251d(str)), str2, 1, new C06377(this));
        List arrayList = new ArrayList();
        for (String kuVar : list) {
            arrayList.add(new ku(kuVar));
        }
        jyVar.m152a(arrayList);
    }

    private synchronized boolean m256i(String str) {
        File fileStreamPath;
        fileStreamPath = jr.m120a().f284a.getFileStreamPath(".FlurrySenderIndex.info." + str);
        kf.m182a(5, f380d, "isOldIndexFilePresent: for " + str + fileStreamPath.exists());
        return fileStreamPath.exists();
    }

    private synchronized List<String> m257j(String str) {
        Throwable th;
        Throwable th2;
        Throwable th3;
        List<String> list = null;
        synchronized (this) {
            lr.m318b();
            kf.m182a(5, f380d, "Reading Index File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(".FlurrySenderIndex.info." + str));
            File fileStreamPath = jr.m120a().f284a.getFileStreamPath(".FlurrySenderIndex.info." + str);
            List<String> arrayList;
            if (fileStreamPath.exists()) {
                kf.m182a(5, f380d, "Reading Index File for " + str + " Found file.");
                Closeable dataInputStream;
                try {
                    dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                    try {
                        int readUnsignedShort = dataInputStream.readUnsignedShort();
                        if (readUnsignedShort == 0) {
                            lr.m311a(dataInputStream);
                        } else {
                            arrayList = new ArrayList(readUnsignedShort);
                            int i = 0;
                            while (i < readUnsignedShort) {
                                try {
                                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                                    kf.m182a(4, f380d, "read iter " + i + " dataLength = " + readUnsignedShort2);
                                    byte[] bArr = new byte[readUnsignedShort2];
                                    dataInputStream.readFully(bArr);
                                    arrayList.add(new String(bArr));
                                    i++;
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            }
                            dataInputStream.readUnsignedShort();
                            lr.m311a(dataInputStream);
                            list = arrayList;
                        }
                    } catch (Throwable th32) {
                        th2 = th32;
                        arrayList = null;
                        th = th2;
                        try {
                            kf.m183a(6, f380d, "Error when loading persistent file", th);
                            lr.m311a(dataInputStream);
                            list = arrayList;
                            return list;
                        } catch (Throwable th5) {
                            th32 = th5;
                            lr.m311a(dataInputStream);
                            throw th32;
                        }
                    }
                } catch (Throwable th6) {
                    th32 = th6;
                    dataInputStream = null;
                    lr.m311a(dataInputStream);
                    throw th32;
                }
            } else {
                kf.m182a(5, f380d, "Agent cache file doesn't exist.");
                arrayList = null;
                list = arrayList;
            }
        }
        return list;
    }

    private static byte[] m258k(String str) {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        byte[] bArr = null;
        lr.m318b();
        kf.m182a(5, f380d, "Reading block File for " + str + " file name:" + jr.m120a().f284a.getFileStreamPath(".flurrydatasenderblock." + str));
        File fileStreamPath = jr.m120a().f284a.getFileStreamPath(".flurrydatasenderblock." + str);
        if (fileStreamPath.exists()) {
            kf.m182a(5, f380d, "Reading Index File for " + str + " Found file.");
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        lr.m311a(dataInputStream);
                    } else {
                        bArr = new byte[readUnsignedShort];
                        dataInputStream.readFully(bArr);
                        dataInputStream.readUnsignedShort();
                        lr.m311a(dataInputStream);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        kf.m183a(6, f380d, "Error when loading persistent file", th);
                        lr.m311a(dataInputStream);
                        return bArr;
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
        kf.m182a(4, f380d, "Agent cache file doesn't exist.");
        return bArr;
    }
}
