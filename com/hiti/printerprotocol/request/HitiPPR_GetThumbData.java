package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.gcm.Task;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.printercommand.Job;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew.Error;
import com.hiti.utility.ByteConvertUtility;
import com.hiti.utility.FileUtility;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetThumbData extends HitiPPR_PrinterCommandNew {
    private static int BUFFER_READ_LEN;
    private static int LIMIT_DATA_TRANS;
    private static int SD_TEST_TIMES;
    Job mJob;
    String mPhotoName;
    int m_AttrLenOfDiskName;
    int m_AttrLenOfObjName;
    int m_LenOfPhotoName;
    byte[] m_PhotoID;
    byte[] m_StorageID;
    String m_ThumbnailPath;
    int m_cnt;
    int m_iBusyTestTime;
    int m_iLoadedIndex;
    String m_strAlbumName;
    String m_strImagePath;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetThumbData.1 */
    static /* synthetic */ class C04031 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetThumbnailRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetThumbnailResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetThumbnailResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageRequest.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponse.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponseSuccess.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    static {
        BUFFER_READ_LEN = Task.EXTRAS_LIMIT_BYTES;
        LIMIT_DATA_TRANS = SMTPReply.UNRECOGNIZED_COMMAND;
        SD_TEST_TIMES = 10;
    }

    public HitiPPR_GetThumbData(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_PhotoID = null;
        this.m_StorageID = null;
        this.m_ThumbnailPath = null;
        this.m_strImagePath = null;
        this.m_AttrLenOfObjName = -1;
        this.m_AttrLenOfDiskName = -1;
        this.m_LenOfPhotoName = -1;
        this.m_cnt = 0;
        this.m_strAlbumName = null;
        this.m_iLoadedIndex = -1;
        this.m_iBusyTestTime = SD_TEST_TIMES;
        this.mPhotoName = null;
        this.mJob = null;
    }

    public void PutIDs(byte[] photoID, byte[] storageID) {
        this.m_PhotoID = photoID;
        this.m_StorageID = storageID;
    }

    public void putJob(Job job) {
        this.mJob = job;
        PutIDs(ByteConvertUtility.IntToByte(job.getThumbnailId()), ByteConvertUtility.IntToByte(job.getStorageId()));
    }

    public Job getJob() {
        if (this.mJob != null) {
            this.mJob.setThumbnailPath(this.m_ThumbnailPath);
            this.mJob.ThumbnailName = this.mPhotoName;
        }
        return this.mJob;
    }

    public void PutLoadedIndex(int index) {
        this.m_iLoadedIndex = index;
    }

    public int GetLoadedIndex() {
        return this.m_iLoadedIndex;
    }

    public void PutAlbumName(String strAlbumName) {
        this.m_strAlbumName = strAlbumName;
    }

    public String GetAlbumName() {
        return this.m_strAlbumName;
    }

    public String GetImagePath() {
        return this.m_strImagePath;
    }

    public String GetThumbPath() {
        return this.m_ThumbnailPath;
    }

    public String GetPhotoName() {
        return this.mPhotoName;
    }

    public void StartRequest() {
        GetThumbData();
    }

    private void InitalVariable() {
        this.m_AttrLenOfObjName = -1;
        this.m_AttrLenOfDiskName = -1;
        this.m_cnt = 0;
    }

    private void GetThumbData() {
        FileNotFoundException e;
        IOException e2;
        String strMSG;
        if (IsRunning()) {
            this.LOG.m385i("HitiPPR_GetThumbData", String.valueOf(GetCurrentStep()));
            int i;
            String folderPath;
            String photoName;
            FileOutputStream fos;
            int iReadSum;
            boolean boReadDone;
            FileOutputStream fileOutputStream;
            int iNeedRead;
            int iStart;
            int readLen;
            switch (C04031.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(FTPReply.FILE_ACTION_PENDING, null);
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    InitalVariable();
                    if (!this.m_bDirectConnect) {
                        if (!Authentication_Request()) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    int iAlbumID = ByteConvertUtility.ByteToInt(this.m_PhotoID, 0, 4);
                    this.LOG.m385i("m_PhotoID", Integer.toHexString(iAlbumID));
                    if (iAlbumID != -1) {
                        if (!Get_Object_Info_Request(this.m_StorageID, this.m_PhotoID)) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectInfoResponse);
                        break;
                    }
                    this.mPhotoName = this.m_Context.getString(this.R_STRING_ROOT_FOLDER);
                    DecideNextStep(SettingStep.Step_GetObjectNumberRequest);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    } else if (IsReadComplete()) {
                        if (CheckCommandSuccess()) {
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetObjectInfoResponseSuccess);
                            this.m_iBusyTestTime = SD_TEST_TIMES;
                            break;
                        }
                        if (this.m_lpReadData[2] == 71) {
                            if (this.m_lpReadData[3] == 1) {
                                SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_FIND_NO_STORAGEID));
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                        }
                        if (this.m_lpReadData[2] == 70) {
                            if (this.m_lpReadData[3] == 5) {
                                SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOT_SUPPORT_STORAGE));
                                DecideNextStep(SettingStep.Step_Error);
                            }
                        }
                        if (this.m_lpReadData[2] == 70) {
                            if (this.m_lpReadData[3] == 6) {
                                this.m_iBusyTestTime--;
                                if (this.m_iBusyTestTime > 0) {
                                    DecideNextStep(SettingStep.Step_GetObjectInfoRequest);
                                } else {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED));
                                }
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                    }
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            i = this.m_LenOfPhotoName;
                            if (r0 != -1) {
                                if (this.mPhotoName == null) {
                                    String m_EncodeType;
                                    if (this.m_lpReadData[2] == 16) {
                                        m_EncodeType = "UNICODE";
                                    } else {
                                        m_EncodeType = "ASCII";
                                    }
                                    try {
                                        byte[] byName = new byte[(this.m_LenOfPhotoName - 32)];
                                        if (m_EncodeType.equals("UNICODE")) {
                                            ByteConvertUtility.EdienChange(this.m_lpReadData, 32, this.m_LenOfPhotoName - 32, byName);
                                            this.mPhotoName = new String(byName, m_EncodeType);
                                            this.mPhotoName = this.mPhotoName.substring(0, this.mPhotoName.length() - 1);
                                        } else {
                                            int i2 = 0;
                                            while (true) {
                                                i = byName.length;
                                                if (i2 < r0) {
                                                    byName[i2] = this.m_lpReadData[i2 + 32];
                                                    i2++;
                                                } else {
                                                    this.mPhotoName = new String(byName, m_EncodeType);
                                                }
                                            }
                                        }
                                        DecideNextStep(SettingStep.Step_GetThumbnailRequest);
                                    } catch (UnsupportedEncodingException e3) {
                                        e3.printStackTrace();
                                        this.LOG.m384e("Step_GetObjectInfoResponseSuccess", "UnsupportedEncodingException");
                                        DecideErrorStatus();
                                    }
                                    this.LOG.m384e("GetObjectInfo: " + m_EncodeType, "mPhotoName: " + this.mPhotoName);
                                    break;
                                }
                            }
                            this.m_LenOfPhotoName = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4) - 4;
                            DecideNextStepAndPrepareReadBuff(this.m_LenOfPhotoName, 0, null);
                            this.LOG.m385i("m_LenOfPhotoName", String.valueOf(this.m_LenOfPhotoName));
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Thumbnail_Request(this.m_StorageID, this.m_PhotoID)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetThumbnailResponse);
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    } else if (IsReadComplete()) {
                        if (CheckCommandSuccess()) {
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetThumbnailResponseSuccess);
                            this.m_iBusyTestTime = SD_TEST_TIMES;
                            break;
                        }
                        this.LOG.m384e("Step_GetThumbnailResponseError", Integer.toHexString(this.m_lpReadData[2]) + "_" + Integer.toHexString(this.m_lpReadData[3]));
                        if (this.m_lpReadData[2] == 70) {
                            if (this.m_lpReadData[3] == 4) {
                                String msg = Error.NOTHUMBNAIL.toString();
                                this.LOG.m384e("Step_GetThumbnailResponseError", XmlPullParser.NO_NAMESPACE + msg);
                                SetErrorMSG(msg);
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                        }
                        if (this.m_lpReadData[2] == 66) {
                            if (this.m_lpReadData[3] == 5) {
                                SetErrorMSG(Error.SIZE_OVER_25.toString());
                                DecideNextStep(SettingStep.Step_Error);
                            }
                        }
                        if (this.m_lpReadData[2] == 71) {
                            if (this.m_lpReadData[3] == 1) {
                                SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_FIND_NO_STORAGEID));
                                DecideNextStep(SettingStep.Step_Error);
                            }
                        }
                        if (this.m_lpReadData[2] == 70) {
                            if (this.m_lpReadData[3] == 5) {
                                SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOT_SUPPORT_STORAGE));
                                DecideNextStep(SettingStep.Step_Error);
                            }
                        }
                        if (this.m_lpReadData[2] == 70) {
                            if (this.m_lpReadData[3] == 6) {
                                this.m_iBusyTestTime--;
                                if (this.m_iBusyTestTime > 0) {
                                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetThumbnailRequest);
                                } else {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED));
                                }
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                    }
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    this.m_lpReadData = new byte[BUFFER_READ_LEN];
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                    } else if (IsReadComplete()) {
                        int sizeOfThumbnail = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                        String strPhotoID = String.valueOf(ByteConvertUtility.ByteToInt(this.m_PhotoID, 0, 4));
                        folderPath = this.m_Context.getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
                        photoName = PringoConvenientConst.PRINBIZ_THUMB_PRE_NAME + MobileInfo.GetTimeStamp() + strPhotoID + PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG;
                        FileUtility.CreateFolder(folderPath);
                        this.m_ThumbnailPath = folderPath + "/" + photoName;
                        try {
                            fos = new FileOutputStream(this.m_ThumbnailPath, false);
                            iReadSum = 0;
                            boReadDone = true;
                            while (true) {
                                if (!boReadDone) {
                                    try {
                                        if (IsReadComplete()) {
                                            fos.flush();
                                            fos.close();
                                            if (!boReadDone) {
                                                DecideNextStep(SettingStep.Step_Complete);
                                            }
                                            fileOutputStream = fos;
                                        }
                                    } catch (FileNotFoundException e4) {
                                        e = e4;
                                        fileOutputStream = fos;
                                    } catch (IOException e5) {
                                        e2 = e5;
                                        fileOutputStream = fos;
                                    }
                                }
                                if (IsRunning()) {
                                    if (IsReadComplete()) {
                                        DecideNextStepAndPrepareReadBuff(BUFFER_READ_LEN, 0, null);
                                        iReadSum += BUFFER_READ_LEN;
                                        if (iReadSum >= sizeOfThumbnail) {
                                            iReadSum -= BUFFER_READ_LEN;
                                            iNeedRead = sizeOfThumbnail - iReadSum;
                                            DecideNextStepAndPrepareReadBuff(iNeedRead, 0, null);
                                            boReadDone = false;
                                            iReadSum += iNeedRead;
                                        }
                                        iStart = 0;
                                    } else {
                                        this.m_cnt++;
                                        if (this.m_cnt <= LIMIT_DATA_TRANS) {
                                            iStart = GetOffsetRead();
                                        }
                                    }
                                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                                        readLen = GetOffsetRead() - iStart;
                                        fos.write(this.m_lpReadData, iStart, readLen);
                                    } else {
                                        DecideErrorStatus();
                                    }
                                }
                                fos.flush();
                                fos.close();
                                if (boReadDone) {
                                    DecideNextStep(SettingStep.Step_Complete);
                                }
                                fileOutputStream = fos;
                            }
                        } catch (FileNotFoundException e6) {
                            e = e6;
                            e.printStackTrace();
                            if (IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                this.LOG.m384e("IsConnectError", String.valueOf(strMSG));
                                if (strMSG.equals(Error.NOTHUMBNAIL.toString())) {
                                    if (strMSG.equals(Error.SIZE_OVER_25.toString())) {
                                        SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR, strMSG);
                                        Stop();
                                    }
                                    SendMessage(NNTPReply.SERVICE_DISCONTINUED, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL, strMSG);
                                StopForNextCommand();
                                return;
                            } else if (IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        } catch (IOException e7) {
                            e2 = e7;
                            e2.printStackTrace();
                            if (IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                this.LOG.m384e("IsConnectError", String.valueOf(strMSG));
                                if (strMSG.equals(Error.NOTHUMBNAIL.toString())) {
                                    SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                if (strMSG.equals(Error.SIZE_OVER_25.toString())) {
                                    SendMessage(NNTPReply.SERVICE_DISCONTINUED, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR, strMSG);
                                Stop();
                            } else if (IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        }
                    }
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (!Get_Object_Request(this.m_StorageID, this.m_PhotoID)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetImageResponse);
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 70) {
                                    if (this.m_lpReadData[3] == 4) {
                                        SetErrorMSG(Error.NOTHUMBNAIL.toString());
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetImageResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    this.m_lpReadData = new byte[BUFFER_READ_LEN];
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                    } else if (IsReadComplete()) {
                        int sizeOfBorderFile = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                        folderPath = this.m_Context.getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER;
                        photoName = PringoConvenientConst.PRINBIZ_THUMB_PRE_NAME + MobileInfo.GetTimeStamp() + PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG;
                        FileUtility.CreateFolder(folderPath);
                        this.m_strImagePath = folderPath + "/" + photoName;
                        try {
                            fos = new FileOutputStream(this.m_strImagePath, false);
                            iReadSum = 0;
                            boReadDone = true;
                            while (true) {
                                if (!boReadDone) {
                                    try {
                                        if (IsReadComplete()) {
                                            fos.flush();
                                            fos.close();
                                            if (!boReadDone) {
                                                SendMessage(FTPReply.FILE_ACTION_PENDING, "create_thumb");
                                                Stop();
                                            }
                                            fileOutputStream = fos;
                                        }
                                    } catch (FileNotFoundException e8) {
                                        e = e8;
                                        fileOutputStream = fos;
                                    } catch (IOException e9) {
                                        e2 = e9;
                                        fileOutputStream = fos;
                                    }
                                }
                                if (IsRunning()) {
                                    if (IsReadComplete()) {
                                        DecideNextStepAndPrepareReadBuff(BUFFER_READ_LEN, 0, null);
                                        iReadSum += BUFFER_READ_LEN;
                                        if (iReadSum >= sizeOfBorderFile) {
                                            iReadSum -= BUFFER_READ_LEN;
                                            iNeedRead = sizeOfBorderFile - iReadSum;
                                            DecideNextStepAndPrepareReadBuff(iNeedRead, 0, null);
                                            boReadDone = false;
                                            iReadSum += iNeedRead;
                                        }
                                        iStart = 0;
                                    } else {
                                        this.m_cnt++;
                                        i = this.m_cnt;
                                        if (r0 <= 30000) {
                                            iStart = GetOffsetRead();
                                        }
                                    }
                                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                                        readLen = GetOffsetRead() - iStart;
                                        fos.write(this.m_lpReadData, iStart, readLen);
                                    } else {
                                        DecideErrorStatus();
                                    }
                                }
                                fos.flush();
                                fos.close();
                                if (boReadDone) {
                                    SendMessage(FTPReply.FILE_ACTION_PENDING, "create_thumb");
                                    Stop();
                                }
                                fileOutputStream = fos;
                            }
                        } catch (FileNotFoundException e10) {
                            e = e10;
                            e.printStackTrace();
                            if (IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                this.LOG.m384e("IsConnectError", String.valueOf(strMSG));
                                if (strMSG.equals(Error.NOTHUMBNAIL.toString())) {
                                    if (strMSG.equals(Error.SIZE_OVER_25.toString())) {
                                        SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR, strMSG);
                                        Stop();
                                    }
                                    SendMessage(NNTPReply.SERVICE_DISCONTINUED, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL, strMSG);
                                StopForNextCommand();
                                return;
                            } else if (IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        } catch (IOException e11) {
                            e2 = e11;
                            e2.printStackTrace();
                            if (IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                this.LOG.m384e("IsConnectError", String.valueOf(strMSG));
                                if (strMSG.equals(Error.NOTHUMBNAIL.toString())) {
                                    SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                if (strMSG.equals(Error.SIZE_OVER_25.toString())) {
                                    SendMessage(NNTPReply.SERVICE_DISCONTINUED, strMSG);
                                    StopForNextCommand();
                                    return;
                                }
                                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR, strMSG);
                                Stop();
                            } else if (IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        }
                    }
            }
            if (IsConnectError()) {
                StopTimerOut();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                this.LOG.m384e("IsConnectError", String.valueOf(strMSG));
                if (strMSG.equals(Error.NOTHUMBNAIL.toString())) {
                    SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL, strMSG);
                    StopForNextCommand();
                    return;
                }
                if (strMSG.equals(Error.SIZE_OVER_25.toString())) {
                    SendMessage(NNTPReply.SERVICE_DISCONTINUED, strMSG);
                    StopForNextCommand();
                    return;
                }
                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR, strMSG);
                Stop();
            } else if (IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }
}
