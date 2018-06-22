package gnu.kawa.reflect;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class TypeSwitch extends MethodProc implements Inlineable {
    public static final TypeSwitch typeSwitch = new TypeSwitch("typeswitch");

    public TypeSwitch(String name) {
        setName(name);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplyTypeSwitch");
    }

    public int numArgs() {
        return -4094;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        Object selector = args[0];
        int n = args.length - 1;
        int i = 1;
        while (i < n) {
            if (args[i].match1(selector, ctx) < 0) {
                i++;
            } else {
                return;
            }
        }
        args[n].check1(selector, ctx);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        CodeAttr code = comp.getCode();
        code.pushScope();
        Variable selector = code.addLocal(Type.pointer_type);
        args[0].compile(comp, Target.pushObject);
        code.emitStore(selector);
        int i = 1;
        while (i < args.length) {
            if (i > 1) {
                code.emitElse();
            }
            int i2 = i + 1;
            Expression arg = args[i];
            if (arg instanceof LambdaExp) {
                LambdaExp lambda = (LambdaExp) arg;
                Declaration param = lambda.firstDecl();
                Type type = param.getType();
                if (param.getCanRead()) {
                    param.allocateVariable(code);
                } else {
                    param = null;
                }
                if (type instanceof TypeValue) {
                    ((TypeValue) type).emitTestIf(selector, param, comp);
                } else {
                    if (i2 < args.length) {
                        code.emitLoad(selector);
                        type.emitIsInstance(code);
                        code.emitIfIntNotZero();
                    }
                    if (param != null) {
                        code.emitLoad(selector);
                        param.compileStore(comp);
                    }
                }
                lambda.allocChildClasses(comp);
                lambda.body.compileWithPosition(comp, target);
                i = i2;
            } else {
                throw new Error("not implemented: typeswitch arg not LambdaExp");
            }
        }
        i = args.length - 2;
        while (true) {
            i--;
            if (i >= 0) {
                code.emitFi();
            } else {
                code.popScope();
                return;
            }
        }
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}
