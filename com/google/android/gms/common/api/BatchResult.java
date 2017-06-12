package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
    private final Status bY;
    private final PendingResult<?>[] rK;

    BatchResult(Status status, PendingResult<?>[] pendingResultArr) {
        this.bY = status;
        this.rK = pendingResultArr;
    }

    public Status getStatus() {
        return this.bY;
    }

    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        zzab.zzb(batchResultToken.mId < this.rK.length, (Object) "The result token does not belong to this batch");
        return this.rK[batchResultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
