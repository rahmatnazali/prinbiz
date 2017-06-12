package com.hiti.gcm;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import com.hiti.jni.hello.Hello;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public final class AppServerUtility {
    static boolean LOG;

    static {
        LOG = false;
    }

    public static boolean StoreRegisterToken(Context context, String regId, int iAppType) throws IOException {
        String serverUrl = GCMInfo.SERVER_URL_REGISTER;
        Map<String, String> params = new HashMap();
        params.put("data2", MakeRegisterString(context, regId));
        params.put("app_type", String.valueOf(iAppType));
        Post(serverUrl, params);
        return true;
    }

    public static void Get(String endpoint, Map<String, String> params) throws IOException {
        try {
            if (endpoint.contains("http://")) {
                endpoint = endpoint.replace("http://", "https://");
            }
            URL url = new URL(endpoint);
            StringBuilder bodyBuilder = new StringBuilder();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> param = (Entry) iterator.next();
                bodyBuilder.append((String) param.getKey()).append('=').append((String) param.getValue());
                if (iterator.hasNext()) {
                    bodyBuilder.append('&');
                }
            }
            String body = bodyBuilder.toString();
            Log.v(GCMInfo.TAG, "Posting '" + body + "' to " + url);
            byte[] bytes = body.getBytes();
            HttpsURLConnection conn = null;
            try {
                conn = (HttpsURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                int status = conn.getResponseCode();
                if (status != NNTPReply.SERVER_READY_POSTING_ALLOWED) {
                    throw new IOException("Post failed with error code " + status);
                } else if (conn != null) {
                    conn.disconnect();
                }
            } catch (Throwable th) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
    }

    public static void Post(String endpoint, Map<String, String> params) throws IOException {
        try {
            if (endpoint.contains("http://")) {
                endpoint = endpoint.replace("http://", "https://");
            }
            URL url = new URL(endpoint);
            StringBuilder bodyBuilder = new StringBuilder();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> param = (Entry) iterator.next();
                bodyBuilder.append((String) param.getKey()).append('=').append((String) param.getValue());
                if (iterator.hasNext()) {
                    bodyBuilder.append('&');
                }
            }
            String body = bodyBuilder.toString();
            Log.v(GCMInfo.TAG, "Posting " + url);
            byte[] bytes = body.getBytes();
            HttpsURLConnection conn = null;
            try {
                conn = (HttpsURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                int status = conn.getResponseCode();
                if (status != NNTPReply.SERVER_READY_POSTING_ALLOWED) {
                    throw new IOException("Post failed with error code " + status);
                } else if (conn != null) {
                    conn.disconnect();
                }
            } catch (Throwable th) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
    }

    public static String MakeRegisterString(Context context, String strRegID) {
        Pair<String, String> pair;
        String strRegisterString = XmlPullParser.NO_NAMESPACE;
        String strU = "000000000";
        String strP = "111111111";
        String strLanguage = "tw";
        String strCountry = "TW";
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        if (GVUserInfo.GetVerify() == 1) {
            pair = UserInfo.GetUP(context, GVUploadInfo.GetUploader());
        } else {
            pair = UserInfo.GetPublicPrintUP(context);
        }
        if (pair != null) {
            if (pair.first != null && ((String) pair.first).length() > 0) {
                strU = pair.first;
            }
            if (pair.second != null && ((String) pair.second).length() > 0) {
                strP = pair.second;
            }
        }
        strLanguage = MobileInfo.GetMapAppServerLanguage();
        strCountry = MobileInfo.GetAppCountryCode(context);
        if (LOG) {
            Log.d("GCM strU", XmlPullParser.NO_NAMESPACE + strU);
        }
        if (LOG) {
            Log.e("strCountry", strCountry);
        }
        if (LOG) {
            Log.e("strLanguage", strLanguage);
        }
        return EncryptAndDecryptAES.EncryptStr(strU + PringoConvenientConst.DATE_TO_DATE_2 + strP + PringoConvenientConst.DATE_TO_DATE_2 + strRegID + PringoConvenientConst.DATE_TO_DATE_2 + strLanguage + PringoConvenientConst.DATE_TO_DATE_2 + strCountry + PringoConvenientConst.DATE_TO_DATE_2 + "2" + PringoConvenientConst.DATE_TO_DATE_2, Hello.SayGoodBye(context, 1399), Hello.SayHello(context, 1399));
    }
}
