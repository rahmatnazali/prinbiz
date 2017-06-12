package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_GetWifiSetting extends HitiPPR_PrinterCommand {
    private String m_AttrSSID;
    private int m_AttrSSIDLength;
    private Byte m_AttrSecurityAlgorithm;
    private String m_AttrSecurityKey;
    private int m_AttrSecurityKeyLength;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetWifiSetting.1 */
    static /* synthetic */ class C04071 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public HitiPPR_GetWifiSetting(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
    }

    public void StartRequest() {
        GetNetworkInfo();
    }

    public void GetNetworkInfo() {
        if (IsRunning()) {
            switch (C04071.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_WIFI_SETTING, null);
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
                                DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_NetWork_Info_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetNetworkInfoResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
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
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    this.LOG.m385i("Step_GetNetworkInfoResponseSuccess", "Step_GetNetworkInfoResponseSuccess");
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrSSIDLength != -1) {
                                if (this.m_AttrSSID != null) {
                                    if (this.m_AttrSecurityAlgorithm.byteValue() != (byte) -1) {
                                        if (this.m_AttrSecurityKeyLength != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                this.m_AttrSecurityKey = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSecurityKeyLength);
                                                this.LOG.m385i("m_AttrSecurityKey", this.m_AttrSecurityKey);
                                                DecideNextStep(SettingStep.Step_Complete);
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
                                    DecideNextStep(SettingStep.Step_Complete);
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
            }
            if (IsConnectError()) {
                StopTimerOut();
                SendMessage(RequestState.REQUEST_GET_WIFI_SETTING_ERROR, GetErrorMSGConnectFail());
                Stop();
            } else if (IsTimeoutError()) {
                Stop();
            }
        }
    }

    public String GetAttrSSID() {
        return this.m_AttrSSID;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }
}
