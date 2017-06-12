package com.flurry.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import javax.jmdns.impl.constants.DNSConstants;

public class ke<T extends lx> {
    private static final String f321a;
    private final jw<Object, T> f322b;
    private final HashMap<T, Object> f323c;
    private final HashMap<T, Future<?>> f324d;
    private final ThreadPoolExecutor f325e;

    /* renamed from: com.flurry.sdk.ke.1 */
    class C01551 extends ThreadPoolExecutor {
        final /* synthetic */ ke f319a;

        /* renamed from: com.flurry.sdk.ke.1.1 */
        class C06081 extends lw {
            final /* synthetic */ lx f626a;
            final /* synthetic */ C01551 f627b;

            C06081(C01551 c01551, lx lxVar) {
                this.f627b = c01551;
                this.f626a = lxVar;
            }

            public final void m598a() {
            }
        }

        /* renamed from: com.flurry.sdk.ke.1.2 */
        class C06092 extends lw {
            final /* synthetic */ lx f628a;
            final /* synthetic */ C01551 f629b;

            C06092(C01551 c01551, lx lxVar) {
                this.f629b = c01551;
                this.f628a = lxVar;
            }

            public final void m599a() {
            }
        }

        C01551(ke keVar, TimeUnit timeUnit, BlockingQueue blockingQueue) {
            this.f319a = keVar;
            super(0, 5, DNSConstants.CLOSE_TIMEOUT, timeUnit, blockingQueue);
        }

        protected final void beforeExecute(Thread thread, Runnable runnable) {
            super.beforeExecute(thread, runnable);
            lx a = ke.m169a(runnable);
            if (a != null) {
                new C06081(this, a).run();
            }
        }

        protected final void afterExecute(Runnable runnable, Throwable th) {
            super.afterExecute(runnable, th);
            lx a = ke.m169a(runnable);
            if (a != null) {
                synchronized (this.f319a.f324d) {
                    this.f319a.f324d.remove(a);
                }
                this.f319a.m173b(a);
                new C06092(this, a).run();
            }
        }

        protected final <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
            throw new UnsupportedOperationException("Callable not supported");
        }

        protected final <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
            RunnableFuture kdVar = new kd(runnable, v);
            synchronized (this.f319a.f324d) {
                this.f319a.f324d.put((lx) runnable, kdVar);
            }
            return kdVar;
        }
    }

    /* renamed from: com.flurry.sdk.ke.2 */
    class C01562 extends DiscardPolicy {
        final /* synthetic */ ke f320a;

        /* renamed from: com.flurry.sdk.ke.2.1 */
        class C06101 extends lw {
            final /* synthetic */ lx f630a;
            final /* synthetic */ C01562 f631b;

            C06101(C01562 c01562, lx lxVar) {
                this.f631b = c01562;
                this.f630a = lxVar;
            }

            public final void m600a() {
            }
        }

        C01562(ke keVar) {
            this.f320a = keVar;
        }

        public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            super.rejectedExecution(runnable, threadPoolExecutor);
            lx a = ke.m169a(runnable);
            if (a != null) {
                synchronized (this.f320a.f324d) {
                    this.f320a.f324d.remove(a);
                }
                this.f320a.m173b(a);
                new C06101(this, a).run();
            }
        }
    }

    /* renamed from: com.flurry.sdk.ke.3 */
    class C06113 extends lw {
        final /* synthetic */ lx f632a;
        final /* synthetic */ ke f633b;

        C06113(ke keVar, lx lxVar) {
            this.f633b = keVar;
            this.f632a = lxVar;
        }

        public final void m601a() {
            this.f632a.m671g();
        }
    }

    static {
        f321a = ke.class.getSimpleName();
    }

    public ke(String str, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this.f322b = new jw();
        this.f323c = new HashMap();
        this.f324d = new HashMap();
        this.f325e = new C01551(this, timeUnit, blockingQueue);
        this.f325e.setRejectedExecutionHandler(new C01562(this));
        this.f325e.setThreadFactory(new lk(str));
    }

    public final synchronized void m177a(Object obj, T t) {
        if (!(obj == null || t == null)) {
            m174b(obj, t);
            this.f325e.submit(t);
        }
    }

    public final synchronized void m176a(Object obj) {
        if (obj != null) {
            Collection<lx> hashSet = new HashSet();
            hashSet.addAll(this.f322b.m140a(obj));
            for (lx a : hashSet) {
                m172a(a);
            }
        }
    }

    private synchronized void m172a(T t) {
        if (t != null) {
            Future future;
            synchronized (this.f324d) {
                future = (Future) this.f324d.remove(t);
            }
            m173b((lx) t);
            if (future != null) {
                future.cancel(true);
            }
            new C06113(this, t).run();
        }
    }

    public final synchronized void m179c() {
        Set<Object> hashSet = new HashSet();
        hashSet.addAll(this.f322b.f306a.keySet());
        for (Object a : hashSet) {
            m176a(a);
        }
    }

    public final synchronized long m178b(Object obj) {
        long j;
        if (obj == null) {
            j = 0;
        } else {
            j = (long) this.f322b.m140a(obj).size();
        }
        return j;
    }

    private synchronized void m174b(Object obj, T t) {
        this.f322b.m144a(obj, (Object) t);
        this.f323c.put(t, obj);
    }

    private synchronized void m173b(T t) {
        m175c(this.f323c.get(t), t);
    }

    private synchronized void m175c(Object obj, T t) {
        this.f322b.m147b(obj, t);
        this.f323c.remove(t);
    }

    static /* synthetic */ lx m169a(Runnable runnable) {
        if (runnable instanceof kd) {
            return (lx) ((kd) runnable).m168a();
        }
        if (runnable instanceof lx) {
            return (lx) runnable;
        }
        kf.m182a(6, f321a, "Unknown runnable class: " + runnable.getClass().getName());
        return null;
    }
}
