package org.kobjects.pim;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public abstract class PimItem {
    public static final int TYPE_STRING = 0;
    public static final int TYPE_STRING_ARRAY = 1;
    Hashtable fields;

    public abstract int getArraySize(String str);

    public abstract String getType();

    public PimItem() {
        this.fields = new Hashtable();
    }

    public PimItem(PimItem orig) {
        this.fields = new Hashtable();
        Enumeration e = orig.fields();
        while (e.hasMoreElements()) {
            addField(new PimField((PimField) e.nextElement()));
        }
    }

    public Enumeration fieldNames() {
        return this.fields.keys();
    }

    public void addField(PimField field) {
        Vector v = (Vector) this.fields.get(field.name);
        if (v == null) {
            v = new Vector();
            this.fields.put(field.name, v);
        }
        v.addElement(field);
    }

    public Enumeration fields() {
        Vector v = new Vector();
        Enumeration e = fieldNames();
        while (e.hasMoreElements()) {
            Enumeration f = fields((String) e.nextElement());
            while (f.hasMoreElements()) {
                v.addElement(f.nextElement());
            }
        }
        return v.elements();
    }

    public Enumeration fields(String name) {
        Vector v = (Vector) this.fields.get(name);
        if (v == null) {
            v = new Vector();
        }
        return v.elements();
    }

    public PimField getField(String name, int index) {
        return (PimField) ((Vector) this.fields.get(name)).elementAt(index);
    }

    public int getFieldCount(String name) {
        Vector v = (Vector) this.fields.get(name);
        return v == null ? TYPE_STRING : v.size();
    }

    public int getType(String name) {
        return getArraySize(name) == -1 ? TYPE_STRING : TYPE_STRING_ARRAY;
    }

    public void removeField(String name, int index) {
        ((Vector) this.fields.get(name)).removeElementAt(index);
    }

    public String toString() {
        return getType() + ":" + this.fields.toString();
    }
}
