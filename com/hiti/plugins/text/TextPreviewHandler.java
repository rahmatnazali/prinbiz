package com.hiti.plugins.text;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.plugins.common.ColorSelector;
import com.hiti.plugins.common.onColorChangedListener;
import com.hiti.plugins.drawer.TextDrawer;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class TextPreviewHandler extends AbstractPageHandler {
    public static final int ACTION_SET_FONT = 2;
    public static final int ACTION_SET_TEXT = 1;
    public static final int DATA_COLOR = 1;
    public static final int DATA_COLOR_TYPE = 2;
    private int R_ID_text_plugin_preview_colorSelector;
    private int R_ID_text_plugin_preview_preview_text;
    private int R_LAYOUT_plugins_text_preview;
    private int R_STRING_preview;
    onColorChangedListener listener;
    private ColorSelector m_ColorSelector;
    private View previewPage;
    private TextDrawer previewText;

    /* renamed from: com.hiti.plugins.text.TextPreviewHandler.1 */
    class C07421 extends onColorChangedListener {
        C07421() {
        }

        public void onColorChanged(int color, int type) {
            TextPreviewHandler.this.previewText.setTextColor(color);
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_text_preview = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_text_preview");
        this.R_STRING_preview = ResourceSearcher.getId(context, RS_TYPE.STRING, "preview");
        this.R_ID_text_plugin_preview_colorSelector = ResourceSearcher.getId(context, RS_TYPE.ID, "text_plugin_preview_colorSelector");
        this.R_ID_text_plugin_preview_preview_text = ResourceSearcher.getId(context, RS_TYPE.ID, "text_plugin_preview_preview_text");
    }

    public TextPreviewHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        this.listener = new C07421();
        GetResourceID(activity);
        this.previewPage = View.inflate(activity, this.R_LAYOUT_plugins_text_preview, null);
        findViews();
    }

    public SparseArray<Object> getData() {
        SparseArray<Object> data = new SparseArray(DATA_COLOR_TYPE);
        data.put(DATA_COLOR, Integer.valueOf(this.m_ColorSelector.getColor()));
        return data;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_COLOR /*1*/:
                return Integer.valueOf(this.m_ColorSelector.getColor());
            case DATA_COLOR_TYPE /*2*/:
                return Integer.valueOf(this.m_ColorSelector.getColorType());
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
        switch (action) {
            case DATA_COLOR /*1*/:
                this.previewText.setText((String) params[0]);
            case DATA_COLOR_TYPE /*2*/:
                this.previewText.setFontFromPath((String) params[0]);
            default:
        }
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_preview);
    }

    private void findViews() {
        this.m_ColorSelector = (ColorSelector) this.previewPage.findViewById(this.R_ID_text_plugin_preview_colorSelector);
        this.previewText = (TextDrawer) this.previewPage.findViewById(this.R_ID_text_plugin_preview_preview_text);
        this.previewText.setTextSize(DATA_COLOR_TYPE, 40);
        this.m_ColorSelector.setOnColorChangedListener(this.listener);
        this.m_ColorSelector.ReflashCurrentColor();
    }

    public View getPage() {
        return this.previewPage;
    }
}
