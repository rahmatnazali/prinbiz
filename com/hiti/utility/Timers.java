package com.hiti.utility;

import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

public class Timers {
    LogManager LOG;
    String TAG;
    int TIME_OUT;
    private boolean bStop;
    Handler mHandler;
    ITimeListener mTimerListener;
    TimeOutTask m_TimeOutTask;
    Timer m_TimeOutTimer;

    public interface ITimeListener {
        void StopThread();

        void TimeOut();
    }

    class TimeOutTask extends TimerTask {

        /* renamed from: com.hiti.utility.Timers.TimeOutTask.1 */
        class C04571 implements Runnable {
            C04571() {
            }

            public void run() {
                if (Timers.this.mTimerListener != null) {
                    Timers.this.mTimerListener.TimeOut();
                }
            }
        }

        TimeOutTask() {
        }

        public void run() {
            Timers.this.LOG.m385i(Timers.this.TAG, "TimeOutTask " + Timers.this.TIME_OUT);
            if (!Timers.this.IsStop()) {
                if (Timers.this.mTimerListener != null) {
                    Timers.this.mTimerListener.StopThread();
                }
                if (Timers.this.mHandler != null) {
                    Timers.this.mHandler.post(new C04571());
                }
            }
        }
    }

    public Timers(Handler handler, ITimeListener iTimeListener) {
        this.m_TimeOutTimer = null;
        this.m_TimeOutTask = null;
        this.mHandler = null;
        this.mTimerListener = null;
        this.bStop = false;
        this.TIME_OUT = 3000;
        this.LOG = null;
        this.TAG = "Timers";
        this.LOG = new LogManager(0);
        this.mHandler = handler;
        this.mTimerListener = iTimeListener;
    }

    public void Start(int iTimeout) {
        this.LOG.m385i(this.TAG, "Start: " + iTimeout);
        this.TIME_OUT = iTimeout;
        this.m_TimeOutTimer = new Timer();
        this.m_TimeOutTask = new TimeOutTask();
        this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) this.TIME_OUT);
    }

    public void Stop(boolean bStop) {
        this.LOG.m385i(this.TAG, "Stop");
        this.bStop = bStop;
        if (bStop) {
            this.m_TimeOutTask.cancel();
            this.m_TimeOutTimer.cancel();
        }
    }

    boolean IsStop() {
        return this.bStop;
    }
}
