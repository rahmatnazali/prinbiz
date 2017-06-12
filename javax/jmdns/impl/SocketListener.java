package javax.jmdns.impl;

import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;

class SocketListener extends Thread {
    static Logger logger;
    private final JmDNSImpl _jmDNSImpl;

    static {
        logger = Logger.getLogger(SocketListener.class.getName());
    }

    SocketListener(JmDNSImpl jmDNSImpl) {
        super("SocketListener(" + (jmDNSImpl != null ? jmDNSImpl.getName() : XmlPullParser.NO_NAMESPACE) + ")");
        setDaemon(true);
        this._jmDNSImpl = jmDNSImpl;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r8 = this;
        r4 = 8972; // 0x230c float:1.2572E-41 double:4.433E-320;
        r0 = new byte[r4];	 Catch:{ IOException -> 0x00f9 }
        r3 = new java.net.DatagramPacket;	 Catch:{ IOException -> 0x00f9 }
        r4 = r0.length;	 Catch:{ IOException -> 0x00f9 }
        r3.<init>(r0, r4);	 Catch:{ IOException -> 0x00f9 }
    L_0x000a:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isCanceling();	 Catch:{ IOException -> 0x00f9 }
        if (r4 != 0) goto L_0x0047;
    L_0x0012:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isCanceled();	 Catch:{ IOException -> 0x00f9 }
        if (r4 != 0) goto L_0x0047;
    L_0x001a:
        r4 = r0.length;	 Catch:{ IOException -> 0x00f9 }
        r3.setLength(r4);	 Catch:{ IOException -> 0x00f9 }
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.getSocket();	 Catch:{ IOException -> 0x00f9 }
        r4.receive(r3);	 Catch:{ IOException -> 0x00f9 }
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isCanceling();	 Catch:{ IOException -> 0x00f9 }
        if (r4 != 0) goto L_0x0047;
    L_0x002f:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isCanceled();	 Catch:{ IOException -> 0x00f9 }
        if (r4 != 0) goto L_0x0047;
    L_0x0037:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isClosing();	 Catch:{ IOException -> 0x00f9 }
        if (r4 != 0) goto L_0x0047;
    L_0x003f:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00f9 }
        r4 = r4.isClosed();	 Catch:{ IOException -> 0x00f9 }
        if (r4 == 0) goto L_0x006e;
    L_0x0047:
        r4 = logger;
        r5 = java.util.logging.Level.FINEST;
        r4 = r4.isLoggable(r5);
        if (r4 == 0) goto L_0x006d;
    L_0x0051:
        r4 = logger;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = r8.getName();
        r5 = r5.append(r6);
        r6 = ".run() exiting.";
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.finest(r5);
    L_0x006d:
        return;
    L_0x006e:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00d8 }
        r4 = r4.getLocalHost();	 Catch:{ IOException -> 0x00d8 }
        r4 = r4.shouldIgnorePacket(r3);	 Catch:{ IOException -> 0x00d8 }
        if (r4 != 0) goto L_0x000a;
    L_0x007a:
        r2 = new javax.jmdns.impl.DNSIncoming;	 Catch:{ IOException -> 0x00d8 }
        r2.<init>(r3);	 Catch:{ IOException -> 0x00d8 }
        r4 = logger;	 Catch:{ IOException -> 0x00d8 }
        r5 = java.util.logging.Level.FINEST;	 Catch:{ IOException -> 0x00d8 }
        r4 = r4.isLoggable(r5);	 Catch:{ IOException -> 0x00d8 }
        if (r4 == 0) goto L_0x00ae;
    L_0x0089:
        r4 = logger;	 Catch:{ IOException -> 0x00d8 }
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00d8 }
        r5.<init>();	 Catch:{ IOException -> 0x00d8 }
        r6 = r8.getName();	 Catch:{ IOException -> 0x00d8 }
        r5 = r5.append(r6);	 Catch:{ IOException -> 0x00d8 }
        r6 = ".run() JmDNS in:";
        r5 = r5.append(r6);	 Catch:{ IOException -> 0x00d8 }
        r6 = 1;
        r6 = r2.print(r6);	 Catch:{ IOException -> 0x00d8 }
        r5 = r5.append(r6);	 Catch:{ IOException -> 0x00d8 }
        r5 = r5.toString();	 Catch:{ IOException -> 0x00d8 }
        r4.finest(r5);	 Catch:{ IOException -> 0x00d8 }
    L_0x00ae:
        r4 = r2.isQuery();	 Catch:{ IOException -> 0x00d8 }
        if (r4 == 0) goto L_0x013f;
    L_0x00b4:
        r4 = r3.getPort();	 Catch:{ IOException -> 0x00d8 }
        r5 = javax.jmdns.impl.constants.DNSConstants.MDNS_PORT;	 Catch:{ IOException -> 0x00d8 }
        if (r4 == r5) goto L_0x00c9;
    L_0x00bc:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00d8 }
        r5 = r3.getAddress();	 Catch:{ IOException -> 0x00d8 }
        r6 = r3.getPort();	 Catch:{ IOException -> 0x00d8 }
        r4.handleQuery(r2, r5, r6);	 Catch:{ IOException -> 0x00d8 }
    L_0x00c9:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00d8 }
        r5 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00d8 }
        r5 = r5.getGroup();	 Catch:{ IOException -> 0x00d8 }
        r6 = javax.jmdns.impl.constants.DNSConstants.MDNS_PORT;	 Catch:{ IOException -> 0x00d8 }
        r4.handleQuery(r2, r5, r6);	 Catch:{ IOException -> 0x00d8 }
        goto L_0x000a;
    L_0x00d8:
        r1 = move-exception;
        r4 = logger;	 Catch:{ IOException -> 0x00f9 }
        r5 = java.util.logging.Level.WARNING;	 Catch:{ IOException -> 0x00f9 }
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00f9 }
        r6.<init>();	 Catch:{ IOException -> 0x00f9 }
        r7 = r8.getName();	 Catch:{ IOException -> 0x00f9 }
        r6 = r6.append(r7);	 Catch:{ IOException -> 0x00f9 }
        r7 = ".run() exception ";
        r6 = r6.append(r7);	 Catch:{ IOException -> 0x00f9 }
        r6 = r6.toString();	 Catch:{ IOException -> 0x00f9 }
        r4.log(r5, r6, r1);	 Catch:{ IOException -> 0x00f9 }
        goto L_0x000a;
    L_0x00f9:
        r1 = move-exception;
        r4 = r8._jmDNSImpl;
        r4 = r4.isCanceling();
        if (r4 != 0) goto L_0x0047;
    L_0x0102:
        r4 = r8._jmDNSImpl;
        r4 = r4.isCanceled();
        if (r4 != 0) goto L_0x0047;
    L_0x010a:
        r4 = r8._jmDNSImpl;
        r4 = r4.isClosing();
        if (r4 != 0) goto L_0x0047;
    L_0x0112:
        r4 = r8._jmDNSImpl;
        r4 = r4.isClosed();
        if (r4 != 0) goto L_0x0047;
    L_0x011a:
        r4 = logger;
        r5 = java.util.logging.Level.WARNING;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = r8.getName();
        r6 = r6.append(r7);
        r7 = ".run() exception ";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r4.log(r5, r6, r1);
        r4 = r8._jmDNSImpl;
        r4.recover();
        goto L_0x0047;
    L_0x013f:
        r4 = r8._jmDNSImpl;	 Catch:{ IOException -> 0x00d8 }
        r4.handleResponse(r2);	 Catch:{ IOException -> 0x00d8 }
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.jmdns.impl.SocketListener.run():void");
    }

    public JmDNSImpl getDns() {
        return this._jmDNSImpl;
    }
}
