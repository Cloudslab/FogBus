package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_variable extends Syntax {
    public static final define_variable define_variable = new define_variable();

    static {
        define_variable.setName("define-variable");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (!(st.getCdr() instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st.getCdr();
        Object sym = p.getCar();
        if ((sym instanceof String) || (sym instanceof Symbol)) {
            if (defs.lookup(sym) != null) {
                tr.error('e', "duplicate declaration for '" + sym + "'");
            }
            Declaration decl = defs.addDeclaration(sym);
            tr.push(decl);
            decl.setSimple(false);
            decl.setPrivate(true);
            decl.setFlag(268697600);
            decl.setCanRead(true);
            decl.setCanWrite(true);
            decl.setIndirectBinding(true);
            st = Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr()));
        }
        forms.addElement(st);
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair obj = form.getCdr();
        Expression value = null;
        Declaration decl = null;
        if (obj instanceof Pair) {
            Pair p1 = obj;
            Object obj2 = p1.getCar();
            if ((obj2 instanceof String) || (obj2 instanceof Symbol)) {
                return tr.syntaxError(getName() + " is only allowed in a <body>");
            }
            if (obj2 instanceof Declaration) {
                decl = (Declaration) p1.getCar();
                LList obj3 = p1.getCdr();
                if (obj3 instanceof Pair) {
                    p1 = (Pair) obj3;
                    if (p1.getCdr() == LList.Empty) {
                        value = tr.rewrite(p1.getCar());
                    }
                }
                if (obj3 != LList.Empty) {
                    decl = null;
                }
            }
        }
        if (decl == null) {
            return tr.syntaxError("invalid syntax for " + getName());
        }
        if (value == null) {
            return QuoteExp.voidExp;
        }
        Expression sexp = new SetExp(decl, value);
        sexp.setDefining(true);
        sexp.setSetIfUnbound(true);
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
