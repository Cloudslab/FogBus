package gnu.kawa.reflect;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class SlotGet extends Procedure2 implements HasSetter, Inlineable {
    public static final SlotGet field = new SlotGet("field", false, SlotSet.set$Mnfield$Ex);
    static Class[] noClasses = new Class[0];
    public static final SlotGet slotRef = new SlotGet("slot-ref", false, SlotSet.set$Mnfield$Ex);
    public static final SlotGet staticField = new SlotGet("static-field", true, SlotSet.set$Mnstatic$Mnfield$Ex);
    boolean isStatic;
    Procedure setter;

    public SlotGet(String name, boolean isStatic) {
        super(name);
        this.isStatic = isStatic;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotGet");
    }

    public SlotGet(String name, boolean isStatic, Procedure setter) {
        this(name, isStatic);
        this.setter = setter;
    }

    public static Object field(Object obj, String fname) {
        return field.apply2(obj, fname);
    }

    public static Object staticField(Object obj, String fname) {
        return staticField.apply2(obj, fname);
    }

    public Object apply2(Object arg1, Object arg2) {
        String fname;
        String name;
        String getName = null;
        String isName = null;
        if (arg2 instanceof Field) {
            fname = ((Field) arg2).getName();
            name = Compilation.demangleName(fname, true);
        } else if (arg2 instanceof Method) {
            String mname = ((Method) arg2).getName();
            name = Compilation.demangleName(mname, false);
            if (mname.startsWith("get")) {
                getName = mname;
            } else if (mname.startsWith("is")) {
                isName = mname;
            }
            fname = null;
        } else if ((arg2 instanceof SimpleSymbol) || (arg2 instanceof CharSequence)) {
            name = arg2.toString();
            fname = Compilation.mangleNameIfNeeded(name);
        } else {
            throw new WrongType((Procedure) this, 2, arg2, PropertyTypeConstants.PROPERTY_TYPE_STRING);
        }
        if ("class".equals(fname)) {
            fname = "class";
        } else if ("length".equals(fname)) {
            fname = "length";
        }
        return getSlotValue(this.isStatic, arg1, name, fname, getName, isName, Language.getDefaultLanguage());
    }

    public static Object getSlotValue(boolean isStatic, Object obj, String name, String fname, String getName, String isName, Language language) {
        Class clas = isStatic ? coerceToClass(obj) : obj.getClass();
        if (fname == "length" && clas.isArray()) {
            return Integer.valueOf(Array.getLength(obj));
        }
        if (fname == "class") {
            return clas;
        }
        java.lang.reflect.Method getmethod;
        boolean illegalAccess = false;
        if (fname != null) {
            java.lang.reflect.Field field;
            try {
                field = clas.getField(fname);
            } catch (Exception e) {
                field = null;
            }
            if (field != null) {
                if (isStatic && (field.getModifiers() & 8) == 0) {
                    throw new RuntimeException("cannot access non-static field '" + fname + '\'');
                }
                try {
                    return language.coerceToObject(field.getType(), field.get(obj));
                } catch (IllegalAccessException e2) {
                    illegalAccess = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        String mname = getName != null ? getName : ClassExp.slotToMethodName("get", name);
        try {
            getmethod = clas.getMethod(mname, noClasses);
        } catch (Exception e3) {
            mname = isName != null ? isName : ClassExp.slotToMethodName("is", name);
            getmethod = clas.getMethod(mname, noClasses);
        }
        if (isStatic) {
            try {
                if ((getmethod.getModifiers() & 8) == 0) {
                    throw new RuntimeException("cannot call non-static getter method '" + mname + '\'');
                }
            } catch (InvocationTargetException ex2) {
                throw WrappedException.wrapIfNeeded(ex2.getTargetException());
            } catch (IllegalAccessException e4) {
                illegalAccess = true;
                if (illegalAccess) {
                    throw new RuntimeException("no such field " + fname + " in " + clas.getName());
                }
                throw new RuntimeException("illegal access for field " + fname);
            } catch (NoSuchMethodException e5) {
                if (illegalAccess) {
                    throw new RuntimeException("no such field " + fname + " in " + clas.getName());
                }
                throw new RuntimeException("illegal access for field " + fname);
            }
        }
        return language.coerceToObject(getmethod.getReturnType(), getmethod.invoke(obj, Values.noArgs));
    }

    static Class coerceToClass(Object obj) {
        if (obj instanceof Class) {
            return (Class) obj;
        }
        if (obj instanceof Type) {
            return ((Type) obj).getReflectClass();
        }
        throw new RuntimeException("argument is neither Class nor Type");
    }

    public void setN(Object[] args) {
        int nargs = args.length;
        if (nargs != 3) {
            throw new WrongArguments(getSetter(), nargs);
        }
        set2(args[0], args[1], args[2]);
    }

    public void set2(Object obj, Object name, Object value) {
        SlotSet.apply(this.isStatic, obj, (String) name, value);
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field, clas)) {
                return field;
            }
        }
        Method method = clas.getMethod(ClassExp.slotToMethodName("get", name), Type.typeArray0);
        if (method != null) {
            return method;
        }
        return field;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Language language = comp.getLanguage();
        Type type = this.isStatic ? language.getTypeFor(arg0) : arg0.getType();
        CodeAttr code = comp.getCode();
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            ObjectType ctype = (ObjectType) type;
            Field part = ((QuoteExp) arg1).getValue();
            if (part instanceof Field) {
                Field field = part;
                boolean isStaticField = (field.getModifiers() & 8) != 0;
                args[0].compile(comp, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (!isStaticField) {
                    code.emitGetField(field);
                } else if (!false) {
                    code.emitGetStatic(field);
                }
                target.compileFromStack(comp, language.getLangTypeFor(field.getType()));
                return;
            } else if (part instanceof Method) {
                Method method = (Method) part;
                int modifiers = method.getModifiers();
                boolean isStaticMethod = method.getStaticFlag();
                args[0].compile(comp, isStaticMethod ? Target.Ignore : Target.pushValue(ctype));
                if (isStaticMethod) {
                    code.emitInvokeStatic(method);
                } else {
                    code.emitInvoke(method);
                }
                target.compileFromStack(comp, method.getReturnType());
                return;
            }
        }
        String name = ClassMethods.checkName(arg1);
        if ((type instanceof ArrayType) && "length".equals(name) && !this.isStatic) {
            args[0].compile(comp, Target.pushValue(type));
            code.emitArrayLength();
            target.compileFromStack(comp, LangPrimType.intType);
            return;
        }
        ApplyExp.compile(exp, comp, target);
    }

    public Type getReturnType(Expression[] args) {
        if (args.length == 2) {
            Expression arg0 = args[0];
            Expression arg1 = args[1];
            if (arg1 instanceof QuoteExp) {
                Object part = ((QuoteExp) arg1).getValue();
                if (part instanceof Field) {
                    return ((Field) part).getType();
                }
                if (part instanceof Method) {
                    return ((Method) part).getReturnType();
                }
                if (!this.isStatic && (arg0.getType() instanceof ArrayType) && "length".equals(ClassMethods.checkName(arg1, true))) {
                    return LangPrimType.intType;
                }
            }
        }
        return Type.pointer_type;
    }

    public Procedure getSetter() {
        return this.setter == null ? super.getSetter() : this.setter;
    }

    public static ApplyExp makeGetField(Expression value, String fieldName) {
        return new ApplyExp(field, new Expression[]{value, new QuoteExp(fieldName)});
    }
}
