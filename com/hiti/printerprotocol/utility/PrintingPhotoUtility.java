package com.hiti.printerprotocol.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import com.hiti.ImageFilter.ImageFilter;
import com.hiti.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.jni.hello.Hello;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_CheckPrintComplete;
import com.hiti.printerprotocol.request.HitiPPR_CheckPrinterTotalPrintedCount;
import com.hiti.printerprotocol.request.HitiPPR_GetPrinterInfo;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.hiti.printerprotocol.request.HitiPPR_SendPhoto;
import com.hiti.service.upload.UploadUtility;
import com.hiti.sql.printerInfo.PrintingInfo;
import com.hiti.sql.printerInfo.PrintingInfoUtility;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.ui.drawview.garnishitem.utility.GarnishItemUtility;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.Verify.PrintMode;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import java.net.Socket;
import org.xmlpull.v1.XmlPullParser;

public class PrintingPhotoUtility {
    private LogManager LOG;
    public int R_COLOR_GS_COLOR;
    public int R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR;
    public int R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR_NO_PRINT_METAL;
    private Context m_Context;
    private GlobalVariable_UploadInfo m_GVUploadInfo;
    private GlobalVariable_UserInfo m_GVUserInfo;
    private HitiPPR_CheckPrintComplete m_HitiPPR_CheckPrintComplete;
    private HitiPPR_CheckPrinterTotalPrintedCount m_HitiPPR_CheckPrinterTotalPrintedCount;
    private HitiPPR_GetPrinterInfo m_HitiPPR_GetPrinterInfo;
    private HitiPPR_RecoveryPrinter m_HitiPPR_RecoveryPrinter;
    private HitiPPR_SendPhoto m_HitiPPR_SendPhoto;
    private PrepareSendPhoto m_PrepareSendPhoto;
    private PrinterHandler m_PrinterHandler;
    private PrintingInfoUtility m_PrintingInfoUtility;
    private PrintingPhotoListener m_PrintingPhotoListener;
    private Socket m_bNextSocket;
    private boolean m_boFlashCard;
    private boolean m_boHaveSilver;
    private boolean m_boMetalEnable;
    private boolean m_boMirror;
    private int m_iCurrentPrintCount;
    private int m_iLastPrintCount;
    private int m_iMaskColor;
    private int m_iPrintCount;
    private int m_iPrintMode;
    private int m_iPrintingInfoID;
    private int m_iTotalFrame;
    private String m_strEditPath;
    private String m_strMaskPath;
    private String m_strProductIDString;

    private class PrepareSendPhoto extends AsyncTask<Void, Void, Boolean> {
        private Bitmap m_EditPhoto;
        private Bitmap m_MaskPhoto;
        private boolean m_boStop;
        private int m_iResult;

        private PrepareSendPhoto() {
            this.m_iResult = 0;
            this.m_EditPhoto = null;
            this.m_MaskPhoto = null;
            this.m_boStop = false;
        }

