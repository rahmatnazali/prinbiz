package org.kxml2.kdom;

import java.io.IOException;
import java.util.Vector;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Node {
    public static final int CDSECT = 5;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;
    public static final int DOCUMENT = 0;
    public static final int ELEMENT = 2;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int TEXT = 4;
    protected Vector children;
    protected StringBuffer types;

    public void addChild(int index, int type, Object child) {
        if (child == null) {
            throw new NullPointerException();
        }
        if (this.children == null) {
            this.children = new Vector();
            this.types = new StringBuffer();
        }
        if (type == ELEMENT) {
            if (child instanceof Element) {
                ((Element) child).setParent(this);
            } else {
                throw new RuntimeException("Element obj expected)");
            }
        } else if (!(child instanceof String)) {
            throw new RuntimeException("String expected");
        }
        this.children.insertElementAt(child, index);
        this.types.insert(index, (char) type);
    }

    public void addChild(int type, Object child) {
        addChild(getChildCount(), type, child);
    }

    public Element createElement(String namespace, String name) {
        Element e = new Element();
        if (namespace == null) {
            namespace = XmlPullParser.NO_NAMESPACE;
        }
        e.namespace = namespace;
        e.name = name;
        return e;
    }

    public Object getChild(int index) {
        return this.children.elementAt(index);
    }

    public int getChildCount() {
        return this.children == null ? DOCUMENT : this.children.size();
    }

    public Element getElement(int index) {
        Object child = getChild(index);
        return child instanceof Element ? (Element) child : null;
    }

    public Element getElement(String namespace, String name) {
        int i = indexOf(namespace, name, DOCUMENT);
        int j = indexOf(namespace, name, i + 1);
        if (i != -1 && j == -1) {
            return getElement(i);
        }
        throw new RuntimeException("Element {" + namespace + "}" + name + (i == -1 ? " not found in " : " more than once in ") + this);
    }

    public String getText(int index) {
        return isText(index) ? (String) getChild(index) : null;
    }

    public int getType(int index) {
        return this.types.charAt(index);
    }

    public int indexOf(String namespace, String name, int startIndex) {
        int len = getChildCount();
        int i = startIndex;
        while (i < len) {
            Element child = getElement(i);
            if (child != null && name.equals(child.getName()) && (namespace == null || namespace.equals(child.getNamespace()))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean isText(int i) {
        int t = getType(i);
        return t == TEXT || t == IGNORABLE_WHITESPACE || t == CDSECT;
    }

    public void parse(XmlPullParser parser) throws IOException, XmlPullParserException {
        boolean leave = false;
        do {
            int type = parser.getEventType();
            switch (type) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    leave = true;
                    continue;
                case ELEMENT /*2*/:
                    Element child = createElement(parser.getNamespace(), parser.getName());
                    addChild(ELEMENT, child);
                    child.parse(parser);
                    continue;
                default:
                    if (parser.getText() != null) {
                        if (type == ENTITY_REF) {
                            type = TEXT;
                        }
                        addChild(type, parser.getText());
                    } else if (type == ENTITY_REF && parser.getName() != null) {
                        addChild(ENTITY_REF, parser.getName());
                    }
                    parser.nextToken();
                    continue;
            }
        } while (!leave);
    }

    public void removeChild(int idx) {
        this.children.removeElementAt(idx);
        int n = this.types.length() - 1;
        for (int i = idx; i < n; i++) {
            this.types.setCharAt(i, this.types.charAt(i + 1));
        }
        this.types.setLength(n);
    }

    public void write(XmlSerializer writer) throws IOException {
        writeChildren(writer);
        writer.flush();
    }

    public void writeChildren(XmlSerializer writer) throws IOException {
        if (this.children != null) {
            int len = this.children.size();
            for (int i = DOCUMENT; i < len; i++) {
                int type = getType(i);
                Object child = this.children.elementAt(i);
                switch (type) {
                    case ELEMENT /*2*/:
                        ((Element) child).write(writer);
                        break;
                    case TEXT /*4*/:
                        writer.text((String) child);
                        break;
                    case CDSECT /*5*/:
                        writer.cdsect((String) child);
                        break;
                    case ENTITY_REF /*6*/:
                        writer.entityRef((String) child);
                        break;
                    case IGNORABLE_WHITESPACE /*7*/:
                        writer.ignorableWhitespace((String) child);
                        break;
                    case PROCESSING_INSTRUCTION /*8*/:
                        writer.processingInstruction((String) child);
                        break;
                    case COMMENT /*9*/:
                        writer.comment((String) child);
                        break;
                    case DOCDECL /*10*/:
                        writer.docdecl((String) child);
                        break;
                    default:
                        throw new RuntimeException("Illegal type: " + type);
                }
            }
        }
    }
}
