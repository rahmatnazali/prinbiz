package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzh<TResult> aDt;

    public TaskCompletionSource() {
        this.aDt = new zzh();
    }

    @NonNull
    public Task<TResult> getTask() {
        return this.aDt;
    }

    public void setException(@NonNull Exception exception) {
        this.aDt.setException(exception);
    }

    public void setResult(TResult tResult) {
        this.aDt.setResult(tResult);
    }
}
