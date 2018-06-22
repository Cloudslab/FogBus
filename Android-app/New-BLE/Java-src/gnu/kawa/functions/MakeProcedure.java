package gnu.kawa.functions;

import gnu.expr.GenericProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;

public class MakeProcedure extends ProcedureN {
    public static final MakeProcedure makeProcedure = new MakeProcedure("make-procedure");

    public MakeProcedure(String name) {
        super(name);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMakeProcedure");
    }

    public static GenericProc makeProcedure$V(Object[] args) {
        return GenericProc.make(args);
    }

    public Object applyN(Object[] args) {
        return GenericProc.make(args);
    }
}
