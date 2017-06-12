package javax.jmdns.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.NetworkTopologyDiscovery.Factory;
import javax.jmdns.impl.DNSRecord.Address;
import javax.jmdns.impl.DNSRecord.IPv4Address;
import javax.jmdns.impl.DNSRecord.IPv6Address;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSStatefulObject.DefaultImplementation;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class HostInfo implements DNSStatefulObject {
    private static Logger logger;
    protected InetAddress _address;
    protected NetworkInterface _interfaze;
    protected String _name;
    private final HostInfoState _state;
    private int hostNameCount;

    /* renamed from: javax.jmdns.impl.HostInfo.1 */
    static /* synthetic */ class C05121 {
        static final /* synthetic */ int[] $SwitchMap$javax$jmdns$impl$constants$DNSRecordType;

        static {
            $SwitchMap$javax$jmdns$impl$constants$DNSRecordType = new int[DNSRecordType.values().length];
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_A.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_A6.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$jmdns$impl$constants$DNSRecordType[DNSRecordType.TYPE_AAAA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static final class HostInfoState extends DefaultImplementation {
        private static final long serialVersionUID = -8191476803620402088L;

        public HostInfoState(JmDNSImpl dns) {
            setDns(dns);
        }
    }

    static {
        logger = Logger.getLogger(HostInfo.class.getName());
    }

    public static HostInfo newHostInfo(InetAddress address, JmDNSImpl dns, String jmdnsName) {
        String aName = XmlPullParser.NO_NAMESPACE;
        InetAddress addr = address;
        if (addr == null) {
            try {
                String ip = System.getProperty("net.mdns.interface");
                if (ip != null) {
                    addr = InetAddress.getByName(ip);
                } else {
                    addr = InetAddress.getLocalHost();
                    if (addr.isLoopbackAddress()) {
                        InetAddress[] addresses = Factory.getInstance().getInetAddresses();
                        if (addresses.length > 0) {
                            addr = addresses[0];
                        }
                    }
                }
                aName = addr.getHostName();
                if (addr.isLoopbackAddress()) {
                    logger.warning("Could not find any address beside the loopback.");
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not intialize the host network interface on " + address + "because of an error: " + e.getMessage(), e);
                addr = loopbackAddress();
                aName = (jmdnsName == null || jmdnsName.length() <= 0) ? "computer" : jmdnsName;
            }
        } else {
            aName = addr.getHostName();
        }
        if (aName.contains("in-addr.arpa") || aName.equals(addr.getHostAddress())) {
            aName = (jmdnsName == null || jmdnsName.length() <= 0) ? addr.getHostAddress() : jmdnsName;
        }
        return new HostInfo(addr, aName.replace('.', '-') + ".local.", dns);
    }

    private static InetAddress loopbackAddress() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
        }
        return inetAddress;
    }

    private HostInfo(InetAddress address, String name, JmDNSImpl dns) {
        this._state = new HostInfoState(dns);
        this._address = address;
        this._name = name;
        if (address != null) {
            try {
                this._interfaze = NetworkInterface.getByInetAddress(address);
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "LocalHostInfo() exception ", exception);
            }
        }
    }

    public String getName() {
        return this._name;
    }

    public InetAddress getInetAddress() {
        return this._address;
    }

    Inet4Address getInet4Address() {
        if (getInetAddress() instanceof Inet4Address) {
            return (Inet4Address) this._address;
        }
        return null;
    }

    Inet6Address getInet6Address() {
        if (getInetAddress() instanceof Inet6Address) {
            return (Inet6Address) this._address;
        }
        return null;
    }

    public NetworkInterface getInterface() {
        return this._interfaze;
    }

    public boolean conflictWithRecord(Address record) {
        Address hostAddress = getDNSAddressRecord(record.getRecordType(), record.isUnique(), DNSConstants.DNS_TTL);
        if (hostAddress == null || !hostAddress.sameType(record) || !hostAddress.sameName(record) || hostAddress.sameValue(record)) {
            return false;
        }
        return true;
    }

    synchronized String incrementHostName() {
        this.hostNameCount++;
        int plocal = this._name.indexOf(".local.");
        int punder = this._name.lastIndexOf(45);
        StringBuilder stringBuilder = new StringBuilder();
        String str = this._name;
        if (punder != -1) {
            plocal = punder;
        }
        this._name = stringBuilder.append(str.substring(0, plocal)).append("-").append(this.hostNameCount).append(".local.").toString();
        return this._name;
    }

    boolean shouldIgnorePacket(DatagramPacket packet) {
        boolean result = false;
        if (getInetAddress() == null) {
            return false;
        }
        InetAddress from = packet.getAddress();
        if (from == null) {
            return false;
        }
        if (from.isLinkLocalAddress() && !getInetAddress().isLinkLocalAddress()) {
            result = true;
        }
        if (!from.isLoopbackAddress() || getInetAddress().isLoopbackAddress()) {
            return result;
        }
        return true;
    }

    Address getDNSAddressRecord(DNSRecordType type, boolean unique, int ttl) {
        switch (C05121.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return getDNS4AddressRecord(unique, ttl);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return getDNS6AddressRecord(unique, ttl);
            default:
                return null;
        }
    }

    private Address getDNS4AddressRecord(boolean unique, int ttl) {
        if (!(getInetAddress() instanceof Inet4Address) && (!(getInetAddress() instanceof Inet6Address) || !((Inet6Address) getInetAddress()).isIPv4CompatibleAddress())) {
            return null;
        }
        return new IPv4Address(getName(), DNSRecordClass.CLASS_IN, unique, ttl, getInetAddress());
    }

    private Address getDNS6AddressRecord(boolean unique, int ttl) {
        if (!(getInetAddress() instanceof Inet6Address)) {
            return null;
        }
        return new IPv6Address(getName(), DNSRecordClass.CLASS_IN, unique, ttl, getInetAddress());
    }

    Pointer getDNSReverseAddressRecord(DNSRecordType type, boolean unique, int ttl) {
        switch (C05121.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return getDNS4ReverseAddressRecord(unique, ttl);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return getDNS6ReverseAddressRecord(unique, ttl);
            default:
                return null;
        }
    }

    private Pointer getDNS4ReverseAddressRecord(boolean unique, int ttl) {
        if (getInetAddress() instanceof Inet4Address) {
            return new Pointer(getInetAddress().getHostAddress() + ".in-addr.arpa.", DNSRecordClass.CLASS_IN, unique, ttl, getName());
        } else if (!(getInetAddress() instanceof Inet6Address) || !((Inet6Address) getInetAddress()).isIPv4CompatibleAddress()) {
            return null;
        } else {
            byte[] rawAddress = getInetAddress().getAddress();
            return new Pointer(((rawAddress[12] & TelnetOption.MAX_OPTION_VALUE) + "." + (rawAddress[13] & TelnetOption.MAX_OPTION_VALUE) + "." + (rawAddress[14] & TelnetOption.MAX_OPTION_VALUE) + "." + (rawAddress[15] & TelnetOption.MAX_OPTION_VALUE)) + ".in-addr.arpa.", DNSRecordClass.CLASS_IN, unique, ttl, getName());
        }
    }

    private Pointer getDNS6ReverseAddressRecord(boolean unique, int ttl) {
        if (!(getInetAddress() instanceof Inet6Address)) {
            return null;
        }
        return new Pointer(getInetAddress().getHostAddress() + ".ip6.arpa.", DNSRecordClass.CLASS_IN, unique, ttl, getName());
    }

    public String toString() {
        StringBuilder buf = new StringBuilder(Util.DEFAULT_COPY_BUFFER_SIZE);
        buf.append("local host info[");
        buf.append(getName() != null ? getName() : "no name");
        buf.append(", ");
        buf.append(getInterface() != null ? getInterface().getDisplayName() : "???");
        buf.append(":");
        buf.append(getInetAddress() != null ? getInetAddress().getHostAddress() : "no address");
        buf.append(", ");
        buf.append(this._state);
        buf.append("]");
        return buf.toString();
    }

    public Collection<DNSRecord> answers(boolean unique, int ttl) {
        List<DNSRecord> list = new ArrayList();
        DNSRecord answer = getDNS4AddressRecord(unique, ttl);
        if (answer != null) {
            list.add(answer);
        }
        answer = getDNS6AddressRecord(unique, ttl);
        if (answer != null) {
            list.add(answer);
        }
        return list;
    }

    public JmDNSImpl getDns() {
        return this._state.getDns();
    }

    public boolean advanceState(DNSTask task) {
        return this._state.advanceState(task);
    }

    public void removeAssociationWithTask(DNSTask task) {
        this._state.removeAssociationWithTask(task);
    }

    public boolean revertState() {
        return this._state.revertState();
    }

    public void associateWithTask(DNSTask task, DNSState state) {
        this._state.associateWithTask(task, state);
    }

    public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
        return this._state.isAssociatedWithTask(task, state);
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
        if (this._address == null) {
            return true;
        }
        return this._state.waitForCanceled(timeout);
    }
}
