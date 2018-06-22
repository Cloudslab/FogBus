package gnu.commonlisp.lang;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class setq extends Syntax {
    public Expression rewriteForm(Pair form, Translator tr) {
        LList obj = form.getCdr();
        Vector results = null;
        while (obj != LList.Empty) {
            if (!(obj instanceof Pair)) {
                return tr.syntaxError("invalid syntax for setq");
            }
            Object obj2;
            Pair pair = (Pair) obj;
            LList sym = pair.getCar();
            if ((sym instanceof Symbol) || (sym instanceof String)) {
                obj2 = sym;
            } else if (sym != CommonLisp.FALSE) {
                return tr.syntaxError("invalid variable name in setq");
            } else {
                obj2 = "nil";
            }
            Pair obj3 = pair.getCdr();
            if (!(obj3 instanceof Pair)) {
                return tr.syntaxError("wrong number of arguments for setq");
            }
            pair = obj3;
            Expression value = tr.rewrite(pair.getCar());
            obj = pair.getCdr();
            Expression sexp = new SetExp(obj2, value);
            sexp.setFlag(8);
            if (obj == LList.Empty) {
                sexp.setHasValue(true);
                if (results == null) {
                    return sexp;
                }
            }
            if (results == null) {
                results = new Vector(10);
            }
            results.addElement(sexp);
        }
        if (results == null) {
            return CommonLisp.nilExpr;
        }
        Expression[] stmts = new Expression[results.size()];
        results.copyInto(stmts);
        return new BeginExp(stmts);
    }
}
