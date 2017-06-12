package com.hiti.snowglobe;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;

public class ParticlePhoto {
    public static final int NON_DRAW_METHOD = -1;
    public static final int PARTICLE_ASSEMBLE_TYPE = 2;
    public static final int PARTICLE_COLOR_TYPE = 0;
    public static final int PARTICLE_GS_TYPE = 1;
    ArrayList<Pair<Bitmap, Bitmap>> m_bmpPairList;
    private int m_iDrawMethod;
    ArrayList<Integer> m_iTypeList;
    ArrayList<Pair<String, String>> m_strPathPairList;

    ParticlePhoto(int iMethod, ArrayList<Pair<String, String>> strPathPairList, ArrayList<Pair<Bitmap, Bitmap>> bmpPairArrayList, ArrayList<Integer> iTypeArrayList) {
        this.m_iDrawMethod = NON_DRAW_METHOD;
        this.m_strPathPairList = null;
        this.m_bmpPairList = null;
        this.m_iTypeList = null;
        this.m_strPathPairList = new ArrayList();
        this.m_bmpPairList = new ArrayList();
        this.m_iTypeList = new ArrayList();
        if (bmpPairArrayList.size() == iTypeArrayList.size()) {
            for (int i = PARTICLE_COLOR_TYPE; i < bmpPairArrayList.size(); i += PARTICLE_GS_TYPE) {
                AddPhoto((Pair) strPathPairList.get(i), (Pair) bmpPairArrayList.get(i), ((Integer) iTypeArrayList.get(i)).intValue());
            }
        }
        this.m_iDrawMethod = iMethod;
        Log.e("ParticlePhoto", String.valueOf(bmpPairArrayList.size()));
    }

    public void Clear() {
        Iterator it = this.m_bmpPairList.iterator();
        while (it.hasNext()) {
            Pair<Bitmap, Bitmap> bmpPair = (Pair) it.next();
            if (bmpPair.first != null) {
                ((Bitmap) bmpPair.first).recycle();
            }
            if (bmpPair.second != null) {
                ((Bitmap) bmpPair.second).recycle();
            }
        }
        this.m_strPathPairList.clear();
        this.m_bmpPairList.clear();
    }

    public void AddPhoto(Pair<String, String> strPathPair, Pair<Bitmap, Bitmap> bmpPair, int iType) {
        this.m_strPathPairList.add(strPathPair);
        this.m_bmpPairList.add(bmpPair);
        this.m_iTypeList.add(Integer.valueOf(iType));
    }

    public Pair<String, String> GetPhotoPath(int iIndex) {
        if (iIndex >= 0 && iIndex < this.m_strPathPairList.size()) {
            return (Pair) this.m_strPathPairList.get(iIndex);
        }
        return null;
    }

    public Pair<Bitmap, Bitmap> GetPhoto(int iIndex) {
        if (iIndex >= 0 && iIndex < this.m_bmpPairList.size()) {
            return (Pair) this.m_bmpPairList.get(iIndex);
        }
        return null;
    }

    public boolean HaveGSParticle() {
        Iterator it = this.m_iTypeList.iterator();
        while (it.hasNext()) {
            int iType = ((Integer) it.next()).intValue();
            if (iType != PARTICLE_ASSEMBLE_TYPE) {
                if (iType == PARTICLE_GS_TYPE) {
                }
            }
            return true;
        }
        return false;
    }

    public int GetPhotoType(int iIndex) {
        if (iIndex >= 0 && iIndex < this.m_iTypeList.size()) {
            return ((Integer) this.m_iTypeList.get(iIndex)).intValue();
        }
        return NON_DRAW_METHOD;
    }

    public int GetRandomIndex() {
        return (int) (((double) GetSize()) * Math.random());
    }

    public int GetSize() {
        return this.m_bmpPairList.size();
    }

    public int GetDrawMethod() {
        return this.m_iDrawMethod;
    }

    public void SetDrawMethod(int iDrawMethod) {
        this.m_iDrawMethod = iDrawMethod;
    }
}
