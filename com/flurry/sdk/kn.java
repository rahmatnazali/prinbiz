package com.flurry.sdk;

import com.google.android.gms.common.ConnectionResult;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class kn extends lx {
    static final String f713e;
    private km f714A;
    private int f715a;
    private int f716b;
    private final jw<String, String> f717c;
    private HttpURLConnection f718d;
    public String f719f;
    public C0161a f720g;
    public int f721h;
    public int f722i;
    public boolean f723j;
    public C0162c f724k;
    public boolean f725l;
    long f726m;
    public long f727n;
    public Exception f728o;
    public int f729p;
    public final jw<String, String> f730q;
    public boolean f731r;
    public int f732s;
    public boolean f733t;
    private boolean f734x;
    private boolean f735y;
    private final Object f736z;

    /* renamed from: com.flurry.sdk.kn.1 */
    class C01591 extends Thread {
        final /* synthetic */ kn f344a;

        C01591(kn knVar) {
            this.f344a = knVar;
        }

        public final void run() {
            try {
                if (this.f344a.f718d != null) {
                    this.f344a.f718d.disconnect();
                }
            } catch (Throwable th) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.kn.2 */
    static /* synthetic */ class C01602 {
        static final /* synthetic */ int[] f345a;

        static {
            f345a = new int[C0161a.values().length];
            try {
                f345a[C0161a.kPost.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f345a[C0161a.kPut.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f345a[C0161a.kDelete.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f345a[C0161a.kHead.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f345a[C0161a.kGet.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.kn.a */
    public enum C0161a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public final String toString() {
            switch (C01602.f345a[ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    return "POST";
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    return "PUT";
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    return "DELETE";
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    return "HEAD";
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    return "GET";
                default:
                    return null;
            }
        }
    }

    /* renamed from: com.flurry.sdk.kn.c */
    public interface C0162c {
        void m216a(kn knVar);

        void m217a(kn knVar, InputStream inputStream) throws Exception;

        void m218a(OutputStream outputStream) throws Exception;
    }

    /* renamed from: com.flurry.sdk.kn.b */
    public static class C0613b implements C0162c {
        public final void m607a(OutputStream outputStream) throws Exception {
        }

        public void m606a(kn knVar, InputStream inputStream) throws Exception {
        }

        public void m605a(kn knVar) {
        }
    }

    static {
        f713e = kn.class.getSimpleName();
    }

    public kn() {
        this.f721h = DNSConstants.RECORD_REAPER_INTERVAL;
        this.f722i = 15000;
        this.f723j = true;
        this.f717c = new jw();
        this.f726m = -1;
        this.f727n = -1;
        this.f729p = -1;
        this.f730q = new jw();
        this.f736z = new Object();
        this.f732s = 25000;
        this.f714A = new km(this);
    }

    public final void m694a(String str, String str2) {
        this.f717c.m144a((Object) str, (Object) str2);
    }

    public final boolean m695b() {
        boolean z;
        synchronized (this.f736z) {
            z = this.f735y;
        }
        return z;
    }

    public final boolean m696c() {
        return !m698e() && m697d();
    }

    public final boolean m697d() {
        return this.f729p >= NNTPReply.SERVER_READY_POSTING_ALLOWED && this.f729p < NNTPReply.SERVICE_DISCONTINUED && !this.f733t;
    }

    public final boolean m698e() {
        return this.f728o != null;
    }

    public final List<String> m692a(String str) {
        return this.f730q.m140a((Object) str);
    }

    public final void m699f() {
        kf.m182a(3, f713e, "Cancelling http request: " + this.f719f);
        synchronized (this.f736z) {
            this.f735y = true;
        }
        if (!this.f734x) {
            this.f734x = true;
            if (this.f718d != null) {
                new C01591(this).start();
            }
        }
    }

    public void m693a() {
        try {
            if (this.f719f != null) {
                if (jk.m98a().f258b) {
                    if (this.f720g == null || C0161a.kUnknown.equals(this.f720g)) {
                        this.f720g = C0161a.kGet;
                    }
                    m690i();
                    kf.m182a(4, f713e, "HTTP status: " + this.f729p + " for url: " + this.f719f);
                    this.f714A.m214a();
                    m701h();
                    return;
                }
                kf.m182a(3, f713e, "Network not available, aborting http request: " + this.f719f);
                this.f714A.m214a();
                m701h();
            }
        } catch (Throwable e) {
            kf.m182a(4, f713e, "HTTP status: " + this.f729p + " for url: " + this.f719f);
            kf.m183a(3, f713e, "Exception during http request: " + this.f719f, e);
            this.f716b = this.f718d.getReadTimeout();
            this.f715a = this.f718d.getConnectTimeout();
            this.f728o = e;
        } finally {
            this.f714A.m214a();
            m701h();
        }
    }

    public final void m700g() {
        m699f();
    }

    private void m690i() throws Exception {
        Closeable outputStream;
        Closeable bufferedOutputStream;
        Throwable th;
        Closeable closeable = null;
        if (!this.f735y) {
            this.f719f = lr.m308a(this.f719f);
            this.f718d = (HttpURLConnection) new URL(this.f719f).openConnection();
            this.f718d.setConnectTimeout(this.f721h);
            this.f718d.setReadTimeout(this.f722i);
            this.f718d.setRequestMethod(this.f720g.toString());
            this.f718d.setInstanceFollowRedirects(this.f723j);
            this.f718d.setDoOutput(C0161a.kPost.equals(this.f720g));
            this.f718d.setDoInput(true);
            for (Entry entry : this.f717c.m145b()) {
                this.f718d.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(C0161a.kGet.equals(this.f720g) || C0161a.kPost.equals(this.f720g))) {
                this.f718d.setRequestProperty("Accept-Encoding", XmlPullParser.NO_NAMESPACE);
            }
            if (this.f735y) {
                m691j();
                return;
            }
            if (C0161a.kPost.equals(this.f720g)) {
                try {
                    outputStream = this.f718d.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = null;
                        closeable = outputStream;
                        lr.m311a(bufferedOutputStream);
                        lr.m311a(closeable);
                        throw th;
                    }
                    try {
                        if (!(this.f724k == null || m695b())) {
                            this.f724k.m218a((OutputStream) bufferedOutputStream);
                        }
                        lr.m311a(bufferedOutputStream);
                        lr.m311a(outputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        closeable = outputStream;
                        lr.m311a(bufferedOutputStream);
                        lr.m311a(closeable);
                        throw th;
                    }
                } catch (Throwable th4) {
                    m691j();
                }
            }
            if (this.f725l) {
                this.f726m = System.currentTimeMillis();
            }
            if (this.f731r) {
                this.f714A.m215a((long) this.f732s);
            }
            this.f729p = this.f718d.getResponseCode();
            if (this.f725l && this.f726m != -1) {
                this.f727n = System.currentTimeMillis() - this.f726m;
            }
            this.f714A.m214a();
            for (Entry entry2 : this.f718d.getHeaderFields().entrySet()) {
                for (Object a : (List) entry2.getValue()) {
                    this.f730q.m144a(entry2.getKey(), a);
                }
            }
            if (!C0161a.kGet.equals(this.f720g) && !C0161a.kPost.equals(this.f720g)) {
                m691j();
            } else if (this.f735y) {
                m691j();
            } else {
                try {
                    bufferedOutputStream = this.f718d.getInputStream();
                    try {
                        outputStream = new BufferedInputStream(bufferedOutputStream);
                        try {
                            if (!(this.f724k == null || m695b())) {
                                this.f724k.m217a(this, outputStream);
                            }
                            lr.m311a(outputStream);
                            lr.m311a(bufferedOutputStream);
                            m691j();
                        } catch (Throwable th5) {
                            th = th5;
                            closeable = bufferedOutputStream;
                            bufferedOutputStream = outputStream;
                            lr.m311a(bufferedOutputStream);
                            lr.m311a(closeable);
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        Closeable closeable2 = bufferedOutputStream;
                        bufferedOutputStream = null;
                        closeable = closeable2;
                        lr.m311a(bufferedOutputStream);
                        lr.m311a(closeable);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    bufferedOutputStream = null;
                    lr.m311a(bufferedOutputStream);
                    lr.m311a(closeable);
                    throw th;
                }
            }
        }
    }

    final void m701h() {
        if (this.f724k != null && !m695b()) {
            this.f724k.m216a(this);
        }
    }

    private void m691j() {
        if (!this.f734x) {
            this.f734x = true;
            if (this.f718d != null) {
                this.f718d.disconnect();
            }
        }
    }
}
