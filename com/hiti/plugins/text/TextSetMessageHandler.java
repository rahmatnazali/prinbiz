package com.hiti.plugins.text;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import com.hiti.gcm.GCMInfo;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class TextSetMessageHandler extends AbstractPageHandler {
    public static final int DATA_MESSAGE = 1;
    private int R_ID_text_plugin_setmessage_message;
    private int R_LAYOUT_plugins_text_setmessage;
    private int R_STRING_text;
    private EditText message;
    private View setmessagePage;

    private void GetResourceID(Context context) {
        this.R_ID_text_plugin_setmessage_message = ResourceSearcher.getId(context, RS_TYPE.ID, "text_plugin_setmessage_message");
        this.R_LAYOUT_plugins_text_setmessage = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_text_setmessage");
        this.R_STRING_text = ResourceSearcher.getId(context, RS_TYPE.STRING, "text");
    }

    public TextSetMessageHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        GetResourceID(activity);
        this.setmessagePage = View.inflate(activity, this.R_LAYOUT_plugins_text_setmessage, null);
        findViews();
        if (bundle != null && bundle.containsKey(GCMInfo.EXTRA_MESSAGE)) {
            this.message.setText(bundle.getString(GCMInfo.EXTRA_MESSAGE));
        }
    }

    private void findViews() {
        this.message = (EditText) this.setmessagePage.findViewById(this.R_ID_text_plugin_setmessage_message);
    }

    public SparseArray<Object> getData() {
        return null;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_MESSAGE /*1*/:
                return this.message.getText().toString();
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_text);
    }

    public View getPage() {
        return this.setmessagePage;
    }
}
