package kawa.lang;

import gnu.mapping.CallContext;

public class CalledContinuation extends RuntimeException {
    public Continuation continuation;
    public CallContext ctx;
    public Object[] values;

    CalledContinuation(Object[] values, Continuation continuation, CallContext ctx) {
        super("call/cc called");
        this.values = values;
        this.continuation = continuation;
        this.ctx = ctx;
    }
}
