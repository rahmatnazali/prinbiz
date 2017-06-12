package com.flurry.sdk;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class kb {
    private static kb f313a;
    private final jw<String, ko<ka<?>>> f314b;
    private final jw<ko<ka<?>>, String> f315c;

    /* renamed from: com.flurry.sdk.kb.1 */
    class C06071 extends lw {
        final /* synthetic */ ka f623a;
        final /* synthetic */ jz f624b;
        final /* synthetic */ kb f625c;

        C06071(kb kbVar, ka kaVar, jz jzVar) {
            this.f625c = kbVar;
            this.f623a = kaVar;
            this.f624b = jzVar;
        }

        public final void m597a() {
            this.f623a.m156a(this.f624b);
        }
    }

    static {
        f313a = null;
    }

    public static synchronized kb m157a() {
        kb kbVar;
        synchronized (kb.class) {
            if (f313a == null) {
                f313a = new kb();
            }
            kbVar = f313a;
        }
        return kbVar;
    }

    public static synchronized void m158b() {
        synchronized (kb.class) {
            if (f313a != null) {
                f313a.m160c();
                f313a = null;
            }
        }
    }

    private kb() {
        this.f314b = new jw();
        this.f315c = new jw();
    }

    public final synchronized void m164a(String str, ka<?> kaVar) {
        boolean z = false;
        synchronized (this) {
            if (!(TextUtils.isEmpty(str) || kaVar == null)) {
                Object koVar = new ko(kaVar);
                List a = this.f314b.m141a((Object) str, false);
                if (a != null) {
                    z = a.contains(koVar);
                }
                if (!z) {
                    this.f314b.m144a((Object) str, koVar);
                    this.f315c.m144a(koVar, (Object) str);
                }
            }
        }
    }

    public final synchronized void m166b(String str, ka<?> kaVar) {
        if (!TextUtils.isEmpty(str)) {
            ko koVar = new ko(kaVar);
            this.f314b.m147b(str, koVar);
            this.f315c.m147b(koVar, str);
        }
    }

    public final synchronized void m163a(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (ko b : this.f314b.m140a((Object) str)) {
                this.f315c.m147b(b, str);
            }
            this.f314b.m146b(str);
        }
    }

    public final synchronized void m162a(ka<?> kaVar) {
        if (kaVar != null) {
            Object koVar = new ko(kaVar);
            for (String b : this.f315c.m140a(koVar)) {
                this.f314b.m147b(b, koVar);
            }
            this.f315c.m146b(koVar);
        }
    }

    private synchronized void m160c() {
        this.f314b.m142a();
        this.f315c.m142a();
    }

    public final synchronized int m165b(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = this.f314b.m140a((Object) str).size();
        }
        return i;
    }

    private synchronized List<ka<?>> m159c(String str) {
        List<ka<?>> emptyList;
        if (TextUtils.isEmpty(str)) {
            emptyList = Collections.emptyList();
        } else {
            List<ka<?>> arrayList = new ArrayList();
            Iterator it = this.f314b.m140a((Object) str).iterator();
            while (it.hasNext()) {
                ka kaVar = (ka) ((ko) it.next()).get();
                if (kaVar == null) {
                    it.remove();
                } else {
                    arrayList.add(kaVar);
                }
            }
            emptyList = arrayList;
        }
        return emptyList;
    }

    public final void m161a(jz jzVar) {
        if (jzVar != null) {
            for (ka c06071 : m159c(jzVar.m154a())) {
                jr.m120a().m126b(new C06071(this, c06071, jzVar));
            }
        }
    }
}
