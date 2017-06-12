package com.hiti.plugins.qrcode;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.plugins.common.ColorSelector;
import com.hiti.plugins.common.onColorChangedListener;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class QRCodeChooseColorHandler extends AbstractPageHandler {
    public static final int DATA_COLOR = 1;
    public static final int DATA_COLOR_TYPE = 2;
    private int R_ID_qrcode_plugin_choosecolor_colorSelector;
    private int R_ID_qrcode_plugin_choosecolor_color_preview;
    private int R_LAYOUT_plugins_qrcode_choosecolor;
    private int R_STRING_pick_a_color;
    private View choosecolorPage;
    private View color_preview;
    private ColorSelector m_ColorSelector;
    private onColorChangedListener m_ColorSelectorListener;

    /* renamed from: com.hiti.plugins.qrcode.QRCodeChooseColorHandler.1 */
    class C07411 extends onColorChangedListener {
        C07411() {
        }

        public void onColorChanged(int color, int type) {
            QRCodeChooseColorHandler.this.color_preview.setBackgroundColor(color);
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_qrcode_choosecolor = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_qrcode_choosecolor");
        this.R_ID_qrcode_plugin_choosecolor_colorSelector = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_choosecolor_colorSelector");
        this.R_ID_qrcode_plugin_choosecolor_color_preview = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_choosecolor_color_preview");
        this.R_STRING_pick_a_color = ResourceSearcher.getId(context, RS_TYPE.STRING, "pick_a_color");
    }

    public SparseArray<Object> getData() {
        SparseArray<Object> data = new SparseArray(DATA_COLOR);
        data.put(DATA_COLOR, Integer.valueOf(getColor()));
        return data;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_COLOR /*1*/:
                return Integer.valueOf(getColor());
            case DATA_COLOR_TYPE /*2*/:
                return Integer.valueOf(getColorType());
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_pick_a_color);
    }

    public QRCodeChooseColorHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        GetResourceID(activity);
        this.m_ColorSelectorListener = new C07411();
        this.choosecolorPage = View.inflate(activity, this.R_LAYOUT_plugins_qrcode_choosecolor, null);
        findViews();
        if (bundle != null) {
            int iOnlyPage = bundle.getInt("ONLYPAGE", -1);
            Log.e("ONLYPAGE", String.valueOf(iOnlyPage));
            if (iOnlyPage == -1) {
                this.m_ColorSelector.NoSilverMode();
            }
        }
    }

    private void findViews() {
        this.m_ColorSelector = (ColorSelector) this.choosecolorPage.findViewById(this.R_ID_qrcode_plugin_choosecolor_colorSelector);
        this.color_preview = this.choosecolorPage.findViewById(this.R_ID_qrcode_plugin_choosecolor_color_preview);
        this.m_ColorSelector.setOnColorChangedListener(this.m_ColorSelectorListener);
        this.m_ColorSelector.ReflashCurrentColor();
    }

    public View getPage() {
        return this.choosecolorPage;
    }

    public int getColor() {
        return this.m_ColorSelector.getColor();
    }

    public int getColorType() {
        return this.m_ColorSelector.getColorType();
    }
}
