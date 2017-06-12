package org.apache.commons.net.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.net.telnet.TelnetOption;

public class SubnetUtils {
    private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    private static final int NBITS = 32;
    private static final String SLASH_FORMAT = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})";
    private static final Pattern addressPattern;
    private static final Pattern cidrPattern;
    private int address;
    private int broadcast;
    private boolean inclusiveHostCount;
    private int netmask;
    private int network;

    public final class SubnetInfo {
        private SubnetInfo() {
        }

        private int netmask() {
            return SubnetUtils.this.netmask;
        }

        private int network() {
            return SubnetUtils.this.network;
        }

        private int address() {
            return SubnetUtils.this.address;
        }

        private int broadcast() {
            return SubnetUtils.this.broadcast;
        }

        private int low() {
            if (SubnetUtils.this.isInclusiveHostCount()) {
                return network();
            }
            return broadcast() - network() > 1 ? network() + 1 : 0;
        }

        private int high() {
            if (SubnetUtils.this.isInclusiveHostCount()) {
                return broadcast();
            }
            return broadcast() - network() > 1 ? broadcast() - 1 : 0;
        }

        public boolean isInRange(String address) {
            return isInRange(SubnetUtils.this.toInteger(address));
        }

        private boolean isInRange(int address) {
            int diff = address - low();
            return diff >= 0 && diff <= high() - low();
        }

        public String getBroadcastAddress() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(broadcast()));
        }

        public String getNetworkAddress() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(network()));
        }

        public String getNetmask() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(netmask()));
        }

        public String getAddress() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(address()));
        }

        public String getLowAddress() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(low()));
        }

        public String getHighAddress() {
            return SubnetUtils.this.format(SubnetUtils.this.toArray(high()));
        }

        public int getAddressCount() {
            int count = (broadcast() - network()) + (SubnetUtils.this.isInclusiveHostCount() ? 1 : -1);
            return count < 0 ? 0 : count;
        }

        public int asInteger(String address) {
            return SubnetUtils.this.toInteger(address);
        }

        public String getCidrSignature() {
            return SubnetUtils.this.toCidrNotation(SubnetUtils.this.format(SubnetUtils.this.toArray(address())), SubnetUtils.this.format(SubnetUtils.this.toArray(netmask())));
        }

        public String[] getAllAddresses() {
            int ct = getAddressCount();
            String[] addresses = new String[ct];
            if (ct != 0) {
                int add = low();
                int j = 0;
                while (add <= high()) {
                    addresses[j] = SubnetUtils.this.format(SubnetUtils.this.toArray(add));
                    add++;
                    j++;
                }
            }
            return addresses;
        }

        public String toString() {
            StringBuilder buf = new StringBuilder();
            buf.append("CIDR Signature:\t[").append(getCidrSignature()).append("]").append(" Netmask: [").append(getNetmask()).append("]\n").append("Network:\t[").append(getNetworkAddress()).append("]\n").append("Broadcast:\t[").append(getBroadcastAddress()).append("]\n").append("First Address:\t[").append(getLowAddress()).append("]\n").append("Last Address:\t[").append(getHighAddress()).append("]\n").append("# Addresses:\t[").append(getAddressCount()).append("]\n");
            return buf.toString();
        }
    }

    static {
        addressPattern = Pattern.compile(IP_ADDRESS);
        cidrPattern = Pattern.compile(SLASH_FORMAT);
    }

    public SubnetUtils(String cidrNotation) {
        this.netmask = 0;
        this.address = 0;
        this.network = 0;
        this.broadcast = 0;
        this.inclusiveHostCount = false;
        calculate(cidrNotation);
    }

    public SubnetUtils(String address, String mask) {
        this.netmask = 0;
        this.address = 0;
        this.network = 0;
        this.broadcast = 0;
        this.inclusiveHostCount = false;
        calculate(toCidrNotation(address, mask));
    }

    public boolean isInclusiveHostCount() {
        return this.inclusiveHostCount;
    }

    public void setInclusiveHostCount(boolean inclusiveHostCount) {
        this.inclusiveHostCount = inclusiveHostCount;
    }

    public final SubnetInfo getInfo() {
        return new SubnetInfo();
    }

    private void calculate(String mask) {
        Matcher matcher = cidrPattern.matcher(mask);
        if (matcher.matches()) {
            this.address = matchAddress(matcher);
            int cidrPart = rangeCheck(Integer.parseInt(matcher.group(5)), 0, NBITS);
            for (int j = 0; j < cidrPart; j++) {
                this.netmask |= 1 << (31 - j);
            }
            this.network = this.address & this.netmask;
            this.broadcast = this.network | (this.netmask ^ -1);
            return;
        }
        throw new IllegalArgumentException("Could not parse [" + mask + "]");
    }

    private int toInteger(String address) {
        Matcher matcher = addressPattern.matcher(address);
        if (matcher.matches()) {
            return matchAddress(matcher);
        }
        throw new IllegalArgumentException("Could not parse [" + address + "]");
    }

    private int matchAddress(Matcher matcher) {
        int addr = 0;
        for (int i = 1; i <= 4; i++) {
            addr |= (rangeCheck(Integer.parseInt(matcher.group(i)), -1, TelnetOption.MAX_OPTION_VALUE) & TelnetOption.MAX_OPTION_VALUE) << ((4 - i) * 8);
        }
        return addr;
    }

    private int[] toArray(int val) {
        int[] ret = new int[4];
        for (int j = 3; j >= 0; j--) {
            ret[j] = ret[j] | ((val >>> ((3 - j) * 8)) & TelnetOption.MAX_OPTION_VALUE);
        }
        return ret;
    }

    private String format(int[] octets) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < octets.length; i++) {
            str.append(octets[i]);
            if (i != octets.length - 1) {
                str.append(".");
            }
        }
        return str.toString();
    }

    private int rangeCheck(int value, int begin, int end) {
        if (value > begin && value <= end) {
            return value;
        }
        throw new IllegalArgumentException("Value [" + value + "] not in range (" + begin + "," + end + "]");
    }

    int pop(int x) {
        x -= (x >>> 1) & 1431655765;
        x = (x & 858993459) + ((x >>> 2) & 858993459);
        x = ((x >>> 4) + x) & 252645135;
        x += x >>> 8;
        return (x + (x >>> 16)) & 63;
    }

    private String toCidrNotation(String addr, String mask) {
        return addr + "/" + pop(toInteger(mask));
    }
}
