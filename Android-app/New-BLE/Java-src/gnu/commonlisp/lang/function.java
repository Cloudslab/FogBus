package gnu.commonlisp.lang;

import gnu.expr.Expression;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class function extends Syntax {
    Syntax lambda;

    public function(Syntax lambda) {
        this.lambda = lambda;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair pair = obj;
            if (pair.getCdr() != LList.Empty) {
                return tr.syntaxError("too many forms after 'function'");
            }
            Pair name = pair.getCar();
            if ((name instanceof String) || (name instanceof Symbol)) {
                Expression rexp = new ReferenceExp((Object) name);
                rexp.setProcedureName(true);
                rexp.setFlag(8);
                return rexp;
            } else if (name instanceof Pair) {
                pair = name;
                Object name2 = pair.getCar();
                if ((name2 instanceof String) ? !"lambda".equals(name2) : !((name2 instanceof Symbol) && "lambda".equals(((Symbol) name2).getName()))) {
                    return this.lambda.rewriteForm(pair, tr);
                }
            }
        }
        return tr.syntaxError("function must be followed by name or lambda expression");
    }
}
