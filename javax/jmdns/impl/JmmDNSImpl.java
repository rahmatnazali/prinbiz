package javax.jmdns.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.JmmDNS;
import javax.jmdns.NetworkTopologyDiscovery;
import javax.jmdns.NetworkTopologyDiscovery.Factory;
import javax.jmdns.NetworkTopologyEvent;
import javax.jmdns.NetworkTopologyListener;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.ServiceInfoImpl.Delegate;
import javax.jmdns.impl.constants.DNSConstants;

public class JmmDNSImpl implements JmmDNS, NetworkTopologyListener, Delegate {
    private static Logger logger;
    private final ExecutorService _ListenerExecutor;
    private final ExecutorService _jmDNSExecutor;
    private final ConcurrentMap<InetAddress, JmDNS> _knownMDNS;
    private final Set<NetworkTopologyListener> _networkListeners;
    private final ConcurrentMap<String, ServiceInfo> _services;
    private final Timer _timer;

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.1 */
    class C05201 implements Runnable {
        final /* synthetic */ JmDNS val$mDNS;

        C05201(JmDNS jmDNS) {
            this.val$mDNS = jmDNS;
        }

        public void run() {
            try {
                this.val$mDNS.close();
            } catch (IOException e) {
            }
        }
    }

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.2 */
    class C05212 implements Runnable {
        final /* synthetic */ JmDNS val$mDNS;
        final /* synthetic */ String val$name;
        final /* synthetic */ boolean val$persistent;
        final /* synthetic */ Set val$result;
        final /* synthetic */ long val$timeout;
        final /* synthetic */ String val$type;

        C05212(Set set, JmDNS jmDNS, String str, String str2, boolean z, long j) {
            this.val$result = set;
            this.val$mDNS = jmDNS;
            this.val$type = str;
            this.val$name = str2;
            this.val$persistent = z;
            this.val$timeout = j;
        }

        public void run() {
            this.val$result.add(this.val$mDNS.getServiceInfo(this.val$type, this.val$name, this.val$persistent, this.val$timeout));
        }
    }

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.3 */
    class C05223 implements Runnable {
        final /* synthetic */ JmDNS val$mDNS;
        final /* synthetic */ String val$name;
        final /* synthetic */ boolean val$persistent;
        final /* synthetic */ long val$timeout;
        final /* synthetic */ String val$type;

        C05223(JmDNS jmDNS, String str, String str2, boolean z, long j) {
            this.val$mDNS = jmDNS;
            this.val$type = str;
            this.val$name = str2;
            this.val$persistent = z;
            this.val$timeout = j;
        }

        public void run() {
            this.val$mDNS.requestServiceInfo(this.val$type, this.val$name, this.val$persistent, this.val$timeout);
        }
    }

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.4 */
    class C05234 implements Runnable {
        final /* synthetic */ JmDNS val$mDNS;
        final /* synthetic */ Set val$result;
        final /* synthetic */ long val$timeout;
        final /* synthetic */ String val$type;

        C05234(Set set, JmDNS jmDNS, String str, long j) {
            this.val$result = set;
            this.val$mDNS = jmDNS;
            this.val$type = str;
            this.val$timeout = j;
        }

        public void run() {
            this.val$result.addAll(Arrays.asList(this.val$mDNS.list(this.val$type, this.val$timeout)));
        }
    }

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.5 */
    class C05245 implements Runnable {
        final /* synthetic */ NetworkTopologyEvent val$jmdnsEvent;
        final /* synthetic */ NetworkTopologyListener val$listener;

        C05245(NetworkTopologyListener networkTopologyListener, NetworkTopologyEvent networkTopologyEvent) {
            this.val$listener = networkTopologyListener;
            this.val$jmdnsEvent = networkTopologyEvent;
        }

        public void run() {
            this.val$listener.inetAddressAdded(this.val$jmdnsEvent);
        }
    }

    /* renamed from: javax.jmdns.impl.JmmDNSImpl.6 */
    class C05256 implements Runnable {
        final /* synthetic */ NetworkTopologyEvent val$jmdnsEvent;
        final /* synthetic */ NetworkTopologyListener val$listener;

