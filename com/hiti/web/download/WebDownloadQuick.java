package com.hiti.web.download;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.net.io.Util;
import org.xmlpull.v1.XmlPullParser;

public class WebDownloadQuick {
    public static WEB_DOWNLOAD_ERROR DownloadNotInBackground(String strDownloadPath, String strFilePath, int iTimeOut) {
        FileNotFoundException e;
        Log.i("DownloadNotInBackground", XmlPullParser.NO_NAMESPACE + strDownloadPath);
        try {
            OutputStream fos = new FileOutputStream(new File(strFilePath));
            OutputStream outputStream;
            try {
                outputStream = fos;
                return DownloadNotInBackground(strDownloadPath, fos, iTimeOut);
            } catch (FileNotFoundException e2) {
                e = e2;
                outputStream = fos;
                e.printStackTrace();
                return WEB_DOWNLOAD_ERROR.ERROR_SAVE_FILE;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            e.printStackTrace();
            return WEB_DOWNLOAD_ERROR.ERROR_SAVE_FILE;
        }
    }

    public static WEB_DOWNLOAD_ERROR DownloadNotInBackground(String strDownloadPath, OutputStream os, int iTimeOut) {
        URL url = WebDownloadUtility.CreateURLPath(strDownloadPath);
        if (url == null) {
            return WEB_DOWNLOAD_ERROR.ERROR_PATH;
        }
        HttpsURLConnection httpURLConn = WebDownloadUtility.OpenConnection(url, iTimeOut);
        if (httpURLConn == null) {
            return WEB_DOWNLOAD_ERROR.ERROR_OPEN_CONNECTION;
        }
        if (!WebDownloadUtility.Connect(httpURLConn)) {
            return WEB_DOWNLOAD_ERROR.ERROR_SOCKET_CONNECTTION;
        }
        BufferedInputStream bufferedInputStream = WebDownloadUtility.GetBufferedInputStream(httpURLConn);
        if (bufferedInputStream == null) {
            return WEB_DOWNLOAD_ERROR.ERROR_GET_FILE;
        }
        if (SaveDownloadFile(bufferedInputStream, os)) {
            return WEB_DOWNLOAD_ERROR.NON;
        }
        return WEB_DOWNLOAD_ERROR.ERROR_SAVE_FILE;
    }

    private static boolean SaveDownloadFile(BufferedInputStream bufferedInputStream, OutputStream os) {
        boolean boRet = false;
        if (os == null) {
            return false;
        }
        byte[] byteArray = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
        while (true) {
            try {
                int current = bufferedInputStream.read(byteArray);
                if (current == -1) {
                    break;
                }
                os.write(byteArray, 0, current);
            } catch (SocketException e) {
                Log.e("SaveDownloadFile", Log.getStackTraceString(e));
            } catch (IOException e2) {
                Log.e("SaveDownloadFile", Log.getStackTraceString(e2));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        bufferedInputStream.close();
        os.flush();
        os.close();
        boRet = true;
        return boRet;
    }
}
