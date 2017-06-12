package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.zze;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleSignInOptions extends AbstractSafeParcelable implements Optional, ReflectedParcelable {
    public static final Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    private static Comparator<Scope> dJ;
    public static final Scope dK;
    public static final Scope dL;
    public static final Scope dM;
    private Account aL;
    private final ArrayList<Scope> dN;
    private boolean dO;
    private final boolean dP;
    private final boolean dQ;
    private String dR;
    private String dS;
    final int versionCode;

    /* renamed from: com.google.android.gms.auth.api.signin.GoogleSignInOptions.1 */
    class C01801 implements Comparator<Scope> {
        C01801() {
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((Scope) obj, (Scope) obj2);
        }

        public int zza(Scope scope, Scope scope2) {
            return scope.zzaok().compareTo(scope2.zzaok());
        }
    }

    public static final class Builder {
        private Account aL;
        private boolean dO;
        private boolean dP;
        private boolean dQ;
        private String dR;
        private String dS;
        private Set<Scope> dT;

        public Builder() {
            this.dT = new HashSet();
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            this.dT = new HashSet();
            zzab.zzy(googleSignInOptions);
            this.dT = new HashSet(googleSignInOptions.dN);
            this.dP = googleSignInOptions.dP;
            this.dQ = googleSignInOptions.dQ;
            this.dO = googleSignInOptions.dO;
            this.dR = googleSignInOptions.dR;
            this.aL = googleSignInOptions.aL;
            this.dS = googleSignInOptions.dS;
        }

        private String zzfr(String str) {
            zzab.zzhr(str);
            boolean z = this.dR == null || this.dR.equals(str);
            zzab.zzb(z, (Object) "two different server client ids provided");
            return str;
        }

        public GoogleSignInOptions build() {
            if (this.dO && (this.aL == null || !this.dT.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(this.aL, this.dO, this.dP, this.dQ, this.dR, this.dS, null);
        }

        public Builder requestEmail() {
            this.dT.add(GoogleSignInOptions.dL);
            return this;
        }

        public Builder requestId() {
            this.dT.add(GoogleSignInOptions.dM);
            return this;
        }

        public Builder requestIdToken(String str) {
            this.dO = true;
            this.dR = zzfr(str);
            return this;
        }

        public Builder requestProfile() {
            this.dT.add(GoogleSignInOptions.dK);
            return this;
        }

        public Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.dT.add(scope);
            this.dT.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public Builder requestServerAuthCode(String str, boolean z) {
            this.dP = true;
            this.dR = zzfr(str);
            this.dQ = z;
            return this;
        }

        public Builder setAccountName(String str) {
            this.aL = new Account(zzab.zzhr(str), "com.google");
            return this;
        }

        public Builder setHostedDomain(String str) {
            this.dS = zzab.zzhr(str);
            return this;
        }
    }

    static {
        dK = new Scope(Scopes.PROFILE);
        dL = new Scope(Scopes.EMAIL);
        dM = new Scope("openid");
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        CREATOR = new zzb();
        dJ = new C01801();
    }

    GoogleSignInOptions(int i, ArrayList<Scope> arrayList, Account account, boolean z, boolean z2, boolean z3, String str, String str2) {
        this.versionCode = i;
        this.dN = arrayList;
        this.aL = account;
        this.dO = z;
        this.dP = z2;
        this.dQ = z3;
        this.dR = str;
        this.dS = str2;
    }

    private GoogleSignInOptions(Set<Scope> set, Account account, boolean z, boolean z2, boolean z3, String str, String str2) {
        this(2, new ArrayList(set), account, z, z2, z3, str, str2);
    }

    private JSONObject zzafp() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.dN, dJ);
            Iterator it = this.dN.iterator();
            while (it.hasNext()) {
                jSONArray.put(((Scope) it.next()).zzaok());
            }
            jSONObject.put("scopes", jSONArray);
            if (this.aL != null) {
                jSONObject.put("accountName", this.aL.name);
            }
            jSONObject.put("idTokenRequested", this.dO);
            jSONObject.put("forceCodeForRefreshToken", this.dQ);
            jSONObject.put("serverAuthRequested", this.dP);
            if (!TextUtils.isEmpty(this.dR)) {
                jSONObject.put("serverClientId", this.dR);
            }
            if (!TextUtils.isEmpty(this.dS)) {
                jSONObject.put("hostedDomain", this.dS);
            }
            return jSONObject;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static GoogleSignInOptions zzfq(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        Set hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            hashSet.add(new Scope(jSONArray.getString(i)));
        }
        Object optString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(hashSet, !TextUtils.isEmpty(optString) ? new Account(optString, "com.google") : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null));
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            GoogleSignInOptions googleSignInOptions = (GoogleSignInOptions) obj;
            if (this.dN.size() != googleSignInOptions.zzafq().size() || !this.dN.containsAll(googleSignInOptions.zzafq())) {
                return false;
            }
            if (this.aL == null) {
                if (googleSignInOptions.getAccount() != null) {
                    return false;
                }
            } else if (!this.aL.equals(googleSignInOptions.getAccount())) {
                return false;
            }
            if (TextUtils.isEmpty(this.dR)) {
                if (!TextUtils.isEmpty(googleSignInOptions.zzafu())) {
                    return false;
                }
            } else if (!this.dR.equals(googleSignInOptions.zzafu())) {
                return false;
            }
            return this.dQ == googleSignInOptions.zzaft() && this.dO == googleSignInOptions.zzafr() && this.dP == googleSignInOptions.zzafs();
        } catch (ClassCastException e) {
            return false;
        }
    }

    public Account getAccount() {
        return this.aL;
    }

    public Scope[] getScopeArray() {
        return (Scope[]) this.dN.toArray(new Scope[this.dN.size()]);
    }

    public int hashCode() {
        List arrayList = new ArrayList();
        Iterator it = this.dN.iterator();
        while (it.hasNext()) {
            arrayList.add(((Scope) it.next()).zzaok());
        }
        Collections.sort(arrayList);
        return new zze().zzq(arrayList).zzq(this.aL).zzq(this.dR).zzba(this.dQ).zzba(this.dO).zzba(this.dP).zzagc();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public String zzafn() {
        return zzafp().toString();
    }

    public ArrayList<Scope> zzafq() {
        return new ArrayList(this.dN);
    }

    public boolean zzafr() {
        return this.dO;
    }

    public boolean zzafs() {
        return this.dP;
    }

    public boolean zzaft() {
        return this.dQ;
    }

    public String zzafu() {
        return this.dR;
    }

    public String zzafv() {
        return this.dS;
    }
}
