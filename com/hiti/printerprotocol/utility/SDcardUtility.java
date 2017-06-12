package com.hiti.printerprotocol.utility;

import android.content.Context;
import android.os.Message;
import com.hiti.jumpinfo.JumpPreferenceKey;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.request.HitiPPR_CheckPrinterTotalPrintedCount;
import com.hiti.printerprotocol.request.HitiPPR_GetPrinterInfo;
import com.hiti.printerprotocol.request.HitiPPR_PaperJamRun;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_QuickPrint;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.hiti.sql.printerInfo.PrintingInfo;
import com.hiti.sql.printerInfo.PrintingInfoUtility;
import com.hiti.trace.GlobalVariable_TotalPrintedRecord;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.net.smtp.SMTPReply;
import org.xmlpull.v1.XmlPullParser;

public class SDcardUtility {
    private String IP;
    LogManager LOG;
    public int R_COLOR_GS_COLOR;
    public int R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR;
    public int R_STRING_version;
    public int R_STRING_version_p310;
    public int R_STRING_version_p520;
    public int R_STRING_version_p750;
    String TAG;
    private HitiPPR_CheckPrinterTotalPrintedCount mCheckPrinterTotalPrintedCount;
    private SendPhotoInfo mPrintInfo;
    private Context m_Context;
    private GlobalVariable_TotalPrintedRecord m_GVTotalPrintedRecord;
    private GlobalVariable_UploadInfo m_GVUploadInfo;
    private HitiPPR_GetPrinterInfo m_HitiPPR_GetPrinterInfo;
    private HitiPPR_RecoveryPrinter m_HitiPPR_RecoveryPrinter;
    private HitiPPR_PaperJamRun m_PaperJamRun;
    private PrintMode m_PrintMode;
    private ISDcard m_PrintSDcardListener;
    private QuickPrintSDcardHandler m_QPrintSDcardHandler;
    private PrintingInfoUtility m_SQLiteUtility;
    private boolean m_bDuplex;
    private byte m_bSharpenValue;
    private int m_iLastCp;
    private ArrayList<Integer> m_iMultiSelPhotoIdList;
    private ArrayList<Integer> m_iMultiSelStorageIdList;
    private int m_iPaperType;
    private int m_iPathRoute;
    private ArrayList<Integer> m_iPhotoCopiesList;
    private int m_iPort;
    private int m_iSQLiteID;
    private JumpPreferenceKey m_pref;
    private String m_strModel;
    private String m_strProductIDString;
    private HitiPPR_QuickPrint printCommand;

