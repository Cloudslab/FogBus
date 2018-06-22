package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LetExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class let extends Syntax {
    public static final let let = new let();

    static {
        let.setName("let");
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let arguments");
        }
        Pair pair = (Pair) obj;
        SyntaxForm bindings = pair.getCar();
        Object body = pair.getCdr();
        int decl_count = Translator.listLength(bindings);
        if (decl_count < 0) {
            return tr.syntaxError("bindings not a proper list");
        }
        int i;
        Expression[] inits = new Expression[decl_count];
        Expression letExp = new LetExp(inits);
        Stack renamedAliases = null;
        int renamedAliasesCount = 0;
        SyntaxForm syntaxRest = null;
        for (i = 0; i < decl_count; i++) {
            while (bindings instanceof SyntaxForm) {
                syntaxRest = bindings;
                bindings = syntaxRest.getDatum();
            }
            Pair bind_pair = (Pair) bindings;
            Pair bind_pair_car = bind_pair.getCar();
            SyntaxForm syntax = syntaxRest;
            if (bind_pair_car instanceof SyntaxForm) {
                syntax = (SyntaxForm) bind_pair_car;
                bind_pair_car = syntax.getDatum();
            }
            if (!(bind_pair_car instanceof Pair)) {
                return tr.syntaxError("let binding is not a pair:" + bind_pair_car);
            }
            ScopeExp templateScope;
            Pair binding = bind_pair_car;
            Object name = binding.getCar();
            if (name instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) name;
                name = sf.getDatum();
                templateScope = sf.getScope();
            } else {
                templateScope = syntax == null ? null : syntax.getScope();
            }
            name = tr.namespaceResolve(name);
            if (!(name instanceof Symbol)) {
                return tr.syntaxError("variable " + name + " in let binding is not a symbol: " + obj);
            }
            Declaration decl = letExp.addDeclaration(name);
            decl.setFlag(262144);
            if (templateScope != null) {
                Declaration alias = tr.makeRenamedAlias(decl, templateScope);
                if (renamedAliases == null) {
                    renamedAliases = new Stack();
                }
                renamedAliases.push(alias);
                renamedAliasesCount++;
            }
            SyntaxForm binding_cdr = binding.getCdr();
            while (binding_cdr instanceof SyntaxForm) {
                syntax = binding_cdr;
                binding_cdr = syntax.getDatum();
            }
            if (!(binding_cdr instanceof Pair)) {
                return tr.syntaxError("let has no value for '" + name + "'");
            }
            Pair init;
            binding = (Pair) binding_cdr;
            LList binding_cdr2 = binding.getCdr();
            while (binding_cdr2 instanceof SyntaxForm) {
                syntax = (SyntaxForm) binding_cdr2;
                binding_cdr2 = syntax.getDatum();
            }
            if (tr.matches(binding.getCar(), "::")) {
                if (binding_cdr2 instanceof Pair) {
                    binding = (Pair) binding_cdr2;
                    if (binding.getCdr() != LList.Empty) {
                        binding_cdr2 = binding.getCdr();
                        while (binding_cdr2 instanceof SyntaxForm) {
                            syntax = (SyntaxForm) binding_cdr2;
                            binding_cdr2 = syntax.getDatum();
                        }
                    }
                }
                return tr.syntaxError("missing type after '::' in let");
            }
            if (binding_cdr2 == LList.Empty) {
                init = binding;
            } else if (!(binding_cdr2 instanceof Pair)) {
                return tr.syntaxError("let binding for '" + name + "' is improper list");
            } else {
                decl.setType(tr.exp2Type(binding));
                decl.setFlag(8192);
                init = (Pair) binding_cdr2;
            }
            inits[i] = tr.rewrite_car(init, syntax);
            if (init.getCdr() != LList.Empty) {
                return tr.syntaxError("junk after declaration of " + name);
            }
            decl.noteValue(inits[i]);
            bindings = bind_pair.getCdr();
        }
        i = renamedAliasesCount;
        while (true) {
            i--;
            if (i >= 0) {
                tr.pushRenamedAlias((Declaration) renamedAliases.pop());
            } else {
                tr.push((ScopeExp) letExp);
                letExp.body = tr.rewrite_body(body);
                tr.pop(letExp);
                tr.popRenamedAlias(renamedAliasesCount);
                return letExp;
            }
        }
    }
}
