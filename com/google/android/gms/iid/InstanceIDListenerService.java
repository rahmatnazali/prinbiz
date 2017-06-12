package com.google.android.gms.iid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class InstanceIDListenerService extends Service {
    static String ACTION;
    private static String Zh;
    private static String aay;
    private static String aaz;
    int aaA;
    int aaB;
    MessengerCompat aaw;
    BroadcastReceiver aax;

    /* renamed from: com.google.android.gms.iid.InstanceIDListenerService.1 */
    class C01951 extends Handler {
        final /* synthetic */ InstanceIDListenerService aaC;

        C01951(InstanceIDListenerService instanceIDListenerService, Looper looper) {
            this.aaC = instanceIDListenerService;
            super(looper);
        }

        public void handleMessage(Message message) {
            this.aaC.zza(message, MessengerCompat.zzc(message));
        }
    }

    /* renamed from: com.google.android.gms.iid.InstanceIDListenerService.2 */
    class C01962 extends BroadcastReceiver {
        final /* synthetic */ InstanceIDListenerService aaC;

        C01962(InstanceIDListenerService instanceIDListenerService) {
            this.aaC = instanceIDListenerService;
        }

        public void onReceive(Context context, Intent intent) {
            if (Log.isLoggable("InstanceID", 3)) {
                intent.getStringExtra("registration_id");
                String valueOf = String.valueOf(intent.getExtras());
                Log.d("InstanceID", new StringBuilder(String.valueOf(valueOf).length() + 46).append("Received GSF callback using dynamic receiver: ").append(valueOf).toString());
            }
            this.aaC.zzn(intent);
            this.aaC.stop();
        }
    }

    static {
        ACTION = "action";
        aay = "google.com/iid";
        aaz = "CMD";
        Zh = "gcm.googleapis.com/refresh";
    }

    public InstanceIDListenerService() {
        this.aaw = new MessengerCompat(new C01951(this, Looper.getMainLooper()));
        this.aax = new C01962(this);
    }

    static void zza(Context context, zzd com_google_android_gms_iid_zzd) {
        com_google_android_gms_iid_zzd.zzbmd();
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.putExtra(aaz, "RST");
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    private void zza(Message message, int i) {
        zzc.zzdi(this);
        getPackageManager();
        if (i == zzc.aaI || i == zzc.aaH) {
            zzn((Intent) message.obj);
            return;
        }
        int i2 = zzc.aaH;
        Log.w("InstanceID", "Message from unexpected caller " + i + " mine=" + i2 + " appid=" + zzc.aaI);
    }

    static void zzdh(Context context) {
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.setPackage(context.getPackageName());
        intent.putExtra(aaz, "SYNC");
        context.startService(intent);
    }

    public IBinder onBind(Intent intent) {
        return (intent == null || !"com.google.android.gms.iid.InstanceID".equals(intent.getAction())) ? null : this.aaw.getBinder();
    }

    public void onCreate() {
        IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
        intentFilter.addCategory(getPackageName());
        registerReceiver(this.aax, intentFilter, "com.google.android.c2dm.permission.RECEIVE", null);
    }

    public void onDestroy() {
        unregisterReceiver(this.aax);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        zzsu(i2);
        if (intent == null) {
            stop();
            return 2;
        }
        try {
            if ("com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
                if (VERSION.SDK_INT <= 18) {
                    Intent intent2 = (Intent) intent.getParcelableExtra("GSF");
                    if (intent2 != null) {
                        startService(intent2);
                        return 1;
                    }
                }
                zzn(intent);
            }
            stop();
            if (intent.getStringExtra("from") != null) {
                WakefulBroadcastReceiver.completeWakefulIntent(intent);
            }
            return 2;
        } finally {
            stop();
        }
    }

    public void onTokenRefresh() {
    }

    void stop() {
        synchronized (this) {
            this.aaA--;
            if (this.aaA == 0) {
                stopSelf(this.aaB);
            }
            if (Log.isLoggable("InstanceID", 3)) {
                int i = this.aaA;
                Log.d("InstanceID", "Stop " + i + " " + this.aaB);
            }
        }
    }

    public void zzbx(boolean z) {
        onTokenRefresh();
    }

    public void zzn(Intent intent) {
        InstanceID instance;
        String stringExtra = intent.getStringExtra("subtype");
        if (stringExtra == null) {
            instance = InstanceID.getInstance(this);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("subtype", stringExtra);
            instance = InstanceID.zza(this, bundle);
        }
        String stringExtra2 = intent.getStringExtra(aaz);
        if (intent.getStringExtra("error") == null && intent.getStringExtra("registration_id") == null) {
            if (Log.isLoggable("InstanceID", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                Log.d("InstanceID", new StringBuilder(((String.valueOf(stringExtra).length() + 18) + String.valueOf(stringExtra2).length()) + String.valueOf(valueOf).length()).append("Service command ").append(stringExtra).append(" ").append(stringExtra2).append(" ").append(valueOf).toString());
            }
            if (intent.getStringExtra("unregistered") != null) {
                zzd zzbly = instance.zzbly();
                if (stringExtra == null) {
                    stringExtra = XmlPullParser.NO_NAMESPACE;
                }
                zzbly.zzkj(stringExtra);
                instance.zzblz().zzv(intent);
                return;
            } else if (Zh.equals(intent.getStringExtra("from"))) {
                instance.zzbly().zzkj(stringExtra);
                zzbx(false);
                return;
            } else if ("RST".equals(stringExtra2)) {
                instance.zzblx();
                zzbx(true);
                return;
            } else if ("RST_FULL".equals(stringExtra2)) {
                if (!instance.zzbly().isEmpty()) {
                    instance.zzbly().zzbmd();
                    zzbx(true);
                    return;
                }
                return;
            } else if ("SYNC".equals(stringExtra2)) {
                instance.zzbly().zzkj(stringExtra);
                zzbx(false);
                return;
            } else if (!"PING".equals(stringExtra2)) {
                return;
            } else {
                return;
            }
        }
        if (Log.isLoggable("InstanceID", 3)) {
            stringExtra2 = "InstanceID";
            String str = "Register result in service ";
            stringExtra = String.valueOf(stringExtra);
            Log.d(stringExtra2, stringExtra.length() != 0 ? str.concat(stringExtra) : new String(str));
        }
        instance.zzblz().zzv(intent);
    }

    void zzsu(int i) {
        synchronized (this) {
            this.aaA++;
            if (i > this.aaB) {
                this.aaB = i;
            }
        }
    }
}
