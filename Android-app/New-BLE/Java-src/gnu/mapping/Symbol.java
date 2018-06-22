package gnu.mapping;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Symbol implements EnvironmentKey, Comparable, Externalizable {
    public static final Symbol FUNCTION = makeUninterned("(function)");
    public static final Symbol PLIST = makeUninterned("(property-list)");
    protected String name;
    Namespace namespace;

    public final Symbol getKeySymbol() {
        return this;
    }

    public final Object getKeyProperty() {
        return null;
    }

    public boolean matches(EnvironmentKey key) {
        return equals(key.getKeySymbol(), this) && key.getKeyProperty() == null;
    }

    public boolean matches(Symbol symbol, Object property) {
        return equals(symbol, this) && property == null;
    }

    public final String getNamespaceURI() {
        Namespace ns = getNamespace();
        return ns == null ? null : ns.getName();
    }

    public final String getLocalPart() {
        return this.name;
    }

    public final String getPrefix() {
        Namespace ns = this.namespace;
        return ns == null ? "" : ns.prefix;
    }

    public final boolean hasEmptyNamespace() {
        Namespace ns = getNamespace();
        if (ns != null) {
            String nsname = ns.getName();
            if (!(nsname == null || nsname.length() == 0)) {
                return false;
            }
        }
        return true;
    }

    public final String getLocalName() {
        return this.name;
    }

    public final String getName() {
        return this.name;
    }

    public static Symbol make(String uri, String name, String prefix) {
        return Namespace.valueOf(uri, prefix).getSymbol(name.intern());
    }

    public static Symbol make(Object namespace, String name) {
        Namespace ns = namespace instanceof String ? Namespace.valueOf((String) namespace) : (Namespace) namespace;
        if (ns == null || name == null) {
            return makeUninterned(name);
        }
        return ns.getSymbol(name.intern());
    }

    public static SimpleSymbol valueOf(String name) {
        return (SimpleSymbol) Namespace.EmptyNamespace.getSymbol(name.intern());
    }

    public static Symbol valueOf(String name, Object spec) {
        if (spec == null || spec == Boolean.FALSE) {
            return makeUninterned(name);
        }
        Namespace ns;
        if (spec instanceof Namespace) {
            ns = (Namespace) spec;
        } else if (spec == Boolean.TRUE) {
            ns = Namespace.EmptyNamespace;
        } else {
            ns = Namespace.valueOf(((CharSequence) spec).toString());
        }
        return ns.getSymbol(name.intern());
    }

    public static Symbol valueOf(String name, String namespace, String prefix) {
        return Namespace.valueOf(namespace, prefix).getSymbol(name.intern());
    }

    public static Symbol parse(String symbol) {
        int slen = symbol.length();
        int lbr = -1;
        int rbr = -1;
        int braceCount = 0;
        int mainStart = 0;
        int prefixEnd = 0;
        int i = 0;
        while (i < slen) {
            char ch = symbol.charAt(i);
            if (ch == ':' && braceCount == 0) {
                prefixEnd = i;
                mainStart = i + 1;
                break;
            }
            if (ch == '{') {
                if (lbr < 0) {
                    prefixEnd = i;
                    lbr = i;
                }
                braceCount++;
            }
            if (ch == '}') {
                braceCount--;
                if (braceCount == 0) {
                    rbr = i;
                    mainStart = (i >= slen || symbol.charAt(i + 1) != ':') ? i + 1 : i + 2;
                } else if (braceCount < 0) {
                    mainStart = prefixEnd;
                    break;
                }
            }
            i++;
        }
        if (lbr >= 0 && rbr > 0) {
            return valueOf(symbol.substring(mainStart), symbol.substring(lbr + 1, rbr), prefixEnd > 0 ? symbol.substring(0, prefixEnd) : null);
        } else if (prefixEnd > 0) {
            return makeWithUnknownNamespace(symbol.substring(mainStart), symbol.substring(0, prefixEnd));
        } else {
            return valueOf(symbol);
        }
    }

    public static Symbol makeWithUnknownNamespace(String local, String prefix) {
        return Namespace.makeUnknownNamespace(prefix).getSymbol(local.intern());
    }

    public static Symbol makeUninterned(String name) {
        return new Symbol(null, name);
    }

    public Symbol(Namespace ns, String name) {
        this.name = name;
        this.namespace = ns;
    }

    public int compareTo(Object o) {
        Symbol other = (Symbol) o;
        if (getNamespaceURI() == other.getNamespaceURI()) {
            return getLocalName().compareTo(other.getLocalName());
        }
        throw new IllegalArgumentException("comparing Symbols in different namespaces");
    }

    public static boolean equals(Symbol sym1, Symbol sym2) {
        if (sym1 == sym2) {
            return true;
        }
        if (sym1 == null || sym2 == null) {
            return false;
        }
        if (sym1.name == sym2.name) {
            Namespace namespace1 = sym1.namespace;
            Namespace namespace2 = sym2.namespace;
            if (!(namespace1 == null || namespace2 == null)) {
                if (namespace1.name != namespace2.name) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final boolean equals(Object o) {
        return (o instanceof Symbol) && equals(this, (Symbol) o);
    }

    public int hashCode() {
        return this.name == null ? 0 : this.name.hashCode();
    }

    public final Namespace getNamespace() {
        return this.namespace;
    }

    public final void setNamespace(Namespace ns) {
        this.namespace = ns;
    }

    public String toString() {
        return toString('P');
    }

    public String toString(char style) {
        boolean hasUri;
        boolean hasPrefix = true;
        String uri = getNamespaceURI();
        String prefix = getPrefix();
        if (uri == null || uri.length() <= 0) {
            hasUri = false;
        } else {
            hasUri = true;
        }
        if (prefix == null || prefix.length() <= 0) {
            hasPrefix = false;
        }
        String name = getName();
        if (!hasUri && !hasPrefix) {
            return name;
        }
        StringBuilder sbuf = new StringBuilder();
        if (hasPrefix && !(style == 'U' && hasUri)) {
            sbuf.append(prefix);
        }
        if (hasUri && !(style == 'P' && hasPrefix)) {
            sbuf.append('{');
            sbuf.append(getNamespaceURI());
            sbuf.append('}');
        }
        sbuf.append(':');
        sbuf.append(name);
        return sbuf.toString();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getNamespace());
        out.writeObject(getName());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.namespace = (Namespace) in.readObject();
        this.name = (String) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        return this.namespace == null ? this : make(this.namespace, getName());
    }
}
