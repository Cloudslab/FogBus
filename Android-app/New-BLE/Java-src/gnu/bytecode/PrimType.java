package gnu.bytecode;

public class PrimType extends Type {
    private static final String numberHierarchy = "A:java.lang.Byte;B:java.lang.Short;C:java.lang.Integer;D:java.lang.Long;E:gnu.math.IntNum;E:java.gnu.math.BitInteger;G:gnu.math.RatNum;H:java.lang.Float;I:java.lang.Double;I:gnu.math.DFloNum;J:gnu.math.RealNum;K:gnu.math.Complex;L:gnu.math.Quantity;K:gnu.math.Numeric;N:java.lang.Number;";

    public PrimType(String nam, String sig, int siz, Class reflectClass) {
        super(nam, sig);
        this.size = siz;
        this.reflectClass = reflectClass;
        Type.registerTypeForClass(reflectClass, this);
    }

    protected PrimType(PrimType type) {
        super(type.this_name, type.signature);
        this.size = type.size;
        this.reflectClass = type.reflectClass;
    }

    public Object coerceFromObject(Object obj) {
        if (obj.getClass() == this.reflectClass) {
            return obj;
        }
        char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
        switch (sig1) {
            case 'B':
                return Byte.valueOf(((Number) obj).byteValue());
            case 'D':
                return Double.valueOf(((Number) obj).doubleValue());
            case 'F':
                return Float.valueOf(((Number) obj).floatValue());
            case 'I':
                return Integer.valueOf(((Number) obj).intValue());
            case 'J':
                return Long.valueOf(((Number) obj).longValue());
            case 'S':
                return Short.valueOf(((Number) obj).shortValue());
            case 'Z':
                return Boolean.valueOf(((Boolean) obj).booleanValue());
            default:
                throw new ClassCastException("don't know how to coerce " + obj.getClass().getName() + " to " + getName());
        }
    }

    public char charValue(Object value) {
        return ((Character) value).charValue();
    }

    public static boolean booleanValue(Object value) {
        return !(value instanceof Boolean) || ((Boolean) value).booleanValue();
    }

    public ClassType boxedType() {
        String cname;
        switch (getSignature().charAt(0)) {
            case 'B':
                cname = "java.lang.Byte";
                break;
            case 'C':
                cname = "java.lang.Character";
                break;
            case 'D':
                cname = "java.lang.Double";
                break;
            case 'F':
                cname = "java.lang.Float";
                break;
            case 'I':
                cname = "java.lang.Integer";
                break;
            case 'J':
                cname = "java.lang.Long";
                break;
            case 'S':
                cname = "java.lang.Short";
                break;
            case 'Z':
                cname = "java.lang.Boolean";
                break;
            default:
                cname = null;
                break;
        }
        return ClassType.make(cname);
    }

    public void emitCoerceToObject(CodeAttr code) {
        char sig1 = getSignature().charAt(0);
        ClassType clas = boxedType();
        if (sig1 == 'Z') {
            code.emitIfIntNotZero();
            code.emitGetStatic(clas.getDeclaredField("TRUE"));
            code.emitElse();
            code.emitGetStatic(clas.getDeclaredField("FALSE"));
            code.emitFi();
            return;
        }
        Method method;
        Type[] args = new Type[]{this};
        if (code.getMethod().getDeclaringClass().classfileFormatVersion >= ClassType.JDK_1_5_VERSION) {
            method = clas.getDeclaredMethod("valueOf", args);
        } else {
            method = clas.getDeclaredMethod("<init>", args);
            code.emitNew(clas);
            code.emitDupX();
            code.emitSwap();
        }
        code.emitInvoke(method);
    }

    public void emitIsInstance(CodeAttr code) {
        char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
        if (sig1 == 'Z') {
            javalangBooleanType.emitIsInstance(code);
        } else if (sig1 == 'V') {
            code.emitPop(1);
            code.emitPushInt(1);
        } else {
            javalangNumberType.emitIsInstance(code);
        }
    }

