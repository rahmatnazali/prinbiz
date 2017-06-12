package com.hiti.ui.drawview.garnishitem.LayerManager;

import com.hiti.ui.drawview.garnishitem.GarnishItem;
import java.util.ArrayList;
import java.util.Collections;

public class GarnishItemLayerManager {
    public static void SwapGarnishLayer(ArrayList<GarnishItem> GarnishManager, GarnishItem FoucsGarnish, boolean boUp) {
        if (GarnishManager != null && FoucsGarnish != null) {
            int ilevel = GarnishManager.indexOf(FoucsGarnish);
            if (ilevel != -1) {
                int iSwap;
                if (boUp) {
                    iSwap = CanGarnishMoveUpLayer(GarnishManager, FoucsGarnish);
                } else {
                    iSwap = CanGarnishMoveDownLayer(GarnishManager, FoucsGarnish);
                }
                if (iSwap != -1) {
                    try {
                        Collections.swap(GarnishManager, ilevel, iSwap);
                        Collections.sort(GarnishManager);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static int CanGarnishMoveUpLayer(ArrayList<GarnishItem> GarnishManager, GarnishItem FoucsGarnish) {
        if (GarnishManager == null || FoucsGarnish == null) {
            return -1;
        }
        Collections.sort(GarnishManager);
        if (FoucsGarnish.GetType() == 6 || FoucsGarnish.GetType() == 4) {
            return -1;
        }
        int ilevel = GarnishManager.indexOf(FoucsGarnish);
        if (ilevel == -1) {
            return -1;
        }
        if (ilevel + 1 >= GarnishManager.size()) {
            return -1;
        }
        int iSwap = ilevel + 1;
        if (((GarnishItem) GarnishManager.get(iSwap)).GetType() != 3) {
            return -1;
        }
        return iSwap;
    }

    public static int CanGarnishMoveDownLayer(ArrayList<GarnishItem> GarnishManager, GarnishItem FoucsGarnish) {
        if (GarnishManager == null || FoucsGarnish == null) {
            return -1;
        }
        Collections.sort(GarnishManager);
        if (FoucsGarnish.GetType() == 6 || FoucsGarnish.GetType() == 4) {
            return -1;
        }
        int ilevel = GarnishManager.indexOf(FoucsGarnish);
        if (ilevel == -1) {
            return -1;
        }
        if (ilevel - 1 < 0) {
            return -1;
        }
        int iSwap = ilevel - 1;
        if (((GarnishItem) GarnishManager.get(iSwap)).GetType() != 3) {
            return -1;
        }
        return iSwap;
    }
}
