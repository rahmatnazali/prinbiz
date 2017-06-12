package com.hiti.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import com.hiti.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.time.Timeout;
import com.hiti.utility.time.Timeout.ITimeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import javax.jmdns.impl.constants.DNSConstants;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.net.nntp.NNTPReply;

public class WebRequestNew {
    LogManager LOG;
    String TAG;
    private final int TIME_OUT;
    boolean bIsStop;
    Timeout mTimeout;

    /* renamed from: com.hiti.web.WebRequestNew.1 */
    class C08141 implements ITimeListener {
        final /* synthetic */ HttpsURLConnection val$mConn2;

        C08141(HttpsURLConnection httpsURLConnection) {
            this.val$mConn2 = httpsURLConnection;
        }

        public void TimeOut() {
            WebRequestNew.this.LOG.m385i(WebRequestNew.this.TAG, "TimeOut!!!");
            this.val$mConn2.disconnect();
            WebRequestNew.this.Stop();
        }
    }

    /* renamed from: com.hiti.web.WebRequestNew.2 */
    class C08152 implements ITimeListener {
        final /* synthetic */ HttpsURLConnection val$mConn;
        final /* synthetic */ OutputStream val$os;

        C08152(OutputStream outputStream, HttpsURLConnection httpsURLConnection) {
            this.val$os = outputStream;
            this.val$mConn = httpsURLConnection;
        }

        public void TimeOut() {
            WebRequestNew.this.LOG.m385i(WebRequestNew.this.TAG, "TimeOut!!!");
            try {
                this.val$os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.val$mConn.disconnect();
            WebRequestNew.this.Stop();
        }
    }

    public WebRequestNew() {
        this.mTimeout = null;
        this.TIME_OUT = DNSConstants.RECORD_REAPER_INTERVAL;
        this.bIsStop = false;
        this.LOG = null;
        this.TAG = "WebRequestNew";
        this.mTimeout = new Timeout();
        this.LOG = new LogManager(0);
    }

