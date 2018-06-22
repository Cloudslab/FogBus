package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Keyword;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import kawa.lang.Record;

public class make extends ProcedureN {
    public int numArgs() {
        return -4095;
    }

    public Object applyN(Object[] args) {
        int nargs = args.length;
        if (nargs == 0) {
            throw new WrongArguments(this, nargs);
        }
        Class clas;
        Object arg_0 = args[0];
        if (arg_0 instanceof Class) {
            clas = (Class) arg_0;
        } else if (arg_0 instanceof ClassType) {
            clas = ((ClassType) arg_0).getReflectClass();
        } else {
            clas = null;
        }
        if (clas == null) {
            throw new WrongType((Procedure) this, 1, arg_0, "class");
        }
        try {
            Object result = clas.newInstance();
            int i = 1;
            while (i < nargs) {
                int i2 = i + 1;
                Keyword key = args[i];
                i = i2 + 1;
                Record.set1(args[i2], key.getName(), result);
            }
            return result;
        } catch (Throwable ex) {
            throw new WrappedException(ex);
        }
    }
}
