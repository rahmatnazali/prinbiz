package com.hiti.snowglobe;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    ParticleView m_ParticleView;
    int m_SleepSpan;
    SurfaceHolder m_SurfaceHolder;
    boolean m_boRun;

    public DrawThread(ParticleView pv, SurfaceHolder holder) {
        this.m_boRun = false;
        this.m_SleepSpan = 1;
        this.m_ParticleView = pv;
        this.m_SurfaceHolder = holder;
        this.m_boRun = true;
    }

    public void Stop() {
        this.m_boRun = false;
    }

    @SuppressLint({"WrongCall"})
    public void run() {
        Canvas canvas = null;
        while (this.m_boRun) {
            try {
                canvas = this.m_SurfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (this.m_SurfaceHolder) {
                        this.m_ParticleView.onDraw(canvas);
                    }
                    if (canvas != null) {
                        this.m_SurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    try {
                        Thread.sleep((long) this.m_SleepSpan);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (canvas != null) {
                    this.m_SurfaceHolder.unlockCanvasAndPost(canvas);
                    return;
                } else {
                    return;
                }
            } catch (Exception e2) {
                try {
                    e2.printStackTrace();
                    if (canvas != null) {
                        this.m_SurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                } catch (Throwable th) {
                    if (canvas != null) {
                        this.m_SurfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
