package com.hiti.plugins.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import org.xmlpull.v1.XmlPullParser;

public class TextDrawer extends View implements AbstractDrawer {
    private static String ASSET_TEXT_PATH = null;
    public static final String font_from_assets_indicator = "@ASSETS/";
    private int color;
    private String font_path;
    private String text;
    private float textSize;
    private Typeface typeface;

    static {
        ASSET_TEXT_PATH = "FONT_PRINGO";
    }

    public TextDrawer(Context context) {
        super(context);
        this.text = null;
        this.textSize = 50.0f;
        this.color = ViewCompat.MEASURED_STATE_MASK;
        init();
    }

    public TextDrawer(Context context, Typeface font) {
        super(context);
        this.text = null;
        this.textSize = 50.0f;
        this.color = ViewCompat.MEASURED_STATE_MASK;
        setFont(font);
        init();
    }

    public TextDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.text = null;
        this.textSize = 50.0f;
        this.color = ViewCompat.MEASURED_STATE_MASK;
        init();
    }

    public void init() {
        setTextSize(2, 30);
    }

    public void setTextColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setFont(Typeface font) {
        this.typeface = font;
    }

    public static Typeface createTypefaceFromPath(Context context, String fontPath) {
        Typeface typeface = null;
        if (fontPath.startsWith(font_from_assets_indicator)) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), ASSET_TEXT_PATH + "/" + fontPath.replace(font_from_assets_indicator, XmlPullParser.NO_NAMESPACE));
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FONT ERROR", fontPath);
            } catch (Error e2) {
                e2.printStackTrace();
                Log.d("FONT ERROR", fontPath);
            }
        } else {
            try {
                typeface = Typeface.createFromFile(fontPath);
            } catch (Exception e3) {
                e3.printStackTrace();
                Log.d("FONT ERROR", fontPath);
            } catch (Error e22) {
                e22.printStackTrace();
                Log.d("FONT ERROR", fontPath);
            }
        }
        return typeface;
    }

    public void setText(String string) {
        this.text = string;
    }

    public void setTextSize(int complexUnitSp, int i) {
        this.textSize = TypedValue.applyDimension(complexUnitSp, (float) i, getContext().getResources().getDisplayMetrics());
    }

    public void setFontFromPath(String fontPath) {
        this.font_path = fontPath;
        setFont(createTypefaceFromPath(getContext(), fontPath));
    }

    public void setFontPath(String fontPath) {
        this.font_path = fontPath;
    }

    public void setFontPathFromAssets(String fontPath) {
        this.font_path = font_from_assets_indicator + fontPath;
    }

    public String getFontPath() {
        return this.font_path;
    }

    public String getText() {
        return this.text;
    }

    public Typeface getFont() {
        return this.typeface;
    }

    private Paint preparePaint() {
        Paint paint = new Paint();
        paint.setTypeface(this.typeface);
        paint.setTextSize(this.textSize);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        return paint;
    }

    public int getBitmapHeight() {
        FontMetrics fm = preparePaint().getFontMetrics();
        return (int) (((((fm.bottom - fm.top) + fm.leading) * ((float) this.text.split("\n").length)) + fm.top) - fm.ascent);
    }

    public int getBitmapHeightNoPadding() {
        FontMetrics fm = preparePaint().getFontMetrics();
        return (int) ((((fm.bottom - fm.top) * ((float) this.text.split("\n").length)) + fm.top) - fm.ascent);
    }

    public int getBitmapWidth() {
        Paint paint = preparePaint();
        int max = 0;
        for (String s : getText().toString().split("\n")) {
            if (((float) max) < paint.measureText(s)) {
                max = (int) paint.measureText(" " + s + " ");
            }
        }
        return max;
    }

    public int getBitmapWidthNoPadding() {
        Paint paint = preparePaint();
        int max = 0;
        for (String s : getText().toString().split("\n")) {
            if (((float) max) < paint.measureText(s)) {
                max = (int) paint.measureText(s);
            }
        }
        return max;
    }

    public void drawBitmap(Bitmap bitmap) {
        Paint paint = preparePaint();
        FontMetrics fm = paint.getFontMetrics();
        paint.setTextAlign(Align.CENTER);
        paint.setColor(this.color);
        Canvas canvas = new Canvas(bitmap);
        int height = (int) ((fm.bottom - fm.top) + fm.leading);
        int center = canvas.getWidth() / 2;
        int position = (int) (((((float) height) - fm.bottom) - fm.ascent) + fm.top);
        for (String s : getText().toString().split("\n")) {
            canvas.drawText(s, (float) center, (float) position, paint);
            position += height;
        }
    }

    public void drawBitmapNoPadding(Bitmap bitmap) {
        Paint paint = preparePaint();
        FontMetrics fm = paint.getFontMetrics();
        paint.setTextAlign(Align.CENTER);
        paint.setColor(this.color);
        Canvas canvas = new Canvas(bitmap);
        int height = (int) (fm.bottom - fm.top);
        int center = canvas.getWidth() / 2;
        int position = (int) fm.top;
        for (String s : getText().toString().split("\n")) {
            canvas.drawText(s, (float) center, (float) position, paint);
            position += height;
        }
    }

    public void onDraw(Canvas c) {
        Log.d("ABC", "Drawing " + hashCode());
        Paint paint = new Paint();
        paint.setTypeface(this.typeface);
        paint.setTextSize(this.textSize);
        paint.setColor(this.color);
        FontMetrics fm = paint.getFontMetrics();
        c.drawText(this.text.replace('\n', ' '), 0.0f, (float) ((int) ((((-fm.top) + fm.leading) + fm.top) - fm.ascent)), paint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        Paint paint = preparePaint();
        FontMetrics fm = paint.getFontMetrics();
        int SingleLineHeight = (int) ((((fm.bottom - fm.top) + fm.leading) + fm.top) - fm.ascent);
        int SingleLineWidth = (int) paint.measureText(this.text);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == 1073741824) {
            width = widthSize;
        } else {
            width = Math.max(SingleLineWidth, getSuggestedMinimumWidth());
            if (widthMode == ExploreByTouchHelper.INVALID_ID) {
                width = Math.min(widthSize, width);
            }
        }
        if (heightMode == 1073741824) {
            height = heightSize;
        } else {
            int desired = SingleLineHeight;
            height = desired;
            if (heightMode == ExploreByTouchHelper.INVALID_ID) {
                height = Math.min(desired, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }
}
