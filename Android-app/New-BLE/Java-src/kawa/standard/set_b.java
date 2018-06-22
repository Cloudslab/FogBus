package kawa.standard;

import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompilationHelpers;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class set_b extends Syntax {
    public static final set_b set = new set_b();

    static {
        set.setName("set!");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        SyntaxForm o1 = form.getCdr();
        SyntaxForm syntax = null;
        while (o1 instanceof SyntaxForm) {
            syntax = o1;
            o1 = syntax.getDatum();
        }
        if (!(o1 instanceof Pair)) {
            return tr.syntaxError("missing name");
        }
        Pair p1 = (Pair) o1;
        Expression name = tr.rewrite_car(p1, syntax);
        SyntaxForm o2 = p1.getCdr();
        while (o2 instanceof SyntaxForm) {
            syntax = o2;
            o2 = syntax.getDatum();
        }
        if (o2 instanceof Pair) {
            Pair p2 = (Pair) o2;
            if (p2.getCdr() == LList.Empty) {
                Expression value = tr.rewrite_car(p2, syntax);
                if (name instanceof ApplyExp) {
                    ApplyExp aexp = (ApplyExp) name;
                    Expression[] args = aexp.getArgs();
                    int nargs = args.length;
                    int skip = 0;
                    Expression func = aexp.getFunction();
                    if (args.length > 0 && (func instanceof ReferenceExp) && ((ReferenceExp) func).getBinding() == Scheme.applyFieldDecl) {
                        skip = 1;
                        nargs--;
                        func = args[0];
                    }
                    Expression[] setterArgs = new Expression[]{func};
                    Object xargs = new Expression[(nargs + 1)];
                    System.arraycopy(args, skip, xargs, 0, nargs);
                    xargs[nargs] = value;
                    return new ApplyExp(new ApplyExp(new ReferenceExp(CompilationHelpers.setterDecl), setterArgs), (Expression[]) xargs);
                } else if (!(name instanceof ReferenceExp)) {
                    return tr.syntaxError("first set! argument is not a variable name");
                } else {
                    ReferenceExp ref = (ReferenceExp) name;
                    Declaration decl = ref.getBinding();
                    Expression setExp = new SetExp(ref.getSymbol(), value);
                    setExp.setContextDecl(ref.contextDecl());
                    if (decl == null) {
                        return setExp;
                    }
                    decl.setCanWrite(true);
                    setExp.setBinding(decl);
                    decl = Declaration.followAliases(decl);
                    if (decl != null) {
                        decl.noteValue(value);
                    }
                    if (decl.getFlag(16384)) {
                        return tr.syntaxError("constant variable " + decl.getName() + " is set!");
                    }
                    if (decl.context == tr.mainLambda || !(decl.context instanceof ModuleExp) || decl.getFlag(268435456) || decl.context.getFlag(1048576)) {
                        return setExp;
                    }
                    tr.error('w', decl, "imported variable ", " is set!");
                    return setExp;
                }
            }
        }
        return tr.syntaxError("missing or extra arguments to set!");
    }
}
