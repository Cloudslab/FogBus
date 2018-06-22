package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class SynchronizedExp extends Expression {
    Expression body;
    Expression object;

    public SynchronizedExp(Expression object, Expression body) {
        this.object = object;
        this.body = body;
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object result;
        synchronized (this.object.eval(ctx)) {
            result = this.body.eval(ctx);
        }
        ctx.writeValue(result);
    }

    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        this.object.compile(comp, Target.pushObject);
        code.emitDup(1);
        Variable objvar = code.pushScope().addVariable(code, Type.pointer_type, null);
        code.emitStore(objvar);
        code.emitMonitorEnter();
        Type type = ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) ? null : target.getType();
        code.emitTryStart(false, type);
        this.body.compileWithPosition(comp, target);
        code.emitLoad(objvar);
        code.emitMonitorExit();
        code.emitTryEnd();
        code.emitCatchStart(null);
        code.emitLoad(objvar);
        code.emitMonitorExit();
        code.emitThrow();
        code.emitCatchEnd();
        code.emitTryCatchEnd();
        code.popScope();
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitSynchronizedExp(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.object = visitor.visitAndUpdate(this.object, d);
        if (visitor.exitValue == null) {
            this.body = visitor.visitAndUpdate(this.body, d);
        }
    }

    public void print(OutPort ps) {
        ps.print("(Synchronized ");
        this.object.print(ps);
        ps.print(" ");
        this.body.print(ps);
        ps.print(")");
    }
}
