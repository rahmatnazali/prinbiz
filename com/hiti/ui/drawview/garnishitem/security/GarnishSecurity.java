package com.hiti.ui.drawview.garnishitem.security;

import android.content.Context;
import com.hiti.jni.hello.Hello;
import com.hiti.sql.offlineadinfo.parser.OfflineADParser;
import com.hiti.ui.drawview.garnishitem.utility.GarnishItemUtility;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.FileUtility;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

public class GarnishSecurity {
    LogManager LOG;
    Context m_Context;
    private ArrayList<GarnishSecurityInfo> m_GarnishSecurityInfoList;

    public GarnishSecurity(Context context) {
        this.LOG = null;
        this.m_Context = null;
        this.m_GarnishSecurityInfoList = null;
        this.m_Context = context;
        this.m_GarnishSecurityInfoList = new ArrayList();
        this.LOG = new LogManager(0);
    }

    public void AddGarnishSecurityInfo(GarnishSecurityInfo garnishSecurityInfo) {
        this.m_GarnishSecurityInfoList.add(garnishSecurityInfo);
    }

    public GarnishSecurityInfo GetGarnishSecurityInfoByItemName(String strItemName) {
        this.LOG.m384e("m_GarnishSecurityInfoList size", String.valueOf(this.m_GarnishSecurityInfoList.size()));
        Iterator it = this.m_GarnishSecurityInfoList.iterator();
        while (it.hasNext()) {
            GarnishSecurityInfo garnishSecurityInfo = (GarnishSecurityInfo) it.next();
            this.LOG.m384e("123 Get GetItemName", garnishSecurityInfo.GetItemName(0));
            if (garnishSecurityInfo.ContainItemName(strItemName)) {
                return garnishSecurityInfo;
            }
        }
        return null;
    }

