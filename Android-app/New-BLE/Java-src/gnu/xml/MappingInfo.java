package gnu.xml;

import gnu.mapping.Symbol;

/* compiled from: XMLFilter */
final class MappingInfo {
    int index = -1;
    String local;
    NamespaceBinding namespaces;
    MappingInfo nextInBucket;
    String prefix;
    Symbol qname;
    int tagHash;
    XName type;
    String uri;

    MappingInfo() {
    }

    static int hash(String prefix, String local) {
        int hash = local.hashCode();
        if (prefix != null) {
            return hash ^ prefix.hashCode();
        }
        return hash;
    }

    static int hash(char[] data, int start, int length) {
        int hash = 0;
        int prefixHash = 0;
        int colonPos = -1;
        for (int i = 0; i < length; i++) {
            char ch = data[start + i];
            if (ch != ':' || colonPos >= 0) {
                hash = (hash * 31) + ch;
            } else {
                colonPos = i;
                prefixHash = hash;
                hash = 0;
            }
        }
        return prefixHash ^ hash;
    }

    boolean match(char[] data, int start, int length) {
        if (this.prefix == null) {
            return equals(this.local, data, start, length);
        }
        int localLength = this.local.length();
        int prefixLength = this.prefix.length();
        return length == (prefixLength + 1) + localLength && data[prefixLength] == ':' && equals(this.prefix, data, start, prefixLength) && equals(this.local, data, (start + prefixLength) + 1, localLength);
    }

    static boolean equals(String tag, StringBuffer sbuf) {
        int length = sbuf.length();
        if (tag.length() != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (sbuf.charAt(i) != tag.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    static boolean equals(String tag, char[] data, int start, int length) {
        if (tag.length() != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (data[start + i] != tag.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
