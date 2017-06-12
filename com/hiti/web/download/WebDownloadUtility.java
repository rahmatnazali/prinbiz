package com.hiti.web.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.net.nntp.NNTPReply;

public class WebDownloadUtility {
    public static URL CreateURLPath(String strPath) {
        try {
            if (strPath.contains("http://")) {
                strPath = strPath.replace("http://", "https://");
            }
            return new URL(strPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpsURLConnection OpenConnection(URL url, int iTimeOut) {
        try {
            HttpsURLConnection httpURLConn = (HttpsURLConnection) url.openConnection();
            httpURLConn.setConnectTimeout(iTimeOut);
            httpURLConn.setReadTimeout(iTimeOut);
            return httpURLConn;
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean Connect(HttpsURLConnection httpURLConn) {
        try {
            httpURLConn.connect();
            switch (httpURLConn.getResponseCode()) {
                case NNTPReply.SERVER_READY_POSTING_ALLOWED /*200*/:
                    return true;
                default:
                    String strRet = httpURLConn.getResponseMessage();
                    byte[] bArr = new byte[new BufferedInputStream(httpURLConn.getErrorStream()).available()];
                    return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static BufferedInputStream GetBufferedInputStream(HttpsURLConnection httpURLConn) {
        try {
            return new BufferedInputStream(httpURLConn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean IsSuccess(WEB_DOWNLOAD_ERROR wde) {
        if (wde == WEB_DOWNLOAD_ERROR.NON) {
            return true;
        }
        return false;
    }
}