    private class QuickPrintSDcardHandler extends MSGHandler {
        private QuickPrintSDcardHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                String strErr;
                String strMSG;
                int iPrintedCount;
                int iCpDone;
                switch (msg.what) {
                    case RequestState.REQUEST_RECOVERY_PRINTER /*305*/:
                        SDcardUtility.this.m_PrintSDcardListener.RecoveryDone();
                    case RequestState.REQUEST_RECOVERY_PRINTER_ERROR /*306*/:
                    case RequestState.REQUEST_PAPER_JAM_RUN_ERROR /*394*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.ErrorOccur(strErr);
                        }
                    case RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER /*309*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        if (strErr != null && !strErr.isEmpty() && SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.ErrorOccurDuetoPrinter(strErr);
                        }
                    case RequestState.REQUEST_GET_PRINTER_INFO /*310*/:
                        When_REQUEST_GET_PRINTER_INFO(SDcardUtility.this.m_HitiPPR_GetPrinterInfo.GetSocket());
                    case RequestState.REQUEST_GET_PRINTER_INFO_ERROR /*311*/:
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            strMSG = msg.getData().getString(MSGHandler.MSG);
                            if (SDcardUtility.this.HavePrintingPhotoListener()) {
                                SDcardUtility.this.m_PrintSDcardListener.ErrorOccur(strMSG);
                            }
                        }
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.ErrorTimeOut(strErr);
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META /*342*/:
                        if (SDcardUtility.this.mCheckPrinterTotalPrintedCount != null) {
                            Integer iFrame = SDcardUtility.this.mCheckPrinterTotalPrintedCount.GetTotalPrintedFrame("4x6");
                            if (SDcardUtility.this.printCommand == null) {
                                iPrintedCount = 0;
                            } else {
                                iPrintedCount = SDcardUtility.this.printCommand.GetPrintedCount();
                            }
                            int iCopies = ((Integer) SDcardUtility.this.m_iPhotoCopiesList.get(iPrintedCount)).intValue();
                            SDcardUtility.this.LOG.m383d(SDcardUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT iFrame: " + iFrame);
                            SDcardUtility.this.LOG.m383d(SDcardUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT count: " + iPrintedCount);
                            SDcardUtility.this.LOG.m383d(SDcardUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT copies: " + iCopies);
                            if (iFrame != null) {
                                SDcardUtility.this.NewRecordPrint(iFrame.intValue(), iCopies);
                            }
                            SDcardUtility.this.StartPrint(SDcardUtility.this.mCheckPrinterTotalPrintedCount.GetSocket());
                        }
                    case RequestState.REQUEST_QUICK_PRINT /*352*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        SDcardUtility.this.LOG.m385i("REQUEST_QUICK_PRINT", "complete: " + strMSG);
                        int iUnCleanNumber = Integer.parseInt(strMSG);
                        iCpDone = PrinterSumOfCopies(true);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.PrintDone(iCpDone, iUnCleanNumber);
                        }
                    case RequestState.REQUEST_QUICK_PRINT_ERROR /*353*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        SDcardUtility.this.LOG.m385i("...REQUEST_QUICK_PRINT_ERROR", String.valueOf(strMSG));
                        if (strMSG.contains(HitiPPR_QuickPrint.ERROR_SIZE_NOT_MATCH_1004)) {
                            if (SDcardUtility.this.HavePrintingPhotoListener()) {
                                SDcardUtility.this.m_PrintSDcardListener.SizeNoMatch(strMSG);
                            }
                        } else if (strMSG.equals(HitiPPR_QuickPrint.ERROR_FIND_NO_STORAGEID_1005) && SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_iMultiSelStorageIdList = null;
                            SDcardUtility.this.m_iMultiSelStorageIdList = SDcardUtility.this.m_PrintSDcardListener.StorageError(strMSG);
                        } else if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.ErrorOccur(strMSG);
                        }
                    case SMTPReply.START_MAIL_INPUT /*354*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.OnPrintingStatusChange(strMSG);
                        }
                    case RequestState.REQUEST_QUICK_PRINT_NEXT /*356*/:
                        int iTotalFrame = Integer.parseInt(msg.getData().getString(MSGHandler.MSG));
                        iCpDone = PrinterSumOfCopies(true);
                        SDcardUtility.this.LOG.m385i("NEXT iCpDone", String.valueOf(iCpDone));
                        SDcardUtility.this.m_PrintSDcardListener.OnPrintingCountChange("next", iCpDone);
                        iPrintedCount = SDcardUtility.this.printCommand.GetPrintedCount();
                        SDcardUtility.this.SavePrintCount(iTotalFrame, ((Integer) SDcardUtility.this.m_iPhotoCopiesList.get(iPrintedCount - 1)).intValue());
                        if (iPrintedCount < SDcardUtility.this.m_iPhotoCopiesList.size()) {
                            SDcardUtility.this.NewRecordPrint(iTotalFrame, ((Integer) SDcardUtility.this.m_iPhotoCopiesList.get(iPrintedCount)).intValue());
                        }
                    case RequestState.REQUEST_QUICK_PRINT_SENDED_DONE /*357*/:
                        SDcardUtility.this.m_PrintSDcardListener.OnPrintSendedNum(msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_CHECK_COPIES /*363*/:
                        int iTotoalFrame = Integer.parseInt(msg.getData().getString(MSGHandler.MSG));
                        int iPrintCopies = ((Integer) SDcardUtility.this.m_iPhotoCopiesList.get(SDcardUtility.this.printCommand.GetPrintedCount())).intValue();
                        iCpDone = PrinterSumOfCopies(false);
                        SDcardUtility.this.SavePrintCount(iTotoalFrame, iPrintCopies);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.OnPrintingCountChange("plus", iCpDone);
                        }
                    case RequestState.REQUEST_PAPER_JAM_RUN /*393*/:
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.PaperJamDone();
                        }
                    case RequestState.REQUEST_PAPER_JAM_RUN_ERROR_DUETO_PRINTER /*395*/:
                        String strError = msg.getData().getString(MSGHandler.MSG);
                        if (SDcardUtility.this.HavePrintingPhotoListener()) {
                            SDcardUtility.this.m_PrintSDcardListener.ErrorOccurDuetoPrinter(strError);
                        }
                    case RequestState.REQUEST_GPS_TOTAL_PRINTED_COUNT /*396*/:
                        SetTotoalPrintedRecord();
                    default:
                }
            }
        }

