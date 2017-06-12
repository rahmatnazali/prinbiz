package javax.jmdns.impl;

import com.google.android.gms.common.ConnectionResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSRecord.HostInformation;
import javax.jmdns.impl.DNSRecord.IPv4Address;
import javax.jmdns.impl.DNSRecord.IPv6Address;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.DNSRecord.Text;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSLabel;
import javax.jmdns.impl.constants.DNSOptionCode;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSResultCode;
import org.apache.commons.net.bsd.RCommandClient;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;
import org.kxml2.wap.Wbxml;
import org.xmlpull.v1.XmlPullParser;

public final class DNSIncoming extends DNSMessage {
    public static boolean USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET;
    private static final char[] _nibbleToHex;
    private static Logger logger;
    private final MessageInputStream _messageInputStream;
    private final DatagramPacket _packet;
    private final long _receivedTime;
    private int _senderUDPPayload;

    /* renamed from: javax.jmdns.impl.DNSIncoming.1 */
    static /* synthetic */ class C05101 {
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$constants$DNSLabel;
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode;
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$constants$DNSRecordType;

        static {
            $SwitchMap$javax$jmdns$impl$constants$DNSRecordType = new int[DNSRecordType.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_A.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_AAAA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_CNAME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_PTR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_TXT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_SRV.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_HINFO.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_OPT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode = new int[DNSOptionCode.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[DNSOptionCode.Owner.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[DNSOptionCode.LLQ.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[DNSOptionCode.NSID.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[DNSOptionCode.UL.ordinal()] = 4;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[DNSOptionCode.Unknown.ordinal()] = 5;
            } catch (NoSuchFieldError e13) {
            }
            $SwitchMap$javax$jmdns$impl$constants$DNSLabel = new int[DNSLabel.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSLabel[DNSLabel.Standard.ordinal()] = 1;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSLabel[DNSLabel.Compressed.ordinal()] = 2;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSLabel[DNSLabel.Extended.ordinal()] = 3;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSLabel[DNSLabel.Unknown.ordinal()] = 4;
            } catch (NoSuchFieldError e17) {
            }
        }
    }

    public static class MessageInputStream extends ByteArrayInputStream {
        private static Logger logger1;
        final Map<Integer, String> _names;

        static {
            logger1 = Logger.getLogger(MessageInputStream.class.getName());
        }

        public MessageInputStream(byte[] buffer, int length) {
            this(buffer, 0, length);
        }

        public MessageInputStream(byte[] buffer, int offset, int length) {
            super(buffer, offset, length);
            this._names = new HashMap();
        }

        public int readByte() {
            return read();
        }

        public int readUnsignedShort() {
            return (read() << 8) | read();
        }

        public int readInt() {
            return (readUnsignedShort() << 16) | readUnsignedShort();
        }

        public byte[] readBytes(int len) {
            byte[] bytes = new byte[len];
            read(bytes, 0, len);
            return bytes;
        }

        public String readUTF(int len) {
            StringBuilder buffer = new StringBuilder(len);
            int index = 0;
            while (index < len) {
                int ch = read();
                switch (ch >> 4) {
                    case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                    case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    case EchoUDPClient.DEFAULT_PORT /*7*/:
                        break;
                    case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    case ConnectionResult.CANCELED /*13*/:
                        ch = ((ch & 31) << 6) | (read() & 63);
                        index++;
                        break;
                    case ConnectionResult.TIMEOUT /*14*/:
                        ch = (((ch & 15) << 12) | ((read() & 63) << 6)) | (read() & 63);
                        index = (index + 1) + 1;
                        break;
                    default:
                        ch = ((ch & 63) << 4) | (read() & 15);
                        index++;
                        break;
                }
                buffer.append((char) ch);
                index++;
            }
            return buffer.toString();
        }

        protected synchronized int peek() {
            return this.pos < this.count ? this.buf[this.pos] & TelnetOption.MAX_OPTION_VALUE : -1;
        }

        public String readName() {
            Map<Integer, StringBuilder> names = new HashMap();
            StringBuilder buffer = new StringBuilder();
            boolean finished = false;
            while (!finished) {
                int len = read();
                if (len != 0) {
                    switch (C05101.$SwitchMap$javax$jmdns$impl$constants$DNSLabel[DNSLabel.labelForByte(len).ordinal()]) {
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            int offset = this.pos - 1;
                            String label = readUTF(len) + ".";
                            buffer.append(label);
                            for (StringBuilder previousLabel : names.values()) {
                                previousLabel.append(label);
                            }
                            names.put(Integer.valueOf(offset), new StringBuilder(label));
                            break;
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                            int index = (DNSLabel.labelValue(len) << 8) | read();
                            String compressedLabel = (String) this._names.get(Integer.valueOf(index));
                            if (compressedLabel == null) {
                                logger1.severe("bad domain name: possible circular name detected. Bad offset: 0x" + Integer.toHexString(index) + " at 0x" + Integer.toHexString(this.pos - 2));
                                compressedLabel = XmlPullParser.NO_NAMESPACE;
                            }
                            buffer.append(compressedLabel);
                            for (StringBuilder previousLabel2 : names.values()) {
                                previousLabel2.append(compressedLabel);
                            }
                            finished = true;
                            break;
                        case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                            logger1.severe("Extended label are not currently supported.");
                            break;
                        default:
                            logger1.severe("unsupported dns label type: '" + Integer.toHexString(len & Wbxml.EXT_0) + "'");
                            break;
                    }
                }
                for (Integer index2 : names.keySet()) {
                    this._names.put(index2, ((StringBuilder) names.get(index2)).toString());
                }
                return buffer.toString();
            }
            for (Integer index22 : names.keySet()) {
                this._names.put(index22, ((StringBuilder) names.get(index22)).toString());
            }
            return buffer.toString();
        }

        public String readNonNameString() {
            return readUTF(read());
        }
    }

    static {
        logger = Logger.getLogger(DNSIncoming.class.getName());
        USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET = true;
        _nibbleToHex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    public DNSIncoming(DatagramPacket packet) throws IOException {
        boolean z;
        if (packet.getPort() == DNSConstants.MDNS_PORT) {
            z = true;
        } else {
            z = false;
        }
        super(0, 0, z);
        this._packet = packet;
        InetAddress source = packet.getAddress();
        this._messageInputStream = new MessageInputStream(packet.getData(), packet.getLength());
        this._receivedTime = System.currentTimeMillis();
        this._senderUDPPayload = DNSConstants.MAX_MSG_TYPICAL;
        try {
            int i;
            DNSRecord rec;
            setId(this._messageInputStream.readUnsignedShort());
            setFlags(this._messageInputStream.readUnsignedShort());
            int numQuestions = this._messageInputStream.readUnsignedShort();
            int numAnswers = this._messageInputStream.readUnsignedShort();
            int numAuthorities = this._messageInputStream.readUnsignedShort();
            int numAdditionals = this._messageInputStream.readUnsignedShort();
            if (numQuestions > 0) {
                for (i = 0; i < numQuestions; i++) {
                    this._questions.add(readQuestion());
                }
            }
            if (numAnswers > 0) {
                for (i = 0; i < numAnswers; i++) {
                    rec = readAnswer(source);
                    if (rec != null) {
                        this._answers.add(rec);
                    }
                }
            }
            if (numAuthorities > 0) {
                for (i = 0; i < numAuthorities; i++) {
                    rec = readAnswer(source);
                    if (rec != null) {
                        this._authoritativeAnswers.add(rec);
                    }
                }
            }
            if (numAdditionals > 0) {
                for (i = 0; i < numAdditionals; i++) {
                    rec = readAnswer(source);
                    if (rec != null) {
                        this._additionals.add(rec);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "DNSIncoming() dump " + print(true) + "\n exception ", e);
            IOException ioe = new IOException("DNSIncoming corrupted message");
            ioe.initCause(e);
            throw ioe;
        }
    }

    private DNSIncoming(int flags, int id, boolean multicast, DatagramPacket packet, long receivedTime) {
        super(flags, id, multicast);
        this._packet = packet;
        this._messageInputStream = new MessageInputStream(packet.getData(), packet.getLength());
        this._receivedTime = receivedTime;
    }

    public DNSIncoming clone() {
        DNSIncoming in = new DNSIncoming(getFlags(), getId(), isMulticast(), this._packet, this._receivedTime);
        in._senderUDPPayload = this._senderUDPPayload;
        in._questions.addAll(this._questions);
        in._answers.addAll(this._answers);
        in._authoritativeAnswers.addAll(this._authoritativeAnswers);
        in._additionals.addAll(this._additionals);
        return in;
    }

    private DNSQuestion readQuestion() {
        String domain = this._messageInputStream.readName();
        DNSRecordType type = DNSRecordType.typeForIndex(this._messageInputStream.readUnsignedShort());
        if (type == DNSRecordType.TYPE_IGNORE) {
            logger.log(Level.SEVERE, "Could not find record type: " + print(true));
        }
        int recordClassIndex = this._messageInputStream.readUnsignedShort();
        DNSRecordClass recordClass = DNSRecordClass.classForIndex(recordClassIndex);
        return DNSQuestion.newQuestion(domain, type, recordClass, recordClass.isUnique(recordClassIndex));
    }

    private DNSRecord readAnswer(InetAddress source) {
        byte[] ownerPassword;
        String domain = this._messageInputStream.readName();
        DNSRecordType type = DNSRecordType.typeForIndex(this._messageInputStream.readUnsignedShort());
        if (type == DNSRecordType.TYPE_IGNORE) {
            logger.log(Level.SEVERE, "Could not find record type. domain: " + domain + "\n" + print(true));
        }
        int recordClassIndex = this._messageInputStream.readUnsignedShort();
        DNSRecordClass recordClass = type == DNSRecordType.TYPE_OPT ? DNSRecordClass.CLASS_UNKNOWN : DNSRecordClass.classForIndex(recordClassIndex);
        if (recordClass == DNSRecordClass.CLASS_UNKNOWN && type != DNSRecordType.TYPE_OPT) {
            logger.log(Level.SEVERE, "Could not find record class. domain: " + domain + " type: " + type + "\n" + print(true));
        }
        boolean unique = recordClass.isUnique(recordClassIndex);
        int ttl = this._messageInputStream.readInt();
        int len = this._messageInputStream.readUnsignedShort();
        DNSRecord rec = null;
        DNSRecord text;
        switch (C05101.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                rec = new IPv4Address(domain, recordClass, unique, ttl, this._messageInputStream.readBytes(len));
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                rec = new IPv6Address(domain, recordClass, unique, ttl, this._messageInputStream.readBytes(len));
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                String service = XmlPullParser.NO_NAMESPACE;
                service = this._messageInputStream.readName();
                if (service.length() <= 0) {
                    logger.log(Level.WARNING, "PTR record of class: " + recordClass + ", there was a problem reading the service name of the answer for domain:" + domain);
                    break;
                }
                rec = new Pointer(domain, recordClass, unique, ttl, service);
                break;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                text = new Text(domain, recordClass, unique, ttl, this._messageInputStream.readBytes(len));
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                int priority = this._messageInputStream.readUnsignedShort();
                int weight = this._messageInputStream.readUnsignedShort();
                int port = this._messageInputStream.readUnsignedShort();
                String target = XmlPullParser.NO_NAMESPACE;
                if (USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET) {
                    target = this._messageInputStream.readName();
                } else {
                    target = this._messageInputStream.readNonNameString();
                }
                text = new Service(domain, recordClass, unique, ttl, priority, weight, port, target);
                break;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                StringBuilder buf = new StringBuilder();
                buf.append(this._messageInputStream.readUTF(len));
                int index = buf.indexOf(" ");
                DNSRecord hostInformation = new HostInformation(domain, recordClass, unique, ttl, (index > 0 ? buf.substring(0, index) : buf.toString()).trim(), (index > 0 ? buf.substring(index + 1) : XmlPullParser.NO_NAMESPACE).trim());
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                DNSResultCode extendedResultCode = DNSResultCode.resultCodeForFlags(getFlags(), ttl);
                int version = (16711680 & ttl) >> 16;
                if (version != 0) {
                    logger.log(Level.WARNING, "There was an OPT answer. Wrong version number: " + version + " result code: " + extendedResultCode);
                    break;
                }
                this._senderUDPPayload = recordClassIndex;
                while (this._messageInputStream.available() > 0) {
                    if (this._messageInputStream.available() < 2) {
                        logger.log(Level.WARNING, "There was a problem reading the OPT record. Ignoring.");
                        break;
                    }
                    int optionCodeInt = this._messageInputStream.readUnsignedShort();
                    DNSOptionCode optionCode = DNSOptionCode.resultCodeForFlags(optionCodeInt);
                    if (this._messageInputStream.available() < 2) {
                        logger.log(Level.WARNING, "There was a problem reading the OPT record. Ignoring.");
                        break;
                    }
                    int optionLength = this._messageInputStream.readUnsignedShort();
                    byte[] optiondata = new byte[0];
                    if (this._messageInputStream.available() >= optionLength) {
                        optiondata = this._messageInputStream.readBytes(optionLength);
                    }
                    switch (C05101.$SwitchMap$javax$jmdns$impl$constants$DNSOptionCode[optionCode.ordinal()]) {
                        case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                            Logger logger;
                            StringBuilder append;
                            String str;
                            int i = (byte) 0;
                            int i2 = (byte) 0;
                            byte[] ownerPrimaryMacAddress = null;
                            byte[] bArr = null;
                            byte[] ownerPassword2 = null;
                            try {
                                i = optiondata[0];
                                i2 = optiondata[1];
                                byte[] ownerPrimaryMacAddress2 = new byte[]{optiondata[2], optiondata[3], optiondata[4], optiondata[5], optiondata[6], optiondata[7]};
                                bArr = ownerPrimaryMacAddress2;
                                try {
                                    if (optiondata.length > 8) {
                                        bArr = new byte[]{optiondata[8], optiondata[9], optiondata[10], optiondata[11], optiondata[12], optiondata[13]};
                                    }
                                    ownerPassword = optiondata.length == 18 ? new byte[]{optiondata[14], optiondata[15], optiondata[16], optiondata[17]} : null;
                                } catch (Exception e) {
                                    ownerPrimaryMacAddress = ownerPrimaryMacAddress2;
                                    logger.warning("Malformed OPT answer. Option code: Owner data: " + _hexString(optiondata));
                                    if (!logger.isLoggable(Level.FINE)) {
                                        logger = logger;
                                        append = new StringBuilder().append("Unhandled Owner OPT version: ").append(i).append(" sequence: ").append(i2).append(" MAC address: ").append(_hexString(ownerPrimaryMacAddress));
                                        if (bArr == ownerPrimaryMacAddress) {
                                            str = XmlPullParser.NO_NAMESPACE;
                                        } else {
                                            str = " wakeup MAC address: " + _hexString(bArr);
                                        }
                                        append = append.append(str);
                                        if (ownerPassword2 == null) {
                                            str = XmlPullParser.NO_NAMESPACE;
                                        } else {
                                            str = " password: " + _hexString(ownerPassword2);
                                        }
                                        logger.fine(append.append(str).toString());
                                    }
                                }
                                try {
                                    ownerPassword2 = optiondata.length == 22 ? new byte[]{optiondata[14], optiondata[15], optiondata[16], optiondata[17], optiondata[18], optiondata[19], optiondata[20], optiondata[21]} : ownerPassword;
                                    ownerPrimaryMacAddress = ownerPrimaryMacAddress2;
                                } catch (Exception e2) {
                                    ownerPassword2 = ownerPassword;
                                    ownerPrimaryMacAddress = ownerPrimaryMacAddress2;
                                    logger.warning("Malformed OPT answer. Option code: Owner data: " + _hexString(optiondata));
                                    if (!logger.isLoggable(Level.FINE)) {
                                        logger = logger;
                                        append = new StringBuilder().append("Unhandled Owner OPT version: ").append(i).append(" sequence: ").append(i2).append(" MAC address: ").append(_hexString(ownerPrimaryMacAddress));
                                        if (bArr == ownerPrimaryMacAddress) {
                                            str = XmlPullParser.NO_NAMESPACE;
                                        } else {
                                            str = " wakeup MAC address: " + _hexString(bArr);
                                        }
                                        append = append.append(str);
                                        if (ownerPassword2 == null) {
                                            str = XmlPullParser.NO_NAMESPACE;
                                        } else {
                                            str = " password: " + _hexString(ownerPassword2);
                                        }
                                        logger.fine(append.append(str).toString());
                                    }
                                }
                            } catch (Exception e3) {
                                logger.warning("Malformed OPT answer. Option code: Owner data: " + _hexString(optiondata));
                                if (!logger.isLoggable(Level.FINE)) {
                                    logger = logger;
                                    append = new StringBuilder().append("Unhandled Owner OPT version: ").append(i).append(" sequence: ").append(i2).append(" MAC address: ").append(_hexString(ownerPrimaryMacAddress));
                                    if (bArr == ownerPrimaryMacAddress) {
                                        str = " wakeup MAC address: " + _hexString(bArr);
                                    } else {
                                        str = XmlPullParser.NO_NAMESPACE;
                                    }
                                    append = append.append(str);
                                    if (ownerPassword2 == null) {
                                        str = " password: " + _hexString(ownerPassword2);
                                    } else {
                                        str = XmlPullParser.NO_NAMESPACE;
                                    }
                                    logger.fine(append.append(str).toString());
                                }
                            }
                            if (!logger.isLoggable(Level.FINE)) {
                                logger = logger;
                                append = new StringBuilder().append("Unhandled Owner OPT version: ").append(i).append(" sequence: ").append(i2).append(" MAC address: ").append(_hexString(ownerPrimaryMacAddress));
                                if (bArr == ownerPrimaryMacAddress) {
                                    str = " wakeup MAC address: " + _hexString(bArr);
                                } else {
                                    str = XmlPullParser.NO_NAMESPACE;
                                }
                                append = append.append(str);
                                if (ownerPassword2 == null) {
                                    str = " password: " + _hexString(ownerPassword2);
                                } else {
                                    str = XmlPullParser.NO_NAMESPACE;
                                }
                                logger.fine(append.append(str).toString());
                            }
                        case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                        case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                        case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                            if (!logger.isLoggable(Level.FINE)) {
                                break;
                            }
                            logger.log(Level.FINE, "There was an OPT answer. Option code: " + optionCode + " data: " + _hexString(optiondata));
                            break;
                        case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                            logger.log(Level.WARNING, "There was an OPT answer. Not currently handled. Option code: " + optionCodeInt + " data: " + _hexString(optiondata));
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("DNSIncoming() unknown type:" + type);
                }
                this._messageInputStream.skip((long) len);
                break;
        }
        if (rec != null) {
            rec.setRecordSource(source);
        }
        return rec;
    }

    String print(boolean dump) {
        StringBuilder buf = new StringBuilder();
        buf.append(print());
        if (dump) {
            byte[] data = new byte[this._packet.getLength()];
            System.arraycopy(this._packet.getData(), 0, data, 0, data.length);
            buf.append(print(data));
        }
        return buf.toString();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(isQuery() ? "dns[query," : "dns[response,");
        if (this._packet.getAddress() != null) {
            buf.append(this._packet.getAddress().getHostAddress());
        }
        buf.append(':');
        buf.append(this._packet.getPort());
        buf.append(", length=");
        buf.append(this._packet.getLength());
        buf.append(", id=0x");
        buf.append(Integer.toHexString(getId()));
        if (getFlags() != 0) {
            buf.append(", flags=0x");
            buf.append(Integer.toHexString(getFlags()));
            if ((getFlags() & DNSRecordClass.CLASS_UNIQUE) != 0) {
                buf.append(":r");
            }
            if ((getFlags() & Util.DEFAULT_COPY_BUFFER_SIZE) != 0) {
                buf.append(":aa");
            }
            if ((getFlags() & RCommandClient.MIN_CLIENT_PORT) != 0) {
                buf.append(":tc");
            }
        }
        if (getNumberOfQuestions() > 0) {
            buf.append(", questions=");
            buf.append(getNumberOfQuestions());
        }
        if (getNumberOfAnswers() > 0) {
            buf.append(", answers=");
            buf.append(getNumberOfAnswers());
        }
        if (getNumberOfAuthorities() > 0) {
            buf.append(", authorities=");
            buf.append(getNumberOfAuthorities());
        }
        if (getNumberOfAdditionals() > 0) {
            buf.append(", additionals=");
            buf.append(getNumberOfAdditionals());
        }
        if (getNumberOfQuestions() > 0) {
            buf.append("\nquestions:");
            for (DNSQuestion question : this._questions) {
                buf.append("\n\t");
                buf.append(question);
            }
        }
        if (getNumberOfAnswers() > 0) {
            buf.append("\nanswers:");
            for (DNSRecord record : this._answers) {
                buf.append("\n\t");
                buf.append(record);
            }
        }
        if (getNumberOfAuthorities() > 0) {
            buf.append("\nauthorities:");
            for (DNSRecord record2 : this._authoritativeAnswers) {
                buf.append("\n\t");
                buf.append(record2);
            }
        }
        if (getNumberOfAdditionals() > 0) {
            buf.append("\nadditionals:");
            for (DNSRecord record22 : this._additionals) {
                buf.append("\n\t");
                buf.append(record22);
            }
        }
        buf.append("]");
        return buf.toString();
    }

    void append(DNSIncoming that) {
        if (isQuery() && isTruncated() && that.isQuery()) {
            this._questions.addAll(that.getQuestions());
            this._answers.addAll(that.getAnswers());
            this._authoritativeAnswers.addAll(that.getAuthorities());
            this._additionals.addAll(that.getAdditionals());
            return;
        }
        throw new IllegalArgumentException();
    }

    public int elapseSinceArrival() {
        return (int) (System.currentTimeMillis() - this._receivedTime);
    }

    public int getSenderUDPPayload() {
        return this._senderUDPPayload;
    }

    private String _hexString(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            int b2 = b & TelnetOption.MAX_OPTION_VALUE;
            result.append(_nibbleToHex[b2 / 16]);
            result.append(_nibbleToHex[b2 % 16]);
        }
        return result.toString();
    }
}
