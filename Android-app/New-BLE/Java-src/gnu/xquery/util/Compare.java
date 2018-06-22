package gnu.xquery.util;

import android.support.v4.media.TransportMediator;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XTimeType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.DateTime;
import gnu.math.Duration;
import gnu.math.Quantity;
import gnu.math.Unit;

public class Compare extends Procedure2 {
    public static final Compare $Eq = make("=", 8);
    public static final Compare $Ex$Eq = make("!=", 23);
    public static final Compare $Gr = make(">", 16);
    public static final Compare $Gr$Eq = make(">=", 24);
    public static final Compare $Ls = make("<", 4);
    public static final Compare $Ls$Eq = make("<=", 12);
    static final int LENIENT_COMPARISON = 64;
    static final int LENIENT_EQ = 72;
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int RESULT_NAN = -2;
    static final int RESULT_NEQ = -3;
    static final int TRUE_IF_EQU = 8;
    static final int TRUE_IF_GRT = 16;
    static final int TRUE_IF_LSS = 4;
    static final int TRUE_IF_NAN = 2;
    static final int TRUE_IF_NEQ = 1;
    static final int VALUE_COMPARISON = 32;
    public static final Compare valEq = make("eq", 40);
    public static final Compare valGe = make("ge", 56);
    public static final Compare valGt = make("gt", 48);
    public static final Compare valLe = make("le", 44);
    public static final Compare valLt = make("lt", 36);
    public static final Compare valNe = make("ne", 55);
    int flags;

    public static Compare make(String name, int flags) {
        Compare proc = new Compare();
        proc.setName(name);
        proc.setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateCompare");
        proc.flags = flags;
        return proc;
    }

    public static boolean apply(int flags, Object arg1, Object arg2, NamedCollator collator) {
        int index;
        int next;
        if (arg1 instanceof Values) {
            Values values1 = (Values) arg1;
            index = 0;
            while (true) {
                next = values1.nextDataIndex(index);
                if (next < 0) {
                    return false;
                }
                if (apply(flags, values1.getPosNext(index << 1), arg2, collator)) {
                    return true;
                }
                index = next;
            }
        } else if (!(arg2 instanceof Values)) {
            return atomicCompare(flags, KNode.atomicValue(arg1), KNode.atomicValue(arg2), collator);
        } else {
            Values values2 = (Values) arg2;
            index = 0;
            while (true) {
                next = values2.nextDataIndex(index);
                if (next < 0) {
                    return false;
                }
                if (apply(flags, arg1, values2.getPosNext(index << 1), collator)) {
                    return true;
                }
                index = next;
            }
        }
    }

    public static boolean equalityComparison(int flags) {
        return ((flags & 16) != 0) == ((flags & 4) != 0);
    }

    public static boolean atomicCompare(int flags, Object arg1, Object arg2, NamedCollator collator) {
        String str;
        Quantity arg12;
        Quantity arg22;
        if (arg1 instanceof UntypedAtomic) {
            str = arg1.toString();
            if ((flags & 32) != 0) {
                arg12 = str;
            } else if (arg2 instanceof DateTime) {
                arg12 = XTimeType.parseDateTime(str, ((DateTime) arg2).components());
            } else if (arg2 instanceof Duration) {
                arg12 = Duration.parse(str, ((Duration) arg2).unit());
            } else if (arg2 instanceof Number) {
                arg12 = new DFloNum(str);
            } else if (arg2 instanceof Boolean) {
                arg12 = XDataType.booleanType.valueOf(str);
            } else {
                arg1 = str;
            }
        }
        if (arg2 instanceof UntypedAtomic) {
            str = arg2.toString();
            if ((flags & 32) != 0) {
                arg22 = str;
            } else if (arg12 instanceof DateTime) {
                arg22 = XTimeType.parseDateTime(str, ((DateTime) arg12).components());
            } else if (arg12 instanceof Duration) {
                arg22 = Duration.parse(str, ((Duration) arg12).unit());
            } else if (arg12 instanceof Number) {
                arg22 = new DFloNum(str);
            } else if (arg12 instanceof Boolean) {
                arg22 = XDataType.booleanType.valueOf(str);
            } else {
                arg2 = str;
            }
        }
        int comp;
        if ((arg12 instanceof Number) || (arg22 instanceof Number)) {
            if (arg12 instanceof Duration) {
                if (arg22 instanceof Duration) {
                    Duration d1 = (Duration) arg12;
                    Duration d2 = (Duration) arg22;
                    if ((d1.unit != d2.unit || d1.unit == Unit.duration) && !equalityComparison(flags)) {
                        comp = -3;
                    } else {
                        comp = Duration.compare(d1, d2);
                    }
                } else {
                    comp = -3;
                }
            } else if (arg12 instanceof DateTime) {
                if (arg22 instanceof DateTime) {
                    DateTime d12 = (DateTime) arg12;
                    DateTime d22 = (DateTime) arg22;
                    int m1 = d12.components();
                    if (m1 != d22.components()) {
                        comp = -3;
                    } else if (equalityComparison(flags) || m1 == DateTime.TIME_MASK || m1 == 14 || m1 == TransportMediator.KEYCODE_MEDIA_PLAY) {
                        comp = DateTime.compare(d12, d22);
                    } else {
                        comp = -3;
                    }
                } else {
                    comp = -3;
                }
            } else if ((arg22 instanceof Duration) || (arg22 instanceof DateTime)) {
                comp = -3;
            } else {
                comp = NumberCompare.compare(arg12, arg22, false);
            }
            if (comp != -3 || (flags & 64) != 0) {
                return NumberCompare.checkCompareCode(comp, flags);
            }
            throw new IllegalArgumentException("values cannot be compared");
        }
        if (arg12 instanceof Symbol) {
            if (!(arg22 instanceof Symbol) || !equalityComparison(flags)) {
                comp = -3;
            } else if (arg12.equals(arg22)) {
                comp = 0;
            } else {
                comp = -2;
            }
        } else if (arg12 instanceof Boolean) {
            if (arg22 instanceof Boolean) {
                boolean b1 = ((Boolean) arg12).booleanValue();
                boolean b2 = ((Boolean) arg22).booleanValue();
                comp = b1 == b2 ? 0 : b2 ? -1 : 1;
            } else {
                comp = -3;
            }
        } else if ((arg22 instanceof Boolean) || (arg22 instanceof Symbol)) {
            comp = -3;
        } else {
            String str1 = arg12.toString();
            String str2 = arg22.toString();
            if (collator != null) {
                comp = collator.compare(str1, str2);
            } else {
                comp = NamedCollator.codepointCompare(str1, str2);
            }
            comp = comp < 0 ? -1 : comp > 0 ? 1 : 0;
        }
        if (comp != -3 || (flags & 64) != 0) {
            return NumberCompare.checkCompareCode(comp, flags);
        }
        throw new IllegalArgumentException("values cannot be compared");
    }

    public Object apply2(Object arg1, Object arg2) {
        if ((this.flags & 32) == 0) {
            return apply(this.flags, arg1, arg2, null) ? Boolean.TRUE : Boolean.FALSE;
        } else {
            if (arg1 == null || arg1 == Values.empty) {
                return arg1;
            }
            if (arg2 == null || arg2 == Values.empty) {
                return arg2;
            }
            return atomicCompare(this.flags, KNode.atomicValue(arg1), KNode.atomicValue(arg2), null) ? Boolean.TRUE : Boolean.FALSE;
        }
    }
}
