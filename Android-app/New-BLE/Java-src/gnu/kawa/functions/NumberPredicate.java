package gnu.kawa.functions;

import android.support.v4.app.FragmentTransaction;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import gnu.math.IntNum;

public class NumberPredicate extends Procedure1 implements Inlineable {
    public static final int EVEN = 2;
    public static final int ODD = 1;
    Language language;
    final int op;

    protected final Language getLanguage() {
        return this.language;
    }

    public Object apply1(Object arg1) {
        boolean result;
        IntNum iarg1 = LangObjType.coerceIntNum(arg1);
        switch (this.op) {
            case 1:
                result = iarg1.isOdd();
                break;
            case 2:
                result = !iarg1.isOdd();
                break;
            default:
                throw new Error();
        }
        return getLanguage().booleanObject(result);
    }

    public NumberPredicate(Language language, String name, int op) {
        super(name);
        this.language = language;
        this.op = op;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberPredicate");
    }

    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length == 1 && (this.op == 1 || this.op == 2)) {
            Expression arg0 = args[0];
            if (Arithmetic.classifyType(arg0.getType()) <= 4) {
                Target wtarget = StackTarget.getInstance(Type.intType);
                CodeAttr code = comp.getCode();
                if (this.op == 2) {
                    code.emitPushInt(1);
                }
                arg0.compile(comp, wtarget);
                code.emitPushInt(1);
                code.emitAnd();
                if (this.op == 2) {
                    code.emitSub(Type.intType);
                }
                target.compileFromStack(comp, Type.booleanType);
                return;
            }
        }
        ApplyExp.compile(exp, comp, target);
    }
}
