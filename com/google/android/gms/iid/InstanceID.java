package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.hiti.sql.oadc.OADCItem;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class InstanceID {
    public static final String ERROR_BACKOFF = "RETRY_LATER";
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    static Map<String, InstanceID> aap;
    private static zzd aaq;
    private static zzc aar;
    static String aav;
    KeyPair aas;
    String aat;
    long aau;
    Context mContext;

    static {
        aap = new HashMap();
    }

    protected InstanceID(Context context, String str, Bundle bundle) {
        this.aat = XmlPullParser.NO_NAMESPACE;
        this.mContext = context.getApplicationContext();
        this.aat = str;
    }

    public static InstanceID getInstance(Context context) {
        return zza(context, null);
    }

    public static synchronized InstanceID zza(Context context, Bundle bundle) {
        InstanceID instanceID;
        synchronized (InstanceID.class) {
            String string = bundle == null ? XmlPullParser.NO_NAMESPACE : bundle.getString("subtype");
            String str = string == null ? XmlPullParser.NO_NAMESPACE : string;
            Context applicationContext = context.getApplicationContext();
            if (aaq == null) {
                aaq = new zzd(applicationContext);
                aar = new zzc(applicationContext);
            }
            aav = Integer.toString(zzdf(applicationContext));
            instanceID = (InstanceID) aap.get(str);
            if (instanceID == null) {
                instanceID = new InstanceID(applicationContext, str, bundle);
                aap.put(str, instanceID);
            }
        }
        return instanceID;
    }

    static String zza(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) (((digest[0] & 15) + 112) & TelnetOption.MAX_OPTION_VALUE);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("InstanceID", "Unexpected error, device missing required alghorithms");
            return null;
        }
    }

    static int zzdf(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return i;
        }
    }

    static String zzdg(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return null;
        }
    }

    static String zzu(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public void deleteInstanceID() throws IOException {
        zzb("*", "*", null);
        zzblx();
    }

    public void deleteToken(String str, String str2) throws IOException {
        zzb(str, str2, null);
    }

    public long getCreationTime() {
        if (this.aau == 0) {
            String str = aaq.get(this.aat, "cre");
            if (str != null) {
                this.aau = Long.parseLong(str);
            }
        }
        return this.aau;
    }

    public String getId() {
        return zza(zzblw());
    }

    public String getToken(String str, String str2) throws IOException {
        return getToken(str, str2, null);
    }

    public String getToken(String str, String str2, Bundle bundle) throws IOException {
        Object obj = null;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        Object obj2 = 1;
        String zzi = zzbma() ? null : aaq.zzi(this.aat, str, str2);
        if (zzi == null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            if (bundle.getString("ttl") != null) {
                obj2 = null;
            }
            if (!"jwt".equals(bundle.getString("type"))) {
                obj = obj2;
            }
            zzi = zzc(str, str2, bundle);
            if (!(zzi == null || r1 == null)) {
                aaq.zza(this.aat, str, str2, zzi, aav);
            }
        }
        return zzi;
    }

    public void zzb(String str, String str2, Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        aaq.zzj(this.aat, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("sender", str);
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("subscription", str);
        bundle.putString("delete", OADCItem.WATCH_TYPE_WATCH);
        bundle.putString("X-delete", OADCItem.WATCH_TYPE_WATCH);
        bundle.putString("subtype", XmlPullParser.NO_NAMESPACE.equals(this.aat) ? str : this.aat);
        String str3 = "X-subtype";
        if (!XmlPullParser.NO_NAMESPACE.equals(this.aat)) {
            str = this.aat;
        }
        bundle.putString(str3, str);
        aar.zzt(aar.zza(bundle, zzblw()));
    }

    KeyPair zzblw() {
        if (this.aas == null) {
            this.aas = aaq.zzkh(this.aat);
        }
        if (this.aas == null) {
            this.aau = System.currentTimeMillis();
            this.aas = aaq.zze(this.aat, this.aau);
        }
        return this.aas;
    }

    public void zzblx() {
        this.aau = 0;
        aaq.zzki(this.aat);
        this.aas = null;
    }

    public zzd zzbly() {
        return aaq;
    }

    public zzc zzblz() {
        return aar;
    }

    boolean zzbma() {
        String str = aaq.get("appVersion");
        if (str == null || !str.equals(aav)) {
            return true;
        }
        str = aaq.get("lastToken");
        if (str == null) {
            return true;
        }
        return (System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong(str)).longValue() > 604800;
    }

    public String zzc(String str, String str2, Bundle bundle) throws IOException {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        String str3 = XmlPullParser.NO_NAMESPACE.equals(this.aat) ? str : this.aat;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str3);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str3);
        }
        return aar.zzt(aar.zza(bundle, zzblw()));
    }
}
