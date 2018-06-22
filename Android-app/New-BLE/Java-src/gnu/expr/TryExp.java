package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.mapping.OutPort;

public class TryExp extends Expression {
    CatchClause catch_clauses;
    Expression finally_clause;
    Expression try_clause;

    public void apply(gnu.mapping.CallContext r7) throws java.lang.Throwable {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r6 = this;
        r4 = r6.try_clause;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r4.apply(r7);	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r7.runUntilDone();	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r4 = r6.finally_clause;
        if (r4 == 0) goto L_0x0011;
    L_0x000c:
        r4 = r6.finally_clause;
        r4.eval(r7);
    L_0x0011:
        return;
    L_0x0012:
        r2 = move-exception;
        r0 = r6.catch_clauses;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
    L_0x0015:
        if (r0 == 0) goto L_0x003d;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
    L_0x0017:
        r1 = r0.firstDecl();	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r4 = r1.getTypeExp();	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r3 = r4.eval(r7);	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r3 = (gnu.bytecode.ClassType) r3;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r4 = r3.isInstance(r2);	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        if (r4 == 0) goto L_0x003a;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
    L_0x002b:
        r7.value1 = r2;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r0.apply(r7);	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        r4 = r6.finally_clause;
        if (r4 == 0) goto L_0x0011;
    L_0x0034:
        r4 = r6.finally_clause;
        r4.eval(r7);
        goto L_0x0011;
    L_0x003a:
        r0 = r0.next;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
        goto L_0x0015;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
    L_0x003d:
        throw r2;	 Catch:{ Throwable -> 0x0012, all -> 0x003e }
    L_0x003e:
        r4 = move-exception;
        r5 = r6.finally_clause;
        if (r5 == 0) goto L_0x0048;
    L_0x0043:
        r5 = r6.finally_clause;
        r5.eval(r7);
    L_0x0048:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.TryExp.apply(gnu.mapping.CallContext):void");
    }

    public final CatchClause getCatchClauses() {
        return this.catch_clauses;
    }

    public final Expression getFinallyClause() {
        return this.finally_clause;
    }

    public final void setCatchClauses(CatchClause catch_clauses) {
        this.catch_clauses = catch_clauses;
    }

    public TryExp(Expression try_clause, Expression finally_clause) {
        this.try_clause = try_clause;
        this.finally_clause = finally_clause;
    }

    protected boolean mustCompile() {
        return false;
    }

    public void compile(Compilation comp, Target target) {
        Target ttarg;
        CodeAttr code = comp.getCode();
        boolean has_finally = this.finally_clause != null;
        if ((target instanceof StackTarget) || (target instanceof ConsumerTarget) || (target instanceof IgnoreTarget) || ((target instanceof ConditionalTarget) && this.finally_clause == null)) {
            ttarg = target;
        } else {
            ttarg = Target.pushValue(target.getType());
        }
        code.emitTryStart(has_finally, ttarg instanceof StackTarget ? ttarg.getType() : null);
        this.try_clause.compileWithPosition(comp, ttarg);
        for (CatchClause catch_clause = this.catch_clauses; catch_clause != null; catch_clause = catch_clause.getNext()) {
            catch_clause.compile(comp, ttarg);
        }
        if (this.finally_clause != null) {
            code.emitFinallyStart();
            this.finally_clause.compileWithPosition(comp, Target.Ignore);
            code.emitFinallyEnd();
        }
        code.emitTryCatchEnd();
        if (ttarg != target) {
            target.compileFromStack(comp, target.getType());
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitTryExp(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.try_clause = visitor.visitAndUpdate(this.try_clause, d);
        CatchClause catch_clause = this.catch_clauses;
        while (visitor.exitValue == null && catch_clause != null) {
            visitor.visit(catch_clause, d);
            catch_clause = catch_clause.getNext();
        }
        if (visitor.exitValue == null && this.finally_clause != null) {
            this.finally_clause = visitor.visitAndUpdate(this.finally_clause, d);
        }
    }

    public Type getType() {
        if (this.catch_clauses == null) {
            return this.try_clause.getType();
        }
        return super.getType();
    }

    public void print(OutPort ps) {
        ps.startLogicalBlock("(Try", ")", 2);
        ps.writeSpaceFill();
        this.try_clause.print(ps);
        for (CatchClause catch_clause = this.catch_clauses; catch_clause != null; catch_clause = catch_clause.getNext()) {
            catch_clause.print(ps);
        }
        if (this.finally_clause != null) {
            ps.writeSpaceLinear();
            ps.print(" finally: ");
            this.finally_clause.print(ps);
        }
        ps.endLogicalBlock(")");
    }
}
