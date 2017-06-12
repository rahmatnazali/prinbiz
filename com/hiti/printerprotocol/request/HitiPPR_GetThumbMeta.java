package com.hiti.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.printercommand.Job;
import com.hiti.utility.ByteConvertUtility;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_GetThumbMeta extends HitiPPR_PrinterCommandNew {
    private static int SD_TEST_TIMES;
    private Job mJob;
    String m_AttrBatteryLevel;
    String m_AttrErrorDescription;
    int m_AttrErrorDescriptionLength;
    byte m_AttrErrorType;
    String m_AttrFirmwareVersionString;
    int m_AttrFirmwareVersionStringLength;
    long m_AttrInitPrintFrame;
    int m_AttrLengthOfAlbumName;
    long m_AttrPrintFrame;
    int m_AttrPrintFrameLength;
    byte m_AttrPrinterStatus;
    byte[] m_AttrPrinterStatusFlag;
    String m_AttrSSID;
    int m_AttrSSIDLength;
    Byte m_AttrSecurityAlgorithm;
    String m_AttrSecurityKey;
    int m_AttrSecurityKeyLength;
    String m_AttrSerialNumber;
    int m_AttrSerialNumberStringLength;
    ArrayList<byte[]> m_PhotoIdList;
    int m_PhotoNum;
    private byte[] m_StorageID;
    private byte[] m_albumID;
    int m_cntPhoto;
    ArrayList<String> m_givenThumbNameList;
    int m_iBusyTestTime;
    int m_iLastRead;
    int m_iRead;
    int m_readNum;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetThumbMeta.1 */
    static /* synthetic */ class C04041 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetObjectHandleResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static {
        SD_TEST_TIMES = 10;
    }

    public HitiPPR_GetThumbMeta(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
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
        this.m_iBusyTestTime = SD_TEST_TIMES;
        this.m_PhotoNum = -1;
        this.m_AttrLengthOfAlbumName = -1;
        this.m_cntPhoto = 1;
        this.m_iRead = -1;
        this.m_iLastRead = -1;
        this.m_readNum = 0;
        this.m_PhotoIdList = null;
        this.m_givenThumbNameList = null;
        this.m_albumID = null;
        this.m_StorageID = null;
        this.mJob = null;
        this.LOG.m385i("HitiPPR_GetThumbMeta", "Constructor");
    }

    public void PutIDs(byte[] albumID, byte[] albumStorageID) {
        this.m_albumID = albumID;
        this.m_StorageID = albumStorageID;
    }

    public void putJob(Job job) {
        this.mJob = job;
        PutIDs(ByteConvertUtility.IntToByte(job.getAlbumId()), ByteConvertUtility.IntToByte(job.getStorageId()));
    }

    public ArrayList<Job> getJobList() {
        ArrayList<Job> mJobList = new ArrayList();
        if (this.mJob != null) {
            Iterator it = this.m_PhotoIdList.iterator();
            while (it.hasNext()) {
                byte[] byteId = (byte[]) it.next();
                Job job = new Job(GetSocket());
                job.setStorageId(this.mJob.getStorageId());
                job.setThumbnailId(ByteConvertUtility.ByteToInt(byteId, 0, 4));
                mJobList.add(job);
            }
        }
        return mJobList;
    }

    public void GetThumbIDList(ArrayList<Integer> photoIdList) {
        if (this.m_PhotoIdList != null) {
            Iterator it = this.m_PhotoIdList.iterator();
            while (it.hasNext()) {
                photoIdList.add(Integer.valueOf(ByteConvertUtility.ByteToInt((byte[]) it.next(), 0, 4)));
            }
        }
    }

    public void StartRequest() {
        GetThumbnails();
    }

    private void GetThumbnails() {
        if (IsRunning()) {
            this.LOG.m385i("GetThumbnails", String.valueOf(GetCurrentStep()));
            switch (C04041.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_THUMBNAILS_META, null);
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
                                DecideNextStep(SettingStep.Step_GetObjectHandleRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    this.m_PhotoIdList = new ArrayList();
                    if (!Get_Object_Handle_Request(this.m_StorageID, this.m_albumID, new byte[]{(byte) 56, (byte) 1})) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetObjectHandleResponse);
                        break;
                    }
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
                                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetThumbnailRequest);
                                    } else {
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED));
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetObjectHandleResponseSuccess);
                            this.m_iBusyTestTime = SD_TEST_TIMES;
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    this.m_lpReadData = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_PhotoNum != -1) {
                                int num = this.m_iRead / 4;
                                for (int i = 0; i < num; i++) {
                                    byte[] photoId = new byte[4];
                                    for (int j = 0; j < 4; j++) {
                                        photoId[j] = this.m_lpReadData[(i * 4) + j];
                                    }
                                    this.m_PhotoIdList.add(photoId);
                                }
                                if (this.m_readNum != 0) {
                                    if (this.m_readNum != 1) {
                                        this.m_cntPhoto++;
                                        if (this.m_cntPhoto != this.m_readNum + 1) {
                                            DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                            break;
                                        }
                                        this.m_iRead = this.m_iLastRead;
                                        DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                        this.m_readNum = 0;
                                        break;
                                    }
                                    this.m_iRead = this.m_iLastRead;
                                    DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
                                    this.m_readNum = 0;
                                    break;
                                }
                                DecideNextStep(SettingStep.Step_Complete);
                                break;
                            }
                            this.m_PhotoNum = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                            int sumRead = this.m_PhotoNum * 4;
                            int halfBuffer = this.m_lpReadData.length / 2;
                            if (sumRead <= halfBuffer) {
                                this.m_iRead = sumRead;
                                this.m_readNum = 0;
                            } else {
                                this.m_iRead = halfBuffer;
                                this.m_readNum = sumRead / this.m_iRead;
                                this.m_iLastRead = sumRead % this.m_iRead;
                            }
                            DecideNextStepAndPrepareReadBuff(this.m_iRead, 0, null);
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
                SendMessage(RequestState.REQUEST_GET_THUMBNAILS_META_ERROR, strMSG);
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
