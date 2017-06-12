package com.hiti.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class GCMAppBroadcastReceiver extends BroadcastReceiver {
    private Context m_Context;

    public GCMAppBroadcastReceiver(Context context) {
        this.m_Context = null;
        this.m_Context = context;
    }

    public void onReceive(Context context, Intent intent) {
        Log.e("GCMAPPBroadcastReceiver", intent.getExtras().getString(GCMInfo.EXTRA_MESSAGE));
    }

    public void Register() {
        this.m_Context.registerReceiver(this, new IntentFilter(GCMInfo.DISPLAY_MESSAGE_ACTION));
    }

    public void Unregister() {
        this.m_Context.unregisterReceiver(this);
    }
}
