package com.google.android.gms.iid;

import android.annotation.TargetApi;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public class MessengerCompat implements Parcelable {
    public static final Creator<MessengerCompat> CREATOR;
    Messenger aaD;
    zzb aaE;

    /* renamed from: com.google.android.gms.iid.MessengerCompat.1 */
    class C01971 implements Creator<MessengerCompat> {
        C01971() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzmo(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzsv(i);
        }

        public MessengerCompat zzmo(Parcel parcel) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            return readStrongBinder != null ? new MessengerCompat(readStrongBinder) : null;
        }

        public MessengerCompat[] zzsv(int i) {
            return new MessengerCompat[i];
        }
    }

    private final class zza extends com.google.android.gms.iid.zzb.zza {
        final /* synthetic */ MessengerCompat aaF;
        Handler handler;

        zza(MessengerCompat messengerCompat, Handler handler) {
            this.aaF = messengerCompat;
            this.handler = handler;
        }

        public void send(Message message) throws RemoteException {
            message.arg2 = Binder.getCallingUid();
            this.handler.dispatchMessage(message);
        }
    }

    static {
        CREATOR = new C01971();
    }

    public MessengerCompat(Handler handler) {
        if (VERSION.SDK_INT >= 21) {
            this.aaD = new Messenger(handler);
        } else {
            this.aaE = new zza(this, handler);
        }
    }

    public MessengerCompat(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.aaD = new Messenger(iBinder);
        } else {
            this.aaE = com.google.android.gms.iid.zzb.zza.zzgq(iBinder);
        }
    }

    public static int zzc(Message message) {
        return VERSION.SDK_INT >= 21 ? zzd(message) : message.arg2;
    }

    @TargetApi(21)
    private static int zzd(Message message) {
        return message.sendingUid;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                z = getBinder().equals(((MessengerCompat) obj).getBinder());
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public IBinder getBinder() {
        return this.aaD != null ? this.aaD.getBinder() : this.aaE.asBinder();
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public void send(Message message) throws RemoteException {
        if (this.aaD != null) {
            this.aaD.send(message);
        } else {
            this.aaE.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.aaD != null) {
            parcel.writeStrongBinder(this.aaD.getBinder());
        } else {
            parcel.writeStrongBinder(this.aaE.asBinder());
        }
    }
}
