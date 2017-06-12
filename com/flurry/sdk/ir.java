package com.flurry.sdk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ir implements kz<hr> {
    private static final String f525a;

    /* renamed from: com.flurry.sdk.ir.1 */
    class C01391 extends DataOutputStream {
        final /* synthetic */ ir f176a;

        C01391(ir irVar, OutputStream outputStream) {
            this.f176a = irVar;
            super(outputStream);
        }

        public final void close() {
        }
    }

    public final /* synthetic */ void m490a(OutputStream outputStream, Object obj) throws IOException {
        hr hrVar = (hr) obj;
        if (outputStream != null && hrVar != null) {
            DataOutputStream c01391 = new C01391(this, outputStream);
            JSONObject jSONObject = new JSONObject();
            try {
                Object obj2;
                m488a(jSONObject, "project_key", hrVar.f50a);
                m488a(jSONObject, "bundle_id", hrVar.f51b);
                m488a(jSONObject, "app_version", hrVar.f52c);
                jSONObject.put("sdk_version", hrVar.f53d);
                jSONObject.put("platform", hrVar.f54e);
                m488a(jSONObject, "platform_version", hrVar.f55f);
                jSONObject.put("limit_ad_tracking", hrVar.f56g);
                if (hrVar.f57h == null || hrVar.f57h.f69a == null) {
                    obj2 = null;
                } else {
                    obj2 = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    m488a(jSONObject2, "model", hrVar.f57h.f69a.f29a);
                    m488a(jSONObject2, "brand", hrVar.f57h.f69a.f30b);
                    m488a(jSONObject2, "id", hrVar.f57h.f69a.f31c);
                    m488a(jSONObject2, "device", hrVar.f57h.f69a.f32d);
                    m488a(jSONObject2, "product", hrVar.f57h.f69a.f33e);
                    m488a(jSONObject2, "version_release", hrVar.f57h.f69a.f34f);
                    obj2.put("com.flurry.proton.generated.avro.v2.AndroidTags", jSONObject2);
                }
                if (obj2 != null) {
                    jSONObject.put("device_tags", obj2);
                } else {
                    jSONObject.put("device_tags", JSONObject.NULL);
                }
                JSONArray jSONArray = new JSONArray();
                for (ht htVar : hrVar.f58i) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", htVar.f67a);
                    m488a(jSONObject3, "id", htVar.f68b);
                    jSONArray.put(jSONObject3);
                }
                jSONObject.put("device_ids", jSONArray);
                if (hrVar.f59j == null || hrVar.f59j.f75a == null) {
                    obj2 = null;
                } else {
                    obj2 = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.putOpt("latitude", Double.valueOf(hrVar.f59j.f75a.f72a));
                    jSONObject4.putOpt("longitude", Double.valueOf(hrVar.f59j.f75a.f73b));
                    jSONObject4.putOpt("accuracy", Float.valueOf(hrVar.f59j.f75a.f74c));
                    obj2.put("com.flurry.proton.generated.avro.v2.Geolocation", jSONObject4);
                }
                if (obj2 != null) {
                    jSONObject.put("geo", obj2);
                } else {
                    jSONObject.put("geo", JSONObject.NULL);
                }
                JSONObject jSONObject5 = new JSONObject();
                if (hrVar.f60k != null) {
                    m488a(jSONObject5, "string", hrVar.f60k.f86a);
                    jSONObject.put("publisher_user_id", jSONObject5);
                } else {
                    jSONObject.put("publisher_user_id", JSONObject.NULL);
                }
                kf.m182a(5, f525a, "Proton Request String: " + jSONObject.toString());
                c01391.write(jSONObject.toString().getBytes());
                c01391.flush();
                c01391.close();
            } catch (Throwable e) {
                throw new IOException("Invalid Json", e);
            } catch (Throwable th) {
                c01391.close();
            }
        }
    }

    static {
        f525a = ir.class.getSimpleName();
    }

    private static void m488a(JSONObject jSONObject, String str, String str2) throws IOException, JSONException {
        if (str2 != null) {
            jSONObject.put(str, str2);
        } else {
            jSONObject.put(str, JSONObject.NULL);
        }
    }

    public final /* synthetic */ Object m489a(InputStream inputStream) throws IOException {
        throw new IOException("Deserialize not supported for request");
    }
}
