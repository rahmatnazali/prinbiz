package javax.jmdns.impl.tasks;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSIncoming;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSConstants;
import org.xmlpull.v1.XmlPullParser;

public class Responder extends DNSTask {
    static Logger logger;
    private final DNSIncoming _in;
    private final boolean _unicast;

    static {
        logger = Logger.getLogger(Responder.class.getName());
    }

    public Responder(JmDNSImpl jmDNSImpl, DNSIncoming in, int port) {
        super(jmDNSImpl);
        this._in = in;
        this._unicast = port != DNSConstants.MDNS_PORT;
    }

    public String getName() {
        return "Responder(" + (getDns() != null ? getDns().getName() : XmlPullParser.NO_NAMESPACE) + ")";
    }

    public String toString() {
        return super.toString() + " incomming: " + this._in;
    }

    public void start(Timer timer) {
        boolean iAmTheOnlyOne = true;
        for (DNSQuestion question : this._in.getQuestions()) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest(getName() + "start() question=" + question);
            }
            iAmTheOnlyOne = question.iAmTheOnlyOne(getDns());
            if (!iAmTheOnlyOne) {
                break;
            }
        }
        int delay = (!iAmTheOnlyOne || this._in.isTruncated()) ? (JmDNSImpl.getRandom().nextInt(96) + 20) - this._in.elapseSinceArrival() : 0;
        if (delay < 0) {
            delay = 0;
        }
        if (logger.isLoggable(Level.FINEST)) {
            logger.finest(getName() + "start() Responder chosen delay=" + delay);
        }
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, (long) delay);
        }
    }

    public void run() {
        getDns().respondToQuery(this._in);
        Set<DNSQuestion> questions = new HashSet();
        Set<DNSRecord> answers = new HashSet();
        if (getDns().isAnnounced()) {
            try {
                for (DNSQuestion question : this._in.getQuestions()) {
                    if (logger.isLoggable(Level.FINER)) {
                        logger.finer(getName() + "run() JmDNS responding to: " + question);
                    }
                    if (this._unicast) {
                        questions.add(question);
                    }
                    question.addAnswers(getDns(), answers);
                }
                long now = System.currentTimeMillis();
                for (DNSRecord knownAnswer : this._in.getAnswers()) {
                    if (knownAnswer.isStale(now)) {
                        answers.remove(knownAnswer);
                        if (logger.isLoggable(Level.FINER)) {
                            logger.finer(getName() + "JmDNS Responder Known Answer Removed");
                        }
                    }
                }
                if (!answers.isEmpty()) {
                    if (logger.isLoggable(Level.FINER)) {
                        logger.finer(getName() + "run() JmDNS responding");
                    }
                    DNSOutgoing out = new DNSOutgoing(33792, !this._unicast, this._in.getSenderUDPPayload());
                    out.setId(this._in.getId());
                    for (DNSQuestion question2 : questions) {
                        if (question2 != null) {
                            out = addQuestion(out, question2);
                        }
                    }
                    for (DNSRecord answer : answers) {
                        if (answer != null) {
                            out = addAnswer(out, this._in, answer);
                        }
                    }
                    if (!out.isEmpty()) {
                        getDns().send(out);
                    }
                }
            } catch (Throwable e) {
                logger.log(Level.WARNING, getName() + "run() exception ", e);
                getDns().close();
            }
        }
    }
}
