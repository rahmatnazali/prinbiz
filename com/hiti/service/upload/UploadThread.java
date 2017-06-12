package com.hiti.service.upload;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Pair;
import com.hiti.AppInfo.AppInfo;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.jni.hello.Hello;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.utility.BurnFWUtility;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.sql.oadc.OADCItemUtility;
import com.hiti.sql.offlineaddownloadinfo.OfflineADDownloadItem;
import com.hiti.sql.offlineaddownloadinfo.OfflineADDownloadItemUtility;
import com.hiti.sql.offlineadinfo.parser.OfflineADInfo;
import com.hiti.sql.offlineadinfo.parser.OfflineADParser;
import com.hiti.sql.printerInfo.PrintingInfo;
import com.hiti.sql.printerInfo.PrintingInfoUtility;
import com.hiti.trace.GlobalVariable_AppInfo;
import com.hiti.trace.GlobalVariable_OfflineADDownloadInfo;
import com.hiti.trace.GlobalVariable_OtherSetting;
import com.hiti.trace.GlobalVariable_SDFWInfo;
import com.hiti.trace.GlobalVariable_TotalPrintedRecord;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import com.hiti.utility.ZipUtility;
import com.hiti.utility.time.SchedualCheck;
import com.hiti.web.HitiWebPath;
import com.hiti.web.WebPostRequest;
import com.hiti.web.WebRequestNew;
import com.hiti.web.download.WEB_DOWNLOAD_ERROR;
import com.hiti.web.download.WebDownloadFTP;
import com.hiti.web.download.WebDownloadQuick;
import com.hiti.web.download.WebDownloadUtility;
import com.hiti.webhiti.HitiWebValue_GetUpdateInfo;
import com.hiti.webhiti.HitiWeb_GetUpdateInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.net.pop3.POP3;
import org.xmlpull.v1.XmlPullParser;

public abstract class UploadThread extends Thread {
    public final int DEFAULT_TIME_PERIOD;
    private LogManager LOG;
    private Context m_Context;
    private GlobalVariable_OtherSetting m_GVOtherSetting;
    private GlobalVariable_UploadInfo m_GVUploadInfo;
    private GlobalVariable_UserInfo m_GVUserInfo;
    private OADCItemUtility m_OADCIUtility;
    private OfflineADDownloadItemUtility m_OADDIUtility;
    private PrintingInfoUtility m_PrintingInfoUtility;
    private ServiceHandler m_ServiceHandler;
    private int m_TimePeriod;
    private String m_XMLFilePath;
    private boolean m_boStop;
    private int m_iAppMode;
    private String m_strXMLVersion;

    public UploadThread(ServiceHandler handler, Context context) {
        this.DEFAULT_TIME_PERIOD = 30000;
        this.m_Context = null;
        this.m_ServiceHandler = null;
        this.m_boStop = false;
        this.m_PrintingInfoUtility = null;
        this.m_OADDIUtility = null;
        this.m_OADCIUtility = null;
        this.m_GVUploadInfo = null;
        this.m_GVUserInfo = null;
        this.m_GVOtherSetting = null;
        this.LOG = null;
        this.m_TimePeriod = 30000;
        this.m_iAppMode = 2;
        this.m_XMLFilePath = XmlPullParser.NO_NAMESPACE;
        this.m_strXMLVersion = "2";
        this.m_Context = context;
        this.m_ServiceHandler = handler;
        this.m_PrintingInfoUtility = new PrintingInfoUtility(this.m_Context);
        this.m_OADDIUtility = new OfflineADDownloadItemUtility(this.m_Context);
        this.m_OADCIUtility = new OADCItemUtility(this.m_Context);
        this.m_GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        this.m_GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
        this.m_GVOtherSetting = new GlobalVariable_OtherSetting(this.m_Context);
        this.LOG = new LogManager(0);
    }

    public void SetStop(boolean boStop) {
        this.m_boStop = boStop;
    }

    public void SetTimePeriod(int iPeriod) {
        if (iPeriod == 0) {
            this.m_TimePeriod = 30000;
        } else {
            this.m_TimePeriod = iPeriod;
        }
    }

