package javax.jmdns.impl;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.EventListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

public class ListenerStatus<T extends EventListener> {
    public static final boolean ASYNCHONEOUS = false;
    public static final boolean SYNCHONEOUS = true;
    private final T _listener;
    private final boolean _synch;

    public static class ServiceListenerStatus extends ListenerStatus<ServiceListener> {
        private static Logger logger;
        private final ConcurrentMap<String, ServiceInfo> _addedServices;

        static {
            logger = Logger.getLogger(ServiceListenerStatus.class.getName());
        }

        public ServiceListenerStatus(ServiceListener listener, boolean synch) {
            super(listener, synch);
            this._addedServices = new ConcurrentHashMap(32);
        }

        void serviceAdded(ServiceEvent event) {
            if (this._addedServices.putIfAbsent(event.getName() + "." + event.getType(), event.getInfo().clone()) == null) {
                ((ServiceListener) getListener()).serviceAdded(event);
                ServiceInfo info = event.getInfo();
                if (info != null && info.hasData()) {
                    ((ServiceListener) getListener()).serviceResolved(event);
                    return;
                }
                return;
            }
            logger.finer("Service Added called for a service already added: " + event);
        }

        void serviceRemoved(ServiceEvent event) {
            String qualifiedName = event.getName() + "." + event.getType();
            if (this._addedServices.remove(qualifiedName, this._addedServices.get(qualifiedName))) {
                ((ServiceListener) getListener()).serviceRemoved(event);
            } else {
                logger.finer("Service Removed called for a service already removed: " + event);
            }
        }

        synchronized void serviceResolved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            if (info == null || !info.hasData()) {
                logger.warning("Service Resolved called for an unresolved event: " + event);
            } else {
                String qualifiedName = event.getName() + "." + event.getType();
                ServiceInfo previousServiceInfo = (ServiceInfo) this._addedServices.get(qualifiedName);
                if (_sameInfo(info, previousServiceInfo)) {
                    logger.finer("Service Resolved called for a service already resolved: " + event);
                } else if (previousServiceInfo == null) {
                    if (this._addedServices.putIfAbsent(qualifiedName, info.clone()) == null) {
                        ((ServiceListener) getListener()).serviceResolved(event);
                    }
                } else if (this._addedServices.replace(qualifiedName, previousServiceInfo, info.clone())) {
                    ((ServiceListener) getListener()).serviceResolved(event);
                }
            }
        }

        private static final boolean _sameInfo(ServiceInfo info, ServiceInfo lastInfo) {
            if (info == null || lastInfo == null || !info.equals(lastInfo)) {
                return ListenerStatus.ASYNCHONEOUS;
            }
            byte[] text = info.getTextBytes();
            byte[] lastText = lastInfo.getTextBytes();
            if (text.length != lastText.length) {
                return ListenerStatus.ASYNCHONEOUS;
            }
            for (int i = 0; i < text.length; i++) {
                if (text[i] != lastText[i]) {
                    return ListenerStatus.ASYNCHONEOUS;
                }
            }
            return ListenerStatus.SYNCHONEOUS;
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT);
            aLog.append("[Status for ");
            aLog.append(((ServiceListener) getListener()).toString());
            if (this._addedServices.isEmpty()) {
                aLog.append(" no type event ");
            } else {
                aLog.append(" (");
                for (String service : this._addedServices.keySet()) {
                    aLog.append(service + ", ");
                }
                aLog.append(") ");
            }
            aLog.append("]");
            return aLog.toString();
        }
    }

    public static class ServiceTypeListenerStatus extends ListenerStatus<ServiceTypeListener> {
        private static Logger logger;
        private final ConcurrentMap<String, String> _addedTypes;

        static {
            logger = Logger.getLogger(ServiceTypeListenerStatus.class.getName());
        }

        public ServiceTypeListenerStatus(ServiceTypeListener listener, boolean synch) {
            super(listener, synch);
            this._addedTypes = new ConcurrentHashMap(32);
        }

        void serviceTypeAdded(ServiceEvent event) {
            if (this._addedTypes.putIfAbsent(event.getType(), event.getType()) == null) {
                ((ServiceTypeListener) getListener()).serviceTypeAdded(event);
            } else {
                logger.finest("Service Type Added called for a service type already added: " + event);
            }
        }

        void subTypeForServiceTypeAdded(ServiceEvent event) {
            if (this._addedTypes.putIfAbsent(event.getType(), event.getType()) == null) {
                ((ServiceTypeListener) getListener()).subTypeForServiceTypeAdded(event);
            } else {
                logger.finest("Service Sub Type Added called for a service sub type already added: " + event);
            }
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(AccessibilityNodeInfoCompat.ACTION_PREVIOUS_HTML_ELEMENT);
            aLog.append("[Status for ");
            aLog.append(((ServiceTypeListener) getListener()).toString());
            if (this._addedTypes.isEmpty()) {
                aLog.append(" no type event ");
            } else {
                aLog.append(" (");
                for (String type : this._addedTypes.keySet()) {
                    aLog.append(type + ", ");
                }
                aLog.append(") ");
            }
            aLog.append("]");
            return aLog.toString();
        }
    }

    public ListenerStatus(T listener, boolean synch) {
        this._listener = listener;
        this._synch = synch;
    }

    public T getListener() {
        return this._listener;
    }

    public boolean isSynchronous() {
        return this._synch;
    }

    public int hashCode() {
        return getListener().hashCode();
    }

    public boolean equals(Object obj) {
        return ((obj instanceof ListenerStatus) && getListener().equals(((ListenerStatus) obj).getListener())) ? SYNCHONEOUS : ASYNCHONEOUS;
    }

    public String toString() {
        return "[Status for " + getListener().toString() + "]";
    }
}
