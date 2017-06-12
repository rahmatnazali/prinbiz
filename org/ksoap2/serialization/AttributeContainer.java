package org.ksoap2.serialization;

import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;

public class AttributeContainer implements HasAttributes {
    protected Vector attributes;

    public AttributeContainer() {
        this.attributes = new Vector();
    }

    public void getAttributeInfo(int index, AttributeInfo attributeInfo) {
        AttributeInfo p = (AttributeInfo) this.attributes.elementAt(index);
        attributeInfo.name = p.name;
        attributeInfo.namespace = p.namespace;
        attributeInfo.flags = p.flags;
        attributeInfo.type = p.type;
        attributeInfo.elementType = p.elementType;
        attributeInfo.value = p.getValue();
    }

    public Object getAttribute(int index) {
        return ((AttributeInfo) this.attributes.elementAt(index)).getValue();
    }

    public String getAttributeAsString(int index) {
        return ((AttributeInfo) this.attributes.elementAt(index)).getValue().toString();
    }

    public Object getAttribute(String name) {
        Integer i = attributeIndex(name);
        if (i != null) {
            return getAttribute(i.intValue());
        }
        throw new RuntimeException(new StringBuffer().append("illegal property: ").append(name).toString());
    }

    public String getAttributeAsString(String name) {
        Integer i = attributeIndex(name);
        if (i != null) {
            return getAttribute(i.intValue()).toString();
        }
        throw new RuntimeException(new StringBuffer().append("illegal property: ").append(name).toString());
    }

    public boolean hasAttribute(String name) {
        if (attributeIndex(name) != null) {
            return true;
        }
        return false;
    }

    public Object getAttributeSafely(String name) {
        Integer i = attributeIndex(name);
        if (i != null) {
            return getAttribute(i.intValue());
        }
        return null;
    }

    public Object getAttributeSafelyAsString(String name) {
        Integer i = attributeIndex(name);
        if (i != null) {
            return getAttribute(i.intValue()).toString();
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    private Integer attributeIndex(String name) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (name.equals(((AttributeInfo) this.attributes.elementAt(i)).getName())) {
                return new Integer(i);
            }
        }
        return null;
    }

    public int getAttributeCount() {
        return this.attributes.size();
    }

    protected boolean attributesAreEqual(AttributeContainer other) {
        int numAttributes = getAttributeCount();
        if (numAttributes != other.getAttributeCount()) {
            return false;
        }
        for (int attribIndex = 0; attribIndex < numAttributes; attribIndex++) {
            AttributeInfo thisAttrib = (AttributeInfo) this.attributes.elementAt(attribIndex);
            Object thisAttribValue = thisAttrib.getValue();
            if (!other.hasAttribute(thisAttrib.getName()) || !thisAttribValue.equals(other.getAttributeSafely(thisAttrib.getName()))) {
                return false;
            }
        }
        return true;
    }

    public void addAttribute(String name, Object value) {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.name = name;
        attributeInfo.type = value == null ? PropertyInfo.OBJECT_CLASS : value.getClass();
        attributeInfo.value = value;
        addAttribute(attributeInfo);
    }

    public void addAttributeIfValue(String name, Object value) {
        if (value != null) {
            addAttribute(name, value);
        }
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        this.attributes.addElement(attributeInfo);
    }

    public void addAttributeIfValue(AttributeInfo attributeInfo) {
        if (attributeInfo.value != null) {
            this.attributes.addElement(attributeInfo);
        }
    }
}