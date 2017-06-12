package com.hiti.utility.mdns;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import com.hiti.printerprotocol.WirelessType;
import com.hiti.utility.LogManager;
import com.hiti.utility.mdns.DiscoverPrinter.Callback;
import com.hiti.utility.wifi.WifiSetting;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceListener;
import org.apache.commons.net.tftp.TFTP;

public class DeviceScan {
    private static final int BACK_BUTTON_CLICKED_STATE = 1;
    private static final int CONNECT_ITEM_STATE = 3;
    private static final int SCAN_BUTTON_CLICKED_STATE = 2;
    LogManager LOG;
    String TAG;
    int TIME_OUT;
    ISearch mListener;
    ArrayList<PrinterInfo> mPrinterInfo;
    LinkedList<String> mPrinterNameList;
    private DiscoverPrinter m_DiscoverPrinter;
    JmDNS m_Jmdns;
    MulticastLock m_Lock;
    private ReleasePrinter m_ReleasePrinter;
    private ArrayList<ServiceListener> m_ServerListenerList;
    TimeOutTask m_TimeOutTask;
    Timer m_TimeOutTimer;
    private ArrayList<String> m_TypeList;
    private Context m_context;
    boolean mbStop;
    WifiManager wifi;

    /* renamed from: com.hiti.utility.mdns.DeviceScan.1 */
    class C04761 implements Runnable {
        final /* synthetic */ boolean val$bRet;

        C04761(boolean z) {
            this.val$bRet = z;
        }

        public void run() {
            DeviceScan.this.mListener.showProgressBar(this.val$bRet);
        }
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.2 */
    class C04772 implements Runnable {
        C04772() {
        }

        public void run() {
            DeviceScan.this.mListener.beforeScan();
        }
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.3 */
    class C04783 implements Runnable {
        C04783() {
        }

        public void run() {
            DeviceScan.this.mListener.scanStop();
        }
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.4 */
    class C04794 implements Runnable {
        C04794() {
        }

        public void run() {
            DeviceScan.this.mListener.scanTimeout();
        }
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.5 */
    class C04805 implements Runnable {
        final /* synthetic */ String val$SSID;
        final /* synthetic */ int val$id;

        C04805(String str, int i) {
            this.val$SSID = str;
            this.val$id = i;
        }

        public void run() {
            DeviceScan.this.mListener.updateDeviceDetected(this.val$SSID, this.val$id);
        }
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.6 */
    class C04816 implements Runnable {
        final /* synthetic */ String val$IP;
        final /* synthetic */ String val$SSID;
        final /* synthetic */ String val$connType;
        final /* synthetic */ int val$port;

        C04816(String str, String str2, int i, String str3) {
            this.val$SSID = str;
            this.val$IP = str2;
            this.val$port = i;
            this.val$connType = str3;
        }

        public void run() {
            DeviceScan.this.mListener.getDeviceInfo(this.val$SSID, this.val$IP, this.val$port, this.val$connType);
        }
    }

    class ReleasePrinter extends AsyncTask<Integer, String, Integer> {
        int m_GetPrinter;

        ReleasePrinter() {
            this.m_GetPrinter = -1;
        }

        protected Integer doInBackground(Integer... iRet) {
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "ReleasePrinter");
            DeviceScan.this.CloseJMDNS();
            if (iRet[0].intValue() == DeviceScan.CONNECT_ITEM_STATE) {
                this.m_GetPrinter = iRet[DeviceScan.BACK_BUTTON_CLICKED_STATE].intValue();
            }
            return iRet[0];
        }

        public void onProgressUpdate(String... progress) {
        }

