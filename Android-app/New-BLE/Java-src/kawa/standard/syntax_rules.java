package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRules;
import kawa.lang.Translator;

public class syntax_rules extends Syntax {
    public static final syntax_rules syntax_rules = new syntax_rules();

    static {
        syntax_rules.setName("syntax-rules");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair pair = (Pair) form.getCdr();
        return new QuoteExp(new SyntaxRules(SyntaxPattern.getLiteralsList(pair.getCar(), null, tr), pair.getCdr(), tr));
    }
}