    public void run() {
        this.LOG.m384e("UploadThread live time:", new Date().toString());
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(this.m_Context);
        GVAppInfo.RestoreGlobalVariable();
        this.m_iAppMode = GVAppInfo.GetAppMode();
        this.LOG.m385i("AppMode", String.valueOf(AppInfo.GetAppModeFromNumber(this.m_iAppMode)));
        if (AppInfo.GetAppModeFromNumber(this.m_iAppMode) == APP_MODE.PRINBIZ) {
            Download_FW_Process_Prinbiz();
            Upload_Process_Prinbiz();
        } else if (AppInfo.GetAppModeFromNumber(this.m_iAppMode) == APP_MODE.PRINHOME) {
            Download_FW_Process(10);
            Upload_Process_P231();
            Upload_OADC_Process_P231();
            Download_Offline_AD_Info_Process_P231();
        } else if (AppInfo.GetAppModeFromNumber(this.m_iAppMode) == APP_MODE.PRINGO) {
            Download_FW_Process();
            Upload_Process_P231();
            Upload_OADC_Process_P231();
            Download_Offline_AD_Info_Process_P231();
        }
        this.m_ServiceHandler.SendMessage(100, null);
        try {
            if (this.m_PrintingInfoUtility != null) {
                this.m_PrintingInfoUtility.Close();
            }
            if (this.m_OADDIUtility != null) {
                this.m_OADDIUtility.Close();
            }
            if (this.m_OADCIUtility != null) {
                this.m_OADCIUtility.Close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.LOG.m384e("UploadThread dead time:", new Date().toString());
    }

    private void Download_Offline_AD_Info_Process_P231() {
        this.LOG.m385i("Download_Offline_AD_Info_Process_P231", "Stop Load ad due to wifi");
        Location location = MobileInfo.GetLocation(this.m_Context, false);
        String strLatitude = null;
        String strLongitude = null;
        if (location != null) {
            strLatitude = String.valueOf(location.getLatitude());
            strLongitude = String.valueOf(location.getLongitude());
        }
        int iTime = 3;
        while (!this.m_boStop) {
            try {
                this.m_GVOtherSetting.RestoreGlobalVariable();
                if (!UploadUtility.HaveWifiLoadADMethod(this.m_Context, this.m_GVOtherSetting)) {
                    if (!FileUtility.SDCardState()) {
                        this.LOG.m385i("Download_Offline_AD_Info_Process_P231", "Stop SDCardState");
                        break;
                    }
                    this.m_GVUploadInfo.RestoreGlobalVariable();
                    if (!GetOfflineADInfoXML(this.m_Context, strLatitude, strLongitude, GetPANumber(this.m_GVUploadInfo)) || !GetOfflineADInfo(this.m_Context)) {
                        this.LOG.m385i("Download_Offline_AD fail", "Time:" + iTime);
                        iTime--;
                        if (iTime == 0) {
                            break;
                        }
                        sleep((long) this.m_TimePeriod);
                        this.LOG.m385i("UploadThread live time:", new Date().toString());
                    } else {
                        break;
                    }
                }
                this.LOG.m385i("Download_Offline_AD_Info_Process_P231", "Stop Load ad due to wifi");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.m_OADDIUtility != null) {
            this.m_OADDIUtility.Close();
        }
    }

    private void Upload_OADC_Process_P231() {
        while (!this.m_boStop) {
            try {
                if (FileUtility.SDCardState()) {
                    if (this.m_OADCIUtility.GetOADCSize() > 0) {
                        if (!UploadOADC(this.m_Context, this.m_OADCIUtility, this.m_iAppMode)) {
                            break;
                        }
                        sleep((long) this.m_TimePeriod);
                        this.LOG.m384e("UploadThread live time:", new Date().toString());
                    } else {
                        this.LOG.m384e("Upload_OADC_Process_P231", "Stop GetOADCSize");
                        break;
                    }
                }
                this.LOG.m384e("Upload_OADC_Process_P231", "Stop SDCardState");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.m_OADCIUtility != null) {
            this.m_OADCIUtility.Close();
        }
    }

    private void Upload_Process_P231() {
        Location location = MobileInfo.GetLocation(this.m_Context, false);
        String strLatitude = null;
        String strLongitude = null;
        if (location != null) {
            strLatitude = String.valueOf(location.getLatitude());
            strLongitude = String.valueOf(location.getLongitude());
        }
        while (!this.m_boStop) {
            try {
                if (AppInfo.GetAppModeFromNumber(this.m_iAppMode) == APP_MODE.PRINHOME && HavePrintedRecord(WirelessType.TYPE_P461)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P461, strLatitude, strLongitude);
                }
                this.m_GVUploadInfo.RestoreGlobalVariable();
                if (UploadUtility.HaveUploadE03(this.m_GVUploadInfo)) {
                    this.LOG.m384e("Upload_Process_P231", "Stop e03");
                    break;
                }
                if (!UploadUtility.HaveUploadCount(this.m_PrintingInfoUtility)) {
                    this.LOG.m384e("Upload_Process_P231", "No Count");
                    this.m_GVUserInfo.RestoreGlobalVariable();
                    if (!UploadUtility.HaveVerify(this.m_GVUserInfo)) {
                        this.LOG.m384e("Upload_Process_P231", "Stop No verify");
                        break;
                    }
                }
                UploadCount(this.m_Context, this.m_PrintingInfoUtility, this.m_iAppMode, strLatitude, strLongitude);
                this.m_GVUserInfo.RestoreGlobalVariable();
                if (!UploadUtility.HaveVerify(this.m_GVUserInfo)) {
                    this.LOG.m384e("Upload_Process_P231", "Next No verify");
                    ClearUploadPhoto(this.m_Context, this.m_GVUploadInfo);
                    sleep((long) this.m_TimePeriod);
                } else if (!UploadUtility.HaveCanUpload(this.m_GVUploadInfo)) {
                    ClearUploadPhoto(this.m_Context, this.m_GVUploadInfo);
                    this.LOG.m384e("Upload_Process_P231", "Stop No Can Upload");
                    break;
                } else if (!FileUtility.SDCardState()) {
                    this.LOG.m384e("Upload_Process_P231", "Stop SDCardState");
                    break;
                } else if (!UploadUtility.HaveUploadPhoto(this.m_GVUploadInfo)) {
                    this.LOG.m384e("Upload_Process_P231", "Stop No Photo");
                    break;
                } else if (UploadUtility.HaveWifiUploadMethod(this.m_Context, this.m_GVUploadInfo)) {
                    this.LOG.m384e("Upload_Process_P231", "No WIFI");
                    break;
                } else {
                    Pair<String, String> pair = UserInfo.GetUP(this.m_Context, this.m_GVUploadInfo.GetUploader());
                    if (pair == null) {
                        this.LOG.m384e("Upload_Process_P231", "Stop No UP");
                        break;
                    }
                    UploadPhoto(this.m_Context, this.m_GVUploadInfo, (String) pair.first, (String) pair.second);
                    sleep((long) this.m_TimePeriod);
                    this.LOG.m384e("UploadThread live time:", new Date().toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.m_PrintingInfoUtility != null) {
            this.m_PrintingInfoUtility.Close();
        }
    }

    private void Upload_Process_Prinbiz() {
        Location location = MobileInfo.GetLocation(this.m_Context, false);
        String strLatitude = XmlPullParser.NO_NAMESPACE;
        String strLongitude = XmlPullParser.NO_NAMESPACE;
        if (location != null) {
            strLatitude = String.valueOf(location.getLatitude());
            strLongitude = String.valueOf(location.getLongitude());
        }
        while (!this.m_boStop) {
            try {
                if (HavePrintedRecord(WirelessType.TYPE_P461)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P461, strLatitude, strLongitude);
                }
                if (HavePrintedRecord(WirelessType.TYPE_P310W)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P310W, strLatitude, strLongitude);
                }
                if (HavePrintedRecord(WirelessType.TYPE_P520L)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P520L, strLatitude, strLongitude);
                }
                if (HavePrintedRecord(WirelessType.TYPE_P750L)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P750L, strLatitude, strLongitude);
                }
                if (HavePrintedRecord(WirelessType.TYPE_P525L)) {
                    UploadTotalPrintedRecord(WirelessType.TYPE_P525L, strLatitude, strLongitude);
                }
                if (!UploadUtility.HaveUploadCount(this.m_PrintingInfoUtility)) {
                    this.LOG.m384e("Service Kill -HaveUploadCount!!!!!", "No Upload Count");
                    break;
                }
                UploadCount(this.m_Context, this.m_PrintingInfoUtility, this.m_iAppMode, strLatitude, strLongitude);
                sleep((long) this.m_TimePeriod);
                this.LOG.m384e("UploadThread live time:", new Date().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.m_PrintingInfoUtility != null) {
            this.m_PrintingInfoUtility.Close();
        }
    }

    private boolean HavePrintedRecord(String strProductID) {
        GlobalVariable_TotalPrintedRecord m_GVTotalPrintedRecord = new GlobalVariable_TotalPrintedRecord(this.m_Context, strProductID);
        m_GVTotalPrintedRecord.RestoreGlobalVariable();
        if (m_GVTotalPrintedRecord.IsEmpty()) {
            return false;
        }
        return true;
    }

    private boolean UploadTotalPrintedRecord(String strProductID, String strLatitude, String strLongitude) {
        String strSerialNumber = XmlPullParser.NO_NAMESPACE;
        HashMap<String, Integer> strPrintOutList = new HashMap();
        GlobalVariable_TotalPrintedRecord m_GVTotalPrintedRecord = new GlobalVariable_TotalPrintedRecord(this.m_Context, strProductID);
        m_GVTotalPrintedRecord.RestoreGlobalVariable();
        int iElementId = GetElementID(strProductID);
        this.LOG.m386v("GetUploadPrinterInfo", "iElementId=" + iElementId);
        if (iElementId == -1) {
            return false;
        }
        strSerialNumber = m_GVTotalPrintedRecord.GetSerialNumber();
        strPrintOutList = m_GVTotalPrintedRecord.GetPrintFrameList();
        this.LOG.m386v("UploadTotalPrintedRecord", "strPrintOutList=" + strPrintOutList.toString());
        if (strSerialNumber.isEmpty() || strPrintOutList.isEmpty()) {
            return false;
        }
        this.LOG.m386v("before SendPrinterInfoXML", "ID: " + iElementId);
        boolean response = SendPrinterInfoXML(iElementId, strSerialNumber, strPrintOutList, strLatitude, strLongitude);
        this.LOG.m386v("After SendPrinterInfoXML", "response: " + response);
        if (response) {
            m_GVTotalPrintedRecord.ClearGlobalVariable();
        }
        return true;
    }

    private boolean SendPrinterInfoXML(int iElementId, String strSerialNumber, HashMap<String, Integer> strPrintOutList, String strLatitude, String strLongitude) {
        String response = new HitiWeb_GetUpdateInfo(this.m_Context).PrinterInfoService(String.valueOf(iElementId), strSerialNumber, strPrintOutList, strLatitude, strLongitude);
        this.LOG.m385i("SendPrinterInfoXML", String.valueOf(response));
        if (response == null || response.contains("ERROR")) {
            return false;
        }
        return true;
    }

    private int GetElementID(String strProductID) {
        this.LOG.m384e("GetElementID", "strProductID=" + strProductID);
        if (strProductID.isEmpty()) {
            return -1;
        }
        if (strProductID.equals(WirelessType.TYPE_P231)) {
            return 4;
        }
        if (strProductID.equals(WirelessType.TYPE_P232)) {
            return 5;
        }
        if (strProductID.equals(WirelessType.TYPE_P310W)) {
            return 9;
        }
        if (strProductID.equals(WirelessType.TYPE_P520L)) {
            return 7;
        }
        if (strProductID.equals(WirelessType.TYPE_P525L)) {
            return 18;
        }
        if (strProductID.equals(WirelessType.TYPE_P750L)) {
            return 8;
        }
        if (strProductID.equals(WirelessType.TYPE_P461)) {
            return 10;
        }
        if (strProductID.equals(WirelessType.TYPE_P530D)) {
            return 11;
        }
        return -1;
    }

    private void Download_FW_Process() {
        Download_FW_Process(4);
        Download_FW_Process(5);
    }

    private void Download_FW_Process_Prinbiz() {
        Download_FW_Process(7);
        Download_FW_Process(8);
        Download_FW_Process(9);
        Download_FW_Process(11);
    }

    public void SetFWXMLVersion(String strXMLVersion) {
        this.m_strXMLVersion = strXMLVersion;
    }

    private void Download_FW_Process(int ElementID) {
        this.LOG.m386v("Download_FW_Process", "ElementID=" + ElementID);
        if (!this.m_boStop) {
            this.m_GVUploadInfo.RestoreGlobalVariable();
            HitiWebValue_GetUpdateInfo gui = GetFWXML(this.m_Context, this.m_strXMLVersion, 2, ElementID, GetReleaseFlag(this.m_GVUploadInfo));
            if (gui != null) {
                int iTime = 5;
                while (!this.m_boStop) {
                    WEB_DOWNLOAD_ERROR webError = DownloadFW(this.m_Context, gui, ElementID);
                    this.LOG.m385i("FW-DOWNLOAD_" + iTime, String.valueOf(webError));
                    iTime--;
                    if (iTime != 0) {
                        try {
                            if (webError.equals(WEB_DOWNLOAD_ERROR.ERROR_OPEN_CONNECTION)) {
                                sleep(3000);
                            } else {
                                return;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private HitiWebValue_GetUpdateInfo GetFWXML(Context context, String Version, int UpdateID, int ElementID, int iReleaseFlag) {
        this.LOG.m386v("GetFWXML", "Version=" + Version);
        HitiWeb_GetUpdateInfo hw_GUI = new HitiWeb_GetUpdateInfo(context);
        String strSOAP = hw_GUI.Service(Version, String.valueOf(UpdateID), String.valueOf(ElementID), String.valueOf(iReleaseFlag));
        if (strSOAP == null) {
            this.LOG.m384e("strSOAP", "FAIL");
            return null;
        }
        HitiWebValue_GetUpdateInfo gui = hw_GUI.Parse(hw_GUI.RemoveSOAPFormat(strSOAP));
        this.LOG.m384e("ui.Response", gui.Response);
        this.LOG.m384e("ui.Version", gui.Version);
        this.LOG.m384e("ui.UpdateID", gui.UpdateID);
        this.LOG.m384e("ui.ElementID", gui.ElementID);
        this.LOG.m384e("ui.TimeStamp", gui.TimeStamp);
        this.LOG.m384e("ui.Signature", gui.Signature);
        this.LOG.m384e("ui.NewsetVersion", gui.NewsetVersion);
        this.LOG.m384e("ui.Info", gui.Info);
        this.LOG.m384e("ui.FTP", gui.FTP);
        this.LOG.m384e("ui.Path", gui.Path);
        this.LOG.m384e("ui.UserName", gui.UserName);
        this.LOG.m384e("ui.Password", gui.Password);
        if (!HitiWebValue_GetUpdateInfo.IsValid(gui)) {
            return null;
        }
        if (gui.NewsetVersion == XmlPullParser.NO_NAMESPACE) {
            return null;
        }
        if (AppInfo.GetAppModeFromNumber(this.m_iAppMode) == APP_MODE.FOR_WECHAT) {
            Message msg = new Message();
            msg.what = POP3.DEFAULT_PORT;
            Bundle bundle = new Bundle();
            bundle.putString("ElemntID", gui.ElementID);
            bundle.putString("FWversion", gui.NewsetVersion);
            bundle.putString("XMLversion", Version);
            msg.setData(bundle);
            if (this.m_ServiceHandler == null) {
                return gui;
            }
            this.m_ServiceHandler.sendMessage(msg);
            return gui;
        }
        String strCurrentFWVersion = BurnFWUtility.GetTheNewestFWVersion(context, ElementID, true).second;
        String strGetVersion = BurnFWUtility.RemoveFWFormat(gui.NewsetVersion);
        this.LOG.m384e("strCurrentFWVersion", strCurrentFWVersion);
        this.LOG.m384e("strGetVersion", strGetVersion);
        if (Integer.parseInt(strGetVersion) > Integer.parseInt(strCurrentFWVersion)) {
            return gui;
        }
        this.LOG.m384e("GetFWXML", "No Need Download FW");
        return null;
    }

    private WEB_DOWNLOAD_ERROR DownloadFW(Context context, HitiWebValue_GetUpdateInfo gui, int ElementID) {
        this.LOG.m385i("DownloadFW", "ElementID: " + ElementID);
        FileUtility.CreateFolder(FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_FIRMWARE);
        String strSDFWName = PringoConvenientConst.FIRMWARE_NAME;
        if (ElementID == 5) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P232;
        }
        if (ElementID == 7) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P520L;
        }
        if (ElementID == 8) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P750L;
        }
        if (ElementID == 9) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P310W;
        }
        if (ElementID == 10) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P461;
        }
        if (ElementID == 11) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P530D;
        }
        if (gui.Path.length() <= 0) {
            return WEB_DOWNLOAD_ERROR.ERROR_PATH;
        }
        String strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_FIRMWARE + "/" + MobileInfo.GetDateStamp() + (this.m_strXMLVersion.equals("2") ? PringoConvenientConst.PRINGO_SHOP_DOWNLOAD_FILE_EXT : ".bin");
        WEB_DOWNLOAD_ERROR web_error = WebDownloadFTP.FTPDownload(gui.FTP, HitiWebPath.FTP_OFFLINE_DOWNLOAD_AD_PORT, gui.UserName, EncryptAndDecryptAES.MakeMD5(gui.Password + Hello.SayHello(this.m_Context, 1217)), gui.Path, strSDFWPath);
        if (!WebDownloadUtility.IsSuccess(web_error) || this.m_boStop) {
            FileUtility.DeleteFile(strSDFWPath);
            return web_error;
        }
        String strFWFolderPath = FileUtility.GetSDAppRootPath(context) + File.separator + PringoConvenientConst.ROOT_FIRMWARE;
        FileUtility.DeleteFile(strFWFolderPath + File.separator + strSDFWName);
        if (gui.Path.contains(PringoConvenientConst.PRINGO_SHOP_DOWNLOAD_FILE_EXT)) {
            strSDFWName = FileUtility.ChangeFileExt(strSDFWName, PringoConvenientConst.PRINGO_SHOP_DOWNLOAD_FILE_EXT);
        } else {
            FileUtility.ReNameFile(strSDFWPath, strSDFWName);
        }
        if (strSDFWName.contains(PringoConvenientConst.PRINGO_SHOP_DOWNLOAD_FILE_EXT) && !UnzipFW(strFWFolderPath, strSDFWName, strSDFWPath)) {
            return WEB_DOWNLOAD_ERROR.ERROR_UNZIP_FAIL;
        }
        GlobalVariable_SDFWInfo GVSDFWInfo = new GlobalVariable_SDFWInfo(context);
        GVSDFWInfo.RestoreGlobalVariable();
        GVSDFWInfo.SetSDFWVersion(ElementID, gui.NewsetVersion);
        GVSDFWInfo.SaveGlobalVariableForever();
        return WEB_DOWNLOAD_ERROR.NON;
    }

    private boolean UnzipFW(String strFWFolderPath, String strSDFWName, String strZipFilePath) {
        strFWFolderPath = strFWFolderPath + File.separator + FileUtility.GetFileNameWithoutExt(strSDFWName);
        FileUtility.CreateFolder(strFWFolderPath);
        if (!FileUtility.FileExist(strFWFolderPath)) {
            return false;
        }
        String strUnzipPath = ZipUtility.UnpackZipForFW(strFWFolderPath, strZipFilePath);
        FileUtility.DeleteFile(strZipFilePath);
        if (strUnzipPath == null || strUnzipPath.length() == 0) {
            return false;
        }
        boolean bRet = FileUtility.ReFullPathFile(strUnzipPath, strUnzipPath.substring(0, strUnzipPath.lastIndexOf("/")) + ".bin");
        FileUtility.DeleteFile(strUnzipPath);
        FileUtility.DeleteALLFolder(strFWFolderPath);
        return bRet;
    }

    private int GetReleaseFlag(GlobalVariable_UploadInfo GVUploadInfo) {
        if (GetPANumber(GVUploadInfo) == 0) {
            return 0;
        }
        return 1;
    }

    private int GetPANumber(GlobalVariable_UploadInfo GVUploadInfo) {
        int iPANumber = 0;
        Pair<String, String> pair = UserInfo.GetUP(this.m_Context, GVUploadInfo.GetUploader());
        if (pair != null) {
            if (((String) pair.first).equals("pringoae1")) {
                iPANumber = 1;
            }
            if (((String) pair.first).equals("pringopatest")) {
                iPANumber = 2;
            }
        }
        this.LOG.m384e("iPANumber", String.valueOf(iPANumber));
        return iPANumber;
    }

    private boolean GetOfflineADInfo(Context context) {
        OfflineADDownloadItem oaddi = this.m_OADDIUtility.GetNextNotDownloadOADDI();
        if (oaddi.GetID() == -1) {
            this.LOG.m384e("GetOfflineADInfo", "No need download ad resource");
        }
        while (oaddi.GetID() != -1) {
            if (!DownLoadOfflineAD(context, oaddi, this.m_OADDIUtility)) {
                return false;
            }
            oaddi = this.m_OADDIUtility.GetNextNotDownloadOADDI();
        }
        return true;
    }

    private boolean GetOfflineADInfoXML(Context context, String strLatitude, String strLongitude, int iPANumber) {
        this.LOG.m385i("GetOfflineADInfoXML", "request: https://pringo.hiti.com/mobile/advertapi/askxmlversion");
        GlobalVariable_OfflineADDownloadInfo GVOfflineADDownloadInfo = new GlobalVariable_OfflineADDownloadInfo(context);
        GVOfflineADDownloadInfo.RestoreGlobalVariable();
        Map<String, String> paramlist = new HashMap();
        paramlist.put("info_id", GVOfflineADDownloadInfo.GetOADDI_Info_ID());
        paramlist.put("country", GVOfflineADDownloadInfo.GetOADDI_Country());
        paramlist.put("version", String.valueOf(1));
        if (!(strLatitude == null || strLongitude == null)) {
            paramlist.put("latitude", strLatitude);
            paramlist.put("longitude", strLongitude);
        }
        paramlist.put("pa", String.valueOf(iPANumber));
        WebPostRequest webPostRequest = new WebPostRequest();
        String strResponse = new WebRequestNew().PostByURLConnection(HitiWebPath.WEB_REQUEST_GET_OFFLINE_AD_INFO, paramlist);
        if (strResponse == null) {
            return false;
        }
        this.LOG.m384e("GetOfflineADInfo", "strResponse: " + strResponse);
        String retStr = webPostRequest.GetResponseCode(strResponse);
        this.LOG.m384e("GetOfflineADInfo ", "GetResponseCode: " + retStr);
        if (retStr == null) {
            return false;
        }
        if (!retStr.equals("s01")) {
            return false;
        }
        retStr = webPostRequest.GetResponseMSG(strResponse);
        this.LOG.m384e("GetOfflineADInfo", "GetResponseMSG: " + retStr);
        this.m_XMLFilePath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_OFFLINE_AD_DOWNLOAD_INFO_FOLDER + "/" + PringoConvenientConst.OFFLINE_AD_DOWNLOAD_INFO_NEW_XML;
        FileUtility.CreateFolder(FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_OFFLINE_AD_DOWNLOAD_INFO_FOLDER);
        this.LOG.m385i("GetOfflineADInfo " + FileUtility.FileExist(this.m_XMLFilePath), "m_XMLFilePath: " + this.m_XMLFilePath);
        if (retStr.length() > 0 && !WebDownloadUtility.IsSuccess(WebDownloadQuick.DownloadNotInBackground(retStr, this.m_XMLFilePath, 30000))) {
            return false;
        }
        if (FileUtility.FileExist(this.m_XMLFilePath)) {
            OfflineADInfo offlineADInfo = OfflineADParser.GetOfflineADInfo(context, this.m_XMLFilePath);
            if (offlineADInfo == null) {
                return false;
            }
            if (!this.m_OADDIUtility.ReflashDBByXML(offlineADInfo)) {
                return false;
            }
            this.m_OADDIUtility.PrintOADDI();
            GVOfflineADDownloadInfo.SetOADDI_Info_ID(offlineADInfo.GetInfoID());
            GVOfflineADDownloadInfo.SetOADDI_Version(offlineADInfo.GetVersion());
            GVOfflineADDownloadInfo.SetOADDI_Type(offlineADInfo.GetType());
            GVOfflineADDownloadInfo.SetOADDI_Number(offlineADInfo.GetNumber());
            GVOfflineADDownloadInfo.SetOADDI_Country(offlineADInfo.GetCountry());
            GVOfflineADDownloadInfo.SaveGlobalVariableForever();
            this.m_XMLFilePath = FileUtility.ReNameFile(this.m_XMLFilePath, PringoConvenientConst.OFFLINE_AD_DOWNLOAD_INFO_OLD_XML);
        }
        return true;
    }

    private boolean DownLoadOfflineAD(Context context, OfflineADDownloadItem oaddi, OfflineADDownloadItemUtility oaddiu) {
        this.LOG.m386v("DownLoadOfflineAD", "DownLoadOfflineAD");
        String strVedioFilePath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_OFFLINE_AD_DOWNLOAD_INFO_FOLDER + "/" + oaddi.GetAD_Video_Name();
        String strPhotoFilePath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_OFFLINE_AD_DOWNLOAD_INFO_FOLDER + "/" + oaddi.GetAD_Photo_Name();
        this.LOG.m386v("DownLoadOfflineAD", "GetAD_Ending_Time:" + String.valueOf(oaddi.GetAD_Ending_Time()));
        if (!SchedualCheck.IsDueTime(oaddi.GetAD_Ending_Time())) {
            String strDomainName;
            boolean boRet;
            if (!oaddi.GetAD_Video_Path().equals("NULL") && oaddi.GetAD_Video_File_Path().equals("NULL")) {
                this.LOG.m384e("oaddi.GetAD_Video_Path()", oaddi.GetAD_Video_Path());
                this.LOG.m384e("strVedioFilePath", strVedioFilePath);
                strDomainName = WebDownloadFTP.GetDomainName(oaddi.GetAD_Video_Path());
                if (strDomainName == null) {
                    boRet = WebDownloadUtility.IsSuccess(WebDownloadQuick.DownloadNotInBackground(oaddi.GetAD_Video_Path(), strVedioFilePath, 60000));
                } else {
                    boRet = WebDownloadUtility.IsSuccess(WebDownloadFTP.FTPDownload(strDomainName, HitiWebPath.FTP_OFFLINE_DOWNLOAD_AD_PORT, HitiWebPath.FTP_OFFLINE_DOWNLOAD_AD_USER, Hello.SayHello(this.m_Context, 1917), oaddi.GetAD_Video_Path(), strVedioFilePath));
                }
                this.LOG.m386v("FTPDownload Vedio", XmlPullParser.NO_NAMESPACE + boRet);
                this.LOG.m386v("Vedio exist", XmlPullParser.NO_NAMESPACE + new File(strVedioFilePath).exists());
                if (boRet) {
                    boRet = oaddiu.UpdateOADDI(oaddi.GetID(), null, null, null, null, null, null, strVedioFilePath, null, null, null, null, null, null, null, null, null);
                    this.LOG.m386v("UpdateOADDI Vedio", XmlPullParser.NO_NAMESPACE + boRet);
                    if (!boRet) {
                        FileUtility.DeleteFile(strVedioFilePath);
                        return false;
                    }
                }
                FileUtility.DeleteFile(strVedioFilePath);
                return false;
            }
            if (!oaddi.GetAD_Photo_Path().equals("NULL") && oaddi.GetAD_Photo_File_Path().equals("NULL")) {
                this.LOG.m384e("oaddi.GetAD_Photo_Path()", oaddi.GetAD_Photo_Path());
                this.LOG.m384e("strPhotoFilePath", strPhotoFilePath);
                strDomainName = WebDownloadFTP.GetDomainName(oaddi.GetAD_Photo_Path());
                if (strDomainName == null) {
                    boRet = WebDownloadUtility.IsSuccess(WebDownloadQuick.DownloadNotInBackground(oaddi.GetAD_Photo_Path(), strPhotoFilePath, 60000));
                } else {
                    boRet = WebDownloadUtility.IsSuccess(WebDownloadFTP.FTPDownload(strDomainName, HitiWebPath.FTP_OFFLINE_DOWNLOAD_AD_PORT, HitiWebPath.FTP_OFFLINE_DOWNLOAD_AD_USER, Hello.SayHello(this.m_Context, 1917), oaddi.GetAD_Photo_Path(), strPhotoFilePath));
                }
                this.LOG.m386v("FTPDownload photo", XmlPullParser.NO_NAMESPACE + boRet);
                if (boRet) {
                    boRet = oaddiu.UpdateOADDI(oaddi.GetID(), null, null, null, null, null, null, null, null, null, null, null, strPhotoFilePath, null, null, null, null);
                    this.LOG.m386v("UpdateOADDI photo", XmlPullParser.NO_NAMESPACE + boRet);
                    if (!boRet) {
                        FileUtility.DeleteFile(strPhotoFilePath);
                        return false;
                    }
                }
                FileUtility.DeleteFile(strPhotoFilePath);
                return false;
            }
        }
        return oaddiu.UpdateOADDI(oaddi.GetID(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, String.valueOf(1));
    }

    private void UploadCount(Context context, PrintingInfoUtility piu, int iAppMode, String strLatitude, String strLongitude) {
        this.LOG.m385i("UploadCount", "UploadCount");
        if (!this.m_boStop) {
            Pair<ArrayList<Integer>, String> pair = GetUploadCount(context, piu);
            ArrayList<Integer> IDArray = pair.first;
            String strUploadCount = pair.second;
            if (strUploadCount.length() != 0 && IDArray.size() != 0) {
                Map<String, String> paramlist = new HashMap();
                paramlist.put("data2", strUploadCount);
                paramlist.put("job_number", String.valueOf(IDArray.size()));
                paramlist.put("app_type", String.valueOf(iAppMode));
                paramlist.put("platform", String.valueOf(2));
                paramlist.put("imei", MobileInfo.GetIMEI(context));
                if (!(strLatitude == null || strLongitude == null)) {
                    paramlist.put("latitude", strLatitude);
                    paramlist.put("longitude", strLongitude);
                }
                WebPostRequest webPostRequest = new WebPostRequest();
                String strResponse = new WebRequestNew().PostByURLConnection(HitiWebPath.WEB_REQUEST_PRINTER_INFO, paramlist);
                String retStr = webPostRequest.GetResponseCode(strResponse);
                if (retStr == null) {
                    return;
                }
                if (retStr.equals("s01") || retStr.equals("e03")) {
                    int iDeleteNumber = 0;
                    if (retStr.equals("s01")) {
                        iDeleteNumber = IDArray.size();
                    } else if (retStr.equals("e03")) {
                        try {
                            String strMSG = webPostRequest.GetResponseMSG(strResponse);
                            this.LOG.m384e("strMSG", XmlPullParser.NO_NAMESPACE + strMSG);
                            if (strMSG != null) {
                                iDeleteNumber = Integer.valueOf(strMSG).intValue() + 1;
                            } else {
                                return;
                            }
                        } catch (NumberFormatException e) {
                            return;
                        }
                    }
                    paramlist.clear();
                    if (iDeleteNumber <= IDArray.size()) {
                        for (int i = 0; i < iDeleteNumber; i++) {
                            piu.DeleteContactByID((long) ((Integer) IDArray.get(i)).intValue());
                        }
                        IDArray.clear();
                        strUploadCount = XmlPullParser.NO_NAMESPACE;
                        this.LOG.m384e("Success", new Date().toString());
                    }
                }
            }
        }
    }

    private void UploadPhoto(Context context, GlobalVariable_UploadInfo GVUploadInfo, String strU, String strP) {
        String strUploadPath = GVUploadInfo.GetUploadPath(0);
        String strUploadTime = GVUploadInfo.GetUploadTime(0);
        this.LOG.m384e("Get strUploadPath", strUploadPath);
        this.LOG.m384e("Get strUploadTime", strUploadTime);
        if (FileUtility.FileExist(strUploadPath)) {
            this.LOG.m384e("Service Work -PHOTO!!!!!", strUploadPath);
            String value = EncryptAndDecryptAES.EncryptStr(strU + PringoConvenientConst.DATE_TO_DATE_2 + strP + PringoConvenientConst.DATE_TO_DATE_2 + strUploadTime + PringoConvenientConst.DATE_TO_DATE_2 + MobileInfo.GetIMEI(context) + PringoConvenientConst.DATE_TO_DATE_2, Hello.SayGoodBye(context, 1289), Hello.SayHello(context, 1289));
            Map<String, String> paramlist = new HashMap();
            paramlist.clear();
            paramlist.put("option1", value);
            paramlist.put("app_type", String.valueOf(this.m_iAppMode));
            WebPostRequest webPostRequest = new WebPostRequest();
            WebRequestNew mUrlConnection = new WebRequestNew();
            mUrlConnection.SetImageData(context, paramlist, strUploadPath);
            String retStr = webPostRequest.GetResponseCode(mUrlConnection.UploadPhotoURLConnection(HitiWebPath.WEB_REQUEST_UPLOAD, paramlist));
            this.LOG.m384e("Service Work -PHOTO response", "retStr: " + retStr);
            if (retStr != null) {
                if (retStr.equals("s01")) {
                    paramlist.clear();
                    this.LOG.m384e("Get s01", new Date().toString());
                    GVUploadInfo.RemoveUploadFile(0);
                    GVUploadInfo.SaveGlobalVariableForever();
                    FileUtility.DeleteFile(strUploadPath);
                } else if (retStr.equals("s02")) {
                    paramlist.clear();
                    this.LOG.m384e("Get s02", new Date().toString());
                    GVUploadInfo.RemoveUploadFile(0);
                    GVUploadInfo.SaveGlobalVariableForever();
                    FileUtility.DeleteFile(strUploadPath);
                } else if (retStr.equals("e10")) {
                    paramlist.clear();
                    this.LOG.m384e("Get e10", new Date().toString());
                    GVUploadInfo.RemoveUploadFile(0);
                    GVUploadInfo.SaveGlobalVariableForever();
                    FileUtility.DeleteFile(strUploadPath);
                } else if (retStr.equals("e03")) {
                    this.LOG.m384e("Get e03", new Date().toString());
                    GVUploadInfo.SetUploadE03(1);
                    GVUploadInfo.SaveGlobalVariableForever();
                }
                this.LOG.m384e("Retry", new Date().toString());
                return;
            }
            return;
        }
        this.LOG.m384e("Service Work -PHOTO ", "Not exist!?");
        GVUploadInfo.RemoveUploadFile(0);
        GVUploadInfo.SaveGlobalVariableForever();
    }

    private Pair<ArrayList<Integer>, String> GetUploadCount(Context context, PrintingInfoUtility piu) {
        String strUploadCount = XmlPullParser.NO_NAMESPACE;
        ArrayList<Integer> IDArray = new ArrayList();
        int i = 0;
        Cursor cursor = piu.GetPrintingInfoCursor();
        if (cursor.moveToFirst()) {
            while (i < 5) {
                Pair<String, String> pair;
                PrintingInfo pi = piu.GetPrintingInfoFromCursor(cursor);
                this.LOG.m385i("Get pi", "ID: " + String.valueOf(pi.GetID()));
                this.LOG.m385i("Get pi", "GetUpLoader: " + String.valueOf(pi.GetUpLoader()));
                if (pi.GetUpLoader().equals("###")) {
                    pair = UserInfo.GetPublicPrintUP(this.m_Context);
                } else {
                    pair = UserInfo.GetUP(context, pi.GetUpLoader());
                }
                String strU = "NULL";
                String strP = "NULL";
                if (pair == null) {
                    pair = UserInfo.GetPublicPrintUP(this.m_Context);
                }
                if (pair != null) {
                    strU = pair.first;
                    strP = pair.second;
                }
                this.LOG.m385i("Get strU", String.valueOf(strU));
                this.LOG.m385i("GetTotalFrame", String.valueOf(pi.GetTotalFrame()));
                String strCopy = EncryptAndDecryptAES.OpenAESCount(context, pi.GetCopys(), pi.GetPrintingTime());
                if (strCopy.length() == 0) {
                    strCopy = "NULL";
                }
                String strRealCount = EncryptAndDecryptAES.OpenAESCount(context, pi.GetRealCount(), pi.GetPrintingTime());
                if (strRealCount.length() == 0) {
                    strRealCount = "NULL";
                }
                strUploadCount = strUploadCount + pi.GetPrintingTime() + PringoConvenientConst.DATE_TO_DATE + pi.GetSerialNumber() + PringoConvenientConst.DATE_TO_DATE + strCopy + PringoConvenientConst.DATE_TO_DATE + strRealCount + PringoConvenientConst.DATE_TO_DATE + pi.GetType() + PringoConvenientConst.DATE_TO_DATE + pi.GetPaperSize() + PringoConvenientConst.DATE_TO_DATE + pi.GetMaskColor() + PringoConvenientConst.DATE_TO_DATE + strU + PringoConvenientConst.DATE_TO_DATE + strP + PringoConvenientConst.DATE_TO_DATE + pi.GetFlashCard() + PringoConvenientConst.DATE_TO_DATE + pi.GetTotalFrame() + PringoConvenientConst.DATE_TO_DATE + pi.GetSnapPrintFlag() + PringoConvenientConst.DATE_TO_DATE;
                IDArray.add(Integer.valueOf(pi.GetID()));
                i++;
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
        cursor.close();
        if (strUploadCount != null && strUploadCount.length() > 0) {
            strUploadCount = EncryptAndDecryptAES.MakeGoodString(EncryptAndDecryptAES.EncryptStr(strUploadCount, Hello.SayGoodBye(context, 1221), Hello.SayHello(context, 1221)));
        }
        if (strUploadCount == null) {
            strUploadCount = XmlPullParser.NO_NAMESPACE;
        }
        return new Pair(IDArray, strUploadCount);
    }

    private void ClearUploadPhoto(Context context, GlobalVariable_UploadInfo GVUploadInfo) {
        if (GVUploadInfo.GetUploadFileSize() > 0) {
            GVUploadInfo.ClearUploadFile();
            GVUploadInfo.SaveGlobalVariableForever();
            FileUtility.DeleteFolder(FileUtility.GetSDAppRootPath(context) + "/print");
        }
    }

    private Pair<ArrayList<Integer>, String> GetOADC(Context context, OADCItemUtility oadciu) {
        String strOADCs = XmlPullParser.NO_NAMESPACE;
        ArrayList<Integer> IDArray = new ArrayList();
        int i = 0;
        Cursor cursor = oadciu.GetOADCCursor();
        if (cursor.moveToFirst()) {
            while (i < 20) {
                OADCItem oadc = oadciu.GetOADCFromCursor(cursor);
                this.LOG.m384e("Get oadc", String.valueOf(oadc.GetID()));
                strOADCs = strOADCs + EncryptAndDecryptAES.DecryptStr(oadc.GetOADC(), Hello.SayGoodBye(context, 3331), Hello.SayHello(context, 3331));
                IDArray.add(Integer.valueOf(oadc.GetID()));
                i++;
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
        cursor.close();
        if (strOADCs != null && strOADCs.length() > 0) {
            strOADCs = EncryptAndDecryptAES.MakeGoodString(EncryptAndDecryptAES.EncryptStr(strOADCs, Hello.SayGoodBye(context, 3177), Hello.SayHello(context, 3177)));
        }
        if (strOADCs == null) {
            strOADCs = XmlPullParser.NO_NAMESPACE;
        }
        return new Pair(IDArray, strOADCs);
    }

    private boolean UploadOADC(Context context, OADCItemUtility oadciu, int iAppMode) {
        this.LOG.m384e("UploadOADC_iAppMode", AppInfo.GetAppModeFromNumber(iAppMode).toString());
        Pair<ArrayList<Integer>, String> pair = GetOADC(this.m_Context, this.m_OADCIUtility);
        ArrayList<Integer> IDArray = pair.first;
        String strOADC = pair.second;
        if (!(strOADC.length() == 0 || IDArray.size() == 0)) {
            this.LOG.m385i("Service Work -OADC!!!!!", strOADC);
            Map<String, String> paramlist = new HashMap();
            paramlist.put("data", strOADC);
            paramlist.put("job_number", String.valueOf(IDArray.size()));
            paramlist.put("app_type", String.valueOf(iAppMode));
            paramlist.put("platform", String.valueOf(2));
            paramlist.put("token", MobileInfo.MakeRandStringNoZeroPrefix(6));
            WebPostRequest webPostRequest = new WebPostRequest();
            String strResponse = new WebRequestNew().PostByURLConnection(HitiWebPath.WEB_REQUEST_OADC, paramlist);
            String retStr = webPostRequest.GetResponseCode(strResponse);
            if (retStr == null) {
                return false;
            }
            if (retStr.equals("s01") || retStr.equals("e03")) {
                int iDeleteNumber = 0;
                if (retStr.equals("s01")) {
                    iDeleteNumber = IDArray.size();
                } else if (retStr.equals("e03")) {
                    try {
                        String strMSG = webPostRequest.GetResponseMSG(strResponse);
                        if (strMSG == null) {
                            return false;
                        }
                        iDeleteNumber = Integer.valueOf(strMSG).intValue() + 1;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                paramlist.clear();
                if (iDeleteNumber > IDArray.size()) {
                    return false;
                }
                for (int i = 0; i < iDeleteNumber; i++) {
                    oadciu.DeleteOADCByID((long) ((Integer) IDArray.get(i)).intValue());
                }
                IDArray.clear();
                strOADC = XmlPullParser.NO_NAMESPACE;
                this.LOG.m384e("Success", new Date().toString());
                return true;
            }
        }
        return false;
    }
}
