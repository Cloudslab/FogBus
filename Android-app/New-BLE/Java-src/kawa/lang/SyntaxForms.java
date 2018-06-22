package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.lists.ImmutablePair;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;

public class SyntaxForms {
    public static final boolean DEBUGGING = true;

    static class SimpleSyntaxForm implements SyntaxForm {
        static int counter;
        private Object datum;
        int id;
        private TemplateScope scope;

        SimpleSyntaxForm(Object datum, TemplateScope scope) {
            int i = counter + 1;
            counter = i;
            this.id = i;
            this.datum = datum;
            this.scope = scope;
        }

        public Object getDatum() {
            return this.datum;
        }

        public TemplateScope getScope() {
            return this.scope;
        }

        public String toString() {
            return SyntaxForms.toString(this, Integer.toString(this.id));
        }
    }

    static class PairSyntaxForm extends ImmutablePair implements SyntaxForm {
        private Pair datum;
        private TemplateScope scope;

        public PairSyntaxForm(Pair datum, TemplateScope scope) {
            this.datum = datum;
            this.scope = scope;
        }

        public Object getDatum() {
            return this.datum;
        }

        public TemplateScope getScope() {
            return this.scope;
        }

        public Object getCar() {
            if (this.car == null) {
                this.car = SyntaxForms.makeForm(this.datum.getCar(), this.scope);
            }
            return this.car;
        }

        public Object getCdr() {
            if (this.cdr == null) {
                this.cdr = SyntaxForms.makeForm(this.datum.getCdr(), this.scope);
            }
            return this.cdr;
        }

        public String toString() {
            return SyntaxForms.toString(this, null);
        }
    }

    public static Object makeForm(Object datum, TemplateScope scope) {
        if (datum instanceof Pair) {
            return new PairSyntaxForm((Pair) datum, scope);
        }
        return datum != LList.Empty ? new SimpleSyntaxForm(datum, scope) : datum;
    }

    public static Object makeWithTemplate(Object template, Object form) {
        if (form instanceof SyntaxForm) {
            return (SyntaxForm) form;
        }
        if (!(template instanceof SyntaxForm)) {
            return form;
        }
        SyntaxForm sform = (SyntaxForm) template;
        if (form == sform.getDatum()) {
            return sform;
        }
        return fromDatum(form, sform);
    }

    public static boolean freeIdentifierEquals(SyntaxForm id1, SyntaxForm id2) {
        Translator tr = (Translator) Compilation.getCurrent();
        return tr.lexical.lookup(id1.getDatum(), -1) == tr.lexical.lookup(id2.getDatum(), -1);
    }

    public static boolean isIdentifier(SyntaxForm form) {
        return form.getDatum() instanceof Symbol;
    }

    public static Object fromDatum(Object datum, SyntaxForm template) {
        return makeForm(datum, template.getScope());
    }

    public static Object fromDatumIfNeeded(Object datum, SyntaxForm template) {
        if (datum == template.getDatum()) {
            return template;
        }
        if (datum instanceof SyntaxForm) {
            return (SyntaxForm) datum;
        }
        return fromDatum(datum, template);
    }

    public static Expression rewrite(Object x) {
        return ((Translator) Compilation.getCurrent()).rewrite(x);
    }

    public static Expression rewriteBody(Object x) {
        return ((Translator) Compilation.getCurrent()).rewrite_body(x);
    }

    public static String toString(SyntaxForm sform, String id) {
        StringBuilder sbuf = new StringBuilder("#<syntax");
        if (id != null) {
            sbuf.append('#');
            sbuf.append(id);
        }
        sbuf.append(' ');
        sbuf.append(sform.getDatum());
        TemplateScope scope = sform.getScope();
        if (scope == null) {
            sbuf.append(" in null");
        } else {
            sbuf.append(" in #");
            sbuf.append(scope.id);
        }
        sbuf.append(">");
        return sbuf.toString();
    }
}
