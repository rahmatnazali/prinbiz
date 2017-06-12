package com.flurry.sdk;

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

public class is implements kz<hs> {
    private static final String f526a;

    public final /* synthetic */ Object m496a(InputStream inputStream) throws IOException {
        return m494b(inputStream);
    }

    static {
        f526a = is.class.getSimpleName();
    }

    private static hs m494b(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        String str = new String(lr.m315a(inputStream));
        kf.m182a(5, f526a, "Proton response string: " + str);
        hs hsVar = new hs();
        try {
            JSONObject jSONObject = new JSONObject(str);
            hsVar.f61a = jSONObject.optLong("issued_at", -1);
            hsVar.f62b = jSONObject.optLong("refresh_ttl", 3600);
            hsVar.f63c = jSONObject.optLong("expiration_ttl", 86400);
            JSONObject optJSONObject = jSONObject.optJSONObject("global_settings");
            hsVar.f64d = new hz();
            if (optJSONObject != null) {
                hsVar.f64d.f76a = m495b(optJSONObject.optString("log_level"));
            }
            optJSONObject = jSONObject.optJSONObject("pulse");
            hq hqVar = new hq();
            if (optJSONObject != null) {
                m493a(hqVar, optJSONObject.optJSONArray("callbacks"));
                hqVar.f46b = optJSONObject.optInt("max_callback_retries", 3);
                hqVar.f47c = optJSONObject.optInt("max_callback_attempts_per_report", 15);
                hqVar.f48d = optJSONObject.optInt("max_report_delay_seconds", 600);
                hqVar.f49e = optJSONObject.optString("agent_report_url", XmlPullParser.NO_NAMESPACE);
            }
            hsVar.f65e = hqVar;
            optJSONObject = jSONObject.optJSONObject("analytics");
            hsVar.f66f = new ic();
            if (optJSONObject == null) {
                return hsVar;
            }
            hsVar.f66f.f88b = optJSONObject.optBoolean("analytics_enabled", true);
            hsVar.f66f.f87a = optJSONObject.optInt("max_session_properties", 10);
            return hsVar;
        } catch (Throwable e) {
            throw new IOException("Exception while deserialize: ", e);
        }
    }

    private static void m493a(hq hqVar, JSONArray jSONArray) throws JSONException {
        if (jSONArray != null) {
            List arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject optJSONObject = jSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    hp hpVar = new hp();
                    hpVar.f36b = optJSONObject.optString("partner", XmlPullParser.NO_NAMESPACE);
                    m492a(hpVar, optJSONObject.optJSONArray("events"));
                    hpVar.f38d = m491a(optJSONObject.optString("method"));
                    hpVar.f39e = optJSONObject.optString("uri_template", XmlPullParser.NO_NAMESPACE);
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject("body_template");
                    if (optJSONObject2 != null) {
                        String optString = optJSONObject2.optString("string", "null");
                        if (!optString.equals("null")) {
                            hpVar.f40f = optString;
                        }
                    }
                    hpVar.f41g = optJSONObject.optInt("max_redirects", 5);
                    hpVar.f42h = optJSONObject.optInt("connect_timeout", 20);
                    hpVar.f43i = optJSONObject.optInt("request_timeout", 20);
                    hpVar.f35a = optJSONObject.optLong("callback_id", -1);
                    optJSONObject = optJSONObject.optJSONObject("headers");
                    if (optJSONObject != null) {
                        optJSONObject = optJSONObject.optJSONObject("map");
                        if (optJSONObject != null) {
                            hpVar.f44j = lt.m332a(optJSONObject);
                        }
                    }
                    arrayList.add(hpVar);
                }
            }
            hqVar.f45a = arrayList;
        }
    }

    private static void m492a(hp hpVar, JSONArray jSONArray) {
        if (jSONArray != null) {
            List list = null;
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject optJSONObject = jSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    if (optJSONObject.has("string")) {
                        if (list == null) {
                            list = new ArrayList();
                        }
                        hv hvVar = new hv();
                        hvVar.f70a = optJSONObject.optString("string", XmlPullParser.NO_NAMESPACE);
                        list.add(hvVar);
                    } else if (optJSONObject.has("com.flurry.proton.generated.avro.v2.EventParameterCallbackTrigger")) {
                        if (list == null) {
                            list = new ArrayList();
                        }
                        optJSONObject = optJSONObject.optJSONObject("com.flurry.proton.generated.avro.v2.EventParameterCallbackTrigger");
                        if (optJSONObject != null) {
                            String[] strArr;
                            hw hwVar = new hw();
                            hwVar.a = optJSONObject.optString("event_name", XmlPullParser.NO_NAMESPACE);
                            hwVar.f458c = optJSONObject.optString("event_parameter_name", XmlPullParser.NO_NAMESPACE);
                            JSONArray optJSONArray = optJSONObject.optJSONArray("event_parameter_values");
                            if (optJSONArray != null) {
                                String[] strArr2 = new String[optJSONArray.length()];
                                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                                    strArr2[i2] = optJSONArray.optString(i2, XmlPullParser.NO_NAMESPACE);
                                }
                                strArr = strArr2;
                            } else {
                                strArr = new String[0];
                            }
                            hwVar.f459d = strArr;
                            list.add(hwVar);
                        }
                    }
                }
            }
            hpVar.f37c = list;
        }
    }

    private static ip m491a(String str) {
        ip ipVar = ip.GET;
        try {
            if (!TextUtils.isEmpty(str)) {
                return (ip) Enum.valueOf(ip.class, str);
            }
        } catch (Exception e) {
        }
        return ipVar;
    }

    private static ia m495b(String str) {
        ia iaVar = ia.OFF;
        try {
            if (!TextUtils.isEmpty(str)) {
                return (ia) Enum.valueOf(ia.class, str);
            }
        } catch (Exception e) {
        }
        return iaVar;
    }

    public final /* synthetic */ void m497a(OutputStream outputStream, Object obj) throws IOException {
        throw new IOException("Serialize not supported for response");
    }
}
