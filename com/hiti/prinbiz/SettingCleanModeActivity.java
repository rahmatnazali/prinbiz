package com.hiti.prinbiz;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_CleanModeRun;
import com.hiti.printerprotocol.request.HitiPPR_GetAllSetting;
import com.hiti.printerprotocol.request.HitiPPR_PaperJamRun;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.hiti.utility.LogManager;
import com.hiti.utility.Verify.ThreadMode;
import com.hiti.utility.dialog.DialogListener;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.dialog.ShowMSGDialog;
import com.hiti.utility.wifi.CheckWifi;
import com.hiti.utility.wifi.CheckWifi.Connection;
import com.hiti.utility.wifi.CheckWifi.ICheckWifiListener;
import com.hiti.utility.wifi.ShowPrinterList.Scan;
import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class SettingCleanModeActivity extends Activity {
    LogManager LOG;
    String TAG;
    CheckWifi mCheckWifi;
    HitiPPR_CleanModeRun mCleanModeRun;
    boolean mCleanRun;
    boolean mCoverOpenCheck;
    ShowMSGDialog mDialog;
    boolean mErrorCancel;
    CleanHandler mHandler;
    String mIP;
    ArrayList<CleanInfo> mItemList;
    boolean mNoPrinter;
    int mNowStep;
    HitiPPR_PaperJamRun mPaperJam;
    boolean mPaperJamEnable;
    ShowMSGDialog mPaperJamRunDialog;
    int mPort;
    ShowMSGDialog mPrinterErrorDialog;
    HitiPPR_GetAllSetting mPrinterInfo;
    HitiPPR_RecoveryPrinter mRecover;
    CleanInfo mTargetCleanInfo;
    ImageButton m_BackImageButton;
    ProgressBar m_CheckWifiProgressBar;
    ImageView m_CleanImageView;
    TextView m_CleanMessageTextView;
    Button m_CleanNextStepButton;
    ProgressBar m_CleanProgressBar;
    TextView m_CleanWaitTextView;
    TextView m_StepTitleTexView;

    /* renamed from: com.hiti.prinbiz.SettingCleanModeActivity.1 */
    class C03651 implements OnClickListener {
        C03651() {
        }

        public void onClick(View v) {
            SettingCleanModeActivity.this.onBackPressed();
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingCleanModeActivity.2 */
    class C03662 implements OnClickListener {
        C03662() {
        }

        public void onClick(View v) {
            SettingCleanModeActivity settingCleanModeActivity = SettingCleanModeActivity.this;
            SettingCleanModeActivity settingCleanModeActivity2 = SettingCleanModeActivity.this;
            int i = settingCleanModeActivity2.mNowStep + 1;
            settingCleanModeActivity2.mNowStep = i;
            settingCleanModeActivity.DispatchStep(i);
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingCleanModeActivity.5 */
    static /* synthetic */ class C03675 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan;

        static {
            $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan = new int[Scan.values().length];
            try {
                $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan[Scan.ScanShow.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan[Scan.NoPrinter.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan[Scan.ScanDismiss.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan[Scan.ApMode.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    class CleanInfo {
        int mIcon;
        String mMessage;
        String mTitle;

        public CleanInfo(String title, String message, int icon) {
            this.mTitle = XmlPullParser.NO_NAMESPACE;
            this.mMessage = XmlPullParser.NO_NAMESPACE;
            this.mIcon = 0;
            this.mTitle = title;
            this.mMessage = message;
            this.mIcon = icon;
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingCleanModeActivity.3 */
    class C07883 extends DialogListener {
        C07883() {
        }

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

        public void CancelConnetion(ThreadMode strSSID) {
            if (SettingCleanModeActivity.this.mPaperJam != null && SettingCleanModeActivity.this.mPaperJam.IsRunning()) {
                SettingCleanModeActivity.this.mPaperJam.Stop();
            }
        }
    }

    /* renamed from: com.hiti.prinbiz.SettingCleanModeActivity.4 */
    class C07894 implements MSGListener {
        final /* synthetic */ String val$strError;

        C07894(String str) {
            this.val$strError = str;
        }

        public void OKClick() {
            SettingCleanModeActivity.this.SetProcessStatus(true);
            if (SettingCleanModeActivity.this.IsPaperJam(this.val$strError)) {
                SettingCleanModeActivity.this.mPrinterErrorDialog.ShowWaitingHintDialog(ThreadMode.PaperJam, SettingCleanModeActivity.this.getString(C0349R.string.PAPER_JAM_WAITING));
                SettingCleanModeActivity.this.RunEjectyPaperJamProcess();
                return;
            }
            SettingCleanModeActivity.this.Recovery(false);
        }

        public void Close() {
        }

        public void CancelClick() {
            SettingCleanModeActivity.this.Recovery(true);
        }
    }

    class CheckWifiListener implements ICheckWifiListener {
        CheckWifiListener() {
        }

        public Activity OpenWifi() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "OpenWifi");
            return null;
        }

        public void onCheckWifiStart() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onCheckWifiStart");
        }

        public void onCheckWifiConnected(String strNowSSID) {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onCheckWifiConnected: " + strNowSSID);
        }

        public void onConnectWifiStart() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onConnectWifiStart");
        }

        public void onNoWifiDialaogCancelClicked() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onNoWifiDialaogCancelClicked");
            SettingCleanModeActivity.this.SetProcessStatus(false);
            SettingCleanModeActivity settingCleanModeActivity = SettingCleanModeActivity.this;
            settingCleanModeActivity.mNowStep--;
        }

        public void onSelectionSSIDSetNowConnSSID(String strNowSSID) {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onSelectionSSIDSetNowConnSSID");
        }

        public void onSelectionSSIDSetLastConnSSID(String strLastSSID) {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onSelectionSSIDSetLastConnSSID");
        }

        public void onSelectionSSIDCancelConnetion() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onSelectionSSIDCancelConnetion");
            SettingCleanModeActivity.this.SetProcessStatus(false);
            SettingCleanModeActivity settingCleanModeActivity = SettingCleanModeActivity.this;
            settingCleanModeActivity.mNowStep--;
        }

        public void onAutoConntectCancel() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onAutoConntectCancel");
            SettingCleanModeActivity.this.SetProcessStatus(false);
        }

        public void onAutoConntectionSuccess(String strNowSSID) {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onAutoConntectionSuccess: " + strNowSSID);
        }

        public void onMdnsPrinterListFinish(String strPrinterSSID, String strIP, int iPort) {
            SettingCleanModeActivity.this.mIP = strIP;
            SettingCleanModeActivity.this.mPort = iPort;
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onMdnsPrinterListFinish: " + strPrinterSSID);
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onMdnsPrinterListFinish step: " + SettingCleanModeActivity.this.mNowStep);
            if (SettingCleanModeActivity.this.mCleanRun) {
                SettingCleanModeActivity.this.mCheckWifi.SetConnectedPrinterSSID(strPrinterSSID, strIP, iPort);
                if (SettingCleanModeActivity.this.mCoverOpenCheck) {
                    SettingCleanModeActivity.this.CheckPrinterInfo(strIP, iPort);
                } else {
                    SettingCleanModeActivity.this.ToRunCleanModeProcess(strIP, iPort);
                }
            }
        }

        public void onMdnsBackFinish() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onMdnsBackFinish");
            if (SettingCleanModeActivity.this.mNoPrinter) {
                SettingCleanModeActivity.this.mNoPrinter = false;
            } else {
                SettingCleanModeActivity.this.SetProcessStatus(false);
            }
        }

        public void onMdnsBackStart() {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onMdnsBackStart");
        }

        public void onMdnsScanState(Scan state, String strPID, String strSSID, String IP, int iPort) {
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "onMdnsScanState: " + state);
            switch (C03675.$SwitchMap$com$hiti$utility$wifi$ShowPrinterList$Scan[state.ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (!SettingCleanModeActivity.this.mNoPrinter) {
                        SettingCleanModeActivity.this.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
                        SettingCleanModeActivity.this.m_CheckWifiProgressBar.setVisibility(0);
                        return;
                    }
                    return;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    SettingCleanModeActivity.this.mNoPrinter = true;
                    SettingCleanModeActivity settingCleanModeActivity = SettingCleanModeActivity.this;
                    settingCleanModeActivity.mNowStep--;
                    SettingCleanModeActivity.this.SetProcessStatus(false);
                    SettingCleanModeActivity.this.mCheckWifi.Stop(Connection.all);
                    String strState = state == Scan.NoPrinter ? SettingCleanModeActivity.this.getString(C0349R.string.PRINTER_UNDETECT) : XmlPullParser.NO_NAMESPACE;
                    if (!strState.isEmpty()) {
                        Toast.makeText(SettingCleanModeActivity.this, strState, 0).show();
                        break;
                    }
                    break;
            }
            SettingCleanModeActivity.this.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            SettingCleanModeActivity.this.m_CheckWifiProgressBar.setVisibility(8);
        }
    }

    class CleanHandler extends MSGHandler {
        CleanHandler() {
        }

        public void handleMessage(Message msg) {
            boolean z = false;
            SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "handleMessage what: " + msg.what);
            if (SettingCleanModeActivity.this.mCleanRun) {
                String strMSG = msg.getData().getString(MSGHandler.MSG);
                SettingCleanModeActivity.this.SetProcessStatus(false);
                SettingCleanModeActivity settingCleanModeActivity;
                switch (msg.what) {
                    case RequestState.REQUEST_RECOVERY_PRINTER /*305*/:
                        SettingCleanModeActivity.this.LOG.m385i("REQUEST_RECOVERY_PRINTER", "REQUEST_RECOVERY_PRINTER");
                        SettingCleanModeActivity.this.mRecover.Stop();
                        if (SettingCleanModeActivity.this.mErrorCancel) {
                            SettingCleanModeActivity.this.mErrorCancel = false;
                            return;
                        } else if (!SettingCleanModeActivity.this.mCoverOpenCheck) {
                            SettingCleanModeActivity.this.ToRunCleanModeProcess(SettingCleanModeActivity.this.mIP, SettingCleanModeActivity.this.mPort);
                            return;
                        } else {
                            return;
                        }
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    case RequestState.REQUEST_SETTING_ITEM_ALL_ERROR /*339*/:
                    case RequestState.REQUEST_CLEAN_MODE_RUN_ERROR /*391*/:
                        settingCleanModeActivity = SettingCleanModeActivity.this;
                        settingCleanModeActivity.mNowStep--;
                        SettingCleanModeActivity.this.CloseDialog();
                        SettingCleanModeActivity.this.mDialog.ShowSimpleMSGDialog(strMSG, SettingCleanModeActivity.this.getString(C0349R.string.ERROR));
                        return;
                    case RequestState.REQUEST_SETTING_ITEM_ALL /*338*/:
                        SettingCleanModeActivity.this.LOG.m385i(SettingCleanModeActivity.this.TAG, "REQUEST_SETTING_ITEM_ALL");
                        String strGetVersion = SettingCleanModeActivity.this.mPrinterInfo.GetAttrFirmwareVersionString().replace(".", XmlPullParser.NO_NAMESPACE).substring(0, 3);
                        SettingCleanModeActivity settingCleanModeActivity2 = SettingCleanModeActivity.this;
                        if (Integer.parseInt(strGetVersion) >= 108) {
                            z = true;
                        }
                        settingCleanModeActivity2.mPaperJamEnable = z;
                        SettingCleanModeActivity.this.ToRunCleanModeProcess(SettingCleanModeActivity.this.mIP, SettingCleanModeActivity.this.mPort);
                        return;
                    case RequestState.REQUEST_CLEAN_MODE_RUN /*390*/:
                        SettingCleanModeActivity.this.ShowStepInfo(SettingCleanModeActivity.this.mNowStep);
                        return;
                    case RequestState.REQUEST_CLEAN_MODE_RUN_ERROR_DUETO_PRINTER /*392*/:
                        settingCleanModeActivity = SettingCleanModeActivity.this;
                        settingCleanModeActivity.mNowStep--;
                        break;
                    case RequestState.REQUEST_PAPER_JAM_RUN /*393*/:
                        SettingCleanModeActivity.this.CloseDialog();
                        SettingCleanModeActivity.this.ShowPrinterErrorDialog(SettingCleanModeActivity.this.getString(C0349R.string.PAPER_JAM_DONE));
                        return;
                    case RequestState.REQUEST_PAPER_JAM_RUN_ERROR_DUETO_PRINTER /*395*/:
                        break;
                    default:
                        return;
                }
                strMSG = SettingCleanModeActivity.this.ErrorMessageCheck(strMSG);
                SettingCleanModeActivity.this.CloseDialog();
                if (strMSG.equals(HitiPPR_CleanModeRun.NEED_COVER_OPEN_FOR_CLEAN_MODE)) {
                    SettingCleanModeActivity.this.mDialog.ShowSimpleMSGDialog(SettingCleanModeActivity.this.getString(C0349R.string.CLEAN_ERROR_COVER_NOT_OPEN), SettingCleanModeActivity.this.getString(C0349R.string.ERROR));
                } else {
                    SettingCleanModeActivity.this.ShowPrinterErrorDialog(strMSG);
                }
            }
        }
    }

    public SettingCleanModeActivity() {
        this.m_BackImageButton = null;
        this.m_CleanImageView = null;
        this.m_StepTitleTexView = null;
        this.m_CleanMessageTextView = null;
        this.m_CleanWaitTextView = null;
        this.m_CleanProgressBar = null;
        this.m_CheckWifiProgressBar = null;
        this.m_CleanNextStepButton = null;
        this.mItemList = null;
        this.mTargetCleanInfo = null;
        this.mNowStep = -1;
        this.mCheckWifi = null;
        this.mCleanModeRun = null;
        this.mRecover = null;
        this.mPaperJam = null;
        this.mPrinterInfo = null;
        this.mHandler = null;
        this.mDialog = null;
        this.mPrinterErrorDialog = null;
        this.mPaperJamRunDialog = null;
        this.mIP = null;
        this.mPort = 0;
        this.mCleanRun = false;
        this.mCoverOpenCheck = false;
        this.mNoPrinter = false;
        this.mPaperJamEnable = false;
        this.mErrorCancel = false;
        this.LOG = null;
        this.TAG = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0349R.layout.activity_clean);
        this.LOG = new LogManager(0);
        this.TAG = getClass().getSimpleName();
        this.m_BackImageButton = (ImageButton) findViewById(C0349R.id.m_BackImageButton);
        this.m_StepTitleTexView = (TextView) findViewById(C0349R.id.m_StepTitleTexView);
        this.m_CleanMessageTextView = (TextView) findViewById(C0349R.id.m_CleanMessageTextView);
        this.m_CleanWaitTextView = (TextView) findViewById(C0349R.id.m_CleanWaitTextView);
        this.m_CleanImageView = (ImageView) findViewById(C0349R.id.m_CleanImageView);
        this.m_CleanProgressBar = (ProgressBar) findViewById(C0349R.id.m_CleanProgressBar);
        this.m_CheckWifiProgressBar = (ProgressBar) findViewById(C0349R.id.m_CheckWifiProgressBar);
        this.m_CleanNextStepButton = (Button) findViewById(C0349R.id.m_CleanNextStepButton);
        this.m_CleanProgressBar.setVisibility(8);
        this.m_CheckWifiProgressBar.setVisibility(8);
        this.m_CleanImageView.setVisibility(8);
        this.m_CleanWaitTextView.setVisibility(8);
        this.m_BackImageButton.setOnClickListener(new C03651());
        this.m_CleanNextStepButton.setOnClickListener(new C03662());
        SetOtherSetting();
        SetItemInfo();
        int i = this.mNowStep + 1;
        this.mNowStep = i;
        ShowStepInfo(i);
    }

    private void SetOtherSetting() {
        this.mHandler = new CleanHandler();
        this.mDialog = new ShowMSGDialog(this, false);
        this.mPrinterErrorDialog = new ShowMSGDialog(this, false);
        this.mPaperJamRunDialog = new ShowMSGDialog(this, true);
        this.mPaperJamRunDialog.SetDialogListener(new C07883());
    }

    private void DispatchStep(int iStep) {
        if (iStep == 1) {
            CheckWifi(true);
            return;
        }
        if (iStep < this.mItemList.size() - 1) {
            ShowStepInfo(iStep);
        }
        if (iStep == this.mItemList.size() - 1) {
            CheckWifi(false);
        }
        if (iStep == this.mItemList.size()) {
            Exit(-1);
        }
    }

    private void SetItemInfo() {
        this.mItemList = new ArrayList();
        this.mItemList.add(new CleanInfo(getString(C0349R.string.CLEAN_STEP1), getString(C0349R.string.CLEAN_MODE_STEP1), 0));
        this.mItemList.add(new CleanInfo(getString(C0349R.string.CLEAN_STEP2), getString(C0349R.string.CLEAN_MODE_STEP2), C0349R.drawable.cleaning_step2));
        this.mItemList.add(new CleanInfo(getString(C0349R.string.CLEAN_STEP3), getString(C0349R.string.CLEAN_MODE_STEP3), C0349R.drawable.cleaning_step3));
        this.mItemList.add(new CleanInfo(getString(C0349R.string.CLEAN_STEP4), getString(C0349R.string.CLEAN_MODE_STEP4), 0));
        this.mItemList.add(new CleanInfo(XmlPullParser.NO_NAMESPACE, getString(C0349R.string.CLEAN_MODE_DONE), 0));
    }

    private void ShowStepInfo(int iStep) {
        this.LOG.m385i(this.TAG, "ShowStepInfo step: " + iStep);
        this.mTargetCleanInfo = (CleanInfo) this.mItemList.get(iStep);
        this.m_StepTitleTexView.setText(this.mTargetCleanInfo.mTitle);
        this.m_CleanMessageTextView.setText(this.mTargetCleanInfo.mMessage);
        this.m_CleanImageView.setImageResource(this.mTargetCleanInfo.mIcon);
        if (iStep == this.mItemList.size()) {
            this.m_StepTitleTexView.setVisibility(8);
        }
        if (this.mTargetCleanInfo.mIcon != 0) {
            this.m_CleanImageView.setVisibility(0);
        } else {
            this.m_CleanImageView.setVisibility(8);
        }
    }

    private void CheckWifi(boolean bCoverOpenCheck) {
        if (!this.mCleanRun) {
            this.mCoverOpenCheck = bCoverOpenCheck;
            SetProcessStatus(true);
            if (this.mCheckWifi == null) {
                this.mCheckWifi = new CheckWifi(this);
                this.mCheckWifi.SetListener(new CheckWifiListener());
            }
            this.mCheckWifi.Check();
        }
    }

    private void SetProcessStatus(boolean bRet) {
        float f;
        boolean z;
        int i = 8;
        boolean z2 = true;
        float f2 = 0.4f;
        this.mCleanRun = bRet;
        this.LOG.m385i(this.TAG, "SetProcessStatus " + bRet);
        if (this.mNowStep == this.mItemList.size() - 1) {
            this.m_CleanProgressBar.setVisibility(bRet ? 0 : 8);
            TextView textView = this.m_CleanWaitTextView;
            if (bRet) {
                i = 0;
            }
            textView.setVisibility(i);
        }
        Button button = this.m_CleanNextStepButton;
        if (bRet) {
            f = 0.4f;
        } else {
            f = 1.0f;
        }
        button.setAlpha(f);
        ImageButton imageButton = this.m_BackImageButton;
        if (!bRet) {
            f2 = 1.0f;
        }
        imageButton.setAlpha(f2);
        button = this.m_CleanNextStepButton;
        if (bRet) {
            z = false;
        } else {
            z = true;
        }
        button.setEnabled(z);
        CleanHandler cleanHandler = this.mHandler;
        if (bRet) {
            z2 = false;
        }
        cleanHandler.SetStop(z2);
        if (!bRet && this.mCleanModeRun != null && this.mCleanModeRun.IsRunning()) {
            this.mCleanModeRun.Stop();
            this.mCleanModeRun.interrupt();
        }
    }

    private void ToRunCleanModeProcess(String strIP, int iPort) {
        this.LOG.m385i(this.TAG, "ToRunCleanModeProcess CoverOpenCheck: " + this.mCoverOpenCheck);
        this.mNowStep = this.mCoverOpenCheck ? 1 : this.mItemList.size() - 1;
        SetProcessStatus(true);
        this.mCleanModeRun = new HitiPPR_CleanModeRun(this, strIP, iPort, this.mHandler);
        this.mCleanModeRun.SetCheckCoverOpen(this.mCoverOpenCheck);
        this.mCleanModeRun.start();
    }

    private void Recovery(boolean bCancel) {
        this.LOG.m385i(this.TAG, "Recovery");
        this.mErrorCancel = bCancel;
        this.mRecover = new HitiPPR_RecoveryPrinter(this, this.mIP, this.mPort, this.mHandler);
        this.mRecover.start();
    }

    public void onBackPressed() {
        SetProcessStatus(false);
        int i = this.mNowStep - 1;
        this.mNowStep = i;
        if (i >= 0) {
            ShowStepInfo(this.mNowStep);
        } else {
            Exit(0);
        }
    }

    void Exit(int iResult) {
        setResult(iResult);
        finish();
    }

    void ShowPrinterErrorDialog(String strMSG) {
        String strError;
        this.LOG.m385i(this.TAG, "ShowPrinterErrorDialog " + strMSG);
        String strTitle = IsPaperJam(strMSG) ? getString(C0349R.string.PAPER_JAM_TITLE) : getString(C0349R.string.ERROR);
        if (IsPaperJam(strMSG)) {
            strError = strMSG.split(":")[0] + ": " + getString(C0349R.string.PAPER_JAM_MESSAGE);
        } else {
            strError = strMSG;
        }
        this.mPrinterErrorDialog.SetMSGListener(new C07894(strError));
        this.mPrinterErrorDialog.ShowMessageDialog(strError, strTitle);
    }

    boolean IsPaperJam(String strMSG) {
        return this.mPaperJamEnable ? HitiPPR_PaperJamRun.IsPaperJamErrorType(strMSG) : false;
    }

    void RunEjectyPaperJamProcess() {
        this.mPaperJam = new HitiPPR_PaperJamRun(this, this.mIP, this.mPort, this.mHandler);
        this.mPaperJam.start();
    }

    void CloseDialog() {
        this.LOG.m385i(this.TAG, "CloseDialog");
        if (this.mPaperJamRunDialog != null) {
            this.mPaperJamRunDialog.StopWaitingDialog();
        }
        if (this.mDialog != null) {
            this.mDialog.StopMSGDialog();
        }
        if (this.mPrinterErrorDialog != null) {
            this.mPrinterErrorDialog.StopMSGDialog();
        }
    }

    void CheckPrinterInfo(String IP, int iPort) {
        this.mPrinterInfo = new HitiPPR_GetAllSetting(this, IP, iPort, this.mHandler);
        this.mPrinterInfo.start();
    }

    private String ErrorMessageCheck(String strMessage) {
        if (strMessage.contains("010")) {
            return getString(C0349R.string.ERROR_PRINTER_0100_P310W);
        }
        return strMessage;
    }
}
