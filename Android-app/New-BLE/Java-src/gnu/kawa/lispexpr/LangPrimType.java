package gnu.kawa.lispexpr;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.text.Char;

public class LangPrimType extends PrimType implements TypeValue {
    public static final PrimType byteType = Type.byteType;
    public static final LangPrimType charType = new LangPrimType(Type.charType);
    public static final PrimType doubleType = Type.doubleType;
    public static final PrimType floatType = Type.floatType;
    public static final PrimType intType = Type.intType;
    public static final PrimType longType = Type.longType;
    public static final PrimType shortType = Type.shortType;
    public static final LangPrimType voidType = new LangPrimType(Type.voidType);
    PrimType implementationType;
    Language language;

    public LangPrimType(PrimType type) {
        super(type);
        this.implementationType = type;
    }

    public LangPrimType(PrimType type, Language language) {
        super(type);
        this.language = language;
        this.implementationType = type;
    }

    public LangPrimType(String nam, String sig, int siz, Class reflectClass) {
        super(nam, sig, siz, reflectClass);
    }

    public LangPrimType(String nam, String sig, int siz, Class reflectClass, Language language) {
        this(nam, sig, siz, reflectClass);
        this.implementationType = Type.signatureToPrimitive(sig.charAt(0));
        this.language = language;
    }

    public Type getImplementationType() {
        return this.implementationType;
    }

    public Object coerceFromObject(Object obj) {
        if (obj.getClass() == this.reflectClass) {
            return obj;
        }
        switch (getSignature().charAt(0)) {
            case 'C':
                return new Character(((Char) obj).charValue());
            case 'V':
                return Values.empty;
            case 'Z':
                return this.language.isTrue(obj) ? Boolean.TRUE : Boolean.FALSE;
            default:
                return super.coerceFromObject(obj);
        }
    }

    public char charValue(Object value) {
        if (value instanceof Character) {
            return ((Character) value).charValue();
        }
        return ((Char) value).charValue();
    }

    public void emitIsInstance(CodeAttr code) {
        switch (getSignature().charAt(0)) {
            case 'C':
                code.emitInstanceof(ClassType.make("gnu.text.Char"));
                return;
            case 'Z':
                code.emitPop(1);
                code.emitPushInt(1);
                return;
            default:
                super.emitIsInstance(code);
                return;
        }
    }

    public void emitCoerceFromObject(CodeAttr code) {
        switch (getSignature().charAt(0)) {
            case 'C':
                ClassType scmCharType = ClassType.make("gnu.text.Char");
                Method charValueMethod = scmCharType.getDeclaredMethod("charValue", 0);
                code.emitCheckcast(scmCharType);
                code.emitInvokeVirtual(charValueMethod);
                return;
            case 'Z':
                this.language.emitCoerceToBoolean(code);
                return;
            default:
                super.emitCoerceFromObject(code);
                return;
        }
    }

    public Object coerceToObject(Object obj) {
        switch (getSignature().charAt(0)) {
            case 'C':
                if (obj instanceof Char) {
                    return obj;
                }
                return Char.make(((Character) obj).charValue());
            case 'V':
                return Values.empty;
            case 'Z':
                return this.language.booleanObject(((Boolean) obj).booleanValue());
            default:
                return super.coerceToObject(obj);
        }
    }

    public void emitCoerceToObject(CodeAttr code) {
        switch (getSignature().charAt(0)) {
            case 'C':
                code.emitInvokeStatic(ClassType.make("gnu.text.Char").getDeclaredMethod("make", 1));
                break;
            case 'Z':
                code.emitIfIntNotZero();
                this.language.emitPushBoolean(true, code);
                code.emitElse();
                this.language.emitPushBoolean(false, code);
                code.emitFi();
                break;
            default:
                super.emitCoerceToObject(code);
                break;
        }
        if (null != null) {
            code.emitInvokeStatic(ClassType.make(null).getDeclaredMethod("make", new Type[]{null}));
        }
    }

    public int compare(Type other) {
        char sig1 = getSignature().charAt(0);
        if (other instanceof PrimType) {
            char sig2 = other.getSignature().charAt(0);
            if (sig1 == sig2) {
                return 0;
            }
            if (sig1 == 'V') {
                return 1;
            }
            if (sig2 == 'V' || sig2 == 'Z') {
                return -1;
            }
        }
        if (sig1 == 'V' || sig1 == 'Z') {
            return 1;
        }
        if (sig1 == Access.CLASS_CONTEXT && other.getName().equals("gnu.text.Char")) {
            return -1;
        }
        if (other instanceof LangObjType) {
            return Type.swappedCompareResult(other.compare(this));
        }
        return super.compare(other);
    }

    public void emitTestIf(Variable incoming, Declaration decl, Compilation comp) {
        char sig1 = getSignature().charAt(0);
        CodeAttr code = comp.getCode();
        if (incoming != null) {
            code.emitLoad(incoming);
        }
        if (decl != null) {
            code.emitDup();
            decl.compileStore(comp);
        }
        emitIsInstance(code);
        code.emitIfIntNotZero();
    }

    public Expression convertValue(Expression value) {
        return null;
    }

    public void emitIsInstance(Variable incoming, Compilation comp, Target target) {
        InstanceOf.emitIsInstance(this, incoming, comp, target);
    }

    public Procedure getConstructor() {
        return null;
    }
}
