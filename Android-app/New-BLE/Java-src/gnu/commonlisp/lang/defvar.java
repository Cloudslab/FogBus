package gnu.commonlisp.lang;

import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class defvar extends Syntax {
    boolean force;

    public defvar(boolean force) {
        this.force = force;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (!(st.getCdr() instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st.getCdr();
        Object name = p.getCar();
        if ((name instanceof String) || (name instanceof Symbol)) {
            Declaration decl = defs.lookup(name);
            if (decl == null) {
                decl = new Declaration(name);
                decl.setFlag(268435456);
                defs.addDeclaration(decl);
            } else {
                tr.error('w', "duplicate declaration for `" + name + "'");
            }
            st = Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr()));
            if (defs instanceof ModuleExp) {
                decl.setCanRead(true);
                decl.setCanWrite(true);
            }
        }
        forms.addElement(st);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r11, kawa.lang.Translator r12) {
        /*
        r10 = this;
        r9 = 1;
        r2 = r11.getCdr();
        r1 = 0;
        r6 = 0;
        r0 = 0;
        r7 = r2 instanceof gnu.lists.Pair;
        if (r7 == 0) goto L_0x003f;
    L_0x000c:
        r3 = r2;
        r3 = (gnu.lists.Pair) r3;
        r7 = r3.getCar();
        r7 = r7 instanceof gnu.expr.Declaration;
        if (r7 == 0) goto L_0x003f;
    L_0x0017:
        r0 = r3.getCar();
        r0 = (gnu.expr.Declaration) r0;
        r1 = r0.getSymbol();
        r7 = r3.getCdr();
        r7 = r7 instanceof gnu.lists.Pair;
        if (r7 == 0) goto L_0x005d;
    L_0x0029:
        r4 = r3.getCdr();
        r4 = (gnu.lists.Pair) r4;
        r7 = r4.getCar();
        r6 = r12.rewrite(r7);
        r7 = r4.getCdr();
        r8 = gnu.lists.LList.Empty;
        if (r7 == r8) goto L_0x003f;
    L_0x003f:
        if (r1 != 0) goto L_0x0067;
    L_0x0041:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "invalid syntax for ";
        r7 = r7.append(r8);
        r8 = r10.getName();
        r7 = r7.append(r8);
        r7 = r7.toString();
        r5 = r12.syntaxError(r7);
    L_0x005c:
        return r5;
    L_0x005d:
        r7 = r3.getCdr();
        r8 = gnu.lists.LList.Empty;
        if (r7 == r8) goto L_0x003f;
    L_0x0065:
        r1 = 0;
        goto L_0x003f;
    L_0x0067:
        if (r6 != 0) goto L_0x006f;
    L_0x0069:
        r7 = r10.force;
        if (r7 == 0) goto L_0x0094;
    L_0x006d:
        r6 = gnu.commonlisp.lang.CommonLisp.nilExpr;
    L_0x006f:
        r5 = new gnu.expr.SetExp;
        r5.<init>(r1, r6);
        r7 = r10.force;
        if (r7 != 0) goto L_0x007b;
    L_0x0078:
        r5.setSetIfUnbound(r9);
    L_0x007b:
        r5.setDefining(r9);
        if (r0 == 0) goto L_0x005c;
    L_0x0080:
        r5.setBinding(r0);
        r7 = r0.context;
        r7 = r7 instanceof gnu.expr.ModuleExp;
        if (r7 == 0) goto L_0x0090;
    L_0x0089:
        r7 = r0.getCanWrite();
        if (r7 == 0) goto L_0x0090;
    L_0x008f:
        r6 = 0;
    L_0x0090:
        r0.noteValue(r6);
        goto L_0x005c;
    L_0x0094:
        r5 = new gnu.expr.QuoteExp;
        r5.<init>(r1);
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.commonlisp.lang.defvar.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
