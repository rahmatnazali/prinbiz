package com.hiti.plugins.common;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.nntp.NNTPReply;

public class ColorPainter extends LinearLayout {
    private int R_DRAWABLE_color_painter_brightness;
    private int R_DRAWABLE_color_painter_brightness_disable;
    private int R_DRAWABLE_color_painter_hue;
    private int R_DRAWABLE_color_painter_hue_disable;
    private int R_DRAWABLE_color_painter_saturation;
    private int R_DRAWABLE_color_painter_saturation_disable;
    private int R_ID_m_ColorPainter_Hue;
    private int R_ID_m_ColorPainter_Light;
    private int R_ID_m_ColorPainter_Saturation;
    private int R_LAYOUT_plugins_color_painter;
    ColorHistoryPainter m_ColorHistoryPainter;
    onColorChangedListener m_ColorPainterListener;
    onColorChangedListener m_ColorPainterOutListener;
    ColorPainter_Scorller m_ColorPainter_Hue;
    ColorPainter_Scorller m_ColorPainter_Light;
    ColorPainter_Scorller m_ColorPainter_Saturation;
    float m_fHue;
    float m_fLight;
    float m_fSaturation;
    int m_iColor;
    View view;

    /* renamed from: com.hiti.plugins.common.ColorPainter.1 */
    class C07321 extends onColorChangedListener {
        C07321() {
        }

        public void onColorChanged(int color, int type) {
            if (ColorPainter.this.m_ColorPainterOutListener != null) {
                ColorPainter.this.m_ColorPainterOutListener.onColorChanged(color, type);
            }
        }
    }

    /* renamed from: com.hiti.plugins.common.ColorPainter.2 */
    class C07332 extends OnColorScrollerChangedListener {
        C07332() {
        }

