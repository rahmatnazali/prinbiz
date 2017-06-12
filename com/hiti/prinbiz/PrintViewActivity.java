package com.hiti.prinbiz;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.media.TransportMediator;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.view.GravityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.Flurry.FlurryLogString;
import com.hiti.Flurry.FlurryUtility;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.jumpinfo.JumpBundleMessage;
import com.hiti.jumpinfo.JumpBundleMessage520;
import com.hiti.jumpinfo.JumpInfo;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.jumpinfo.JumpPreferenceKey.PHOTO_MODE;
import com.hiti.nfc.utility.NFCInfo;
import com.hiti.nfc.utility.NFCInfo.INfcPreview;
import com.hiti.nfc.utility.NfcListener;
import com.hiti.printerprotocol.PrinterErrorCode;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_GetPSC;
import com.hiti.printerprotocol.request.HitiPPR_PaperJamRun;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommand;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_QuickPrint;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.hiti.printerprotocol.request.HitiPPR_SendPhotoPrinbiz;
import com.hiti.printerprotocol.utility.IMobile;
import com.hiti.printerprotocol.utility.ISDcard;
import com.hiti.printerprotocol.utility.MobileUtility;
import com.hiti.printerprotocol.utility.PrinterInfo;
import com.hiti.printerprotocol.utility.SDcardUtility;
import com.hiti.service.print.PrintBinder;
import com.hiti.service.print.PrintBinder.IBinder;
import com.hiti.service.print.PrintConnection;
import com.hiti.service.print.PrintService.NotifyInfo;
import com.hiti.service.upload.UploadService;
import com.hiti.trace.GlobalVariable_WifiAutoConnectInfo;
import com.hiti.ui.drawview.garnishitem.utility.EditMeta;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.drawview.garnishitem.utility.MakeEditPhoto;
import com.hiti.ui.drawview.garnishitem.utility.MakePhotoListener;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.CleanModeHint;
import com.hiti.utility.LogManager;
import com.hiti.utility.PrinterListListener;
import com.hiti.utility.ShowPrinterList;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.QualityListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.dialog.ShowMSGDialog.HintDialog;
import com.hiti.utility.dialog.ShowMSGDialog.ICleanListener;
import com.hiti.utility.permissionRequest.PermissionAsk;
import com.hiti.utility.permissionRequest.PermissionAsk.IPermission;
import com.hiti.utility.wifi.WifiAutoConnect;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.finger.FingerClient;
import org.xmlpull.v1.XmlPullParser;

public class PrintViewActivity extends Activity {
    private static final int BACKPAGE = 1;
    private static final int GLOSSY_TEXTURE = 0;
    private static final int LEAVE_TO_MAIN = 2;
    private static final int LEAVE_TO_PHOTO = 3;
    private static final int MATTE_TEXTURE = 1;
    String IP;
    LogManager LOG;
    String TAG;
    private DisplayMetrics dm;
    NFCInfo mNFCInfo;
    PermissionAsk mPermissionAsk;
    PrintConnection mPrintConnection;
    private ImageButton m_BackImageButton;
    private ProgressBar m_CloseListProgressBar;
    private RelativeLayout m_DuplexLayout;
    private RadioGroup m_DuplexRadioGroup;
    private EditMetaUtility m_EditMetaUtility;
    HitiPPR_GetPSC m_GetPrintSatusCapabi;
    Handler m_Handler;
    private ImageButton m_InfoHODImageButton;
    private ShowMSGDialog m_LeaveDialog;
    private MakeEditPhoto m_MakeEditPhoto;
    private TextView m_MediaSizeTextView;
    private MobileUtility m_MobileUtility;
    private ArrayList<Integer> m_MultiSelStorageIdList;
    private LinearLayout m_NumberLinearLayout;
    private TextView m_PaperCountTextView;
    PHOTO_MODE m_PhotoMode;
    private ImageButton m_PrintCancelImageButton;
    private ImageButton m_PrintImageButton;
    PrintMode m_PrintMode;
    HitiPPR_SendPhotoPrinbiz m_PrintSendPhoto;
    private TextView m_PrintStatusTextView;
    private RelativeLayout m_QualityLayout;
    private RadioGroup m_QualityRadioGroup;
    HitiPPR_RecoveryPrinter m_RcoveryPrinter;
    private SDcardUtility m_SDcardUtility;
    private TextView m_SelNumTextView;
    private TextView m_SendedCountTextView;
    private ImageButton m_SettingImageButton;
    private RelativeLayout m_SharpenLayout;
    private SeekBar m_SharpenSeekBar;
    private ShowMSGDialog m_ShowMSGDialog;
    ShowPrinterList m_ShowPrinterList;
    private ProgressBar m_StatusProgressBar;
    private RadioGroup m_TextureRadioGroup;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;
    private boolean m_bBurnFChecked;
    private boolean m_bCnclButRcv;
    private boolean m_bConnErro;
    private boolean m_bFirstEdit;
    private boolean m_bID_SD_Route;
    private boolean m_bNeedUnsharpen;
    private boolean m_bNextPhoto;
    private boolean m_bOnMDNS;
    private boolean m_bPrintDone;
    private boolean m_bPrintStatus;
    private boolean m_bSelAll;
    private boolean m_bWaitForRecovery;
    private ArrayList<Integer> m_iBufCopiesList;
    private ArrayList<Integer> m_iEditCopiesList;
    private int m_iEditSumCopies;
    private ArrayList<Integer> m_iErrCopiesList;
    private ArrayList<Integer> m_iMultiSelPhotoIdList;
    private int m_iPathRoute;
    private ArrayList<Integer> m_iPhotoCopiesList;
    int m_iPort;
    private int m_iPrintedNumber;
    private int m_iSelRoute;
    private int m_iSumOfPhoto;
    private int m_iTotalPhotoCount;
    private ArrayList<String> m_strBufQueueList;
    private String m_strCurrentSSID;
    private ArrayList<String> m_strErrPhotoList;
    private String m_strLastSSID;
    private ArrayList<String> m_strPhotoPathList;
    String m_strPrinterFWversion;
    private String m_strProductID;
    private String m_strSecurityKey;
    private String m_strSelSSID;
    private String m_strSnapPath;
    private AutoWifiConnect m_wifiAutoConnect;

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.20 */
    class AnonymousClass20 implements OnClickListener {
        final /* synthetic */ Socket val$socket;

        AnonymousClass20(Socket socket) {
            this.val$socket = socket;
        }

