package com.hiti.patch;

import android.content.Context;
import android.support.v4.view.PointerIconCompat;
import com.hiti.jni.hello.Hello;
import com.hiti.jscommand.JSCommand;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.trace.GlobalVariable_PatchInfo;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.FileUtility;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import org.xmlpull.v1.XmlPullParser;

public class HitiPatch {
    public static void Patch001(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        try {
            if (GVUploadInfo.GetVersion() == 0) {
                String strEUploader = GVUploadInfo.GetUploader();
                if (UserInfo.HaveUpLoader(strEUploader)) {
                    String strUploader = XmlPullParser.NO_NAMESPACE;
                    strEUploader = EncryptAndDecryptAES.DecryptStr(strEUploader, Hello.SayGoodBye(context, 604), Hello.SayHello(context, 604));
                    if (strEUploader == null) {
                        ClearUser(GVUploadInfo, GVUserInfo);
                        return;
                    }
                    strUploader = strEUploader.replace(MobileInfo.GetIMEI(context), XmlPullParser.NO_NAMESPACE);
                    if (strEUploader.length() == strUploader.length()) {
                        ClearUser(GVUploadInfo, GVUserInfo);
                        return;
                    }
                    JSCommand jsCommand = new JSCommand(context);
                    String strUP = jsCommand.ParseUP(strUploader, Hello.SayGoodBye(context, PointerIconCompat.TYPE_ALL_SCROLL), Hello.SayHello(context, PointerIconCompat.TYPE_ALL_SCROLL));
                    String strU = jsCommand.GetU(strUP);
                    String strP = jsCommand.GetP(strUP);
                    if (UserInfo.HaveUP(strU, strP)) {
                        strUploader = EncryptAndDecryptAES.EncryptStrNoPadding(strU + JSCommand.UP + strP, Hello.SayGoodBye(context, 7894), Hello.SayHello(context, 7894));
                        if (strUploader == null) {
                            ClearUser(GVUploadInfo, GVUserInfo);
                            return;
                        }
                        strEUploader = EncryptAndDecryptAES.EncryptStr(OADCItem.WATCH_TYPE_WATCH + strUploader + MobileInfo.GetIMEI(context), Hello.SayGoodBye(context, 7895), Hello.SayHello(context, 7895));
                        if (strEUploader == null) {
                            ClearUser(GVUploadInfo, GVUserInfo);
                            return;
                        }
                        GVUploadInfo.SetUploader(strEUploader);
                        GVUploadInfo.SetVersion(1);
                        GVUploadInfo.SetUploadE03(0);
                        GVUploadInfo.SaveGlobalVariableForever();
                        return;
                    }
                    ClearUser(GVUploadInfo, GVUserInfo);
                    return;
                }
                ClearUser(GVUploadInfo, GVUserInfo);
            }
        } catch (Exception e) {
            ClearUser(GVUploadInfo, GVUserInfo);
            e.printStackTrace();
        }
    }

    public static boolean Patch002(Context context) {
        GlobalVariable_PatchInfo GVPatchInfo = new GlobalVariable_PatchInfo(context);
        GVPatchInfo.RestoreGlobalVariable();
        if (GVPatchInfo.GetPatch002() != 0) {
            return false;
        }
        String strBorderPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH;
        String strBorderCatalogPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH + "/" + PringoConvenientConst.CATTHUMB;
        if (!FileUtility.FileExist(strBorderPath) || FileUtility.FileExist(strBorderCatalogPath)) {
            return false;
        }
        FileUtility.DeleteALLFolder(FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH);
        FileUtility.DeleteALLFolder(FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.H_BORDER_PATH);
        GVPatchInfo.SetPatch002(1);
        GVPatchInfo.SaveGlobalVariableForever();
        return true;
    }

    private static void ClearUser(GlobalVariable_UploadInfo GVUploadInfo, GlobalVariable_UserInfo GVUserInfo) {
        GVUploadInfo.SetUploader(XmlPullParser.NO_NAMESPACE);
        GVUploadInfo.SetVersion(1);
        GVUploadInfo.SaveGlobalVariableForever();
        GVUserInfo.SetVerify(0);
        GVUserInfo.SaveGlobalVariableForever();
    }
}
