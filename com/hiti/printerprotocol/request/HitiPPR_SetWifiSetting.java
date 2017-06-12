package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_SetWifiSetting extends HitiPPR_PrinterCommand {
    private String m_AttrSSID;
    private String m_AttrSecurityKey;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_SetWifiSetting.1 */
    static /* synthetic */ class C04151 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_ConfigureNetworkSettingRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_ConfigureNetworkSettingResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public HitiPPR_SetWifiSetting(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSID = XmlPullParser.NO_NAMESPACE;
        this.m_AttrSecurityKey = XmlPullParser.NO_NAMESPACE;
    }

    public void StartRequest() {
        ConfigureNetworkInfo();
    }

    public void ConfigureNetworkInfo() {
        if (IsRunning()) {
            switch (C04151.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_SET_WIFI_SETTING, null);
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
                                DecideNextStep(SettingStep.Step_ConfigureNetworkSettingRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Configure_NetWork_Setting_Request(this.m_AttrSSID, this.m_AttrSecurityKey)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_ConfigureNetworkSettingResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_Complete);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                SendMessage(RequestState.REQUEST_SET_WIFI_SETTING_ERROR, GetErrorMSGConnectFail());
                Stop();
            } else if (IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }

    public void SetSSID(String strAttrSSID) {
        this.m_AttrSSID = strAttrSSID;
    }

    public void SetPassword(String strAttrSecurityKey) {
        this.m_AttrSecurityKey = strAttrSecurityKey;
    }

    public String GetSSID() {
        return this.m_AttrSSID;
    }

    public String GetPassword() {
        return this.m_AttrSecurityKey;
    }
}
