package gnu.mapping;

import java.util.concurrent.Callable;

public class RunnableClosure implements Callable<Object>, Runnable {
    static int nrunnables = 0;
    Procedure action;
    CallContext context;
    private OutPort err;
    Throwable exception;
    private InPort in;
    String name;
    private OutPort out;
    Object result;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RunnableClosure(Procedure action, CallContext parentContext) {
        StringBuilder append = new StringBuilder().append("r");
        int i = nrunnables;
        nrunnables = i + 1;
        setName(append.append(i).toString());
        this.action = action;
    }

    public RunnableClosure(Procedure action, InPort in, OutPort out, OutPort err) {
        this(action, CallContext.getInstance());
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public RunnableClosure(Procedure action) {
        this(action, CallContext.getInstance());
    }

    public final CallContext getCallContext() {
        return this.context;
    }

    public void run() {
        try {
            Environment env = Environment.getCurrent();
            String name = getName();
            if (!(env == null || env.getSymbol() != null || name == null)) {
                env.setName(name);
            }
            if (this.context == null) {
                this.context = CallContext.getInstance();
            } else {
                CallContext.setInstance(this.context);
            }
            if (this.in != null) {
                InPort.setInDefault(this.in);
            }
            if (this.out != null) {
                OutPort.setOutDefault(this.out);
            }
            if (this.err != null) {
                OutPort.setErrDefault(this.err);
            }
            this.result = this.action.apply0();
        } catch (Throwable ex) {
            this.exception = ex;
        }
    }

    Object getResult() throws Throwable {
        Throwable ex = this.exception;
        if (ex == null) {
            return this.result;
        }
        throw ex;
    }

    public Object call() throws Exception {
        run();
        Throwable ex = this.exception;
        if (ex == null) {
            return this.result;
        }
        if (ex instanceof Exception) {
            throw ((Exception) ex);
        } else if (ex instanceof Error) {
            throw ((Error) ex);
        } else {
            throw new RuntimeException(ex);
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("#<runnable ");
        buf.append(getName());
        buf.append(">");
        return buf.toString();
    }
}
