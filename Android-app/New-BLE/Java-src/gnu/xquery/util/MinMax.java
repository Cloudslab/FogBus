package gnu.xquery.util;

import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.xml.TextUtils;

public class MinMax {
    public static Object min(Object arg, NamedCollator collation) {
        return minMax(arg, false, collation);
    }

    public static Object max(Object arg, NamedCollator collation) {
        return minMax(arg, true, collation);
    }

    public static Object minMax(Object arg, boolean returnMax, NamedCollator collation) {
        int flags = 16;
        if (arg instanceof Values) {
            TreeList tlist = (TreeList) arg;
            int pos = 0;
            if (!returnMax) {
                flags = 4;
            }
            Object cur = tlist.getPosNext(0);
            if (cur == Sequence.eofValue) {
                return Values.empty;
            }
            Object result = convert(cur);
            while (true) {
                pos = tlist.nextPos(pos);
                cur = tlist.getPosNext(pos);
                if (cur == Sequence.eofValue) {
                    return result;
                }
                cur = convert(cur);
                if ((result instanceof Number) || (cur instanceof Number)) {
                    int code1 = Arithmetic.classifyValue(result);
                    int code2 = Arithmetic.classifyValue(cur);
                    int rcode = NumberCompare.compare(result, code1, cur, code2, false);
                    if (rcode == -3) {
                        throw new IllegalArgumentException("values cannot be compared");
                    }
                    int code;
                    boolean castNeeded;
                    if (code1 < code2) {
                        code = code2;
                    } else {
                        code = code1;
                    }
                    if (rcode == -2) {
                        result = NumberValue.NaN;
                        castNeeded = true;
                    } else if (NumberCompare.checkCompareCode(rcode, flags)) {
                        castNeeded = code != code1;
                    } else {
                        if (code != code2) {
                            castNeeded = true;
                        } else {
                            castNeeded = false;
                        }
                        result = cur;
                    }
                    if (castNeeded) {
                        result = Arithmetic.convert(result, code);
                    }
                } else if (!Compare.atomicCompare(flags, result, cur, collation)) {
                    result = cur;
                }
            }
        } else {
            arg = convert(arg);
            Compare.atomicCompare(16, arg, arg, collation);
            return arg;
        }
    }

    static Object convert(Object arg) {
        arg = KNode.atomicValue(arg);
        if (arg instanceof UntypedAtomic) {
            return (Double) XDataType.doubleType.valueOf(TextUtils.stringValue(arg));
        }
        return arg;
    }
}