        C05256(NetworkTopologyListener networkTopologyListener, NetworkTopologyEvent networkTopologyEvent) {
            this.val$listener = networkTopologyListener;
            this.val$jmdnsEvent = networkTopologyEvent;
        }

        public void run() {
            this.val$listener.inetAddressRemoved(this.val$jmdnsEvent);
        }
    }

    static class NetworkChecker extends TimerTask {
        private static Logger logger1;
        private Set<InetAddress> _knownAddresses;
        private final NetworkTopologyListener _mmDNS;
        private final NetworkTopologyDiscovery _topology;

        static {
            logger1 = Logger.getLogger(NetworkChecker.class.getName());
        }

        public NetworkChecker(NetworkTopologyListener mmDNS, NetworkTopologyDiscovery topology) {
            this._mmDNS = mmDNS;
            this._topology = topology;
            this._knownAddresses = Collections.synchronizedSet(new HashSet());
        }

        public void start(Timer timer) {
            timer.schedule(this, 0, 10000);
        }

        public void run() {
            try {
                InetAddress[] curentAddresses = this._topology.getInetAddresses();
                Set<InetAddress> current = new HashSet(curentAddresses.length);
                for (InetAddress address : curentAddresses) {
                    current.add(address);
                    if (!this._knownAddresses.contains(address)) {
                        this._mmDNS.inetAddressAdded(new NetworkTopologyEventImpl(this._mmDNS, address));
                    }
                }
                for (InetAddress address2 : this._knownAddresses) {
                    if (!current.contains(address2)) {
                        this._mmDNS.inetAddressRemoved(new NetworkTopologyEventImpl(this._mmDNS, address2));
                    }
                }
                this._knownAddresses = current;
            } catch (Exception e) {
                logger1.warning("Unexpected unhandled exception: " + e);
            }
        }
    }

    static {
        logger = Logger.getLogger(JmmDNSImpl.class.getName());
    }

    public JmmDNSImpl() {
        this._networkListeners = Collections.synchronizedSet(new HashSet());
        this._knownMDNS = new ConcurrentHashMap();
        this._services = new ConcurrentHashMap(20);
        this._ListenerExecutor = Executors.newSingleThreadExecutor();
        this._jmDNSExecutor = Executors.newCachedThreadPool();
        this._timer = new Timer("Multihommed mDNS.Timer", true);
        new NetworkChecker(this, Factory.getInstance()).start(this._timer);
    }

