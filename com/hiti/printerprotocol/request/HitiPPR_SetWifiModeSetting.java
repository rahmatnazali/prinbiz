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
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_SetWifiModeSetting extends HitiPPR_PrinterCommandNew {
    private static final int AP_MODE = 0;
    private static final int INFRA_MODE = 1;
    private String m_AttrSSID;
    private String m_AttrSecurityKey;
    private int m_iConnMode;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_SetWifiModeSetting.1 */
    static /* synthetic */ class C04141 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = HitiPPR_SetWifiModeSetting.INFRA_MODE;
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

    public HitiPPR_SetWifiModeSetting(Context context, String IP, int iPort, int iMode, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSID = XmlPullParser.NO_NAMESPACE;
        this.m_AttrSecurityKey = XmlPullParser.NO_NAMESPACE;
        this.m_iConnMode = AP_MODE;
        this.m_iConnMode = iMode;
    }

    public void StartRequest() {
        ConfigureNetworkInfo();
    }

    public void ConfigureNetworkInfo() {
        if (IsRunning()) {
            switch (C04141.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case INFRA_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_SET_WIFI_MODE_SETTING, null);
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, AP_MODE, SettingStep.Step_AuthenticationResponse);
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
                    if (this.m_iConnMode != INFRA_MODE) {
                        if (!Configure_AP_NetWork_Setting_Request(AP_MODE)) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, AP_MODE, SettingStep.Step_ConfigureNetworkSettingResponse);
                            break;
                        }
                    } else if (!Configure_Infra_NetWork_Setting_Request(INFRA_MODE, this.m_AttrSSID, this.m_AttrSecurityKey)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, AP_MODE, SettingStep.Step_ConfigureNetworkSettingResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    }
                    this.LOG.m384e("Step_ConfigureNetworkSettingResponse", ByteConvertUtility.ByteToString(this.m_lpReadData, AP_MODE, 4));
                    if (IsReadComplete()) {
                        if (!CheckCommandSuccess()) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStep(SettingStep.Step_Complete);
                            break;
                        }
                    }
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                SendMessage(RequestState.REQUEST_SET_WIFI_MODE_SETTING_ERROR, GetErrorMSGConnectFail());
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

    public String getSSID() {
        return this.m_AttrSSID;
    }

    public String getPassword() {
        return this.m_AttrSecurityKey;
    }
}
