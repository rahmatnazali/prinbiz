package com.hiti.utility.mdns;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import com.hiti.utility.LogManager;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jmdns.impl.constants.DNSConstants;
import org.xmlpull.v1.XmlPullParser;

public class DiscoverPrinter extends AsyncTask<String, String, Object> {
    private static final int GET_PRINTER_INFO_COUNT = 2;
    LogManager LOG;
    String TAG;
    private Callback callback;
    String mPrinterID;
    private JmDNS m_Jmdns;
    private MulticastLock m_Lock;
    private ArrayList<PrinterInfo> m_PrinterInfo;
    private ArrayList<ServiceListener> m_ServerListenerList;
    private ArrayList<String> m_TypeList;
    private LinkedList<String> m_strPrinterNameList;
    private boolean mbStop;
    private String strProtectDiscoverPrinter;
    private WifiManager wifi;

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

    /* renamed from: com.hiti.utility.mdns.DiscoverPrinter.1 */
    class C08071 implements ServiceListener {
        C08071() {
        }

        public void serviceResolved(ServiceEvent event) {
            if (!DiscoverPrinter.this.mbStop) {
                String[] IP = event.getInfo().getHostAddresses();
                String str_Type = event.getType();
                PrinterInfo getPrinterInfo = DiscoverPrinter.this.ReturnPrinterInfo(IP[0]);
                if (event.getName().compareTo(XmlPullParser.NO_NAMESPACE) != 0 && !DiscoverPrinter.this.mbStop) {
                    if (str_Type.compareTo("_htipp._tcp.local.") == 0) {
                        getPrinterInfo.m_Port = event.getInfo().getPort();
                        getPrinterInfo.m_strPrinterName = event.getName();
                        getPrinterInfo.str_usb_PID = event.getInfo().getPropertyString("usb_PID");
                        getPrinterInfo.str_usb_MFG = event.getInfo().getPropertyString("usb_MFG");
                        getPrinterInfo.str_usb_MDL = event.getInfo().getPropertyString("usb_MDL");
                        getPrinterInfo.str_pdl = event.getInfo().getPropertyString("pdl");
                        getPrinterInfo.str_prsz = event.getInfo().getPropertyString("prsz");
                        getPrinterInfo.iComplete++;
                    }
                    if (str_Type.compareTo("_htconf._tcp.local.") == 0) {
                        getPrinterInfo.m_strPrinterName = event.getName();
                        getPrinterInfo.str_mac = event.getInfo().getPropertyString("mac");
                        getPrinterInfo.str_conn = event.getInfo().getPropertyString("conn");
                        getPrinterInfo.str_ssid = event.getInfo().getPropertyString("ssid");
                        getPrinterInfo.str_rcrd = event.getInfo().getPropertyString("rcrd");
                        getPrinterInfo.str_srvmod = event.getInfo().getPropertyString("srvmod");
                        getPrinterInfo.str_ssidmod = event.getInfo().getPropertyString("ssidmod");
                        getPrinterInfo.iComplete++;
                    }
                    if (getPrinterInfo.str_conn.equals("AP")) {
                        if (!DiscoverPrinter.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                            DiscoverPrinter.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        }
                        DiscoverPrinter.this.cancel(true);
                        if (DiscoverPrinter.this.getPIDSeting().contains(DiscoverPrinter.this.transformSSID(getPrinterInfo.str_usb_PID)) && DiscoverPrinter.this.callback != null) {
                            DiscoverPrinter.this.callback.apDeviceDetected(getPrinterInfo);
                        }
                    } else if (false && !DiscoverPrinter.this.isCancelled()) {
                        DiscoverPrinter.this.cancel(true);
                    } else if (getPrinterInfo.iComplete == DiscoverPrinter.GET_PRINTER_INFO_COUNT && !DiscoverPrinter.this.m_strPrinterNameList.contains(getPrinterInfo.m_strPrinterName)) {
                        DiscoverPrinter.this.m_strPrinterNameList.add(getPrinterInfo.m_strPrinterName);
                        DiscoverPrinter.this.UpdateDeviceDetected(getPrinterInfo);
                    }
                }
            }
        }

        public void serviceRemoved(ServiceEvent event) {
        }

        public void serviceAdded(ServiceEvent event) {
            DiscoverPrinter.this.m_Jmdns.requestServiceInfo(event.getType(), event.getName(), true);
        }
    }

    public interface Callback extends IMdns {
        void apDeviceDetected(PrinterInfo printerInfo);

        void discoveryDone(boolean z);

        void updateDeviceDetected(PrinterInfo printerInfo);
    }

    public DiscoverPrinter(WifiManager wifi) {
        this.strProtectDiscoverPrinter = "PROTECT_DISCOVER_PRINTER";
        this.m_ServerListenerList = null;
        this.m_TypeList = null;
        this.m_PrinterInfo = null;
        this.m_strPrinterNameList = null;
        this.m_Jmdns = null;
        this.m_Lock = null;
        this.wifi = null;
        this.mbStop = false;
        this.callback = null;
        this.LOG = null;
        this.TAG = "Discover";
        this.mPrinterID = null;
        this.wifi = wifi;
        this.m_PrinterInfo = new ArrayList();
        this.m_strPrinterNameList = new LinkedList();
        this.LOG = new LogManager(0);
    }

    PrinterInfo ReturnPrinterInfo(String ip) {
        for (int i = 0; i < this.m_PrinterInfo.size(); i++) {
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

    public void setListener(Callback listener) {
        this.callback = listener;
    }

    public void Stop() {
        this.mbStop = true;
    }

    protected Object doInBackground(String... AlbumName) {
        if (!this.mbStop) {
            this.m_Lock = this.wifi.createMulticastLock(this.strProtectDiscoverPrinter);
            this.m_Lock.setReferenceCounted(true);
            this.m_Lock.acquire();
            this.m_TypeList = new ArrayList();
            this.m_ServerListenerList = new ArrayList();
            this.m_PrinterInfo = new ArrayList();
            this.m_PrinterInfo.clear();
            this.m_TypeList.add("_htipp._tcp.local.");
            this.m_TypeList.add("_htconf._tcp.local.");
            try {
                this.m_Jmdns = JmDNS.create(InetAddress.getByName(DNSConstants.MDNS_GROUP));
                for (int j = 0; j < this.m_TypeList.size() && !this.mbStop; j++) {
                    JmDNS jmDNS = this.m_Jmdns;
                    String str = (String) this.m_TypeList.get(j);
                    ServiceListener serviceListener = new C08071();
                    jmDNS.addServiceListener(str, serviceListener);
                    this.m_ServerListenerList.add(serviceListener);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void onPostExecute(Object result) {
        if (this.callback != null) {
            this.callback.discoveryDone(this.mbStop);
        }
    }

    private void UpdateDeviceDetected(PrinterInfo printerInfo) {
        if (printerInfo.str_usb_PID.equals(getPIDSeting())) {
            this.callback.updateDeviceDetected(printerInfo);
        }
    }

    private String getPIDSeting() {
        return this.mPrinterID;
    }

    public void setPrinterID(String pid) {
        this.mPrinterID = pid;
    }

    private String transformSSID(String PID) {
        return Integer.toHexString(Integer.parseInt(PID));
    }
}
