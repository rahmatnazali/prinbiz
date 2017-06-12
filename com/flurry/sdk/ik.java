package com.flurry.sdk;

import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.ftp.FTPClient;

public enum ik {
    COMPLETE(1),
    TIMEOUT(2),
    INVALID_RESPONSE(3),
    PENDING_COMPLETION(4);
    
    int f125e;

    private ik(int i) {
        this.f125e = i;
    }

    public static ik m19a(int i) {
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return COMPLETE;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return TIMEOUT;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return INVALID_RESPONSE;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return PENDING_COMPLETION;
            default:
                return null;
        }
    }
}
