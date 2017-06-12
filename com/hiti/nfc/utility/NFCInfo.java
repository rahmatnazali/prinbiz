package com.hiti.nfc.utility;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import com.hiti.jni.hello.Hello;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.nfc.Nfc;
import com.hiti.nfc.utility.NfcHandler.IhandlerListener;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.PrintMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class NFCInfo {
    static LogManager LOG;
    public String mAppType;
    public NFCError mNFCError;
    public NFCInerface mNFCListener;
    public NFCMode mNFCTag;
    public Nfc mNfc;
    public NfcAdapter mNfcAdapter;
    public String mPassword;
    public PrintMode mPrintMode;
    public String mPrinterModel;
    public String mSSID;

    /* renamed from: com.hiti.nfc.utility.NFCInfo.2 */
    static /* synthetic */ class C02392 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$Verify$PrintMode;

        static {
            $SwitchMap$com$hiti$utility$Verify$PrintMode = new int[PrintMode.values().length];
            try {
                $SwitchMap$com$hiti$utility$Verify$PrintMode[PrintMode.Init.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$Verify$PrintMode[PrintMode.NFC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public interface INfcPreview {
        void GetNfcData(NFCInfo nFCInfo);
    }

    public enum NFCError {
        AppNotMatch,
        NoSSID,
        ModeNotMath,
        ReadNFCFail,
        Non,
        NotSupport
    }

    public enum NFCMode {
        NFCRead,
        NFCWritePrinter,
        NFCWriteTag
    }

    public enum NfcState {
        on,
        off,
        not_support
    }

    public interface SnapListener {
        void PackNFCDataDone(Bundle bundle);
    }

    /* renamed from: com.hiti.nfc.utility.NFCInfo.1 */
    static class C07311 implements IhandlerListener {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ NFCInfo val$nfcInfo;
        final /* synthetic */ INfcPreview val$preListener;

        C07311(NFCInfo nFCInfo, Activity activity, INfcPreview iNfcPreview) {
            this.val$nfcInfo = nFCInfo;
            this.val$activity = activity;
            this.val$preListener = iNfcPreview;
        }

        public void GetNfcData(ArrayList<HashMap<String, String>> data) {
            NFCInfo.CheckNfcTagFormat(data, this.val$nfcInfo, this.val$activity);
            if (this.val$preListener != null) {
                this.val$preListener.GetNfcData(this.val$nfcInfo);
            }
            NFCInfo.AfterScanNFCInfo(this.val$nfcInfo, this.val$activity);
        }
    }

    public NFCInfo() {
        this.mSSID = null;
        this.mPassword = null;
        this.mPrinterModel = null;
        this.mAppType = null;
        this.mPrintMode = null;
        this.mNfcAdapter = null;
        this.mNFCListener = null;
        this.mNfc = null;
        this.mNFCError = NFCError.Non;
        this.mNFCTag = NFCMode.NFCRead;
    }

    static {
        LOG = new LogManager(0);
    }

    public static boolean IsNFCForHitiApp(NFCInfo mNFCInfo) {
        if (mNFCInfo == null || mNFCInfo.mNFCError == NFCError.Non || mNFCInfo.mNFCError == NFCError.NoSSID) {
            return true;
        }
        return false;
    }

    public static NFCInfo InitNFC(Intent intent, NFCInerface listener) {
        NFCInfo mNFCInfo = NewNFCInfo(GetNFCPackData(intent));
        mNFCInfo.mNFCListener = listener;
        return mNFCInfo;
    }

    public static String xorEncrypt(String strNFC, String key) {
        byte[] inputBytes = strNFC.getBytes();
        int inputSize = inputBytes.length;
        byte[] keyByte = key.getBytes();
        int keySize = keyByte.length;
        byte[] xorBytes = new byte[inputSize];
        for (int i = 0; i < inputSize; i++) {
            xorBytes[i] = (byte) (inputBytes[i] ^ keyByte[i % keySize]);
        }
        return new String(xorBytes);
    }

    public static String xorDecrypt(String input, String Key) {
        byte[] inputBytes = input.getBytes();
        int inputSize = inputBytes.length;
        byte[] keyByte = Key.getBytes();
        int keySize = keyByte.length;
        byte[] xorBytes = new byte[inputSize];
        for (int i = 0; i < inputSize; i++) {
            xorBytes[i] = (byte) (inputBytes[i] ^ keyByte[i % keySize]);
        }
        return new String(xorBytes);
    }

    public static NFCInfo WriteNFC(NFCInfo mNFCInfo, Activity activity) {
        mNFCInfo = NewNFCInfo(mNFCInfo);
        mNFCInfo.mNfcAdapter = enableNdefExchangeMode(activity);
        if (IsNFCSupport(activity.getIntent())) {
            mNFCInfo.mNfc = Nfc.getNfcInstance(activity.getIntent());
        } else {
            mNFCInfo.mNfc = null;
        }
        return mNFCInfo;
    }

    private static NfcAdapter enableNdefExchangeMode(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        PendingIntent mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()).addFlags(536870912), 0);
        if (mNfcAdapter != null) {
            Nfc.enableNdefExchangeMode(mNfcAdapter, activity, mPendingIntent);
        }
        return mNfcAdapter;
    }

    public static NFCInfo NewNFCInfo(NFCInfo mNFCInfo) {
        LOG.m385i("NewNFCInfo", "NewNFCInfo");
        if (mNFCInfo != null) {
            return mNFCInfo;
        }
        mNFCInfo = new NFCInfo();
        mNFCInfo.mPrintMode = PrintMode.NormalMain;
        mNFCInfo.mNFCError = NFCError.Non;
        return mNFCInfo;
    }

    public static void PreviewNFCData(Activity activity, INfcPreview preListener, NFCInerface listener) {
        LOG.m385i("PreviewNFCData", activity.getClass().getSimpleName());
        boolean bRet = false;
        NFCInfo mNFCInfo = NewNFCInfo(null);
        mNFCInfo.mNFCListener = listener;
        if (IsNFCSupport(activity.getIntent())) {
            bRet = ReadNfcTagData(mNFCInfo, activity, preListener);
        }
        if (!bRet) {
            preListener.GetNfcData(mNFCInfo);
        }
    }

    public static NFCInfo disableForegroundDispatch(NFCInfo mNFCInfo, Activity activity) {
        if (mNFCInfo.mNfcAdapter != null) {
            mNFCInfo.mNfcAdapter.disableForegroundDispatch(activity);
        }
        activity.setIntent(null);
        return null;
    }

    public static NFCInfo NfcEnableNdefExchangeMode(NFCInfo mNFCInfo, Activity activity) {
        mNFCInfo.mNfcAdapter = enableNdefExchangeMode(activity);
        AfterScanNFCInfo(mNFCInfo, activity);
        return mNFCInfo;
    }

    public static void CheckNFC(NFCInerface listener, Activity activity, INfcPreview preListener) {
        NFCInfo mNFCInfo = NewNFCInfo(GetNFCPackData(activity.getIntent()));
        mNFCInfo.mNFCListener = listener;
        CheckNFC(mNFCInfo, activity, preListener);
    }

    public static void CheckNFC(NFCInfo nfcInfo, NFCInerface listener, Activity activity, INfcPreview preListener) {
        nfcInfo.mNFCListener = listener;
        CheckNFC(nfcInfo, activity, preListener);
    }

    private static void CheckNFC(NFCInfo nfcInfo, Activity activity, INfcPreview preListener) {
        nfcInfo.mNfcAdapter = enableNdefExchangeMode(activity);
        if (nfcInfo.mNfcAdapter == null || !IsNFCSupport(activity.getIntent())) {
            preListener.GetNfcData(nfcInfo);
        } else {
            ReadNfcTagData(nfcInfo, activity, preListener);
        }
    }

    public static void AfterScanNFCInfo(NFCInfo mNFCInfo, Context context) {
        if (mNFCInfo.mNFCListener != null) {
            LOG.m385i("AfterScanNFCInfo", "mPrintMode:" + mNFCInfo.mPrintMode);
            switch (C02392.$SwitchMap$com$hiti$utility$Verify$PrintMode[mNFCInfo.mPrintMode.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (mNFCInfo.mNFCListener != null) {
                        mNFCInfo.mNFCListener.ReadNFCFail(context, mNFCInfo);
                    }
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (mNFCInfo.mNFCListener != null) {
                        mNFCInfo.mNFCListener.ReadNFCSuccess(context, mNFCInfo);
                    }
                default:
            }
        }
    }

    public static boolean IsNFCSupport(Intent intent) {
        if (intent == null || intent.getAction() == null || !(intent.getAction().equals("android.nfc.action.NDEF_DISCOVERED") || intent.getAction().equals("android.nfc.action.TECH_DISCOVERED") || intent.getAction().equals("android.nfc.action.TAG_DISCOVERED"))) {
            if (intent != null) {
                LOG.m386v("NFCInfo", "IsNFCSupport: getAction = " + intent.getAction());
            }
            return false;
        }
        LOG.m386v("NFCInfo", "IsNFCSupport: getAction = " + intent.getAction());
        return true;
    }

    private static boolean ReadNfcTagData(NFCInfo nfcInfo, Activity activity, INfcPreview preListener) {
        Nfc nfc = Nfc.getNfcInstance(activity.getIntent());
        LOG.m385i("ReadNfcTagData", activity.getClass().getSimpleName());
        if (nfc != null) {
            NfcHandler mNfcHandler = new NfcHandler();
            mNfcHandler.SetHandlerListener(new C07311(nfcInfo, activity, preListener));
            nfc.readNFC(mNfcHandler);
            return true;
        }
        nfcInfo.mNFCError = NFCError.NotSupport;
        AfterScanNFCInfo(nfcInfo, activity);
        return false;
    }

    private static String GetValue(ArrayList<HashMap<String, String>> nfcData, String name) {
        Iterator it = nfcData.iterator();
        while (it.hasNext()) {
            HashMap<String, String> data = (HashMap) it.next();
            if (((String) data.get("name")).equals(name)) {
                String value = (String) data.get("value");
                LOG.m385i("Get NFC Value", name + "=" + value);
                return value;
            }
        }
        return null;
    }

    private static void CheckNfcTagFormat(ArrayList<HashMap<String, String>> nfcData, NFCInfo nfcInfo, Context context) {
        LOG.m385i("CheckFormat", "nfcData" + nfcData);
        String strSSID = GetValue(nfcData, "SSID");
        String password = GetValue(nfcData, "password");
        String strAppName = GetValue(nfcData, "app");
        String strPrinter = GetValue(nfcData, "printer");
        String strError = GetValue(nfcData, "error");
        if (strError != null && strError.equals("not-support")) {
            nfcInfo.mNFCError = NFCError.NotSupport;
        } else if (strAppName == null || strPrinter == null) {
            nfcInfo.mNFCError = NFCError.ReadNFCFail;
        } else {
            if (strSSID != null) {
                password = DecodeData(password, context);
                nfcInfo.mSSID = strSSID;
                nfcInfo.mPassword = password;
                nfcInfo.mPrintMode = PrintMode.NFC;
                if (nfcInfo.mSSID.length() == 0) {
                    nfcInfo.mNFCError = NFCError.NoSSID;
                }
            } else {
                nfcInfo.mNFCError = NFCError.ReadNFCFail;
            }
            if (strAppName != null) {
                if (strAppName.equals(GetAppName(context))) {
                    nfcInfo.mAppType = strAppName;
                    if (strPrinter != null) {
                        if (GetSupportPrinterModel(context).contains(strPrinter)) {
                            nfcInfo.mPrinterModel = strPrinter;
                        } else {
                            nfcInfo.mNFCError = NFCError.ModeNotMath;
                        }
                    }
                } else {
                    nfcInfo.mNFCError = NFCError.AppNotMatch;
                }
            }
            LOG.m386v("CheckNFCDataFormat", "mNFCError=" + String.valueOf(nfcInfo.mNFCError));
        }
        LOG.m386v("CheckNFCDataFormat", "mPrintMode=" + String.valueOf(nfcInfo.mPrintMode));
        if (nfcInfo.mNFCError != NFCError.Non) {
            nfcInfo.mPrintMode = PrintMode.Init;
        }
    }

    public static String DecodeData(String password, Context context) {
        if (password != null) {
            password = xorDecrypt(password, Hello.SayGoodBye(context, 1119));
        }
        LOG.m386v("DecodeData", "password=" + String.valueOf(password));
        return password;
    }

    public static NFCInfo GetNFCPackData(Intent intent) {
        NFCInfo mNFCInfo = null;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String strMode = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
                bundle.remove(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
                LOG.m386v("GetNFCPackData", "bundle=" + String.valueOf(bundle));
                if (strMode != null) {
                    mNFCInfo = new NFCInfo();
                    mNFCInfo.mPrintMode = PrintMode.valueOf(strMode);
                    String strAppType = bundle.getString(JumpBundleMessage.BUNDLE_MSG_NFC_APPNAME);
                    String strPrinterModel = bundle.getString(JumpBundleMessage.BUNDLE_MSG_NFC_PRINTER_MODEL);
                    if (strAppType != null) {
                        mNFCInfo.mAppType = strAppType;
                    }
                    if (strPrinterModel != null) {
                        mNFCInfo.mPrinterModel = strPrinterModel;
                    }
                    if (mNFCInfo.mPrintMode == PrintMode.NFC || mNFCInfo.mPrintMode == PrintMode.NFC_launch) {
                        String strSSID = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_NFC_SSID);
                        String password = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_NFC_PASSWORD);
                        if (strSSID != null) {
                            mNFCInfo.mSSID = strSSID;
                        }
                        if (password != null) {
                            mNFCInfo.mPassword = password;
                        }
                        LOG.m386v("GetNFCPackData", "mSSID=" + String.valueOf(mNFCInfo.mSSID));
                        LOG.m386v("GetNFCPackData", "password=" + String.valueOf(mNFCInfo.mPassword));
                    }
                }
            }
        }
        return mNFCInfo;
    }

    public static Bundle PackNFCData(NFCInfo info) {
        Bundle bundle = new Bundle();
        if (info == null) {
            return null;
        }
        LOG.m386v("PackNFCData", "mPrintMode=" + String.valueOf(info.mPrintMode));
        if (info.mPrintMode != null) {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE, String.valueOf(info.mPrintMode));
        }
        if (info.mAppType != null) {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_NFC_APPNAME, String.valueOf(info.mAppType));
        }
        if (info.mPrinterModel != null) {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_NFC_PRINTER_MODEL, String.valueOf(info.mPrinterModel));
        }
        if (info.mSSID != null) {
            bundle.putString(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_NFC_SSID, String.valueOf(info.mSSID));
        }
        if (info.mPassword == null) {
            return bundle;
        }
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_NFC_PASSWORD, String.valueOf(info.mPassword));
        return bundle;
    }

    public static boolean IsSupportWifiDefault(NFCInfo mNFCInfo) {
        if (mNFCInfo.mPrinterModel.equals(GetPrinterModelForNFC(WirelessType.TYPE_P520L)) || mNFCInfo.mPrinterModel.equals(GetPrinterModelForNFC(WirelessType.TYPE_P750L))) {
            return false;
        }
        return true;
    }

    public static String GetPrinterModelForNFC(String strProductID) {
        if (strProductID == null) {
            return null;
        }
        if (strProductID.equals(WirelessType.TYPE_P231)) {
            return "p231";
        }
        if (strProductID.equals(WirelessType.TYPE_P232)) {
            return "p232";
        }
        if (strProductID.equals(WirelessType.TYPE_P310W)) {
            return "p310w";
        }
        if (strProductID.equals(WirelessType.TYPE_P461)) {
            return "p461";
        }
        if (strProductID.equals(WirelessType.TYPE_P520L)) {
            return "p520l";
        }
        if (strProductID.equals(WirelessType.TYPE_P530D)) {
            return "p530d";
        }
        if (strProductID.equals(WirelessType.TYPE_P750L)) {
            return "p750l";
        }
        return "p461";
    }

    public static String GetPrinterModelFromNFC(String strNFCModel) {
        if (strNFCModel == null) {
            return null;
        }
        if (strNFCModel.equals("p231")) {
            return WirelessType.TYPE_P231;
        }
        if (strNFCModel.equals("p232")) {
            return WirelessType.TYPE_P232;
        }
        if (strNFCModel.equals("p310w")) {
            return WirelessType.TYPE_P310W;
        }
        if (strNFCModel.equals("p461")) {
            return WirelessType.TYPE_P461;
        }
        if (strNFCModel.equals("p520l")) {
            return WirelessType.TYPE_P520L;
        }
        if (strNFCModel.equals("p530d")) {
            return WirelessType.TYPE_P530D;
        }
        if (strNFCModel.equals("p750l")) {
            return WirelessType.TYPE_P750L;
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    private static String GetSupportPrinterModel(Context context) {
        String strAppName = GetAppName(context);
        if (strAppName.equals("pringo")) {
            return "p231, p232, p233";
        }
        if (strAppName.equals("prinhome")) {
            return "p461";
        }
        if (strAppName.equals("prinbiz")) {
            return "p310w, p520l, p750l";
        }
        return null;
    }

    private static String GetAppName(Context context) {
        String packageName = context.getPackageName();
        packageName = packageName.substring(packageName.lastIndexOf(".") + 1);
        LOG.m386v("GetAppName in device", String.valueOf(packageName));
        return packageName;
    }

    public static void ChangeSSID(NFCMode mode, NFCInerface listener, String strSetSSID, String strSetPwd) {
        if (listener != null) {
            if (mode != NFCMode.NFCWritePrinter) {
                listener.SetSSID(mode);
            } else if (strSetSSID.length() == 0 || strSetPwd.length() == 0 || strSetPwd.length() == 5 || strSetPwd.length() == 13) {
                listener.SetSSID(mode);
            } else {
                listener.PasswordRuleError();
            }
        }
    }

    public static NfcState GetNfcDeviceState(Context context) {
        NfcAdapter mAdpter = NfcAdapter.getDefaultAdapter(context);
        if (mAdpter == null) {
            return NfcState.not_support;
        }
        if (mAdpter.isEnabled()) {
            return NfcState.on;
        }
        return NfcState.off;
    }
}
