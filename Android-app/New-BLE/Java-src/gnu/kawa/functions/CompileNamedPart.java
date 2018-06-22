package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.HasNamedParts;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Translator;

public class CompileNamedPart {
    static final ClassType typeHasNamedParts = ClassType.make("gnu.mapping.HasNamedParts");

    public static Expression validateGetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(exp instanceof GetNamedExp)) {
            return exp;
        }
        Expression context = args[0];
        Declaration decl = null;
        if (context instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) context;
            if ("*".equals(rexp.getName())) {
                return makeGetNamedInstancePartExp(args[1]);
            }
            decl = rexp.getBinding();
        }
        String mname = ((QuoteExp) args[1]).getValue().toString();
        Type type = context.getType();
        if (context == QuoteExp.nullExp) {
        }
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Type typeval = language.getTypeFor(context, false);
        ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
        GetNamedExp nexp = (GetNamedExp) exp;
        if (typeval != null) {
            if (mname.equals(GetNamedPart.CLASSTYPE_FOR)) {
                return new QuoteExp(typeval);
            }
            if (typeval instanceof ObjectType) {
                if (mname.equals("new")) {
                    return nexp.setProcedureKind('N');
                }
                if (mname.equals(GetNamedPart.INSTANCEOF_METHOD_NAME)) {
                    return nexp.setProcedureKind(Access.INNERCLASS_CONTEXT);
                }
                if (mname.equals(GetNamedPart.CAST_METHOD_NAME)) {
                    return nexp.setProcedureKind(Access.CLASS_CONTEXT);
                }
            }
        }
        PrimProcedure[] methods;
        ApplyExp aexp;
        if (!(typeval instanceof ObjectType)) {
            if (typeval != null) {
            }
            if (type.isSubtype(Compilation.typeClassType) || type.isSubtype(Type.javalangClassType)) {
                return exp;
            }
            if (type instanceof ObjectType) {
                ObjectType otype = (ObjectType) type;
                methods = ClassMethods.getMethods(otype, Compilation.mangleName(mname), 'V', caller, language);
                if (methods != null && methods.length > 0) {
                    nexp.methods = methods;
                    return nexp.setProcedureKind(Access.METHOD_CONTEXT);
                } else if (type.isSubtype(typeHasNamedParts)) {
                    if (decl != null) {
                        HasNamedParts val = Declaration.followAliases(decl).getConstantValue();
                        if (val != null) {
                            HasNamedParts value = val;
                            if (value.isConstant(mname)) {
                                return QuoteExp.getInstance(value.get(mname));
                            }
                        }
                    }
                    Expression[] args2 = new Expression[]{args[0], QuoteExp.getInstance(mname)};
                    args = args2;
                    return new ApplyExp(typeHasNamedParts.getDeclaredMethod("get", 1), args2).setLine((Expression) exp);
                } else if (SlotGet.lookupMember(otype, mname, caller) != null || (mname.equals("length") && (type instanceof ArrayType))) {
                    aexp = new ApplyExp(SlotGet.field, args);
                    aexp.setLine((Expression) exp);
                    return visitor.visitApplyOnly(aexp, required);
                }
            }
            if (!comp.warnUnknownMember()) {
                return exp;
            }
            comp.error('w', "no known slot '" + mname + "' in " + type.getName());
            return exp;
        } else if (mname.length() > 1 && mname.charAt(0) == '.') {
            return new QuoteExp(new NamedPart(typeval, mname, 'D'));
        } else {
            if (CompileReflect.checkKnownClass(typeval, comp) < 0) {
                return exp;
            }
            methods = ClassMethods.getMethods((ObjectType) typeval, Compilation.mangleName(mname), '\u0000', caller, language);
            if (methods == null || methods.length <= 0) {
                aexp = new ApplyExp(SlotGet.staticField, args);
                aexp.setLine((Expression) exp);
                return visitor.visitApplyOnly(aexp, required);
            }
            nexp.methods = methods;
            return nexp.setProcedureKind('S');
        }
    }

    public static Expression validateSetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 3 && (args[1] instanceof QuoteExp)) {
            Expression context = args[0];
            String mname = ((QuoteExp) args[1]).getValue().toString();
            Type type = context.getType();
            Compilation comp = visitor.getCompilation();
            Type typeval = comp.getLanguage().getTypeFor(context);
            ClassType caller = comp == null ? null : comp.curClass != null ? comp.curClass : comp.mainClass;
            ApplyExp original = exp;
            if (typeval instanceof ClassType) {
                exp = new ApplyExp(SlotSet.set$Mnstatic$Mnfield$Ex, args);
            } else if ((type instanceof ClassType) && SlotSet.lookupMember((ClassType) type, mname, caller) != null) {
                exp = new ApplyExp(SlotSet.set$Mnfield$Ex, args);
            }
            if (exp != original) {
                exp.setLine((Expression) original);
            }
            exp.setType(Type.voidType);
        }
        return exp;
    }

    public static Expression makeExp(Expression clas, Expression member) {
        Object combinedName = combineName(clas, member);
        Environment env = Environment.getCurrent();
        if (combinedName != null) {
            Translator tr = (Translator) Compilation.getCurrent();
            Object symbol = Namespace.EmptyNamespace.getSymbol(combinedName);
            Declaration decl = tr.lexical.lookup(symbol, false);
            if (!Declaration.isUnknown(decl)) {
                return new ReferenceExp(decl);
            }
            if (symbol != null && env.isBound(symbol, null)) {
                return new ReferenceExp(combinedName);
            }
        }
        if (clas instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) clas;
            if (rexp.isUnknown()) {
                Object rsym = rexp.getSymbol();
                if (env.get(rsym instanceof Symbol ? (Symbol) rsym : env.getSymbol(rsym.toString()), null) == null) {
                    try {
                        clas = QuoteExp.getInstance(Type.make(ObjectType.getContextClass(rexp.getName())));
                    } catch (Throwable th) {
                    }
                }
            }
        }
        Expression exp = new GetNamedExp(new Expression[]{clas, member});
        exp.combinedName = combinedName;
        return exp;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String combineName(gnu.expr.Expression r4, gnu.expr.Expression r5) {
        /*
        r1 = r5.valueIfConstant();
        r2 = r1 instanceof gnu.mapping.SimpleSymbol;
        if (r2 == 0) goto L_0x003b;
    L_0x0008:
        r2 = r4 instanceof gnu.expr.ReferenceExp;
        if (r2 == 0) goto L_0x0015;
    L_0x000c:
        r2 = r4;
        r2 = (gnu.expr.ReferenceExp) r2;
        r0 = r2.getSimpleName();
        if (r0 != 0) goto L_0x001f;
    L_0x0015:
        r2 = r4 instanceof gnu.kawa.functions.GetNamedExp;
        if (r2 == 0) goto L_0x003b;
    L_0x0019:
        r4 = (gnu.kawa.functions.GetNamedExp) r4;
        r0 = r4.combinedName;
        if (r0 == 0) goto L_0x003b;
    L_0x001f:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r2 = r2.append(r0);
        r3 = 58;
        r2 = r2.append(r3);
        r2 = r2.append(r1);
        r2 = r2.toString();
        r2 = r2.intern();
    L_0x003a:
        return r2;
    L_0x003b:
        r2 = 0;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.CompileNamedPart.combineName(gnu.expr.Expression, gnu.expr.Expression):java.lang.String");
    }

    public static Expression makeExp(Expression clas, String member) {
        return makeExp(clas, new QuoteExp(member));
    }

    public static Expression makeExp(Type type, String member) {
        return makeExp(new QuoteExp(type), new QuoteExp(member));
    }

    public static Expression validateNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        NamedPart namedPart = (NamedPart) proc;
        switch (namedPart.kind) {
            case 'D':
                Procedure slotProc;
                Expression[] xargs = new Expression[2];
                xargs[1] = QuoteExp.getInstance(namedPart.member.toString().substring(1));
                if (args.length > 0) {
                    xargs[0] = Compilation.makeCoercion(args[0], new QuoteExp(namedPart.container));
                    slotProc = SlotGet.field;
                } else {
                    xargs[0] = QuoteExp.getInstance(namedPart.container);
                    slotProc = SlotGet.staticField;
                }
                ApplyExp aexp = new ApplyExp(slotProc, xargs);
                aexp.setLine((Expression) exp);
                return visitor.visitApplyOnly(aexp, required);
            default:
                return exp;
        }
    }

    public static Expression validateNamedPartSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        NamedPart get = (NamedPart) ((NamedPartSetter) proc).getGetter();
        if (get.kind != 'D') {
            return exp;
        }
        Procedure slotProc;
        Expression[] xargs = new Expression[3];
        xargs[1] = QuoteExp.getInstance(get.member.toString().substring(1));
        xargs[2] = exp.getArgs()[0];
        if (exp.getArgCount() == 1) {
            xargs[0] = QuoteExp.getInstance(get.container);
            slotProc = SlotSet.set$Mnstatic$Mnfield$Ex;
        } else if (exp.getArgCount() != 2) {
            return exp;
        } else {
            xargs[0] = Compilation.makeCoercion(exp.getArgs()[0], new QuoteExp(get.container));
            slotProc = SlotSet.set$Mnfield$Ex;
        }
        ApplyExp aexp = new ApplyExp(slotProc, xargs);
        aexp.setLine((Expression) exp);
        return visitor.visitApplyOnly(aexp, required);
    }

    public static Expression makeGetNamedInstancePartExp(Expression member) {
        if (member instanceof QuoteExp) {
            Object val = ((QuoteExp) member).getValue();
            if (val instanceof SimpleSymbol) {
                return QuoteExp.getInstance(new GetNamedInstancePart(val.toString()));
            }
        }
        return new ApplyExp(Invoke.make, new Expression[]{new QuoteExp(ClassType.make("gnu.kawa.functions.GetNamedInstancePart")), member});
    }

    public static Expression validateGetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Expression[] xargs;
        Procedure property;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        GetNamedInstancePart gproc = (GetNamedInstancePart) proc;
        if (gproc.isField) {
            xargs = new Expression[]{args[0], new QuoteExp(gproc.pname)};
            property = SlotGet.field;
        } else {
            int nargs = args.length;
            xargs = new Expression[(nargs + 1)];
            xargs[0] = args[0];
            xargs[1] = new QuoteExp(gproc.pname);
            System.arraycopy(args, 1, xargs, 2, nargs - 1);
            property = Invoke.invoke;
        }
        return visitor.visitApplyOnly(new ApplyExp(property, xargs), required);
    }

    public static Expression validateSetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        String pname = ((SetNamedInstancePart) proc).pname;
        return visitor.visitApplyOnly(new ApplyExp(SlotSet.set$Mnfield$Ex, new Expression[]{args[0], new QuoteExp(pname), args[1]}), required);
    }
}
