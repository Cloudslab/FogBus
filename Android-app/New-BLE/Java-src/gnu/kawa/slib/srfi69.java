package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.HashNode;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lib.kawa.hashtable;
import kawa.lib.kawa.hashtable.HashTable;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.rnrs.hashtables;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.standard.Scheme;

/* compiled from: srfi69.scm */
public class srfi69 extends ModuleBody {
    public static final srfi69 $instance = new srfi69();
    static final IntNum Lit0 = IntNum.make(64);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("string-hash").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("hash-table-update!").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("hash-table-update!/default").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("hash-table-walk").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("hash-table-fold").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("alist->hash-table").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("hash-table->alist").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("hash-table-copy").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("hash-table-merge!").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("hash-table-keys").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("hash-table-values").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("string-ci-hash").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("hash").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("hash-by-identity").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("hash-table-equivalence-function").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("hash-table-hash-function").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("make-hash-table").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("hash-table-ref").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("hash-table-ref/default").readResolve());
    public static final ModuleMethod alist$Mn$Grhash$Mntable;
    public static final ModuleMethod hash;
    public static final ModuleMethod hash$Mnby$Mnidentity;
    public static final ModuleMethod hash$Mntable$Mn$Gralist;
    public static final ModuleMethod hash$Mntable$Mncopy;
    public static final Location hash$Mntable$Mndelete$Ex = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mndelete$Ex");
    public static final ModuleMethod hash$Mntable$Mnequivalence$Mnfunction;
    public static final Location hash$Mntable$Mnexists$Qu = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mncontains$Qu");
    public static final ModuleMethod hash$Mntable$Mnfold;
    public static final ModuleMethod hash$Mntable$Mnhash$Mnfunction;
    public static final ModuleMethod hash$Mntable$Mnkeys;
    public static final ModuleMethod hash$Mntable$Mnmerge$Ex;
    public static final ModuleMethod hash$Mntable$Mnref;
    public static final ModuleMethod hash$Mntable$Mnref$Sldefault;
    public static final Location hash$Mntable$Mnset$Ex = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mnset$Ex");
    public static final Location hash$Mntable$Mnsize = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Mnsize");
    public static final ModuleMethod hash$Mntable$Mnupdate$Ex;
    public static final ModuleMethod hash$Mntable$Mnupdate$Ex$Sldefault;
    public static final ModuleMethod hash$Mntable$Mnvalues;
    public static final ModuleMethod hash$Mntable$Mnwalk;
    public static final Location hash$Mntable$Qu = StaticFieldLocation.make("kawa.lib.rnrs.hashtables", "hashtable$Qu");
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod make$Mnhash$Mntable;
    public static final ModuleMethod string$Mnci$Mnhash;
    public static final ModuleMethod string$Mnhash;

    public srfi69() {
        ModuleInfo.register(this);
    }

    public static HashTable alist$To$HashTable(Object obj) {
        return alist$To$HashTable(obj, Scheme.isEqual);
    }

    public static HashTable alist$To$HashTable(Object obj, Object obj2) {
        return alist$To$HashTable(obj, obj2, appropriateHashFunctionFor(obj2));
    }

    public static Object hash(Object obj) {
        return hash(obj, null);
    }

    public static Object hashByIdentity(Object obj) {
        return hashByIdentity(obj, null);
    }

    public static Object hashTableRef(HashTable hashTable, Object obj) {
        return hashTableRef(hashTable, obj, Boolean.FALSE);
    }

    public static void hashTableUpdate$Ex(HashTable hashTable, Object obj, Object obj2) {
        hashTableUpdate$Ex(hashTable, obj, obj2, Boolean.FALSE);
    }

    public static HashTable makeHashTable() {
        return makeHashTable(Scheme.isEqual);
    }

    public static HashTable makeHashTable(Procedure procedure) {
        return makeHashTable(procedure, appropriateHashFunctionFor(procedure), 64);
    }

    public static HashTable makeHashTable(Procedure procedure, Procedure procedure2) {
        return makeHashTable(procedure, procedure2, 64);
    }

    public static Object stringCiHash(Object obj) {
        return stringCiHash(obj, null);
    }

    public static Object stringHash(CharSequence charSequence) {
        return stringHash(charSequence, null);
    }

    static Object symbolHash(Symbol symbol) {
        return symbolHash(symbol, null);
    }

    static Object vectorHash(Object obj) {
        return vectorHash(obj, null);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static {
        ModuleBody moduleBody = $instance;
        string$Mnhash = new ModuleMethod(moduleBody, 1, Lit1, 8193);
        string$Mnci$Mnhash = new ModuleMethod(moduleBody, 3, Lit2, 8193);
        hash = new ModuleMethod(moduleBody, 5, Lit3, 8193);
        hash$Mnby$Mnidentity = new ModuleMethod(moduleBody, 7, Lit4, 8193);
        hash$Mntable$Mnequivalence$Mnfunction = new ModuleMethod(moduleBody, 9, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        hash$Mntable$Mnhash$Mnfunction = new ModuleMethod(moduleBody, 10, Lit6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnhash$Mntable = new ModuleMethod(moduleBody, 11, Lit7, 12288);
        hash$Mntable$Mnref = new ModuleMethod(moduleBody, 15, Lit8, 12290);
        hash$Mntable$Mnref$Sldefault = new ModuleMethod(moduleBody, 17, Lit9, 12291);
        hash$Mntable$Mnupdate$Ex = new ModuleMethod(moduleBody, 18, Lit10, 16387);
        hash$Mntable$Mnupdate$Ex$Sldefault = new ModuleMethod(moduleBody, 20, Lit11, 16388);
        hash$Mntable$Mnwalk = new ModuleMethod(moduleBody, 21, Lit12, 8194);
        hash$Mntable$Mnfold = new ModuleMethod(moduleBody, 22, Lit13, 12291);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 23, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi69.scm:166");
        lambda$Fn1 = moduleMethod;
        alist$Mn$Grhash$Mntable = new ModuleMethod(moduleBody, 24, Lit14, 16385);
        hash$Mntable$Mn$Gralist = new ModuleMethod(moduleBody, 28, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        hash$Mntable$Mncopy = new ModuleMethod(moduleBody, 29, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        hash$Mntable$Mnmerge$Ex = new ModuleMethod(moduleBody, 30, Lit17, 8194);
        moduleMethod = new ModuleMethod(moduleBody, 31, null, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi69.scm:183");
        lambda$Fn2 = moduleMethod;
        hash$Mntable$Mnkeys = new ModuleMethod(moduleBody, 32, Lit18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod = new ModuleMethod(moduleBody, 33, null, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi69.scm:186");
        lambda$Fn3 = moduleMethod;
        hash$Mntable$Mnvalues = new ModuleMethod(moduleBody, 34, Lit19, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public static Object stringHash(CharSequence s, IntNum bound) {
        int h = s.hashCode();
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 9:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 11:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 32:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 34:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 3:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 5:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 11:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 15:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 21:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 30:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof HashTable)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object stringCiHash(Object s, IntNum bound) {
        int h = s.toString().toLowerCase().hashCode();
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    static Object symbolHash(Symbol s, IntNum bound) {
        int h = s.hashCode();
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    public static Object hash(Object obj, IntNum bound) {
        int h = obj == null ? 0 : obj.hashCode();
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    public static Object hashByIdentity(Object obj, IntNum bound) {
        int h = System.identityHashCode(obj);
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    static Object vectorHash(Object v, IntNum bound) {
        int h = v.hashCode();
        return bound == null ? Integer.valueOf(h) : IntNum.modulo(IntNum.make(h), bound);
    }

    public static Procedure hashTableEquivalenceFunction(HashTable hash$Mntable) {
        return hash$Mntable.equivalenceFunction;
    }

    public static Procedure hashTableHashFunction(HashTable hash$Mntable) {
        return hash$Mntable.hashFunction;
    }

    static Procedure appropriateHashFunctionFor(Object comparison) {
        boolean x;
        if (comparison == Scheme.isEq) {
            x = true;
        } else {
            x = false;
        }
        x = x ? hash$Mnby$Mnidentity : x ? Boolean.TRUE : Boolean.FALSE;
        if (x != Boolean.FALSE) {
            return (Procedure) x;
        }
        if (comparison == strings.string$Eq$Qu) {
            x = true;
        } else {
            x = false;
        }
        x = x ? string$Mnhash : x ? Boolean.TRUE : Boolean.FALSE;
        if (x != Boolean.FALSE) {
            return (Procedure) x;
        }
        if (comparison == unicode.string$Mnci$Eq$Qu) {
            x = true;
        } else {
            x = false;
        }
        x = x ? string$Mnci$Mnhash : x ? Boolean.TRUE : Boolean.FALSE;
        return x != Boolean.FALSE ? (Procedure) x : hash;
    }

    public static HashTable makeHashTable(Procedure comparison, Procedure hash, int size) {
        return new HashTable(comparison, hash, size);
    }

    public Object apply0(ModuleMethod moduleMethod) {
        return moduleMethod.selector == 11 ? makeHashTable() : super.apply0(moduleMethod);
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        if (moduleMethod.selector != 11) {
            return super.match0(moduleMethod, callContext);
        }
        callContext.proc = moduleMethod;
        callContext.pc = 0;
        return 0;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 11:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 15:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 17:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 18:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 22:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object hashTableRef(HashTable hash$Mntable, Object key, Object default_) {
        HashNode node = hash$Mntable.getNode(key);
        if (node != null) {
            return node.getValue();
        }
        if (default_ != Boolean.FALSE) {
            return Scheme.applyToArgs.apply1(default_);
        }
        return misc.error$V("hash-table-ref: no value associated with", new Object[]{key});
    }

    public static Object hashTableRef$SlDefault(HashTable hash$Mntable, Object key, Object default_) {
        return hash$Mntable.get(key, default_);
    }

    public static void hashTableUpdate$Ex(HashTable hash$Mntable, Object key, Object function, Object thunk) {
        hashtable.hashtableCheckMutable(hash$Mntable);
        HashNode node = hash$Mntable.getNode(key);
        if (node != null) {
            node.setValue(Scheme.applyToArgs.apply2(function, node.getValue()));
        } else if (thunk != Boolean.FALSE) {
            hashtables.hashtableSet$Ex(hash$Mntable, key, Scheme.applyToArgs.apply2(function, Scheme.applyToArgs.apply1(thunk)));
        } else {
            misc.error$V("hash-table-update!: no value exists for key", new Object[]{key});
        }
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 18:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 20:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    public static void hashTableUpdate$Ex$SlDefault(HashTable hash$Mntable, Object key, Object function, Object default_) {
        hashtable.hashtableCheckMutable(hash$Mntable);
        HashNode node = hash$Mntable.getNode(key);
        if (node == null) {
            hashtables.hashtableSet$Ex(hash$Mntable, key, Scheme.applyToArgs.apply2(function, default_));
        } else {
            node.setValue(Scheme.applyToArgs.apply2(function, node.getValue()));
        }
    }

    public static void hashTableWalk(HashTable hash$Mntable, Procedure proc) {
        hash$Mntable.walk(proc);
    }

    public static Object hashTableFold(HashTable hash$Mntable, Procedure proc, Object acc) {
        return hash$Mntable.fold(proc, acc);
    }

    public static HashTable alist$To$HashTable(Object alist, Object comparison, Object hash, Object size) {
        try {
            try {
                try {
                    HashTable hash$Mntable = makeHashTable((Procedure) comparison, (Procedure) hash, ((Number) size).intValue());
                    Object arg0 = alist;
                    while (arg0 != LList.Empty) {
                        try {
                            Pair arg02 = (Pair) arg0;
                            Object elem = arg02.getCar();
                            hashTableUpdate$Ex$SlDefault(hash$Mntable, lists.car.apply1(elem), lambda$Fn1, lists.cdr.apply1(elem));
                            arg0 = arg02.getCdr();
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "arg0", -2, arg0);
                        }
                    }
                    return hash$Mntable;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-hash-table", 2, size);
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "make-hash-table", 1, hash);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "make-hash-table", 0, comparison);
        }
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case 18:
                try {
                    hashTableUpdate$Ex((HashTable) obj, obj2, obj3, obj4);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "hash-table-update!", 1, obj);
                }
            case 20:
                try {
                    hashTableUpdate$Ex$SlDefault((HashTable) obj, obj2, obj3, obj4);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "hash-table-update!/default", 1, obj);
                }
            case 24:
                return alist$To$HashTable(obj, obj2, obj3, obj4);
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static HashTable alist$To$HashTable(Object obj, Object obj2, Object obj3) {
        Object[] objArr = new Object[2];
        objArr[0] = Lit0;
        try {
            objArr[1] = Integer.valueOf(lists.length((LList) obj) * 2);
            return alist$To$HashTable(obj, obj2, obj3, numbers.max(objArr));
        } catch (ClassCastException e) {
            throw new WrongType(e, "length", 1, obj);
        }
    }

    static Object lambda1(Object x) {
        return x;
    }

    public static Object hashTable$To$Alist(HashTable hash$Mntable) {
        return hash$Mntable.toAlist();
    }

    public static HashTable hashTableCopy(HashTable hash$Mntable) {
        return new HashTable(hash$Mntable, true);
    }

    public static void hashTableMerge$Ex(HashTable hash$Mntable1, HashTable hash$Mntable2) {
        hash$Mntable1.putAll(hash$Mntable2);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 1:
                try {
                    try {
                        return stringHash((CharSequence) obj, LangObjType.coerceIntNum(obj2));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-hash", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-hash", 1, obj);
                }
            case 3:
                try {
                    return stringCiHash(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "string-ci-hash", 2, obj2);
                }
            case 5:
                try {
                    return hash(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "hash", 2, obj2);
                }
            case 7:
                try {
                    return hashByIdentity(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hash-by-identity", 2, obj2);
                }
            case 11:
                try {
                    try {
                        return makeHashTable((Procedure) obj, (Procedure) obj2);
                    } catch (ClassCastException e22222) {
                        throw new WrongType(e22222, "make-hash-table", 2, obj2);
                    }
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "make-hash-table", 1, obj);
                }
            case 15:
                try {
                    return hashTableRef((HashTable) obj, obj2);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "hash-table-ref", 1, obj);
                }
            case 21:
                try {
                    try {
                        hashTableWalk((HashTable) obj, (Procedure) obj2);
                        return Values.empty;
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "hash-table-walk", 2, obj2);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "hash-table-walk", 1, obj);
                }
            case 24:
                return alist$To$HashTable(obj, obj2);
            case 30:
                try {
                    try {
                        hashTableMerge$Ex((HashTable) obj, (HashTable) obj2);
                        return Values.empty;
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "hash-table-merge!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "hash-table-merge!", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object hashTableKeys(HashTable hash$Mntable) {
        return hashTableFold(hash$Mntable, lambda$Fn2, LList.Empty);
    }

    static Pair lambda2(Object key, Object val, Object acc) {
        return lists.cons(key, acc);
    }

    public static Object hashTableValues(HashTable hash$Mntable) {
        return hashTableFold(hash$Mntable, lambda$Fn3, LList.Empty);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                try {
                    return stringHash((CharSequence) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-hash", 1, obj);
                }
            case 3:
                return stringCiHash(obj);
            case 5:
                return hash(obj);
            case 7:
                return hashByIdentity(obj);
            case 9:
                try {
                    return hashTableEquivalenceFunction((HashTable) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "hash-table-equivalence-function", 1, obj);
                }
            case 10:
                try {
                    return hashTableHashFunction((HashTable) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "hash-table-hash-function", 1, obj);
                }
            case 11:
                try {
                    return makeHashTable((Procedure) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "make-hash-table", 1, obj);
                }
            case 23:
                return lambda1(obj);
            case 24:
                return alist$To$HashTable(obj);
            case 28:
                try {
                    return hashTable$To$Alist((HashTable) obj);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hash-table->alist", 1, obj);
                }
            case 29:
                try {
                    return hashTableCopy((HashTable) obj);
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "hash-table-copy", 1, obj);
                }
            case 32:
                try {
                    return hashTableKeys((HashTable) obj);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "hash-table-keys", 1, obj);
                }
            case 34:
                try {
                    return hashTableValues((HashTable) obj);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "hash-table-values", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Pair lambda3(Object key, Object val, Object acc) {
        return lists.cons(val, acc);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 11:
                try {
                    try {
                        try {
                            return makeHashTable((Procedure) obj, (Procedure) obj2, ((Number) obj3).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "make-hash-table", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "make-hash-table", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "make-hash-table", 1, obj);
                }
            case 15:
                try {
                    return hashTableRef((HashTable) obj, obj2, obj3);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "hash-table-ref", 1, obj);
                }
            case 17:
                try {
                    return hashTableRef$SlDefault((HashTable) obj, obj2, obj3);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hash-table-ref/default", 1, obj);
                }
            case 18:
                try {
                    hashTableUpdate$Ex((HashTable) obj, obj2, obj3);
                    return Values.empty;
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "hash-table-update!", 1, obj);
                }
            case 22:
                try {
                    try {
                        return hashTableFold((HashTable) obj, (Procedure) obj2, obj3);
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "hash-table-fold", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "hash-table-fold", 1, obj);
                }
            case 24:
                return alist$To$HashTable(obj, obj2, obj3);
            case 31:
                return lambda2(obj, obj2, obj3);
            case 33:
                return lambda3(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }
}
