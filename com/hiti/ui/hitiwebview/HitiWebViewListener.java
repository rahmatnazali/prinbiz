package com.hiti.ui.hitiwebview;

import android.webkit.JsResult;
import com.hiti.utility.Install.ZIP_AND_COPY_INSTALLER_ERROR;
import com.hiti.web.download.WEB_DOWNLOAD_ERROR;

public abstract class HitiWebViewListener {
    public abstract void DownloadCancel(String str);

    public abstract void DownloadFail(String str, WEB_DOWNLOAD_ERROR web_download_error);

    public abstract void DownloadProgress(String str, int i);

    public abstract void DownloadSuccess(String str, long j);

    public abstract void InstallFail(ZIP_AND_COPY_INSTALLER_ERROR zip_and_copy_installer_error);

    public abstract void InstallSuccess(String str);

    public abstract void OnPageFinished(String str);

    public abstract void OnPageStarted(String str);

    public abstract void OnReceivedError(int i, String str, String str2);

    public abstract void OnUrlChange(String str);

    public abstract void StartDefaultBrowser(String str);

    public abstract void StartDownloadCenter();

    public abstract void StartDownloadCloudPhoto(String str, String str2, int i);

    public abstract void StartDownloadStamp(String str, String str2, int i);

    public abstract void StartInAppBillingList();

    public abstract void StartInstall();

    public abstract void StartLoadCloudAlbum();

    public abstract void StartLoadDownloadCenter();

    public abstract void StartLoadEDM();

    public abstract void StartLoadMemberInfo();

    public abstract void StartLoadPreferential();

    public abstract void StartLoadRewardPointsQurey();

    public abstract void StartLoadVerify();

    public abstract void StartMailto(String str);

    public abstract void StartMember();

    public abstract void StartVideoView(String str, String str2, String str3, String str4);

    public abstract void UploadPhotoToCloudAlbum(String str);

    public abstract void UserLogin();

    public abstract void UserLogout();

    public abstract void onJsAlert(String str, String str2, JsResult jsResult);

    public abstract void onJsConfirm(String str, String str2, JsResult jsResult);
}
