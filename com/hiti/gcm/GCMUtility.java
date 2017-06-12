package com.hiti.gcm;

import android.content.Context;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import org.xmlpull.v1.XmlPullParser;

public class GCMUtility {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    APP_MODE m_APPMode;
    Context m_Context;
    GCMRegisterAppServer m_GCMRegisterAppServer;
    String m_RegId;

    class GCMRegisterAppServer extends Thread {
        private static final int BACKOFF_MILLI_SECONDS = 2000;
        private static final int MAX_ATTEMPTS = 5;
        private APP_MODE m_APPMode;
        private boolean m_boOnlyAppServer;
        private boolean m_boStop;

        public GCMRegisterAppServer(boolean boOnlyAppServer, APP_MODE APPMode) {
            this.m_boStop = false;
            this.m_boOnlyAppServer = false;
            this.m_APPMode = APP_MODE.PRINGO;
            this.m_boOnlyAppServer = boOnlyAppServer;
            this.m_APPMode = APPMode;
        }

        public void Stop() {
            this.m_boStop = true;
        }
    }

    public GCMUtility(Context context, APP_MODE APPMode) {
        this.m_Context = null;
        this.m_RegId = XmlPullParser.NO_NAMESPACE;
        this.m_GCMRegisterAppServer = null;
        this.m_APPMode = APP_MODE.PRINGO;
        this.m_Context = context;
        this.m_APPMode = APPMode;
    }
}
