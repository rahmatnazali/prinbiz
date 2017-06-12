package com.flurry.sdk;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.flurry.sdk.ju.C0153a;
import java.lang.ref.WeakReference;

public class jv {
    private static jv f303a;
    private static final String f304b;
    private Object f305c;

    /* renamed from: com.flurry.sdk.jv.1 */
    class C01541 implements ActivityLifecycleCallbacks {
        final /* synthetic */ jv f302a;

        C01541(jv jvVar) {
            this.f302a = jvVar;
        }

        public final void onActivityCreated(Activity activity, Bundle bundle) {
            kf.m182a(3, jv.f304b, "onActivityCreated for activity:" + activity);
            C01541.m135a(activity, C0153a.kCreated);
        }

        public final void onActivityStarted(Activity activity) {
            kf.m182a(3, jv.f304b, "onActivityStarted for activity:" + activity);
            C01541.m135a(activity, C0153a.kStarted);
        }

        public final void onActivityResumed(Activity activity) {
            kf.m182a(3, jv.f304b, "onActivityResumed for activity:" + activity);
            C01541.m135a(activity, C0153a.kResumed);
        }

        public final void onActivityPaused(Activity activity) {
            kf.m182a(3, jv.f304b, "onActivityPaused for activity:" + activity);
            C01541.m135a(activity, C0153a.kPaused);
        }

        public final void onActivityStopped(Activity activity) {
            kf.m182a(3, jv.f304b, "onActivityStopped for activity:" + activity);
            C01541.m135a(activity, C0153a.kStopped);
        }

        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            kf.m182a(3, jv.f304b, "onActivitySaveInstanceState for activity:" + activity);
            C01541.m135a(activity, C0153a.kSaveState);
        }

        public final void onActivityDestroyed(Activity activity) {
            kf.m182a(3, jv.f304b, "onActivityDestroyed for activity:" + activity);
            C01541.m135a(activity, C0153a.kDestroyed);
        }

        private static void m135a(Activity activity, C0153a c0153a) {
            ju juVar = new ju();
            juVar.f621a = new WeakReference(activity);
            juVar.f622b = c0153a;
            juVar.m155b();
        }
    }

    public static synchronized jv m136a() {
        jv jvVar;
        synchronized (jv.class) {
            if (f303a == null) {
                f303a = new jv();
            }
            jvVar = f303a;
        }
        return jvVar;
    }

    public static synchronized void m137b() {
        synchronized (jv.class) {
            if (f303a != null) {
                jv jvVar = f303a;
                if (VERSION.SDK_INT >= 14 && jvVar.f305c != null) {
                    Context context = jr.m120a().f284a;
                    if (context instanceof Application) {
                        ((Application) context).unregisterActivityLifecycleCallbacks((ActivityLifecycleCallbacks) jvVar.f305c);
                        jvVar.f305c = null;
                    }
                }
            }
            f303a = null;
        }
    }

    static {
        f304b = jv.class.getSimpleName();
    }

    private jv() {
        if (VERSION.SDK_INT >= 14 && this.f305c == null) {
            Context context = jr.m120a().f284a;
            if (context instanceof Application) {
                this.f305c = new C01541(this);
                ((Application) context).registerActivityLifecycleCallbacks((ActivityLifecycleCallbacks) this.f305c);
            }
        }
    }

    public final boolean m139c() {
        return this.f305c != null;
    }
}
