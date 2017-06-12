package com.hiti.jumpinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.hiti.utility.UserInfo;
import org.xmlpull.v1.XmlPullParser;

public class JumpInfoUtility {
    public static final String GOOGLE_PLAY_PRINGO_URL = "market://search?q=pname:com.hiti.pringo";
    public static final int JUMP_ERROR_NO_APK = 1;
    public static final int JUMP_ERROR_NO_USER = 2;
    public static final int JUMP_SUCCESS = 0;
    public static final String PRINGO_PACKAGE_NAME = "com.hiti.pringo";

    public static boolean OpenPringoGooglePlay(Context context) {
        return OpenGooglePlay(context, GOOGLE_PLAY_PRINGO_URL);
    }

    public static int JumpPringoDownloadCenter(Context context) {
        if (!CheckApkExist(context, PRINGO_PACKAGE_NAME)) {
            return JUMP_ERROR_NO_APK;
        }
        String strUploader = UserInfo.GetUploader(context);
        if (strUploader.length() <= 0) {
            return JUMP_ERROR_NO_USER;
        }
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PRINGO_PACKAGE_NAME);
        Bundle bundle = new Bundle();
        bundle.putInt(JumpBundleMessage.BUNDLE_MSG_JUMP_INFO, 34);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_JUMP_INFO_UPLOADER, strUploader);
        intent.putExtras(bundle);
        context.startActivity(intent);
        return JUMP_SUCCESS;
    }

    public static int OpenPringoDownloadCenter(Context context) {
        String strAppName = PRINGO_PACKAGE_NAME;
        String strClassName = "com.hiti.pringo.DownloadCenterActivity";
        if (!CheckApkExist(context, strAppName)) {
            return JUMP_ERROR_NO_APK;
        }
        String strUploader = UserInfo.GetUploader(context);
        if (strUploader.length() <= 0) {
            return JUMP_ERROR_NO_USER;
        }
        Intent intent = new Intent();
        intent.setClassName(strAppName, strClassName);
        Bundle bundle = new Bundle();
        bundle.putInt(JumpBundleMessage.BUNDLE_MSG_JUMP_INFO, 34);
        bundle.putString(JumpBundleMessage.BUNDLE_MSG_JUMP_INFO_UPLOADER, strUploader);
        intent.putExtras(bundle);
        context.startActivity(intent);
        return JUMP_SUCCESS;
    }

    public static boolean JumpPringoActivity(Context context, Bundle bundle) {
        if (!CheckApkExist(context, PRINGO_PACKAGE_NAME)) {
            return false;
        }
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PRINGO_PACKAGE_NAME);
        intent.putExtras(bundle);
        context.startActivity(intent);
        return true;
    }

    public static boolean OpenGooglePlay(Context context, String url) {
        try {
            if (url.startsWith("market://")) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean CheckApkExist(Context context, String packageName) {
        if (packageName == null || XmlPullParser.NO_NAMESPACE.equals(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }
}
