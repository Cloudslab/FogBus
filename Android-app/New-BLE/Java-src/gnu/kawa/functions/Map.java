package gnu.kawa.functions;

import gnu.expr.Declaration;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;

public class Map extends ProcedureN {
    final Declaration applyFieldDecl;
    final ApplyToArgs applyToArgs;
    boolean collect;
    final IsEq isEq;

    public Map(boolean collect, ApplyToArgs applyToArgs, Declaration applyFieldDecl, IsEq isEq) {
        super(collect ? "map" : "for-each");
        this.collect = collect;
        this.applyToArgs = applyToArgs;
        this.applyFieldDecl = applyFieldDecl;
        this.isEq = isEq;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMap");
    }

    public static Object map1(Procedure proc, Object list) throws Throwable {
        Object result = LList.Empty;
        Pair last = null;
        LList list2;
        while (list2 != LList.Empty) {
            Pair pair = (Pair) list2;
            Pair new_pair = new Pair(proc.apply1(pair.getCar()), LList.Empty);
            if (last == null) {
                result = new_pair;
            } else {
                last.setCdr(new_pair);
            }
            last = new_pair;
            list2 = pair.getCdr();
        }
        return result;
    }

    public static void forEach1(Procedure proc, Object list) throws Throwable {
        LList list2;
        while (list2 != LList.Empty) {
            Pair pair = (Pair) list2;
            proc.apply1(pair.getCar());
            list2 = pair.getCdr();
        }
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        if (arg1 instanceof Procedure) {
            Procedure proc = (Procedure) arg1;
            if (this.collect) {
                return map1(proc, arg2);
            }
            forEach1(proc, arg2);
            return Values.empty;
        }
        return applyN(new Object[]{arg1, arg2});
    }

    public Object applyN(Object[] args) throws Throwable {
        int arity = args.length - 1;
        Procedure proc;
        if (arity == 1 && (args[0] instanceof Procedure)) {
            proc = (Procedure) args[0];
            if (this.collect) {
                return map1(proc, args[1]);
            }
            forEach1(proc, args[1]);
            return Values.empty;
        }
        Object obj;
        int need_apply;
        Object[] each_args;
        Pair last = null;
        if (this.collect) {
            obj = LList.Empty;
        } else {
            obj = Values.empty;
        }
        Object[] rest = new Object[arity];
        System.arraycopy(args, 1, rest, 0, arity);
        if (args[0] instanceof Procedure) {
            need_apply = 0;
            each_args = new Object[arity];
            proc = (Procedure) args[0];
        } else {
            need_apply = 1;
            each_args = new Object[(arity + 1)];
            each_args[0] = args[0];
            proc = this.applyToArgs;
        }
        while (true) {
            for (int i = 0; i < arity; i++) {
                LList list = rest[i];
                if (list == LList.Empty) {
                    return obj;
                }
                Pair pair = (Pair) list;
                each_args[need_apply + i] = pair.getCar();
                rest[i] = pair.getCdr();
            }
            Object value = proc.applyN(each_args);
            if (this.collect) {
                Pair new_pair = new Pair(value, LList.Empty);
                if (last == null) {
                    obj = new_pair;
                } else {
                    last.setCdr(new_pair);
                }
                last = new_pair;
            }
        }
    }
}
