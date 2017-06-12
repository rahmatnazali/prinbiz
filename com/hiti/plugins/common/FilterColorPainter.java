package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import org.apache.commons.net.nntp.NNTPReply;

public class FilterColorPainter extends LinearLayout {
    private static final int RANGE_CONTRAST = 100;
    private static final int RANGE_LIGHT = 200;
    private static final int RANGE_SATURATION = 200;
    private int R_DRAWABLE_filter_color_painter_brightness;
    private int R_DRAWABLE_filter_color_painter_contrast;
    private int R_DRAWABLE_filter_color_painter_saturation;
    private int R_ID_m_ColorPainter_Contrast;
    private int R_ID_m_ColorPainter_Light;
    private int R_ID_m_ColorPainter_Saturation;
    private int R_LAYOUT_view_filter_color_painter;
    OnColorScrollerChangedListener m_FilterColorPainterOutListener;
    ColorPainter_Scorller m_FilterColorPainter_Contrast;
    ColorPainter_Scorller m_FilterColorPainter_Light;
    ColorPainter_Scorller m_FilterColorPainter_Saturation;
    float m_fContrast;
    float m_fHue;
    float m_fLight;
    float m_fSaturation;
    int m_iColor;
    View view;

    /* renamed from: com.hiti.plugins.common.FilterColorPainter.1 */
    class C07371 extends OnColorScrollerChangedListener {
        C07371() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterColorPainter.this.m_fContrast = ColorPainter_Utility.PositionToFilterColor(value, FilterColorPainter.this.m_FilterColorPainter_Contrast.GetMaxValue(), FilterColorPainter.RANGE_CONTRAST);
            FilterColorPainter.this.SetHSLC();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterColorPainter.2 */
    class C07382 extends OnColorScrollerChangedListener {
        C07382() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterColorPainter.this.m_fLight = ColorPainter_Utility.PositionToFilterColor(value, FilterColorPainter.this.m_FilterColorPainter_Light.GetMaxValue(), FilterColorPainter.RANGE_SATURATION);
            FilterColorPainter.this.SetHSLC();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterColorPainter.3 */
    class C07393 extends OnColorScrollerChangedListener {
        C07393() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterColorPainter.this.m_fSaturation = ColorPainter_Utility.PositionToFilterColor(value, FilterColorPainter.this.m_FilterColorPainter_Saturation.GetMaxValue(), FilterColorPainter.RANGE_SATURATION);
            FilterColorPainter.this.SetHSLC();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_view_filter_color_painter = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "view_filter_color_painter");
        this.R_ID_m_ColorPainter_Contrast = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Contrast");
        this.R_ID_m_ColorPainter_Light = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Light");
        this.R_ID_m_ColorPainter_Saturation = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Saturation");
        this.R_DRAWABLE_filter_color_painter_brightness = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "filter_color_painter_brightness");
        this.R_DRAWABLE_filter_color_painter_saturation = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "filter_color_painter_saturation");
        this.R_DRAWABLE_filter_color_painter_contrast = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "filter_color_painter_contrast");
    }

    private void init(Context context) {
        GetResourceID(context);
        InitColorPainter(context);
    }

    public FilterColorPainter(Context context) {
        super(context);
        this.m_FilterColorPainter_Contrast = null;
        this.m_FilterColorPainter_Light = null;
        this.m_FilterColorPainter_Saturation = null;
        this.m_iColor = 0;
        this.m_fHue = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fSaturation = 0.0f;
        this.m_fContrast = 0.0f;
        init(context);
    }

    public FilterColorPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_FilterColorPainter_Contrast = null;
        this.m_FilterColorPainter_Light = null;
        this.m_FilterColorPainter_Saturation = null;
        this.m_iColor = 0;
        this.m_fHue = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fSaturation = 0.0f;
        this.m_fContrast = 0.0f;
        init(context);
    }

    void InitColorPainter(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.view = View.inflate(context, this.R_LAYOUT_view_filter_color_painter, this);
        int iContrastW = dm.widthPixels;
        int iContrastH = iContrastW / 16;
        int fColourLeft = (iContrastW * 53) / NNTPReply.AUTHENTICATION_REQUIRED;
        int fColourTop = (iContrastH * 0) / 30;
        int fColourRight = (iContrastW * 427) / NNTPReply.AUTHENTICATION_REQUIRED;
        int fColourBottom = (iContrastH * 30) / 30;
        this.m_FilterColorPainter_Contrast = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Contrast);
        this.m_FilterColorPainter_Contrast.InitArea(iContrastW, iContrastH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_Contrast.setOnColorScrollerChangedListener(new C07371());
        this.m_FilterColorPainter_Contrast.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = iContrastW;
        layoutParams.height = iContrastH;
        this.m_FilterColorPainter_Contrast.setLayoutParams(layoutParams);
        int iLightW = dm.widthPixels;
        int iLightH = iLightW / 16;
        fColourLeft = (iLightW * 53) / NNTPReply.AUTHENTICATION_REQUIRED;
        fColourTop = (iLightH * 0) / 30;
        fColourRight = (iLightW * 427) / NNTPReply.AUTHENTICATION_REQUIRED;
        fColourBottom = (iLightH * 30) / 30;
        this.m_FilterColorPainter_Light = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Light);
        this.m_FilterColorPainter_Light.InitArea(iLightW, iLightH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_Light.setOnColorScrollerChangedListener(new C07382());
        this.m_FilterColorPainter_Light.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = iLightW;
        layoutParams.height = iLightH;
        this.m_FilterColorPainter_Light.setLayoutParams(layoutParams);
        int iSaturationW = dm.widthPixels;
        int iSaturationH = iSaturationW / 16;
        fColourLeft = (iSaturationW * 53) / NNTPReply.AUTHENTICATION_REQUIRED;
        fColourTop = (iSaturationH * 0) / 30;
        fColourRight = (iSaturationW * 427) / NNTPReply.AUTHENTICATION_REQUIRED;
        fColourBottom = (iSaturationH * 30) / 30;
        this.m_FilterColorPainter_Saturation = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Saturation);
        this.m_FilterColorPainter_Saturation.InitArea(iSaturationW, iSaturationH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_Saturation.setOnColorScrollerChangedListener(new C07393());
        this.m_FilterColorPainter_Saturation.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = iSaturationW;
        layoutParams.height = iSaturationH;
        this.m_FilterColorPainter_Saturation.setLayoutParams(layoutParams);
    }

    void SetHSLC() {
        Log.e("HUE", String.valueOf(this.m_fHue));
        Log.e("Saturation", String.valueOf(this.m_fSaturation));
        Log.e("Light", String.valueOf(this.m_fLight));
        Log.e("Contrast", String.valueOf(this.m_fContrast));
        if (this.m_FilterColorPainterOutListener != null) {
            this.m_FilterColorPainterOutListener.onColorChanged(this.m_fHue, this.m_fSaturation, this.m_fLight, this.m_fContrast);
        }
    }

    void SetHSLCAndPostion(float fHue, float fSaturation, float fLight, float fContrast) {
        this.m_fHue = fHue;
        this.m_fSaturation = fSaturation;
        this.m_fLight = fLight;
        this.m_fContrast = fContrast;
        this.m_FilterColorPainter_Saturation.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(fSaturation, this.m_FilterColorPainter_Saturation.GetMaxValue(), RANGE_SATURATION));
        this.m_FilterColorPainter_Light.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(fLight, this.m_FilterColorPainter_Light.GetMaxValue(), RANGE_SATURATION));
        this.m_FilterColorPainter_Contrast.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(fContrast, this.m_FilterColorPainter_Contrast.GetMaxValue(), RANGE_CONTRAST));
    }

    public void setOnColorChangedListener(OnColorScrollerChangedListener outListener) {
        this.m_FilterColorPainterOutListener = outListener;
    }

    public void Enable(boolean boEnable) {
        if (boEnable) {
            this.m_FilterColorPainter_Light.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_brightness);
            this.m_FilterColorPainter_Saturation.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_saturation);
            this.m_FilterColorPainter_Contrast.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_contrast);
            this.m_FilterColorPainter_Light.Enable(true);
            this.m_FilterColorPainter_Saturation.Enable(true);
            this.m_FilterColorPainter_Contrast.Enable(true);
        }
    }
}
