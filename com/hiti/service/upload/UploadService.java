package com.hiti.service.upload;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.hiti.AppInfo.AppInfo;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.trace.GlobalVariable_AppInfo;

public class UploadService extends Service {
    final IBinder mLocalBinder;
    private ServiceHandler m_ServiceHandler;
    UploadThread m_UploadThread;
    int m_iPrintedRecord;
    String m_strPrintOut;
    String m_strProductID;

    public class LocalBinder extends Binder {
        public UploadService getService() {
            return UploadService.this;
        }
    }

    class PrinfanUploadThread extends UploadThread {
        public PrinfanUploadThread(ServiceHandler handler, Context context) {
            super(handler, context);
        }
    }

    public UploadService() {
        this.m_ServiceHandler = null;
        this.m_UploadThread = null;
        this.m_strProductID = null;
        this.m_strPrintOut = null;
        this.m_iPrintedRecord = -1;
        this.mLocalBinder = new LocalBinder();
    }

    public void onCreate() {
        super.onCreate();
        if (this.m_ServiceHandler == null) {
            this.m_ServiceHandler = new ServiceHandler(this);
        }
        if (this.m_UploadThread == null) {
            this.m_UploadThread = new PrinfanUploadThread(this.m_ServiceHandler, getApplicationContext());
            this.m_UploadThread.start();
        }
    }

    public void SetPrinterListener(IPrinterListner mFWListener) {
        this.m_ServiceHandler.SetPrinterListener(mFWListener);
    }

    public IBinder onBind(Intent intent) {
        return this.mLocalBinder;
    }

    public void onDestroy() {
        if (this.m_ServiceHandler != null) {
            this.m_ServiceHandler.SetStop(true);
        }
        if (this.m_UploadThread != null) {
            this.m_UploadThread.SetStop(true);
        }
        super.onDestroy();
    }

    public static void StartUploadService(Context packageContext, Class<?> cls, APP_MODE appMode) {
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(packageContext);
        GVAppInfo.RestoreGlobalVariable();
        GVAppInfo.SetAppMode(AppInfo.GetAppModeNumber(appMode));
        GVAppInfo.SaveGlobalVariableForever();
        packageContext.startService(new Intent(packageContext, cls));
    }

    public static void StopUploadService(Context packageContext, Class<?> cls) {
        packageContext.stopService(new Intent(packageContext, cls));
    }
}
