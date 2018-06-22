package gnu.mapping;

public class Future extends Thread {
    public RunnableClosure closure;

    public Future(Procedure action, CallContext parentContext) {
        this.closure = new RunnableClosure(action, parentContext);
    }

    public Future(Procedure action, InPort in, OutPort out, OutPort err) {
        this.closure = new RunnableClosure(action, in, out, err);
    }

    public Future(Procedure action) {
        this.closure = new RunnableClosure(action);
    }

    public static Future make(Procedure action, Environment penvironment, InPort in, OutPort out, OutPort err) {
        Environment saveEnv = Environment.setSaveCurrent(penvironment);
        try {
            Future future = new Future(action, in, out, err);
            return future;
        } finally {
            Environment.restoreCurrent(saveEnv);
        }
    }

    public final CallContext getCallContext() {
        return this.closure.getCallContext();
    }

    public void run() {
        this.closure.run();
    }

    public Object waitForResult() throws Throwable {
        try {
            join();
            Throwable ex = this.closure.exception;
            if (ex == null) {
                return this.closure.result;
            }
            throw ex;
        } catch (InterruptedException e) {
            throw new RuntimeException("thread join [force] was interrupted");
        }
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("#<future ");
        buf.append(getName());
        buf.append(">");
        return buf.toString();
    }
}
