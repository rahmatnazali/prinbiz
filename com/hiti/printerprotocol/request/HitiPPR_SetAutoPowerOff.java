package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_SetAutoPowerOff extends HitiPPR_PrinterCommand {
    private byte[] m_bAutoPowerOffSeconds;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_SetAutoPowerOff.1 */
    static /* synthetic */ class C04131 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SetAutoPowerOffRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SetAutoPowerOffResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesRequest.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponse.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponseSuccess.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public enum AUTO_POWER_OFF {
        OFF,
        OFF_30_SECONDS,
        OFF_01_MINUTE,
        OFF_02_MINUTES,
        OFF_03_MINUTES,
        OFF_20_MINUTES,
        OFF_60_MINUTES
    }

    public static AUTO_POWER_OFF IntToAUTO_POWER_OFF(int i) {
        if (i == 0) {
            return AUTO_POWER_OFF.OFF;
        }
        if (i == 1) {
            return AUTO_POWER_OFF.OFF_30_SECONDS;
        }
        if (i == 2) {
            return AUTO_POWER_OFF.OFF_01_MINUTE;
        }
        if (i == 3) {
            return AUTO_POWER_OFF.OFF_02_MINUTES;
        }
        if (i == 4) {
            return AUTO_POWER_OFF.OFF_03_MINUTES;
        }
        if (i == 5) {
            return AUTO_POWER_OFF.OFF_20_MINUTES;
        }
        if (i == 6) {
            return AUTO_POWER_OFF.OFF_60_MINUTES;
        }
        return AUTO_POWER_OFF.OFF;
    }

    public void SetAutoPowerOffSeconds(AUTO_POWER_OFF AutoPowerOffSeconds) {
        if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 0;
            this.m_bAutoPowerOffSeconds[1] = (byte) 0;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_30_SECONDS) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 0;
            this.m_bAutoPowerOffSeconds[1] = (byte) 30;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_01_MINUTE) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 0;
            this.m_bAutoPowerOffSeconds[1] = (byte) 60;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_02_MINUTES) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 0;
            this.m_bAutoPowerOffSeconds[1] = (byte) 120;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_03_MINUTES) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 0;
            this.m_bAutoPowerOffSeconds[1] = (byte) -76;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_20_MINUTES) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 4;
            this.m_bAutoPowerOffSeconds[1] = (byte) -80;
        } else if (AutoPowerOffSeconds == AUTO_POWER_OFF.OFF_60_MINUTES) {
            this.m_bAutoPowerOffSeconds[0] = (byte) 14;
            this.m_bAutoPowerOffSeconds[1] = (byte) 16;
        }
        this.LOG.m385i("Set Auto Power Off AutoPowerOffSeconds", String.valueOf(AutoPowerOffSeconds));
        this.LOG.m385i("Set Auto Power Off", String.valueOf(this.m_bAutoPowerOffSeconds[1]));
    }

    public HitiPPR_SetAutoPowerOff(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_bAutoPowerOffSeconds = new byte[]{(byte) -1, (byte) -1};
    }

    public void StartRequest() {
        if (this.m_bAutoPowerOffSeconds[0] == (byte) -1 && this.m_bAutoPowerOffSeconds[1] == (byte) -1) {
            Stop();
        } else {
            SetAutoPowerOff();
        }
    }

    public void SetAutoPowerOff() {
        if (IsRunning()) {
            switch (C04131.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(NNTPReply.SEND_ARTICLE_TO_POST, null);
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
                                DecideNextStep(SettingStep.Step_SetAutoPowerOffRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Auto_Power_off_Request(this.m_bAutoPowerOffSeconds)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(13, 0, SettingStep.Step_SetAutoPowerOffResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (ByteConvertUtility.CheckBit(this.m_lpReadData[10], Byte.MIN_VALUE) != null) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetPrinterCapabilitiesRequest);
                            this.LOG.m385i("Set Auto Power Off Response", "Step_SetAutoPowerOffResponse");
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (!Get_Printer_Capabilities_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterCapabilitiesResponse);
                        break;
                    }
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(6, 0, SettingStep.Step_GetPrinterCapabilitiesResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_lpReadData[3] == this.m_bAutoPowerOffSeconds[0] && this.m_lpReadData[4] == this.m_bAutoPowerOffSeconds[1]) {
                                DecideNextStep(SettingStep.Step_Complete);
                            } else {
                                DecideNextStep(SettingStep.Step_Error);
                            }
                            this.LOG.m385i("Set Auto Power Off", String.valueOf(this.m_lpReadData[4]));
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                SendMessage(RequestState.REQUEST_AUTO_POWER_OFF_ERROR, GetErrorMSGConnectFail());
                Stop();
            } else if (IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }
}
