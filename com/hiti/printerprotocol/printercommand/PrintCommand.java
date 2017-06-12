package com.hiti.printerprotocol.printercommand;

import android.content.Context;
import android.os.Message;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.printercommand.BasicCommand.BasicCallBack;
import com.hiti.printerprotocol.printercommand.BasicCommand.CallBack;
import com.hiti.printerprotocol.printercommand.BasicCommand.Error;
import com.hiti.printerprotocol.request.HitiPPR_CleanModeRun;
import com.hiti.printerprotocol.request.HitiPPR_GetAlbumData;
import com.hiti.printerprotocol.request.HitiPPR_GetAlbumMeta;
import com.hiti.printerprotocol.request.HitiPPR_GetAllSetting;
import com.hiti.printerprotocol.request.HitiPPR_SetAutoPowerOff;
import com.hiti.printerprotocol.request.HitiPPR_SetAutoPowerOff.AUTO_POWER_OFF;
import com.hiti.printerprotocol.request.HitiPPR_SetWifiModeSetting;
import com.hiti.printerprotocol.request.HitiPPR_SetWifiSetting;
import com.hiti.utility.ByteConvertUtility;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public class PrintCommand extends BasicCommand {
    HitiPPR_CleanModeRun mHitiPPR_CleanModeRun;
    HitiPPR_GetAlbumData mHitiPPR_GetAlbumData;
    HitiPPR_GetAlbumMeta mHitiPPR_GetAlbumMeta;
    HitiPPR_GetAllSetting mHitiPPR_GetAllSetting;
    HitiPPR_SetAutoPowerOff mHitiPPR_SetAutoPowerOff;
    HitiPPR_SetWifiModeSetting mHitiPPR_SetWifiModeSetting;
    HitiPPR_SetWifiSetting mHitiPPR_SetWifiSetting;
    ListCallBack mPrintCallBack;
    PrinterHandler mPrinterHandler;
    SettingCallback mSettingCallback;
    private ArrayList<byte[]> m_AlbumIDList;
    private ArrayList<byte[]> m_AlbumSIDList;

    public interface SettingCallback extends CallBack {
        void getSettingInfo(String str, String str2, int i);

        void setAutoPowerOffDone();

        void setSecurityInfoDone(String str, String str2);

        void setWifiModeDone(String str, String str2);
    }

    public interface ListCallBack extends BasicCallBack {
        void onGetAlbumIdListFinished(ArrayList<Job> arrayList);

        void onGetAlbumInfoFinished(Job job);
    }

    class PrinterHandler extends PrintHandler {
        PrinterHandler() {
            super();
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (PrintCommand.this.mCallBack != null) {
                PrintCommand.this.mCallBack.onShowWaitingDialog(false);
            }
            if (!PrintCommand.this.m_bThreadStop) {
                String SSID;
                String password;
                switch (msg.what) {
                    case RequestState.REQUEST_RECOVERY_PRINTER_ERROR /*306*/:
                        PrintCommand.this.ShowError(Error.Error_recovery_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_SET_WIFI_SETTING /*327*/:
                        SSID = PrintCommand.this.mHitiPPR_SetWifiSetting.GetSSID();
                        password = PrintCommand.this.mHitiPPR_SetWifiSetting.GetSSID();
                        if (PrintCommand.this.mSettingCallback != null) {
                            PrintCommand.this.mSettingCallback.setSecurityInfoDone(SSID, password);
                        }
                    case RequestState.REQUEST_SET_WIFI_SETTING_ERROR /*328*/:
                        PrintCommand.this.ShowError(Error.Error_Set_Security_info_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_SETTING_ITEM_ALL /*338*/:
                        String strPassword;
                        String strSSID = PrintCommand.this.mHitiPPR_GetAllSetting.GetAttrSSID();
                        if (PrintCommand.this.mHitiPPR_GetAllSetting.GetAttrSecurityKey() == null) {
                            strPassword = XmlPullParser.NO_NAMESPACE;
                        } else {
                            strPassword = PrintCommand.this.mHitiPPR_GetAllSetting.GetAttrSecurityKey();
                        }
                        int time = PrintCommand.this.mHitiPPR_GetAllSetting.GetAttrAutoPowerOffSeconds();
                        if (PrintCommand.this.mSettingCallback != null) {
                            PrintCommand.this.mSettingCallback.getSettingInfo(strSSID, strPassword, time);
                        }
                    case RequestState.REQUEST_SETTING_ITEM_ALL_ERROR /*339*/:
                        PrintCommand.this.ShowError(Error.Error_Get_Security_info_fail, msg.getData().getString(MSGHandler.MSG));
                    case NNTPReply.SEND_ARTICLE_TO_POST /*340*/:
                        if (PrintCommand.this.mSettingCallback != null) {
                            PrintCommand.this.mSettingCallback.setAutoPowerOffDone();
                        }
                    case RequestState.REQUEST_AUTO_POWER_OFF_ERROR /*341*/:
                        PrintCommand.this.ShowError(Error.Error_Set_auto_power_off_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_GET_ALBUM_ID_META /*342*/:
                        if (PrintCommand.this.m_AlbumIDList.isEmpty()) {
                            PrintCommand.this.m_AlbumIDList.clear();
                            PrintCommand.this.m_AlbumSIDList.clear();
                            if (PrintCommand.this.mHitiPPR_GetAlbumMeta != null) {
                                PrintCommand.this.mHitiPPR_GetAlbumMeta.GetAlbumID(PrintCommand.this.m_AlbumIDList, PrintCommand.this.m_AlbumSIDList);
                                Socket socket = PrintCommand.this.mHitiPPR_GetAlbumMeta.GetSocket();
                                byte[] mStorageID = (byte[]) PrintCommand.this.m_AlbumSIDList.get(0);
                                ArrayList<Job> jobList = new ArrayList();
                                Iterator it = PrintCommand.this.m_AlbumIDList.iterator();
                                while (it.hasNext()) {
                                    byte[] albumId = (byte[]) it.next();
                                    Job job = new Job(socket);
                                    int objectId = ByteConvertUtility.ByteToInt(albumId, 0, 4);
                                    int storageId = ByteConvertUtility.ByteToInt(mStorageID, 0, 4);
                                    job.setAlbumId(objectId);
                                    job.setStorageId(storageId);
                                    jobList.add(job);
                                }
                                if (PrintCommand.this.mPrintCallBack != null) {
                                    PrintCommand.this.mPrintCallBack.onGetAlbumIdListFinished(jobList);
                                }
                            }
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META_ERROR /*343*/:
                        PrintCommand.this.ShowError(Error.Error_Get_album_Id_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_GET_ALBUM_DATA /*346*/:
                        if (!PrintCommand.this.m_bThreadStop && PrintCommand.this.mPrintCallBack != null) {
                            PrintCommand.this.mPrintCallBack.onGetAlbumInfoFinished(PrintCommand.this.mHitiPPR_GetAlbumData.getJob());
                        }
                    case RequestState.REQUEST_GET_ALBUM_DATA_ERROR /*347*/:
                        PrintCommand.this.ShowError(Error.Error_Get_album_name_and_numbers_of_photo_fail, msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_CLEAN_MODE_RUN_ERROR /*391*/:
                        PrintCommand.this.ShowError(Error.Error_Clean_Mode_fail, XmlPullParser.NO_NAMESPACE);
                    case RequestState.REQUEST_CLEAN_MODE_RUN_ERROR_DUETO_PRINTER /*392*/:
                    case RequestState.REQUEST_SET_WIFI_MODE_SETTING /*398*/:
                        SSID = PrintCommand.this.mHitiPPR_SetWifiModeSetting.getSSID();
                        password = PrintCommand.this.mHitiPPR_SetWifiModeSetting.getPassword();
                        if (PrintCommand.this.mSettingCallback != null) {
                            PrintCommand.this.mSettingCallback.setWifiModeDone(SSID, password);
                        }
                    case RequestState.REQUEST_SET_WIFI_MODE_SETTING_ERROR /*399*/:
                        PrintCommand.this.ShowError(Error.Error_Set_wifi_mode_fail, msg.getData().getString(MSGHandler.MSG));
                    default:
                }
            }
        }
    }

    public PrintCommand(Context context, CallBack callBack) {
        super(context, callBack);
        this.mHitiPPR_GetAlbumMeta = null;
        this.mHitiPPR_GetAlbumData = null;
        this.mHitiPPR_CleanModeRun = null;
        this.mHitiPPR_GetAllSetting = null;
        this.mHitiPPR_SetWifiSetting = null;
        this.mHitiPPR_SetWifiModeSetting = null;
        this.mHitiPPR_SetAutoPowerOff = null;
        this.m_AlbumIDList = null;
        this.m_AlbumSIDList = null;
        this.mPrintCallBack = null;
        this.mPrinterHandler = null;
        this.mSettingCallback = null;
        this.mPrintCallBack = (ListCallBack) callBack;
        this.mPrinterHandler = new PrinterHandler();
        this.m_AlbumIDList = new ArrayList();
        this.m_AlbumSIDList = new ArrayList();
    }

    protected boolean KillThread() {
        if (this.mHitiPPR_GetAlbumData != null) {
            this.mHitiPPR_GetAlbumData.Stop();
            this.mHitiPPR_GetAlbumData = null;
        }
        if (this.mHitiPPR_GetAlbumMeta != null) {
            this.mHitiPPR_GetAlbumMeta.Stop();
            this.mHitiPPR_GetAlbumMeta = null;
        }
        return super.KillThread();
    }

    public void Stop() {
        this.m_bThreadStop = true;
        this.mPrinterHandler.SetStop(true);
        KillThread();
    }

    public void getAlbumIdList(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_GetAlbumMeta = new HitiPPR_GetAlbumMeta(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetAlbumMeta.start();
    }

    public void getAlbumInfo(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_GetAlbumData = new HitiPPR_GetAlbumData(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetAlbumData.putJob(job);
        this.mHitiPPR_GetAlbumData.SetSocket(job.getSocket());
        this.mHitiPPR_GetAlbumData.start();
    }

    private void runCleanMode(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_CleanModeRun = new HitiPPR_CleanModeRun(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_CleanModeRun.start();
    }

    public PrintCommand(Context context, SettingCallback callBack) {
        super(context, callBack);
        this.mHitiPPR_GetAlbumMeta = null;
        this.mHitiPPR_GetAlbumData = null;
        this.mHitiPPR_CleanModeRun = null;
        this.mHitiPPR_GetAllSetting = null;
        this.mHitiPPR_SetWifiSetting = null;
        this.mHitiPPR_SetWifiModeSetting = null;
        this.mHitiPPR_SetAutoPowerOff = null;
        this.m_AlbumIDList = null;
        this.m_AlbumSIDList = null;
        this.mPrintCallBack = null;
        this.mPrinterHandler = null;
        this.mSettingCallback = null;
        this.mSettingCallback = callBack;
        this.mPrinterHandler = new PrinterHandler();
    }

    public void getPrinterSetting(Job job) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_GetAllSetting = new HitiPPR_GetAllSetting(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_GetAllSetting.start();
    }

    public boolean setSecurityInfo(Job job) {
        if (!checkSSIDandPassword(job)) {
            return false;
        }
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_SetWifiSetting = new HitiPPR_SetWifiSetting(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_SetWifiSetting.SetSSID(job.SSID);
        this.mHitiPPR_SetWifiSetting.SetPassword(job.Password);
        this.mHitiPPR_SetWifiSetting.start();
        return true;
    }

    public boolean setWifiMode(Job job, int wifiMode) {
        if (!checkSSIDandPassword(job)) {
            return false;
        }
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_SetWifiModeSetting = new HitiPPR_SetWifiModeSetting(this.mContext, this.IP, this.mPort, wifiMode, this.mPrinterHandler);
        if (wifiMode == 1) {
            this.mHitiPPR_SetWifiModeSetting.SetSSID(job.SSID);
            this.mHitiPPR_SetWifiModeSetting.SetPassword(job.Password);
        }
        this.mHitiPPR_SetWifiModeSetting.start();
        return true;
    }

    public void setAutuPowerOffTime(Job job, AUTO_POWER_OFF timeItem) {
        startRun(true);
        checkIPaddress(job);
        this.mHitiPPR_SetAutoPowerOff = new HitiPPR_SetAutoPowerOff(this.mContext, this.IP, this.mPort, this.mPrinterHandler);
        this.mHitiPPR_SetAutoPowerOff.SetAutoPowerOffSeconds(timeItem);
        this.mHitiPPR_SetAutoPowerOff.start();
    }
}