        public void onClick(DialogInterface dialog, int which) {
            PrintViewActivity.this.SetPrintButtonStatus(true);
            PrintViewActivity.this.StartPrintForNotBurnFireWare(this.val$socket);
            dialog.cancel();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.3 */
    class C03333 implements View.OnClickListener {
        C03333() {
        }

        public void onClick(View v) {
            PrintViewActivity.this.m_ShowMSGDialog.ShowHODInfoDialog();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.4 */
    class C03344 implements View.OnClickListener {
        C03344() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_print_TARGET_start_print);
            PrintViewActivity.this.onStartPrint();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.5 */
    class C03355 implements View.OnClickListener {
        C03355() {
        }

        public void onClick(View v) {
            FlurryUtility.logEvent(FlurryLogString.UI_PAGE_print_TARGET_cancel_print);
            PrintViewActivity.this.ShowLeaveDialog();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.6 */
    class C03366 implements View.OnClickListener {
        C03366() {
        }

        public void onClick(View v) {
            PrintViewActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.7 */
    class C03377 implements View.OnClickListener {
        C03377() {
        }

        public void onClick(View v) {
            Intent intent = null;
            if (PrintViewActivity.this.m_iPathRoute == ControllerState.PLAY_PHOTO) {
                intent = new Intent(PrintViewActivity.this.getBaseContext(), SettingQuickPrintActivity.class);
            } else if (PrintViewActivity.this.m_iPathRoute == ControllerState.NO_PLAY_ITEM) {
                intent = new Intent(PrintViewActivity.this.getBaseContext(), SettingGeneralPrintActivity.class);
            }
            PrintViewActivity.this.startActivityForResult(intent, 62);
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.8 */
    class C03388 implements OnCheckedChangeListener {
        C03388() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case C0349R.id.m_BrightRadio /*2131427508*/:
                    PrintViewActivity.this.m_EditMetaUtility.SetPrintTexture(PrintViewActivity.GLOSSY_TEXTURE);
                case C0349R.id.m_ShadowRadio /*2131427509*/:
                    PrintViewActivity.this.m_EditMetaUtility.SetPrintTexture(PrintViewActivity.MATTE_TEXTURE);
                default:
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.9 */
    class C03399 implements OnCheckedChangeListener {
        C03399() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case C0349R.id.m_PageSingoRadio /*2131427512*/:
                    PrintViewActivity.this.m_EditMetaUtility.SetPrintDuplex(PrintViewActivity.GLOSSY_TEXTURE);
                case C0349R.id.m_PageDuplexRadio /*2131427513*/:
                    PrintViewActivity.this.m_EditMetaUtility.SetPrintDuplex(PrintViewActivity.MATTE_TEXTURE);
                default:
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.14 */
    class AnonymousClass14 implements MSGListener {
        final /* synthetic */ boolean val$bIsAgain;
        final /* synthetic */ boolean val$bIsPaperJam;

        AnonymousClass14(boolean z, boolean z2) {
            this.val$bIsPaperJam = z;
            this.val$bIsAgain = z2;
        }

        public void OKClick() {
            PrintViewActivity.this.SetPrintButtonStatus(true);
            if (this.val$bIsPaperJam) {
                PrintViewActivity.this.ToRunPaperJamProgress(this.val$bIsAgain);
            } else {
                PrintViewActivity.this.RecoveryPrint();
            }
        }

        public void CancelClick() {
            PrintViewActivity.this.SetPrintButtonStatus(false);
            PrintViewActivity.this.m_bCnclButRcv = true;
            PrintViewActivity.this.RecoveryPrint();
        }

        public void Close() {
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.1 */
    class C07761 implements INfcPreview {
        C07761() {
        }

        public void GetNfcData(NFCInfo nfcInfo) {
            PrintViewActivity.this.mNFCInfo = nfcInfo;
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.23 */
    class AnonymousClass23 extends QualityListener {
        final /* synthetic */ HitiPPR_PrinterCommand val$PrinterCommand;
        final /* synthetic */ int val$iCheckFlag;

        AnonymousClass23(int i, HitiPPR_PrinterCommand hitiPPR_PrinterCommand) {
            this.val$iCheckFlag = i;
            this.val$PrinterCommand = hitiPPR_PrinterCommand;
        }

        public void OKClick(boolean bCliked) {
            PrintViewActivity.this.m_bSelAll = bCliked;
            PrintViewActivity.this.m_MobileUtility.SendForQty(this.val$iCheckFlag, true, PrintViewActivity.this.m_bSelAll, this.val$PrinterCommand);
        }

        public void CancelClick(boolean bCliked) {
            PrintViewActivity.this.m_bSelAll = bCliked;
            PrintViewActivity.this.m_MobileUtility.SendForQty(this.val$iCheckFlag, false, PrintViewActivity.this.m_bSelAll, this.val$PrinterCommand);
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.24 */
    class AnonymousClass24 implements MSGListener {
        final /* synthetic */ int val$iType;

        AnonymousClass24(int i) {
            this.val$iType = i;
        }

        public void OKClick() {
            PrintViewActivity.this.SaveNewWifiInfo(this.val$iType);
        }

        public void CancelClick() {
            PrintViewActivity.this.BackOrLeave(this.val$iType);
        }

        public void Close() {
        }
    }

    /* renamed from: com.hiti.prinbiz.PrintViewActivity.2 */
    class C07772 extends DialogListener {
        C07772() {
        }

        public void SetNowConnSSID(String strNowSSID) {
            PrintViewActivity.this.PrintStausAndCount(String.valueOf(strNowSSID), -1);
            PrintViewActivity.this.m_strLastSSID = PrintViewActivity.this.m_strCurrentSSID;
            PrintViewActivity.this.m_WifiInfo.SetSSID(PrintViewActivity.this.m_strLastSSID);
            PrintViewActivity.this.m_WifiInfo.SaveGlobalVariable();
        }

        public void SetLastConnSSID(String strLastSSID) {
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.CONN_SEARCHING), -1);
            PrintViewActivity.this.m_wifiAutoConnect = new AutoWifiConnect(PrintViewActivity.this.getBaseContext(), PrintViewActivity.this.m_strLastSSID, PrintViewActivity.this.m_strSecurityKey);
            PrintViewActivity.this.m_wifiAutoConnect.execute(new Void[PrintViewActivity.GLOSSY_TEXTURE]);
        }

        public void LeaveConfirm() {
        }

        public void LeaveCancel() {
        }

        public void LeaveClose() {
        }

        public void CancelConnetion(ThreadMode strSSID) {
        }
    }

    class AutoWifiConnect extends WifiAutoConnect {
        public AutoWifiConnect(Context context, String strSSID, String strPassword) {
            super(context, strSSID, strPassword);
        }

        public void ConnectionSuccess() {
            ConnectionStop();
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.GetNowSSID(), -1);
            PrintViewActivity.this.SetPrintButtonStatus(false);
            PrintViewActivity.this.m_strLastSSID = PrintViewActivity.this.GetNowSSID();
            PrintViewActivity.this.m_strCurrentSSID = PrintViewActivity.this.m_strLastSSID;
        }

        public void ConnectionFail() {
            ConnectionStop();
            PrintViewActivity.this.ShowNoWiFiDialog();
        }

        public void ConnectionStop() {
            PrintViewActivity.this.m_ShowMSGDialog.StopWaitingDialog();
            SetStop(true);
        }
    }

    class MobileEditFlow extends MobileUtility {
        public MobileEditFlow(Context context, boolean bNeedUnSharpen) {
            super(context, bNeedUnSharpen);
        }

        public int[][] SetPrintoutSize(int iPaperType) {
            return PrintViewActivity.this.SetPrintoutFromat(iPaperType);
        }
    }

    class MobileNoEditFlow extends MobileUtility {
        public MobileNoEditFlow(Context context, ArrayList<String> strPhotoPathList, ArrayList<Integer> iPhotoCopiesList, boolean bNeedUnSharpen) {
            super(context, strPhotoPathList, iPhotoCopiesList, bNeedUnSharpen);
        }

        public int[][] SetPrintoutSize(int iPaperType) {
            return PrintViewActivity.this.SetPrintoutFromat(iPaperType);
        }
    }

    private class OnMobileListener implements IMobile {
        private OnMobileListener() {
        }

        public void GetMethodAndSharpen(int iMethod, int iSharpen) {
        }

        public void StartCheckPrintInfo() {
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.CHECK_PRINT_STATUS), -1);
        }

        public void EndCheckPrintInfo(String strVersion, String strProductID) {
            PrintViewActivity.this.m_strProductID = strProductID;
            PrintViewActivity.this.m_strPrinterFWversion = strVersion;
        }

        public void MediaSizeNotMatch(String strMSG) {
            PrintViewActivity.this.LOG.m383d(PrintViewActivity.this.TAG, "MediaSizeNotMatch: " + strMSG);
            if (PrintViewActivity.this.m_iPathRoute != ControllerState.PLAY_COUNT_STATE) {
                PrintViewActivity.this.m_SettingImageButton.setEnabled(true);
                PrintViewActivity.this.m_SettingImageButton.setVisibility(PrintViewActivity.GLOSSY_TEXTURE);
            }
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void InitialBusy(String strMSG) {
            PrintViewActivity.this.PrintStausAndCount(strMSG, -1);
            PrintViewActivity.this.SetPrintButtonStatus(false);
        }

        public void IsPrinterBusy(String strMSG) {
            PrintViewActivity.this.StatusCircle(true);
            PrintViewActivity.this.PrintStausAndCount(strMSG, -1);
        }

        public void PreparePhoto() {
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.PRINT_MAKE_IMAGE), -1);
        }

        public void SendingPhoto(String strMSG) {
            PrintViewActivity.this.StatusCircle(true);
            PrintViewActivity.this.PrintStausAndCount(strMSG, -1);
        }

        public void SendingPhotoDone(Socket socket, int iNum) {
            if (iNum > PrintViewActivity.this.m_iTotalPhotoCount) {
                iNum = PrintViewActivity.this.m_iTotalPhotoCount;
            }
            String message = iNum + "/" + PrintViewActivity.this.m_iTotalPhotoCount;
            PrintViewActivity.this.m_SendedCountTextView.setText(message);
            PrintViewActivity.this.updatePrintNotification(PrintViewActivity.this.getString(C0349R.string.SENDED_DONE_NUM) + message);
            if (PrintViewActivity.this.m_PrintMode == PrintMode.EditPrint && PrintViewActivity.this.m_MakeEditPhoto != null) {
                PrintViewActivity.this.m_MakeEditPhoto.MakeNextPhoto(socket);
            }
        }

        public void PrintDone(Socket socket, int iCopies, int iPrintedNumber, int iUnCleanNumber) {
            PrintViewActivity.this.StatusCircle(false);
            PrintViewActivity.this.LOG.m385i("PrintDone", "count=" + String.valueOf(iPrintedNumber));
            if (PrintViewActivity.this.m_iSumOfPhoto == iCopies) {
                PrintViewActivity.this.PrintDoneState(iCopies);
                PrintViewActivity.this.CleanModeCheck(iUnCleanNumber);
            } else if (PrintViewActivity.this.m_PrintMode == PrintMode.EditPrint) {
                PrintViewActivity.this.m_iEditSumCopies = iCopies;
                PrintViewActivity.this.m_iPrintedNumber = iPrintedNumber;
                if (!PrintViewActivity.this.HaveNoEditPhoto()) {
                    PrintViewActivity.this.m_MobileUtility.Stop();
                    PrintViewActivity.this.PrintDoneState(iCopies);
                    PrintViewActivity.this.CleanModeCheck(iUnCleanNumber);
                } else if (PrintViewActivity.this.m_iSelRoute == PrintViewActivity.LEAVE_TO_MAIN) {
                    PrintViewActivity.this.NextToNoEditPhoto(socket);
                }
            } else {
                PrintViewActivity.this.m_MobileUtility.Stop();
                PrintViewActivity.this.PrintDoneState(iCopies);
                PrintViewActivity.this.CleanModeCheck(iUnCleanNumber);
            }
        }

        public void ChangeCopies(String strMSG, int iCopies) {
            PrintViewActivity.this.PrintStausAndCount(strMSG, iCopies);
        }

        public void CancelPrint(String strMSG) {
            PrintViewActivity.this.StatusCircle(false);
            PrintViewActivity.this.PrintStausAndCount(strMSG, -1);
            PrintViewActivity.this.SetPrintButtonStatus(false);
            PrintViewActivity.this.updatePrintNotification(strMSG);
        }

        public void RecoveryDone(Socket socket) {
            PrintViewActivity.this.m_bWaitForRecovery = false;
            if (PrintViewActivity.this.m_bCnclButRcv) {
                PrintViewActivity.this.m_bCnclButRcv = false;
            } else {
                PrintViewActivity.this.m_MobileUtility.SendPhoto(socket, PrintViewActivity.this.IP, PrintViewActivity.this.m_iPort);
            }
        }

        public void StartBurnFW(Socket socket) {
            if (PrintViewActivity.this.m_bBurnFChecked) {
                PrintViewActivity.this.StartPrintForNotBurnFireWare(socket);
                return;
            }
            PrintViewActivity.this.m_bBurnFChecked = true;
            PrintViewActivity.this.ShowBurnFirwareDialog(socket);
        }

        public void ErrorOccur(String strErr) {
            PrintViewActivity.this.StatusCircle(false);
            if (strErr.equals(PrintViewActivity.this.getString(C0349R.string.ERROR_PRINTER_0001))) {
                PrintViewActivity.this.ShowSimpleErrMessage(strErr);
                return;
            }
            PrintViewActivity.this.m_MobileUtility.SetStop(true);
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strErr);
        }

        public void ErrorOccurDueToPrinter(String strErr) {
            strErr = PrintViewActivity.this.ErrorMessageCheck(strErr);
            PrintViewActivity.this.StatusCircle(false);
            PrintViewActivity.this.m_MobileUtility.SetStop(true);
            PrintViewActivity.this.m_bWaitForRecovery = true;
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(strErr, false);
        }

        public void ErrorTimeOut(String strMSG) {
            PrintViewActivity.this.m_MobileUtility.SetStop(true);
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void PhotoLawQty(String strMSG, int iType, HitiPPR_PrinterCommand m_PrintCommand) {
            PrintViewActivity.this.updatePrintNotification(strMSG);
            PrintViewActivity.this.ShowLowQalityDialog(strMSG, iType, m_PrintCommand);
        }

        public void ErrorBitmap(String strErr) {
            PrintViewActivity.this.ShowBitmapErrorAlertDialog(strErr);
        }

        public void PaperJamDone() {
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(PrintViewActivity.this.getString(C0349R.string.PAPER_JAM_DONE), false);
        }

        public void PaperJamAgain(String strError) {
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(strError, true);
        }
    }

    private class OnSDcardListener implements ISDcard {
        private OnSDcardListener() {
        }

        public void SetPrintout(String strPrintout) {
            PrintViewActivity.this.setPrintout(strPrintout);
        }

        public void StartCheckPrintInfo() {
            PrintViewActivity.this.StatusCircle(true);
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.CHECK_PRINT_STATUS), -1);
        }

        public void EndCheckPrintInfo(String strPrintFWVersion, String strProductID) {
            PrintViewActivity.this.StatusCircle(false);
            PrintViewActivity.this.m_strProductID = strProductID;
            PrintViewActivity.this.m_strPrinterFWversion = strPrintFWVersion;
        }

        public void StartPrintFromSDcard(int iCurrentPrintCount, int iPrintCount) {
            String text = String.valueOf(iCurrentPrintCount) + "/" + String.valueOf(iPrintCount);
            PrintViewActivity.this.m_PaperCountTextView.setText(text);
            PrintViewActivity.this.updatePrintNotification(text);
        }

        public void OnPrintingStatusChange(String strMSG) {
            strMSG = PrintViewActivity.this.ErrorMessageCheck(strMSG);
            PrintViewActivity.this.StatusCircle(true);
            PrintViewActivity.this.SetPrintButtonStatus(true);
            PrintViewActivity.this.PrintStausAndCount(strMSG, -1);
        }

        public void CancelPrinting() {
        }

        public void ErrorOccurDuetoPrinter(String strErr) {
            PrintViewActivity.this.StatusCircle(false);
            if (strErr.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
                PrintViewActivity.this.ShowSimpleErrMessage(PrintViewActivity.this.getString(C0349R.string.ERROR_PRINTER_0001));
                return;
            }
            strErr = PrintViewActivity.this.ErrorMessageCheck(strErr) + "\n" + PrintViewActivity.this.getString(C0349R.string.ERROR_PRINTER_CHECKED);
            PrintViewActivity.this.m_SDcardUtility.SetStop(true);
            PrintViewActivity.this.m_bWaitForRecovery = true;
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(strErr, false);
        }

        public void ErrorOccur(String strErr) {
            strErr = PrintViewActivity.this.ErrorMessageCheck(strErr);
            if (strErr.equals(PrintViewActivity.this.getString(C0349R.string.ERROR_BUSY_INITIAL)) || strErr.equals(PrintViewActivity.this.getString(C0349R.string.ERROR_PRINTER_BUSY))) {
                PrintViewActivity.this.PrintStausAndCount(strErr, -1);
            } else {
                PrintViewActivity.this.ShowConnectErrorAlertDialog(strErr);
            }
        }

        public void SizeNoMatch(String strMSG) {
            strMSG = PrintViewActivity.this.ErrorMessageCheck(strMSG);
            PrintViewActivity.this.StatusCircle(false);
            PrintViewActivity.this.m_SDcardUtility.SetStop(true);
            PrintViewActivity.this.SetPrintButtonStatus(false);
            PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.PRINT_PAUSE), -1);
            PrintViewActivity.this.m_SettingImageButton.setEnabled(true);
            PrintViewActivity.this.m_SettingImageButton.setVisibility(PrintViewActivity.GLOSSY_TEXTURE);
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void RecoveryDone() {
            PrintViewActivity.this.m_bWaitForRecovery = false;
            if (PrintViewActivity.this.m_bCnclButRcv) {
                PrintViewActivity.this.m_bCnclButRcv = false;
            } else {
                PrintViewActivity.this.m_SDcardUtility.SendPhoto();
            }
        }

        public void ErrorTimeOut(String strMSG) {
            PrintViewActivity.this.m_SDcardUtility.SetStop(true);
            PrintViewActivity.this.SetPrintButtonStatus(false);
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void OnPrintingCountChange(String strMSG, int iCpDone) {
            PrintViewActivity.this.StatusCircle(true);
            PrintViewActivity.this.PrintStausAndCount(strMSG, iCpDone);
        }

        public void OnPrintSendedNum(String strNum) {
            if (PrintViewActivity.this.m_iPrintedNumber > 0) {
                strNum = String.valueOf(PrintViewActivity.this.m_iPrintedNumber + Integer.parseInt(strNum));
            }
            String text = strNum + "/" + PrintViewActivity.this.m_iTotalPhotoCount;
            PrintViewActivity.this.m_SendedCountTextView.setText(text);
            PrintViewActivity.this.updatePrintNotification(PrintViewActivity.this.getString(C0349R.string.SENDED_DONE_NUM) + text);
        }

        public void StartBurnFW(Socket socket) {
            if (PrintViewActivity.this.m_bBurnFChecked) {
                PrintViewActivity.this.StartPrintForNotBurnFireWare(socket);
                return;
            }
            PrintViewActivity.this.m_bBurnFChecked = true;
            PrintViewActivity.this.ShowBurnFirwareDialog(socket);
        }

        public ArrayList<Integer> StorageError(String strErr) {
            strErr = PrintViewActivity.this.ErrorMessageCheck(strErr);
            int iSID = ((Integer) PrintViewActivity.this.m_MultiSelStorageIdList.get(PrintViewActivity.GLOSSY_TEXTURE)).intValue() + PrintViewActivity.MATTE_TEXTURE;
            int iSize = PrintViewActivity.this.m_MultiSelStorageIdList.size();
            PrintViewActivity.this.m_MultiSelStorageIdList.clear();
            for (int i = PrintViewActivity.GLOSSY_TEXTURE; i < iSize; i += PrintViewActivity.MATTE_TEXTURE) {
                PrintViewActivity.this.m_MultiSelStorageIdList.add(Integer.valueOf(iSID));
            }
            PrintViewActivity.this.ShowConnectErrorAlertDialog(strErr);
            return PrintViewActivity.this.m_MultiSelStorageIdList;
        }

        public void PrintDone(int iCpDone, int iUnCleanNumber) {
            PrintViewActivity.this.PrintDoneState(iCpDone);
            PrintViewActivity.this.CleanModeCheck(iUnCleanNumber);
        }

        public void PaperJamDone() {
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(PrintViewActivity.this.getString(C0349R.string.PAPER_JAM_DONE), false);
        }

        public void PaperJamAgain(String strError) {
            PrintViewActivity.this.ShowPrinterErrorAlertDialog(PrintViewActivity.this.ErrorMessageCheck(strError), true);
        }
    }

    class PermissionCallback implements IPermission {
        PermissionCallback() {
        }

        public void GetGrantState(int iPermissionCode, int askState) {
            if (iPermissionCode == 4 && askState == PrintViewActivity.LEAVE_TO_PHOTO) {
                PrintViewActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                PrintViewActivity.this.onStartPrint();
            }
        }

        public int SetLatterState(int iPermissionCode, int askState) {
            return PrintViewActivity.MATTE_TEXTURE;
        }
    }

    public PrintViewActivity() {
        this.m_PrintCancelImageButton = null;
        this.m_PrintImageButton = null;
        this.m_BackImageButton = null;
        this.m_InfoHODImageButton = null;
        this.m_SettingImageButton = null;
        this.m_SelNumTextView = null;
        this.m_MediaSizeTextView = null;
        this.m_PrintStatusTextView = null;
        this.m_PaperCountTextView = null;
        this.m_SendedCountTextView = null;
        this.m_SharpenLayout = null;
        this.m_QualityLayout = null;
        this.m_DuplexLayout = null;
        this.m_NumberLinearLayout = null;
        this.m_SharpenSeekBar = null;
        this.m_TextureRadioGroup = null;
        this.m_DuplexRadioGroup = null;
        this.m_QualityRadioGroup = null;
        this.m_CloseListProgressBar = null;
        this.m_StatusProgressBar = null;
        this.m_LeaveDialog = null;
        this.m_ShowMSGDialog = null;
        this.m_bPrintStatus = false;
        this.m_iSelRoute = GLOSSY_TEXTURE;
        this.m_PrintSendPhoto = null;
        this.m_GetPrintSatusCapabi = null;
        this.m_RcoveryPrinter = null;
        this.m_strLastSSID = null;
        this.m_strCurrentSSID = null;
        this.m_strSecurityKey = null;
        this.m_wifiAutoConnect = null;
        this.m_bSelAll = false;
        this.m_strPhotoPathList = null;
        this.m_strBufQueueList = null;
        this.m_strErrPhotoList = null;
        this.m_iPhotoCopiesList = null;
        this.m_iBufCopiesList = null;
        this.m_iErrCopiesList = null;
        this.m_iEditCopiesList = null;
        this.m_iMultiSelPhotoIdList = null;
        this.m_MultiSelStorageIdList = null;
        this.m_bPrintDone = false;
        this.m_bBurnFChecked = false;
        this.m_iSumOfPhoto = GLOSSY_TEXTURE;
        this.m_iTotalPhotoCount = GLOSSY_TEXTURE;
        this.m_iEditSumCopies = GLOSSY_TEXTURE;
        this.m_iPrintedNumber = GLOSSY_TEXTURE;
        this.dm = null;
        this.m_WifiInfo = null;
        this.m_iPathRoute = GLOSSY_TEXTURE;
        this.m_strProductID = null;
        this.m_SDcardUtility = null;
        this.m_MobileUtility = null;
        this.m_EditMetaUtility = null;
        this.m_bID_SD_Route = false;
        this.m_bWaitForRecovery = false;
        this.m_bCnclButRcv = false;
        this.m_bNeedUnsharpen = true;
        this.m_bNextPhoto = false;
        this.m_bOnMDNS = false;
        this.m_bConnErro = false;
        this.m_strSelSSID = null;
        this.m_Handler = new Handler();
        this.m_ShowPrinterList = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_PhotoMode = PHOTO_MODE.MODE_DEFAULT;
        this.m_strPrinterFWversion = null;
        this.m_MakeEditPhoto = null;
        this.m_bFirstEdit = false;
        this.mNFCInfo = null;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.LOG = null;
        this.TAG = getClass().getSimpleName();
        this.m_strSnapPath = null;
        this.mPermissionAsk = null;
        this.mPrintConnection = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(MATTE_TEXTURE);
        setContentView(C0349R.layout.activity_printview);
        this.LOG = new LogManager(GLOSSY_TEXTURE);
        this.m_strBufQueueList = new ArrayList();
        this.m_strErrPhotoList = new ArrayList();
        this.m_iPhotoCopiesList = new ArrayList();
        this.m_iBufCopiesList = new ArrayList();
        this.m_iErrCopiesList = new ArrayList();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int iScreenWidth = dm.widthPixels;
        UploadService.StopUploadService(this, UploadService.class);
        BitmapMonitor.ShowHeapSize(this);
        this.LOG.m385i(this.TAG, "onCreate " + savedInstanceState);
        CheckPrintMode();
        SetMSGDialog();
        this.mPermissionAsk = new PermissionAsk();
        EditMeta mEditMeta = GetBundle(getIntent().getExtras());
        SetView();
        FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);
        if (mEditMeta == null || !mEditMeta.GetIsEditList().contains(Boolean.valueOf(true))) {
            SetNoEditPrintFlow(this.m_PrintMode);
        } else {
            SetEditPrintFlow(mEditMeta, iScreenWidth);
        }
        CheckWifi();
    }

    void CheckPrintMode() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String strPrintMode = bundle.getString(JumpBundleMessage.BUNDLE_MSG_PRINT_MODE);
            if (strPrintMode != null) {
                this.m_PrintMode = PrintMode.valueOf(strPrintMode);
            }
            if (this.m_PrintMode == PrintMode.Snap || this.m_PrintMode == PrintMode.NFC || this.m_PrintMode == PrintMode.CloudALbum) {
                this.m_iSelRoute = MATTE_TEXTURE;
            }
        }
    }

    protected void onStart() {
        FlurryUtility.onStartSession(this);
        super.onStart();
    }

    protected void onResume() {
        this.LOG.m386v(this.TAG, "onResume");
        super.onResume();
        String strSSID = GetNowSSID();
        if (!strSSID.isEmpty()) {
            this.m_PrintStatusTextView.setText(strSSID);
        }
        if (!this.mPermissionAsk.JustAfterCheck(4, new PermissionCallback())) {
            getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            NFCInfo.CheckNFC(new NfcListener(), (Activity) this, new C07761());
        }
    }

    protected void onPause() {
        this.LOG.m386v(this.TAG, "onPause");
        if (this.m_ShowPrinterList != null) {
            this.m_ShowPrinterList.ListClose();
        }
        if (this.mNFCInfo.mNfcAdapter != null) {
            NFCInfo.disableForegroundDispatch(this.mNFCInfo, this);
        }
        super.onPause();
    }

    protected void onStop() {
        this.LOG.m383d(this.TAG, "onStop");
        FlurryUtility.onEndSession(this);
        super.onStop();
    }

    protected void onDestroy() {
        this.LOG.m386v(this.TAG, "onDestroy PrintMode: " + this.m_PrintMode);
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            if (this.m_MobileUtility != null) {
                this.m_MobileUtility.Stop();
            }
        } else if (this.m_SDcardUtility != null) {
            this.m_SDcardUtility.Stop();
        }
        stopPrintService();
        if (this.m_ShowPrinterList != null) {
            this.m_ShowPrinterList.Stop();
        }
        super.onDestroy();
    }

    private void SetMSGDialog() {
        this.dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.dm);
        this.m_ShowMSGDialog = new ShowMSGDialog(this, false);
        this.m_ShowMSGDialog.SetDialogListener(new C07772());
        this.m_LeaveDialog = new ShowMSGDialog(this, false);
    }

    private void NextToNoEditPhoto(Socket socket) {
        this.LOG.m386v(this.TAG, "AddNoEditPhoto Route: " + this.m_iSelRoute);
        this.m_PrintMode = PrintMode.NormalPrint;
        if (this.m_iSelRoute != MATTE_TEXTURE) {
            this.m_MobileUtility.SetStop(true);
            this.m_SDcardUtility = new SDcardUtility(this, this.m_iMultiSelPhotoIdList, this.m_MultiSelStorageIdList, this.m_iPhotoCopiesList);
            this.m_SDcardUtility.SetPrintingPhotoListener(new OnSDcardListener());
            this.m_SDcardUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
            this.m_SDcardUtility.StartPrintAfterEditFlow(socket, this.m_EditMetaUtility.GetModel());
        } else if (this.m_MobileUtility != null) {
            this.m_MobileUtility.AddNoEditPhotoList(this.m_strPhotoPathList, this.m_iPhotoCopiesList);
        }
    }

    private void SetNoEditPrintFlow(PrintMode mMode) {
        this.LOG.m385i("SetNoEditPrintFlow", "Mode: " + mMode);
        if (this.m_iSelRoute == MATTE_TEXTURE) {
            if (this.m_MobileUtility != null) {
                this.m_MobileUtility.SetStop(true);
            }
            this.m_MobileUtility = new MobileNoEditFlow(this, this.m_strPhotoPathList, this.m_iPhotoCopiesList, this.m_bNeedUnsharpen);
            this.m_MobileUtility.SetMobileListener(new OnMobileListener());
            return;
        }
        this.m_SDcardUtility = new SDcardUtility(this, this.m_iMultiSelPhotoIdList, this.m_MultiSelStorageIdList, this.m_iPhotoCopiesList);
        this.m_SDcardUtility.SetPrintingPhotoListener(new OnSDcardListener());
    }

    private void SetView() {
        this.m_PrintCancelImageButton = (ImageButton) findViewById(C0349R.id.m_PrintCancelImageButton);
        this.m_PrintImageButton = (ImageButton) findViewById(C0349R.id.m_PrintCheckImageButton);
        this.m_SettingImageButton = (ImageButton) findViewById(C0349R.id.m_SettingImageButton);
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackButton);
        this.m_TextureRadioGroup = (RadioGroup) findViewById(C0349R.id.m_TextureRadioGroup);
        this.m_DuplexLayout = (RelativeLayout) findViewById(C0349R.id.m_DuplexLayout);
        this.m_DuplexRadioGroup = (RadioGroup) findViewById(C0349R.id.m_DuplexRadioGroup);
        this.m_QualityLayout = (RelativeLayout) findViewById(C0349R.id.m_QualityLayout);
        this.m_QualityRadioGroup = (RadioGroup) findViewById(C0349R.id.m_QualityRadioGroup);
        RadioButton m_RefineRadio = (RadioButton) findViewById(C0349R.id.m_RefineRadio);
        this.m_SelNumTextView = (TextView) findViewById(C0349R.id.m_SelNumTextView);
        this.m_MediaSizeTextView = (TextView) findViewById(C0349R.id.m_MediaSizeTextView);
        this.m_PrintStatusTextView = (TextView) findViewById(C0349R.id.m_PrintStatusTextView);
        this.m_SendedCountTextView = (TextView) findViewById(C0349R.id.m_SendedCountTextView);
        this.m_PaperCountTextView = (TextView) findViewById(C0349R.id.m_PaperCountTextView);
        this.m_CloseListProgressBar = (ProgressBar) findViewById(C0349R.id.m_CloseListProgressBar);
        this.m_SharpenLayout = (RelativeLayout) findViewById(C0349R.id.m_SharpenLayout);
        this.m_NumberLinearLayout = (LinearLayout) findViewById(C0349R.id.m_NumberLinearLayout);
        this.m_SharpenSeekBar = (SeekBar) findViewById(C0349R.id.m_SharpenSeekBar);
        this.m_InfoHODImageButton = (ImageButton) findViewById(C0349R.id.m_InfoHODImageView);
        this.m_InfoHODImageButton.setOnClickListener(new C03333());
        this.m_PaperCountTextView.setText("0/" + String.valueOf(this.m_iSumOfPhoto));
        this.m_SendedCountTextView.setText("0/" + String.valueOf(this.m_iTotalPhotoCount));
        this.m_SelNumTextView.setText(String.valueOf(this.m_iSumOfPhoto));
        String strPrinterModel = this.m_EditMetaUtility.GetModel();
        if (strPrinterModel.equals(WirelessType.TYPE_P310W)) {
            m_RefineRadio.setText(getString(C0349R.string.QTY_HOD));
        }
        if (strPrinterModel.equals(WirelessType.TYPE_P520L) || strPrinterModel.equals(WirelessType.TYPE_P530D)) {
            this.m_QualityLayout.setVisibility(8);
        }
        if (strPrinterModel.equals(WirelessType.TYPE_P750L)) {
            this.m_InfoHODImageButton.setVisibility(8);
        }
        if (strPrinterModel.equals(WirelessType.TYPE_P530D)) {
            this.m_DuplexLayout.setVisibility(GLOSSY_TEXTURE);
            InitDuplex();
        }
        InitPrintOut();
        InitSharpenSeekBar(strPrinterModel);
        InitTextureUI();
        InitMethodUI(strPrinterModel);
        this.m_PrintImageButton.setOnClickListener(new C03344());
        this.m_PrintCancelImageButton.setOnClickListener(new C03355());
        this.m_BackImageButton.setOnClickListener(new C03366());
        this.m_SettingImageButton.setEnabled(false);
        this.m_SettingImageButton.setOnClickListener(new C03377());
    }

    private void InitPrintOut() {
        setPrintout(PrinterInfo.GetPrintoutItem(this, this.m_EditMetaUtility.GetServerPaperType()));
    }

    private void setPrintout(String printout) {
        if (this.m_EditMetaUtility.GetServerPaperType() == 6) {
            printout = "4x6";
        }
        this.m_MediaSizeTextView.setText(printout);
    }

    private void InitTextureUI() {
        this.m_TextureRadioGroup.setOnCheckedChangeListener(new C03388());
        switch (this.m_EditMetaUtility.GetTexturePref()) {
            case GLOSSY_TEXTURE /*0*/:
                this.m_TextureRadioGroup.check(C0349R.id.m_BrightRadio);
            case MATTE_TEXTURE /*1*/:
                this.m_TextureRadioGroup.check(C0349R.id.m_ShadowRadio);
            default:
        }
    }

    private void InitDuplex() {
        this.m_DuplexRadioGroup.setOnCheckedChangeListener(new C03399());
        if (this.m_EditMetaUtility.GetPrintDuplexPref() == 0) {
            this.m_DuplexRadioGroup.check(C0349R.id.m_PageSingoRadio);
        } else {
            this.m_DuplexRadioGroup.check(C0349R.id.m_PageDuplexRadio);
        }
    }

    private void InitMethodUI(String strPrinterModel) {
        if (!strPrinterModel.equals(WirelessType.TYPE_P520L) && !strPrinterModel.equals(WirelessType.TYPE_P530D)) {
            this.m_QualityRadioGroup.setVisibility(GLOSSY_TEXTURE);
            this.m_QualityRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case C0349R.id.m_NormalRadio /*2131427517*/:
                            PrintViewActivity.this.m_EditMetaUtility.SetPrintMethod(PrintViewActivity.GLOSSY_TEXTURE);
                        case C0349R.id.m_RefineRadio /*2131427518*/:
                            PrintViewActivity.this.m_EditMetaUtility.SetPrintMethod(PrintViewActivity.MATTE_TEXTURE);
                        default:
                    }
                }
            });
            switch (this.m_EditMetaUtility.GetMethodPref()) {
                case GLOSSY_TEXTURE /*0*/:
                    this.m_QualityRadioGroup.check(C0349R.id.m_NormalRadio);
                case MATTE_TEXTURE /*1*/:
                    this.m_QualityRadioGroup.check(C0349R.id.m_RefineRadio);
                default:
            }
        }
    }

