package com.hiti.utility.wifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_GetAllSetting;
import com.hiti.utility.LogManager;
import com.hiti.utility.dialog.MSGListener;
import com.hiti.utility.resource.ResourceId;
import com.hiti.utility.resource.ResourceId.Page;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.impl.constants.DNSConstants;
import org.xmlpull.v1.XmlPullParser;

public class ShowPrinterList {
    private static final int BACK_BUTTON_CLICKED_STATE = 1;
    private static final int GET_PRINTER_INFO_COUNT = 2;
    private static final int MSG_CLEAR_ALL_ITEM = 21;
    private static final int MSG_HIDE_SANBAR = 19;
    private static final int MSG_REMOVE_ITEM = 20;
    private static final int MSG_SCAN_NO_PRINTER = 22;
    private static final int MSG_SHOW_LIST = 17;
    private static final int MSG_SHOW_SANBAR = 18;
    private static final int PRINTER_ITEM_CLICKED_STATE = 3;
    private static final int SCAN_BUTTON_CLICKED_STATE = 2;
    LogManager LOG;
    int TIME_OUT;
    MDNS mMDNStype;
    ResourceId mRID;
    ArrayList<HashMap<String, Object>> m_ArrayListItem;
    private DiscoverPrinter m_DiscoverPrinter;
    int m_GetPrinterServiceCount;
    ListHandler m_Handler;
    HitiPPR_GetAllSetting m_HitiPPR_GetAllSetting;
    String m_IP;
    View m_InfraListView;
    JmDNS m_Jmdns;
    MulticastLock m_Lock;
    String m_Password;
    ArrayList<PrinterInfo> m_PrinterInfo;
    private Dialog m_PrinterListDialog;
    PrinterListListener m_PrinterListListener;
    RelativeLayout m_PrinterListRelativeLayout;
    ListView m_PrinterListView;
    SimpleAdapter m_PrinterSimpleAdapter;
    PwdCheckDialog m_PwdCheckDialog;
    private ReleasePrinter m_ReleasePrinter;
    Button m_ScanButton;
    ProgressBar m_ScanProgressBar;
    private ArrayList<ServiceListener> m_ServerListenerList;
    SettingHandler m_SettingHandler;
    TimeOutTask m_TimeOutTask;
    Timer m_TimeOutTimer;
    private ArrayList<String> m_TypeList;
    boolean m_bCancelScan;
    private Bitmap m_bpPrinterBitmap;
    private Context m_context;
    private int m_iItemSize;
    int m_iPort;
    LinkedList<String> m_strPrinterNameList;
    String m_strSSID;
    boolean mbStop;
    private String strProtectDiscoverPrinter;
    WifiManager wifi;

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.1 */
    class C04901 implements OnClickListener {
        C04901() {
        }

