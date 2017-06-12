package com.flurry.sdk;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.flurry.sdk.lj.C0175a;
import org.apache.commons.net.ftp.FTPClient;

public class ji implements C0175a {
    private static ji f597a;
    private static final String f598b;
    private final int f599c;
    private final long f600d;
    private final long f601e;
    private final long f602f;
    private boolean f603g;
    private Location f604h;
    private long f605i;
    private LocationManager f606j;
    private C0149a f607k;
    private Location f608l;
    private boolean f609m;
    private int f610n;
    private ka<ll> f611o;

    /* renamed from: com.flurry.sdk.ji.a */
    class C0149a implements LocationListener {
        final /* synthetic */ ji f249a;

        public C0149a(ji jiVar) {
            this.f249a = jiVar;
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public final void onProviderEnabled(String str) {
        }

        public final void onProviderDisabled(String str) {
        }

        public final void onLocationChanged(Location location) {
            if (location != null) {
                this.f249a.f608l = location;
            }
            if (ji.m574c(this.f249a) >= 3) {
                kf.m182a(4, ji.f598b, "Max location reports reached, stopping");
                this.f249a.m576g();
            }
        }
    }

    /* renamed from: com.flurry.sdk.ji.1 */
    class C06041 implements ka<ll> {
        final /* synthetic */ ji f596a;

        C06041(ji jiVar) {
            this.f596a = jiVar;
        }

        public final /* synthetic */ void m565a(jz jzVar) {
            if (this.f596a.f605i > 0 && this.f596a.f605i < System.currentTimeMillis()) {
                kf.m182a(4, ji.f598b, "No location received in 90 seconds , stopping LocationManager");
                this.f596a.m576g();
            }
        }
    }

    static /* synthetic */ int m574c(ji jiVar) {
        int i = jiVar.f610n + 1;
        jiVar.f610n = i;
        return i;
    }

    public static synchronized ji m569a() {
        ji jiVar;
        synchronized (ji.class) {
            if (f597a == null) {
                f597a = new ji();
            }
            jiVar = f597a;
        }
        return jiVar;
    }

    public static void m571b() {
        if (f597a != null) {
            li.m668a().m275b("ReportLocation", f597a);
            li.m668a().m275b("ExplicitLocation", f597a);
        }
        f597a = null;
    }

    static {
        f598b = ji.class.getSimpleName();
    }

    private ji() {
        this.f599c = 3;
        this.f600d = 10000;
        this.f601e = 90000;
        this.f602f = 0;
        this.f605i = 0;
        this.f609m = false;
        this.f610n = 0;
        this.f611o = new C06041(this);
        this.f606j = (LocationManager) jr.m120a().f284a.getSystemService("location");
        this.f607k = new C0149a(this);
        lj a = li.m668a();
        this.f603g = ((Boolean) a.m272a("ReportLocation")).booleanValue();
        a.m273a("ReportLocation", (C0175a) this);
        kf.m182a(4, f598b, "initSettings, ReportLocation = " + this.f603g);
        this.f604h = (Location) a.m272a("ExplicitLocation");
        a.m273a("ExplicitLocation", (C0175a) this);
        kf.m182a(4, f598b, "initSettings, ExplicitLocation = " + this.f604h);
    }

    public final synchronized void m578c() {
        kf.m182a(4, f598b, "Location update requested");
        if (this.f610n < 3 && !this.f609m && this.f603g && this.f604h == null) {
            Context context = jr.m120a().f284a;
            if (context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 || context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                this.f610n = 0;
                String str = null;
                if (m570a(context)) {
                    str = "passive";
                } else if (m573b(context)) {
                    str = "network";
                }
                if (!TextUtils.isEmpty(str)) {
                    this.f606j.requestLocationUpdates(str, 10000, 0.0f, this.f607k, Looper.getMainLooper());
                }
                this.f608l = m568a(str);
                this.f605i = System.currentTimeMillis() + 90000;
                kf.m182a(4, f598b, "Register location timer");
                lm.m277a().m280a(this.f611o);
                this.f609m = true;
                kf.m182a(4, f598b, "LocationProvider started");
            }
        }
    }

    public final synchronized void m579d() {
        kf.m182a(4, f598b, "Stop update location requested");
        m576g();
    }

    public final Location m580e() {
        Location location = null;
        if (this.f604h != null) {
            return this.f604h;
        }
        if (this.f603g) {
            Context context = jr.m120a().f284a;
            if (!m570a(context) && !m573b(context)) {
                return null;
            }
            String str = m570a(context) ? "passive" : m573b(context) ? "network" : null;
            if (str != null) {
                location = m568a(str);
                if (location != null) {
                    this.f608l = location;
                }
                location = this.f608l;
            }
        }
        kf.m182a(4, f598b, "getLocation() = " + location);
        return location;
    }

    private void m576g() {
        if (this.f609m) {
            this.f606j.removeUpdates(this.f607k);
            this.f610n = 0;
            this.f605i = 0;
            kf.m182a(4, f598b, "Unregister location timer");
            lm.m277a().m281b(this.f611o);
            this.f609m = false;
            kf.m182a(4, f598b, "LocationProvider stopped");
        }
    }

    private static boolean m570a(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private static boolean m573b(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    private Location m568a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f606j.getLastKnownLocation(str);
    }

    public final void m577a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -864112343:
                if (str.equals("ReportLocation")) {
                    obj2 = null;
                    break;
                }
                break;
            case -300729815:
                if (str.equals("ExplicitLocation")) {
                    obj2 = 1;
                    break;
                }
                break;
        }
        switch (obj2) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.f603g = ((Boolean) obj).booleanValue();
                kf.m182a(4, f598b, "onSettingUpdate, ReportLocation = " + this.f603g);
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.f604h = (Location) obj;
                kf.m182a(4, f598b, "onSettingUpdate, ExplicitLocation = " + this.f604h);
            default:
                kf.m182a(6, f598b, "LocationProvider internal error! Had to be LocationCriteria, ReportLocation or ExplicitLocation key.");
        }
    }
}