    public void emitCoerceFromObject(CodeAttr code) {
        char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
        if (sig1 == 'Z') {
            code.emitCheckcast(javalangBooleanType);
            code.emitInvokeVirtual(booleanValue_method);
        } else if (sig1 == 'V') {
            code.emitPop(1);
        } else {
            code.emitCheckcast(javalangNumberType);
            if (sig1 == Access.INNERCLASS_CONTEXT || sig1 == 'S' || sig1 == 'B') {
                code.emitInvokeVirtual(intValue_method);
            } else if (sig1 == 'J') {
                code.emitInvokeVirtual(longValue_method);
            } else if (sig1 == 'D') {
                code.emitInvokeVirtual(doubleValue_method);
            } else if (sig1 == Access.FIELD_CONTEXT) {
                code.emitInvokeVirtual(floatValue_method);
            } else {
                super.emitCoerceFromObject(code);
            }
        }
    }

    public static int compare(PrimType type1, PrimType type2) {
        int i = -3;
        char sig1 = type1.signature.charAt(0);
        char sig2 = type2.signature.charAt(0);
        if (sig1 == sig2) {
            return 0;
        }
        if (sig1 == 'V') {
            return 1;
        }
        if (sig2 == 'V') {
            return -1;
        }
        if (sig1 == 'Z' || sig2 == 'Z') {
            return -3;
        }
        if (sig1 == Access.CLASS_CONTEXT) {
            if (type2.size <= 2) {
                return -3;
            }
            return -1;
        } else if (sig2 == Access.CLASS_CONTEXT) {
            if (type1.size > 2) {
                i = 1;
            }
            return i;
        } else if (sig1 == 'D') {
            return 1;
        } else {
            if (sig2 == 'D') {
                return -1;
            }
            if (sig1 == Access.FIELD_CONTEXT) {
                return 1;
            }
            if (sig2 == Access.FIELD_CONTEXT) {
                return -1;
            }
            if (sig1 == 'J') {
                return 1;
            }
            if (sig2 == 'J') {
                return -1;
            }
            if (sig1 == Access.INNERCLASS_CONTEXT) {
                return 1;
            }
            if (sig2 == Access.INNERCLASS_CONTEXT) {
                return -1;
            }
            if (sig1 == 'S') {
                return 1;
            }
            if (sig2 != 'S') {
                return -3;
            }
            return -1;
        }
    }

    public Type promotedType() {
        switch (this.signature.charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                return Type.intType;
            default:
                return getImplementationType();
        }
    }

    private static char findInHierarchy(String cname) {
        int pos = numberHierarchy.indexOf(cname) - 2;
        return pos < 0 ? '\u0000' : numberHierarchy.charAt(pos);
    }

    public int compare(Type other) {
        if (other instanceof PrimType) {
            if (other.getImplementationType() != other) {
                return Type.swappedCompareResult(other.compare(this));
            }
            return compare(this, (PrimType) other);
        } else if (other instanceof ClassType) {
            char sig1 = this.signature.charAt(0);
            String otherName = other.getName();
            if (otherName == null) {
                return -1;
            }
            char thisPriority = '\u0000';
            switch (sig1) {
                case 'B':
                    thisPriority = 'A';
                    break;
                case 'C':
                    break;
                case 'D':
                    thisPriority = Access.INNERCLASS_CONTEXT;
                    break;
                case 'F':
                    thisPriority = 'H';
                    break;
                case 'I':
                    thisPriority = Access.CLASS_CONTEXT;
                    break;
                case 'J':
                    thisPriority = 'D';
                    break;
                case 'S':
                    thisPriority = 'B';
                    break;
                case 'V':
                    return 1;
                case 'Z':
                    if (otherName.equals("java.lang.Boolean")) {
                        return 0;
                    }
                    break;
            }
            if (otherName.equals("java.lang.Character")) {
                return 0;
            }
            if (thisPriority != '\u0000') {
                char otherPriority = findInHierarchy(otherName);
                if (otherPriority != '\u0000') {
                    if (otherPriority == thisPriority) {
                        return 0;
                    }
                    if (otherPriority < thisPriority) {
                        return 1;
                    }
                    return -1;
                }
            }
            if (otherName.equals("java.lang.Object") || other == toStringType) {
                return -1;
            }
            return -3;
        } else if (other instanceof ArrayType) {
            return -3;
        } else {
            return Type.swappedCompareResult(other.compare(this));
        }
    }
}
