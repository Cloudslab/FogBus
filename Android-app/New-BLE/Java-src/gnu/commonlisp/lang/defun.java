package gnu.commonlisp.lang;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class defun extends Syntax {
    Lambda lambdaSyntax;

    public defun(Lambda lambdaSyntax) {
        this.lambdaSyntax = lambdaSyntax;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (st.getCdr() instanceof Pair) {
            Pair p = (Pair) st.getCdr();
            if ((p.getCar() instanceof String) || (p.getCar() instanceof Symbol)) {
                Object sym = p.getCar();
                Declaration decl = defs.lookup(sym);
                if (decl == null) {
                    decl = new Declaration(sym);
                    decl.setProcedureDecl(true);
                    defs.addDeclaration(decl);
                } else {
                    tr.error('w', "duplicate declaration for `" + sym + "'");
                }
                if (defs instanceof ModuleExp) {
                    decl.setCanRead(true);
                }
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr())));
                return true;
            }
        }
        return super.scanForDefinitions(st, forms, defs, tr);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair obj = form.getCdr();
        Object name = null;
        Declaration decl = null;
        if (obj instanceof Pair) {
            Pair p1 = obj;
            Declaration p1_car = p1.getCar();
            if ((p1_car instanceof Symbol) || (p1_car instanceof String)) {
                name = p1_car.toString();
            } else if (p1_car instanceof Declaration) {
                decl = p1_car;
                name = decl.getSymbol();
            }
            if (name != null && (p1.getCdr() instanceof Pair)) {
                Pair p2 = (Pair) p1.getCdr();
                Expression lexp = new LambdaExp();
                this.lambdaSyntax.rewrite(lexp, p2.getCar(), p2.getCdr(), tr, null);
                lexp.setSymbol(name);
                if (p2 instanceof PairWithPosition) {
                    lexp.setLocation((PairWithPosition) p2);
                }
                Expression value = lexp;
                SetExp sexp = new SetExp(name, value);
                sexp.setDefining(true);
                sexp.setFuncDef(true);
                if (decl == null) {
                    return sexp;
                }
                sexp.setBinding(decl);
                if ((decl.context instanceof ModuleExp) && decl.getCanWrite()) {
                    value = null;
                }
                decl.noteValue(value);
                return sexp;
            }
        }
        return tr.syntaxError("invalid syntax for " + getName());
    }
}
