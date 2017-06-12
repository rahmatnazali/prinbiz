package com.hiti.snowglobe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.hiti.bitmapmanager.BitmapMonitor;
import com.hiti.bitmapmanager.BitmapMonitorResult;
import com.hiti.utility.FileUtility;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.net.telnet.TelnetOption;

public class ParticleView extends SurfaceView implements Callback {
    public static final int PARTICLE_VIEW_END = 1;
    public static final int PARTICLE_VIEW_START = 0;
    Bitmap m_BackgroundBitmap;
    PaintFlagsDrawFilter m_CanvasDrawFilter;
    Context m_Context;
    long m_DrawPeriod;
    DrawThread m_DrawThread;
    Bitmap m_ForegroundBitmap;
    Paint m_Paint;
    Handler m_ParentHandler;
    ParticleEngine m_ParticleEngine;
    ParticleGroup m_ParticleGroup;
    ParticlePhoto m_ParticlePhoto;
    int m_ParticleViewStatus;
    SurfaceHolder m_SurfaceHolder;

    public Bitmap GetBackgroundBitmap() {
        return this.m_BackgroundBitmap;
    }

    public void SetBackgroundBitmap(Bitmap backgroundBitmap) {
        this.m_BackgroundBitmap = backgroundBitmap;
    }

    public Bitmap GetForegroundBitmap() {
        return this.m_ForegroundBitmap;
    }

