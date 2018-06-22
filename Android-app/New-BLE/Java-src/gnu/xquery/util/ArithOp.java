package gnu.xquery.util;

import gnu.bytecode.Access;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1or2;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RealNum;
import gnu.math.Unit;
import gnu.xml.TextUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class ArithOp extends Procedure1or2 implements Inlineable {
    static final BigInteger TEN = BigInteger.valueOf(10);
    public static final ArithOp add = new ArithOp("+", '+', 2);
    public static final ArithOp div = new ArithOp("div", 'd', 2);
    public static final ArithOp idiv = new ArithOp("idiv", 'i', 2);
    public static final ArithOp minus = new ArithOp("-", Access.METHOD_CONTEXT, 1);
    public static final ArithOp mod = new ArithOp("mod", 'm', 2);
    public static final ArithOp mul = new ArithOp("*", '*', 2);
    public static final ArithOp plus = new ArithOp("+", 'P', 1);
    public static final ArithOp sub = new ArithOp("-", '-', 2);
    char op;

    ArithOp(String name, char op, int nargs) {
        super(name);
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateArithOp");
        this.op = op;
    }

    public Object apply1(Object arg1) throws Throwable {
        if (arg1 == Values.empty || arg1 == null) {
            return arg1;
        }
        if ((arg1 instanceof KNode) || (arg1 instanceof UntypedAtomic)) {
            arg1 = XDataType.doubleType.valueOf(TextUtils.stringValue(arg1));
        }
        switch (this.op) {
            case 'M':
                switch (Arithmetic.classifyValue(arg1)) {
                    case 7:
                        return XDataType.makeFloat(-Arithmetic.asFloat(arg1));
                    case 8:
                        return XDataType.makeDouble(-Arithmetic.asDouble(arg1));
                    default:
                        if (arg1 instanceof Numeric) {
                            return ((Numeric) arg1).neg();
                        }
                        return AddOp.apply2(-1, IntNum.zero(), arg1);
                }
            case 'P':
                return AddOp.apply2(1, IntNum.zero(), arg1);
            default:
                throw new UnsupportedOperationException(getName());
        }
    }

    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        return d1.divide(d2, MathContext.DECIMAL128);
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        if (arg1 == Values.empty || arg1 == null) {
            return arg1;
        }
        if (arg2 == Values.empty || arg2 == null) {
            return arg2;
        }
        if ((arg1 instanceof KNode) || (arg1 instanceof UntypedAtomic)) {
            arg1 = XDataType.doubleType.valueOf(TextUtils.stringValue(arg1));
        }
        if ((arg2 instanceof KNode) || (arg2 instanceof UntypedAtomic)) {
            arg2 = XDataType.doubleType.valueOf(TextUtils.stringValue(arg2));
        }
        switch (this.op) {
            case '*':
                return MultiplyOp.$St.apply2(arg1, arg2);
            case '+':
                return AddOp.apply2(1, arg1, arg2);
            case '-':
                return AddOp.apply2(-1, arg1, arg2);
            default:
                int code;
                int code1 = Arithmetic.classifyValue(arg1);
                int code2 = Arithmetic.classifyValue(arg2);
                if (code1 < code2) {
                    code = code2;
                } else {
                    code = code1;
                }
                switch (this.op) {
                    case 'd':
                        if (code1 >= 0 && code2 >= 0) {
                            if (code <= 6) {
                                return div((BigDecimal) XDataType.decimalType.cast(arg1), (BigDecimal) XDataType.decimalType.cast(arg2));
                            }
                            if (code == 7) {
                                return new Float(((Number) arg1).floatValue() / ((Number) arg2).floatValue());
                            }
                            if (code == 8) {
                                return new Double(((Number) arg1).doubleValue() / ((Number) arg2).doubleValue());
                            }
                            if ((arg1 instanceof Duration) && (arg2 instanceof Duration)) {
                                Duration dur1 = (Duration) arg1;
                                Duration dur2 = (Duration) arg2;
                                if (dur1.unit() == Unit.second && dur2.unit() == Unit.second) {
                                    long s1 = dur1.getTotalSeconds();
                                    long s2 = dur2.getTotalSeconds();
                                    return div(TimeUtils.secondsBigDecimalFromDuration(s1, dur1.getNanoSecondsOnly()), TimeUtils.secondsBigDecimalFromDuration(s2, dur2.getNanoSecondsOnly()));
                                }
                                if (dur1.unit() == Unit.month && dur2.unit() == Unit.month) {
                                    return div(BigDecimal.valueOf((long) dur1.getTotalMonths()), BigDecimal.valueOf((long) dur2.getTotalMonths()));
                                }
                                throw new ArithmeticException("divide of incompatible durations");
                            } else if (code >= 0) {
                                return Arithmetic.asNumeric(arg1).div(Arithmetic.asNumeric(arg2));
                            }
                        }
                        break;
                    case 'i':
                        if (code1 >= 0 && code2 >= 0) {
                            if (code <= 4) {
                                return IntNum.quotient(Arithmetic.asIntNum(arg1), Arithmetic.asIntNum(arg2));
                            }
                            if (code <= 6) {
                                return Arithmetic.asIntNum(((BigDecimal) XDataType.decimalType.cast(arg1)).divide((BigDecimal) XDataType.decimalType.cast(arg2), 0, 1));
                            }
                            if (code <= 7) {
                                return RealNum.toExactInt((double) (((Number) arg1).floatValue() / ((Number) arg2).floatValue()), 3);
                            }
                            return RealNum.toExactInt(((Number) arg1).doubleValue() / ((Number) arg2).doubleValue(), 3);
                        }
                    case 'm':
                        if (code1 >= 0 && code2 >= 0) {
                            if (code <= 4) {
                                return IntNum.remainder(Arithmetic.asIntNum(arg1), Arithmetic.asIntNum(arg2));
                            }
                            if (code <= 6) {
                                return sub.apply2(arg1, mul.apply2(idiv.apply2(arg1, arg2), arg2));
                            } else if (code <= 7) {
                                return XDataType.makeFloat(Arithmetic.asFloat(arg1) % Arithmetic.asFloat(arg2));
                            } else {
                                if (code <= 9) {
                                    double d = Arithmetic.asDouble(arg1) % Arithmetic.asDouble(arg2);
                                    if (code == 9) {
                                        return DFloNum.make(d);
                                    }
                                    return XDataType.makeDouble(d);
                                }
                            }
                        }
                        break;
                }
                throw new UnsupportedOperationException(getName());
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ApplyExp.compile(exp, comp, target);
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}
