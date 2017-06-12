package com.hiti.plugins.qrcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import com.hiti.plugins.common.AbstractPageHandler;
import com.hiti.plugins.common.AbstractPluginActivity;
import com.hiti.plugins.drawer.QRCodeDrawer;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class QRCodePreviewHandler extends AbstractPageHandler {
    public static final int ACTION_CLEAR_PREVIEW_CONTAINER = 1;
    public static final int ACTION_DISPLAY_QRCODE = 2;
    public static final int DATA_IMAGEURL = 6;
    private int R_ID_qrcode_plugin_preview_chooseimage;
    private int R_ID_qrcode_plugin_preview_qrcode_container;
    private int R_LAYOUT_plugins_qrcode_preview;
    private int R_STRING_ERROR;
    private int R_STRING_preview;
    private Button chooseimage;
    private String imageurl;
    OnClickListener ocl_chooseimage;
    private ViewGroup previewContainer;
    private View previewPage;
    private QRCodeDrawer qrdrawer;

    /* renamed from: com.hiti.plugins.qrcode.QRCodePreviewHandler.1 */
    class C02521 implements OnClickListener {
        C02521() {
        }

        public void onClick(View v) {
            Intent it = new Intent("android.intent.action.GET_CONTENT");
            it.addCategory("android.intent.category.OPENABLE");
            it.setType("image/*");
            QRCodePreviewHandler.this.activity.startActivityForResult(it, 0);
        }
    }

    public QRCodePreviewHandler(AbstractPluginActivity activity, Bundle bundle) {
        super(activity);
        this.ocl_chooseimage = new C02521();
        GetResourceID(activity);
        this.previewPage = View.inflate(activity, this.R_LAYOUT_plugins_qrcode_preview, null);
        findViews();
        this.chooseimage.setOnClickListener(this.ocl_chooseimage);
        if (bundle != null) {
            this.imageurl = bundle.getString("image");
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_qrcode_preview = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_qrcode_preview");
        this.R_STRING_preview = ResourceSearcher.getId(context, RS_TYPE.STRING, "preview");
        this.R_ID_qrcode_plugin_preview_qrcode_container = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_preview_qrcode_container");
        this.R_ID_qrcode_plugin_preview_chooseimage = ResourceSearcher.getId(context, RS_TYPE.ID, "qrcode_plugin_preview_chooseimage");
        this.R_STRING_ERROR = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR");
    }

    public SparseArray<Object> getData() {
        SparseArray<Object> data = new SparseArray(DATA_IMAGEURL);
        data.put(DATA_IMAGEURL, getImageUrl());
        return data;
    }

    private String getImageUrl() {
        return this.imageurl;
    }

    public Object getData(int data) {
        switch (data) {
            case DATA_IMAGEURL /*6*/:
                return getImageUrl();
            default:
                return null;
        }
    }

    public void requestAction(int action, Object... params) {
        switch (action) {
            case ACTION_CLEAR_PREVIEW_CONTAINER /*1*/:
                clearPreviewContainer();
            default:
        }
    }

    public String getTitle() {
        return this.activity.getString(this.R_STRING_preview);
    }

    private void findViews() {
        this.previewContainer = (ViewGroup) this.previewPage.findViewById(this.R_ID_qrcode_plugin_preview_qrcode_container);
        this.chooseimage = (Button) this.previewPage.findViewById(this.R_ID_qrcode_plugin_preview_chooseimage);
    }

    public View getPage() {
        return this.previewPage;
    }

    public void clearPreviewContainer() {
        this.previewContainer.removeAllViews();
        ProgressBar progressbar = new ProgressBar(this.previewPage.getContext());
        LayoutParams layoutparams = new LayoutParams(-2, -2);
        layoutparams.gravity = 17;
        progressbar.setLayoutParams(layoutparams);
        this.previewContainer.addView(progressbar);
    }

    public ViewGroup getPreviewContainer() {
        return this.previewContainer;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            this.imageurl = data.getData().toString();
            if (this.qrdrawer != null) {
                this.qrdrawer.setPic(this.imageurl);
                this.qrdrawer.invalidate();
            }
        }
    }
}
