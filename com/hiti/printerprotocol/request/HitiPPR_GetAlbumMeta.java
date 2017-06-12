package com.hiti.printerprotocol.request;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.utility.ByteConvertUtility;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetAlbumMeta extends HitiPPR_PrinterCommandNew {
    ArrayList<byte[]> m_AlbumIdList;
    int m_AlbumIdNum;
    ArrayList<byte[]> m_AlbumStorageIdList;
    String m_AttrBatteryLevel;
    String m_AttrErrorDescription;
    int m_AttrErrorDescriptionLength;
    byte m_AttrErrorType;
    String m_AttrFirmwareVersionString;
    int m_AttrFirmwareVersionStringLength;
    long m_AttrInitPrintFrame;
    long m_AttrPrintFrame;
    int m_AttrPrintFrameLength;
    byte m_AttrPrinterStatus;
    byte[] m_AttrPrinterStatusFlag;
    String m_AttrSSID;
    int m_AttrSSIDLength;
    byte m_AttrSecurityAlgorithm;
    String m_AttrSecurityKey;
    int m_AttrSecurityKeyLength;
    String m_AttrSerialNumber;
    int m_AttrSerialNumberStringLength;
    ArrayList<byte[]> m_AttrStorageIdList;
    int m_AttrStorageIdNum;
    byte[] m_NowStorageId;
    int m_cntPhoto;
    int m_iLastRead;
    int m_iRead;
    int m_readNum;
    int m_storageIdNum;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetAlbumMeta.1 */
    static /* synthetic */ class C03971 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageIdResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleRequest.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponse.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponseSuccess.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    public HitiPPR_GetAlbumMeta(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = (byte) -1;
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrFirmwareVersionStringLength = -1;
        this.m_AttrFirmwareVersionString = null;
        this.m_AttrSerialNumberStringLength = -1;
        this.m_AttrSerialNumber = null;
        this.m_AttrBatteryLevel = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = (byte) -1;
        this.m_AttrErrorType = (byte) -1;
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrame = -1;
        this.m_AttrInitPrintFrame = -1;
        this.m_storageIdNum = -1;
        this.m_AttrStorageIdNum = -1;
        this.m_AttrStorageIdList = null;
        this.m_AlbumStorageIdList = null;
        this.m_AlbumIdNum = -1;
        this.m_NowStorageId = null;
        this.m_AlbumIdList = null;
        this.m_cntPhoto = 1;
        this.m_iRead = -1;
        this.m_iLastRead = -1;
        this.m_readNum = 0;
        this.LOG.m385i("HitiPPR_GetAlbumMeta", "HitiPPR_GetAlbumMeta_Constructor");
    }

    public void GetAlbumID(ArrayList<byte[]> byteAlbumIDList, ArrayList<byte[]> byteAlbumStorageIdList) {
        if (this.m_AlbumIdList != null && this.m_AlbumStorageIdList != null) {
            Iterator it = this.m_AlbumIdList.iterator();
            while (it.hasNext()) {
                byteAlbumIDList.add((byte[]) it.next());
            }
            it = this.m_AlbumStorageIdList.iterator();
            while (it.hasNext()) {
                byteAlbumStorageIdList.add((byte[]) it.next());
            }
        }
    }

    public String GetSSID() {
        return this.m_AttrSSID;
    }

    public String GetSecurityKey() {
        return this.m_AttrSecurityKey;
    }

    public void StartRequest() {
        GetAlbumId();
    }

    private void GetAlbumId() {
        if (IsRunning()) {
            this.LOG.m385i("HitiPPR_GetAlbumMeta", String.valueOf(GetCurrentStep()));
            int i;
            int j;
            switch (C03971.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    this.LOG.m383d("Step_Complete", "m_AlbumIdNum: " + this.m_AlbumIdNum);
                    if (this.m_AlbumIdNum != 0) {
                        SendMessage(RequestState.REQUEST_GET_ALBUM_ID_META, null);
                        StopForNextCommand();
                        break;
                    }
                    SendMessage(RequestState.REQUEST_GET_ALBUM_ID_META_ERROR, this.m_Context.getString(this.R_STRING_ERROR_NOPHOTO));
                    this.m_AlbumIdNum = -1;
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (!this.m_bDirectConnect) {
                        if (!Authentication_Request()) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                            break;
                        }
                    }
                    DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                    break;
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
                                    if (this.m_AttrSecurityAlgorithm != -1) {
                                        if (this.m_AttrSecurityKeyLength != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                char[] AttrSecurityKey = new char[this.m_AttrSecurityKeyLength];
                                                for (i = 0; i < this.m_AttrSecurityKeyLength; i++) {
                                                    AttrSecurityKey[i] = (char) this.m_lpReadData[i];
                                                }
                                                this.m_AttrSecurityKey = String.valueOf(AttrSecurityKey);
                                                Log.i("m_AttrSecurityKey", this.m_AttrSecurityKey);
                                                DecideNextStep(SettingStep.Step_GetStorageIdRequest);
                                                break;
                                            }
                                        }
                                        this.m_AttrSecurityKeyLength = this.m_lpReadData[3];
                                        this.LOG.m385i("m_AttrSecurityKeyLength", String.valueOf(this.m_AttrSecurityKeyLength));
                                        DecideNextStepAndPrepareReadBuff(this.m_AttrSecurityKeyLength + 1, 0, null);
                                        break;
                                    }
                                    this.m_AttrSecurityAlgorithm = this.m_lpReadData[3];
                                    Log.i("m_AttrSecurityAlgorithm", String.valueOf(this.m_AttrSecurityAlgorithm));
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                char[] AttrSSID = new char[this.m_AttrSSIDLength];
                                for (i = 0; i < this.m_AttrSSIDLength; i++) {
                                    AttrSSID[i] = (char) this.m_lpReadData[i];
                                }
                                this.m_AttrSSID = String.valueOf(AttrSSID);
                                this.LOG.m385i("m_AttrSSID", this.m_AttrSSID);
                                if (this.m_lpReadData[this.m_AttrSSIDLength] != -1) {
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_GetStorageIdRequest);
                                    break;
                                }
                            }
                            this.m_AttrSSIDLength = this.m_lpReadData[2];
                            DecideNextStepAndPrepareReadBuff(this.m_AttrSSIDLength + 1, 0, null);
                            this.LOG.m385i("m_AttrSSIDLength", String.valueOf(this.m_AttrSSIDLength));
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Storage_Id_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetStorageIdResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 71 && this.m_lpReadData[3] == 1) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_FIND_NO_STORAGEID));
                                } else if (this.m_lpReadData[2] == 70 && this.m_lpReadData[3] == 5) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOT_SUPPORT_STORAGE));
                                } else if (this.m_lpReadData[2] == 70 && this.m_lpReadData[3] == 6) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED));
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetStorageIdResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrStorageIdNum != -1) {
                                if (this.m_AttrStorageIdList == null) {
                                    this.m_AttrStorageIdList = new ArrayList();
                                    for (i = 0; i < this.m_AttrStorageIdNum; i++) {
                                        byte[] storageId = new byte[4];
                                        for (j = 0; j < 4; j++) {
                                            storageId[j] = this.m_lpReadData[(i * 4) + j];
                                        }
                                        this.m_AttrStorageIdList.add(storageId);
                                        Log.i("storageID", String.valueOf(ByteConvertUtility.ByteToInt(storageId, 0, 4)));
                                    }
                                    DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
                                    break;
                                }
                            }
                            this.m_AttrStorageIdNum = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                            DecideNextStepAndPrepareReadBuff(this.m_AttrStorageIdNum * 4, 0, null);
                            Log.i("m_AttrStorageIdNum", String.valueOf(this.m_AttrStorageIdNum));
                            break;
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (this.m_storageIdNum != 0) {
                        if (this.m_storageIdNum == -1) {
                            this.m_storageIdNum = this.m_AttrStorageIdList.size();
                            this.m_AlbumIdList = new ArrayList();
                            this.m_AlbumStorageIdList = new ArrayList();
                        }
                        ArrayList arrayList = this.m_AttrStorageIdList;
                        int i2 = this.m_storageIdNum - 1;
                        this.m_storageIdNum = i2;
                        this.m_NowStorageId = (byte[]) arrayList.get(i2);
                        byte[] parentObjH = new byte[]{(byte) -1, (byte) -1, (byte) -1, (byte) -1};
                        byte[] objFormat = new byte[]{(byte) 48, (byte) 2};
                        if (!Get_Object_Handle_Request(this.m_NowStorageId, parentObjH, objFormat)) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectHandleResponse);
                            break;
                        }
                    }
                    DecideNextStep(SettingStep.Step_Complete);
                    this.m_AlbumIdNum = -1;
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 70 && this.m_lpReadData[3] == 5) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOPHOTO));
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetObjectHandleResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            this.LOG.m385i("m_AlbumIdNum", String.valueOf(this.m_AlbumIdNum));
                            if (this.m_AlbumIdNum != -1) {
                                int num = this.m_iRead / 4;
                                this.LOG.m385i("num of ID this time", String.valueOf(num));
                                for (i = 0; i < num; i++) {
                                    byte[] albumId = new byte[4];
                                    for (j = 0; j < 4; j++) {
                                        albumId[j] = this.m_lpReadData[(i * 4) + j];
                                    }
                                    this.m_AlbumIdList.add(albumId);
                                    this.m_AlbumStorageIdList.add(this.m_NowStorageId);
                                    this.LOG.m385i("Add albumId", XmlPullParser.NO_NAMESPACE + Integer.toHexString(ByteConvertUtility.ByteToInt(albumId, 0, 4)));
                                }
                                if (this.m_readNum != 0) {
                                    if (this.m_readNum != 1) {
                                        this.m_cntPhoto++;
                                        if (this.m_cntPhoto == this.m_readNum + 1) {
                                            if (this.m_iLastRead != 0) {
                                                this.m_iRead = this.m_iLastRead;
                                                DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                                this.m_readNum = 0;
                                                break;
                                            }
                                            DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
                                            this.m_AlbumIdNum = -1;
                                            this.m_cntPhoto = 1;
                                            this.m_iRead = -1;
                                            this.m_iLastRead = -1;
                                            this.m_readNum = 0;
                                            break;
                                        }
                                        DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                        break;
                                    }
                                    this.m_iRead = this.m_iLastRead;
                                    DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                    this.m_iLastRead = 0;
                                    this.m_readNum = 0;
                                    break;
                                }
                                DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
                                this.m_AlbumIdNum = -1;
                                this.m_cntPhoto = 1;
                                this.m_iRead = -1;
                                this.m_iLastRead = -1;
                                this.m_readNum = 0;
                                break;
                            }
                            this.m_AlbumIdNum = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                            this.LOG.m384e("**m_AlbumIdNum", String.valueOf(this.m_AlbumIdNum));
                            if (this.m_AlbumIdNum != 0) {
                                this.m_lpReadData = new byte[(this.m_AlbumIdNum * 4)];
                                this.m_iRead = this.m_AlbumIdNum * 4;
                                DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                break;
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
                SendMessage(RequestState.REQUEST_GET_ALBUM_ID_META_ERROR, strMSG);
                Stop();
            } else if (IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }

    byte CheckBit(byte bCheck, byte bMask) {
        return (byte) (bCheck & bMask);
    }
}
