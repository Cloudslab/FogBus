package gnu.kawa.functions;

import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

public class Apply extends ProcedureN {
    ApplyToArgs applyToArgs;

    public Apply(String name, ApplyToArgs applyToArgs) {
        super(name);
        this.applyToArgs = applyToArgs;
    }

    public static Object[] getArguments(Object[] args, int skip, Procedure proc) {
        int count = args.length;
        if (count < skip + 1) {
            throw new WrongArguments("apply", 2, "(apply proc [args] args) [count:" + count + " skip:" + skip + "]");
        }
        int last_count;
        Pair last = args[count - 1];
        if (last instanceof Object[]) {
            Object[] last_arr = (Object[]) last;
            if (count == 2) {
                return last_arr;
            }
            last_count = last_arr.length;
        } else if (last instanceof Sequence) {
            last_count = last.size();
        } else {
            last_count = -1;
        }
        if (last_count < 0) {
            throw new WrongType(proc, count, (Object) last, "sequence or array");
        }
        Object[] proc_args = new Object[(last_count + ((count - skip) - 1))];
        int i = 0;
        while (i < (count - skip) - 1) {
            proc_args[i] = args[i + skip];
            i++;
        }
        if (last instanceof Object[]) {
            System.arraycopy((Object[]) last, 0, proc_args, i, last_count);
        } else {
            int i2;
            while (last instanceof Pair) {
                Pair pair = last;
                i2 = i + 1;
                proc_args[i] = pair.getCar();
                last = pair.getCdr();
                last_count--;
                i = i2;
            }
            if (last_count > 0) {
                Sequence last_seq = last;
                int j = 0;
                i2 = i;
                while (j < last_count) {
                    i = i2 + 1;
                    proc_args[i2] = last_seq.get(j);
                    j++;
                    i2 = i;
                }
                i = i2;
            }
        }
        return proc_args;
    }

    public Object applyN(Object[] args) throws Throwable {
        return this.applyToArgs.applyN(getArguments(args, 0, this));
    }

    public void apply(CallContext ctx) throws Throwable {
        this.applyToArgs.checkN(getArguments(ctx.getArgs(), 0, this), ctx);
    }
}
