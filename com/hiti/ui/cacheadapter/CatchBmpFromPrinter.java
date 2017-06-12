package com.hiti.ui.cacheadapter;

import android.graphics.Bitmap;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;

public class CatchBmpFromPrinter {
    private ArrayList<Pair<Integer, Pair<String, Bitmap>>> m_CacheGroup;
    Object m_Lock;
    int m_iSize;

    public CatchBmpFromPrinter(int iSize) {
        this.m_Lock = new Object();
        this.m_CacheGroup = null;
        this.m_iSize = 0;
        this.m_CacheGroup = new ArrayList();
        this.m_iSize = iSize;
    }

    public boolean IsCache(int iID) {
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                if (((Integer) ((Pair) it.next()).first).intValue() == iID) {
                    return true;
                }
            }
            return false;
        }
    }

    public Bitmap GetCacheBmp(int iID) {
        Bitmap retBmp = null;
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                Pair<Integer, Pair<String, Bitmap>> P = (Pair) it.next();
                if (((Integer) P.first).intValue() == iID) {
                    retBmp = (Bitmap) ((Pair) P.second).second;
                }
            }
        }
        return retBmp;
    }

    public String GetCacheText(int iID) {
        String retText = null;
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                Pair<Integer, Pair<String, Bitmap>> P = (Pair) it.next();
                if (((Integer) P.first).intValue() == iID) {
                    retText = (String) ((Pair) P.second).first;
                }
            }
        }
        return retText;
    }

    public boolean AddCache(int iID, Pair<String, Bitmap> P1) {
        synchronized (this.m_Lock) {
            VictimOneCache();
            this.m_CacheGroup.add(new Pair(Integer.valueOf(iID), P1));
        }
        return true;
    }

    private void VictimOneCache() {
        if (this.m_CacheGroup.size() > this.m_iSize) {
            this.m_CacheGroup.remove(0);
        }
    }

    public void ClearCache() {
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                ((Bitmap) ((Pair) ((Pair) it.next()).second).second).recycle();
            }
            this.m_CacheGroup.clear();
        }
    }
}
