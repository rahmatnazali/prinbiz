package com.hiti.ui.drawview.garnishitem.history;

import com.hiti.ui.drawview.garnishitem.GarnishItem;

public class GarnishItemHistory {
    private GARNISH_ITEM_ACTION m_Action;
    private GarnishItem m_GarnishItem;

    public GarnishItemHistory(GarnishItem garnishItem, GARNISH_ITEM_ACTION action) {
        this.m_GarnishItem = null;
        this.m_Action = GARNISH_ITEM_ACTION.ACTION_NON;
        this.m_GarnishItem = garnishItem;
        this.m_Action = action;
    }

    public GarnishItem GetGarnishItem() {
        return this.m_GarnishItem;
    }

    public GARNISH_ITEM_ACTION GetAction() {
        return this.m_Action;
    }
}
