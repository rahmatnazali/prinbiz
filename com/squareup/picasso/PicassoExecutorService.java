package com.squareup.picasso;

import android.net.NetworkInfo;
import com.google.android.gms.common.ConnectionResult;
import com.squareup.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    private static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter hunter;

        public PicassoFutureTask(BitmapHunter hunter) {
            super(hunter, null);
            this.hunter = hunter;
        }

        public int compareTo(PicassoFutureTask other) {
            Priority p1 = this.hunter.getPriority();
            Priority p2 = other.hunter.getPriority();
            return p1 == p2 ? this.hunter.sequence - other.hunter.sequence : p2.ordinal() - p1.ordinal();
        }
    }

    PicassoExecutorService() {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(DEFAULT_THREAD_COUNT);
            return;
        }
        switch (info.getType()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                switch (info.getSubtype()) {
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        setThreadCount(1);
                    case DEFAULT_THREAD_COUNT /*3*/:
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                        setThreadCount(2);
                    case ConnectionResult.CANCELED /*13*/:
                    case ConnectionResult.TIMEOUT /*14*/:
                    case ConnectionResult.INTERRUPTED /*15*/:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                    default:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                }
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
            case ConnectionResult.SERVICE_INVALID /*9*/:
                setThreadCount(4);
            default:
                setThreadCount(DEFAULT_THREAD_COUNT);
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }

    public Future<?> submit(Runnable task) {
        PicassoFutureTask ftask = new PicassoFutureTask((BitmapHunter) task);
        execute(ftask);
        return ftask;
    }
}
