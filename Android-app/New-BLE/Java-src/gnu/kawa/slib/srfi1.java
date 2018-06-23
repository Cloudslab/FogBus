package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.TransportMediator;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.YaVersion;
import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DateTime;
import gnu.math.IntNum;
import gnu.math.Numeric;
import kawa.lang.Continuation;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.standard.Scheme;
import kawa.standard.append;
import kawa.standard.call_with_values;

/* compiled from: srfi1.scm */
public class srfi1 extends ModuleBody {
    public static final Macro $Pcevery = Macro.make(Lit84, Lit85, $instance);
    public static final int $Pcprovide$Pclist$Mnlib = 123;
    public static final int $Pcprovide$Pcsrfi$Mn1 = 123;
    public static final srfi1 $instance = new srfi1();
    static final IntNum Lit0 = IntNum.make(0);
    static final IntNum Lit1 = IntNum.make(1);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("proper-list?").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("tail").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("head").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("lp").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("car").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("cdr").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("dotted-list?").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("circular-list?").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("not-pair?").readResolve());
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("list=").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("length+").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("zip").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("fifth").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("sixth").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("tmp").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("seventh").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("eighth").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("ninth").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("tenth").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("car+cdr").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("take").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("drop").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("take!").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("take-right").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("drop-right").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("xcons").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("drop-right!").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("split-at").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("split-at!").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("last").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("last-pair").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("unzip1").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("unzip2").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("unzip3").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("unzip4").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("unzip5").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("make-list").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("append!").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("append-reverse").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("append-reverse!").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("concatenate").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("concatenate!").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("count").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("unfold-right").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("unfold").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("fold").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("fold-right").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("list-tabulate").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("pair-fold-right").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("pair-fold").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("reduce").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("reduce-right").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("append-map").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("append-map!").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("pair-for-each").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("map!").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("filter-map").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("filter").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("cons*").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("filter!").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("partition").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("partition!").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("remove").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("remove!").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("delete").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("delete!").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("delete-duplicates").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("delete-duplicates!").readResolve());
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("alist-cons").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("list-copy").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("alist-copy").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("alist-delete").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("alist-delete!").readResolve());
    static final SimpleSymbol Lit73 = ((SimpleSymbol) new SimpleSymbol("find").readResolve());
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("find-tail").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("take-while").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("drop-while").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("take-while!").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("span").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("span!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("iota").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("break").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("break!").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("every").readResolve());
    static final SimpleSymbol Lit84;
    static final SyntaxRules Lit85;
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("list-index").readResolve());
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("lset<=").readResolve());
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("lset=").readResolve());
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("lset-adjoin").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("circular-list").readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("lset-union").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("lset-union!").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("lset-intersection").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("lset-intersection!").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("lset-difference").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("lset-difference!").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("lset-xor").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("lset-xor!").readResolve());
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("lset-diff+intersection").readResolve());
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("lset-diff+intersection!").readResolve());
    public static final ModuleMethod alist$Mncons;
    public static final ModuleMethod alist$Mncopy;
    public static final ModuleMethod alist$Mndelete;
    public static final ModuleMethod alist$Mndelete$Ex;
    public static final ModuleMethod any;
    public static final ModuleMethod append$Ex;
    public static final ModuleMethod append$Mnmap;
    public static final ModuleMethod append$Mnmap$Ex;
    public static final ModuleMethod append$Mnreverse;
    public static final ModuleMethod append$Mnreverse$Ex;
    public static final ModuleMethod f95break;
    public static final ModuleMethod break$Ex;
    public static final ModuleMethod car$Plcdr;
    public static final ModuleMethod circular$Mnlist;
    public static final ModuleMethod circular$Mnlist$Qu;
    public static final ModuleMethod concatenate;
    public static final ModuleMethod concatenate$Ex;
    public static final ModuleMethod cons$St;
    public static final ModuleMethod count;
    public static final ModuleMethod delete;
    public static final ModuleMethod delete$Ex;
    public static final ModuleMethod delete$Mnduplicates;
    public static final ModuleMethod delete$Mnduplicates$Ex;
    public static final ModuleMethod dotted$Mnlist$Qu;
    public static final ModuleMethod drop;
    public static final ModuleMethod drop$Mnright;
    public static final ModuleMethod drop$Mnright$Ex;
    public static final ModuleMethod drop$Mnwhile;
    public static final ModuleMethod eighth;
    public static final ModuleMethod every;
    public static final ModuleMethod fifth;
    public static final ModuleMethod filter;
    public static final ModuleMethod filter$Ex;
    public static final ModuleMethod filter$Mnmap;
    public static final ModuleMethod find;
    public static final ModuleMethod find$Mntail;
    public static GenericProc first;
    public static final ModuleMethod fold;
    public static final ModuleMethod fold$Mnright;
    public static GenericProc fourth;
    public static final ModuleMethod iota;
    static final ModuleMethod lambda$Fn64;
    static final ModuleMethod lambda$Fn78;
    public static final ModuleMethod last;
    public static final ModuleMethod last$Mnpair;
    public static final ModuleMethod length$Pl;
    public static final ModuleMethod list$Eq;
    public static final ModuleMethod list$Mncopy;
    public static final ModuleMethod list$Mnindex;
    public static final ModuleMethod list$Mntabulate;
    public static final ModuleMethod lset$Eq;
    public static final ModuleMethod lset$Ls$Eq;
    public static final ModuleMethod lset$Mnadjoin;
    public static final ModuleMethod lset$Mndiff$Plintersection;
    public static final ModuleMethod lset$Mndiff$Plintersection$Ex;
    public static final ModuleMethod lset$Mndifference;
    public static final ModuleMethod lset$Mndifference$Ex;
    public static final ModuleMethod lset$Mnintersection;
    public static final ModuleMethod lset$Mnintersection$Ex;
    public static final ModuleMethod lset$Mnunion;
    public static final ModuleMethod lset$Mnunion$Ex;
    public static final ModuleMethod lset$Mnxor;
    public static final ModuleMethod lset$Mnxor$Ex;
    public static final ModuleMethod make$Mnlist;
    public static final ModuleMethod map$Ex;
    public static Map map$Mnin$Mnorder;
    public static final ModuleMethod ninth;
    public static final ModuleMethod not$Mnpair$Qu;
    public static final ModuleMethod null$Mnlist$Qu;
    public static final ModuleMethod pair$Mnfold;
    public static final ModuleMethod pair$Mnfold$Mnright;
    public static final ModuleMethod pair$Mnfor$Mneach;
    public static final ModuleMethod partition;
    public static final ModuleMethod partition$Ex;
    public static final ModuleMethod proper$Mnlist$Qu;
    public static final ModuleMethod reduce;
    public static final ModuleMethod reduce$Mnright;
    public static final ModuleMethod remove;
    public static final ModuleMethod remove$Ex;
    public static GenericProc second;
    public static final ModuleMethod seventh;
    public static final ModuleMethod sixth;
    public static final ModuleMethod span;
    public static final ModuleMethod span$Ex;
    public static final ModuleMethod split$Mnat;
    public static final ModuleMethod split$Mnat$Ex;
    public static final ModuleMethod take;
    public static final ModuleMethod take$Ex;
    public static final ModuleMethod take$Mnright;
    public static final ModuleMethod take$Mnwhile;
    public static final ModuleMethod take$Mnwhile$Ex;
    public static final ModuleMethod tenth;
    public static GenericProc third;
    public static final ModuleMethod unfold;
    public static final ModuleMethod unfold$Mnright;
    public static final ModuleMethod unzip1;
    public static final ModuleMethod unzip2;
    public static final ModuleMethod unzip3;
    public static final ModuleMethod unzip4;
    public static final ModuleMethod unzip5;
    public static final ModuleMethod xcons;
    public static final ModuleMethod zip;

    /* compiled from: srfi1.scm */
    public class frame0 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn1 = new ModuleMethod(this, 1, null, 0);
        final ModuleMethod lambda$Fn2;
        Object lis;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 2, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:627");
            this.lambda$Fn2 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda3() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 2 ? lambda4(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda3() {
            return frame.lambda2recur(lists.cdr.apply1(this.lis));
        }

        Object lambda4(Object a, Object b) {
            return misc.values(lists.cons(lists.car.apply1(this.elt), a), lists.cons(lists.cadr.apply1(this.elt), b));
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame10 extends ModuleBody {
        Procedure f30f;
        Object zero;

        public Object lambda19recur(Object lists) {
            if (lists.isNull(srfi1.$PcCdrs(lists))) {
                return this.zero;
            }
            return Scheme.apply.apply2(this.f30f, srfi1.append$Ex$V(new Object[]{lists, LList.list1(lambda19recur(cdrs))}));
        }

        public Object lambda20recur(Object lis) {
            return srfi1.isNullList(lis) != Boolean.FALSE ? this.zero : this.f30f.apply2(lis, lambda20recur(lists.cdr.apply1(lis)));
        }
    }

    /* compiled from: srfi1.scm */
    public class frame11 extends ModuleBody {
        Procedure f31f;

        public Object lambda21recur(Object head, Object lis) {
            if (lists.isPair(lis)) {
                return this.f31f.apply2(head, lambda21recur(lists.car.apply1(lis), lists.cdr.apply1(lis)));
            }
            return head;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame12 extends ModuleBody {
        Procedure f32f;
        final ModuleMethod lambda$Fn11;

        public frame12() {
            PropertySet moduleMethod = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:961");
            this.lambda$Fn11 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector != 11) {
                return super.apply1(moduleMethod, obj);
            }
            lambda22(obj);
            return Values.empty;
        }

        void lambda22(Object pair) {
            try {
                lists.setCar$Ex((Pair) pair, this.f32f.apply1(lists.car.apply1(pair)));
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-car!", 1, pair);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame13 extends ModuleBody {
        Procedure f33f;

        public Object lambda23recur(Object lists, Object res) {
            frame14 gnu_kawa_slib_srfi1_frame14 = new frame14();
            gnu_kawa_slib_srfi1_frame14.staticLink = this;
            gnu_kawa_slib_srfi1_frame14.lists = lists;
            gnu_kawa_slib_srfi1_frame14.res = res;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame14.lambda$Fn12, gnu_kawa_slib_srfi1_frame14.lambda$Fn13);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame14 extends ModuleBody {
        final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 12, null, 0);
        final ModuleMethod lambda$Fn13;
        Object lists;
        Object res;
        frame13 staticLink;

        public frame14() {
            PropertySet moduleMethod = new ModuleMethod(this, 13, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:969");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 12 ? lambda24() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 12) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 13 ? lambda25(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda24() {
            return srfi1.$PcCars$PlCdrs(this.lists);
        }

        Object lambda25(Object cars, Object cdrs) {
            if (srfi1.isNotPair(cars)) {
                Object obj = this.res;
                try {
                    return lists.reverse$Ex((LList) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "reverse!", 1, obj);
                }
            }
            Boolean head = Scheme.apply.apply2(this.staticLink.f33f, cars);
            if (head != Boolean.FALSE) {
                return this.staticLink.lambda23recur(cdrs, lists.cons(head, this.res));
            }
            return this.staticLink.lambda23recur(cdrs, this.res);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 13) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame15 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object pred;

        public frame15() {
            PropertySet moduleMethod = new ModuleMethod(this, 14, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1199");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 14) {
                return lambda26(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda26(Object x) {
            return ((Scheme.applyToArgs.apply2(this.pred, x) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 14) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame16 extends ModuleBody {
        final ModuleMethod lambda$Fn15;
        Object pred;

        public frame16() {
            PropertySet moduleMethod = new ModuleMethod(this, 15, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1200");
            this.lambda$Fn15 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 15) {
                return lambda27(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda27(Object x) {
            return ((Scheme.applyToArgs.apply2(this.pred, x) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame17 extends ModuleBody {
        final ModuleMethod lambda$Fn16;
        Object maybe$Mn$Eq;
        Object f34x;

        public frame17() {
            PropertySet moduleMethod = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1222");
            this.lambda$Fn16 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 16) {
                return lambda28(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda28(Object y) {
            return ((Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.f34x, y) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 16) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame18 extends ModuleBody {
        final ModuleMethod lambda$Fn17;
        Object maybe$Mn$Eq;
        Object f35x;

        public frame18() {
            PropertySet moduleMethod = new ModuleMethod(this, 17, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1225");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 17) {
                return lambda29(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda29(Object y) {
            return ((Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.f35x, y) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 17) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame19 extends ModuleBody {
        Procedure maybe$Mn$Eq;

        public Object lambda30recur(Object lis) {
            if (srfi1.isNullList(lis) != Boolean.FALSE) {
                return lis;
            }
            Object x = lists.car.apply1(lis);
            Object tail = lists.cdr.apply1(lis);
            Object new$Mntail = lambda30recur(srfi1.delete(x, tail, this.maybe$Mn$Eq));
            return tail != new$Mntail ? lists.cons(x, new$Mntail) : lis;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame1 extends ModuleBody {
        public static Object lambda5recur(Object lis) {
            frame2 gnu_kawa_slib_srfi1_frame2 = new frame2();
            gnu_kawa_slib_srfi1_frame2.lis = lis;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame2.lis) != Boolean.FALSE) {
                return misc.values(gnu_kawa_slib_srfi1_frame2.lis, gnu_kawa_slib_srfi1_frame2.lis, gnu_kawa_slib_srfi1_frame2.lis);
            }
            gnu_kawa_slib_srfi1_frame2.elt = lists.car.apply1(gnu_kawa_slib_srfi1_frame2.lis);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame2.lambda$Fn3, gnu_kawa_slib_srfi1_frame2.lambda$Fn4);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame20 extends ModuleBody {
        Procedure maybe$Mn$Eq;

        public Object lambda31recur(Object lis) {
            if (srfi1.isNullList(lis) != Boolean.FALSE) {
                return lis;
            }
            Object x = lists.car.apply1(lis);
            Object tail = lists.cdr.apply1(lis);
            Object new$Mntail = lambda31recur(srfi1.delete$Ex(x, tail, this.maybe$Mn$Eq));
            return tail != new$Mntail ? lists.cons(x, new$Mntail) : lis;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame21 extends ModuleBody {
        Object key;
        final ModuleMethod lambda$Fn18;
        Object maybe$Mn$Eq;

        public frame21() {
            PropertySet moduleMethod = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1280");
            this.lambda$Fn18 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 18) {
                return lambda32(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda32(Object elt) {
            return ((Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.key, lists.car.apply1(elt)) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 18) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame22 extends ModuleBody {
        Object key;
        final ModuleMethod lambda$Fn19;
        Object maybe$Mn$Eq;

        public frame22() {
            PropertySet moduleMethod = new ModuleMethod(this, 19, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1283");
            this.lambda$Fn19 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 19) {
                return lambda33(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda33(Object elt) {
            return ((Scheme.applyToArgs.apply3(this.maybe$Mn$Eq, this.key, lists.car.apply1(elt)) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 19) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame23 extends ModuleBody {
        Procedure pred;

        public Object lambda34recur(Object lis) {
            if (srfi1.isNullList(lis) != Boolean.FALSE) {
                return LList.Empty;
            }
            Object x = lists.car.apply1(lis);
            return this.pred.apply1(x) != Boolean.FALSE ? lists.cons(x, lambda34recur(lists.cdr.apply1(lis))) : LList.Empty;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame24 extends ModuleBody {
        final ModuleMethod lambda$Fn20;
        Object pred;

        public frame24() {
            PropertySet moduleMethod = new ModuleMethod(this, 20, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1343");
            this.lambda$Fn20 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 20) {
                return lambda35(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda35(Object x) {
            return ((Scheme.applyToArgs.apply2(this.pred, x) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 20) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame25 extends ModuleBody {
        final ModuleMethod lambda$Fn21;
        Object pred;

        public frame25() {
            PropertySet moduleMethod = new ModuleMethod(this, 21, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1344");
            this.lambda$Fn21 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 21) {
                return lambda36(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda36(Object x) {
            return ((Scheme.applyToArgs.apply2(this.pred, x) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 21) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame26 extends ModuleBody {
        final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 22, null, 0);
        final ModuleMethod lambda$Fn23;
        Object lis1;
        LList lists;
        Procedure pred;

        public frame26() {
            PropertySet moduleMethod = new ModuleMethod(this, 23, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1350");
            this.lambda$Fn23 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 22 ? lambda37() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 22) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 23 ? lambda38(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda37() {
            return srfi1.$PcCars$PlCdrs(lists.cons(this.lis1, this.lists));
        }

        Object lambda38(Object heads, Object tails) {
            boolean x = lists.isPair(heads);
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                while (true) {
                    Object split = srfi1.$PcCars$PlCdrs$SlPair(tails);
                    Object next$Mnheads = lists.car.apply1(split);
                    Object next$Mntails = lists.cdr.apply1(split);
                    if (!lists.isPair(next$Mnheads)) {
                        return Scheme.apply.apply2(this.pred, heads);
                    }
                    x = Scheme.apply.apply2(this.pred, heads);
                    if (x != Boolean.FALSE) {
                        return x;
                    }
                    tails = next$Mntails;
                    heads = next$Mnheads;
                }
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 23) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame27 extends ModuleBody {
        final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 26, null, 0);
        final ModuleMethod lambda$Fn25;
        Object lis1;
        LList lists;
        Procedure pred;

        public frame27() {
            PropertySet moduleMethod = new ModuleMethod(this, 27, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1378");
            this.lambda$Fn25 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 26 ? lambda39() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 26) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 27 ? lambda40(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda39() {
            return srfi1.$PcCars$PlCdrs(lists.cons(this.lis1, this.lists));
        }

        Object lambda40(Object heads, Object tails) {
            frame28 gnu_kawa_slib_srfi1_frame28 = new frame28();
            gnu_kawa_slib_srfi1_frame28.staticLink = this;
            boolean x = (lists.isPair(heads) + 1) & 1;
            if (x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return gnu_kawa_slib_srfi1_frame28.lambda41lp(heads, tails);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 27) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame28 extends ModuleBody {
        frame27 staticLink;

        public Object lambda41lp(Object heads, Object tails) {
            frame29 gnu_kawa_slib_srfi1_frame29 = new frame29();
            gnu_kawa_slib_srfi1_frame29.staticLink = this;
            gnu_kawa_slib_srfi1_frame29.heads = heads;
            gnu_kawa_slib_srfi1_frame29.tails = tails;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame29.lambda$Fn26, gnu_kawa_slib_srfi1_frame29.lambda$Fn27);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame29 extends ModuleBody {
        Object heads;
        final ModuleMethod lambda$Fn26 = new ModuleMethod(this, 24, null, 0);
        final ModuleMethod lambda$Fn27;
        frame28 staticLink;
        Object tails;

        public frame29() {
            PropertySet moduleMethod = new ModuleMethod(this, 25, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1381");
            this.lambda$Fn27 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 24 ? lambda42() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 24) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 25 ? lambda43(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda42() {
            return srfi1.$PcCars$PlCdrs(this.tails);
        }

        Object lambda43(Object next$Mnheads, Object next$Mntails) {
            if (!lists.isPair(next$Mnheads)) {
                return Scheme.apply.apply2(this.staticLink.staticLink.pred, this.heads);
            }
            Boolean x = Scheme.apply.apply2(this.staticLink.staticLink.pred, this.heads);
            if (x != Boolean.FALSE) {
                return this.staticLink.lambda41lp(next$Mnheads, next$Mntails);
            }
            return x;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 25) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame2 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn4;
        Object lis;

        public frame2() {
            PropertySet moduleMethod = new ModuleMethod(this, 4, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:635");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 3 ? lambda6() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 4 ? lambda7(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda6() {
            return frame1.lambda5recur(lists.cdr.apply1(this.lis));
        }

        Object lambda7(Object a, Object b, Object c) {
            return misc.values(lists.cons(lists.car.apply1(this.elt), a), lists.cons(lists.cadr.apply1(this.elt), b), lists.cons(lists.caddr.apply1(this.elt), c));
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame30 extends ModuleBody {
        Procedure pred;

        public Object lambda44lp(Object lists, Object n) {
            frame31 gnu_kawa_slib_srfi1_frame31 = new frame31();
            gnu_kawa_slib_srfi1_frame31.staticLink = this;
            gnu_kawa_slib_srfi1_frame31.lists = lists;
            gnu_kawa_slib_srfi1_frame31.f36n = n;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame31.lambda$Fn28, gnu_kawa_slib_srfi1_frame31.lambda$Fn29);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame31 extends ModuleBody {
        final ModuleMethod lambda$Fn28 = new ModuleMethod(this, 28, null, 0);
        final ModuleMethod lambda$Fn29;
        Object lists;
        Object f36n;
        frame30 staticLink;

        public frame31() {
            PropertySet moduleMethod = new ModuleMethod(this, 29, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1404");
            this.lambda$Fn29 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 28 ? lambda45() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 28) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 29 ? lambda46(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda45() {
            return srfi1.$PcCars$PlCdrs(this.lists);
        }

        Object lambda46(Object heads, Object tails) {
            boolean x = lists.isPair(heads);
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                if (Scheme.apply.apply2(this.staticLink.pred, heads) != Boolean.FALSE) {
                    return this.f36n;
                }
                return this.staticLink.lambda44lp(tails, AddOp.$Pl.apply2(this.f36n, srfi1.Lit1));
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 29) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame32 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn30;

        public frame32() {
            PropertySet moduleMethod = new ModuleMethod(this, 30, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1466");
            this.lambda$Fn30 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 30 ? lambda47(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda47(Object elt, Object ans) {
            return lists.member(elt, ans, this.$Eq) != Boolean.FALSE ? ans : lists.cons(elt, ans);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 30) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame33 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn31;
        final ModuleMethod lambda$Fn32;

        public frame33() {
            PropertySet moduleMethod = new ModuleMethod(this, 32, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1476");
            this.lambda$Fn32 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 33, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1471");
            this.lambda$Fn31 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 32:
                    return lambda49(obj, obj2);
                case 33:
                    return lambda48(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda48(Object lis, Object ans) {
            if (lists.isNull(lis)) {
                return ans;
            }
            if (lists.isNull(ans)) {
                return lis;
            }
            return lis != ans ? srfi1.fold$V(this.lambda$Fn32, ans, lis, new Object[0]) : ans;
        }

        Object lambda49(Object elt, Object ans) {
            frame34 gnu_kawa_slib_srfi1_frame34 = new frame34();
            gnu_kawa_slib_srfi1_frame34.staticLink = this;
            gnu_kawa_slib_srfi1_frame34.elt = elt;
            return srfi1.any$V(gnu_kawa_slib_srfi1_frame34.lambda$Fn33, ans, new Object[0]) != Boolean.FALSE ? ans : lists.cons(gnu_kawa_slib_srfi1_frame34.elt, ans);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 32:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 33:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }
    }

    /* compiled from: srfi1.scm */
    public class frame34 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn33;
        frame33 staticLink;

        public frame34() {
            PropertySet moduleMethod = new ModuleMethod(this, 31, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1476");
            this.lambda$Fn33 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 31 ? lambda50(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda50(Object x) {
            return this.staticLink.$Eq.apply2(x, this.elt);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 31) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame35 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn34;
        final ModuleMethod lambda$Fn35;

        public frame35() {
            PropertySet moduleMethod = new ModuleMethod(this, 35, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1488");
            this.lambda$Fn35 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 36, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1483");
            this.lambda$Fn34 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 35:
                    return lambda52(obj, obj2);
                case 36:
                    return lambda51(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda51(Object lis, Object ans) {
            if (lists.isNull(lis)) {
                return ans;
            }
            if (lists.isNull(ans)) {
                return lis;
            }
            return lis != ans ? srfi1.pairFold$V(this.lambda$Fn35, ans, lis, new Object[0]) : ans;
        }

        Object lambda52(Object pair, Object ans) {
            frame36 gnu_kawa_slib_srfi1_frame36 = new frame36();
            gnu_kawa_slib_srfi1_frame36.staticLink = this;
            gnu_kawa_slib_srfi1_frame36.elt = lists.car.apply1(pair);
            if (srfi1.any$V(gnu_kawa_slib_srfi1_frame36.lambda$Fn36, ans, new Object[0]) != Boolean.FALSE) {
                return ans;
            }
            try {
                lists.setCdr$Ex((Pair) pair, ans);
                return pair;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, pair);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 35:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 36:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }
    }

    /* compiled from: srfi1.scm */
    public class frame36 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn36;
        frame35 staticLink;

        public frame36() {
            PropertySet moduleMethod = new ModuleMethod(this, 34, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1490");
            this.lambda$Fn36 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 34 ? lambda53(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda53(Object x) {
            return this.staticLink.$Eq.apply2(x, this.elt);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 34) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame37 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn37;
        Object lists;

        public frame37() {
            PropertySet moduleMethod = new ModuleMethod(this, 38, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1501");
            this.lambda$Fn37 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 38 ? lambda54(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda54(Object x) {
            frame38 gnu_kawa_slib_srfi1_frame38 = new frame38();
            gnu_kawa_slib_srfi1_frame38.staticLink = this;
            gnu_kawa_slib_srfi1_frame38.f37x = x;
            return srfi1.every$V(gnu_kawa_slib_srfi1_frame38.lambda$Fn38, this.lists, new Object[0]);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 38) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame38 extends ModuleBody {
        final ModuleMethod lambda$Fn38;
        frame37 staticLink;
        Object f37x;

        public frame38() {
            PropertySet moduleMethod = new ModuleMethod(this, 37, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1502");
            this.lambda$Fn38 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 37 ? lambda55(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda55(Object lis) {
            return lists.member(this.f37x, lis, this.staticLink.$Eq);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 37) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame39 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn39;
        Object lists;

        public frame39() {
            PropertySet moduleMethod = new ModuleMethod(this, 40, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1509");
            this.lambda$Fn39 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 40 ? lambda56(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda56(Object x) {
            frame40 gnu_kawa_slib_srfi1_frame40 = new frame40();
            gnu_kawa_slib_srfi1_frame40.staticLink = this;
            gnu_kawa_slib_srfi1_frame40.f38x = x;
            return srfi1.every$V(gnu_kawa_slib_srfi1_frame40.lambda$Fn40, this.lists, new Object[0]);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 40) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame3 extends ModuleBody {
        public static Object lambda8recur(Object lis) {
            frame4 gnu_kawa_slib_srfi1_frame4 = new frame4();
            gnu_kawa_slib_srfi1_frame4.lis = lis;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame4.lis) != Boolean.FALSE) {
                return misc.values(gnu_kawa_slib_srfi1_frame4.lis, gnu_kawa_slib_srfi1_frame4.lis, gnu_kawa_slib_srfi1_frame4.lis, gnu_kawa_slib_srfi1_frame4.lis);
            }
            gnu_kawa_slib_srfi1_frame4.elt = lists.car.apply1(gnu_kawa_slib_srfi1_frame4.lis);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame4.lambda$Fn5, gnu_kawa_slib_srfi1_frame4.lambda$Fn6);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame40 extends ModuleBody {
        final ModuleMethod lambda$Fn40;
        frame39 staticLink;
        Object f38x;

        public frame40() {
            PropertySet moduleMethod = new ModuleMethod(this, 39, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1510");
            this.lambda$Fn40 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 39 ? lambda57(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda57(Object lis) {
            return lists.member(this.f38x, lis, this.staticLink.$Eq);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 39) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame41 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn41;
        Object lists;

        public frame41() {
            PropertySet moduleMethod = new ModuleMethod(this, 42, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1518");
            this.lambda$Fn41 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 42 ? lambda58(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda58(Object x) {
            frame42 gnu_kawa_slib_srfi1_frame42 = new frame42();
            gnu_kawa_slib_srfi1_frame42.staticLink = this;
            gnu_kawa_slib_srfi1_frame42.f39x = x;
            return srfi1.every$V(gnu_kawa_slib_srfi1_frame42.lambda$Fn42, this.lists, new Object[0]);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 42) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame42 extends ModuleBody {
        final ModuleMethod lambda$Fn42;
        frame41 staticLink;
        Object f39x;

        public frame42() {
            PropertySet moduleMethod = new ModuleMethod(this, 41, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1519");
            this.lambda$Fn42 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 41) {
                return lambda59(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda59(Object lis) {
            return ((lists.member(this.f39x, lis, this.staticLink.$Eq) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 41) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame43 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn43;
        Object lists;

        public frame43() {
            PropertySet moduleMethod = new ModuleMethod(this, 44, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1527");
            this.lambda$Fn43 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 44 ? lambda60(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda60(Object x) {
            frame44 gnu_kawa_slib_srfi1_frame44 = new frame44();
            gnu_kawa_slib_srfi1_frame44.staticLink = this;
            gnu_kawa_slib_srfi1_frame44.f40x = x;
            return srfi1.every$V(gnu_kawa_slib_srfi1_frame44.lambda$Fn44, this.lists, new Object[0]);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 44) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame44 extends ModuleBody {
        final ModuleMethod lambda$Fn44;
        frame43 staticLink;
        Object f40x;

        public frame44() {
            PropertySet moduleMethod = new ModuleMethod(this, 43, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1528");
            this.lambda$Fn44 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 43) {
                return lambda61(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda61(Object lis) {
            return ((lists.member(this.f40x, lis, this.staticLink.$Eq) != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 43) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame45 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn45;

        public frame45() {
            PropertySet moduleMethod = new ModuleMethod(this, 48, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1534");
            this.lambda$Fn45 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 48 ? lambda62(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda62(Object b, Object a) {
            frame46 gnu_kawa_slib_srfi1_frame46 = new frame46();
            gnu_kawa_slib_srfi1_frame46.staticLink = this;
            gnu_kawa_slib_srfi1_frame46.f42b = b;
            gnu_kawa_slib_srfi1_frame46.f41a = a;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame46.lambda$Fn46, gnu_kawa_slib_srfi1_frame46.lambda$Fn47);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 48) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame46 extends ModuleBody {
        Object f41a;
        Object f42b;
        final ModuleMethod lambda$Fn46 = new ModuleMethod(this, 46, null, 0);
        final ModuleMethod lambda$Fn47;
        frame45 staticLink;

        public frame46() {
            PropertySet moduleMethod = new ModuleMethod(this, 47, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1544");
            this.lambda$Fn47 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 46 ? lambda63() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 46) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 47 ? lambda64(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda63() {
            return srfi1.lsetDiff$PlIntersection$V(this.staticLink.$Eq, this.f41a, new Object[]{this.f42b});
        }

        Object lambda64(Object a$Mnb, Object aIntB) {
            frame47 gnu_kawa_slib_srfi1_frame47 = new frame47();
            gnu_kawa_slib_srfi1_frame47.staticLink = this;
            gnu_kawa_slib_srfi1_frame47.a$Mnint$Mnb = aIntB;
            if (lists.isNull(a$Mnb)) {
                return srfi1.lsetDifference$V(this.staticLink.$Eq, this.f42b, new Object[]{this.f41a});
            } else if (!lists.isNull(gnu_kawa_slib_srfi1_frame47.a$Mnint$Mnb)) {
                return srfi1.fold$V(gnu_kawa_slib_srfi1_frame47.lambda$Fn48, a$Mnb, this.f42b, new Object[0]);
            } else {
                return append.append$V(new Object[]{this.f42b, this.f41a});
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 47) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame47 extends ModuleBody {
        Object a$Mnint$Mnb;
        final ModuleMethod lambda$Fn48;
        frame46 staticLink;

        public frame47() {
            PropertySet moduleMethod = new ModuleMethod(this, 45, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1547");
            this.lambda$Fn48 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 45 ? lambda65(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda65(Object xb, Object ans) {
            return lists.member(xb, this.a$Mnint$Mnb, this.staticLink.staticLink.$Eq) != Boolean.FALSE ? ans : lists.cons(xb, ans);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 45) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame48 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn49;

        public frame48() {
            PropertySet moduleMethod = new ModuleMethod(this, 52, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1555");
            this.lambda$Fn49 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 52 ? lambda66(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda66(Object b, Object a) {
            frame49 gnu_kawa_slib_srfi1_frame49 = new frame49();
            gnu_kawa_slib_srfi1_frame49.staticLink = this;
            gnu_kawa_slib_srfi1_frame49.f44b = b;
            gnu_kawa_slib_srfi1_frame49.f43a = a;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame49.lambda$Fn50, gnu_kawa_slib_srfi1_frame49.lambda$Fn51);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 52) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame49 extends ModuleBody {
        Object f43a;
        Object f44b;
        final ModuleMethod lambda$Fn50 = new ModuleMethod(this, 50, null, 0);
        final ModuleMethod lambda$Fn51;
        frame48 staticLink;

        public frame49() {
            PropertySet moduleMethod = new ModuleMethod(this, 51, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1565");
            this.lambda$Fn51 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 50 ? lambda67() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 50) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 51 ? lambda68(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda67() {
            return srfi1.lsetDiff$PlIntersection$Ex$V(this.staticLink.$Eq, this.f43a, new Object[]{this.f44b});
        }

        Object lambda68(Object a$Mnb, Object aIntB) {
            frame50 gnu_kawa_slib_srfi1_frame50 = new frame50();
            gnu_kawa_slib_srfi1_frame50.staticLink = this;
            gnu_kawa_slib_srfi1_frame50.a$Mnint$Mnb = aIntB;
            if (lists.isNull(a$Mnb)) {
                return srfi1.lsetDifference$Ex$V(this.staticLink.$Eq, this.f44b, new Object[]{this.f43a});
            } else if (!lists.isNull(gnu_kawa_slib_srfi1_frame50.a$Mnint$Mnb)) {
                return srfi1.pairFold$V(gnu_kawa_slib_srfi1_frame50.lambda$Fn52, a$Mnb, this.f44b, new Object[0]);
            } else {
                return srfi1.append$Ex$V(new Object[]{this.f44b, this.f43a});
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 51) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame4 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 5, null, 0);
        final ModuleMethod lambda$Fn6;
        Object lis;

        public frame4() {
            PropertySet moduleMethod = new ModuleMethod(this, 6, null, 16388);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:644");
            this.lambda$Fn6 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 5 ? lambda9() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 6 ? lambda10(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        Object lambda10(Object a, Object b, Object c, Object d) {
            return misc.values(lists.cons(lists.car.apply1(this.elt), a), lists.cons(lists.cadr.apply1(this.elt), b), lists.cons(lists.caddr.apply1(this.elt), c), lists.cons(lists.cadddr.apply1(this.elt), d));
        }

        Object lambda9() {
            return frame3.lambda8recur(lists.cdr.apply1(this.lis));
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector != 6) {
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.value4 = obj4;
            callContext.proc = moduleMethod;
            callContext.pc = 4;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame50 extends ModuleBody {
        Object a$Mnint$Mnb;
        final ModuleMethod lambda$Fn52;
        frame49 staticLink;

        public frame50() {
            PropertySet moduleMethod = new ModuleMethod(this, 49, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1568");
            this.lambda$Fn52 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 49 ? lambda69(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda69(Object b$Mnpair, Object ans) {
            if (lists.member(lists.car.apply1(b$Mnpair), this.a$Mnint$Mnb, this.staticLink.staticLink.$Eq) != Boolean.FALSE) {
                return ans;
            }
            try {
                lists.setCdr$Ex((Pair) b$Mnpair, ans);
                return b$Mnpair;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, b$Mnpair);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 49) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame51 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn53;
        LList lists;

        public frame51() {
            PropertySet moduleMethod = new ModuleMethod(this, 54, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1579");
            this.lambda$Fn53 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 54) {
                return lambda70(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda70(Object elt) {
            int i = 0;
            frame52 gnu_kawa_slib_srfi1_frame52 = new frame52();
            gnu_kawa_slib_srfi1_frame52.staticLink = this;
            gnu_kawa_slib_srfi1_frame52.elt = elt;
            if (srfi1.any$V(gnu_kawa_slib_srfi1_frame52.lambda$Fn54, this.lists, new Object[0]) != Boolean.FALSE) {
                i = 1;
            }
            return (i + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 54) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame52 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn54;
        frame51 staticLink;

        public frame52() {
            PropertySet moduleMethod = new ModuleMethod(this, 53, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1580");
            this.lambda$Fn54 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 53 ? lambda71(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda71(Object lis) {
            return lists.member(this.elt, lis, this.staticLink.$Eq);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 53) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame53 extends ModuleBody {
        Procedure $Eq;
        final ModuleMethod lambda$Fn55;
        LList lists;

        public frame53() {
            PropertySet moduleMethod = new ModuleMethod(this, 56, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1587");
            this.lambda$Fn55 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 56) {
                return lambda72(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda72(Object elt) {
            int i = 0;
            frame54 gnu_kawa_slib_srfi1_frame54 = new frame54();
            gnu_kawa_slib_srfi1_frame54.staticLink = this;
            gnu_kawa_slib_srfi1_frame54.elt = elt;
            if (srfi1.any$V(gnu_kawa_slib_srfi1_frame54.lambda$Fn56, this.lists, new Object[0]) != Boolean.FALSE) {
                i = 1;
            }
            return (i + 1) & 1;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 56) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame54 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn56;
        frame53 staticLink;

        public frame54() {
            PropertySet moduleMethod = new ModuleMethod(this, 55, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1588");
            this.lambda$Fn56 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 55 ? lambda73(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda73(Object lis) {
            return lists.member(this.elt, lis, this.staticLink.$Eq);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 55) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame55 extends ModuleBody {
        Continuation abort;

        public Object lambda74recur(Object lists) {
            if (!lists.isPair(lists)) {
                return LList.Empty;
            }
            Object lis = lists.car.apply1(lists);
            if (srfi1.isNullList(lis) != Boolean.FALSE) {
                return this.abort.apply1(LList.Empty);
            }
            return lists.cons(lists.cdr.apply1(lis), lambda74recur(lists.cdr.apply1(lists)));
        }
    }

    /* compiled from: srfi1.scm */
    public class frame56 extends ModuleBody {
        Object last$Mnelt;

        public Object lambda75recur(Object lists) {
            return lists.isPair(lists) ? lists.cons(lists.caar.apply1(lists), lambda75recur(lists.cdr.apply1(lists))) : LList.list1(this.last$Mnelt);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame57 extends ModuleBody {
        Continuation abort;

        public Object lambda76recur(Object lists) {
            frame58 gnu_kawa_slib_srfi1_frame58 = new frame58();
            gnu_kawa_slib_srfi1_frame58.staticLink = this;
            gnu_kawa_slib_srfi1_frame58.lists = lists;
            if (lists.isPair(gnu_kawa_slib_srfi1_frame58.lists)) {
                return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame58.lambda$Fn57, gnu_kawa_slib_srfi1_frame58.lambda$Fn58);
            }
            return misc.values(LList.Empty, LList.Empty);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame58 extends ModuleBody {
        final ModuleMethod lambda$Fn57 = new ModuleMethod(this, 61, null, 0);
        final ModuleMethod lambda$Fn58;
        Object lists;
        frame57 staticLink;

        public frame58() {
            PropertySet moduleMethod = new ModuleMethod(this, 62, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:762");
            this.lambda$Fn58 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 61 ? lambda77() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 61) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 62 ? lambda78(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda77() {
            return srfi1.car$PlCdr(this.lists);
        }

        Object lambda78(Object list, Object otherLists) {
            frame59 gnu_kawa_slib_srfi1_frame59 = new frame59();
            gnu_kawa_slib_srfi1_frame59.staticLink = this;
            gnu_kawa_slib_srfi1_frame59.list = list;
            gnu_kawa_slib_srfi1_frame59.other$Mnlists = otherLists;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame59.list) != Boolean.FALSE) {
                return this.staticLink.abort.apply2(LList.Empty, LList.Empty);
            }
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame59.lambda$Fn59, gnu_kawa_slib_srfi1_frame59.lambda$Fn60);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 62) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame59 extends ModuleBody {
        final ModuleMethod lambda$Fn59 = new ModuleMethod(this, 59, null, 0);
        final ModuleMethod lambda$Fn60;
        Object list;
        Object other$Mnlists;
        frame58 staticLink;

        public frame59() {
            PropertySet moduleMethod = new ModuleMethod(this, 60, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:764");
            this.lambda$Fn60 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 59 ? lambda79() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 59) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 60 ? lambda80(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda79() {
            return srfi1.car$PlCdr(this.list);
        }

        Object lambda80(Object a, Object d) {
            frame60 gnu_kawa_slib_srfi1_frame60 = new frame60();
            gnu_kawa_slib_srfi1_frame60.staticLink = this;
            gnu_kawa_slib_srfi1_frame60.f45a = a;
            gnu_kawa_slib_srfi1_frame60.f46d = d;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame60.lambda$Fn61, gnu_kawa_slib_srfi1_frame60.lambda$Fn62);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 60) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame5 extends ModuleBody {
        public static Object lambda11recur(Object lis) {
            frame6 gnu_kawa_slib_srfi1_frame6 = new frame6();
            gnu_kawa_slib_srfi1_frame6.lis = lis;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame6.lis) != Boolean.FALSE) {
                return misc.values(gnu_kawa_slib_srfi1_frame6.lis, gnu_kawa_slib_srfi1_frame6.lis, gnu_kawa_slib_srfi1_frame6.lis, gnu_kawa_slib_srfi1_frame6.lis, gnu_kawa_slib_srfi1_frame6.lis);
            }
            gnu_kawa_slib_srfi1_frame6.elt = lists.car.apply1(gnu_kawa_slib_srfi1_frame6.lis);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame6.lambda$Fn7, gnu_kawa_slib_srfi1_frame6.lambda$Fn8);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame60 extends ModuleBody {
        Object f45a;
        Object f46d;
        final ModuleMethod lambda$Fn61 = new ModuleMethod(this, 57, null, 0);
        final ModuleMethod lambda$Fn62;
        frame59 staticLink;

        public frame60() {
            PropertySet moduleMethod = new ModuleMethod(this, 58, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:765");
            this.lambda$Fn62 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 57 ? lambda81() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 57) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 58 ? lambda82(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda81() {
            return this.staticLink.staticLink.staticLink.lambda76recur(this.staticLink.other$Mnlists);
        }

        Object lambda82(Object cars, Object cdrs) {
            return misc.values(lists.cons(this.f45a, cars), lists.cons(this.f46d, cdrs));
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 58) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame61 extends ModuleBody {
        final ModuleMethod lambda$Fn63 = new ModuleMethod(this, 63, null, 0);
        Object lists;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 63 ? lambda83() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 63) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        static Pair lambda84(Object obj, Object x) {
            return lists.cons(obj, x);
        }

        Object lambda83() {
            return srfi1.$PcCars$PlCdrs(this.lists);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame62 extends ModuleBody {
        Object cars$Mnfinal;
    }

    /* compiled from: srfi1.scm */
    public class frame63 extends ModuleBody {
        Continuation abort;
        frame62 staticLink;

        public Object lambda85recur(Object lists) {
            frame64 gnu_kawa_slib_srfi1_frame64 = new frame64();
            gnu_kawa_slib_srfi1_frame64.staticLink = this;
            gnu_kawa_slib_srfi1_frame64.lists = lists;
            if (lists.isPair(gnu_kawa_slib_srfi1_frame64.lists)) {
                return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame64.lambda$Fn65, gnu_kawa_slib_srfi1_frame64.lambda$Fn66);
            }
            return misc.values(LList.list1(this.staticLink.cars$Mnfinal), LList.Empty);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame64 extends ModuleBody {
        final ModuleMethod lambda$Fn65 = new ModuleMethod(this, 68, null, 0);
        final ModuleMethod lambda$Fn66;
        Object lists;
        frame63 staticLink;

        public frame64() {
            PropertySet moduleMethod = new ModuleMethod(this, 69, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:783");
            this.lambda$Fn66 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 68 ? lambda86() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 68) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 69 ? lambda87(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda86() {
            return srfi1.car$PlCdr(this.lists);
        }

        Object lambda87(Object list, Object otherLists) {
            frame65 gnu_kawa_slib_srfi1_frame65 = new frame65();
            gnu_kawa_slib_srfi1_frame65.staticLink = this;
            gnu_kawa_slib_srfi1_frame65.list = list;
            gnu_kawa_slib_srfi1_frame65.other$Mnlists = otherLists;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame65.list) != Boolean.FALSE) {
                return this.staticLink.abort.apply2(LList.Empty, LList.Empty);
            }
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame65.lambda$Fn67, gnu_kawa_slib_srfi1_frame65.lambda$Fn68);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 69) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame65 extends ModuleBody {
        final ModuleMethod lambda$Fn67 = new ModuleMethod(this, 66, null, 0);
        final ModuleMethod lambda$Fn68;
        Object list;
        Object other$Mnlists;
        frame64 staticLink;

        public frame65() {
            PropertySet moduleMethod = new ModuleMethod(this, 67, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:785");
            this.lambda$Fn68 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 66 ? lambda88() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 66) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 67 ? lambda89(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda88() {
            return srfi1.car$PlCdr(this.list);
        }

        Object lambda89(Object a, Object d) {
            frame66 gnu_kawa_slib_srfi1_frame66 = new frame66();
            gnu_kawa_slib_srfi1_frame66.staticLink = this;
            gnu_kawa_slib_srfi1_frame66.f47a = a;
            gnu_kawa_slib_srfi1_frame66.f48d = d;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame66.lambda$Fn69, gnu_kawa_slib_srfi1_frame66.lambda$Fn70);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 67) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame66 extends ModuleBody {
        Object f47a;
        Object f48d;
        final ModuleMethod lambda$Fn69 = new ModuleMethod(this, 64, null, 0);
        final ModuleMethod lambda$Fn70;
        frame65 staticLink;

        public frame66() {
            PropertySet moduleMethod = new ModuleMethod(this, 65, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:786");
            this.lambda$Fn70 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 64 ? lambda90() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 64) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 65 ? lambda91(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda90() {
            return this.staticLink.staticLink.staticLink.lambda85recur(this.staticLink.other$Mnlists);
        }

        Object lambda91(Object cars, Object cdrs) {
            return misc.values(lists.cons(this.f47a, cars), lists.cons(this.f48d, cdrs));
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 65) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame67 extends ModuleBody {
        public static Object lambda92recur(Object lists) {
            frame68 gnu_kawa_slib_srfi1_frame68 = new frame68();
            gnu_kawa_slib_srfi1_frame68.lists = lists;
            if (lists.isPair(gnu_kawa_slib_srfi1_frame68.lists)) {
                return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame68.lambda$Fn71, gnu_kawa_slib_srfi1_frame68.lambda$Fn72);
            }
            return misc.values(LList.Empty, LList.Empty);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame68 extends ModuleBody {
        final ModuleMethod lambda$Fn71 = new ModuleMethod(this, 74, null, 0);
        final ModuleMethod lambda$Fn72;
        Object lists;

        public frame68() {
            PropertySet moduleMethod = new ModuleMethod(this, 75, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:794");
            this.lambda$Fn72 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 74 ? lambda93() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 74) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 75 ? lambda94(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda93() {
            return srfi1.car$PlCdr(this.lists);
        }

        Object lambda94(Object list, Object otherLists) {
            frame69 gnu_kawa_slib_srfi1_frame69 = new frame69();
            gnu_kawa_slib_srfi1_frame69.staticLink = this;
            gnu_kawa_slib_srfi1_frame69.list = list;
            gnu_kawa_slib_srfi1_frame69.other$Mnlists = otherLists;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame69.lambda$Fn73, gnu_kawa_slib_srfi1_frame69.lambda$Fn74);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 75) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame69 extends ModuleBody {
        final ModuleMethod lambda$Fn73 = new ModuleMethod(this, 72, null, 0);
        final ModuleMethod lambda$Fn74;
        Object list;
        Object other$Mnlists;
        frame68 staticLink;

        public frame69() {
            PropertySet moduleMethod = new ModuleMethod(this, 73, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:795");
            this.lambda$Fn74 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 72 ? lambda95() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 72) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 73 ? lambda96(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda95() {
            return srfi1.car$PlCdr(this.list);
        }

        Object lambda96(Object a, Object d) {
            frame70 gnu_kawa_slib_srfi1_frame70 = new frame70();
            gnu_kawa_slib_srfi1_frame70.staticLink = this;
            gnu_kawa_slib_srfi1_frame70.f49a = a;
            gnu_kawa_slib_srfi1_frame70.f50d = d;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame70.lambda$Fn75, gnu_kawa_slib_srfi1_frame70.lambda$Fn76);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 73) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame6 extends ModuleBody {
        Object elt;
        final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 7, null, 0);
        final ModuleMethod lambda$Fn8;
        Object lis;

        public frame6() {
            PropertySet moduleMethod = new ModuleMethod(this, 8, null, 20485);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:654");
            this.lambda$Fn8 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 7 ? lambda12() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 7) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            return moduleMethod.selector == 8 ? lambda13(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]) : super.applyN(moduleMethod, objArr);
        }

        Object lambda12() {
            return frame5.lambda11recur(lists.cdr.apply1(this.lis));
        }

        Object lambda13(Object a, Object b, Object c, Object d, Object e) {
            return misc.values(lists.cons(lists.car.apply1(this.elt), a), lists.cons(lists.cadr.apply1(this.elt), b), lists.cons(lists.caddr.apply1(this.elt), c), lists.cons(lists.cadddr.apply1(this.elt), d), lists.cons(lists.car.apply1(lists.cddddr.apply1(this.elt)), e));
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame70 extends ModuleBody {
        Object f49a;
        Object f50d;
        final ModuleMethod lambda$Fn75 = new ModuleMethod(this, 70, null, 0);
        final ModuleMethod lambda$Fn76;
        frame69 staticLink;

        public frame70() {
            PropertySet moduleMethod = new ModuleMethod(this, 71, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:796");
            this.lambda$Fn76 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 70 ? lambda97() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 70) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 71 ? lambda98(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda97() {
            return frame67.lambda92recur(this.staticLink.other$Mnlists);
        }

        Object lambda98(Object cars, Object cdrs) {
            return misc.values(lists.cons(this.f49a, cars), lists.cons(this.f50d, cdrs));
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 71) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame71 extends ModuleBody {
        final ModuleMethod lambda$Fn77 = new ModuleMethod(this, 76, null, 0);
        Object lists;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 76 ? lambda99() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 76) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        static Pair lambda100(Object obj, Object x) {
            return lists.cons(obj, x);
        }

        Object lambda99() {
            return srfi1.$PcCars$PlCdrs$SlNoTest(this.lists);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame72 extends ModuleBody {
        Object $Eq;
        final ModuleMethod lambda$Fn79;
        Object lis2;

        public frame72() {
            PropertySet moduleMethod = new ModuleMethod(this, 77, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:1443");
            this.lambda$Fn79 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 77 ? lambda101(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda101(Object x) {
            Object obj = this.lis2;
            Object obj2 = this.$Eq;
            try {
                return lists.member(x, obj, (Procedure) obj2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "member", 3, obj2);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 77) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame7 extends ModuleBody {
        Procedure kons;

        public Object lambda14lp(Object lists, Object ans) {
            frame8 gnu_kawa_slib_srfi1_frame8 = new frame8();
            gnu_kawa_slib_srfi1_frame8.staticLink = this;
            gnu_kawa_slib_srfi1_frame8.lists = lists;
            gnu_kawa_slib_srfi1_frame8.ans = ans;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame8.lambda$Fn9, gnu_kawa_slib_srfi1_frame8.lambda$Fn10);
        }
    }

    /* compiled from: srfi1.scm */
    public class frame8 extends ModuleBody {
        Object ans;
        final ModuleMethod lambda$Fn10;
        final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 9, null, 0);
        Object lists;
        frame7 staticLink;

        public frame8() {
            PropertySet moduleMethod = new ModuleMethod(this, 10, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm:859");
            this.lambda$Fn10 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 9 ? lambda15() : super.apply0(moduleMethod);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 10 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda15() {
            return srfi1.$PcCars$PlCdrs$Pl(this.lists, this.ans);
        }

        Object lambda16(Object cars$Plans, Object cdrs) {
            if (lists.isNull(cars$Plans)) {
                return this.ans;
            }
            return this.staticLink.lambda14lp(cdrs, Scheme.apply.apply2(this.staticLink.kons, cars$Plans));
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: srfi1.scm */
    public class frame9 extends ModuleBody {
        Object knil;
        Procedure kons;

        public Object lambda17recur(Object lists) {
            Object cdrs = srfi1.$PcCdrs(lists);
            if (lists.isNull(cdrs)) {
                return this.knil;
            }
            return Scheme.apply.apply2(this.kons, srfi1.$PcCars$Pl(lists, lambda17recur(cdrs)));
        }

        public Object lambda18recur(Object lis) {
            if (srfi1.isNullList(lis) != Boolean.FALSE) {
                return this.knil;
            }
            return this.kons.apply2(lists.car.apply1(lis), lambda18recur(lists.cdr.apply1(lis)));
        }
    }

    /* compiled from: srfi1.scm */
    public class frame extends ModuleBody {
        public static Object lambda2recur(Object lis) {
            frame0 gnu_kawa_slib_srfi1_frame0 = new frame0();
            gnu_kawa_slib_srfi1_frame0.lis = lis;
            if (srfi1.isNullList(gnu_kawa_slib_srfi1_frame0.lis) != Boolean.FALSE) {
                return misc.values(gnu_kawa_slib_srfi1_frame0.lis, gnu_kawa_slib_srfi1_frame0.lis);
            }
            gnu_kawa_slib_srfi1_frame0.elt = lists.car.apply1(gnu_kawa_slib_srfi1_frame0.lis);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame0.lambda$Fn1, gnu_kawa_slib_srfi1_frame0.lambda$Fn2);
        }
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("%every").readResolve();
        Lit84 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        r4 = new Object[10];
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("null-list?").readResolve();
        Lit14 = simpleSymbol2;
        r4[7] = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(Lit100, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722136), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722124);
        r4[8] = PairWithPosition.make(Lit101, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722148);
        r4[9] = PairWithPosition.make(PairWithPosition.make(Lit102, PairWithPosition.make(PairWithPosition.make(Lit103, PairWithPosition.make(Lit100, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722163), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722158), PairWithPosition.make(PairWithPosition.make(Lit104, PairWithPosition.make(Lit100, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722174), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722169), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722169), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722158), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722154), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi1.scm", 5722154);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f¡I\u0011\u0018\u0014\b\u0011\u0018\u001c\b\u000b\b\u0011\u0018$\b\u0011\u0018,\b\u000b\b\u0011\u00184\u0011\u0018<!\t\u0003\u0018D\u0018L", r4, 0);
        Lit85 = new SyntaxRules(objArr, syntaxRuleArr, 2);
        ModuleBody moduleBody = $instance;
        xcons = new ModuleMethod(moduleBody, 78, Lit3, 8194);
        make$Mnlist = new ModuleMethod(moduleBody, 79, Lit4, -4095);
        list$Mntabulate = new ModuleMethod(moduleBody, 80, Lit5, 8194);
        cons$St = new ModuleMethod(moduleBody, 81, Lit6, -4096);
        list$Mncopy = new ModuleMethod(moduleBody, 82, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        iota = new ModuleMethod(moduleBody, 83, Lit8, 12289);
        circular$Mnlist = new ModuleMethod(moduleBody, 86, Lit9, -4095);
        proper$Mnlist$Qu = new ModuleMethod(moduleBody, 87, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        dotted$Mnlist$Qu = new ModuleMethod(moduleBody, 88, Lit11, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        circular$Mnlist$Qu = new ModuleMethod(moduleBody, 89, Lit12, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        not$Mnpair$Qu = new ModuleMethod(moduleBody, 90, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        null$Mnlist$Qu = new ModuleMethod(moduleBody, 91, Lit14, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Eq = new ModuleMethod(moduleBody, 92, Lit15, -4095);
        length$Pl = new ModuleMethod(moduleBody, 93, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        zip = new ModuleMethod(moduleBody, 94, Lit17, -4095);
        fifth = new ModuleMethod(moduleBody, 95, Lit18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        sixth = new ModuleMethod(moduleBody, 96, Lit19, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        seventh = new ModuleMethod(moduleBody, 97, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        eighth = new ModuleMethod(moduleBody, 98, Lit21, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ninth = new ModuleMethod(moduleBody, 99, Lit22, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tenth = new ModuleMethod(moduleBody, 100, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        car$Plcdr = new ModuleMethod(moduleBody, 101, Lit24, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        take = new ModuleMethod(moduleBody, 102, Lit25, 8194);
        drop = new ModuleMethod(moduleBody, 103, Lit26, 8194);
        take$Ex = new ModuleMethod(moduleBody, 104, Lit27, 8194);
        take$Mnright = new ModuleMethod(moduleBody, 105, Lit28, 8194);
        drop$Mnright = new ModuleMethod(moduleBody, 106, Lit29, 8194);
        drop$Mnright$Ex = new ModuleMethod(moduleBody, 107, Lit30, 8194);
        split$Mnat = new ModuleMethod(moduleBody, 108, Lit31, 8194);
        split$Mnat$Ex = new ModuleMethod(moduleBody, 109, Lit32, 8194);
        last = new ModuleMethod(moduleBody, 110, Lit33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        last$Mnpair = new ModuleMethod(moduleBody, 111, Lit34, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        unzip1 = new ModuleMethod(moduleBody, DateTime.TIME_MASK, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        unzip2 = new ModuleMethod(moduleBody, 113, Lit36, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        unzip3 = new ModuleMethod(moduleBody, 114, Lit37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        unzip4 = new ModuleMethod(moduleBody, 115, Lit38, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        unzip5 = new ModuleMethod(moduleBody, 116, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        append$Ex = new ModuleMethod(moduleBody, 117, Lit40, -4096);
        append$Mnreverse = new ModuleMethod(moduleBody, 118, Lit41, 8194);
        append$Mnreverse$Ex = new ModuleMethod(moduleBody, 119, Lit42, 8194);
        concatenate = new ModuleMethod(moduleBody, 120, Lit43, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        concatenate$Ex = new ModuleMethod(moduleBody, 121, Lit44, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        count = new ModuleMethod(moduleBody, 122, Lit45, -4094);
        unfold$Mnright = new ModuleMethod(moduleBody, 123, Lit46, 20484);
        unfold = new ModuleMethod(moduleBody, 125, Lit47, -4092);
        fold = new ModuleMethod(moduleBody, TransportMediator.KEYCODE_MEDIA_PLAY, Lit48, -4093);
        fold$Mnright = new ModuleMethod(moduleBody, TransportMediator.KEYCODE_MEDIA_PAUSE, Lit49, -4093);
        pair$Mnfold$Mnright = new ModuleMethod(moduleBody, 128, Lit50, -4093);
        pair$Mnfold = new ModuleMethod(moduleBody, 129, Lit51, -4093);
        reduce = new ModuleMethod(moduleBody, TransportMediator.KEYCODE_MEDIA_RECORD, Lit52, 12291);
        reduce$Mnright = new ModuleMethod(moduleBody, 131, Lit53, 12291);
        append$Mnmap = new ModuleMethod(moduleBody, 132, Lit54, -4094);
        append$Mnmap$Ex = new ModuleMethod(moduleBody, 133, Lit55, -4094);
        pair$Mnfor$Mneach = new ModuleMethod(moduleBody, 134, Lit56, -4094);
        map$Ex = new ModuleMethod(moduleBody, 135, Lit57, -4094);
        filter$Mnmap = new ModuleMethod(moduleBody, 136, Lit58, -4094);
        filter = new ModuleMethod(moduleBody, 137, Lit59, 8194);
        filter$Ex = new ModuleMethod(moduleBody, 138, Lit60, 8194);
        partition = new ModuleMethod(moduleBody, 139, Lit61, 8194);
        partition$Ex = new ModuleMethod(moduleBody, 140, Lit62, 8194);
        remove = new ModuleMethod(moduleBody, 141, Lit63, 8194);
        remove$Ex = new ModuleMethod(moduleBody, 142, Lit64, 8194);
        delete = new ModuleMethod(moduleBody, 143, Lit65, 12290);
        delete$Ex = new ModuleMethod(moduleBody, 145, Lit66, 12290);
        delete$Mnduplicates = new ModuleMethod(moduleBody, 147, Lit67, 8193);
        delete$Mnduplicates$Ex = new ModuleMethod(moduleBody, 149, Lit68, 8193);
        alist$Mncons = new ModuleMethod(moduleBody, 151, Lit69, 12291);
        alist$Mncopy = new ModuleMethod(moduleBody, 152, Lit70, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        alist$Mndelete = new ModuleMethod(moduleBody, 153, Lit71, 12290);
        alist$Mndelete$Ex = new ModuleMethod(moduleBody, 155, Lit72, 12290);
        find = new ModuleMethod(moduleBody, 157, Lit73, 8194);
        find$Mntail = new ModuleMethod(moduleBody, 158, Lit74, 8194);
        take$Mnwhile = new ModuleMethod(moduleBody, 159, Lit75, 8194);
        drop$Mnwhile = new ModuleMethod(moduleBody, ComponentConstants.TEXTBOX_PREFERRED_WIDTH, Lit76, 8194);
        take$Mnwhile$Ex = new ModuleMethod(moduleBody, 161, Lit77, 8194);
        span = new ModuleMethod(moduleBody, 162, Lit78, 8194);
        span$Ex = new ModuleMethod(moduleBody, 163, Lit79, 8194);
        f95break = new ModuleMethod(moduleBody, 164, Lit80, 8194);
        break$Ex = new ModuleMethod(moduleBody, 165, Lit81, 8194);
        any = new ModuleMethod(moduleBody, 166, Lit82, -4094);
        every = new ModuleMethod(moduleBody, YaVersion.YOUNG_ANDROID_VERSION, Lit83, -4094);
        list$Mnindex = new ModuleMethod(moduleBody, 168, Lit86, -4094);
        lset$Ls$Eq = new ModuleMethod(moduleBody, 169, Lit87, -4095);
        lset$Eq = new ModuleMethod(moduleBody, 170, Lit88, -4095);
        lset$Mnadjoin = new ModuleMethod(moduleBody, 171, Lit89, -4094);
        lset$Mnunion = new ModuleMethod(moduleBody, 172, Lit90, -4095);
        lset$Mnunion$Ex = new ModuleMethod(moduleBody, 173, Lit91, -4095);
        lset$Mnintersection = new ModuleMethod(moduleBody, 174, Lit92, -4094);
        lset$Mnintersection$Ex = new ModuleMethod(moduleBody, 175, Lit93, -4094);
        lset$Mndifference = new ModuleMethod(moduleBody, 176, Lit94, -4094);
        lset$Mndifference$Ex = new ModuleMethod(moduleBody, 177, Lit95, -4094);
        lset$Mnxor = new ModuleMethod(moduleBody, 178, Lit96, -4095);
        lset$Mnxor$Ex = new ModuleMethod(moduleBody, 179, Lit97, -4095);
        lset$Mndiff$Plintersection = new ModuleMethod(moduleBody, 180, Lit98, -4094);
        lset$Mndiff$Plintersection$Ex = new ModuleMethod(moduleBody, 181, Lit99, -4094);
        lambda$Fn64 = new ModuleMethod(moduleBody, 182, null, 8194);
        lambda$Fn78 = new ModuleMethod(moduleBody, 183, null, 8194);
        $instance.run();
    }

    public srfi1() {
        ModuleInfo.register(this);
    }

    public static Object alistDelete(Object obj, Object obj2) {
        return alistDelete(obj, obj2, Scheme.isEqual);
    }

    public static Object alistDelete$Ex(Object obj, Object obj2) {
        return alistDelete$Ex(obj, obj2, Scheme.isEqual);
    }

    public static Object delete(Object obj, Object obj2) {
        return delete(obj, obj2, Scheme.isEqual);
    }

    public static Object delete$Ex(Object obj, Object obj2) {
        return delete$Ex(obj, obj2, Scheme.isEqual);
    }

    public static Object deleteDuplicates(Object obj) {
        return deleteDuplicates(obj, Scheme.isEqual);
    }

    public static Object deleteDuplicates$Ex(Object obj) {
        return deleteDuplicates$Ex(obj, Scheme.isEqual);
    }

    public static Object iota(IntNum intNum) {
        return iota(intNum, Lit0, Lit1);
    }

    public static Object iota(IntNum intNum, Numeric numeric) {
        return iota(intNum, numeric, Lit1);
    }

    public static Object unfoldRight(Procedure procedure, Procedure procedure2, Procedure procedure3, Object obj) {
        return unfoldRight(procedure, procedure2, procedure3, obj, LList.Empty);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        first = lists.car;
        second = lists.cadr;
        third = lists.caddr;
        fourth = lists.cadddr;
        map$Mnin$Mnorder = Scheme.map;
    }

    public static Pair xcons(Object d, Object a) {
        return lists.cons(a, d);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 78:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 80:
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 83:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (Numeric.asNumericOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 102:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 103:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 104:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 105:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 106:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 107:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 108:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 109:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 118:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 119:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 137:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 138:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 139:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 140:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 141:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 142:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 143:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 145:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 147:
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 149:
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 153:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 155:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 157:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 158:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 159:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ComponentConstants.TEXTBOX_PREFERRED_WIDTH /*160*/:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 161:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 162:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 163:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 164:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 165:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 182:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 183:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object makeList$V(java.lang.Object r9, java.lang.Object[] r10) {
        /*
        r8 = 0;
        r3 = gnu.lists.LList.makeList(r10, r8);
        r5 = kawa.lib.numbers.isInteger(r9);
        r5 = r5 + 1;
        r4 = r5 & 1;
        if (r4 == 0) goto L_0x003c;
    L_0x000f:
        if (r4 == 0) goto L_0x0018;
    L_0x0011:
        r5 = "make-list arg#1 must be a non-negative integer";
        r6 = new java.lang.Object[r8];
        kawa.lib.misc.error$V(r5, r6);
    L_0x0018:
        r5 = kawa.lib.lists.isNull(r3);
        if (r5 == 0) goto L_0x0049;
    L_0x001e:
        r1 = java.lang.Boolean.FALSE;
    L_0x0020:
        r0 = gnu.lists.LList.Empty;
        r2 = r9;
    L_0x0023:
        r5 = kawa.standard.Scheme.numLEq;
        r6 = Lit0;
        r5 = r5.apply2(r2, r6);
        r6 = java.lang.Boolean.FALSE;
        if (r5 != r6) goto L_0x006c;
    L_0x002f:
        r5 = gnu.kawa.functions.AddOp.$Mn;
        r6 = Lit1;
        r2 = r5.apply2(r2, r6);
        r0 = kawa.lib.lists.cons(r1, r0);
        goto L_0x0023;
    L_0x003c:
        r5 = kawa.standard.Scheme.numLss;
        r6 = Lit0;
        r5 = r5.apply2(r9, r6);
        r6 = java.lang.Boolean.FALSE;
        if (r5 == r6) goto L_0x0018;
    L_0x0048:
        goto L_0x0011;
    L_0x0049:
        r5 = kawa.lib.lists.cdr;
        r5 = r5.apply1(r3);
        r5 = kawa.lib.lists.isNull(r5);
        if (r5 == 0) goto L_0x005c;
    L_0x0055:
        r5 = kawa.lib.lists.car;
        r1 = r5.apply1(r3);
        goto L_0x0020;
    L_0x005c:
        r5 = "Too many arguments to MAKE-LIST";
        r6 = 1;
        r6 = new java.lang.Object[r6];
        r7 = kawa.lib.lists.cons(r9, r3);
        r6[r8] = r7;
        r1 = kawa.lib.misc.error$V(r5, r6);
        goto L_0x0020;
    L_0x006c:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi1.makeList$V(java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 79:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 81:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 86:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 92:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 94:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 117:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 122:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 123:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 125:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case TransportMediator.KEYCODE_MEDIA_PAUSE /*127*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 128:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 129:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 132:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 133:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 134:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 135:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 136:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 166:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case YaVersion.YOUNG_ANDROID_VERSION /*167*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 168:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 169:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 170:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 171:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 172:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 173:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 174:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 175:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 176:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 177:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 178:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 179:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 180:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 181:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object listTabulate(java.lang.Object r6, gnu.mapping.Procedure r7) {
        /*
        r4 = kawa.lib.numbers.isInteger(r6);
        r4 = r4 + 1;
        r3 = r4 & 1;
        if (r3 == 0) goto L_0x003c;
    L_0x000a:
        if (r3 == 0) goto L_0x0014;
    L_0x000c:
        r4 = "list-tabulate arg#1 must be a non-negative integer";
        r5 = 0;
        r5 = new java.lang.Object[r5];
        kawa.lib.misc.error$V(r4, r5);
    L_0x0014:
        r4 = gnu.kawa.functions.AddOp.$Mn;
        r5 = Lit1;
        r1 = r4.apply2(r6, r5);
        r0 = gnu.lists.LList.Empty;
    L_0x001e:
        r4 = kawa.standard.Scheme.numLss;
        r5 = Lit0;
        r4 = r4.apply2(r1, r5);
        r5 = java.lang.Boolean.FALSE;
        if (r4 != r5) goto L_0x0049;
    L_0x002a:
        r4 = gnu.kawa.functions.AddOp.$Mn;
        r5 = Lit1;
        r2 = r4.apply2(r1, r5);
        r4 = r7.apply1(r1);
        r0 = kawa.lib.lists.cons(r4, r0);
        r1 = r2;
        goto L_0x001e;
    L_0x003c:
        r4 = kawa.standard.Scheme.numLss;
        r5 = Lit0;
        r4 = r4.apply2(r6, r5);
        r5 = java.lang.Boolean.FALSE;
        if (r4 == r5) goto L_0x0014;
    L_0x0048:
        goto L_0x000c;
    L_0x0049:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi1.listTabulate(java.lang.Object, gnu.mapping.Procedure):java.lang.Object");
    }

    public static Object cons$St(Object... args) {
        return LList.consX(args);
    }

    public static LList listCopy(LList lis) {
        LList result = LList.Empty;
        Object prev = LList.Empty;
        while (lists.isPair(lis)) {
            LList p = lists.cons(lists.car.apply1(lis), LList.Empty);
            if (prev == LList.Empty) {
                result = p;
            } else {
                try {
                    lists.setCdr$Ex((Pair) prev, p);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, prev);
                }
            }
            LList prev2 = p;
            lis = (LList) lists.cdr.apply1(lis);
        }
        return result;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 82:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 83:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 87:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 88:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 89:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 90:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 91:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 93:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 95:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 96:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 97:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 98:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 99:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 100:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 101:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 110:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 111:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case DateTime.TIME_MASK /*112*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 113:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 114:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 115:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 116:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 120:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 121:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 147:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 149:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 152:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object iota(IntNum count, Numeric start, Numeric step) {
        if (IntNum.compare(count, 0) < 0) {
            misc.error$V("Negative step count", new Object[]{iota, count});
        }
        Object apply2 = AddOp.$Pl.apply2(start, MultiplyOp.$St.apply2(IntNum.add(count, -1), step));
        try {
            apply2 = (Numeric) apply2;
            Object obj = LList.Empty;
            while (Scheme.numLEq.apply2(count, Lit0) == Boolean.FALSE) {
                count = AddOp.$Mn.apply2(count, Lit1);
                Object val = AddOp.$Mn.apply2(apply2, step);
                obj = lists.cons(apply2, obj);
                apply2 = val;
            }
            return obj;
        } catch (ClassCastException e) {
            throw new WrongType(e, "last-val", -2, apply2);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 83:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (Numeric.asNumericOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                if (Numeric.asNumericOrNull(obj3) == null) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 131:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 143:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 145:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 151:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 153:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 155:
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

    public static Pair circularList$V(Object val1, Object[] argsArray) {
        Pair ans = lists.cons(val1, LList.makeList(argsArray, 0));
        Object lastPair = lastPair(ans);
        try {
            lists.setCdr$Ex((Pair) lastPair, ans);
            return ans;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-cdr!", 1, lastPair);
        }
    }

    public static Object isProperList(Object x) {
        Object lag = x;
        while (lists.isPair(x)) {
            x = lists.cdr.apply1(x);
            if (!lists.isPair(x)) {
                return lists.isNull(x) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                x = lists.cdr.apply1(x);
                lag = lists.cdr.apply1(lag);
                boolean x2 = ((x == lag ? 1 : 0) + 1) & 1;
                if (!x2) {
                    return x2 ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return lists.isNull(x) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object isDottedList(Object x) {
        Object lag = x;
        while (lists.isPair(x)) {
            x = lists.cdr.apply1(x);
            if (!lists.isPair(x)) {
                return lists.isNull(x) ? Boolean.FALSE : Boolean.TRUE;
            } else {
                x = lists.cdr.apply1(x);
                lag = lists.cdr.apply1(lag);
                boolean x2 = ((x == lag ? 1 : 0) + 1) & 1;
                if (!x2) {
                    return x2 ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return lists.isNull(x) ? Boolean.FALSE : Boolean.TRUE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object isCircularList(java.lang.Object r3) {
        /*
        r0 = r3;
    L_0x0001:
        r1 = kawa.lib.lists.isPair(r3);
        if (r1 == 0) goto L_0x0037;
    L_0x0007:
        r2 = kawa.lib.lists.cdr;
        r3 = r2.apply1(r3);
        r1 = kawa.lib.lists.isPair(r3);
        if (r1 == 0) goto L_0x002f;
    L_0x0013:
        r2 = kawa.lib.lists.cdr;
        r3 = r2.apply1(r3);
        r2 = kawa.lib.lists.cdr;
        r0 = r2.apply1(r0);
        if (r3 != r0) goto L_0x0029;
    L_0x0021:
        r1 = 1;
    L_0x0022:
        if (r1 == 0) goto L_0x002e;
    L_0x0024:
        if (r1 == 0) goto L_0x002b;
    L_0x0026:
        r2 = java.lang.Boolean.TRUE;
    L_0x0028:
        return r2;
    L_0x0029:
        r1 = 0;
        goto L_0x0022;
    L_0x002b:
        r2 = java.lang.Boolean.FALSE;
        goto L_0x0028;
    L_0x002e:
        goto L_0x0001;
    L_0x002f:
        if (r1 == 0) goto L_0x0034;
    L_0x0031:
        r2 = java.lang.Boolean.TRUE;
        goto L_0x0028;
    L_0x0034:
        r2 = java.lang.Boolean.FALSE;
        goto L_0x0028;
    L_0x0037:
        if (r1 == 0) goto L_0x003c;
    L_0x0039:
        r2 = java.lang.Boolean.TRUE;
        goto L_0x0028;
    L_0x003c:
        r2 = java.lang.Boolean.FALSE;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi1.isCircularList(java.lang.Object):java.lang.Object");
    }

    public static boolean isNotPair(Object x) {
        return (lists.isPair(x) + 1) & 1;
    }

    public static Object isNullList(Object l) {
        if (l instanceof Pair) {
            return Boolean.FALSE;
        }
        if (l == LList.Empty) {
            return Boolean.TRUE;
        }
        return misc.error$V("null-list?: argument out of domain", new Object[]{l});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object list$Eq$V(java.lang.Object r11, java.lang.Object[] r12) {
        /*
        r7 = 0;
        r3 = gnu.lists.LList.makeList(r12, r7);
        r5 = kawa.lib.lists.isNull(r3);
        if (r5 == 0) goto L_0x0013;
    L_0x000b:
        if (r5 == 0) goto L_0x0010;
    L_0x000d:
        r6 = java.lang.Boolean.TRUE;
    L_0x000f:
        return r6;
    L_0x0010:
        r6 = java.lang.Boolean.FALSE;
        goto L_0x000f;
    L_0x0013:
        r6 = kawa.lib.lists.car;
        r0 = r6.apply1(r3);
        r6 = kawa.lib.lists.cdr;
        r4 = r6.apply1(r3);
    L_0x001f:
        r5 = kawa.lib.lists.isNull(r4);
        if (r5 == 0) goto L_0x002d;
    L_0x0025:
        if (r5 == 0) goto L_0x002a;
    L_0x0027:
        r6 = java.lang.Boolean.TRUE;
        goto L_0x000f;
    L_0x002a:
        r6 = java.lang.Boolean.FALSE;
        goto L_0x000f;
    L_0x002d:
        r6 = kawa.lib.lists.car;
        r2 = r6.apply1(r4);
        r6 = kawa.lib.lists.cdr;
        r4 = r6.apply1(r4);
        if (r0 != r2) goto L_0x003d;
    L_0x003b:
        r0 = r2;
        goto L_0x001f;
    L_0x003d:
        r1 = r2;
    L_0x003e:
        r6 = isNullList(r0);
        r8 = java.lang.Boolean.FALSE;
        if (r6 == r8) goto L_0x0052;
    L_0x0046:
        r5 = isNullList(r1);
        r6 = java.lang.Boolean.FALSE;
        if (r5 == r6) goto L_0x0050;
    L_0x004e:
        r0 = r1;
        goto L_0x001f;
    L_0x0050:
        r6 = r5;
        goto L_0x000f;
    L_0x0052:
        r6 = isNullList(r1);
        r8 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x0092 }
        if (r6 == r8) goto L_0x0085;
    L_0x005a:
        r6 = 1;
    L_0x005b:
        r6 = r6 + 1;
        r5 = r6 & 1;
        if (r5 == 0) goto L_0x0089;
    L_0x0061:
        r6 = kawa.standard.Scheme.applyToArgs;
        r8 = kawa.lib.lists.car;
        r8 = r8.apply1(r0);
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r1);
        r5 = r6.apply3(r11, r8, r9);
        r6 = java.lang.Boolean.FALSE;
        if (r5 == r6) goto L_0x0087;
    L_0x0077:
        r6 = kawa.lib.lists.cdr;
        r0 = r6.apply1(r0);
        r6 = kawa.lib.lists.cdr;
        r2 = r6.apply1(r1);
        r1 = r2;
        goto L_0x003e;
    L_0x0085:
        r6 = r7;
        goto L_0x005b;
    L_0x0087:
        r6 = r5;
        goto L_0x000f;
    L_0x0089:
        if (r5 == 0) goto L_0x008e;
    L_0x008b:
        r6 = java.lang.Boolean.TRUE;
        goto L_0x000f;
    L_0x008e:
        r6 = java.lang.Boolean.FALSE;
        goto L_0x000f;
    L_0x0092:
        r7 = move-exception;
        r8 = new gnu.mapping.WrongType;
        r9 = "x";
        r10 = -2;
        r8.<init>(r7, r9, r10, r6);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi1.list$Eq$V(java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public static Object length$Pl(Object x) {
        Object len = Lit0;
        Object lag = x;
        while (lists.isPair(x)) {
            x = lists.cdr.apply1(x);
            len = AddOp.$Pl.apply2(len, Lit1);
            if (!lists.isPair(x)) {
                return len;
            }
            x = lists.cdr.apply1(x);
            lag = lists.cdr.apply1(lag);
            len = AddOp.$Pl.apply2(len, Lit1);
            boolean x2 = ((x == lag ? 1 : 0) + 1) & 1;
            if (!x2) {
                if (x2) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        }
        return len;
    }

    public static Object zip$V(Object list1, Object[] argsArray) {
        return Scheme.apply.apply4(Scheme.map, LangObjType.listType, list1, LList.makeList(argsArray, 0));
    }

    public static Object fifth(Object x) {
        return lists.car.apply1(lists.cddddr.apply1(x));
    }

    public static Object sixth(Object x) {
        return lists.cadr.apply1(lists.cddddr.apply1(x));
    }

    public static Object seventh(Object x) {
        return lists.caddr.apply1(lists.cddddr.apply1(x));
    }

    public static Object eighth(Object x) {
        return lists.cadddr.apply1(lists.cddddr.apply1(x));
    }

    public static Object ninth(Object x) {
        return lists.car.apply1(lists.cddddr.apply1(lists.cddddr.apply1(x)));
    }

    public static Object tenth(Object x) {
        return lists.cadr.apply1(lists.cddddr.apply1(lists.cddddr.apply1(x)));
    }

    public static Object car$PlCdr(Object pair) {
        return misc.values(lists.car.apply1(pair), lists.cdr.apply1(pair));
    }

    public static Object take(Object lis, IntNum k) {
        Object obj = LList.Empty;
        Object apply2;
        while (!numbers.isZero((Number) apply2)) {
            try {
                Object lis2 = lists.cdr.apply1(lis);
                apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                Pair cons = lists.cons(lists.car.apply1(lis), obj);
                lis = lis2;
            } catch (ClassCastException e) {
                throw new WrongType(e, "zero?", 1, apply2);
            }
        }
        try {
            return lists.reverse$Ex((LList) obj);
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "reverse!", 1, obj);
        }
    }

    public static Object drop(Object lis, IntNum k) {
        Object apply2;
        while (!numbers.isZero((Number) apply2)) {
            try {
                lis = lists.cdr.apply1(lis);
                apply2 = AddOp.$Mn.apply2(apply2, Lit1);
            } catch (ClassCastException e) {
                throw new WrongType(e, "zero?", 1, apply2);
            }
        }
        return lis;
    }

    public static Object take$Ex(Object lis, IntNum k) {
        if (numbers.isZero(k)) {
            return LList.Empty;
        }
        Object drop = drop(lis, IntNum.add(k, -1));
        try {
            lists.setCdr$Ex((Pair) drop, LList.Empty);
            return lis;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-cdr!", 1, drop);
        }
    }

    public static Object takeRight(Object lis, IntNum k) {
        Object lag = lis;
        for (Object lead = drop(lis, k); lists.isPair(lead); lead = lists.cdr.apply1(lead)) {
            lag = lists.cdr.apply1(lag);
        }
        return lag;
    }

    public static Object dropRight(Object lis, IntNum k) {
        return lambda1recur(lis, drop(lis, k));
    }

    public static Object lambda1recur(Object lag, Object lead) {
        return lists.isPair(lead) ? lists.cons(lists.car.apply1(lag), lambda1recur(lists.cdr.apply1(lag), lists.cdr.apply1(lead))) : LList.Empty;
    }

    public static Object dropRight$Ex(Object lis, IntNum k) {
        Object lead = drop(lis, k);
        if (!lists.isPair(lead)) {
            return LList.Empty;
        }
        Object lag = lis;
        for (lead = lists.cdr.apply1(lead); lists.isPair(lead); lead = lists.cdr.apply1(lead)) {
            lag = lists.cdr.apply1(lag);
        }
        try {
            lists.setCdr$Ex((Pair) lag, LList.Empty);
            return lis;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-cdr!", 1, lag);
        }
    }

    public static Object splitAt(Object x, IntNum k) {
        Object k2;
        Object prefix = LList.Empty;
        while (!numbers.isZero((Number) k2)) {
            try {
                prefix = lists.cons(lists.car.apply1(x), prefix);
                Object suffix = lists.cdr.apply1(x);
                k2 = AddOp.$Mn.apply2(k2, Lit1);
                x = suffix;
            } catch (ClassCastException e) {
                throw new WrongType(e, "zero?", 1, k2);
            }
        }
        Object[] objArr = new Object[2];
        try {
            objArr[0] = lists.reverse$Ex((LList) prefix);
            objArr[1] = x;
            return misc.values(objArr);
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "reverse!", 1, prefix);
        }
    }

    public static Object splitAt$Ex(Object x, IntNum k) {
        if (numbers.isZero(k)) {
            return misc.values(LList.Empty, x);
        }
        Object prev = drop(x, IntNum.add(k, -1));
        Object suffix = lists.cdr.apply1(prev);
        try {
            lists.setCdr$Ex((Pair) prev, LList.Empty);
            return misc.values(x, suffix);
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-cdr!", 1, prev);
        }
    }

    public static Object last(Object lis) {
        try {
            return lists.car.apply1(lastPair((Pair) lis));
        } catch (ClassCastException e) {
            throw new WrongType(e, "last-pair", 0, lis);
        }
    }

    public static Object lastPair(Pair lis) {
        while (true) {
            Pair tail = lists.cdr.apply1(lis);
            if (!lists.isPair(tail)) {
                return lis;
            }
            lis = tail;
        }
    }

    public static LList unzip1(Object lis) {
        Pair result = LList.Empty;
        Object arg0 = lis;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(lists.car.apply1(arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    public static Object unzip2(Object lis) {
        frame gnu_kawa_slib_srfi1_frame = new frame();
        return frame.lambda2recur(lis);
    }

    public static Object unzip3(Object lis) {
        frame1 gnu_kawa_slib_srfi1_frame1 = new frame1();
        return frame1.lambda5recur(lis);
    }

    public static Object unzip4(Object lis) {
        frame3 gnu_kawa_slib_srfi1_frame3 = new frame3();
        return frame3.lambda8recur(lis);
    }

    public static Object unzip5(Object lis) {
        frame5 gnu_kawa_slib_srfi1_frame5 = new frame5();
        return frame5.lambda11recur(lis);
    }

    public static Object append$Ex$V(Object[] argsArray) {
        Object lists = LList.makeList(argsArray, 0);
        Object obj = LList.Empty;
        while (lists.isPair(lists)) {
            Object first = lists.car.apply1(lists);
            Object rest = lists.cdr.apply1(lists);
            if (lists.isPair(first)) {
                try {
                    Object tail$Mncons = lastPair((Pair) first);
                    while (lists.isPair(rest)) {
                        Object next = lists.car.apply1(rest);
                        rest = lists.cdr.apply1(rest);
                        try {
                            lists.setCdr$Ex((Pair) tail$Mncons, next);
                            if (lists.isPair(next)) {
                                try {
                                    tail$Mncons = lastPair((Pair) next);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "last-pair", 0, next);
                                }
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "set-cdr!", 1, tail$Mncons);
                        }
                    }
                    return first;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "last-pair", 0, first);
                }
            }
            obj = first;
            lists = rest;
        }
        return obj;
    }

    public static Object appendReverse(Object rev$Mnhead, Object tail) {
        while (isNullList(rev$Mnhead) == Boolean.FALSE) {
            Object rev$Mnhead2 = lists.cdr.apply1(rev$Mnhead);
            tail = lists.cons(lists.car.apply1(rev$Mnhead), tail);
            rev$Mnhead = rev$Mnhead2;
        }
        return tail;
    }

    public static Object appendReverse$Ex(Object rev$Mnhead, Object tail) {
        while (isNullList(rev$Mnhead) == Boolean.FALSE) {
            Object next$Mnrev = lists.cdr.apply1(rev$Mnhead);
            try {
                lists.setCdr$Ex((Pair) rev$Mnhead, tail);
                tail = rev$Mnhead;
                rev$Mnhead = next$Mnrev;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, rev$Mnhead);
            }
        }
        return tail;
    }

    public static Object concatenate(Object lists) {
        return reduceRight(append.append, LList.Empty, lists);
    }

    public static Object concatenate$Ex(Object lists) {
        return reduceRight(append$Ex, LList.Empty, lists);
    }

    static Object $PcCdrs(Object lists) {
        Continuation abort = new Continuation(CallContext.getInstance());
        try {
            frame55 gnu_kawa_slib_srfi1_frame55 = new frame55();
            gnu_kawa_slib_srfi1_frame55.abort = abort;
            Object lambda74recur = gnu_kawa_slib_srfi1_frame55.lambda74recur(lists);
            abort.invoked = true;
            return lambda74recur;
        } catch (Throwable th) {
            return Continuation.handleException(th, abort);
        }
    }

    static Object $PcCars$Pl(Object lists, Object lastElt) {
        frame56 gnu_kawa_slib_srfi1_frame56 = new frame56();
        gnu_kawa_slib_srfi1_frame56.last$Mnelt = lastElt;
        return gnu_kawa_slib_srfi1_frame56.lambda75recur(lists);
    }

    static Object $PcCars$PlCdrs(Object lists) {
        Continuation abort = new Continuation(CallContext.getInstance());
        try {
            frame57 gnu_kawa_slib_srfi1_frame57 = new frame57();
            gnu_kawa_slib_srfi1_frame57.abort = abort;
            Object lambda76recur = gnu_kawa_slib_srfi1_frame57.lambda76recur(lists);
            abort.invoked = true;
            return lambda76recur;
        } catch (Throwable th) {
            return Continuation.handleException(th, abort);
        }
    }

    static Object $PcCars$PlCdrs$SlPair(Object lists) {
        frame61 gnu_kawa_slib_srfi1_frame61 = new frame61();
        gnu_kawa_slib_srfi1_frame61.lists = lists;
        return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame61.lambda$Fn63, lambda$Fn64);
    }

    static Object $PcCars$PlCdrs$Pl(Object lists, Object carsFinal) {
        frame62 closureEnv = new frame62();
        closureEnv.cars$Mnfinal = carsFinal;
        Continuation abort = new Continuation(CallContext.getInstance());
        try {
            frame63 gnu_kawa_slib_srfi1_frame63 = new frame63();
            gnu_kawa_slib_srfi1_frame63.staticLink = closureEnv;
            gnu_kawa_slib_srfi1_frame63.abort = abort;
            Object lambda85recur = gnu_kawa_slib_srfi1_frame63.lambda85recur(lists);
            abort.invoked = true;
            return lambda85recur;
        } catch (Throwable th) {
            return Continuation.handleException(th, abort);
        }
    }

    static Object $PcCars$PlCdrs$SlNoTest(Object lists) {
        frame67 gnu_kawa_slib_srfi1_frame67 = new frame67();
        return frame67.lambda92recur(lists);
    }

    static Object $PcCars$PlCdrs$SlNoTest$SlPair(Object lists) {
        frame71 gnu_kawa_slib_srfi1_frame71 = new frame71();
        gnu_kawa_slib_srfi1_frame71.lists = lists;
        return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame71.lambda$Fn77, lambda$Fn78);
    }

    public static Object count$V(Procedure pred, Object list1, Object[] argsArray) {
        Object i;
        Object lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            i = Lit0;
            while (isNullList(list1) == Boolean.FALSE) {
                Object split = $PcCars$PlCdrs$SlPair(lists);
                Object a$Mns = lists.car.apply1(split);
                Object d$Mns = lists.cdr.apply1(split);
                if (lists.isNull(a$Mns)) {
                    break;
                }
                Object list12 = lists.cdr.apply1(list1);
                if (Scheme.apply.apply3(pred, lists.car.apply1(list1), a$Mns) != Boolean.FALSE) {
                    i = AddOp.$Pl.apply2(i, Lit1);
                }
                lists = d$Mns;
                list1 = list12;
            }
        } else {
            i = Lit0;
            Object lis = list1;
            while (isNullList(lis) == Boolean.FALSE) {
                Object lis2 = lists.cdr.apply1(lis);
                if (pred.apply1(lists.car.apply1(lis)) != Boolean.FALSE) {
                    i = AddOp.$Pl.apply2(i, Lit1);
                }
                lis = lis2;
            }
        }
        return i;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        if (moduleMethod.selector != 123) {
            return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
        try {
            try {
                try {
                    return unfoldRight((Procedure) obj, (Procedure) obj2, (Procedure) obj3, obj4);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "unfold-right", 3, obj3);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "unfold-right", 2, obj2);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "unfold-right", 1, obj);
        }
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 123) {
            return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
        if (!(obj instanceof Procedure)) {
            return -786431;
        }
        callContext.value1 = obj;
        if (!(obj2 instanceof Procedure)) {
            return -786430;
        }
        callContext.value2 = obj2;
        if (!(obj3 instanceof Procedure)) {
            return -786429;
        }
        callContext.value3 = obj3;
        callContext.value4 = obj4;
        callContext.proc = moduleMethod;
        callContext.pc = 4;
        return 0;
    }

    public static Object unfoldRight(Procedure p, Procedure f, Procedure g, Object seed, Object obj) {
        while (p.apply1(seed) == Boolean.FALSE) {
            Object seed2 = g.apply1(seed);
            Pair cons = lists.cons(f.apply1(seed), obj);
            seed = seed2;
        }
        return obj;
    }

    public static Object unfold$V(Procedure p, Procedure f, Procedure g, Object seed, Object[] argsArray) {
        LList maybe$Mntail$Mngen = LList.makeList(argsArray, 0);
        if (lists.isPair(maybe$Mntail$Mngen)) {
            Object tail$Mngen = lists.car.apply1(maybe$Mntail$Mngen);
            if (lists.isPair(lists.cdr.apply1(maybe$Mntail$Mngen))) {
                return Scheme.apply.applyN(new Object[]{misc.error, "Too many arguments", unfold, p, f, g, seed, maybe$Mntail$Mngen});
            }
            Pair res = LList.Empty;
            while (p.apply1(seed) == Boolean.FALSE) {
                Object seed2 = g.apply1(seed);
                res = lists.cons(f.apply1(seed), res);
                seed = seed2;
            }
            return appendReverse$Ex(res, Scheme.applyToArgs.apply2(tail$Mngen, seed));
        }
        Object obj = LList.Empty;
        while (p.apply1(seed) == Boolean.FALSE) {
            seed2 = g.apply1(seed);
            Pair cons = lists.cons(f.apply1(seed), obj);
            seed = seed2;
        }
        try {
            return lists.reverse$Ex((LList) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "reverse!", 1, obj);
        }
    }

    public static Object fold$V(Procedure kons, Object knil, Object lis1, Object[] argsArray) {
        frame7 gnu_kawa_slib_srfi1_frame7 = new frame7();
        gnu_kawa_slib_srfi1_frame7.kons = kons;
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return gnu_kawa_slib_srfi1_frame7.lambda14lp(lists.cons(lis1, lists), knil);
        }
        Object lis = lis1;
        while (isNullList(lis) == Boolean.FALSE) {
            Object lis2 = lists.cdr.apply1(lis);
            knil = gnu_kawa_slib_srfi1_frame7.kons.apply2(lists.car.apply1(lis), knil);
            lis = lis2;
        }
        return knil;
    }

    public static Object foldRight$V(Procedure kons, Object knil, Object lis1, Object[] argsArray) {
        frame9 gnu_kawa_slib_srfi1_frame9 = new frame9();
        gnu_kawa_slib_srfi1_frame9.kons = kons;
        gnu_kawa_slib_srfi1_frame9.knil = knil;
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return gnu_kawa_slib_srfi1_frame9.lambda17recur(lists.cons(lis1, lists));
        }
        return gnu_kawa_slib_srfi1_frame9.lambda18recur(lis1);
    }

    public static Object pairFoldRight$V(Procedure f, Object zero, Object lis1, Object[] argsArray) {
        frame10 gnu_kawa_slib_srfi1_frame10 = new frame10();
        gnu_kawa_slib_srfi1_frame10.f30f = f;
        gnu_kawa_slib_srfi1_frame10.zero = zero;
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return gnu_kawa_slib_srfi1_frame10.lambda19recur(lists.cons(lis1, lists));
        }
        return gnu_kawa_slib_srfi1_frame10.lambda20recur(lis1);
    }

    public static Object pairFold$V(Procedure f, Object zero, Object lis1, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            Object lists2 = lists.cons(lis1, lists);
            while (true) {
                Object tails = $PcCdrs(lists2);
                if (lists.isNull(tails)) {
                    break;
                }
                Object[] objArr = new Object[]{lists2, LList.list1(zero)};
                lists2 = tails;
                zero = Scheme.apply.apply2(f, append$Ex$V(objArr));
            }
        } else {
            Object lis = lis1;
            while (isNullList(lis) == Boolean.FALSE) {
                Object tail = lists.cdr.apply1(lis);
                Object ans = f.apply2(lis, zero);
                lis = tail;
                zero = ans;
            }
        }
        return zero;
    }

    public static Object reduce(Procedure f, Object ridentity, Object lis) {
        return isNullList(lis) != Boolean.FALSE ? ridentity : fold$V(f, lists.car.apply1(lis), lists.cdr.apply1(lis), new Object[0]);
    }

    public static Object reduceRight(Procedure f, Object ridentity, Object lis) {
        frame11 gnu_kawa_slib_srfi1_frame11 = new frame11();
        gnu_kawa_slib_srfi1_frame11.f31f = f;
        return isNullList(lis) != Boolean.FALSE ? ridentity : gnu_kawa_slib_srfi1_frame11.lambda21recur(lists.car.apply1(lis), lists.cdr.apply1(lis));
    }

    public static Object appendMap$V(Object f, Object lis1, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return Scheme.apply.apply2(append.append, Scheme.apply.apply4(Scheme.map, f, lis1, lists));
        }
        Procedure procedure = Scheme.apply;
        append kawa_standard_append = append.append;
        Pair result = LList.Empty;
        Object arg0 = lis1;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(Scheme.applyToArgs.apply2(f, arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return procedure.apply2(kawa_standard_append, LList.reverseInPlace(result));
    }

    public static Object appendMap$Ex$V(Object f, Object lis1, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return Scheme.apply.apply2(append$Ex, Scheme.apply.apply4(Scheme.map, f, lis1, lists));
        }
        Procedure procedure = Scheme.apply;
        ModuleMethod moduleMethod = append$Ex;
        Pair result = LList.Empty;
        Object arg0 = lis1;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(Scheme.applyToArgs.apply2(f, arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return procedure.apply2(moduleMethod, LList.reverseInPlace(result));
    }

    public static Object pairForEach$V(Procedure proc, Object lis1, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            Object lists2 = lists.cons(lis1, lists);
            while (true) {
                Object tails = $PcCdrs(lists2);
                if (!lists.isPair(tails)) {
                    return Values.empty;
                }
                Scheme.apply.apply2(proc, lists2);
                lists2 = tails;
            }
        } else {
            Object lis = lis1;
            while (isNullList(lis) == Boolean.FALSE) {
                Object tail = lists.cdr.apply1(lis);
                proc.apply1(lis);
                lis = tail;
            }
            return Values.empty;
        }
    }

    public static Object map$Ex$V(Procedure f, Object lis1, Object[] argsArray) {
        frame12 gnu_kawa_slib_srfi1_frame12 = new frame12();
        gnu_kawa_slib_srfi1_frame12.f32f = f;
        Object lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            Object lis12 = lis1;
            while (isNullList(lis12) == Boolean.FALSE) {
                Object split = $PcCars$PlCdrs$SlNoTest$SlPair(lists);
                Object heads = lists.car.apply1(split);
                Object tails = lists.cdr.apply1(split);
                try {
                    lists.setCar$Ex((Pair) lis12, Scheme.apply.apply3(gnu_kawa_slib_srfi1_frame12.f32f, lists.car.apply1(lis12), heads));
                    lis12 = lists.cdr.apply1(lis12);
                    lists = tails;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-car!", 1, lis12);
                }
            }
        }
        pairForEach$V(gnu_kawa_slib_srfi1_frame12.lambda$Fn11, lis1, new Object[0]);
        return lis1;
    }

    public static Object filterMap$V(Procedure f, Object lis1, Object[] argsArray) {
        frame13 gnu_kawa_slib_srfi1_frame13 = new frame13();
        gnu_kawa_slib_srfi1_frame13.f33f = f;
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return gnu_kawa_slib_srfi1_frame13.lambda23recur(lists.cons(lis1, lists), LList.Empty);
        }
        Object obj = LList.Empty;
        Object lis = lis1;
        while (isNullList(lis) == Boolean.FALSE) {
            Boolean head = gnu_kawa_slib_srfi1_frame13.f33f.apply1(lists.car.apply1(lis));
            Object tail = lists.cdr.apply1(lis);
            if (head != Boolean.FALSE) {
                obj = lists.cons(head, obj);
                lis = tail;
            } else {
                lis = tail;
            }
        }
        try {
            return lists.reverse$Ex((LList) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "reverse!", 1, obj);
        }
    }

    public static Object filter(Procedure pred, Object lis) {
        Object obj = LList.Empty;
        while (isNullList(lis) == Boolean.FALSE) {
            Object head = lists.car.apply1(lis);
            Object tail = lists.cdr.apply1(lis);
            if (pred.apply1(head) != Boolean.FALSE) {
                obj = lists.cons(head, obj);
                lis = tail;
            } else {
                lis = tail;
            }
        }
        try {
            return lists.reverse$Ex((LList) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "reverse!", 1, obj);
        }
    }

    public static Object filter$Ex(Procedure pred, Object lis) {
        Object ans = lis;
        while (isNullList(ans) == Boolean.FALSE) {
            if (pred.apply1(lists.car.apply1(ans)) == Boolean.FALSE) {
                ans = lists.cdr.apply1(ans);
            } else {
                Object apply1 = lists.cdr.apply1(ans);
                Object prev = ans;
                loop1:
                while (lists.isPair(apply1)) {
                    if (pred.apply1(lists.car.apply1(apply1)) != Boolean.FALSE) {
                        prev = apply1;
                        apply1 = lists.cdr.apply1(apply1);
                    } else {
                        lis = lists.cdr.apply1(apply1);
                        while (lists.isPair(lis)) {
                            if (pred.apply1(lists.car.apply1(lis)) != Boolean.FALSE) {
                                try {
                                    lists.setCdr$Ex((Pair) prev, lis);
                                    prev = lis;
                                    apply1 = lists.cdr.apply1(lis);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "set-cdr!", 1, prev);
                                }
                            }
                            lis = lists.cdr.apply1(lis);
                        }
                        try {
                            lists.setCdr$Ex((Pair) prev, lis);
                            break loop1;
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "set-cdr!", 1, prev);
                        }
                    }
                }
                return ans;
            }
        }
        return ans;
    }

    public static Object partition(Procedure pred, Object lis) {
        Object obj = LList.Empty;
        Object obj2 = LList.Empty;
        while (isNullList(lis) == Boolean.FALSE) {
            Object head = lists.car.apply1(lis);
            Object tail = lists.cdr.apply1(lis);
            if (pred.apply1(head) != Boolean.FALSE) {
                obj = lists.cons(head, obj);
                lis = tail;
            } else {
                obj2 = lists.cons(head, obj2);
                lis = tail;
            }
        }
        Object[] objArr = new Object[2];
        try {
            objArr[0] = lists.reverse$Ex((LList) obj);
            try {
                objArr[1] = lists.reverse$Ex((LList) obj2);
                return misc.values(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "reverse!", 1, obj2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "reverse!", 1, obj);
        }
    }

    public static Object partition$Ex(Procedure pred, Object lis) {
        Pair in$Mnhead = lists.cons(Lit2, LList.Empty);
        Object in = in$Mnhead;
        Object cons = lists.cons(Lit2, LList.Empty);
        while (!isNotPair(lis)) {
            if (pred.apply1(lists.car.apply1(lis)) != Boolean.FALSE) {
                try {
                    lists.setCdr$Ex((Pair) in, lis);
                    in = lis;
                    lis = lists.cdr.apply1(lis);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, in);
                }
            }
            try {
                lists.setCdr$Ex((Pair) cons, lis);
                cons = lis;
                lis = lists.cdr.apply1(lis);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "set-cdr!", 1, cons);
            }
        }
        try {
            lists.setCdr$Ex((Pair) in, LList.Empty);
            try {
                lists.setCdr$Ex((Pair) cons, LList.Empty);
                return misc.values(lists.cdr.apply1(in$Mnhead), lists.cdr.apply1(out$Mnhead));
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "set-cdr!", 1, cons);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "set-cdr!", 1, in);
        }
    }

    public static Object remove(Object pred, Object l) {
        frame15 gnu_kawa_slib_srfi1_frame15 = new frame15();
        gnu_kawa_slib_srfi1_frame15.pred = pred;
        return filter(gnu_kawa_slib_srfi1_frame15.lambda$Fn14, l);
    }

    public static Object remove$Ex(Object pred, Object l) {
        frame16 gnu_kawa_slib_srfi1_frame16 = new frame16();
        gnu_kawa_slib_srfi1_frame16.pred = pred;
        return filter$Ex(gnu_kawa_slib_srfi1_frame16.lambda$Fn15, l);
    }

    public static Object delete(Object x, Object lis, Object maybe$Mn$Eq) {
        frame17 gnu_kawa_slib_srfi1_frame17 = new frame17();
        gnu_kawa_slib_srfi1_frame17.f34x = x;
        gnu_kawa_slib_srfi1_frame17.maybe$Mn$Eq = maybe$Mn$Eq;
        return filter(gnu_kawa_slib_srfi1_frame17.lambda$Fn16, lis);
    }

    public static Object delete$Ex(Object x, Object lis, Object maybe$Mn$Eq) {
        frame18 gnu_kawa_slib_srfi1_frame18 = new frame18();
        gnu_kawa_slib_srfi1_frame18.f35x = x;
        gnu_kawa_slib_srfi1_frame18.maybe$Mn$Eq = maybe$Mn$Eq;
        return filter$Ex(gnu_kawa_slib_srfi1_frame18.lambda$Fn17, lis);
    }

    public static Object deleteDuplicates(Object lis, Procedure maybe$Mn$Eq) {
        frame19 gnu_kawa_slib_srfi1_frame19 = new frame19();
        gnu_kawa_slib_srfi1_frame19.maybe$Mn$Eq = maybe$Mn$Eq;
        return gnu_kawa_slib_srfi1_frame19.lambda30recur(lis);
    }

    public static Object deleteDuplicates$Ex(Object lis, Procedure maybe$Mn$Eq) {
        frame20 gnu_kawa_slib_srfi1_frame20 = new frame20();
        gnu_kawa_slib_srfi1_frame20.maybe$Mn$Eq = maybe$Mn$Eq;
        return gnu_kawa_slib_srfi1_frame20.lambda31recur(lis);
    }

    public static Pair alistCons(Object key, Object datum, Object alist) {
        return lists.cons(lists.cons(key, datum), alist);
    }

    public static LList alistCopy(Object alist) {
        Pair result = LList.Empty;
        Object arg0 = alist;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                Object elt = arg02.getCar();
                result = Pair.make(lists.cons(lists.car.apply1(elt), lists.cdr.apply1(elt)), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 82:
                try {
                    return listCopy((LList) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "list-copy", 1, obj);
                }
            case 83:
                try {
                    return iota(LangObjType.coerceIntNum(obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "iota", 1, obj);
                }
            case 87:
                return isProperList(obj);
            case 88:
                return isDottedList(obj);
            case 89:
                return isCircularList(obj);
            case 90:
                return isNotPair(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 91:
                return isNullList(obj);
            case 93:
                return length$Pl(obj);
            case 95:
                return fifth(obj);
            case 96:
                return sixth(obj);
            case 97:
                return seventh(obj);
            case 98:
                return eighth(obj);
            case 99:
                return ninth(obj);
            case 100:
                return tenth(obj);
            case 101:
                return car$PlCdr(obj);
            case 110:
                return last(obj);
            case 111:
                try {
                    return lastPair((Pair) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "last-pair", 1, obj);
                }
            case DateTime.TIME_MASK /*112*/:
                return unzip1(obj);
            case 113:
                return unzip2(obj);
            case 114:
                return unzip3(obj);
            case 115:
                return unzip4(obj);
            case 116:
                return unzip5(obj);
            case 120:
                return concatenate(obj);
            case 121:
                return concatenate$Ex(obj);
            case 147:
                return deleteDuplicates(obj);
            case 149:
                return deleteDuplicates$Ex(obj);
            case 152:
                return alistCopy(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object alistDelete(Object key, Object alist, Object maybe$Mn$Eq) {
        frame21 gnu_kawa_slib_srfi1_frame21 = new frame21();
        gnu_kawa_slib_srfi1_frame21.key = key;
        gnu_kawa_slib_srfi1_frame21.maybe$Mn$Eq = maybe$Mn$Eq;
        return filter(gnu_kawa_slib_srfi1_frame21.lambda$Fn18, alist);
    }

    public static Object alistDelete$Ex(Object key, Object alist, Object maybe$Mn$Eq) {
        frame22 gnu_kawa_slib_srfi1_frame22 = new frame22();
        gnu_kawa_slib_srfi1_frame22.key = key;
        gnu_kawa_slib_srfi1_frame22.maybe$Mn$Eq = maybe$Mn$Eq;
        return filter$Ex(gnu_kawa_slib_srfi1_frame22.lambda$Fn19, alist);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 83:
                try {
                    try {
                        try {
                            return iota(LangObjType.coerceIntNum(obj), LangObjType.coerceNumeric(obj2), LangObjType.coerceNumeric(obj3));
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "iota", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "iota", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "iota", 1, obj);
                }
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                try {
                    return reduce((Procedure) obj, obj2, obj3);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "reduce", 1, obj);
                }
            case 131:
                try {
                    return reduceRight((Procedure) obj, obj2, obj3);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "reduce-right", 1, obj);
                }
            case 143:
                return delete(obj, obj2, obj3);
            case 145:
                return delete$Ex(obj, obj2, obj3);
            case 151:
                return alistCons(obj, obj2, obj3);
            case 153:
                return alistDelete(obj, obj2, obj3);
            case 155:
                return alistDelete$Ex(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object find(Object pred, Object list) {
        try {
            Boolean temp = findTail((Procedure) pred, list);
            if (temp != Boolean.FALSE) {
                return lists.car.apply1(temp);
            }
            return Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "find-tail", 0, pred);
        }
    }

    public static Object findTail(Procedure pred, Object list) {
        boolean x;
        while (true) {
            Object isNullList = isNullList(list);
            try {
                x = ((isNullList != Boolean.FALSE ? 1 : 0) + 1) & 1;
                if (!x) {
                    break;
                } else if (pred.apply1(lists.car.apply1(list)) != Boolean.FALSE) {
                    return list;
                } else {
                    list = lists.cdr.apply1(list);
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, isNullList);
            }
        }
        return x ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object takeWhile(Procedure pred, Object lis) {
        frame23 gnu_kawa_slib_srfi1_frame23 = new frame23();
        gnu_kawa_slib_srfi1_frame23.pred = pred;
        return gnu_kawa_slib_srfi1_frame23.lambda34recur(lis);
    }

    public static Object dropWhile(Procedure pred, Object lis) {
        while (isNullList(lis) == Boolean.FALSE) {
            if (pred.apply1(lists.car.apply1(lis)) == Boolean.FALSE) {
                return lis;
            }
            lis = lists.cdr.apply1(lis);
        }
        return LList.Empty;
    }

    public static Object takeWhile$Ex(Procedure pred, Object lis) {
        Boolean x = isNullList(lis);
        if (x == Boolean.FALSE ? pred.apply1(lists.car.apply1(lis)) == Boolean.FALSE : x != Boolean.FALSE) {
            return LList.Empty;
        }
        Object prev = lists.cdr.apply1(lis);
        Object prev2 = lis;
        while (lists.isPair(prev)) {
            if (pred.apply1(lists.car.apply1(prev)) != Boolean.FALSE) {
                prev2 = prev;
                prev = lists.cdr.apply1(prev);
            } else {
                try {
                    lists.setCdr$Ex((Pair) prev2, LList.Empty);
                    return lis;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, prev2);
                }
            }
        }
        return lis;
    }

    public static Object span(Procedure pred, Object lis) {
        Object[] objArr;
        Object obj = LList.Empty;
        while (isNullList(lis) == Boolean.FALSE) {
            Object head = lists.car.apply1(lis);
            if (pred.apply1(head) != Boolean.FALSE) {
                lis = lists.cdr.apply1(lis);
                Pair cons = lists.cons(head, obj);
            } else {
                objArr = new Object[2];
                try {
                    objArr[0] = lists.reverse$Ex((LList) obj);
                    objArr[1] = lis;
                    return misc.values(objArr);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "reverse!", 1, obj);
                }
            }
        }
        objArr = new Object[2];
        try {
            objArr[0] = lists.reverse$Ex((LList) obj);
            objArr[1] = lis;
            return misc.values(objArr);
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "reverse!", 1, obj);
        }
    }

    public static Object span$Ex(Procedure pred, Object lis) {
        Boolean x = isNullList(lis);
        if (x == Boolean.FALSE ? pred.apply1(lists.car.apply1(lis)) == Boolean.FALSE : x != Boolean.FALSE) {
            return misc.values(LList.Empty, lis);
        }
        Object suffix;
        Object apply1 = lists.cdr.apply1(lis);
        Object prev = lis;
        while (isNullList(apply1) == Boolean.FALSE) {
            if (pred.apply1(lists.car.apply1(apply1)) != Boolean.FALSE) {
                prev = apply1;
                apply1 = lists.cdr.apply1(apply1);
            } else {
                try {
                    lists.setCdr$Ex((Pair) prev, LList.Empty);
                    suffix = apply1;
                    break;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, prev);
                }
            }
        }
        suffix = apply1;
        return misc.values(lis, suffix);
    }

    public static Object m42break(Object pred, Object lis) {
        frame24 gnu_kawa_slib_srfi1_frame24 = new frame24();
        gnu_kawa_slib_srfi1_frame24.pred = pred;
        return span(gnu_kawa_slib_srfi1_frame24.lambda$Fn20, lis);
    }

    public static Object break$Ex(Object pred, Object lis) {
        frame25 gnu_kawa_slib_srfi1_frame25 = new frame25();
        gnu_kawa_slib_srfi1_frame25.pred = pred;
        return span$Ex(gnu_kawa_slib_srfi1_frame25.lambda$Fn21, lis);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 78:
                return xcons(obj, obj2);
            case 80:
                try {
                    return listTabulate(obj, (Procedure) obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "list-tabulate", 2, obj2);
                }
            case 83:
                try {
                    try {
                        return iota(LangObjType.coerceIntNum(obj), LangObjType.coerceNumeric(obj2));
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "iota", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "iota", 1, obj);
                }
            case 102:
                try {
                    return take(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "take", 2, obj2);
                }
            case 103:
                try {
                    return drop(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "drop", 2, obj2);
                }
            case 104:
                try {
                    return take$Ex(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "take!", 2, obj2);
                }
            case 105:
                try {
                    return takeRight(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "take-right", 2, obj2);
                }
            case 106:
                try {
                    return dropRight(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "drop-right", 2, obj2);
                }
            case 107:
                try {
                    return dropRight$Ex(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "drop-right!", 2, obj2);
                }
            case 108:
                try {
                    return splitAt(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "split-at", 2, obj2);
                }
            case 109:
                try {
                    return splitAt$Ex(obj, LangObjType.coerceIntNum(obj2));
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "split-at!", 2, obj2);
                }
            case 118:
                return appendReverse(obj, obj2);
            case 119:
                return appendReverse$Ex(obj, obj2);
            case 137:
                try {
                    return filter((Procedure) obj, obj2);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "filter", 1, obj);
                }
            case 138:
                try {
                    return filter$Ex((Procedure) obj, obj2);
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "filter!", 1, obj);
                }
            case 139:
                try {
                    return partition((Procedure) obj, obj2);
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "partition", 1, obj);
                }
            case 140:
                try {
                    return partition$Ex((Procedure) obj, obj2);
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "partition!", 1, obj);
                }
            case 141:
                return remove(obj, obj2);
            case 142:
                return remove$Ex(obj, obj2);
            case 143:
                return delete(obj, obj2);
            case 145:
                return delete$Ex(obj, obj2);
            case 147:
                try {
                    return deleteDuplicates(obj, (Procedure) obj2);
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "delete-duplicates", 2, obj2);
                }
            case 149:
                try {
                    return deleteDuplicates$Ex(obj, (Procedure) obj2);
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "delete-duplicates!", 2, obj2);
                }
            case 153:
                return alistDelete(obj, obj2);
            case 155:
                return alistDelete$Ex(obj, obj2);
            case 157:
                return find(obj, obj2);
            case 158:
                try {
                    return findTail((Procedure) obj, obj2);
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "find-tail", 1, obj);
                }
            case 159:
                try {
                    return takeWhile((Procedure) obj, obj2);
                } catch (ClassCastException e222222222222222222) {
                    throw new WrongType(e222222222222222222, "take-while", 1, obj);
                }
            case ComponentConstants.TEXTBOX_PREFERRED_WIDTH /*160*/:
                try {
                    return dropWhile((Procedure) obj, obj2);
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "drop-while", 1, obj);
                }
            case 161:
                try {
                    return takeWhile$Ex((Procedure) obj, obj2);
                } catch (ClassCastException e22222222222222222222) {
                    throw new WrongType(e22222222222222222222, "take-while!", 1, obj);
                }
            case 162:
                try {
                    return span((Procedure) obj, obj2);
                } catch (ClassCastException e222222222222222222222) {
                    throw new WrongType(e222222222222222222222, "span", 1, obj);
                }
            case 163:
                try {
                    return span$Ex((Procedure) obj, obj2);
                } catch (ClassCastException e2222222222222222222222) {
                    throw new WrongType(e2222222222222222222222, "span!", 1, obj);
                }
            case 164:
                return m42break(obj, obj2);
            case 165:
                return break$Ex(obj, obj2);
            case 182:
                return frame61.lambda84(obj, obj2);
            case 183:
                return frame71.lambda100(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object any$V(Procedure pred, Object lis1, Object[] argsArray) {
        int i = 0;
        frame26 gnu_kawa_slib_srfi1_frame26 = new frame26();
        gnu_kawa_slib_srfi1_frame26.pred = pred;
        gnu_kawa_slib_srfi1_frame26.lis1 = lis1;
        gnu_kawa_slib_srfi1_frame26.lists = LList.makeList(argsArray, 0);
        if (lists.isPair(gnu_kawa_slib_srfi1_frame26.lists)) {
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame26.lambda$Fn22, gnu_kawa_slib_srfi1_frame26.lambda$Fn23);
        }
        Object isNullList = isNullList(gnu_kawa_slib_srfi1_frame26.lis1);
        try {
            if (isNullList != Boolean.FALSE) {
                i = 1;
            }
            boolean x = (i + 1) & 1;
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                Object head = lists.car.apply1(gnu_kawa_slib_srfi1_frame26.lis1);
                for (Object tail = lists.cdr.apply1(gnu_kawa_slib_srfi1_frame26.lis1); isNullList(tail) == Boolean.FALSE; tail = lists.cdr.apply1(tail)) {
                    Boolean x2 = gnu_kawa_slib_srfi1_frame26.pred.apply1(head);
                    if (x2 != Boolean.FALSE) {
                        return x2;
                    }
                    head = lists.car.apply1(tail);
                }
                return gnu_kawa_slib_srfi1_frame26.pred.apply1(head);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "x", -2, isNullList);
        }
    }

    public static Object every$V(Procedure pred, Object lis1, Object[] argsArray) {
        frame27 gnu_kawa_slib_srfi1_frame27 = new frame27();
        gnu_kawa_slib_srfi1_frame27.pred = pred;
        gnu_kawa_slib_srfi1_frame27.lis1 = lis1;
        gnu_kawa_slib_srfi1_frame27.lists = LList.makeList(argsArray, 0);
        if (lists.isPair(gnu_kawa_slib_srfi1_frame27.lists)) {
            return call_with_values.callWithValues(gnu_kawa_slib_srfi1_frame27.lambda$Fn24, gnu_kawa_slib_srfi1_frame27.lambda$Fn25);
        }
        Boolean x = isNullList(gnu_kawa_slib_srfi1_frame27.lis1);
        if (x != Boolean.FALSE) {
            return x;
        }
        Object head = lists.car.apply1(gnu_kawa_slib_srfi1_frame27.lis1);
        for (Object tail = lists.cdr.apply1(gnu_kawa_slib_srfi1_frame27.lis1); isNullList(tail) == Boolean.FALSE; tail = lists.cdr.apply1(tail)) {
            x = gnu_kawa_slib_srfi1_frame27.pred.apply1(head);
            if (x == Boolean.FALSE) {
                return x;
            }
            head = lists.car.apply1(tail);
        }
        return gnu_kawa_slib_srfi1_frame27.pred.apply1(head);
    }

    public static Object listIndex$V(Procedure pred, Object lis1, Object[] argsArray) {
        frame30 gnu_kawa_slib_srfi1_frame30 = new frame30();
        gnu_kawa_slib_srfi1_frame30.pred = pred;
        LList lists = LList.makeList(argsArray, 0);
        if (lists.isPair(lists)) {
            return gnu_kawa_slib_srfi1_frame30.lambda44lp(lists.cons(lis1, lists), Lit0);
        }
        boolean x;
        Object n = Lit0;
        Object lis = lis1;
        while (true) {
            Object isNullList = isNullList(lis);
            try {
                int i;
                if (isNullList != Boolean.FALSE) {
                    i = 1;
                } else {
                    i = 0;
                }
                x = (i + 1) & 1;
                if (!x) {
                    break;
                } else if (gnu_kawa_slib_srfi1_frame30.pred.apply1(lists.car.apply1(lis)) != Boolean.FALSE) {
                    return n;
                } else {
                    lis = lists.cdr.apply1(lis);
                    n = AddOp.$Pl.apply2(n, Lit1);
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, isNullList);
            }
        }
        return x ? Boolean.TRUE : Boolean.FALSE;
    }

    static Object $PcLset2$Ls$Eq(Object $Eq, Object lis1, Object lis2) {
        frame72 gnu_kawa_slib_srfi1_frame72 = new frame72();
        gnu_kawa_slib_srfi1_frame72.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame72.lis2 = lis2;
        return every$V(gnu_kawa_slib_srfi1_frame72.lambda$Fn79, lis1, new Object[0]);
    }

    public static Object lset$Ls$Eq$V(Procedure $Eq, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        boolean x = (lists.isPair(lists) + 1) & 1;
        if (x) {
            return x ? Boolean.TRUE : Boolean.FALSE;
        } else {
            Object s1 = lists.car.apply1(lists);
            Object rest = lists.cdr.apply1(lists);
            while (true) {
                x = (lists.isPair(rest) + 1) & 1;
                if (x) {
                    break;
                }
                Boolean x2;
                Object s2 = lists.car.apply1(rest);
                rest = lists.cdr.apply1(rest);
                if (s2 == s1) {
                    x = true;
                } else {
                    x = false;
                }
                if (x) {
                    x2 = x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x2 = $PcLset2$Ls$Eq($Eq, s1, s2);
                }
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                s1 = s2;
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public static Object lset$Eq$V(Procedure $Eq, Object[] argsArray) {
        LList lists = LList.makeList(argsArray, 0);
        boolean x = (lists.isPair(lists) + 1) & 1;
        if (x) {
            return x ? Boolean.TRUE : Boolean.FALSE;
        } else {
            Object s1 = lists.car.apply1(lists);
            Object rest = lists.cdr.apply1(lists);
            while (true) {
                x = (lists.isPair(rest) + 1) & 1;
                if (x) {
                    break;
                }
                Boolean x2;
                Object s2 = lists.car.apply1(rest);
                rest = lists.cdr.apply1(rest);
                if (s1 == s2) {
                    x = true;
                } else {
                    x = false;
                }
                if (x) {
                    x2 = x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x2 = $PcLset2$Ls$Eq($Eq, s1, s2);
                    if (x2 != Boolean.FALSE) {
                        x2 = $PcLset2$Ls$Eq($Eq, s2, s1);
                    }
                }
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                s1 = s2;
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public static Object lsetAdjoin$V(Procedure $Eq, Object lis, Object[] argsArray) {
        frame32 gnu_kawa_slib_srfi1_frame32 = new frame32();
        gnu_kawa_slib_srfi1_frame32.$Eq = $Eq;
        return fold$V(gnu_kawa_slib_srfi1_frame32.lambda$Fn30, lis, LList.makeList(argsArray, 0), new Object[0]);
    }

    public static Object lsetUnion$V(Procedure $Eq, Object[] argsArray) {
        frame33 gnu_kawa_slib_srfi1_frame33 = new frame33();
        gnu_kawa_slib_srfi1_frame33.$Eq = $Eq;
        return reduce(gnu_kawa_slib_srfi1_frame33.lambda$Fn31, LList.Empty, LList.makeList(argsArray, 0));
    }

    public static Object lsetUnion$Ex$V(Procedure $Eq, Object[] argsArray) {
        frame35 gnu_kawa_slib_srfi1_frame35 = new frame35();
        gnu_kawa_slib_srfi1_frame35.$Eq = $Eq;
        return reduce(gnu_kawa_slib_srfi1_frame35.lambda$Fn34, LList.Empty, LList.makeList(argsArray, 0));
    }

    public static Object lsetIntersection$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame37 gnu_kawa_slib_srfi1_frame37 = new frame37();
        gnu_kawa_slib_srfi1_frame37.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame37.lists = delete(lis1, LList.makeList(argsArray, 0), Scheme.isEq);
        if (any$V(null$Mnlist$Qu, gnu_kawa_slib_srfi1_frame37.lists, new Object[0]) != Boolean.FALSE) {
            return LList.Empty;
        }
        return !lists.isNull(gnu_kawa_slib_srfi1_frame37.lists) ? filter(gnu_kawa_slib_srfi1_frame37.lambda$Fn37, lis1) : lis1;
    }

    public static Object lsetIntersection$Ex$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame39 gnu_kawa_slib_srfi1_frame39 = new frame39();
        gnu_kawa_slib_srfi1_frame39.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame39.lists = delete(lis1, LList.makeList(argsArray, 0), Scheme.isEq);
        if (any$V(null$Mnlist$Qu, gnu_kawa_slib_srfi1_frame39.lists, new Object[0]) != Boolean.FALSE) {
            return LList.Empty;
        }
        return !lists.isNull(gnu_kawa_slib_srfi1_frame39.lists) ? filter$Ex(gnu_kawa_slib_srfi1_frame39.lambda$Fn39, lis1) : lis1;
    }

    public static Object lsetDifference$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame41 gnu_kawa_slib_srfi1_frame41 = new frame41();
        gnu_kawa_slib_srfi1_frame41.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame41.lists = filter(lists.pair$Qu, LList.makeList(argsArray, 0));
        if (lists.isNull(gnu_kawa_slib_srfi1_frame41.lists)) {
            return lis1;
        }
        if (lists.memq(lis1, gnu_kawa_slib_srfi1_frame41.lists) != Boolean.FALSE) {
            return LList.Empty;
        }
        return filter(gnu_kawa_slib_srfi1_frame41.lambda$Fn41, lis1);
    }

    public static Object lsetDifference$Ex$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame43 gnu_kawa_slib_srfi1_frame43 = new frame43();
        gnu_kawa_slib_srfi1_frame43.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame43.lists = filter(lists.pair$Qu, LList.makeList(argsArray, 0));
        if (lists.isNull(gnu_kawa_slib_srfi1_frame43.lists)) {
            return lis1;
        }
        if (lists.memq(lis1, gnu_kawa_slib_srfi1_frame43.lists) != Boolean.FALSE) {
            return LList.Empty;
        }
        return filter$Ex(gnu_kawa_slib_srfi1_frame43.lambda$Fn43, lis1);
    }

    public static Object lsetXor$V(Procedure $Eq, Object[] argsArray) {
        frame45 gnu_kawa_slib_srfi1_frame45 = new frame45();
        gnu_kawa_slib_srfi1_frame45.$Eq = $Eq;
        return reduce(gnu_kawa_slib_srfi1_frame45.lambda$Fn45, LList.Empty, LList.makeList(argsArray, 0));
    }

    public static Object lsetXor$Ex$V(Procedure $Eq, Object[] argsArray) {
        frame48 gnu_kawa_slib_srfi1_frame48 = new frame48();
        gnu_kawa_slib_srfi1_frame48.$Eq = $Eq;
        return reduce(gnu_kawa_slib_srfi1_frame48.lambda$Fn49, LList.Empty, LList.makeList(argsArray, 0));
    }

    public static Object lsetDiff$PlIntersection$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame51 gnu_kawa_slib_srfi1_frame51 = new frame51();
        gnu_kawa_slib_srfi1_frame51.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame51.lists = LList.makeList(argsArray, 0);
        if (every$V(null$Mnlist$Qu, gnu_kawa_slib_srfi1_frame51.lists, new Object[0]) != Boolean.FALSE) {
            return misc.values(lis1, LList.Empty);
        } else if (lists.memq(lis1, gnu_kawa_slib_srfi1_frame51.lists) == Boolean.FALSE) {
            return partition(gnu_kawa_slib_srfi1_frame51.lambda$Fn53, lis1);
        } else {
            return misc.values(LList.Empty, lis1);
        }
    }

    public static Object lsetDiff$PlIntersection$Ex$V(Procedure $Eq, Object lis1, Object[] argsArray) {
        frame53 gnu_kawa_slib_srfi1_frame53 = new frame53();
        gnu_kawa_slib_srfi1_frame53.$Eq = $Eq;
        gnu_kawa_slib_srfi1_frame53.lists = LList.makeList(argsArray, 0);
        if (every$V(null$Mnlist$Qu, gnu_kawa_slib_srfi1_frame53.lists, new Object[0]) != Boolean.FALSE) {
            return misc.values(lis1, LList.Empty);
        } else if (lists.memq(lis1, gnu_kawa_slib_srfi1_frame53.lists) == Boolean.FALSE) {
            return partition$Ex(gnu_kawa_slib_srfi1_frame53.lambda$Fn55, lis1);
        } else {
            return misc.values(LList.Empty, lis1);
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        int length;
        Object[] objArr2;
        Procedure procedure;
        Object obj4;
        int length2;
        Object[] objArr3;
        Procedure procedure2;
        Procedure procedure3;
        Object obj5;
        switch (moduleMethod.selector) {
            case 79:
                obj3 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return makeList$V(obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 81:
                return cons$St(objArr);
            case 86:
                obj3 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return circularList$V(obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 92:
                obj3 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return list$Eq$V(obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 94:
                obj3 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return zip$V(obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 117:
                return append$Ex$V(objArr);
            case 122:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return count$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "count", 1, obj);
                }
            case 123:
                int length3 = objArr.length - 4;
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj2 = objArr[1];
                    try {
                        procedure2 = (Procedure) obj2;
                        obj3 = objArr[2];
                        try {
                            procedure3 = (Procedure) obj3;
                            obj5 = objArr[3];
                            if (length3 <= 0) {
                                return unfoldRight(procedure, procedure2, procedure3, obj5);
                            }
                            length3--;
                            return unfoldRight(procedure, procedure2, procedure3, obj5, objArr[4]);
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "unfold-right", 3, obj3);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "unfold-right", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "unfold-right", 1, obj);
                }
            case 125:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj2 = objArr[1];
                    try {
                        procedure2 = (Procedure) obj2;
                        obj3 = objArr[2];
                        try {
                            procedure3 = (Procedure) obj3;
                            Object obj6 = objArr[3];
                            int length4 = objArr.length - 4;
                            Object[] objArr4 = new Object[length4];
                            while (true) {
                                length4--;
                                if (length4 < 0) {
                                    return unfold$V(procedure, procedure2, procedure3, obj6, objArr4);
                                }
                                objArr4[length4] = objArr[length4 + 4];
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "unfold", 3, obj3);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "unfold", 2, obj2);
                    }
                } catch (ClassCastException e32) {
                    throw new WrongType(e32, "unfold", 1, obj);
                }
            case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    obj5 = objArr[2];
                    length2 = objArr.length - 3;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return fold$V(procedure, obj4, obj5, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                } catch (ClassCastException e322) {
                    throw new WrongType(e322, "fold", 1, obj);
                }
            case TransportMediator.KEYCODE_MEDIA_PAUSE /*127*/:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    obj5 = objArr[2];
                    length2 = objArr.length - 3;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return foldRight$V(procedure, obj4, obj5, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                } catch (ClassCastException e3222) {
                    throw new WrongType(e3222, "fold-right", 1, obj);
                }
            case 128:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    obj5 = objArr[2];
                    length2 = objArr.length - 3;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return pairFoldRight$V(procedure, obj4, obj5, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                } catch (ClassCastException e32222) {
                    throw new WrongType(e32222, "pair-fold-right", 1, obj);
                }
            case 129:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    obj5 = objArr[2];
                    length2 = objArr.length - 3;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return pairFold$V(procedure, obj4, obj5, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                } catch (ClassCastException e322222) {
                    throw new WrongType(e322222, "pair-fold", 1, obj);
                }
            case 132:
                obj3 = objArr[0];
                obj4 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return appendMap$V(obj3, obj4, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 133:
                obj3 = objArr[0];
                obj4 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return appendMap$Ex$V(obj3, obj4, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 134:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return pairForEach$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e3222222) {
                    throw new WrongType(e3222222, "pair-for-each", 1, obj);
                }
            case 135:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return map$Ex$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e32222222) {
                    throw new WrongType(e32222222, "map!", 1, obj);
                }
            case 136:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return filterMap$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222) {
                    throw new WrongType(e322222222, "filter-map", 1, obj);
                }
            case 166:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return any$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e3222222222) {
                    throw new WrongType(e3222222222, "any", 1, obj);
                }
            case YaVersion.YOUNG_ANDROID_VERSION /*167*/:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return every$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e32222222222) {
                    throw new WrongType(e32222222222, "every", 1, obj);
                }
            case 168:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return listIndex$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222222) {
                    throw new WrongType(e322222222222, "list-index", 1, obj);
                }
            case 169:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lset$Ls$Eq$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e3222222222222) {
                    throw new WrongType(e3222222222222, "lset<=", 1, obj);
                }
            case 170:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lset$Eq$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e32222222222222) {
                    throw new WrongType(e32222222222222, "lset=", 1, obj);
                }
            case 171:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetAdjoin$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222222222) {
                    throw new WrongType(e322222222222222, "lset-adjoin", 1, obj);
                }
            case 172:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetUnion$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e3222222222222222) {
                    throw new WrongType(e3222222222222222, "lset-union", 1, obj);
                }
            case 173:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetUnion$Ex$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e32222222222222222) {
                    throw new WrongType(e32222222222222222, "lset-union!", 1, obj);
                }
            case 174:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetIntersection$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222222222222) {
                    throw new WrongType(e322222222222222222, "lset-intersection", 1, obj);
                }
            case 175:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetIntersection$Ex$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e3222222222222222222) {
                    throw new WrongType(e3222222222222222222, "lset-intersection!", 1, obj);
                }
            case 176:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetDifference$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e32222222222222222222) {
                    throw new WrongType(e32222222222222222222, "lset-difference", 1, obj);
                }
            case 177:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetDifference$Ex$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222222222222222) {
                    throw new WrongType(e322222222222222222222, "lset-difference!", 1, obj);
                }
            case 178:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetXor$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e3222222222222222222222) {
                    throw new WrongType(e3222222222222222222222, "lset-xor", 1, obj);
                }
            case 179:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    length2 = objArr.length - 1;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetXor$Ex$V(procedure, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 1];
                    }
                } catch (ClassCastException e32222222222222222222222) {
                    throw new WrongType(e32222222222222222222222, "lset-xor!", 1, obj);
                }
            case 180:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetDiff$PlIntersection$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e322222222222222222222222) {
                    throw new WrongType(e322222222222222222222222, "lset-diff+intersection", 1, obj);
                }
            case 181:
                obj = objArr[0];
                try {
                    procedure = (Procedure) obj;
                    obj4 = objArr[1];
                    length2 = objArr.length - 2;
                    objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lsetDiff$PlIntersection$Ex$V(procedure, obj4, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 2];
                    }
                } catch (ClassCastException e3222222222222222222222222) {
                    throw new WrongType(e3222222222222222222222222, "lset-diff+intersection!", 1, obj);
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
