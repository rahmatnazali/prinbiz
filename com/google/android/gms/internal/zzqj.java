package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzqj {
    protected final zzqk vm;

    protected zzqj(zzqk com_google_android_gms_internal_zzqk) {
        this.vm = com_google_android_gms_internal_zzqk;
    }

    protected static zzqk zzc(zzqi com_google_android_gms_internal_zzqi) {
        return com_google_android_gms_internal_zzqi.zzaqq() ? zzqv.zza(com_google_android_gms_internal_zzqi.zzaqs()) : zzql.zzt(com_google_android_gms_internal_zzqi.zzaqr());
    }

    protected static zzqk zzs(Activity activity) {
        return zzc(new zzqi(activity));
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public Activity getActivity() {
        return this.vm.zzaqt();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @MainThread
    public void onStop() {
    }
}
