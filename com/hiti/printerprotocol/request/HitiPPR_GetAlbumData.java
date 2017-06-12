package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.printercommand.Job;
import com.hiti.utility.ByteConvertUtility;
import java.io.UnsupportedEncodingException;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetAlbumData extends HitiPPR_PrinterCommandNew {
    private static int SD_TEST_TIMES;
    Job mJob;
    byte[] m_AlbumId;
    String m_AlbumName;
    byte m_AttrAlbumNameEncode;
    String m_EncodeType;
    int m_LenOfAlbumName;
    byte[] m_OnePhotoId;
    int m_iBusyTestTime;
    int m_iNumOfPhotoId;
    int m_index;
    byte[] m_storageId;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetAlbumData.1 */
    static /* synthetic */ class C03961 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectInfoResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectNumberRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectNumberResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectNumberResponseSuccess.ordinal()] = 9;
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

    static {
        SD_TEST_TIMES = 10;
    }

    public HitiPPR_GetAlbumData(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_storageId = null;
        this.m_AlbumId = null;
        this.m_LenOfAlbumName = -1;
        this.m_AlbumName = null;
        this.m_AttrAlbumNameEncode = (byte) -1;
        this.m_EncodeType = null;
        this.m_iNumOfPhotoId = -1;
        this.m_OnePhotoId = null;
        this.m_index = -1;
        this.m_iBusyTestTime = SD_TEST_TIMES;
        this.mJob = null;
        this.LOG.m384e("HitiPPR_GetAlbumData", "HitiPPR_GetAlbumData_ contructor");
    }

    public void PutIDs(byte[] storageID, byte[] albumID) {
        this.m_storageId = storageID;
        this.m_AlbumId = albumID;
    }

    public void putJob(Job job) {
        this.mJob = job;
        PutIDs(ByteConvertUtility.IntToByte(job.getStorageId()), ByteConvertUtility.IntToByte(job.getAlbumId()));
    }

    public Job getJob() {
        if (this.mJob != null) {
            this.mJob.setThumbnailId(ByteConvertUtility.ByteToInt(this.m_OnePhotoId, 0, 4));
            this.mJob.AlbumName = this.m_AlbumName;
            this.mJob.PhotoNumber = this.m_iNumOfPhotoId;
        }
        return this.mJob;
    }

    public void PutIndex(int index) {
        this.m_index = index;
    }

    public int GetIndex() {
        return this.m_index;
    }

    public byte[] GetOneThumbID() {
        return this.m_OnePhotoId;
    }

    public String GetAlbumName() {
        return this.m_AlbumName;
    }

    public byte[] GetStorageID() {
        return this.m_storageId;
    }

    public int GetNumberOfPhoto() {
        return this.m_iNumOfPhotoId;
    }

    public void StartRequest() {
        GetAlbumNameAndOneThumb();
    }

    private void GetAlbumNameAndOneThumb() {
        if (IsRunning()) {
            this.LOG.m385i("GetAlbumNameAndOneThumb", String.valueOf(GetCurrentStep()));
            int i;
            switch (C03961.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_ALBUM_DATA, null);
                    this.LOG.m385i("Step_Complete", "Step_Complete");
                    StopForNextCommand();
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
                    DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    int iAlbumID = ByteConvertUtility.ByteToInt(this.m_AlbumId, 0, 4);
                    this.LOG.m385i("m_AlbumId", Integer.toHexString(iAlbumID));
                    if (iAlbumID != -1) {
                        if (!Get_Object_Info_Request(this.m_storageId, this.m_AlbumId)) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectInfoResponse);
                            break;
                        }
                    }
                    this.m_AlbumName = this.m_Context.getString(this.R_STRING_ROOT_FOLDER);
                    this.LOG.m384e("m_AlbumName", XmlPullParser.NO_NAMESPACE + this.m_AlbumName);
                    DecideNextStep(SettingStep.Step_GetObjectNumberRequest);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 71 && this.m_lpReadData[3] == 1) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_FIND_NO_STORAGEID));
                                } else if (this.m_lpReadData[2] == 70 && this.m_lpReadData[3] == 5) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOT_SUPPORT_STORAGE));
                                } else if (this.m_lpReadData[2] == 70 && this.m_lpReadData[3] == 6) {
                                    this.m_iBusyTestTime--;
                                    if (this.m_iBusyTestTime > 0) {
                                        DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                                    } else {
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED));
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetObjectInfoResponseSuccess);
                            this.m_iBusyTestTime = SD_TEST_TIMES;
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_LenOfAlbumName != -1) {
                                if (this.m_AlbumName == null) {
                                    this.m_AttrAlbumNameEncode = this.m_lpReadData[2];
                                    if (this.m_AttrAlbumNameEncode == 16) {
                                        this.m_EncodeType = "UNICODE";
                                    } else {
                                        this.m_EncodeType = "ASCII";
                                    }
                                    try {
                                        byte[] byName;
                                        if (this.m_EncodeType == "UNICODE") {
                                            byName = new byte[(this.m_LenOfAlbumName - 32)];
                                            ByteConvertUtility.EdienChange(this.m_lpReadData, 32, this.m_LenOfAlbumName - 32, byName);
                                            this.m_AlbumName = new String(byName, this.m_EncodeType);
                                            this.m_AlbumName = this.m_AlbumName.substring(0, this.m_AlbumName.length() - 1);
                                        } else {
                                            byName = new byte[(this.m_LenOfAlbumName - 32)];
                                            for (i = 0; i < byName.length; i++) {
                                                byName[i] = this.m_lpReadData[i + 32];
                                            }
                                            this.m_AlbumName = new String(byName, this.m_EncodeType);
                                        }
                                        DecideNextStep(SettingStep.Step_GetObjectNumberRequest);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                        DecideErrorStatus();
                                    }
                                    this.LOG.m384e("m_AlbumName_len:" + this.m_LenOfAlbumName, XmlPullParser.NO_NAMESPACE + this.m_AlbumName);
                                    break;
                                }
                            }
                            this.m_LenOfAlbumName = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4) - 4;
                            DecideNextStepAndPrepareReadBuff(this.m_LenOfAlbumName, 0, null);
                            this.LOG.m385i("lengthOfAlbumName", String.valueOf(this.m_LenOfAlbumName));
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Object_Number_Request(this.m_storageId, this.m_AlbumId, new byte[]{(byte) 56, (byte) 1})) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectNumberResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_GetObjectNumberResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete() && this.m_iNumOfPhotoId == -1) {
                            this.m_iNumOfPhotoId = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                            this.LOG.m385i("m_iNumOfPhotoId", String.valueOf(this.m_iNumOfPhotoId));
                            DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    byte[] number = ByteConvertUtility.Short2Byte((short) (this.m_iNumOfPhotoId - 1));
                    byte[] index = new byte[]{number[0], number[1], number[0], number[1]};
                    if (!Get_Object_Handle_With_Range_Index_Request(this.m_storageId, this.m_AlbumId, index)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectHandleResponse);
                        break;
                    }
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_GetObjectHandleResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            this.LOG.m385i("num", String.valueOf(ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4)));
                            if (this.m_OnePhotoId == null) {
                                this.m_OnePhotoId = new byte[4];
                                for (i = 0; i < 4; i++) {
                                    this.m_OnePhotoId[i] = this.m_lpReadData[i + 4];
                                }
                                this.LOG.m385i("m_OnePhotoId", XmlPullParser.NO_NAMESPACE + ByteConvertUtility.ByteToInt(this.m_OnePhotoId, 0, 4));
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
                String strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_GET_ALBUM_DATA_ERROR, strMSG);
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
