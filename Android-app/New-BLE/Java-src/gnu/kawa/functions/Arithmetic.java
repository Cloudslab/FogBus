package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Arithmetic {
    public static final int BIGDECIMAL_CODE = 5;
    public static final int BIGINTEGER_CODE = 3;
    public static final int DOUBLE_CODE = 8;
    public static final int FLOAT_CODE = 7;
    public static final int FLONUM_CODE = 9;
    public static final int INTNUM_CODE = 4;
    public static final int INT_CODE = 1;
    public static final int LONG_CODE = 2;
    public static final int NUMERIC_CODE = 11;
    public static final int RATNUM_CODE = 6;
    public static final int REALNUM_CODE = 10;
    static LangObjType typeDFloNum = LangObjType.dflonumType;
    static LangObjType typeIntNum = LangObjType.integerType;
    static ClassType typeNumber = ClassType.make("java.lang.Number");
    static ClassType typeNumeric = ClassType.make("gnu.math.Numeric");
    static LangObjType typeRatNum = LangObjType.rationalType;
    static LangObjType typeRealNum = LangObjType.realType;

    public static int classifyValue(Object value) {
        if (value instanceof Numeric) {
            if (value instanceof IntNum) {
                return 4;
            }
            if (value instanceof RatNum) {
                return 6;
            }
            if (value instanceof DFloNum) {
                return 9;
            }
            if (value instanceof RealNum) {
                return 10;
            }
            return 11;
        } else if (!(value instanceof Number)) {
            return -1;
        } else {
            if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
                return 1;
            }
            if (value instanceof Long) {
                return 2;
            }
            if (value instanceof Float) {
                return 7;
            }
            if (value instanceof Double) {
                return 8;
            }
            if (value instanceof BigInteger) {
                return 3;
            }
            if (value instanceof BigDecimal) {
                return 5;
            }
            return -1;
        }
    }

    public static Type kindType(int kind) {
        switch (kind) {
            case 1:
                return LangPrimType.intType;
            case 2:
                return LangPrimType.longType;
            case 3:
                return ClassType.make("java.math.BigInteger");
            case 4:
                return typeIntNum;
            case 5:
                return ClassType.make("java.math.BigDecimal");
            case 6:
                return typeRatNum;
            case 7:
                return LangPrimType.floatType;
            case 8:
                return LangPrimType.doubleType;
            case 9:
                return typeDFloNum;
            case 10:
                return typeRealNum;
            case 11:
                return typeNumeric;
            default:
                return Type.pointer_type;
        }
    }

    public static int classifyType(Type type) {
        if (type instanceof PrimType) {
            char sig = type.getSignature().charAt(0);
            if (sig == 'V' || sig == 'Z' || sig == Access.CLASS_CONTEXT) {
                return 0;
            }
            if (sig == 'D') {
                return 8;
            }
            if (sig == Access.FIELD_CONTEXT) {
                return 7;
            }
            if (sig == 'J') {
                return 2;
            }
            return 1;
        }
        String tname = type.getName();
        if (type.isSubtype(typeIntNum)) {
            return 4;
        }
        if (type.isSubtype(typeRatNum)) {
            return 6;
        }
        if (type.isSubtype(typeDFloNum)) {
            return 9;
        }
        if ("java.lang.Double".equals(tname)) {
            return 8;
        }
        if ("java.lang.Float".equals(tname)) {
            return 7;
        }
        if ("java.lang.Long".equals(tname)) {
            return 2;
        }
        if ("java.lang.Integer".equals(tname) || "java.lang.Short".equals(tname) || "java.lang.Byte".equals(tname)) {
            return 1;
        }
        if ("java.math.BigInteger".equals(tname)) {
            return 3;
        }
        if ("java.math.BigDecimal".equals(tname)) {
            return 5;
        }
        if (type.isSubtype(typeRealNum)) {
            return 10;
        }
        return type.isSubtype(typeNumeric) ? 11 : 0;
    }

    public static int asInt(Object value) {
        return ((Number) value).intValue();
    }

    public static long asLong(Object value) {
        return ((Number) value).longValue();
    }

    public static float asFloat(Object value) {
        return ((Number) value).floatValue();
    }

    public static double asDouble(Object value) {
        return ((Number) value).doubleValue();
    }

    public static BigInteger asBigInteger(Object value) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof IntNum) {
            return new BigInteger(value.toString());
        }
        return BigInteger.valueOf(((Number) value).longValue());
    }

    public static IntNum asIntNum(BigDecimal value) {
        return IntNum.valueOf(value.toBigInteger().toString(), 10);
    }

    public static IntNum asIntNum(BigInteger value) {
        return IntNum.valueOf(value.toString(), 10);
    }

    public static IntNum asIntNum(Object value) {
        if (value instanceof IntNum) {
            return (IntNum) value;
        }
        if (value instanceof BigInteger) {
            return IntNum.valueOf(value.toString(), 10);
        }
        if (value instanceof BigDecimal) {
            return asIntNum((BigDecimal) value);
        }
        return IntNum.make(((Number) value).longValue());
    }

    public static BigDecimal asBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }
        if ((value instanceof Long) || (value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
            return BigDecimal.valueOf(((Number) value).longValue());
        }
        return new BigDecimal(value.toString());
    }

    public static RatNum asRatNum(Object value) {
        if (value instanceof RatNum) {
            return (RatNum) value;
        }
        if (value instanceof BigInteger) {
            return IntNum.valueOf(value.toString(), 10);
        }
        if (value instanceof BigDecimal) {
            return RatNum.valueOf((BigDecimal) value);
        }
        return IntNum.make(((Number) value).longValue());
    }

    public static Numeric asNumeric(Object value) {
        Numeric n = Numeric.asNumericOrNull(value);
        return n != null ? n : (Numeric) value;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String toString(java.lang.Object r4, int r5) {
        /*
        r1 = 10;
        r0 = classifyValue(r4);
        switch(r0) {
            case 1: goto L_0x0012;
            case 2: goto L_0x001b;
            case 3: goto L_0x0024;
            case 4: goto L_0x002d;
            case 5: goto L_0x0036;
            case 6: goto L_0x0009;
            case 7: goto L_0x0041;
            case 8: goto L_0x004c;
            case 9: goto L_0x004c;
            default: goto L_0x0009;
        };
    L_0x0009:
        r1 = asNumeric(r4);
        r1 = r1.toString(r5);
    L_0x0011:
        return r1;
    L_0x0012:
        r1 = asInt(r4);
        r1 = java.lang.Integer.toString(r1, r5);
        goto L_0x0011;
    L_0x001b:
        r2 = asLong(r4);
        r1 = java.lang.Long.toString(r2, r5);
        goto L_0x0011;
    L_0x0024:
        r1 = asBigInteger(r4);
        r1 = r1.toString(r5);
        goto L_0x0011;
    L_0x002d:
        r1 = asIntNum(r4);
        r1 = r1.toString(r5);
        goto L_0x0011;
    L_0x0036:
        if (r5 != r1) goto L_0x0041;
    L_0x0038:
        r1 = asBigDecimal(r4);
        r1 = r1.toString();
        goto L_0x0011;
    L_0x0041:
        if (r5 != r1) goto L_0x004c;
    L_0x0043:
        r1 = asFloat(r4);
        r1 = java.lang.Float.toString(r1);
        goto L_0x0011;
    L_0x004c:
        if (r5 != r1) goto L_0x0009;
    L_0x004e:
        r2 = asDouble(r4);
        r1 = java.lang.Double.toString(r2);
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.Arithmetic.toString(java.lang.Object, int):java.lang.String");
    }

    public static Object convert(Object value, int code) {
        switch (code) {
            case 1:
                if (value instanceof Integer) {
                    return value;
                }
                return Integer.valueOf(((Number) value).intValue());
            case 2:
                if (value instanceof Long) {
                    return value;
                }
                return Long.valueOf(((Number) value).longValue());
            case 3:
                return asBigInteger(value);
            case 4:
                return asIntNum(value);
            case 5:
                return asBigDecimal(value);
            case 6:
                return asRatNum(value);
            case 7:
                if (value instanceof Float) {
                    return value;
                }
                return Float.valueOf(asFloat(value));
            case 8:
                if (value instanceof Double) {
                    return value;
                }
                return Double.valueOf(asDouble(value));
            case 9:
                if (value instanceof DFloNum) {
                    return value;
                }
                return DFloNum.make(asDouble(value));
            case 10:
                return (RealNum) asNumeric(value);
            case 11:
                return asNumeric(value);
            default:
                return (Number) value;
        }
    }

    public static boolean isExact(Number num) {
        if (num instanceof Numeric) {
            return ((Numeric) num).isExact();
        }
        return ((num instanceof Double) || (num instanceof Float) || (num instanceof BigDecimal)) ? false : true;
    }

    public static Number toExact(Number num) {
        if (num instanceof Numeric) {
            return ((Numeric) num).toExact();
        }
        if ((num instanceof Double) || (num instanceof Float) || (num instanceof BigDecimal)) {
            return DFloNum.toExact(num.doubleValue());
        }
        return num;
    }

    public static Number toInexact(Number num) {
        if (num instanceof Numeric) {
            return ((Numeric) num).toInexact();
        }
        return ((num instanceof Double) || (num instanceof Float) || (num instanceof BigDecimal)) ? num : Double.valueOf(num.doubleValue());
    }
}
