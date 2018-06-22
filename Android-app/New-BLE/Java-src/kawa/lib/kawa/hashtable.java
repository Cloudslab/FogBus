package kawa.lib.kawa;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.SetNamedPart;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.GeneralHashTable;
import gnu.kawa.util.HashNode;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.thisRef;

/* compiled from: hashtable.scm */
public class hashtable extends ModuleBody {
    public static final Location $Prvt$do = StaticFieldLocation.make("kawa.lib.std_syntax", "do");
    public static final Class $Prvt$hashnode = HashNode.class;
    public static final Location $Prvt$let$St = StaticFieldLocation.make("kawa.lib.std_syntax", "let$St");
    public static final hashtable $instance = new hashtable();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("mutable").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("hashtable-check-mutable").readResolve());
    public static final Class hashtable = HashTable.class;
    public static final ModuleMethod hashtable$Mncheck$Mnmutable = new ModuleMethod($instance, 1, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

    /* compiled from: hashtable.scm */
    public class HashTable extends GeneralHashTable {
        public Procedure equivalenceFunction;
        public Procedure hashFunction;
        public boolean mutable;

        private void $finit$() {
            this.mutable = true;
        }

        public HashTable(Procedure procedure, Procedure procedure2, int i) {
            super(i);
            $finit$();
            this.equivalenceFunction = procedure;
            this.hashFunction = procedure2;
        }

        public HashTable(Procedure procedure, Procedure procedure2) {
            $finit$();
            this.equivalenceFunction = procedure;
            this.hashFunction = procedure2;
        }

        public HashTable(HashTable hashTable, boolean z) {
            $finit$();
            Invoke.invokeSpecial.applyN(new Object[]{hashtable.hashtable, this, hashTable.equivalenceFunction.apply0(), hashTable.hashFunction.apply0(), Integer.valueOf(hashTable.size() + 100)});
            putAll(hashTable);
            SetNamedPart.setNamedPart.apply3(thisRef.thisSyntax, hashtable.Lit0, z ? Boolean.TRUE : Boolean.FALSE);
        }

        public int hash(Object key) {
            return ((Number) this.hashFunction.apply1(key)).intValue();
        }

        public boolean matches(Object value1, Object value2) {
            return this.equivalenceFunction.apply2(value1, value2) != Boolean.FALSE;
        }

        public void walk(Procedure proc) {
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        proc.apply2(node.getKey(), node.getValue());
                        node = getEntryNext(node);
                    }
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public Object fold(Procedure proc, Object acc) {
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        acc = proc.apply3(node.getKey(), node.getValue(), acc);
                        node = getEntryNext(node);
                    }
                }
                return acc;
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public FVector keysVector() {
            FVector v = new FVector();
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        v.add(node.getKey());
                        node = getEntryNext(node);
                    }
                }
                return v;
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public Pair entriesVectorPair() {
            FVector keys = new FVector();
            FVector vals = new FVector();
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        keys.add(node.getKey());
                        vals.add(node.getValue());
                        node = getEntryNext(node);
                    }
                }
                return lists.cons(keys, vals);
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public Object toAlist() {
            LList result = LList.Empty;
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        result = lists.cons(lists.cons(node.getKey(), node.getValue()), result);
                        node = getEntryNext(node);
                    }
                }
                return result;
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public LList toNodeList() {
            LList result = LList.Empty;
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                Object obj2 = result;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        result = lists.cons(node, obj2);
                        node = getEntryNext(node);
                        LList lList = result;
                    }
                }
                return (LList) obj2;
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public HashNode[] toNodeArray() {
            HashNode[] result = new HashNode[size()];
            int i = 0;
            Object obj = this.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                int length = table.length - 1;
                while (length >= 0) {
                    HashNode node = table[length];
                    int i2 = i;
                    while (node != null) {
                        result[i2] = node;
                        i = i2 + 1;
                        node = getEntryNext(node);
                        i2 = i;
                    }
                    length--;
                    i = i2;
                }
                return result;
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public void putAll(HashTable other) {
            Object obj = other.table;
            try {
                HashNode[] table = (HashNode[]) obj;
                for (int i = table.length - 1; i >= 0; i--) {
                    HashNode node = table[i];
                    while (node != null) {
                        put(node.getKey(), node.getValue());
                        node = other.getEntryNext(node);
                    }
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "table", -2, obj);
            }
        }

        public Object clone() {
            return new HashTable(this, true);
        }
    }

    public hashtable() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static {
        $instance.run();
    }

    public static void hashtableCheckMutable(HashTable ht) {
        if (!ht.mutable) {
            misc.error$V("cannot modify non-mutable hashtable", new Object[0]);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        if (moduleMethod.selector != 1) {
            return super.apply1(moduleMethod, obj);
        }
        try {
            hashtableCheckMutable((HashTable) obj);
            return Values.empty;
        } catch (ClassCastException e) {
            throw new WrongType(e, "hashtable-check-mutable", 1, obj);
        }
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match1(moduleMethod, obj, callContext);
        }
        if (!(obj instanceof HashTable)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.pc = 1;
        return 0;
    }
}
