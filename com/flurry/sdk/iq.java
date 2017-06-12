package com.flurry.sdk;

import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.ftp.FTPClient;

public enum iq {
    INSTALL(1),
    SESSION_START(2),
    SESSION_END(3),
    APPLICATION_EVENT(4);
    
    int f175e;

    private iq(int i) {
        this.f175e = i;
    }

    public static iq m73a(int i) {
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return INSTALL;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return SESSION_START;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return SESSION_END;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return APPLICATION_EVENT;
            default:
                return null;
        }
    }
}
