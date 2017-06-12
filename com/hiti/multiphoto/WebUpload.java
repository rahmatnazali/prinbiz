package com.hiti.multiphoto;

import android.content.Context;
import android.os.AsyncTask;
import com.hiti.jni.hello.Hello;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.Timers;
import com.hiti.utility.Timers.ITimeListener;
import com.hiti.web.HitiWebPath;
import com.hiti.web.WebPostRequest;
import com.hiti.web.WebRequestNew;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public abstract class WebUpload extends AsyncTask<String, Integer, WEB_UPLOAD_ERROR> {
    private LogManager LOG;
    private String TAG;
    private int TIME_OUT;
    private TimerListener mTimeListener;
    private Timers mTimer;
    private Context m_Context;
    private boolean mbCanceled;
    private boolean mbTimeout;
    private String strNowPath;

    class TimerListener implements ITimeListener {
        TimerListener() {
        }

        public void TimeOut() {
        }

        public void StopThread() {
            WebUpload.this.LOG.m385i(WebUpload.this.TAG, "StopThread: TimeOut");
            WebUpload.this.mbTimeout = true;
            WebUpload.this.cancel(true);
        }
    }

    public abstract void RemoveRecord(String str);

    public abstract void UploadFail(WEB_UPLOAD_ERROR web_upload_error, String str);

    public abstract void UploadSuccess(String str);

    public WebUpload(Context context) {
        this.m_Context = null;
        this.TAG = null;
        this.LOG = null;
        this.strNowPath = null;
        this.mTimer = null;
        this.TIME_OUT = 30000;
        this.mTimeListener = null;
        this.mbCanceled = false;
        this.mbTimeout = false;
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.TAG = "WebUpload";
        this.LOG.m385i(this.TAG, "WebUpload TAG =" + String.valueOf(this.TAG));
        this.mTimeListener = new TimerListener();
        this.mTimer = new Timers(null, this.mTimeListener);
    }

    protected void onPreExecute() {
    }

    protected WEB_UPLOAD_ERROR doInBackground(String... pair) {
        return UploadPhoto(pair[0], pair[1], pair[2], pair[3]);
    }

    protected void onPostExecute(WEB_UPLOAD_ERROR result) {
        this.LOG.m385i(this.TAG, "onPostExecute " + result);
        if (result == WEB_UPLOAD_ERROR.UPLOAD_SUCCESS) {
            UploadSuccess(this.strNowPath);
        } else {
            UploadFail(result, this.strNowPath);
        }
    }

    protected void onCancelled() {
        WEB_UPLOAD_ERROR error = this.mbTimeout ? WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL : WEB_UPLOAD_ERROR.UPLOAD_CANCEL;
        this.LOG.m385i(this.TAG, "onCancelled " + error);
        UploadFail(error, this.strNowPath);
        super.onCancelled();
    }

    public void UploadCancel() {
        this.LOG.m385i(this.TAG, "UploadCancel");
        this.mbCanceled = true;
        cancel(true);
    }

    private WEB_UPLOAD_ERROR UploadPhoto(String strU, String strP, String path, String albumPID) {
        this.strNowPath = path;
        if (strU == null || strP == null) {
            return WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL;
        }
        String strUploadPath = path;
        String strUploadTime = MobileInfo.GetTimeStamp();
        if (strUploadPath == null) {
            return WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL;
        }
        if (FileUtility.FileExist(strUploadPath)) {
            String albumID = albumPID;
            this.LOG.m385i(this.TAG, "UploadPhoto strU:\n" + strU);
            this.LOG.m385i(this.TAG, "UploadPhoto strP:\n" + strP);
            String value = EncryptAndDecryptAES.EncryptStr(strU + PringoConvenientConst.DATE_TO_DATE_2 + strP + PringoConvenientConst.DATE_TO_DATE_2 + strUploadTime + PringoConvenientConst.DATE_TO_DATE_2 + MobileInfo.GetIMEI(this.m_Context) + PringoConvenientConst.DATE_TO_DATE_2, Hello.SayGoodBye(this.m_Context, 1289), Hello.SayHello(this.m_Context, 1289));
            Map<String, String> paramlist = new HashMap();
            paramlist.clear();
            paramlist.put("option1", value);
            paramlist.put("album_pid", String.valueOf(albumID));
            WebRequestNew mWebRequestNew = new WebRequestNew();
            mWebRequestNew.SetImageData(this.m_Context, paramlist, strUploadPath);
            this.mTimer.Start(this.TIME_OUT);
            String strResponse = mWebRequestNew.UploadPhotoURLConnection(HitiWebPath.WEB_REQUEST_CLOUD_ALBUM_UPLOAD, paramlist);
            this.LOG.m385i(this.TAG, "PostGetResponse done mbCanceled:" + this.mbCanceled);
            this.mTimer.Stop(true);
            String retCode = new WebPostRequest().GetResponseCode(strResponse);
            this.LOG.m385i(this.TAG, "PostGetResponse retCode:" + retCode);
            if (retCode == null || !retCode.equals("s01")) {
                return WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL;
            }
            paramlist.clear();
            if (this.mbTimeout) {
                return WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL;
            }
            RemoveRecord(retCode);
            if (this.mbCanceled) {
                return WEB_UPLOAD_ERROR.UPLOAD_CANCEL;
            }
            return WEB_UPLOAD_ERROR.UPLOAD_SUCCESS;
        }
        RemoveRecord(XmlPullParser.NO_NAMESPACE);
        return WEB_UPLOAD_ERROR.UPLOAD_INTERNET_FAIL;
    }
}
