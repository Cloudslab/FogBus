package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class syntax_error extends Syntax {
    public static final syntax_error syntax_error = new syntax_error();

    static {
        syntax_error.setName("%syntax-error");
    }

    public Expression rewrite(Object obj, Translator tr) {
        StringBuffer buffer = new StringBuffer();
        int words = 0;
        LList obj2;
        while (obj2 instanceof Pair) {
            Pair pair = (Pair) obj2;
            if (words > 0) {
                buffer.append(' ');
            }
            buffer.append(pair.getCar());
            obj2 = pair.getCdr();
            words++;
        }
        if (obj2 != LList.Empty) {
            if (words > 0) {
                buffer.append(' ');
            }
            buffer.append(obj2);
        }
        return tr.syntaxError(buffer.toString());
    }

    public static Expression error(Object form, Object[] message) {
        StringBuffer buffer = new StringBuffer();
        if (message == null || len == 0) {
            buffer.append("invalid syntax");
        } else {
            for (Object append : message) {
                buffer.append(append);
            }
        }
        Translator tr = (Translator) Compilation.getCurrent();
        if (tr == null) {
            throw new RuntimeException(buffer.toString());
        }
        Object savePos = tr.pushPositionOf(form);
        try {
            Expression syntaxError = tr.syntaxError(buffer.toString());
            return syntaxError;
        } finally {
            tr.popPositionOf(savePos);
        }
    }
}
