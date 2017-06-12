package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.media.TransportMediator;
import android.util.Log;
import com.google.android.gms.common.stats.zzc.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzt;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.net.io.Util;

public class zzb {
    private static zzb Aj;
    private static Integer Ap;
    private static final Object yL;
    private final List<String> Ak;
    private final List<String> Al;
    private final List<String> Am;
    private final List<String> An;
    private zze Ao;
    private zze Aq;

    static {
        yL = new Object();
    }

    private zzb() {
        if (getLogLevel() == zzd.LOG_LEVEL_OFF) {
            this.Ak = Collections.EMPTY_LIST;
            this.Al = Collections.EMPTY_LIST;
            this.Am = Collections.EMPTY_LIST;
            this.An = Collections.EMPTY_LIST;
            return;
        }
        String str = (String) zza.Au.get();
        this.Ak = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.Av.get();
        this.Al = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.Aw.get();
        this.Am = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        str = (String) zza.Ax.get();
        this.An = str == null ? Collections.EMPTY_LIST : Arrays.asList(str.split(","));
        this.Ao = new zze(Util.DEFAULT_COPY_BUFFER_SIZE, ((Long) zza.Ay.get()).longValue());
        this.Aq = new zze(Util.DEFAULT_COPY_BUFFER_SIZE, ((Long) zza.Ay.get()).longValue());
    }

    private static int getLogLevel() {
        if (Ap == null) {
            try {
                Ap = Integer.valueOf(zzd.zzabc() ? ((Integer) zza.At.get()).intValue() : zzd.LOG_LEVEL_OFF);
            } catch (SecurityException e) {
                Ap = Integer.valueOf(zzd.LOG_LEVEL_OFF);
            }
        }
        return Ap.intValue();
    }

    private static String zza(StackTraceElement[] stackTraceElementArr, int i) {
        if (i + 4 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = stackTraceElementArr[i + 4];
        String valueOf = String.valueOf(stackTraceElement.getClassName());
        String valueOf2 = String.valueOf(stackTraceElement.getMethodName());
        return new StringBuilder((String.valueOf(valueOf).length() + 13) + String.valueOf(valueOf2).length()).append(valueOf).append(".").append(valueOf2).append(":").append(stackTraceElement.getLineNumber()).toString();
    }

    private void zza(Context context, String str, int i, String str2, String str3, String str4, String str5) {
        Parcelable connectionEvent;
        long currentTimeMillis = System.currentTimeMillis();
        String str6 = null;
        if (!((getLogLevel() & zzd.AD) == 0 || i == 13)) {
            str6 = zzm(3, 5);
        }
        long j = 0;
        if ((getLogLevel() & zzd.AF) != 0) {
            j = Debug.getNativeHeapAllocatedSize();
        }
        if (i == 1 || i == 4 || i == 14) {
            connectionEvent = new ConnectionEvent(currentTimeMillis, i, null, null, null, null, str6, str, SystemClock.elapsedRealtime(), j);
        } else {
            connectionEvent = new ConnectionEvent(currentTimeMillis, i, str2, str3, str4, str5, str6, str, SystemClock.elapsedRealtime(), j);
        }
        context.startService(new Intent().setComponent(zzd.Az).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", connectionEvent));
    }

    private void zza(Context context, String str, String str2, Intent intent, int i) {
        String str3 = null;
        if (zzauy() && this.Ao != null) {
            String str4;
            String str5;
            if (i != 4 && i != 1) {
                ServiceInfo zzd = zzd(context, intent);
                if (zzd == null) {
                    Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", new Object[]{str2, intent.toUri(0)}));
                    return;
                }
                str4 = zzd.processName;
                str5 = zzd.name;
                str3 = zzt.zzavz();
                if (zzb(str3, str2, str4, str5)) {
                    this.Ao.zzhx(str);
                } else {
                    return;
                }
            } else if (this.Ao.zzhy(str)) {
                str5 = null;
                str4 = null;
            } else {
                return;
            }
            zza(context, str, i, str3, str2, str4, str5);
        }
    }

    public static zzb zzaux() {
        synchronized (yL) {
            if (Aj == null) {
                Aj = new zzb();
            }
        }
        return Aj;
    }

    private boolean zzauy() {
        return false;
    }

    private String zzb(ServiceConnection serviceConnection) {
        return String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(serviceConnection)));
    }

    private boolean zzb(String str, String str2, String str3, String str4) {
        return (this.Ak.contains(str) || this.Al.contains(str2) || this.Am.contains(str3) || this.An.contains(str4) || (str3.equals(str) && (getLogLevel() & zzd.AE) != 0)) ? false : true;
    }

    private boolean zzc(Context context, Intent intent) {
        ComponentName component = intent.getComponent();
        return component == null ? false : zzd.zzq(context, component.getPackageName());
    }

    private static ServiceInfo zzd(Context context, Intent intent) {
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, TransportMediator.FLAG_KEY_MEDIA_NEXT);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), zzm(3, 20)}));
            return null;
        } else if (queryIntentServices.size() <= 1) {
            return ((ResolveInfo) queryIntentServices.get(0)).serviceInfo;
        } else {
            Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", new Object[]{intent.toUri(0), zzm(3, 20)}));
            for (ResolveInfo resolveInfo : queryIntentServices) {
                Log.w("ConnectionTracker", resolveInfo.serviceInfo.name);
            }
            return null;
        }
    }

    private static String zzm(int i, int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        int i3 = i2 + i;
        while (i < i3) {
            stringBuffer.append(zza(stackTrace, i)).append(" ");
            i++;
        }
        return stringBuffer.toString();
    }

    @SuppressLint({"UntrackedBindService"})
    public void zza(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
        zza(context, zzb(serviceConnection), null, null, 1);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String str, Intent intent) {
        zza(context, zzb(serviceConnection), str, intent, 3);
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int i) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i);
    }

    @SuppressLint({"UntrackedBindService"})
    public boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i) {
        if (zzc(context, intent)) {
            Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        boolean bindService = context.bindService(intent, serviceConnection, i);
        if (bindService) {
            zza(context, zzb(serviceConnection), str, intent, 2);
        }
        return bindService;
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
        zza(context, zzb(serviceConnection), null, null, 4);
    }
}
