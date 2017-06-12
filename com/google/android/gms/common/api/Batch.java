package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.internal.zzpo;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends zzpo<BatchResult> {
    private int rH;
    private boolean rI;
    private boolean rJ;
    private final PendingResult<?>[] rK;
    private final Object zzail;

    public static final class Builder {
        private GoogleApiClient gY;
        private List<PendingResult<?>> rM;

        public Builder(GoogleApiClient googleApiClient) {
            this.rM = new ArrayList();
            this.gY = googleApiClient;
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.rM.size());
            this.rM.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.gY, null);
        }
    }

    /* renamed from: com.google.android.gms.common.api.Batch.1 */
    class C06441 implements zza {
        final /* synthetic */ Batch rL;

        C06441(Batch batch) {
            this.rL = batch;
        }

        public void zzv(Status status) {
            synchronized (this.rL.zzail) {
                if (this.rL.isCanceled()) {
                    return;
                }
                if (status.isCanceled()) {
                    this.rL.rJ = true;
                } else if (!status.isSuccess()) {
                    this.rL.rI = true;
                }
                this.rL.rH = this.rL.rH - 1;
                if (this.rL.rH == 0) {
                    if (this.rL.rJ) {
                        super.cancel();
                    } else {
                        this.rL.zzc(new BatchResult(this.rL.rI ? new Status(13) : Status.sq, this.rL.rK));
                    }
                }
            }
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzail = new Object();
        this.rH = list.size();
        this.rK = new PendingResult[this.rH];
        if (list.isEmpty()) {
            zzc(new BatchResult(Status.sq, this.rK));
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            PendingResult pendingResult = (PendingResult) list.get(i);
            this.rK[i] = pendingResult;
            pendingResult.zza(new C06441(this));
        }
    }

    public void cancel() {
        super.cancel();
        for (PendingResult cancel : this.rK) {
            cancel.cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.rK);
    }

    public /* synthetic */ Result zzc(Status status) {
        return createFailedResult(status);
    }
}
