package com.hiti.webhiti;

import android.content.Context;
import com.hiti.jni.hello.Hello;
import com.hiti.sql.offlineadinfo.parser.OfflineADParser;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.web.WebRequestNew;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

public class HitiWeb_GetUpdateInfo {
    public static final int ELEMENT_ID_P231 = 4;
    public static final int ELEMENT_ID_P232 = 5;
    public static final int ELEMENT_ID_P235 = 17;
    public static final int ELEMENT_ID_P310W = 9;
    public static final int ELEMENT_ID_P461 = 10;
    public static final int ELEMENT_ID_P520K = 2;
    public static final int ELEMENT_ID_P520L = 7;
    public static final int ELEMENT_ID_P525L = 18;
    public static final int ELEMENT_ID_P530D = 11;
    public static final int ELEMENT_ID_P750L = 8;
    public static final int ELEMENT_ID_WECHAT = 12;
    public static final int UPDATE_ID_DRIVER = 3;
    public static final int UPDATE_ID_FW = 2;
    public static final int UPDATE_ID_TOOL = 1;
    private LogManager LOG;
    private String TAG;
    private Context m_Context;

    public HitiWeb_GetUpdateInfo(Context context) {
        this.LOG = null;
        this.TAG = "GetUpdateInfo";
        this.m_Context = context;
        this.LOG = new LogManager(0);
    }

    public String Service(String Version, String UpdateID, String ElementID, String strReleaseFlag) {
        String serviceNameSpace = "http://tempuri.org/";
        String serviceURL = "https://update.hiti.com/UpdateService/Service.asmx";
        String methodName = "GetUpdateInfo";
        String soapAction = serviceNameSpace + methodName;
        String TimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(ELEMENT_ID_P530D);
        String soapRequset = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><HiTi><Command><Version>" + Version + "</Version>" + "<UpdateID>" + UpdateID + "</UpdateID>" + "<ElementID>" + ElementID + "</ElementID>" + "<TimeStamp>" + TimeStamp + "</TimeStamp>" + "<Signature>" + EncryptAndDecryptAES.MakeMD5(TimeStamp + Hello.SayHello(this.m_Context, 1217)) + "</Signature>" + "<ReleaseFlag>" + strReleaseFlag + "</ReleaseFlag>" + "</Command>" + "</HiTi>";
        this.LOG.m385i(this.TAG, "serviceURL: " + serviceURL);
        this.LOG.m385i("Service soapRequset", String.valueOf(soapRequset));
        return new WebRequestNew().PostSOAP(serviceNameSpace, serviceURL, methodName, soapAction, "<xml>" + soapRequset.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;") + "</xml>");
    }

    public String PrinterInfoService(String ElementID, String SerialsNumber, HashMap<String, Integer> PrintOutList, String strLatitude, String strLongitude) {
        String serviceNameSpace = "http://tempuri.org/";
        String serviceURL = "https://update.hiti.com/UpdateService/PrinbizService.asmx";
        String methodName = "SendPrinterInfo";
        String soapAction = serviceNameSpace + methodName;
        String TimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(ELEMENT_ID_P530D);
        String Signature = EncryptAndDecryptAES.MakeMD5(TimeStamp + Hello.SayHello(this.m_Context, 1217));
        String strPrintOut = XmlPullParser.NO_NAMESPACE;
        for (String printoutType : PrintOutList.keySet()) {
            strPrintOut = strPrintOut + AddPrintOutTag(printoutType, ((Integer) PrintOutList.get(printoutType)).intValue());
        }
        return new WebRequestNew().PostSOAP(serviceNameSpace, serviceURL, methodName, soapAction, "<xml>" + ("<?xml version=\"1.0\" encoding=\"UTF-8\"?><HiTi><Command><Version>1</Version><Platform>2</Platform><ElementID>" + ElementID + "</ElementID>" + "<SerialsNumber>" + SerialsNumber + "</SerialsNumber>" + strPrintOut + "<Latitude>" + strLatitude + "</Latitude>" + "<Longitude>" + strLongitude + "</Longitude>" + "<TimeStamp>" + TimeStamp + "</TimeStamp>" + "<Signature>" + Signature + "</Signature>" + "</Command>" + "</HiTi>").replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;") + "</xml>");
    }

    private String AddPrintOutTag(String frameType, int printOutCount) {
        return "<PrintOut" + frameType + ">" + printOutCount + "</PrintOut" + frameType + ">";
    }

    public String RemoveSOAPFormat(String strSOAP) {
        String strXml = strSOAP;
        int iStart = strSOAP.indexOf("<?xml version='1.0' encoding='UTF-8'?><HiTi><Result>");
        if (iStart == -1) {
            return XmlPullParser.NO_NAMESPACE;
        }
        int iEnd = strSOAP.indexOf("</Result></HiTi>");
        if (iEnd == -1) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return strSOAP.substring(iStart, new String("</Result></HiTi>").length() + iEnd);
    }

    public HitiWebValue_GetUpdateInfo Parse(String strXML) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        HitiWebValue_GetUpdateInfo ui = new HitiWebValue_GetUpdateInfo();
        try {
            NodeList Results = factory.newDocumentBuilder().parse(new InputSource(new StringReader(strXML))).getDocumentElement().getElementsByTagName("Result");
            for (int i = 0; i < Results.getLength(); i += UPDATE_ID_TOOL) {
                Element ResultElement = (Element) Results.item(i);
                ui.Response = GetFirstNodeValue(ResultElement, "Response");
                ui.Version = GetFirstNodeValue(ResultElement, OfflineADParser.ATTR_Version);
                ui.UpdateID = GetFirstNodeValue(ResultElement, "UpdateID");
                ui.ElementID = GetFirstNodeValue(ResultElement, "ElementID");
                ui.TimeStamp = GetFirstNodeValue(ResultElement, "TimeStamp");
                ui.Signature = GetFirstNodeValue(ResultElement, "Signature");
                ui.NewsetVersion = GetFirstNodeValue(ResultElement, "NewsetVersion");
                ui.Info = GetFirstNodeValue(ResultElement, "Info");
                ui.FTP = GetFirstNodeValue(ResultElement, "FTP");
                ui.Path = GetFirstNodeValue(ResultElement, "Path");
                ui.UserName = GetFirstNodeValue(ResultElement, "UserName");
                ui.Password = GetFirstNodeValue(ResultElement, "Password");
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ParserConfigurationException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return ui;
    }

    private String GetFirstNodeValue(Element parentElement, String strTagName) {
        NodeList node = parentElement.getElementsByTagName(strTagName);
        if (node != null) {
            try {
                Element element = (Element) node.item(0);
                if (element == null) {
                    return XmlPullParser.NO_NAMESPACE;
                }
                if (element.getFirstChild() == null) {
                    return XmlPullParser.NO_NAMESPACE;
                }
                if (element.getFirstChild().getNodeValue() == null) {
                    return XmlPullParser.NO_NAMESPACE;
                }
                return element.getFirstChild().getNodeValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return XmlPullParser.NO_NAMESPACE;
    }
}
