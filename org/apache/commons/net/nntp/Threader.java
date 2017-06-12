package org.apache.commons.net.nntp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Threader {
    private int bogusIdCount;
    private HashMap<String, ThreadContainer> idTable;
    private ThreadContainer root;

    public Threader() {
        this.bogusIdCount = 0;
    }

    public Threadable thread(List<? extends Threadable> messages) {
        return thread((Iterable) messages);
    }

    public Threadable thread(Iterable<? extends Threadable> messages) {
        if (messages == null) {
            return null;
        }
        this.idTable = new HashMap();
        for (Threadable t : messages) {
            if (!t.isDummy()) {
                buildContainer(t);
            }
        }
        this.root = findRootSet();
        this.idTable.clear();
        this.idTable = null;
        pruneEmptyContainers(this.root);
        this.root.reverseChildren();
        gatherSubjects();
        if (this.root.next != null) {
            throw new RuntimeException("root node has a next:" + this.root);
        }
        for (ThreadContainer r = this.root.child; r != null; r = r.next) {
            if (r.threadable == null) {
                r.threadable = r.child.threadable.makeDummy();
            }
        }
        Threadable result = this.root.child == null ? null : this.root.child.threadable;
        this.root.flush();
        this.root = null;
        return result;
    }

    private void buildContainer(Threadable threadable) {
        String id = threadable.messageThreadId();
        ThreadContainer container = (ThreadContainer) this.idTable.get(id);
        if (container != null) {
            if (container.threadable != null) {
                StringBuilder append = new StringBuilder().append("<Bogus-id:");
                int i = this.bogusIdCount;
                this.bogusIdCount = i + 1;
                id = append.append(i).append(">").toString();
                container = null;
            } else {
                container.threadable = threadable;
            }
        }
        if (container == null) {
            container = new ThreadContainer();
            container.threadable = threadable;
            this.idTable.put(id, container);
        }
        ThreadContainer parentRef = null;
        for (String refString : threadable.messageThreadReferences()) {
            ThreadContainer ref = (ThreadContainer) this.idTable.get(refString);
            if (ref == null) {
                ref = new ThreadContainer();
                this.idTable.put(refString, ref);
            }
            if (!(parentRef == null || ref.parent != null || parentRef == ref || ref.findChild(parentRef))) {
                ref.parent = parentRef;
                ref.next = parentRef.child;
                parentRef.child = ref;
            }
            parentRef = ref;
        }
        if (parentRef != null && (parentRef == container || container.findChild(parentRef))) {
            parentRef = null;
        }
        if (container.parent != null) {
            ThreadContainer prev = null;
            ThreadContainer rest = container.parent.child;
            while (rest != null && rest != container) {
                prev = rest;
                rest = rest.next;
            }
            if (rest == null) {
                throw new RuntimeException("Didnt find " + container + " in parent" + container.parent);
            }
            if (prev == null) {
                container.parent.child = container.next;
            } else {
                prev.next = container.next;
            }
            container.next = null;
            container.parent = null;
        }
        if (parentRef != null) {
            container.parent = parentRef;
            container.next = parentRef.child;
            parentRef.child = container;
        }
    }

    private ThreadContainer findRootSet() {
        ThreadContainer root = new ThreadContainer();
        for (Object key : this.idTable.keySet()) {
            ThreadContainer c = (ThreadContainer) this.idTable.get(key);
            if (c.parent == null) {
                if (c.next != null) {
                    throw new RuntimeException("c.next is " + c.next.toString());
                }
                c.next = root.child;
                root.child = c;
            }
        }
        return root;
    }

    private void pruneEmptyContainers(ThreadContainer parent) {
        ThreadContainer prev = null;
        ThreadContainer container = parent.child;
        ThreadContainer next = container.next;
        while (container != null) {
            if (container.threadable == null && container.child == null) {
                if (prev == null) {
                    parent.child = container.next;
                } else {
                    prev.next = container.next;
                }
                container = prev;
            } else if (container.threadable == null && container.child != null && (container.parent != null || container.child.next == null)) {
                ThreadContainer kids = container.child;
                if (prev == null) {
                    parent.child = kids;
                } else {
                    prev.next = kids;
                }
                ThreadContainer tail = kids;
                while (tail.next != null) {
                    tail.parent = container.parent;
                    tail = tail.next;
                }
                tail.parent = container.parent;
                tail.next = container.next;
                next = kids;
                container = prev;
            } else if (container.child != null) {
                pruneEmptyContainers(container);
            }
            prev = container;
            container = next;
            next = container == null ? null : container.next;
        }
    }

    private void gatherSubjects() {
        ThreadContainer c;
        ThreadContainer old;
        int count = 0;
        for (c = this.root.child; c != null; c = c.next) {
            count++;
        }
        HashMap<String, ThreadContainer> subjectTable = new HashMap((int) (((double) count) * 1.2d), 0.9f);
        count = 0;
        c = this.root.child;
        while (c != null) {
            Threadable threadable = c.threadable;
            if (threadable == null) {
                threadable = c.child.threadable;
            }
            String subj = threadable.simplifiedSubject();
            if (!(subj == null || subj.length() == 0)) {
                old = (ThreadContainer) subjectTable.get(subj);
                if (old == null || ((c.threadable == null && old.threadable != null) || !(old.threadable == null || !old.threadable.subjectIsReply() || c.threadable == null || c.threadable.subjectIsReply()))) {
                    subjectTable.put(subj, c);
                    count++;
                }
            }
            c = c.next;
        }
        if (count != 0) {
            ThreadContainer prev = null;
            c = this.root.child;
            ThreadContainer rest = c.next;
            while (c != null) {
                threadable = c.threadable;
                if (threadable == null) {
                    threadable = c.child.threadable;
                }
                subj = threadable.simplifiedSubject();
                if (!(subj == null || subj.length() == 0)) {
                    old = (ThreadContainer) subjectTable.get(subj);
                    if (old != c) {
                        if (prev == null) {
                            this.root.child = c.next;
                        } else {
                            prev.next = c.next;
                        }
                        c.next = null;
                        ThreadContainer tail;
                        if (old.threadable == null && c.threadable == null) {
                            tail = old.child;
                            while (tail != null && tail.next != null) {
                                tail = tail.next;
                            }
                            if (tail != null) {
                                tail.next = c.child;
                            }
                            for (tail = c.child; tail != null; tail = tail.next) {
                                tail.parent = old;
                            }
                            c.child = null;
                        } else if (old.threadable == null || !(c.threadable == null || !c.threadable.subjectIsReply() || old.threadable.subjectIsReply())) {
                            c.parent = old;
                            c.next = old.child;
                            old.child = c;
                        } else {
                            ThreadContainer newc = new ThreadContainer();
                            newc.threadable = old.threadable;
                            newc.child = old.child;
                            for (tail = newc.child; tail != null; tail = tail.next) {
                                tail.parent = newc;
                            }
                            old.threadable = null;
                            old.child = null;
                            c.parent = old;
                            newc.parent = old;
                            old.child = c;
                            c.next = newc;
                        }
                        c = prev;
                    }
                }
                prev = c;
                c = rest;
                if (rest == null) {
                    rest = null;
                } else {
                    rest = rest.next;
                }
            }
            subjectTable.clear();
        }
    }

    @Deprecated
    public Threadable thread(Threadable[] messages) {
        return thread(Arrays.asList(messages));
    }
}
