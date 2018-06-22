package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;

public abstract class ArithOp extends ProcedureN {
    static final int ADD = 1;
    public static final int AND = 13;
    public static final int ASHIFT_GENERAL = 9;
    public static final int ASHIFT_LEFT = 10;
    public static final int ASHIFT_RIGHT = 11;
    public static final int DIVIDE_GENERIC = 4;
    public static final int DIVIDE_INEXACT = 5;
    public static final int IOR = 14;
    public static final int LSHIFT_RIGHT = 12;
    public static final int MODULO = 8;
    static final int MUL = 3;
    public static final int NOT = 16;
    public static final int QUOTIENT = 6;
    public static final int QUOTIENT_EXACT = 7;
    static final int SUB = 2;
    public static final int XOR = 15;
    final int op;

    public ArithOp(String name, int op) {
        super(name);
        this.op = op;
    }

    public Object defaultResult() {
        return IntNum.zero();
    }

    public boolean isSideEffectFree() {
        return true;
    }

    public static int classify(Type type) {
        if (type instanceof PrimType) {
            char sig = type.getSignature().charAt(0);
            if (sig == 'V' || sig == 'Z' || sig == Access.CLASS_CONTEXT) {
                return 0;
            }
            if (sig == 'D' || sig == Access.FIELD_CONTEXT) {
                return 3;
            }
            return 4;
        } else if (type.isSubtype(Arithmetic.typeIntNum)) {
            return 4;
        } else {
            if (type.isSubtype(Arithmetic.typeDFloNum)) {
                return 3;
            }
            if (type.isSubtype(Arithmetic.typeRealNum)) {
                return 2;
            }
            return type.isSubtype(Arithmetic.typeNumeric) ? 1 : 0;
        }
    }
}
