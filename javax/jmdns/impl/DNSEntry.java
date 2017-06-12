package javax.jmdns.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.jmdns.ServiceInfo.Fields;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import org.apache.commons.net.nntp.NNTPReply;
import org.xmlpull.v1.XmlPullParser;

public abstract class DNSEntry {
    private final DNSRecordClass _dnsClass;
    private final String _key;
    private final String _name;
    final Map<Fields, String> _qualifiedNameMap;
    private final DNSRecordType _recordType;
    private final String _type;
    private final boolean _unique;

    public abstract boolean isExpired(long j);

    public abstract boolean isStale(long j);

    DNSEntry(String name, DNSRecordType type, DNSRecordClass recordClass, boolean unique) {
        this._name = name;
        this._recordType = type;
        this._dnsClass = recordClass;
        this._unique = unique;
        this._qualifiedNameMap = ServiceInfoImpl.decodeQualifiedNameMapForType(getName());
        String domain = (String) this._qualifiedNameMap.get(Fields.Domain);
        String protocol = (String) this._qualifiedNameMap.get(Fields.Protocol);
        String application = (String) this._qualifiedNameMap.get(Fields.Application);
        String instance = ((String) this._qualifiedNameMap.get(Fields.Instance)).toLowerCase();
        this._type = (application.length() > 0 ? "_" + application + "." : XmlPullParser.NO_NAMESPACE) + (protocol.length() > 0 ? "_" + protocol + "." : XmlPullParser.NO_NAMESPACE) + domain + ".";
        this._key = ((instance.length() > 0 ? instance + "." : XmlPullParser.NO_NAMESPACE) + this._type).toLowerCase();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DNSEntry)) {
            return false;
        }
        DNSEntry other = (DNSEntry) obj;
        return getKey().equals(other.getKey()) && getRecordType().equals(other.getRecordType()) && getRecordClass() == other.getRecordClass();
    }

    public boolean isSameEntry(DNSEntry entry) {
        return getKey().equals(entry.getKey()) && getRecordType().equals(entry.getRecordType()) && (DNSRecordClass.CLASS_ANY == entry.getRecordClass() || getRecordClass().equals(entry.getRecordClass()));
    }

    public boolean sameSubtype(DNSEntry other) {
        return getSubtype().equals(other.getSubtype());
    }

    public String getSubtype() {
        String subtype = (String) getQualifiedNameMap().get(Fields.Subtype);
        return subtype != null ? subtype : XmlPullParser.NO_NAMESPACE;
    }

    public String getName() {
        return this._name != null ? this._name : XmlPullParser.NO_NAMESPACE;
    }

    public String getType() {
        return this._type != null ? this._type : XmlPullParser.NO_NAMESPACE;
    }

    public String getKey() {
        return this._key != null ? this._key : XmlPullParser.NO_NAMESPACE;
    }

    public DNSRecordType getRecordType() {
        return this._recordType != null ? this._recordType : DNSRecordType.TYPE_IGNORE;
    }

    public DNSRecordClass getRecordClass() {
        return this._dnsClass != null ? this._dnsClass : DNSRecordClass.CLASS_UNKNOWN;
    }

    public boolean isUnique() {
        return this._unique;
    }

    public Map<Fields, String> getQualifiedNameMap() {
        return Collections.unmodifiableMap(this._qualifiedNameMap);
    }

    public boolean isServicesDiscoveryMetaQuery() {
        return ((String) this._qualifiedNameMap.get(Fields.Application)).equals("dns-sd") && ((String) this._qualifiedNameMap.get(Fields.Instance)).equals("_services");
    }

    public boolean isDomainDiscoveryQuery() {
        if (!((String) this._qualifiedNameMap.get(Fields.Application)).equals("dns-sd")) {
            return false;
        }
        String name = (String) this._qualifiedNameMap.get(Fields.Instance);
        if ("b".equals(name) || "db".equals(name) || "r".equals(name) || "dr".equals(name) || "lb".equals(name)) {
            return true;
        }
        return false;
    }

    public boolean isReverseLookup() {
        return isV4ReverseLookup() || isV6ReverseLookup();
    }

    public boolean isV4ReverseLookup() {
        return ((String) this._qualifiedNameMap.get(Fields.Domain)).endsWith("in-addr.arpa");
    }

    public boolean isV6ReverseLookup() {
        return ((String) this._qualifiedNameMap.get(Fields.Domain)).endsWith("ip6.arpa");
    }

    public boolean isSameRecordClass(DNSEntry entry) {
        return entry != null && entry.getRecordClass() == getRecordClass();
    }

    public boolean isSameType(DNSEntry entry) {
        return entry != null && entry.getRecordType() == getRecordType();
    }

    protected void toByteArray(DataOutputStream dout) throws IOException {
        dout.write(getName().getBytes("UTF8"));
        dout.writeShort(getRecordType().indexValue());
        dout.writeShort(getRecordClass().indexValue());
    }

    protected byte[] toByteArray() {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            toByteArray(dout);
            dout.close();
            return bout.toByteArray();
        } catch (IOException e) {
            throw new InternalError();
        }
    }

    public int compareTo(DNSEntry that) {
        byte[] thisBytes = toByteArray();
        byte[] thatBytes = that.toByteArray();
        int n = Math.min(thisBytes.length, thatBytes.length);
        for (int i = 0; i < n; i++) {
            if (thisBytes[i] > thatBytes[i]) {
                return 1;
            }
            if (thisBytes[i] < thatBytes[i]) {
                return -1;
            }
        }
        return thisBytes.length - thatBytes.length;
    }

    public int hashCode() {
        return (getKey().hashCode() + getRecordType().indexValue()) + getRecordClass().indexValue();
    }

    public String toString() {
        StringBuilder aLog = new StringBuilder(NNTPReply.SERVER_READY_POSTING_ALLOWED);
        aLog.append("[" + getClass().getSimpleName() + "@" + System.identityHashCode(this));
        aLog.append(" type: " + getRecordType());
        aLog.append(", class: " + getRecordClass());
        aLog.append(this._unique ? "-unique," : ",");
        aLog.append(" name: " + this._name);
        toString(aLog);
        aLog.append("]");
        return aLog.toString();
    }

    protected void toString(StringBuilder aLog) {
    }
}
