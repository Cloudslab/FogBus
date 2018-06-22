package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.ArrayGet;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.math.Numeric;
import gnu.text.Char;
import java.io.Externalizable;

public class CompilationHelpers {
    public static final Declaration setterDecl = new Declaration((Object) "setter", setterField);
    static final Field setterField = setterType.getDeclaredField("setter");
    static final ClassType setterType = ClassType.make("gnu.kawa.functions.Setter");
    static final ClassType typeList = ClassType.make("java.util.List");

    private static boolean nonNumeric(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            return false;
        }
        Object value = ((QuoteExp) exp).getValue();
        if ((value instanceof Numeric) || (value instanceof Char) || (value instanceof Symbol)) {
            return false;
        }
        return true;
    }

    static {
        setterDecl.noteValue(new QuoteExp(Setter.setter));
    }

    public static Expression validateApplyToArgs(ApplyExp exp, InlineCalls visitor, Type required, Procedure applyToArgs) {
        Expression[] args = exp.getArgs();
        int nargs = args.length - 1;
        if (nargs >= 0) {
            Expression[] rargs;
            Expression proc = args[0];
            if (!proc.getFlag(1)) {
                if (proc instanceof LambdaExp) {
                    rargs = new Expression[nargs];
                    System.arraycopy(args, 1, rargs, 0, nargs);
                    return visitor.visit(new ApplyExp(proc, rargs).setLine((Expression) exp), required);
                }
                proc = visitor.visit(proc, null);
                args[0] = proc;
            }
            Type ptype = proc.getType().getRealType();
            Compilation comp = visitor.getCompilation();
            Language language = comp.getLanguage();
            if (ptype.isSubtype(Compilation.typeProcedure)) {
                rargs = new Expression[nargs];
                System.arraycopy(args, 1, rargs, 0, nargs);
                ApplyExp nexp = new ApplyExp(proc, rargs);
                nexp.setLine((Expression) exp);
                return proc.validateApply(nexp, visitor, required, null);
            }
            ApplyExp result = null;
            if (CompileReflect.checkKnownClass(ptype, comp) >= 0) {
                if (ptype.isSubtype(Compilation.typeType) || language.getTypeFor(proc, false) != null) {
                    result = new ApplyExp(Invoke.make, args);
                } else if (ptype instanceof ArrayType) {
                    result = new ApplyExp(new ArrayGet(((ArrayType) ptype).getComponentType()), args);
                } else if (ptype instanceof ClassType) {
                    ClassType ctype = (ClassType) ptype;
                    if (ctype.isSubclass(typeList) && nargs == 1) {
                        result = new ApplyExp(ctype.getMethod("get", new Type[]{Type.intType}), args);
                    }
                }
            }
            if (result != null) {
                result.setLine((Expression) exp);
                return visitor.visitApplyOnly(result, required);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            return exp;
        }
        Expression arg = args[0];
        Type argType = arg.getType();
        if (argType instanceof ArrayType) {
            return new SetArrayExp(arg, (ArrayType) argType);
        }
        if (!(argType instanceof ClassType) || !((ClassType) argType).isSubclass(typeList)) {
            Declaration decl;
            if (arg instanceof ReferenceExp) {
                decl = ((ReferenceExp) arg).getBinding();
                if (decl != null) {
                    arg = decl.getValue();
                }
            }
            if (!(arg instanceof QuoteExp)) {
                return exp;
            }
            Object value = ((QuoteExp) arg).getValue();
            if (!(value instanceof Procedure)) {
                return exp;
            }
            Procedure setter = ((Procedure) value).getSetter();
            if (!(setter instanceof Procedure)) {
                return exp;
            }
            if (setter instanceof Externalizable) {
                return new QuoteExp(setter);
            }
            decl = Declaration.getDeclaration(setter);
            if (decl != null) {
                return new ReferenceExp(decl);
            }
            return exp;
        } else if (exp instanceof SetListExp) {
            return exp;
        } else {
            return new SetListExp(exp.getFunction(), args);
        }
    }

    public static Expression validateIsEqv(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (nonNumeric(args[0]) || nonNumeric(args[1])) {
            return new ApplyExp(((IsEqv) proc).isEq, args);
        }
        return exp;
    }
}
