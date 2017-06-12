package com.flurry.sdk;

import org.apache.commons.net.ftp.FTPClient;

public enum ip {
    GET("GET", 0),
    PUT("PUT", 1),
    POST("POST", 2);
    
    String f168d;
    int f169e;

    private ip(String str, int i) {
        this.f168d = str;
        this.f169e = i;
    }

    public static ip m72a(int i) {
        switch (i) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return GET;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return PUT;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return POST;
            default:
                return null;
        }
    }
}
