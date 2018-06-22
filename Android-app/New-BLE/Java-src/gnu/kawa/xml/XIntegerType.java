package gnu.kawa.xml;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.kawa.functions.Arithmetic;
import gnu.math.IntNum;
import gnu.math.RealNum;
import java.math.BigDecimal;

public class XIntegerType extends XDataType {
    public static final XIntegerType byteType = new XIntegerType("byte", shortType, 11, IntNum.make(-128), IntNum.make((int) TransportMediator.KEYCODE_MEDIA_PAUSE));
    public static final XIntegerType intType = new XIntegerType("int", longType, 9, IntNum.make(Integer.MIN_VALUE), IntNum.make((int) ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
    public static final XIntegerType integerType = new XIntegerType(PropertyTypeConstants.PROPERTY_TYPE_INTEGER, decimalType, 5, null, null);
    public static final XIntegerType longType = new XIntegerType("long", integerType, 8, IntNum.make(Long.MIN_VALUE), IntNum.make(Long.MAX_VALUE));
    public static final XIntegerType negativeIntegerType = new XIntegerType("negativeInteger", nonPositiveIntegerType, 7, null, IntNum.minusOne());
    public static final XIntegerType nonNegativeIntegerType = new XIntegerType("nonNegativeInteger", integerType, 12, IntNum.zero(), null);
    public static final XIntegerType nonPositiveIntegerType = new XIntegerType("nonPositiveInteger", integerType, 6, null, IntNum.zero());
    public static final XIntegerType positiveIntegerType = new XIntegerType("positiveInteger", nonNegativeIntegerType, 17, IntNum.one(), null);
    public static final XIntegerType shortType = new XIntegerType("short", intType, 10, IntNum.make(-32768), IntNum.make(32767));
    static ClassType typeIntNum = ClassType.make("gnu.math.IntNum");
    public static final XIntegerType unsignedByteType = new XIntegerType("unsignedByte", unsignedShortType, 16, IntNum.zero(), IntNum.make(255));
    public static final XIntegerType unsignedIntType = new XIntegerType("unsignedInt", unsignedLongType, 14, IntNum.zero(), IntNum.make(4294967295L));
    public static final XIntegerType unsignedLongType = new XIntegerType("unsignedLong", nonNegativeIntegerType, 13, IntNum.zero(), IntNum.valueOf("18446744073709551615"));
    public static final XIntegerType unsignedShortType = new XIntegerType("unsignedShort", unsignedIntType, 15, IntNum.zero(), IntNum.make((int) SupportMenu.USER_MASK));
    boolean isUnsignedType;
    public final IntNum maxValue;
    public final IntNum minValue;

    public boolean isUnsignedType() {
        return this.isUnsignedType;
    }

    public XIntegerType(String name, XDataType base, int typeCode, IntNum min, IntNum max) {
        this((Object) name, base, typeCode, min, max);
        this.isUnsignedType = name.startsWith("unsigned");
    }

    public XIntegerType(Object name, XDataType base, int typeCode, IntNum min, IntNum max) {
        super(name, typeIntNum, typeCode);
        this.minValue = min;
        this.maxValue = max;
        this.baseType = base;
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof IntNum)) {
            return false;
        }
        if (this == integerType) {
            return true;
        }
        XDataType objType = obj instanceof XInteger ? ((XInteger) obj).getIntegerType() : integerType;
        while (objType != null) {
            if (objType == this) {
                return true;
            }
            objType = objType.baseType;
        }
        return false;
    }

    public Object coerceFromObject(Object obj) {
        return valueOf((IntNum) obj);
    }

    public IntNum valueOf(IntNum value) {
        if (this == integerType) {
            return value;
        }
        if ((this.minValue == null || IntNum.compare(value, this.minValue) >= 0) && (this.maxValue == null || IntNum.compare(value, this.maxValue) <= 0)) {
            return new XInteger(value, this);
        }
        throw new ClassCastException("cannot cast " + value + " to " + this.name);
    }

    public Object cast(Object value) {
        if (value instanceof Boolean) {
            return valueOf(((Boolean) value).booleanValue() ? IntNum.one() : IntNum.zero());
        } else if (value instanceof IntNum) {
            return valueOf((IntNum) value);
        } else {
            if (value instanceof BigDecimal) {
                return valueOf(Arithmetic.asIntNum((BigDecimal) value));
            }
            if (value instanceof RealNum) {
                return valueOf(((RealNum) value).toExactInt(3));
            }
            if (value instanceof Number) {
                return valueOf(RealNum.toExactInt(((Number) value).doubleValue(), 3));
            }
            return super.cast(value);
        }
    }

    public Object valueOf(String value) {
        return valueOf(IntNum.valueOf(value.trim(), 10));
    }

    public IntNum valueOf(String value, int radix) {
        return valueOf(IntNum.valueOf(value.trim(), radix));
    }
}
