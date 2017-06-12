package com.flurry.sdk;

import android.app.Activity;
import java.lang.ref.WeakReference;

public final class ju extends jz {
    public WeakReference<Activity> f621a;
    public C0153a f622b;

    /* renamed from: com.flurry.sdk.ju.a */
    public enum C0153a {
        kCreated,
        kDestroyed,
        kPaused,
        kResumed,
        kStarted,
        kStopped,
        kSaveState
    }

    public ju() {
        super("com.flurry.android.sdk.ActivityLifecycleEvent");
        this.f621a = new WeakReference(null);
    }
}
