package com.flurry.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.flurry.sdk.hl;
import com.flurry.sdk.kf;
import com.flurry.sdk.lr;

public final class FlurryInstallReceiver extends BroadcastReceiver {
    static final String f19a;

    static {
        f19a = FlurryInstallReceiver.class.getSimpleName();
    }

    public final void onReceive(Context context, Intent intent) {
        kf.m182a(4, f19a, "Received an Install nofication of " + intent.getAction());
        String string = intent.getExtras().getString("referrer");
        kf.m182a(4, f19a, "Received an Install referrer of " + string);
        if (string == null || !"com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
            kf.m182a(5, f19a, "referrer is null");
            return;
        }
        if (!string.contains("=")) {
            kf.m182a(4, f19a, "referrer is before decoding: " + string);
            string = lr.m320d(string);
            kf.m182a(4, f19a, "referrer is: " + string);
        }
        new hl(context).m14a(string);
    }
}