    public void close() throws IOException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("Cancelling JmmDNS: " + this);
        }
        this._timer.cancel();
        this._ListenerExecutor.shutdown();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            executor.submit(new C05201(mDNS));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(DNSConstants.CLOSE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.WARNING, "Exception ", exception);
        }
        this._knownMDNS.clear();
    }

    public String[] getNames() {
        Set<String> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getName());
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public String[] getHostNames() {
        Set<String> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getHostName());
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public InetAddress[] getInterfaces() throws IOException {
        Set<InetAddress> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getInterface());
        }
        return (InetAddress[]) result.toArray(new InetAddress[result.size()]);
    }

    public ServiceInfo[] getServiceInfos(String type, String name) {
        return getServiceInfos(type, name, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, long timeout) {
        return getServiceInfos(type, name, false, timeout);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, boolean persistent) {
        return getServiceInfos(type, name, persistent, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, boolean persistent, long timeout) {
        Set<ServiceInfo> result = Collections.synchronizedSet(new HashSet(this._knownMDNS.size()));
        ExecutorService executor = Executors.newCachedThreadPool();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            executor.submit(new C05212(result, mDNS, type, name, persistent, timeout));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.WARNING, "Exception ", exception);
        }
        return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
    }

    public void requestServiceInfo(String type, String name) {
        requestServiceInfo(type, name, false, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void requestServiceInfo(String type, String name, boolean persistent) {
        requestServiceInfo(type, name, persistent, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public void requestServiceInfo(String type, String name, long timeout) {
        requestServiceInfo(type, name, false, timeout);
    }

    public void requestServiceInfo(String type, String name, boolean persistent, long timeout) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            this._jmDNSExecutor.submit(new C05223(mDNS, type, name, persistent, timeout));
        }
    }

    public void addServiceTypeListener(ServiceTypeListener listener) throws IOException {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.addServiceTypeListener(listener);
        }
    }

    public void removeServiceTypeListener(ServiceTypeListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.removeServiceTypeListener(listener);
        }
    }

    public void addServiceListener(String type, ServiceListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.addServiceListener(type, listener);
        }
    }

    public void removeServiceListener(String type, ServiceListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.removeServiceListener(type, listener);
        }
    }

    public void textValueUpdated(ServiceInfo target, byte[] value) {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                ServiceInfo info = (ServiceInfo) ((JmDNSImpl) mDNS).getServices().get(target.getQualifiedName());
                if (info != null) {
                    info.setText(value);
                } else {
                    logger.warning("We have a mDNS that does not know about the service info being updated.");
                }
            }
        }
    }

    public void registerService(ServiceInfo info) throws IOException {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.registerService(info.clone());
            }
            ((ServiceInfoImpl) info).setDelegate(this);
            this._services.put(info.getQualifiedName(), info);
        }
    }

    public void unregisterService(ServiceInfo info) {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.unregisterService(info);
            }
            ((ServiceInfoImpl) info).setDelegate(null);
            this._services.remove(info.getQualifiedName());
        }
    }

    public void unregisterAllServices() {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.unregisterAllServices();
            }
            this._services.clear();
        }
    }

    public void registerServiceType(String type) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.registerServiceType(type);
        }
    }

    public ServiceInfo[] list(String type) {
        return list(type, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public ServiceInfo[] list(String type, long timeout) {
        Set<ServiceInfo> result = Collections.synchronizedSet(new HashSet(this._knownMDNS.size() * 5));
        ExecutorService executor = Executors.newCachedThreadPool();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            executor.submit(new C05234(result, mDNS, type, timeout));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.WARNING, "Exception ", exception);
        }
        return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type) {
        return listBySubtype(type, DNSConstants.SERVICE_INFO_TIMEOUT);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type, long timeout) {
        Map<String, List<ServiceInfo>> map = new HashMap(5);
        for (ServiceInfo info : list(type, timeout)) {
            String subtype = info.getSubtype();
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

    public void addNetworkTopologyListener(NetworkTopologyListener listener) {
        this._networkListeners.add(listener);
    }

    public void removeNetworkTopologyListener(NetworkTopologyListener listener) {
        this._networkListeners.remove(listener);
    }

    public NetworkTopologyListener[] networkListeners() {
        return (NetworkTopologyListener[]) this._networkListeners.toArray(new NetworkTopologyListener[this._networkListeners.size()]);
    }

    public void inetAddressAdded(NetworkTopologyEvent event) {
        InetAddress address = event.getInetAddress();
        try {
            synchronized (this) {
                if (!this._knownMDNS.containsKey(address)) {
                    this._knownMDNS.put(address, JmDNS.create(address));
                    NetworkTopologyEvent jmdnsEvent = new NetworkTopologyEventImpl((JmDNS) this._knownMDNS.get(address), address);
                    for (NetworkTopologyListener listener : networkListeners()) {
                        this._ListenerExecutor.submit(new C05245(listener, jmdnsEvent));
                    }
                }
            }
        } catch (Exception e) {
            logger.warning("Unexpected unhandled exception: " + e);
        }
    }

    public void inetAddressRemoved(NetworkTopologyEvent event) {
        InetAddress address = event.getInetAddress();
        try {
            synchronized (this) {
                if (this._knownMDNS.containsKey(address)) {
                    JmDNS mDNS = (JmDNS) this._knownMDNS.remove(address);
                    mDNS.close();
                    NetworkTopologyEvent jmdnsEvent = new NetworkTopologyEventImpl(mDNS, address);
                    for (NetworkTopologyListener listener : networkListeners()) {
                        this._ListenerExecutor.submit(new C05256(listener, jmdnsEvent));
                    }
                }
            }
        } catch (Exception e) {
            logger.warning("Unexpected unhandled exception: " + e);
        }
    }
}
