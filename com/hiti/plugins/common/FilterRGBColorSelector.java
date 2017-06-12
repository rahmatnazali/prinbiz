package com.hiti.plugins.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.hiti.prinbiz.C0349R;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;

public class FilterRGBColorSelector extends FrameLayout {
    private static final int COLOR_HSLC = 1;
    private static final int COLOR_RGB = 2;
    private int R_DRAWABLE_button_filter_color_select_cancel;
    private int R_DRAWABLE_button_filter_color_select_ok;
    private int R_ID_filter_color_selector_painter;
    private int R_ID_filter_rgb_color_selector_painter;
    private int R_ID_m_HSLCButton;
    private int R_ID_m_OKButton;
    private int R_ID_m_RGBButton;
    private int R_ID_m_ResetButton;
    private int R_LAYOUT_view_filter_rgb_color_selector;
    private OnRGBColorScrollerChangedListener m_ColorPainterListener;
    private OnRGBFilterColorSelectChangedListener m_ColorSelectorOutListener;
    private Context m_Context;
    private Button m_HSLCButton;
    private FilterColorPainter m_HSLCColorPainter;
    private Button m_OKButton;
    private Button m_RGBButton;
    private FilterRGBColorPainter m_RGBColorPainter;
    private Button m_ResetButton;
    private View m_View;
    private int m_iType;

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.3 */
    class C02483 implements OnClickListener {
        C02483() {
        }

