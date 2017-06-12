package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.C0178R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.dynamic.zzg.zza;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int mColor;
    private int mSize;
    private OnClickListener rA;
    private Scope[] ry;
    private View rz;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorScheme {
    }

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, SIZE_STANDARD);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rA = null;
        zzb(context, attributeSet);
        setStyle(this.mSize, this.mColor, this.ry);
    }

    private static Button zza(Context context, int i, int i2, Scope[] scopeArr) {
        Button com_google_android_gms_common_internal_zzag = new zzag(context);
        com_google_android_gms_common_internal_zzag.zza(context.getResources(), i, i2, scopeArr);
        return com_google_android_gms_common_internal_zzag;
    }

    private void zzb(Context context, AttributeSet attributeSet) {
        int i = SIZE_STANDARD;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C0178R.styleable.SignInButton, SIZE_STANDARD, SIZE_STANDARD);
        try {
            this.mSize = obtainStyledAttributes.getInt(C0178R.styleable.SignInButton_buttonSize, SIZE_STANDARD);
            this.mColor = obtainStyledAttributes.getInt(C0178R.styleable.SignInButton_colorScheme, SIZE_ICON_ONLY);
            String string = obtainStyledAttributes.getString(C0178R.styleable.SignInButton_scopeUris);
            if (string == null) {
                this.ry = null;
            } else {
                String[] split = string.trim().split("\\s+");
                this.ry = new Scope[split.length];
                while (i < split.length) {
                    this.ry[i] = new Scope(split[i].toString());
                    i += SIZE_WIDE;
                }
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    private void zzca(Context context) {
        if (this.rz != null) {
            removeView(this.rz);
        }
        try {
            this.rz = zzaf.zzb(context, this.mSize, this.mColor, this.ry);
        } catch (zza e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            this.rz = zza(context, this.mSize, this.mColor, this.ry);
        }
        addView(this.rz);
        this.rz.setEnabled(isEnabled());
        this.rz.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.rA != null && view == this.rz) {
            this.rA.onClick(this);
        }
    }

    public void setColorScheme(int i) {
        setStyle(this.mSize, i, this.ry);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.rz.setEnabled(z);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.rA = onClickListener;
        if (this.rz != null) {
            this.rz.setOnClickListener(this);
        }
    }

    public void setScopes(Scope[] scopeArr) {
        setStyle(this.mSize, this.mColor, scopeArr);
    }

    public void setSize(int i) {
        setStyle(i, this.mColor, this.ry);
    }

    public void setStyle(int i, int i2) {
        setStyle(i, i2, this.ry);
    }

    public void setStyle(int i, int i2, Scope[] scopeArr) {
        this.mSize = i;
        this.mColor = i2;
        this.ry = scopeArr;
        zzca(getContext());
    }
}
