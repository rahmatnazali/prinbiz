package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.PrinterErrorCode;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_CleanModeRun extends HitiPPR_PrinterCommandNew {
    public static final String NEED_COVER_OPEN_FOR_CLEAN_MODE = "NEED_COVER_OPEN_FOR_CLEAN_MODE";
    private int m_AttrCleanLength;
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    boolean m_bCleanAsked;
    boolean m_bCoverOpenCheck;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_CleanModeRun.1 */
    static /* synthetic */ class C03951 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CleanModeRunRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CleanModeRunResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CleanModeRunResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataErrorDueToPrinter.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public HitiPPR_CleanModeRun(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrCleanLength = -1;
        this.m_bCleanAsked = false;
        this.m_bCoverOpenCheck = false;
    }

    public void StartRequest() {
        CheckPrintComplete();
    }

    public void SetCheckCoverOpen(boolean bCoverOpenCheck) {
        this.m_bCoverOpenCheck = bCoverOpenCheck;
    }

    public void CheckPrintComplete() {
        if (IsRunning()) {
            String strMSG;
            this.LOG.m385i("HitiPPR_CleanModeRun", String.valueOf(GetCurrentStep()));
            switch (C03951.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_CLEAN_MODE_RUN, null);
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
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
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                if (this.m_AttrErrorDescriptionLength != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        this.m_AttrErrorDescription = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrErrorDescriptionLength);
                                        this.LOG.m385i("Printer error", String.valueOf(this.m_AttrErrorDescription));
                                        if (this.m_bCoverOpenCheck) {
                                            if (CheckPrinterStatusCoverOpen(this.m_AttrPrinterStatusFlag)) {
                                                DecideNextStep(SettingStep.Step_Complete);
                                            } else {
                                                SetErrorMSG(NEED_COVER_OPEN_FOR_CLEAN_MODE);
                                                DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                            }
                                        } else if (!CheckPrinterStatusNotBusy(this.m_AttrPrinterStatusFlag)) {
                                            try {
                                                sleep(500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                        } else if (!CheckPrinterStatusCoverOpen(this.m_AttrPrinterStatusFlag)) {
                                            SetErrorMSG(NEED_COVER_OPEN_FOR_CLEAN_MODE);
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                        } else if (!this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0100)) {
                                            SetErrorMSG(this.m_AttrErrorDescription);
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                        } else if (this.m_bCleanAsked) {
                                            DecideNextStep(SettingStep.Step_Complete);
                                        } else {
                                            this.m_bCleanAsked = true;
                                            DecideNextStep(SettingStep.Step_CleanModeRunRequest);
                                        }
                                        this.m_AttrPrinterStatusFlag = null;
                                        this.m_AttrErrorDescriptionLength = -1;
                                        this.m_AttrErrorDescription = null;
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
                            if (this.m_lpReadData[16] == (byte) -1) {
                                SetErrorMSG(NEED_COVER_OPEN_FOR_CLEAN_MODE);
                                DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Clean_Mode_Run_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_CleanModeRunResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_CleanModeRunResponseSuccess);
                                break;
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrCleanLength != -1) {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                            this.m_AttrCleanLength = this.m_lpReadData[2];
                            DecideNextStepAndPrepareReadBuff(this.m_AttrCleanLength, 0, null);
                            break;
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    strMSG = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (this.m_ErrorString != null) {
                        strMSG = this.m_ErrorString;
                    }
                    this.LOG.m385i("CleanMode", "ErrorDueToPrinter: " + strMSG);
                    SendMessage(RequestState.REQUEST_CLEAN_MODE_RUN_ERROR_DUETO_PRINTER, strMSG);
                    Stop();
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                Stop();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_CLEAN_MODE_RUN_ERROR, strMSG);
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    private boolean CheckPrinterStatusCoverOpen(byte[] iStatusFlag) {
        this.LOG.m385i("CheckPrinterStatusCoverOpen", XmlPullParser.NO_NAMESPACE + iStatusFlag[2]);
        if (ByteConvertUtility.CheckBit(iStatusFlag[2], (byte) 8) != null) {
            return true;
        }
        return false;
    }

    private boolean CheckPrinterStatusNotBusy(byte[] iStatusFlag) {
        this.LOG.m385i("CheckPrinterStatusNotBusy", XmlPullParser.NO_NAMESPACE + iStatusFlag[0]);
        if (ByteConvertUtility.CheckBit(iStatusFlag[0], Byte.MIN_VALUE) == null) {
            return true;
        }
        return false;
    }
}
