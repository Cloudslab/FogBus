package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BlockExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ExitExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.math.IntNum;
import kawa.lang.Pattern;
import kawa.lang.PatternScope;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.SyntaxPattern;
import kawa.lang.Translator;

public class syntax_case extends Syntax {
    public static final syntax_case syntax_case = new syntax_case();
    PrimProcedure call_error;

    static {
        syntax_case.setName("syntax-case");
    }

    Expression rewriteClauses(Object clauses, syntax_case_work work, Translator tr) {
        Language language = tr.getLanguage();
        if (clauses == LList.Empty) {
            Expression[] args = new Expression[]{new QuoteExp("syntax-case"), new ReferenceExp(work.inputExpression)};
            if (this.call_error == null) {
                this.call_error = new PrimProcedure(ClassType.make("kawa.standard.syntax_case").addMethod("error", new Type[]{Compilation.javaStringType, Type.objectType}, Type.objectType, 9), language);
            }
            return new ApplyExp(this.call_error, args);
        }
        Object savePos = tr.pushPositionOf(clauses);
        try {
            Expression block;
            if (clauses instanceof Pair) {
                Object clause = ((Pair) clauses).getCar();
                if (clause instanceof Pair) {
                    Pair pair = (Pair) clause;
                    PatternScope clauseScope = PatternScope.push(tr);
                    clauseScope.matchArray = tr.matchArray;
                    tr.push((ScopeExp) clauseScope);
                    SyntaxForm syntax = null;
                    Object tail = pair.getCdr();
                    while (tail instanceof SyntaxForm) {
                        syntax = (SyntaxForm) tail;
                        tail = syntax.getDatum();
                    }
                    if (tail instanceof Pair) {
                        Expression output;
                        int outerVarCount = clauseScope.pattern_names.size();
                        int varCount = new SyntaxPattern(pair.getCar(), work.literal_identifiers, tr).varCount();
                        if (varCount > work.maxVars) {
                            work.maxVars = varCount;
                        }
                        block = new BlockExp();
                        Expression applyExp = new ApplyExp(new PrimProcedure(Pattern.matchPatternMethod, language), new Expression[]{new QuoteExp(r0), new ReferenceExp(work.inputExpression), new ReferenceExp(tr.matchArray), new QuoteExp(IntNum.zero())});
                        int newVarCount = varCount - outerVarCount;
                        Expression[] inits = new Expression[newVarCount];
                        int i = newVarCount;
                        while (true) {
                            i--;
                            if (i < 0) {
                                break;
                            }
                            inits[i] = QuoteExp.undefined_exp;
                        }
                        clauseScope.inits = inits;
                        pair = (Pair) tail;
                        if (pair.getCdr() == LList.Empty) {
                            output = tr.rewrite_car(pair, syntax);
                        } else {
                            Expression fender = tr.rewrite_car(pair, syntax);
                            if (pair.getCdr() instanceof Pair) {
                                pair = (Pair) pair.getCdr();
                                if (pair.getCdr() == LList.Empty) {
                                    applyExp = new IfExp(fender, tr.rewrite_car(pair, syntax), new ExitExp(block));
                                }
                            }
                            block = tr.syntaxError("syntax-case:  bad clause");
                            tr.popPositionOf(savePos);
                            return block;
                        }
                        clauseScope.setBody(output);
                        tr.pop(clauseScope);
                        PatternScope.pop(tr);
                        block.setBody(new IfExp(applyExp, clauseScope, new ExitExp(block)), rewriteClauses(((Pair) clauses).getCdr(), work, tr));
                        tr.popPositionOf(savePos);
                        return block;
                    }
                    block = tr.syntaxError("missing syntax-case output expression");
                    tr.popPositionOf(savePos);
                    return block;
                }
            }
            block = tr.syntaxError("syntax-case:  bad clause list");
            return block;
        } finally {
            tr.popPositionOf(savePos);
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        syntax_case_work work = new syntax_case_work();
        Pair obj = form.getCdr();
        if (!(obj instanceof Pair) || !(obj.getCdr() instanceof Pair)) {
            return tr.syntaxError("insufficiant arguments to syntax-case");
        }
        Expression[] linits = new Expression[2];
        LetExp let = new LetExp(linits);
        work.inputExpression = let.addDeclaration((Object) (String) null);
        Declaration matchArrayOuter = tr.matchArray;
        Declaration matchArray = let.addDeclaration((Object) (String) null);
        matchArray.setType(Compilation.objArrayType);
        matchArray.setCanRead(true);
        tr.matchArray = matchArray;
        work.inputExpression.setCanRead(true);
        tr.push((ScopeExp) let);
        form = obj;
        linits[0] = tr.rewrite(form.getCar());
        work.inputExpression.noteValue(linits[0]);
        form = (Pair) form.getCdr();
        work.literal_identifiers = SyntaxPattern.getLiteralsList(form.getCar(), null, tr);
        let.body = rewriteClauses(form.getCdr(), work, tr);
        tr.pop(let);
        Method allocVars = ClassType.make("kawa.lang.SyntaxPattern").getDeclaredMethod("allocVars", 2);
        Expression[] args = new Expression[2];
        args[0] = new QuoteExp(IntNum.make(work.maxVars));
        if (matchArrayOuter == null) {
            args[1] = QuoteExp.nullExp;
        } else {
            args[1] = new ReferenceExp(matchArrayOuter);
        }
        linits[1] = new ApplyExp(allocVars, args);
        matchArray.noteValue(linits[1]);
        tr.matchArray = matchArrayOuter;
        return let;
    }

    public static Object error(String kind, Object arg) {
        Translator tr = (Translator) Compilation.getCurrent();
        if (tr == null) {
            throw new RuntimeException("no match in syntax-case");
        }
        Syntax syntax = tr.getCurrentSyntax();
        return tr.syntaxError("no matching case while expanding " + (syntax == null ? "some syntax" : syntax.getName()));
    }
}
