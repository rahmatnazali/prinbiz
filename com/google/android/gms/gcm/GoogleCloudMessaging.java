package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzc;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.xmlpull.v1.XmlPullParser;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    public static int Zm;
    public static int Zn;
    public static int Zo;
    static GoogleCloudMessaging Zp;
    private static final AtomicInteger Zs;
    private PendingIntent Zq;
    private Map<String, Handler> Zr;
    private final BlockingQueue<Intent> Zt;
    final Messenger Zu;
    private Context zzagf;

    /* renamed from: com.google.android.gms.gcm.GoogleCloudMessaging.1 */
    class C01911 extends Handler {
        final /* synthetic */ GoogleCloudMessaging Zv;

        C01911(GoogleCloudMessaging googleCloudMessaging, Looper looper) {
            this.Zv = googleCloudMessaging;
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message == null || !(message.obj instanceof Intent)) {
                Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
            }
            Intent intent = (Intent) message.obj;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                this.Zv.Zt.add(intent);
            } else if (!this.Zv.zzq(intent)) {
                intent.setPackage(this.Zv.zzagf.getPackageName());
                this.Zv.zzagf.sendBroadcast(intent);
            }
        }
    }

    static {
        Zm = 5000000;
        Zn = 6500000;
        Zo = 7000000;
        Zs = new AtomicInteger(1);
    }

    public GoogleCloudMessaging() {
        this.Zt = new LinkedBlockingQueue();
        this.Zr = Collections.synchronizedMap(new HashMap());
        this.Zu = new Messenger(new C01911(this, Looper.getMainLooper()));
    }

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (Zp == null) {
                Zp = new GoogleCloudMessaging();
                Zp.zzagf = context.getApplicationContext();
            }
            googleCloudMessaging = Zp;
        }
        return googleCloudMessaging;
    }

    static String zza(Intent intent, String str) throws IOException {
        if (intent == null) {
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        }
        String stringExtra = intent.getStringExtra(str);
        if (stringExtra != null) {
            return stringExtra;
        }
        stringExtra = intent.getStringExtra("error");
        if (stringExtra != null) {
            throw new IOException(stringExtra);
        }
        throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
    }

    private void zza(String str, String str2, long j, int i, Bundle bundle) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        String zzdd = zzdd(this.zzagf);
        if (zzdd == null) {
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        zzr(intent);
        intent.setPackage(zzdd);
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        intent.putExtra("google.delay", Integer.toString(i));
        intent.putExtra("google.from", zzkd(str));
        if (zzdd.contains(".gsf")) {
            Bundle bundle2 = new Bundle();
            for (String zzdd2 : bundle.keySet()) {
                Object obj = bundle.get(zzdd2);
                if (obj instanceof String) {
                    String str3 = "gcm.";
                    zzdd2 = String.valueOf(zzdd2);
                    bundle2.putString(zzdd2.length() != 0 ? str3.concat(zzdd2) : new String(str3), (String) obj);
                }
            }
            bundle2.putString("google.to", str);
            bundle2.putString("google.message_id", str2);
            InstanceID.getInstance(this.zzagf).zzc(INSTANCE_ID_SCOPE, "upstream", bundle2);
            return;
        }
        this.zzagf.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    private String zzblh() {
        String valueOf = String.valueOf("google.rpc");
        String valueOf2 = String.valueOf(String.valueOf(Zs.getAndIncrement()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String zzdd(Context context) {
        return zzc.zzdi(context);
    }

    public static int zzde(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String zzdd = zzdd(context);
        if (zzdd != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(zzdd, 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException e) {
            }
        }
        return -1;
    }

    private String zzkd(String str) {
        int indexOf = str.indexOf(64);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return InstanceID.getInstance(this.zzagf).zzbly().zzi(XmlPullParser.NO_NAMESPACE, str, INSTANCE_ID_SCOPE);
    }

    private boolean zzq(Intent intent) {
        Object stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.Zr.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    public void close() {
        Zp = null;
        zza.Zd = null;
        zzbli();
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized String register(String... strArr) throws IOException {
        String zzdd;
        zzdd = zzdd(this.zzagf);
        if (zzdd == null) {
            throw new IOException(ERROR_SERVICE_NOT_AVAILABLE);
        }
        String zze = zze(strArr);
        Bundle bundle = new Bundle();
        if (zzdd.contains(".gsf")) {
            bundle.putString("legacy.sender", zze);
            zzdd = InstanceID.getInstance(this.zzagf).getToken(zze, INSTANCE_ID_SCOPE, bundle);
        } else {
            bundle.putString("sender", zze);
            zzdd = zza(zzah(bundle), "registration_id");
        }
        return zzdd;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        zza(str, str2, j, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized void unregister() throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        }
        InstanceID.getInstance(this.zzagf).deleteInstanceID();
    }

    @Deprecated
    Intent zzah(Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException(ERROR_MAIN_THREAD);
        } else if (zzde(this.zzagf) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzdd(this.zzagf));
            zzr(intent);
            intent.putExtra("google.message_id", zzblh());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.Zu);
            this.zzagf.startService(intent);
            try {
                return (Intent) this.Zt.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    synchronized void zzbli() {
        if (this.Zq != null) {
            this.Zq.cancel();
            this.Zq = null;
        }
    }

    String zze(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder stringBuilder = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            stringBuilder.append(',').append(strArr[i]);
        }
        return stringBuilder.toString();
    }

    synchronized void zzr(Intent intent) {
        if (this.Zq == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.Zq = PendingIntent.getBroadcast(this.zzagf, 0, intent2, 0);
        }
        intent.putExtra("app", this.Zq);
    }
}
