package com.flurry.android;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.hk;
import com.flurry.sdk.ja;
import com.flurry.sdk.jd;
import com.flurry.sdk.jq;
import com.flurry.sdk.jr;
import com.flurry.sdk.js;
import com.flurry.sdk.jt;
import com.flurry.sdk.jz;
import com.flurry.sdk.ka;
import com.flurry.sdk.kb;
import com.flurry.sdk.kf;
import com.flurry.sdk.le;
import com.flurry.sdk.le.C0172a;
import com.flurry.sdk.lf;
import com.flurry.sdk.li;
import com.flurry.sdk.lr;
import com.flurry.sdk.mb;
import java.util.Date;
import java.util.Map;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.pop3.POP3;
import org.xmlpull.v1.XmlPullParser;

public final class FlurryAgent {
    private static final String f9a;
    private static final ka<le> f10b;
    private static FlurryAgentListener f11c;
    private static boolean f12d;
    private static int f13e;
    private static long f14f;
    private static boolean f15g;
    private static boolean f16h;
    private static String f17i;

    /* renamed from: com.flurry.android.FlurryAgent.2 */
    static /* synthetic */ class C01232 {
        static final /* synthetic */ int[] f2a;

        static {
            f2a = new int[C0172a.m268a().length];
            try {
                f2a[C0172a.f394b - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public static class Builder {
        private static FlurryAgentListener f3a;
        private boolean f4b;
        private int f5c;
        private long f6d;
        private boolean f7e;
        private boolean f8f;

        public Builder() {
            this.f4b = false;
            this.f5c = 5;
            this.f6d = 10000;
            this.f7e = true;
            this.f8f = false;
        }

        static {
            f3a = null;
        }

        public Builder withListener(FlurryAgentListener flurryAgentListener) {
            f3a = flurryAgentListener;
            return this;
        }

        public Builder withLogEnabled(boolean z) {
            this.f4b = z;
            return this;
        }

        public Builder withLogLevel(int i) {
            this.f5c = i;
            return this;
        }

        public Builder withContinueSessionMillis(long j) {
            this.f6d = j;
            return this;
        }

        public Builder withCaptureUncaughtExceptions(boolean z) {
            this.f7e = z;
            return this;
        }

        public Builder withPulseEnabled(boolean z) {
            this.f8f = z;
            return this;
        }

        public void build(Context context, String str) {
            FlurryAgent.m10a(f3a, this.f4b, this.f5c, this.f6d, this.f7e, this.f8f, context, str);
        }
    }

    /* renamed from: com.flurry.android.FlurryAgent.1 */
    class C05651 implements ka<le> {

        /* renamed from: com.flurry.android.FlurryAgent.1.1 */
        class C01221 implements Runnable {
            final /* synthetic */ le f0a;
            final /* synthetic */ C05651 f1b;

            C01221(C05651 c05651, le leVar) {
                this.f1b = c05651;
                this.f0a = leVar;
            }

            public final void run() {
                switch (C01232.f2a[this.f0a.f673c - 1]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        if (FlurryAgent.f11c != null) {
                            FlurryAgent.f11c.onSessionStarted();
                        }
                    default:
                }
            }
        }

        C05651() {
        }

        public final /* synthetic */ void m390a(jz jzVar) {
            jr.m120a().m124a(new C01221(this, (le) jzVar));
        }
    }

    static {
        f9a = FlurryAgent.class.getSimpleName();
        f10b = new C05651();
        f11c = null;
        f12d = false;
        f13e = 5;
        f14f = 10000;
        f15g = true;
        f16h = false;
        f17i = null;
    }

    private FlurryAgent() {
    }

    @Deprecated
    public static synchronized void init(Context context, String str) {
        synchronized (FlurryAgent.class) {
            if (VERSION.SDK_INT < 10) {
                kf.m189b(f9a, "Device SDK Version older than 10");
            } else {
                if (context == null) {
                    throw new NullPointerException("Null context");
                } else if (TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("API key not specified");
                } else {
                    if (jr.m120a() != null) {
                        kf.m196e(f9a, "Flurry is already initialized");
                    }
                    try {
                        mb.m358a();
                        jr.m121a(context, str);
                    } catch (Throwable th) {
                        kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
                    }
                    kf.m196e(f9a, "'init' method is deprecated.");
                }
            }
        }
    }

    public static int getAgentVersion() {
        return js.m128a();
    }

    public static String getReleaseVersion() {
        return js.m129b();
    }

    @Deprecated
    public static void setFlurryAgentListener(FlurryAgentListener flurryAgentListener) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (flurryAgentListener == null) {
            kf.m189b(f9a, "Listener cannot be null");
            kb.m157a().m166b("com.flurry.android.sdk.FlurrySessionEvent", f10b);
        } else {
            f11c = flurryAgentListener;
            kb.m157a().m164a("com.flurry.android.sdk.FlurrySessionEvent", f10b);
            kf.m196e(f9a, "'setFlurryAgentListener' method is deprecated.");
        }
    }

    @Deprecated
    public static void setLogEnabled(boolean z) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        if (z) {
            kf.m187b();
        } else {
            kf.m180a();
        }
        kf.m196e(f9a, "'setLogEnabled' method is deprecated.");
    }

    @Deprecated
    public static void setLogLevel(int i) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        kf.m181a(i);
        kf.m196e(f9a, "'setLogLevel' method is deprecated.");
    }

    public static void setVersionName(String str) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String versionName passed to setVersionName was null.");
        } else {
            li.m668a().m274a("VersionName", (Object) str);
        }
    }

    public static void setReportLocation(boolean z) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else {
            li.m668a().m274a("ReportLocation", (Object) Boolean.valueOf(z));
        }
    }

    public static void setLocation(float f, float f2) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        Location location = new Location("Explicit");
        location.setLatitude((double) f);
        location.setLongitude((double) f2);
        li.m668a().m274a("ExplicitLocation", (Object) location);
    }

    public static void clearLocation() {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else {
            li.m668a().m274a("ExplicitLocation", null);
        }
    }

    @Deprecated
    public static void setContinueSessionMillis(long j) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (j < DNSConstants.CLOSE_TIMEOUT) {
            kf.m189b(f9a, "Invalid time set for session resumption: " + j);
        } else {
            li.m668a().m274a("ContinueSessionMillis", (Object) Long.valueOf(j));
            kf.m196e(f9a, "'setContinueSessionMillis' method is deprecated.");
        }
    }

    public static void setLogEvents(boolean z) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else {
            li.m668a().m274a("LogEvents", (Object) Boolean.valueOf(z));
        }
    }

    @Deprecated
    public static void setCaptureUncaughtExceptions(boolean z) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        li.m668a().m274a("CaptureUncaughtExceptions", (Object) Boolean.valueOf(z));
        kf.m196e(f9a, "'setCaptureUncaughtExceptions' method is deprecated.");
    }

    public static void addOrigin(String str, String str2) {
        addOrigin(str, str2, null);
    }

    public static void addOrigin(String str, String str2, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("originName not specified");
        } else if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("originVersion not specified");
        } else {
            try {
                jt.m131a().m133a(str, str2, map);
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
        }
    }

    @Deprecated
    public static void setPulseEnabled(boolean z) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        li.m668a().m274a("ProtonEnabled", (Object) Boolean.valueOf(z));
        if (!z) {
            li.m668a().m274a("analyticsEnabled", (Object) Boolean.valueOf(true));
        }
        kf.m196e(f9a, "'setPulseEnabled' method is deprecated.");
    }

    @Deprecated
    public static void onStartSession(Context context, String str) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Api key not specified");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                lf.m651a().m662b(context);
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
            kf.m196e(f9a, "'onStartSession' method is deprecated.");
        }
    }

    public static void onStartSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                lf.m651a().m662b(context);
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
        }
    }

    public static void onEndSession(Context context) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (context == null) {
            throw new NullPointerException("Null context");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before ending a session");
        } else {
            try {
                lf.m651a().m664c(context);
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
        }
    }

    public static boolean isSessionActive() {
        boolean z = false;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else {
            try {
                z = lf.m651a().m666d();
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
        }
        return z;
    }

    public static String getSessionId() {
        String str = null;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            try {
                jd.m552a();
                str = jd.m553c();
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
        }
        return str;
    }

    public static FlurryEventRecordStatus logEvent(String str) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return flurryEventRecordStatus;
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else {
            FlurryEventRecordStatus flurryEventRecordStatus2;
            try {
                hk.m392a();
                ja c = hk.m394c();
                flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventFailed;
                if (c != null) {
                    flurryEventRecordStatus2 = c.m538a(str, null, false);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to log event: " + str, th);
                flurryEventRecordStatus2 = flurryEventRecordStatus;
            }
            return flurryEventRecordStatus2;
        }
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return flurryEventRecordStatus;
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else if (map == null) {
            kf.m189b(f9a, "String parameters passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else {
            FlurryEventRecordStatus flurryEventRecordStatus2;
            try {
                hk.m392a();
                ja c = hk.m394c();
                flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventFailed;
                if (c != null) {
                    flurryEventRecordStatus2 = c.m538a(str, (Map) map, false);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to log event: " + str, th);
                flurryEventRecordStatus2 = flurryEventRecordStatus;
            }
            return flurryEventRecordStatus2;
        }
    }

    public static FlurryEventRecordStatus logEvent(FlurrySyndicationEventName flurrySyndicationEventName, String str, Map<String, String> map) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return flurryEventRecordStatus;
        } else if (flurrySyndicationEventName == null) {
            kf.m189b(f9a, "String eventName passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else if (TextUtils.isEmpty(str)) {
            kf.m189b(f9a, "String syndicationId passed to logEvent was null or empty.");
            return flurryEventRecordStatus;
        } else if (map == null) {
            kf.m189b(f9a, "String parameters passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else {
            FlurryEventRecordStatus flurryEventRecordStatus2;
            try {
                hk.m392a();
                String flurrySyndicationEventName2 = flurrySyndicationEventName.toString();
                ja c = hk.m394c();
                flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventFailed;
                if (c != null) {
                    flurryEventRecordStatus2 = c.m537a(flurrySyndicationEventName2, str, (Map) map);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to log event: " + flurrySyndicationEventName.toString(), th);
                flurryEventRecordStatus2 = flurryEventRecordStatus;
            }
            return flurryEventRecordStatus2;
        }
    }

    public static FlurryEventRecordStatus logEvent(String str, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return flurryEventRecordStatus;
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else {
            FlurryEventRecordStatus flurryEventRecordStatus2;
            try {
                hk.m392a();
                ja c = hk.m394c();
                flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventFailed;
                if (c != null) {
                    flurryEventRecordStatus2 = c.m538a(str, null, z);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to log event: " + str, th);
                flurryEventRecordStatus2 = flurryEventRecordStatus;
            }
            return flurryEventRecordStatus2;
        }
    }

    public static FlurryEventRecordStatus logEvent(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return flurryEventRecordStatus;
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else if (map == null) {
            kf.m189b(f9a, "String parameters passed to logEvent was null.");
            return flurryEventRecordStatus;
        } else {
            FlurryEventRecordStatus flurryEventRecordStatus2;
            try {
                hk.m392a();
                ja c = hk.m394c();
                flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventFailed;
                if (c != null) {
                    flurryEventRecordStatus2 = c.m538a(str, (Map) map, z);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to log event: " + str, th);
                flurryEventRecordStatus2 = flurryEventRecordStatus;
            }
            return flurryEventRecordStatus2;
        }
    }

    public static void endTimedEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                hk.m392a();
                ja c = hk.m394c();
                if (c != null) {
                    c.m545a(str, null);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    public static void endTimedEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to endTimedEvent was null.");
        } else if (map == null) {
            kf.m189b(f9a, "String eventId passed to endTimedEvent was null.");
        } else {
            try {
                hk.m392a();
                ja c = hk.m394c();
                if (c != null) {
                    c.m545a(str, (Map) map);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, "Failed to signify the end of event: " + str, th);
            }
        }
    }

    @Deprecated
    public static void onError(String str, String str2, String str3) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            kf.m189b(f9a, "String message passed to onError was null.");
        } else if (str3 == null) {
            kf.m189b(f9a, "String errorClass passed to onError was null.");
        } else {
            try {
                StackTraceElement[] stackTraceElementArr;
                hk.m392a();
                Object stackTrace = Thread.currentThread().getStackTrace();
                if (stackTrace == null || stackTrace.length <= 2) {
                    Object obj = stackTrace;
                } else {
                    stackTraceElementArr = new StackTraceElement[(stackTrace.length - 2)];
                    System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
                }
                Throwable th = new Throwable(str2);
                th.setStackTrace(stackTraceElementArr);
                ja c = hk.m394c();
                if (c != null) {
                    c.m544a(str, str2, str3, th);
                }
            } catch (Throwable th2) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th2);
            }
            kf.m196e(f9a, "'onError' method is deprecated.");
        }
    }

    public static void onError(String str, String str2, Throwable th) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String errorId passed to onError was null.");
        } else if (str2 == null) {
            kf.m189b(f9a, "String message passed to onError was null.");
        } else if (th == null) {
            kf.m189b(f9a, "Throwable passed to onError was null.");
        } else {
            try {
                hk.m392a();
                hk.m393a(str, str2, th);
            } catch (Throwable th2) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th2);
            }
        }
    }

    @Deprecated
    public static void onEvent(String str) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to onEvent was null.");
        } else {
            try {
                hk.m392a();
                ja c = hk.m394c();
                if (c != null) {
                    c.m538a(str, null, false);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
            kf.m196e(f9a, "'onEvent' method is deprecated.");
        }
    }

    @Deprecated
    public static void onEvent(String str, Map<String, String> map) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String eventId passed to onEvent was null.");
        } else if (map == null) {
            kf.m189b(f9a, "Parameters Map passed to onEvent was null.");
        } else {
            try {
                hk.m392a();
                ja c = hk.m394c();
                if (c != null) {
                    c.m538a(str, (Map) map, false);
                }
            } catch (Throwable th) {
                kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
            }
            kf.m196e(f9a, "'onEvent' method is deprecated.");
        }
    }

    public static void onPageView() {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        try {
            hk.m392a();
            ja c = hk.m394c();
            if (c != null) {
                c.m548c();
            }
        } catch (Throwable th) {
            kf.m185a(f9a, XmlPullParser.NO_NAMESPACE, th);
        }
    }

    @Deprecated
    public static void setLocationCriteria(Criteria criteria) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        }
        kf.m196e(f9a, "'setLocationCriteria' method is deprecated.");
    }

    public static void setAge(int i) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (i > 0 && i < POP3.DEFAULT_PORT) {
            li.m668a().m274a("Age", (Object) Long.valueOf(new Date(new Date(System.currentTimeMillis() - (((long) i) * 31449600000L)).getYear(), 1, 1).getTime()));
        }
    }

    public static void setGender(byte b) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
            return;
        }
        switch (b) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                li.m668a().m274a("Gender", (Object) Byte.valueOf(b));
            default:
                li.m668a().m274a("Gender", (Object) Byte.valueOf((byte) -1));
        }
    }

    public static void setUserId(String str) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (str == null) {
            kf.m189b(f9a, "String userId passed to setUserId was null.");
        } else {
            li.m668a().m274a("UserId", (Object) lr.m316b(str));
        }
    }

    public static void setSessionOrigin(String str, String str2) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (TextUtils.isEmpty(str)) {
            kf.m189b(f9a, "String originName passed to setSessionOrigin was null or empty.");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            jd.m552a();
            jq i = jd.m559i();
            if (i != null) {
                i.m113a(str);
            }
            jd.m552a();
            i = jd.m559i();
            if (i != null) {
                i.m115b(str2);
            }
        }
    }

    public static void addSessionProperty(String str, String str2) {
        if (VERSION.SDK_INT < 10) {
            kf.m189b(f9a, "Device SDK Version older than 10");
        } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            kf.m189b(f9a, "String name or value passed to addSessionProperty was null or empty.");
        } else if (jr.m120a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else {
            jd.m552a();
            jq i = jd.m559i();
            if (i != null) {
                i.m114a(str, str2);
            }
        }
    }

    static /* synthetic */ void m10a(FlurryAgentListener flurryAgentListener, boolean z, int i, long j, boolean z2, boolean z3, Context context, String str) {
        f11c = flurryAgentListener;
        setFlurryAgentListener(flurryAgentListener);
        f12d = z;
        setLogEnabled(z);
        f13e = i;
        setLogLevel(i);
        f14f = j;
        setContinueSessionMillis(j);
        f15g = z2;
        setCaptureUncaughtExceptions(z2);
        f16h = z3;
        setPulseEnabled(z3);
        f17i = str;
        init(context, f17i);
    }
}
