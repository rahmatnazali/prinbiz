package com.hiti.printerprotocol.request;

import android.content.Context;
import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.prinbiz.BorderDelFragment;
import com.hiti.printerprotocol.JobInfo;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.PrinterErrorCode;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.utility.PrinterInfo;
import com.hiti.printerprotocol.utility.SendPhotoInfo;
import com.hiti.utility.ByteConvertUtility;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.smtp.SMTP;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_QuickPrint extends HitiPPR_PrinterCommandNew {
    public static String ERROR_BUSY_INITIAL_1001;
    public static String ERROR_DATA_DELIVERY_1003;
    public static String ERROR_FIND_NO_STORAGEID_1005;
    public static String ERROR_MODEL_1007;
    public static String ERROR_PRINT_BUSY_1002;
    public static String ERROR_SIZE_NOT_MATCH_1004;
    public static String ERROR_STORAGE_ACCESS_DENIED_1006;
    ArrayList<Byte> mFrameTypeList;
    private int mOneJobPicNum;
    private int m_AttrCleanLength;
    byte m_AttrCurrentMediaSize;
    String m_AttrErrorDescription;
    int m_AttrErrorDescriptionLength;
    Byte m_AttrErrorType;
    int m_AttrLenOfDiskName;
    byte m_AttrMediaSize;
    int m_AttrNumOfConsunableRemain;
    private int m_AttrPrintFrameLength;
    private long m_AttrPrintFrameNumber;
    Byte m_AttrPrinterStatus;
    byte[] m_AttrPrinterStatusFlag;
    String m_AttrSSID;
    int m_AttrSSIDLength;
    Byte m_AttrSecurityAlgorithm;
    String m_AttrSecurityKey;
    int m_AttrSecurityKeyLength;
    boolean m_AttrSelTexture;
    Boolean m_AttrTexturePrint;
    private int m_AttrUnCleanNumber;
    Context m_Context;
    private byte[][] m_NowObjectIdList;
    private byte[][] m_NowStorageIdList;
    ArrayList<byte[]> m_ObjIdsList;
    ArrayList<byte[]> m_StorageIdsList;
    boolean m_bCheckSDcardAccess;
    boolean m_bContinueJobState;
    boolean m_bContinueState;
    private boolean m_bCopiesPlus;
    boolean m_bCpAttrGet;
    byte m_bDuplex;
    private boolean m_bErrorOccur;
    private boolean m_bNext;
    private boolean m_bNotMath;
    private byte m_bSharpenValue;
    private boolean m_bStopForCancel;
    boolean m_bStorageError;
    private byte m_boMaskColor;
    byte m_byJobQTY;
    byte m_byteJobStatus;
    byte m_bytePrintout;
    int m_iCopiesOfJob;
    int m_iLastCp;
    int m_iNowCp;
    HashMap<String, Integer> m_iPaperCountList;
    ArrayList<Integer> m_iPhotoCopiesList;
    int m_iPreCopies;
    ArrayList<Integer> m_iPrintedJobIdList;
    private int m_iPrintedJobNumber;
    ArrayList<Integer> m_iQueueJobIdList;
    int m_iStorageErrNum;
    JumpPreferenceKey m_pref;
    String m_strDiskName;
    String m_strError0001;
    String m_strModel;
    String m_strPrintOutItem;
    private String m_strProductIDString;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_QuickPrint.1 */
    static /* synthetic */ class C04091 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PrintingCancel.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataErrorDueToPrinter.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoRequest.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponse.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponseSuccess.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesRequest.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponse.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponseSuccess.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PrepareCreateJobRequest.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoRequest.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoResponse.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoResponseSuccess.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobRequest.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponse.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponseSuccess.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobRequest.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobResponse.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataRequest.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataResponse.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusRequest.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponse.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponse_plus.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponseSuccess.ordinal()] = 29;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponseSuccess_plus.ordinal()] = 30;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameRequest.ordinal()] = 31;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponse.ordinal()] = 32;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponseSuccess.ordinal()] = 33;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberRequest.ordinal()] = 34;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberResponse.ordinal()] = 35;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberResponseSuccess.ordinal()] = 36;
            } catch (NoSuchFieldError e36) {
            }
        }
    }

    static {
        ERROR_BUSY_INITIAL_1001 = "ERROR_BUSY_INITIAL_1001";
        ERROR_PRINT_BUSY_1002 = "ERROR_PRINT_BUSY_1002";
        ERROR_DATA_DELIVERY_1003 = "ERROR_DATA_DELIVERY_1003";
        ERROR_SIZE_NOT_MATCH_1004 = "ERROR_SIZE_NOT_MATCH_1004";
        ERROR_FIND_NO_STORAGEID_1005 = "ERROR_FIND_NO_STORAGEID_1005";
        ERROR_STORAGE_ACCESS_DENIED_1006 = "ERROR_STORAGE_ACCESS_DENIED_1006";
        ERROR_MODEL_1007 = "ERROR_MODEL_1007";
    }

    public HitiPPR_QuickPrint(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrNumOfConsunableRemain = -1;
        this.m_AttrCurrentMediaSize = (byte) -1;
        this.m_AttrTexturePrint = null;
        this.m_AttrSelTexture = false;
        this.m_bDuplex = (byte) 0;
        this.m_AttrMediaSize = (byte) -1;
        this.m_bytePrintout = (byte) -1;
        this.m_strPrintOutItem = XmlPullParser.NO_NAMESPACE;
        this.m_strDiskName = null;
        this.m_AttrLenOfDiskName = -1;
        this.m_boMaskColor = (byte) 0;
        this.m_ObjIdsList = null;
        this.m_StorageIdsList = null;
        this.m_iPhotoCopiesList = null;
        this.m_bContinueJobState = false;
        this.m_byteJobStatus = (byte) -1;
        this.m_iPrintedJobIdList = null;
        this.m_iQueueJobIdList = null;
        this.m_iPrintedJobNumber = 0;
        this.mOneJobPicNum = 1;
        this.m_NowObjectIdList = (byte[][]) null;
        this.m_NowStorageIdList = (byte[][]) null;
        this.m_bStopForCancel = false;
        this.m_Context = null;
        this.m_bCpAttrGet = false;
        this.m_bContinueState = false;
        this.m_iCopiesOfJob = -1;
        this.m_iLastCp = -1;
        this.m_iPreCopies = 0;
        this.m_iNowCp = 0;
        this.m_byJobQTY = (byte) 0;
        this.m_strError0001 = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.m_iStorageErrNum = 0;
        this.m_bStorageError = false;
        this.m_bCheckSDcardAccess = false;
        this.m_bSharpenValue = (byte) -120;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
        this.m_bNext = false;
        this.m_bErrorOccur = false;
        this.m_bNotMath = false;
        this.m_bCopiesPlus = false;
        this.m_strProductIDString = null;
        this.m_AttrUnCleanNumber = -1;
        this.m_AttrCleanLength = -1;
        this.m_iPaperCountList = null;
        this.mFrameTypeList = null;
        this.m_Context = context;
        this.m_OneJob = new JobInfo();
        this.m_pref = new JumpPreferenceKey(this.m_Context);
        this.m_strModel = this.m_pref.GetModelPreference();
        this.m_iPrintedJobIdList = new ArrayList();
        this.m_iQueueJobIdList = new ArrayList();
    }

    private void InitialAllAttribute() {
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrNumOfConsunableRemain = -1;
        this.m_AttrCurrentMediaSize = (byte) -1;
        this.m_AttrTexturePrint = null;
        this.m_strDiskName = null;
        this.m_AttrLenOfDiskName = -1;
        this.m_byteJobStatus = (byte) -1;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
    }

    public void SetSharpenValue(byte bSharpenValue) {
        this.m_bSharpenValue = bSharpenValue;
    }

    public void PutIDs(ArrayList<Integer> objIdList, ArrayList<Integer> storageIdsList) {
        Iterator it;
        if (objIdList != null) {
            this.m_ObjIdsList = new ArrayList();
            it = objIdList.iterator();
            while (it.hasNext()) {
                this.m_ObjIdsList.add(ByteConvertUtility.IntToByte(((Integer) it.next()).intValue()));
            }
        }
        if (storageIdsList != null) {
            this.m_StorageIdsList = new ArrayList();
            it = storageIdsList.iterator();
            while (it.hasNext()) {
                this.m_StorageIdsList.add(ByteConvertUtility.IntToByte(((Integer) it.next()).intValue()));
            }
        }
        this.LOG.m385i("PutIDs", "m_ObjIdsList size: " + this.m_ObjIdsList);
        this.LOG.m385i("PutIDs", "m_StorageIdsList size: " + this.m_StorageIdsList);
    }

    public void SetProductID(String strProductIDString) {
        this.m_strProductIDString = strProductIDString;
    }

    public void SetPrintQTY(byte byJobQTY) {
        this.m_byJobQTY = byJobQTY;
    }

    public void PutCopies(ArrayList<Integer> iCopiesList) {
        this.m_iPhotoCopiesList = new ArrayList();
        if (iCopiesList != null) {
            Iterator it = iCopiesList.iterator();
            while (it.hasNext()) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
            return;
        }
        for (int i = 0; i < this.m_ObjIdsList.size(); i++) {
            this.m_iPhotoCopiesList.add(Integer.valueOf(1));
        }
    }

    public void SetPrintInfo(SendPhotoInfo mPrintInfo) {
        this.m_AttrSelTexture = mPrintInfo.GetTexture();
        this.m_bDuplex = mPrintInfo.GetDuplex() ? (byte) 1 : (byte) 0;
        this.m_AttrMediaSize = mPrintInfo.GetMediaSize();
        this.m_bytePrintout = mPrintInfo.GetPrintout();
        int iPaperType = mPrintInfo.GetPaperType();
        this.m_strPrintOutItem = PrinterInfo.GetPrintoutItem(this.m_Context, iPaperType);
        if (iPaperType == 6) {
            this.m_strPrintOutItem = this.m_Context.getString(this.R_STRING_PRINT_OUT_6x8_2up);
        }
        if (mPrintInfo.GetDuplex()) {
            if (mPrintInfo.GetPaperType() == 6) {
                this.m_NowObjectIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{4, 4});
                this.m_NowStorageIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{4, 4});
            } else {
                this.m_NowObjectIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{2, 4});
                this.m_NowStorageIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{2, 4});
            }
        } else if (mPrintInfo.GetPaperType() == 6) {
            this.m_NowObjectIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{2, 4});
            this.m_NowStorageIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{2, 4});
        } else {
            this.m_NowObjectIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{1, 4});
            this.m_NowStorageIdList = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{1, 4});
        }
        this.mOneJobPicNum = this.m_NowObjectIdList.length;
        this.LOG.m385i("SetPrintInfo", "mOneJobPicNum: " + this.mOneJobPicNum);
    }

    public void ContinuePrint(int iPrintedCount, int iLastCp) {
        this.m_iPrintedJobNumber = iPrintedCount / this.mOneJobPicNum;
        for (int i = 0; i < this.m_iPrintedJobNumber; i++) {
            this.m_iPrintedJobIdList.add(Integer.valueOf(1));
        }
        this.m_iLastCp = iLastCp;
        this.m_bContinueState = true;
        this.LOG.m384e("ContinuePrint_m_iPrintedJobIdList", String.valueOf(this.m_iPrintedJobIdList));
    }

    public String GetAttrSSID() {
        return this.m_AttrSSID;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }

    public void StopForCancel() {
        this.m_bStopForCancel = true;
    }

    public boolean IsStopForCancel() {
        return this.m_bStopForCancel;
    }

    public void StartRequest() {
        try {
            QuickPrint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void QuickPrint() throws InterruptedException {
        if (IsRunning()) {
            String strMSG;
            int i;
            this.LOG.m385i("QuickPrint Step()", String.valueOf(GetCurrentStep()));
            int i2;
            ArrayList arrayList;
            byte b;
            ArrayList arrayList2;
            JobInfo jobInfo;
            switch (C04091.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    this.LOG.m384e("Step_Complete", "Step_Complete");
                    SendMessage(RequestState.REQUEST_QUICK_PRINT, String.valueOf(this.m_AttrUnCleanNumber));
                    Stop();
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    strMSG = XmlPullParser.NO_NAMESPACE;
                    if (this.m_ErrorString != null) {
                        strMSG = this.m_ErrorString;
                    }
                    this.m_bErrorOccur = true;
                    SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER, strMSG);
                    DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    InitialAllAttribute();
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (!Get_NetWork_Info_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetNetworkInfoResponse);
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetNetworkInfoResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            i = this.m_AttrSSIDLength;
                            if (r0 != -1) {
                                if (this.m_AttrSSID != null) {
                                    if (this.m_AttrSecurityAlgorithm.byteValue() != -1) {
                                        i = this.m_AttrSecurityKeyLength;
                                        if (r0 != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                char[] AttrSecurityKey = new char[this.m_AttrSecurityKeyLength];
                                                i2 = 0;
                                                while (true) {
                                                    i = this.m_AttrSecurityKeyLength;
                                                    if (i2 >= r0) {
                                                        this.m_AttrSecurityKey = String.valueOf(AttrSecurityKey);
                                                        DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                                        break;
                                                    }
                                                    AttrSecurityKey[i2] = (char) this.m_lpReadData[i2];
                                                    i2++;
                                                }
                                            }
                                        } else {
                                            this.m_AttrSecurityKeyLength = this.m_lpReadData[3];
                                            DecideNextStepAndPrepareReadBuff(this.m_AttrSecurityKeyLength + 1, 0, null);
                                            break;
                                        }
                                    }
                                    this.m_AttrSecurityAlgorithm = Byte.valueOf(this.m_lpReadData[3]);
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                char[] AttrSSID = new char[this.m_AttrSSIDLength];
                                i2 = 0;
                                while (true) {
                                    i = this.m_AttrSSIDLength;
                                    if (i2 >= r0) {
                                        this.m_AttrSSID = String.valueOf(AttrSSID);
                                        if (this.m_lpReadData[this.m_AttrSSIDLength] != -1) {
                                            DecideNextStepAndPrepareReadBuff(4, 0, null);
                                            break;
                                        }
                                        DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                        break;
                                    }
                                    AttrSSID[i2] = (char) this.m_lpReadData[i2];
                                    i2++;
                                }
                            } else {
                                this.m_AttrSSIDLength = this.m_lpReadData[2];
                                DecideNextStepAndPrepareReadBuff(this.m_AttrSSIDLength + 1, 0, null);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    Thread.sleep(2000);
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(17, 0, SettingStep.Step_GetPrinterStatusResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                i = this.m_AttrErrorDescriptionLength;
                                if (r0 != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        char[] AttrErrorDescription = new char[this.m_AttrErrorDescriptionLength];
                                        i2 = 0;
                                        while (true) {
                                            i = this.m_AttrErrorDescriptionLength;
                                            if (i2 >= r0) {
                                                this.m_AttrErrorDescription = String.valueOf(AttrErrorDescription);
                                                SetErrorMSG(this.m_AttrErrorDescription);
                                                if (this.m_AttrErrorType.byteValue() == 16) {
                                                    if (this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
                                                        DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                                        this.m_strError0001 = this.m_AttrErrorDescription;
                                                        this.m_AttrPrinterStatusFlag = null;
                                                        this.m_AttrErrorDescriptionLength = -1;
                                                        this.m_AttrErrorDescription = null;
                                                        break;
                                                    }
                                                }
                                                DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                                this.LOG.m384e("!!m_AttrErrorDescription", XmlPullParser.NO_NAMESPACE + this.m_AttrErrorDescription);
                                                break;
                                            }
                                            AttrErrorDescription[i2] = (char) this.m_lpReadData[i2];
                                            i2++;
                                        }
                                    }
                                } else {
                                    this.m_AttrErrorDescriptionLength = this.m_lpReadData[2];
                                    DecideNextStepAndPrepareReadBuff(this.m_AttrErrorDescriptionLength + 1, 0, null);
                                    break;
                                }
                            }
                            this.m_AttrPrinterStatusFlag = new byte[3];
                            this.m_AttrPrinterStatusFlag[0] = this.m_lpReadData[3];
                            this.m_AttrPrinterStatusFlag[1] = this.m_lpReadData[4];
                            this.m_AttrPrinterStatusFlag[2] = this.m_lpReadData[5];
                            this.m_AttrPrinterStatus = Byte.valueOf(this.m_lpReadData[10]);
                            this.m_AttrErrorType = Byte.valueOf(this.m_lpReadData[15]);
                            if (this.m_lpReadData[16] == -1) {
                                if (this.m_strError0001 == null) {
                                    this.LOG.m385i("printer status", XmlPullParser.NO_NAMESPACE + this.m_AttrPrinterStatus.byteValue());
                                    if (this.m_AttrPrinterStatus.byteValue() != 2) {
                                        this.m_bContinueState = false;
                                        if (CheckBit(this.m_AttrPrinterStatusFlag[0], Byte.MIN_VALUE) == null) {
                                            this.LOG.m385i("Qucik", "Not busy");
                                            if (this.m_iPrintedJobNumber * this.mOneJobPicNum == this.m_ObjIdsList.size()) {
                                                if (this.m_iQueueJobIdList.isEmpty()) {
                                                    DecideNextStep(SettingStep.Step_GetUnCleanNumberRequest);
                                                } else {
                                                    DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                                }
                                            } else {
                                                i = this.m_iPrintedJobNumber;
                                                if ((r0 + 1) * this.mOneJobPicNum == this.m_ObjIdsList.size()) {
                                                    if (this.m_iQueueJobIdList.size() == 1) {
                                                        DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                                    }
                                                }
                                                if (this.m_bStopForCancel) {
                                                    DecideNextStep(SettingStep.Step_PrintingCancel);
                                                } else {
                                                    DecideNextStep(SettingStep.Step_GetPrinterCapabilitiesRequest);
                                                }
                                            }
                                        } else {
                                            this.LOG.m385i("Qucik", "busy");
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                            SendMessage(SMTPReply.START_MAIL_INPUT, ERROR_BUSY_INITIAL_1001);
                                        }
                                        this.m_AttrPrinterStatusFlag = null;
                                        this.m_AttrErrorDescription = null;
                                        this.m_AttrErrorDescriptionLength = -1;
                                        break;
                                    }
                                    if (this.m_iQueueJobIdList.size() == 0 || this.m_bContinueState) {
                                        SendMessage(SMTPReply.START_MAIL_INPUT, ERROR_BUSY_INITIAL_1001);
                                        arrayList = this.m_iPrintedJobIdList;
                                        if (r0.size() * this.mOneJobPicNum < this.m_ObjIdsList.size()) {
                                            DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                                        } else {
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                        }
                                    } else {
                                        SendMessage(SMTPReply.START_MAIL_INPUT, ERROR_PRINT_BUSY_1002);
                                        DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                    }
                                    this.m_AttrPrinterStatusFlag = null;
                                    this.m_AttrErrorDescription = null;
                                    this.m_AttrErrorDescriptionLength = -1;
                                    break;
                                }
                                this.m_strError0001 = null;
                                DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                this.m_AttrPrinterStatusFlag = null;
                                this.m_AttrErrorDescription = null;
                                this.m_AttrErrorDescriptionLength = -1;
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    this.m_AttrCurrentMediaSize = (byte) -1;
                    this.m_AttrTexturePrint = null;
                    if (!Get_Printer_Capabilities_For_QuickPrint_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterCapabilitiesResponse);
                    break;
                case ConnectionResult.CANCELED /*13*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(5, 0, SettingStep.Step_GetPrinterCapabilitiesResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.TIMEOUT /*14*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            b = this.m_AttrCurrentMediaSize;
                            if (r0 != -1) {
                                if (this.m_AttrTexturePrint == null) {
                                    if (this.m_lpReadData[3] == 1) {
                                        this.m_AttrTexturePrint = Boolean.valueOf(true);
                                    } else {
                                        this.m_AttrTexturePrint = Boolean.valueOf(false);
                                    }
                                    DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                                    break;
                                }
                            }
                            this.m_AttrCurrentMediaSize = this.m_lpReadData[3];
                            this.LOG.m383d("m_AttrMediaSize", XmlPullParser.NO_NAMESPACE + this.m_AttrMediaSize);
                            this.LOG.m383d("m_AttrCurrentMediaSize", XmlPullParser.NO_NAMESPACE + this.m_AttrCurrentMediaSize);
                            if (this.m_strProductIDString != null) {
                                if (!(this.m_strProductIDString.equals(WirelessType.TYPE_P530D) || this.m_AttrMediaSize == this.m_AttrCurrentMediaSize)) {
                                    strMSG = ERROR_SIZE_NOT_MATCH_1004;
                                    strMSG = strMSG + ":" + GetMediaSize(this.m_AttrMediaSize) + ":" + GetMediaSize(this.m_AttrCurrentMediaSize);
                                    this.m_bNotMath = true;
                                    SetErrorMSG(strMSG);
                                    DecideNextStep(SettingStep.Step_Error);
                                    break;
                                }
                            }
                            DecideNextStepAndPrepareReadBuff(5, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.INTERRUPTED /*15*/:
                    i = this.m_iPrintedJobNumber;
                    arrayList2 = this.m_iQueueJobIdList;
                    int iNextNumber = (r0 + r0.size()) * this.mOneJobPicNum;
                    int iCopies = ((Integer) this.m_iPhotoCopiesList.get(iNextNumber)).intValue();
                    this.LOG.m385i("Step Prepare Job ", "iPrintedJobNumber: " + this.m_iPrintedJobNumber);
                    this.LOG.m385i("Step Prepare Job  ", "iQueue ID Number: " + this.m_iQueueJobIdList.size());
                    this.LOG.m385i("Step Prepare Job  ", "iNextNumber: " + iNextNumber);
                    i2 = 0;
                    while (true) {
                        i = this.mOneJobPicNum;
                        if (i2 < r0) {
                            this.m_NowObjectIdList[i2] = (byte[]) ((byte[]) this.m_ObjIdsList.get(iNextNumber)).clone();
                            int iNextNumber2 = iNextNumber + 1;
                            this.m_NowStorageIdList[i2] = (byte[]) ((byte[]) this.m_StorageIdsList.get(iNextNumber)).clone();
                            this.LOG.m385i("m_NowObjectIdList " + i2, "obj: " + ByteConvertUtility.ByteToInt(this.m_NowObjectIdList[i2], 0, 4));
                            this.LOG.m385i("m_NowStorageIdList " + i2, "objSID: " + ByteConvertUtility.ByteToInt(this.m_NowStorageIdList[i2], 0, 4));
                            i2++;
                            iNextNumber = iNextNumber2;
                        } else {
                            i = this.m_iLastCp;
                            if (r0 != -1) {
                                iCopies -= this.m_iLastCp;
                            }
                            this.m_OneJob.shJobId = 0;
                            this.m_OneJob.bFrmt = (byte) 1;
                            short s = (short) iCopies;
                            this.m_OneJob.shCopies = s;
                            jobInfo = this.m_OneJob;
                            jobInfo.bQlty = this.m_byJobQTY;
                            this.m_OneJob.bType = (byte) 1;
                            jobInfo = this.m_OneJob;
                            jobInfo.bMSize = this.m_AttrMediaSize;
                            jobInfo = this.m_OneJob;
                            jobInfo.bPrintLayout = this.m_bytePrintout;
                            if (this.m_AttrSelTexture) {
                                if (this.m_AttrTexturePrint.booleanValue()) {
                                    this.m_OneJob.bTxtr = (byte) 1;
                                    jobInfo = this.m_OneJob;
                                    jobInfo.boDuplex = this.m_bDuplex;
                                    this.m_offsetWrite = 0;
                                    this.m_OneJob.iTotal = 1;
                                    this.m_OneJob.iMaskSize = 0;
                                    this.m_OneJob.iTotalSize = 0;
                                    jobInfo = this.m_OneJob;
                                    jobInfo.boMaskColor = this.m_boMaskColor;
                                    jobInfo = this.m_OneJob;
                                    jobInfo.bSharpen = this.m_bSharpenValue;
                                    if (this.m_maskData != null) {
                                        this.m_OneJob.iTotal = 2;
                                        jobInfo = this.m_OneJob;
                                        jobInfo.iMaskSize = this.m_maskData.length;
                                    }
                                    DecideNextStep(SettingStep.Step_GetStorageInfoRequest);
                                    break;
                                }
                            }
                            this.m_OneJob.bTxtr = (byte) 0;
                            jobInfo = this.m_OneJob;
                            jobInfo.boDuplex = this.m_bDuplex;
                            this.m_offsetWrite = 0;
                            this.m_OneJob.iTotal = 1;
                            this.m_OneJob.iMaskSize = 0;
                            this.m_OneJob.iTotalSize = 0;
                            jobInfo = this.m_OneJob;
                            jobInfo.boMaskColor = this.m_boMaskColor;
                            jobInfo = this.m_OneJob;
                            jobInfo.bSharpen = this.m_bSharpenValue;
                            if (this.m_maskData != null) {
                                this.m_OneJob.iTotal = 2;
                                jobInfo = this.m_OneJob;
                                jobInfo.iMaskSize = this.m_maskData.length;
                            }
                            DecideNextStep(SettingStep.Step_GetStorageInfoRequest);
                        }
                    }
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    this.m_AttrLenOfDiskName = -1;
                    this.m_strDiskName = null;
                    if (!Get_Storage_Info_Request(this.m_NowStorageIdList[0])) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetStorageInfoResponse);
                    break;
                case ConnectionResult.SIGN_IN_FAILED /*17*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 70) {
                                    if (this.m_lpReadData[3] == 1) {
                                        this.m_iStorageErrNum++;
                                        this.m_bStorageError = true;
                                        SetErrorMSG(ERROR_FIND_NO_STORAGEID_1005);
                                    }
                                }
                                if (this.m_lpReadData[2] == 64) {
                                    if (this.m_lpReadData[3] == 4) {
                                        if (!this.m_bCheckSDcardAccess) {
                                            this.m_bCheckSDcardAccess = true;
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                            break;
                                        }
                                        SetErrorMSG(ERROR_STORAGE_ACCESS_DENIED_1006);
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetStorageInfoResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SERVICE_UPDATING /*18*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            i = this.m_AttrLenOfDiskName;
                            if (r0 != -1) {
                                if (this.m_strDiskName == null) {
                                    try {
                                        byte[] byteName = new byte[this.m_AttrLenOfDiskName];
                                        i2 = 0;
                                        while (true) {
                                            i = this.m_AttrLenOfDiskName;
                                            if (i2 >= r0) {
                                                this.m_strDiskName = new String(byteName, "BIG5");
                                                DecideNextStep(SettingStep.Step_CreateJobRequest);
                                                break;
                                            }
                                            byteName[i2] = this.m_lpReadData[i2];
                                            i2++;
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                        DecideErrorStatus();
                                        break;
                                    }
                                }
                            }
                            this.m_AttrLenOfDiskName = this.m_lpReadData[6];
                            DecideNextStepAndPrepareReadBuff(this.m_AttrLenOfDiskName, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                    break;
                    break;
                case ConnectionResult.SERVICE_MISSING_PERMISSION /*19*/:
                    if (!Create_Job_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_CreateJobResponse);
                    break;
                case ConnectionResult.RESTRICTED_PROFILE /*20*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                this.LOG.m384e("CreateJob ReadResponse", XmlPullParser.NO_NAMESPACE + this.m_lpReadData[2] + this.m_lpReadData[3]);
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_CreateJobResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case FTP.DEFAULT_PORT /*21*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_lpReadData[7] != -1) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            jobInfo = this.m_OneJob;
                            jobInfo.shJobId = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                            DecideNextStep(SettingStep.Step_StartJobRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.SUPDUP_OUTPUT /*22*/:
                    if (!Start_Job_For_QuickPrint_Request(this.m_NowObjectIdList, this.m_NowStorageIdList)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_StartJobResponse);
                    break;
                case TelnetOption.SEND_LOCATION /*23*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            SendMessage(SMTPReply.START_MAIL_INPUT, ERROR_BUSY_INITIAL_1001);
                            DecideNextStep(SettingStep.Step_SendDataRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.TERMINAL_TYPE /*24*/:
                    if (!Send_Data_On_Printer_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_SendDataResponse);
                    break;
                case SMTP.DEFAULT_PORT /*25*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                            this.m_strDiskName = null;
                            this.m_AttrLenOfDiskName = -1;
                            this.m_iQueueJobIdList.add(Integer.valueOf(this.m_OneJob.shJobId));
                            arrayList = this.m_iPrintedJobIdList;
                            arrayList2 = this.m_iQueueJobIdList;
                            SendMessage(RequestState.REQUEST_QUICK_PRINT_SENDED_DONE, String.valueOf((r0.size() + r0.size()) * this.mOneJobPicNum));
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                    this.m_byteJobStatus = (byte) -1;
                    if (!this.m_iQueueJobIdList.isEmpty()) {
                        if (!Get_Job_Status_Request(((Integer) this.m_iQueueJobIdList.get(0)).intValue())) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetJobStatusResponse);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                    break;
                case TelnetOption.OUTPUT_MARKING /*27*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 66) {
                                    if (this.m_lpReadData[3] == 2) {
                                        this.m_strError0001 = null;
                                        this.m_bNext = true;
                                        this.LOG.m385i("Step_GetJobStatusResponse m_bNext", String.valueOf(this.m_bNext));
                                        this.m_iPrintedJobIdList.add(this.m_iQueueJobIdList.get(0));
                                        this.m_iPrintedJobNumber = this.m_iPrintedJobIdList.size();
                                        this.m_iQueueJobIdList.remove(0);
                                        if (!this.m_bErrorOccur) {
                                            DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                            break;
                                        }
                                        Stop();
                                        break;
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(5, 0, SettingStep.Step_GetJobStatusResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.TERMINAL_LOCATION_NUMBER /*28*/:
                    if (this.m_bStopForCancel) {
                        DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                    } else {
                        if (this.m_iPrintedJobNumber * this.mOneJobPicNum < this.m_ObjIdsList.size()) {
                            if (this.m_iQueueJobIdList.isEmpty()) {
                                DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                            } else {
                                Thread.sleep(1500);
                                DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                            }
                        } else {
                            if (this.m_iPrintedJobNumber * this.mOneJobPicNum == this.m_ObjIdsList.size()) {
                                DecideNextStep(SettingStep.Step_GetUnCleanNumberRequest);
                            }
                        }
                    }
                    this.m_iCopiesOfJob = 0;
                    this.m_iPreCopies = 0;
                    break;
                case TelnetOption.REGIME_3270 /*29*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            b = this.m_byteJobStatus;
                            if (r0 == -1) {
                                this.m_byteJobStatus = this.m_lpReadData[3];
                                if (this.m_lpReadData[4] != -1) {
                                    this.m_bCpAttrGet = true;
                                    DecideNextStepAndPrepareReadBuff(12, 0, null);
                                    break;
                                }
                            }
                            if (this.m_bCpAttrGet) {
                                this.m_bCpAttrGet = false;
                                this.m_iCopiesOfJob = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                                if (this.m_iCopiesOfJob > 0) {
                                    if (this.m_iCopiesOfJob == this.m_iPreCopies + 1) {
                                        this.m_iPreCopies = this.m_iCopiesOfJob;
                                        this.m_bCopiesPlus = true;
                                        this.LOG.m385i("m_iCopiesOfJob", String.valueOf(this.m_iCopiesOfJob));
                                        DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                        break;
                                    }
                                }
                            }
                            DecideNextStep(SettingStep.Step_GetJobStatusResponseSuccess_plus);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.X3_PAD /*30*/:
                    if (!this.m_bStopForCancel) {
                        b = this.m_byteJobStatus;
                        if (r0 != -128) {
                            b = this.m_byteJobStatus;
                            if (r0 != 2) {
                                this.m_strError0001 = null;
                                SendMessage(SMTPReply.START_MAIL_INPUT, ERROR_BUSY_INITIAL_1001);
                            }
                            if (!this.m_bErrorOccur) {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                            Stop();
                            break;
                        }
                        if (this.m_strError0001 != null) {
                            SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER, this.m_strError0001);
                            this.m_strError0001 = null;
                        }
                        if (this.m_iPrintedJobNumber * this.mOneJobPicNum >= this.m_ObjIdsList.size()) {
                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                            break;
                        }
                        i = this.m_iPrintedJobNumber;
                        arrayList2 = this.m_iQueueJobIdList;
                        if ((r0 + r0.size()) * this.mOneJobPicNum < this.m_ObjIdsList.size()) {
                            if (this.m_iQueueJobIdList.size() >= 2) {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                            break;
                        }
                        DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                    break;
                case TelnetOption.WINDOW_SIZE /*31*/:
                    if (this.m_iPaperCountList == null) {
                        this.m_iPaperCountList = new HashMap();
                    }
                    if (this.m_strProductIDString != null) {
                        this.mFrameTypeList = PrinterInfo.SetFrameType(this.m_strProductIDString);
                    }
                    GetPrintFrameCount();
                    break;
                case BorderDelFragment.PENDDING_SIZE /*32*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetPrintFrameResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.REMOTE_FLOW_CONTROL /*33*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            i = this.m_AttrPrintFrameLength;
                            if (r0 != -1) {
                                if (this.m_AttrPrintFrameNumber == -1) {
                                    i = this.m_AttrPrintFrameLength;
                                    if (r0 != 7) {
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrPrintFrameNumber = ByteConvertUtility.ByteToLong(this.m_lpReadData, 3, 4);
                                    int iTotalFrame = GetPrinterPageNumber(this.m_strProductIDString, this.m_AttrPrintFrameNumber);
                                    byte mFrmaeType = ((Byte) this.mFrameTypeList.get(0)).byteValue();
                                    String strPrintoutType = PrinterInfo.GetFrameName(this.m_Context, mFrmaeType);
                                    this.m_iPaperCountList.put(strPrintoutType, Integer.valueOf(iTotalFrame));
                                    if (strPrintoutType.equals(this.m_strPrintOutItem)) {
                                        if (this.m_bNext) {
                                            SendMessage(RequestState.REQUEST_QUICK_PRINT_NEXT, String.valueOf(iTotalFrame));
                                        }
                                        if (this.m_bCopiesPlus) {
                                            this.m_bCopiesPlus = false;
                                            SendMessage(RequestState.REQUEST_CHECK_COPIES, String.valueOf(iTotalFrame));
                                        }
                                    }
                                    this.m_AttrPrintFrameNumber = -1;
                                    this.m_AttrPrintFrameLength = -1;
                                    if (this.mFrameTypeList.size() > 0) {
                                        this.mFrameTypeList.remove(0);
                                    }
                                    if (this.mFrameTypeList.size() <= 0) {
                                        SendMessage(RequestState.REQUEST_GPS_TOTAL_PRINTED_COUNT, null);
                                        if (!this.m_bNext) {
                                            DecideNextStep(SettingStep.Step_GetJobStatusResponseSuccess_plus);
                                            break;
                                        }
                                        this.m_bNext = false;
                                        DecideNextStep(SettingStep.Step_GetJobStatusResponse_plus);
                                        break;
                                    }
                                    GetPrintFrameCount();
                                    break;
                                }
                            }
                            this.m_AttrPrintFrameLength = (this.m_lpReadData[1] << 8) | this.m_lpReadData[2];
                            DecideNextStepAndPrepareReadBuff(this.m_AttrPrintFrameLength, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.LINEMODE /*34*/:
                    if (!Get_UnClean_Number_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetUnCleanNumberResponse);
                    break;
                case TelnetOption.X_DISPLAY_LOCATION /*35*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetUnCleanNumberResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TelnetOption.OLD_ENVIRONMENT_VARIABLES /*36*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    }
                    if (IsReadComplete()) {
                        i = this.m_AttrCleanLength;
                        if (r0 != -1) {
                            byte iFlag = this.m_lpReadData[0];
                            this.LOG.m385i("Get Clean Flag", XmlPullParser.NO_NAMESPACE + iFlag);
                            if ((iFlag & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
                                i = this.m_AttrUnCleanNumber;
                                if (r0 == -1) {
                                    int iUnCleanFrame = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                                    this.LOG.m385i("iUnCleanFrame", XmlPullParser.NO_NAMESPACE + iUnCleanFrame);
                                    if (iUnCleanFrame >= 0) {
                                        this.m_AttrUnCleanNumber = iUnCleanFrame / 4;
                                        DecideNextStep(SettingStep.Step_Complete);
                                        break;
                                    }
                                }
                            }
                        }
                        this.m_AttrCleanLength = this.m_lpReadData[2];
                        this.LOG.m385i("m_AttrCleanLength", XmlPullParser.NO_NAMESPACE + this.m_AttrCleanLength);
                        DecideNextStepAndPrepareReadBuff(this.m_AttrCleanLength, 0, null);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
            }
            boolean bRet;
            if (IsConnectError()) {
                StopTimerOut();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                this.LOG.m384e("Quick-IsConnectError", strMSG);
                if (!this.m_bNotMath) {
                    this.m_iResponseErrorTimes++;
                }
                i = this.m_iResponseErrorTimes;
                if (r0 < 10) {
                    bRet = Reconnection();
                } else {
                    bRet = false;
                }
                if (!bRet) {
                    Stop();
                    SendMessage(RequestState.REQUEST_QUICK_PRINT_ERROR, strMSG);
                }
            } else if (IsTimeoutError()) {
                strMSG = GetErrorMSGTimeOut();
                this.LOG.m384e("Quick-IsTimeoutError", strMSG);
                this.m_iResponseErrorTimes++;
                i = this.m_iResponseErrorTimes;
                if (r0 < 10) {
                    bRet = Reconnection();
                } else {
                    bRet = false;
                }
                if (!bRet) {
                    Stop();
                    SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, strMSG);
                }
            }
        }
    }

    byte CheckBit(byte bCheck, byte bMask) {
        return (byte) (bCheck & bMask);
    }

    public void SetMaskColor(byte boMaskColor) {
        this.m_boMaskColor = boMaskColor;
    }

    public byte GetMaskColor() {
        return this.m_boMaskColor;
    }

    public int GetJobId() {
        return this.m_OneJob.shJobId;
    }

    public int GetPrintedLastCopies() {
        int iLastCp = 0;
        if (this.m_iCopiesOfJob != -1) {
            iLastCp = this.m_iCopiesOfJob;
        }
        if (this.m_iLastCp != -1) {
            iLastCp += this.m_iLastCp;
        }
        this.LOG.m384e("Get_iLastCp", String.valueOf(iLastCp));
        return iLastCp;
    }

    public int GetPrintedCount() {
        return this.mOneJobPicNum * this.m_iPrintedJobIdList.size();
    }

    public String GetMediaSize(byte bySize) {
        if (bySize == 1) {
            return "4x6";
        }
        if (bySize == 2) {
            return "5x7";
        }
        if (bySize == 3) {
            return "6x8";
        }
        if (bySize == 4) {
            return "6x9";
        }
        if (bySize == 5) {
            return "2x3";
        }
        return "4x6";
    }

    public HashMap<String, Integer> GetTotalPrintedFrameRecord() {
        return this.m_iPaperCountList;
    }

    public void InitTotalPrintedCountList() {
        if (this.m_iPaperCountList != null) {
            this.m_iPaperCountList.clear();
        }
    }

    private void GetPrintFrameCount() {
        if (this.m_strProductIDString != null && this.mFrameTypeList != null && !this.mFrameTypeList.isEmpty()) {
            if (Get_Print_Frame_Or_Page_Request(((Byte) this.mFrameTypeList.get(0)).byteValue())) {
                DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrintFrameResponse);
            } else {
                DecideNextStep(SettingStep.Step_Error);
            }
        }
    }
}
