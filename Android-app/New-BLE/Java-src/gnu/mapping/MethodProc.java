package gnu.mapping;

import gnu.bytecode.ArrayType;
import gnu.bytecode.Type;

public abstract class MethodProc extends ProcedureN {
    public static final int NO_MATCH = -1;
    public static final int NO_MATCH_AMBIGUOUS = -851968;
    public static final int NO_MATCH_BAD_TYPE = -786432;
    public static final int NO_MATCH_TOO_FEW_ARGS = -983040;
    public static final int NO_MATCH_TOO_MANY_ARGS = -917504;
    static final Type[] unknownArgTypes = new Type[]{Type.pointer_type};
    protected Object argTypes;

    public int isApplicable(Type[] argTypes) {
        int argCount = argTypes.length;
        int num = numArgs();
        if (argCount < (num & 4095) || (num >= 0 && argCount > (num >> 12))) {
            return -1;
        }
        int result = 1;
        int i = argCount;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            int code = getParameterType(i).compare(argTypes[i]);
            if (code == -3) {
                return -1;
            }
            if (code < 0) {
                result = 0;
            }
        }
    }

    public int numParameters() {
        int num = numArgs();
        int max = num >> 12;
        return max >= 0 ? max : (num & 4095) + 1;
    }

    protected void resolveParameterTypes() {
        this.argTypes = unknownArgTypes;
    }

    public Type getParameterType(int index) {
        if (!(this.argTypes instanceof Type[])) {
            resolveParameterTypes();
        }
        Type[] atypes = (Type[]) this.argTypes;
        if (index < atypes.length && (index < atypes.length - 1 || maxArgs() >= 0)) {
            return atypes[index];
        }
        if (maxArgs() < 0) {
            Type rtype = atypes[atypes.length - 1];
            if (rtype instanceof ArrayType) {
                return ((ArrayType) rtype).getComponentType();
            }
        }
        return Type.objectType;
    }

    public static RuntimeException matchFailAsException(int code, Procedure proc, Object[] args) {
        int arg = (short) code;
        if ((code & -65536) != NO_MATCH_BAD_TYPE) {
            return new WrongArguments(proc, args.length);
        }
        return new WrongType(proc, arg, arg > 0 ? args[arg - 1] : null);
    }

    public Object applyN(Object[] args) throws Throwable {
        Procedure.checkArgCount(this, args.length);
        CallContext ctx = CallContext.getInstance();
        checkN(args, ctx);
        return ctx.runUntilValue();
    }

    public static MethodProc mostSpecific(MethodProc proc1, MethodProc proc2) {
        boolean not1 = false;
        boolean not2 = false;
        int min1 = proc1.minArgs();
        int min2 = proc2.minArgs();
        int max1 = proc1.maxArgs();
        int max2 = proc2.maxArgs();
        if ((max1 >= 0 && max1 < min2) || (max2 >= 0 && max2 < min1)) {
            return null;
        }
        int limit;
        int num1 = proc1.numParameters();
        int num2 = proc2.numParameters();
        if (num1 > num2) {
            limit = num1;
        } else {
            limit = num2;
        }
        if (max1 != max2) {
            if (max1 < 0) {
                not1 = true;
            }
            if (max2 < 0) {
                not2 = true;
            }
        }
        if (min1 < min2) {
            not1 = true;
        } else if (min1 > min2) {
            not2 = true;
        }
        for (int i = 0; i < limit; i++) {
            int comp = proc1.getParameterType(i).compare(proc2.getParameterType(i));
            if (comp == -1) {
                not2 = true;
                if (not1) {
                    return null;
                }
            }
            if (comp == 1) {
                not1 = true;
                if (not2) {
                    return null;
                }
            }
        }
        if (not2) {
            return proc1;
        }
        return not1 ? proc2 : null;
    }

    public static int mostSpecific(MethodProc[] procs, int length) {
        if (length <= 1) {
            return length - 1;
        }
        MethodProc best = procs[0];
        MethodProc[] bests = null;
        int i = 1;
        int bestn = 0;
        while (i < length) {
            int bestn2;
            MethodProc method = procs[i];
            MethodProc winner;
            if (best != null) {
                winner = mostSpecific(best, method);
                if (winner == null) {
                    if (bests == null) {
                        bests = new MethodProc[length];
                    }
                    bests[0] = best;
                    bests[1] = method;
                    bestn2 = 2;
                    best = null;
                } else if (winner == method) {
                    best = method;
                    bestn2 = i;
                } else {
                    bestn2 = bestn;
                }
            } else {
                int j = 0;
                while (j < bestn) {
                    MethodProc old = bests[j];
                    winner = mostSpecific(old, method);
                    if (winner == old) {
                        bestn2 = bestn;
                        break;
                    } else if (winner == null) {
                        bestn2 = bestn + 1;
                        bests[bestn] = method;
                        break;
                    } else {
                        j++;
                    }
                }
                best = method;
                bestn2 = i;
            }
            i++;
            bestn = bestn2;
        }
        return best == null ? -1 : bestn;
    }
}
