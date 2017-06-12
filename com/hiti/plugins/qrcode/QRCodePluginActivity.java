package com.hiti.plugins.qrcode;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.google.android.gms.common.Scopes;
import com.hiti.gcm.GCMInfo;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class QRCodePluginActivity extends AbstractPluginActivity {
    public static final int PAGE_CHOOSE_COLOR = 2;
    public static final int PAGE_FINISH = 4;
    public static final int PAGE_PREVIEW = 3;
    public static final int PAGE_RETURN = 0;
    public static final int PAGE_SET_MESSAGE = 1;
    private AbstractPageHandler CurrentPageHandler;
    private int CurrentPageNumber;
    private int R_ID_frame_container;
    private int R_ID_m_TitleTextView;
    private int R_STRING_error_empty_qr_message;
    private int R_STRING_error_exceed_maximum_qr_message1;
    private int R_STRING_error_exceed_maximum_qr_message2;
    private ViewGroup container;
    private InputMethodManager imm;
    int m_onlyPage;
    private AbstractPageHandler subpage_choosecolor;
    private AbstractPageHandler subpage_preview;
    private AbstractPageHandler subpage_setMessage;
    private TextView title;

    public QRCodePluginActivity() {
        this.CurrentPageNumber = PAGE_RETURN;
        this.CurrentPageHandler = null;
        this.imm = null;
        this.m_onlyPage = -1;
    }

    public int getCurrentPageNumber() {
        return this.CurrentPageNumber;
    }

    public AbstractPageHandler getCurrentPage() {
        return this.CurrentPageHandler;
    }

    private void GetResourceID(Context context) {
        this.R_ID_m_TitleTextView = ResourceSearcher.getId(context, RS_TYPE.ID, "m_TitleTextView");
        this.R_ID_frame_container = ResourceSearcher.getId(context, RS_TYPE.ID, "frame_container");
        this.R_STRING_error_empty_qr_message = ResourceSearcher.getId(context, RS_TYPE.STRING, "error_empty_qr_message");
        this.R_STRING_error_exceed_maximum_qr_message1 = ResourceSearcher.getId(context, RS_TYPE.STRING, "error_exceed_maximum_qr_message1");
        this.R_STRING_error_exceed_maximum_qr_message2 = ResourceSearcher.getId(context, RS_TYPE.STRING, "error_exceed_maximum_qr_message2");
    }

    protected void initActivity() {
        GetResourceID(this);
        this.title = (TextView) findViewById(this.R_ID_m_TitleTextView);
        this.container = (ViewGroup) findViewById(this.R_ID_frame_container);
        this.imm = (InputMethodManager) getSystemService("input_method");
    }

    public void turnToFirstPage() {
        if (this.m_onlyPage != -1) {
            turnToPage(this.m_onlyPage);
        } else {
            turnToPage(PAGE_SET_MESSAGE);
        }
    }

    public void turnToPage(int page) {
        if (this.CurrentPageNumber == PAGE_SET_MESSAGE && page != 0) {
            if (organizeMessage().length() == 0) {
                showMessage(this.R_STRING_error_empty_qr_message);
                return;
            } else if (organizeMessage().length() >= 600) {
                showMessage(getResources().getString(this.R_STRING_error_exceed_maximum_qr_message1) + String.valueOf(600) + getResources().getString(this.R_STRING_error_exceed_maximum_qr_message2));
                return;
            }
        }
        try {
            this.imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), PAGE_RETURN);
        } catch (Exception e) {
        }
        this.container.removeAllViews();
        AbstractPageHandler target = null;
        if (this.m_onlyPage == -1) {
            switch (page) {
                case PAGE_RETURN /*0*/:
                    setResult(-1);
                    finish();
                    return;
                case PAGE_SET_MESSAGE /*1*/:
                    target = this.subpage_setMessage;
                    break;
                case PAGE_CHOOSE_COLOR /*2*/:
                    target = this.subpage_choosecolor;
                    break;
                case PAGE_PREVIEW /*3*/:
                    target = this.subpage_preview;
                    this.subpage_preview.requestAction(PAGE_SET_MESSAGE, new Object[PAGE_RETURN]);
                    break;
                case PAGE_FINISH /*4*/:
                    goFinish();
                    return;
            }
        }
        switch (page) {
            case PAGE_SET_MESSAGE /*1*/:
                setResult(-1);
                finish();
                return;
            case PAGE_CHOOSE_COLOR /*2*/:
                target = this.subpage_choosecolor;
                break;
            default:
                super.goFinish();
                return;
        }
        this.container.addView(target.getPage());
        this.CurrentPageNumber = page;
        this.CurrentPageHandler = target;
        this.title.setText(target.getTitle());
    }

    protected void generateSubpages(Bundle bundle) {
        if (bundle != null) {
            this.m_onlyPage = bundle.getInt("ONLYPAGE", -1);
        }
        this.subpage_setMessage = new QRCodeSetMessageHandler(this, bundle);
        this.subpage_choosecolor = new QRCodeChooseColorHandler(this, bundle);
        this.subpage_preview = new QRCodePreviewHandler(this, bundle);
    }

    private String organizeMessage() {
        SparseArray<Object> data = this.subpage_setMessage.getData();
        String message = (String) data.get(PAGE_SET_MESSAGE);
        String name = (String) data.get(PAGE_CHOOSE_COLOR);
        String phone_number = (String) data.get(PAGE_PREVIEW);
        String email = (String) data.get(PAGE_FINISH);
        String url = (String) data.get(5);
        StringBuffer targetBuffer = new StringBuffer();
        if (message.length() > 0) {
            targetBuffer.append(message + "\n");
        }
        if (name.length() > 0) {
            targetBuffer.append(name + "\n");
        }
        if (phone_number.length() > 0) {
            targetBuffer.append(phone_number + "\n");
        }
        if (email.length() > 0) {
            targetBuffer.append(email + "\n");
        }
        if (url.length() > 0) {
            targetBuffer.append(url + "\n");
        }
        return targetBuffer.toString();
    }

    public void goFinish() {
        super.goFinish();
    }

    protected Bundle prepareBundle(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        SparseArray<Object> data = this.subpage_setMessage.getData();
        String message = (String) data.get(PAGE_SET_MESSAGE);
        String name = (String) data.get(PAGE_CHOOSE_COLOR);
        String phone_number = (String) data.get(PAGE_PREVIEW);
        String email = (String) data.get(PAGE_FINISH);
        String url = (String) data.get(5);
        bundle.putInt("color", ((Integer) this.subpage_choosecolor.getData(PAGE_SET_MESSAGE)).intValue());
        bundle.putInt("color_type", ((Integer) this.subpage_choosecolor.getData(PAGE_CHOOSE_COLOR)).intValue());
        bundle.putString("image", (String) this.subpage_preview.getData(6));
        bundle.putString(GCMInfo.EXTRA_MESSAGE, message);
        bundle.putString("name", name);
        bundle.putString("phone_number", phone_number);
        bundle.putString(Scopes.EMAIL, email);
        bundle.putString("url", url);
        bundle.putInt("ONLYPAGE", this.m_onlyPage);
        return bundle;
    }
}
