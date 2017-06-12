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

public class HitiPPR_GetWifiConfig extends HitiPPR_PrinterCommandNew {
    private String m_AttrSSID;
    private int m_AttrSSIDLength;
    private Byte m_AttrSecurityAlgorithm;
    private String m_AttrSecurityKey;
    private int m_AttrSecurityKeyLength;
    private int m_iCnnectionMode;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetWifiConfig.1 */
    static /* synthetic */ class C04061 {
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

    public HitiPPR_GetWifiConfig(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_iCnnectionMode = -1;
    }

    public void StartRequest() {
        GetNetworkInfo();
    }

    public void GetNetworkInfo() {
        if (IsRunning()) {
            switch (C04061.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
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
                    if (!Get_NetWork_Current_Config_Request()) {
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
                                DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_GetNetworkInfoResponseSuccess);
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
                            if (this.m_AttrSSIDLength != -1) {
                                if (this.m_AttrSSID != null) {
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                }
                                this.m_AttrSSID = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSSIDLength);
                                if (this.m_lpReadData[this.m_AttrSSIDLength] != (byte) -1) {
                                    DecideNextStepAndPrepareReadBuff(34, 0, null);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                }
                            }
                            this.m_iCnnectionMode = this.m_lpReadData[3];
                            this.LOG.m384e("m_iCnnectionMode", String.valueOf(this.m_iCnnectionMode));
                            this.m_AttrSSIDLength = this.m_lpReadData[7];
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

    public int GetAttrWifiMode() {
        return this.m_iCnnectionMode;
    }

    public String GetAttrSSID() {
        if (this.m_AttrSSID == null) {
            this.m_AttrSSID = XmlPullParser.NO_NAMESPACE;
        }
        return this.m_AttrSSID;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }
}
