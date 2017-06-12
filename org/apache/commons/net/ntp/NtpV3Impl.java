package org.apache.commons.net.ntp;

import android.support.v4.media.session.PlaybackStateCompat;
import java.net.DatagramPacket;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;

public class NtpV3Impl implements NtpV3Packet {
    private static final int LI_INDEX = 0;
    private static final int LI_SHIFT = 6;
    private static final int MODE_INDEX = 0;
    private static final int MODE_SHIFT = 0;
    private static final int ORIGINATE_TIMESTAMP_INDEX = 24;
    private static final int POLL_INDEX = 2;
    private static final int PRECISION_INDEX = 3;
    private static final int RECEIVE_TIMESTAMP_INDEX = 32;
    private static final int REFERENCE_ID_INDEX = 12;
    private static final int REFERENCE_TIMESTAMP_INDEX = 16;
    private static final int ROOT_DELAY_INDEX = 4;
    private static final int ROOT_DISPERSION_INDEX = 8;
    private static final int STRATUM_INDEX = 1;
    private static final int TRANSMIT_TIMESTAMP_INDEX = 40;
    private static final int VERSION_INDEX = 0;
    private static final int VERSION_SHIFT = 3;
    private final byte[] buf;
    private volatile DatagramPacket dp;

    public NtpV3Impl() {
        this.buf = new byte[48];
    }

    public int getMode() {
        return (ui(this.buf[VERSION_INDEX]) >> VERSION_INDEX) & 7;
    }

    public String getModeName() {
        return NtpUtils.getModeName(getMode());
    }

    public void setMode(int mode) {
        this.buf[VERSION_INDEX] = (byte) ((this.buf[VERSION_INDEX] & TelnetCommand.EL) | (mode & 7));
    }

    public int getLeapIndicator() {
        return (ui(this.buf[VERSION_INDEX]) >> LI_SHIFT) & VERSION_SHIFT;
    }

    public void setLeapIndicator(int li) {
        this.buf[VERSION_INDEX] = (byte) ((this.buf[VERSION_INDEX] & 63) | ((li & VERSION_SHIFT) << LI_SHIFT));
    }

    public int getPoll() {
        return this.buf[POLL_INDEX];
    }

    public void setPoll(int poll) {
        this.buf[POLL_INDEX] = (byte) (poll & TelnetOption.MAX_OPTION_VALUE);
    }

    public int getPrecision() {
        return this.buf[VERSION_SHIFT];
    }

    public void setPrecision(int precision) {
        this.buf[VERSION_SHIFT] = (byte) (precision & TelnetOption.MAX_OPTION_VALUE);
    }

    public int getVersion() {
        return (ui(this.buf[VERSION_INDEX]) >> VERSION_SHIFT) & 7;
    }

    public void setVersion(int version) {
        this.buf[VERSION_INDEX] = (byte) ((this.buf[VERSION_INDEX] & NNTPReply.DEBUG_OUTPUT) | ((version & 7) << VERSION_SHIFT));
    }

    public int getStratum() {
        return ui(this.buf[STRATUM_INDEX]);
    }

    public void setStratum(int stratum) {
        this.buf[STRATUM_INDEX] = (byte) (stratum & TelnetOption.MAX_OPTION_VALUE);
    }

    public int getRootDelay() {
        return getInt(ROOT_DELAY_INDEX);
    }

    public double getRootDelayInMillisDouble() {
        return ((double) getRootDelay()) / 65.536d;
    }

    public int getRootDispersion() {
        return getInt(ROOT_DISPERSION_INDEX);
    }

    public long getRootDispersionInMillis() {
        return (1000 * ((long) getRootDispersion())) / PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
    }

    public double getRootDispersionInMillisDouble() {
        return ((double) getRootDispersion()) / 65.536d;
    }

    public void setReferenceId(int refId) {
        for (int i = VERSION_SHIFT; i >= 0; i--) {
            this.buf[i + REFERENCE_ID_INDEX] = (byte) (refId & TelnetOption.MAX_OPTION_VALUE);
            refId >>>= ROOT_DISPERSION_INDEX;
        }
    }

    public int getReferenceId() {
        return getInt(REFERENCE_ID_INDEX);
    }

    public String getReferenceIdString() {
        int version = getVersion();
        int stratum = getStratum();
        if (version == VERSION_SHIFT || version == ROOT_DELAY_INDEX) {
            if (stratum == 0 || stratum == STRATUM_INDEX) {
                return idAsString();
            }
            if (version == ROOT_DELAY_INDEX) {
                return idAsHex();
            }
        }
        if (stratum >= POLL_INDEX) {
            return idAsIPAddress();
        }
        return idAsHex();
    }

