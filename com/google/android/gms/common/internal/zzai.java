package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.C0178R;

public class zzai {
    private final Resources zo;
    private final String zp;

    public zzai(Context context) {
        zzab.zzy(context);
        this.zo = context.getResources();
        this.zp = this.zo.getResourcePackageName(C0178R.string.common_google_play_services_unknown_issue);
    }

    public String getString(String str) {
        int identifier = this.zo.getIdentifier(str, "string", this.zp);
        return identifier == 0 ? null : this.zo.getString(identifier);
    }
}
