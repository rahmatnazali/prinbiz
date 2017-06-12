package com.hiti.ui.advideoview;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.VideoView;
import com.hiti.AppInfo.AppInfo;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.jni.hello.Hello;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.LogManager;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import com.hiti.web.HitiWebPath;
import com.hiti.web.WebRequestNew;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ADVideoView extends RelativeLayout {
    private LogManager LOG;
    private ADVideoViewListener m_ADVideoViewListener;
    private APP_MODE m_APPMode;
    Context m_Context;
    private VideoView m_ExternalVideoView;
    private ProgressBar m_ExternalVideoViewLoadProgressBar;
    private PostADPoint m_PostADPoint;
    private String m_strCountry;
    private String m_strID;
    private String m_strPath;
    private String m_strPoint;

    /* renamed from: com.hiti.ui.advideoview.ADVideoView.1 */
    class C04201 implements OnPreparedListener {

        /* renamed from: com.hiti.ui.advideoview.ADVideoView.1.1 */
        class C04191 implements OnInfoListener {
            C04191() {
            }

            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == 701) {
                    if (ADVideoView.this.HaveADVideoViewListener()) {
                        ADVideoView.this.m_ADVideoViewListener.Loading();
                    }
                    ADVideoView.this.m_ExternalVideoViewLoadProgressBar.setVisibility(0);
                } else if (what == 702) {
                    if (ADVideoView.this.HaveADVideoViewListener()) {
                        ADVideoView.this.m_ADVideoViewListener.StopLoading();
                    }
                    ADVideoView.this.m_ExternalVideoViewLoadProgressBar.setVisibility(8);
                }
                return false;
            }
        }

        C04201() {
        }

        public void onPrepared(MediaPlayer mp) {
            ADVideoView.this.LOG.m384e("m_ExternalVideoViewLoadProgressBar", "Changed");
            ADVideoView.this.m_ExternalVideoViewLoadProgressBar.setVisibility(8);
            if (ADVideoView.this.HaveADVideoViewListener()) {
                ADVideoView.this.m_ADVideoViewListener.StartLoading();
            }
            if (VERSION.SDK_INT >= 9) {
                mp.setOnInfoListener(new C04191());
            }
        }
    }

    /* renamed from: com.hiti.ui.advideoview.ADVideoView.2 */
    class C04212 implements OnCompletionListener {
        C04212() {
        }

        public void onCompletion(MediaPlayer mp) {
            Log.e("onCompletion", "onCompletion");
            if (ADVideoView.this.HaveADVideoViewListener()) {
                ADVideoView.this.m_ADVideoViewListener.StartNotifyServer();
            }
            if (ADVideoView.this.m_PostADPoint != null) {
                ADVideoView.this.m_PostADPoint.Stop();
            }
            ADVideoView.this.m_PostADPoint = new PostADPoint(ADVideoView.this.m_Context);
            ADVideoView.this.m_PostADPoint.execute(new Object[]{XmlPullParser.NO_NAMESPACE});
        }
    }

    /* renamed from: com.hiti.ui.advideoview.ADVideoView.3 */
    class C04223 implements OnErrorListener {
        C04223() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            ADVideoView.this.LOG.m385i("setOnErrorListener", "setOnErrorListener");
            switch (what) {
            }
            if (ADVideoView.this.HaveADVideoViewListener()) {
                ADVideoView.this.m_ADVideoViewListener.Error(what);
            }
            return true;
        }
    }

    class PostADPoint extends AsyncTask<Object, Object, Boolean> {
        private Context m_Context;
        public boolean m_boStop;

        PostADPoint(Context context) {
            this.m_Context = null;
            this.m_boStop = false;
            this.m_Context = context;
        }

        protected Boolean doInBackground(Object... arg0) {
            GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
            GVUploadInfo.RestoreGlobalVariable();
            Pair<String, String> pair = UserInfo.GetUP(this.m_Context, GVUploadInfo.GetUploader());
            String strU = "000000000";
            String strP = "111111111";
            if (pair != null) {
                strU = pair.first;
                strP = pair.second;
            }
            Log.e("doInBackground", "App_mode=" + String.valueOf(ADVideoView.this.m_APPMode));
            String value = EncryptAndDecryptAES.MakeGoodString(EncryptAndDecryptAES.EncryptStr(strU + PringoConvenientConst.DATE_TO_DATE_2 + strP + PringoConvenientConst.DATE_TO_DATE_2 + ADVideoView.this.m_strID + PringoConvenientConst.DATE_TO_DATE_2 + "2" + PringoConvenientConst.DATE_TO_DATE_2 + ADVideoView.this.m_strCountry + PringoConvenientConst.DATE_TO_DATE_2 + String.valueOf(AppInfo.GetAppModeNumber(ADVideoView.this.m_APPMode)) + PringoConvenientConst.DATE_TO_DATE_2, Hello.SayGoodBye(this.m_Context, 7788), Hello.SayHello(this.m_Context, 7788)));
            Map<String, String> paramlist = new HashMap();
            paramlist.put("data2", value);
            ADVideoView.this.LOG.m384e("data2", value);
            return Boolean.valueOf(new WebRequestNew().Post(HitiWebPath.WEB_REQUEST_ACQUIRE_POINT, paramlist));
        }

        protected void onPostExecute(Boolean result) {
            if (!this.m_boStop) {
                if (result.booleanValue()) {
                    if (ADVideoView.this.HaveADVideoViewListener()) {
                        ADVideoView.this.m_ADVideoViewListener.NotifyServerSuccess(ADVideoView.this.m_strPoint);
                    }
                } else if (ADVideoView.this.HaveADVideoViewListener()) {
                    ADVideoView.this.m_ADVideoViewListener.NotifyServerFail();
                }
            }
        }

        public void Stop() {
            this.m_boStop = true;
        }
    }

    public ADVideoView(Context context) {
        super(context);
        this.m_ExternalVideoView = null;
        this.m_ExternalVideoViewLoadProgressBar = null;
        this.m_ADVideoViewListener = null;
        this.m_PostADPoint = null;
        this.m_strPath = XmlPullParser.NO_NAMESPACE;
        this.m_strID = XmlPullParser.NO_NAMESPACE;
        this.m_strCountry = XmlPullParser.NO_NAMESPACE;
        this.m_strPoint = XmlPullParser.NO_NAMESPACE;
        this.m_APPMode = APP_MODE.PRINGO;
        this.LOG = null;
        this.m_Context = context;
        SetupExternalVideoView();
        this.LOG = new LogManager(0);
    }

    public ADVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_ExternalVideoView = null;
        this.m_ExternalVideoViewLoadProgressBar = null;
        this.m_ADVideoViewListener = null;
        this.m_PostADPoint = null;
        this.m_strPath = XmlPullParser.NO_NAMESPACE;
        this.m_strID = XmlPullParser.NO_NAMESPACE;
        this.m_strCountry = XmlPullParser.NO_NAMESPACE;
        this.m_strPoint = XmlPullParser.NO_NAMESPACE;
        this.m_APPMode = APP_MODE.PRINGO;
        this.LOG = null;
        this.m_Context = context;
        SetupExternalVideoView();
        this.LOG = new LogManager(0);
    }

    private void SetupExternalVideoView() {
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.addRule(13);
        lp.addRule(10);
        lp.addRule(12);
        lp.addRule(5);
        this.m_ExternalVideoView = new VideoView(this.m_Context);
        this.m_ExternalVideoView.setLayoutParams(lp);
        addView(this.m_ExternalVideoView);
        LayoutParams lpp = new LayoutParams(-2, -2);
        lpp.addRule(13);
        this.m_ExternalVideoViewLoadProgressBar = new ProgressBar(this.m_Context, null, 16842874);
        this.m_ExternalVideoViewLoadProgressBar.setLayoutParams(lpp);
        addView(this.m_ExternalVideoViewLoadProgressBar);
        this.m_ExternalVideoView.setOnPreparedListener(new C04201());
        this.m_ExternalVideoView.setOnCompletionListener(new C04212());
        this.m_ExternalVideoView.setOnErrorListener(new C04223());
        this.m_ExternalVideoView.requestFocus();
    }

    public void Stop() {
        this.m_ExternalVideoView.stopPlayback();
        if (this.m_PostADPoint != null) {
            this.m_PostADPoint.Stop();
        }
    }

    private boolean HaveADVideoViewListener() {
        if (this.m_ADVideoViewListener == null) {
            return false;
        }
        return true;
    }

    public void SetADVideoViewListener(ADVideoViewListener Listener) {
        this.m_ADVideoViewListener = Listener;
    }

    public void ShowExternalVideoView(String strPath, String strID, String strCountry, String strPoint, APP_MODE appMode) {
        Log.e("ShowExternalVideoView", "App_mode=" + String.valueOf(appMode));
        this.m_strPath = strPath;
        this.m_strID = strID;
        this.m_strCountry = strCountry;
        this.m_strPoint = strPoint;
        this.m_APPMode = appMode;
        this.m_ExternalVideoView.setVideoURI(Uri.parse(this.m_strPath));
        this.m_ExternalVideoView.start();
    }
}
