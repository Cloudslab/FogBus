package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.Target;
import gnu.mapping.Procedure2;

public class IsEq extends Procedure2 implements Inlineable {
    Language language;

    public IsEq(Language language, String name) {
        this.language = language;
        setName(name);
    }

    public boolean apply(Object arg1, Object arg2) {
        return arg1 == arg2;
    }

    public Object apply2(Object arg1, Object arg2) {
        return this.language.booleanObject(arg1 == arg2);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        compile(exp.getArgs(), comp, target, this.language);
    }

    public static void compile(Expression[] args, Compilation comp, Target target, Language language) {
        CodeAttr code = comp.getCode();
        args[0].compile(comp, Target.pushObject);
        args[1].compile(comp, Target.pushObject);
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            if (ctarget.trueBranchComesFirst) {
                code.emitGotoIfNE(ctarget.ifFalse);
            } else {
                code.emitGotoIfEq(ctarget.ifTrue);
            }
            ctarget.emitGotoFirstBranch(code);
            return;
        }
        Type type;
        code.emitIfEq();
        if (target.getType() instanceof ClassType) {
            Object trueValue = language.booleanObject(true);
            Object falseValue = language.booleanObject(false);
            comp.compileConstant(trueValue, Target.pushObject);
            code.emitElse();
            comp.compileConstant(falseValue, Target.pushObject);
            if ((trueValue instanceof Boolean) && (falseValue instanceof Boolean)) {
                type = Compilation.scmBooleanType;
            } else {
                type = Type.pointer_type;
            }
        } else {
            code.emitPushInt(1);
            code.emitElse();
            code.emitPushInt(0);
            type = language.getTypeFor(Boolean.TYPE);
        }
        code.emitFi();
        target.compileFromStack(comp, type);
    }

    public Type getReturnType(Expression[] args) {
        return this.language.getTypeFor(Boolean.TYPE);
    }
}
