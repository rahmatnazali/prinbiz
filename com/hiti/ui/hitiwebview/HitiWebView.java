package com.hiti.ui.hitiwebview;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.hiti.AppInfo.AppInfo;
import com.hiti.AppInfo.AppInfo.APP_MODE;
import com.hiti.jni.hello.Hello;
import com.hiti.jscommand.JSCommand;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommand;
import com.hiti.sql.oadc.OADCItem;
import com.hiti.sql.offlineadinfo.parser.OfflineADParser;
import com.hiti.trace.GlobalVariable_UploadInfo;
import com.hiti.trace.GlobalVariable_UserInfo;
import com.hiti.ui.drawview.garnishitem.utility.GarnishItemUtility;
import com.hiti.utility.EncryptAndDecryptAES;
import com.hiti.utility.FileUtility;
import com.hiti.utility.Install.ZIP_AND_COPY_INSTALLER_ERROR;
import com.hiti.utility.Install.ZipAndCopyInstaller;
import com.hiti.utility.LogManager;
import com.hiti.utility.MobileInfo;
import com.hiti.utility.PringoConvenientConst;
import com.hiti.utility.UserInfo;
import com.hiti.web.HitiWebPath;
import com.hiti.web.WebPostRequest;
import com.hiti.web.WebRequestNew;
import com.hiti.web.download.WEB_DOWNLOAD_ERROR;
import com.hiti.web.download.WebDownload;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

@SuppressLint({"SetJavaScriptEnabled"})
public class HitiWebView extends RelativeLayout {
    private LogManager LOG;
    private final int MAX_DOWNLOAD_SIZE;
    private AppInfo m_APPMode;
    private Context m_Context;
    private DownloadCenterInstaller m_DownloadCenterInstaller;
    private HitiWebViewListener m_HitiWebViewListener;
    private JSCommand m_JSCommand;
    private Uri m_SaveWebPhotoUri;
    private VIEW_MODE m_ViewMode;
    private WebDownloadPhoto m_WebDownloadPhoto;
    private WebDownloadZip m_WebDownloadZip;
    private WebView m_WebView;
    private long m_lSaveWebPhotoID;
    private String m_strDownloadID;
    private String m_strExpireDate;
    private String m_strFileLanguage;
    private String m_strSaveFilePath;
    private String m_strSaveWebPhotoPath;

    public class DOWNLOAD_CENTER_CATALOG {
        public static final int BRAND_AREA = 7;
        public static final int FESTIVAL = 3;
        public static final int FREE_AREA = 5;
        public static final int HOME = 1;
        public static final int ONLY_METAL = 4;
        public static final int POINT_AREA = 6;
        public static final int PRINGO_FRIENDS = 2;
    }

    public class DOWNLOAD_CENTER_EFRAME_TYPE {
        public static final int BORDER_PAGE = 1;
        public static final int BUSINESSCARD_PAGE = 4;
        public static final int COLLAGE_PAGE = 3;
        public static final int GREETINGCARD_PAGE = 5;
        public static final int STAMP_PAGE = 2;
    }

    public enum VIEW_MODE {
        NON,
        VERIFY,
        CLOUD_ALBUM,
        CLOUD_ALBUM_DOWNLAOD,
        CLOUD_ALBUM_UPLAOD,
        MEMBER,
        DOWNLOAD_CENTER
    }

