package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return true;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
            case EchoUDPClient.DEFAULT_PORT /*7*/:
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return false;
            default:
                return true;
        }
    }
}
