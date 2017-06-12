package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.JobInfo;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_SendPhoto extends HitiPPR_PrinterCommand {
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private Byte m_AttrErrorType;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    private String m_AttrSSID;
    private int m_AttrSSIDLength;
    private Byte m_AttrSecurityAlgorithm;
    private String m_AttrSecurityKey;
    private int m_AttrSecurityKeyLength;
    private byte m_boMaskColor;
    private int m_iPrintCount;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_SendPhoto.1 */
    static /* synthetic */ class C04111 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataErrorDueToPrinter.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoRequest.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponse.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponseSuccess.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PrepareCreateJobRequest.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobRequest.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponse.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponseSuccess.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobRequest.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobResponse.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataRequest.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataResponse.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    public HitiPPR_SendPhoto(Context context, String IP, int iPort, MSGHandler msgHandler) {
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
        this.m_boMaskColor = (byte) 0;
        this.m_iPrintCount = 0;
        this.m_OneJob = new JobInfo();
    }

    public void StartRequest() {
        SendPhoto();
    }

    private void InitialPrinterVariable() {
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
    }

    public void SendPhoto() {
        if (IsRunning()) {
            String strMSG;
            switch (C04111.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_SEND_PHOTO, String.valueOf(this.m_OneJob.shJobId));
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    strMSG = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (this.m_ErrorString != null) {
                        strMSG = this.m_ErrorString;
                    }
                    SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER, strMSG);
                    Stop();
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    InitialPrinterVariable();
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (!Get_NetWork_Info_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetNetworkInfoResponse);
                        break;
                    }
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetNetworkInfoResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrSSIDLength != -1) {
                                if (this.m_AttrSSID != null) {
                                    if (this.m_AttrSecurityAlgorithm.byteValue() != (byte) -1) {
                                        if (this.m_AttrSecurityKeyLength != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                this.m_AttrSecurityKey = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSecurityKeyLength);
                                                this.LOG.m385i("m_AttrSecurityKey", this.m_AttrSecurityKey);
                                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                                break;
                                            }
                                        }
                                        this.m_AttrSecurityKeyLength = this.m_lpReadData[3];
                                        this.LOG.m385i("m_AttrSecurityKeyLength", String.valueOf(this.m_AttrSecurityKeyLength));
                                        DecideNextStepAndPrepareReadBuff(this.m_AttrSecurityKeyLength + 1, 0, null);
                                        break;
                                    }
                                    this.m_AttrSecurityAlgorithm = Byte.valueOf(this.m_lpReadData[3]);
                                    this.LOG.m385i("m_AttrSecurityAlgorithm", String.valueOf(this.m_AttrSecurityAlgorithm));
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                this.m_AttrSSID = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSSIDLength);
                                this.LOG.m385i("m_AttrSSID", this.m_AttrSSID);
                                if (this.m_lpReadData[this.m_AttrSSIDLength] != (byte) -1) {
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                    break;
                                }
                            }
                            this.m_AttrSSIDLength = this.m_lpReadData[2];
                            this.LOG.m385i("m_AttrSSIDLength", String.valueOf(this.m_AttrSSIDLength));
                            DecideNextStepAndPrepareReadBuff(this.m_AttrSSIDLength + 1, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                        break;
                    }
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(17, 0, SettingStep.Step_GetPrinterStatusResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                if (this.m_AttrErrorDescriptionLength != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        this.m_AttrErrorDescription = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrErrorDescriptionLength);
                                        this.LOG.m385i("m_AttrErrorDescription", this.m_AttrErrorDescription);
                                        SetErrorMSG(this.m_AttrErrorDescription);
                                        DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                        break;
                                    }
                                }
                                this.m_AttrErrorDescriptionLength = this.m_lpReadData[2];
                                DecideNextStepAndPrepareReadBuff(this.m_AttrErrorDescriptionLength + 1, 0, null);
                                break;
                            }
                            this.m_AttrPrinterStatusFlag = new byte[3];
                            this.m_AttrPrinterStatusFlag[0] = this.m_lpReadData[3];
                            this.m_AttrPrinterStatusFlag[1] = this.m_lpReadData[4];
                            this.m_AttrPrinterStatusFlag[2] = this.m_lpReadData[5];
                            this.m_AttrPrinterStatus = Byte.valueOf(this.m_lpReadData[10]);
                            this.m_AttrErrorType = Byte.valueOf(this.m_lpReadData[15]);
                            this.LOG.m385i("m_AttrPrinterStatusFlag", String.valueOf(this.m_AttrPrinterStatusFlag[0]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[1]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[2]));
                            this.LOG.m385i("m_AttrPrinterStatus", String.valueOf(this.m_AttrPrinterStatus.byteValue()));
                            this.LOG.m385i("m_AttrErrorType", String.valueOf(this.m_AttrErrorType.byteValue()));
                            this.LOG.m385i("m_lpReadData[16]", String.valueOf(this.m_lpReadData[16]));
                            if (this.m_lpReadData[16] == (byte) -1) {
                                if (this.m_AttrPrinterStatus.byteValue() == (byte) 1) {
                                    if (ByteConvertUtility.CheckBit(this.m_AttrPrinterStatusFlag[0], Byte.MIN_VALUE) != null) {
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrErrorDescriptionLength = 0;
                                    this.m_AttrErrorDescription = null;
                                    DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                                    break;
                                }
                                SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (this.m_imageData == null) {
                        Stop();
                        break;
                    }
                    this.m_OneJob.shJobId = 0;
                    this.m_OneJob.bFrmt = (byte) 1;
                    this.m_OneJob.shCopies = (short) this.m_iPrintCount;
                    this.m_OneJob.lSize = (long) this.m_imageData.length;
                    this.m_OneJob.bQlty = (byte) 1;
                    this.m_OneJob.bType = (byte) 1;
                    this.m_OneJob.bMSize = (byte) 5;
                    this.m_OneJob.bTxtr = (byte) 0;
                    this.m_offsetWrite = 0;
                    this.m_OneJob.iTotal = 1;
                    this.m_OneJob.iMaskSize = 0;
                    this.m_OneJob.iTotalSize = 0;
                    this.m_OneJob.boMaskColor = this.m_boMaskColor;
                    if (this.m_maskData != null) {
                        this.m_OneJob.iTotal = 2;
                        this.m_OneJob.iMaskSize = this.m_maskData.length;
                    }
                    int[] iSizeArray = new int[this.m_OneJob.iTotal];
                    iSizeArray[0] = (int) this.m_OneJob.lSize;
                    if (this.m_OneJob.iTotal > 1) {
                        iSizeArray[1] = this.m_OneJob.iMaskSize;
                    }
                    this.m_OneJob.iTotalSize = GetTotalSize(this.m_OneJob.iTotal, iSizeArray);
                    DecideNextStep(SettingStep.Step_CreateJobRequest);
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (!Create_Job_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_CreateJobResponse);
                        break;
                    }
                case ConnectionResult.CANCELED /*13*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_CreateJobResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.TIMEOUT /*14*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_lpReadData[7] != (byte) -1) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            this.m_OneJob.shJobId = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                            this.LOG.m385i("Get Job ID", String.valueOf(this.m_OneJob.shJobId));
                            DecideNextStep(SettingStep.Step_StartJobRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.INTERRUPTED /*15*/:
                    if (!Start_Job_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_StartJobResponse);
                        break;
                    }
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_SendDataRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_FAILED /*17*/:
                    long nWrite = 0;
                    if (this.m_OneJob.iTotalSize <= 0) {
                        Stop();
                        break;
                    }
                    if (this.m_OneJob.iTotalSize < HitiPPR_PrinterCommand.MAX_DATA_SIZE) {
                        nWrite = (long) this.m_OneJob.iTotalSize;
                    }
                    this.LOG.m385i("Send data size: " + String.valueOf(nWrite), "Step_SendDataRequest");
                    nWrite = Send_Data_Request(nWrite);
                    this.m_offsetWrite += nWrite;
                    JobInfo jobInfo = this.m_OneJob;
                    jobInfo.iTotalSize = (int) (((long) jobInfo.iTotalSize) - nWrite);
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_SendDataResponse);
                    break;
                case ConnectionResult.SERVICE_UPDATING /*18*/:
                    if (ReadResponse(DNSConstants.RECORD_REAPER_INTERVAL)) {
                        if (IsReadComplete()) {
                            if (CheckCommandSuccess()) {
                                if (this.m_OneJob.iTotalSize <= 0) {
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_SendDataRequest);
                                    break;
                                }
                            }
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
            }
            if (IsConnectError()) {
                boolean bRet;
                StopTimerOut();
                strMSG = GetErrorMSGConnectFail();
                Stop();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                this.m_iResponseErrorTimes++;
                if (this.m_iResponseErrorTimes < 10) {
                    bRet = Reconnection();
                } else {
                    bRet = false;
                }
                if (!bRet) {
                    SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR, strMSG);
                }
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    public String GetAttrSSID() {
        return this.m_AttrSSID;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }

    public void SetMaskColor(byte boMaskColor) {
        this.m_boMaskColor = boMaskColor;
    }

    public byte GetMaskColor() {
        return this.m_boMaskColor;
    }

    public int GetPrintCount() {
        return this.m_iPrintCount;
    }

    public void SetPrintCount(int iPrintCount) {
        this.m_iPrintCount = iPrintCount;
    }
}
