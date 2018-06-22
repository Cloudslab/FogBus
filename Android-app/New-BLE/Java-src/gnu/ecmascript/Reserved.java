package gnu.ecmascript;

import gnu.mapping.Procedure;

public class Reserved {
    public static final int BREAK_TOKEN = 35;
    public static final int CONTINUE_TOKEN = 34;
    public static final int ELSE_TOKEN = 38;
    public static final int FOR_TOKEN = 33;
    public static final int FUNCTION_TOKEN = 41;
    public static final int IF_TOKEN = 31;
    public static final int LESS_OP = 5;
    public static final int LSHIFT_OP = 4;
    public static final int MINUS_OP = 2;
    public static final int NEW_TOKEN = 39;
    public static final int PLUS_OP = 1;
    public static final int RETURN_TOKEN = 36;
    public static final int THIS_TOKEN = 40;
    public static final int TIMES_OP = 3;
    public static final int VAR_TOKEN = 30;
    public static final int WHILE_TOKEN = 32;
    public static final int WITH_TOKEN = 37;
    static final Reserved opBitAnd = new Reserved("&", 5, 0);
    static final Reserved opBitOr = new Reserved("|", 3, 0);
    static final Reserved opBitXor = new Reserved("^", 4, 0);
    static final Reserved opBoolAnd = new Reserved("&&", 2, 0);
    static final Reserved opBoolOr = new Reserved("||", 1, 0);
    static final Reserved opDivide = new Reserved("/", 10, 0);
    static final Reserved opEqual = new Reserved("=", 6, 0);
    static final Reserved opGreater = new Reserved(">", 7, 0);
    static final Reserved opGreaterEqual = new Reserved(">=", 7, 0);
    static final Reserved opLess = new Reserved("<", 7, 5);
    static final Reserved opLessEqual = new Reserved("<=", 7, 0);
    static final Reserved opLshift = new Reserved("<<", 8, 4);
    static final Reserved opMinus = new Reserved("-", 9, 2);
    static Reserved opMinusMinus;
    static final Reserved opNotEqual = new Reserved("!=", 6, 0);
    static final Reserved opPlus = new Reserved("+", 9, 1);
    static Reserved opPlusPlus;
    static final Reserved opRemainder = new Reserved("%", 10, 0);
    static final Reserved opRshiftSigned = new Reserved(">>", 8, 0);
    static final Reserved opRshiftUnsigned = new Reserved(">>>", 8, 0);
    static final Reserved opTimes = new Reserved("*", 10, 3);
    String name;
    int prio;
    Procedure proc;

    public Reserved(String name, int prio, Procedure proc) {
        this.name = name;
        this.prio = prio;
        this.proc = proc;
    }

    public Reserved(String name, int prio) {
        this.name = name;
        this.prio = prio;
    }

    public Reserved(String name, int prio, int op) {
        this.name = name;
        this.prio = prio;
        this.proc = new BinaryOp(name, op);
    }

    public String toString() {
        return "[Reserved \"" + this.name + "\" prio:" + this.prio + "]";
    }

    public boolean isAssignmentOp() {
        return false;
    }
}
