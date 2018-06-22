package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class ExitExp extends Expression {
    BlockExp block;
    Expression result;

    public ExitExp(Expression result, BlockExp block) {
        this.result = result;
        this.block = block;
    }

    public ExitExp(BlockExp block) {
        this.result = QuoteExp.voidExp;
        this.block = block;
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        throw new BlockExitException(this, this.result.eval(ctx));
    }

    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        (this.result == null ? QuoteExp.voidExp : this.result).compileWithPosition(comp, this.block.exitTarget);
        this.block.exitableBlock.exit();
    }

    protected Expression deepCopy(IdentityHashTable mapper) {
        Expression res = Expression.deepCopy(this.result, mapper);
        if (res == null && this.result != null) {
            return null;
        }
        Object b = mapper.get(this.block);
        if (b == null) {
            b = this.block;
        } else {
            BlockExp b2 = (BlockExp) b;
        }
        Expression copy = new ExitExp(res, b);
        copy.flags = getFlags();
        return copy;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitExitExp(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.result = visitor.visitAndUpdate(this.result, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Exit", false, ")");
        out.writeSpaceFill();
        if (this.block == null || this.block.label == null) {
            out.print("<unknown>");
        } else {
            out.print(this.block.label.getName());
        }
        if (this.result != null) {
            out.writeSpaceLinear();
            this.result.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Type getType() {
        return Type.neverReturnsType;
    }
}
