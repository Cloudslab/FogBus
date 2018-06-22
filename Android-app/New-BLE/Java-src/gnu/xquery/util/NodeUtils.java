package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.SortedNodes;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.xml.NamespaceBinding;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import gnu.xquery.lang.XQuery;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

public class NodeUtils {
    static String collectionNamespace = "http://gnu.org/kawa/cached-collections";
    public static final Symbol collectionResolverSymbol = Symbol.make(XQuery.LOCAL_NAMESPACE, "collection-resolver", "qexo");

    public static Object nodeName(Object node) {
        if (node == Values.empty || node == null) {
            return node;
        }
        if (node instanceof KNode) {
            Object sym = ((KNode) node).getNodeSymbol();
            if (sym == null) {
                return Values.empty;
            }
            return sym;
        }
        throw new WrongType("node-name", 1, node, "node()?");
    }

    public static String name(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        Values name = ((KNode) node).getNodeNameObject();
        if (name == null || name == Values.empty) {
            return "";
        }
        return name.toString();
    }

    public static String localName(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        if (node instanceof KNode) {
            Values name = ((KNode) node).getNodeNameObject();
            if (name == null || name == Values.empty) {
                return "";
            }
            if (name instanceof Symbol) {
                return ((Symbol) name).getName();
            }
            return name.toString();
        }
        throw new WrongType("local-name", 1, node, "node()?");
    }

    public static Object namespaceURI(Object node) {
        if (!(node == Values.empty || node == null)) {
            if (node instanceof KNode) {
                Object name = ((KNode) node).getNodeNameObject();
                if (name instanceof Symbol) {
                    return QNameUtils.namespaceURIFromQName(name);
                }
            }
            throw new WrongType("namespace-uri", 1, node, "node()?");
        }
        return "";
    }

    public static void prefixesFromNodetype(XName name, Consumer out) {
        NamespaceBinding bindings = name.getNamespaceNodes();
        for (NamespaceBinding ns = bindings; ns != null; ns = ns.getNext()) {
            if (ns.getUri() != null) {
                String prefix = ns.getPrefix();
                for (NamespaceBinding ns2 = bindings; ns2 != ns; ns2 = ns2.getNext()) {
                    if (ns2.getPrefix() == prefix) {
                        break;
                    }
                }
                if (prefix == null) {
                    prefix = "";
                }
                out.writeObject(prefix);
            }
        }
    }

    public static void inScopePrefixes$X(Object node, CallContext ctx) {
        Object type = ((KElement) node).getNodeNameObject();
        if (type instanceof XName) {
            prefixesFromNodetype((XName) type, ctx.consumer);
        } else {
            ctx.consumer.writeObject("xml");
        }
    }