        public void onClick(View v) {
            FilterRGBColorSelector.this.onResetButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.4 */
    class C02494 implements OnClickListener {
        C02494() {
        }

        public void onClick(View v) {
            FilterRGBColorSelector.this.onOKButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.5 */
    class C02505 implements OnClickListener {
        C02505() {
        }

        public void onClick(View v) {
            FilterRGBColorSelector.this.onBrightnessButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.6 */
    class C02516 implements OnClickListener {
        C02516() {
        }

        public void onClick(View v) {
            FilterRGBColorSelector.this.onHueButtonClicked(v);
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.1 */
    class C08341 extends OnRGBColorScrollerChangedListener {
        C08341() {
        }

        public void onColorChanged(float value, int colorType) {
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
        }

        public void onRGBcolorChanged(float fRed, float fGreen, float fBlue) {
            if (FilterRGBColorSelector.this.m_ColorSelectorOutListener != null) {
                FilterRGBColorSelector.this.m_ColorSelectorOutListener.onRGBColorChanged(fRed, fGreen, fBlue);
            }
        }
    }

    /* renamed from: com.hiti.plugins.common.FilterRGBColorSelector.2 */
    class C08352 extends OnRGBColorScrollerChangedListener {
        C08352() {
        }

        public void onColorChanged(float value, int colorType) {
        }

        public void onColorChanged(float fHue, float fSaturation, float fLight, float fContrast) {
            if (FilterRGBColorSelector.this.m_ColorSelectorOutListener != null) {
                FilterRGBColorSelector.this.m_ColorSelectorOutListener.onColorChanged(fHue, fSaturation, fLight, fContrast);
            }
        }

        public void onRGBcolorChanged(float fRed, float fGreen, float fBlue) {
        }
    }

    private void GetResourceID(Context context) {
        this.R_LAYOUT_view_filter_rgb_color_selector = ResourceSearcher.getId(context, RS_TYPE.LAYOUT, "view_filter_rgb_color_selector");
        this.R_ID_filter_rgb_color_selector_painter = ResourceSearcher.getId(context, RS_TYPE.ID, "filter_rgb_color_selector_painter");
        this.R_ID_filter_color_selector_painter = ResourceSearcher.getId(context, RS_TYPE.ID, "filter_color_selector_painter");
        SetButton(context);
    }

    private void SetButton(Context context) {
        this.R_ID_m_ResetButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_ResetButton");
        this.R_ID_m_OKButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_OKButton");
        this.R_ID_m_HSLCButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_HSLCButton");
        this.R_ID_m_RGBButton = ResourceSearcher.getId(context, RS_TYPE.ID, "m_RGBButton");
        this.R_DRAWABLE_button_filter_color_select_ok = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "button_adjust_check");
        this.R_DRAWABLE_button_filter_color_select_cancel = ResourceSearcher.getId(context, RS_TYPE.DRAWABLE, "button_adjust_cancel");
    }

    public FilterRGBColorSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_iType = COLOR_HSLC;
        this.m_Context = null;
        this.m_View = null;
        this.m_Context = context;
        GetResourceID(context);
        initHSLC(context);
    }

    public FilterRGBColorSelector(Context context) {
        super(context);
        this.m_iType = COLOR_HSLC;
        this.m_Context = null;
        this.m_View = null;
        this.m_Context = context;
        GetResourceID(context);
        initHSLC(context);
    }

    private void initRGB(Context context) {
        this.m_iType = COLOR_RGB;
        this.m_ColorPainterListener = new C08341();
        this.m_RGBColorPainter.setOnColorChangedListener(this.m_ColorPainterListener);
        this.m_HSLCColorPainter.setVisibility(8);
        this.m_RGBColorPainter.setVisibility(0);
    }

    private void initHSLC(Context context) {
        this.m_iType = COLOR_HSLC;
        this.m_ColorPainterListener = new C08352();
        if (this.m_View == null) {
            this.m_View = inflate(context, this.R_LAYOUT_view_filter_rgb_color_selector, this);
            this.m_RGBColorPainter = (FilterRGBColorPainter) this.m_View.findViewById(this.R_ID_filter_rgb_color_selector_painter);
            this.m_HSLCColorPainter = (FilterColorPainter) this.m_View.findViewById(this.R_ID_filter_color_selector_painter);
            this.m_HSLCColorPainter.setOnColorChangedListener(this.m_ColorPainterListener);
            SetBtnListener(this.m_View);
        }
        this.m_HSLCColorPainter.setVisibility(0);
        this.m_RGBColorPainter.setVisibility(8);
    }

    private void SetBtnListener(View v) {
        this.m_ResetButton = (Button) v.findViewById(this.R_ID_m_ResetButton);
        this.m_ResetButton.setOnClickListener(new C02483());
        this.m_OKButton = (Button) v.findViewById(this.R_ID_m_OKButton);
        this.m_OKButton.setOnClickListener(new C02494());
        this.m_HSLCButton = (Button) v.findViewById(this.R_ID_m_HSLCButton);
        this.m_HSLCButton.setOnClickListener(new C02505());
        this.m_RGBButton = (Button) v.findViewById(this.R_ID_m_RGBButton);
        this.m_RGBButton.setOnClickListener(new C02516());
    }

    public void setOnColorChangedListener(OnRGBFilterColorSelectChangedListener outListener) {
        this.m_ColorSelectorOutListener = outListener;
    }

    public void SetRGBtoPostion(float fRedCyan, float fGreenMagenta, float fBlueYellow) {
        this.m_RGBColorPainter.SetRGBtoPostion(fRedCyan, fGreenMagenta, fBlueYellow);
    }

    public void SetHSVCAndPostion(float fHue, float fSaturation, float fLight, float fContrast) {
        this.m_HSLCColorPainter.SetHSLCAndPostion(fHue, fSaturation, fLight, fContrast);
    }

    public void onResetButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onResetButtonClicked(v);
        }
    }

    public void onBrightnessButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onBrightnessButtonClicked(v);
        }
        this.m_HSLCButton.setBackgroundResource(C0349R.drawable.brightness_button_clicked);
        this.m_RGBButton.setBackgroundResource(C0349R.drawable.hue_button);
        initHSLC(this.m_Context);
    }

    public void onHueButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onHueButtonClicked(v);
        }
        this.m_HSLCButton.setBackgroundResource(C0349R.drawable.brightness_button);
        this.m_RGBButton.setBackgroundResource(C0349R.drawable.hue_button_clicked);
        initRGB(this.m_Context);
    }

    public void onOKButtonClicked(View v) {
        if (this.m_ColorSelectorOutListener != null) {
            this.m_ColorSelectorOutListener.onOKButtonClicked(v);
        }
    }

    public void SetOKButtonStatus(boolean boOk) {
        if (boOk) {
            this.m_OKButton.setBackgroundResource(this.R_DRAWABLE_button_filter_color_select_ok);
        } else {
            this.m_OKButton.setBackgroundResource(this.R_DRAWABLE_button_filter_color_select_cancel);
        }
    }

    public int GetFilterType() {
        return this.m_iType;
    }

    public int GetColorRange() {
        return this.m_RGBColorPainter.GetColorRange();
    }
}