    private class WatchWebChromeClient extends WebChromeClient {
        private WatchWebChromeClient() {
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            int iAttr = HitiWebView.this.m_JSCommand.GetServerResponseAttr(message);
            result.confirm();
            HitiWebView.this.LOG.m384e("onJsAlert", String.valueOf(iAttr));
            if (iAttr == -1) {
                HitiWebView.this.LOG.m384e("ShowJSAlertDialog", message);
                if (HitiWebView.this.HaveHitiWebViewListener()) {
                    HitiWebView.this.m_HitiWebViewListener.onJsAlert(url, message, result);
                }
            } else if (iAttr == 1) {
                if (HitiWebView.this.HaveHitiWebViewListener()) {
                    HitiWebView.this.m_HitiWebViewListener.onJsAlert(url, HitiWebView.this.m_JSCommand.GetServerResponseContent(message), result);
                }
            } else if (iAttr == 2) {
                HitiWebView.this.UploadPointFinish();
            } else if (iAttr != 3) {
                String strMSG;
                int iSize;
                String strTemp;
                String strPath;
                String strNewFileName;
                if (iAttr == 5) {
                    strMSG = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                    iSize = -1;
                    String strExt = null;
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 5, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_SIZE);
                    if (strTemp != null) {
                        iSize = Integer.valueOf(strTemp).intValue();
                    }
                    if (iSize == 0) {
                        iSize = HitiPPR_PrinterCommand.MAX_DATA_SIZE;
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 5, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_EXT);
                    if (strTemp != null) {
                        strExt = strTemp;
                    }
                    strPath = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 5, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_PATH);
                    if (iSize == -1 || strExt == null || strPath == null) {
                        return true;
                    }
                    strNewFileName = FileUtility.GetNewNameWithExt(strExt, PringoConvenientConst.NEW_FILE_NAME_WEB);
                    HitiWebView.this.PrepareDownloadPhoto(strPath, strNewFileName, iSize);
                } else if (iAttr == 9) {
                    String strRet = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                    UserInfo.UserLogin(HitiWebView.this.m_Context, strRet, Hello.SayGoodBye(HitiWebView.this.m_Context, 3333), Hello.SayHello(HitiWebView.this.m_Context, 3333));
                    if (HitiWebView.this.HaveHitiWebViewListener()) {
                        HitiWebView.this.m_HitiWebViewListener.UserLogin();
                    }
                } else if (iAttr == 10) {
                    UserInfo.UserLogout(HitiWebView.this.m_Context);
                    if (HitiWebView.this.HaveHitiWebViewListener()) {
                        HitiWebView.this.m_HitiWebViewListener.UserLogout();
                    }
                } else if (iAttr == 12) {
                    strMSG = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                    String strID = XmlPullParser.NO_NAMESPACE;
                    String strCountry = XmlPullParser.NO_NAMESPACE;
                    String strPoint = XmlPullParser.NO_NAMESPACE;
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 12, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_ID);
                    if (strTemp != null) {
                        strID = strTemp;
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 12, JSCommand.SERVER_RESPONSE_SUB_ATTR_COUNTRY);
                    if (strTemp != null) {
                        strCountry = strTemp;
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 12, JSCommand.SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT);
                    if (strTemp != null) {
                        strPoint = strTemp;
                    }
                    strPath = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 12, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_PATH);
                    if (strPath != null) {
                        if (HitiWebView.this.HaveHitiWebViewListener()) {
                            HitiWebView.this.m_HitiWebViewListener.StartVideoView(strPath, strID, strCountry, strPoint);
                        }
                    }
                } else if (iAttr == 13) {
                    strMSG = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                    HitiWebView.this.ADBrowserInternet(strMSG);
                } else if (iAttr == 11) {
                    strMSG = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                    HitiWebView.this.LOG.m384e("ShopDownloadPhoto", strMSG);
                    strPath = null;
                    int iID = -1;
                    iSize = -1;
                    String strLanguage = null;
                    HitiWebView.this.m_strExpireDate = OADCItem.WATCH_TYPE_NON;
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 11, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_EXPIRE);
                    if (strTemp != null) {
                        HitiWebView.this.m_strExpireDate = strTemp;
                    }
                    HitiWebView.this.m_strExpireDate = HitiWebView.this.m_strExpireDate.replace("-", XmlPullParser.NO_NAMESPACE);
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 11, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_ID);
                    if (strTemp != null) {
                        iID = Integer.valueOf(strTemp).intValue();
                        HitiWebView.this.m_strDownloadID = strTemp;
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 11, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_SIZE);
                    if (strTemp != null) {
                        iSize = Integer.valueOf(strTemp).intValue();
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 11, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE);
                    if (strTemp != null) {
                        strLanguage = strTemp;
                    }
                    strTemp = HitiWebView.this.m_JSCommand.GetSubType(strMSG, 11, JSCommand.SERVER_RESPONSE_SUB_ATTR_FILE_PATH);
                    if (strTemp != null) {
                        strPath = strTemp;
                    }
                    if (iSize == -1 || iID == -1 || strPath == null || strLanguage == null) {
                        return true;
                    }
                    strNewFileName = FileUtility.GetNewNameWithExt(PringoConvenientConst.PRINGO_SHOP_DOWNLOAD_FILE_EXT, PringoConvenientConst.NEW_FILE_NAME_SHOP);
                    HitiWebView.this.LOG.m384e("PrepareDownloadZip", message);
                    HitiWebView.this.PrepareDownloadZip(strPath, strNewFileName, iSize, strLanguage);
                } else if (iAttr == 14) {
                    if (HitiWebView.this.HaveHitiWebViewListener()) {
                        HitiWebView.this.m_HitiWebViewListener.StartDownloadCenter();
                    }
                } else if (iAttr == 15) {
                    if (HitiWebView.this.HaveHitiWebViewListener()) {
                        HitiWebView.this.m_HitiWebViewListener.StartMember();
                    }
                } else if (iAttr != 16) {
                    if (iAttr == 17) {
                        String strAblumPID = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                        HitiWebView.this.LOG.m385i("HitiWebView CLOUD_ALBUM_UPLOAD", "strAblumPID: " + strAblumPID);
                        if (HitiWebView.this.HaveHitiWebViewListener()) {
                            HitiWebView.this.m_HitiWebViewListener.UploadPhotoToCloudAlbum(strAblumPID);
                        }
                    } else if (iAttr == 18) {
                        String strAblumSortType = HitiWebView.this.m_JSCommand.GetServerResponseContent(message);
                        if (!(strAblumSortType == null || strAblumSortType.isEmpty())) {
                            UserInfo.SetCloudAlbumSortType(HitiWebView.this.m_Context, strAblumSortType);
                        }
                    }
                }
            }
            return true;
        }

        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.onJsConfirm(url, message, result);
            }
            return true;
        }
    }

    private class WatchWebViewClient extends WebViewClient {
        private WatchWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            HitiWebView.this.LOG.m384e("Time", "shouldOverrideUrlLoading " + url.toString());
            HitiWebView.this.LOG.m384e("Time", "shouldOverrideUrlLoading " + MobileInfo.GetTimeReocrd());
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.OnUrlChange(url);
            }
            if (url.toString().contains("PRINGO_AD_URL_http")) {
                HitiWebView.this.ADBrowserInternet(url.toString());
                return true;
            } else if (!url.toString().contains(PringoConvenientConst.WEB_MAILTO)) {
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                if (!HitiWebView.this.HaveHitiWebViewListener()) {
                    return true;
                }
                HitiWebView.this.m_HitiWebViewListener.StartMailto(url);
                return true;
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            HitiWebView.this.LOG.m384e("Time", "onPageStarted " + url.toString());
            HitiWebView.this.LOG.m384e("Time", "onPageStarted " + MobileInfo.GetTimeReocrd());
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.OnPageStarted(url);
            }
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            HitiWebView.this.LOG.m384e("Time", "onPageFinished " + url.toString());
            HitiWebView.this.LOG.m384e("Time", "onPageFinished " + MobileInfo.GetTimeReocrd());
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.OnPageFinished(url);
            }
            super.onPageFinished(view, url);
        }

        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.OnReceivedError(errorCod, description, failingUrl);
            }
        }

        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            resend.sendToTarget();
        }
    }

    public class DownloadCenterInstaller extends ZipAndCopyInstaller {
        public DownloadCenterInstaller(Context context) {
            super(context);
        }

        public void InstallFail(ZIP_AND_COPY_INSTALLER_ERROR error) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.InstallFail(error);
            }
        }

        public void InstallSuccess(String m_strTargetFolderPath) {
            if (HitiWebView.this.m_WebView != null) {
                HitiWebView.this.m_WebView.reload();
            }
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.InstallSuccess(m_strTargetFolderPath);
            }
        }

        public boolean CustomInstall(String strUnZipFolderPath) {
            HitiWebView.this.LOG.m384e("CustomInstall strUnZipFolderPath", strUnZipFolderPath);
            String strParentPath = FileUtility.SearchFolderParentPath(PringoConvenientConst.CONFIG, strUnZipFolderPath);
            if (!TextUtils.isEmpty(strParentPath)) {
                strParentPath = strParentPath.substring(0, strParentPath.lastIndexOf("/"));
            }
            HitiWebView.this.LOG.m386v("CustomInstall strModifiedPath", " " + strParentPath);
            if (strParentPath == null) {
                return false;
            }
            ArrayList<String> retStringList = GarnishItemUtility.SearchGarnishConfig(strParentPath);
            String strConfigType = XmlPullParser.NO_NAMESPACE;
            if (retStringList.size() == 0) {
                return false;
            }
            Iterator it = retStringList.iterator();
            while (it.hasNext()) {
                String strConfigXMLPath = (String) it.next();
                HitiWebView.this.LOG.m384e("strConfigXMLPath", strConfigXMLPath);
                File file = new File(strConfigXMLPath);
                try {
                    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                    Element root = document.getDocumentElement();
                    Element CommonElement = (Element) root.getElementsByTagName(OfflineADParser.NODE_Common).item(0);
                    CommonElement.setAttribute("expire", HitiWebView.this.m_strExpireDate);
                    HitiWebView.this.LOG.m384e("Garnish Number", CommonElement.getAttribute("item_numbers"));
                    HitiWebView.this.LOG.m384e("Garnish expire", CommonElement.getAttribute("expire"));
                    strConfigType = CommonElement.getAttribute("config_type");
                    HitiWebView.this.LOG.m384e("Garnish config_type", strConfigType);
                    NodeList nodes = root.getElementsByTagName("Item");
                    ArrayList<String> itemList = new ArrayList();
                    String strThumbnailName = null;
                    for (int i = 0; i < nodes.getLength(); i++) {
                        strThumbnailName = ((Element) nodes.item(i)).getAttribute("item_name");
                        itemList.add(strThumbnailName);
                    }
                    TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(file));
                    String strConfigContext = FileUtility.ReadFile(strConfigXMLPath);
                    String strIV = EncryptAndDecryptAES.MakeIVFromUser(this.m_Context);
                    if (strConfigContext == null) {
                        HitiWebView.this.LOG.m384e("Error EncryptStr", "strConfigContext == null");
                    } else if (strConfigContext.length() <= 0) {
                        HitiWebView.this.LOG.m384e("Error EncryptStr", "strConfigContext == null");
                    }
                    FileUtility.WriteFile(strConfigXMLPath, EncryptAndDecryptAES.EncryptStr(strConfigContext, strIV, Hello.SayHello(this.m_Context, 831)));
                    file.renameTo(new File(FileUtility.GetFolderFullPath(strConfigXMLPath) + "/" + FileUtility.GetFileNameWithoutExt(strThumbnailName) + ".xnl"));
                    HitiWebView.this.DeleteBeforeCopy(HitiWebView.this.GetDeleteBeforeCopyList(itemList, strConfigType));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (ParserConfigurationException e2) {
                    e2.printStackTrace();
                    return false;
                } catch (SAXException e3) {
                    e3.printStackTrace();
                    return false;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return false;
                } catch (TransformerException e5) {
                    e5.printStackTrace();
                    return false;
                } catch (NullPointerException e6) {
                    e6.printStackTrace();
                    return false;
                }
            }
            return ResponseGetFile();
        }

        private boolean ResponseGetFile() {
            GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
            GVUploadInfo.RestoreGlobalVariable();
            GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
            GVUserInfo.RestoreGlobalVariable();
            if (GVUserInfo.GetVerify() != 1) {
                return false;
            }
            GVUploadInfo.RestoreGlobalVariable();
            Pair<String, String> pair = UserInfo.GetUP(this.m_Context, GVUploadInfo.GetUploader());
            String strU = XmlPullParser.NO_NAMESPACE;
            String strP = XmlPullParser.NO_NAMESPACE;
            if (pair == null) {
                return false;
            }
            strP = pair.second;
            String value = EncryptAndDecryptAES.EncryptStr(pair.first + PringoConvenientConst.DATE_TO_DATE_2 + strP + PringoConvenientConst.DATE_TO_DATE_2 + MobileInfo.GetTimeStamp() + PringoConvenientConst.DATE_TO_DATE_2 + MobileInfo.GetIMEI(this.m_Context) + PringoConvenientConst.DATE_TO_DATE_2 + HitiWebView.this.m_strDownloadID + PringoConvenientConst.DATE_TO_DATE_2 + "2" + PringoConvenientConst.DATE_TO_DATE_2 + HitiWebView.this.m_strFileLanguage + PringoConvenientConst.DATE_TO_DATE_2, Hello.SayGoodBye(this.m_Context, 405), Hello.SayHello(this.m_Context, 405));
            HitiWebView.this.LOG.m384e("Get value", value);
            Map<String, String> paramlist = new HashMap();
            paramlist.put("option2", value);
            WebPostRequest webPostRequest = new WebPostRequest();
            String response = new WebRequestNew().PostByURLConnection(HitiWebPath.WEB_REQUEST_EFRAME_DOWNLOAD, paramlist);
            if (response == null) {
                SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_COPY_FILE_RESPONSE_BACK_FAIL);
            } else if (webPostRequest.GetResponseMSG(response) == null) {
                SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_COPY_FILE_RESPONSE_BACK_FAIL);
            } else if (webPostRequest.Success(response)) {
                return true;
            } else {
                SetLastError(ZIP_AND_COPY_INSTALLER_ERROR.ERROR_COPY_FILE_RESPONSE_SERVER_REJECT);
            }
            return false;
        }

        public void DeleteTemp() {
            HitiWebView.this.DeleteDownloadFolder();
        }
    }

    private class WebDownloadPhoto extends WebDownload {

        /* renamed from: com.hiti.ui.hitiwebview.HitiWebView.WebDownloadPhoto.1 */
        class C04361 implements OnScanCompletedListener {
            C04361() {
            }

            public void onScanCompleted(String path, Uri uri) {
            }
        }

        WebDownloadPhoto(Context context) {
            super(context);
        }

        public void DownloadSuccess() {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadSuccess(HitiWebView.this.m_strSaveWebPhotoPath, HitiWebView.this.m_lSaveWebPhotoID);
            }
            if (HitiWebView.this.m_strSaveWebPhotoPath != null) {
                MediaScannerConnection.scanFile(this.m_Context, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new C04361());
            }
        }

        public void DownloadProgress(int iProgress) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadProgress(HitiWebView.this.m_strSaveWebPhotoPath, iProgress);
            }
        }

        public void DownloadFail(WEB_DOWNLOAD_ERROR Result) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadFail(HitiWebView.this.m_strSaveWebPhotoPath, Result);
            }
        }

        public void DownloadCancel() {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadCancel(HitiWebView.this.m_strSaveWebPhotoPath);
            }
        }

        public void SaveFileSuccess() {
            ContentValues updateValues = new ContentValues();
            File updateFile = new File(HitiWebView.this.m_strSaveWebPhotoPath);
            updateValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            updateValues.put("_size", Long.valueOf(updateFile.length()));
            this.m_Context.getContentResolver().update(Media.EXTERNAL_CONTENT_URI, updateValues, "_id=?", new String[]{String.valueOf(HitiWebView.this.m_lSaveWebPhotoID)});
            this.m_Context.getContentResolver().notifyChange(HitiWebView.this.m_SaveWebPhotoUri, null);
        }

        public OutputStream GetOutputStream() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("_data", HitiWebView.this.m_strSaveWebPhotoPath);
            HitiWebView.this.m_SaveWebPhotoUri = this.m_Context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
            HitiWebView.this.m_lSaveWebPhotoID = ContentUris.parseId(HitiWebView.this.m_SaveWebPhotoUri);
            try {
                return this.m_Context.getContentResolver().openOutputStream(HitiWebView.this.m_SaveWebPhotoUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void DeleteDownload() {
        }
    }

    public class WebDownloadZip extends WebDownload {

        /* renamed from: com.hiti.ui.hitiwebview.HitiWebView.WebDownloadZip.1 */
        class C04371 implements OnScanCompletedListener {
            C04371() {
            }

            public void onScanCompleted(String path, Uri uri) {
            }
        }

        WebDownloadZip(Context context) {
            super(context);
        }

        public void DownloadSuccess() {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadSuccess(HitiWebView.this.m_strSaveFilePath, -1);
            }
            if (HitiWebView.this.m_strSaveFilePath != null) {
                if (HitiWebView.this.HaveHitiWebViewListener()) {
                    HitiWebView.this.m_HitiWebViewListener.StartInstall();
                }
                MediaScannerConnection.scanFile(this.m_Context, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new C04371());
                String strZipPath = HitiWebView.this.m_strSaveFilePath;
                String strUnZipFolderPath = FileUtility.GetFolderFullPath(HitiWebView.this.m_strSaveFilePath) + "/" + FileUtility.GetFileNameWithoutExt(HitiWebView.this.m_strSaveFilePath);
                String strTargetFolderPath = FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER;
                HitiWebView.this.LOG.m384e("strUnZipFolderPath", strUnZipFolderPath);
                HitiWebView.this.LOG.m384e("strZipPath", strZipPath);
                HitiWebView.this.LOG.m384e("strTargetFolderPath", strTargetFolderPath);
                HitiWebView.this.m_DownloadCenterInstaller = new DownloadCenterInstaller(this.m_Context);
                HitiWebView.this.m_DownloadCenterInstaller.SetInstallInfo(strZipPath, strUnZipFolderPath, strTargetFolderPath);
                HitiWebView.this.m_DownloadCenterInstaller.execute(new String[]{XmlPullParser.NO_NAMESPACE});
            }
        }

        public void DownloadProgress(int iProgress) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadProgress(HitiWebView.this.m_strSaveFilePath, iProgress);
            }
        }

        public void DownloadFail(WEB_DOWNLOAD_ERROR Result) {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadFail(HitiWebView.this.m_strSaveFilePath, Result);
            }
        }

        public void DownloadCancel() {
            if (HitiWebView.this.HaveHitiWebViewListener()) {
                HitiWebView.this.m_HitiWebViewListener.DownloadCancel(HitiWebView.this.m_strSaveFilePath);
            }
        }

        public OutputStream GetOutputStream() {
            OutputStream os = CreateOutputStream0();
            if (os != null) {
                return os;
            }
            os = CreateOutputStream1();
            if (os != null) {
                return os;
            }
            return null;
        }

        private OutputStream CreateOutputStream0() {
            HitiWebView.this.LOG.m384e("GetOutputStream", "CreateOutputStream0");
            try {
                FileOutputStream fos = new FileOutputStream(new File(HitiWebView.this.m_strSaveFilePath));
                return fos;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        private OutputStream CreateOutputStream1() {
            HitiWebView.this.LOG.m384e("GetOutputStream", "CreateOutputStream1");
            String strFolderPath = HitiWebView.this.m_strSaveFilePath.substring(0, HitiWebView.this.m_strSaveFilePath.lastIndexOf("/") + 1);
            HitiWebView.this.LOG.m384e("strFolderPath", strFolderPath);
            FileUtility.CreateFolder(strFolderPath);
            String strFileName = FileUtility.GetFileName(HitiWebView.this.m_strSaveFilePath);
            File file = new File(strFolderPath, strFileName);
            HitiWebView.this.LOG.m384e("strFileName", strFileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                return fos;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void SaveFileSuccess() {
        }

        public void DeleteDownload() {
            HitiWebView.this.DeleteDownloadFolder();
        }
    }

    public HitiWebView(Context context) {
        super(context);
        this.MAX_DOWNLOAD_SIZE = HitiPPR_PrinterCommand.MAX_DATA_SIZE;
        this.m_Context = null;
        this.m_WebView = null;
        this.m_ViewMode = VIEW_MODE.NON;
        this.m_APPMode = null;
        this.m_JSCommand = null;
        this.m_strSaveWebPhotoPath = null;
        this.m_lSaveWebPhotoID = -1;
        this.m_SaveWebPhotoUri = null;
        this.m_WebDownloadPhoto = null;
        this.m_strSaveFilePath = null;
        this.m_strExpireDate = null;
        this.m_strDownloadID = XmlPullParser.NO_NAMESPACE;
        this.m_strFileLanguage = XmlPullParser.NO_NAMESPACE;
        this.m_WebDownloadZip = null;
        this.m_DownloadCenterInstaller = null;
        this.m_HitiWebViewListener = null;
        this.LOG = null;
        this.LOG = new LogManager(0);
        SetupWebView(context);
    }

    public HitiWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.MAX_DOWNLOAD_SIZE = HitiPPR_PrinterCommand.MAX_DATA_SIZE;
        this.m_Context = null;
        this.m_WebView = null;
        this.m_ViewMode = VIEW_MODE.NON;
        this.m_APPMode = null;
        this.m_JSCommand = null;
        this.m_strSaveWebPhotoPath = null;
        this.m_lSaveWebPhotoID = -1;
        this.m_SaveWebPhotoUri = null;
        this.m_WebDownloadPhoto = null;
        this.m_strSaveFilePath = null;
        this.m_strExpireDate = null;
        this.m_strDownloadID = XmlPullParser.NO_NAMESPACE;
        this.m_strFileLanguage = XmlPullParser.NO_NAMESPACE;
        this.m_WebDownloadZip = null;
        this.m_DownloadCenterInstaller = null;
        this.m_HitiWebViewListener = null;
        this.LOG = null;
        this.LOG = new LogManager(0);
        SetupWebView(context);
    }

    public void SetHitiWebViewListener(HitiWebViewListener HitiWebViewListener) {
        this.m_HitiWebViewListener = HitiWebViewListener;
    }

    private boolean HaveHitiWebViewListener() {
        if (this.m_HitiWebViewListener == null) {
            return false;
        }
        return true;
    }

    public VIEW_MODE GetViewMode() {
        return this.m_ViewMode;
    }

    public void SetViewMode(VIEW_MODE viewMode) {
        this.m_ViewMode = viewMode;
    }

    @SuppressLint({"NewApi"})
    private void SetupWebView(Context context) {
        this.m_Context = context;
        this.m_APPMode = new AppInfo(context);
        LayoutParams lp = new LayoutParams(-1, -2);
        lp.addRule(10);
        lp.addRule(12);
        lp.addRule(5);
        this.m_WebView = new WebView(this.m_Context);
        this.m_WebView.setLayoutParams(lp);
        WebSettings websettings = this.m_WebView.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setSupportZoom(false);
        websettings.setBuiltInZoomControls(false);
        websettings.setCacheMode(1);
        this.m_WebView.setWebViewClient(new WatchWebViewClient());
        this.m_WebView.setWebChromeClient(new WatchWebChromeClient());
        this.m_WebView.clearCache(true);
        this.m_WebView.clearHistory();
        addView(this.m_WebView);
        this.m_JSCommand = new JSCommand(this.m_Context);
        this.m_ViewMode = VIEW_MODE.NON;
    }

    public void StopLoading() {
        this.m_WebView.stopLoading();
    }

    public void Destroy() {
        this.m_WebView.destroy();
    }

    public void ClearWebView() {
        this.m_WebView.loadUrl("about:blank");
    }

    public void ClearCookie() {
        CookieSyncManager.createInstance(this.m_Context);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }

    public void LoadVerify(boolean boRegister) {
        if (this.m_ViewMode == VIEW_MODE.VERIFY) {
            String strPath;
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadVerify();
            }
            ClearWebView();
            if (MobileInfo.GetLocation(this.m_Context, true) != null) {
                strPath = String.format(HitiWebPath.VERIFY_LOGIN_PATH, new Object[]{String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), String.valueOf(GetAppModeNumber())});
            } else {
                strPath = String.format(HitiWebPath.VERIFY_LOGIN_PATH, new Object[]{String.valueOf(-1), String.valueOf(-1), String.valueOf(GetAppModeNumber())});
            }
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998), false);
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            if (boRegister) {
                strPath = strPath + "/type/2";
            } else {
                strPath = strPath + "/type/1";
            }
            this.m_WebView.loadUrl(strPath);
        }
    }

    public void LoadDownloadCenter(int iCatalog, int iEframeType, int iPaperType, int iWidth) {
        if (this.m_ViewMode == VIEW_MODE.DOWNLOAD_CENTER) {
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadDownloadCenter();
            }
            ClearWebView();
            String strPath = HitiWebPath.DOWNLOAD_CENTER_PATH;
            Location location = MobileInfo.GetLocation(this.m_Context, false);
            String strLatitude = "-1";
            String strLongitude = "-1";
            if (location != null) {
                strLatitude = String.valueOf(location.getLatitude());
                strLongitude = String.valueOf(location.getLongitude());
            }
            strPath = String.format(strPath, new Object[]{String.valueOf(iCatalog), String.valueOf(iEframeType), String.valueOf(iPaperType), String.valueOf(2), Integer.valueOf(GetAppModeNumber()), strLatitude, strLongitude, String.valueOf(iWidth)});
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998));
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            this.LOG.m386v("LoadDownloadCenter", "!!!: " + strPath);
            this.m_WebView.loadUrl(strPath);
        }
    }

    private void CreatePrintOutFolder(String strUnZipPath) {
        this.LOG.m385i("CreateFolder rameFolderPreName:", String.valueOf(strUnZipPath));
        FileUtility.CreateFolders(strUnZipPath);
    }

    public void LoadCloudAlbum(String strShowPerson, int iWidthDp) {
        if (this.m_ViewMode == VIEW_MODE.CLOUD_ALBUM) {
            String strPath;
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadCloudAlbum();
            }
            ClearWebView();
            String strAlbumSortType = UserInfo.GetCloudAlbumSortType(this.m_Context);
            if (MobileInfo.GetLocation(this.m_Context, false) != null) {
                strPath = String.format(HitiWebPath.CLOUD_ALBUM_PATH, new Object[]{String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), strShowPerson, Integer.valueOf(iWidthDp), String.valueOf(strAlbumSortType)});
            } else {
                strPath = String.format(HitiWebPath.CLOUD_ALBUM_PATH, new Object[]{String.valueOf(-1), String.valueOf(-1), strShowPerson, Integer.valueOf(iWidthDp), String.valueOf(strAlbumSortType)});
            }
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998));
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            if (!SupportGoBack()) {
                strPath = strPath + "/back/1";
            }
            this.LOG.m384e("strPath **** ", strPath);
            this.m_WebView.loadUrl(strPath);
        }
    }

    public void LoadPreferential() {
        if (this.m_ViewMode == VIEW_MODE.MEMBER) {
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadPreferential();
            }
            ClearWebView();
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998));
            Location location = MobileInfo.GetLocation(this.m_Context, false);
            String strLatitude = "-1";
            String strLongitude = "-1";
            if (location != null) {
                strLatitude = String.valueOf(location.getLatitude());
                strLongitude = String.valueOf(location.getLongitude());
            }
            String strtVersion = "99.99.99";
            try {
                strtVersion = String.valueOf(this.m_Context.getPackageManager().getPackageInfo(this.m_Context.getPackageName(), 0).versionName);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            String strPath = String.format(HitiWebPath.AD_VEDIO_PATH, new Object[]{strLatitude, strLongitude, "2", String.valueOf(GetAppModeNumber()), strtVersion});
            if (this.m_APPMode.GetAppMode() == APP_MODE.LIKODA) {
                strPath = strPath + "/newlikodaflow/1";
            }
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            this.m_WebView.loadUrl(strPath);
        }
    }

    public void LoadRewardPointsQurey() {
        if (this.m_ViewMode == VIEW_MODE.MEMBER) {
            String strPath;
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadRewardPointsQurey();
            }
            ClearWebView();
            Location location = MobileInfo.GetLocation(this.m_Context, false);
            String strtVersion = "99.99.99";
            try {
                strtVersion = String.valueOf(this.m_Context.getPackageManager().getPackageInfo(this.m_Context.getPackageName(), 0).versionName);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (location != null) {
                strPath = String.format(HitiWebPath.REWARD_POINTS_QUERY_PATH, new Object[]{"Pringo print", OADCItem.WATCH_TYPE_NON, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "2", String.valueOf(GetAppModeNumber()), strtVersion});
            } else {
                strPath = String.format(HitiWebPath.REWARD_POINTS_QUERY_PATH, new Object[]{"Pringo print", OADCItem.WATCH_TYPE_NON, String.valueOf(-1), String.valueOf(-1), "2", String.valueOf(GetAppModeNumber()), strtVersion});
            }
            if (this.m_APPMode.GetAppMode() == APP_MODE.LIKODA) {
                strPath = strPath + "/newlikodaflow/1";
            }
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998));
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            this.m_WebView.loadUrl(strPath);
        }
    }

    public void LoadMemberInfo() {
        if (this.m_ViewMode == VIEW_MODE.MEMBER) {
            String strPath;
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadMemberInfo();
            }
            if (MobileInfo.GetLocation(this.m_Context, false) != null) {
                strPath = String.format(HitiWebPath.MEMBER_INFO_PATH, new Object[]{String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())});
            } else {
                strPath = String.format(HitiWebPath.MEMBER_INFO_PATH, new Object[]{String.valueOf(-1), String.valueOf(-1)});
            }
            String strAutoLogin = UserInfo.MakeAutoLoginString(this.m_Context, Hello.SayGoodBye(this.m_Context, 9998), Hello.SayHello(this.m_Context, 9998));
            if (strAutoLogin.length() > 0) {
                strPath = strPath + "/data/" + strAutoLogin;
            }
            this.m_WebView.loadUrl(strPath);
        }
    }

    public void LoadEDM() {
        if (this.m_ViewMode == VIEW_MODE.MEMBER) {
            if (HaveHitiWebViewListener()) {
                this.m_HitiWebViewListener.StartLoadEDM();
            }
            this.m_WebView.loadUrl(HitiWebPath.EDM_PATH);
        }
    }

    public void StopDownloadPhoto() {
        if (this.m_WebDownloadPhoto != null) {
            this.m_WebDownloadPhoto.SetDownloadCancel(true);
        }
    }

    private void PrepareDownloadPhoto(String strDownloadPath, String strSaveName, int iSize) {
        String strSaveFolder = Environment.getExternalStorageDirectory().getPath() + File.separator;
        String strAlbumFolder = XmlPullParser.NO_NAMESPACE;
        if (this.m_APPMode.GetAppMode() == APP_MODE.PRINGO) {
            strAlbumFolder = PringoConvenientConst.PRINGO_FOLDER;
        } else if (this.m_APPMode.GetAppMode() == APP_MODE.LIKODA) {
            strAlbumFolder = PringoConvenientConst.LIKODA_FOLDER;
        } else if (this.m_APPMode.GetAppMode() == APP_MODE.PRINBIZ) {
            strAlbumFolder = PringoConvenientConst.PRINBIZ_FOLDER;
        } else if (this.m_APPMode.GetAppMode() == APP_MODE.PRINSNAP) {
            strAlbumFolder = PringoConvenientConst.PRINSNAP_FOLDER;
        } else if (this.m_APPMode.GetAppMode() == APP_MODE.PRINHOME) {
            strAlbumFolder = PringoConvenientConst.PRINHOME_FOLDER;
        }
        strSaveFolder = strSaveFolder + strAlbumFolder;
        FileUtility.CreateFolder(strSaveFolder);
        this.m_strSaveWebPhotoPath = strSaveFolder + "/" + strSaveName;
        if (HaveHitiWebViewListener()) {
            this.m_HitiWebViewListener.StartDownloadCloudPhoto(strDownloadPath, this.m_strSaveWebPhotoPath, iSize);
        }
        this.m_WebDownloadPhoto = new WebDownloadPhoto(this.m_Context);
        this.m_WebDownloadPhoto.SetProgress((float) (iSize / 100));
        this.m_WebDownloadPhoto.execute(new String[]{strDownloadPath});
    }

    private void UploadPointFinish() {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        GVUploadInfo.RestoreGlobalVariable();
        GVUploadInfo.RestoreGlobalVariable();
        GVUploadInfo.SetAESCount(XmlPullParser.NO_NAMESPACE);
        GVUploadInfo.SaveGlobalVariableForever();
    }

    public void StopDownloadZip() {
        if (this.m_WebDownloadZip != null) {
            this.m_WebDownloadZip.SetDownloadCancel(true);
        }
    }

    void PrepareDownloadZip(String strDownloadPath, String strSaveName, int iSize, String strLanguage) {
        this.m_strFileLanguage = strLanguage;
        String strSaveFolder = new File(FileUtility.GetSDAppRootPath(this.m_Context)).getPath() + File.separator + PringoConvenientConst.PRINGO_DOWNLOAD_FOLDER;
        FileUtility.CreateFolder(strSaveFolder);
        this.m_strSaveFilePath = strSaveFolder + "/" + strSaveName;
        this.LOG.m386v("PrepareDownloadZip", "path: " + this.m_strSaveFilePath);
        if (HaveHitiWebViewListener()) {
            this.m_HitiWebViewListener.StartDownloadStamp(strDownloadPath, this.m_strSaveFilePath, iSize);
        }
        this.m_WebDownloadZip = new WebDownloadZip(this.m_Context);
        this.m_WebDownloadZip.SetProgress((float) (iSize / 100));
        this.m_WebDownloadZip.execute(new String[]{strDownloadPath});
    }

    private void ADBrowserInternet(String url) {
        String strPRINGO_AD_URL = PringoConvenientConst.PRINGO_AD_URL;
        String strPath = url;
        if (strPath.contains(strPRINGO_AD_URL)) {
            strPath = url.substring(url.indexOf(strPRINGO_AD_URL) + strPRINGO_AD_URL.length(), url.length());
        }
        this.LOG.m384e("strPath", strPath);
        if (HaveHitiWebViewListener()) {
            this.m_HitiWebViewListener.StartDefaultBrowser(strPath);
        }
    }

    private ArrayList<String> GetDeleteBeforeCopyList(ArrayList<String> itemNameList, String strConfigType) {
        ArrayList<String> deleteBeforeCopyList = new ArrayList();
        Iterator it = itemNameList.iterator();
        while (it.hasNext()) {
            String itemName = (String) it.next();
            if (strConfigType.contains("cgarnish")) {
                this.LOG.m384e("99999999", FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.C_GARNISH_PATH + "/" + GarnishItemUtility.GetGarnishFolderName(itemName, 2));
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.C_GARNISH_PATH + "/" + GarnishItemUtility.GetGarnishFolderName(itemName, 2));
            } else if (strConfigType.contains("vborder")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.V_BORDER_PATH + "/" + GarnishItemUtility.GetGarnishFolderName(itemName, 1));
            } else if (strConfigType.contains("hborder")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.ROOT_GARNISH_FOLDER + PringoConvenientConst.H_BORDER_PATH + "/" + GarnishItemUtility.GetGarnishFolderName(itemName, 1));
            } else if (strConfigType.contains("vcollage")) {
                this.LOG.m384e("delete 99999999", FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.V_COLLAGE_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.V_COLLAGE_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            } else if (strConfigType.contains("hcollage")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.H_COLLAGE_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            } else if (strConfigType.contains("vbusinesscard")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.V_BUSINESS_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            } else if (strConfigType.contains("hbusinesscard")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.H_BUSINESS_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            } else if (strConfigType.contains("vgreetingcard")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.V_GREETING_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            } else if (strConfigType.contains("hgreetingcard")) {
                deleteBeforeCopyList.add(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + PringoConvenientConst.H_GREETING_CARD_PATH + "/" + FileUtility.GetFileNameWithoutExt(itemName));
            }
        }
        return deleteBeforeCopyList;
    }

    private void DeleteBeforeCopy(ArrayList<String> deleteBeforeCopyList) {
        Iterator it = deleteBeforeCopyList.iterator();
        while (it.hasNext()) {
            FileUtility.DeleteALLFolder((String) it.next());
        }
    }

    void DeleteDownloadFolder() {
        FileUtility.DeleteALLFolder(new File(FileUtility.GetSDAppRootPath(this.m_Context)).getPath() + File.separator + PringoConvenientConst.PRINGO_DOWNLOAD_FOLDER);
    }

    public void SetAppMode(APP_MODE appMode) {
        this.m_APPMode.SetAppMode(appMode);
    }

    public int GetAppModeNumber() {
        return this.m_APPMode.GetAppModeNumber();
    }

    public boolean GoBackInWebView() {
        WebBackForwardList history = this.m_WebView.copyBackForwardList();
        String url = null;
        this.LOG.m384e("tag", "history.getCurrentIndex() " + String.valueOf(history.getCurrentIndex()));
        for (int index = -1; this.m_WebView.canGoBackOrForward(index); index--) {
            if (!history.getItemAtIndex(history.getCurrentIndex() + index).getUrl().equals("about:blank")) {
                this.m_WebView.goBackOrForward(index);
                url = history.getItemAtIndex(history.getCurrentIndex() + index).getUrl();
                this.LOG.m384e("tag", "history.getCurrentIndex() " + String.valueOf(history.getCurrentIndex()));
                this.LOG.m384e("tag", "index " + String.valueOf(index));
                this.LOG.m384e("tag", "first non empty " + url);
                break;
            }
        }
        if (url == null) {
            return false;
        }
        return true;
    }

    public String GetUrl() {
        return this.m_WebView.getUrl();
    }

    public static boolean SupportGoBack() {
        if (VERSION.SDK_INT < 19) {
            return true;
        }
        return false;
    }

    public void ReloadPage() {
        if (this.m_WebView != null) {
            this.m_WebView.loadUrl(this.m_WebView.getUrl());
        }
    }
}
