package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Stack;
import kawa.lang.Macro;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class let_syntax extends Syntax {
    public static final let_syntax let_syntax = new let_syntax(false, "let-syntax");
    public static final let_syntax letrec_syntax = new let_syntax(true, "letrec-syntax");
    boolean recursive;

    public let_syntax(boolean recursive, String name) {
        super(name);
        this.recursive = recursive;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let-syntax arguments");
        }
        Pair pair = (Pair) obj;
        SyntaxForm bindings = pair.getCar();
        Object body = pair.getCdr();
        int decl_count = Translator.listLength(bindings);
        if (decl_count < 0) {
            return tr.syntaxError("bindings not a proper list");
        }
        int i;
        Stack renamedAliases = null;
        int renamedAliasesCount = 0;
        Expression[] inits = new Expression[decl_count];
        Declaration[] decls = new Declaration[decl_count];
        Macro[] macros = new Macro[decl_count];
        Pair[] transformers = new Pair[decl_count];
        SyntaxForm[] trSyntax = new SyntaxForm[decl_count];
        ScopeExp letExp = new LetExp(inits);
        SyntaxForm listSyntax = null;
        for (i = 0; i < decl_count; i++) {
            while (bindings instanceof SyntaxForm) {
                listSyntax = bindings;
                bindings = listSyntax.getDatum();
            }
            SyntaxForm bindingSyntax = listSyntax;
            Pair bind_pair = (Pair) bindings;
            Pair bind_pair_car = bind_pair.getCar();
            if (bind_pair_car instanceof SyntaxForm) {
                bindingSyntax = (SyntaxForm) bind_pair_car;
                bind_pair_car = bindingSyntax.getDatum();
            }
            if (!(bind_pair_car instanceof Pair)) {
                return tr.syntaxError(getName() + " binding is not a pair");
            }
            Pair binding = bind_pair_car;
            SyntaxForm name = binding.getCar();
            SyntaxForm nameSyntax = bindingSyntax;
            while (name instanceof SyntaxForm) {
                nameSyntax = name;
                name = nameSyntax.getDatum();
            }
            if (!(name instanceof String) && !(name instanceof Symbol)) {
                return tr.syntaxError("variable in " + getName() + " binding is not a symbol");
            }
            SyntaxForm binding_cdr = binding.getCdr();
            while (binding_cdr instanceof SyntaxForm) {
                bindingSyntax = binding_cdr;
                binding_cdr = bindingSyntax.getDatum();
            }
            if (!(binding_cdr instanceof Pair)) {
                return tr.syntaxError(getName() + " has no value for '" + name + "'");
            }
            binding = (Pair) binding_cdr;
            if (binding.getCdr() != LList.Empty) {
                return tr.syntaxError("let binding for '" + name + "' is improper list");
            }
            Declaration decl = new Declaration((Object) name);
            Macro macro = Macro.make(decl);
            macros[i] = macro;
            transformers[i] = binding;
            trSyntax[i] = bindingSyntax;
            letExp.addDeclaration(decl);
            ScopeExp templateScope = nameSyntax == null ? null : nameSyntax.getScope();
            if (templateScope != null) {
                Declaration alias = tr.makeRenamedAlias(decl, templateScope);
                if (renamedAliases == null) {
                    renamedAliases = new Stack();
                }
                renamedAliases.push(alias);
                renamedAliasesCount++;
            }
            ScopeExp scope = bindingSyntax != null ? bindingSyntax.getScope() : this.recursive ? letExp : tr.currentScope();
            macro.setCapturedScope(scope);
            decls[i] = decl;
            inits[i] = QuoteExp.nullExp;
            bindings = bind_pair.getCdr();
        }
        if (this.recursive) {
            push(letExp, tr, renamedAliases);
        }
        Macro savedMacro = tr.currentMacroDefinition;
        for (i = 0; i < decl_count; i++) {
            macro = macros[i];
            tr.currentMacroDefinition = macro;
            Expression value = tr.rewrite_car(transformers[i], trSyntax[i]);
            inits[i] = value;
            decl = decls[i];
            macro.expander = value;
            decl.noteValue(new QuoteExp(macro));
            if (value instanceof LambdaExp) {
                LambdaExp lvalue = (LambdaExp) value;
                lvalue.nameDecl = decl;
                lvalue.setSymbol(decl.getSymbol());
            }
        }
        tr.currentMacroDefinition = savedMacro;
        if (!this.recursive) {
            push(letExp, tr, renamedAliases);
        }
        Expression result = tr.rewrite_body(body);
        tr.pop(letExp);
        tr.popRenamedAlias(renamedAliasesCount);
        return result;
    }

    private void push(LetExp let, Translator tr, Stack renamedAliases) {
        tr.push((ScopeExp) let);
        if (renamedAliases != null) {
            int i = renamedAliases.size();
            while (true) {
                i--;
                if (i >= 0) {
                    tr.pushRenamedAlias((Declaration) renamedAliases.pop());
                } else {
                    return;
                }
            }
        }
    }
}
