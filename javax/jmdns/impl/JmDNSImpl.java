package javax.jmdns.impl;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.JmDNS.Delegate;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceInfo.Fields;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.DNSTaskStarter.Factory;
import javax.jmdns.impl.ListenerStatus.ServiceListenerStatus;
import javax.jmdns.impl.ListenerStatus.ServiceTypeListenerStatus;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import org.apache.commons.net.bsd.RCommandClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class JmDNSImpl extends JmDNS implements DNSStatefulObject, DNSTaskStarter {
    private static final Random _random;
    private static Logger logger;
    private final DNSCache _cache;
    private volatile Delegate _delegate;
    private final ExecutorService _executor;
    private volatile InetAddress _group;
    private Thread _incomingListener;
    private final ReentrantLock _ioLock;
    private long _lastThrottleIncrement;
    private final List<DNSListener> _listeners;
    private HostInfo _localHost;
    private final String _name;
    private DNSIncoming _plannedAnswer;
    private final Object _recoverLock;
    private final ConcurrentMap<String, ServiceCollector> _serviceCollectors;
    private final ConcurrentMap<String, List<ServiceListenerStatus>> _serviceListeners;
    private final ConcurrentMap<String, ServiceTypeEntry> _serviceTypes;
    private final ConcurrentMap<String, ServiceInfo> _services;
    protected Thread _shutdown;
    private volatile MulticastSocket _socket;
    private int _throttle;
    private final Set<ServiceTypeListenerStatus> _typeListeners;

    /* renamed from: javax.jmdns.impl.JmDNSImpl.1 */
    class C05131 implements Runnable {
        final /* synthetic */ ServiceListenerStatus val$listener;
        final /* synthetic */ ServiceEvent val$localEvent;

        C05131(ServiceListenerStatus serviceListenerStatus, ServiceEvent serviceEvent) {
            this.val$listener = serviceListenerStatus;
            this.val$localEvent = serviceEvent;
        }

        public void run() {
            this.val$listener.serviceResolved(this.val$localEvent);
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.2 */
    class C05142 implements Runnable {
        final /* synthetic */ ServiceEvent val$event;
        final /* synthetic */ ServiceTypeListenerStatus val$status;

        C05142(ServiceTypeListenerStatus serviceTypeListenerStatus, ServiceEvent serviceEvent) {
            this.val$status = serviceTypeListenerStatus;
            this.val$event = serviceEvent;
        }

        public void run() {
            this.val$status.serviceTypeAdded(this.val$event);
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.3 */
    class C05153 implements Runnable {
        final /* synthetic */ ServiceEvent val$event;
        final /* synthetic */ ServiceTypeListenerStatus val$status;

        C05153(ServiceTypeListenerStatus serviceTypeListenerStatus, ServiceEvent serviceEvent) {
            this.val$status = serviceTypeListenerStatus;
            this.val$event = serviceEvent;
        }

        public void run() {
            this.val$status.subTypeForServiceTypeAdded(this.val$event);
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.4 */
    class C05164 implements Runnable {
        final /* synthetic */ ServiceListenerStatus val$listener;
        final /* synthetic */ ServiceEvent val$localEvent;

        C05164(ServiceListenerStatus serviceListenerStatus, ServiceEvent serviceEvent) {
            this.val$listener = serviceListenerStatus;
            this.val$localEvent = serviceEvent;
        }

        public void run() {
            this.val$listener.serviceAdded(this.val$localEvent);
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.5 */
    class C05175 implements Runnable {
        final /* synthetic */ ServiceListenerStatus val$listener;
        final /* synthetic */ ServiceEvent val$localEvent;

        C05175(ServiceListenerStatus serviceListenerStatus, ServiceEvent serviceEvent) {
            this.val$listener = serviceListenerStatus;
            this.val$localEvent = serviceEvent;
        }

        public void run() {
            this.val$listener.serviceRemoved(this.val$localEvent);
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.6 */
    class C05186 extends Thread {
        C05186(String x0) {
            super(x0);
        }

        public void run() {
            JmDNSImpl.this.__recover();
        }
    }

    /* renamed from: javax.jmdns.impl.JmDNSImpl.7 */
    static /* synthetic */ class C05197 {
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation;

        static {
            $SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation = new int[Operation.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation[Operation.Add.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation[Operation.Remove.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public enum Operation {
        Remove,
        Update,
        Add,
        RegisterServiceType,
        Noop
    }

    public static class ServiceTypeEntry extends AbstractMap<String, String> implements Cloneable {
        private final Set<Entry<String, String>> _entrySet;
        private final String _type;

        private static class SubTypeEntry implements Entry<String, String>, Serializable, Cloneable {
            private static final long serialVersionUID = 9188503522395855322L;
            private final String _key;
            private final String _value;

            public SubTypeEntry(String subtype) {
                if (subtype == null) {
                    subtype = XmlPullParser.NO_NAMESPACE;
                }
                this._value = subtype;
                this._key = this._value.toLowerCase();
            }

            public String getKey() {
                return this._key;
            }

            public String getValue() {
                return this._value;
            }

            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            public boolean equals(Object entry) {
                if (!(entry instanceof Entry)) {
                    return false;
                }
                boolean z = getKey().equals(((Entry) entry).getKey()) && getValue().equals(((Entry) entry).getValue());
                return z;
            }

            public int hashCode() {
                int i = 0;
                int hashCode = this._key == null ? 0 : this._key.hashCode();
                if (this._value != null) {
                    i = this._value.hashCode();
                }
                return hashCode ^ i;
            }

            public SubTypeEntry clone() {
                return this;
            }

            public String toString() {
                return this._key + "=" + this._value;
            }
        }

        public ServiceTypeEntry(String type) {
            this._type = type;
            this._entrySet = new HashSet();
        }

        public String getType() {
            return this._type;
        }

        public Set<Entry<String, String>> entrySet() {
            return this._entrySet;
        }

        public boolean contains(String subtype) {
            return subtype != null && containsKey(subtype.toLowerCase());
        }

        public boolean add(String subtype) {
            if (subtype == null || contains(subtype)) {
                return false;
            }
            this._entrySet.add(new SubTypeEntry(subtype));
            return true;
        }

        public Iterator<String> iterator() {
            return keySet().iterator();
        }

        public ServiceTypeEntry clone() {
            ServiceTypeEntry entry = new ServiceTypeEntry(getType());
            for (Entry<String, String> subTypeEntry : entrySet()) {
                entry.add((String) subTypeEntry.getValue());
            }
            return entry;
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(NNTPReply.SERVER_READY_POSTING_ALLOWED);
            if (isEmpty()) {
                aLog.append("empty");
            } else {
                for (String value : values()) {
                    aLog.append(value);
                    aLog.append(", ");
                }
                aLog.setLength(aLog.length() - 2);
            }
            return aLog.toString();
        }
    }

    protected class Shutdown implements Runnable {
        protected Shutdown() {
        }

        public void run() {
            try {
                JmDNSImpl.this._shutdown = null;
                JmDNSImpl.this.close();
            } catch (Throwable exception) {
                System.err.println("Error while shuting down. " + exception);
            }
        }
    }

    private static class ServiceCollector implements ServiceListener {
        private final ConcurrentMap<String, ServiceEvent> _events;
        private final ConcurrentMap<String, ServiceInfo> _infos;
        private volatile boolean _needToWaitForInfos;
        private final String _type;

        public ServiceCollector(String type) {
            this._infos = new ConcurrentHashMap();
            this._events = new ConcurrentHashMap();
            this._type = type;
            this._needToWaitForInfos = true;
        }

        public void serviceAdded(ServiceEvent event) {
            synchronized (this) {
                ServiceInfo info = event.getInfo();
                if (info == null || !info.hasData()) {
                    info = ((JmDNSImpl) event.getDNS()).resolveServiceInfo(event.getType(), event.getName(), info != null ? info.getSubtype() : XmlPullParser.NO_NAMESPACE, true);
                    if (info != null) {
                        this._infos.put(event.getName(), info);
                    } else {
                        this._events.put(event.getName(), event);
                    }
                } else {
                    this._infos.put(event.getName(), info);
                }
            }
        }

        public void serviceRemoved(ServiceEvent event) {
            synchronized (this) {
                this._infos.remove(event.getName());
                this._events.remove(event.getName());
            }
        }

        public void serviceResolved(ServiceEvent event) {
            synchronized (this) {
                this._infos.put(event.getName(), event.getInfo());
                this._events.remove(event.getName());
            }
        }

        public ServiceInfo[] list(long timeout) {
            if (this._infos.isEmpty() || !this._events.isEmpty() || this._needToWaitForInfos) {
                long loops = timeout / 200;
                if (loops < 1) {
                    loops = 1;
                }
                for (int i = 0; ((long) i) < loops; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    if (this._events.isEmpty() && !this._infos.isEmpty() && !this._needToWaitForInfos) {
                        break;
                    }
                }
            }
            this._needToWaitForInfos = false;
            return (ServiceInfo[]) this._infos.values().toArray(new ServiceInfo[this._infos.size()]);
        }

        public String toString() {
            StringBuffer aLog = new StringBuffer();
            aLog.append("\n\tType: ");
            aLog.append(this._type);
            if (this._infos.isEmpty()) {
                aLog.append("\n\tNo services collected.");
            } else {
                aLog.append("\n\tServices");
                for (String key : this._infos.keySet()) {
                    aLog.append("\n\t\tService: ");
                    aLog.append(key);
                    aLog.append(": ");
                    aLog.append(this._infos.get(key));
                }
            }
            if (this._events.isEmpty()) {
                aLog.append("\n\tNo event queued.");
            } else {
                aLog.append("\n\tEvents");
                for (String key2 : this._events.keySet()) {
                    aLog.append("\n\t\tEvent: ");
                    aLog.append(key2);
                    aLog.append(": ");
                    aLog.append(this._events.get(key2));
                }
            }
            return aLog.toString();
        }
    }

    static {
        logger = Logger.getLogger(JmDNSImpl.class.getName());
        _random = new Random();
    }

    public static void main(String[] argv) {
        String version;
        try {
            Properties pomProperties = new Properties();
            pomProperties.load(JmDNSImpl.class.getResourceAsStream("/META-INF/maven/javax.jmdns/jmdns/pom.properties"));
            version = pomProperties.getProperty("version");
        } catch (Exception e) {
            version = "RUNNING.IN.IDE.FULL";
        }
        System.out.println("JmDNS version \"" + version + "\"");
        System.out.println(" ");
        System.out.println("Running on java version \"" + System.getProperty("java.version") + "\"" + " (build " + System.getProperty("java.runtime.version") + ")" + " from " + System.getProperty("java.vendor"));
        System.out.println("Operating environment \"" + System.getProperty("os.name") + "\"" + " version " + System.getProperty("os.version") + " on " + System.getProperty("os.arch"));
        System.out.println("For more information on JmDNS please visit https://sourceforge.net/projects/jmdns/");
    }

    public JmDNSImpl(InetAddress address, String name) throws IOException {
        this._executor = Executors.newSingleThreadExecutor();
        this._ioLock = new ReentrantLock();
        this._recoverLock = new Object();
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("JmDNS instance created");
        }
        this._cache = new DNSCache(100);
        this._listeners = Collections.synchronizedList(new ArrayList());
        this._serviceListeners = new ConcurrentHashMap();
        this._typeListeners = Collections.synchronizedSet(new HashSet());
        this._serviceCollectors = new ConcurrentHashMap();
        this._services = new ConcurrentHashMap(20);
        this._serviceTypes = new ConcurrentHashMap(20);
        this._localHost = HostInfo.newHostInfo(address, this, name);
        if (name == null) {
            name = this._localHost.getName();
        }
        this._name = name;
        openMulticastSocket(getLocalHost());
        start(getServices().values());
        startReaper();
    }

    private void start(Collection<? extends ServiceInfo> serviceInfos) {
        if (this._incomingListener == null) {
            this._incomingListener = new SocketListener(this);
            this._incomingListener.start();
        }
        startProber();
        for (ServiceInfo info : serviceInfos) {
            try {
                registerService(new ServiceInfoImpl(info));
            } catch (Exception exception) {
                logger.log(Level.WARNING, "start() Registration exception ", exception);
            }
        }
    }

    private void openMulticastSocket(HostInfo hostInfo) throws IOException {
        if (this._group == null) {
            if (hostInfo.getInetAddress() instanceof Inet6Address) {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP_IPV6);
            } else {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP);
            }
        }
        if (this._socket != null) {
            closeMulticastSocket();
        }
        this._socket = new MulticastSocket(DNSConstants.MDNS_PORT);
        if (!(hostInfo == null || hostInfo.getInterface() == null)) {
            try {
                this._socket.setNetworkInterface(hostInfo.getInterface());
            } catch (SocketException e) {
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("openMulticastSocket() Set network interface exception: " + e.getMessage());
                }
            }
        }
        this._socket.setTimeToLive(TelnetOption.MAX_OPTION_VALUE);
        this._socket.joinGroup(this._group);
    }

    private void closeMulticastSocket() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("closeMulticastSocket()");
        }
        if (this._socket != null) {
            try {
                this._socket.leaveGroup(this._group);
            } catch (SocketException e) {
            }
            try {
                this._socket.close();
                while (this._incomingListener != null && this._incomingListener.isAlive()) {
                    synchronized (this) {
                        try {
                            if (this._incomingListener != null && this._incomingListener.isAlive()) {
                                if (logger.isLoggable(Level.FINER)) {
                                    logger.finer("closeMulticastSocket(): waiting for jmDNS monitor");
                                }
                                wait(1000);
                            }
                        } catch (InterruptedException e2) {
                        }
                    }
                }
                this._incomingListener = null;
            } catch (Exception exception) {
                logger.log(Level.WARNING, "closeMulticastSocket() Close socket exception ", exception);
            }
            this._socket = null;
        }
    }

    public boolean advanceState(DNSTask task) {
        return this._localHost.advanceState(task);
    }

    public boolean revertState() {
        return this._localHost.revertState();
    }

    public boolean cancelState() {
        return this._localHost.cancelState();
    }

    public boolean closeState() {
        return this._localHost.closeState();
    }

    public boolean recoverState() {
        return this._localHost.recoverState();
    }

    public JmDNSImpl getDns() {
        return this;
    }

    public void associateWithTask(DNSTask task, DNSState state) {
        this._localHost.associateWithTask(task, state);
    }

    public void removeAssociationWithTask(DNSTask task) {
        this._localHost.removeAssociationWithTask(task);
    }

    public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
        return this._localHost.isAssociatedWithTask(task, state);
    }

    public boolean isProbing() {
        return this._localHost.isProbing();
    }

    public boolean isAnnouncing() {
        return this._localHost.isAnnouncing();
    }

    public boolean isAnnounced() {
        return this._localHost.isAnnounced();
    }

    public boolean isCanceling() {
        return this._localHost.isCanceling();
    }

    public boolean isCanceled() {
        return this._localHost.isCanceled();
    }

    public boolean isClosing() {
        return this._localHost.isClosing();
    }

    public boolean isClosed() {
        return this._localHost.isClosed();
    }

    public boolean waitForAnnounced(long timeout) {
        return this._localHost.waitForAnnounced(timeout);
    }

    public boolean waitForCanceled(long timeout) {
        return this._localHost.waitForCanceled(timeout);
    }

    public DNSCache getCache() {
        return this._cache;
    }

    public String getName() {
        return this._name;
    }

    public String getHostName() {
        return this._localHost.getName();
    }

    public HostInfo getLocalHost() {
        return this._localHost;
    }

    public InetAddress getInterface() throws IOException {
        return this._socket.getInterface();
    }

    public ServiceInfo getServiceInfo(String type, String name) {
        return getServiceInfo(type, name, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo getServiceInfo(String type, String name, long timeout) {
        return getServiceInfo(type, name, false, timeout);
    }

    public ServiceInfo getServiceInfo(String type, String name, boolean persistent) {
        return getServiceInfo(type, name, persistent, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo getServiceInfo(String type, String name, boolean persistent, long timeout) {
        ServiceInfoImpl info = resolveServiceInfo(type, name, XmlPullParser.NO_NAMESPACE, persistent);
        waitForInfoData(info, timeout);
        return info.hasData() ? info : null;
    }

    ServiceInfoImpl resolveServiceInfo(String type, String name, String subtype, boolean persistent) {
        cleanCache();
        String loType = type.toLowerCase();
        registerServiceType(type);
        if (this._serviceCollectors.putIfAbsent(loType, new ServiceCollector(type)) == null) {
            addServiceListener(loType, (ServiceListener) this._serviceCollectors.get(loType), true);
        }
        ServiceInfoImpl info = getServiceInfoFromCache(type, name, subtype, persistent);
        startServiceInfoResolver(info);
        return info;
    }

    ServiceInfoImpl getServiceInfoFromCache(String type, String name, String subtype, boolean persistent) {
        ServiceInfoImpl info = new ServiceInfoImpl(type, name, subtype, 0, 0, 0, persistent, (byte[]) null);
        DNSEntry pointerEntry = getCache().getDNSEntry(new Pointer(type, DNSRecordClass.CLASS_ANY, false, 0, info.getQualifiedName()));
        if (!(pointerEntry instanceof DNSRecord)) {
            return info;
        }
        ServiceInfoImpl cachedInfo = (ServiceInfoImpl) ((DNSRecord) pointerEntry).getServiceInfo(persistent);
        if (cachedInfo == null) {
            return info;
        }
        ServiceInfo cachedAddressInfo;
        Map map = cachedInfo.getQualifiedNameMap();
        byte[] srvBytes = null;
        String server = XmlPullParser.NO_NAMESPACE;
        DNSEntry serviceEntry = getCache().getDNSEntry(info.getQualifiedName(), DNSRecordType.TYPE_SRV, DNSRecordClass.CLASS_ANY);
        if (serviceEntry instanceof DNSRecord) {
            ServiceInfo cachedServiceEntryInfo = ((DNSRecord) serviceEntry).getServiceInfo(persistent);
            if (cachedServiceEntryInfo != null) {
                cachedInfo = new ServiceInfoImpl(map, cachedServiceEntryInfo.getPort(), cachedServiceEntryInfo.getWeight(), cachedServiceEntryInfo.getPriority(), persistent, (byte[]) null);
                srvBytes = cachedServiceEntryInfo.getTextBytes();
                server = cachedServiceEntryInfo.getServer();
            }
        }
        DNSEntry addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_A, DNSRecordClass.CLASS_ANY);
        if (addressEntry instanceof DNSRecord) {
            cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(persistent);
            if (cachedAddressInfo != null) {
                for (Inet4Address address : cachedAddressInfo.getInet4Addresses()) {
                    cachedInfo.addAddress(address);
                }
                cachedInfo._setText(cachedAddressInfo.getTextBytes());
            }
        }
        addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_ANY);
        if (addressEntry instanceof DNSRecord) {
            cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(persistent);
            if (cachedAddressInfo != null) {
                for (Inet6Address address2 : cachedAddressInfo.getInet6Addresses()) {
                    cachedInfo.addAddress(address2);
                }
                cachedInfo._setText(cachedAddressInfo.getTextBytes());
            }
        }
        DNSEntry textEntry = getCache().getDNSEntry(cachedInfo.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_ANY);
        if (textEntry instanceof DNSRecord) {
            ServiceInfo cachedTextInfo = ((DNSRecord) textEntry).getServiceInfo(persistent);
            if (cachedTextInfo != null) {
                cachedInfo._setText(cachedTextInfo.getTextBytes());
            }
        }
        if (cachedInfo.getTextBytes().length == 0) {
            cachedInfo._setText(srvBytes);
        }
        if (cachedInfo.hasData()) {
            return cachedInfo;
        }
        return info;
    }

    private void waitForInfoData(ServiceInfo info, long timeout) {
        synchronized (info) {
            long loops = timeout / 200;
            if (loops < 1) {
                loops = 1;
            }
            for (int i = 0; ((long) i) < loops && !info.hasData(); i++) {
                try {
                    info.wait(200);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void requestServiceInfo(String type, String name) {
        requestServiceInfo(type, name, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void requestServiceInfo(String type, String name, boolean persistent) {
        requestServiceInfo(type, name, persistent, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void requestServiceInfo(String type, String name, long timeout) {
        requestServiceInfo(type, name, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void requestServiceInfo(String type, String name, boolean persistent, long timeout) {
        waitForInfoData(resolveServiceInfo(type, name, XmlPullParser.NO_NAMESPACE, persistent), timeout);
    }

    void handleServiceResolved(ServiceEvent event) {
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(event.getType().toLowerCase());
        if (list != null && !list.isEmpty() && event.getInfo() != null && event.getInfo().hasData()) {
            ServiceEvent localEvent = event;
            synchronized (list) {
                List<ServiceListenerStatus> listCopy = new ArrayList(list);
            }
            for (ServiceListenerStatus listener : listCopy) {
                this._executor.submit(new C05131(listener, localEvent));
            }
        }
    }

    public void addServiceTypeListener(ServiceTypeListener listener) throws IOException {
        ServiceTypeListenerStatus status = new ServiceTypeListenerStatus(listener, false);
        this._typeListeners.add(status);
        for (String type : this._serviceTypes.keySet()) {
            status.serviceTypeAdded(new ServiceEventImpl(this, type, XmlPullParser.NO_NAMESPACE, null));
        }
        startTypeResolver();
    }

    public void removeServiceTypeListener(ServiceTypeListener listener) {
        this._typeListeners.remove(new ServiceTypeListenerStatus(listener, false));
    }

    public void addServiceListener(String type, ServiceListener listener) {
        addServiceListener(type, listener, false);
    }

    private void addServiceListener(String type, ServiceListener listener, boolean synch) {
        ServiceListenerStatus status = new ServiceListenerStatus(listener, synch);
        String loType = type.toLowerCase();
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(loType);
        if (list == null) {
            if (this._serviceListeners.putIfAbsent(loType, new LinkedList()) == null && this._serviceCollectors.putIfAbsent(loType, new ServiceCollector(type)) == null) {
                addServiceListener(loType, (ServiceListener) this._serviceCollectors.get(loType), true);
            }
            list = (List) this._serviceListeners.get(loType);
        }
        if (list != null) {
            synchronized (list) {
                if (!list.contains(listener)) {
                    list.add(status);
                }
            }
        }
        List<ServiceEvent> serviceEvents = new ArrayList();
        for (DNSEntry record : getCache().allValues()) {
            DNSRecord record2 = (DNSRecord) record;
            if (record2.getRecordType() == DNSRecordType.TYPE_SRV && record2.getKey().endsWith(loType)) {
                serviceEvents.add(new ServiceEventImpl(this, record2.getType(), toUnqualifiedName(record2.getType(), record2.getName()), record2.getServiceInfo()));
            }
        }
        for (ServiceEvent serviceEvent : serviceEvents) {
            status.serviceAdded(serviceEvent);
        }
        startServiceResolver(type);
    }

    public void removeServiceListener(String type, ServiceListener listener) {
        String loType = type.toLowerCase();
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(loType);
        if (list != null) {
            synchronized (list) {
                list.remove(new ServiceListenerStatus(listener, false));
                if (list.isEmpty()) {
                    this._serviceListeners.remove(loType, list);
                }
            }
        }
    }

    public void registerService(ServiceInfo infoAbstract) throws IOException {
        if (isClosing() || isClosed()) {
            throw new IllegalStateException("This DNS is closed.");
        }
        ServiceInfoImpl info = (ServiceInfoImpl) infoAbstract;
        if (info.getDns() != null) {
            if (info.getDns() != this) {
                throw new IllegalStateException("A service information can only be registered with a single instamce of JmDNS.");
            } else if (this._services.get(info.getKey()) != null) {
                throw new IllegalStateException("A service information can only be registered once.");
            }
        }
        info.setDns(this);
        registerServiceType(info.getTypeWithSubtype());
        info.recoverState();
        info.setServer(this._localHost.getName());
        info.addAddress(this._localHost.getInet4Address());
        info.addAddress(this._localHost.getInet6Address());
        waitForAnnounced(DNSConstants.SERVICE_INFO_TIMEOUT);
        makeServiceNameUnique(info);
        while (this._services.putIfAbsent(info.getKey(), info) != null) {
            makeServiceNameUnique(info);
        }
        startProber();
        info.waitForAnnounced(DNSConstants.SERVICE_INFO_TIMEOUT);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("registerService() JmDNS registered service as " + info);
        }
    }

    public void unregisterService(ServiceInfo infoAbstract) {
        ServiceInfoImpl info = (ServiceInfoImpl) this._services.get(infoAbstract.getKey());
        if (info != null) {
            info.cancelState();
            startCanceler();
            info.waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
            this._services.remove(info.getKey(), info);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("unregisterService() JmDNS unregistered service as " + info);
                return;
            }
            return;
        }
        logger.warning("Removing unregistered service info: " + infoAbstract.getKey());
    }

    public void unregisterAllServices() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("unregisterAllServices()");
        }
        for (String name : this._services.keySet()) {
            ServiceInfoImpl info = (ServiceInfoImpl) this._services.get(name);
            if (info != null) {
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("Cancelling service info: " + info);
                }
                info.cancelState();
            }
        }
        startCanceler();
        for (String name2 : this._services.keySet()) {
            info = (ServiceInfoImpl) this._services.get(name2);
            if (info != null) {
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("Wait for service info cancel: " + info);
                }
                info.waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
                this._services.remove(name2, info);
            }
        }
    }

    public boolean registerServiceType(String type) {
        ServiceEvent event;
        boolean typeAdded = false;
        Map<Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);
        String domain = (String) map.get(Fields.Domain);
        String protocol = (String) map.get(Fields.Protocol);
        String application = (String) map.get(Fields.Application);
        String subtype = (String) map.get(Fields.Subtype);
        String name = (application.length() > 0 ? "_" + application + "." : XmlPullParser.NO_NAMESPACE) + (protocol.length() > 0 ? "_" + protocol + "." : XmlPullParser.NO_NAMESPACE) + domain + ".";
        String loname = name.toLowerCase();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(getName() + ".registering service type: " + type + " as: " + name + (subtype.length() > 0 ? " subtype: " + subtype : XmlPullParser.NO_NAMESPACE));
        }
        if (!(this._serviceTypes.containsKey(loname) || application.toLowerCase().equals("dns-sd") || domain.toLowerCase().endsWith("in-addr.arpa") || domain.toLowerCase().endsWith("ip6.arpa"))) {
            typeAdded = this._serviceTypes.putIfAbsent(loname, new ServiceTypeEntry(name)) == null;
            if (typeAdded) {
                ServiceTypeListenerStatus[] list = (ServiceTypeListenerStatus[]) this._typeListeners.toArray(new ServiceTypeListenerStatus[this._typeListeners.size()]);
                event = new ServiceEventImpl(this, name, XmlPullParser.NO_NAMESPACE, null);
                for (ServiceTypeListenerStatus status : list) {
                    this._executor.submit(new C05142(status, event));
                }
            }
        }
        if (subtype.length() > 0) {
            ServiceTypeEntry subtypes = (ServiceTypeEntry) this._serviceTypes.get(loname);
            if (!(subtypes == null || subtypes.contains(subtype))) {
                synchronized (subtypes) {
                    if (!subtypes.contains(subtype)) {
                        typeAdded = true;
                        subtypes.add(subtype);
                        list = (ServiceTypeListenerStatus[]) this._typeListeners.toArray(new ServiceTypeListenerStatus[this._typeListeners.size()]);
                        event = new ServiceEventImpl(this, "_" + subtype + "._sub." + name, XmlPullParser.NO_NAMESPACE, null);
                        for (ServiceTypeListenerStatus status2 : list) {
                            this._executor.submit(new C05153(status2, event));
                        }
                    }
                }
            }
        }
        return typeAdded;
    }

    private boolean makeServiceNameUnique(ServiceInfoImpl info) {
        String originalQualifiedName = info.getKey();
        long now = System.currentTimeMillis();
        boolean collision;
        do {
            ServiceInfo selfService;
            collision = false;
            for (DNSEntry dnsEntry : getCache().getDNSEntryList(info.getKey())) {
                if (DNSRecordType.TYPE_SRV.equals(dnsEntry.getRecordType()) && !dnsEntry.isExpired(now)) {
                    Service s = (Service) dnsEntry;
                    if (s.getPort() != info.getPort() || !s.getServer().equals(this._localHost.getName())) {
                        if (logger.isLoggable(Level.FINER)) {
                            logger.finer("makeServiceNameUnique() JmDNS.makeServiceNameUnique srv collision:" + dnsEntry + " s.server=" + s.getServer() + " " + this._localHost.getName() + " equals:" + s.getServer().equals(this._localHost.getName()));
                        }
                        info.setName(incrementName(info.getName()));
                        collision = true;
                        selfService = (ServiceInfo) this._services.get(info.getKey());
                        if (!(selfService == null || selfService == info)) {
                            info.setName(incrementName(info.getName()));
                            collision = true;
                            continue;
                        }
                    }
                }
            }
            selfService = (ServiceInfo) this._services.get(info.getKey());
            info.setName(incrementName(info.getName()));
            collision = true;
            continue;
        } while (collision);
        return !originalQualifiedName.equals(info.getKey());
    }

    String incrementName(String name) {
        String aName = name;
        try {
            int l = aName.lastIndexOf(40);
            int r = aName.lastIndexOf(41);
            if (l < 0 || l >= r) {
                return aName + " (2)";
            }
            return aName.substring(0, l) + "(" + (Integer.parseInt(aName.substring(l + 1, r)) + 1) + ")";
        } catch (NumberFormatException e) {
            return aName + " (2)";
        }
    }

    public void addListener(DNSListener listener, DNSQuestion question) {
        long now = System.currentTimeMillis();
        this._listeners.add(listener);
        if (question != null) {
            for (DNSEntry dnsEntry : getCache().getDNSEntryList(question.getName().toLowerCase())) {
                if (question.answeredBy(dnsEntry) && !dnsEntry.isExpired(now)) {
                    listener.updateRecord(getCache(), now, dnsEntry);
                }
            }
        }
    }

    public void removeListener(DNSListener listener) {
        this._listeners.remove(listener);
    }

    public void renewServiceCollector(DNSRecord record) {
        ServiceInfo info = record.getServiceInfo();
        if (this._serviceCollectors.containsKey(info.getType().toLowerCase())) {
            startServiceResolver(info.getType());
        }
    }

    public void updateRecord(long now, DNSRecord rec, Operation operation) {
        Throwable th;
        synchronized (this._listeners) {
            try {
                List<DNSListener> listenerList = new ArrayList(this._listeners);
                try {
                    for (DNSListener listener : listenerList) {
                        listener.updateRecord(getCache(), now, rec);
                    }
                    if (DNSRecordType.TYPE_PTR.equals(rec.getRecordType())) {
                        List<ServiceListenerStatus> serviceListenerList;
                        ServiceEvent event = rec.getServiceEvent(this);
                        if (event.getInfo() == null || !event.getInfo().hasData()) {
                            ServiceInfo info = getServiceInfoFromCache(event.getType(), event.getName(), XmlPullParser.NO_NAMESPACE, false);
                            if (info.hasData()) {
                                event = new ServiceEventImpl(this, event.getType(), event.getName(), info);
                            }
                        }
                        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(event.getType().toLowerCase());
                        if (list != null) {
                            synchronized (list) {
                                serviceListenerList = new ArrayList(list);
                            }
                        } else {
                            serviceListenerList = Collections.emptyList();
                        }
                        if (logger.isLoggable(Level.FINEST)) {
                            logger.finest(getName() + ".updating record for event: " + event + " list " + serviceListenerList + " operation: " + operation);
                        }
                        if (!serviceListenerList.isEmpty()) {
                            ServiceEvent localEvent = event;
                            switch (C05197.$SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation[operation.ordinal()]) {
                                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                                    for (ServiceListenerStatus listener2 : serviceListenerList) {
                                        if (listener2.isSynchronous()) {
                                            listener2.serviceAdded(localEvent);
                                        } else {
                                            this._executor.submit(new C05164(listener2, localEvent));
                                        }
                                    }
                                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                                    for (ServiceListenerStatus listener22 : serviceListenerList) {
                                        if (listener22.isSynchronous()) {
                                            listener22.serviceRemoved(localEvent);
                                        } else {
                                            this._executor.submit(new C05175(listener22, localEvent));
                                        }
                                    }
                                default:
                            }
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    List<DNSListener> list2 = listenerList;
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                throw th;
            }
        }
    }

    void handleRecord(DNSRecord record, long now) {
        DNSRecord newRecord = record;
        Operation cacheOperation = Operation.Noop;
        boolean expired = newRecord.isExpired(now);
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(getName() + " handle response: " + newRecord);
        }
        if (!(newRecord.isServicesDiscoveryMetaQuery() || newRecord.isDomainDiscoveryQuery())) {
            boolean unique = newRecord.isUnique();
            DNSEntry cachedRecord = (DNSRecord) getCache().getDNSEntry(newRecord);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(getName() + " handle response cached record: " + cachedRecord);
            }
            if (unique) {
                for (DNSEntry entry : getCache().getDNSEntryList(newRecord.getKey())) {
                    if (newRecord.getRecordType().equals(entry.getRecordType()) && newRecord.getRecordClass().equals(entry.getRecordClass()) && entry != cachedRecord) {
                        ((DNSRecord) entry).setWillExpireSoon(now);
                    }
                }
            }
            if (cachedRecord != null) {
                if (expired) {
                    if (newRecord.getTTL() == 0) {
                        cacheOperation = Operation.Noop;
                        cachedRecord.setWillExpireSoon(now);
                    } else {
                        cacheOperation = Operation.Remove;
                        getCache().removeDNSEntry(cachedRecord);
                    }
                } else if (newRecord.sameValue(cachedRecord) && (newRecord.sameSubtype(cachedRecord) || newRecord.getSubtype().length() <= 0)) {
                    cachedRecord.resetTTL(newRecord);
                    DNSEntry newRecord2 = cachedRecord;
                } else if (newRecord.isSingleValued()) {
                    cacheOperation = Operation.Update;
                    getCache().replaceDNSEntry(newRecord, cachedRecord);
                } else {
                    cacheOperation = Operation.Add;
                    getCache().addDNSEntry(newRecord);
                }
            } else if (!expired) {
                cacheOperation = Operation.Add;
                getCache().addDNSEntry(newRecord);
            }
        }
        if (newRecord.getRecordType() == DNSRecordType.TYPE_PTR) {
            if (newRecord.isServicesDiscoveryMetaQuery()) {
                if (!expired) {
                    boolean typeAdded = registerServiceType(((Pointer) newRecord).getAlias());
                    return;
                }
                return;
            } else if ((false | registerServiceType(newRecord.getName())) && cacheOperation == Operation.Noop) {
                cacheOperation = Operation.RegisterServiceType;
            }
        }
        if (cacheOperation != Operation.Noop) {
            updateRecord(now, newRecord, cacheOperation);
        }
    }

    void handleResponse(DNSIncoming msg) throws IOException {
        long now = System.currentTimeMillis();
        boolean hostConflictDetected = false;
        boolean serviceConflictDetected = false;
        for (DNSRecord newRecord : msg.getAllAnswers()) {
            handleRecord(newRecord, now);
            if (DNSRecordType.TYPE_A.equals(newRecord.getRecordType()) || DNSRecordType.TYPE_AAAA.equals(newRecord.getRecordType())) {
                hostConflictDetected |= newRecord.handleResponse(this);
            } else {
                serviceConflictDetected |= newRecord.handleResponse(this);
            }
        }
        if (hostConflictDetected || serviceConflictDetected) {
            startProber();
        }
    }

    void handleQuery(DNSIncoming in, InetAddress addr, int port) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(getName() + ".handle query: " + in);
        }
        boolean conflictDetected = false;
        long expirationTime = System.currentTimeMillis() + 120;
        for (DNSRecord answer : in.getAllAnswers()) {
            conflictDetected |= answer.handleQuery(this, expirationTime);
        }
        ioLock();
        try {
            if (this._plannedAnswer != null) {
                this._plannedAnswer.append(in);
            } else {
                DNSIncoming plannedAnswer = in.clone();
                if (in.isTruncated()) {
                    this._plannedAnswer = plannedAnswer;
                }
                startResponder(plannedAnswer, port);
            }
            ioUnlock();
            long now = System.currentTimeMillis();
            for (DNSRecord answer2 : in.getAnswers()) {
                handleRecord(answer2, now);
            }
            if (conflictDetected) {
                startProber();
            }
        } catch (Throwable th) {
            ioUnlock();
        }
    }

    public void respondToQuery(DNSIncoming in) {
        ioLock();
        try {
            if (this._plannedAnswer == in) {
                this._plannedAnswer = null;
            }
            ioUnlock();
        } catch (Throwable th) {
            ioUnlock();
        }
    }

    public DNSOutgoing addAnswer(DNSIncoming in, InetAddress addr, int port, DNSOutgoing out, DNSRecord rec) throws IOException {
        DNSOutgoing newOut = out;
        if (newOut == null) {
            newOut = new DNSOutgoing(33792, false, in.getSenderUDPPayload());
        }
        try {
            newOut.addAnswer(in, rec);
            return newOut;
        } catch (IOException e) {
            newOut.setFlags(newOut.getFlags() | RCommandClient.MIN_CLIENT_PORT);
            newOut.setId(in.getId());
            send(newOut);
            newOut = new DNSOutgoing(33792, false, in.getSenderUDPPayload());
            newOut.addAnswer(in, rec);
            return newOut;
        }
    }

    public void send(DNSOutgoing out) throws IOException {
        if (!out.isEmpty()) {
            byte[] message = out.data();
            DatagramPacket packet = new DatagramPacket(message, message.length, this._group, DNSConstants.MDNS_PORT);
            if (logger.isLoggable(Level.FINEST)) {
                try {
                    DNSIncoming msg = new DNSIncoming(packet);
                    if (logger.isLoggable(Level.FINEST)) {
                        logger.finest("send(" + getName() + ") JmDNS out:" + msg.print(true));
                    }
                } catch (IOException e) {
                    logger.throwing(getClass().toString(), "send(" + getName() + ") - JmDNS can not parse what it sends!!!", e);
                }
            }
            MulticastSocket ms = this._socket;
            if (ms != null && !ms.isClosed()) {
                ms.send(packet);
            }
        }
    }

    public void purgeTimer() {
        Factory.getInstance().getStarter(getDns()).purgeTimer();
    }

    public void purgeStateTimer() {
        Factory.getInstance().getStarter(getDns()).purgeStateTimer();
    }

    public void cancelTimer() {
        Factory.getInstance().getStarter(getDns()).cancelTimer();
    }

    public void cancelStateTimer() {
        Factory.getInstance().getStarter(getDns()).cancelStateTimer();
    }

    public void startProber() {
        Factory.getInstance().getStarter(getDns()).startProber();
    }

    public void startAnnouncer() {
        Factory.getInstance().getStarter(getDns()).startAnnouncer();
    }

    public void startRenewer() {
        Factory.getInstance().getStarter(getDns()).startRenewer();
    }

    public void startCanceler() {
        Factory.getInstance().getStarter(getDns()).startCanceler();
    }

    public void startReaper() {
        Factory.getInstance().getStarter(getDns()).startReaper();
    }

    public void startServiceInfoResolver(ServiceInfoImpl info) {
        Factory.getInstance().getStarter(getDns()).startServiceInfoResolver(info);
    }

    public void startTypeResolver() {
        Factory.getInstance().getStarter(getDns()).startTypeResolver();
    }

    public void startServiceResolver(String type) {
        Factory.getInstance().getStarter(getDns()).startServiceResolver(type);
    }

    public void startResponder(DNSIncoming in, int port) {
        Factory.getInstance().getStarter(getDns()).startResponder(in, port);
    }

    public void recover() {
        logger.finer(getName() + "recover()");
        if (!isClosing() && !isClosed() && !isCanceling() && !isCanceled()) {
            synchronized (this._recoverLock) {
                if (cancelState()) {
                    logger.finer(getName() + "recover() thread " + Thread.currentThread().getName());
                    new C05186(getName() + ".recover()").start();
                }
            }
        }
    }

    void __recover() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(getName() + "recover() Cleanning up");
        }
        logger.warning("RECOVERING");
        purgeTimer();
        Collection<ServiceInfo> oldServiceInfos = new ArrayList(getServices().values());
        unregisterAllServices();
        disposeServiceCollectors();
        waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
        purgeStateTimer();
        closeMulticastSocket();
        getCache().clear();
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(getName() + "recover() All is clean");
        }
        if (isCanceled()) {
            for (ServiceInfo info : oldServiceInfos) {
                ((ServiceInfoImpl) info).recoverState();
            }
            recoverState();
            try {
                openMulticastSocket(getLocalHost());
                start(oldServiceInfos);
            } catch (Exception exception) {
                logger.log(Level.WARNING, getName() + "recover() Start services exception ", exception);
            }
            logger.log(Level.WARNING, getName() + "recover() We are back!");
            return;
        }
        logger.log(Level.WARNING, getName() + "recover() Could not recover we are Down!");
        if (getDelegate() != null) {
            getDelegate().cannotRecoverFromIOError(getDns(), oldServiceInfos);
        }
    }

    public void cleanCache() {
        long now = System.currentTimeMillis();
        for (DNSEntry entry : getCache().allValues()) {
            try {
                DNSRecord record = (DNSRecord) entry;
                if (record.isExpired(now)) {
                    updateRecord(now, record, Operation.Remove);
                    getCache().removeDNSEntry(record);
                } else if (record.isStale(now)) {
                    renewServiceCollector(record);
                }
            } catch (Exception exception) {
                logger.log(Level.SEVERE, getName() + ".Error while reaping records: " + entry, exception);
                logger.severe(toString());
            }
        }
    }

    public void close() {
        if (!isClosing()) {
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("Cancelling JmDNS: " + this);
            }
            if (closeState()) {
                logger.finer("Canceling the timer");
                cancelTimer();
                unregisterAllServices();
                disposeServiceCollectors();
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("Wait for JmDNS cancel: " + this);
                }
                waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
                logger.finer("Canceling the state timer");
                cancelStateTimer();
                this._executor.shutdown();
                closeMulticastSocket();
                if (this._shutdown != null) {
                    Runtime.getRuntime().removeShutdownHook(this._shutdown);
                }
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("JmDNS closed.");
                }
            }
            advanceState(null);
        }
    }

    @Deprecated
    public void printServices() {
        System.err.println(toString());
    }

    public String toString() {
        StringBuilder aLog = new StringBuilder(AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT);
        aLog.append("\t---- Local Host -----");
        aLog.append("\n\t");
        aLog.append(this._localHost);
        aLog.append("\n\t---- Services -----");
        for (String key : this._services.keySet()) {
            aLog.append("\n\t\tService: ");
            aLog.append(key);
            aLog.append(": ");
            aLog.append(this._services.get(key));
        }
        aLog.append("\n");
        aLog.append("\t---- Types ----");
        for (String key2 : this._serviceTypes.keySet()) {
            ServiceTypeEntry subtypes = (ServiceTypeEntry) this._serviceTypes.get(key2);
            aLog.append("\n\t\tType: ");
            aLog.append(subtypes.getType());
            aLog.append(": ");
            if (subtypes.isEmpty()) {
                subtypes = "no subtypes";
            }
            aLog.append(subtypes);
        }
        aLog.append("\n");
        aLog.append(this._cache.toString());
        aLog.append("\n");
        aLog.append("\t---- Service Collectors ----");
        for (String key22 : this._serviceCollectors.keySet()) {
            aLog.append("\n\t\tService Collector: ");
            aLog.append(key22);
            aLog.append(": ");
            aLog.append(this._serviceCollectors.get(key22));
        }
        aLog.append("\n");
        aLog.append("\t---- Service Listeners ----");
        for (String key222 : this._serviceListeners.keySet()) {
            aLog.append("\n\t\tService Listener: ");
            aLog.append(key222);
            aLog.append(": ");
            aLog.append(this._serviceListeners.get(key222));
        }
        return aLog.toString();
    }

    public ServiceInfo[] list(String type) {
        return list(type, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo[] list(String type, long timeout) {
        cleanCache();
        String loType = type.toLowerCase();
        if (isCanceling() || isCanceled()) {
            return new ServiceInfo[0];
        }
        ServiceCollector collector = (ServiceCollector) this._serviceCollectors.get(loType);
        if (collector == null) {
            collector = (ServiceCollector) this._serviceCollectors.get(loType);
            if (this._serviceCollectors.putIfAbsent(loType, new ServiceCollector(type)) == null) {
                addServiceListener(type, collector, true);
            }
        }
        if (logger.isLoggable(Level.FINER)) {
            logger.finer(getName() + ".collector: " + collector);
        }
        if (collector != null) {
            return collector.list(timeout);
        }
        return new ServiceInfo[0];
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type) {
        return listBySubtype(type, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type, long timeout) {
        Map<String, List<ServiceInfo>> map = new HashMap(5);
        for (ServiceInfo info : list(type, timeout)) {
            String subtype = info.getSubtype().toLowerCase();
            if (!map.containsKey(subtype)) {
                map.put(subtype, new ArrayList(10));
            }
            ((List) map.get(subtype)).add(info);
        }
        Map<String, ServiceInfo[]> result = new HashMap(map.size());
        for (String subtype2 : map.keySet()) {
            List<ServiceInfo> infoForSubType = (List) map.get(subtype2);
            result.put(subtype2, infoForSubType.toArray(new ServiceInfo[infoForSubType.size()]));
        }
        return result;
    }

    private void disposeServiceCollectors() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("disposeServiceCollectors()");
        }
        for (String type : this._serviceCollectors.keySet()) {
            ServiceCollector collector = (ServiceCollector) this._serviceCollectors.get(type);
            if (collector != null) {
                removeServiceListener(type, collector);
                this._serviceCollectors.remove(type, collector);
            }
        }
    }

    static String toUnqualifiedName(String type, String qualifiedName) {
        String loType = type.toLowerCase();
        String loQualifiedName = qualifiedName.toLowerCase();
        if (!loQualifiedName.endsWith(loType) || loQualifiedName.equals(loType)) {
            return qualifiedName;
        }
        return qualifiedName.substring(0, (qualifiedName.length() - type.length()) - 1);
    }

    public Map<String, ServiceInfo> getServices() {
        return this._services;
    }

    public void setLastThrottleIncrement(long lastThrottleIncrement) {
        this._lastThrottleIncrement = lastThrottleIncrement;
    }

    public long getLastThrottleIncrement() {
        return this._lastThrottleIncrement;
    }

    public void setThrottle(int throttle) {
        this._throttle = throttle;
    }

    public int getThrottle() {
        return this._throttle;
    }

    public static Random getRandom() {
        return _random;
    }

    public void ioLock() {
        this._ioLock.lock();
    }

    public void ioUnlock() {
        this._ioLock.unlock();
    }

    public void setPlannedAnswer(DNSIncoming plannedAnswer) {
        this._plannedAnswer = plannedAnswer;
    }

    public DNSIncoming getPlannedAnswer() {
        return this._plannedAnswer;
    }

    void setLocalHost(HostInfo localHost) {
        this._localHost = localHost;
    }

    public Map<String, ServiceTypeEntry> getServiceTypes() {
        return this._serviceTypes;
    }

    public MulticastSocket getSocket() {
        return this._socket;
    }

    public InetAddress getGroup() {
        return this._group;
    }

    public Delegate getDelegate() {
        return this._delegate;
    }

    public Delegate setDelegate(Delegate delegate) {
        Delegate previous = this._delegate;
        this._delegate = delegate;
        return previous;
    }
}
