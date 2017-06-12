package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

@SuppressLint({"NewApi"})
public final class zzb extends zza {
    private Fragment KC;

    private zzb(Fragment fragment) {
        this.KC = fragment;
    }

    public static zzb zza(Fragment fragment) {
        return fragment != null ? new zzb(fragment) : null;
    }

    public Bundle getArguments() {
        return this.KC.getArguments();
    }

    public int getId() {
        return this.KC.getId();
    }

    public boolean getRetainInstance() {
        return this.KC.getRetainInstance();
    }

    public String getTag() {
        return this.KC.getTag();
    }

    public int getTargetRequestCode() {
        return this.KC.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() {
        return this.KC.getUserVisibleHint();
    }

    public zzd getView() {
        return zze.zzac(this.KC.getView());
    }

    public boolean isAdded() {
        return this.KC.isAdded();
    }

    public boolean isDetached() {
        return this.KC.isDetached();
    }

    public boolean isHidden() {
        return this.KC.isHidden();
    }

    public boolean isInLayout() {
        return this.KC.isInLayout();
    }

    public boolean isRemoving() {
        return this.KC.isRemoving();
    }

    public boolean isResumed() {
        return this.KC.isResumed();
    }

    public boolean isVisible() {
        return this.KC.isVisible();
    }

    public void setHasOptionsMenu(boolean z) {
        this.KC.setHasOptionsMenu(z);
    }

    public void setMenuVisibility(boolean z) {
        this.KC.setMenuVisibility(z);
    }

    public void setRetainInstance(boolean z) {
        this.KC.setRetainInstance(z);
    }

    public void setUserVisibleHint(boolean z) {
        this.KC.setUserVisibleHint(z);
    }

    public void startActivity(Intent intent) {
        this.KC.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        this.KC.startActivityForResult(intent, i);
    }

    public void zzab(zzd com_google_android_gms_dynamic_zzd) {
        this.KC.registerForContextMenu((View) zze.zzad(com_google_android_gms_dynamic_zzd));
    }

    public void zzac(zzd com_google_android_gms_dynamic_zzd) {
        this.KC.unregisterForContextMenu((View) zze.zzad(com_google_android_gms_dynamic_zzd));
    }

    public zzd zzbbu() {
        return zze.zzac(this.KC.getActivity());
    }

    public zzc zzbbv() {
        return zza(this.KC.getParentFragment());
    }

    public zzd zzbbw() {
        return zze.zzac(this.KC.getResources());
    }

    public zzc zzbbx() {
        return zza(this.KC.getTargetFragment());
    }
}
