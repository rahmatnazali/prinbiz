package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.atomic.AtomicReference;

public class zzpm {

    public interface zzb<R> {
        void setResult(R r);

        void zzz(Status status);
    }

    public static abstract class zza<R extends Result, A extends com.google.android.gms.common.api.Api.zzb> extends zzpo<R> implements zzb<R> {
        private final Api<?> pN;
        private final zzc<A> sJ;
        private AtomicReference<zzb> sK;

        @Deprecated
        protected zza(zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super((GoogleApiClient) zzab.zzb((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
            this.sK = new AtomicReference();
            this.sJ = (zzc) zzab.zzy(com_google_android_gms_common_api_Api_zzc_A);
            this.pN = null;
        }

        protected zza(Api<?> api, GoogleApiClient googleApiClient) {
            super((GoogleApiClient) zzab.zzb((Object) googleApiClient, (Object) "GoogleApiClient must not be null"));
            this.sK = new AtomicReference();
            this.sJ = api.zzans();
            this.pN = api;
        }

        private void zza(RemoteException remoteException) {
            zzz(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        public /* synthetic */ void setResult(Object obj) {
            super.zzc((Result) obj);
        }

        protected abstract void zza(A a) throws RemoteException;

        public void zza(zzb com_google_android_gms_internal_zzqy_zzb) {
            this.sK.set(com_google_android_gms_internal_zzqy_zzb);
        }

        public final zzc<A> zzans() {
            return this.sJ;
        }

        public final Api<?> zzanz() {
            return this.pN;
        }

        public void zzaor() {
            setResultCallback(null);
        }

        protected void zzaos() {
            zzb com_google_android_gms_internal_zzqy_zzb = (zzb) this.sK.getAndSet(null);
            if (com_google_android_gms_internal_zzqy_zzb != null) {
                com_google_android_gms_internal_zzqy_zzb.zzh(this);
            }
        }

        public final void zzb(A a) throws DeadObjectException {
            try {
                zza((com.google.android.gms.common.api.Api.zzb) a);
            } catch (RemoteException e) {
                zza(e);
                throw e;
            } catch (RemoteException e2) {
                zza(e2);
            }
        }

        protected void zzb(R r) {
        }

        public final void zzz(Status status) {
            zzab.zzb(!status.isSuccess(), (Object) "Failed result must not be success");
            Result zzc = zzc(status);
            zzc(zzc);
            zzb(zzc);
        }
    }
}
