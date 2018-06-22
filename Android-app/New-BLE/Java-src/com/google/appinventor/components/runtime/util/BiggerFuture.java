package com.google.appinventor.components.runtime.util;

import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.RunnableClosure;

public class BiggerFuture extends Thread {
    public BiggerFuture(Procedure action, InPort in, OutPort out, OutPort err, String threadName, long stackSize) {
        super(new ThreadGroup("biggerthreads"), new RunnableClosure(action, in, out, err), threadName, stackSize);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("#<future ");
        buf.append(getName());
        buf.append(">");
        return buf.toString();
    }
}
