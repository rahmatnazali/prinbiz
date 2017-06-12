package javax.jmdns.impl.tasks;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.JmDNSImpl;
import org.xmlpull.v1.XmlPullParser;

public class RecordReaper extends DNSTask {
    static Logger logger;

    static {
        logger = Logger.getLogger(RecordReaper.class.getName());
    }

    public RecordReaper(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    public String getName() {
        return "RecordReaper(" + (getDns() != null ? getDns().getName() : XmlPullParser.NO_NAMESPACE) + ")";
    }

    public void start(Timer timer) {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 10000, 10000);
        }
    }

    public void run() {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest(getName() + ".run() JmDNS reaping cache");
            }
            getDns().cleanCache();
        }
    }
}
