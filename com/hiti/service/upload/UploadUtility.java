package com.hiti.service.upload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import com.hiti.HitiChunk.HitiChunk.ChunkType;
import com.hiti.HitiChunk.HitiChunkUtility;
import com.hiti.sql.printerInfo.PrintingInfoUtility;
import com.hiti.trace.GlobalVariable_OtherSetting;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.utility.FileUtility;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.wifi.WifiSetting;
import org.xmlpull.v1.XmlPullParser;

public class UploadUtility {
    public static boolean NeedUploadPhoto(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        if (GVUploadInfo.GetUpload() < 1) {
            return false;
        }
        return true;
    }

    public static boolean AddUploadPhoto(Context context, Bitmap editBmp, Bitmap maskBmp) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        if (GVUploadInfo.GetUpload() < 1) {
            return false;
        }
        String strSaveFolderPath = FileUtility.GetSDAppRootPath(context) + "/print";
        String strUploadPath = strSaveFolderPath + "/" + FileUtility.GetNewNameWithExt(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG, XmlPullParser.NO_NAMESPACE);
        FileUtility.CreateFolder(strSaveFolderPath);
        if (editBmp == null || !FileUtility.SaveBitmap(strUploadPath, editBmp, CompressFormat.JPEG)) {
            return false;
        }
        if (maskBmp != null) {
            HitiChunkUtility.AddHiTiChunk(strUploadPath, maskBmp, ChunkType.JPG);
        }
        GVUploadInfo.AddUploadFile(strUploadPath, MobileInfo.GetTimeStamp());
        GVUploadInfo.SaveGlobalVariableForever();
        return true;
    }

    public static boolean HaveVerify(GlobalVariable_UserInfo GVUserInfo) {
        if (GVUserInfo.GetVerify() == 1) {
            return true;
        }
        return false;
    }

    public static boolean HaveUploadE03(GlobalVariable_UploadInfo GVUploadInfo) {
        if (GVUploadInfo.GetUploadE03() == 1) {
            return true;
        }
        return false;
    }

    public static boolean HaveWifiUploadMethod(Context context, GlobalVariable_UploadInfo GVUploadInfo) {
        if (GVUploadInfo.GetUploadMethod() != 0 || WifiSetting.IsWifiConnected(context)) {
            return false;
        }
        return true;
    }

    public static boolean HaveWifiLoadADMethod(Context context, GlobalVariable_OtherSetting GVOtherSetting) {
        if (GVOtherSetting.GetLoadADMethod() != 0 || WifiSetting.IsWifiConnected(context)) {
            return false;
        }
        return true;
    }

    public static boolean HaveUploadCount(PrintingInfoUtility piu) {
        if (piu.GetPrintingInfoFirst().GetID() != -1) {
            return true;
        }
        return false;
    }

    public static boolean HaveCanUpload(GlobalVariable_UploadInfo GVUploadInfo) {
        if (GVUploadInfo.GetUpload() == 1) {
            return true;
        }
        return false;
    }

    public static boolean HaveUploadPhoto(GlobalVariable_UploadInfo GVUploadInfo) {
        Log.d("HaveUploadPhoto", "GetUploadFileSize: " + GVUploadInfo.GetUploadFileSize());
        if (GVUploadInfo.GetUploadFileSize() > 0) {
            return true;
        }
        return false;
    }
}