        protected void onPostExecute(Integer result) {
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "onPostExecute result: " + result);
            if (DeviceScan.this.mbStop) {
                DeviceScan.this.scanStop();
            } else if (result.intValue() == DeviceScan.BACK_BUTTON_CLICKED_STATE) {
                DeviceScan.this.scanStop();
            } else if (result.intValue() == DeviceScan.SCAN_BUTTON_CLICKED_STATE) {
                DeviceScan.this.scanPrinter();
            } else if (result.intValue() == DeviceScan.CONNECT_ITEM_STATE) {
                DeviceScan.this.getDeviceInfo(this.m_GetPrinter);
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
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "TimeOutTask");
            DeviceScan.this.showProgress(false);
            DeviceScan.this.scanTimeout();
            DeviceScan.this.CloseJMDNS();
        }
    }

    public interface ISearch extends IMdns {
        void beforeScan();

        void disconnect();

        void getDeviceInfo(String str, String str2, int i, String str3);

        void scanStop();

        void scanTimeout();

        void showProgressBar(boolean z);

        void updateDeviceDetected(String str, int i);
    }

    /* renamed from: com.hiti.utility.mdns.DeviceScan.7 */
    class C08367 implements Callback {
        C08367() {
        }

        public void discoveryDone(boolean isStop) {
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "discoveryDone stop: " + isStop);
            if (!isStop) {
                DeviceScan.this.startTimerOut(DeviceScan.this.TIME_OUT);
            }
        }

        public void updateDeviceDetected(PrinterInfo printerInfo) {
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "updateDeviceDetected");
            DeviceScan.this.addPrinterInfo(printerInfo);
            DeviceScan.this.updateNewDevice(printerInfo.str_ssid, DeviceScan.this.mPrinterNameList.size() - 1);
        }

        public void apDeviceDetected(PrinterInfo printerInfo) {
            DeviceScan.this.LOG.m383d(DeviceScan.this.TAG, "apDeviceDetected");
            DeviceScan.this.addPrinterInfo(printerInfo);
            DeviceScan.this.releaseDevice(DeviceScan.CONNECT_ITEM_STATE, 0);
        }
    }

    public DeviceScan(Context context, ISearch listener) {
        this.m_context = null;
        this.m_ReleasePrinter = null;
        this.m_DiscoverPrinter = null;
        this.m_TypeList = null;
        this.m_ServerListenerList = null;
        this.m_Jmdns = null;
        this.m_Lock = null;
        this.wifi = null;
        this.mPrinterNameList = null;
        this.mPrinterInfo = null;
        this.LOG = null;
        this.TAG = "DeviceScan";
        this.mbStop = false;
        this.mListener = null;
        this.m_TimeOutTask = null;
        this.m_TimeOutTimer = null;
        this.TIME_OUT = TFTP.DEFAULT_TIMEOUT;
        this.m_context = context;
        this.mListener = listener;
        this.LOG = new LogManager(0);
        this.mPrinterNameList = new LinkedList();
        this.mPrinterInfo = new ArrayList();
    }

    public void setTimeout(int timeout) {
        if (timeout > 0) {
            this.TIME_OUT = timeout;
        }
    }

    private boolean HaveListener() {
        return this.mListener != null;
    }

    public void scan() {
        MDNS();
    }

