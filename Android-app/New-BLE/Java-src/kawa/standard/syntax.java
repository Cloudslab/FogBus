package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.PatternScope;
import kawa.lang.Quote;
import kawa.lang.SyntaxTemplate;
import kawa.lang.Translator;

public class syntax extends Quote {
    static final Method makeTemplateScopeMethod = typeTemplateScope.getDeclaredMethod("make", 0);
    public static final syntax quasiSyntax = new syntax("quasisyntax", true);
    public static final syntax syntax = new syntax("syntax", false);
    static final ClassType typeTemplateScope = ClassType.make("kawa.lang.TemplateScope");

    public syntax(String name, boolean isQuasi) {
        super(name, isQuasi);
    }

    protected boolean expandColonForms() {
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (form.getCdr() instanceof Pair) {
            form = (Pair) form.getCdr();
            if (form.getCdr() == LList.Empty) {
                Declaration saveTemplateScopeDecl = tr.templateScopeDecl;
                if (saveTemplateScopeDecl == null) {
                    tr.letStart();
                    Declaration templateScopeDecl = tr.letVariable(null, typeTemplateScope, new ApplyExp(makeTemplateScopeMethod, Expression.noExpressions));
                    templateScopeDecl.setCanRead();
                    tr.templateScopeDecl = templateScopeDecl;
                    tr.letEnter();
                }
                try {
                    Expression body = coerceExpression(expand(form.getCar(), this.isQuasi ? 1 : -1, tr), tr);
                    if (saveTemplateScopeDecl == null) {
                        body = tr.letDone(body);
                    }
                    tr.templateScopeDecl = saveTemplateScopeDecl;
                    return body;
                } catch (Throwable th) {
                    tr.templateScopeDecl = saveTemplateScopeDecl;
                }
            }
        }
        return tr.syntaxError("syntax forms requires a single form");
    }

    protected Expression leaf(Object val, Translator tr) {
        return makeSyntax(val, tr);
    }

    static Expression makeSyntax(Object form, Translator tr) {
        SyntaxTemplate template = new SyntaxTemplate(form, null, tr);
        Expression matchArray = QuoteExp.nullExp;
        PatternScope patternScope = tr.patternScope;
        if (!(patternScope == null || patternScope.matchArray == null)) {
            matchArray = new ReferenceExp(patternScope.matchArray);
        }
        return new ApplyExp(ClassType.make("kawa.lang.SyntaxTemplate").getDeclaredMethod("execute", 2), new Expression[]{new QuoteExp(template), matchArray, new ReferenceExp(tr.templateScopeDecl)});
    }
}