    private String idAsIPAddress() {
        return ui(this.buf[REFERENCE_ID_INDEX]) + "." + ui(this.buf[13]) + "." + ui(this.buf[14]) + "." + ui(this.buf[15]);
    }

    private String idAsString() {
        StringBuilder id = new StringBuilder();
        for (int i = VERSION_INDEX; i <= VERSION_SHIFT; i += STRATUM_INDEX) {
            char c = (char) this.buf[i + REFERENCE_ID_INDEX];
            if (c == '\u0000') {
                break;
            }
            id.append(c);
        }
        return id.toString();
    }

    private String idAsHex() {
        return Integer.toHexString(getReferenceId());
    }

    public TimeStamp getTransmitTimeStamp() {
        return getTimestamp(TRANSMIT_TIMESTAMP_INDEX);
    }

    public void setTransmitTime(TimeStamp ts) {
        setTimestamp(TRANSMIT_TIMESTAMP_INDEX, ts);
    }

    public void setOriginateTimeStamp(TimeStamp ts) {
        setTimestamp(ORIGINATE_TIMESTAMP_INDEX, ts);
    }

    public TimeStamp getOriginateTimeStamp() {
        return getTimestamp(ORIGINATE_TIMESTAMP_INDEX);
    }

    public TimeStamp getReferenceTimeStamp() {
        return getTimestamp(REFERENCE_TIMESTAMP_INDEX);
    }

    public void setReferenceTime(TimeStamp ts) {
        setTimestamp(REFERENCE_TIMESTAMP_INDEX, ts);
    }

    public TimeStamp getReceiveTimeStamp() {
        return getTimestamp(RECEIVE_TIMESTAMP_INDEX);
    }

    public void setReceiveTimeStamp(TimeStamp ts) {
        setTimestamp(RECEIVE_TIMESTAMP_INDEX, ts);
    }

    public String getType() {
        return NtpV3Packet.TYPE_NTP;
    }

    private int getInt(int index) {
        return (((ui(this.buf[index]) << ORIGINATE_TIMESTAMP_INDEX) | (ui(this.buf[index + STRATUM_INDEX]) << REFERENCE_TIMESTAMP_INDEX)) | (ui(this.buf[index + POLL_INDEX]) << ROOT_DISPERSION_INDEX)) | ui(this.buf[index + VERSION_SHIFT]);
    }

    private TimeStamp getTimestamp(int index) {
        return new TimeStamp(getLong(index));
    }

    private long getLong(int index) {
        return (((((((ul(this.buf[index]) << 56) | (ul(this.buf[index + STRATUM_INDEX]) << 48)) | (ul(this.buf[index + POLL_INDEX]) << TRANSMIT_TIMESTAMP_INDEX)) | (ul(this.buf[index + VERSION_SHIFT]) << RECEIVE_TIMESTAMP_INDEX)) | (ul(this.buf[index + ROOT_DELAY_INDEX]) << ORIGINATE_TIMESTAMP_INDEX)) | (ul(this.buf[index + 5]) << REFERENCE_TIMESTAMP_INDEX)) | (ul(this.buf[index + LI_SHIFT]) << ROOT_DISPERSION_INDEX)) | ul(this.buf[index + 7]);
    }

    private void setTimestamp(int index, TimeStamp t) {
        long ntpTime = t == null ? 0 : t.ntpValue();
        for (int i = 7; i >= 0; i--) {
            this.buf[index + i] = (byte) ((int) (255 & ntpTime));
            ntpTime >>>= ROOT_DISPERSION_INDEX;
        }
    }

    public synchronized DatagramPacket getDatagramPacket() {
        if (this.dp == null) {
            this.dp = new DatagramPacket(this.buf, this.buf.length);
            this.dp.setPort(NTPUDPClient.DEFAULT_PORT);
        }
        return this.dp;
    }

    public void setDatagramPacket(DatagramPacket srcDp) {
        byte[] incomingBuf = srcDp.getData();
        int len = srcDp.getLength();
        if (len > this.buf.length) {
            len = this.buf.length;
        }
        System.arraycopy(incomingBuf, VERSION_INDEX, this.buf, VERSION_INDEX, len);
    }

    protected static final int ui(byte b) {
        return b & TelnetOption.MAX_OPTION_VALUE;
    }

    protected static final long ul(byte b) {
        return (long) (b & TelnetOption.MAX_OPTION_VALUE);
    }

    public String toString() {
        return "[version:" + getVersion() + ", mode:" + getMode() + ", poll:" + getPoll() + ", precision:" + getPrecision() + ", delay:" + getRootDelay() + ", dispersion(ms):" + getRootDispersionInMillisDouble() + ", id:" + getReferenceIdString() + ", xmitTime:" + getTransmitTimeStamp().toDateString() + " ]";
    }
}
