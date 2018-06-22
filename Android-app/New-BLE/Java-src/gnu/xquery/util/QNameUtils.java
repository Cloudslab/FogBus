package gnu.xquery.util;

import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XStringType;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.net.URISyntaxException;

public class QNameUtils {
    public static Object resolveQNameUsingElement(Object qname, KElement node) {
        Values qname2 = KNode.atomicValue(qname);
        if (qname2 == Values.empty || qname2 == null) {
            return qname2;
        }
        if ((qname2 instanceof Values) || !((qname2 instanceof String) || (qname2 instanceof UntypedAtomic))) {
            throw new RuntimeException("bad argument to QName");
        }
        String prefix;
        String localPart;
        String name = TextUtils.replaceWhitespace(qname2.toString(), true);
        int colon = name.indexOf(58);
        if (colon < 0) {
            prefix = null;
            localPart = name;
        } else {
            prefix = name.substring(0, colon).intern();
            localPart = name.substring(colon + 1);
        }
        String uri = node.lookupNamespaceURI(prefix);
        if (uri == null) {
            if (prefix == null) {
                uri = "";
            } else {
                throw new RuntimeException("unknown namespace for '" + name + "'");
            }
        }
        if (validNCName(localPart) && (prefix == null || validNCName(prefix))) {
            if (prefix == null) {
                prefix = "";
            }
            return Symbol.make(uri, localPart, prefix);
        }
        throw new RuntimeException("invalid QName syntax '" + name + "'");
    }

    public static Object resolveQName(Object qname, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        qname = KNode.atomicValue(qname);
        if (qname instanceof Symbol) {
            return qname;
        }
        if ((qname instanceof Values) || !((qname instanceof String) || (qname instanceof UntypedAtomic))) {
            throw new RuntimeException("bad argument to QName");
        }
        String localPart;
        String prefix;
        String name = TextUtils.replaceWhitespace(qname.toString(), true);
        int colon = name.indexOf(58);
        if (colon < 0) {
            localPart = name;
            prefix = null;
        } else {
            prefix = name.substring(0, colon).intern();
            localPart = name.substring(colon + 1);
        }
        if (validNCName(localPart) && (prefix == null || validNCName(prefix))) {
            String uri = resolvePrefix(prefix, constructorNamespaces, prologNamespaces);
            if (prefix == null) {
                prefix = "";
            }
            return Symbol.make(uri, localPart, prefix);
        }
        throw new RuntimeException("invalid QName syntax '" + name + "'");
    }

    public static String lookupPrefix(String prefix, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        String uri;
        for (NamespaceBinding ns = constructorNamespaces; ns != null; ns = ns.getNext()) {
            if (ns.getPrefix() == prefix) {
                uri = ns.getUri();
                break;
            }
        }
        uri = prologNamespaces.resolve(prefix);
        if (uri == null && prefix == null) {
            return "";
        }
        return uri;
    }

    public static String resolvePrefix(String prefix, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        String uri = lookupPrefix(prefix, constructorNamespaces, prologNamespaces);
        if (uri != null) {
            return uri;
        }
        throw new RuntimeException("unknown namespace prefix '" + prefix + "'");
    }

    public static boolean validNCName(String name) {
        return XName.isName(name);
    }

    public static Symbol makeQName(Object paramURI, String paramQName) {
        String paramURI2;
        String localPart;
        String prefix;
        if (paramURI == null || paramURI == Values.empty) {
            paramURI2 = "";
        }
        int colon = paramQName.indexOf(58);
        String namespaceURI = paramURI2;
        if (colon < 0) {
            localPart = paramQName;
            prefix = "";
        } else {
            localPart = paramQName.substring(colon + 1);
            prefix = paramQName.substring(0, colon).intern();
        }
        if (!validNCName(localPart) || (colon >= 0 && !validNCName(prefix))) {
            throw new IllegalArgumentException("invalid QName syntax '" + paramQName + "'");
        } else if (colon < 0 || namespaceURI.length() != 0) {
            return Symbol.make(namespaceURI, localPart, prefix);
        } else {
            throw new IllegalArgumentException("empty uri for '" + paramQName + "'");
        }
    }

    public static Object localNameFromQName(Object name) {
        if (name == Values.empty || name == null) {
            return name;
        }
        if (name instanceof Symbol) {
            return XStringType.makeNCName(((Symbol) name).getName());
        }
        throw new WrongType("local-name-from-QName", 1, name, "xs:QName");
    }

    public static Object prefixFromQName(Object name) {
        if (name == Values.empty || name == null) {
            return name;
        }
        if (name instanceof Symbol) {
            String prefix = ((Symbol) name).getPrefix();
            if (prefix == null || prefix.length() == 0) {
                return Values.empty;
            }
            return XStringType.makeNCName(prefix);
        }
        throw new WrongType("prefix-from-QName", 1, name, "xs:QName");
    }

    public static Object namespaceURIFromQName(Object name) {
        if (!(name == Values.empty || name == null)) {
            try {
                name = URIPath.makeURI(((Symbol) name).getNamespaceURI());
            } catch (ClassCastException e) {
                throw new WrongType("namespace-uri", 1, name, "xs:QName");
            }
        }
        return name;
    }

    public static Object namespaceURIForPrefix(Object prefix, Object element) {
        KNode el = KNode.coerce(element);
        if (el == null) {
            throw new WrongType("namespace-uri-for-prefix", 2, element, "node()");
        }
        String str;
        if (prefix == null || prefix == Values.empty) {
            str = null;
        } else if ((prefix instanceof String) || (prefix instanceof UntypedAtomic)) {
            str = prefix.toString().intern();
            if (str == "") {
                str = null;
            }
        } else {
            throw new WrongType("namespace-uri-for-prefix", 1, element, "xs:string");
        }
        String uri = el.lookupNamespaceURI(str);
        if (uri == null) {
            return Values.empty;
        }
        return uri;
    }

    public static Object resolveURI(Object relative, Object base) throws URISyntaxException {
        if (relative instanceof KNode) {
            relative = KNode.atomicValue(relative);
        }
        if (base instanceof KNode) {
            base = KNode.atomicValue(base);
        }
        if (relative == Values.empty || relative == null) {
            return relative;
        }
        if (relative instanceof UntypedAtomic) {
            relative = relative.toString();
        }
        if (base instanceof UntypedAtomic) {
            base = base.toString();
        }
        Path baseP = base instanceof Path ? (Path) base : URIPath.makeURI(base);
        if (relative instanceof Path) {
            return baseP.resolve((Path) relative);
        }
        return baseP.resolve(relative.toString());
    }
}
