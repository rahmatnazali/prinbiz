package com.hiti.printerprotocol.printercommand;

import android.content.Context;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_GetPrinterInfo;
import com.hiti.printerprotocol.request.HitiPPR_GetThumbData;
import com.hiti.printerprotocol.request.HitiPPR_GetThumbMeta;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import java.util.ArrayList;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class BasicCommand {
    String IP;
    CallBack mCallBack;
    Context mContext;
    HitiPPR_GetPrinterInfo mHitiPPR_GetPrinterInfo;
    HitiPPR_GetThumbData mHitiPPR_GetThumbData;
    HitiPPR_GetThumbMeta mHitiPPR_GetThumbMeta;
    HitiPPR_RecoveryPrinter mHitiPPR_RecoveryPrinter;
    int mPort;
    PrintHandler mPrinterHandler;
    boolean m_bThreadStop;

    public interface CallBack {
        void onErrorResponse(Error error, String str);

        void onShowWaitingDialog(boolean z);
    }

    public enum Error {
        Error_recovery_fail,
        Error_Get_info_fail,
        Error_Clean_Mode_fail,
        Error_Get_Security_info_fail,
        Error_Set_Security_info_fail,
        Error_Set_wifi_mode_fail,
        Error_Set_auto_power_off_fail,
        Error_Printer_timeout_fail,
        Error_Get_album_Id_fail,
        Error_Get_album_name_and_numbers_of_photo_fail,
        Error_Get_thumbnail_fail,
        Error_Printer_Model_Not_Match
    }

    public enum InfoType {
        FirmwareVersion,
        SerialNumber,
        ProductName
    }

    public interface BasicCallBack extends CallBack {
        void onGetInfoResult(InfoType infoType, String str);

        void onGetThumbnailFinished(Job job, boolean z);

        void onGetThumbnailIdListFinished(ArrayList<Job> arrayList);

        void onRecoveryFinished(Job job);
    }

    class PrintHandler extends MSGHandler {
        InfoType mInfoType;

        PrintHandler() {
            this.mInfoType = InfoType.FirmwareVersion;
        }

        public void setInfoType(InfoType info) {
            this.mInfoType = info;
        }

        public void handleMessage(Message msg) {
            if (BasicCommand.this.HaveListener()) {
                BasicCommand.this.mCallBack.onShowWaitingDialog(false);
            }
            if (!BasicCommand.this.m_bThreadStop) {
                Job job;
                switch (msg.what) {
                    case RequestState.REQUEST_RECOVERY_PRINTER /*305*/:
                        job = BasicCommand.this.mHitiPPR_RecoveryPrinter.getJob();
                        if (BasicCommand.this.HaveListener()) {
                            ((BasicCallBack) BasicCommand.this.mCallBack).onRecoveryFinished(job);
                        }
                    case RequestState.REQUEST_GET_PRINTER_INFO /*310*/:
                        String strFirmwareVersion = BasicCommand.this.mHitiPPR_GetPrinterInfo.GetAttrFirmwareVersionString();
                        String strSerialNumber = BasicCommand.this.mHitiPPR_GetPrinterInfo.GetAttrSerialNumber();
                        String strProductID = BasicCommand.this.mHitiPPR_GetPrinterInfo.GetAttrProductIDString();
                        String strProductName = BasicCommand.this.mHitiPPR_GetPrinterInfo.GetProductName();
                        BasicCommand.this.mHitiPPR_GetPrinterInfo.Stop();
                        if (strProductID.equals(WirelessType.TYPE_P461)) {
                            String result = null;
                            if (this.mInfoType == InfoType.FirmwareVersion) {
                                result = strFirmwareVersion;
                            }
                            if (this.mInfoType == InfoType.SerialNumber) {
                                result = strSerialNumber;
                            }
                            if (this.mInfoType == InfoType.ProductName) {
                                result = strProductName;
                            }
                            if (BasicCommand.this.HaveListener()) {
                                ((BasicCallBack) BasicCommand.this.mCallBack).onGetInfoResult(this.mInfoType, result);
                                return;
                            }
                            return;
                        }
                        BasicCommand.this.ShowError(Error.Error_Printer_Model_Not_Match, XmlPullParser.NO_NAMESPACE);
                    case RequestState.REQUEST_GET_PRINTER_INFO_ERROR /*311*/:
                        BasicCommand.this.ShowError(Error.Error_Get_info_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        BasicCommand.this.ShowError(Error.Error_Printer_timeout_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_GET_THUMBNAILS_META /*348*/:
                        ArrayList<Job> mJobList = BasicCommand.this.mHitiPPR_GetThumbMeta.getJobList();
                        if (BasicCommand.this.HaveListener()) {
                            ((BasicCallBack) BasicCommand.this.mCallBack).onGetThumbnailIdListFinished(mJobList);
                        }
                    case FTPReply.FILE_ACTION_PENDING /*350*/:
                        job = BasicCommand.this.mHitiPPR_GetThumbData.getJob();
                        if (BasicCommand.this.HaveListener()) {
                            ((BasicCallBack) BasicCommand.this.mCallBack).onGetThumbnailFinished(job, true);
                        }
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_ERROR /*351*/:
                        BasicCommand.this.ShowError(Error.Error_Get_thumbnail_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_GET_THUMBNAILS_DATA_NO_THUMBNAIL /*397*/:
                        job = BasicCommand.this.mHitiPPR_GetThumbData.getJob();
                        if (BasicCommand.this.HaveListener()) {
                            ((BasicCallBack) BasicCommand.this.mCallBack).onGetThumbnailFinished(job, false);
                        }
                    default:
                }
            }
        }
    }

    protected BasicCommand(Context context, CallBack callBack) {
        this.mContext = null;
        this.mCallBack = null;
        this.mPrinterHandler = null;
        this.mHitiPPR_GetPrinterInfo = null;
        this.mHitiPPR_GetThumbData = null;
        this.mHitiPPR_GetThumbMeta = null;
        this.mHitiPPR_RecoveryPrinter = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.mPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_bThreadStop = false;
        this.mContext = context;
        this.mCallBack = callBack;
        this.mPrinterHandler = new PrintHandler();
    }

    private boolean HaveListener() {
        return this.mCallBack != null;
    }

    protected void startRun(boolean bRet) {
        this.m_bThreadStop = !bRet;
        if (HaveListener()) {
            this.mCallBack.onShowWaitingDialog(bRet);
        }
    }

    void ShowError(Error type, String message) {
        if (!this.m_bThreadStop) {
            Stop();
            if (HaveListener()) {
                this.mCallBack.onErrorResponse(type, message);
            }
        }
    }

    public void Stop() {
        this.m_bThreadStop = true;
        this.mPrinterHandler.SetStop(true);
        KillThread();
    }

    protected boolean KillThread() {
        if (this.mHitiPPR_GetPrinterInfo != null) {
            this.mHitiPPR_GetPrinterInfo.Stop();
            this.mHitiPPR_GetPrinterInfo = null;
        }
        if (this.mHitiPPR_GetThumbData != null) {
            this.mHitiPPR_GetThumbData.Stop();
            this.mHitiPPR_GetThumbData = null;
        }
        if (this.mHitiPPR_GetThumbMeta != null) {
            this.mHitiPPR_GetThumbMeta.Stop();
            this.mHitiPPR_GetThumbMeta = null;
        }
        return true;
    }

    public void getPrinterInfo(Job job, InfoType info) {
        startRun(true);
        checkIPaddress(job);
        this.mPrinterHandler.setInfoType(info);
        this.mHitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetPrinterInfo.SetRetry(false);
        this.mHitiPPR_GetPrinterInfo.start();
    }

    public void getPhotoIdList(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_GetThumbMeta = new HitiPPR_GetThumbMeta(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetThumbMeta.putJob(job);
        this.mHitiPPR_GetThumbMeta.SetSocket(job.getSocket());
        this.mHitiPPR_GetThumbMeta.start();
    }

    public void getThumbnail(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_GetThumbData = new HitiPPR_GetThumbData(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetThumbData.putJob(job);
        this.mHitiPPR_GetThumbData.SetSocket(job.getSocket());
        this.mHitiPPR_GetThumbData.start();
    }

    public void recovery(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_RecoveryPrinter = new HitiPPR_RecoveryPrinter(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_RecoveryPrinter.putJob(job);
        this.mHitiPPR_RecoveryPrinter.start();
    }

    protected void checkIPaddress(Job job) {
        if (!(job == null || job.IP == null)) {
            this.IP = job.IP;
        }
        if (job != null && job.port != 0) {
            this.mPort = job.port;
        }
    }

    protected boolean checkSSIDandPassword(Job job) {
        if (job == null || job.SSID == null || job.Password == null) {
            return false;
        }
        return true;
    }

    public static byte GetSarpenValue(int iSharpen) {
        switch (iSharpen) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return Byte.MIN_VALUE;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return (byte) -120;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return (byte) -112;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return (byte) -104;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return (byte) -96;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return (byte) -88;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return (byte) -80;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return (byte) -72;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return (byte) -64;
            default:
                return (byte) -120;
        }
    }
}