        private void When_REQUEST_GET_PRINTER_INFO(Socket socket) {
            SDcardUtility.this.LOG.m385i(SDcardUtility.this.TAG, "When_REQUEST_GET_PRINTER_INFO");
            String strGetVersion = SDcardUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrFirmwareVersionString().replace(".", XmlPullParser.NO_NAMESPACE).substring(0, 3);
            SDcardUtility.this.m_strProductIDString = SDcardUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrProductIDString();
            SDcardUtility.this.LOG.m385i(SDcardUtility.this.TAG, "When_REQUEST_GET_PRINTER_INFO PID: " + SDcardUtility.this.m_strProductIDString);
            SDcardUtility.this.LOG.m385i(SDcardUtility.this.TAG, "m_strModel: " + SDcardUtility.this.m_strModel);
            if (SDcardUtility.this.m_strProductIDString != null) {
                SDcardUtility.this.m_GVTotalPrintedRecord = new GlobalVariable_TotalPrintedRecord(SDcardUtility.this.m_Context, SDcardUtility.this.m_strProductIDString);
                if (!SDcardUtility.this.m_strModel.equals(SDcardUtility.this.m_strProductIDString)) {
                    String strErr = HitiPPR_QuickPrint.ERROR_MODEL_1007;
                    if (SDcardUtility.this.HavePrintingPhotoListener()) {
                        SDcardUtility.this.m_PrintSDcardListener.ErrorOccur(strErr);
                        return;
                    }
                    return;
                }
            }
            if (SDcardUtility.this.HavePrintingPhotoListener()) {
                SDcardUtility.this.m_PrintSDcardListener.EndCheckPrintInfo(strGetVersion, SDcardUtility.this.m_strProductIDString);
            }
            String strUpdateVersion = ((String) BurnFWUtility.GetTheNewestFWVersion(SDcardUtility.this.m_Context, SDcardUtility.this.m_strProductIDString, true).second).substring(0, 3);
            String strSerialNumber = SDcardUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrSerialNumber();
            if (strSerialNumber != null) {
                SDcardUtility.this.m_GVUploadInfo.RestoreGlobalVariable();
                SDcardUtility.this.m_GVUploadInfo.SetSerialNumber(strSerialNumber);
                SDcardUtility.this.m_GVUploadInfo.SaveGlobalVariableForever();
                SDcardUtility.this.m_GVTotalPrintedRecord.RestoreGlobalVariable();
                SDcardUtility.this.m_GVTotalPrintedRecord.SetSerialNumber(strSerialNumber);
                SDcardUtility.this.m_GVTotalPrintedRecord.SaveGlobalVariable();
            }
            if (Integer.parseInt(strGetVersion) >= Integer.parseInt(strUpdateVersion)) {
                SDcardUtility.this.GetFirstPrintFrame(socket);
            } else if (SDcardUtility.this.HavePrintingPhotoListener()) {
                SDcardUtility.this.m_PrintSDcardListener.StartBurnFW(socket);
            }
        }

        private int PrinterSumOfCopies(boolean bNext) {
            int iCpDone = 0;
            int iPrintedCount = SDcardUtility.this.printCommand.GetPrintedCount();
            SDcardUtility.this.m_iLastCp = SDcardUtility.this.printCommand.GetPrintedLastCopies();
            for (int i = 0; i < iPrintedCount; i++) {
                iCpDone += ((Integer) SDcardUtility.this.m_iPhotoCopiesList.get(i)).intValue();
            }
            if (SDcardUtility.this.m_iPathRoute != ControllerState.NO_PLAY_ITEM || bNext) {
                return iCpDone;
            }
            return iCpDone + SDcardUtility.this.m_iLastCp;
        }

