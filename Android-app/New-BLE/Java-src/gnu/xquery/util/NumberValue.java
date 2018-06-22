package gnu.xquery.util;

import gnu.kawa.functions.Arithmetic;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.kawa.xml.XIntegerType;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RealNum;
import gnu.xml.TextUtils;
import java.math.BigDecimal;

public class NumberValue extends Procedure1 {
    public static final Double NaN = new Double(Double.NaN);
    public static final NumberValue numberValue = new NumberValue();

    public static boolean isNaN(Object arg) {
        return ((arg instanceof Double) || (arg instanceof Float) || (arg instanceof DFloNum)) && Double.isNaN(((Number) arg).doubleValue());
    }

    public Object apply1(Object arg) {
        if (!(arg == Values.empty || arg == null)) {
            try {
                return numberValue(arg);
            } catch (Throwable th) {
            }
        }
        return NaN;
    }

    public static Number numberCast(Object value) {
        if (value == Values.empty || value == null) {
            return null;
        }
        if (value instanceof Values) {
            Values vals = (Values) value;
            int ipos = vals.startPos();
            int count = 0;
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos == 0) {
                    break;
                } else if (count > 0) {
                    throw new ClassCastException("non-singleton sequence cast to number");
                } else {
                    value = vals.getPosPrevious(ipos);
                    count++;
                }
            }
        }
        if ((value instanceof KNode) || (value instanceof UntypedAtomic)) {
            return (Double) XDataType.doubleType.valueOf(TextUtils.stringValue(value));
        }
        return (Number) value;
    }

    public static Object numberValue(Object value) {
        value = KNode.atomicValue(value);
        double d;
        if ((value instanceof UntypedAtomic) || (value instanceof String)) {
            try {
                return XDataType.doubleType.valueOf(TextUtils.stringValue(value));
            } catch (Throwable th) {
                d = Double.NaN;
            }
        } else {
            if (!(value instanceof Number) || (!(value instanceof RealNum) && (value instanceof Numeric))) {
                d = Double.NaN;
            } else {
                d = ((Number) value).doubleValue();
            }
            return XDataType.makeDouble(d);
        }
    }

    public static Object abs(Object value) {
        if (value == null || value == Values.empty) {
            return value;
        }
        Number value2 = numberCast(value);
        if (value2 instanceof Double) {
            Double d = (Double) value2;
            long bits = Double.doubleToRawLongBits(d.doubleValue());
            if (bits < 0) {
                return Double.valueOf(Double.longBitsToDouble(bits & Long.MAX_VALUE));
            }
            return d;
        } else if (value2 instanceof Float) {
            Float d2 = (Float) value2;
            int bits2 = Float.floatToRawIntBits(d2.floatValue());
            if (bits2 < 0) {
                return Float.valueOf(Float.intBitsToFloat(bits2 & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
            }
            return d2;
        } else if (!(value2 instanceof BigDecimal)) {
            return ((Numeric) value2).abs();
        } else {
            BigDecimal dec = (BigDecimal) value2;
            if (dec.signum() < 0) {
                dec = dec.negate();
            }
            return dec;
        }
    }

    public static Object floor(Object val) {
        Number value = numberCast(val);
        if (value == null) {
            return val;
        }
        if (value instanceof Double) {
            return XDataType.makeDouble(Math.floor(((Double) value).doubleValue()));
        }
        if (value instanceof Float) {
            return XDataType.makeFloat((float) Math.floor((double) ((Float) value).floatValue()));
        }
        if (value instanceof BigDecimal) {
            return Arithmetic.asIntNum(((BigDecimal) value).divide(XDataType.DECIMAL_ONE, 0, 3).toBigInteger());
        }
        return ((RealNum) value).toInt(1);
    }

    public static Object ceiling(Object val) {
        Number value = numberCast(val);
        if (value == null) {
            return val;
        }
        if (value instanceof Double) {
            return XDataType.makeDouble(Math.ceil(((Double) value).doubleValue()));
        }
        if (value instanceof Float) {
            return XDataType.makeFloat((float) Math.ceil((double) ((Float) value).floatValue()));
        }
        if (value instanceof BigDecimal) {
            return Arithmetic.asIntNum(((BigDecimal) value).divide(XDataType.DECIMAL_ONE, 0, 2).toBigInteger());
        }
        return ((RealNum) value).toInt(2);
    }

    public static Object round(Object arg) {
        int mode = 4;
        Number value = numberCast(arg);
        if (value == null) {
            return arg;
        }
        if (value instanceof Double) {
            double val = ((Double) value).doubleValue();
            if (val < -0.5d || val > 0.0d || (val >= 0.0d && Double.doubleToLongBits(val) >= 0)) {
                val = Math.floor(val + 0.5d);
            } else {
                val = -0.0d;
            }
            return XDataType.makeDouble(val);
        } else if (value instanceof Float) {
            float val2 = ((Float) value).floatValue();
            if (((double) val2) < -0.5d || ((double) val2) > 0.0d || (((double) val2) >= 0.0d && Float.floatToIntBits(val2) >= 0)) {
                val2 = (float) Math.floor(((double) val2) + 0.5d);
            } else {
                val2 = -0.0f;
            }
            return XDataType.makeFloat(val2);
        } else if (!(value instanceof BigDecimal)) {
            return ((RealNum) value).toInt(4);
        } else {
            BigDecimal dec = (BigDecimal) value;
            if (dec.signum() < 0) {
                mode = 5;
            }
            return Arithmetic.asIntNum(dec.divide(XDataType.DECIMAL_ONE, 0, mode).toBigInteger());
        }
    }

    public static Object roundHalfToEven(Object value, IntNum precision) {
        Number number = numberCast(value);
        if (number == null) {
            return value;
        }
        if ((value instanceof Double) || (value instanceof Float)) {
            double v = ((Number) value).doubleValue();
            if (v == 0.0d || Double.isInfinite(v) || Double.isNaN(v)) {
                return value;
            }
        }
        BigDecimal dec = ((BigDecimal) XDataType.decimalType.cast(number)).setScale(precision.intValue(), 6);
        if (number instanceof Double) {
            return XDataType.makeDouble(dec.doubleValue());
        }
        if (number instanceof Float) {
            return XDataType.makeFloat(dec.floatValue());
        }
        return number instanceof IntNum ? XIntegerType.integerType.cast(dec) : dec;
    }

    public static Object roundHalfToEven(Object value) {
        return roundHalfToEven(value, IntNum.zero());
    }
}
