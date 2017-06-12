package com.hiti.plugins.text;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.hiti.gcm.GCMInfo;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class TextPluginActivity extends AbstractPluginActivity {
    public static final int PAGE_CHOOSE_FONT = 2;
    public static final int PAGE_FINISH = 4;
    public static final int PAGE_PREVIEW = 3;
    public static final int PAGE_RETURN = 0;
    public static final int PAGE_SET_MESSAGE = 1;
    private AbstractPageHandler CurrentPageHandler;
    private int CurrentPageNumber;
    private int R_ID_frame_container;
    private int R_ID_m_TitleTextView;
    private int R_STRING_error_empty_font_message;
    private int R_STRING_error_empty_text_message;
    private ViewGroup container;
    private InputMethodManager imm;
    private AbstractPageHandler subpage_choosefont;
    private AbstractPageHandler subpage_preview;
    private AbstractPageHandler subpage_setMessage;
    private TextView title;

    public TextPluginActivity() {
        this.CurrentPageNumber = PAGE_RETURN;
        this.CurrentPageHandler = null;
        this.imm = null;
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
        this.R_STRING_error_empty_text_message = ResourceSearcher.getId(context, RS_TYPE.STRING, "error_empty_text_message");
        this.R_STRING_error_empty_font_message = ResourceSearcher.getId(context, RS_TYPE.STRING, "error_empty_font_message");
    }

    protected void initActivity() {
        GetResourceID(this);
        this.title = (TextView) findViewById(this.R_ID_m_TitleTextView);
        this.container = (ViewGroup) findViewById(this.R_ID_frame_container);
        this.imm = (InputMethodManager) getSystemService("input_method");
    }

    public void turnToFirstPage() {
        turnToPage(PAGE_SET_MESSAGE);
    }

    public void turnToPage(int page) {
        if (this.CurrentPageNumber == PAGE_SET_MESSAGE && page != 0 && ((String) this.subpage_setMessage.getData(PAGE_SET_MESSAGE)).trim().length() == 0) {
            showMessage(this.R_STRING_error_empty_text_message);
        } else if (this.CurrentPageNumber == PAGE_CHOOSE_FONT && page != PAGE_SET_MESSAGE && this.subpage_choosefont.getData(PAGE_SET_MESSAGE) == null) {
            showMessage(this.R_STRING_error_empty_font_message);
        } else {
            try {
                this.imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), PAGE_RETURN);
            } catch (Exception e) {
            }
            this.container.removeAllViews();
            AbstractPageHandler target = null;
            Object[] objArr;
            switch (page) {
                case PAGE_RETURN /*0*/:
                    setResult(-1);
                    finish();
                    return;
                case PAGE_SET_MESSAGE /*1*/:
                    target = this.subpage_setMessage;
                    break;
                case PAGE_CHOOSE_FONT /*2*/:
                    target = this.subpage_choosefont;
                    objArr = new Object[PAGE_SET_MESSAGE];
                    objArr[PAGE_RETURN] = this.subpage_setMessage.getData(PAGE_SET_MESSAGE);
                    target.requestAction(PAGE_SET_MESSAGE, objArr);
                    break;
                case PAGE_PREVIEW /*3*/:
                    target = this.subpage_preview;
                    objArr = new Object[PAGE_SET_MESSAGE];
                    objArr[PAGE_RETURN] = this.subpage_setMessage.getData(PAGE_SET_MESSAGE);
                    target.requestAction(PAGE_SET_MESSAGE, objArr);
                    objArr = new Object[PAGE_SET_MESSAGE];
                    objArr[PAGE_RETURN] = this.subpage_choosefont.getData(PAGE_SET_MESSAGE);
                    target.requestAction(PAGE_CHOOSE_FONT, objArr);
                    break;
                case PAGE_FINISH /*4*/:
                    goFinish();
                    return;
            }
            this.container.addView(target.getPage());
            this.CurrentPageNumber = page;
            this.CurrentPageHandler = target;
            this.title.setText(target.getTitle());
        }
    }

    protected void generateSubpages(Bundle bundle) {
        this.subpage_setMessage = new TextSetMessageHandler(this, bundle);
        this.subpage_choosefont = new TextChooseFontHandler(this, bundle);
        this.subpage_preview = new TextPreviewHandler(this, bundle);
    }

    protected Bundle prepareBundle(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        String message = (String) this.subpage_setMessage.getData(PAGE_SET_MESSAGE);
        String font_path = (String) this.subpage_choosefont.getData(PAGE_SET_MESSAGE);
        int font_color = ((Integer) this.subpage_preview.getData(PAGE_SET_MESSAGE)).intValue();
        int font_type = ((Integer) this.subpage_preview.getData(PAGE_CHOOSE_FONT)).intValue();
        bundle.putString(GCMInfo.EXTRA_MESSAGE, message);
        bundle.putString("font_path", font_path);
        bundle.putInt("font_color", font_color);
        bundle.putInt("color_type", font_type);
        return bundle;
    }
}
