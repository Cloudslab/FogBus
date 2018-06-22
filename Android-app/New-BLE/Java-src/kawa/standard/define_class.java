package kawa.standard;

import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class define_class extends Syntax {
    public static final define_class define_class = new define_class("define-class", false);
    public static final define_class define_simple_class = new define_class("define-simple-class", true);
    boolean isSimple;
    object objectSyntax;

    define_class(object objectSyntax, boolean isSimple) {
        this.objectSyntax = objectSyntax;
        this.isSimple = isSimple;
    }

    define_class(String name, boolean isSimple) {
        super(name);
        this.objectSyntax = object.objectSyntax;
        this.isSimple = isSimple;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        SyntaxForm st_cdr = st.getCdr();
        SyntaxForm nameSyntax = null;
        while (st_cdr instanceof SyntaxForm) {
            nameSyntax = st_cdr;
            st_cdr = nameSyntax.getDatum();
        }
        if (!(st_cdr instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st_cdr;
        SyntaxForm name = p.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = name;
            name = nameSyntax.getDatum();
        }
        Object name2 = tr.namespaceResolve(name);
        if ((name2 instanceof String) || (name2 instanceof Symbol)) {
            Declaration decl = tr.define(name2, nameSyntax, defs);
            if (p instanceof PairWithPosition) {
                decl.setLocation((PairWithPosition) p);
            }
            ClassExp oexp = new ClassExp(this.isSimple);
            decl.noteValue(oexp);
            decl.setFlag(536887296);
            decl.setType(this.isSimple ? Compilation.typeClass : Compilation.typeClassType);
            tr.mustCompileHere();
            String cname = name2 instanceof Symbol ? ((Symbol) name2).getName() : name2.toString();
            int nlen = cname.length();
            if (nlen > 2 && cname.charAt(0) == '<' && cname.charAt(nlen - 1) == '>') {
                cname = cname.substring(1, nlen - 1);
            }
            oexp.setName(cname);
            SyntaxForm members = p.getCdr();
            while (members instanceof SyntaxForm) {
                nameSyntax = members;
                members = nameSyntax.getDatum();
            }
            if (members instanceof Pair) {
                p = (Pair) members;
                ScopeExp save_scope = tr.currentScope();
                if (nameSyntax != null) {
                    tr.setCurrentScope(nameSyntax.getScope());
                }
                Object[] saved = this.objectSyntax.scanClassDef(p, oexp, tr);
                if (nameSyntax != null) {
                    tr.setCurrentScope(save_scope);
                }
                if (saved == null) {
                    return false;
                }
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, saved)));
                return true;
            }
            tr.error('e', "missing class members");
            return false;
        }
        tr.error('e', "missing class name");
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Declaration decl = null;
        Pair form_cdr = form.getCdr();
        if (form_cdr instanceof Pair) {
            form = form_cdr;
            Declaration form_car = form.getCar();
            if (!(form_car instanceof Declaration)) {
                return tr.syntaxError(getName() + " can only be used in <body>");
            }
            decl = form_car;
        }
        Expression oexp = (ClassExp) decl.getValue();
        this.objectSyntax.rewriteClassDef((Object[]) form.getCdr(), tr);
        Expression sexp = new SetExp(decl, oexp);
        sexp.setDefining(true);
        return sexp;
    }
}
