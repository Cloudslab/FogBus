package gnu.expr;

import gnu.bytecode.Type;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.util.IdentityHashTable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.text.Printable;
import gnu.text.SourceLocator;
import java.io.PrintWriter;

public abstract class Expression extends Procedure0 implements Printable, SourceLocator {
    protected static final int NEXT_AVAIL_FLAG = 2;
    public static final int VALIDATED = 1;
    public static final Expression[] noExpressions = new Expression[0];
    String filename;
    protected int flags;
    int position;

    public abstract void compile(Compilation compilation, Target target);

    protected abstract boolean mustCompile();

    public abstract void print(OutPort outPort);

    public final Object eval(CallContext ctx) throws Throwable {
        int start = ctx.startFromContext();
        try {
            match0(ctx);
            return ctx.getFromContext(start);
        } catch (Throwable th) {
            ctx.cleanupFromContext(start);
        }
    }

    public final Object eval(Environment env) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        Environment saveEnv = Environment.setSaveCurrent(env);
        try {
            Object eval = eval(ctx);
            return eval;
        } finally {
            Environment.restoreCurrent(saveEnv);
        }
    }

    public final int match0(CallContext ctx) {
        ctx.proc = this;
        ctx.pc = 0;
        return 0;
    }

    public final Object apply0() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        check0(ctx);
        return ctx.runUntilValue();
    }

    public void apply(CallContext ctx) throws Throwable {
        throw new RuntimeException("internal error - " + getClass() + ".eval called");
    }

    public final void print(Consumer out) {
        if (out instanceof OutPort) {
            print((OutPort) out);
        } else if (out instanceof PrintWriter) {
            port = new OutPort((PrintWriter) out);
            print(port);
            port.close();
        } else {
            port = new CharArrayOutPort();
            print(port);
            port.close();
            port.writeTo(out);
        }
    }

    public void printLineColumn(OutPort out) {
        int line = getLineNumber();
        if (line > 0) {
            out.print("line:");
            out.print(line);
            int column = getColumnNumber();
            if (column > 0) {
                out.print(':');
                out.print(column);
            }
            out.writeSpaceFill();
        }
    }

    public final void compileWithPosition(Compilation comp, Target target) {
        int line = getLineNumber();
        if (line > 0) {
            comp.getCode().putLineNumber(getFileName(), line);
            compileNotePosition(comp, target, this);
            return;
        }
        compile(comp, target);
    }

    public final void compileWithPosition(Compilation comp, Target target, Expression position) {
        int line = position.getLineNumber();
        if (line > 0) {
            comp.getCode().putLineNumber(position.getFileName(), line);
            compileNotePosition(comp, target, position);
            return;
        }
        compile(comp, target);
    }

    public final void compileNotePosition(Compilation comp, Target target, Expression position) {
        String saveFilename = comp.getFileName();
        int saveLine = comp.getLineNumber();
        int saveColumn = comp.getColumnNumber();
        comp.setLine(position);
        compile(comp, target);
        comp.setLine(saveFilename, saveLine, saveColumn);
    }

    public final void compile(Compilation comp, Type type) {
        compile(comp, StackTarget.getInstance(type));
    }

    public final void compile(Compilation comp, Declaration lhs) {
        compile(comp, CheckedTarget.getInstance(lhs));
    }

    public static void compileButFirst(Expression exp, Compilation comp) {
        if (exp instanceof BeginExp) {
            BeginExp bexp = (BeginExp) exp;
            int n = bexp.length;
            if (n != 0) {
                Expression[] exps = bexp.exps;
                compileButFirst(exps[0], comp);
                for (int i = 1; i < n; i++) {
                    exps[i].compileWithPosition(comp, Target.Ignore);
                }
            }
        }
    }

    public static Expression deepCopy(Expression exp, IdentityHashTable mapper) {
        if (exp == null) {
            return null;
        }
        Object tr = mapper.get(exp);
        if (tr != null) {
            return (Expression) tr;
        }
        Expression copy = exp.deepCopy(mapper);
        mapper.put(exp, copy);
        return copy;
    }

    public static Expression[] deepCopy(Expression[] exps, IdentityHashTable mapper) {
        if (exps == null) {
            return null;
        }
        int nargs = exps.length;
        Expression[] a = new Expression[nargs];
        for (int i = 0; i < nargs; i++) {
            Expression ei = exps[i];
            Expression ai = deepCopy(ei, mapper);
            if (ai == null && ei != null) {
                return null;
            }
            a[i] = ai;
        }
        return a;
    }

    protected static Expression deepCopy(Expression exp) {
        return deepCopy(exp, new IdentityHashTable());
    }

    protected Expression deepCopy(IdentityHashTable mapper) {
        return null;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitExpression(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> expVisitor, D d) {
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        exp.args = visitor.visitExps(exp.args, null);
        return exp;
    }

    public static Expression makeWhile(Object cond, Object body, Compilation parser) {
        Expression[] inits = new Expression[1];
        LetExp let = new LetExp(inits);
        String fname = "%do%loop";
        Declaration fdecl = let.addDeclaration((Object) fname);
        Expression recurse = new ApplyExp(new ReferenceExp(fdecl), noExpressions);
        ScopeExp lexp = new LambdaExp();
        parser.push(lexp);
        lexp.body = new IfExp(parser.parse(cond), new BeginExp(parser.parse(body), recurse), QuoteExp.voidExp);
        lexp.setName(fname);
        parser.pop(lexp);
        inits[0] = lexp;
        fdecl.noteValue(lexp);
        let.setBody(new ApplyExp(new ReferenceExp(fdecl), noExpressions));
        return let;
    }

    public final void setLocation(SourceLocator location) {
        this.filename = location.getFileName();
        setLine(location.getLineNumber(), location.getColumnNumber());
    }

    public final Expression setLine(Expression old) {
        setLocation(old);
        return this;
    }

    public final void setFile(String filename) {
        this.filename = filename;
    }

    public final void setLine(int lineno, int colno) {
        if (lineno < 0) {
            lineno = 0;
        }
        if (colno < 0) {
            colno = 0;
        }
        this.position = (lineno << 12) + colno;
    }

    public final void setLine(int lineno) {
        setLine(lineno, 0);
    }

    public final String getFileName() {
        return this.filename;
    }

    public void setLine(Compilation comp) {
        int line = comp.getLineNumber();
        if (line > 0) {
            setFile(comp.getFileName());
            setLine(line, comp.getColumnNumber());
        }
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return this.filename;
    }

    public final int getLineNumber() {
        int line = this.position >> 12;
        return line == 0 ? -1 : line;
    }

    public final int getColumnNumber() {
        int column = this.position & 4095;
        return column == 0 ? -1 : column;
    }

    public boolean isStableSourceLocation() {
        return true;
    }

    public Type getType() {
        return Type.pointer_type;
    }

    public boolean isSingleValue() {
        return OccurrenceType.itemCountIsOne(getType());
    }

    public Object valueIfConstant() {
        return null;
    }

    public void setFlag(boolean setting, int flag) {
        if (setting) {
            this.flags |= flag;
        } else {
            this.flags &= flag ^ -1;
        }
    }

    public void setFlag(int flag) {
        this.flags |= flag;
    }

    public int getFlags() {
        return this.flags;
    }

    public boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    public boolean side_effects() {
        return true;
    }

    public String toString() {
        String tname = getClass().getName();
        if (tname.startsWith("gnu.expr.")) {
            tname = tname.substring(9);
        }
        return tname + GetNamedPart.CAST_METHOD_NAME + Integer.toHexString(hashCode());
    }
}
