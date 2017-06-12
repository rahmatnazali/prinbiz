package com.hiti.plugins.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public abstract class AbstractPluginActivity extends Activity {
    private int R_ID_m_BackButton;
    private int R_ID_m_NextButton;
    private int R_LAYOUT_plugins_frame;
    private int R_STRING_OK;
    private OnClickListener ocl_back_button;
    private OnClickListener ocl_next_button;

    /* renamed from: com.hiti.plugins.common.AbstractPluginActivity.1 */
    class C02431 implements OnClickListener {
        C02431() {
        }

        public void onClick(View v) {
            AbstractPluginActivity.this.turnToPage(AbstractPluginActivity.this.getCurrentPageNumber() - 1);
        }
    }

    /* renamed from: com.hiti.plugins.common.AbstractPluginActivity.2 */
    class C02442 implements OnClickListener {
        C02442() {
        }

        public void onClick(View v) {
            AbstractPluginActivity.this.turnToPage(AbstractPluginActivity.this.getCurrentPageNumber() + 1);
        }
    }

    protected abstract void generateSubpages(Bundle bundle);

    public abstract AbstractPageHandler getCurrentPage();

    public abstract int getCurrentPageNumber();

    protected abstract void initActivity();

    protected abstract Bundle prepareBundle(Bundle bundle);

    public abstract void turnToFirstPage();

    public abstract void turnToPage(int i);

    public AbstractPluginActivity() {
        this.ocl_back_button = new C02431();
        this.ocl_next_button = new C02442();
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_frame = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_frame");
        this.R_ID_m_BackButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_BackButton");
        this.R_ID_m_NextButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_NextButton");
        this.R_STRING_OK = ResourceSearcher.getId(context, RS_TYPE.STRING, "OK");
    }

    public void onCreate(Bundle bundle) {
        Bundle extras;
        super.onCreate(bundle);
        requestWindowFeature(1);
        GetResourceID(this);
        setContentView(this.R_LAYOUT_plugins_frame);
        initActivity();
        if (bundle == null) {
            extras = getIntent().getExtras();
        } else {
            extras = bundle;
        }
        generateSubpages(extras);
        findViewById(this.R_ID_m_BackButton).setOnClickListener(this.ocl_back_button);
        findViewById(this.R_ID_m_NextButton).setOnClickListener(this.ocl_next_button);
        if (bundle != null) {
            turnToPage(bundle.getInt("currentPage", 1));
        } else {
            turnToFirstPage();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        prepareBundle(outState);
        outState.putInt("currentPage", getCurrentPageNumber());
        super.onSaveInstanceState(outState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getCurrentPage().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBackPressed() {
        turnToPage(getCurrentPageNumber() - 1);
    }

    public void showMessage(String message) {
        new Builder(this).setMessage(message).setPositiveButton(this.R_STRING_OK, null).show();
    }

    public void showMessage(int message) {
        new Builder(this).setMessage(message).setPositiveButton(this.R_STRING_OK, null).show();
    }

    public void goFinish() {
        Intent intent = new Intent();
        intent.putExtras(prepareBundle(null));
        setResult(100, intent);
        finish();
    }

    public void CloseNextButton() {
        findViewById(this.R_ID_m_NextButton).setVisibility(8);
    }
}
