package com.hiti.utility.time;

import com.hiti.utility.LogManager;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.net.smtp.SMTPReply;

public class Timeout {
    LogManager LOG;
    String TAG;
    final int TIME_OUT;
    ITimeListener mListener;
    TimeOutTask mTimeOutTask;
    Timer mTimeOutTimer;

    public interface ITimeListener {
        void TimeOut();
    }

    class TimeOutTask extends TimerTask {
        TimeOutTask() {
        }

        public void run() {
            Timeout.this.LOG.m386v(Timeout.this.TAG, "RUN TIMEOUT");
            Timeout.this.mListener.TimeOut();
        }
    }

    public Timeout() {
        this.mTimeOutTimer = null;
        this.mTimeOutTask = null;
        this.mListener = null;
        this.TIME_OUT = SMTPReply.UNRECOGNIZED_COMMAND;
        this.LOG = null;
        this.TAG = "TimeOut";
        this.LOG = new LogManager(0);
    }

    public Timeout(ITimeListener Ilistener) {
        this.mTimeOutTimer = null;
        this.mTimeOutTask = null;
        this.mListener = null;
        this.TIME_OUT = SMTPReply.UNRECOGNIZED_COMMAND;
        this.LOG = null;
        this.TAG = "TimeOut";
        this.mListener = Ilistener;
        this.LOG = new LogManager(0);
    }

    public void Start(int iTimeOut, ITimeListener listener) {
        if (iTimeOut == 0) {
            iTimeOut = SMTPReply.UNRECOGNIZED_COMMAND;
        }
        this.mListener = listener;
        this.mTimeOutTimer = new Timer(true);
        this.mTimeOutTask = new TimeOutTask();
        this.mTimeOutTimer.schedule(this.mTimeOutTask, (long) iTimeOut);
        this.LOG.m386v(this.TAG, "SET TIMEOUT");
    }

    public void Start(int iTimeOut) {
        Start(iTimeOut, this.mListener);
    }

    public void Stop() {
        this.LOG.m386v(this.TAG, "Stop TIMEOUT");
        if (this.mTimeOutTimer != null) {
            this.mTimeOutTimer.cancel();
            this.mTimeOutTimer = null;
        }
        if (this.mTimeOutTask != null) {
            this.mTimeOutTask.cancel();
            this.mTimeOutTask = null;
        }
    }
}
