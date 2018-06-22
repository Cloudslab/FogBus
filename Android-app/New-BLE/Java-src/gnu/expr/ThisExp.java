package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.mapping.CallContext;

public class ThisExp extends ReferenceExp {
    static int EVAL_TO_CONTEXT = 2;
    public static final String THIS_NAME = new String("$this$");
    ScopeExp context;

    public final boolean isForContext() {
        return (this.flags & EVAL_TO_CONTEXT) != 0;
    }

    public void apply(CallContext ctx) throws Throwable {
        if (isForContext()) {
            ctx.writeValue(this.context);
        } else {
            super.apply(ctx);
        }
    }

    public ScopeExp getContextScope() {
        return this.context;
    }

    public ThisExp() {
        super(THIS_NAME);
    }

    public ThisExp(ScopeExp context) {
        super(THIS_NAME);
        this.context = context;
    }

    public ThisExp(Declaration binding) {
        super(THIS_NAME, binding);
    }

    public ThisExp(ClassType type) {
        this(new Declaration(THIS_NAME, (Type) type));
    }

    public static ThisExp makeGivingContext(ScopeExp context) {
        ThisExp exp = new ThisExp(context);
        exp.flags |= EVAL_TO_CONTEXT;
        return exp;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            if (isForContext()) {
                CodeAttr code = comp.getCode();
                if (comp.method.getStaticFlag()) {
                    code.emitGetStatic(comp.moduleInstanceMainField);
                } else {
                    code.emitPushThis();
                }
                target.compileFromStack(comp, getType());
                return;
            }
            super.compile(comp, target);
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitThisExp(this, d);
    }

    public final Type getType() {
        if (this.binding != null) {
            return this.binding.getType();
        }
        if ((this.context instanceof ClassExp) || (this.context instanceof ModuleExp)) {
            return this.context.getType();
        }
        return Type.pointer_type;
    }
}
