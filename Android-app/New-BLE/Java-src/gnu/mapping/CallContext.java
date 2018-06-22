package gnu.mapping;

import android.support.v4.internal.view.SupportMenu;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.math.IntNum;

public class CallContext {
    public static final int ARG_IN_IVALUE1 = 5;
    public static final int ARG_IN_IVALUE2 = 6;
    public static final int ARG_IN_VALUE1 = 1;
    public static final int ARG_IN_VALUE2 = 2;
    public static final int ARG_IN_VALUE3 = 3;
    public static final int ARG_IN_VALUE4 = 4;
    public static final int ARG_IN_VALUES_ARRAY = 0;
    static ThreadLocal currentContext = new ThreadLocal();
    public Consumer consumer = this.vstack;
    public int count;
    public Object[][] evalFrames;
    public int ivalue1;
    public int ivalue2;
    public int next;
    public int pc;
    public Procedure proc;
    public Object value1;
    public Object value2;
    public Object value3;
    public Object value4;
    public Object[] values;
    public ValueStack vstack = new ValueStack();
    public int where;

    public static void setInstance(CallContext ctx) {
        Thread thread = Thread.currentThread();
        currentContext.set(ctx);
    }

    public static CallContext getOnlyInstance() {
        return (CallContext) currentContext.get();
    }

    public static CallContext getInstance() {
        CallContext ctx = getOnlyInstance();
        if (ctx != null) {
            return ctx;
        }
        ctx = new CallContext();
        setInstance(ctx);
        return ctx;
    }

    Object getArgAsObject(int i) {
        if (i < 8) {
            switch ((this.where >> (i * 4)) & 15) {
                case 1:
                    return this.value1;
                case 2:
                    return this.value2;
                case 3:
                    return this.value3;
                case 4:
                    return this.value4;
                case 5:
                    return IntNum.make(this.ivalue1);
                case 6:
                    return IntNum.make(this.ivalue2);
            }
        }
        return this.values[i];
    }

    public int getArgCount() {
        return this.count;
    }

    public Object getNextArg() {
        if (this.next >= this.count) {
            throw new WrongArguments(null, this.count);
        }
        int i = this.next;
        this.next = i + 1;
        return getArgAsObject(i);
    }

    public int getNextIntArg() {
        if (this.next >= this.count) {
            throw new WrongArguments(null, this.count);
        }
        int i = this.next;
        this.next = i + 1;
        return ((Number) getArgAsObject(i)).intValue();
    }

    public Object getNextArg(Object defaultValue) {
        if (this.next >= this.count) {
            return defaultValue;
        }
        int i = this.next;
        this.next = i + 1;
        return getArgAsObject(i);
    }

    public int getNextIntArg(int defaultValue) {
        if (this.next >= this.count) {
            return defaultValue;
        }
        int i = this.next;
        this.next = i + 1;
        return ((Number) getArgAsObject(i)).intValue();
    }

    public final Object[] getRestArgsArray(int next) {
        Object[] args = new Object[(this.count - next)];
        int i = 0;
        while (next < this.count) {
            int i2 = i + 1;
            int next2 = next + 1;
            args[i] = getArgAsObject(next);
            i = i2;
            next = next2;
        }
        return args;
    }

    public final LList getRestArgsList(int next) {
        LList nil = LList.Empty;
        LList list = nil;
        Pair last = null;
        while (next < this.count) {
            int next2 = next + 1;
            LList pair = new Pair(getArgAsObject(next), nil);
            if (last == null) {
                list = pair;
            } else {
                last.setCdr(pair);
            }
            LList last2 = pair;
            next = next2;
        }
        return list;
    }

    public void lastArg() {
        if (this.next < this.count) {
            throw new WrongArguments(null, this.count);
        }
        this.values = null;
    }

    public Object[] getArgs() {
        if (this.where == 0) {
            return this.values;
        }
        int n = this.count;
        this.next = 0;
        Object[] args = new Object[n];
        for (int i = 0; i < n; i++) {
            args[i] = getNextArg();
        }
        return args;
    }

    public void runUntilDone() throws Throwable {
        while (true) {
            Procedure proc = this.proc;
            if (proc != null) {
                this.proc = null;
                proc.apply(this);
            } else {
                return;
            }
        }
    }

    public final int startFromContext() {
        ValueStack vst = this.vstack;
        int oindex = vst.find(this.consumer);
        vst.ensureSpace(3);
        int i = vst.gapStart;
        int gapStart = i + 1;
        vst.data[i] = '';
        vst.setIntN(gapStart, oindex);
        i = gapStart + 2;
        this.consumer = vst;
        vst.gapStart = i;
        return i;
    }

    public final Object getFromContext(int oldIndex) throws Throwable {
        runUntilDone();
        ValueStack vst = this.vstack;
        Object result = Values.make(vst, oldIndex, vst.gapStart);
        cleanupFromContext(oldIndex);
        return result;
    }

    public final void cleanupFromContext(int oldIndex) {
        ValueStack vst = this.vstack;
        char[] data = vst.data;
        int oindex = (data[oldIndex - 2] << 16) | (data[oldIndex - 1] & SupportMenu.USER_MASK);
        this.consumer = (Consumer) vst.objects[oindex];
        vst.objects[oindex] = null;
        vst.oindex = oindex;
        vst.gapStart = oldIndex - 3;
    }

    public final Object runUntilValue() throws Throwable {
        Consumer consumerSave = this.consumer;
        ValueStack vst = this.vstack;
        this.consumer = vst;
        int dindexSave = vst.gapStart;
        int oindexSave = vst.oindex;
        try {
            runUntilDone();
            Object make = Values.make(vst, dindexSave, vst.gapStart);
            return make;
        } finally {
            this.consumer = consumerSave;
            vst.gapStart = dindexSave;
            vst.oindex = oindexSave;
        }
    }

    public final void runUntilValue(Consumer out) throws Throwable {
        Consumer consumerSave = this.consumer;
        this.consumer = out;
        try {
            runUntilDone();
        } finally {
            this.consumer = consumerSave;
        }
    }

    public void writeValue(Object value) {
        Values.writeValues(value, this.consumer);
    }
}
