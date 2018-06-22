package kawa.standard;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.ThisExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class thisRef extends Syntax {
    public static final thisRef thisSyntax = new thisRef();

    static {
        thisSyntax.setName("this");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (form.getCdr() != LList.Empty) {
            return tr.syntaxError("this with parameter not implemented");
        }
        LambdaExp method = tr.curMethodLambda;
        Declaration firstParam = method == null ? null : method.firstDecl();
        if (firstParam == null || !firstParam.isThisParameter()) {
            firstParam = null;
            if (method == null || method.nameDecl == null) {
                tr.error('e', "use of 'this' not in a named method");
            } else if (method.nameDecl.isStatic()) {
                tr.error('e', "use of 'this' in a static method");
            } else {
                firstParam = new Declaration(ThisExp.THIS_NAME);
                method.add(null, firstParam);
                method.nameDecl.setFlag(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
            }
        }
        return new ThisExp(firstParam);
    }
}
