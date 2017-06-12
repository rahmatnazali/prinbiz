package com.hiti.utility.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import com.hiti.utility.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.jmdns.impl.constants.DNSConstants;

public class WifiConnect {
    private LogManager LOG;
    private ArrayList<String> m_WifiListSSID;
    private WifiManager m_WifiManager;
    private boolean m_boOpen;

    public WifiConnect(Context context, WifiManager wifiManager) {
        this.m_WifiManager = null;
        this.m_WifiListSSID = null;
        this.m_boOpen = false;
        this.LOG = null;
        this.LOG = new LogManager(0);
        this.m_WifiManager = wifiManager;
        this.m_WifiListSSID = new ArrayList();
    }

    public boolean OpenWifi() {
        if (this.m_WifiManager.isWifiEnabled()) {
            return true;
        }
        boolean bRet = this.m_WifiManager.setWifiEnabled(true);
        this.m_boOpen = true;
        return bRet;
    }

    public boolean CloseWifi() {
        if (!this.m_WifiManager.isWifiEnabled() || !this.m_boOpen) {
            return true;
        }
        boolean bRet = this.m_WifiManager.setWifiEnabled(false);
        this.m_boOpen = false;
        return bRet;
    }

    public boolean Connect(String SSID, String Password, WifiCipherType Type) {
        boolean boGetWifi = false;
        this.LOG.m385i("Connect", "SSID: " + SSID + "\npwd: " + Password);
        if (!OpenWifi()) {
            return false;
        }
        while (this.m_WifiManager.getWifiState() == 2) {
            try {
                this.LOG.m385i("Connect", "WifiManager.WIFI_STATE_ENABLING");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        WifiInfo wifiInfo = this.m_WifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return false;
        }
        if (wifiInfo.getSSID() == null || !wifiInfo.getSSID().contains(SSID)) {
            if (VERSION.SDK_INT < 23) {
                int iCount = GetAllNetwork();
                if (iCount == 0) {
                    this.LOG.m385i("iCount", "iCount == 0");
                    return false;
                }
                for (int i = 0; i < iCount; i++) {
                    String strWifi = (String) this.m_WifiListSSID.get(i);
                    this.LOG.m385i("m_WifiListSSID---", strWifi);
                    if (strWifi.contains(SSID)) {
                        this.LOG.m385i("Find", SSID);
                        boGetWifi = true;
                    }
                }
                if (!boGetWifi) {
                    return false;
                }
            }
            WifiConfiguration wifiConfig = CreateWifiInfo(SSID, Password, Type);
            if (wifiConfig == null) {
                this.LOG.m385i("Connect", "wifiConfig == null");
                return false;
            }
            int netID;
            if (VERSION.SDK_INT < 23) {
                netID = this.m_WifiManager.addNetwork(wifiConfig);
                this.LOG.m385i("Connect add netID", String.valueOf(netID));
            } else {
                netID = wifiConfig.networkId;
            }
            this.LOG.m385i("Connect", "netID= " + netID);
            if (netID == -1) {
                return false;
            }
            return this.m_WifiManager.enableNetwork(netID, true);
        }
        this.LOG.m385i("Connect", "already Connect");
        return true;
    }

    public int GetAllNetwork() {
        this.LOG.m385i("GetAllNetwork", "GetAllNetwork");
        int iCount = 0;
        if (!OpenWifi()) {
            return 0;
        }
        List<ScanResult> WifiList;
        while (this.m_WifiManager.getWifiState() == 2) {
            try {
                this.LOG.m385i("GetAllNetwork", "WIFI_STATE_ENABLING");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            this.m_WifiManager.startScan();
            this.LOG.m385i("Delay for scan", "GetAllNetwork");
            Thread.sleep(DNSConstants.CLOSE_TIMEOUT);
            WifiList = this.m_WifiManager.getScanResults();
            if (WifiList == null || WifiList.size() == 0) {
                Thread.sleep(DNSConstants.CLOSE_TIMEOUT);
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        WifiList = this.m_WifiManager.getScanResults();
        if (WifiList == null) {
            this.LOG.m385i("GetAllNetwork", "ScanResult == null");
            return 0;
        } else if (WifiList.size() == 0) {
            this.LOG.m385i("GetAllNetwork", "ScanResult == 0");
            return 0;
        } else {
            for (int i = 0; i < WifiList.size(); i++) {
                String strGetWifiItem = ((ScanResult) WifiList.get(i)).SSID;
                this.LOG.m385i("Add AP", strGetWifiItem);
                this.m_WifiListSSID.add(strGetWifiItem);
                iCount++;
            }
            return iCount;
        }
    }

    public WifiConfiguration IsExits(String SSID) {
        String strSSID;
        List<WifiConfiguration> existingConfigs = this.m_WifiManager.getConfiguredNetworks();
        if (SSID.substring(0, 1).equals("\"")) {
            strSSID = SSID;
        } else {
            strSSID = "\"" + SSID + "\"";
        }
        this.LOG.m383d("IsExits", "target SSID: " + strSSID);
        for (WifiConfiguration existingConfig : existingConfigs) {
            this.LOG.m383d("IsExits", "exist SSID: " + existingConfig.SSID);
            if (existingConfig.SSID.equals(strSSID)) {
                return existingConfig;
            }
        }
        return null;
    }

    private WifiConfiguration CreateWifiInfo(String SSID, String Password, WifiCipherType Type) {
        WifiConfiguration tempConfig = IsExits(SSID);
        WifiConfiguration config = null;
        if (tempConfig != null) {
            if (VERSION.SDK_INT < 23) {
                this.m_WifiManager.removeNetwork(tempConfig.networkId);
            } else {
                config = tempConfig;
            }
        }
        this.LOG.m385i("CreateWifiInfo", "tempConfig " + (tempConfig != null ? "exist" : "null"));
        if (config == null) {
            config = new WifiConfiguration();
            config.allowedAuthAlgorithms.clear();
            config.allowedGroupCiphers.clear();
            config.allowedKeyManagement.clear();
            config.allowedPairwiseCiphers.clear();
            config.allowedProtocols.clear();
            config.SSID = "\"" + SSID + "\"";
        }
        if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(0);
            config.wepTxKeyIndex = 0;
            this.LOG.m385i("WIFICIPHER_NOPASS", "CreateWifiInfo");
            return config;
        } else if (Type == WifiCipherType.WIFICIPHER_WEP) {
            this.LOG.m385i("CreateWifiInfo", "WIFICIPHER_WEP");
            config.wepKeys = new String[4];
            config.wepKeys[0] = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedGroupCiphers.set(0);
            config.allowedGroupCiphers.set(1);
            config.allowedKeyManagement.set(0);
            config.wepTxKeyIndex = 0;
            return config;
        } else if (Type != WifiCipherType.WIFICIPHER_WPA) {
            return null;
        } else {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(0);
            config.allowedGroupCiphers.set(2);
            config.allowedKeyManagement.set(1);
            config.allowedPairwiseCiphers.set(1);
            config.allowedProtocols.set(0);
            config.status = 2;
            return config;
        }
    }
}
