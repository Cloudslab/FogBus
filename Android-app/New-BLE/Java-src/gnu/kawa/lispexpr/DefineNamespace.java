package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class DefineNamespace extends Syntax {
    public static final String XML_NAMESPACE_MAGIC = "&xml&";
    public static final DefineNamespace define_namespace = new DefineNamespace();
    public static final DefineNamespace define_private_namespace = new DefineNamespace();
    public static final DefineNamespace define_xml_namespace = new DefineNamespace();
    private boolean makePrivate;
    private boolean makeXML;

    static {
        define_namespace.setName("define-namespace");
        define_private_namespace.setName("define-private-namespace");
        define_private_namespace.makePrivate = true;
        define_xml_namespace.setName("define-xml-namespace");
        define_xml_namespace.makeXML = true;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (st.getCdr() instanceof Pair) {
            Object p1 = (Pair) st.getCdr();
            if ((p1.getCar() instanceof Symbol) && (p1.getCdr() instanceof Pair)) {
                Pair p2 = (Pair) p1.getCdr();
                if (p2.getCdr() == LList.Empty) {
                    Expression value;
                    Symbol name = (Symbol) p1.getCar();
                    Declaration decl = defs.getDefine(name, 'w', tr);
                    tr.push(decl);
                    decl.setFlag(2375680);
                    if (this.makePrivate) {
                        decl.setFlag(16777216);
                        decl.setPrivate(true);
                    } else if (defs instanceof ModuleExp) {
                        decl.setCanRead(true);
                    }
                    Translator.setLine(decl, p1);
                    if (p2.getCar() instanceof CharSequence) {
                        Namespace namespace;
                        String literal = p2.getCar().toString();
                        if (literal.startsWith("class:")) {
                            namespace = ClassNamespace.getInstance(literal, ClassType.make(literal.substring(6)));
                            decl.setType(ClassType.make("gnu.kawa.lispexpr.ClassNamespace"));
                        } else if (this.makeXML) {
                            namespace = XmlNamespace.getInstance(name.getName(), literal);
                            decl.setType(ClassType.make("gnu.kawa.xml.XmlNamespace"));
                        } else {
                            namespace = Namespace.valueOf(literal);
                            decl.setType(ClassType.make("gnu.mapping.Namespace"));
                        }
                        value = new QuoteExp(namespace);
                        decl.setFlag(8192);
                    } else {
                        value = tr.rewrite_car(p2, false);
                    }
                    decl.noteValue(value);
                    forms.addElement(SetExp.makeDefinition(decl, value));
                    return true;
                }
            }
        }
        tr.error('e', "invalid syntax for define-namespace");
        return false;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return tr.syntaxError("define-namespace is only allowed in a <body>");
    }
}