        protected Boolean doInBackground(Void... params) {
            BitmapMonitorResult bmr;
            PrintingPhotoUtility.this.m_HitiPPR_SendPhoto = new HitiPPR_SendPhoto(PrintingPhotoUtility.this.m_Context, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, PrintingPhotoUtility.this.m_PrinterHandler);
            if (PrintingPhotoUtility.this.m_strMaskPath != null) {
                bmr = BitmapMonitor.GetBitmapFromFile(PrintingPhotoUtility.this.m_Context, PrintingPhotoUtility.this.m_strMaskPath, true);
                if (bmr.IsSuccess()) {
                    this.m_MaskPhoto = bmr.GetBitmap();
                } else {
                    this.m_iResult = bmr.GetResult();
                    return Boolean.valueOf(false);
                }
            }
            if (this.m_MaskPhoto != null && PrintingPhotoUtility.this.IsMirror()) {
                PrintingPhotoUtility.this.Mirror(this.m_MaskPhoto);
            }
            if (PrintingPhotoUtility.this.m_strEditPath != null) {
                bmr = BitmapMonitor.GetBitmapFromFile(PrintingPhotoUtility.this.m_Context, PrintingPhotoUtility.this.m_strEditPath, true);
                if (bmr.IsSuccess()) {
                    this.m_EditPhoto = bmr.GetBitmap();
                } else {
                    this.m_iResult = bmr.GetResult();
                    return Boolean.valueOf(false);
                }
            }
            if (new ImageFilter(PrintingPhotoUtility.this.m_Context).ProcessImage(this.m_EditPhoto, IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_UnSharpMask)) {
                if (PrintingPhotoUtility.this.IsMirror() || PrintingPhotoUtility.this.IsFlashCard()) {
                    PrintingPhotoUtility.this.Mirror(this.m_EditPhoto);
                }
                if (PrintingPhotoUtility.this.IsFlashCard()) {
                    if (this.m_MaskPhoto == null) {
                        bmr = BitmapMonitor.CreateBitmap(this.m_EditPhoto.getWidth(), this.m_EditPhoto.getHeight(), Config.ARGB_8888);
                        if (bmr.IsSuccess()) {
                            this.m_MaskPhoto = bmr.GetBitmap();
                        } else {
                            this.m_iResult = bmr.GetResult();
                            return Boolean.valueOf(false);
                        }
                    }
                    this.m_MaskPhoto.eraseColor(PrintingPhotoUtility.this.m_Context.getResources().getColor(PrintingPhotoUtility.this.R_COLOR_GS_COLOR));
                } else if (this.m_MaskPhoto != null) {
                    int iColor = PrintingPhotoUtility.this.m_Context.getResources().getColor(PrintingPhotoUtility.this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR);
                    if (!PrintingPhotoUtility.this.IsMetalEnable()) {
                        iColor = PrintingPhotoUtility.this.m_Context.getResources().getColor(PrintingPhotoUtility.this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR_NO_PRINT_METAL);
                    }
                    GarnishItemUtility.ReplaceMaskColor(PrintingPhotoUtility.this.m_Context, this.m_MaskPhoto, iColor);
                    new Canvas(this.m_EditPhoto).drawBitmap(this.m_MaskPhoto, 0.0f, 0.0f, null);
                }
                if (this.m_MaskPhoto != null) {
                    if (PrintingPhotoUtility.this.IsMetalEnable()) {
                        if (PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.PreparePhoto(null, this.m_MaskPhoto, null)) {
                            PrintingPhotoUtility.this.m_boHaveSilver = true;
                        } else {
                            this.m_iResult = 99;
                            return Boolean.valueOf(false);
                        }
                    }
                    this.m_MaskPhoto.recycle();
                    this.m_MaskPhoto = null;
                }
                if (this.m_EditPhoto != null) {
                    PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.PreparePhoto(this.m_EditPhoto, null, null);
                    this.m_EditPhoto.recycle();
                    this.m_EditPhoto = null;
                }
                PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.SetMaskColor((byte) PrintingPhotoUtility.this.m_iMaskColor);
                PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.SetPrintCount((short) (PrintingPhotoUtility.this.m_iPrintCount - PrintingPhotoUtility.this.m_iCurrentPrintCount));
                return Boolean.valueOf(true);
            }
            this.m_EditPhoto.recycle();
            this.m_EditPhoto = null;
            this.m_iResult = 99;
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean boResult) {
            if (this.m_iResult != 0) {
                if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                    PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorPreparePhoto(this.m_iResult);
                }
            } else if (!this.m_boStop) {
                PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.SetSocket(PrintingPhotoUtility.this.m_bNextSocket);
                PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.start();
            }
        }

