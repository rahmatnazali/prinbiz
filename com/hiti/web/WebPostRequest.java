package com.hiti.web;

import android.content.Context;
import com.hiti.utility.LogManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WebPostRequest {
    LogManager LOG;
    String TAG;
    private Context m_Context;

    public WebPostRequest() {
        this.m_Context = null;
        this.LOG = null;
        this.TAG = null;
    }

    public String GetResponseVersion(String strResponse) {
        String str = null;
        if (strResponse != null) {
            try {
                NodeList nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strResponse.getBytes())).getDocumentElement().getElementsByTagName("res");
                Element resElement = null;
                if (nodes.getLength() > 0) {
                    resElement = (Element) nodes.item(0);
                }
                if (resElement != null) {
                    str = resElement.getAttribute("countrycode") + ":" + resElement.getAttribute("version");
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

    public String GetResponseMSG(String strResponse) {
        String str = null;
        if (strResponse != null) {
            try {
                NodeList nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strResponse.getBytes())).getDocumentElement().getElementsByTagName("res");
                Element resElement = null;
                if (nodes.getLength() > 0) {
                    resElement = (Element) nodes.item(0);
                }
                if (resElement != null) {
                    str = resElement.getAttribute(NotificationCompatApi24.CATEGORY_MESSAGE);
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

    public boolean Success(String strResponse) {
        try {
            NodeList nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(strResponse.getBytes())).getDocumentElement().getElementsByTagName("res");
            Element resElement = null;
            if (nodes.getLength() > 0) {
                resElement = (Element) nodes.item(0);
            }
            if (resElement == null || !resElement.getAttribute("code").equals("s01")) {
                return false;
            }
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e2) {
            e2.printStackTrace();
            return false;
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }
}
