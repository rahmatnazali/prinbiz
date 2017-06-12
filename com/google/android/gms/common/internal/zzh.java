package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.C0178R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzi;
import com.hiti.jumpinfo.JumpInfo;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public final class zzh {
    private static final SimpleArrayMap<String, String> yo;

    static {
        yo = new SimpleArrayMap();
    }

    public static String zzc(Context context, int i, String str) {
        Resources resources = context.getResources();
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                if (zzi.zzb(resources)) {
                    return resources.getString(C0178R.string.common_google_play_services_install_text_tablet, new Object[]{str});
                }
                return resources.getString(C0178R.string.common_google_play_services_install_text_phone, new Object[]{str});
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return resources.getString(C0178R.string.common_google_play_services_update_text, new Object[]{str});
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return resources.getString(C0178R.string.common_google_play_services_enable_text, new Object[]{str});
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return zze(context, "common_google_play_services_invalid_account_text", str);
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return zze(context, "common_google_play_services_network_error_text", str);
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return resources.getString(C0178R.string.common_google_play_services_unsupported_text, new Object[]{str});
            case ConnectionResult.API_UNAVAILABLE /*16*/:
                return zze(context, "common_google_play_services_api_unavailable_text", str);
            case ConnectionResult.SIGN_IN_FAILED /*17*/:
                return zze(context, "common_google_play_services_sign_in_failed_text", str);
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                return resources.getString(C0178R.string.common_google_play_services_updating_text, new Object[]{str});
            case ConnectionResult.RESTRICTED_PROFILE /*20*/:
                return zze(context, "common_google_play_services_restricted_profile_text", str);
            case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                return resources.getString(C0178R.string.common_google_play_services_wear_update_text);
            default:
                return resources.getString(C0178R.string.common_google_play_services_unknown_issue, new Object[]{str});
        }
    }

    public static String zzd(Context context, int i, String str) {
        return i == 6 ? zze(context, "common_google_play_services_resolution_required_text", str) : zzc(context, i, str);
    }

    private static String zze(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zzn = zzn(context, str);
        if (zzn == null) {
            zzn = resources.getString(C0178R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zzn, new Object[]{str2});
    }

    @Nullable
    public static String zzf(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return resources.getString(C0178R.string.common_google_play_services_install_title);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                return resources.getString(C0178R.string.common_google_play_services_update_title);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return resources.getString(C0178R.string.common_google_play_services_enable_title);
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return null;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return zzn(context, "common_google_play_services_invalid_account_title");
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return zzn(context, "common_google_play_services_network_error_title");
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case ConnectionResult.SERVICE_INVALID /*9*/:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return resources.getString(C0178R.string.common_google_play_services_unsupported_title);
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case ConnectionResult.API_UNAVAILABLE /*16*/:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case ConnectionResult.SIGN_IN_FAILED /*17*/:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return zzn(context, "common_google_play_services_sign_in_failed_title");
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                return resources.getString(C0178R.string.common_google_play_services_updating_title);
            case ConnectionResult.RESTRICTED_PROFILE /*20*/:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return zzn(context, "common_google_play_services_restricted_profile_title");
            default:
                Log.e("GoogleApiAvailability", "Unexpected error code " + i);
                return null;
        }
    }

    @Nullable
    public static String zzg(Context context, int i) {
        return i == 6 ? zzn(context, "common_google_play_services_resolution_required_title") : zzf(context, i);
    }

    public static String zzh(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return resources.getString(C0178R.string.common_google_play_services_install_button);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return resources.getString(C0178R.string.common_google_play_services_update_button);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return resources.getString(C0178R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    @Nullable
    private static String zzn(Context context, String str) {
        synchronized (yo) {
            String str2 = (String) yo.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
            if (identifier == 0) {
                String str3 = "GoogleApiAvailability";
                String str4 = "Missing resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            Object string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                str3 = "GoogleApiAvailability";
                str4 = "Got empty resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            yo.put(str, string);
            return string;
        }
    }
}
