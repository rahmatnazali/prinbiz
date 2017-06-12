package com.hiti.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.hiti.utility.LogManager;

public class AnimaEffect {
    LogManager LOG;
    String TAG;
    Runnable mAnimation;
    Context mContext;
    int mLastTime;
    boolean mStop;
    View mTargetView;
    int mType;
    Runnable mWaitMoment;

    /* renamed from: com.hiti.animation.AnimaEffect.1 */
    class C02301 implements Runnable {
        C02301() {
        }

        public void run() {
            AnimaEffect.this.LOG.m385i(AnimaEffect.this.TAG, "run stop: " + AnimaEffect.this.mStop + " " + AnimaEffect.this.mLastTime);
            if (!AnimaEffect.this.mStop) {
                AnimaEffect.this.mTargetView.setAlpha(1.0f);
                AnimaEffect.this.mTargetView.startAnimation(AnimationUtils.loadAnimation(AnimaEffect.this.mContext, AnimaEffect.this.mType));
                AnimaEffect.this.mTargetView.postOnAnimationDelayed(AnimaEffect.this.mWaitMoment, (long) AnimaEffect.this.mLastTime);
            }
        }
    }

    /* renamed from: com.hiti.animation.AnimaEffect.2 */
    class C02312 implements Runnable {
        C02312() {
        }

        public void run() {
            if (!AnimaEffect.this.mStop) {
                AnimaEffect.this.mTargetView.clearAnimation();
                AnimaEffect.this.mTargetView.setAlpha(0.0f);
                AnimaEffect.this.mTargetView.postOnAnimationDelayed(AnimaEffect.this.mAnimation, 200);
            }
        }
    }

    public enum TypeAnima {
        shine,
        alet,
        fade
    }

    public AnimaEffect(Context context) {
        this.mContext = null;
        this.mStop = false;
        this.mType = 0;
        this.mTargetView = null;
        this.mLastTime = 0;
        this.LOG = null;
        this.TAG = null;
        this.mAnimation = new C02301();
        this.mWaitMoment = new C02312();
        this.mContext = context;
        this.LOG = new LogManager(0);
        this.TAG = "AnimaEffect";
    }

    public void Start(View view, int animationType, int iLastTime) {
        this.mType = animationType;
        this.mTargetView = view;
        this.mLastTime = iLastTime;
        this.mStop = false;
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        this.LOG.m386v(this.TAG, "Start animation: " + iLastTime);
        this.mTargetView.startAnimation(AnimationUtils.loadAnimation(this.mContext, this.mType));
        view.postOnAnimation(this.mAnimation);
    }

    public boolean IsStop() {
        this.LOG.m385i(this.TAG, "IsStop: " + this.mStop + " " + this.mLastTime);
        return this.mStop;
    }

    public void Remove(View view) {
        this.mStop = true;
        view.clearAnimation();
        view.removeCallbacks(this.mAnimation);
        view.removeCallbacks(this.mWaitMoment);
        view.setAlpha(1.0f);
    }
}
