package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.tasks.DNSTask;

public abstract class DNSResolverTask extends DNSTask {
    private static Logger logger;
    protected int _count;

    protected abstract DNSOutgoing addAnswers(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract DNSOutgoing addQuestions(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract String description();

    static {
        logger = Logger.getLogger(DNSResolverTask.class.getName());
    }

    public DNSResolverTask(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
        this._count = 0;
    }

    public String toString() {
        return super.toString() + " count: " + this._count;
    }

    public void start(Timer timer) {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 225, 225);
        }
    }

    public void run() {
        try {
            if (getDns().isCanceling() || getDns().isCanceled()) {
                cancel();
                return;
            }
            int i = this._count;
            this._count = i + 1;
            if (i < 3) {
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer(getName() + ".run() JmDNS " + description());
                }
                DNSOutgoing out = addQuestions(new DNSOutgoing(0));
                if (getDns().isAnnounced()) {
                    out = addAnswers(out);
                }
                if (!out.isEmpty()) {
                    getDns().send(out);
                    return;
                }
                return;
            }
            cancel();
        } catch (Throwable e) {
            logger.log(Level.WARNING, getName() + ".run() exception ", e);
            getDns().recover();
        }
    }
}