        public void onColorChanged(float value, int colorType) {
            float fHue = ColorPainter_Utility.PositionToHue(value, ColorPainter.this.m_ColorPainter_Hue.GetMaxValue());
            ColorPainter.this.m_fHue = fHue;
            ColorPainter.this.SetHSV(fHue, -1.0f, -1.0f);
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    /* renamed from: com.hiti.plugins.common.ColorPainter.3 */
    class C07343 extends OnColorScrollerChangedListener {
        C07343() {
        }

        public void onColorChanged(float value, int colorType) {
            ColorPainter.this.m_fLight = ColorPainter_Utility.PositionToLight(value, ColorPainter.this.m_ColorPainter_Light.GetMaxValue());
            ColorPainter.this.SetHSV(-1.0f, -1.0f, ColorPainter.this.m_fLight);
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    /* renamed from: com.hiti.plugins.common.ColorPainter.4 */
    class C07354 extends OnColorScrollerChangedListener {
        C07354() {
        }

        public void onColorChanged(float value, int colorType) {
            ColorPainter.this.m_fSaturation = ColorPainter_Utility.PositionToSaturation(value, ColorPainter.this.m_ColorPainter_Saturation.GetMaxValue());
            ColorPainter.this.SetHSV(-1.0f, ColorPainter.this.m_fSaturation, -1.0f);
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_plugins_color_painter = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "plugins_color_painter");
        this.R_ID_m_ColorPainter_Hue = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Hue");
        this.R_ID_m_ColorPainter_Light = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Light");
        this.R_ID_m_ColorPainter_Saturation = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_Saturation");
        this.R_DRAWABLE_color_painter_hue = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_hue");
        this.R_DRAWABLE_color_painter_saturation = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_saturation");
        this.R_DRAWABLE_color_painter_brightness = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_brightness");
        this.R_DRAWABLE_color_painter_hue_disable = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_hue_disable");
        this.R_DRAWABLE_color_painter_saturation_disable = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_saturation_disable");
        this.R_DRAWABLE_color_painter_brightness_disable = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_brightness_disable");
    }

    private void init(Context context) {
        GetResourceID(context);
        InitColorPainter(context);
    }

    public ColorPainter(Context context) {
        super(context);
        this.m_ColorPainter_Hue = null;
        this.m_ColorPainter_Light = null;
        this.m_ColorPainter_Saturation = null;
        this.m_iColor = 0;
        this.m_fHue = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fSaturation = 0.0f;
        init(context);
    }

    public ColorPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_ColorPainter_Hue = null;
        this.m_ColorPainter_Light = null;
        this.m_ColorPainter_Saturation = null;
        this.m_iColor = 0;
        this.m_fHue = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fSaturation = 0.0f;
        init(context);
    }

    void InitColorPainter(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.view = View.inflate(context, this.R_LAYOUT_plugins_color_painter, this);
        this.m_ColorPainterListener = new C07321();
        int ihueW = dm.widthPixels - 30;
        int ihueH = ihueW / 9;
        int fColourLeft = (ihueW * 27) / FTPReply.TRANSFER_ABORTED;
        int fColourTop = (ihueH * 0) / 47;
        int fColourRight = (ihueW * 402) / FTPReply.TRANSFER_ABORTED;
        int fColourBottom = (ihueH * 47) / 47;
        this.m_ColorPainter_Hue = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Hue);
        this.m_ColorPainter_Hue.InitArea(ihueW, ihueH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_ColorPainter_Hue.setOnColorScrollerChangedListener(new C07332());
        this.m_ColorPainter_Hue.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = ihueW;
        layoutParams.height = ihueH;
        this.m_ColorPainter_Hue.setLayoutParams(layoutParams);
        int iLightW = (dm.widthPixels / 2) - 30;
        int iLightH = iLightW / 4;
        fColourLeft = (iLightW * 27) / NNTPReply.DEBUG_OUTPUT;
        fColourTop = (iLightH * 0) / 47;
        fColourRight = (iLightW * 172) / NNTPReply.DEBUG_OUTPUT;
        fColourBottom = (iLightH * 47) / 47;
        this.m_ColorPainter_Light = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Light);
        this.m_ColorPainter_Light.InitArea(iLightW, iLightH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_ColorPainter_Light.setOnColorScrollerChangedListener(new C07343());
        this.m_ColorPainter_Light.SetInitPosition(ColorPainter_Scorller.BAR_RIGHT);
        layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = iLightW;
        layoutParams.height = iLightH;
        this.m_ColorPainter_Light.setLayoutParams(layoutParams);
        int iSaturationW = (dm.widthPixels / 2) - 30;
        int iSaturationH = iSaturationW / 4;
        fColourLeft = (iSaturationW * 27) / NNTPReply.DEBUG_OUTPUT;
        fColourTop = (iSaturationH * 0) / 47;
        fColourRight = (iSaturationW * 172) / NNTPReply.DEBUG_OUTPUT;
        fColourBottom = (iSaturationH * 47) / 47;
        this.m_ColorPainter_Saturation = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_Saturation);
        this.m_ColorPainter_Saturation.InitArea(iSaturationW, iSaturationH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_ColorPainter_Saturation.setOnColorScrollerChangedListener(new C07354());
        this.m_ColorPainter_Saturation.SetInitPosition(ColorPainter_Scorller.BAR_RIGHT);
        layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.width = iSaturationW;
        layoutParams.height = iSaturationH;
        this.m_ColorPainter_Saturation.setLayoutParams(layoutParams);
    }

    void SetHSV(float fHue, float fSaturation, float fLight) {
        if (fHue < 0.0f) {
            fHue = this.m_fHue;
        }
        if (fSaturation < 0.0f) {
            fSaturation = this.m_fSaturation;
        }
        if (fLight < 0.0f) {
            fLight = this.m_fLight;
        }
        Log.e("HUE", String.valueOf(fHue));
        Log.e("Saturation", String.valueOf(fSaturation));
        Log.e("Light", String.valueOf(fLight));
        this.m_iColor = Color.HSVToColor(new float[]{fHue, fSaturation, fLight});
        if (this.m_ColorPainterListener != null) {
            this.m_ColorPainterListener.onColorChanged(this.m_iColor, 0);
        }
    }

    public void setOnColorChangedListener(onColorChangedListener outListener) {
        this.m_ColorPainterOutListener = outListener;
    }

    public void Enable(boolean boEnable) {
        if (boEnable) {
            this.m_ColorPainter_Hue.setBackgroundResource(this.R_DRAWABLE_color_painter_hue);
            this.m_ColorPainter_Light.setBackgroundResource(this.R_DRAWABLE_color_painter_brightness);
            this.m_ColorPainter_Saturation.setBackgroundResource(this.R_DRAWABLE_color_painter_saturation);
            this.m_ColorPainter_Hue.Enable(true);
            this.m_ColorPainter_Light.Enable(true);
            this.m_ColorPainter_Saturation.Enable(true);
            return;
        }
        this.m_ColorPainter_Hue.setBackgroundResource(this.R_DRAWABLE_color_painter_hue_disable);
        this.m_ColorPainter_Light.setBackgroundResource(this.R_DRAWABLE_color_painter_brightness_disable);
        this.m_ColorPainter_Saturation.setBackgroundResource(this.R_DRAWABLE_color_painter_saturation_disable);
        this.m_ColorPainter_Hue.Enable(false);
        this.m_ColorPainter_Light.Enable(false);
        this.m_ColorPainter_Saturation.Enable(false);
    }
}