        private void SetTotoalPrintedRecord() {
            if (SDcardUtility.this.printCommand != null) {
                HashMap<String, Integer> iPrintedCountList = SDcardUtility.this.printCommand.GetTotalPrintedFrameRecord();
                SDcardUtility.this.LOG.m384e("SetTotoalPrintedRecord_done", String.valueOf(iPrintedCountList));
                if (iPrintedCountList != null) {
                    SDcardUtility.this.m_GVTotalPrintedRecord = new GlobalVariable_TotalPrintedRecord(SDcardUtility.this.m_Context, SDcardUtility.this.m_strModel);
                    SDcardUtility.this.m_GVTotalPrintedRecord.RestoreGlobalVariable();
                    SDcardUtility.this.m_GVTotalPrintedRecord.ClearGlobalVariable();
                    SDcardUtility.this.m_GVTotalPrintedRecord.SetPrintOutList(iPrintedCountList);
                    SDcardUtility.this.m_GVTotalPrintedRecord.SaveGlobalVariable();
                    SDcardUtility.this.printCommand.InitTotalPrintedCountList();
                }
            }
        }
    }

    public SDcardUtility(Context context, ArrayList<Integer> iMultiSelPhotoIdList, ArrayList<Integer> iMultiSelStorageIdLis, ArrayList<Integer> iPhotoCopiesList) {
        this.R_STRING_version = 0;
        this.R_STRING_version_p310 = 0;
        this.R_STRING_version_p520 = 0;
        this.R_STRING_version_p750 = 0;
        this.R_COLOR_GS_COLOR = 0;
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = 0;
        this.m_iPathRoute = 0;
        this.m_iMultiSelPhotoIdList = null;
        this.m_iMultiSelStorageIdList = null;
        this.m_iPhotoCopiesList = null;
        this.m_iLastCp = 0;
        this.m_iPaperType = 0;
        this.mPrintInfo = null;
        this.m_Context = null;
        this.m_strProductIDString = null;
        this.m_QPrintSDcardHandler = null;
        this.m_PrintSDcardListener = null;
        this.m_GVUploadInfo = null;
        this.m_SQLiteUtility = null;
        this.m_iSQLiteID = -1;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.printCommand = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_bSharpenValue = (byte) -120;
        this.LOG = null;
        this.TAG = null;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.m_bDuplex = false;
        this.m_GVTotalPrintedRecord = null;
        this.m_PaperJamRun = null;
        this.mCheckPrinterTotalPrintedCount = null;
        this.m_Context = context;
        this.m_QPrintSDcardHandler = new QuickPrintSDcardHandler();
        this.m_iMultiSelPhotoIdList = iMultiSelPhotoIdList;
        this.m_iMultiSelStorageIdList = iMultiSelStorageIdLis;
        this.m_iPhotoCopiesList = iPhotoCopiesList;
        this.m_SQLiteUtility = new PrintingInfoUtility(this.m_Context);
        this.m_GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        this.LOG = new LogManager(0);
        this.TAG = "SDcardUtility";
        GetResourceID(this.m_Context);
        GetPref(this.m_Context);
    }

    public SDcardUtility(Context context) {
        this.R_STRING_version = 0;
        this.R_STRING_version_p310 = 0;
        this.R_STRING_version_p520 = 0;
        this.R_STRING_version_p750 = 0;
        this.R_COLOR_GS_COLOR = 0;
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = 0;
        this.m_iPathRoute = 0;
        this.m_iMultiSelPhotoIdList = null;
        this.m_iMultiSelStorageIdList = null;
        this.m_iPhotoCopiesList = null;
        this.m_iLastCp = 0;
        this.m_iPaperType = 0;
        this.mPrintInfo = null;
        this.m_Context = null;
        this.m_strProductIDString = null;
        this.m_QPrintSDcardHandler = null;
        this.m_PrintSDcardListener = null;
        this.m_GVUploadInfo = null;
        this.m_SQLiteUtility = null;
        this.m_iSQLiteID = -1;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.printCommand = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_bSharpenValue = (byte) -120;
        this.LOG = null;
        this.TAG = null;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.m_bDuplex = false;
        this.m_GVTotalPrintedRecord = null;
        this.m_PaperJamRun = null;
        this.mCheckPrinterTotalPrintedCount = null;
        this.m_Context = context;
        this.m_QPrintSDcardHandler = new QuickPrintSDcardHandler();
    }

    public void SetIPandPort(String IP, int iPort) {
        this.IP = IP;
        this.m_iPort = iPort;
    }

    private void GetResourceID(Context context) {
        this.R_STRING_version = ResourceSearcher.getId(context, RS_TYPE.STRING, "version");
        this.R_STRING_version_p310 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_310w");
        this.R_STRING_version_p520 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p520");
        this.R_STRING_version_p750 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_750l");
        this.R_COLOR_GS_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "GS_COLOR");
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "REPLACE_EDIT_PHOTO_MASK_COLOR");
    }

    private void GetPref(Context context) {
        this.m_pref = new JumpPreferenceKey(context);
        this.m_iPathRoute = this.m_pref.GetPathSelectedPref();
        this.m_strModel = this.m_pref.GetModelPreference();
    }

    public void SetPrintingPhotoListener(ISDcard printingPhotoListener) {
        this.m_PrintSDcardListener = printingPhotoListener;
    }

    private boolean HavePrintingPhotoListener() {
        if (this.m_PrintSDcardListener == null) {
            return false;
        }
        return true;
    }

    public void SetPrinterInfo(EditMetaUtility m_EditMetaUtility, PrintMode printMode) {
        boolean bSelTexture;
        boolean bDuplex;
        this.m_PrintMode = printMode;
        if (this.mPrintInfo == null) {
            this.mPrintInfo = new SendPhotoInfo(this.m_strModel);
        }
        if (m_EditMetaUtility.GetPrintTexture() == 0) {
            bSelTexture = false;
        } else {
            bSelTexture = true;
        }
        int iMethod = m_EditMetaUtility.GetPrintMethod();
        this.mPrintInfo.SetPhotoFace(bSelTexture, iMethod == 0 ? (byte) 1 : (byte) 2, m_EditMetaUtility.GetPrintSharpenByte());
        if (m_EditMetaUtility.GetPrintDuplex() == 0) {
            bDuplex = false;
        } else {
            bDuplex = true;
        }
        int iPaperType = m_EditMetaUtility.GetServerPaperType();
        SetPrinterInfo(iPaperType, bDuplex);
        if (HavePrintingPhotoListener()) {
            this.m_PrintSDcardListener.SetPrintout(PrinterInfo.GetPrintoutItem(this.m_Context, iPaperType));
        }
    }

    public void SetPrinterInfo(int paperType, boolean bIsTexture, boolean bDuplex, int methodSpeed, int iSharpenValue) {
        if (this.mPrintInfo == null) {
            this.mPrintInfo = new SendPhotoInfo(this.m_strModel);
        }
        byte mbyPrintQTY = (byte) methodSpeed;
        this.mPrintInfo.SetPhotoFace(bIsTexture, mbyPrintQTY, EditMetaUtility.GetSarpenValue(iSharpenValue));
        SetPrinterInfo(paperType, bDuplex);
    }

    private void SetPrinterInfo(int paperType, boolean bDuplex) {
        if (this.mPrintInfo == null) {
            this.mPrintInfo = new SendPhotoInfo(this.m_strModel);
        }
        this.m_iPaperType = paperType;
        this.m_bDuplex = bDuplex;
        this.mPrintInfo.SetPhotoSize(PrinterInfo.GetMediaSize(paperType), PrinterInfo.GetPrintLayout(paperType), bDuplex, paperType);
    }

    public void Stop() {
        if (this.m_SQLiteUtility != null) {
            this.m_SQLiteUtility.Close();
        }
        if (this.printCommand != null) {
            this.printCommand.Stop();
        }
        if (this.m_HitiPPR_GetPrinterInfo != null) {
            this.m_HitiPPR_GetPrinterInfo.Stop();
        }
        if (this.m_HitiPPR_RecoveryPrinter != null) {
            this.m_HitiPPR_RecoveryPrinter.Stop();
        }
        if (this.m_QPrintSDcardHandler != null) {
            this.m_QPrintSDcardHandler.SetStop(true);
        }
    }

    public void SetStop(boolean bStop) {
        if (this.m_QPrintSDcardHandler == null) {
            this.m_QPrintSDcardHandler = new QuickPrintSDcardHandler();
        }
        if (bStop) {
            this.m_QPrintSDcardHandler.SetStop(true);
        } else {
            this.m_QPrintSDcardHandler.SetStop(false);
        }
    }

    public void SendPhoto() {
        this.LOG.m385i(this.TAG, "SendPhoto IP: " + this.IP);
        if (HavePrintingPhotoListener()) {
            this.m_PrintSDcardListener.StartCheckPrintInfo();
        }
        this.m_HitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this.m_Context, this.IP, this.m_iPort, this.m_QPrintSDcardHandler);
        this.m_HitiPPR_GetPrinterInfo.start();
    }

    public void SendPhoto(String strIP, int iPort) {
        if (HavePrintingPhotoListener()) {
            this.m_PrintSDcardListener.StartCheckPrintInfo();
        }
        this.m_HitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this.m_Context, strIP, iPort, this.m_QPrintSDcardHandler);
        this.m_HitiPPR_GetPrinterInfo.start();
    }

    public void StartPrintForNotBurnFireWare(Socket socket) {
        GetFirstPrintFrame(socket);
    }

    private void StartPrint(Socket socket) {
        this.LOG.m385i(this.TAG, "StartPrint printCommand: " + this.printCommand);
        int iPrintedCount = 0;
        if (this.printCommand != null) {
            iPrintedCount = this.printCommand.GetPrintedCount();
            this.m_iLastCp = this.printCommand.GetPrintedLastCopies();
        }
        SetPrintCommand(socket, this.m_strProductIDString);
        this.LOG.m385i(this.TAG, "StartPrint iPrintedCount: " + iPrintedCount);
        this.LOG.m385i(this.TAG, "StartPrint m_iLastCp: " + this.m_iLastCp);
        if (this.m_iLastCp + iPrintedCount > 0) {
            this.printCommand.ContinuePrint(iPrintedCount, this.m_iLastCp);
        }
        this.printCommand.start();
    }

    public void StartPrintAfterEditFlow(Socket socket, String strProductIDString) {
        this.m_strProductIDString = strProductIDString;
        SetPrintCommand(socket, strProductIDString);
        this.printCommand.start();
    }

    private void SetPrintCommand(Socket socket, String strProductIDString) {
        this.printCommand = new HitiPPR_QuickPrint(this.m_Context, this.IP, this.m_iPort, this.m_QPrintSDcardHandler);
        this.printCommand.PutIDs(this.m_iMultiSelPhotoIdList, this.m_iMultiSelStorageIdList);
        if (this.mPrintInfo != null) {
            this.printCommand.SetPrintInfo(this.mPrintInfo);
        }
        this.printCommand.PutCopies(this.m_iPhotoCopiesList);
        this.printCommand.SetProductID(strProductIDString);
        this.printCommand.SetSharpenValue(this.m_bSharpenValue);
        this.printCommand.SetSocket(socket);
    }

    public void PutCancel() {
        if (this.printCommand != null) {
            this.printCommand.StopForCancel();
        }
    }

    public void RecoveryPrint() {
        RecoveryPrint(this.m_Context);
    }

    public void RecoveryPrint(Context context) {
        this.m_HitiPPR_RecoveryPrinter = new HitiPPR_RecoveryPrinter(context, this.IP, this.m_iPort, this.m_QPrintSDcardHandler);
        this.m_HitiPPR_RecoveryPrinter.start();
    }

    private void NewRecordPrint(int iTotalFrame, int iNowCopies) {
        int i;
        this.m_GVUploadInfo.RestoreGlobalVariable();
        String strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(3);
        PrintingInfoUtility printingInfoUtility = this.m_SQLiteUtility;
        String GetSerialNumber = this.m_GVUploadInfo.GetSerialNumber();
        String GetUploader = this.m_GVUploadInfo.GetUploader();
        int AdjustPaperType = PrinterInfo.AdjustPaperType(this.m_iPaperType, this.m_bDuplex);
        String MakeAESCount = EncryptAndDecryptAES.MakeAESCount(this.m_Context, iNowCopies, strTimeStamp);
        String MakeAESCount2 = EncryptAndDecryptAES.MakeAESCount(this.m_Context, 0, strTimeStamp);
        int ChangeProductIDValueForServer = PrinterInfo.ChangeProductIDValueForServer(this.m_strProductIDString);
        if (this.m_PrintMode == PrintMode.Snap) {
            i = 1;
        } else {
            i = 0;
        }
        this.m_iSQLiteID = (int) printingInfoUtility.InsertPrintingInfo(GetSerialNumber, strTimeStamp, GetUploader, 0, AdjustPaperType, MakeAESCount, MakeAESCount2, 0, ChangeProductIDValueForServer, iTotalFrame, i);
        this.LOG.m383d("New!!!_ID_" + String.valueOf(this.m_iSQLiteID), "count=" + String.valueOf(iNowCopies));
        this.LOG.m383d("New!!!_ID_" + String.valueOf(this.m_iSQLiteID), "iTotalFrame=" + String.valueOf(iTotalFrame));
    }

    private void SavePrintCount(int iTotalFrame, int iNowCopies) {
        PrintingInfo printingInfo = this.m_SQLiteUtility.GetPrintingInfoById((long) this.m_iSQLiteID);
        int iLastCount = 0;
        String strTimeStamp;
        if (printingInfo.GetID() == -1) {
            int i;
            strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(3);
            this.m_GVUploadInfo.RestoreGlobalVariable();
            PrintingInfoUtility printingInfoUtility = this.m_SQLiteUtility;
            String GetSerialNumber = this.m_GVUploadInfo.GetSerialNumber();
            String GetUploader = this.m_GVUploadInfo.GetUploader();
            int AdjustPaperType = PrinterInfo.AdjustPaperType(this.m_iPaperType, this.m_bDuplex);
            String MakeAESCount = EncryptAndDecryptAES.MakeAESCount(this.m_Context, iNowCopies, strTimeStamp);
            String MakeAESCount2 = EncryptAndDecryptAES.MakeAESCount(this.m_Context, 1, strTimeStamp);
            int ChangeProductIDValueForServer = PrinterInfo.ChangeProductIDValueForServer(this.m_strProductIDString);
            if (this.m_PrintMode == PrintMode.Snap) {
                i = 1;
            } else {
                i = 0;
            }
            this.m_iSQLiteID = (int) printingInfoUtility.InsertPrintingInfo(GetSerialNumber, strTimeStamp, GetUploader, 0, AdjustPaperType, MakeAESCount, MakeAESCount2, 0, ChangeProductIDValueForServer, iTotalFrame, i);
        } else {
            String strAESCount = printingInfo.GetRealCount();
            strTimeStamp = printingInfo.GetPrintingTime();
            if (!(strAESCount == null || strAESCount.equals("NULL"))) {
                iLastCount = Integer.valueOf(EncryptAndDecryptAES.OpenCount(this.m_Context, EncryptAndDecryptAES.OpenAESCount(this.m_Context, strAESCount, strTimeStamp))).intValue();
            }
            iLastCount++;
            this.m_SQLiteUtility.UpdatePrintingInfo(this.m_iSQLiteID, null, null, null, null, null, null, EncryptAndDecryptAES.MakeAESCount(this.m_Context, iLastCount, strTimeStamp), null, null, -1);
        }
        this.LOG.m383d("save!!!_ID_" + String.valueOf(this.m_iSQLiteID), String.valueOf(iLastCount) + "/" + String.valueOf(iNowCopies));
        this.LOG.m383d("save!!!_ID_" + String.valueOf(this.m_iSQLiteID), "=" + iTotalFrame);
    }

    public void EjectPaperJam() {
        this.m_PaperJamRun = new HitiPPR_PaperJamRun(this.m_Context, this.IP, this.m_iPort, this.m_QPrintSDcardHandler);
        this.m_PaperJamRun.start();
    }

    public void StopPaperJam() {
        if (this.m_PaperJamRun != null) {
            this.m_PaperJamRun.Stop();
        }
    }

    void GetFirstPrintFrame(Socket socket) {
        this.LOG.m383d(this.TAG, "GetFirstPrintFrame");
        this.mCheckPrinterTotalPrintedCount = new HitiPPR_CheckPrinterTotalPrintedCount(this.m_Context, this.IP, this.m_iPort, this.m_QPrintSDcardHandler);
        this.mCheckPrinterTotalPrintedCount.SetProductID(this.m_strProductIDString);
        this.mCheckPrinterTotalPrintedCount.SetSocket(socket);
        this.mCheckPrinterTotalPrintedCount.start();
    }
}
