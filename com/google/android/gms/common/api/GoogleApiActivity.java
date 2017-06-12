package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.internal.zzqc;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.pop3.POP3;

public class GoogleApiActivity extends Activity implements OnCancelListener {
    protected int rV;

    public GoogleApiActivity() {
        this.rV = 0;
    }

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int i) {
        return zza(context, pendingIntent, i, true);
    }

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int i, boolean z) {
        return PendingIntent.getActivity(context, 0, zzb(context, pendingIntent, i, z), 134217728);
    }

    private void zza(int i, zzqc com_google_android_gms_internal_zzqc) {
        switch (i) {
            case POP3.DISCONNECTED_STATE /*-1*/:
                com_google_android_gms_internal_zzqc.zzaoo();
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                com_google_android_gms_internal_zzqc.zza(new ConnectionResult(13, null), getIntent().getIntExtra("failing_client_id", -1));
            default:
        }
    }

    private void zzaod() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e("GoogleApiActivity", "Activity started without extras");
            finish();
            return;
        }
        PendingIntent pendingIntent = (PendingIntent) extras.get("pending_intent");
        Integer num = (Integer) extras.get("error_code");
        if (pendingIntent == null && num == null) {
            Log.e("GoogleApiActivity", "Activity started without resolution");
            finish();
        } else if (pendingIntent != null) {
            try {
                startIntentSenderForResult(pendingIntent.getIntentSender(), 1, null, 0, 0, 0);
                this.rV = 1;
            } catch (Throwable e) {
                Log.e("GoogleApiActivity", "Failed to launch pendingIntent", e);
                finish();
            }
        } else {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, num.intValue(), 2, this);
            this.rV = 1;
        }
    }

    public static Intent zzb(Context context, PendingIntent pendingIntent, int i, boolean z) {
        Intent intent = new Intent(context, GoogleApiActivity.class);
        intent.putExtra("pending_intent", pendingIntent);
        intent.putExtra("failing_client_id", i);
        intent.putExtra("notify_manager", z);
        return intent;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            boolean booleanExtra = getIntent().getBooleanExtra("notify_manager", true);
            this.rV = 0;
            zzqc zzaqd = zzqc.zzaqd();
            setResultCode(i2);
            if (booleanExtra) {
                zza(i2, zzaqd);
            }
        } else if (i == 2) {
            this.rV = 0;
            setResultCode(i2);
        }
        finish();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.rV = 0;
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.rV = bundle.getInt("resolution");
        }
        if (this.rV != 1) {
            zzaod();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.rV);
        super.onSaveInstanceState(bundle);
    }

    protected void setResultCode(int i) {
        setResult(i);
    }
}
