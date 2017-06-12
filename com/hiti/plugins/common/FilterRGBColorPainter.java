package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import org.apache.commons.net.nntp.NNTPReply;

public class FilterRGBColorPainter extends LinearLayout {
    private static final int RANGE_RGB = 100;
    private int R_DRAWABLE_filter_color_painter_BlueYellow;
    private int R_DRAWABLE_filter_color_painter_GreenMagenta;
    private int R_DRAWABLE_filter_color_painter_RedCyan;
    private int R_ID_m_ColorPainter_BlueYellow;
    private int R_ID_m_ColorPainter_GreenMagenta;
    private int R_ID_m_ColorPainter_RedCyan;
    private int R_LAYOUT_view_filter_color_painter;
    private Context m_Context;
    ColorPainter_Scorller m_FilterColorPainter_BLUE_YELLOW;
    ColorPainter_Scorller m_FilterColorPainter_GREEN_MAGENTA;
    ColorPainter_Scorller m_FilterColorPainter_RED_CYAN;
    OnRGBColorScrollerChangedListener m_FilterRGBColorPainterOutListener;
    float m_fBlueYellow;
    float m_fGreenMagenta;
    float m_fRedCyan;
    int m_iColor;
    View view;

    /* renamed from: com.hiti.plugins.common.FilterRGBColorPainter.1 */
    class C08311 extends OnRGBColorScrollerChangedListener {
        C08311() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterRGBColorPainter.this.m_fRedCyan = ColorPainter_Utility.PositionToFilterColor(value, FilterRGBColorPainter.this.m_FilterColorPainter_RED_CYAN.GetMaxValue(), FilterRGBColorPainter.RANGE_RGB);
            FilterRGBColorPainter.this.GetRGB();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }

