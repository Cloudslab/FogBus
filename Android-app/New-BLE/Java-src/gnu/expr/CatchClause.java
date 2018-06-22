package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Variable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

public class CatchClause extends LetExp {
    CatchClause next;

    public CatchClause() {
        super(new Expression[]{QuoteExp.voidExp});
    }

    public CatchClause(Object name, ClassType type) {
        this();
        addDeclaration(name, type);
    }

    public CatchClause(LambdaExp lexp) {
        this();
        Declaration decl = lexp.firstDecl();
        lexp.remove(null, decl);
        add(decl);
        this.body = lexp.body;
    }

    public final CatchClause getNext() {
        return this.next;
    }

    public final void setNext(CatchClause next) {
        this.next = next;
    }

    public final Expression getBody() {
        return this.body;
    }

    public final void setBody(Expression body) {
        this.body = body;
    }

    protected boolean mustCompile() {
        return false;
    }

    protected Object evalVariable(int i, CallContext ctx) throws Throwable {
        return ctx.value1;
    }

    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        Variable catchVar = firstDecl().allocateVariable(code);
        code.enterScope(getVarScope());
        code.emitCatchStart(catchVar);
        this.body.compileWithPosition(comp, target);
        code.emitCatchEnd();
        code.popScope();
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.body = visitor.visitAndUpdate(this.body, d);
    }

    public void print(OutPort out) {
        out.writeSpaceLinear();
        out.startLogicalBlock("(Catch", ")", 2);
        out.writeSpaceFill();
        this.decls.printInfo(out);
        out.writeSpaceLinear();
        this.body.print(out);
        out.endLogicalBlock(")");
    }
}
