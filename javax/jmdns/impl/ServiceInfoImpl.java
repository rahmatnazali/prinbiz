package javax.jmdns.impl;

import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceInfo.Fields;
import javax.jmdns.impl.DNSRecord.Address;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.DNSRecord.Text;
import javax.jmdns.impl.DNSStatefulObject.DefaultImplementation;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;
import org.kxml2.wap.Wbxml;
import org.xmlpull.v1.XmlPullParser;

public class ServiceInfoImpl extends ServiceInfo implements DNSListener, DNSStatefulObject {
    private static Logger logger;
    private String _application;
    private Delegate _delegate;
    private String _domain;
    private final Set<Inet4Address> _ipv4Addresses;
    private final Set<Inet6Address> _ipv6Addresses;
    private transient String _key;
    private String _name;
    private boolean _needTextAnnouncing;
    private boolean _persistent;
    private int _port;
    private int _priority;
    private Map<String, byte[]> _props;
    private String _protocol;
    private String _server;
    private final ServiceInfoState _state;
    private String _subtype;
    private byte[] _text;
    private int _weight;

    /* renamed from: javax.jmdns.impl.ServiceInfoImpl.1 */
    static /* synthetic */ class C05261 {
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
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_SRV.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_TXT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_PTR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public interface Delegate {
        void textValueUpdated(ServiceInfo serviceInfo, byte[] bArr);
    }

    private static final class ServiceInfoState extends DefaultImplementation {
        private static final long serialVersionUID = 1104131034952196820L;
        private final ServiceInfoImpl _info;

        public ServiceInfoState(ServiceInfoImpl info) {
            this._info = info;
        }

        protected void setTask(DNSTask task) {
            super.setTask(task);
            if (this._task == null && this._info.needTextAnnouncing()) {
                lock();
                try {
                    if (this._task == null && this._info.needTextAnnouncing()) {
                        if (this._state.isAnnounced()) {
                            setState(DNSState.ANNOUNCING_1);
                            if (getDns() != null) {
                                getDns().startAnnouncer();
                            }
                        }
                        this._info.setNeedTextAnnouncing(false);
                    }
                    unlock();
                } catch (Throwable th) {
                    unlock();
                }
            }
        }

        public void setDns(JmDNSImpl dns) {
            super.setDns(dns);
        }
    }

    static {
        logger = Logger.getLogger(ServiceInfoImpl.class.getName());
    }

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, String text) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, (byte[]) null);
        this._server = text;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(text.length());
            writeUTF(out, text);
            this._text = out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("unexpected exception: " + e);
        }
    }

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, Map<String, ?> props) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, textFromProperties(props));
    }

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, byte[] text) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, text);
    }

    public ServiceInfoImpl(Map<Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, Map<String, ?> props) {
        this((Map) qualifiedNameMap, port, weight, priority, persistent, textFromProperties(props));
    }

    ServiceInfoImpl(Map<Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, String text) {
        this((Map) qualifiedNameMap, port, weight, priority, persistent, (byte[]) null);
        this._server = text;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(text.length());
            writeUTF(out, text);
            this._text = out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("unexpected exception: " + e);
        }
    }

    ServiceInfoImpl(Map<Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, byte[] text) {
        Map<Fields, String> map = checkQualifiedNameMap(qualifiedNameMap);
        this._domain = (String) map.get(Fields.Domain);
        this._protocol = (String) map.get(Fields.Protocol);
        this._application = (String) map.get(Fields.Application);
        this._name = (String) map.get(Fields.Instance);
        this._subtype = (String) map.get(Fields.Subtype);
        this._port = port;
        this._weight = weight;
        this._priority = priority;
        this._text = text;
        setNeedTextAnnouncing(false);
        this._state = new ServiceInfoState(this);
        this._persistent = persistent;
        this._ipv4Addresses = Collections.synchronizedSet(new LinkedHashSet());
        this._ipv6Addresses = Collections.synchronizedSet(new LinkedHashSet());
    }

    ServiceInfoImpl(ServiceInfo info) {
        this._ipv4Addresses = Collections.synchronizedSet(new LinkedHashSet());
        this._ipv6Addresses = Collections.synchronizedSet(new LinkedHashSet());
        if (info != null) {
            this._domain = info.getDomain();
            this._protocol = info.getProtocol();
            this._application = info.getApplication();
            this._name = info.getName();
            this._subtype = info.getSubtype();
            this._port = info.getPort();
            this._weight = info.getWeight();
            this._priority = info.getPriority();
            this._text = info.getTextBytes();
            this._persistent = info.isPersistent();
            for (Inet6Address address : info.getInet6Addresses()) {
                this._ipv6Addresses.add(address);
            }
            for (Inet4Address address2 : info.getInet4Addresses()) {
                this._ipv4Addresses.add(address2);
            }
        }
        this._state = new ServiceInfoState(this);
    }

    public static Map<Fields, String> decodeQualifiedNameMap(String type, String name, String subtype) {
        Map<Fields, String> qualifiedNameMap = decodeQualifiedNameMapForType(type);
        qualifiedNameMap.put(Fields.Instance, name);
        qualifiedNameMap.put(Fields.Subtype, subtype);
        return checkQualifiedNameMap(qualifiedNameMap);
    }

    public static Map<Fields, String> decodeQualifiedNameMapForType(String type) {
        String casePreservedType = type;
        String aType = type.toLowerCase();
        String application = aType;
        String protocol = XmlPullParser.NO_NAMESPACE;
        String subtype = XmlPullParser.NO_NAMESPACE;
        String name = XmlPullParser.NO_NAMESPACE;
        String domain = XmlPullParser.NO_NAMESPACE;
        int index;
        if (aType.contains("in-addr.arpa") || aType.contains("ip6.arpa")) {
            index = aType.contains("in-addr.arpa") ? aType.indexOf("in-addr.arpa") : aType.indexOf("ip6.arpa");
            name = removeSeparators(casePreservedType.substring(0, index));
            domain = casePreservedType.substring(index);
            application = XmlPullParser.NO_NAMESPACE;
        } else if (aType.contains("_") || !aType.contains(".")) {
            int start;
            if (!aType.startsWith("_") || aType.startsWith("_services")) {
                index = aType.indexOf(46);
                if (index > 0) {
                    name = casePreservedType.substring(0, index);
                    if (index + 1 < aType.length()) {
                        aType = aType.substring(index + 1);
                        casePreservedType = casePreservedType.substring(index + 1);
                    }
                }
            }
            index = aType.lastIndexOf("._");
            if (index > 0) {
                start = index + 2;
                protocol = casePreservedType.substring(start, aType.indexOf(46, start));
            }
            if (protocol.length() > 0) {
                index = aType.indexOf("_" + protocol.toLowerCase() + ".");
                domain = casePreservedType.substring((protocol.length() + index) + 2, aType.length() - (aType.endsWith(".") ? 1 : 0));
                application = casePreservedType.substring(0, index - 1);
            }
            index = application.toLowerCase().indexOf("._sub");
            if (index > 0) {
                start = index + 5;
                subtype = removeSeparators(application.substring(0, index));
                application = application.substring(start);
            }
        } else {
            index = aType.indexOf(46);
            name = removeSeparators(casePreservedType.substring(0, index));
            domain = removeSeparators(casePreservedType.substring(index));
            application = XmlPullParser.NO_NAMESPACE;
        }
        Map<Fields, String> qualifiedNameMap = new HashMap(5);
        qualifiedNameMap.put(Fields.Domain, removeSeparators(domain));
        qualifiedNameMap.put(Fields.Protocol, protocol);
        qualifiedNameMap.put(Fields.Application, removeSeparators(application));
        qualifiedNameMap.put(Fields.Instance, name);
        qualifiedNameMap.put(Fields.Subtype, subtype);
        return qualifiedNameMap;
    }

    protected static Map<Fields, String> checkQualifiedNameMap(Map<Fields, String> qualifiedNameMap) {
        Map<Fields, String> checkedQualifiedNameMap = new HashMap(5);
        String domain = qualifiedNameMap.containsKey(Fields.Domain) ? (String) qualifiedNameMap.get(Fields.Domain) : "local";
        if (domain == null || domain.length() == 0) {
            domain = "local";
        }
        checkedQualifiedNameMap.put(Fields.Domain, removeSeparators(domain));
        String protocol = qualifiedNameMap.containsKey(Fields.Protocol) ? (String) qualifiedNameMap.get(Fields.Protocol) : "tcp";
        if (protocol == null || protocol.length() == 0) {
            protocol = "tcp";
        }
        checkedQualifiedNameMap.put(Fields.Protocol, removeSeparators(protocol));
        String application = qualifiedNameMap.containsKey(Fields.Application) ? (String) qualifiedNameMap.get(Fields.Application) : XmlPullParser.NO_NAMESPACE;
        if (application == null || application.length() == 0) {
            application = XmlPullParser.NO_NAMESPACE;
        }
        checkedQualifiedNameMap.put(Fields.Application, removeSeparators(application));
        String instance = qualifiedNameMap.containsKey(Fields.Instance) ? (String) qualifiedNameMap.get(Fields.Instance) : XmlPullParser.NO_NAMESPACE;
        if (instance == null || instance.length() == 0) {
            instance = XmlPullParser.NO_NAMESPACE;
        }
        checkedQualifiedNameMap.put(Fields.Instance, removeSeparators(instance));
        String subtype = qualifiedNameMap.containsKey(Fields.Subtype) ? (String) qualifiedNameMap.get(Fields.Subtype) : XmlPullParser.NO_NAMESPACE;
        if (subtype == null || subtype.length() == 0) {
            subtype = XmlPullParser.NO_NAMESPACE;
        }
        checkedQualifiedNameMap.put(Fields.Subtype, removeSeparators(subtype));
        return checkedQualifiedNameMap;
    }

    private static String removeSeparators(String name) {
        if (name == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String newName = name.trim();
        if (newName.startsWith(".")) {
            newName = newName.substring(1);
        }
        if (newName.startsWith("_")) {
            newName = newName.substring(1);
        }
        if (newName.endsWith(".")) {
            return newName.substring(0, newName.length() - 1);
        }
        return newName;
    }

    public String getType() {
        String domain = getDomain();
        String protocol = getProtocol();
        String application = getApplication();
        return (application.length() > 0 ? "_" + application + "." : XmlPullParser.NO_NAMESPACE) + (protocol.length() > 0 ? "_" + protocol + "." : XmlPullParser.NO_NAMESPACE) + domain + ".";
    }

    public String getTypeWithSubtype() {
        String subtype = getSubtype();
        return (subtype.length() > 0 ? "_" + subtype.toLowerCase() + "._sub." : XmlPullParser.NO_NAMESPACE) + getType();
    }

    public String getName() {
        return this._name != null ? this._name : XmlPullParser.NO_NAMESPACE;
    }

    public String getKey() {
        if (this._key == null) {
            this._key = getQualifiedName().toLowerCase();
        }
        return this._key;
    }

    void setName(String name) {
        this._name = name;
        this._key = null;
    }

    public String getQualifiedName() {
        String domain = getDomain();
        String protocol = getProtocol();
        String application = getApplication();
        String instance = getName();
        return (instance.length() > 0 ? instance + "." : XmlPullParser.NO_NAMESPACE) + (application.length() > 0 ? "_" + application + "." : XmlPullParser.NO_NAMESPACE) + (protocol.length() > 0 ? "_" + protocol + "." : XmlPullParser.NO_NAMESPACE) + domain + ".";
    }

    public String getServer() {
        return this._server != null ? this._server : XmlPullParser.NO_NAMESPACE;
    }

    void setServer(String server) {
        this._server = server;
    }

    @Deprecated
    public String getHostAddress() {
        String[] names = getHostAddresses();
        return names.length > 0 ? names[0] : XmlPullParser.NO_NAMESPACE;
    }

    public String[] getHostAddresses() {
        int i;
        Inet4Address[] ip4Aaddresses = getInet4Addresses();
        Inet6Address[] ip6Aaddresses = getInet6Addresses();
        String[] names = new String[(ip4Aaddresses.length + ip6Aaddresses.length)];
        for (i = 0; i < ip4Aaddresses.length; i++) {
            names[i] = ip4Aaddresses[i].getHostAddress();
        }
        for (i = 0; i < ip6Aaddresses.length; i++) {
            names[ip4Aaddresses.length + i] = "[" + ip6Aaddresses[i].getHostAddress() + "]";
        }
        return names;
    }

    void addAddress(Inet4Address addr) {
        this._ipv4Addresses.add(addr);
    }

    void addAddress(Inet6Address addr) {
        this._ipv6Addresses.add(addr);
    }

    @Deprecated
    public InetAddress getAddress() {
        return getInetAddress();
    }

    @Deprecated
    public InetAddress getInetAddress() {
        InetAddress[] addresses = getInetAddresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    @Deprecated
    public Inet4Address getInet4Address() {
        Inet4Address[] addresses = getInet4Addresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    @Deprecated
    public Inet6Address getInet6Address() {
        Inet6Address[] addresses = getInet6Addresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    public InetAddress[] getInetAddresses() {
        List<InetAddress> aList = new ArrayList(this._ipv4Addresses.size() + this._ipv6Addresses.size());
        aList.addAll(this._ipv4Addresses);
        aList.addAll(this._ipv6Addresses);
        return (InetAddress[]) aList.toArray(new InetAddress[aList.size()]);
    }

    public Inet4Address[] getInet4Addresses() {
        return (Inet4Address[]) this._ipv4Addresses.toArray(new Inet4Address[this._ipv4Addresses.size()]);
    }

    public Inet6Address[] getInet6Addresses() {
        return (Inet6Address[]) this._ipv6Addresses.toArray(new Inet6Address[this._ipv6Addresses.size()]);
    }

    public int getPort() {
        return this._port;
    }

    public int getPriority() {
        return this._priority;
    }

    public int getWeight() {
        return this._weight;
    }

    public byte[] getTextBytes() {
        return (this._text == null || this._text.length <= 0) ? DNSRecord.EMPTY_TXT : this._text;
    }

    @Deprecated
    public String getTextString() {
        Map<String, byte[]> properties = getProperties();
        Iterator i$ = properties.keySet().iterator();
        if (!i$.hasNext()) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String key = (String) i$.next();
        byte[] value = (byte[]) properties.get(key);
        if (value == null || value.length <= 0) {
            return key;
        }
        return key + "=" + new String(value);
    }

    @Deprecated
    public String getURL() {
        return getURL("http");
    }

    public String[] getURLs() {
        return getURLs("http");
    }

    @Deprecated
    public String getURL(String protocol) {
        String[] urls = getURLs(protocol);
        return urls.length > 0 ? urls[0] : protocol + "://null:" + getPort();
    }

    public String[] getURLs(String protocol) {
        InetAddress[] addresses = getInetAddresses();
        String[] urls = new String[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            String url = protocol + "://" + addresses[i].getHostAddress() + ":" + getPort();
            String path = getPropertyString("path");
            if (path != null) {
                if (path.indexOf("://") >= 0) {
                    url = path;
                } else {
                    StringBuilder append = new StringBuilder().append(url);
                    if (!path.startsWith("/")) {
                        path = "/" + path;
                    }
                    url = append.append(path).toString();
                }
            }
            urls[i] = url;
        }
        return urls;
    }

    public synchronized byte[] getPropertyBytes(String name) {
        return (byte[]) getProperties().get(name);
    }

    public synchronized String getPropertyString(String name) {
        String str;
        byte[] data = (byte[]) getProperties().get(name);
        if (data == null) {
            str = null;
        } else if (data == NO_VALUE) {
            str = "true";
        } else {
            str = readUTF(data, 0, data.length);
        }
        return str;
    }

    public Enumeration<String> getPropertyNames() {
        Map<String, byte[]> properties = getProperties();
        return new Vector(properties != null ? properties.keySet() : Collections.emptySet()).elements();
    }

    public String getApplication() {
        return this._application != null ? this._application : XmlPullParser.NO_NAMESPACE;
    }

    public String getDomain() {
        return this._domain != null ? this._domain : "local";
    }

    public String getProtocol() {
        return this._protocol != null ? this._protocol : "tcp";
    }

    public String getSubtype() {
        return this._subtype != null ? this._subtype : XmlPullParser.NO_NAMESPACE;
    }

    public Map<Fields, String> getQualifiedNameMap() {
        Map<Fields, String> map = new HashMap(5);
        map.put(Fields.Domain, getDomain());
        map.put(Fields.Protocol, getProtocol());
        map.put(Fields.Application, getApplication());
        map.put(Fields.Instance, getName());
        map.put(Fields.Subtype, getSubtype());
        return map;
    }

    static void writeUTF(OutputStream out, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            int c = str.charAt(i);
            if (c >= 1 && c <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
                out.write(c);
            } else if (c > 2047) {
                out.write(((c >> 12) & 15) | 224);
                out.write(((c >> 6) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
                out.write(((c >> 0) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            } else {
                out.write(((c >> 6) & 31) | Wbxml.EXT_0);
                out.write(((c >> 0) & 63) | TransportMediator.FLAG_KEY_MEDIA_NEXT);
            }
        }
    }

    String readUTF(byte[] data, int off, int len) {
        int offset = off;
        StringBuffer buf = new StringBuffer();
        int end = offset + len;
        int offset2 = offset;
        while (offset2 < end) {
            offset = offset2 + 1;
            int ch = data[offset2] & TelnetOption.MAX_OPTION_VALUE;
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
                    if (offset < len) {
                        ch = ((ch & 31) << 6) | (data[offset] & 63);
                        offset++;
                        break;
                    }
                    return null;
                case ConnectionResult.TIMEOUT /*14*/:
                    if (offset + 2 < len) {
                        offset2 = offset + 1;
                        offset = offset2 + 1;
                        ch = (((ch & 15) << 12) | ((data[offset] & 63) << 6)) | (data[offset2] & 63);
                        break;
                    }
                    return null;
                default:
                    if (offset + 1 < len) {
                        ch = ((ch & 63) << 4) | (data[offset] & 15);
                        offset++;
                        break;
                    }
                    return null;
            }
            buf.append((char) ch);
            offset2 = offset;
        }
        offset = offset2;
        return buf.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    synchronized java.util.Map<java.lang.String, byte[]> getProperties() {
        /*
        r12 = this;
        monitor-enter(r12);
        r8 = r12._props;	 Catch:{ all -> 0x0066 }
        if (r8 != 0) goto L_0x0033;
    L_0x0005:
        r8 = r12.getTextBytes();	 Catch:{ all -> 0x0066 }
        if (r8 == 0) goto L_0x0033;
    L_0x000b:
        r6 = new java.util.Hashtable;	 Catch:{ all -> 0x0066 }
        r6.<init>();	 Catch:{ all -> 0x0066 }
        r4 = 0;
        r5 = r4;
    L_0x0012:
        r8 = r12.getTextBytes();	 Catch:{ Exception -> 0x008e }
        r8 = r8.length;	 Catch:{ Exception -> 0x008e }
        if (r5 >= r8) goto L_0x0091;
    L_0x0019:
        r8 = r12.getTextBytes();	 Catch:{ Exception -> 0x008e }
        r4 = r5 + 1;
        r8 = r8[r5];	 Catch:{ Exception -> 0x005b }
        r2 = r8 & 255;
        if (r2 == 0) goto L_0x002e;
    L_0x0025:
        r8 = r4 + r2;
        r9 = r12.getTextBytes();	 Catch:{ Exception -> 0x005b }
        r9 = r9.length;	 Catch:{ Exception -> 0x005b }
        if (r8 <= r9) goto L_0x003b;
    L_0x002e:
        r6.clear();	 Catch:{ Exception -> 0x005b }
    L_0x0031:
        r12._props = r6;	 Catch:{ all -> 0x0066 }
    L_0x0033:
        r8 = r12._props;	 Catch:{ all -> 0x0066 }
        if (r8 == 0) goto L_0x0089;
    L_0x0037:
        r8 = r12._props;	 Catch:{ all -> 0x0066 }
    L_0x0039:
        monitor-exit(r12);
        return r8;
    L_0x003b:
        r1 = 0;
    L_0x003c:
        if (r1 >= r2) goto L_0x004d;
    L_0x003e:
        r8 = r12.getTextBytes();	 Catch:{ Exception -> 0x005b }
        r9 = r4 + r1;
        r8 = r8[r9];	 Catch:{ Exception -> 0x005b }
        r9 = 61;
        if (r8 == r9) goto L_0x004d;
    L_0x004a:
        r1 = r1 + 1;
        goto L_0x003c;
    L_0x004d:
        r8 = r12.getTextBytes();	 Catch:{ Exception -> 0x005b }
        r3 = r12.readUTF(r8, r4, r1);	 Catch:{ Exception -> 0x005b }
        if (r3 != 0) goto L_0x0069;
    L_0x0057:
        r6.clear();	 Catch:{ Exception -> 0x005b }
        goto L_0x0031;
    L_0x005b:
        r0 = move-exception;
    L_0x005c:
        r8 = logger;	 Catch:{ all -> 0x0066 }
        r9 = java.util.logging.Level.WARNING;	 Catch:{ all -> 0x0066 }
        r10 = "Malformed TXT Field ";
        r8.log(r9, r10, r0);	 Catch:{ all -> 0x0066 }
        goto L_0x0031;
    L_0x0066:
        r8 = move-exception;
        monitor-exit(r12);
        throw r8;
    L_0x0069:
        if (r1 != r2) goto L_0x0072;
    L_0x006b:
        r8 = NO_VALUE;	 Catch:{ Exception -> 0x005b }
        r6.put(r3, r8);	 Catch:{ Exception -> 0x005b }
    L_0x0070:
        r5 = r4;
        goto L_0x0012;
    L_0x0072:
        r1 = r1 + 1;
        r8 = r2 - r1;
        r7 = new byte[r8];	 Catch:{ Exception -> 0x005b }
        r8 = r12.getTextBytes();	 Catch:{ Exception -> 0x005b }
        r9 = r4 + r1;
        r10 = 0;
        r11 = r2 - r1;
        java.lang.System.arraycopy(r8, r9, r7, r10, r11);	 Catch:{ Exception -> 0x005b }
        r6.put(r3, r7);	 Catch:{ Exception -> 0x005b }
        r4 = r4 + r2;
        goto L_0x0070;
    L_0x0089:
        r8 = java.util.Collections.emptyMap();	 Catch:{ all -> 0x0066 }
        goto L_0x0039;
    L_0x008e:
        r0 = move-exception;
        r4 = r5;
        goto L_0x005c;
    L_0x0091:
        r4 = r5;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.ServiceInfoImpl.getProperties():java.util.Map<java.lang.String, byte[]>");
    }

    public void updateRecord(DNSCache dnsCache, long now, DNSEntry rec) {
        if ((rec instanceof DNSRecord) && !rec.isExpired(now)) {
            boolean serviceUpdated = false;
            switch (C05261.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType[rec.getRecordType().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    if (rec.getName().equalsIgnoreCase(getServer())) {
                        this._ipv4Addresses.add((Inet4Address) ((Address) rec).getAddress());
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (rec.getName().equalsIgnoreCase(getServer())) {
                        this._ipv6Addresses.add((Inet6Address) ((Address) rec).getAddress());
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (rec.getName().equalsIgnoreCase(getQualifiedName())) {
                        Service srv = (Service) rec;
                        boolean serverChanged = this._server == null || !this._server.equalsIgnoreCase(srv.getServer());
                        this._server = srv.getServer();
                        this._port = srv.getPort();
                        this._weight = srv.getWeight();
                        this._priority = srv.getPriority();
                        if (!serverChanged) {
                            serviceUpdated = true;
                            break;
                        }
                        this._ipv4Addresses.clear();
                        this._ipv6Addresses.clear();
                        for (DNSEntry entry : dnsCache.getDNSEntryList(this._server, DNSRecordType.TYPE_A, DNSRecordClass.CLASS_IN)) {
                            updateRecord(dnsCache, now, entry);
                        }
                        for (DNSEntry entry2 : dnsCache.getDNSEntryList(this._server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_IN)) {
                            updateRecord(dnsCache, now, entry2);
                        }
                        break;
                    }
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (rec.getName().equalsIgnoreCase(getQualifiedName())) {
                        this._text = ((Text) rec).getText();
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (getSubtype().length() == 0 && rec.getSubtype().length() != 0) {
                        this._subtype = rec.getSubtype();
                        serviceUpdated = true;
                        break;
                    }
            }
            if (serviceUpdated && hasData()) {
                JmDNSImpl dns = getDns();
                if (dns != null) {
                    ServiceEvent event = ((DNSRecord) rec).getServiceEvent(dns);
                    dns.handleServiceResolved(new ServiceEventImpl(dns, event.getType(), event.getName(), this));
                }
            }
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public synchronized boolean hasData() {
        boolean z;
        z = getServer() != null && hasInetAddress() && getTextBytes() != null && getTextBytes().length > 0;
        return z;
    }

    private final boolean hasInetAddress() {
        return this._ipv4Addresses.size() > 0 || this._ipv6Addresses.size() > 0;
    }

    public boolean advanceState(DNSTask task) {
        return this._state.advanceState(task);
    }

    public boolean revertState() {
        return this._state.revertState();
    }

    public boolean cancelState() {
        return this._state.cancelState();
    }

    public boolean closeState() {
        return this._state.closeState();
    }

    public boolean recoverState() {
        return this._state.recoverState();
    }

    public void removeAssociationWithTask(DNSTask task) {
        this._state.removeAssociationWithTask(task);
    }

    public void associateWithTask(DNSTask task, DNSState state) {
        this._state.associateWithTask(task, state);
    }

    public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
        return this._state.isAssociatedWithTask(task, state);
    }

    public boolean isProbing() {
        return this._state.isProbing();
    }

    public boolean isAnnouncing() {
        return this._state.isAnnouncing();
    }

    public boolean isAnnounced() {
        return this._state.isAnnounced();
    }

    public boolean isCanceling() {
        return this._state.isCanceling();
    }

    public boolean isCanceled() {
        return this._state.isCanceled();
    }

    public boolean isClosing() {
        return this._state.isClosing();
    }

    public boolean isClosed() {
        return this._state.isClosed();
    }

    public boolean waitForAnnounced(long timeout) {
        return this._state.waitForAnnounced(timeout);
    }

    public boolean waitForCanceled(long timeout) {
        return this._state.waitForCanceled(timeout);
    }

    public int hashCode() {
        return getQualifiedName().hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ServiceInfoImpl) && getQualifiedName().equals(((ServiceInfoImpl) obj).getQualifiedName());
    }

    public String getNiceTextString() {
        StringBuffer buf = new StringBuffer();
        int len = getTextBytes().length;
        for (int i = 0; i < len; i++) {
            if (i >= NNTPReply.SERVER_READY_POSTING_ALLOWED) {
                buf.append("...");
                break;
            }
            int ch = getTextBytes()[i] & TelnetOption.MAX_OPTION_VALUE;
            if (ch < 32 || ch > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                buf.append("\\0");
                buf.append(Integer.toString(ch, 8));
            } else {
                buf.append((char) ch);
            }
        }
        return buf.toString();
    }

    public ServiceInfoImpl clone() {
        ServiceInfoImpl serviceInfo = new ServiceInfoImpl(getQualifiedNameMap(), this._port, this._weight, this._priority, this._persistent, this._text);
        for (Inet6Address address : getInet6Addresses()) {
            serviceInfo._ipv6Addresses.add(address);
        }
        for (Inet4Address address2 : getInet4Addresses()) {
            serviceInfo._ipv4Addresses.add(address2);
        }
        return serviceInfo;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[" + getClass().getSimpleName() + "@" + System.identityHashCode(this) + " ");
        buf.append("name: '");
        buf.append((getName().length() > 0 ? getName() + "." : XmlPullParser.NO_NAMESPACE) + getTypeWithSubtype());
        buf.append("' address: '");
        InetAddress[] addresses = getInetAddresses();
        if (addresses.length > 0) {
            for (InetAddress address : addresses) {
                buf.append(address);
                buf.append(':');
                buf.append(getPort());
                buf.append(' ');
            }
        } else {
            buf.append("(null):");
            buf.append(getPort());
        }
        buf.append("' status: '");
        buf.append(this._state.toString());
        buf.append(isPersistent() ? "' is persistent," : "',");
        buf.append(" has ");
        buf.append(hasData() ? XmlPullParser.NO_NAMESPACE : "NO ");
        buf.append("data");
        if (getTextBytes().length > 0) {
            Map<String, byte[]> properties = getProperties();
            if (properties.isEmpty()) {
                buf.append(" empty");
            } else {
                buf.append("\n");
                for (String key : properties.keySet()) {
                    buf.append("\t" + key + ": " + new String((byte[]) properties.get(key)) + "\n");
                }
            }
        }
        buf.append(']');
        return buf.toString();
    }

    public Collection<DNSRecord> answers(boolean unique, int ttl, HostInfo localHost) {
        List<DNSRecord> list = new ArrayList();
        if (getSubtype().length() > 0) {
            list.add(new Pointer(getTypeWithSubtype(), DNSRecordClass.CLASS_IN, false, ttl, getQualifiedName()));
        }
        list.add(new Pointer(getType(), DNSRecordClass.CLASS_IN, false, ttl, getQualifiedName()));
        list.add(new Service(getQualifiedName(), DNSRecordClass.CLASS_IN, unique, ttl, this._priority, this._weight, this._port, localHost.getName()));
        list.add(new Text(getQualifiedName(), DNSRecordClass.CLASS_IN, unique, ttl, getTextBytes()));
        return list;
    }

    public void setText(byte[] text) throws IllegalStateException {
        synchronized (this) {
            this._text = text;
            this._props = null;
            setNeedTextAnnouncing(true);
        }
    }

    public void setText(Map<String, ?> props) throws IllegalStateException {
        setText(textFromProperties(props));
    }

    void _setText(byte[] text) {
        this._text = text;
        this._props = null;
    }

    private static byte[] textFromProperties(Map<String, ?> props) {
        byte[] text = null;
        if (props != null) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream(DNSConstants.FLAGS_RD);
                for (String key : props.keySet()) {
                    Object val = props.get(key);
                    ByteArrayOutputStream out2 = new ByteArrayOutputStream(100);
                    writeUTF(out2, key);
                    if (val != null) {
                        if (val instanceof String) {
                            out2.write(61);
                            writeUTF(out2, (String) val);
                        } else if (val instanceof byte[]) {
                            byte[] bval = (byte[]) val;
                            if (bval.length > 0) {
                                out2.write(61);
                                out2.write(bval, 0, bval.length);
                            } else {
                                val = null;
                            }
                        } else {
                            throw new IllegalArgumentException("invalid property value: " + val);
                        }
                    }
                    byte[] data = out2.toByteArray();
                    if (data.length > TelnetOption.MAX_OPTION_VALUE) {
                        throw new IOException("Cannot have individual values larger that 255 chars. Offending value: " + key + (val != null ? XmlPullParser.NO_NAMESPACE : "=" + val));
                    }
                    out.write((byte) data.length);
                    out.write(data, 0, data.length);
                }
                text = out.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("unexpected exception: " + e);
            }
        }
        return (text == null || text.length <= 0) ? DNSRecord.EMPTY_TXT : text;
    }

    public void setDns(JmDNSImpl dns) {
        this._state.setDns(dns);
    }

    public JmDNSImpl getDns() {
        return this._state.getDns();
    }

    public boolean isPersistent() {
        return this._persistent;
    }

    public void setNeedTextAnnouncing(boolean needTextAnnouncing) {
        this._needTextAnnouncing = needTextAnnouncing;
        if (this._needTextAnnouncing) {
            this._state.setTask(null);
        }
    }

    public boolean needTextAnnouncing() {
        return this._needTextAnnouncing;
    }

    Delegate getDelegate() {
        return this._delegate;
    }

    void setDelegate(Delegate delegate) {
        this._delegate = delegate;
    }
}
