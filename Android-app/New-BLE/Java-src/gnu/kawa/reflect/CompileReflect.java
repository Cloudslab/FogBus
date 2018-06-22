package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

public class CompileReflect {
    public static int checkKnownClass(Type type, Compilation comp) {
        if (!(type instanceof ClassType) || !type.isExisting()) {
            return 0;
        }
        try {
            type.getReflectClass();
            return 1;
        } catch (Exception e) {
            comp.error('e', "unknown class: " + type.getName());
            return -1;
        }
    }

    public static ApplyExp inlineClassName(ApplyExp exp, int carg, InlineCalls walker) {
        Compilation comp = walker.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length <= carg) {
            return exp;
        }
        Type type = language.getTypeFor(args[carg]);
        if (!(type instanceof Type)) {
            return exp;
        }
        checkKnownClass(type, comp);
        Expression[] nargs = new Expression[args.length];
        System.arraycopy(args, 0, nargs, 0, args.length);
        nargs[carg] = new QuoteExp(type);
        ApplyExp nexp = new ApplyExp(exp.getFunction(), nargs);
        nexp.setLine((Expression) exp);
        return nexp;
    }

    public static Expression validateApplyInstanceOf(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression exp2 = inlineClassName(exp, 1, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length == 2) {
            Expression value = args[0];
            Expression texp = args[1];
            if (texp instanceof QuoteExp) {
                Type t = ((QuoteExp) texp).getValue();
                if (t instanceof Type) {
                    Type type = t;
                    if (type instanceof PrimType) {
                        type = ((PrimType) type).boxedType();
                    }
                    if (value instanceof QuoteExp) {
                        if (type.isInstance(((QuoteExp) value).getValue())) {
                            return QuoteExp.trueExp;
                        }
                        return QuoteExp.falseExp;
                    } else if (!value.side_effects()) {
                        int comp = type.compare(value.getType());
                        if (comp == 1 || comp == 0) {
                            return QuoteExp.trueExp;
                        }
                        if (comp == -3) {
                            return QuoteExp.falseExp;
                        }
                    }
                }
            }
        }
        return exp2;
    }

    public static Expression validateApplySlotGet(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        boolean isStatic = ((SlotGet) proc).isStatic;
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        Object val1 = args[1].valueIfConstant();
        if (!(val1 instanceof String) && !(val1 instanceof FString) && !(val1 instanceof Symbol)) {
            return exp;
        }
        Type type;
        String name = val1.toString();
        if (isStatic) {
            type = language.getTypeFor(arg0);
            int known = checkKnownClass(type, comp);
            if (known < 0) {
                return exp;
            }
            if ("class".equals(name)) {
                if (known > 0) {
                    return QuoteExp.getInstance(type.getReflectClass());
                }
                return new ApplyExp(Compilation.typeType.getDeclaredMethod("getReflectClass", 0), new Expression[]{arg0});
            } else if (type != null) {
                Expression applyExp = new ApplyExp(exp.getFunction(), new Expression[]{new QuoteExp(type), arg1});
                applyExp.setLine((Expression) exp);
                Expression exp2 = applyExp;
            }
        } else {
            type = arg0.getType();
        }
        if (type instanceof ArrayType) {
            return exp2;
        }
        ApplyExp applyExp2;
        QuoteExp quoteExp;
        if (type instanceof ObjectType) {
            ObjectType ctype = (ObjectType) type;
            ClassType caller = comp.curClass != null ? comp.curClass : comp.mainClass;
            Member part = SlotGet.lookupMember(ctype, name, caller);
            if (part instanceof Field) {
                Field field = (Field) part;
                boolean isStaticField = (field.getModifiers() & 8) != 0;
                if (isStatic && !isStaticField) {
                    return new ErrorExp("cannot access non-static field `" + name + "' using `" + proc.getName() + '\'', comp);
                }
                if (!(caller == null || caller.isAccessible(field, ctype))) {
                    return new ErrorExp("field " + field.getDeclaringClass().getName() + '.' + name + " is not accessible here", comp);
                }
            } else if (part instanceof Method) {
                Method method = (Method) part;
                ClassType dtype = method.getDeclaringClass();
                int modifiers = method.getModifiers();
                boolean isStaticMethod = method.getStaticFlag();
                if (isStatic && !isStaticMethod) {
                    return new ErrorExp("cannot call non-static getter method `" + name + "' using `" + proc.getName() + '\'', comp);
                }
                if (!(caller == null || caller.isAccessible(dtype, ctype, modifiers))) {
                    return new ErrorExp("method " + method + " is not accessible here", comp);
                }
            }
            if (part != null) {
                applyExp2 = new ApplyExp(exp2.getFunction(), new Expression[]{arg0, new QuoteExp(part)});
                applyExp2.setLine(exp2);
                return applyExp2;
            } else if (type != Type.pointer_type && comp.warnUnknownMember()) {
                comp.error('e', "no slot `" + name + "' in " + ctype.getName());
            }
        }
        String fname = Compilation.mangleNameIfNeeded(name).intern();
        String getName = ClassExp.slotToMethodName("get", name);
        String isName = ClassExp.slotToMethodName("is", name);
        Procedure procedure = Invoke.invokeStatic;
        Expression[] expressionArr = new Expression[9];
        expressionArr[0] = QuoteExp.getInstance("gnu.kawa.reflect.SlotGet");
        expressionArr[1] = QuoteExp.getInstance("getSlotValue");
        if (isStatic) {
            quoteExp = QuoteExp.trueExp;
        } else {
            quoteExp = QuoteExp.falseExp;
        }
        expressionArr[2] = quoteExp;
        expressionArr[3] = args[0];
        expressionArr[4] = QuoteExp.getInstance(name);
        expressionArr[5] = QuoteExp.getInstance(fname);
        expressionArr[6] = QuoteExp.getInstance(getName);
        expressionArr[7] = QuoteExp.getInstance(isName);
        expressionArr[8] = QuoteExp.getInstance(language);
        applyExp2 = new ApplyExp(procedure, expressionArr);
        applyExp2.setLine(exp2);
        return visitor.visitApplyOnly(applyExp2, null);
    }

    public static Expression validateApplySlotSet(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        SlotSet sproc = (SlotSet) proc;
        if (sproc.isStatic && visitor.getCompilation().mustCompile) {
            exp = inlineClassName(exp, 0, visitor);
        }
        Type type = (sproc.returnSelf && exp.getArgCount() == 3) ? exp.getArg(0).getType() : Type.voidType;
        exp.setType(type);
        return exp;
    }

    public static Expression validateApplyTypeSwitch(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        for (int i = 1; i < args.length; i++) {
            if (args[i] instanceof LambdaExp) {
                LambdaExp lexp = args[i];
                lexp.setInlineOnly(true);
                lexp.returnContinuation = exp;
                lexp.inlineHome = visitor.getCurrentLambda();
            }
        }
        return exp;
    }
}