        public void Stop() {
            this.m_boStop = true;
        }
    }

    private class PrinterHandler extends MSGHandler {
        private PrinterHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                switch (msg.what) {
                    case RequestState.REQUEST_CHECK_PRINT_COMPLETE /*300*/:
                        When_REQUEST_CHECK_PRINT_COMPLETE(msg);
                    case RequestState.REQUEST_CHECK_PRINT_COMPLETE_SAVE_COUNTER /*301*/:
                        When_REQUEST_CHECK_PRINT_COMPLETE_SAVE_COUNTER(msg);
                    case RequestState.REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS /*302*/:
                        When_REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS(msg);
                    case RequestState.REQUEST_CHECK_PRINT_COMPLETE_ERROR_DUETO_PRINTER /*303*/:
                        When_REQUEST_CHECK_PRINT_COMPLETE_ERROR_DUETO_PRINTER(msg);
                    case RequestState.REQUEST_CHECK_PRINT_COMPLETE_ERROR /*304*/:
                        When_REQUEST_CHECK_PRINT_COMPLETE_ERROR(msg);
                    case RequestState.REQUEST_RECOVERY_PRINTER /*305*/:
                        When_REQUEST_RECOVERY_PRINTER(msg);
                    case RequestState.REQUEST_RECOVERY_PRINTER_ERROR /*306*/:
                        When_REQUEST_RECOVERY_PRINTER_ERROR(msg);
                    case RequestState.REQUEST_SEND_PHOTO /*307*/:
                        When_REQUEST_SEND_PHOTO(msg);
                    case RequestState.REQUEST_SEND_PHOTO_ERROR /*308*/:
                        When_REQUEST_SEND_PHOTO_ERROR(msg);
                    case RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER /*309*/:
                        When_REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER(msg);
                    case RequestState.REQUEST_GET_PRINTER_INFO /*310*/:
                        When_REQUEST_GET_PRINTER_INFO(msg);
                    case RequestState.REQUEST_GET_PRINTER_INFO_ERROR /*311*/:
                        When_REQUEST_GET_PRINTER_INFO_ERROR(msg);
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        When_REQUEST_TIMEOUT_ERROR(msg);
                    case RequestState.REQUEST_SET_PRINT_COUNT /*337*/:
                        When_REQUEST_SET_PRINT_COUNT(msg);
                    case RequestState.REQUEST_GET_ALBUM_ID_META /*342*/:
                        PrintingPhotoUtility.this.LOG.m384e("REQUEST_CHECK_TOTAL_PRINTED_COUNT", "REQUEST_CHECK_TOTAL_PRINTED_COUNT");
                        When_Get_Total_Frame();
                    case RequestState.REQUEST_GET_ALBUM_ID_META_ERROR /*343*/:
                        if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                            PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorCheckPrintInfo(msg);
                        }
                    default:
                }
            }
        }

        private void When_Get_Total_Frame() {
            Integer frame = PrintingPhotoUtility.this.m_HitiPPR_CheckPrinterTotalPrintedCount.GetTotalPrintedFrame("2x3");
            String printoutName = XmlPullParser.NO_NAMESPACE;
            if (frame != null) {
                PrintingPhotoUtility.this.m_iTotalFrame = frame.intValue();
            }
            PrintingPhotoUtility.this.LOG.m384e("printoutName", "=" + String.valueOf(printoutName));
            PrintingPhotoUtility.this.LOG.m384e("m_iTotalFrame", "=" + String.valueOf(PrintingPhotoUtility.this.m_iTotalFrame));
            PrintingPhotoUtility.this.RecordPrint();
            PrintingPhotoUtility.this.StartPrint(false);
        }

        private void When_REQUEST_GET_PRINTER_INFO(Message msg) {
            PrintingPhotoUtility.this.m_strProductIDString = PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrProductIDString();
            PrintingPhotoUtility.this.m_bNextSocket = PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetSocket();
            if (PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrSerialNumber() != null) {
                PrintingPhotoUtility.this.m_GVUploadInfo.RestoreGlobalVariable();
                PrintingPhotoUtility.this.m_GVUploadInfo.SetSerialNumber(PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrSerialNumber());
                PrintingPhotoUtility.this.m_GVUploadInfo.SaveGlobalVariableForever();
                PrintingPhotoUtility.this.LOG.m385i("m_SerialNumber", PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrSerialNumber());
            }
            String strGetVersion = PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrFirmwareVersionString().replace(".", XmlPullParser.NO_NAMESPACE).substring(0, 4);
            PrintingPhotoUtility.this.LOG.m385i("strGetVersion", strGetVersion);
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.EndCheckPrintInfo(strGetVersion, PrintingPhotoUtility.this.m_strProductIDString);
            }
            String strUpdateVersion = BurnFWUtility.GetTheNewestFWVersion(PrintingPhotoUtility.this.m_Context, PrintingPhotoUtility.this.m_strProductIDString, true).second;
            PrintingPhotoUtility.this.LOG.m385i("strUpdateVersion", strUpdateVersion);
            if (Integer.parseInt(strGetVersion) < Integer.parseInt(strUpdateVersion)) {
                PrintingPhotoUtility.this.m_HitiPPR_GetPrinterInfo.Stop();
                if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                    PrintingPhotoUtility.this.m_PrintingPhotoListener.StartBurnFW(strGetVersion, strUpdateVersion);
                    return;
                }
                return;
            }
            PrintingPhotoUtility.this.StartPrint(true);
        }

        private void When_REQUEST_GET_PRINTER_INFO_ERROR(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorCheckPrintInfo(msg);
            }
        }

        private void When_REQUEST_SEND_PHOTO(Message msg) {
            Bundle buddle = msg.getData();
            String strMSG = null;
            if (buddle != null) {
                strMSG = buddle.getString(MSGHandler.MSG);
            }
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.EndSendingPhoto(PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.GetAttrSSID(), PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.GetAttrSecurityKey());
            }
            PrintingPhotoUtility.this.m_bNextSocket = PrintingPhotoUtility.this.m_HitiPPR_SendPhoto.GetSocket();
            PrintingPhotoUtility.this.CheckPrintComplete(Integer.parseInt(strMSG));
        }

        private void When_REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorSendingPhotoDuetoPrinter(msg);
            }
        }

        private void When_REQUEST_SEND_PHOTO_ERROR(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorSendingPhoto(msg);
            }
        }

        private void When_REQUEST_CHECK_PRINT_COMPLETE_SAVE_COUNTER(Message msg) {
            PrintingPhotoUtility.this.SavePrintCount();
            PrintingPhotoUtility.this.LOG.m385i("Save print count", "Finish");
        }

        private void When_REQUEST_CHECK_PRINT_COMPLETE_SET_PRINTER_STATUS(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.OnPrintingStatusChange(msg);
            }
        }

        private void When_REQUEST_SET_PRINT_COUNT(Message msg) {
            PrintingPhotoUtility.this.m_iCurrentPrintCount = PrintingPhotoUtility.this.m_HitiPPR_CheckPrintComplete.GetCurrentPrintCount();
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.OnPrintingCountChange(PrintingPhotoUtility.this.m_iCurrentPrintCount, PrintingPhotoUtility.this.m_iPrintCount);
            }
        }

        private void When_REQUEST_CHECK_PRINT_COMPLETE(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.EndCheckPrinting();
            }
            PrintingPhotoUtility.this.m_HitiPPR_CheckPrintComplete.Stop();
        }

        private void When_REQUEST_CHECK_PRINT_COMPLETE_ERROR_DUETO_PRINTER(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorCheckPrintingDuetoPrinter(msg);
            }
        }

        private void When_REQUEST_CHECK_PRINT_COMPLETE_ERROR(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorCheckPrinting(msg);
            }
        }

        private void When_REQUEST_RECOVERY_PRINTER(Message msg) {
            PrintingPhotoUtility.this.m_bNextSocket = PrintingPhotoUtility.this.m_HitiPPR_RecoveryPrinter.GetSocket();
            PrintingPhotoUtility.this.StartPrint(false);
            PrintingPhotoUtility.this.LOG.m385i("REQUEST_RECOVERY_PRINTER", "Try again");
        }

        private void When_REQUEST_RECOVERY_PRINTER_ERROR(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorRecoveryPrinter(msg);
            }
        }

        private void When_REQUEST_TIMEOUT_ERROR(Message msg) {
            if (PrintingPhotoUtility.this.HavePrintingPhotoListener()) {
                PrintingPhotoUtility.this.m_PrintingPhotoListener.ErrorTimeOut(msg);
            }
        }
    }

    public PrintingPhotoUtility(Context context, String strEditPath, String strMaskPath, boolean boFlashCard, boolean boMirror, int iPrintCount, int iMaskColor, boolean boMetalEnable, PrintMode printMode) {
        this.R_COLOR_GS_COLOR = 0;
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = 0;
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR_NO_PRINT_METAL = 0;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_SendPhoto = null;
        this.m_HitiPPR_CheckPrintComplete = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.m_HitiPPR_CheckPrinterTotalPrintedCount = null;
        this.m_strEditPath = null;
        this.m_strMaskPath = null;
        this.m_iPrintCount = 1;
        this.m_iCurrentPrintCount = 0;
        this.m_iLastPrintCount = 0;
        this.m_boHaveSilver = false;
        this.m_boFlashCard = false;
        this.m_boMirror = false;
        this.m_boMetalEnable = true;
        this.m_iMaskColor = 0;
        this.m_PrintingPhotoListener = null;
        this.m_strProductIDString = WirelessType.TYPE_P231;
        this.m_PrepareSendPhoto = null;
        this.m_PrintingInfoUtility = null;
        this.m_GVUserInfo = null;
        this.m_iPrintingInfoID = -1;
        this.m_GVUploadInfo = null;
        this.m_bNextSocket = null;
        this.m_iTotalFrame = 0;
        this.m_iPrintMode = 0;
        this.m_PrinterHandler = new PrinterHandler();
        this.LOG = new LogManager(0);
        this.m_Context = context;
        this.m_strEditPath = strEditPath;
        this.m_strMaskPath = strMaskPath;
        this.m_iPrintCount = iPrintCount;
        this.m_boFlashCard = boFlashCard;
        this.m_boMirror = boMirror;
        this.m_iMaskColor = iMaskColor;
        this.m_boMetalEnable = boMetalEnable;
        this.m_iPrintMode = IfSnapMode(printMode);
        this.m_GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        this.m_GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
        this.m_PrintingInfoUtility = new PrintingInfoUtility(this.m_Context);
        GetResourceID(this.m_Context);
    }

    private int IfSnapMode(PrintMode printMode) {
        if (printMode.equals(PrintMode.Snap)) {
            return 1;
        }
        return 0;
    }

    private void GetResourceID(Context context) {
        this.R_COLOR_GS_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "GS_COLOR");
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = ResourceSearcher.getId(context, RS_TYPE.COLOR, "REPLACE_EDIT_PHOTO_MASK_COLOR");
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR_NO_PRINT_METAL = ResourceSearcher.getId(context, RS_TYPE.COLOR, "REPLACE_EDIT_PHOTO_MASK_COLOR_NO_PRINT_METAL");
    }

    public void SetPrintingPhotoListener(PrintingPhotoListener printingPhotoListener) {
        this.m_PrintingPhotoListener = printingPhotoListener;
    }

    private boolean HavePrintingPhotoListener() {
        if (this.m_PrintingPhotoListener == null) {
            return false;
        }
        return true;
    }

    public void Stop() {
        if (this.m_PrintingInfoUtility != null) {
            this.m_PrintingInfoUtility.Close();
        }
        if (this.m_PrepareSendPhoto != null) {
            this.m_PrepareSendPhoto.Stop();
        }
        if (this.m_HitiPPR_CheckPrintComplete != null) {
            this.m_HitiPPR_CheckPrintComplete.Stop();
        }
        if (this.m_HitiPPR_GetPrinterInfo != null) {
            this.m_HitiPPR_GetPrinterInfo.Stop();
        }
        if (this.m_HitiPPR_RecoveryPrinter != null) {
            this.m_HitiPPR_RecoveryPrinter.Stop();
        }
        if (this.m_HitiPPR_SendPhoto != null) {
            this.m_HitiPPR_SendPhoto.Stop();
        }
        if (this.m_PrinterHandler != null) {
            this.m_PrinterHandler.SetStop(true);
        }
    }

    public void SendPhoto() {
        if (HavePrintingPhotoListener()) {
            this.m_PrintingPhotoListener.StartCheckPrintInfo();
        }
        this.m_HitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this.m_Context, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, this.m_PrinterHandler);
        this.m_HitiPPR_GetPrinterInfo.SetRetry(false);
        this.m_HitiPPR_GetPrinterInfo.start();
    }

    public void ContinueSendPhoto() {
        StartPrint(true);
    }

    private void StartPrint(boolean boRecord) {
        if (boRecord) {
            CheckTotalFrame();
            return;
        }
        if (HavePrintingPhotoListener()) {
            this.m_PrintingPhotoListener.StartSendingPhoto(this.m_iCurrentPrintCount, this.m_iPrintCount);
        }
        this.m_iLastPrintCount = this.m_iCurrentPrintCount;
        this.m_PrepareSendPhoto = new PrepareSendPhoto();
        this.m_PrepareSendPhoto.execute(new Void[0]);
    }

    private void CheckPrintComplete(int iJobID) {
        if (HavePrintingPhotoListener()) {
            this.m_PrintingPhotoListener.StartCheckPrinting();
        }
        this.m_HitiPPR_CheckPrintComplete = new HitiPPR_CheckPrintComplete(this.m_Context, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, this.m_PrinterHandler);
        this.m_HitiPPR_CheckPrintComplete.SetCurrentPrintCount(this.m_iCurrentPrintCount);
        this.m_HitiPPR_CheckPrintComplete.SetCheckCount(this.m_iPrintCount);
        this.m_HitiPPR_CheckPrintComplete.SetLastPrintCount(this.m_iLastPrintCount);
        this.m_HitiPPR_CheckPrintComplete.SetHaveSilver(this.m_boHaveSilver);
        this.m_HitiPPR_CheckPrintComplete.SetSocket(this.m_bNextSocket);
        this.m_HitiPPR_CheckPrintComplete.start();
    }

    private void CheckTotalFrame() {
        this.LOG.m384e("CheckTotalFrame", String.valueOf("CheckTotalFrame"));
        if (this.m_strProductIDString != null) {
            this.m_HitiPPR_CheckPrinterTotalPrintedCount = new HitiPPR_CheckPrinterTotalPrintedCount(this.m_Context, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, this.m_PrinterHandler);
            this.m_HitiPPR_CheckPrinterTotalPrintedCount.SetProductID(this.m_strProductIDString);
            this.m_HitiPPR_CheckPrinterTotalPrintedCount.start();
            return;
        }
        this.m_PrinterHandler.sendEmptyMessage(RequestState.REQUEST_GET_ALBUM_ID_META);
    }

    public void ReSendingPhoto() {
        this.m_HitiPPR_RecoveryPrinter = new HitiPPR_RecoveryPrinter(this.m_Context, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, this.m_PrinterHandler);
        this.m_HitiPPR_RecoveryPrinter.start();
    }

    private void SavePrintCount() {
        PrintingInfo printingInfo = this.m_PrintingInfoUtility.GetPrintingInfoById((long) this.m_iPrintingInfoID);
        int iLastCount = 0;
        String strUploader;
        if (UploadUtility.HaveVerify(this.m_GVUserInfo)) {
            strUploader = this.m_GVUploadInfo.GetUploader();
        } else {
            strUploader = "###";
        }
        if (printingInfo.GetID() == -1) {
            String strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(3);
            this.m_GVUploadInfo.RestoreGlobalVariable();
            this.m_iPrintingInfoID = (int) this.m_PrintingInfoUtility.InsertPrintingInfo(this.m_GVUploadInfo.GetSerialNumber(), strTimeStamp, this.m_GVUploadInfo.GetUploader(), PrintingInfoUtility.ChangeMaskValueForServer(this.m_iMaskColor, this.m_boMetalEnable), 1, EncryptAndDecryptAES.MakeAESCount(this.m_Context, this.m_iPrintCount, strTimeStamp), EncryptAndDecryptAES.MakeAESCount(this.m_Context, 1, strTimeStamp), PrintingInfoUtility.ChangeFlashCardValueForServer(this.m_boFlashCard), PrinterInfo.ChangeProductIDValueForServer(this.m_strProductIDString), this.m_iTotalFrame, this.m_iPrintMode);
            return;
        }
        String strAESCount = printingInfo.GetRealCount();
        strTimeStamp = printingInfo.GetPrintingTime();
        if (!(strAESCount == null || strAESCount.equals("NULL"))) {
            iLastCount = Integer.valueOf(EncryptAndDecryptAES.OpenCount(this.m_Context, EncryptAndDecryptAES.OpenAESCount(this.m_Context, strAESCount, strTimeStamp))).intValue();
        }
        this.m_PrintingInfoUtility.UpdatePrintingInfo(this.m_iPrintingInfoID, null, null, null, null, null, null, EncryptAndDecryptAES.MakeAESCount(this.m_Context, iLastCount + 1, strTimeStamp), null, null, 0);
    }

    private void RecordPrint() {
        String strUploader;
        String strTimeStamp;
        this.m_GVUploadInfo.RestoreGlobalVariable();
        this.m_GVUserInfo.RestoreGlobalVariable();
        if (UploadUtility.HaveVerify(this.m_GVUserInfo)) {
            strUploader = this.m_GVUploadInfo.GetUploader();
        } else {
            strUploader = "###";
        }
        if (this.m_GVUploadInfo.GetAESCount().length() != 0) {
            String strAECSount = this.m_GVUploadInfo.GetAESCount();
            strTimeStamp = EncryptAndDecryptAES.OpenAESCountGetTime(this.m_Context, strAECSount, Hello.SayGoodBye(this.m_Context, 2266), Hello.SayHello(this.m_Context, 2266)) + MobileInfo.MakeRandString(3);
            strAECSount = EncryptAndDecryptAES.MakeAESCount(this.m_Context, Integer.valueOf(EncryptAndDecryptAES.OpenCount(this.m_Context, EncryptAndDecryptAES.OpenAESCount(this.m_Context, strAECSount, Hello.SayGoodBye(this.m_Context, 2266), Hello.SayHello(this.m_Context, 2266)), Hello.SayGoodBye(this.m_Context, 2266), Hello.SayHello(this.m_Context, 2266))).intValue(), strTimeStamp);
            this.m_PrintingInfoUtility.InsertPrintingInfo("999999999999", strTimeStamp, strUploader, 0, 1, strAECSount, strAECSount, 0, 1, 0, 0);
            this.m_GVUploadInfo.SetAESCount(XmlPullParser.NO_NAMESPACE);
            this.m_GVUploadInfo.SaveGlobalVariableForever();
            this.m_GVUploadInfo.RestoreGlobalVariable();
        }
        strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(3);
        String str = strTimeStamp;
        String str2 = strUploader;
        this.m_iPrintingInfoID = (int) this.m_PrintingInfoUtility.InsertPrintingInfo(this.m_GVUploadInfo.GetSerialNumber(), str, str2, PrintingInfoUtility.ChangeMaskValueForServer(this.m_iMaskColor, this.m_boMetalEnable), 1, EncryptAndDecryptAES.MakeAESCount(this.m_Context, this.m_iPrintCount, strTimeStamp), EncryptAndDecryptAES.MakeAESCount(this.m_Context, 0, strTimeStamp), PrintingInfoUtility.ChangeFlashCardValueForServer(this.m_boFlashCard), PrinterInfo.ChangeProductIDValueForServer(this.m_strProductIDString), this.m_iTotalFrame, this.m_iPrintMode);
    }

    private boolean IsFlashCard() {
        return this.m_boFlashCard;
    }

    private boolean IsMirror() {
        return this.m_boMirror;
    }

    private boolean IsMetalEnable() {
        return this.m_boMetalEnable;
    }

    private boolean Mirror(Bitmap bmp) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(bmp.getWidth(), bmp.getHeight(), Config.ARGB_8888);
        if (!bmr.IsSuccess()) {
            return false;
        }
        Canvas tempCanvas = new Canvas(bmr.GetBitmap());
        tempCanvas.save();
        tempCanvas.scale(-1.0f, 1.0f);
        tempCanvas.drawBitmap(bmp, (float) (-bmp.getWidth()), 0.0f, null);
        tempCanvas.restore();
        Canvas canvas = new Canvas(bmp);
        bmp.eraseColor(0);
        canvas.save();
        canvas.drawBitmap(bmr.GetBitmap(), 0.0f, 0.0f, null);
        canvas.restore();
        bmr.GetBitmap().recycle();
        return true;
    }
}
