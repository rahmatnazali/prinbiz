package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class zzz implements zzy {
    private final zza zzce;
    private final SSLSocketFactory zzcf;

    public interface zza {
        String zzh(String str);
    }

    public zzz() {
        this(null);
    }

    public zzz(zza com_google_android_gms_internal_zzz_zza) {
        this(com_google_android_gms_internal_zzz_zza, null);
    }

    public zzz(zza com_google_android_gms_internal_zzz_zza, SSLSocketFactory sSLSocketFactory) {
        this.zzce = com_google_android_gms_internal_zzz_zza;
        this.zzcf = sSLSocketFactory;
    }

    private HttpURLConnection zza(URL url, zzk<?> com_google_android_gms_internal_zzk_) throws IOException {
        HttpURLConnection zza = zza(url);
        int zzs = com_google_android_gms_internal_zzk_.zzs();
        zza.setConnectTimeout(zzs);
        zza.setReadTimeout(zzs);
        zza.setUseCaches(false);
        zza.setDoInput(true);
        if ("https".equals(url.getProtocol()) && this.zzcf != null) {
            ((HttpsURLConnection) zza).setSSLSocketFactory(this.zzcf);
        }
        return zza;
    }

    private static HttpEntity zza(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        HttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long) httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    static void zza(HttpURLConnection httpURLConnection, zzk<?> com_google_android_gms_internal_zzk_) throws IOException, zza {
        switch (com_google_android_gms_internal_zzk_.getMethod()) {
            case POP3.DISCONNECTED_STATE /*-1*/:
                byte[] zzl = com_google_android_gms_internal_zzk_.zzl();
                if (zzl != null) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.addRequestProperty("Content-Type", com_google_android_gms_internal_zzk_.zzk());
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.write(zzl);
                    dataOutputStream.close();
                }
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                httpURLConnection.setRequestMethod("GET");
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                httpURLConnection.setRequestMethod("POST");
                zzb(httpURLConnection, com_google_android_gms_internal_zzk_);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                httpURLConnection.setRequestMethod("PUT");
                zzb(httpURLConnection, com_google_android_gms_internal_zzk_);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                httpURLConnection.setRequestMethod("DELETE");
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                httpURLConnection.setRequestMethod("HEAD");
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                httpURLConnection.setRequestMethod("OPTIONS");
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                httpURLConnection.setRequestMethod("TRACE");
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                httpURLConnection.setRequestMethod("PATCH");
                zzb(httpURLConnection, com_google_android_gms_internal_zzk_);
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void zzb(HttpURLConnection httpURLConnection, zzk<?> com_google_android_gms_internal_zzk_) throws IOException, zza {
        byte[] zzp = com_google_android_gms_internal_zzk_.zzp();
        if (zzp != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", com_google_android_gms_internal_zzk_.zzo());
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzp);
            dataOutputStream.close();
        }
    }

    protected HttpURLConnection zza(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public HttpResponse zza(zzk<?> com_google_android_gms_internal_zzk_, Map<String, String> map) throws IOException, zza {
        String zzh;
        String url = com_google_android_gms_internal_zzk_.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(com_google_android_gms_internal_zzk_.getHeaders());
        hashMap.putAll(map);
        if (this.zzce != null) {
            zzh = this.zzce.zzh(url);
            if (zzh == null) {
                String str = "URL blocked by rewriter: ";
                zzh = String.valueOf(url);
                throw new IOException(zzh.length() != 0 ? str.concat(zzh) : new String(str));
            }
        }
        zzh = url;
        HttpURLConnection zza = zza(new URL(zzh), (zzk) com_google_android_gms_internal_zzk_);
        for (String zzh2 : hashMap.keySet()) {
            zza.addRequestProperty(zzh2, (String) hashMap.get(zzh2));
        }
        zza(zza, (zzk) com_google_android_gms_internal_zzk_);
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if (zza.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        HttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(protocolVersion, zza.getResponseCode(), zza.getResponseMessage()));
        basicHttpResponse.setEntity(zza(zza));
        for (Entry entry : zza.getHeaderFields().entrySet()) {
            if (entry.getKey() != null) {
                basicHttpResponse.addHeader(new BasicHeader((String) entry.getKey(), (String) ((List) entry.getValue()).get(0)));
            }
        }
        return basicHttpResponse;
    }
}
