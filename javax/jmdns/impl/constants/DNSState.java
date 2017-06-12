package javax.jmdns.impl.constants;

import com.google.android.gms.common.ConnectionResult;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public enum DNSState {
    PROBING_1("probing 1", StateClass.probing),
    PROBING_2("probing 2", StateClass.probing),
    PROBING_3("probing 3", StateClass.probing),
    ANNOUNCING_1("announcing 1", StateClass.announcing),
    ANNOUNCING_2("announcing 2", StateClass.announcing),
    ANNOUNCED("announced", StateClass.announced),
    CANCELING_1("canceling 1", StateClass.canceling),
    CANCELING_2("canceling 2", StateClass.canceling),
    CANCELING_3("canceling 3", StateClass.canceling),
    CANCELED("canceled", StateClass.canceled),
    CLOSING("closing", StateClass.closing),
    CLOSED("closed", StateClass.closed);
    
    private final String _name;
    private final StateClass _state;

    /* renamed from: javax.jmdns.impl.constants.DNSState.1 */
    static /* synthetic */ class C05271 {
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$constants$DNSState;

        static {
            $SwitchMap$javax$jmdns$impl$constants$DNSState = new int[DNSState.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.PROBING_1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.PROBING_2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.PROBING_3.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.ANNOUNCING_1.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.ANNOUNCING_2.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.ANNOUNCED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CANCELING_1.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CANCELING_2.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CANCELING_3.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CANCELED.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CLOSING.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSState[DNSState.CLOSED.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    private enum StateClass {
        private static final /* synthetic */ StateClass[] $VALUES;
        public static final StateClass announced;
        public static final StateClass announcing;
        public static final StateClass canceled;
        public static final StateClass canceling;
        public static final StateClass closed;
        public static final StateClass closing;
        public static final StateClass probing;

        private StateClass(String str, int i) {
        }

        public static StateClass valueOf(String name) {
            return (StateClass) Enum.valueOf(StateClass.class, name);
        }

        public static StateClass[] values() {
            return (StateClass[]) $VALUES.clone();
        }

        static {
            probing = new StateClass("probing", 0);
            announcing = new StateClass("announcing", 1);
            announced = new StateClass("announced", 2);
            canceling = new StateClass("canceling", 3);
            canceled = new StateClass("canceled", 4);
            closing = new StateClass("closing", 5);
            closed = new StateClass("closed", 6);
            $VALUES = new StateClass[]{probing, announcing, announced, canceling, canceled, closing, closed};
        }
    }

    private DNSState(String name, StateClass state) {
        this._name = name;
        this._state = state;
    }

    public final String toString() {
        return this._name;
    }

    public final DNSState advance() {
        switch (C05271.$SwitchMap$javax$jmdns$impl$constants$DNSState[ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return PROBING_2;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return PROBING_3;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return ANNOUNCING_1;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return ANNOUNCING_2;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return ANNOUNCED;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return ANNOUNCED;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return CANCELING_2;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return CANCELING_3;
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return CANCELED;
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return CANCELED;
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return CLOSED;
            case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                return CLOSED;
            default:
                return this;
        }
    }

    public final DNSState revert() {
        switch (C05271.$SwitchMap$javax$jmdns$impl$constants$DNSState[ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return PROBING_1;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
            case ConnectionResult.INTERNAL_ERROR /*8*/:
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return CANCELING_1;
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return CANCELED;
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return CLOSING;
            case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                return CLOSED;
            default:
                return this;
        }
    }

    public final boolean isProbing() {
        return this._state == StateClass.probing;
    }

    public final boolean isAnnouncing() {
        return this._state == StateClass.announcing;
    }

    public final boolean isAnnounced() {
        return this._state == StateClass.announced;
    }

    public final boolean isCanceling() {
        return this._state == StateClass.canceling;
    }

    public final boolean isCanceled() {
        return this._state == StateClass.canceled;
    }

    public final boolean isClosing() {
        return this._state == StateClass.closing;
    }

    public final boolean isClosed() {
        return this._state == StateClass.closed;
    }
}
