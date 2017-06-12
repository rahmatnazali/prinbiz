package com.hiti.plugins.qrcode;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.common.Scopes;
import com.hiti.gcm.GCMInfo;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class QRCodeSetMessageHandler extends AbstractPageHandler {
    public static final int DATA_EMAIL = 4;
    public static final int DATA_MESSAGE = 1;
    public static final int DATA_NAME = 2;
    public static final int DATA_PHONE_NUMBER = 3;
    public static final int DATA_URL = 5;
    private int R_ID_qrcode_plugin_setmessage_email;
    private int R_ID_qrcode_plugin_setmessage_message;
    private int R_ID_qrcode_plugin_setmessage_name;
    private int R_ID_qrcode_plugin_setmessage_phone_number;
    private int R_ID_qrcode_plugin_setmessage_url;
    private int R_LAYOUT_plugins_qrcode_setmessage;
    private int R_STRING_qr_code;
    private EditText email;
    private EditText message;
    private EditText name;
    private EditText phone_number;
    private View setmessagePage;
    private EditText url;

    public SparseArray<Object> getData() {
        SparseArray<Object> data = new SparseArray(6);
        data.put(DATA_MESSAGE, getMessage());
        data.put(DATA_NAME, getName());
        data.put(DATA_PHONE_NUMBER, getPhoneNumber());
        data.put(DATA_EMAIL, getEmail());
        data.put(DATA_URL, getUrl());
        return data;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_MESSAGE /*1*/:
                return getMessage();
            case DATA_NAME /*2*/:
                return getName();
            case DATA_PHONE_NUMBER /*3*/:
                return getPhoneNumber();
            case DATA_EMAIL /*4*/:
                return getEmail();
            case DATA_URL /*5*/:
                return getUrl();
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_qr_code);
    }

    public QRCodeSetMessageHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        GetResourceID(activity);
        this.setmessagePage = View.inflate(activity, this.R_LAYOUT_plugins_qrcode_setmessage, null);
        findViews();
        if (bundle != null) {
            this.message.setText(bundle.getString(GCMInfo.EXTRA_MESSAGE));
            this.name.setText(bundle.getString("name"));
            this.phone_number.setText(bundle.getString("phone_number"));
            this.email.setText(bundle.getString(Scopes.EMAIL));
            this.url.setText(bundle.getString("url"));
        }
    }

    private void GetResourceID(Context context) {
        this.R_ID_qrcode_plugin_setmessage_message = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_setmessage_message");
        this.R_ID_qrcode_plugin_setmessage_name = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_setmessage_name");
        this.R_ID_qrcode_plugin_setmessage_phone_number = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_setmessage_phone_number");
        this.R_ID_qrcode_plugin_setmessage_email = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_setmessage_email");
        this.R_ID_qrcode_plugin_setmessage_url = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_setmessage_url");
        this.R_STRING_qr_code = ResourceSearcher.getId(context, RS_TYPE.STRING, "qr_code");
        this.R_LAYOUT_plugins_qrcode_setmessage = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_qrcode_setmessage");
    }

    private void findViews() {
        this.message = (EditText) this.setmessagePage.findViewById(this.R_ID_qrcode_plugin_setmessage_message);
        this.name = (EditText) this.setmessagePage.findViewById(this.R_ID_qrcode_plugin_setmessage_name);
        this.phone_number = (EditText) this.setmessagePage.findViewById(this.R_ID_qrcode_plugin_setmessage_phone_number);
        this.email = (EditText) this.setmessagePage.findViewById(this.R_ID_qrcode_plugin_setmessage_email);
        this.url = (EditText) this.setmessagePage.findViewById(this.R_ID_qrcode_plugin_setmessage_url);
    }

    public View getPage() {
        return this.setmessagePage;
    }

    public String getMessage() {
        return this.message.getText().toString();
    }

    public String getName() {
        return this.name.getText().toString();
    }

    public String getPhoneNumber() {
        return this.phone_number.getText().toString();
    }

    public String getEmail() {
        return this.email.getText().toString();
    }

    public String getUrl() {
        return this.url.getText().toString();
    }
}
