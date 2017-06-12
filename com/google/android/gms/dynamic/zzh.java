package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

public final class zzh extends zza {
    private Fragment KF;

    private zzh(Fragment fragment) {
        this.KF = fragment;
    }

    public static zzh zza(Fragment fragment) {
        return fragment != null ? new zzh(fragment) : null;
    }

    public Bundle getArguments() {
        return this.KF.getArguments();
    }

    public int getId() {
        return this.KF.getId();
    }

    public boolean getRetainInstance() {
        return this.KF.getRetainInstance();
    }

    public String getTag() {
        return this.KF.getTag();
    }

    public int getTargetRequestCode() {
        return this.KF.getTargetRequestCode();
    }

    public boolean getUserVisibleHint() {
        return this.KF.getUserVisibleHint();
    }

    public zzd getView() {
        return zze.zzac(this.KF.getView());
    }

    public boolean isAdded() {
        return this.KF.isAdded();
    }

    public boolean isDetached() {
        return this.KF.isDetached();
    }

    public boolean isHidden() {
        return this.KF.isHidden();
    }

    public boolean isInLayout() {
        return this.KF.isInLayout();
    }

    public boolean isRemoving() {
        return this.KF.isRemoving();
    }

    public boolean isResumed() {
        return this.KF.isResumed();
    }

    public boolean isVisible() {
        return this.KF.isVisible();
    }

    public void setHasOptionsMenu(boolean z) {
        this.KF.setHasOptionsMenu(z);
    }

    public void setMenuVisibility(boolean z) {
        this.KF.setMenuVisibility(z);
    }

    public void setRetainInstance(boolean z) {
        this.KF.setRetainInstance(z);
    }

    public void setUserVisibleHint(boolean z) {
        this.KF.setUserVisibleHint(z);
    }

    public void startActivity(Intent intent) {
        this.KF.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int i) {
        this.KF.startActivityForResult(intent, i);
    }

    public void zzab(zzd com_google_android_gms_dynamic_zzd) {
        this.KF.registerForContextMenu((View) zze.zzad(com_google_android_gms_dynamic_zzd));
    }

    public void zzac(zzd com_google_android_gms_dynamic_zzd) {
        this.KF.unregisterForContextMenu((View) zze.zzad(com_google_android_gms_dynamic_zzd));
    }

    public zzd zzbbu() {
        return zze.zzac(this.KF.getActivity());
    }

    public zzc zzbbv() {
        return zza(this.KF.getParentFragment());
    }

    public zzd zzbbw() {
        return zze.zzac(this.KF.getResources());
    }

    public zzc zzbbx() {
        return zza(this.KF.getTargetFragment());
    }
}
