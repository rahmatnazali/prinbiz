package com.hiti.utility;

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
import com.hiti.prinbiz.C0349R;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.printerprotocol.request.HitiPPR_GetAllSetting;
import com.hiti.utility.dialog.MSGListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private static final int MSG_SHOW_LIST = 17;
    private static final int MSG_SHOW_SANBAR = 18;
    private static final int PRINTER_ITEM_CLICKED_STATE = 3;
    private static final int SCAN_BUTTON_CLICKED_STATE = 2;
    boolean mStop;
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
    private ArrayList<String> m_TypeList;
    boolean m_bBackState;
    boolean m_bCancelScan;
    private Bitmap m_bpPrinterBitmap;
    private Context m_context;
    private int m_iItemSize;
    int m_iPort;
    LinkedList<String> m_strPrinterNameList;
    String m_strSSID;
    private String strProtectDiscoverPrinter;
    WifiManager wifi;

    /* renamed from: com.hiti.utility.ShowPrinterList.1 */
    class C04531 implements OnClickListener {
        C04531() {
        }

        public void onClick(View v) {
            ShowPrinterList.this.MDNS();
        }
    }

    /* renamed from: com.hiti.utility.ShowPrinterList.2 */
    class C04542 implements OnItemClickListener {
        C04542() {
        }

        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            ShowPrinterList.this.onPrinterListViewItemClicked(a, v, position, id);
        }
    }

    /* renamed from: com.hiti.utility.ShowPrinterList.3 */
    class C04553 implements OnCancelListener {
        C04553() {
        }

        public void onCancel(DialogInterface dialog) {
            if (ShowPrinterList.this.HavePrinterListListener()) {
                ShowPrinterList.this.m_PrinterListListener.BackStart();
            }
            ShowPrinterList.this.onBackClick();
        }
    }

    /* renamed from: com.hiti.utility.ShowPrinterList.4 */
    class C04564 implements DialogInterface.OnClickListener {
        C04564() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ShowPrinterList.this.onBackClick();
        }
    }

    class DiscoverPrinter extends AsyncTask<String, String, Object> {

        /* renamed from: com.hiti.utility.ShowPrinterList.DiscoverPrinter.1 */
        class C08041 implements ServiceListener {
            C08041() {
            }

            public void serviceResolved(ServiceEvent event) {
                String[] IP = event.getInfo().getHostAddresses();
                String str_Type = event.getType();
                PrinterInfo getPrinterInfo = ShowPrinterList.this.ReturnPrinterInfo(IP[0]);
                if (event.getName().compareTo(XmlPullParser.NO_NAMESPACE) != 0) {
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
                        ShowPrinterList.this.m_bCancelScan = true;
                        if (!ShowPrinterList.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                            ShowPrinterList.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        }
                        ShowPrinterList.this.m_DiscoverPrinter.cancel(true);
                        Message msg = new Message();
                        msg.what = RequestState.REQUEST_CLOSE_MDNS_SCAN_LIST;
                        ShowPrinterList.this.m_SettingHandler.sendMessage(msg);
                    } else if (ShowPrinterList.this.m_bCancelScan && !ShowPrinterList.this.m_DiscoverPrinter.isCancelled()) {
                        ShowPrinterList.this.m_bCancelScan = false;
                        ShowPrinterList.this.m_DiscoverPrinter.cancel(true);
                    } else if (getPrinterInfo.iComplete == ShowPrinterList.SCAN_BUTTON_CLICKED_STATE && !ShowPrinterList.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                        ShowPrinterList.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        ShowPrinterList.this.UpDatePrinterListSSID(getPrinterInfo.str_usb_PID, getPrinterInfo.str_ssid);
                    }
                }
            }

            public void serviceRemoved(ServiceEvent event) {
                String strPrinterName = event.getInfo().getName();
                if (ShowPrinterList.this.m_strPrinterNameList.contains(strPrinterName)) {
                    ShowPrinterList.this.m_strPrinterNameList.remove(strPrinterName);
                }
                ShowPrinterList.this.UpDatePrinterListName(strPrinterName);
            }

            public void serviceAdded(ServiceEvent event) {
                ShowPrinterList.this.m_Jmdns.requestServiceInfo(event.getType(), event.getName(), true);
            }
        }

        DiscoverPrinter() {
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
                InetAddress group = InetAddress.getByName(DNSConstants.MDNS_GROUP);
                ShowPrinterList.this.m_Jmdns = JmDNS.create(group);
                for (int j = 0; j < ShowPrinterList.this.m_TypeList.size(); j += ShowPrinterList.BACK_BUTTON_CLICKED_STATE) {
                    JmDNS jmDNS = ShowPrinterList.this.m_Jmdns;
                    String str = (String) ShowPrinterList.this.m_TypeList.get(j);
                    ServiceListener serviceListener = new C08041();
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
            ShowPrinterList.this.ShowScanProgress(false);
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
                    HashMap<String, Object> map = new HashMap();
                    map.put("ItemImage", ShowPrinterList.this.GetListBimapByModel(strPID));
                    map.put("ItemName", strSSID);
                    ShowPrinterList.this.m_ArrayListItem.add(map);
                    ShowPrinterList.this.m_PrinterSimpleAdapter.notifyDataSetChanged();
                case ShowPrinterList.MSG_SHOW_SANBAR /*18*/:
                    ShowPrinterList.this.m_ScanProgressBar.setVisibility(0);
                    ShowPrinterList.this.m_ScanButton.setEnabled(false);
                case ShowPrinterList.MSG_HIDE_SANBAR /*19*/:
                    ShowPrinterList.this.m_ScanProgressBar.setVisibility(8);
                    ShowPrinterList.this.m_ScanButton.setEnabled(true);
                case ShowPrinterList.MSG_REMOVE_ITEM /*20*/:
                    String strPrinterName = msg.getData().getString("NAME");
                    for (int i = ShowPrinterList.this.m_ArrayListItem.size() - 1; i >= 0; i--) {
                        if (((HashMap) ShowPrinterList.this.m_ArrayListItem.get(i)).get("ItemName") == strPrinterName) {
                            ShowPrinterList.this.m_ArrayListItem.remove(i);
                        }
                    }
                    ShowPrinterList.this.m_PrinterSimpleAdapter.notifyDataSetChanged();
                case ShowPrinterList.MSG_CLEAR_ALL_ITEM /*21*/:
                    ShowPrinterList.this.m_ArrayListItem.clear();
                default:
            }
        }
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
            if (ShowPrinterList.this.IsStop()) {
                ShowPrinterList.this.m_bBackState = true;
            }
            if (result.intValue() == ShowPrinterList.BACK_BUTTON_CLICKED_STATE || ShowPrinterList.this.m_bBackState) {
                ShowPrinterList.this.ShowScanProgress(false);
                if (ShowPrinterList.this.m_PrinterListDialog != null) {
                    ShowPrinterList.this.m_PrinterListDialog.dismiss();
                }
                if (ShowPrinterList.this.HavePrinterListListener()) {
                    ShowPrinterList.this.m_PrinterListListener.BackFinish();
                }
            } else if (result.intValue() == ShowPrinterList.SCAN_BUTTON_CLICKED_STATE) {
                ShowPrinterList.this.ScanPrinter();
            } else if (result.intValue() == ShowPrinterList.PRINTER_ITEM_CLICKED_STATE) {
                ShowPrinterList.this.onPrinterItemSelectFinish(this.m_GetPrinter);
                this.m_GetPrinter = -1;
            }
        }
    }

    class SettingHandler extends MSGHandler {

        /* renamed from: com.hiti.utility.ShowPrinterList.SettingHandler.1 */
        class C08051 implements MSGListener {
            C08051() {
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
                    ShowPrinterList.this.ShowScanProgress(false);
                    buddle = msg.getData();
                    strTitle = ShowPrinterList.this.m_context.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    ShowPrinterList.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_SETTING_ITEM_ALL /*338*/:
                    ShowPrinterList.this.ShowScanProgress(false);
                    if (ShowPrinterList.this.m_HitiPPR_GetAllSetting != null) {
                        String m_SSID = ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSSID();
                        if (ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSecurityKey() == null) {
                            ShowPrinterList.this.m_Password = XmlPullParser.NO_NAMESPACE;
                        } else {
                            ShowPrinterList.this.m_Password = ShowPrinterList.this.m_HitiPPR_GetAllSetting.GetAttrSecurityKey();
                        }
                        if (ShowPrinterList.this.m_Password.isEmpty()) {
                            ShowPrinterList.this.LeavePrinterList("Infra");
                            return;
                        }
                        if (ShowPrinterList.this.m_PwdCheckDialog == null) {
                            ShowPrinterList.this.m_PwdCheckDialog = new PwdCheckDialog(ShowPrinterList.this.m_context);
                            ShowPrinterList.this.m_PwdCheckDialog.SetMSGListener(new C08051());
                        }
                        ShowPrinterList.this.m_PwdCheckDialog.ShowDialog(ShowPrinterList.this.m_Password);
                    }
                case RequestState.REQUEST_SETTING_ITEM_ALL_ERROR /*339*/:
                    ShowPrinterList.this.ShowScanProgress(false);
                    buddle = msg.getData();
                    strTitle = ShowPrinterList.this.m_context.getString(C0349R.string.ERROR);
                    strMSG = null;
                    if (buddle != null) {
                        strMSG = buddle.getString(MSGHandler.MSG);
                    }
                    ShowPrinterList.this.ShowErrorDialog(strMSG, strTitle, 17301569);
                case RequestState.REQUEST_CLOSE_MDNS_SCAN_LIST /*382*/:
                    ShowPrinterList.this.ApModeAutoClick();
                default:
            }
        }
    }

    @SuppressLint({"NewApi"})
    public ShowPrinterList(Context context) {
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
        this.m_bBackState = false;
        this.mStop = false;
        this.m_context = context;
        if (this.m_PrinterListDialog == null) {
            this.m_PrinterListDialog = new Dialog(this.m_context, C0349R.style.Dialog_MSG);
            this.m_InfraListView = this.m_PrinterListDialog.getLayoutInflater().inflate(C0349R.layout.dialog_printer_list, null);
            this.m_PrinterListRelativeLayout = (RelativeLayout) this.m_InfraListView.findViewById(C0349R.id.m_PrinterListRelativeLayout);
            this.m_ScanButton = (Button) this.m_InfraListView.findViewById(C0349R.id.m_ScanButton);
            this.m_ScanButton.setOnClickListener(new C04531());
            this.m_ScanProgressBar = (ProgressBar) this.m_InfraListView.findViewById(C0349R.id.m_ScanProgressBar);
            this.m_PrinterListView = (ListView) this.m_InfraListView.findViewById(C0349R.id.m_PrinterListView);
            this.m_PrinterListView.setOnItemClickListener(new C04542());
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) this.m_context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            this.m_iItemSize = dm.widthPixels / 6;
            this.m_PrinterListRelativeLayout.getLayoutParams().width = (dm.widthPixels / PRINTER_ITEM_CLICKED_STATE) * SCAN_BUTTON_CLICKED_STATE;
            this.m_PrinterListDialog.setContentView(this.m_InfraListView);
            this.m_bpPrinterBitmap = BitmapFactory.decodeStream(this.m_context.getResources().openRawResource(C0349R.drawable.printer));
            Bitmap tempBmp = this.m_bpPrinterBitmap;
            this.m_bpPrinterBitmap = BitmapMonitor.CreateScaledBitmap(this.m_bpPrinterBitmap, this.m_iItemSize, this.m_iItemSize, true).GetBitmap();
            if (!tempBmp.isRecycled()) {
                tempBmp.recycle();
            }
            this.m_PrinterListDialog.setOnCancelListener(new C04553());
            this.m_ArrayListItem = new ArrayList();
            this.m_strPrinterNameList = new LinkedList();
            Context context2 = this.m_context;
            List list = this.m_ArrayListItem;
            String[] strArr = new String[SCAN_BUTTON_CLICKED_STATE];
            strArr[0] = "ItemImage";
            strArr[BACK_BUTTON_CLICKED_STATE] = "ItemName";
            this.m_PrinterSimpleAdapter = new SimpleAdapter(context2, list, C0349R.layout.item_printer_list, strArr, new int[]{C0349R.id.m_PrinterImageView, C0349R.id.m_PrinterTextView});
            this.m_PrinterSimpleAdapter.setViewBinder(new MyViewBinder());
            this.m_PrinterListView.setAdapter(this.m_PrinterSimpleAdapter);
            this.m_Handler = new ListHandler();
            this.m_SettingHandler = new SettingHandler();
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
        if (this.m_PrinterListDialog != null && !this.m_PrinterListDialog.isShowing()) {
            this.m_bBackState = false;
            this.m_PrinterListDialog.show();
            MDNS();
        }
    }

    public void Stop() {
        this.mStop = true;
    }

    private boolean IsStop() {
        return this.mStop;
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
            UpDatePrinterListClear();
        }
    }

    private void MDNS() {
        ShowScanProgress(true);
        if (HavePrinterListListener()) {
            this.m_PrinterListListener.IsBackStateOnMDNS(true);
        }
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ScanPrinter() {
        this.m_strPrinterNameList.clear();
        UpDatePrinterListClear();
        if (this.m_DiscoverPrinter == null || this.m_DiscoverPrinter.getStatus() == Status.FINISHED) {
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

    private void UpDatePrinterListSSID(String strPID, String strSSID) {
        Message msg = new Message();
        msg.what = MSG_SHOW_LIST;
        Bundle bundle = new Bundle();
        bundle.putString("PID", strPID);
        bundle.putString("SSID", strSSID);
        msg.setData(bundle);
        this.m_Handler.sendMessage(msg);
    }

    private void UpDatePrinterListName(String strName) {
        Message msg = new Message();
        msg.what = MSG_REMOVE_ITEM;
        Bundle bundle = new Bundle();
        bundle.putString("NAME", strName);
        msg.setData(bundle);
        this.m_Handler.sendMessage(msg);
    }

    private void UpDatePrinterListClear() {
        this.m_Handler.sendEmptyMessage(MSG_CLEAR_ALL_ITEM);
    }

    void CloseJMDNS() {
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
                for (int i = 0; i < this.m_TypeList.size(); i += BACK_BUTTON_CLICKED_STATE) {
                    this.m_Jmdns.removeServiceListener((String) this.m_TypeList.get(i), (ServiceListener) this.m_ServerListenerList.get(i));
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
            is = this.m_context.getResources().openRawResource(C0349R.drawable.p310w);
        } else if (WirelessType.TYPE_P520L.contains(strPID)) {
            is = this.m_context.getResources().openRawResource(C0349R.drawable.p520l);
        } else if (WirelessType.TYPE_P750L.contains(strPID)) {
            is = this.m_context.getResources().openRawResource(C0349R.drawable.p750l);
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
                return (PrinterInfo) this.m_PrinterInfo.get(i);
            }
        }
        PrinterInfo printerInfo = new PrinterInfo();
        printerInfo.m_IP = ip;
        printerInfo.iComplete = 0;
        this.m_PrinterInfo.add(printerInfo);
        return printerInfo;
    }

    public void onBackClick() {
        this.m_bBackState = true;
        ShowScanProgress(true);
        try {
            if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
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
        if (this.m_ScanButton.isEnabled()) {
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
        if (!IsStop() && position < this.m_strPrinterNameList.size()) {
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
            if (strConn.equals("AP")) {
                LeavePrinterList("AP");
            } else {
                CheckingPassWord();
            }
        }
    }

    private void CheckingPassWord() {
        if (this.m_HitiPPR_GetAllSetting != null) {
            this.m_HitiPPR_GetAllSetting.Stop();
        }
        this.m_HitiPPR_GetAllSetting = new HitiPPR_GetAllSetting(this.m_context, this.m_IP, this.m_iPort, this.m_SettingHandler);
        this.m_HitiPPR_GetAllSetting.start();
    }

    private void LeavePrinterList(String strConn) {
        ShowScanProgress(false);
        if (!this.m_bBackState) {
            if (this.m_PrinterListDialog != null) {
                this.m_PrinterListDialog.dismiss();
            }
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
        errorDialog.setPositiveButton(this.m_context.getString(C0349R.string.OK), new C04564());
        errorDialog.show();
    }

    private void ShowScanProgress(boolean bShow) {
        if (bShow) {
            this.m_Handler.sendEmptyMessage(MSG_SHOW_SANBAR);
        } else {
            this.m_Handler.sendEmptyMessage(MSG_HIDE_SANBAR);
        }
    }
}
