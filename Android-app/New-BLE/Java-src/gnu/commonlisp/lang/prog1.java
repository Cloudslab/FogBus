package gnu.commonlisp.lang;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class prog1 extends Syntax {
    public static final prog1 prog1 = new prog1("prog1", 1);
    public static final prog1 prog2 = new prog1("prog2", 2);
    int index;

    public prog1(String name, int index) {
        this.index = index;
        setName(name);
    }

    public Expression rewrite(Object obj, Translator tr) {
        int nexps = LList.length(obj);
        if (nexps < this.index) {
            return tr.syntaxError("too few expressions in " + getName());
        }
        if (this.index == 2) {
            Pair pair = (Pair) obj;
            return new BeginExp(tr.rewrite(pair.getCar()), prog1.rewrite(pair.getCdr(), tr));
        }
        Expression[] inits = new Expression[1];
        Expression let = new LetExp(inits);
        Expression[] body = new Expression[nexps];
        pair = (Pair) obj;
        inits[0] = tr.rewrite(pair.getCar());
        Pair obj2 = pair.getCdr();
        for (int i = 0; i < nexps - 1; i++) {
            pair = obj2;
            body[i] = tr.rewrite(pair.getCar());
            obj2 = pair.getCdr();
        }
        body[nexps - 1] = new ReferenceExp(let.addDeclaration(null));
        let.body = BeginExp.canonicalize(body);
        tr.mustCompileHere();
        return let;
    }
}
