package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.MakeText;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.xml.NamespaceBinding;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class MakeXmlElement extends Syntax {
    public static final MakeXmlElement makeXml = new MakeXmlElement();
    static final ClassType typeNamespace = ClassType.make("gnu.mapping.Namespace");

    static {
        makeXml.setName("$make-xml$");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Pair pair1 = (Pair) form.getCdr();
        Pair namespaceList = pair1.getCar();
        Object obj = pair1.getCdr();
        boolean nsSeen = false;
        NamespaceBinding saveBindings = tr.xmlElementNamespaces;
        NamespaceBinding nsBindings = saveBindings;
        while (namespaceList instanceof Pair) {
            String str;
            Namespace namespace;
            if (!nsSeen) {
                tr.letStart();
                nsSeen = true;
            }
            Pair namespacePair = namespaceList;
            Pair namespaceNode = (Pair) namespacePair.getCar();
            String nsPrefix = (String) namespaceNode.getCar();
            nsPrefix = nsPrefix.length() == 0 ? null : nsPrefix.intern();
            Pair valueList = namespaceNode.getCdr();
            StringBuilder sbuf = new StringBuilder();
            while (valueList instanceof Pair) {
                Object value;
                Pair valuePair = valueList;
                Object valueForm = valuePair.getCar();
                if (LList.listLength(valueForm, false) == 2 && (valueForm instanceof Pair) && ((Pair) valueForm).getCar() == MakeText.makeText) {
                    value = ((Pair) ((Pair) valueForm).getCdr()).getCar();
                } else {
                    value = tr.rewrite_car(valuePair, false).valueIfConstant();
                }
                if (value == null) {
                    Object savePos = tr.pushPositionOf(valuePair);
                    tr.error('e', "namespace URI must be literal");
                    tr.popPositionOf(savePos);
                } else {
                    sbuf.append(value);
                }
                valueList = valuePair.getCdr();
            }
            String nsUri = sbuf.toString().intern();
            if (nsUri == "") {
                str = null;
            } else {
                str = nsUri;
            }
            NamespaceBinding nsBindings2 = new NamespaceBinding(nsPrefix, str, nsBindings);
            if (nsPrefix == null) {
                namespace = Namespace.valueOf(nsUri);
                nsPrefix = "[default-element-namespace]";
            } else {
                namespace = XmlNamespace.getInstance(nsPrefix, nsUri);
            }
            tr.letVariable(Namespace.EmptyNamespace.getSymbol(nsPrefix), typeNamespace, new QuoteExp(namespace)).setFlag(2121728);
            namespaceList = namespacePair.getCdr();
            nsBindings = nsBindings2;
        }
        MakeElement mkElement = new MakeElement();
        mkElement.setNamespaceNodes(nsBindings);
        tr.xmlElementNamespaces = nsBindings;
        if (nsSeen) {
            try {
                tr.letEnter();
            } catch (Throwable th) {
                tr.xmlElementNamespaces = saveBindings;
            }
        }
        Expression result = tr.rewrite(Translator.makePair(form, mkElement, obj));
        if (nsSeen) {
            result = tr.letDone(result);
        }
        tr.xmlElementNamespaces = saveBindings;
        return result;
    }
}
