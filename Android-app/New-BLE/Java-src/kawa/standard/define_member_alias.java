package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_member_alias extends Syntax {
    public static final define_member_alias define_member_alias = new define_member_alias();

    static {
        define_member_alias.setName("define-member-alias");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if ((st.getCdr() instanceof Pair) && !(tr.currentScope() instanceof ModuleExp)) {
            Pair p = (Pair) st.getCdr();
            if (p.getCar() instanceof String) {
                Declaration decl = defs.addDeclaration((String) p.getCar(), Compilation.typeSymbol);
                decl.setIndirectBinding(true);
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr())));
                return true;
            }
        }
        return super.scanForDefinitions(st, forms, defs, tr);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair p1 = obj;
            if ((p1.getCar() instanceof String) || (p1.getCar() instanceof Declaration)) {
                if (p1.getCdr() instanceof Pair) {
                    String name;
                    Declaration p1_car = p1.getCar();
                    if (p1_car instanceof Declaration) {
                        name = p1_car.getName();
                    } else {
                        name = (String) p1_car;
                    }
                    Pair p2 = (Pair) p1.getCdr();
                    Expression fname = null;
                    Expression arg = tr.rewrite(p2.getCar());
                    LList p2_cdr = p2.getCdr();
                    if (p2_cdr == LList.Empty) {
                        fname = new QuoteExp(Compilation.mangleName(name));
                    } else if (p2_cdr instanceof Pair) {
                        Pair p3 = (Pair) p2_cdr;
                        if (p3.getCdr() == LList.Empty) {
                            fname = tr.rewrite(p3.getCar());
                        }
                    }
                    if (fname != null) {
                        return Invoke.makeInvokeStatic(ClassType.make("gnu.kawa.reflect.ClassMemberConstraint"), "define", new Expression[]{new QuoteExp(name), arg, fname});
                    }
                }
                return tr.syntaxError("invalid syntax for " + getName());
            }
        }
        return tr.syntaxError("missing name in " + getName());
    }
}
