package com.hiti.nfc.utility;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;
import com.hiti.AppInfo.AppInfo;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.nfc.utility.NFCInfo.NFCError;
import com.hiti.nfc.utility.NFCInfo.NFCMode;
import com.hiti.trace.GlobalVariable_AppInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class NfcListener implements NFCInerface {
    LogManager LOG;

    /* renamed from: com.hiti.nfc.utility.NfcListener.1 */
    static /* synthetic */ class C02421 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE;

        static {
            $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE = new int[APP_MODE.values().length];
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINBIZ.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINHOME.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINGO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public NfcListener() {
        this.LOG = null;
        this.LOG = new LogManager(0);
    }

    int GetResourceID(Context context, RS_TYPE style, String strName) {
        return ResourceSearcher.getId(context, style, strName);
    }

    private String GetNowSSID(Context context) {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        this.LOG.m386v("GetNowSSID: ", XmlPullParser.NO_NAMESPACE + strSSID);
        return strSSID;
    }

    public void ReadNFCFail(Context context, NFCInfo mNFCInfo) {
        this.LOG.m386v("ReadNFCFail NFCError:", XmlPullParser.NO_NAMESPACE + mNFCInfo.mNFCError);
        if (mNFCInfo.mNFCError == NFCError.NoSSID) {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_READ_NO_SSID")), 0).show();
        } else if (mNFCInfo.mNFCError == NFCError.NotSupport) {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_FORMATE_NOT_SUPPORT")), 0).show();
        } else if (mNFCInfo.mNFCError == NFCError.AppNotMatch) {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_APP_NAME_NOT_MATCH")), 0).show();
        } else if (mNFCInfo.mNFCError == NFCError.ModeNotMath) {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_PRINTER_MODEL_NOT_MATCH")), 0).show();
        } else {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_READ_FAIL")), 0).show();
        }
    }

    public void ReadNFCSuccess(Context context, NFCInfo mNFCInfo) {
        this.LOG.m386v("ReadNFCSuccess SSID:", XmlPullParser.NO_NAMESPACE + mNFCInfo.mSSID);
        if (mNFCInfo.mSSID != null && !mNFCInfo.mSSID.isEmpty()) {
            Toast.makeText(context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_READ_SUCCESS")) + " " + mNFCInfo.mSSID, 0).show();
            if (!GetNowSSID(context).contains(mNFCInfo.mSSID)) {
                ConnectPrinter.NfcConnect(mNFCInfo, context, context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_WIFI_CONNECT_SUCCESSS")), context.getString(GetResourceID(context, RS_TYPE.STRING, "NFC_WIFI_CONNECT_FAIL")));
            }
        }
    }

    private String GetFlurryAPI(Context context) {
        String strPackgeName = context.getPackageName();
        this.LOG.m386v("GetFlurryAPI() packageName:", XmlPullParser.NO_NAMESPACE + strPackgeName);
        GlobalVariable_AppInfo appInfo = new GlobalVariable_AppInfo(context);
        appInfo.RestoreGlobalVariable();
        if (strPackgeName != null) {
            switch (C02421.$SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[AppInfo.GetAppModeFromNumber(appInfo.GetAppMode()).ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    return FlurryUtility.FLURRY_API_KEY_PRINBIZ;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    return FlurryUtility.FLURRY_API_KEY_PRINHOME;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    return FlurryUtility.FLURRY_API_KEY_PRINGO;
            }
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    public void WriteDialogClose() {
    }

    public void SSIDEmpty() {
    }

    public void PasswordRuleError() {
    }

    public void SetSSID(NFCMode mNFCMode) {
    }
}
