package com.hiti.ui.drawview;

import android.graphics.Matrix;

public abstract class DrawViewListener {
    public static final int VIEW_MODE_BORDER = 5;
    public static final int VIEW_MODE_BRUSH = 6;
    public static final int VIEW_MODE_COLOR_GARNISH = 4;
    public static final int VIEW_MODE_FILTER = 8;
    public static final int VIEW_MODE_GS_GARNISH = 3;
    public static final int VIEW_MODE_ID_PHOTO = 9;
    public static final int VIEW_MODE_NOTHING = 0;
    public static final int VIEW_MODE_ROLLER = 7;
    public static final int VIEW_STATE_ZOOM_END = 2;
    public static final int VIEW_STATE_ZOOM_START = 1;

    public abstract void OnBorderMode();

    public abstract void OnBrushMode();

    public abstract void OnColorGarnishMode();

    public abstract void OnFilterMode();

    public abstract void OnFocusGarnish();

    public abstract void OnGSGarnishMode();

    public abstract void OnIDPhotoMode(Matrix matrix);

    public abstract void OnMissFocusGarnish();

    public abstract void OnRollerMode();

    public abstract void OnZoomEnd(float f);

    public abstract void OnZoomStart(float f);
}
