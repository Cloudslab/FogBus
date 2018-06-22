package kawa.standard;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.ScopeExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.Options;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class with_compile_options extends Syntax {
    public static final with_compile_options with_compile_options = new with_compile_options();

    static {
        with_compile_options.setName("with-compile-options");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Stack stack = new Stack();
        LList rest = getOptions(form.getCdr(), stack, this, tr);
        if (rest != LList.Empty) {
            if (rest == form.getCdr()) {
                tr.scanBody(rest, defs, false);
                return;
            }
            Pair rest2 = new Pair(stack, tr.scanBody(rest, defs, true));
            tr.currentOptions.popOptionValues(stack);
            tr.formStack.add(Translator.makePair(form, form.getCar(), rest2));
        }
    }

    public static Object getOptions(Object form, Stack stack, Syntax command, Translator tr) {
        boolean seenKey = false;
        Options options = tr.currentOptions;
        SyntaxForm syntax = null;
        loop2:
        while (true) {
            if (!(form instanceof SyntaxForm)) {
                if (!(form instanceof Pair)) {
                    break loop2;
                }
                Pair pair = (Pair) form;
                Object pair_car = Translator.stripSyntax(pair.getCar());
                if (!(pair_car instanceof Keyword)) {
                    break loop2;
                }
                String key = ((Keyword) pair_car).getName();
                seenKey = true;
                Object savePos = tr.pushPositionOf(pair);
                form = pair.getCdr();
                while (form instanceof SyntaxForm) {
                    syntax = (SyntaxForm) form;
                    form = syntax.getDatum();
                }
                if (form instanceof Pair) {
                    pair = (Pair) form;
                    Object value = Translator.stripSyntax(pair.getCar());
                    form = pair.getCdr();
                    Object oldValue = options.getLocal(key);
                    if (options.getInfo(key) == null) {
                        tr.error('w', "unknown compile option: " + key);
                    } else {
                        if (value instanceof FString) {
                            value = value.toString();
                        } else {
                            try {
                                if (!((value instanceof Boolean) || (value instanceof Number))) {
                                    value = null;
                                    tr.error('e', "invalid literal value for key " + key);
                                }
                            } finally {
                                tr.popPositionOf(savePos);
                            }
                        }
                        options.set(key, value, tr.getMessages());
                        if (stack != null) {
                            stack.push(key);
                            stack.push(oldValue);
                            stack.push(value);
                        }
                        tr.popPositionOf(savePos);
                    }
                } else {
                    tr.error('e', "keyword " + key + " not followed by value");
                    Object obj = LList.Empty;
                    tr.popPositionOf(savePos);
                    return obj;
                }
            }
            syntax = (SyntaxForm) form;
            form = syntax.getDatum();
        }
        if (!seenKey) {
            tr.error('e', "no option keyword in " + command.getName());
        }
        return Translator.wrapSyntax(form, syntax);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Stack stack;
        Object rest;
        Expression result;
        BeginExp bresult;
        Pair obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair p = obj;
            if (p.getCar() instanceof Stack) {
                stack = (Stack) p.getCar();
                rest = p.getCdr();
                tr.currentOptions.pushOptionValues(stack);
                result = tr.rewrite_body(rest);
                if (result instanceof BeginExp) {
                    bresult = new BeginExp(new Expression[]{result});
                } else {
                    bresult = (BeginExp) result;
                }
                bresult.setCompileOptions(stack);
                return bresult;
            }
        }
        stack = new Stack();
        rest = getOptions(obj, stack, this, tr);
        try {
            result = tr.rewrite_body(rest);
            if (result instanceof BeginExp) {
                bresult = new BeginExp(new Expression[]{result});
            } else {
                bresult = (BeginExp) result;
            }
            bresult.setCompileOptions(stack);
            return bresult;
        } finally {
            tr.currentOptions.popOptionValues(stack);
        }
    }
}
