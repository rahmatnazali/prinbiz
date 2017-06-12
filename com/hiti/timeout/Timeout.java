package com.hiti.timeout;

import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.net.tftp.TFTP;

public class Timeout {
    int TIME_OUT;
    ITimeOut mListener;
    TimeOutTask m_TimeOutTask;
    Timer m_TimeOutTimer;

    public interface ITimeOut {
        void RunTimeOut();
    }

    public class TimeOutTask extends TimerTask {
        public void run() {
            Log.i("run", String.valueOf("TimeOutTask"));
            Timeout.this.StopTimerOut();
            if (Timeout.this.mListener != null) {
                Timeout.this.mListener.RunTimeOut();
            }
        }
    }

    public Timeout() {
        this.m_TimeOutTask = null;
        this.m_TimeOutTimer = null;
        this.TIME_OUT = TFTP.DEFAULT_TIMEOUT;
        this.mListener = null;
    }

    public void SetListener(ITimeOut litener) {
        this.mListener = litener;
    }

    public void StartTimerOut(int iTimeOut) {
        Log.i("start-" + iTimeOut, String.valueOf("StartTimerOut"));
        if (this.m_TimeOutTimer == null) {
            if (iTimeOut == 0) {
                iTimeOut = this.TIME_OUT;
            }
            this.m_TimeOutTimer = new Timer(true);
            this.m_TimeOutTask = new TimeOutTask();
            this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) iTimeOut);
        }
    }

    public void StopTimerOut() {
        Log.i("StopTimerOut", String.valueOf("StopTimerOut"));
        if (this.m_TimeOutTimer != null) {
            this.m_TimeOutTimer.cancel();
            this.m_TimeOutTimer = null;
        }
        if (this.m_TimeOutTask != null) {
            this.m_TimeOutTask.cancel();
            this.m_TimeOutTask = null;
        }
    }
}
