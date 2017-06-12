package com.hiti.web.download;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public abstract class WebDownload extends AsyncTask<String, Integer, Object> {
    private int TIMEOUT;
    public Context m_Context;
    private WEB_DOWNLOAD_ERROR m_LastError;
    private OutputStream m_OS;
    private int m_TimeOut;
    private boolean m_boDownloadCancel;
    private boolean m_boDownloadFail;
    private int m_iProgress;
    private String m_strDownloadPath;

    public abstract void DeleteDownload();

    public abstract void DownloadCancel();

    public abstract void DownloadFail(WEB_DOWNLOAD_ERROR web_download_error);

    public abstract void DownloadProgress(int i);

    public abstract void DownloadSuccess();

    public abstract OutputStream GetOutputStream();

    public abstract void SaveFileSuccess();

    public WebDownload(Context context) {
        this.TIMEOUT = 30000;
        this.m_TimeOut = 0;
        this.m_boDownloadFail = false;
        this.m_boDownloadCancel = false;
        this.m_strDownloadPath = null;
        this.m_LastError = WEB_DOWNLOAD_ERROR.NON;
        this.m_Context = null;
        this.m_iProgress = 0;
        this.m_OS = null;
        this.m_TimeOut = this.TIMEOUT;
        this.m_Context = context;
        GetResourceID(context);
        this.m_OS = GetOutputStream();
    }

    private void GetResourceID(Context context) {
    }

    protected Object doInBackground(String... arg0) {
        this.m_strDownloadPath = arg0[0];
        URL url = WebDownloadUtility.CreateURLPath(this.m_strDownloadPath);
        if (url == null) {
            SetLastError(WEB_DOWNLOAD_ERROR.ERROR_PATH);
        } else {
            HttpsURLConnection httpURLConn = WebDownloadUtility.OpenConnection(url, GetTimeOut());
            if (httpURLConn == null) {
                SetLastError(WEB_DOWNLOAD_ERROR.ERROR_OPEN_CONNECTION);
            } else if (WebDownloadUtility.Connect(httpURLConn)) {
                BufferedInputStream bufferedInputStream = WebDownloadUtility.GetBufferedInputStream(httpURLConn);
                if (bufferedInputStream == null) {
                    SetLastError(WEB_DOWNLOAD_ERROR.ERROR_GET_FILE);
                } else if (this.m_boDownloadCancel) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (SaveDownloadFile(bufferedInputStream, this.m_OS)) {
                    SaveFileSuccess();
                    if (this.m_boDownloadCancel || this.m_boDownloadFail) {
                        DeleteDownload();
                    }
                } else {
                    SetLastError(WEB_DOWNLOAD_ERROR.ERROR_SAVE_FILE);
                }
            } else {
                SetLastError(WEB_DOWNLOAD_ERROR.ERROR_SOCKET_CONNECTTION);
            }
        }
        return null;
    }

    public void onProgressUpdate(Integer... params) {
        DownloadProgress(params[0].intValue());
    }

    protected void onPostExecute(Object result) {
        if (this.m_boDownloadCancel) {
            DownloadCancel();
        } else if (this.m_boDownloadFail) {
            DownloadFail(GetLastError());
        } else {
            DownloadSuccess();
        }
    }

    public void SetProgress(float fProgress) {
        if (fProgress < 1.0f) {
            this.m_iProgress = 1;
        } else {
            this.m_iProgress = (int) fProgress;
        }
    }

    public int GetProgress() {
        return this.m_iProgress;
    }

    public void SetTimeOut(int iTimeout) {
        this.m_TimeOut = iTimeout;
    }

    public int GetTimeOut() {
        return this.m_TimeOut;
    }

    public void SetDownloadCancel(boolean boDownloadCancel) {
        this.m_boDownloadCancel = boDownloadCancel;
    }

    public boolean GetboDownloadCancel() {
        return this.m_boDownloadCancel;
    }

    public void SetLastError(WEB_DOWNLOAD_ERROR lastError) {
        this.m_boDownloadFail = true;
        this.m_LastError = lastError;
    }

    public WEB_DOWNLOAD_ERROR GetLastError() {
        return this.m_LastError;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean SaveDownloadFile(java.io.BufferedInputStream r10, java.io.OutputStream r11) {
        /*
        r9 = this;
        r6 = 0;
        r0 = 0;
        r4 = 0;
        r5 = 0;
        if (r11 != 0) goto L_0x0007;
    L_0x0006:
        return r6;
    L_0x0007:
        r6 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1 = new byte[r6];
        r2 = 0;
    L_0x000c:
        r2 = r10.read(r1);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r6 = -1;
        if (r2 == r6) goto L_0x0034;
    L_0x0013:
        r6 = r9.m_boDownloadCancel;	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        if (r6 != 0) goto L_0x0034;
    L_0x0017:
        r6 = 0;
        r11.write(r1, r6, r2);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r4 = r4 + r2;
        r6 = r9.m_iProgress;	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r5 = r4 / r6;
        if (r5 <= 0) goto L_0x000c;
    L_0x0022:
        r6 = 1;
        r6 = new java.lang.Integer[r6];	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r7 = 0;
        r8 = java.lang.Integer.valueOf(r5);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r6[r7] = r8;	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r9.publishProgress(r6);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r6 = r9.m_iProgress;	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r6 = r6 * r5;
        r4 = r4 - r6;
        goto L_0x000c;
    L_0x0034:
        r6 = 1;
        r6 = new java.lang.Integer[r6];	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r7 = 0;
        r8 = 1;
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r6[r7] = r8;	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r9.publishProgress(r6);	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r10.close();	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r11.flush();	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r11.close();	 Catch:{ SocketException -> 0x004e, IOException -> 0x0059, Exception -> 0x0064 }
        r0 = 1;
    L_0x004c:
        r6 = r0;
        goto L_0x0006;
    L_0x004e:
        r3 = move-exception;
        r6 = "DownloadFile";
        r7 = android.util.Log.getStackTraceString(r3);
        android.util.Log.e(r6, r7);
        goto L_0x004c;
    L_0x0059:
        r3 = move-exception;
        r6 = "DownloadFile";
        r7 = android.util.Log.getStackTraceString(r3);
        android.util.Log.e(r6, r7);
        goto L_0x004c;
    L_0x0064:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.web.download.WebDownload.SaveDownloadFile(java.io.BufferedInputStream, java.io.OutputStream):boolean");
    }
}
