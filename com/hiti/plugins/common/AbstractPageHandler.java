package com.hiti.plugins.common;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;

public abstract class AbstractPageHandler {
    protected AbstractPluginActivity activity;

    public abstract SparseArray<Object> getData();

    public abstract Object getData(int i);

    public abstract View getPage();

    public abstract String getTitle();

    public abstract void requestAction(int i, Object... objArr);

    public AbstractPageHandler(AbstractPluginActivity activity) {
        this.activity = activity;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