    public void SetImageData(Context context, Map<String, String> params, String strImagePath) {
        this.LOG.m385i(this.TAG, "SetImageData: " + strImagePath);
        try {
            Bitmap bmp = Media.getBitmap(context.getContentResolver(), Uri.parse("file://" + strImagePath));
            this.LOG.m385i(this.TAG, "SetImageData size: " + (FileUtility.GetFileSize(strImagePath) / 1000) + "KB");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.JPEG, 100, baos);
            params.put("data", Base64.encodeToString(baos.toByteArray(), 0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        params.put("version", "2");
        params.put("ext", FileUtility.GetFileExt(strImagePath));
    }

    public void Stop() {
        this.LOG.m385i(this.TAG, "Stop");
        if (this.mTimeout != null) {
            this.mTimeout.Stop();
        }
        SetStop(true);
    }

    private void SetStop(boolean bStop) {
        this.bIsStop = bStop;
    }

    private boolean IsStop() {
        return this.bIsStop;
    }

    public String UploadPhotoURLConnection(String strRequest, Map<String, String> params) {
        String str = null;
        if (params != null) {
            HttpsURLConnection mConn = null;
            this.LOG.m386v("UploadPhotoURLConnection request:\n", strRequest);
            try {
                mConn = SendRequest(strRequest, params);
                str = GetResponse(mConn);
                if (mConn != null) {
                    mConn.disconnect();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                if (mConn != null) {
                    mConn.disconnect();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (mConn != null) {
                    mConn.disconnect();
                }
            } catch (Throwable th) {
                if (mConn != null) {
                    mConn.disconnect();
                }
            }
        }
        return str;
    }

    private String getData(Map<String, String> params) throws UnsupportedEncodingException {
        this.LOG.m385i(this.TAG, "getData");
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
            result.append("&");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private String GetResponse(HttpsURLConnection mConn) throws IOException {
        this.LOG.m385i(this.TAG, "GetResponse Stop!!!:" + IsStop());
        if (IsStop() || mConn == null) {
            return null;
        }
        Stop();
        this.LOG.m385i(this.TAG, "GetResponse HTTP_OK: " + mConn.getResponseCode());
        if (mConn.getResponseCode() != NNTPReply.SERVER_READY_POSTING_ALLOWED) {
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(mConn.getInputStream()));
        StringBuilder responseOutput = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line != null) {
                responseOutput.append(line);
            } else {
                br.close();
                this.LOG.m385i(this.TAG, "GetResponse responseOutput: " + responseOutput);
                return responseOutput.toString();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String PostSOAP(java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18) {
        /*
        r13 = this;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><";
        r9 = r9.append(r10);
        r0 = r16;
        r9 = r9.append(r0);
        r10 = " xmlns=\"";
        r9 = r9.append(r10);
        r9 = r9.append(r14);
        r10 = "\">";
        r9 = r9.append(r10);
        r0 = r18;
        r9 = r9.append(r0);
        r10 = "</";
        r9 = r9.append(r10);
        r0 = r16;
        r9 = r9.append(r0);
        r10 = ">";
        r9 = r9.append(r10);
        r10 = "</soap:Body>";
        r9 = r9.append(r10);
        r10 = "</soap:Envelope>";
        r9 = r9.append(r10);
        r1 = r9.toString();
        r9 = r13.LOG;
        r10 = r13.TAG;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "PostSOAP request: ";
        r11 = r11.append(r12);
        r11 = r11.append(r15);
        r11 = r11.toString();
        r9.m386v(r10, r11);
        r3 = 0;
        r9 = "http://";
        r9 = r15.contains(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        if (r9 == 0) goto L_0x0074;
    L_0x006c:
        r9 = "http://";
        r10 = "https://";
        r15 = r15.replace(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
    L_0x0074:
        r7 = new java.net.URL;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r7.<init>(r15);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = r7.openConnection();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r0 = r9;
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r3 = r0;
        r9 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setReadTimeout(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r3.setConnectTimeout(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "POST";
        r3.setRequestMethod(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = 1;
        r3.setDoInput(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = 1;
        r3.setDoOutput(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "Host";
        r10 = android.net.Uri.parse(r15);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r10 = r10.getHost();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r3.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "Content-Type";
        r10 = "text/xml; charset=utf-8";
        r3.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "SOAPAction";
        r0 = r17;
        r3.setRequestProperty(r9, r0);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "Accept-Charset";
        r10 = "utf-8";
        r3.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "Accept";
        r10 = "text/xml,application/text+xml,application/soap+xml";
        r3.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "Content-Length";
        r10 = r1.getBytes();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r10 = r10.length;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r10 = java.lang.String.valueOf(r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r3.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r4 = r3;
        r9 = r13.mTimeout;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r11 = new com.hiti.web.WebRequestNew$1;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r11.<init>(r4);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9.Start(r10, r11);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r5 = r3.getOutputStream();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r8 = new java.io.BufferedWriter;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = new java.io.OutputStreamWriter;	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r10 = "UTF-8";
        r9.<init>(r5, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r8.<init>(r9);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r8.write(r1);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r8.flush();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r8.close();	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r6 = r13.GetResponse(r3);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        if (r6 == 0) goto L_0x011b;
    L_0x00fb:
        r9 = "&amp;";
        r10 = "&";
        r6 = r6.replaceAll(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "&lt;";
        r10 = "<";
        r6 = r6.replaceAll(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "&gt;";
        r10 = ">";
        r6 = r6.replaceAll(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
        r9 = "&quot;";
        r10 = "\"";
        r6 = r6.replaceAll(r9, r10);	 Catch:{ IOException -> 0x0121, Exception -> 0x012c }
    L_0x011b:
        if (r3 == 0) goto L_0x0120;
    L_0x011d:
        r3.disconnect();
    L_0x0120:
        return r6;
    L_0x0121:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0136 }
        if (r3 == 0) goto L_0x012a;
    L_0x0127:
        r3.disconnect();
    L_0x012a:
        r6 = 0;
        goto L_0x0120;
    L_0x012c:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0136 }
        if (r3 == 0) goto L_0x012a;
    L_0x0132:
        r3.disconnect();
        goto L_0x012a;
    L_0x0136:
        r9 = move-exception;
        if (r3 == 0) goto L_0x013c;
    L_0x0139:
        r3.disconnect();
    L_0x013c:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.web.WebRequestNew.PostSOAP(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public boolean Post(String strRequest, Map<String, String> params) {
        if (params == null) {
            return false;
        }
        this.LOG.m385i(this.TAG, "Post");
        String strResponse = PostByURLConnection(strRequest, params);
        if (strResponse == null) {
            return false;
        }
        if (new WebPostRequest().Success(strResponse)) {
            return true;
        }
        this.LOG.m385i(HITI_WEB_VIEW_STATUS.ERROR, strResponse);
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String PostByURLConnection(java.lang.String r7, java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
        r6 = this;
        r2 = 0;
        if (r8 != 0) goto L_0x0004;
    L_0x0003:
        return r2;
    L_0x0004:
        r3 = r6.LOG;
        r4 = "send POST Request";
        r3.m386v(r4, r7);
        r1 = 0;
        r1 = r6.SendRequest(r7, r8);	 Catch:{ UnsupportedEncodingException -> 0x001a, IOException -> 0x002d }
        r2 = r6.GetResponse(r1);	 Catch:{ UnsupportedEncodingException -> 0x001a, IOException -> 0x002d }
        if (r1 == 0) goto L_0x0003;
    L_0x0016:
        r1.disconnect();
        goto L_0x0003;
    L_0x001a:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0037 }
        if (r1 == 0) goto L_0x0023;
    L_0x0020:
        r1.disconnect();
    L_0x0023:
        r3 = r6.LOG;
        r4 = r6.TAG;
        r5 = "POST response exception!!!";
        r3.m385i(r4, r5);
        goto L_0x0003;
    L_0x002d:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0037 }
        if (r1 == 0) goto L_0x0023;
    L_0x0033:
        r1.disconnect();
        goto L_0x0023;
    L_0x0037:
        r2 = move-exception;
        if (r1 == 0) goto L_0x003d;
    L_0x003a:
        r1.disconnect();
    L_0x003d:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.web.WebRequestNew.PostByURLConnection(java.lang.String, java.util.Map):java.lang.String");
    }

    private HttpsURLConnection SendRequest(String strRequest, Map<String, String> params) throws IOException {
        this.LOG.m385i(this.TAG, "SendRequest");
        if (strRequest.contains("http://")) {
            strRequest = strRequest.replace("http://", "https://");
        }
        HttpsURLConnection mConn = (HttpsURLConnection) new URL(strRequest).openConnection();
        mConn.setReadTimeout(30000);
        mConn.setConnectTimeout(30000);
        mConn.setRequestMethod("POST");
        mConn.setDoInput(true);
        mConn.setDoOutput(true);
        String data = getData(params);
        mConn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
        mConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        OutputStream os = mConn.getOutputStream();
        this.mTimeout.Start(DNSConstants.RECORD_REAPER_INTERVAL, new C08152(os, mConn));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
        return mConn;
    }
}
