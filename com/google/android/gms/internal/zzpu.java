package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class zzpu extends GoogleApiClient {
    private final UnsupportedOperationException tv;

    public zzpu(String str) {
        this.tv = new UnsupportedOperationException(str);
    }

    public ConnectionResult blockingConnect() {
        throw this.tv;
    }

    public ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        throw this.tv;
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw this.tv;
    }

    public void connect() {
        throw this.tv;
    }

    public void disconnect() {
        throw this.tv;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        throw this.tv;
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw this.tv;
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw this.tv;
    }

    public boolean isConnected() {
        throw this.tv;
    }

    public boolean isConnecting() {
        throw this.tv;
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.tv;
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.tv;
    }

    public void reconnect() {
        throw this.tv;
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.tv;
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.tv;
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw this.tv;
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        throw this.tv;
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        throw this.tv;
    }
}
