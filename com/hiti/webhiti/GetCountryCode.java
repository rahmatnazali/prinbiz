package com.hiti.webhiti;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import com.hiti.trace.GlobalVariable_AppInfo;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.Timers;
import com.hiti.utility.Timers.ITimeListener;
import com.hiti.web.HitiWebPath;
import com.hiti.web.WebPostRequest;
import com.hiti.web.WebRequestNew;
import java.util.HashMap;
import java.util.Map;

public class GetCountryCode {
    private static final int TIME_OUT = 3000;
    LogManager LOG;
    String TAG;
    GetCountryCodeThread mGetCountryCodeThread;
    Handler mHandler;
    Timers mTimer;
    Context m_Context;
    IGetCountryCodeListener m_GetCountryCodeListenter;
    boolean m_bRun;
    int m_iAppMode;

    class GetCountryCodeThread extends Thread {

        /* renamed from: com.hiti.webhiti.GetCountryCode.GetCountryCodeThread.1 */
        class C04961 implements Runnable {
            final /* synthetic */ String val$strCountryCode;

            C04961(String str) {
                this.val$strCountryCode = str;
            }

            public void run() {
                if (GetCountryCode.this.m_GetCountryCodeListenter != null) {
                    GetCountryCode.this.m_GetCountryCodeListenter.GetCountryCodeFinish(this.val$strCountryCode);
                }
            }
        }

        GetCountryCodeThread() {
        }

        public void run() {
            GetCountryCode.this.mTimer = new Timers(GetCountryCode.this.mHandler, new TimerListener());
            GetCountryCode.this.mTimer.Start(GetCountryCode.TIME_OUT);
            GetCountryCode.this.m_bRun = true;
            String strCountryCode = GetCountryCode.this.GetCountryCodePost();
            GetCountryCode.this.mTimer.Stop(true);
            if (GetCountryCode.this.m_bRun && strCountryCode != null) {
                GetCountryCode.this.mHandler.post(new C04961(strCountryCode));
            }
        }
    }

    public interface IGetCountryCodeListener {
        void GetCountryCodeFinish(String str);

        void Timeout();
    }

    class TimerListener implements ITimeListener {
        TimerListener() {
        }

        public void TimeOut() {
            if (GetCountryCode.this.m_GetCountryCodeListenter != null) {
                GetCountryCode.this.m_GetCountryCodeListenter.Timeout();
            }
        }

        public void StopThread() {
            GetCountryCode.this.Stop();
        }
    }

    public GetCountryCode(Context context) {
        this.m_Context = null;
        this.m_GetCountryCodeListenter = null;
        this.mGetCountryCodeThread = null;
        this.m_iAppMode = 0;
        this.m_bRun = false;
        this.mHandler = null;
        this.mTimer = null;
        this.LOG = null;
        this.TAG = "GetCountryCode";
        this.m_Context = context;
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(this.m_Context);
        GVAppInfo.RestoreGlobalVariable();
        this.m_iAppMode = GVAppInfo.GetAppMode();
        this.mHandler = new Handler();
        this.LOG = new LogManager(0);
    }

    public void SetListener(IGetCountryCodeListener listner) {
        this.m_GetCountryCodeListenter = listner;
    }

    public void Start() {
        if (!this.m_bRun) {
            this.mGetCountryCodeThread = new GetCountryCodeThread();
            this.mGetCountryCodeThread.start();
        }
    }

    public void Stop() {
        this.m_bRun = false;
        if (this.mGetCountryCodeThread != null) {
            this.mGetCountryCodeThread.interrupt();
        }
    }

    private String GetCountryCodePost() {
        this.LOG.m385i(this.TAG, "GetCountryCodePost request: https://pringo.hiti.com/mobile/api/getWebInfo/");
        Map<String, String> paramlist = new HashMap();
        Location location = MobileInfo.GetLocation(this.m_Context, false);
        String strLatitude = null;
        String strLongitude = null;
        if (location != null) {
            strLatitude = String.valueOf(location.getLatitude());
            strLongitude = String.valueOf(location.getLongitude());
        }
        if (strLatitude == null || strLongitude == null) {
            paramlist.put("latitude", String.valueOf(-1));
            paramlist.put("longitude", String.valueOf(-1));
        } else {
            paramlist.put("latitude", strLatitude);
            paramlist.put("longitude", strLongitude);
        }
        paramlist.put("app", String.valueOf(this.m_iAppMode));
        paramlist.put("platform", String.valueOf(2));
        String strResponse = new WebRequestNew().PostByURLConnection(HitiWebPath.WEB_REQUEST_GET_COUNTRYCODE, paramlist);
        this.LOG.m385i(this.TAG, "GetResponse: " + strResponse);
        String retStr = new WebPostRequest().GetResponseVersion(strResponse);
        paramlist.clear();
        if (retStr == null || retStr.split(":").length != 2) {
            return null;
        }
        return retStr.split(":")[0];
    }
}
