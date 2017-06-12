package org.apache.commons.net.smtp;

public class SimpleSMTPHeader {
    private StringBuffer __cc;
    private final String __from;
    private final StringBuffer __headerFields;
    private final String __subject;
    private final String __to;

    public SimpleSMTPHeader(String from, String to, String subject) {
        this.__to = to;
        this.__from = from;
        this.__subject = subject;
        this.__headerFields = new StringBuffer();
        this.__cc = null;
    }

    public void addHeaderField(String headerField, String value) {
        this.__headerFields.append(headerField);
        this.__headerFields.append(": ");
        this.__headerFields.append(value);
        this.__headerFields.append('\n');
    }

    public void addCC(String address) {
        if (this.__cc == null) {
            this.__cc = new StringBuffer();
        } else {
            this.__cc.append(", ");
        }
        this.__cc.append(address);
    }

    public String toString() {
        StringBuilder header = new StringBuilder();
        if (this.__headerFields.length() > 0) {
            header.append(this.__headerFields.toString());
        }
        header.append("From: ");
        header.append(this.__from);
        header.append("\nTo: ");
        header.append(this.__to);
        if (this.__cc != null) {
            header.append("\nCc: ");
            header.append(this.__cc.toString());
        }
        if (this.__subject != null) {
            header.append("\nSubject: ");
            header.append(this.__subject);
        }
        header.append('\n');
        header.append('\n');
        return header.toString();
    }
}
