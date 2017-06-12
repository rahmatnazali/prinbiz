package com.hiti.utility.Install;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.hiti.utility.FileUtility;
import com.hiti.utility.ZipUtility;
import java.io.IOException;

public abstract class ZipAndCopyInstaller extends AsyncTask<String, Integer, Object> {
    protected Context m_Context;
    private ZIP_AND_COPY_INSTALLER_ERROR m_LastError;
    private boolean m_boInstallFail;
    private String m_strTargetFolderPath;
    private String m_strUnZipFolderPath;
    private String m_strZipPath;

    public abstract boolean CustomInstall(String str);

    public abstract void DeleteTemp();

    public abstract void InstallFail(ZIP_AND_COPY_INSTALLER_ERROR zip_and_copy_installer_error);

    public abstract void InstallSuccess(String str);

    public ZipAndCopyInstaller(Context context) {
        this.m_Context = null;
        this.m_strZipPath = null;
        this.m_strUnZipFolderPath = null;
        this.m_strTargetFolderPath = null;
        this.m_LastError = null;
        this.m_boInstallFail = false;
        this.m_Context = context;
    }

    public void SetInstallInfo(String strZipPath, String strUnZipFolderPath, String strTargetFolderPath) {
        this.m_strZipPath = strZipPath;
        this.m_strUnZipFolderPath = strUnZipFolderPath;
        this.m_strTargetFolderPath = strTargetFolderPath;
        this.m_boInstallFail = false;
    }

    protected Object doInBackground(String... arg0) {
        if (UnZip()) {
            if (!CustomInstall(this.m_strUnZipFolderPath)) {
                Log.e("HAHAHA", "HAHAHA");
                SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_COPY_FILE);
            } else if (CopyFile()) {
                DeleteTemp();
            }
        }
        return null;
    }

    protected void SetLastError(ZIP_AND_COPY_INSTALLER_ERROR lastError) {
        this.m_boInstallFail = true;
        this.m_LastError = lastError;
    }

    public ZIP_AND_COPY_INSTALLER_ERROR GetLastError() {
        return this.m_LastError;
    }

    private boolean UnZip() {
        if (ZipUtility.UnpackZip(this.m_strUnZipFolderPath, this.m_strZipPath)) {
            return true;
        }
        SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_UNZIP);
        return false;
    }

    public boolean CopyFile() {
        try {
            FileUtility.CopyDirectory(this.m_strUnZipFolderPath, this.m_strTargetFolderPath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_COPY_FILE);
            return false;
        }
    }

    protected void onPostExecute(Object result) {
        if (this.m_boInstallFail) {
            InstallFail(GetLastError());
        } else {
            InstallSuccess(this.m_strTargetFolderPath);
        }
    }
}
