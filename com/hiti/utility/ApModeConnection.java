package com.hiti.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.google.android.gms.common.ConnectionResult;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.prinbiz.C0349R;
import com.hiti.printerprotocol.MSGHandler;
import com.hiti.printerprotocol.RequestState;
import com.hiti.printerprotocol.SettingStep;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommand;
import com.hiti.printerprotocol.request.HitiPPR_PrinterCommandNew;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.tftp.TFTPClient;

@SuppressLint({"NewApi"})
public class ApModeConnection {
    ArrayList<ClientScanResult> m_ARPIpList;
    Handler m_ARPhandler;
    Runnable m_ARPrunnable;
    HandlerThread m_ARPthread;
    ApModeCommand m_ApCommand;
    ApModeListener m_ApModeForListListener;
    ApModeHandler m_ApModeHandler;
    ApModeListener m_ApModeListener;
    ArrayList<HashMap<String, Object>> m_ArrayListItem;
    Context m_Context;
    Handler m_Handler;
    Dialog m_PrinterListDialog;
    PrinterListListener m_PrinterListListener;
    RelativeLayout m_PrinterListRelativeLayout;
    ListView m_PrinterListView;
    SimpleAdapter m_PrinterSimpleAdapter;
    Button m_ScanButton;
    ProgressBar m_ScanProgressBar;
    boolean m_bStop;
    Bitmap m_bpPrinterBitmap;
    int m_iItemSize;
    int m_iTimeOut;
    LinkedList<String> m_strPrinterNameList;

    /* renamed from: com.hiti.utility.ApModeConnection.1 */
    class C04421 implements OnClickListener {
        C04421() {
        }

