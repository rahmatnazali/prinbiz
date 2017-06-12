package org.apache.commons.net.smtp;

import java.util.Enumeration;
import java.util.Vector;

public final class RelayPath {
    String _emailAddress;
    Vector<String> _path;

    public RelayPath(String emailAddress) {
        this._path = new Vector();
        this._emailAddress = emailAddress;
    }

    public void addRelay(String hostname) {
        this._path.addElement(hostname);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('<');
        Enumeration<String> hosts = this._path.elements();
        if (hosts.hasMoreElements()) {
            buffer.append('@');
            buffer.append((String) hosts.nextElement());
            while (hosts.hasMoreElements()) {
                buffer.append(",@");
                buffer.append((String) hosts.nextElement());
            }
            buffer.append(':');
        }
        buffer.append(this._emailAddress);
        buffer.append('>');
        return buffer.toString();
    }
}