    private void showProgress(boolean bRet) {
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04761(bRet));
        }
    }

    private void beforeScan() {
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04772());
        }
    }

    private void scanStop() {
        showProgress(false);
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04783());
        }
    }

    private void scanTimeout() {
        showProgress(false);
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04794());
        }
    }

    private void updateNewDevice(String SSID, int id) {
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04805(SSID, id));
        }
    }

    private void getDeviceInfo(String SSID, String IP, int port, String connType) {
        showProgress(false);
        if (HaveListener()) {
            ((Activity) this.m_context).runOnUiThread(new C04816(SSID, IP, port, connType));
        }
    }

    private void MDNS() {
        this.mPrinterNameList.clear();
        beforeScan();
        if (WifiSetting.IsWifiConnected(this.m_context)) {
            if (this.wifi == null) {
                this.wifi = (WifiManager) this.m_context.getSystemService("wifi");
            }
            releaseDevice(SCAN_BUTTON_CLICKED_STATE, -1);
        } else if (HaveListener()) {
            this.mListener.disconnect();
        }
    }

    public void releaseDevice(int serviceType, int position) {
        showProgress(true);
        try {
            if (this.m_ReleasePrinter == null || this.m_ReleasePrinter.getStatus() == Status.FINISHED) {
                this.m_ReleasePrinter = new ReleasePrinter();
                ReleasePrinter releasePrinter = this.m_ReleasePrinter;
                Integer[] numArr = new Integer[SCAN_BUTTON_CLICKED_STATE];
                numArr[0] = Integer.valueOf(serviceType);
                numArr[BACK_BUTTON_CLICKED_STATE] = Integer.valueOf(position);
                releasePrinter.execute(numArr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scanPrinter() {
        this.LOG.m383d(this.TAG, "scanPrinter");
        if (this.m_DiscoverPrinter == null || this.m_DiscoverPrinter.getStatus() == Status.FINISHED) {
            this.mListener.showProgressBar(true);
            this.m_DiscoverPrinter = new DiscoverPrinter(this.wifi);
            this.m_DiscoverPrinter.setPrinterID(WirelessType.TYPE_P461);
            this.m_DiscoverPrinter.setListener(new C08367());
            this.m_DiscoverPrinter.execute(new String[0]);
        }
    }

    private void addPrinterInfo(PrinterInfo printerInfo) {
        this.LOG.m383d(this.TAG, "addPrinterInfo ");
        this.mPrinterNameList.add(printerInfo.m_strPrinterName);
        this.mPrinterInfo.add(printerInfo);
    }

    private void getDeviceInfo(int position) {
        this.LOG.m383d(this.TAG, "getDeviceInfo id: " + position);
        if (position <= this.mPrinterNameList.size() - 1) {
            String strName = (String) this.mPrinterNameList.get(position);
            if (this.mPrinterInfo != null) {
                for (int i = 0; i < this.mPrinterInfo.size(); i += BACK_BUTTON_CLICKED_STATE) {
                    if (strName.compareTo(((PrinterInfo) this.mPrinterInfo.get(i)).m_strPrinterName) == 0) {
                        getDeviceInfo(((PrinterInfo) this.mPrinterInfo.get(i)).str_ssid, ((PrinterInfo) this.mPrinterInfo.get(i)).m_IP, ((PrinterInfo) this.mPrinterInfo.get(i)).m_Port, ((PrinterInfo) this.mPrinterInfo.get(i)).str_conn);
                        return;
                    }
                }
            }
        }
    }

    void CloseJMDNS() {
        StopTimerOut();
        this.LOG.m383d(this.TAG, "CloseJMDNS");
        if (this.m_DiscoverPrinter != null) {
            try {
                this.m_DiscoverPrinter.Stop();
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

    public void Stop() {
        this.mbStop = true;
        StopTimerOut();
        releaseDevice(BACK_BUTTON_CLICKED_STATE, -1);
    }

    public void Cancel() {
        this.mbStop = true;
        StopTimerOut();
        if (this.m_ReleasePrinter != null) {
            this.m_ReleasePrinter.cancel(true);
        }
        if (this.m_DiscoverPrinter != null) {
            this.m_DiscoverPrinter.cancel(true);
        }
    }

    public void startTimerOut(int iTimeOut) {
        this.LOG.m383d(this.TAG, "startTimerOut");
        if (this.m_TimeOutTimer == null) {
            if (iTimeOut == 0) {
                iTimeOut = this.TIME_OUT;
            }
            this.m_TimeOutTimer = new Timer(true);
            this.m_TimeOutTask = new TimeOutTask();
            this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) iTimeOut);
        }
    }

    public void StopTimerOut() {
        this.LOG.m383d(this.TAG, "StopTimerOut");
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