        public void onClick(View v) {
            Log.e("ApModeConnection", "Scan");
            ApModeConnection.this.Scan();
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.2 */
    class C04432 implements OnItemClickListener {
        C04432() {
        }

        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            ApModeConnection.this.onPrinterListViewItemClicked(a, v, position, id);
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.4 */
    class C04444 implements Runnable {
        final /* synthetic */ boolean val$bShow;

        C04444(boolean z) {
            this.val$bShow = z;
        }

        public void run() {
            if (this.val$bShow) {
                ApModeConnection.this.m_ScanProgressBar.setVisibility(0);
                ApModeConnection.this.m_ScanButton.setEnabled(false);
                return;
            }
            ApModeConnection.this.m_ScanProgressBar.setVisibility(8);
            ApModeConnection.this.m_ScanButton.setEnabled(true);
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.5 */
    class C04455 implements Runnable {
        final /* synthetic */ boolean val$onlyReachables;
        final /* synthetic */ int val$reachableTimeout;

        C04455(int i, boolean z) {
            this.val$reachableTimeout = i;
            this.val$onlyReachables = z;
        }

        public void run() {
            Exception e;
            Throwable th;
            if (!ApModeConnection.this.m_bStop) {
                BufferedReader bufferedReader = null;
                if (ApModeConnection.this.m_ARPIpList == null) {
                    ApModeConnection.this.m_ARPIpList = new ArrayList();
                } else {
                    ApModeConnection.this.m_ARPIpList.clear();
                }
                ApModeConnection.this.m_ArrayListItem.clear();
                try {
                    BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
                    while (true) {
                        try {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            String[] splitted = line.split(" +");
                            if (!(ApModeConnection.this.CompareSameIP(ApModeConnection.this.m_ARPIpList, splitted[0]) || splitted == null || splitted.length < 4)) {
                                String ip = splitted[0];
                                String mac = splitted[3];
                                String mode = splitted[5];
                                if (mac.matches("..:..:..:..:..:..")) {
                                    boolean isReachable = InetAddress.getByName(ip).isReachable(this.val$reachableTimeout);
                                    if (isReachable) {
                                        Log.e("ping: " + splitted[0], "success");
                                    }
                                    if (!this.val$onlyReachables || isReachable) {
                                        ApModeConnection.this.StoreList(ip, mac, mode, isReachable);
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = br;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = br;
                        }
                    }
                    if (ApModeConnection.this.m_ApModeForListListener != null) {
                        ApModeConnection.this.m_ApModeForListListener.SocketTest(0);
                    }
                    try {
                        br.close();
                        bufferedReader = br;
                    } catch (IOException e3) {
                        Log.e(getClass().toString(), e3.getMessage());
                        ApModeConnection.this.ShowScanProgress(false);
                        bufferedReader = br;
                    }
                } catch (Exception e4) {
                    e = e4;
                    try {
                        Log.e(getClass().toString(), e.toString());
                        ApModeConnection.this.ShowScanProgress(false);
                        try {
                            bufferedReader.close();
                        } catch (IOException e32) {
                            Log.e(getClass().toString(), e32.getMessage());
                            ApModeConnection.this.ShowScanProgress(false);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        try {
                            bufferedReader.close();
                        } catch (IOException e322) {
                            Log.e(getClass().toString(), e322.getMessage());
                            ApModeConnection.this.ShowScanProgress(false);
                        }
                        throw th;
                    }
                }
            }
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.6 */
    class C04466 implements Runnable {
        C04466() {
        }

        public void run() {
            Log.e("m_ARPIpList", String.valueOf(ApModeConnection.this.m_ARPIpList));
            Log.e("m_ArrayListItem", String.valueOf(ApModeConnection.this.m_ArrayListItem.size()));
            Log.e("m_ArrayListItem", String.valueOf(ApModeConnection.this.m_ArrayListItem));
            ApModeConnection.this.m_PrinterSimpleAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.7 */
    static /* synthetic */ class C04477 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoRequest.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponse.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponseSuccess.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public class ClientScanResult {
        public String strIP;
        public String strInterface;
        public String strMac;

        public ClientScanResult(String ip, String mac, String Interface, boolean isReachable) {
            this.strIP = null;
            this.strMac = null;
            this.strInterface = null;
            this.strIP = ip;
            this.strMac = mac;
            this.strInterface = Interface;
        }

        public String GetIP() {
            return this.strIP;
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
            iv.getLayoutParams().height = ApModeConnection.this.m_iItemSize;
            iv.getLayoutParams().width = ApModeConnection.this.m_iItemSize;
            return true;
        }
    }

    /* renamed from: com.hiti.utility.ApModeConnection.3 */
    class C08033 extends ApModeListener {
        C08033() {
        }

        public void SocketTest(int num) {
            if (ApModeConnection.this.m_ArrayListItem.size() > num) {
                ApModeConnection.this.TestSocketConnection(((HashMap) ApModeConnection.this.m_ArrayListItem.get(num)).get("ItemName").toString(), HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT, num);
            } else if (ApModeConnection.this.m_ArrayListItem.isEmpty()) {
                ApModeConnection.this.ShowScanProgress(false);
                Log.e("No useful IP", "!!!!!!!");
            } else {
                ApModeConnection.this.ShowScanProgress(false);
                ApModeConnection.this.upDatePrinterListItem();
            }
        }

        public void ScanIpFinish(String ip, int port) {
        }
    }

    class ApModeCommand extends HitiPPR_PrinterCommand {
        private String m_AttrSSID;
        private int m_AttrSSIDLength;
        private int m_iNum;
        private String m_strIp;

        public ApModeCommand(Context context, String IP, int iPort, MSGHandler msgHandler) {
            super(context, IP, iPort, msgHandler);
            this.m_strIp = null;
            this.m_AttrSSID = null;
            this.m_iNum = 0;
            this.m_AttrSSIDLength = -1;
            this.m_strIp = IP;
        }

        public void SetNumber(int num) {
            this.m_iNum = num;
        }

        public int GetNumber() {
            return this.m_iNum;
        }

        public void StartRequest() {
            if (IsRunning()) {
                switch (C04477.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                        if (!Authentication_Request()) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                            break;
                        }
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        if (ReadResponse(ApModeConnection.this.m_iTimeOut)) {
                            if (IsReadComplete()) {
                                if (!CheckCommandSuccess()) {
                                    DecideNextStep(SettingStep.Step_Error);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                                    break;
                                }
                            }
                        }
                        DecideErrorStatus();
                        break;
                        break;
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        if (!Get_NetWork_Info_Request()) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetNetworkInfoResponse);
                            break;
                        }
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                        if (ReadResponse(ApModeConnection.this.m_iTimeOut)) {
                            if (IsReadComplete()) {
                                if (!CheckCommandSuccess()) {
                                    DecideNextStep(SettingStep.Step_Error);
                                    break;
                                } else {
                                    DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetNetworkInfoResponseSuccess);
                                    break;
                                }
                            }
                        }
                        DecideErrorStatus();
                        break;
                        break;
                    case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                        if (ReadResponse(ApModeConnection.this.m_iTimeOut)) {
                            if (IsReadComplete()) {
                                if (this.m_AttrSSIDLength != -1) {
                                    if (this.m_AttrSSID == null) {
                                        char[] AttrSSID = new char[this.m_AttrSSIDLength];
                                        for (int i = 0; i < this.m_AttrSSIDLength; i++) {
                                            AttrSSID[i] = (char) this.m_lpReadData[i];
                                        }
                                        this.m_AttrSSID = String.valueOf(AttrSSID);
                                        DecideNextStep(SettingStep.Step_Complete);
                                        break;
                                    }
                                }
                                this.m_AttrSSIDLength = this.m_lpReadData[2];
                                DecideNextStepAndPrepareReadBuff(this.m_AttrSSIDLength + 1, 0, null);
                                break;
                            }
                        }
                        DecideErrorStatus();
                        break;
                        break;
                    case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                        Log.e("!!!Step_Complete!!!", "SSID=" + String.valueOf(this.m_AttrSSID));
                        Stop();
                        SendMessage(RequestState.REQUEST_AP_MODE_CONNECTION, this.m_AttrSSID);
                        break;
                }
                if (IsConnectError()) {
                    Stop();
                    StopTimerOut();
                    SendMessage(RequestState.REQUEST_AP_MODE_CONNECTION_ERROR, this.m_strIp);
                } else if (IsTimeoutError()) {
                    Stop();
                    SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, this.m_strIp);
                }
            }
        }
    }

    class ApModeHandler extends MSGHandler {
        ApModeHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                String strIP;
                int iNum;
                switch (msg.what) {
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        strIP = msg.getData().getString(MSGHandler.MSG);
                        Log.e("timeout_" + ApModeConnection.this.m_ApCommand.GetNumber(), "ip=" + String.valueOf(strIP));
                        iNum = ApModeConnection.this.RemoveList(strIP);
                        if (iNum == -1) {
                            iNum = ApModeConnection.this.m_ApCommand.GetNumber() + 1;
                        }
                        if (ApModeConnection.this.m_ApModeForListListener != null) {
                            ApModeConnection.this.m_ApModeForListListener.SocketTest(iNum);
                        }
                    case RequestState.REQUEST_AP_MODE_CONNECTION_ERROR /*384*/:
                        strIP = msg.getData().getString(MSGHandler.MSG);
                        Log.e("Conn error_" + ApModeConnection.this.m_ApCommand.GetNumber(), "ip=" + String.valueOf(strIP));
                        iNum = ApModeConnection.this.RemoveList(strIP);
                        if (iNum == -1) {
                            iNum = ApModeConnection.this.m_ApCommand.GetNumber() + 1;
                        }
                        if (ApModeConnection.this.m_ApModeForListListener != null) {
                            ApModeConnection.this.m_ApModeForListListener.SocketTest(iNum);
                        }
                    case RequestState.REQUEST_AP_MODE_CONNECTION /*385*/:
                        String strSSID = msg.getData().getString(MSGHandler.MSG);
                        iNum = ApModeConnection.this.m_ApCommand.GetNumber();
                        ApModeConnection.this.UpdateList(strSSID, iNum);
                        Log.e("OK", "test_" + String.valueOf(iNum) + "_" + strSSID);
                        if (ApModeConnection.this.m_ApModeForListListener != null) {
                            ApModeConnection.this.m_ApModeForListListener.SocketTest(iNum + 1);
                        }
                    default:
                }
            }
        }
    }

    public ApModeConnection(Context context) {
        this.m_ApModeListener = null;
        this.m_ApModeForListListener = null;
        this.m_Handler = new Handler();
        this.m_ApModeHandler = null;
        this.m_ArrayListItem = null;
        this.m_ARPIpList = null;
        this.m_strPrinterNameList = null;
        this.m_PrinterSimpleAdapter = null;
        this.m_PrinterListView = null;
        this.m_PrinterListDialog = null;
        this.m_PrinterListRelativeLayout = null;
        this.m_ScanProgressBar = null;
        this.m_ScanButton = null;
        this.m_PrinterListListener = null;
        this.m_bpPrinterBitmap = null;
        this.m_iItemSize = 0;
        this.m_Context = null;
        this.m_ApCommand = null;
        this.m_ARPthread = null;
        this.m_ARPhandler = null;
        this.m_ARPrunnable = null;
        this.m_bStop = false;
        this.m_iTimeOut = SMTPReply.UNRECOGNIZED_COMMAND;
        this.m_Context = context;
        if (this.m_PrinterListDialog == null) {
            Log.e("ApModeConnection", "new");
            this.m_PrinterListDialog = new Dialog(context, C0349R.style.Dialog_MSG);
            this.m_PrinterListDialog.setContentView(C0349R.layout.dialog_printer_list);
            this.m_PrinterListRelativeLayout = (RelativeLayout) this.m_PrinterListDialog.findViewById(C0349R.id.m_PrinterListRelativeLayout);
            this.m_ScanButton = (Button) this.m_PrinterListDialog.findViewById(C0349R.id.m_ScanButton);
            this.m_ScanButton.setOnClickListener(new C04421());
            this.m_ScanProgressBar = (ProgressBar) this.m_PrinterListDialog.findViewById(C0349R.id.m_ScanProgressBar);
            ShowScanProgress(false);
            this.m_PrinterListView = (ListView) this.m_PrinterListDialog.findViewById(C0349R.id.m_PrinterListView);
            this.m_PrinterListView.setOnItemClickListener(new C04432());
            SetDialogView();
            PreparePrinterIcon();
            this.m_ArrayListItem = new ArrayList();
            this.m_strPrinterNameList = new LinkedList();
            this.m_PrinterSimpleAdapter = new SimpleAdapter(this.m_Context, this.m_ArrayListItem, C0349R.layout.item_printer_list, new String[]{"ItemImage", "ItemName"}, new int[]{C0349R.id.m_PrinterImageView, C0349R.id.m_PrinterTextView});
            this.m_PrinterSimpleAdapter.setViewBinder(new MyViewBinder());
            this.m_PrinterListView.setAdapter(this.m_PrinterSimpleAdapter);
            this.m_ApModeForListListener = new C08033();
            this.m_ApModeHandler = new ApModeHandler();
        }
    }

    private void SetDialogView() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) this.m_Context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.m_iItemSize = dm.widthPixels / 6;
        this.m_PrinterListRelativeLayout.getLayoutParams().width = this.m_iItemSize * 4;
    }

    private void PreparePrinterIcon() {
        this.m_bpPrinterBitmap = BitmapFactory.decodeStream(this.m_Context.getResources().openRawResource(C0349R.drawable.printer));
        Bitmap tempBmp = this.m_bpPrinterBitmap;
        this.m_bpPrinterBitmap = BitmapMonitor.CreateScaledBitmap(this.m_bpPrinterBitmap, this.m_iItemSize, this.m_iItemSize, true).GetBitmap();
        if (!tempBmp.isRecycled()) {
            tempBmp.recycle();
        }
    }

    private void ShowScanProgress(boolean bShow) {
        this.m_Handler.post(new C04444(bShow));
    }

    public void Scan() {
        Log.e("Scan", "Scan");
        if (this.m_ARPhandler != null) {
            this.m_bStop = true;
            if (this.m_ARPrunnable != null) {
                this.m_ARPhandler.removeCallbacks(this.m_ARPrunnable);
                this.m_ARPrunnable = null;
            }
            if (this.m_ARPthread != null) {
                this.m_ARPhandler.removeCallbacks(this.m_ARPthread);
                this.m_ARPthread = null;
            }
            this.m_ARPhandler = null;
        }
        ShowScanProgress(true);
        GetClientList(false, SMTPReply.UNRECOGNIZED_COMMAND);
    }

    public void Show() {
        if (!this.m_PrinterListDialog.isShowing()) {
            this.m_PrinterListDialog.show();
        }
    }

    public void SetApModeListener(ApModeListener listener) {
        this.m_ApModeListener = listener;
    }

    private boolean HaveApModeListener() {
        if (this.m_ApModeListener != null) {
            return true;
        }
        return false;
    }

    public void GetClientList(boolean onlyReachables, int reachableTimeout) {
        this.m_ARPrunnable = new C04455(reachableTimeout, onlyReachables);
        this.m_bStop = false;
        this.m_ARPthread = new HandlerThread("ARP");
        this.m_ARPthread.start();
        this.m_ARPhandler = new Handler(this.m_ARPthread.getLooper());
        this.m_ARPhandler.post(this.m_ARPrunnable);
    }

    private void StoreList(String ip, String mac, String mode, boolean isReachable) {
        Log.e("StoreList", "isReachable=" + isReachable);
        if (!isReachable) {
            this.m_ARPIpList.add(new ClientScanResult(ip, mac, mode, isReachable));
            HashMap<String, Object> map = new HashMap();
            map.put("ItemImage", this.m_bpPrinterBitmap);
            map.put("ItemName", ip);
            this.m_ArrayListItem.add(map);
            Log.e("StoreList", "add_" + this.m_ArrayListItem.indexOf(map) + "=" + ip);
        }
    }

    private void upDatePrinterListItem() {
        this.m_Handler.postDelayed(new C04466(), 1);
    }

    private boolean CompareSameIP(ArrayList<ClientScanResult> result, String ip) {
        Iterator it = result.iterator();
        while (it.hasNext()) {
            if (((ClientScanResult) it.next()).strIP.equals(ip)) {
                return true;
            }
        }
        return false;
    }

    private void onPrinterListViewItemClicked(AdapterView<?> adapterView, View v, int position, long id) {
        Log.i("onPrinterListViewItemClicked", "PrinterActivity");
        if (this.m_ScanButton.isEnabled()) {
            try {
                if (this.m_PrinterListDialog != null) {
                    this.m_PrinterListDialog.dismiss();
                }
                String ip = ((ClientScanResult) this.m_ARPIpList.get(position)).strIP;
                Log.e("Click_" + position, String.valueOf(ip));
                if (HaveApModeListener()) {
                    this.m_ApModeListener.ScanIpFinish(ip, HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void TestSocketConnection(String IP, int iPort, int num) {
        Log.e("TestSocketConnection_" + num, "IP=" + String.valueOf(IP));
        if (this.m_ApCommand != null) {
            this.m_ApCommand.Stop();
            this.m_ApCommand = null;
        }
        this.m_ApCommand = new ApModeCommand(this.m_Context, IP, iPort, this.m_ApModeHandler);
        this.m_ApCommand.SetNumber(num);
        this.m_ApCommand.start();
    }

    private int RemoveList(String IP) {
        Iterator it = this.m_ArrayListItem.iterator();
        while (it.hasNext()) {
            HashMap<String, Object> map = (HashMap) it.next();
            if (map.get("ItemName").toString().equals(IP)) {
                int iNum = this.m_ArrayListItem.indexOf(map);
                this.m_ArrayListItem.remove(map);
                return iNum;
            }
        }
        return -1;
    }

    private void UpdateList(String SSID, int iNum) {
        ((HashMap) this.m_ArrayListItem.get(iNum)).remove("ItemName");
        ((HashMap) this.m_ArrayListItem.get(iNum)).put("ItemName", SSID);
        upDatePrinterListItem();
    }
}
