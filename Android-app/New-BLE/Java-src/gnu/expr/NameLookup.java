package gnu.expr;

import gnu.kawa.util.GeneralHashTable;
import gnu.kawa.util.HashNode;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Symbol;

public class NameLookup extends GeneralHashTable<Object, Declaration> {
    static final Symbol KEY = Symbol.makeUninterned("<current-NameLookup>");
    Language language;

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public NameLookup(Language language) {
        this.language = language;
    }

    public static NameLookup getInstance(Environment env, Language language) {
        Location loc = env.getLocation(KEY);
        NameLookup nl = (NameLookup) loc.get(null);
        if (nl == null) {
            nl = new NameLookup(language);
            loc.set(nl);
            return nl;
        }
        nl.setLanguage(language);
        return nl;
    }

    public static void setInstance(Environment env, NameLookup instance) {
        if (instance == null) {
            env.remove(KEY);
        } else {
            env.put(KEY, null, instance);
        }
    }

    public void push(Declaration decl) {
        Object symbol = decl.getSymbol();
        if (symbol != null) {
            int i = this.num_bindings + 1;
            this.num_bindings = i;
            if (i >= ((HashNode[]) this.table).length) {
                rehash();
            }
            int hash = hash(symbol);
            HashNode node = makeEntry(symbol, hash, (Object) decl);
            int index = hashToIndex(hash);
            node.next = ((HashNode[]) this.table)[index];
            ((HashNode[]) this.table)[index] = node;
        }
    }

    public boolean pop(Declaration decl) {
        Object symbol = decl.getSymbol();
        if (symbol == null) {
            return false;
        }
        HashNode prev = null;
        int index = hashToIndex(hash(symbol));
        HashNode node = ((HashNode[]) this.table)[index];
        while (node != null) {
            HashNode next = node.next;
            if (node.getValue() == decl) {
                if (prev == null) {
                    ((HashNode[]) this.table)[index] = next;
                } else {
                    prev.next = next;
                }
                this.num_bindings--;
                return true;
            }
            prev = node;
            node = next;
        }
        return false;
    }

    public void push(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            push(decl);
        }
    }

    public void pop(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            pop(decl);
        }
    }

    public void removeSubsumed(Declaration decl) {
        int index = hashToIndex(hash(decl.getSymbol()));
        HashNode prev = null;
        HashNode node = ((HashNode[]) this.table)[index];
        while (node != null) {
            HashNode next = node.next;
            Declaration ndecl = (Declaration) node.getValue();
            if (ndecl == decl || !subsumedBy(decl, ndecl)) {
                prev = node;
            } else if (prev == null) {
                ((HashNode[]) this.table)[index] = next;
            } else {
                prev.next = next;
            }
            node = next;
        }
    }

    protected boolean subsumedBy(Declaration decl, Declaration other) {
        return decl.getSymbol() == other.getSymbol() && (this.language.getNamespaceOf(decl) & this.language.getNamespaceOf(other)) != 0;
    }

    public Declaration lookup(Object symbol, int namespace) {
        for (HashNode node = ((HashNode[]) this.table)[hashToIndex(hash(symbol))]; node != null; node = node.next) {
            Declaration decl = (Declaration) node.getValue();
            if (symbol.equals(decl.getSymbol()) && this.language.hasNamespace(decl, namespace)) {
                return decl;
            }
        }
        return null;
    }

    public Declaration lookup(Object symbol, boolean function) {
        return lookup(symbol, function ? 2 : 1);
    }
}