        public void onClick(View v) {
            ShowPrinterList.this.MDNS();
        }
    }

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.2 */
    class C04912 implements OnItemClickListener {
        C04912() {
        }

        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            ShowPrinterList.this.onPrinterListViewItemClicked(a, v, position, id);
        }
    }

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.3 */
    class C04923 implements OnCancelListener {
        C04923() {
        }

        public void onCancel(DialogInterface dialog) {
            if (ShowPrinterList.this.HavePrinterListListener()) {
                ShowPrinterList.this.m_PrinterListListener.BackStart();
            }
            ShowPrinterList.this.onBackClick();
        }
    }

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.4 */
    class C04934 implements Runnable {
        C04934() {
        }

        public void run() {
            ShowPrinterList.this.CloseJMDNS();
        }
    }

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.5 */
    class C04945 implements DialogInterface.OnClickListener {
        C04945() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ShowPrinterList.this.onBackClick();
        }
    }

    /* renamed from: com.hiti.utility.wifi.ShowPrinterList.6 */
    static /* synthetic */ class C04956 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$MDNS;

        static {
            $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$MDNS = new int[MDNS.values().length];
            try {
                $SwitchMap$com$hiti$utility$wifi$ShowPrinterList$MDNS[MDNS.Check.ordinal()] = ShowPrinterList.BACK_BUTTON_CLICKED_STATE;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public class DiscoverPrinter extends AsyncTask<String, String, Object> {

        /* renamed from: com.hiti.utility.wifi.ShowPrinterList.DiscoverPrinter.1 */
        class C08121 implements ServiceListener {
            C08121() {
            }

            public void serviceResolved(ServiceEvent event) {
                String[] IP = event.getInfo().getHostAddresses();
                String str_Type = event.getType();
                ShowPrinterList.this.LOG.m385i("service Resolved", event.getName());
                PrinterInfo getPrinterInfo = ShowPrinterList.this.ReturnPrinterInfo(IP[0]);
                if (event.getName().compareTo(XmlPullParser.NO_NAMESPACE) != 0 && !ShowPrinterList.this.mbStop) {
                    if (str_Type.compareTo("_htipp._tcp.local.") == 0) {
                        getPrinterInfo.m_Port = event.getInfo().getPort();
                        getPrinterInfo.m_strPrinterName = event.getName();
                        getPrinterInfo.str_usb_PID = event.getInfo().getPropertyString("usb_PID");
                        getPrinterInfo.str_usb_MFG = event.getInfo().getPropertyString("usb_MFG");
                        getPrinterInfo.str_usb_MDL = event.getInfo().getPropertyString("usb_MDL");
                        getPrinterInfo.str_pdl = event.getInfo().getPropertyString("pdl");
                        getPrinterInfo.str_prsz = event.getInfo().getPropertyString("prsz");
                        getPrinterInfo.iComplete += ShowPrinterList.BACK_BUTTON_CLICKED_STATE;
                    }
                    if (str_Type.compareTo("_htconf._tcp.local.") == 0) {
                        getPrinterInfo.m_strPrinterName = event.getName();
                        getPrinterInfo.str_mac = event.getInfo().getPropertyString("mac");
                        getPrinterInfo.str_conn = event.getInfo().getPropertyString("conn");
                        getPrinterInfo.str_ssid = event.getInfo().getPropertyString("ssid");
                        getPrinterInfo.str_rcrd = event.getInfo().getPropertyString("rcrd");
                        getPrinterInfo.str_srvmod = event.getInfo().getPropertyString("srvmod");
                        getPrinterInfo.str_ssidmod = event.getInfo().getPropertyString("ssidmod");
                        getPrinterInfo.iComplete += ShowPrinterList.BACK_BUTTON_CLICKED_STATE;
                    }
                    if (getPrinterInfo.str_conn.equals("AP")) {
                        ShowPrinterList.this.LOG.m385i("AP", String.valueOf(getPrinterInfo.str_conn));
                        ShowPrinterList.this.m_bCancelScan = true;
                        if (!ShowPrinterList.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                            ShowPrinterList.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        }
                        ShowPrinterList.this.LOG.m385i("m_strPrinterNameList", String.valueOf(ShowPrinterList.this.m_strPrinterNameList));
                        ShowPrinterList.this.m_DiscoverPrinter.cancel(true);
                        Message msg = new Message();
                        msg.what = RequestState.REQUEST_CLOSE_MDNS_SCAN_LIST;
                        Bundle data = new Bundle();
                        data.putString("PID", getPrinterInfo.str_usb_PID);
                        data.putString("SSID", getPrinterInfo.str_ssid);
                        msg.setData(data);
                        ShowPrinterList.this.m_SettingHandler.sendMessage(msg);
                    } else if (ShowPrinterList.this.m_bCancelScan && !ShowPrinterList.this.m_DiscoverPrinter.isCancelled()) {
                        ShowPrinterList.this.m_bCancelScan = false;
                        ShowPrinterList.this.m_DiscoverPrinter.cancel(true);
                    } else if (getPrinterInfo.iComplete == ShowPrinterList.SCAN_BUTTON_CLICKED_STATE && !ShowPrinterList.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                        ShowPrinterList.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        ShowPrinterList.this.UpdatePrinterListSSID(getPrinterInfo.str_usb_PID, getPrinterInfo.str_ssid);
                    }
                }
            }

            public void serviceRemoved(ServiceEvent event) {
                ShowPrinterList.this.LOG.m385i("service Removed", event.getName());
                String strPrinterName = event.getInfo().getName();
                if (ShowPrinterList.this.m_strPrinterNameList.contains(strPrinterName)) {
                    ShowPrinterList.this.m_strPrinterNameList.remove(strPrinterName);
                }
                ShowPrinterList.this.UpDatePrinterListName(strPrinterName);
            }

            public void serviceAdded(ServiceEvent event) {
                ShowPrinterList.this.LOG.m385i("Service Added", event.getName());
                ShowPrinterList.this.m_Jmdns.requestServiceInfo(event.getType(), event.getName(), true);
            }
        }

        protected Object doInBackground(String... AlbumName) {
            ShowPrinterList.this.m_Lock = ShowPrinterList.this.wifi.createMulticastLock(ShowPrinterList.this.strProtectDiscoverPrinter);
            ShowPrinterList.this.m_Lock.setReferenceCounted(true);
            ShowPrinterList.this.m_Lock.acquire();
            ShowPrinterList.this.m_TypeList = new ArrayList();
            ShowPrinterList.this.m_ServerListenerList = new ArrayList();
            ShowPrinterList.this.m_PrinterInfo = new ArrayList();
            ShowPrinterList.this.m_PrinterInfo.clear();
            ShowPrinterList.this.m_TypeList.add("_htipp._tcp.local.");
            ShowPrinterList.this.m_TypeList.add("_htconf._tcp.local.");
            try {
                if (ShowPrinterList.this.m_Jmdns == null) {
                    ShowPrinterList.this.LOG.m385i("m_Jmdns", "==NULL");
                } else {
                    ShowPrinterList.this.LOG.m385i("m_Jmdns", "!=NULL");
                }
                InetAddress group = InetAddress.getByName(DNSConstants.MDNS_GROUP);
                ShowPrinterList.this.m_Jmdns = JmDNS.create(group);
                for (int j = 0; j < ShowPrinterList.this.m_TypeList.size(); j += ShowPrinterList.BACK_BUTTON_CLICKED_STATE) {
                    JmDNS jmDNS = ShowPrinterList.this.m_Jmdns;
                    String str = (String) ShowPrinterList.this.m_TypeList.get(j);
                    ServiceListener serviceListener = new C08121();
                    jmDNS.addServiceListener(str, serviceListener);
                    ShowPrinterList.this.m_ServerListenerList.add(serviceListener);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(Object result) {
            ShowPrinterList.this.LOG.m385i("onPostExecute_stop:" + ShowPrinterList.this.mbStop, "DiscoverPrinter");
            if (ShowPrinterList.this.mbStop) {
                ShowPrinterList.this.BackFinish();
                return;
            }
            ShowPrinterList.this.LOG.m385i("onPostExecute NameList", String.valueOf(ShowPrinterList.this.m_strPrinterNameList));
            ShowPrinterList.this.StartTimerOut(ShowPrinterList.this.TIME_OUT);
        }
    }

    class ListHandler extends Handler {
        ListHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ShowPrinterList.MSG_SHOW_LIST /*17*/:
                    String strPID = msg.getData().getString("PID");
                    String strSSID = msg.getData().getString("SSID");
                    if (ShowPrinterList.this.mMDNStype == MDNS.List) {
                        HashMap<String, Object> map = new HashMap();
                        map.put("ItemImage", ShowPrinterList.this.GetListBimapByModel(strPID));
                        map.put("ItemName", strSSID);
                        ShowPrinterList.this.m_ArrayListItem.add(map);
                        if (!ShowPrinterList.this.IsShowing()) {
                            ShowPrinterList.this.m_PrinterListDialog.show();
                        }
                        ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.ScanDismiss, strPID, null, null, -1);
                        ShowPrinterList.this.m_PrinterSimpleAdapter.notifyDataSetChanged();
                        return;
                    }
                    ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.HaveSome, Integer.toHexString(Integer.parseInt(strPID)), strSSID, null, 0);
                case ShowPrinterList.MSG_SHOW_SANBAR /*18*/:
                    if (ShowPrinterList.this.m_ScanProgressBar != null) {
                        ShowPrinterList.this.m_ScanProgressBar.setVisibility(0);
                        ShowPrinterList.this.m_ScanButton.setEnabled(false);
                    }
                    if (ShowPrinterList.this.mMDNStype == MDNS.List) {
                        ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.ScanShow, null, null, null, -1);
                    }
                case ShowPrinterList.MSG_HIDE_SANBAR /*19*/:
                    if (ShowPrinterList.this.m_ScanProgressBar != null) {
                        ShowPrinterList.this.m_ScanProgressBar.setVisibility(8);
                        ShowPrinterList.this.m_ScanButton.setEnabled(true);
                    }
                case ShowPrinterList.MSG_REMOVE_ITEM /*20*/:
                    String strPrinterName = msg.getData().getString("NAME");
                    if (ShowPrinterList.this.m_ArrayListItem != null) {
                        for (int i = ShowPrinterList.this.m_ArrayListItem.size() - 1; i >= 0; i--) {
                            if (((HashMap) ShowPrinterList.this.m_ArrayListItem.get(i)).get("ItemName") == strPrinterName) {
                                ShowPrinterList.this.m_ArrayListItem.remove(i);
                            }
                        }
                    }
                    if (ShowPrinterList.this.m_PrinterSimpleAdapter != null) {
                        ShowPrinterList.this.m_PrinterSimpleAdapter.notifyDataSetChanged();
                    }
                case ShowPrinterList.MSG_CLEAR_ALL_ITEM /*21*/:
                    if (ShowPrinterList.this.m_ArrayListItem != null) {
                        ShowPrinterList.this.m_ArrayListItem.clear();
                    }
                    if (ShowPrinterList.this.mMDNStype == MDNS.List) {
                        ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.ScanDismiss, null, null, null, -1);
                    }
                case ShowPrinterList.MSG_SCAN_NO_PRINTER /*22*/:
                    ShowPrinterList.this.LOG.m385i("MSG_SCAN_NO_PRINTER", "type:" + ShowPrinterList.this.mMDNStype);
                    ShowPrinterList.this.ShowScanProgress(false);
                    if (ShowPrinterList.this.HavePrinterListListener()) {
                        ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.NoPrinter, null, null, null, -1);
                    }
                    if (ShowPrinterList.this.mMDNStype == MDNS.List) {
                        ShowPrinterList.this.m_PrinterListDialog.dismiss();
                        ShowPrinterList.this.onBackClick();
                    }
                default:
            }
        }
    }

    public enum MDNS {
        List,
        Check
    }

    class MyViewBinder implements ViewBinder {
        MyViewBinder() {
        }

        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (((view instanceof ImageView) & (data instanceof Bitmap)) == 0) {
                return false;
            }
            ImageView iv = (ImageView) view;
            iv.setImageBitmap((Bitmap) data);
            iv.getLayoutParams().height = ShowPrinterList.this.m_iItemSize;
            iv.getLayoutParams().width = ShowPrinterList.this.m_iItemSize;
            return true;
        }
    }

    class PrinterInfo {
        int iComplete;
        String m_IP;
        int m_Port;
        String m_strPrinterName;
        String str_conn;
        String str_mac;
        String str_pdl;
        String str_prsz;
        String str_rcrd;
        String str_srvmod;
        String str_ssid;
        String str_ssidmod;
        String str_usb_MDL;
        String str_usb_MFG;
        String str_usb_PID;

        public PrinterInfo() {
            this.m_IP = null;
            this.m_Port = 0;
            this.m_strPrinterName = null;
            this.str_usb_PID = null;
            this.str_usb_MFG = null;
            this.str_usb_MDL = null;
            this.str_pdl = null;
            this.str_prsz = null;
            this.str_mac = null;
            this.str_conn = null;
            this.str_ssid = null;
            this.str_rcrd = null;
            this.str_srvmod = null;
            this.str_ssidmod = null;
            this.iComplete = 0;
        }
    }

    class ReleasePrinter extends AsyncTask<Integer, String, Integer> {
        int m_GetPrinter;

        ReleasePrinter() {
            this.m_GetPrinter = -1;
        }

        protected Integer doInBackground(Integer... iRet) {
            ShowPrinterList.this.CloseJMDNS();
            if (iRet[0].intValue() == ShowPrinterList.PRINTER_ITEM_CLICKED_STATE) {
                this.m_GetPrinter = iRet[ShowPrinterList.BACK_BUTTON_CLICKED_STATE].intValue();
            }
            return iRet[0];
        }

        public void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(Integer result) {
            if (ShowPrinterList.this.mbStop) {
                ShowPrinterList.this.BackFinish();
            } else if (result.intValue() == ShowPrinterList.BACK_BUTTON_CLICKED_STATE) {
                ShowPrinterList.this.BackFinish();
            } else if (result.intValue() == ShowPrinterList.SCAN_BUTTON_CLICKED_STATE) {
                if (!ShowPrinterList.this.mbStop) {
                    ShowPrinterList.this.ScanPrinter();
                }
            } else if (result.intValue() == ShowPrinterList.PRINTER_ITEM_CLICKED_STATE && !ShowPrinterList.this.mbStop) {
                ShowPrinterList.this.onPrinterItemSelectFinish(this.m_GetPrinter);
                this.m_GetPrinter = -1;
            }
        }
    }

    public enum Scan {
        NoPrinter,
        HaveSome,
        ApMode,
        ScanShow,
        ScanDismiss
    }

    public class TimeOutTask extends TimerTask {
        public void run() {
            ShowPrinterList.this.LOG.m385i("RUN TIMEOUT", ":" + ShowPrinterList.this.m_strPrinterNameList);
            ShowPrinterList.this.ShowScanProgress(false);
            ShowPrinterList.this.StopTimerOut();
            if (ShowPrinterList.this.m_strPrinterNameList.isEmpty()) {
                ShowPrinterList.this.m_Handler.sendEmptyMessage(ShowPrinterList.MSG_SCAN_NO_PRINTER);
            } else {
                ShowPrinterList.this.m_Handler.sendEmptyMessage(ShowPrinterList.MSG_HIDE_SANBAR);
            }
            if (ShowPrinterList.this.mMDNStype == MDNS.List) {
                ShowPrinterList.this.CloseJMDNS();
            }
            if (ShowPrinterList.this.mMDNStype == MDNS.Check) {
                ShowPrinterList.this.MDNS();
            }
        }
    }

    class SettingHandler extends MSGHandler {

        /* renamed from: com.hiti.utility.wifi.ShowPrinterList.SettingHandler.1 */
        class C08131 implements MSGListener {
            C08131() {
            }

            public void Close() {
                ShowPrinterList.this.LeavePrinterList("Infra");
            }

            public void OKClick() {
            }

            public void CancelClick() {
            }
        }

        SettingHandler() {
        }

        public void handleMessage(Message msg) {
            Bundle buddle;
            String strTitle;
            String strMSG;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    ShowPrinterList.this.LOG.m385i("CheckingPassWord", "timeout");
                    ShowPrinterList.this.ShowScanProgress(false);
                    buddle = msg.getData();
                    strTitle = ShowPrinterList.this.m_context.getString(ShowPrinterList.this.mRID.R_STRING_ERROR_TITLT);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    ShowPrinterList.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_SETTING_ITEM_ALL /*338*/:
                    ShowPrinterList.this.ShowScanProgress(false);
                    if (ShowPrinterList.this.m_HitiPPR_GetAllSetting != null) {
                        ShowPrinterList.this.m_strSSID = ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSSID();
                        ShowPrinterList.this.LOG.m385i("REQUEST_SETTING_ITEM_ALL", "SSID:\u040e@" + String.valueOf(ShowPrinterList.this.m_strSSID));
                        if (ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSecurityKey() == null) {
                            ShowPrinterList.this.m_Password = XmlPullParser.NO_NAMESPACE;
                        } else {
                            ShowPrinterList.this.m_Password = ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSecurityKey();
                        }
                        ShowPrinterList.this.LOG.m385i("m_Password", String.valueOf(ShowPrinterList.this.m_Password));
                        if (ShowPrinterList.this.m_Password.isEmpty()) {
                            ShowPrinterList.this.LeavePrinterList("Infra");
                            return;
                        }
                        if (ShowPrinterList.this.m_PwdCheckDialog == null) {
                            ShowPrinterList.this.m_PwdCheckDialog = new PwdCheckDialog(ShowPrinterList.this.m_context);
                            ShowPrinterList.this.m_PwdCheckDialog.SetMSGListener(new C08131());
                        }
                        ShowPrinterList.this.m_PwdCheckDialog.ShowDialog(ShowPrinterList.this.m_Password);
                    }
                case RequestState.REQUEST_SETTING_ITEM_ALL_ERROR /*339*/:
                    ShowPrinterList.this.LOG.m385i("CheckingPassWord", "conn_error");
                    ShowPrinterList.this.ShowScanProgress(false);
                    buddle = msg.getData();
                    strTitle = ShowPrinterList.this.m_context.getString(ShowPrinterList.this.mRID.R_STRING_ERROR_TITLT);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    ShowPrinterList.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_CLOSE_MDNS_SCAN_LIST /*382*/:
                    ShowPrinterList.this.StopTimerOut();
                    if (ShowPrinterList.this.mMDNStype == MDNS.Check) {
                        if (ShowPrinterList.this.m_PrinterInfo.size() == ShowPrinterList.BACK_BUTTON_CLICKED_STATE) {
                            ShowPrinterList.this.m_IP = ((PrinterInfo) ShowPrinterList.this.m_PrinterInfo.get(0)).m_IP;
                            ShowPrinterList.this.m_iPort = ((PrinterInfo) ShowPrinterList.this.m_PrinterInfo.get(0)).m_Port;
                        }
                        Bundle data = msg.getData();
                        String strPID = null;
                        String strSSID = null;
                        if (data != null) {
                            strPID = data.getString("PID");
                            strSSID = data.getString("SSID");
                        }
                        if (strPID != null) {
                            strPID = Integer.toHexString(Integer.parseInt(strPID));
                        }
                        ShowPrinterList.this.m_PrinterListListener.ScanState(Scan.ApMode, String.valueOf(strPID), String.valueOf(strSSID), ShowPrinterList.this.m_IP, ShowPrinterList.this.m_iPort);
                        ShowPrinterList.this.MDNS();
                        return;
                    }
                    ShowPrinterList.this.ApModeAutoClick();
                default:
            }
        }
    }

    @SuppressLint({"NewApi"})
    public ShowPrinterList(Context context, MDNS type) {
        this.m_context = null;
        this.m_ReleasePrinter = null;
        this.m_DiscoverPrinter = null;
        this.strProtectDiscoverPrinter = "PROTECT_DISCOVER_PRINTER";
        this.m_TypeList = null;
        this.m_ServerListenerList = null;
        this.m_bpPrinterBitmap = null;
        this.m_PrinterListDialog = null;
        this.m_InfraListView = null;
        this.m_iItemSize = 0;
        this.m_Jmdns = null;
        this.m_Lock = null;
        this.wifi = null;
        this.m_GetPrinterServiceCount = 0;
        this.m_PrinterInfo = null;
        this.m_ArrayListItem = null;
        this.m_strPrinterNameList = null;
        this.m_PrinterSimpleAdapter = null;
        this.m_PrinterListView = null;
        this.m_PrinterListRelativeLayout = null;
        this.m_ScanProgressBar = null;
        this.m_ScanButton = null;
        this.m_PrinterListListener = null;
        this.m_HitiPPR_GetAllSetting = null;
        this.m_SettingHandler = null;
        this.m_Handler = null;
        this.m_Password = null;
        this.m_IP = null;
        this.m_iPort = 0;
        this.m_strSSID = null;
        this.m_PwdCheckDialog = null;
        this.m_bCancelScan = false;
        this.mRID = null;
        this.LOG = null;
        this.mMDNStype = null;
        this.mbStop = false;
        this.m_TimeOutTask = null;
        this.m_TimeOutTimer = null;
        this.TIME_OUT = 4200;
        this.m_context = context;
        this.mMDNStype = type;
        this.mRID = new ResourceId(context, Page.MDNS);
        this.LOG = new LogManager(0);
        this.m_Handler = new ListHandler();
        this.m_SettingHandler = new SettingHandler();
        switch (C04956.$SwitchMap$com$hiti$utility$wifi$ShowPrinterList$MDNS[type.ordinal()]) {
            case BACK_BUTTON_CLICKED_STATE /*1*/:
                this.m_strPrinterNameList = new LinkedList();
            default:
                PreparePrinterList();
        }
    }

    private void PreparePrinterList() {
        if (this.m_PrinterListDialog == null) {
            this.m_PrinterListDialog = new Dialog(this.m_context, this.mRID.R_STYLE_Dialog_MSG);
            this.m_InfraListView = this.m_PrinterListDialog.getLayoutInflater().inflate(this.mRID.R_LAYOUT_dialog_printer_list, null);
            this.m_PrinterListRelativeLayout = (RelativeLayout) this.m_InfraListView.findViewById(this.mRID.R_ID_m_PrinterListRelativeLayout);
            this.m_ScanButton = (Button) this.m_InfraListView.findViewById(this.mRID.R_ID_m_ScanButton);
            this.m_ScanButton.setOnClickListener(new C04901());
            this.m_ScanProgressBar = (ProgressBar) this.m_InfraListView.findViewById(this.mRID.R_ID_m_ScanProgressBar);
            this.m_PrinterListView = (ListView) this.m_InfraListView.findViewById(this.mRID.R_ID_m_PrinterListView);
            this.m_PrinterListView.setOnItemClickListener(new C04912());
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) this.m_context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            this.m_iItemSize = dm.widthPixels / 6;
            this.m_PrinterListRelativeLayout.getLayoutParams().width = (dm.widthPixels / PRINTER_ITEM_CLICKED_STATE) * SCAN_BUTTON_CLICKED_STATE;
            this.m_PrinterListDialog.setContentView(this.m_InfraListView);
            this.m_bpPrinterBitmap = BitmapFactory.decodeStream(this.m_context.getResources().openRawResource(this.mRID.R_DRAWABLE_printer));
            Bitmap tempBmp = this.m_bpPrinterBitmap;
            this.m_bpPrinterBitmap = BitmapMonitor.CreateScaledBitmap(this.m_bpPrinterBitmap, this.m_iItemSize, this.m_iItemSize, true).GetBitmap();
            if (!tempBmp.isRecycled()) {
                tempBmp.recycle();
            }
            this.m_PrinterListDialog.setOnCancelListener(new C04923());
            this.m_ArrayListItem = new ArrayList();
            this.m_strPrinterNameList = new LinkedList();
            Context context = this.m_context;
            List list = this.m_ArrayListItem;
            int i = this.mRID.R_LAYOUT_item_printer_list;
            String[] strArr = new String[SCAN_BUTTON_CLICKED_STATE];
            strArr[0] = "ItemImage";
            strArr[BACK_BUTTON_CLICKED_STATE] = "ItemName";
            int[] iArr = new int[SCAN_BUTTON_CLICKED_STATE];
            iArr[0] = this.mRID.R_ID_m_PrinterImageView;
            iArr[BACK_BUTTON_CLICKED_STATE] = this.mRID.R_ID_m_PrinterTextView;
            this.m_PrinterSimpleAdapter = new SimpleAdapter(context, list, i, strArr, iArr);
            this.m_PrinterSimpleAdapter.setViewBinder(new MyViewBinder());
            this.m_PrinterListView.setAdapter(this.m_PrinterSimpleAdapter);
            ShowScanProgress(false);
        }
    }

    public void SetPrinterListListener(PrinterListListener listener) {
        if (this.m_PrinterListListener == null) {
            this.m_PrinterListListener = listener;
        }
    }

    private boolean HavePrinterListListener() {
        if (this.m_PrinterListListener != null) {
            return true;
        }
        return false;
    }

    public void Show() {
        if (this.mMDNStype != MDNS.List) {
            MDNS();
        } else if (this.m_PrinterListDialog != null) {
            this.mbStop = false;
            MDNS();
        }
    }

    public boolean IsShowing() {
        if (this.m_PrinterListDialog == null || !this.m_PrinterListDialog.isShowing()) {
            return false;
        }
        return true;
    }

    public void ListClose() {
        if (this.m_PrinterListDialog != null) {
            this.m_PrinterListDialog.dismiss();
            this.m_strPrinterNameList.clear();
            this.m_Handler.sendEmptyMessage(MSG_CLEAR_ALL_ITEM);
        }
    }

    private void BackFinish() {
        this.LOG.m385i("BackFinish ", "mbStop:" + this.mbStop);
        ShowScanProgress(false);
        if (this.m_PrinterListDialog != null) {
            this.m_PrinterListDialog.dismiss();
        }
        if (HavePrinterListListener()) {
            this.m_PrinterListListener.BackFinish();
        }
    }

    private void MDNS() {
        ShowScanProgress(true);
        this.m_strPrinterNameList.clear();
        this.m_Handler.sendEmptyMessage(MSG_CLEAR_ALL_ITEM);
        if (HavePrinterListListener()) {
            this.m_PrinterListListener.IsMdnsState(true);
        }
        boolean bConnected = WifiSetting.IsWifiConnected(this.m_context);
        if (this.mMDNStype != MDNS.Check || bConnected) {
            if (this.wifi == null) {
                this.wifi = (WifiManager) this.m_context.getSystemService("wifi");
            }
            try {
                if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
                    this.m_ReleasePrinter = new ReleasePrinter();
                    ReleasePrinter releasePrinter = this.m_ReleasePrinter;
                    Integer[] numArr = new Integer[BACK_BUTTON_CLICKED_STATE];
                    numArr[0] = Integer.valueOf(SCAN_BUTTON_CLICKED_STATE);
                    releasePrinter.execute(numArr);
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        StartTimerOut(this.TIME_OUT);
    }

    void ScanPrinter() {
        if (this.m_DiscoverPrinter == null || this.m_DiscoverPrinter.getStatus() == Status.FINISHED) {
            this.LOG.m385i("execute", "m_DiscoverPrinter");
            ShowScanProgress(true);
            this.m_DiscoverPrinter = new DiscoverPrinter();
            this.m_DiscoverPrinter.execute(new String[0]);
        }
    }

    private void ApModeAutoClick() {
        try {
            ShowScanProgress(true);
            if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
                this.m_ReleasePrinter = new ReleasePrinter();
                ReleasePrinter releasePrinter = this.m_ReleasePrinter;
                Integer[] numArr = new Integer[SCAN_BUTTON_CLICKED_STATE];
                numArr[0] = Integer.valueOf(PRINTER_ITEM_CLICKED_STATE);
                numArr[BACK_BUTTON_CLICKED_STATE] = Integer.valueOf(0);
                releasePrinter.execute(numArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdatePrinterListSSID(String strPID, String strSSID) {
        Message msg = new Message();
        msg.what = MSG_SHOW_LIST;
        Bundle bundle = new Bundle();
        bundle.putString("PID", strPID);
        bundle.putString("SSID", strSSID);
        msg.setData(bundle);
        this.m_Handler.sendMessage(msg);
        if (this.mMDNStype == MDNS.Check) {
            MDNS();
        }
    }

    private void UpDatePrinterListName(String strName) {
        Message msg = new Message();
        msg.what = MSG_REMOVE_ITEM;
        Bundle bundle = new Bundle();
        bundle.putString("NAME", strName);
        msg.setData(bundle);
        this.m_Handler.sendMessage(msg);
    }

    void RemoveMDNS() {
        Thread thread = new Thread(new C04934());
    }

    void CloseJMDNS() {
        this.LOG.m385i("CloseJMDNS", "type:" + this.mMDNStype);
        StopTimerOut();
        if (this.m_DiscoverPrinter != null) {
            try {
                this.m_DiscoverPrinter.cancel(true);
                this.m_DiscoverPrinter = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.m_Jmdns != null) {
            if (this.m_ServerListenerList != null) {
                if (this.m_TypeList.size() == this.m_ServerListenerList.size()) {
                    for (int i = 0; i < this.m_TypeList.size(); i += BACK_BUTTON_CLICKED_STATE) {
                        this.m_Jmdns.removeServiceListener((String) this.m_TypeList.get(i), (ServiceListener) this.m_ServerListenerList.get(i));
                    }
                }
                this.m_ServerListenerList.clear();
            }
            this.m_Jmdns.unregisterAllServices();
            try {
                this.m_Jmdns.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.m_Jmdns = null;
        }
        if (this.m_Lock != null) {
            this.m_Lock.release();
            this.m_Lock = null;
        }
    }

    private Bitmap GetListBimapByModel(String strModel) {
        Bitmap bmp;
        Bitmap tempBmp = null;
        BitmapMonitorResult bmr = null;
        InputStream is = null;
        String strPID = Integer.toHexString(Integer.parseInt(strModel));
        if (WirelessType.TYPE_P310W.contains(strPID) || WirelessType.TYPE_P461.contains(strPID)) {
            is = this.m_context.getResources().openRawResource(this.mRID.R_DRAWABLE_p310w);
        } else if (WirelessType.TYPE_P520L.contains(strPID)) {
            is = this.m_context.getResources().openRawResource(this.mRID.R_DRAWABLE_p520l);
        } else if (WirelessType.TYPE_P750L.contains(strPID)) {
            is = this.m_context.getResources().openRawResource(this.mRID.R_DRAWABLE_p750l);
        }
        if (is != null) {
            bmp = BitmapFactory.decodeStream(is);
            tempBmp = bmp;
            bmr = BitmapMonitor.CreateScaledBitmap(bmp, this.m_iItemSize, this.m_iItemSize, true);
        }
        if (bmr == null || !bmr.IsSuccess()) {
            bmp = this.m_bpPrinterBitmap;
        } else {
            bmp = bmr.GetBitmap();
        }
        if (!(tempBmp == null || tempBmp.isRecycled())) {
            tempBmp.recycle();
        }
        return bmp;
    }

    PrinterInfo ReturnPrinterInfo(String ip) {
        for (int i = 0; i < this.m_PrinterInfo.size(); i += BACK_BUTTON_CLICKED_STATE) {
            if (((PrinterInfo) this.m_PrinterInfo.get(i)).m_IP.compareTo(ip) == 0) {
                this.LOG.m385i("Get", "ReturnPrinterInfo");
                return (PrinterInfo) this.m_PrinterInfo.get(i);
            }
        }
        this.LOG.m385i("New", "ReturnPrinterInfo");
        PrinterInfo printerInfo = new PrinterInfo();
        printerInfo.m_IP = ip;
        printerInfo.iComplete = 0;
        this.m_PrinterInfo.add(printerInfo);
        return printerInfo;
    }

    public void Stop() {
        this.mbStop = true;
        this.LOG.m385i("Stop()", "mbStop:" + this.mbStop);
        StopTimerOut();
        onBackClick();
    }

    public void Cancel() {
        this.mbStop = true;
        this.LOG.m385i("Cancel()", "Cacnel:" + this.mbStop);
        StopTimerOut();
        if (this.m_ReleasePrinter != null) {
            this.m_ReleasePrinter.cancel(true);
        }
        if (this.m_DiscoverPrinter != null) {
            this.m_DiscoverPrinter.cancel(true);
        }
    }

    private void onBackClick() {
        this.mbStop = true;
        this.LOG.m385i("onBackClick ", "mbStop:" + this.mbStop);
        try {
            if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
                ShowScanProgress(true);
                this.m_ReleasePrinter = new ReleasePrinter();
                ReleasePrinter releasePrinter = this.m_ReleasePrinter;
                Integer[] numArr = new Integer[BACK_BUTTON_CLICKED_STATE];
                numArr[0] = Integer.valueOf(BACK_BUTTON_CLICKED_STATE);
                releasePrinter.execute(numArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPrinterListViewItemClicked(AdapterView<?> adapterView, View v, int position, long id) {
        this.LOG.m385i("onPrinterListViewItemClicked", "PrinterActivity");
        if (this.m_ScanButton.isEnabled() && !this.m_ScanProgressBar.isShown()) {
            ShowScanProgress(true);
            try {
                if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
                    this.m_ReleasePrinter = new ReleasePrinter();
                    ReleasePrinter releasePrinter = this.m_ReleasePrinter;
                    Integer[] numArr = new Integer[SCAN_BUTTON_CLICKED_STATE];
                    numArr[0] = Integer.valueOf(PRINTER_ITEM_CLICKED_STATE);
                    numArr[BACK_BUTTON_CLICKED_STATE] = Integer.valueOf(position);
                    releasePrinter.execute(numArr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void onPrinterItemSelectFinish(int position) {
        if (position <= this.m_strPrinterNameList.size() - 1) {
            String strName = (String) this.m_strPrinterNameList.get(position);
            String strConn = XmlPullParser.NO_NAMESPACE;
            if (this.m_PrinterInfo != null) {
                for (int i = 0; i < this.m_PrinterInfo.size(); i += BACK_BUTTON_CLICKED_STATE) {
                    if (strName.compareTo(((PrinterInfo) this.m_PrinterInfo.get(i)).m_strPrinterName) == 0) {
                        this.m_IP = ((PrinterInfo) this.m_PrinterInfo.get(i)).m_IP;
                        this.m_iPort = ((PrinterInfo) this.m_PrinterInfo.get(i)).m_Port;
                        this.m_strSSID = ((PrinterInfo) this.m_PrinterInfo.get(i)).str_ssid;
                        strConn = ((PrinterInfo) this.m_PrinterInfo.get(i)).str_conn;
                        break;
                    }
                }
            }
            this.LOG.m385i("onPrinterItemSelectFinish", "strConn:" + String.valueOf(strConn));
            if (strConn.equals("AP")) {
                LeavePrinterList("AP");
            } else {
                CheckingPassWord();
            }
        }
    }

    private void CheckingPassWord() {
        this.LOG.m385i("CheckingPassWord", "CheckingPassWord");
        if (this.m_HitiPPR_GetAllSetting != null) {
            this.m_HitiPPR_GetAllSetting.Stop();
        }
        this.m_HitiPPR_GetAllSetting = new HitiPPR_GetAllSetting(this.m_context, this.m_IP, this.m_iPort, this.m_SettingHandler);
        this.m_HitiPPR_GetAllSetting.start();
    }

    private void LeavePrinterList(String strConn) {
        ShowScanProgress(false);
        if (!this.mbStop) {
            ListClose();
            if (HavePrinterListListener()) {
                this.m_PrinterListListener.PrinterListFinish(this.m_strSSID, this.m_IP, this.m_iPort, strConn);
            }
        }
    }

    public void ShowErrorDialog(String strMessage, String strTitle, int iResId) {
        Builder errorDialog = new Builder(this.m_context);
        errorDialog.setCancelable(false);
        errorDialog.setIcon(iResId);
        errorDialog.setTitle(strTitle);
        errorDialog.setMessage(strMessage);
        errorDialog.setPositiveButton(this.m_context.getString(this.mRID.R_STRING_OK), new C04945());
        errorDialog.show();
    }

    private void ShowScanProgress(boolean bShow) {
        this.LOG.m385i("ShowScanProgress", String.valueOf(bShow));
        if (this.mMDNStype != MDNS.List) {
            return;
        }
        if (bShow) {
            this.m_Handler.sendEmptyMessage(MSG_SHOW_SANBAR);
        } else {
            this.m_Handler.sendEmptyMessage(MSG_HIDE_SANBAR);
        }
    }

    public void StartTimerOut(int iTimeOut) {
        this.LOG.m385i("StartTimerOut", "timer=" + this.m_TimeOutTimer);
        if (this.m_TimeOutTimer == null) {
            if (iTimeOut == 0) {
                iTimeOut = this.TIME_OUT;
            }
            this.LOG.m385i("SET TIMEOUT", "SET TIMEOUT");
            this.m_TimeOutTimer = new Timer(true);
            this.m_TimeOutTask = new TimeOutTask();
            this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) iTimeOut);
        }
    }

    public void StopTimerOut() {
        this.LOG.m385i("STOP TIMEOUT", "type:" + this.mMDNStype);
        if (this.m_TimeOutTimer != null) {
            this.m_TimeOutTimer.cancel();
            this.m_TimeOutTimer = null;
        }
        if (this.m_TimeOutTask != null) {
            this.m_TimeOutTask.cancel();
            this.m_TimeOutTask = null;
        }
    }
}
