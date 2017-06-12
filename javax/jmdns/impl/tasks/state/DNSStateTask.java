package javax.jmdns.impl.tasks.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSStatefulObject;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

public abstract class DNSStateTask extends DNSTask {
    private static int _defaultTTL;
    static Logger logger1;
    private DNSState _taskState;
    private final int _ttl;

    protected abstract void advanceTask();

    protected abstract DNSOutgoing buildOutgoingForDNS(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract DNSOutgoing buildOutgoingForInfo(ServiceInfoImpl serviceInfoImpl, DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract boolean checkRunCondition();

    protected abstract DNSOutgoing createOugoing();

    public abstract String getTaskDescription();

    protected abstract void recoverTask(Throwable th);

    static {
        logger1 = Logger.getLogger(DNSStateTask.class.getName());
        _defaultTTL = DNSConstants.DNS_TTL;
    }

    public static int defaultTTL() {
        return _defaultTTL;
    }

    public static void setDefaultTTL(int value) {
        _defaultTTL = value;
    }

    public DNSStateTask(JmDNSImpl jmDNSImpl, int ttl) {
        super(jmDNSImpl);
        this._taskState = null;
        this._ttl = ttl;
    }

    public int getTTL() {
        return this._ttl;
    }

    protected void associate(DNSState state) {
        synchronized (getDns()) {
            getDns().associateWithTask(this, state);
        }
        for (ServiceInfo serviceInfo : getDns().getServices().values()) {
            ((ServiceInfoImpl) serviceInfo).associateWithTask(this, state);
        }
    }

    protected void removeAssociation() {
        synchronized (getDns()) {
            getDns().removeAssociationWithTask(this);
        }
        for (ServiceInfo serviceInfo : getDns().getServices().values()) {
            ((ServiceInfoImpl) serviceInfo).removeAssociationWithTask(this);
        }
    }

    public void run() {
        DNSOutgoing out = createOugoing();
        try {
            if (checkRunCondition()) {
                List<DNSStatefulObject> stateObjects = new ArrayList();
                synchronized (getDns()) {
                    if (getDns().isAssociatedWithTask(this, getTaskState())) {
                        logger1.finer(getName() + ".run() JmDNS " + getTaskDescription() + " " + getDns().getName());
                        stateObjects.add(getDns());
                        out = buildOutgoingForDNS(out);
                    }
                }
                for (ServiceInfo serviceInfo : getDns().getServices().values()) {
                    ServiceInfoImpl info = (ServiceInfoImpl) serviceInfo;
                    synchronized (info) {
                        if (info.isAssociatedWithTask(this, getTaskState())) {
                            logger1.fine(getName() + ".run() JmDNS " + getTaskDescription() + " " + info.getQualifiedName());
                            stateObjects.add(info);
                            out = buildOutgoingForInfo(info, out);
                        }
                    }
                }
                if (out.isEmpty()) {
                    advanceObjectsState(stateObjects);
                    cancel();
                    return;
                }
                logger1.finer(getName() + ".run() JmDNS " + getTaskDescription() + " #" + getTaskState());
                getDns().send(out);
                advanceObjectsState(stateObjects);
                advanceTask();
                return;
            }
            cancel();
        } catch (Throwable e) {
            logger1.log(Level.WARNING, getName() + ".run() exception ", e);
            recoverTask(e);
        }
    }

    protected void advanceObjectsState(List<DNSStatefulObject> list) {
        if (list != null) {
            for (DNSStatefulObject object : list) {
                synchronized (object) {
                    object.advanceState(this);
                }
            }
        }
    }

    protected DNSState getTaskState() {
        return this._taskState;
    }

    protected void setTaskState(DNSState taskState) {
        this._taskState = taskState;
    }
}
