package javax.jmdns.impl.constants;

import com.hiti.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import java.util.logging.Logger;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;

public enum DNSRecordType {
    TYPE_IGNORE("ignore", 0),
    TYPE_A("a", 1),
    TYPE_NS("ns", 2),
    TYPE_MD("md", 3),
    TYPE_MF("mf", 4),
    TYPE_CNAME("cname", 5),
    TYPE_SOA("soa", 6),
    TYPE_MB("mb", 7),
    TYPE_MG("mg", 8),
    TYPE_MR("mr", 9),
    TYPE_NULL("null", 10),
    TYPE_WKS("wks", 11),
    TYPE_PTR("ptr", 12),
    TYPE_HINFO("hinfo", 13),
    TYPE_MINFO("minfo", 14),
    TYPE_MX("mx", 15),
    TYPE_TXT("txt", 16),
    TYPE_RP("rp", 17),
    TYPE_AFSDB("afsdb", 18),
    TYPE_X25("x25", 19),
    TYPE_ISDN("isdn", 20),
    TYPE_RT("rt", 21),
    TYPE_NSAP("nsap", 22),
    TYPE_NSAP_PTR("nsap-otr", 23),
    TYPE_SIG("sig", 24),
    TYPE_KEY("key", 25),
    TYPE_PX("px", 26),
    TYPE_GPOS("gpos", 27),
    TYPE_AAAA("aaaa", 28),
    TYPE_LOC("loc", 29),
    TYPE_NXT("nxt", 30),
    TYPE_EID("eid", 31),
    TYPE_NIMLOC("nimloc", 32),
    TYPE_SRV("srv", 33),
    TYPE_ATMA("atma", 34),
    TYPE_NAPTR("naptr", 35),
    TYPE_KX("kx", 36),
    TYPE_CERT("cert", 37),
    TYPE_A6("a6", 38),
    TYPE_DNAME("dname", 39),
    TYPE_SINK("sink", 40),
    TYPE_OPT("opt", 41),
    TYPE_APL("apl", 42),
    TYPE_DS("ds", 43),
    TYPE_SSHFP("sshfp", 44),
    TYPE_RRSIG("rrsig", 46),
    TYPE_NSEC("nsec", 47),
    TYPE_DNSKEY("dnskey", 48),
    TYPE_UINFO("uinfo", 100),
    TYPE_UID("uid", ControllerState.PLAY_PHOTO),
    TYPE_GID("gid", ControllerState.NO_PLAY_ITEM),
    TYPE_UNSPEC("unspec", ControllerState.PLAY_COUNT_STATE),
    TYPE_TKEY("tkey", TelnetCommand.GA),
    TYPE_TSIG("tsig", TelnetCommand.SB),
    TYPE_IXFR("ixfr", TelnetCommand.WILL),
    TYPE_AXFR("axfr", TelnetCommand.WONT),
    TYPE_MAILA("mails", TelnetCommand.DO),
    TYPE_MAILB("mailb", TelnetCommand.DONT),
    TYPE_ANY("any", TelnetOption.MAX_OPTION_VALUE);
    
    private static Logger logger;
    private final String _externalName;
    private final int _index;

    static {
        logger = Logger.getLogger(DNSRecordType.class.getName());
    }

    private DNSRecordType(String name, int index) {
        this._externalName = name;
        this._index = index;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public static DNSRecordType typeForName(String name) {
        if (name != null) {
            String aName = name.toLowerCase();
            for (DNSRecordType aType : values()) {
                if (aType._externalName.equals(aName)) {
                    return aType;
                }
            }
        }
        logger.severe("Could not find record type for name: " + name);
        return TYPE_IGNORE;
    }

    public static DNSRecordType typeForIndex(int index) {
        for (DNSRecordType aType : values()) {
            if (aType._index == index) {
                return aType;
            }
        }
        logger.severe("Could not find record type for index: " + index);
        return TYPE_IGNORE;
    }

    public String toString() {
        return name() + " index " + indexValue();
    }
}
