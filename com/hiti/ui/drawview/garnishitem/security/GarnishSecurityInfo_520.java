package com.hiti.ui.drawview.garnishitem.security;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GarnishSecurityInfo_520 {
    private int m_iItemNumbers;
    private ArrayList<Long> m_lItemSizeList;
    private String m_strConfigType;
    private String m_strExpire;
    private ArrayList<String> m_strItemNameList;

    public GarnishSecurityInfo_520(String strExpire, String strConfigType, int iItemNumbers, ArrayList<Long> lItemSizeList, ArrayList<String> strItemNameList) {
        this.m_strExpire = XmlPullParser.NO_NAMESPACE;
        this.m_strConfigType = XmlPullParser.NO_NAMESPACE;
        this.m_iItemNumbers = 0;
        this.m_lItemSizeList = null;
        this.m_strItemNameList = null;
        this.m_strExpire = strExpire;
        this.m_strConfigType = strConfigType;
        this.m_iItemNumbers = iItemNumbers;
        this.m_lItemSizeList = new ArrayList();
        this.m_strItemNameList = new ArrayList();
        Iterator it = lItemSizeList.iterator();
        while (it.hasNext()) {
            this.m_lItemSizeList.add((Long) it.next());
        }
        it = strItemNameList.iterator();
        while (it.hasNext()) {
            this.m_strItemNameList.add((String) it.next());
        }
    }

    public String GetExpire() {
        return this.m_strExpire;
    }

    public void SetExpire(String strExpire) {
        this.m_strExpire = strExpire;
    }

    public String GetConfigType() {
        return this.m_strConfigType;
    }

    public void SetConfigType(String strConfigType) {
        this.m_strConfigType = strConfigType;
    }

    public int GetItemNumbers() {
        return this.m_iItemNumbers;
    }

    public void SetItemNumbers(int iItemNumbers) {
        this.m_iItemNumbers = iItemNumbers;
    }

    public void AddItem(String strItemName, long lSize) {
        this.m_strItemNameList.add(strItemName);
        this.m_lItemSizeList.add(Long.valueOf(lSize));
    }

    public String GetItemName(int iIndex) {
        if (this.m_strItemNameList.size() < iIndex || iIndex < 0) {
            return null;
        }
        return (String) this.m_strItemNameList.get(iIndex);
    }

    public long GetItemSize(int iIndex) {
        if (this.m_lItemSizeList.size() < iIndex || iIndex < 0) {
            return -1;
        }
        return ((Long) this.m_lItemSizeList.get(iIndex)).longValue();
    }

    public long GetItemSizeByName(String strItemName) {
        Log.e("GetItemSizeByName strItemName", strItemName);
        if (!this.m_strItemNameList.contains(strItemName)) {
            return -1;
        }
        Log.e("GetItemSizeByName strItemName2", strItemName);
        int iIndex = this.m_strItemNameList.indexOf(strItemName);
        Log.e("GetItemSizeByName iIndex", String.valueOf(iIndex));
        if (this.m_lItemSizeList.size() < iIndex || iIndex < 0) {
            return -1;
        }
        return ((Long) this.m_lItemSizeList.get(iIndex)).longValue();
    }

    public boolean ContainItemName(String strItemName) {
        Log.e("m_strItemNameList size....", String.valueOf(this.m_strItemNameList.size()));
        Log.e("m_strItemNameList m_strItemNameList....", (String) this.m_strItemNameList.get(0));
        if (this.m_strItemNameList.contains(strItemName)) {
            return true;
        }
        return false;
    }
}