        public void onRGBcolorChanged(float fRed, float fGreen, float fBlue) {
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorPainter.2 */
    class C08322 extends OnRGBColorScrollerChangedListener {
        C08322() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterRGBColorPainter.this.m_fGreenMagenta = ColorPainter_Utility.PositionToFilterColor(value, FilterRGBColorPainter.this.m_FilterColorPainter_GREEN_MAGENTA.GetMaxValue(), FilterRGBColorPainter.RANGE_RGB);
            FilterRGBColorPainter.this.GetRGB();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }

        public void onRGBcolorChanged(float fRed, float fGreen, float fBlue) {
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorPainter.3 */
    class C08333 extends OnRGBColorScrollerChangedListener {
        C08333() {
        }

        public void onColorChanged(float value, int colorType) {
            FilterRGBColorPainter.this.m_fBlueYellow = ColorPainter_Utility.PositionToFilterColor(value, FilterRGBColorPainter.this.m_FilterColorPainter_BLUE_YELLOW.GetMaxValue(), FilterRGBColorPainter.RANGE_RGB);
            FilterRGBColorPainter.this.GetRGB();
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }

        public void onRGBcolorChanged(float fRed, float fGreen, float fBlue) {
        }
    }

    public FilterRGBColorPainter(Context context) {
        super(context);
        this.m_FilterColorPainter_RED_CYAN = null;
        this.m_FilterColorPainter_GREEN_MAGENTA = null;
        this.m_FilterColorPainter_BLUE_YELLOW = null;
        this.m_iColor = 0;
        this.m_fRedCyan = 0.0f;
        this.m_fGreenMagenta = 0.0f;
        this.m_fBlueYellow = 0.0f;
        this.m_Context = null;
        this.m_Context = context;
        init(context);
    }

    public FilterRGBColorPainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_FilterColorPainter_RED_CYAN = null;
        this.m_FilterColorPainter_GREEN_MAGENTA = null;
        this.m_FilterColorPainter_BLUE_YELLOW = null;
        this.m_iColor = 0;
        this.m_fRedCyan = 0.0f;
        this.m_fGreenMagenta = 0.0f;
        this.m_fBlueYellow = 0.0f;
        this.m_Context = null;
        this.m_Context = context;
        init(context);
    }

    private void init(Context context) {
        GetResourceID(context);
        InitRGBPainter();
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_view_filter_color_painter = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "view_filter_rgb_color_painter");
        this.R_ID_m_ColorPainter_RedCyan = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_RedCyan");
        this.R_ID_m_ColorPainter_GreenMagenta = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_GreenMagenta");
        this.R_ID_m_ColorPainter_BlueYellow = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ColorPainter_BlueYellow");
        this.R_DRAWABLE_filter_color_painter_RedCyan = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_rc");
        this.R_DRAWABLE_filter_color_painter_GreenMagenta = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_gm");
        this.R_DRAWABLE_filter_color_painter_BlueYellow = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "color_painter_by");
    }

    private void InitRGBPainter() {
        this.view = View.inflate(this.m_Context, this.R_LAYOUT_view_filter_color_painter, this);
        int iContrastW = this.m_Context.getResources().getDisplayMetrics().widthPixels;
        int iContrastH = iContrastW / 16;
        int fColourLeft = (iContrastW * 53) / NNTPReply.AUTHENTICATION_REQUIRED;
        int fColourTop = (iContrastH * 0) / 30;
        int fColourRight = (iContrastW * 427) / NNTPReply.AUTHENTICATION_REQUIRED;
        int fColourBottom = (iContrastH * 30) / 30;
        this.m_FilterColorPainter_RED_CYAN = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_RedCyan);
        this.m_FilterColorPainter_RED_CYAN.InitArea(iContrastW, iContrastH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_RED_CYAN.setOnColorScrollerChangedListener(new C08311());
        this.m_FilterColorPainter_RED_CYAN.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        LayoutParams params1 = new LayoutParams(-2, -2);
        params1.width = iContrastW;
        params1.height = iContrastH;
        this.m_FilterColorPainter_RED_CYAN.setLayoutParams(params1);
        this.m_FilterColorPainter_GREEN_MAGENTA = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_GreenMagenta);
        this.m_FilterColorPainter_GREEN_MAGENTA.InitArea(iContrastW, iContrastH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_GREEN_MAGENTA.setOnColorScrollerChangedListener(new C08322());
        this.m_FilterColorPainter_GREEN_MAGENTA.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        LayoutParams params2 = new LayoutParams(-2, -2);
        params2.width = iContrastW;
        params2.height = iContrastH;
        this.m_FilterColorPainter_GREEN_MAGENTA.setLayoutParams(params2);
        this.m_FilterColorPainter_BLUE_YELLOW = (ColorPainter_Scorller) this.view.findViewById(this.R_ID_m_ColorPainter_BlueYellow);
        this.m_FilterColorPainter_BLUE_YELLOW.InitArea(iContrastW, iContrastH, (float) fColourLeft, (float) fColourTop, (float) fColourRight, (float) fColourBottom);
        this.m_FilterColorPainter_BLUE_YELLOW.setOnColorScrollerChangedListener(new C08333());
        this.m_FilterColorPainter_BLUE_YELLOW.SetInitPosition(ColorPainter_Scorller.BAR_CENTER);
        LayoutParams params3 = new LayoutParams(-2, -2);
        params3.width = iContrastW;
        params3.height = iContrastH;
        this.m_FilterColorPainter_BLUE_YELLOW.setLayoutParams(params3);
    }

    void GetRGB() {
        if (this.m_FilterRGBColorPainterOutListener != null) {
            this.m_FilterRGBColorPainterOutListener.onRGBcolorChanged(this.m_fRedCyan, this.m_fGreenMagenta, this.m_fBlueYellow);
        }
    }

    void SetRGBtoPostion(float fRedCyan, float fGreenMagenta, float fBlueYellow) {
        this.m_fRedCyan = fRedCyan;
        this.m_fGreenMagenta = fGreenMagenta;
        this.m_fBlueYellow = fBlueYellow;
        this.m_FilterColorPainter_RED_CYAN.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(this.m_fRedCyan, this.m_FilterColorPainter_RED_CYAN.GetMaxValue(), RANGE_RGB));
        this.m_FilterColorPainter_GREEN_MAGENTA.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(this.m_fGreenMagenta, this.m_FilterColorPainter_GREEN_MAGENTA.GetMaxValue(), RANGE_RGB));
        this.m_FilterColorPainter_BLUE_YELLOW.OnlySetPosition(ColorPainter_Utility.FilterColorToPosition(this.m_fBlueYellow, this.m_FilterColorPainter_BLUE_YELLOW.GetMaxValue(), RANGE_RGB));
    }

    public void setOnColorChangedListener(OnRGBColorScrollerChangedListener outListener) {
        this.m_FilterRGBColorPainterOutListener = outListener;
    }

    public void Enable(boolean boEnable) {
        if (boEnable) {
            this.m_FilterColorPainter_RED_CYAN.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_RedCyan);
            this.m_FilterColorPainter_GREEN_MAGENTA.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_GreenMagenta);
            this.m_FilterColorPainter_BLUE_YELLOW.setBackgroundResource(this.R_DRAWABLE_filter_color_painter_BlueYellow);
            this.m_FilterColorPainter_RED_CYAN.Enable(true);
            this.m_FilterColorPainter_GREEN_MAGENTA.Enable(true);
            this.m_FilterColorPainter_BLUE_YELLOW.Enable(true);
        }
    }

    public int GetColorRange() {
        return RANGE_RGB;
    }
}
