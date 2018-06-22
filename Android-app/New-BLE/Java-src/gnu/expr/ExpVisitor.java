package gnu.expr;

import gnu.text.SourceLocator;
import gnu.text.SourceMessages;

public class ExpVisitor<R, D> implements SourceLocator {
    Compilation comp;
    protected LambdaExp currentLambda = null;
    protected Object exitValue = null;
    protected SourceMessages messages;

    public Compilation getCompilation() {
        return this.comp;
    }

    public SourceMessages getMessages() {
        return this.messages;
    }

    protected R defaultValue(Expression r, D d) {
        return null;
    }

    protected R visitExpression(Expression exp, D d) {
        exp.visitChildren(this, d);
        return defaultValue(exp, d);
    }

    public void setContext(Compilation comp) {
        this.comp = comp;
        this.messages = comp.getMessages();
    }

    public R visit(Expression exp, D d) {
        int line = exp.getLineNumber();
        if (this.messages == null || line <= 0) {
            return exp.visit(this, d);
        }
        String saveFile = this.messages.getFileName();
        int saveLine = this.messages.getLineNumber();
        int saveColumn = this.messages.getColumnNumber();
        this.messages.setLine(exp.getFileName(), line, exp.getColumnNumber());
        R ret = exp.visit(this, d);
        this.messages.setLine(saveFile, saveLine, saveColumn);
        return ret;
    }

    protected Expression update(Expression exp, R r) {
        return exp;
    }

    protected R visitApplyExp(ApplyExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitIfExp(IfExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected final void visitDeclarationType(Declaration decl) {
        Expression texp1 = decl.typeExp;
        if (texp1 != null) {
            Expression texp2 = visitAndUpdate(texp1, null);
            if (texp2 != texp1) {
                decl.setTypeExp(texp2);
            }
        }
    }

    protected final void visitDeclarationTypes(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            visitDeclarationType(decl);
        }
    }

    protected R visitScopeExp(ScopeExp exp, D d) {
        visitDeclarationTypes(exp);
        return visitExpression(exp, d);
    }

    protected R visitLetExp(LetExp exp, D d) {
        return visitScopeExp(exp, d);
    }

    protected R visitLambdaExp(LambdaExp exp, D d) {
        return visitScopeExp(exp, d);
    }

    protected R visitClassExp(ClassExp exp, D d) {
        return visitLambdaExp(exp, d);
    }

    protected R visitObjectExp(ObjectExp exp, D d) {
        return visitClassExp(exp, d);
    }

    protected R visitModuleExp(ModuleExp exp, D d) {
        return visitLambdaExp(exp, d);
    }

    protected Expression visitSetExpValue(Expression new_value, D d, Declaration decl) {
        return visitAndUpdate(new_value, d);
    }

    protected R visitSetExp(SetExp exp, D d) {
        Declaration decl = exp.binding;
        boolean updateNeeded = decl != null && decl.value == exp.new_value;
        exp.new_value = visitSetExpValue(exp.new_value, d, exp.getBinding());
        if (updateNeeded && exp.isDefining()) {
            decl.value = exp.new_value;
            if (exp.new_value instanceof LambdaExp) {
                ((LambdaExp) exp.new_value).nameDecl = decl;
            }
        }
        return defaultValue(exp, d);
    }

    protected R visitTryExp(TryExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitBeginExp(BeginExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitQuoteExp(QuoteExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitReferenceExp(ReferenceExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitThisExp(ThisExp exp, D d) {
        return visitReferenceExp(exp, d);
    }

    protected R visitSynchronizedExp(SynchronizedExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitBlockExp(BlockExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitExitExp(ExitExp exp, D d) {
        return visitExpression(exp, d);
    }

    protected R visitFluidLetExp(FluidLetExp exp, D d) {
        return visitLetExp(exp, d);
    }

    protected R visitLangExp(LangExp exp, D d) {
        return visitExpression(exp, d);
    }

    public Object getExitValue() {
        return this.exitValue;
    }

    public final LambdaExp getCurrentLambda() {
        return this.currentLambda;
    }

    public Expression visitAndUpdate(Expression exp, D d) {
        return update(exp, visit(exp, d));
    }

    public Expression[] visitExps(Expression[] exps, D d) {
        return exps == null ? null : visitExps(exps, exps.length, d);
    }

    public Expression[] visitExps(Expression[] exps, int n, D d) {
        for (int i = 0; i < n && this.exitValue == null; i++) {
            exps[i] = visitAndUpdate(exps[i], d);
        }
        return exps;
    }

    public void visitDefaultArgs(LambdaExp exp, D d) {
        exp.defaultArgs = visitExps(exp.defaultArgs, d);
    }

    public void error(char kind, String message) {
        if (kind == 'w' && this.comp.warnAsError()) {
            kind = 'e';
        }
        if (this.messages != null) {
            this.messages.error(kind, message);
        } else {
            Error error = new Error("internal error: " + message);
        }
    }

    public Expression noteError(String message) {
        if (this.messages != null) {
            this.messages.error('e', message);
        }
        return new ErrorExp(message);
    }

    public final String getFileName() {
        return this.messages.getFileName();
    }

    public final int getLineNumber() {
        return this.messages.getLineNumber();
    }

    public final int getColumnNumber() {
        return this.messages.getColumnNumber();
    }

    public String getPublicId() {
        return this.messages.getPublicId();
    }

    public String getSystemId() {
        return this.messages.getSystemId();
    }

    public boolean isStableSourceLocation() {
        return false;
    }

    public void setFile(String filename) {
        this.messages.setFile(filename);
    }

    public void setLine(int line) {
        this.messages.setLine(line);
    }

    public void setColumn(int column) {
        this.messages.setColumn(column);
    }

    public void setLine(String filename, int line, int column) {
        this.messages.setLine(filename, line, column);
    }
}
