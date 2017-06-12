package org.apache.commons.net.util;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListenerList implements Serializable, Iterable<EventListener> {
    private static final long serialVersionUID = -1934227607974228213L;
    private final CopyOnWriteArrayList<EventListener> __listeners;

    public ListenerList() {
        this.__listeners = new CopyOnWriteArrayList();
    }

    public void addListener(EventListener listener) {
        this.__listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        this.__listeners.remove(listener);
    }

    public int getListenerCount() {
        return this.__listeners.size();
    }

    public Iterator<EventListener> iterator() {
        return this.__listeners.iterator();
    }
}