    public GarnishSecurityInfo CreateGarnishSecurityInfo(String strConfigXMLPath) {
        if (!FileUtility.FileExist(strConfigXMLPath)) {
            return null;
        }
        String strEncrypt = FileUtility.ReadFile(strConfigXMLPath);
        String strIV = XmlPullParser.NO_NAMESPACE;
        if (strConfigXMLPath.contains(".xnl")) {
            strIV = EncryptAndDecryptAES.MakeIVFromUser(this.m_Context);
        } else {
            strIV = EncryptAndDecryptAES.MakeIVFromIMEI(this.m_Context);
        }
        String strConfigContext = EncryptAndDecryptAES.DecryptStr(strEncrypt, strIV, Hello.SayHello(this.m_Context, 831));
        if (strConfigContext == null) {
            return null;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            String strExpire = XmlPullParser.NO_NAMESPACE;
            String strConfigType = XmlPullParser.NO_NAMESPACE;
            ArrayList<Long> lItemSizeList = new ArrayList();
            ArrayList<String> strItemNameList = new ArrayList();
            Element root = factory.newDocumentBuilder().parse(new InputSource(new StringReader(strConfigContext))).getDocumentElement();
            Element CommonElement = (Element) root.getElementsByTagName(OfflineADParser.NODE_Common).item(0);
            strExpire = CommonElement.getAttribute("expire");
            strConfigType = CommonElement.getAttribute("config_type");
            int iItemNumbers = Integer.valueOf(CommonElement.getAttribute("item_numbers")).intValue();
            this.LOG.m384e("Garnish expire ", String.valueOf(strExpire));
            this.LOG.m384e("Garnish config_type ", String.valueOf(strConfigType));
            this.LOG.m384e("Garnish item_numbers ", String.valueOf(iItemNumbers));
            NodeList nodes = root.getElementsByTagName("Item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element GarnishElement = (Element) nodes.item(i);
                this.LOG.m384e("Garnish item_name ", GarnishElement.getAttribute("item_name"));
                this.LOG.m384e("Garnish size ", GarnishElement.getAttribute("size"));
                strItemNameList.add(GarnishElement.getAttribute("item_name"));
                lItemSizeList.add(Long.valueOf(GarnishElement.getAttribute("size")));
            }
            return new GarnishSecurityInfo(strExpire, strConfigType, iItemNumbers, lItemSizeList, strItemNameList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ParserConfigurationException e2) {
            e2.printStackTrace();
            return null;
        } catch (SAXException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public void CheckExpire(ArrayList<String> strConfigXMLPathList) {
        this.m_GarnishSecurityInfoList.clear();
        Iterator it = strConfigXMLPathList.iterator();
        while (it.hasNext()) {
            String strPath = (String) it.next();
            this.LOG.m384e("Configure Path", strPath);
            GarnishSecurityInfo garnishSecurityInfo = CreateGarnishSecurityInfo(strPath);
            if (garnishSecurityInfo != null) {
                if (IsExpire(garnishSecurityInfo.GetExpire())) {
                    DeleteExpire(strPath, garnishSecurityInfo);
                } else {
                    this.m_GarnishSecurityInfoList.add(garnishSecurityInfo);
                }
            }
        }
    }

    public ArrayList<String> GetAllVerifyItemName(String strAppendPath) {
        ArrayList<String> retVerifyItemList = new ArrayList();
        Iterator it = this.m_GarnishSecurityInfoList.iterator();
        while (it.hasNext()) {
            GarnishSecurityInfo garnishSecurityInfo = (GarnishSecurityInfo) it.next();
            for (int i = 0; i < garnishSecurityInfo.GetItemNumbers(); i++) {
                retVerifyItemList.add(strAppendPath + garnishSecurityInfo.GetItemName(i));
            }
        }
        return retVerifyItemList;
    }

    public boolean CheckItemVerify(String strThumbnailNamePath) {
        if (!FileUtility.IsFromSDCard(this.m_Context, strThumbnailNamePath)) {
            return true;
        }
        String strThumbnailName = FileUtility.GetFileName(strThumbnailNamePath);
        GarnishSecurityInfo garnishSecurityInfo = GetGarnishSecurityInfoByItemName(strThumbnailName);
        long lSize = garnishSecurityInfo.GetItemSizeByName(strThumbnailName);
        String strVerifyFolderPath = SearchVerifyFolderPath(garnishSecurityInfo.GetConfigType(), strThumbnailNamePath);
        if (strVerifyFolderPath == null) {
            return false;
        }
        this.LOG.m384e("--- iSize ---", String.valueOf(lSize));
        long lVerifySize = FileUtility.FolderSize(strVerifyFolderPath);
        this.LOG.m384e("--- Folder Size ---", String.valueOf(lVerifySize));
        if (lSize != lVerifySize) {
            return false;
        }
        return true;
    }

    private boolean IsExpire(String strExpire) {
        String strToday = MobileInfo.GetDateStamp();
        if (strExpire.contains("99999999")) {
            return false;
        }
        if (strExpire.length() < 0) {
            return true;
        }
        if (Integer.valueOf(strExpire).intValue() < Integer.valueOf(strToday).intValue()) {
            return true;
        }
        return false;
    }

    void DeleteExpire(String strExpireConfigPath, GarnishSecurityInfo garnishSecurityInfo) {
        FileUtility.DeleteFile(strExpireConfigPath);
        String strRootFilePath = FileUtility.GetSDAppRootPath(this.m_Context);
        for (int i = 0; i < garnishSecurityInfo.GetItemNumbers(); i++) {
            if (garnishSecurityInfo.GetConfigType().contains("cgarnish")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.C_GARNISH_PATH + "/" + GarnishItemUtility.GetCatalogFolderName(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.C_GARNISH_PATH + "/" + PringoConvenientConst.CATTHUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("vborder")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH + "/" + GarnishItemUtility.GetCatalogFolderName(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH + "/" + PringoConvenientConst.CATTHUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("hborder")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.H_BORDER_PATH + "/" + GarnishItemUtility.GetCatalogFolderName(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.H_BORDER_PATH + "/" + PringoConvenientConst.CATTHUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("vcollage")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.V_COLLAGE_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.V_COLLAGE_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("hcollage")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.H_COLLAGE_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.H_COLLAGE_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("vbusinesscard")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.V_BUSINESS_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.V_BUSINESS_CARD_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("hbusinesscard")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.H_BUSINESS_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.H_BUSINESS_CARD_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("vgreetingcard")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.V_GREETING_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.V_GREETING_CARD_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            } else if (garnishSecurityInfo.GetConfigType().contains("hgreetingcard")) {
                FileUtility.DeleteALLFolder(strRootFilePath + "/" + PringoConvenientConst.H_GREETING_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(garnishSecurityInfo.GetItemName(i)));
                FileUtility.DeleteFile(strRootFilePath + "/" + PringoConvenientConst.H_GREETING_CARD_PATH + "/" + PringoConvenientConst.THUMB + "/" + garnishSecurityInfo.GetItemName(i));
            }
        }
    }

    private String SearchVerifyFolderPath(String strConfigType, String strPath) {
        if (strConfigType.contains("cgarnish")) {
            return FileUtility.GetFolderFullPath(strPath).replace("/catthumb", XmlPullParser.NO_NAMESPACE) + "/" + GarnishItemUtility.GetCatalogFolderName(strPath);
        } else if (strConfigType.contains("vborder") || strConfigType.contains("hborder")) {
            return FileUtility.GetFolderFullPath(strPath).replace("/catthumb", XmlPullParser.NO_NAMESPACE) + "/" + GarnishItemUtility.GetCatalogFolderName(strPath);
        } else if (!strConfigType.contains("vcollage") && !strConfigType.contains("hcollage") && !strConfigType.contains("vbusinesscard") && !strConfigType.contains("hbusinesscard") && !strConfigType.contains("vgreetingcard") && !strConfigType.contains("hgreetingcard")) {
            return null;
        } else {
            return FileUtility.GetFolderFullPath(strPath).replace("/thumb", XmlPullParser.NO_NAMESPACE) + "/" + FileUtility.GetFileNameWithoutExt(strPath);
        }
    }
}