    public static void data$X(Object arg, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values vals = (Values) arg;
            int ipos = vals.startPos();
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    out.writeObject(KNode.atomicValue(vals.getPosPrevious(ipos)));
                } else {
                    return;
                }
            }
        }
        out.writeObject(KNode.atomicValue(arg));
    }

    public static Object root(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (arg instanceof KNode) {
            KNode node = (KNode) arg;
            return Nodes.root((NodeTree) node.sequence, node.getPos());
        }
        throw new WrongType("root", 1, arg, "node()?");
    }

    public static KDocument rootDocument(Object arg) {
        if (arg instanceof KNode) {
            KNode node = (KNode) arg;
            node = Nodes.root((NodeTree) node.sequence, node.getPos());
            if (node instanceof KDocument) {
                return (KDocument) node;
            }
            throw new WrongType("root-document", 1, arg, "document()");
        }
        throw new WrongType("root-document", 1, arg, "node()?");
    }

    public static String getLang(KNode node) {
        NodeTree seq = node.sequence;
        int attr = seq.ancestorAttribute(node.ipos, NamespaceBinding.XML_NAMESPACE, "lang");
        if (attr == 0) {
            return null;
        }
        return KNode.getNodeValue(seq, attr);
    }

    public static boolean lang(Object testlang, Object node) {
        String teststr;
        if (testlang == null || testlang == Values.empty) {
            teststr = "";
        } else {
            teststr = TextUtils.stringValue(testlang);
        }
        String lang = getLang((KNode) node);
        if (lang == null) {
            return false;
        }
        int langlen = lang.length();
        int testlen = teststr.length();
        if (langlen > testlen && lang.charAt(testlen) == '-') {
            lang = lang.substring(0, testlen);
        }
        return lang.equalsIgnoreCase(teststr);
    }

    public static Object documentUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (arg instanceof KNode) {
            KNode node = (KNode) arg;
            Object uri = ((NodeTree) node.sequence).documentUriOfPos(node.ipos);
            return uri == null ? Values.empty : uri;
        } else {
            throw new WrongType("xs:document-uri", 1, arg, "node()?");
        }
    }

    public static Object nilled(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (!(arg instanceof KNode)) {
            throw new WrongType("nilled", 1, arg, "node()?");
        } else if (arg instanceof KElement) {
            return Boolean.FALSE;
        } else {
            return Values.empty;
        }
    }

    public static Object baseUri(Object arg) {
        if (arg == null || arg == Values.empty) {
            return arg;
        }
        if (arg instanceof KNode) {
            Object uri = ((KNode) arg).baseURI();
            if (uri == null) {
                return Values.empty;
            }
            return uri;
        }
        throw new WrongType("base-uri", 1, arg, "node()?");
    }

    static Object getIDs(Object arg, Object collector) {
        if (arg instanceof KNode) {
            arg = KNode.atomicValue(arg);
        }
        int i;
        if (arg instanceof Values) {
            Object[] ar = ((Values) arg).getValues();
            i = ar.length;
            while (true) {
                i--;
                if (i < 0) {
                    return collector;
                }
                collector = getIDs(ar[i], collector);
            }
        } else {
            String str = StringUtils.coerceToString(arg, "fn:id", 1, "");
            int len = str.length();
            int i2 = 0;
            Object obj = collector;
            while (i2 < len) {
                i = i2 + 1;
                char ch = str.charAt(i2);
                if (Character.isWhitespace(ch)) {
                    i2 = i;
                } else {
                    int start;
                    if (XName.isNameStart(ch)) {
                        start = i - 1;
                    } else {
                        start = len;
                    }
                    while (i < len) {
                        ch = str.charAt(i);
                        if (Character.isWhitespace(ch)) {
                            break;
                        }
                        i++;
                        if (start < len && !XName.isNamePart(ch)) {
                            start = len;
                        }
                    }
                    if (start < len) {
                        String ref = str.substring(start, i);
                        if (obj == null) {
                            obj = ref;
                        } else {
                            Stack st;
                            if (obj instanceof Stack) {
                                st = (Stack) obj;
                            } else {
                                st = new Stack();
                                st.push(obj);
                                Stack stack = st;
                            }
                            st.push(ref);
                        }
                    }
                    i2 = i + 1;
                }
            }
            return obj;
        }
    }

    public static void id$X(Object arg1, Object arg2, CallContext ctx) {
        KNode node = (KNode) arg2;
        NodeTree ntree = node.sequence;
        KDocument root = (KDocument) Nodes.root(ntree, node.ipos);
        Consumer out = ctx.consumer;
        Object idrefs = getIDs(arg1, null);
        if (idrefs != null) {
            ntree.makeIDtableIfNeeded();
            if ((out instanceof PositionConsumer) && ((idrefs instanceof String) || (out instanceof SortedNodes))) {
                idScan(idrefs, ntree, (PositionConsumer) out);
            } else if (idrefs instanceof String) {
                int pos = ntree.lookupID((String) idrefs);
                if (pos != -1) {
                    out.writeObject(KNode.make(ntree, pos));
                }
            } else {
                SortedNodes nodes = new SortedNodes();
                idScan(idrefs, ntree, nodes);
                Values.writeValues(nodes, out);
            }
        }
    }

    private static void idScan(Object ids, NodeTree seq, PositionConsumer out) {
        if (ids instanceof String) {
            int pos = seq.lookupID((String) ids);
            if (pos != -1) {
                out.writePosition(seq, pos);
            }
        } else if (ids instanceof Stack) {
            Stack st = (Stack) ids;
            int n = st.size();
            for (int i = 0; i < n; i++) {
                idScan(st.elementAt(i), seq, out);
            }
        }
    }

    public static Object idref(Object arg1, Object arg2) {
        KNode node = (KNode) arg2;
        KDocument root = (KDocument) Nodes.root((NodeTree) node.sequence, node.getPos());
        return Values.empty;
    }

    public static void setSavedCollection(Object uri, Object value, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        env.put(Symbol.make(collectionNamespace, uri.toString()), null, value);
    }

    public static void setSavedCollection(Object uri, Object value) {
        setSavedCollection(uri, value, Environment.getCurrent());
    }

    public static Object getSavedCollection(Object uri, Environment env) {
        if (uri == null) {
            uri = "#default";
        }
        Object coll = env.get(Symbol.make(collectionNamespace, uri.toString()), null, null);
        if (coll != null) {
            return coll;
        }
        throw new RuntimeException("collection '" + uri + "' not found");
    }

    public static Object getSavedCollection(Object uri) {
        return getSavedCollection(uri, Environment.getCurrent());
    }

    public static Object collection(Object uri, Object base) throws Throwable {
        uri = resolve(uri, base, "collection");
        Environment env = Environment.getCurrent();
        Symbol rsym = collectionResolverSymbol;
        Object rvalue = env.get(rsym, null, null);
        if (rvalue == null) {
            rvalue = env.get(Symbol.makeWithUnknownNamespace(rsym.getLocalName(), rsym.getPrefix()), null, null);
        }
        if (rvalue == null) {
            return getSavedCollection(uri);
        }
        if ((rvalue instanceof String) || (rvalue instanceof UntypedAtomic)) {
            String str = rvalue.toString();
            int colon = str.indexOf(58);
            if (colon > 0) {
                String cname = str.substring(0, colon);
                String mname = str.substring(colon + 1);
                try {
                    rvalue = ClassMethods.apply((ClassType) Type.make(Class.forName(cname)), mname, '\u0000', XQuery.instance);
                    if (rvalue == null) {
                        throw new RuntimeException("invalid collection-resolver: no method " + mname + " in " + cname);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("invalid collection-resolver: class " + cname + " not found");
                } catch (Throwable ex) {
                    RuntimeException runtimeException = new RuntimeException("invalid collection-resolver: " + ex);
                }
            }
        }
        if (rvalue instanceof Procedure) {
            return ((Procedure) rvalue).apply1(uri);
        }
        throw new RuntimeException("invalid collection-resolver: " + rvalue);
    }

    static Object resolve(Object uri, Object base, String fname) throws Throwable {
        if (!((uri instanceof File) || (uri instanceof Path) || (uri instanceof URI) || (uri instanceof URL))) {
            Values uri2 = StringUtils.coerceToString(uri, fname, 1, null);
        }
        if (uri2 == Values.empty || uri2 == null) {
            return null;
        }
        return Path.currentPath().resolve(Path.valueOf(uri2));
    }

    public static Object docCached(Object uri, Object base) throws Throwable {
        uri = resolve(uri, base, "doc");
        if (uri == null) {
            return Values.empty;
        }
        return Document.parseCached(uri);
    }

    public static boolean availableCached(Object uri, Object base) throws Throwable {
        uri = resolve(uri, base, "doc-available");
        if (uri == null) {
            return false;
        }
        try {
            Document.parseCached(uri);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
