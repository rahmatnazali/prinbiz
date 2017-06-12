package com.hiti.webhiti;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.hiti.jni.hello.Hello;
import com.hiti.utility.UserInfo;
import com.hiti.web.WebRequestNew;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CheckIfVerify {
    CheckIfVerifiedHandler m_CheckIfVerifiedHandler;
    CheckIfVerifiedThread m_CheckIfVerifiedThread;
    Context m_Context;
    boolean m_bRun;
    String m_strApiUrl;

    class CheckIfVerifiedHandler extends Handler {
        CheckIfVerifiedHandler() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    class CheckIfVerifiedThread extends Thread {
        CheckIfVerifiedThread() {
        }

        public void run() {
            CheckIfVerify.this.m_bRun = true;
            if (CheckIfVerify.this.m_bRun) {
                CheckIfVerify.this.CheckIfVerified(CheckIfVerify.this.m_strApiUrl);
            }
            CheckIfVerify.this.m_bRun = false;
            CheckIfVerify.this.m_CheckIfVerifiedHandler.removeCallbacks(CheckIfVerify.this.m_CheckIfVerifiedThread);
        }
    }

    public CheckIfVerify(Context context) {
        this.m_Context = null;
        this.m_CheckIfVerifiedThread = null;
        this.m_CheckIfVerifiedHandler = null;
        this.m_bRun = false;
        this.m_strApiUrl = null;
        this.m_Context = context;
        this.m_CheckIfVerifiedHandler = new CheckIfVerifiedHandler();
    }

    public void StartCheckIfVerified(String strApiUrl) {
        this.m_strApiUrl = strApiUrl;
        if (!this.m_bRun) {
            this.m_CheckIfVerifiedThread = new CheckIfVerifiedThread();
            this.m_CheckIfVerifiedThread.start();
        }
    }

    public void SetStop(boolean bRet) {
        this.m_bRun = bRet;
    }

    private boolean CheckIfVerified(String strApiUrl) {
        if (this.m_Context == null || !this.m_bRun) {
            return false;
        }
        Map<String, String> paramlist = new HashMap();
        paramlist.put("data", UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998), false));
        String retStr = GetResponseCode(new WebRequestNew().PostByURLConnection(strApiUrl, paramlist));
        paramlist.clear();
        if (retStr == null || !retStr.equals("OK")) {
            return false;
        }
        return true;
    }

    public String GetResponseCode(String strResponse) {
        String str = null;
        if (strResponse != null) {
            try {
                NodeList nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strResponse.getBytes())).getDocumentElement().getElementsByTagName("res");
                Element resElement = null;
                if (nodes.getLength() > 0) {
                    resElement = (Element) nodes.item(0);
                }
                if (resElement != null) {
                    str = resElement.getAttribute("code");
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return str;
    }
}
