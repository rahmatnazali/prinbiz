package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.CheckPrintStage;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.PrinterErrorCode;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_CheckPrintComplete extends HitiPPR_PrinterCommand {
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private Byte m_AttrErrorType;
    private long m_AttrInitPrintFrame;
    private long m_AttrPrintFrame;
    private int m_AttrPrintFrameLength;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    private CheckPrintStage m_CheckPrintStage;
    boolean m_boHaveSilver;
    int m_iCurrentPrintCount;
    int m_iLastPrintCount;
    int m_iPrintCount;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_CheckPrintComplete.1 */
    static /* synthetic */ class C03931 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CheckPrintCompleteErrorDueToPrinter.ordinal()] = 2;
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameRequest.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponse.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponseSuccess.ordinal()] = 7;
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
        }
    }

    public void SetCheckCount(int iPrintCount) {
        this.m_iPrintCount = iPrintCount;
    }

    public boolean GetHaveSilver() {
        return this.m_boHaveSilver;
    }

    public void SetHaveSilver(boolean boHaveSilver) {
        this.m_boHaveSilver = boHaveSilver;
    }

    public int GetCurrentPrintCount() {
        return this.m_iCurrentPrintCount;
    }

    public void SetCurrentPrintCount(int iCurrentPrintCount) {
        this.m_iCurrentPrintCount = iCurrentPrintCount;
    }

    public int GetLastPrintCount() {
        return this.m_iLastPrintCount;
    }

    public void SetLastPrintCount(int iLastPrintCount) {
        this.m_iLastPrintCount = iLastPrintCount;
    }

    public HitiPPR_CheckPrintComplete(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrame = -1;
        this.m_AttrInitPrintFrame = -1;
        this.m_CheckPrintStage = CheckPrintStage.Enter_Printing;
        this.m_iPrintCount = 1;
        this.m_iLastPrintCount = 0;
        this.m_iCurrentPrintCount = 0;
        this.m_boHaveSilver = false;
    }

    public void StartRequest() {
        CheckPrintComplete();
    }

    private void InitialVariable() {
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrame = -1;
        this.m_AttrInitPrintFrame = -1;
    }

    public void CheckPrintComplete() {
        if (IsRunning()) {
            String strMSG;
            switch (C03931.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE, null);
                    this.LOG.m384e("CheckPrintComplete", "Step_Complete");
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    strMSG = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (this.m_ErrorString != null) {
                        strMSG = this.m_ErrorString;
                    }
                    SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_ERROR_DUETO_PRINTER, strMSG);
                    Stop();
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    InitialVariable();
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (!Get_Print_Frame_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrintFrameResponse);
                        break;
                    }
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetPrintFrameResponseSuccess);
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
                            if (this.m_AttrPrintFrameLength != -1) {
                                if (this.m_AttrPrintFrame == -1) {
                                    if (this.m_AttrPrintFrameLength != 7) {
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrPrintFrame = ByteConvertUtility.ByteToLong(this.m_lpReadData, 3, 4);
                                    if (this.m_AttrInitPrintFrame == -1) {
                                        this.m_AttrInitPrintFrame = this.m_AttrPrintFrame;
                                    }
                                    int iHaveMask = 0;
                                    if (this.m_boHaveSilver) {
                                        iHaveMask = 1;
                                    }
                                    this.LOG.m384e("m_AttrInitPrintFrame", String.valueOf(this.m_AttrInitPrintFrame));
                                    this.LOG.m384e("m_AttrPrintFrame", String.valueOf(this.m_AttrPrintFrame));
                                    this.LOG.m384e("iHaveMask", String.valueOf(iHaveMask));
                                    int iCurrentPrintCount = (((int) (this.m_AttrPrintFrame - this.m_AttrInitPrintFrame)) / (iHaveMask + 4)) + this.m_iLastPrintCount;
                                    if (this.m_iCurrentPrintCount < iCurrentPrintCount && (this.m_AttrPrintFrame - this.m_AttrInitPrintFrame) % ((long) (iHaveMask + 4)) == 1 && this.m_CheckPrintStage == CheckPrintStage.Wait_Printing_O) {
                                        this.LOG.m384e("amend", "amend...HAHA");
                                        this.m_CheckPrintStage = CheckPrintStage.Wait_Printing_Y;
                                    }
                                    this.m_iCurrentPrintCount = iCurrentPrintCount;
                                    if (this.m_CheckPrintStage == CheckPrintStage.Enter_Printing) {
                                        SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS, this.m_Context.getString(this.R_STRING_PRINTER_STATUS_PRINTING));
                                        this.m_CheckPrintStage = CheckPrintStage.Wait_Printing_Y;
                                    }
                                    if ((this.m_AttrPrintFrame - this.m_AttrInitPrintFrame) % ((long) (iHaveMask + 4)) >= 1 && this.m_CheckPrintStage == CheckPrintStage.Wait_Printing_Y) {
                                        SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_SAVE_COUNTER, null);
                                        this.m_CheckPrintStage = CheckPrintStage.Wait_Printing_O;
                                    }
                                    if ((this.m_AttrPrintFrame - this.m_AttrInitPrintFrame) % ((long) (iHaveMask + 4)) == 0 && this.m_CheckPrintStage == CheckPrintStage.Wait_Printing_O) {
                                        SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS, this.m_Context.getString(this.R_STRING_PRINTTING_COMPLETE));
                                        this.m_CheckPrintStage = CheckPrintStage.Enter_Printing;
                                    }
                                    SendMessage(RequestState.REQUEST_SET_PRINT_COUNT, null);
                                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                    if (this.m_iCurrentPrintCount == this.m_iPrintCount) {
                                        SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS, this.m_Context.getString(this.R_STRING_PRINTTING_COMPLETE));
                                        SendMessage(RequestState.REQUEST_SET_PRINT_COUNT, null);
                                        DecideNextStep(SettingStep.Step_Complete);
                                        break;
                                    }
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
                                        if (this.m_AttrErrorType.byteValue() != 16 || !this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
                                            SetErrorMSG(this.m_AttrErrorDescription);
                                            this.LOG.m385i("m_AttrErrorDescription", this.m_AttrErrorDescription);
                                            DecideNextStep(SettingStep.Step_CheckPrintCompleteErrorDueToPrinter);
                                            break;
                                        }
                                        this.m_CheckPrintStage = CheckPrintStage.Wait_User_take_paper;
                                        this.m_AttrPrinterStatusFlag = null;
                                        this.m_AttrErrorDescriptionLength = -1;
                                        this.m_AttrErrorDescription = null;
                                        this.m_AttrPrintFrameLength = -1;
                                        this.m_AttrPrintFrame = -1;
                                        DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                        try {
                                            sleep(1000);
                                            break;
                                        } catch (InterruptedException e) {
                                            DecideNextStep(SettingStep.Step_Error);
                                            e.printStackTrace();
                                            break;
                                        }
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
                                if (this.m_AttrPrinterStatus.byteValue() != 1) {
                                    String strMsg = GetPrinterStatus(this.m_AttrPrinterStatus);
                                    if (this.m_CheckPrintStage == CheckPrintStage.Wait_User_take_paper) {
                                        this.m_CheckPrintStage = CheckPrintStage.Enter_Printing;
                                    }
                                    if (ByteConvertUtility.CheckBit(this.m_AttrPrinterStatusFlag[0], Byte.MIN_VALUE) != null) {
                                        strMsg = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY);
                                    }
                                    SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS, strMsg);
                                    try {
                                        DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                        this.m_AttrErrorDescription = null;
                                        this.m_AttrErrorDescriptionLength = -1;
                                        this.m_AttrPrinterStatusFlag = null;
                                        this.m_AttrPrintFrameLength = -1;
                                        this.m_AttrPrintFrame = -1;
                                        sleep(3000);
                                        break;
                                    } catch (InterruptedException e2) {
                                        e2.printStackTrace();
                                        break;
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Complete);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
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
                    SendMessage(RequestState.REQUEST_CHECK_PRINT_COMPLETE_ERROR, strMSG);
                }
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    public byte[] GetAttrPrinterStatusFlag() {
        return this.m_AttrPrinterStatusFlag;
    }

    public Byte GetAttrPrinterStatus() {
        return this.m_AttrPrinterStatus;
    }

    public Byte GetAttrErrorType() {
        return this.m_AttrErrorType;
    }

    public String GetAttrErrorDescription() {
        return this.m_AttrErrorDescription;
    }
}