    public void SetForegroundBitmap(Bitmap foregroundBitmap) {
        this.m_ForegroundBitmap = foregroundBitmap;
    }

    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.m_ParticleViewStatus = 0;
        this.m_DrawPeriod = -1;
        this.m_BackgroundBitmap = null;
        this.m_ForegroundBitmap = null;
        this.m_Context = context;
        this.m_SurfaceHolder = getHolder();
        this.m_SurfaceHolder.addCallback(this);
        this.m_Paint = new Paint();
        this.m_Paint.setAntiAlias(true);
        this.m_CanvasDrawFilter = new PaintFlagsDrawFilter(0, 3);
        setZOrderOnTop(true);
        getHolder().setFormat(-3);
    }

    public ParticleView(Context context) {
        super(context);
        this.m_ParticleViewStatus = 0;
        this.m_DrawPeriod = -1;
        this.m_BackgroundBitmap = null;
        this.m_ForegroundBitmap = null;
        this.m_Context = context;
        this.m_SurfaceHolder = getHolder();
        this.m_SurfaceHolder.addCallback(this);
        this.m_Paint = new Paint();
        this.m_Paint.setAntiAlias(true);
        this.m_CanvasDrawFilter = new PaintFlagsDrawFilter(0, 3);
        setZOrderOnTop(true);
        getHolder().setFormat(-3);
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Canvas canvas = arg0.lockCanvas();
        if (!(canvas == null || GetBackgroundBitmap() == null)) {
            canvas.drawBitmap(GetBackgroundBitmap(), 0.0f, 0.0f, null);
        }
        if (!(canvas == null || GetForegroundBitmap() == null)) {
            canvas.drawBitmap(GetForegroundBitmap(), 0.0f, 0.0f, null);
        }
        arg0.unlockCanvasAndPost(canvas);
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        if (this.m_DrawThread != null) {
            this.m_DrawThread.Stop();
        }
    }

    private void SendMessage(int iMsg) {
        Message msg = Message.obtain();
        msg.what = iMsg;
        if (this.m_ParentHandler != null) {
            this.m_ParentHandler.sendMessage(msg);
        }
    }

    public void SetParticle(Handler parentHandler, int iMethod, ArrayList<Pair<String, String>> strPathPairList, ArrayList<Pair<Bitmap, Bitmap>> bmpPairArrayList, ArrayList<Integer> iTypeArrayList) {
        this.m_ParentHandler = parentHandler;
        if (this.m_ParticleGroup != null) {
            this.m_ParticleGroup.ClearParticleArrayList();
        }
        this.m_ParticleGroup = new ParticleGroup();
        if (this.m_ParticlePhoto != null) {
            this.m_ParticlePhoto.Clear();
        }
        this.m_ParticlePhoto = new ParticlePhoto(iMethod, strPathPairList, bmpPairArrayList, iTypeArrayList);
        this.m_ParticleEngine = new ParticleEngine(this);
        if (this.m_DrawThread != null) {
            this.m_DrawThread.Stop();
        }
        this.m_DrawThread = new DrawThread(this, this.m_SurfaceHolder);
    }

    public void StratDraw() {
        if (this.m_DrawThread != null) {
            if (!this.m_DrawThread.isAlive()) {
                this.m_DrawThread.start();
            }
            this.m_DrawPeriod = System.currentTimeMillis();
            SendMessage(0);
            this.m_ParticleViewStatus = 0;
        }
    }

    public void onDraw(Canvas canvas) {
        if (GetBackgroundBitmap() != null) {
            canvas.drawBitmap(GetBackgroundBitmap(), 0.0f, 0.0f, null);
        } else {
            canvas.drawColor(0, Mode.CLEAR);
        }
        if (this.m_ParticleGroup != null) {
            int i;
            if (System.currentTimeMillis() - this.m_DrawPeriod < 3000) {
                this.m_ParticleEngine.Move_Method_1();
            } else if (this.m_ParticleViewStatus == 0) {
                SendMessage(PARTICLE_VIEW_END);
                this.m_ParticleViewStatus = PARTICLE_VIEW_END;
            }
            ArrayList<Particle> particleSet = this.m_ParticleGroup.GetParticleArrayList();
            for (i = 0; i < particleSet.size(); i += PARTICLE_VIEW_END) {
                DrawPartcle(canvas, (Particle) particleSet.get(i), this.m_Paint, 0);
            }
            for (i = 0; i < particleSet.size(); i += PARTICLE_VIEW_END) {
                DrawPartcle(canvas, (Particle) particleSet.get(i), this.m_Paint, PARTICLE_VIEW_END);
            }
        }
        if (GetForegroundBitmap() != null) {
            canvas.drawBitmap(GetForegroundBitmap(), 0.0f, 0.0f, null);
        }
    }

    void DrawPartcle(Canvas canvas, Particle p, Paint paint, int iType) {
        float tempX = p.m_fCurrentX;
        float tempY = p.m_fCurrentY;
        float tempScale = p.m_fScale;
        int iAlpha = p.m_iAlpha;
        Pair<Bitmap, Bitmap> bmpPair = this.m_ParticlePhoto.GetPhoto(p.m_iBmpIndex);
        Bitmap bmp = null;
        if (iType == 0) {
            paint.setAlpha(iAlpha);
            if (p.m_iType == 0) {
                bmp = bmpPair.first;
            } else if (p.m_iType == 2) {
                bmp = bmpPair.first;
            }
        } else if (iType == PARTICLE_VIEW_END) {
            paint.setAlpha(TelnetOption.MAX_OPTION_VALUE);
            if (p.m_iType == PARTICLE_VIEW_END) {
                bmp = bmpPair.first;
            } else if (p.m_iType == 2) {
                bmp = bmpPair.second;
            }
        }
        if (bmp != null) {
            canvas.save();
            canvas.scale(tempScale, tempScale);
            canvas.drawBitmap(bmp, tempX, tempY, paint);
            canvas.restore();
        }
    }

    public BitmapMonitorResult GetOrgBitmap(int iType, float fViewScale, int iWidth, int iHeight) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(iWidth, iHeight, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            Canvas canvas = new Canvas(bmr.GetBitmap());
            canvas.setDrawFilter(this.m_CanvasDrawFilter);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            ArrayList<Particle> particleSet = this.m_ParticleGroup.GetParticleArrayList();
            for (int i = 0; i < particleSet.size(); i += PARTICLE_VIEW_END) {
                DrawOrgPartcle(canvas, (Particle) particleSet.get(i), paint, iType, fViewScale);
            }
        }
        return bmr;
    }

    private void DrawOrgPartcle(Canvas canvas, Particle p, Paint paint, int iType, float fViewScale) {
        float tempX = p.m_fCurrentX * fViewScale;
        float tempY = p.m_fCurrentY * fViewScale;
        float tempScale = p.m_fScale;
        int iAlpha = p.m_iAlpha;
        Pair<String, String> strPathPair = this.m_ParticlePhoto.GetPhotoPath(p.m_iBmpIndex);
        String strBmpPath = null;
        if (iType == 0) {
            paint.setAlpha(iAlpha);
            if (p.m_iType == 0) {
                strBmpPath = strPathPair.first;
            } else if (p.m_iType == 2) {
                strBmpPath = strPathPair.first;
            }
        } else if (iType == PARTICLE_VIEW_END) {
            paint.setAlpha(TelnetOption.MAX_OPTION_VALUE);
            if (p.m_iType == PARTICLE_VIEW_END) {
                strBmpPath = strPathPair.first;
            } else if (p.m_iType == 2) {
                strBmpPath = strPathPair.second;
            }
        }
        if (strBmpPath != null) {
            BitmapMonitorResult bmr = new BitmapMonitorResult();
            if (FileUtility.IsFromSDCard(this.m_Context, strBmpPath)) {
                bmr = BitmapMonitor.CreateBitmap(strBmpPath, false);
            } else {
                try {
                    bmr = BitmapMonitor.CreateBitmap(this.m_Context.getAssets().open(strBmpPath), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bmr.IsSuccess()) {
                Bitmap bmp = bmr.GetBitmap();
                if (bmp != null) {
                    canvas.save();
                    canvas.scale(tempScale, tempScale);
                    canvas.drawBitmap(bmp, tempX, tempY, paint);
                    canvas.restore();
                    bmp.recycle();
                }
            }
        }
    }

    public boolean HaveGSParticle() {
        return this.m_ParticlePhoto.HaveGSParticle();
    }

    public boolean HaveParticle() {
        if (this.m_ParticleGroup == null) {
            return false;
        }
        return this.m_ParticleGroup.HaveParticle();
    }
}