    private void InitSharpenSeekBar(String strPrinterModel) {
        this.m_SharpenLayout.setVisibility(8);
        if (strPrinterModel.equals(WirelessType.TYPE_P310W)) {
            this.m_SharpenLayout.setVisibility(GLOSSY_TEXTURE);
            int width = getResources().getDrawable(C0349R.drawable.scale).getIntrinsicWidth();
            this.m_NumberLinearLayout.getLayoutParams().width = (width * 9) / 8;
            this.m_SharpenSeekBar.getLayoutParams().width = width;
            this.m_SharpenSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    PrintViewActivity.this.m_EditMetaUtility.SetPrintSharpen(progress);
                    PrintViewActivity.this.SetNumberForSeekBar(progress);
                }
            });
            int iSharpen = this.m_EditMetaUtility.GetSharpenPref();
            this.m_SharpenSeekBar.setProgress(iSharpen);
            SetNumberForSeekBar(iSharpen);
        }
    }

    private void SetNumberForSeekBar(int iSelected) {
        this.m_NumberLinearLayout.removeAllViews();
        for (int i = GLOSSY_TEXTURE; i <= 8; i += MATTE_TEXTURE) {
            TextView numTextView = new TextView(this);
            numTextView.setBackgroundColor(getResources().getColor(17170445));
            numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_GRAY));
            if (i == iSelected) {
                numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_ORANGE));
            } else {
                numTextView.setTextColor(getResources().getColor(C0349R.color.SETTING_GRAY));
            }
            numTextView.setLayoutParams(new LayoutParams(-1, -2, 1.0f));
            numTextView.setTextSize(13.0f);
            numTextView.setPadding(5, GLOSSY_TEXTURE, 5, GLOSSY_TEXTURE);
            numTextView.setGravity(GravityCompat.START);
            numTextView.setText(String.valueOf(i - 1));
            this.m_NumberLinearLayout.addView(numTextView);
        }
        this.m_NumberLinearLayout.invalidate();
    }

    private boolean NumberCheck() {
        if (this.m_EditMetaUtility.GetPrintDuplex() == 0) {
            if (this.m_EditMetaUtility.GetServerPaperType() == 6 && this.m_iPhotoCopiesList.size() % LEAVE_TO_MAIN != 0) {
                return false;
            }
        } else if (this.m_EditMetaUtility.GetServerPaperType() == 6) {
            if (this.m_iPhotoCopiesList.size() % 4 != 0) {
                return false;
            }
        } else if (this.m_iPhotoCopiesList.size() % LEAVE_TO_MAIN != 0) {
            return false;
        }
        return true;
    }

    private void onStartPrint() {
        if (!NumberCheck()) {
            ShowConnectErrorAlertDialog(getString(C0349R.string.DUPLEX_2UP_NUMBER_HINT));
        } else if (this.m_bConnErro) {
            this.m_strSelSSID = null;
            ShowPrinterListDialog();
        } else if (this.m_strSelSSID == null || !GetNowSSID().contains(this.m_strSelSSID)) {
            ShowPrinterListDialog();
        } else {
            startPrintService();
        }
    }

    private EditMeta GetBundle(Bundle bundle) {
        if (bundle != null) {
            this.m_bNeedUnsharpen = !bundle.getBoolean(JumpBundleMessage520.BUNDLE_MSG_COLLAGE_NO_UNSHARPEN);
            this.IP = bundle.getString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP);
            this.m_iPort = bundle.getInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT);
            this.m_strSnapPath = bundle.getString(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_PICTURE_PATH);
            if (this.m_PrintMode == PrintMode.Snap && this.m_strSnapPath != null) {
                this.m_strPhotoPathList = new ArrayList();
                this.m_strPhotoPathList.add(this.m_strSnapPath);
                this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
                this.m_iTotalPhotoCount = MATTE_TEXTURE;
                this.m_iSumOfPhoto = MATTE_TEXTURE;
                this.m_iPathRoute = ControllerState.PLAY_PHOTO;
                this.m_iSelRoute = MATTE_TEXTURE;
            }
        }
        this.m_EditMetaUtility = new EditMetaUtility(this);
        if (this.m_PrintMode == PrintMode.Snap) {
            return null;
        }
        int i;
        if (this.m_PhotoMode == PHOTO_MODE.MODE_OUTPHOTO) {
            i = ControllerState.NO_PLAY_ITEM;
        } else {
            i = this.m_EditMetaUtility.GetPathRoute();
        }
        this.m_iPathRoute = i;
        if (this.m_PhotoMode == PHOTO_MODE.MODE_OUTPHOTO) {
            i = MATTE_TEXTURE;
        } else {
            i = EditMetaUtility.GetSrcRoute(this);
        }
        this.m_iSelRoute = i;
        if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE && this.m_iSelRoute == LEAVE_TO_MAIN) {
            this.m_bID_SD_Route = true;
            this.m_iSelRoute = MATTE_TEXTURE;
        }
        EditMeta multiData = this.m_EditMetaUtility.GetEditMeta(this.m_iSelRoute);
        if (this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_strPhotoPathList = multiData.GetMobilePathList();
        } else {
            this.m_iMultiSelPhotoIdList = multiData.GetSDcardIDList();
            this.m_MultiSelStorageIdList = multiData.GetSDcardSIDList();
        }
        if (this.m_iPathRoute != ControllerState.PLAY_PHOTO) {
            this.m_iPhotoCopiesList = multiData.GetCopiesList();
        } else if (this.m_iSelRoute == MATTE_TEXTURE) {
            for (i = GLOSSY_TEXTURE; i < this.m_strPhotoPathList.size(); i += MATTE_TEXTURE) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
            }
        } else {
            for (i = GLOSSY_TEXTURE; i < this.m_iMultiSelPhotoIdList.size(); i += MATTE_TEXTURE) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
            }
        }
        SetSumCopies();
        return multiData;
    }

    private void SetEditPrintFlow(EditMeta mEditMeta, int iScreenWidth) {
        this.m_PrintMode = PrintMode.EditPrint;
        this.m_MobileUtility = new MobileEditFlow(this, this.m_bNeedUnsharpen);
        this.m_MobileUtility.SetMobileListener(new OnMobileListener());
        this.m_MakeEditPhoto = new MakeEditPhoto(this, iScreenWidth);
        this.m_MakeEditPhoto.SetListener(new MakePhotoListener() {
            private static final int MAKE_ALL_EDIT_PHOTO_DONE = -1;

            public void MakeOneEditPhotoDone(Socket socket, int iPos, String strSavePhoto) {
                if (iPos != MAKE_ALL_EDIT_PHOTO_DONE) {
                    PrintViewActivity.this.m_MobileUtility.AddPhoto(strSavePhoto, ((Integer) PrintViewActivity.this.m_iEditCopiesList.get(iPos)).intValue());
                    if (PrintViewActivity.this.m_bFirstEdit) {
                        PrintViewActivity.this.SetPrintButtonStatus(false);
                        PrintViewActivity.this.m_bFirstEdit = false;
                    }
                } else if (PrintViewActivity.this.m_PrintMode == PrintMode.EditPrint && PrintViewActivity.this.HaveNoEditPhoto() && PrintViewActivity.this.m_iSelRoute == PrintViewActivity.MATTE_TEXTURE) {
                    PrintViewActivity.this.NextToNoEditPhoto(socket);
                }
            }

            public void onMakePhotoError() {
                PrintViewActivity.this.SetPrintButtonStatus(false);
                if (PrintViewActivity.this.m_bFirstEdit) {
                    PrintViewActivity.this.m_bFirstEdit = false;
                }
            }

            public int[] SetPrintout(int iPaperType) {
                return PrintViewActivity.this.SetPrintoutFromat(iPaperType)[PrintViewActivity.GLOSSY_TEXTURE];
            }
        });
        SetPrintButtonStatus(true);
        this.m_bFirstEdit = true;
        SplitNoEditPhotoListByMobileFlow(mEditMeta.GetIsEditList());
        this.m_MakeEditPhoto.SetStop(false);
        if (this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MakeEditPhoto.SetPhotoPath(mEditMeta.GetMobilePathList());
        } else {
            this.m_MakeEditPhoto.SetPhotoPath(mEditMeta.GetFetchPathList());
        }
        this.m_MakeEditPhoto.SetIsEditAndIsVerticalAndXMLpath(mEditMeta.GetIsEditList(), mEditMeta.GetIsVerticalList(), mEditMeta.GetXMLList());
        this.m_MakeEditPhoto.StartMake(null);
    }

    private void InitMobileQualityPre() {
        JumpPreferenceKey pef = new JumpPreferenceKey(this);
        pef.SetPreference(JumpPreferenceKey.SIZE_DOWN_SEL_ALL, false);
        pef.SetPreference(JumpPreferenceKey.SCALL_SEL_ALL, false);
        pef.SetPreference(JumpPreferenceKey.LOW_QUALITY_SEL_ALL, false);
    }

    private void SplitNoEditPhotoListByMobileFlow(ArrayList<Boolean> m_bIsEditList) {
        this.m_iEditCopiesList = new ArrayList();
        Iterator it = this.m_iPhotoCopiesList.iterator();
        while (it.hasNext()) {
            this.m_iEditCopiesList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        for (int i = m_bIsEditList.size() - 1; i >= 0; i--) {
            if (((Boolean) m_bIsEditList.get(i)).booleanValue()) {
                this.m_iPhotoCopiesList.remove(i);
                if (this.m_iSelRoute == MATTE_TEXTURE) {
                    this.m_strPhotoPathList.remove(i);
                } else {
                    this.m_iMultiSelPhotoIdList.remove(i);
                    this.m_MultiSelStorageIdList.remove(i);
                }
            }
        }
    }

    private boolean HaveNoEditPhoto() {
        if (this.m_iPhotoCopiesList.size() > 0) {
            return true;
        }
        return false;
    }

    private void SetSumCopies() {
        this.m_iTotalPhotoCount = this.m_iPhotoCopiesList.size();
        Iterator it = this.m_iPhotoCopiesList.iterator();
        while (it.hasNext()) {
            this.m_iSumOfPhoto += ((Integer) it.next()).intValue();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.LOG.m383d(this.TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                this.m_ShowMSGDialog.StopMSGDialog();
            case JumpInfo.RESULT_PRINT_VIEW_ACTIVITY /*62*/:
                InitPrintOut();
                if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
                    this.m_MobileUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
                } else {
                    this.m_SDcardUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
                }
                this.m_SettingImageButton.setVisibility(4);
                this.m_SettingImageButton.setEnabled(false);
            case JumpInfo.REQUEST_SETTING_CLEAN_MODE /*74*/:
                ShowPrintDoneDialog();
            case FingerClient.DEFAULT_PORT /*79*/:
                this.LOG.m383d(this.TAG, "REQUEST_IGNORE_BATTERY_OPTIMIZATION " + resultCode);
                GeneralPrint(null);
            default:
        }
    }

    private void SetPrintButtonStatus(boolean status) {
        this.m_bPrintStatus = status;
        if (this.m_bPrintStatus) {
            this.m_PrintImageButton.setEnabled(false);
            this.m_PrintImageButton.setAlpha(0.4f);
            this.m_BackImageButton.setEnabled(false);
            this.m_BackImageButton.setAlpha(0.4f);
            this.m_TextureRadioGroup.setEnabled(false);
            this.m_TextureRadioGroup.setAlpha(0.4f);
            this.m_QualityRadioGroup.setEnabled(false);
            this.m_QualityRadioGroup.setAlpha(0.4f);
            this.m_SharpenSeekBar.setEnabled(false);
            this.m_SharpenSeekBar.setAlpha(0.4f);
            this.m_DuplexLayout.setEnabled(false);
            this.m_DuplexLayout.setAlpha(0.4f);
            SetNumberForSeekBar(-1);
            return;
        }
        this.m_PrintImageButton.setEnabled(true);
        this.m_PrintImageButton.setAlpha(1.0f);
        this.m_BackImageButton.setEnabled(true);
        this.m_BackImageButton.setAlpha(1.0f);
        this.m_TextureRadioGroup.setEnabled(true);
        this.m_TextureRadioGroup.setAlpha(1.0f);
        StatusCircle(false);
        this.m_QualityRadioGroup.setEnabled(true);
        this.m_QualityRadioGroup.setAlpha(1.0f);
        this.m_SharpenSeekBar.setEnabled(true);
        this.m_SharpenSeekBar.setAlpha(1.0f);
        this.m_DuplexLayout.setEnabled(true);
        this.m_DuplexLayout.setAlpha(1.0f);
        SetNumberForSeekBar(this.m_SharpenSeekBar.getProgress());
    }

    private void PrintStausAndCount(String strStatus, int count) {
        String strCnt = XmlPullParser.NO_NAMESPACE;
        if (!(strStatus == null || strStatus == "plus" || strStatus == "next")) {
            this.m_PrintStatusTextView.setText(strStatus);
            updatePrintNotification(strStatus);
        }
        if (count != -1) {
            if (this.m_iEditSumCopies > 0) {
                count += this.m_iEditSumCopies;
            }
            strCnt = String.valueOf(count) + "/" + String.valueOf(this.m_iSumOfPhoto);
            if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
                String text = count + "/" + this.m_iSumOfPhoto;
                this.m_PaperCountTextView.setText(text);
                updatePrintNotification(text);
            } else if (!this.m_bPrintDone) {
                if (strStatus == "plus" || strStatus == "next") {
                    this.m_PaperCountTextView.setText(strCnt);
                    updatePrintNotification(strCnt);
                }
            }
        }
    }

    private void GeneralPrint(Socket socket) {
        this.LOG.m385i(this.TAG, "GeneralPrint: " + this.m_PrintMode);
        SetPrintButtonStatus(true);
        if (this.m_bNextPhoto) {
            this.m_bNextPhoto = false;
            this.m_MobileUtility.SkipToPrintNext(null);
        } else if (this.m_bWaitForRecovery) {
            RecoveryPrint();
        } else if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
            this.m_MobileUtility.SetStop(false);
            this.m_MobileUtility.SendPhoto(socket, this.IP, this.m_iPort);
        } else {
            this.m_SDcardUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
            this.m_SDcardUtility.SetStop(false);
            this.m_SDcardUtility.SendPhoto();
        }
    }

    private void ReConnection(String IP, int iPort) {
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.SetStop(false);
            this.m_MobileUtility.SendPhoto(null, IP, iPort);
            return;
        }
        this.m_SDcardUtility.SetStop(false);
        this.m_SDcardUtility.SendPhoto(IP, iPort);
    }

    private void StatusCircle(boolean bRun) {
        if (this.m_StatusProgressBar == null) {
            this.m_StatusProgressBar = (ProgressBar) findViewById(C0349R.id.m_StatusProgressBar);
        }
        if (bRun) {
            this.m_StatusProgressBar.setVisibility(GLOSSY_TEXTURE);
        } else {
            this.m_StatusProgressBar.setVisibility(8);
        }
    }

    private void onBackButtonClicked() {
        if (!this.m_bPrintStatus) {
            CheckIfStoreNewWifiInfo(MATTE_TEXTURE);
        }
    }

    void Exit(int iResult, Intent returnIntent) {
        stopPrintService();
        if (this.m_ShowPrinterList != null) {
            this.m_ShowPrinterList.Stop();
        }
        InitMobileQualityPre();
        if (returnIntent == null) {
            returnIntent = new Intent();
        }
        if (this.m_wifiAutoConnect != null) {
            this.m_wifiAutoConnect.SetStop(true);
            this.m_wifiAutoConnect = null;
        }
        getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        setResult(iResult, returnIntent);
        finish();
    }

    public void onBackPressed() {
        if (this.m_ShowPrinterList == null || !this.m_bOnMDNS) {
            onBackButtonClicked();
        } else {
            this.m_ShowPrinterList.onBackClick();
        }
    }

    private void ShowLeaveDialog() {
        this.m_ShowMSGDialog.StopMSGDialog();
        String strMSG = getString(C0349R.string.DIALOG_LEAVE_MSG1);
        this.m_LeaveDialog.SetDialogListener(new DialogListener() {
            public void SetNowConnSSID(String strNowSSID) {
            }

            public void SetLastConnSSID(String strLastSSID) {
            }

            public void LeaveConfirm() {
                if (PrintViewActivity.this.m_PrintMode == PrintMode.EditPrint || PrintViewActivity.this.m_iSelRoute == PrintViewActivity.MATTE_TEXTURE) {
                    if (PrintViewActivity.this.m_MobileUtility != null) {
                        PrintViewActivity.this.m_MobileUtility.SetCancel();
                    }
                } else if (PrintViewActivity.this.m_SDcardUtility != null) {
                    PrintViewActivity.this.m_SDcardUtility.PutCancel();
                }
                PrintViewActivity.this.m_bPrintStatus = false;
                PrintViewActivity.this.CheckIfStoreNewWifiInfo(PrintViewActivity.LEAVE_TO_PHOTO);
            }

            public void LeaveClose() {
            }

            public void LeaveCancel() {
                PrintViewActivity.this.CheckIfStoreNewWifiInfo(PrintViewActivity.LEAVE_TO_MAIN);
            }

            public void CancelConnetion(ThreadMode strSSID) {
            }
        });
        this.m_LeaveDialog.ShowLeaveDialog(strMSG);
    }

    private void ShowPrinterErrorAlertDialog(String strMSG, boolean bIsAgain) {
        boolean bIsPaperJam;
        this.m_ShowMSGDialog.StopMSGDialog();
        boolean bNewFWversion = this.m_strPrinterFWversion == null ? false : Integer.parseInt(this.m_strPrinterFWversion) >= 108;
        if (bNewFWversion) {
            bIsPaperJam = HitiPPR_PaperJamRun.IsPaperJamErrorType(strMSG);
        } else {
            bIsPaperJam = false;
        }
        String strTitle = bIsPaperJam ? getString(C0349R.string.PAPER_JAM_TITLE) : getString(C0349R.string.ERROR);
        if (bIsPaperJam) {
            strMSG = strMSG.split(":")[GLOSSY_TEXTURE] + ": " + getString(C0349R.string.PAPER_JAM_MESSAGE);
        }
        if (strMSG.contains(getString(C0349R.string.ERROR_PRINTER_0400)) && this.m_EditMetaUtility != null && this.m_EditMetaUtility.GetModel().equals(WirelessType.TYPE_P310W)) {
            strMSG = getString(C0349R.string.ERROR_PRINTER_0400_1);
        }
        PrintStausAndCount(getString(C0349R.string.PRINT_PAUSE), -1);
        this.m_ShowMSGDialog.SetMSGListener(new AnonymousClass14(bIsPaperJam, bIsAgain));
        this.m_ShowMSGDialog.ShowMessageDialog(strMSG, strTitle);
    }

    private void ShowConnectErrorAlertDialog(String strMSG) {
        this.m_ShowMSGDialog.StopMSGDialog();
        SetPrintButtonStatus(false);
        PrintStausAndCount(getString(C0349R.string.PRINT_PAUSE), -1);
        this.m_ShowMSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                PrintViewActivity.this.m_bConnErro = true;
            }

            public void Close() {
            }

            public void CancelClick() {
            }
        });
        this.m_ShowMSGDialog.ShowSimpleMSGDialog(strMSG, getString(C0349R.string.ERROR));
    }

    private void ShowSimpleErrMessage(String strErr) {
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
            }

            public void Close() {
            }

            public void CancelClick() {
            }
        });
        this.m_ShowMSGDialog.ShowSimpleMSGDialog(strErr, getString(C0349R.string.HINT_TITLE));
    }

    private void ShowBitmapErrorAlertDialog(String strMSG) {
        this.m_ShowMSGDialog.StopMSGDialog();
        PrintStausAndCount(getString(C0349R.string.PRINT_PAUSE), -1);
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(17301659);
        alertDialog.setTitle(getString(C0349R.string.ERROR));
        alertDialog.setMessage(strMSG);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(getString(C0349R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PrintViewActivity.this.m_MobileUtility.SkipToPrintNext(null);
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton(getString(C0349R.string.CANCEL), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PrintViewActivity.this.SetPrintButtonStatus(false);
                PrintViewActivity.this.m_bNextPhoto = true;
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void ShowBurnFirwareDialog(Socket socket) {
        this.m_ShowMSGDialog.StopMSGDialog();
        PrintStausAndCount(getString(C0349R.string.PRINT_PAUSE), -1);
        String strMSG = getString(C0349R.string.SUGGEST_UPDATE_FIRMWARE);
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(17301659);
        alertDialog.setTitle(getString(C0349R.string.HINT_TITLE));
        alertDialog.setMessage(strMSG);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(getString(C0349R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PrintViewActivity.this.SetPrintButtonStatus(false);
                PrintViewActivity.this.GoToBurnFirmware();
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton(getString(C0349R.string.CANCEL), new AnonymousClass20(socket));
        alertDialog.show();
    }

    private void StartPrintForNotBurnFireWare(Socket socket) {
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.StartPrintForNotBurnFirmware(socket);
        } else {
            this.m_SDcardUtility.StartPrintForNotBurnFireWare(socket);
        }
    }

    private void GoToBurnFirmware() {
        Intent intent = new Intent(getBaseContext(), BurnFirmwareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE, this.m_strProductID);
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
        intent.putExtras(bundle);
        startActivityForResult(intent, GLOSSY_TEXTURE);
    }

    private void RecoveryPrint() {
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.SetStop(false);
            this.m_MobileUtility.Recovery();
            return;
        }
        this.m_SDcardUtility.SetStop(false);
        this.m_SDcardUtility.RecoveryPrint();
    }

    private void PrintDoneState(int iCpDone) {
        StatusCircle(false);
        PrintStausAndCount(getString(C0349R.string.PRINT_DONE), iCpDone);
        this.m_ShowMSGDialog.StopMSGDialog();
    }

    private void ShowPrintDoneDialog() {
        String strMSG;
        this.m_LeaveDialog.CloseLeaveDialog();
        this.m_LeaveDialog.SetDialogListener(new DialogListener() {
            public void SetNowConnSSID(String strNowSSID) {
            }

            public void SetLastConnSSID(String strLastSSID) {
            }

            public void LeaveConfirm() {
                PrintViewActivity.this.SetPrintButtonStatus(true);
                PrintViewActivity.this.ClearDataAfterPrintDone();
                PrintViewActivity.this.BackOrLeave(PrintViewActivity.LEAVE_TO_MAIN);
            }

            public void LeaveClose() {
            }

            public void LeaveCancel() {
            }

            public void CancelConnetion(ThreadMode strSSID) {
            }
        });
        if (this.m_MobileUtility == null) {
            strMSG = getString(C0349R.string.PRINT_DONE);
        } else if (this.m_MobileUtility.GetSkipState()) {
            strMSG = getString(C0349R.string.PRINT_NONE);
        } else {
            strMSG = getString(C0349R.string.PRINT_DONE);
        }
        this.m_LeaveDialog.ShowPrintDoneDialog(strMSG);
    }

    void ClearDataAfterPrintDone() {
        if (this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_bPrintDone = false;
            this.m_strBufQueueList.clear();
            this.m_strErrPhotoList.clear();
            this.m_strPhotoPathList.clear();
            this.m_iBufCopiesList.clear();
            this.m_iErrCopiesList.clear();
        }
        this.m_iPhotoCopiesList.clear();
        if (this.m_EditMetaUtility != null) {
            this.m_EditMetaUtility.ClearMultiSelMeta();
        }
    }

    private void CheckWifi() {
        this.LOG.m386v(this.TAG, "CheckWifi");
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(MATTE_TEXTURE);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strCurrentSSID = GetNowSSID();
        this.m_strLastSSID = this.m_WifiInfo.GetSSID();
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
        this.m_PrintStatusTextView.setText(getString(C0349R.string.CONNECTING));
        this.m_strLastSSID = CleanSSID(this.m_strLastSSID);
        this.m_strCurrentSSID = CleanSSID(this.m_strCurrentSSID);
        if (mWifi.isConnected()) {
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                this.m_PrintStatusTextView.setText(this.m_strCurrentSSID);
                if (this.m_PrintMode == PrintMode.Snap) {
                    ShowPrinterListDialog();
                    return;
                }
                return;
            }
            this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() == 0 || this.m_strLastSSID.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
            ShowNoWiFiDialog();
        } else {
            this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.CONN_SEARCHING));
            this.m_PrintStatusTextView.setText(getString(C0349R.string.CONN_SEARCHING));
            this.m_wifiAutoConnect = new AutoWifiConnect(this, this.m_strLastSSID, this.m_strSecurityKey);
            this.m_wifiAutoConnect.execute(new Void[GLOSSY_TEXTURE]);
        }
    }

    String CleanSSID(String strSSID) {
        if (strSSID.contains("\"")) {
            return strSSID.split("\"")[MATTE_TEXTURE];
        }
        return strSSID;
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    private String GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        return CleanSSID(strSSID);
    }

    public void ShowNoWiFiDialog() {
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                PrintViewActivity.this.OpenWifi();
            }

            public void CancelClick() {
                PrintViewActivity.this.SetPrintButtonStatus(false);
                PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.PRINT_PAUSE), -1);
            }

            public void Close() {
            }
        });
        this.m_ShowMSGDialog.ShowMessageDialog(getString(C0349R.string.PLEASE_SELECT_NETWORK), getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER));
    }

    private void ShowLowQalityDialog(String strHint, int iCheckFlag, HitiPPR_PrinterCommand PrinterCommand) {
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetHintListener(new AnonymousClass23(iCheckFlag, PrinterCommand));
        this.m_ShowMSGDialog.ShowHintDialog(strHint, HintDialog.Quality, this.m_iPathRoute);
    }

    private void ShowCheckWifiDefaultDialog(int iType) {
        this.m_ShowMSGDialog.StopMSGDialog();
        String strMSG = getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_1) + this.m_strCurrentSSID + getString(C0349R.string.MODIFY_DEFAULT_CONNECTION_2);
        ShowMSGDialog m_CheckDialog = new ShowMSGDialog(this, false);
        m_CheckDialog.SetMSGListener(new AnonymousClass24(iType));
        m_CheckDialog.ShowMessageDialog(strMSG, getString(C0349R.string.TITLE_WIFI_SETTING));
    }

    private void LeaveIDPhotoDialog() {
        this.m_ShowMSGDialog.StopWaitingDialog();
        ShowMSGDialog m_MSGDialog = new ShowMSGDialog(this, false);
        m_MSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                PrintViewActivity.this.GoBack();
            }

            public void Close() {
            }

            public void CancelClick() {
            }
        });
        m_MSGDialog.ShowMessageDialog(getString(C0349R.string.LEAVE_PRINT_ID_PHOTO_MSG), getString(C0349R.string.LEAVE_PRINT_ID_PHOTO_TITLE));
    }

    private void ShowPrinterListDialog() {
        if (this.m_ShowPrinterList == null) {
            this.m_ShowPrinterList = new ShowPrinterList(this);
            this.m_ShowPrinterList.SetPrinterListListener(new PrinterListListener() {
                public void PrinterListFinish(String strPrinterSSID, String IP, int iPort, String strConn) {
                    PrintViewActivity.this.PrintStausAndCount(strPrinterSSID, -1);
                    PrintViewActivity.this.m_strLastSSID = PrintViewActivity.this.GetNowSSID();
                    PrintViewActivity.this.m_strCurrentSSID = PrintViewActivity.this.m_strLastSSID;
                    PrintViewActivity.this.m_strSelSSID = PrintViewActivity.this.m_strCurrentSSID;
                    PrintViewActivity.this.IP = IP;
                    PrintViewActivity.this.m_iPort = iPort;
                    if (PrintViewActivity.this.m_bConnErro) {
                        PrintViewActivity.this.m_bConnErro = false;
                        PrintViewActivity.this.SetPrintButtonStatus(true);
                        PrintViewActivity.this.ReConnection(IP, PrintViewActivity.this.m_iPort);
                        return;
                    }
                    PrintViewActivity.this.startPrintService();
                }

                public void IsBackStateOnMDNS(boolean bMDNS) {
                    PrintViewActivity.this.m_bOnMDNS = bMDNS;
                }

                public void BackFinish() {
                    PrintViewActivity.this.m_bOnMDNS = false;
                    PrintViewActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    PrintViewActivity.this.m_CloseListProgressBar.setVisibility(8);
                    PrintViewActivity.this.SetPrintButtonStatus(false);
                    PrintViewActivity.this.m_PrintCancelImageButton.setEnabled(true);
                    PrintViewActivity.this.m_PrintCancelImageButton.setAlpha(1.0f);
                }

                public void BackStart() {
                    PrintViewActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                    PrintViewActivity.this.m_CloseListProgressBar.setVisibility(PrintViewActivity.GLOSSY_TEXTURE);
                    PrintViewActivity.this.SetPrintButtonStatus(true);
                    PrintViewActivity.this.m_PrintCancelImageButton.setEnabled(false);
                    PrintViewActivity.this.m_PrintCancelImageButton.setAlpha(0.4f);
                    if (PrintViewActivity.this.m_ShowPrinterList.IsShowing()) {
                        PrintViewActivity.this.m_ShowPrinterList.ListClose();
                    }
                }
            });
        }
        this.m_ShowPrinterList.Show();
    }

    private void CheckIfStoreNewWifiInfo(int iType) {
        this.m_strCurrentSSID = GetNowSSID();
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(getBaseContext());
        this.m_WifiInfo.RestoreGlobalVariable();
        String strLastSSID = this.m_WifiInfo.GetSSID();
        if (strLastSSID.length() == 0) {
            SaveNewWifiInfo(iType);
        } else if (this.m_strCurrentSSID.contains(strLastSSID) || this.m_strCurrentSSID.equals("0x") || this.m_strCurrentSSID.isEmpty()) {
            BackOrLeave(iType);
        } else if (this.m_bID_SD_Route) {
            BackOrLeave(iType);
        } else {
            ShowCheckWifiDefaultDialog(iType);
        }
    }

    private void BackOrLeave(int iType) {
        stopPrintService();
        if (this.m_PrintMode == PrintMode.Snap) {
            Intent intent = new Intent();
            intent.putExtra(JumpBundleMessage.BUNDLE_MSG_SNAP_PRINT_PICTURE_PATH, this.m_strSnapPath == null ? XmlPullParser.NO_NAMESPACE : this.m_strSnapPath);
            setResult(44, intent);
            finish();
            return;
        }
        switch (iType) {
            case MATTE_TEXTURE /*1*/:
                if (this.m_iPathRoute == ControllerState.PLAY_COUNT_STATE) {
                    LeaveIDPhotoDialog();
                } else {
                    GoBack();
                }
            case LEAVE_TO_MAIN /*2*/:
                CleanEditMetaPref();
                Exit(50, null);
            case LEAVE_TO_PHOTO /*3*/:
                CleanEditMetaPref();
                if (this.m_iPathRoute == ControllerState.NO_PLAY_ITEM) {
                    Exit(57, SendIPandPort());
                } else if (this.m_iSelRoute == LEAVE_TO_MAIN || this.m_bID_SD_Route) {
                    Exit(56, SendIPandPort());
                } else {
                    Exit(55, null);
                }
            default:
        }
    }

    private void CleanEditMetaPref() {
        if (this.m_EditMetaUtility != null) {
            this.m_EditMetaUtility.ClearPoolEditMeta();
        }
    }

    private void GoBack() {
        if (this.m_iPathRoute == ControllerState.PLAY_PHOTO) {
            if (this.m_iSelRoute == LEAVE_TO_MAIN) {
                Exit(56, SendIPandPort());
            } else {
                Exit(55, SendIPandPort());
            }
        } else if (this.m_iPathRoute == ControllerState.NO_PLAY_ITEM) {
            Exit(57, SendIPandPort());
        } else if (this.m_bID_SD_Route) {
            BackOrLeave(LEAVE_TO_PHOTO);
        } else {
            Exit(55, null);
        }
    }

    private Intent SendIPandPort() {
        Intent intent = new Intent(getBaseContext(), BurnFirmwareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIRELESS_TYPE, this.m_strProductID);
        bundle.putString(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_IP, this.IP);
        bundle.putInt(JumpBundleMessage520.BUNDLE_MSG_WIFI_PRINTER_PORT, this.m_iPort);
        intent.putExtras(bundle);
        return intent;
    }

    private void SaveNewWifiInfo(int iType) {
        String strNowSSID = XmlPullParser.NO_NAMESPACE;
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            if (this.m_PrintSendPhoto != null) {
                strNowSSID = this.m_PrintSendPhoto.GetAttrSSID();
            } else if (this.m_strCurrentSSID != null) {
                strNowSSID = this.m_strCurrentSSID;
            }
        } else if (this.m_strCurrentSSID != null) {
            strNowSSID = this.m_strCurrentSSID;
        }
        if (!strNowSSID.isEmpty()) {
            this.m_WifiInfo.SetSSID(strNowSSID);
            this.m_WifiInfo.SaveGlobalVariable();
        }
        BackOrLeave(iType);
    }

    private int[][] SetPrintoutFromat(int iPaperType) {
        int MAX_HEIGHT;
        int MAX_WIDTH;
        int OUTPUT_WIDTH;
        int OUTPUT_HEIGHT;
        this.LOG.m385i(this.TAG, "SetPrintoutFromat  iPaperType: " + iPaperType);
        int[][] length = (int[][]) Array.newInstance(Integer.TYPE, new int[]{LEAVE_TO_MAIN, LEAVE_TO_MAIN});
        switch (iPaperType) {
            case LEAVE_TO_MAIN /*2*/:
                if (!this.m_EditMetaUtility.GetModel().equals(WirelessType.TYPE_P310W)) {
                    MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                    MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                    OUTPUT_WIDTH = MAX_WIDTH;
                    OUTPUT_HEIGHT = MAX_HEIGHT;
                    break;
                }
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_310w_4x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_310w_4x6));
                OUTPUT_HEIGHT = Integer.parseInt(getString(C0349R.string.OUTPUT_HEIGHT_310w_4x6));
                OUTPUT_WIDTH = Integer.parseInt(getString(C0349R.string.OUTPUT_WIDTH_310w_4x6));
                break;
            case LEAVE_TO_PHOTO /*3*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_5x7));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_5x7));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x8));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x8));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                OUTPUT_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x8_2up));
                OUTPUT_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x8_2up));
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x6));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            default:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
        }
        length[GLOSSY_TEXTURE][GLOSSY_TEXTURE] = MAX_WIDTH;
        length[GLOSSY_TEXTURE][MATTE_TEXTURE] = MAX_HEIGHT;
        length[MATTE_TEXTURE][GLOSSY_TEXTURE] = OUTPUT_WIDTH;
        length[MATTE_TEXTURE][MATTE_TEXTURE] = OUTPUT_HEIGHT;
        this.LOG.m385i(this.TAG, "SetPrintoutFromat  width: " + MAX_WIDTH);
        this.LOG.m385i(this.TAG, "SetPrintoutFromat  height: " + MAX_HEIGHT);
        return length;
    }

    private String ErrorMessageCheck(String strMessage) {
        this.LOG.m385i(this.TAG, "ErrorMessageCheck: " + String.valueOf(strMessage));
        if (strMessage.contains(HitiPPR_QuickPrint.ERROR_BUSY_INITIAL_1001)) {
            return getString(C0349R.string.ERROR_BUSY_INITIAL);
        }
        if (strMessage.contains(HitiPPR_QuickPrint.ERROR_PRINT_BUSY_1002)) {
            return getString(C0349R.string.PRINT_BUSY);
        }
        if (strMessage.contains(HitiPPR_QuickPrint.ERROR_DATA_DELIVERY_1003)) {
            return getString(C0349R.string.DATA_DELIVERY);
        }
        if (strMessage.contains(HitiPPR_QuickPrint.ERROR_SIZE_NOT_MATCH_1004)) {
            String strMSG = XmlPullParser.NO_NAMESPACE;
            String[] strErr = strMessage.split(":");
            if (strErr.length != LEAVE_TO_PHOTO) {
                return strMSG;
            }
            String[] strMsg = getString(C0349R.string.SIZE_NOT_MATCH).split(":");
            strMSG = strMsg[GLOSSY_TEXTURE] + "(" + strErr[MATTE_TEXTURE] + ")" + strMsg[MATTE_TEXTURE] + "(" + strErr[LEAVE_TO_MAIN] + ")";
            if (strMsg.length == LEAVE_TO_PHOTO) {
                return strMSG + strMsg[LEAVE_TO_MAIN];
            }
            return strMSG;
        } else if (strMessage.contains(HitiPPR_QuickPrint.ERROR_FIND_NO_STORAGEID_1005)) {
            return getString(C0349R.string.ERROR_FIND_NO_STORAGEID);
        } else {
            if (strMessage.contains(HitiPPR_QuickPrint.ERROR_STORAGE_ACCESS_DENIED_1006)) {
                return getString(C0349R.string.STORAGE_ACCESS_DENIED);
            }
            if (strMessage.contains(HitiPPR_QuickPrint.ERROR_MODEL_1007)) {
                return getString(C0349R.string.ERROR_MODEL);
            }
            if (strMessage.contains("010") && this.m_EditMetaUtility.GetModel().contains(WirelessType.TYPE_P310W)) {
                strMessage = getString(C0349R.string.ERROR_PRINTER_0100_P310W);
            }
            return strMessage;
        }
    }

    void CleanModeCheck(int iUnCleanNumber) {
        this.LOG.m385i(this.TAG, "CleanModeCheck: " + iUnCleanNumber);
        if (this.m_strProductID != null && !this.m_strProductID.equals(WirelessType.TYPE_P310W)) {
            ShowPrintDoneDialog();
        } else if (CleanModeHint.CleanNuberCheck(this, iUnCleanNumber)) {
            ShowCleanModePrograssDialog(iUnCleanNumber);
        } else {
            ShowPrintDoneDialog();
        }
    }

    private void ShowCleanModePrograssDialog(int iUnCleanNumber) {
        this.m_ShowMSGDialog.SetCleanHintDialog(getString(C0349R.string.PRINT_DONE), getString(C0349R.string.CLEAN_MODE_HINT), new ICleanListener() {
            public Dialog SetDialog() {
                return new Dialog(PrintViewActivity.this, C0349R.style.Dialog_MSG);
            }

            public View SetView(Dialog dialog) {
                return dialog.getLayoutInflater().inflate(C0349R.layout.dialog_clean_mode, null);
            }

            public TextView SetTitle(View view) {
                return (TextView) view.findViewById(C0349R.id.m_CleanTitleTextView);
            }

            public Button SetOKButton(View view) {
                return (Button) view.findViewById(C0349R.id.m_CleanOKButton);
            }

            public Button SetNoButton(View view) {
                return (Button) view.findViewById(C0349R.id.m_CleanNoButton);
            }

            public TextView SetContent(View view) {
                return (TextView) view.findViewById(C0349R.id.m_CleanMSGTextView);
            }

            public void OKclicked() {
                PrintViewActivity.this.startActivityForResult(new Intent(PrintViewActivity.this, SettingCleanModeActivity.class), 74);
            }

            public void CancelClicked() {
                PrintViewActivity.this.ShowPrintDoneDialog();
            }
        }).show();
    }

    private void ToRunPaperJamProgress(boolean bAgain) {
        this.LOG.m385i(this.TAG, "ToPaperJamProcess bAgain: " + bAgain);
        this.m_ShowMSGDialog.SetDialogListener(new DialogListener() {
            public void SetNowConnSSID(String strNowSSID) {
            }

            public void SetLastConnSSID(String strLastSSID) {
            }

            public void LeaveConfirm() {
            }

            public void LeaveClose() {
            }

            public void LeaveCancel() {
            }

            public void CancelConnetion(ThreadMode mode) {
                if (PrintViewActivity.this.m_PrintMode == PrintMode.EditPrint || PrintViewActivity.this.m_iSelRoute == PrintViewActivity.MATTE_TEXTURE) {
                    PrintViewActivity.this.m_MobileUtility.StopPaperJam();
                    PrintViewActivity.this.SetPrintButtonStatus(false);
                }
            }
        });
        this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.PaperJam, getString(C0349R.string.PAPER_JAM_WAITING));
        if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.SetStop(false);
            this.m_MobileUtility.EjectPaperJam();
            return;
        }
        this.m_SDcardUtility.SetStop(false);
        this.m_SDcardUtility.EjectPaperJam();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        this.LOG.m383d(this.TAG, "onRequestPermissionsResult " + permissions[GLOSSY_TEXTURE] + " " + grantResults[GLOSSY_TEXTURE]);
        if (this.mPermissionAsk != null) {
            for (int i = GLOSSY_TEXTURE; i < permissions.length; i += MATTE_TEXTURE) {
                this.mPermissionAsk.SetAskState(permissions[i], grantResults[i]);
            }
        }
    }

    private void openBatteryOptimizationSetting() {
        if (VERSION.SDK_INT < 23 || ((PowerManager) getSystemService("power")).isIgnoringBatteryOptimizations(getPackageName())) {
            GeneralPrint(null);
        } else {
            startActivityForResult(new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS"), 79);
        }
    }

    private void startPrintService() {
        this.LOG.m383d(this.TAG, "startPrintService");
        stopPrintService();
        this.mPrintConnection = PrintBinder.start(this, new IBinder() {
            public void startPrint() {
                PrintViewActivity.this.LOG.m383d(PrintViewActivity.this.TAG, "GeneralPrint");
                PrintViewActivity.this.GeneralPrint(null);
            }

            public NotifyInfo setNotifyInfo(NotifyInfo notifyInfo) {
                notifyInfo.icon = C0349R.drawable.print_button;
                notifyInfo.title = PrintViewActivity.this.getString(C0349R.string.app_name);
                notifyInfo.message = PrintViewActivity.this.getString(C0349R.string.PRINTER_STATUS_INITIALIZING);
                return notifyInfo;
            }
        });
    }

    void updatePrintNotification(String message) {
        if (this.mPrintConnection != null) {
            this.mPrintConnection.updateNotification(message);
        }
    }

    void stopPrintService() {
        if (this.mPrintConnection != null) {
            PrintBinder.stop(this, this.mPrintConnection);
        }
    }
}
