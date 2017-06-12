package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.utility.ByteConvertUtility;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetAllSetting extends HitiPPR_PrinterCommand {
    private int m_AttrAutoPowerOffSeconds;
    private String m_AttrBatteryLevel;
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private Byte m_AttrErrorType;
    private String m_AttrFirmwareVersionString;
    private int m_AttrFirmwareVersionStringLength;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    private String m_AttrProductIDString;
    private String m_AttrProductName;
    private int m_AttrProductNameLength;
    private String m_AttrSSID;
    private int m_AttrSSIDLength;
    private Byte m_AttrSecurityAlgorithm;
    private String m_AttrSecurityKey;
    private int m_AttrSecurityKeyLength;
    private String m_AttrSerialNumber;
    private int m_AttrSerialNumberStringLength;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetAllSetting.1 */
    static /* synthetic */ class C03981 {
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
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoRequest.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoResponse.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoResponseSuccess.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesRequest.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponse.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponseSuccess.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    public HitiPPR_GetAllSetting(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrProductIDString = null;
        this.m_AttrProductNameLength = -1;
        this.m_AttrProductName = null;
        this.m_AttrFirmwareVersionStringLength = -1;
        this.m_AttrFirmwareVersionString = null;
        this.m_AttrSerialNumberStringLength = -1;
        this.m_AttrSerialNumber = null;
        this.m_AttrBatteryLevel = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrAutoPowerOffSeconds = 0;
    }

    public void StartRequest() {
        GetAllSetting();
    }

    public void GetAllSetting() {
        if (IsRunning()) {
            this.LOG.m383d("GetAllSetting", XmlPullParser.NO_NAMESPACE + GetCurrentStep());
            switch (C03981.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_SETTING_ITEM_ALL, null);
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
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrSSIDLength != -1) {
                                if (this.m_AttrSSID != null) {
                                    if (this.m_AttrSecurityAlgorithm.byteValue() != -1) {
                                        if (this.m_AttrSecurityKeyLength != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                this.m_AttrSecurityKey = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSecurityKeyLength);
                                                this.LOG.m385i("m_AttrSecurityKey", this.m_AttrSecurityKey);
                                                DecideNextStep(SettingStep.Step_GetProductIDRequest);
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
                                if (this.m_lpReadData[this.m_AttrSSIDLength] != -1) {
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_GetProductIDRequest);
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
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Product_ID_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetProductIDResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideErrorStatus();
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(6, 0, SettingStep.Step_GetProductIDResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete() && this.m_AttrProductIDString == null) {
                            this.m_AttrProductIDString = GetWirelessType(this.m_lpReadData, 3);
                            this.LOG.m385i("m_AttrProductIDString", this.m_AttrProductIDString);
                            DecideNextStep(SettingStep.Step_GetPrinterInfoRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (!Get_Printer_Info_Request(this.m_AttrProductIDString)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterInfoResponse);
                        break;
                    }
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideErrorStatus();
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(6, 0, SettingStep.Step_GetPrinterInfoResponseSuccess);
                            this.m_AttrProductIDString = null;
                            break;
                        }
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrProductIDString != null) {
                                if (this.m_AttrProductNameLength != -1) {
                                    if (this.m_AttrProductName != null) {
                                        if (this.m_AttrFirmwareVersionStringLength != -1) {
                                            if (this.m_AttrFirmwareVersionString != null) {
                                                if (this.m_AttrSerialNumberStringLength != -1) {
                                                    if (this.m_AttrSerialNumber != null) {
                                                        if (this.m_AttrBatteryLevel == null) {
                                                            if (this.m_AttrProductIDString.equals(WirelessType.TYPE_P520L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P310W) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P750L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P461) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P530D)) {
                                                                this.m_AttrBatteryLevel = "100";
                                                            } else {
                                                                float CurrentSize = (float) ByteConvertUtility.ByteToInt(this.m_lpReadData, 4, 4);
                                                                this.LOG.m385i("CurrentSize", String.valueOf(CurrentSize));
                                                                float MaxSize = (float) ByteConvertUtility.ByteToInt(this.m_lpReadData, 8, 4);
                                                                this.LOG.m385i("MaxSize", String.valueOf(MaxSize));
                                                                this.m_AttrBatteryLevel = Integer.toString((int) ((CurrentSize / MaxSize) * 100.0f)) + "%";
                                                            }
                                                            this.LOG.m385i("m_BatteryLevel", this.m_AttrBatteryLevel);
                                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                                            break;
                                                        }
                                                    }
                                                    int iResize = 0;
                                                    int i = 0;
                                                    while (i < this.m_AttrSerialNumberStringLength && this.m_lpReadData[i] != null) {
                                                        iResize++;
                                                        i++;
                                                    }
                                                    if (iResize > 0) {
                                                        this.m_AttrSerialNumberStringLength = iResize;
                                                        this.m_AttrSerialNumber = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSerialNumberStringLength);
                                                    } else {
                                                        this.m_AttrSerialNumberStringLength = 14;
                                                        this.m_AttrSerialNumber = "00000000000000";
                                                    }
                                                    this.LOG.m385i("m_AttrSerialNumber", this.m_AttrSerialNumber);
                                                    this.LOG.m385i("m_AttrSerialNumberStringLength", String.valueOf(this.m_AttrSerialNumberStringLength));
                                                    if (!this.m_AttrProductIDString.equals(WirelessType.TYPE_P231) && !this.m_AttrProductIDString.equals(WirelessType.TYPE_P232)) {
                                                        if (this.m_AttrProductIDString.equals(WirelessType.TYPE_P520L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P310W) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P750L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P461) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P530D)) {
                                                            DecideNextStepAndPrepareReadBuff(1, 0, null);
                                                            break;
                                                        }
                                                    }
                                                    DecideNextStepAndPrepareReadBuff(13, 0, null);
                                                    break;
                                                }
                                                this.m_AttrSerialNumberStringLength = this.m_lpReadData[3];
                                                this.LOG.m385i("m_AttrSerialNumberStringLength", String.valueOf(this.m_lpReadData[3]));
                                                DecideNextStepAndPrepareReadBuff(this.m_AttrSerialNumberStringLength, 0, null);
                                                break;
                                            }
                                            this.m_AttrFirmwareVersionString = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrFirmwareVersionStringLength);
                                            this.LOG.m385i("m_AttrFirmwareVersionString", this.m_AttrFirmwareVersionString);
                                            DecideNextStepAndPrepareReadBuff(4, 0, null);
                                            break;
                                        }
                                        this.m_AttrFirmwareVersionStringLength = this.m_lpReadData[3];
                                        this.LOG.m385i("m_AttrFirmwareVersionStringLength", String.valueOf(this.m_AttrFirmwareVersionStringLength));
                                        DecideNextStepAndPrepareReadBuff(this.m_AttrFirmwareVersionStringLength, 0, null);
                                        break;
                                    }
                                    this.m_AttrProductName = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrProductNameLength);
                                    this.LOG.m385i("m_AttrProductName!!", String.valueOf(this.m_AttrProductName));
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                this.m_AttrProductNameLength = this.m_lpReadData[2];
                                this.LOG.m385i("m_AttrProductNameLength", String.valueOf(this.m_AttrProductNameLength));
                                DecideNextStepAndPrepareReadBuff(this.m_AttrProductNameLength, 0, null);
                                break;
                            }
                            this.m_AttrProductIDString = GetWirelessType(this.m_lpReadData, 3);
                            this.LOG.m385i("m_AttrProductIDString", this.m_AttrProductIDString);
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.CANCELED /*13*/:
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                        break;
                    }
                case ConnectionResult.TIMEOUT /*14*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideErrorStatus();
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
                case ConnectionResult.INTERRUPTED /*15*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                if (this.m_AttrErrorDescriptionLength != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        this.m_AttrErrorDescription = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrErrorDescriptionLength);
                                        this.LOG.m385i("m_AttrErrorDescription", this.m_AttrErrorDescription);
                                        DecideNextStep(SettingStep.Step_GetPrinterCapabilitiesRequest);
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
                            this.LOG.m385i("m_AttrPrinterStatus", String.valueOf(this.m_AttrPrinterStatus.byteValue()));
                            this.LOG.m385i("m_AttrPrinterStatusFlag", String.valueOf(this.m_AttrPrinterStatusFlag[0]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[1]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[2]));
                            this.LOG.m385i("m_AttrErrorType", String.valueOf(this.m_AttrErrorType.byteValue()));
                            this.LOG.m385i("m_lpReadData[16]", String.valueOf(this.m_lpReadData[16]));
                            if (this.m_lpReadData[16] == -1) {
                                this.m_AttrErrorDescriptionLength = 0;
                                this.m_AttrErrorDescription = null;
                                this.LOG.m385i("Step_GetPrinterStatusResponseSuccess", "Step_GetPrinterStatusResponseSuccess");
                                DecideNextStep(SettingStep.Step_GetPrinterCapabilitiesRequest);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    if (!this.m_AttrProductIDString.equals(WirelessType.TYPE_P231) && !this.m_AttrProductIDString.equals(WirelessType.TYPE_P232) && !this.m_AttrProductIDString.equals(WirelessType.TYPE_P310W) && !this.m_AttrProductIDString.equals(WirelessType.TYPE_P461)) {
                        if (this.m_AttrProductIDString.equals(WirelessType.TYPE_P520L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P750L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P530D)) {
                            this.m_AttrAutoPowerOffSeconds = 0;
                            DecideNextStep(SettingStep.Step_Complete);
                            break;
                        }
                    } else if (!Get_Printer_Capabilities_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterCapabilitiesResponse);
                        break;
                    }
                    break;
                case ConnectionResult.SIGN_IN_FAILED /*17*/:
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
                case ConnectionResult.SERVICE_UPDATING /*18*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            byte[] tmpSeconds = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
                            tmpSeconds[2] = this.m_lpReadData[3];
                            tmpSeconds[3] = this.m_lpReadData[4];
                            int iAutoPowerOFFseconds = ByteConvertUtility.ByteToInt(tmpSeconds, 0, 4);
                            this.m_AttrAutoPowerOffSeconds = 0;
                            if (iAutoPowerOFFseconds == 0 || iAutoPowerOFFseconds == 30 || iAutoPowerOFFseconds == 60 || iAutoPowerOFFseconds == SoapEnvelope.VER12 || iAutoPowerOFFseconds == 180 || iAutoPowerOFFseconds == 1200 || iAutoPowerOFFseconds == DNSConstants.DNS_TTL) {
                                this.m_AttrAutoPowerOffSeconds = iAutoPowerOFFseconds;
                            }
                            DecideNextStep(SettingStep.Step_Complete);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                String strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_SETTING_ITEM_ALL_ERROR, strMSG);
                Stop();
            } else if (IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }

    public String GetAttrSSID() {
        return this.m_AttrSSID;
    }

    public Byte GetAttrSecurityAlgorithm() {
        return this.m_AttrSecurityAlgorithm;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }

    public String GetAttrFirmwareVersionString() {
        return this.m_AttrFirmwareVersionString;
    }

    public String GetAttrSerialNumber() {
        return this.m_AttrSerialNumber;
    }

    public String GetAttrBatteryLevel() {
        return this.m_AttrBatteryLevel;
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

    public int GetAttrAutoPowerOffSeconds() {
        return this.m_AttrAutoPowerOffSeconds;
    }

    public String GetAttrProductIDString() {
        return this.m_AttrProductIDString;
    }
}
