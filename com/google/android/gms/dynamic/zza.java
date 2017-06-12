package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.zze;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T Ks;
    private Bundle Kt;
    private LinkedList<zza> Ku;
    private final zzf<T> Kv;

    /* renamed from: com.google.android.gms.dynamic.zza.5 */
    class C01835 implements OnClickListener {
        final /* synthetic */ Context zzala;
        final /* synthetic */ int zzbjv;

        C01835(Context context, int i) {
            this.zzala = context;
            this.zzbjv = i;
        }

        public void onClick(View view) {
            this.zzala.startActivity(GooglePlayServicesUtil.zzfd(this.zzbjv));
        }
    }

    private interface zza {
        int getState();

        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    /* renamed from: com.google.android.gms.dynamic.zza.1 */
    class C06601 implements zzf<T> {
        final /* synthetic */ zza Kw;

        C06601(zza com_google_android_gms_dynamic_zza) {
            this.Kw = com_google_android_gms_dynamic_zza;
        }

        public void zza(T t) {
            this.Kw.Ks = t;
            Iterator it = this.Kw.Ku.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzb(this.Kw.Ks);
            }
            this.Kw.Ku.clear();
            this.Kw.Kt = null;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.2 */
    class C06612 implements zza {
        final /* synthetic */ zza Kw;
        final /* synthetic */ Bundle Kx;
        final /* synthetic */ Bundle Ky;
        final /* synthetic */ Activity val$activity;

        C06612(zza com_google_android_gms_dynamic_zza, Activity activity, Bundle bundle, Bundle bundle2) {
            this.Kw = com_google_android_gms_dynamic_zza;
            this.val$activity = activity;
            this.Kx = bundle;
            this.Ky = bundle2;
        }

        public int getState() {
            return 0;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.Kw.Ks.onInflate(this.val$activity, this.Kx, this.Ky);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.3 */
    class C06623 implements zza {
        final /* synthetic */ zza Kw;
        final /* synthetic */ Bundle Ky;

        C06623(zza com_google_android_gms_dynamic_zza, Bundle bundle) {
            this.Kw = com_google_android_gms_dynamic_zza;
            this.Ky = bundle;
        }

        public int getState() {
            return 1;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.Kw.Ks.onCreate(this.Ky);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.4 */
    class C06634 implements zza {
        final /* synthetic */ LayoutInflater KA;
        final /* synthetic */ ViewGroup KB;
        final /* synthetic */ zza Kw;
        final /* synthetic */ Bundle Ky;
        final /* synthetic */ FrameLayout Kz;

        C06634(zza com_google_android_gms_dynamic_zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.Kw = com_google_android_gms_dynamic_zza;
            this.Kz = frameLayout;
            this.KA = layoutInflater;
            this.KB = viewGroup;
            this.Ky = bundle;
        }

        public int getState() {
            return 2;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.Kz.removeAllViews();
            this.Kz.addView(this.Kw.Ks.onCreateView(this.KA, this.KB, this.Ky));
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.6 */
    class C06646 implements zza {
        final /* synthetic */ zza Kw;

        C06646(zza com_google_android_gms_dynamic_zza) {
            this.Kw = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 4;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.Kw.Ks.onStart();
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.7 */
    class C06657 implements zza {
        final /* synthetic */ zza Kw;

        C06657(zza com_google_android_gms_dynamic_zza) {
            this.Kw = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 5;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.Kw.Ks.onResume();
        }
    }

    public zza() {
        this.Kv = new C06601(this);
    }

    private void zza(Bundle bundle, zza com_google_android_gms_dynamic_zza_zza) {
        if (this.Ks != null) {
            com_google_android_gms_dynamic_zza_zza.zzb(this.Ks);
            return;
        }
        if (this.Ku == null) {
            this.Ku = new LinkedList();
        }
        this.Ku.add(com_google_android_gms_dynamic_zza_zza);
        if (bundle != null) {
            if (this.Kt == null) {
                this.Kt = (Bundle) bundle.clone();
            } else {
                this.Kt.putAll(bundle);
            }
        }
        zza(this.Kv);
    }

    public static void zzb(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        CharSequence zzc = zzh.zzc(context, isGooglePlayServicesAvailable, zze.zzbv(context));
        CharSequence zzh = zzh.zzh(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzc);
        linearLayout.addView(textView);
        if (zzh != null) {
            View button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzh);
            linearLayout.addView(button);
            button.setOnClickListener(new C01835(context, isGooglePlayServicesAvailable));
        }
    }

    private void zznd(int i) {
        while (!this.Ku.isEmpty() && ((zza) this.Ku.getLast()).getState() >= i) {
            this.Ku.removeLast();
        }
    }

    public void onCreate(Bundle bundle) {
        zza(bundle, new C06623(this, bundle));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zza(bundle, new C06634(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.Ks == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.Ks != null) {
            this.Ks.onDestroy();
        } else {
            zznd(1);
        }
    }

    public void onDestroyView() {
        if (this.Ks != null) {
            this.Ks.onDestroyView();
        } else {
            zznd(2);
        }
    }

    public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        zza(bundle2, new C06612(this, activity, bundle, bundle2));
    }

    public void onLowMemory() {
        if (this.Ks != null) {
            this.Ks.onLowMemory();
        }
    }

    public void onPause() {
        if (this.Ks != null) {
            this.Ks.onPause();
        } else {
            zznd(5);
        }
    }

    public void onResume() {
        zza(null, new C06657(this));
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.Ks != null) {
            this.Ks.onSaveInstanceState(bundle);
        } else if (this.Kt != null) {
            bundle.putAll(this.Kt);
        }
    }

    public void onStart() {
        zza(null, new C06646(this));
    }

    public void onStop() {
        if (this.Ks != null) {
            this.Ks.onStop();
        } else {
            zznd(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> com_google_android_gms_dynamic_zzf_T);

    public T zzbbt() {
        return this.Ks;
    }
}
