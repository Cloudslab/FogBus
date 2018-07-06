package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.ScreenDensityUtil;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.BitwiseOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.DateTime;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.Telnet;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

/* compiled from: srfi13.scm */
public class srfi13 extends ModuleBody {
    public static final ModuleMethod $Pccheck$Mnbounds;
    public static final ModuleMethod $Pcfinish$Mnstring$Mnconcatenate$Mnreverse;
    public static final ModuleMethod $Pckmp$Mnsearch;
    public static final ModuleMethod $Pcmultispan$Mnrepcopy$Ex;
    public static final ModuleMethod $Pcstring$Mncompare;
    public static final ModuleMethod $Pcstring$Mncompare$Mnci;
    public static final ModuleMethod $Pcstring$Mncopy$Ex;
    public static final ModuleMethod $Pcstring$Mnhash;
    public static final ModuleMethod $Pcstring$Mnmap;
    public static final ModuleMethod $Pcstring$Mnmap$Ex;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnci$Qu;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnlength;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnlength$Mnci;
    public static final ModuleMethod $Pcstring$Mnprefix$Qu;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnci$Qu;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength$Mnci;
    public static final ModuleMethod $Pcstring$Mnsuffix$Qu;
    public static final ModuleMethod $Pcstring$Mntitlecase$Ex;
    public static final ModuleMethod $Pcsubstring$Slshared;
    public static final srfi13 $instance = new srfi13();
    static final IntNum Lit0 = IntNum.make(0);
    static final IntNum Lit1 = IntNum.make(1);
    static final IntNum Lit10 = IntNum.make(4194304);
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("string-hash-ci").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("string-upcase").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("string-upcase!").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("string-downcase").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("string-downcase!").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("%string-titlecase!").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("string-titlecase!").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("string-titlecase").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("string-take").readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("string-take-right").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("whitespace").readResolve());
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("string-drop").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("string-drop-right").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("string-trim").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("string-trim-right").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("string-trim-both").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("string-pad-right").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("string-pad").readResolve());
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("string-delete").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("string-filter").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("string-index").readResolve());
    static final Char Lit12 = Char.make(32);
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("string-index-right").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("string-skip").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("string-skip-right").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("string-count").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("string-fill!").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("string-copy!").readResolve());
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("%string-copy!").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("string-contains").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("string-contains-ci").readResolve());
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("%kmp-search").readResolve());
    static final IntNum Lit13 = IntNum.make(-1);
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("make-kmp-restart-vector").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("kmp-step").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("string-kmp-partial-search").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("string-null?").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("string-reverse").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("string-reverse!").readResolve());
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("reverse-list->string").readResolve());
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("string->list").readResolve());
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("string-append/shared").readResolve());
    static final SimpleSymbol Lit139 = ((SimpleSymbol) new SimpleSymbol("string-concatenate/shared").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("graphic").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("string-concatenate").readResolve());
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("string-concatenate-reverse").readResolve());
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("string-concatenate-reverse/shared").readResolve());
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("%finish-string-concatenate-reverse").readResolve());
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("string-replace").readResolve());
    static final SimpleSymbol Lit145 = ((SimpleSymbol) new SimpleSymbol("string-tokenize").readResolve());
    static final SimpleSymbol Lit146 = ((SimpleSymbol) new SimpleSymbol("xsubstring").readResolve());
    static final SimpleSymbol Lit147 = ((SimpleSymbol) new SimpleSymbol("string-xcopy!").readResolve());
    static final SimpleSymbol Lit148 = ((SimpleSymbol) new SimpleSymbol("%multispan-repcopy!").readResolve());
    static final SimpleSymbol Lit149 = ((SimpleSymbol) new SimpleSymbol("string-join").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("infix").readResolve());
    static final SimpleSymbol Lit150 = ((SimpleSymbol) new SimpleSymbol("receive").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("strict-infix").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("prefix").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("suffix").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("check-arg").readResolve());
    static final IntNum Lit2 = IntNum.make(4096);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol(":optional").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("let-optionals*").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("base").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("make-final").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("char-set?").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("char-set-contains?").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("bound").readResolve());
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("char-cased?").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("criterion").readResolve());
    static final IntNum Lit3 = IntNum.make(40);
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("char-set").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("c=").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("start").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("end").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("p-start").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("s-start").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("s-end").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("final").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("token-chars").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("delim").readResolve());
    static final IntNum Lit4 = IntNum.make(4096);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("grammar").readResolve());
    static final SimpleSymbol Lit41;
    static final SyntaxRules Lit42;
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("let-string-start+end2").readResolve());
    static final SyntaxRules Lit44;
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("string-parse-start+end").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("%check-bounds").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("string-parse-final-start+end").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("substring-spec-ok?").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("check-substring-spec").readResolve());
    static final IntNum Lit5 = IntNum.make(65536);
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("substring/shared").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("%substring/shared").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("string-copy").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("string-map").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("%string-map").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("string-map!").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("%string-map!").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("string-fold").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("string-fold-right").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("string-unfold").readResolve());
    static final IntNum Lit6 = IntNum.make(37);
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("string-unfold-right").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("string-for-each").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("string-for-each-index").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("string-every").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("string-any").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("string-tabulate").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("%string-prefix-length").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("%string-suffix-length").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("%string-prefix-length-ci").readResolve());
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("%string-suffix-length-ci").readResolve());
    static final IntNum Lit7 = IntNum.make(4194304);
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("string-prefix-length").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("string-suffix-length").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("string-prefix-length-ci").readResolve());
    static final SimpleSymbol Lit73 = ((SimpleSymbol) new SimpleSymbol("string-suffix-length-ci").readResolve());
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("string-prefix?").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("string-suffix?").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("string-prefix-ci?").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("string-suffix-ci?").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("%string-prefix?").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("%string-suffix?").readResolve());
    static final IntNum Lit8 = IntNum.make(4194304);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("%string-prefix-ci?").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("%string-suffix-ci?").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("%string-compare").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("%string-compare-ci").readResolve());
    static final SimpleSymbol Lit84 = ((SimpleSymbol) new SimpleSymbol("string-compare").readResolve());
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("string-compare-ci").readResolve());
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("string=").readResolve());
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("string<>").readResolve());
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("string<").readResolve());
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("string>").readResolve());
    static final IntNum Lit9 = IntNum.make(4194304);
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("string<=").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("string>=").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("string-ci=").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("string-ci<>").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("string-ci<").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("string-ci>").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("string-ci<=").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("string-ci>=").readResolve());
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("%string-hash").readResolve());
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("string-hash").readResolve());
    public static final ModuleMethod check$Mnsubstring$Mnspec;
    public static final ModuleMethod kmp$Mnstep;
    static final ModuleMethod lambda$Fn100;
    static final ModuleMethod lambda$Fn105;
    static final ModuleMethod lambda$Fn106;
    static final ModuleMethod lambda$Fn111;
    static final ModuleMethod lambda$Fn116;
    static final ModuleMethod lambda$Fn117;
    static final ModuleMethod lambda$Fn122;
    static final ModuleMethod lambda$Fn123;
    static final ModuleMethod lambda$Fn128;
    static final ModuleMethod lambda$Fn133;
    static final ModuleMethod lambda$Fn138;
    static final ModuleMethod lambda$Fn163;
    static final ModuleMethod lambda$Fn166;
    static final ModuleMethod lambda$Fn17;
    static final ModuleMethod lambda$Fn18;
    static final ModuleMethod lambda$Fn210;
    static final ModuleMethod lambda$Fn216;
    static final ModuleMethod lambda$Fn220;
    static final ModuleMethod lambda$Fn27;
    static final ModuleMethod lambda$Fn5;
    static final ModuleMethod lambda$Fn72;
    static final ModuleMethod lambda$Fn73;
    static final ModuleMethod lambda$Fn78;
    static final ModuleMethod lambda$Fn83;
    static final ModuleMethod lambda$Fn84;
    static final ModuleMethod lambda$Fn89;
    static final ModuleMethod lambda$Fn90;
    static final ModuleMethod lambda$Fn95;
    public static final Macro let$Mnstring$Mnstart$Plend = Macro.make(Lit41, Lit42, $instance);
    public static final Macro let$Mnstring$Mnstart$Plend2 = Macro.make(Lit43, Lit44, $instance);
    static final Location loc$$Cloptional = ThreadLocation.getInstance(Lit20, null);
    static final Location loc$base = ThreadLocation.getInstance(Lit22, null);
    static final Location loc$bound = ThreadLocation.getInstance(Lit26, null);
    static final Location loc$c$Eq = ThreadLocation.getInstance(Lit31, null);
    static final Location loc$char$Mncased$Qu = ThreadLocation.getInstance(Lit28, null);
    static final Location loc$char$Mnset = ThreadLocation.getInstance(Lit30, null);
    static final Location loc$char$Mnset$Mncontains$Qu = ThreadLocation.getInstance(Lit25, null);
    static final Location loc$char$Mnset$Qu = ThreadLocation.getInstance(Lit24, null);
    static final Location loc$check$Mnarg = ThreadLocation.getInstance(Lit19, null);
    static final Location loc$criterion = ThreadLocation.getInstance(Lit29, null);
    static final Location loc$delim = ThreadLocation.getInstance(Lit39, null);
    static final Location loc$end = ThreadLocation.getInstance(Lit33, null);
    static final Location loc$final = ThreadLocation.getInstance(Lit37, null);
    static final Location loc$grammar = ThreadLocation.getInstance(Lit40, null);
    static final Location loc$let$Mnoptionals$St = ThreadLocation.getInstance(Lit21, null);
    static final Location loc$make$Mnfinal = ThreadLocation.getInstance(Lit23, null);
    static final Location loc$p$Mnstart = ThreadLocation.getInstance(Lit34, null);
    static final Location loc$rest = ThreadLocation.getInstance(Lit27, null);
    static final Location loc$s$Mnend = ThreadLocation.getInstance(Lit36, null);
    static final Location loc$s$Mnstart = ThreadLocation.getInstance(Lit35, null);
    static final Location loc$start = ThreadLocation.getInstance(Lit32, null);
    static final Location loc$token$Mnchars = ThreadLocation.getInstance(Lit38, null);
    public static final ModuleMethod make$Mnkmp$Mnrestart$Mnvector;
    public static final ModuleMethod reverse$Mnlist$Mn$Grstring;
    public static final ModuleMethod string$Eq;
    public static final ModuleMethod string$Gr;
    public static final ModuleMethod string$Gr$Eq;
    public static final ModuleMethod string$Ls;
    public static final ModuleMethod string$Ls$Eq;
    public static final ModuleMethod string$Ls$Gr;
    public static final ModuleMethod string$Mn$Grlist;
    public static final ModuleMethod string$Mnany;
    public static final ModuleMethod string$Mnappend$Slshared;
    public static final ModuleMethod string$Mnci$Eq;
    public static final ModuleMethod string$Mnci$Gr;
    public static final ModuleMethod string$Mnci$Gr$Eq;
    public static final ModuleMethod string$Mnci$Ls;
    public static final ModuleMethod string$Mnci$Ls$Eq;
    public static final ModuleMethod string$Mnci$Ls$Gr;
    public static final ModuleMethod string$Mncompare;
    public static final ModuleMethod string$Mncompare$Mnci;
    public static final ModuleMethod string$Mnconcatenate;
    public static final ModuleMethod string$Mnconcatenate$Mnreverse;
    public static final ModuleMethod string$Mnconcatenate$Mnreverse$Slshared;
    public static final ModuleMethod string$Mnconcatenate$Slshared;
    public static final ModuleMethod string$Mncontains;
    public static final ModuleMethod string$Mncontains$Mnci;
    public static final ModuleMethod string$Mncopy;
    public static final ModuleMethod string$Mncopy$Ex;
    public static final ModuleMethod string$Mncount;
    public static final ModuleMethod string$Mndelete;
    public static final ModuleMethod string$Mndowncase;
    public static final ModuleMethod string$Mndowncase$Ex;
    public static final ModuleMethod string$Mndrop;
    public static final ModuleMethod string$Mndrop$Mnright;
    public static final ModuleMethod string$Mnevery;
    public static final ModuleMethod string$Mnfill$Ex;
    public static final ModuleMethod string$Mnfilter;
    public static final ModuleMethod string$Mnfold;
    public static final ModuleMethod string$Mnfold$Mnright;
    public static final ModuleMethod string$Mnfor$Mneach;
    public static final ModuleMethod string$Mnfor$Mneach$Mnindex;
    public static final ModuleMethod string$Mnhash;
    public static final ModuleMethod string$Mnhash$Mnci;
    public static final ModuleMethod string$Mnindex;
    public static final ModuleMethod string$Mnindex$Mnright;
    public static final ModuleMethod string$Mnjoin;
    public static final ModuleMethod string$Mnkmp$Mnpartial$Mnsearch;
    public static final ModuleMethod string$Mnmap;
    public static final ModuleMethod string$Mnmap$Ex;
    public static final ModuleMethod string$Mnnull$Qu;
    public static final ModuleMethod string$Mnpad;
    public static final ModuleMethod string$Mnpad$Mnright;
    public static final ModuleMethod string$Mnparse$Mnfinal$Mnstart$Plend;
    public static final ModuleMethod string$Mnparse$Mnstart$Plend;
    public static final ModuleMethod string$Mnprefix$Mnci$Qu;
    public static final ModuleMethod string$Mnprefix$Mnlength;
    public static final ModuleMethod string$Mnprefix$Mnlength$Mnci;
    public static final ModuleMethod string$Mnprefix$Qu;
    public static final ModuleMethod string$Mnreplace;
    public static final ModuleMethod string$Mnreverse;
    public static final ModuleMethod string$Mnreverse$Ex;
    public static final ModuleMethod string$Mnskip;
    public static final ModuleMethod string$Mnskip$Mnright;
    public static final ModuleMethod string$Mnsuffix$Mnci$Qu;
    public static final ModuleMethod string$Mnsuffix$Mnlength;
    public static final ModuleMethod string$Mnsuffix$Mnlength$Mnci;
    public static final ModuleMethod string$Mnsuffix$Qu;
    public static final ModuleMethod string$Mntabulate;
    public static final ModuleMethod string$Mntake;
    public static final ModuleMethod string$Mntake$Mnright;
    public static final ModuleMethod string$Mntitlecase;
    public static final ModuleMethod string$Mntitlecase$Ex;
    public static final ModuleMethod string$Mntokenize;
    public static final ModuleMethod string$Mntrim;
    public static final ModuleMethod string$Mntrim$Mnboth;
    public static final ModuleMethod string$Mntrim$Mnright;
    public static final ModuleMethod string$Mnunfold;
    public static final ModuleMethod string$Mnunfold$Mnright;
    public static final ModuleMethod string$Mnupcase;
    public static final ModuleMethod string$Mnupcase$Ex;
    public static final ModuleMethod string$Mnxcopy$Ex;
    public static final ModuleMethod substring$Mnspec$Mnok$Qu;
    public static final ModuleMethod substring$Slshared;
    public static final ModuleMethod xsubstring;

    /* compiled from: srfi13.scm */
    public class frame0 extends ModuleBody {
        Object args;
        final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn4;
        Object proc;
        Object f51s;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 4, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:174");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 3 ? lambda3() : super.apply0(moduleMethod);
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
            return moduleMethod.selector == 4 ? lambda4(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda3() {
            return srfi13.stringParseStart$PlEnd(this.proc, this.f51s, this.args);
        }

        Object lambda4(Object rest, Object start, Object end) {
            if (lists.isPair(rest)) {
                return misc.error$V("Extra arguments to procedure", new Object[]{this.proc, rest});
            }
            return misc.values(start, end);
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

    /* compiled from: srfi13.scm */
    public class frame10 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn25 = new ModuleMethod(this, 22, null, 0);
        final ModuleMethod lambda$Fn26 = new ModuleMethod(this, 23, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f52s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 22 ? lambda25() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 23 ? lambda26(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 22) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda25() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnany, this.f52s, this.maybe$Mnstart$Plend);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda26(java.lang.Object r14, java.lang.Object r15) {
            /*
            r13 = this;
            r12 = -2;
            r11 = 2;
            r10 = 1;
            r5 = r13.criterion;
            r5 = kawa.lib.characters.isChar(r5);
            if (r5 == 0) goto L_0x0054;
        L_0x000b:
            r2 = r14;
        L_0x000c:
            r5 = kawa.standard.Scheme.numLss;
            r6 = r5.apply2(r2, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0126 }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x0126 }
            if (r4 == 0) goto L_0x004c;
        L_0x001c:
            r5 = r13.criterion;
            r5 = (gnu.text.Char) r5;	 Catch:{ ClassCastException -> 0x012f }
            r6 = r13.f52s;
            r6 = (java.lang.CharSequence) r6;	 Catch:{ ClassCastException -> 0x0138 }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0141 }
            r7 = r0;
            r7 = r7.intValue();	 Catch:{ ClassCastException -> 0x0141 }
            r6 = kawa.lib.strings.stringRef(r6, r7);
            r6 = gnu.text.Char.make(r6);
            r4 = kawa.lib.characters.isChar$Eq(r5, r6);
            if (r4 == 0) goto L_0x0043;
        L_0x003a:
            if (r4 == 0) goto L_0x0040;
        L_0x003c:
            r5 = java.lang.Boolean.TRUE;
        L_0x003e:
            r4 = r5;
        L_0x003f:
            return r4;
        L_0x0040:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x003e;
        L_0x0043:
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r2 = r5.apply2(r2, r6);
            goto L_0x000c;
        L_0x004c:
            if (r4 == 0) goto L_0x0051;
        L_0x004e:
            r5 = java.lang.Boolean.TRUE;
            goto L_0x003e;
        L_0x0051:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x003e;
        L_0x0054:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = gnu.kawa.slib.srfi13.loc$char$Mnset$Qu;
            r6 = r6.get();	 Catch:{ UnboundLocationException -> 0x014a }
            r7 = r13.criterion;
            r5 = r5.apply2(r6, r7);
            r6 = java.lang.Boolean.FALSE;
            if (r5 == r6) goto L_0x00ae;
        L_0x0066:
            r2 = r14;
        L_0x0067:
            r5 = kawa.standard.Scheme.numLss;
            r6 = r5.apply2(r2, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0154 }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x0154 }
            if (r4 == 0) goto L_0x00a6;
        L_0x0077:
            r7 = kawa.standard.Scheme.applyToArgs;
            r5 = gnu.kawa.slib.srfi13.loc$char$Mnset$Mncontains$Qu;
            r8 = r5.get();	 Catch:{ UnboundLocationException -> 0x015d }
            r9 = r13.criterion;
            r5 = r13.f52s;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x0168 }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0171 }
            r6 = r0;
            r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x0171 }
            r5 = kawa.lib.strings.stringRef(r5, r6);
            r5 = gnu.text.Char.make(r5);
            r4 = r7.apply3(r8, r9, r5);
            r5 = java.lang.Boolean.FALSE;
            if (r4 != r5) goto L_0x003f;
        L_0x009d:
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r2 = r5.apply2(r2, r6);
            goto L_0x0067;
        L_0x00a6:
            if (r4 == 0) goto L_0x00ab;
        L_0x00a8:
            r4 = java.lang.Boolean.TRUE;
            goto L_0x003f;
        L_0x00ab:
            r4 = java.lang.Boolean.FALSE;
            goto L_0x003f;
        L_0x00ae:
            r5 = r13.criterion;
            r5 = kawa.lib.misc.isProcedure(r5);
            if (r5 == 0) goto L_0x0113;
        L_0x00b6:
            r5 = kawa.standard.Scheme.numLss;
            r6 = r5.apply2(r14, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x017a }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x017a }
            if (r4 == 0) goto L_0x0109;
        L_0x00c6:
            r2 = r14;
        L_0x00c7:
            r5 = r13.f52s;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x0183 }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x018c }
            r6 = r0;
            r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x018c }
            r1 = kawa.lib.strings.stringRef(r5, r6);
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r3 = r5.apply2(r2, r6);
            r5 = kawa.standard.Scheme.numEqu;
            r5 = r5.apply2(r3, r15);
            r6 = java.lang.Boolean.FALSE;
            if (r5 == r6) goto L_0x00f7;
        L_0x00e9:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = r13.criterion;
            r7 = gnu.text.Char.make(r1);
            r4 = r5.apply2(r6, r7);
            goto L_0x003f;
        L_0x00f7:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = r13.criterion;
            r7 = gnu.text.Char.make(r1);
            r4 = r5.apply2(r6, r7);
            r5 = java.lang.Boolean.FALSE;
            if (r4 != r5) goto L_0x003f;
        L_0x0107:
            r2 = r3;
            goto L_0x00c7;
        L_0x0109:
            if (r4 == 0) goto L_0x010f;
        L_0x010b:
            r4 = java.lang.Boolean.TRUE;
            goto L_0x003f;
        L_0x010f:
            r4 = java.lang.Boolean.FALSE;
            goto L_0x003f;
        L_0x0113:
            r5 = "Second param is neither char-set, char, or predicate procedure.";
            r6 = new java.lang.Object[r11];
            r7 = 0;
            r8 = gnu.kawa.slib.srfi13.string$Mnany;
            r6[r7] = r8;
            r7 = r13.criterion;
            r6[r10] = r7;
            r4 = kawa.lib.misc.error$V(r5, r6);
            goto L_0x003f;
        L_0x0126:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x012f:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "char=?";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x0138:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r10, r6);
            throw r7;
        L_0x0141:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
        L_0x014a:
            r5 = move-exception;
            r6 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
            r7 = 515; // 0x203 float:7.22E-43 double:2.544E-321;
            r8 = 5;
            r5.setLine(r6, r7, r8);
            throw r5;
        L_0x0154:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x015d:
            r5 = move-exception;
            r6 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
            r7 = 518; // 0x206 float:7.26E-43 double:2.56E-321;
            r8 = 9;
            r5.setLine(r6, r7, r8);
            throw r5;
        L_0x0168:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x0171:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
        L_0x017a:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x0183:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x018c:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.frame10.lambda26(java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: srfi13.scm */
    public class frame11 extends ModuleBody {
        final ModuleMethod lambda$Fn28 = new ModuleMethod(this, 26, null, 0);
        final ModuleMethod lambda$Fn29 = new ModuleMethod(this, 27, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 26 ? lambda28() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 27 ? lambda29(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 26) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 27) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda28() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda29(Object rest, Object start1, Object end1) {
            frame12 gnu_kawa_slib_srfi13_frame12 = new frame12();
            gnu_kawa_slib_srfi13_frame12.staticLink = this;
            gnu_kawa_slib_srfi13_frame12.rest = rest;
            gnu_kawa_slib_srfi13_frame12.start1 = start1;
            gnu_kawa_slib_srfi13_frame12.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame12.lambda$Fn30, gnu_kawa_slib_srfi13_frame12.lambda$Fn31);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame12 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn30 = new ModuleMethod(this, 24, null, 0);
        final ModuleMethod lambda$Fn31 = new ModuleMethod(this, 25, null, 8194);
        Object rest;
        Object start1;
        frame11 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 24 ? lambda30() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 25 ? lambda31(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 24) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda30() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.staticLink.s2, this.rest);
        }

        Object lambda31(Object start2, Object end2) {
            return srfi13.$PcStringPrefixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame13 extends ModuleBody {
        final ModuleMethod lambda$Fn32 = new ModuleMethod(this, 30, null, 0);
        final ModuleMethod lambda$Fn33 = new ModuleMethod(this, 31, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 30 ? lambda32() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 31 ? lambda33(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 30) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 31) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda32() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda33(Object rest, Object start1, Object end1) {
            frame14 gnu_kawa_slib_srfi13_frame14 = new frame14();
            gnu_kawa_slib_srfi13_frame14.staticLink = this;
            gnu_kawa_slib_srfi13_frame14.rest = rest;
            gnu_kawa_slib_srfi13_frame14.start1 = start1;
            gnu_kawa_slib_srfi13_frame14.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame14.lambda$Fn34, gnu_kawa_slib_srfi13_frame14.lambda$Fn35);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame14 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn34 = new ModuleMethod(this, 28, null, 0);
        final ModuleMethod lambda$Fn35 = new ModuleMethod(this, 29, null, 8194);
        Object rest;
        Object start1;
        frame13 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 28 ? lambda34() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 29 ? lambda35(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 28) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda34() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.staticLink.s2, this.rest);
        }

        Object lambda35(Object start2, Object end2) {
            return srfi13.$PcStringSuffixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame15 extends ModuleBody {
        final ModuleMethod lambda$Fn36 = new ModuleMethod(this, 34, null, 0);
        final ModuleMethod lambda$Fn37 = new ModuleMethod(this, 35, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 34 ? lambda36() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 35 ? lambda37(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 34) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 35) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda36() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda37(Object rest, Object start1, Object end1) {
            frame16 gnu_kawa_slib_srfi13_frame16 = new frame16();
            gnu_kawa_slib_srfi13_frame16.staticLink = this;
            gnu_kawa_slib_srfi13_frame16.rest = rest;
            gnu_kawa_slib_srfi13_frame16.start1 = start1;
            gnu_kawa_slib_srfi13_frame16.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame16.lambda$Fn38, gnu_kawa_slib_srfi13_frame16.lambda$Fn39);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame16 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn38 = new ModuleMethod(this, 32, null, 0);
        final ModuleMethod lambda$Fn39 = new ModuleMethod(this, 33, null, 8194);
        Object rest;
        Object start1;
        frame15 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 32 ? lambda38() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 33 ? Integer.valueOf(lambda39(obj, obj2)) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 32) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 33) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda38() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.staticLink.s2, this.rest);
        }

        int lambda39(Object start2, Object end2) {
            Object obj = this.staticLink.s1;
            Object obj2 = this.start1;
            try {
                int intValue = ((Number) obj2).intValue();
                Object obj3 = this.end1;
                try {
                    try {
                        try {
                            return srfi13.$PcStringPrefixLengthCi(obj, intValue, ((Number) obj3).intValue(), this.staticLink.s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-prefix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-prefix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "%string-prefix-length-ci", 2, obj3);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "%string-prefix-length-ci", 1, obj2);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame17 extends ModuleBody {
        final ModuleMethod lambda$Fn40 = new ModuleMethod(this, 38, null, 0);
        final ModuleMethod lambda$Fn41 = new ModuleMethod(this, 39, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 38 ? lambda40() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 39 ? lambda41(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 38) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 39) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda40() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda41(Object rest, Object start1, Object end1) {
            frame18 gnu_kawa_slib_srfi13_frame18 = new frame18();
            gnu_kawa_slib_srfi13_frame18.staticLink = this;
            gnu_kawa_slib_srfi13_frame18.rest = rest;
            gnu_kawa_slib_srfi13_frame18.start1 = start1;
            gnu_kawa_slib_srfi13_frame18.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame18.lambda$Fn42, gnu_kawa_slib_srfi13_frame18.lambda$Fn43);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame18 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn42 = new ModuleMethod(this, 36, null, 0);
        final ModuleMethod lambda$Fn43 = new ModuleMethod(this, 37, null, 8194);
        Object rest;
        Object start1;
        frame17 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 36 ? lambda42() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 37 ? Integer.valueOf(lambda43(obj, obj2)) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 36) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 37) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda42() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.staticLink.s2, this.rest);
        }

        int lambda43(Object start2, Object end2) {
            Object obj = this.staticLink.s1;
            Object obj2 = this.start1;
            try {
                int intValue = ((Number) obj2).intValue();
                Object obj3 = this.end1;
                try {
                    try {
                        try {
                            return srfi13.$PcStringSuffixLengthCi(obj, intValue, ((Number) obj3).intValue(), this.staticLink.s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-suffix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-suffix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "%string-suffix-length-ci", 2, obj3);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "%string-suffix-length-ci", 1, obj2);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame19 extends ModuleBody {
        final ModuleMethod lambda$Fn44 = new ModuleMethod(this, 42, null, 0);
        final ModuleMethod lambda$Fn45 = new ModuleMethod(this, 43, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 42 ? lambda44() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 43 ? lambda45(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 42) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 43) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda44() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda45(Object rest, Object start1, Object end1) {
            frame20 gnu_kawa_slib_srfi13_frame20 = new frame20();
            gnu_kawa_slib_srfi13_frame20.staticLink = this;
            gnu_kawa_slib_srfi13_frame20.rest = rest;
            gnu_kawa_slib_srfi13_frame20.start1 = start1;
            gnu_kawa_slib_srfi13_frame20.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame20.lambda$Fn46, gnu_kawa_slib_srfi13_frame20.lambda$Fn47);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn6;
        int slen;
        Object start;

        public frame1() {
            PropertySet moduleMethod = new ModuleMethod(this, 5, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:227");
            this.lambda$Fn6 = moduleMethod;
        }

        static boolean lambda5(Object start) {
            boolean x = numbers.isInteger(start);
            if (!x) {
                return x;
            }
            x = numbers.isExact(start);
            return x ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, start)).booleanValue() : x;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 5) {
                return lambda6(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda6(Object end) {
            boolean x = numbers.isInteger(end);
            if (!x) {
                return x;
            }
            x = numbers.isExact(end);
            if (!x) {
                return x;
            }
            Object apply2 = Scheme.numLEq.apply2(this.start, end);
            try {
                x = ((Boolean) apply2).booleanValue();
                if (x) {
                    return ((Boolean) Scheme.numLEq.apply2(end, Integer.valueOf(this.slen))).booleanValue();
                }
                return x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame20 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn46 = new ModuleMethod(this, 40, null, 0);
        final ModuleMethod lambda$Fn47 = new ModuleMethod(this, 41, null, 8194);
        Object rest;
        Object start1;
        frame19 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 40 ? lambda46() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 41 ? lambda47(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 40) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 41) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda46() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda47(Object start2, Object end2) {
            return srfi13.$PcStringPrefix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame21 extends ModuleBody {
        final ModuleMethod lambda$Fn48 = new ModuleMethod(this, 46, null, 0);
        final ModuleMethod lambda$Fn49 = new ModuleMethod(this, 47, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 46 ? lambda48() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 47 ? lambda49(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 46) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 47) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda48() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda49(Object rest, Object start1, Object end1) {
            frame22 gnu_kawa_slib_srfi13_frame22 = new frame22();
            gnu_kawa_slib_srfi13_frame22.staticLink = this;
            gnu_kawa_slib_srfi13_frame22.rest = rest;
            gnu_kawa_slib_srfi13_frame22.start1 = start1;
            gnu_kawa_slib_srfi13_frame22.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame22.lambda$Fn50, gnu_kawa_slib_srfi13_frame22.lambda$Fn51);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame22 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn50 = new ModuleMethod(this, 44, null, 0);
        final ModuleMethod lambda$Fn51 = new ModuleMethod(this, 45, null, 8194);
        Object rest;
        Object start1;
        frame21 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 44 ? lambda50() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 45 ? lambda51(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 44) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda50() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda51(Object start2, Object end2) {
            return srfi13.$PcStringSuffix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame23 extends ModuleBody {
        final ModuleMethod lambda$Fn52 = new ModuleMethod(this, 50, null, 0);
        final ModuleMethod lambda$Fn53 = new ModuleMethod(this, 51, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 50 ? lambda52() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 51 ? lambda53(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 50) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 51) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda52() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda53(Object rest, Object start1, Object end1) {
            frame24 gnu_kawa_slib_srfi13_frame24 = new frame24();
            gnu_kawa_slib_srfi13_frame24.staticLink = this;
            gnu_kawa_slib_srfi13_frame24.rest = rest;
            gnu_kawa_slib_srfi13_frame24.start1 = start1;
            gnu_kawa_slib_srfi13_frame24.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame24.lambda$Fn54, gnu_kawa_slib_srfi13_frame24.lambda$Fn55);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame24 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn54 = new ModuleMethod(this, 48, null, 0);
        final ModuleMethod lambda$Fn55 = new ModuleMethod(this, 49, null, 8194);
        Object rest;
        Object start1;
        frame23 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 48 ? lambda54() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 49 ? lambda55(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 48) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda54() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda55(Object start2, Object end2) {
            return srfi13.$PcStringPrefixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame25 extends ModuleBody {
        final ModuleMethod lambda$Fn56 = new ModuleMethod(this, 54, null, 0);
        final ModuleMethod lambda$Fn57 = new ModuleMethod(this, 55, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 54 ? lambda56() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 55 ? lambda57(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 54) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 55) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda56() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda57(Object rest, Object start1, Object end1) {
            frame26 gnu_kawa_slib_srfi13_frame26 = new frame26();
            gnu_kawa_slib_srfi13_frame26.staticLink = this;
            gnu_kawa_slib_srfi13_frame26.rest = rest;
            gnu_kawa_slib_srfi13_frame26.start1 = start1;
            gnu_kawa_slib_srfi13_frame26.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame26.lambda$Fn58, gnu_kawa_slib_srfi13_frame26.lambda$Fn59);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame26 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn58 = new ModuleMethod(this, 52, null, 0);
        final ModuleMethod lambda$Fn59 = new ModuleMethod(this, 53, null, 8194);
        Object rest;
        Object start1;
        frame25 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 52 ? lambda58() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 53 ? lambda59(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 52) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 53) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda58() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda59(Object start2, Object end2) {
            return srfi13.$PcStringSuffixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame27 extends ModuleBody {
        final ModuleMethod lambda$Fn60 = new ModuleMethod(this, 58, null, 0);
        final ModuleMethod lambda$Fn61 = new ModuleMethod(this, 59, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object proc$Eq;
        Object proc$Gr;
        Object proc$Ls;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 58 ? lambda60() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 59 ? lambda61(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 58) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 59) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda60() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda61(Object rest, Object start1, Object end1) {
            frame28 gnu_kawa_slib_srfi13_frame28 = new frame28();
            gnu_kawa_slib_srfi13_frame28.staticLink = this;
            gnu_kawa_slib_srfi13_frame28.rest = rest;
            gnu_kawa_slib_srfi13_frame28.start1 = start1;
            gnu_kawa_slib_srfi13_frame28.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame28.lambda$Fn62, gnu_kawa_slib_srfi13_frame28.lambda$Fn63);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame28 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn62 = new ModuleMethod(this, 56, null, 0);
        final ModuleMethod lambda$Fn63 = new ModuleMethod(this, 57, null, 8194);
        Object rest;
        Object start1;
        frame27 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 56 ? lambda62() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 57 ? lambda63(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 56) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 57) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda62() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare, this.staticLink.s2, this.rest);
        }

        Object lambda63(Object start2, Object end2) {
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame29 extends ModuleBody {
        final ModuleMethod lambda$Fn64 = new ModuleMethod(this, 62, null, 0);
        final ModuleMethod lambda$Fn65 = new ModuleMethod(this, 63, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object proc$Eq;
        Object proc$Gr;
        Object proc$Ls;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 62 ? lambda64() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 63 ? lambda65(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 62) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 63) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda64() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda65(Object rest, Object start1, Object end1) {
            frame30 gnu_kawa_slib_srfi13_frame30 = new frame30();
            gnu_kawa_slib_srfi13_frame30.staticLink = this;
            gnu_kawa_slib_srfi13_frame30.rest = rest;
            gnu_kawa_slib_srfi13_frame30.start1 = start1;
            gnu_kawa_slib_srfi13_frame30.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame30.lambda$Fn66, gnu_kawa_slib_srfi13_frame30.lambda$Fn67);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 6, null, 0);
        final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 7, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f53s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 6 ? lambda7() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 7 ? lambda8(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 6) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 7) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda7() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncopy, this.f53s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda8(Object start, Object end) {
            Object obj = this.f53s;
            try {
                try {
                    try {
                        return strings.substring((CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, end);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, start);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame30 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn66 = new ModuleMethod(this, 60, null, 0);
        final ModuleMethod lambda$Fn67 = new ModuleMethod(this, 61, null, 8194);
        Object rest;
        Object start1;
        frame29 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 60 ? lambda66() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 61 ? lambda67(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 60) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 61) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda66() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare$Mnci, this.staticLink.s2, this.rest);
        }

        Object lambda67(Object start2, Object end2) {
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame31 extends ModuleBody {
        final ModuleMethod lambda$Fn68 = new ModuleMethod(this, 66, null, 0);
        final ModuleMethod lambda$Fn69 = new ModuleMethod(this, 67, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 66 ? lambda68() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 67 ? lambda69(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 66) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 67) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda68() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda69(Object rest, Object start1, Object end1) {
            frame32 gnu_kawa_slib_srfi13_frame32 = new frame32();
            gnu_kawa_slib_srfi13_frame32.staticLink = this;
            gnu_kawa_slib_srfi13_frame32.rest = rest;
            gnu_kawa_slib_srfi13_frame32.start1 = start1;
            gnu_kawa_slib_srfi13_frame32.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame32.lambda$Fn70, gnu_kawa_slib_srfi13_frame32.lambda$Fn71);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame32 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn70 = new ModuleMethod(this, 64, null, 0);
        final ModuleMethod lambda$Fn71 = new ModuleMethod(this, 65, null, 8194);
        Object rest;
        Object start1;
        frame31 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 64 ? lambda70() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 65 ? lambda71(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 64) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda70() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda71(Object start2, Object end2) {
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x = this.staticLink.s1 == this.staticLink.s2;
                    if (x) {
                        apply2 = Scheme.numEqu.apply2(this.start1, start2);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "x", -2, apply2);
                        }
                    }
                    if (x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn72, misc.values, srfi13.lambda$Fn73);
                    }
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda72(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda73(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame33 extends ModuleBody {
        final ModuleMethod lambda$Fn74 = new ModuleMethod(this, 70, null, 0);
        final ModuleMethod lambda$Fn75 = new ModuleMethod(this, 71, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 70 ? lambda74() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 71 ? lambda75(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 70) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 71) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda74() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda75(Object rest, Object start1, Object end1) {
            frame34 gnu_kawa_slib_srfi13_frame34 = new frame34();
            gnu_kawa_slib_srfi13_frame34.staticLink = this;
            gnu_kawa_slib_srfi13_frame34.rest = rest;
            gnu_kawa_slib_srfi13_frame34.start1 = start1;
            gnu_kawa_slib_srfi13_frame34.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame34.lambda$Fn76, gnu_kawa_slib_srfi13_frame34.lambda$Fn77);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame34 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn76 = new ModuleMethod(this, 68, null, 0);
        final ModuleMethod lambda$Fn77 = new ModuleMethod(this, 69, null, 8194);
        Object rest;
        Object start1;
        frame33 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 68 ? lambda76() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 69 ? lambda77(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 68) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda76() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda77(Object start2, Object end2) {
            int i = 1;
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                int i2;
                if (apply2 != Boolean.FALSE) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                boolean x = (i2 + 1) & 1;
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x = this.staticLink.s1 == this.staticLink.s2;
                    if (x) {
                        apply2 = Scheme.numEqu.apply2(this.start1, start2);
                        try {
                            if (apply2 == Boolean.FALSE) {
                                i = 0;
                            }
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "x", -2, apply2);
                        }
                    }
                    boolean z = x;
                    x = (i + 1) & 1;
                    if (!x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn78, misc.values);
                    }
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda78(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame35 extends ModuleBody {
        final ModuleMethod lambda$Fn79 = new ModuleMethod(this, 74, null, 0);
        final ModuleMethod lambda$Fn80 = new ModuleMethod(this, 75, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 74 ? lambda79() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 75 ? lambda80(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 74) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 75) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda79() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda80(Object rest, Object start1, Object end1) {
            frame36 gnu_kawa_slib_srfi13_frame36 = new frame36();
            gnu_kawa_slib_srfi13_frame36.staticLink = this;
            gnu_kawa_slib_srfi13_frame36.rest = rest;
            gnu_kawa_slib_srfi13_frame36.start1 = start1;
            gnu_kawa_slib_srfi13_frame36.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame36.lambda$Fn81, gnu_kawa_slib_srfi13_frame36.lambda$Fn82);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame36 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn81 = new ModuleMethod(this, 72, null, 0);
        final ModuleMethod lambda$Fn82 = new ModuleMethod(this, 73, null, 8194);
        Object rest;
        Object start1;
        frame35 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 72 ? lambda81() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 73 ? lambda82(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 72) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda81() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls, this.staticLink.s2, this.rest);
        }

        Object lambda82(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numLss.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn83, srfi13.lambda$Fn84);
        }

        static Boolean lambda83(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda84(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame37 extends ModuleBody {
        final ModuleMethod lambda$Fn85 = new ModuleMethod(this, 78, null, 0);
        final ModuleMethod lambda$Fn86 = new ModuleMethod(this, 79, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 78 ? lambda85() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 79 ? lambda86(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 78) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 79) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda85() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda86(Object rest, Object start1, Object end1) {
            frame38 gnu_kawa_slib_srfi13_frame38 = new frame38();
            gnu_kawa_slib_srfi13_frame38.staticLink = this;
            gnu_kawa_slib_srfi13_frame38.rest = rest;
            gnu_kawa_slib_srfi13_frame38.start1 = start1;
            gnu_kawa_slib_srfi13_frame38.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame38.lambda$Fn87, gnu_kawa_slib_srfi13_frame38.lambda$Fn88);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame38 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn87 = new ModuleMethod(this, 76, null, 0);
        final ModuleMethod lambda$Fn88 = new ModuleMethod(this, 77, null, 8194);
        Object rest;
        Object start1;
        frame37 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 76 ? lambda87() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 77 ? lambda88(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 76) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 77) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda87() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda88(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numGrt.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn89, srfi13.lambda$Fn90, misc.values);
        }

        static Boolean lambda89(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda90(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame39 extends ModuleBody {
        final ModuleMethod lambda$Fn91 = new ModuleMethod(this, 82, null, 0);
        final ModuleMethod lambda$Fn92 = new ModuleMethod(this, 83, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 82 ? lambda91() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 83 ? lambda92(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 82) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 83) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda91() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda92(Object rest, Object start1, Object end1) {
            frame40 gnu_kawa_slib_srfi13_frame40 = new frame40();
            gnu_kawa_slib_srfi13_frame40.staticLink = this;
            gnu_kawa_slib_srfi13_frame40.rest = rest;
            gnu_kawa_slib_srfi13_frame40.start1 = start1;
            gnu_kawa_slib_srfi13_frame40.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame40.lambda$Fn93, gnu_kawa_slib_srfi13_frame40.lambda$Fn94);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 9, null, 8194);
        final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 8, null, 0);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object f54s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 8 ? lambda9() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 9 ? lambda10(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda9() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap, this.f54s, this.maybe$Mnstart$Plend);
        }

        Object lambda10(Object start, Object end) {
            return srfi13.$PcStringMap(this.proc, this.f54s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame40 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn93 = new ModuleMethod(this, 80, null, 0);
        final ModuleMethod lambda$Fn94 = new ModuleMethod(this, 81, null, 8194);
        Object rest;
        Object start1;
        frame39 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 80 ? lambda93() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 81 ? lambda94(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 80) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 81) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda93() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda94(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numLEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, misc.values, srfi13.lambda$Fn95);
        }

        static Boolean lambda95(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame41 extends ModuleBody {
        final ModuleMethod lambda$Fn96 = new ModuleMethod(this, 86, null, 0);
        final ModuleMethod lambda$Fn97 = new ModuleMethod(this, 87, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 86 ? lambda96() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 87 ? lambda97(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 86) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 87) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda96() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda97(Object rest, Object start1, Object end1) {
            frame42 gnu_kawa_slib_srfi13_frame42 = new frame42();
            gnu_kawa_slib_srfi13_frame42.staticLink = this;
            gnu_kawa_slib_srfi13_frame42.rest = rest;
            gnu_kawa_slib_srfi13_frame42.start1 = start1;
            gnu_kawa_slib_srfi13_frame42.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame42.lambda$Fn98, gnu_kawa_slib_srfi13_frame42.lambda$Fn99);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame42 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn98 = new ModuleMethod(this, 84, null, 0);
        final ModuleMethod lambda$Fn99 = new ModuleMethod(this, 85, null, 8194);
        Object rest;
        Object start1;
        frame41 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 84 ? lambda98() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 85 ? lambda99(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 84) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 85) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda98() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda99(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numGEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn100, misc.values, misc.values);
        }

        static Boolean lambda100(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame43 extends ModuleBody {
        final ModuleMethod lambda$Fn101 = new ModuleMethod(this, 90, null, 0);
        final ModuleMethod lambda$Fn102 = new ModuleMethod(this, 91, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 90 ? lambda101() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 91 ? lambda102(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 90) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 91) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda101() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda102(Object rest, Object start1, Object end1) {
            frame44 gnu_kawa_slib_srfi13_frame44 = new frame44();
            gnu_kawa_slib_srfi13_frame44.staticLink = this;
            gnu_kawa_slib_srfi13_frame44.rest = rest;
            gnu_kawa_slib_srfi13_frame44.start1 = start1;
            gnu_kawa_slib_srfi13_frame44.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame44.lambda$Fn103, gnu_kawa_slib_srfi13_frame44.lambda$Fn104);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame44 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn103 = new ModuleMethod(this, 88, null, 0);
        final ModuleMethod lambda$Fn104 = new ModuleMethod(this, 89, null, 8194);
        Object rest;
        Object start1;
        frame43 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 88 ? lambda103() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 89 ? lambda104(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 88) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 89) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda103() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda104(Object start2, Object end2) {
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x = this.staticLink.s1 == this.staticLink.s2;
                    if (x) {
                        apply2 = Scheme.numEqu.apply2(this.start1, start2);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "x", -2, apply2);
                        }
                    }
                    if (x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn105, misc.values, srfi13.lambda$Fn106);
                    }
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda105(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda106(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame45 extends ModuleBody {
        final ModuleMethod lambda$Fn107 = new ModuleMethod(this, 94, null, 0);
        final ModuleMethod lambda$Fn108 = new ModuleMethod(this, 95, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 94 ? lambda107() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 95 ? lambda108(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 94) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 95) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda107() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda108(Object rest, Object start1, Object end1) {
            frame46 gnu_kawa_slib_srfi13_frame46 = new frame46();
            gnu_kawa_slib_srfi13_frame46.staticLink = this;
            gnu_kawa_slib_srfi13_frame46.rest = rest;
            gnu_kawa_slib_srfi13_frame46.start1 = start1;
            gnu_kawa_slib_srfi13_frame46.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame46.lambda$Fn109, gnu_kawa_slib_srfi13_frame46.lambda$Fn110);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame46 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn109 = new ModuleMethod(this, 92, null, 0);
        final ModuleMethod lambda$Fn110 = new ModuleMethod(this, 93, null, 8194);
        Object rest;
        Object start1;
        frame45 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 92 ? lambda109() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 93 ? lambda110(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 92) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 93) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda109() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda110(Object start2, Object end2) {
            int i = 1;
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                int i2;
                if (apply2 != Boolean.FALSE) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                boolean x = (i2 + 1) & 1;
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    x = this.staticLink.s1 == this.staticLink.s2;
                    if (x) {
                        apply2 = Scheme.numEqu.apply2(this.start1, start2);
                        try {
                            if (apply2 == Boolean.FALSE) {
                                i = 0;
                            }
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "x", -2, apply2);
                        }
                    }
                    boolean z = x;
                    x = (i + 1) & 1;
                    if (!x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn111, misc.values);
                    }
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda111(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame47 extends ModuleBody {
        final ModuleMethod lambda$Fn112 = new ModuleMethod(this, 98, null, 0);
        final ModuleMethod lambda$Fn113 = new ModuleMethod(this, 99, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 98 ? lambda112() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 99 ? lambda113(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 98) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 99) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda112() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda113(Object rest, Object start1, Object end1) {
            frame48 gnu_kawa_slib_srfi13_frame48 = new frame48();
            gnu_kawa_slib_srfi13_frame48.staticLink = this;
            gnu_kawa_slib_srfi13_frame48.rest = rest;
            gnu_kawa_slib_srfi13_frame48.start1 = start1;
            gnu_kawa_slib_srfi13_frame48.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame48.lambda$Fn114, gnu_kawa_slib_srfi13_frame48.lambda$Fn115);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame48 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn114 = new ModuleMethod(this, 96, null, 0);
        final ModuleMethod lambda$Fn115 = new ModuleMethod(this, 97, null, 8194);
        Object rest;
        Object start1;
        frame47 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 96 ? lambda114() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 97 ? lambda115(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 96) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 97) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda114() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls, this.staticLink.s2, this.rest);
        }

        Object lambda115(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numLss.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn116, srfi13.lambda$Fn117);
        }

        static Boolean lambda116(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda117(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame49 extends ModuleBody {
        final ModuleMethod lambda$Fn118 = new ModuleMethod(this, 102, null, 0);
        final ModuleMethod lambda$Fn119 = new ModuleMethod(this, 103, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 102 ? lambda118() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 103 ? lambda119(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 102) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 103) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda118() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda119(Object rest, Object start1, Object end1) {
            frame50 gnu_kawa_slib_srfi13_frame50 = new frame50();
            gnu_kawa_slib_srfi13_frame50.staticLink = this;
            gnu_kawa_slib_srfi13_frame50.rest = rest;
            gnu_kawa_slib_srfi13_frame50.start1 = start1;
            gnu_kawa_slib_srfi13_frame50.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame50.lambda$Fn120, gnu_kawa_slib_srfi13_frame50.lambda$Fn121);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 10, null, 0);
        final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 11, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object f55s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 10 ? lambda11() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 11 ? lambda12(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda11() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap$Ex, this.f55s, this.maybe$Mnstart$Plend);
        }

        Object lambda12(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(this.proc, this.f55s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame50 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn120 = new ModuleMethod(this, 100, null, 0);
        final ModuleMethod lambda$Fn121 = new ModuleMethod(this, 101, null, 8194);
        Object rest;
        Object start1;
        frame49 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 100 ? lambda120() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 101 ? lambda121(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 100) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 101) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda120() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda121(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numGrt.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn122, srfi13.lambda$Fn123, misc.values);
        }

        static Boolean lambda122(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda123(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame51 extends ModuleBody {
        final ModuleMethod lambda$Fn124 = new ModuleMethod(this, 106, null, 0);
        final ModuleMethod lambda$Fn125 = new ModuleMethod(this, 107, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 106 ? lambda124() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 107 ? lambda125(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 106) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 107) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda124() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda125(Object rest, Object start1, Object end1) {
            frame52 gnu_kawa_slib_srfi13_frame52 = new frame52();
            gnu_kawa_slib_srfi13_frame52.staticLink = this;
            gnu_kawa_slib_srfi13_frame52.rest = rest;
            gnu_kawa_slib_srfi13_frame52.start1 = start1;
            gnu_kawa_slib_srfi13_frame52.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame52.lambda$Fn126, gnu_kawa_slib_srfi13_frame52.lambda$Fn127);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame52 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn126 = new ModuleMethod(this, 104, null, 0);
        final ModuleMethod lambda$Fn127 = new ModuleMethod(this, 105, null, 8194);
        Object rest;
        Object start1;
        frame51 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 104 ? lambda126() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 105 ? lambda127(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 104) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 105) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda126() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda127(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numLEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, misc.values, srfi13.lambda$Fn128);
        }

        static Boolean lambda128(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame53 extends ModuleBody {
        final ModuleMethod lambda$Fn129 = new ModuleMethod(this, 110, null, 0);
        final ModuleMethod lambda$Fn130 = new ModuleMethod(this, 111, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 110 ? lambda129() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 111 ? lambda130(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 110) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 111) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda129() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda130(Object rest, Object start1, Object end1) {
            frame54 gnu_kawa_slib_srfi13_frame54 = new frame54();
            gnu_kawa_slib_srfi13_frame54.staticLink = this;
            gnu_kawa_slib_srfi13_frame54.rest = rest;
            gnu_kawa_slib_srfi13_frame54.start1 = start1;
            gnu_kawa_slib_srfi13_frame54.end1 = end1;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame54.lambda$Fn131, gnu_kawa_slib_srfi13_frame54.lambda$Fn132);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame54 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn131 = new ModuleMethod(this, 108, null, 0);
        final ModuleMethod lambda$Fn132 = new ModuleMethod(this, 109, null, 8194);
        Object rest;
        Object start1;
        frame53 staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 108 ? lambda131() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 109 ? lambda132(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 108) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 109) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda131() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda132(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (x ? Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE : x) {
                return Scheme.numGEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn133, misc.values, misc.values);
        }

        static Boolean lambda133(Object i) {
            return Boolean.FALSE;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame55 extends ModuleBody {
        Object char$Mn$Grint;
    }

    /* compiled from: srfi13.scm */
    public class frame56 extends ModuleBody {
        Object bound;
        final ModuleMethod lambda$Fn134 = new ModuleMethod(this, DateTime.TIME_MASK, null, 0);
        final ModuleMethod lambda$Fn135 = new ModuleMethod(this, 113, null, 8194);
        Object f56s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == DateTime.TIME_MASK ? lambda134() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 113 ? lambda135(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != DateTime.TIME_MASK) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 113) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda134() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnhash, this.f56s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 912, 55);
                throw e;
            }
        }

        Object lambda135(Object start, Object end) {
            return srfi13.$PcStringHash(this.f56s, characters.char$Mn$Grinteger, this.bound, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame57 extends ModuleBody {
        Object bound;
        final ModuleMethod lambda$Fn136 = new ModuleMethod(this, 114, null, 0);
        final ModuleMethod lambda$Fn137 = new ModuleMethod(this, 115, null, 8194);
        Object f57s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 114 ? lambda136() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 115 ? lambda137(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 114) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 115) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda136() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnhash$Mnci, this.f57s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 921, 58);
                throw e;
            }
        }

        static int lambda138(Object c) {
            try {
                return characters.char$To$Integer(unicode.charDowncase((Char) c));
            } catch (ClassCastException e) {
                throw new WrongType(e, "char-downcase", 1, c);
            }
        }

        Object lambda137(Object start, Object end) {
            return srfi13.$PcStringHash(this.f57s, srfi13.lambda$Fn138, this.bound, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame58 extends ModuleBody {
        final ModuleMethod lambda$Fn139 = new ModuleMethod(this, 116, null, 0);
        final ModuleMethod lambda$Fn140 = new ModuleMethod(this, 117, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f58s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 116 ? lambda139() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 117 ? lambda140(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 116) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 117) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda139() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase, this.f58s, this.maybe$Mnstart$Plend);
        }

        Object lambda140(Object start, Object end) {
            return srfi13.$PcStringMap(unicode.char$Mnupcase, this.f58s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame59 extends ModuleBody {
        final ModuleMethod lambda$Fn141 = new ModuleMethod(this, 118, null, 0);
        final ModuleMethod lambda$Fn142 = new ModuleMethod(this, 119, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f59s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 118 ? lambda141() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 119 ? lambda142(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 118) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 119) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda141() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase$Ex, this.f59s, this.maybe$Mnstart$Plend);
        }

        Object lambda142(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(unicode.char$Mnupcase, this.f59s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame5 extends ModuleBody {
        Object knil;
        Object kons;
        final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 12, null, 0);
        final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 13, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f60s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 12 ? lambda13() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 13 ? lambda14(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 12) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
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

        Object lambda13() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold, this.f60s, this.maybe$Mnstart$Plend);
        }

        Object lambda14(Object start, Object end) {
            Object v = this.knil;
            Object obj = start;
            while (Scheme.numLss.apply2(obj, end) != Boolean.FALSE) {
                Procedure procedure = Scheme.applyToArgs;
                Object obj2 = this.kons;
                Object obj3 = this.f60s;
                try {
                    try {
                        v = procedure.apply3(obj2, Char.make(strings.stringRef((CharSequence) obj3, ((Number) obj).intValue())), v);
                        obj = AddOp.$Pl.apply2(obj, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, obj);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj3);
                }
            }
            return v;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame60 extends ModuleBody {
        final ModuleMethod lambda$Fn143 = new ModuleMethod(this, 120, null, 0);
        final ModuleMethod lambda$Fn144 = new ModuleMethod(this, 121, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f61s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 120 ? lambda143() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 121 ? lambda144(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 120) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 121) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda143() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase, this.f61s, this.maybe$Mnstart$Plend);
        }

        Object lambda144(Object start, Object end) {
            return srfi13.$PcStringMap(unicode.char$Mndowncase, this.f61s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame61 extends ModuleBody {
        final ModuleMethod lambda$Fn145 = new ModuleMethod(this, 122, null, 0);
        final ModuleMethod lambda$Fn146 = new ModuleMethod(this, 123, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f62s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 122 ? lambda145() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 123 ? lambda146(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 122) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 123) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda145() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase$Ex, this.f62s, this.maybe$Mnstart$Plend);
        }

        Object lambda146(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(unicode.char$Mndowncase, this.f62s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame62 extends ModuleBody {
        final ModuleMethod lambda$Fn147 = new ModuleMethod(this, 124, null, 0);
        final ModuleMethod lambda$Fn148 = new ModuleMethod(this, 125, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f63s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 124 ? lambda147() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 125 ? lambda148(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 124) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 125) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda147() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.f63s, this.maybe$Mnstart$Plend);
        }

        Object lambda148(Object start, Object end) {
            return srfi13.$PcStringTitlecase$Ex(this.f63s, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame63 extends ModuleBody {
        final ModuleMethod lambda$Fn149 = new ModuleMethod(this, TransportMediator.KEYCODE_MEDIA_PLAY, null, 0);
        final ModuleMethod lambda$Fn150 = new ModuleMethod(this, TransportMediator.KEYCODE_MEDIA_PAUSE, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f64s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == TransportMediator.KEYCODE_MEDIA_PLAY ? lambda149() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == TransportMediator.KEYCODE_MEDIA_PAUSE ? lambda150(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != TransportMediator.KEYCODE_MEDIA_PLAY) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != TransportMediator.KEYCODE_MEDIA_PAUSE) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda149() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.f64s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda150(Object start, Object end) {
            Object obj = this.f64s;
            try {
                try {
                    try {
                        CharSequence ans = strings.substring((CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                        srfi13.$PcStringTitlecase$Ex(ans, srfi13.Lit0, AddOp.$Mn.apply2(end, start));
                        return ans;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, end);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, start);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame64 extends ModuleBody {
        final ModuleMethod lambda$Fn151;
        Object f65n;
        Object f66s;

        public frame64() {
            PropertySet moduleMethod = new ModuleMethod(this, 128, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:996");
            this.lambda$Fn151 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 128) {
                return lambda151(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda151(Object val) {
            boolean x = numbers.isInteger(this.f65n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(this.f65n);
            if (!x) {
                return x;
            }
            Procedure procedure = Scheme.numLEq;
            IntNum intNum = srfi13.Lit0;
            Object obj = this.f65n;
            Object obj2 = this.f66s;
            try {
                return ((Boolean) procedure.apply3(intNum, obj, Integer.valueOf(strings.stringLength((CharSequence) obj2)))).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, obj2);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 128) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame65 extends ModuleBody {
        final ModuleMethod lambda$Fn152;
        int len;
        Object f67n;

        public frame65() {
            PropertySet moduleMethod = new ModuleMethod(this, 129, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1004");
            this.lambda$Fn152 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 129) {
                return lambda152(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda152(Object val) {
            boolean x = numbers.isInteger(this.f67n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(this.f67n);
            return x ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.f67n, Integer.valueOf(this.len))).booleanValue() : x;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 129) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame66 extends ModuleBody {
        final ModuleMethod lambda$Fn153;
        int len;
        Object f68n;

        public frame66() {
            PropertySet moduleMethod = new ModuleMethod(this, TransportMediator.KEYCODE_MEDIA_RECORD, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1010");
            this.lambda$Fn153 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == TransportMediator.KEYCODE_MEDIA_RECORD) {
                return lambda153(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda153(Object val) {
            boolean x = numbers.isInteger(this.f68n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(this.f68n);
            return x ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.f68n, Integer.valueOf(this.len))).booleanValue() : x;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != TransportMediator.KEYCODE_MEDIA_RECORD) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame67 extends ModuleBody {
        final ModuleMethod lambda$Fn154;
        int len;
        Object f69n;

        public frame67() {
            PropertySet moduleMethod = new ModuleMethod(this, 131, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1016");
            this.lambda$Fn154 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 131) {
                return lambda154(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda154(Object val) {
            boolean x = numbers.isInteger(this.f69n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(this.f69n);
            return x ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.f69n, Integer.valueOf(this.len))).booleanValue() : x;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 131) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame68 extends ModuleBody {
        final ModuleMethod lambda$Fn155 = new ModuleMethod(this, 132, null, 0);
        final ModuleMethod lambda$Fn156 = new ModuleMethod(this, 133, null, 8194);
        Object f70s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 132 ? lambda155() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 133 ? lambda156(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 132) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 133) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda155() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim, this.f70s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1023, 53);
                throw e;
            }
        }

        Object lambda156(Object start, Object end) {
            try {
                Object temp = srfi13.stringSkip$V(this.f70s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp == Boolean.FALSE) {
                    return "";
                }
                Object obj = this.f70s;
                try {
                    try {
                        try {
                            return srfi13.$PcSubstring$SlShared((CharSequence) obj, ((Number) temp).intValue(), ((Number) end).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 2, end);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 1, temp);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%substring/shared", 0, obj);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1024, 29);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame69 extends ModuleBody {
        final ModuleMethod lambda$Fn157 = new ModuleMethod(this, 134, null, 0);
        final ModuleMethod lambda$Fn158 = new ModuleMethod(this, 135, null, 8194);
        Object f71s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 134 ? lambda157() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 135 ? lambda158(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 134) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 135) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda157() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim$Mnright, this.f71s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1030, 59);
                throw e;
            }
        }

        Object lambda158(Object start, Object end) {
            try {
                Boolean temp = srfi13.stringSkipRight$V(this.f71s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp == Boolean.FALSE) {
                    return "";
                }
                Object obj = this.f71s;
                try {
                    CharSequence charSequence = (CharSequence) obj;
                    Object apply2 = AddOp.$Pl.apply2(srfi13.Lit1, temp);
                    try {
                        return srfi13.$PcSubstring$SlShared(charSequence, 0, ((Number) apply2).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "%substring/shared", 2, apply2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%substring/shared", 0, obj);
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1031, 35);
                throw e3;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame6 extends ModuleBody {
        Object knil;
        Object kons;
        final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 14, null, 0);
        final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 15, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f72s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 14 ? lambda15() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 15 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 14) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda15() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold$Mnright, this.f72s, this.maybe$Mnstart$Plend);
        }

        Object lambda16(Object start, Object end) {
            Object v = this.knil;
            Object apply2 = AddOp.$Mn.apply2(end, srfi13.Lit1);
            while (Scheme.numGEq.apply2(apply2, start) != Boolean.FALSE) {
                Procedure procedure = Scheme.applyToArgs;
                Object obj = this.kons;
                Object obj2 = this.f72s;
                try {
                    try {
                        v = procedure.apply3(obj, Char.make(strings.stringRef((CharSequence) obj2, ((Number) apply2).intValue())), v);
                        apply2 = AddOp.$Mn.apply2(apply2, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, apply2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return v;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame70 extends ModuleBody {
        final ModuleMethod lambda$Fn159 = new ModuleMethod(this, 136, null, 0);
        final ModuleMethod lambda$Fn160 = new ModuleMethod(this, 137, null, 8194);
        Object f73s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 136 ? lambda159() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 137 ? lambda160(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 136) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 137) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda159() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim$Mnboth, this.f73s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1037, 58);
                throw e;
            }
        }

        Object lambda160(Object start, Object end) {
            try {
                Object temp = srfi13.stringSkip$V(this.f73s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp == Boolean.FALSE) {
                    return "";
                }
                Object obj = this.f73s;
                try {
                    CharSequence charSequence = (CharSequence) obj;
                    try {
                        int intValue = ((Number) temp).intValue();
                        try {
                            Object apply2 = AddOp.$Pl.apply2(srfi13.Lit1, srfi13.stringSkipRight$V(this.f73s, srfi13.loc$criterion.get(), new Object[]{temp, end}));
                            try {
                                return srfi13.$PcSubstring$SlShared(charSequence, intValue, ((Number) apply2).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, apply2);
                            }
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1040, 58);
                            throw e2;
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 1, temp);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "%substring/shared", 0, obj);
                }
            } catch (UnboundLocationException e22) {
                e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1038, 29);
                throw e22;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame71 extends ModuleBody {
        final ModuleMethod lambda$Fn161 = new ModuleMethod(this, 138, null, 0);
        final ModuleMethod lambda$Fn162 = new ModuleMethod(this, 139, null, 8194);
        Object f74n;
        Object f75s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 138 ? lambda161() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 139 ? lambda162(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 138) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 139) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda161() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnpad$Mnright, this.f75s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1046, 58);
                throw e;
            }
        }

        static boolean lambda163(Object n) {
            boolean x = numbers.isInteger(n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(n);
            return x ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, n)).booleanValue() : x;
        }

        Object lambda162(Object start, Object end) {
            Object obj;
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn163, this.f74n, srfi13.string$Mnpad$Mnright);
                if (Scheme.numLEq.apply2(this.f74n, AddOp.$Mn.apply2(end, start)) != Boolean.FALSE) {
                    obj = this.f75s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        try {
                            int intValue = ((Number) start).intValue();
                            Object apply2 = AddOp.$Pl.apply2(start, this.f74n);
                            try {
                                return srfi13.$PcSubstring$SlShared(charSequence, intValue, ((Number) apply2).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, apply2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%substring/shared", 1, start);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 0, obj);
                    }
                }
                Object obj2 = this.f74n;
                try {
                    Object ans = strings.makeString(((Number) obj2).intValue(), LangPrimType.charType);
                    obj = this.f75s;
                    try {
                        try {
                            try {
                                srfi13.$PcStringCopy$Ex(ans, 0, (CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                                return ans;
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "%string-copy!", 4, end);
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "%string-copy!", 3, start);
                        }
                    } catch (ClassCastException e32) {
                        throw new WrongType(e32, "%string-copy!", 2, obj);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "make-string", 1, obj2);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1047, 7);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame72 extends ModuleBody {
        final ModuleMethod lambda$Fn164 = new ModuleMethod(this, 140, null, 0);
        final ModuleMethod lambda$Fn165 = new ModuleMethod(this, 141, null, 8194);
        Object f76n;
        Object f77s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 140 ? lambda164() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 141 ? lambda165(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 140) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 141) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda164() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnpad, this.f77s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1058, 52);
                throw e;
            }
        }

        static boolean lambda166(Object n) {
            boolean x = numbers.isInteger(n);
            if (!x) {
                return x;
            }
            x = numbers.isExact(n);
            return x ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, n)).booleanValue() : x;
        }

        Object lambda165(Object start, Object end) {
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn166, this.f76n, srfi13.string$Mnpad);
                Object len = AddOp.$Mn.apply2(end, start);
                Object obj;
                if (Scheme.numLEq.apply2(this.f76n, len) != Boolean.FALSE) {
                    obj = this.f77s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        Object apply2 = AddOp.$Mn.apply2(end, this.f76n);
                        try {
                            try {
                                return srfi13.$PcSubstring$SlShared(charSequence, ((Number) apply2).intValue(), ((Number) end).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, end);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%substring/shared", 1, apply2);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 0, obj);
                    }
                }
                Object obj2 = this.f76n;
                try {
                    Object ans = strings.makeString(((Number) obj2).intValue(), LangPrimType.charType);
                    obj2 = AddOp.$Mn.apply2(this.f76n, len);
                    try {
                        int intValue = ((Number) obj2).intValue();
                        obj = this.f77s;
                        try {
                            try {
                                try {
                                    srfi13.$PcStringCopy$Ex(ans, intValue, (CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                                    return ans;
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "%string-copy!", 4, end);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "%string-copy!", 3, start);
                            }
                        } catch (ClassCastException e32) {
                            throw new WrongType(e32, "%string-copy!", 2, obj);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "%string-copy!", 1, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "make-string", 1, obj2);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1059, 7);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame73 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn167 = new ModuleMethod(this, 145, null, 0);
        final ModuleMethod lambda$Fn168 = new ModuleMethod(this, 146, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f78s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 145 ? lambda167() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 146 ? lambda168(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 145) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 146) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda167() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndelete, this.f78s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda168(Object start, Object end) {
            frame74 gnu_kawa_slib_srfi13_frame74 = new frame74();
            gnu_kawa_slib_srfi13_frame74.staticLink = this;
            if (misc.isProcedure(this.criterion)) {
                Object slen = AddOp.$Mn.apply2(end, start);
                try {
                    gnu_kawa_slib_srfi13_frame74.temp = strings.makeString(((Number) slen).intValue());
                    Object ans$Mnlen = srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame74.lambda$Fn169, srfi13.Lit0, this.f78s, new Object[]{start, end});
                    if (Scheme.numEqu.apply2(ans$Mnlen, slen) != Boolean.FALSE) {
                        return gnu_kawa_slib_srfi13_frame74.temp;
                    }
                    try {
                        return strings.substring(gnu_kawa_slib_srfi13_frame74.temp, 0, ((Number) ans$Mnlen).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, ans$Mnlen);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-string", 1, slen);
                }
            }
            try {
                Object obj;
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    obj = this.criterion;
                } else if (characters.isChar(this.criterion)) {
                    try {
                        obj = Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset.get(), this.criterion);
                    } catch (UnboundLocationException e3) {
                        e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1097, 26);
                        throw e3;
                    }
                } else {
                    obj = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
                }
                gnu_kawa_slib_srfi13_frame74.cset = obj;
                Object len = srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame74.lambda$Fn170, srfi13.Lit0, this.f78s, new Object[]{start, end});
                try {
                    gnu_kawa_slib_srfi13_frame74.ans = strings.makeString(((Number) len).intValue());
                    srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame74.lambda$Fn171, srfi13.Lit0, this.f78s, new Object[]{start, end});
                    return gnu_kawa_slib_srfi13_frame74.ans;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "make-string", 1, len);
                }
            } catch (UnboundLocationException e32) {
                e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1096, 22);
                throw e32;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame74 extends ModuleBody {
        CharSequence ans;
        Object cset;
        final ModuleMethod lambda$Fn169;
        final ModuleMethod lambda$Fn170;
        final ModuleMethod lambda$Fn171;
        frame73 staticLink;
        CharSequence temp;

        public frame74() {
            PropertySet moduleMethod = new ModuleMethod(this, 142, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1089");
            this.lambda$Fn169 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 143, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1099");
            this.lambda$Fn170 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 144, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1104");
            this.lambda$Fn171 = moduleMethod;
        }

        Object lambda169(Object c, Object i) {
            if (Scheme.applyToArgs.apply2(this.staticLink.criterion, c) != Boolean.FALSE) {
                return i;
            }
            Object obj = this.temp;
            try {
                try {
                    try {
                        strings.stringSet$Ex((CharSeq) obj, ((Number) i).intValue(), ((Char) c).charValue());
                        return AddOp.$Pl.apply2(i, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-set!", 3, c);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-set!", 2, i);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-set!", 1, obj);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
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
                case 144:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        Object lambda170(Object c, Object i) {
            try {
                return Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) != Boolean.FALSE ? i : AddOp.$Pl.apply2(i, srfi13.Lit1);
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1099, 45);
                throw e;
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 142:
                    return lambda169(obj, obj2);
                case 143:
                    return lambda170(obj, obj2);
                case 144:
                    return lambda171(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda171(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) != Boolean.FALSE) {
                    return i;
                }
                Object obj = this.ans;
                try {
                    try {
                        try {
                            strings.stringSet$Ex((CharSeq) obj, ((Number) i).intValue(), ((Char) c).charValue());
                            return AddOp.$Pl.apply2(i, srfi13.Lit1);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, c);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_WEB_UNABLE_TO_POST_OR_PUT_FILE, 35);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame75 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn172 = new ModuleMethod(this, 150, null, 0);
        final ModuleMethod lambda$Fn173 = new ModuleMethod(this, 151, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f79s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 150 ? lambda172() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 151 ? lambda173(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 150) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 151) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda172() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfilter, this.f79s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda173(Object start, Object end) {
            frame76 gnu_kawa_slib_srfi13_frame76 = new frame76();
            gnu_kawa_slib_srfi13_frame76.staticLink = this;
            if (misc.isProcedure(this.criterion)) {
                Object slen = AddOp.$Mn.apply2(end, start);
                try {
                    gnu_kawa_slib_srfi13_frame76.temp = strings.makeString(((Number) slen).intValue());
                    Object ans$Mnlen = srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame76.lambda$Fn174, srfi13.Lit0, this.f79s, new Object[]{start, end});
                    if (Scheme.numEqu.apply2(ans$Mnlen, slen) != Boolean.FALSE) {
                        return gnu_kawa_slib_srfi13_frame76.temp;
                    }
                    try {
                        return strings.substring(gnu_kawa_slib_srfi13_frame76.temp, 0, ((Number) ans$Mnlen).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, ans$Mnlen);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-string", 1, slen);
                }
            }
            try {
                Object obj;
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    obj = this.criterion;
                } else if (characters.isChar(this.criterion)) {
                    try {
                        obj = Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset.get(), this.criterion);
                    } catch (UnboundLocationException e3) {
                        e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1125, 26);
                        throw e3;
                    }
                } else {
                    obj = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
                }
                gnu_kawa_slib_srfi13_frame76.cset = obj;
                Object len = srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame76.lambda$Fn175, srfi13.Lit0, this.f79s, new Object[]{start, end});
                try {
                    gnu_kawa_slib_srfi13_frame76.ans = strings.makeString(((Number) len).intValue());
                    srfi13.stringFold$V(gnu_kawa_slib_srfi13_frame76.lambda$Fn176, srfi13.Lit0, this.f79s, new Object[]{start, end});
                    return gnu_kawa_slib_srfi13_frame76.ans;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "make-string", 1, len);
                }
            } catch (UnboundLocationException e32) {
                e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1124, 22);
                throw e32;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame76 extends ModuleBody {
        CharSequence ans;
        Object cset;
        final ModuleMethod lambda$Fn174;
        final ModuleMethod lambda$Fn175;
        final ModuleMethod lambda$Fn176;
        frame75 staticLink;
        CharSequence temp;

        public frame76() {
            PropertySet moduleMethod = new ModuleMethod(this, 147, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1116");
            this.lambda$Fn174 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 148, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1128");
            this.lambda$Fn175 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 149, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1133");
            this.lambda$Fn176 = moduleMethod;
        }

        Object lambda174(Object c, Object i) {
            if (Scheme.applyToArgs.apply2(this.staticLink.criterion, c) == Boolean.FALSE) {
                return i;
            }
            Object obj = this.temp;
            try {
                try {
                    try {
                        strings.stringSet$Ex((CharSeq) obj, ((Number) i).intValue(), ((Char) c).charValue());
                        return AddOp.$Pl.apply2(i, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-set!", 3, c);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-set!", 2, i);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-set!", 1, obj);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 147:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 148:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 149:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        Object lambda175(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) != Boolean.FALSE) {
                    return AddOp.$Pl.apply2(i, srfi13.Lit1);
                }
                return i;
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1128, 45);
                throw e;
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 147:
                    return lambda174(obj, obj2);
                case 148:
                    return lambda175(obj, obj2);
                case 149:
                    return lambda176(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda176(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) == Boolean.FALSE) {
                    return i;
                }
                Object obj = this.ans;
                try {
                    try {
                        try {
                            strings.stringSet$Ex((CharSeq) obj, ((Number) i).intValue(), ((Char) c).charValue());
                            return AddOp.$Pl.apply2(i, srfi13.Lit1);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, c);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1133, 35);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame77 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn177 = new ModuleMethod(this, 152, null, 0);
        final ModuleMethod lambda$Fn178 = new ModuleMethod(this, 153, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 152 ? lambda177() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 153 ? lambda178(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 152) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 153) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda177() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda178(Object start, Object end) {
            Object obj;
            Object i;
            Object apply2;
            boolean x;
            if (characters.isChar(this.criterion)) {
                i = start;
                while (true) {
                    apply2 = Scheme.numLss.apply2(i, end);
                    try {
                        x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            break;
                        }
                        obj = this.criterion;
                        try {
                            Char charR = (Char) obj;
                            apply2 = this.str;
                            try {
                                try {
                                    if (characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) apply2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, apply2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "x", -2, apply2);
                    }
                }
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    Procedure procedure;
                    Object obj2;
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        i = start;
                        while (true) {
                            apply2 = Scheme.numLss.apply2(i, end);
                            try {
                                x = ((Boolean) apply2).booleanValue();
                                if (!x) {
                                    break;
                                }
                                procedure = Scheme.applyToArgs;
                                try {
                                    obj2 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj3 = this.criterion;
                                    obj = this.str;
                                    try {
                                        try {
                                            if (procedure.apply3(obj2, obj3, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                                return i;
                                            }
                                            i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                        } catch (ClassCastException e222) {
                                            throw new WrongType(e222, "string-ref", 2, i);
                                        }
                                    } catch (ClassCastException e32) {
                                        throw new WrongType(e32, "string-ref", 1, obj);
                                    }
                                } catch (UnboundLocationException e4) {
                                    e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1162, 9);
                                    throw e4;
                                }
                            } catch (ClassCastException e2222) {
                                throw new WrongType(e2222, "x", -2, apply2);
                            }
                        }
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else if (misc.isProcedure(this.criterion)) {
                        i = start;
                        while (true) {
                            apply2 = Scheme.numLss.apply2(i, end);
                            try {
                                x = ((Boolean) apply2).booleanValue();
                                if (!x) {
                                    break;
                                }
                                procedure = Scheme.applyToArgs;
                                obj2 = this.criterion;
                                obj = this.str;
                                try {
                                    try {
                                        if (procedure.apply2(obj2, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                            return i;
                                        }
                                        i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                    } catch (ClassCastException e22222) {
                                        throw new WrongType(e22222, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e322) {
                                    throw new WrongType(e322, "string-ref", 1, obj);
                                }
                            } catch (ClassCastException e222222) {
                                throw new WrongType(e222222, "x", -2, apply2);
                            }
                        }
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex, this.criterion});
                    }
                } catch (UnboundLocationException e42) {
                    e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1159, 5);
                    throw e42;
                }
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame78 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn179 = new ModuleMethod(this, 154, null, 0);
        final ModuleMethod lambda$Fn180 = new ModuleMethod(this, 155, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 154 ? lambda179() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 155 ? lambda180(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 154) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 155) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda179() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex$Mnright, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda180(Object start, Object end) {
            Object apply2;
            Object obj;
            Object i;
            boolean x;
            if (characters.isChar(this.criterion)) {
                i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                while (true) {
                    apply2 = Scheme.numGEq.apply2(i, start);
                    try {
                        x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            break;
                        }
                        obj = this.criterion;
                        try {
                            Char charR = (Char) obj;
                            apply2 = this.str;
                            try {
                                try {
                                    if (characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) apply2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, apply2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "x", -2, apply2);
                    }
                }
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    Procedure procedure;
                    Object obj2;
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            apply2 = Scheme.numGEq.apply2(i, start);
                            try {
                                x = ((Boolean) apply2).booleanValue();
                                if (!x) {
                                    break;
                                }
                                procedure = Scheme.applyToArgs;
                                try {
                                    obj2 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj3 = this.criterion;
                                    obj = this.str;
                                    try {
                                        try {
                                            if (procedure.apply3(obj2, obj3, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                                return i;
                                            }
                                            i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                        } catch (ClassCastException e222) {
                                            throw new WrongType(e222, "string-ref", 2, i);
                                        }
                                    } catch (ClassCastException e32) {
                                        throw new WrongType(e32, "string-ref", 1, obj);
                                    }
                                } catch (UnboundLocationException e4) {
                                    e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1182, 9);
                                    throw e4;
                                }
                            } catch (ClassCastException e2222) {
                                throw new WrongType(e2222, "x", -2, apply2);
                            }
                        }
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else if (misc.isProcedure(this.criterion)) {
                        i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            apply2 = Scheme.numGEq.apply2(i, start);
                            try {
                                x = ((Boolean) apply2).booleanValue();
                                if (!x) {
                                    break;
                                }
                                procedure = Scheme.applyToArgs;
                                obj2 = this.criterion;
                                obj = this.str;
                                try {
                                    try {
                                        if (procedure.apply2(obj2, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                            return i;
                                        }
                                        i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                    } catch (ClassCastException e22222) {
                                        throw new WrongType(e22222, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e322) {
                                    throw new WrongType(e322, "string-ref", 1, obj);
                                }
                            } catch (ClassCastException e222222) {
                                throw new WrongType(e222222, "x", -2, apply2);
                            }
                        }
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex$Mnright, this.criterion});
                    }
                } catch (UnboundLocationException e42) {
                    e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1179, 5);
                    throw e42;
                }
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame79 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn181 = new ModuleMethod(this, 156, null, 0);
        final ModuleMethod lambda$Fn182 = new ModuleMethod(this, 157, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 156 ? lambda181() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 157 ? lambda182(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 156) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 157) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda181() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda182(Object start, Object end) {
            Object apply2;
            Object i;
            boolean x;
            Object obj;
            if (characters.isChar(this.criterion)) {
                i = start;
                while (true) {
                    apply2 = Scheme.numLss.apply2(i, end);
                    try {
                        x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            break;
                        }
                        obj = this.criterion;
                        try {
                            Char charR = (Char) obj;
                            apply2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) apply2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, apply2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "x", -2, apply2);
                    }
                }
                if (x) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            try {
                Procedure procedure;
                Object obj2;
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    i = start;
                    while (true) {
                        apply2 = Scheme.numLss.apply2(i, end);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                            if (!x) {
                                break;
                            }
                            procedure = Scheme.applyToArgs;
                            try {
                                obj2 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                Object obj3 = this.criterion;
                                obj = this.str;
                                try {
                                    try {
                                        if (procedure.apply3(obj2, obj3, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) == Boolean.FALSE) {
                                            return i;
                                        }
                                        i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                    } catch (ClassCastException e222) {
                                        throw new WrongType(e222, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e32) {
                                    throw new WrongType(e32, "string-ref", 1, obj);
                                }
                            } catch (UnboundLocationException e4) {
                                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1203, 9);
                                throw e4;
                            }
                        } catch (ClassCastException e2222) {
                            throw new WrongType(e2222, "x", -2, apply2);
                        }
                    }
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else if (misc.isProcedure(this.criterion)) {
                    i = start;
                    while (true) {
                        apply2 = Scheme.numLss.apply2(i, end);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                            if (!x) {
                                break;
                            }
                            procedure = Scheme.applyToArgs;
                            obj2 = this.criterion;
                            obj = this.str;
                            try {
                                try {
                                    if (procedure.apply2(obj2, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) == Boolean.FALSE) {
                                        return i;
                                    }
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e322) {
                                throw new WrongType(e322, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e222222) {
                            throw new WrongType(e222222, "x", -2, apply2);
                        }
                    }
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnskip, this.criterion});
                }
            } catch (UnboundLocationException e42) {
                e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1200, 5);
                throw e42;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame7 extends ModuleBody {
        final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 16, null, 0);
        final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 17, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object f80s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 16 ? lambda19() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 17 ? lambda20(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 16) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 17) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda19() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach, this.f80s, this.maybe$Mnstart$Plend);
        }

        Object lambda20(Object start, Object end) {
            Object i = start;
            while (Scheme.numLss.apply2(i, end) != Boolean.FALSE) {
                Procedure procedure = Scheme.applyToArgs;
                Object obj = this.proc;
                Object obj2 = this.f80s;
                try {
                    try {
                        procedure.apply2(obj, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())));
                        i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return Values.empty;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame80 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn183 = new ModuleMethod(this, 158, null, 0);
        final ModuleMethod lambda$Fn184 = new ModuleMethod(this, 159, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 158 ? lambda183() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 159 ? lambda184(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 158) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 159) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda183() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip$Mnright, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda184(Object start, Object end) {
            Object i;
            Object apply2;
            boolean x;
            Object obj;
            if (characters.isChar(this.criterion)) {
                i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                while (true) {
                    apply2 = Scheme.numGEq.apply2(i, start);
                    try {
                        x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            break;
                        }
                        obj = this.criterion;
                        try {
                            Char charR = (Char) obj;
                            apply2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) apply2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, apply2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "x", -2, apply2);
                    }
                }
                if (x) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            try {
                Procedure procedure;
                Object obj2;
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                    while (true) {
                        apply2 = Scheme.numGEq.apply2(i, start);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                            if (!x) {
                                break;
                            }
                            procedure = Scheme.applyToArgs;
                            try {
                                obj2 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                Object obj3 = this.criterion;
                                obj = this.str;
                                try {
                                    try {
                                        if (procedure.apply3(obj2, obj3, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) == Boolean.FALSE) {
                                            return i;
                                        }
                                        i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                    } catch (ClassCastException e222) {
                                        throw new WrongType(e222, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e32) {
                                    throw new WrongType(e32, "string-ref", 1, obj);
                                }
                            } catch (UnboundLocationException e4) {
                                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1225, 9);
                                throw e4;
                            }
                        } catch (ClassCastException e2222) {
                            throw new WrongType(e2222, "x", -2, apply2);
                        }
                    }
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else if (misc.isProcedure(this.criterion)) {
                    i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                    while (true) {
                        apply2 = Scheme.numGEq.apply2(i, start);
                        try {
                            x = ((Boolean) apply2).booleanValue();
                            if (!x) {
                                break;
                            }
                            procedure = Scheme.applyToArgs;
                            obj2 = this.criterion;
                            obj = this.str;
                            try {
                                try {
                                    if (procedure.apply2(obj2, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) == Boolean.FALSE) {
                                        return i;
                                    }
                                    i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e322) {
                                throw new WrongType(e322, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e222222) {
                            throw new WrongType(e222222, "x", -2, apply2);
                        }
                    }
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    return misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mnskip$Mnright, this.criterion});
                }
            } catch (UnboundLocationException e42) {
                e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1222, 5);
                throw e42;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame81 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn185 = new ModuleMethod(this, ComponentConstants.TEXTBOX_PREFERRED_WIDTH, null, 0);
        final ModuleMethod lambda$Fn186 = new ModuleMethod(this, 161, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f81s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == ComponentConstants.TEXTBOX_PREFERRED_WIDTH ? lambda185() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 161 ? lambda186(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != ComponentConstants.TEXTBOX_PREFERRED_WIDTH) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 161) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda185() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncount, this.f81s, this.maybe$Mnstart$Plend);
        }

        Object lambda186(Object start, Object end) {
            Object obj;
            Object obj2;
            Object i;
            Object i2;
            if (characters.isChar(this.criterion)) {
                obj2 = srfi13.Lit0;
                i = start;
                while (Scheme.numGEq.apply2(i, end) == Boolean.FALSE) {
                    i2 = AddOp.$Pl.apply2(i, srfi13.Lit1);
                    obj = this.criterion;
                    try {
                        Char charR = (Char) obj;
                        Object obj3 = this.f81s;
                        try {
                            try {
                                if (characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) obj3, ((Number) i).intValue())))) {
                                    obj2 = AddOp.$Pl.apply2(obj2, srfi13.Lit1);
                                }
                                i = i2;
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj3);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "char=?", 1, obj);
                    }
                }
                return obj2;
            }
            try {
                Procedure procedure;
                Object obj4;
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    obj2 = srfi13.Lit0;
                    i = start;
                    while (Scheme.numGEq.apply2(i, end) == Boolean.FALSE) {
                        i2 = AddOp.$Pl.apply2(i, srfi13.Lit1);
                        procedure = Scheme.applyToArgs;
                        try {
                            obj4 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                            Object obj5 = this.criterion;
                            obj = this.f81s;
                            try {
                                try {
                                    if (procedure.apply3(obj4, obj5, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                        obj2 = AddOp.$Pl.apply2(obj2, srfi13.Lit1);
                                    }
                                    i = i2;
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e32) {
                                throw new WrongType(e32, "string-ref", 1, obj);
                            }
                        } catch (UnboundLocationException e4) {
                            e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1248, 16);
                            throw e4;
                        }
                    }
                    return obj2;
                } else if (misc.isProcedure(this.criterion)) {
                    obj2 = srfi13.Lit0;
                    i = start;
                    while (Scheme.numGEq.apply2(i, end) == Boolean.FALSE) {
                        i2 = AddOp.$Pl.apply2(i, srfi13.Lit1);
                        procedure = Scheme.applyToArgs;
                        obj4 = this.criterion;
                        obj = this.f81s;
                        try {
                            try {
                                if (procedure.apply2(obj4, Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue()))) != Boolean.FALSE) {
                                    obj2 = AddOp.$Pl.apply2(obj2, srfi13.Lit1);
                                }
                                i = i2;
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e322) {
                            throw new WrongType(e322, "string-ref", 1, obj);
                        }
                    }
                    return obj2;
                } else {
                    return misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mncount, this.criterion});
                }
            } catch (UnboundLocationException e42) {
                e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1246, 5);
                throw e42;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame82 extends ModuleBody {
        Object f82char;
        final ModuleMethod lambda$Fn187 = new ModuleMethod(this, 162, null, 0);
        final ModuleMethod lambda$Fn188 = new ModuleMethod(this, 163, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f83s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 162 ? lambda187() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 163 ? lambda188(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 162) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 163) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda187() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfill$Ex, this.f83s, this.maybe$Mnstart$Plend);
        }

        Object lambda188(Object start, Object end) {
            Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
            while (Scheme.numLss.apply2(i, start) == Boolean.FALSE) {
                Object obj = this.f83s;
                try {
                    CharSeq charSeq = (CharSeq) obj;
                    try {
                        int intValue = ((Number) i).intValue();
                        Object obj2 = this.f82char;
                        try {
                            strings.stringSet$Ex(charSeq, intValue, ((Char) obj2).charValue());
                            i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, obj2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            }
            return Values.empty;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame83 extends ModuleBody {
        final ModuleMethod lambda$Fn189 = new ModuleMethod(this, 166, null, 0);
        final ModuleMethod lambda$Fn190 = new ModuleMethod(this, YaVersion.YOUNG_ANDROID_VERSION, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object pattern;
        Object text;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 166 ? lambda189() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == YaVersion.YOUNG_ANDROID_VERSION ? lambda190(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 166) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != YaVersion.YOUNG_ANDROID_VERSION) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda189() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains, this.text, this.maybe$Mnstarts$Plends);
        }

        Object lambda190(Object rest, Object tStart, Object tEnd) {
            frame84 gnu_kawa_slib_srfi13_frame84 = new frame84();
            gnu_kawa_slib_srfi13_frame84.staticLink = this;
            gnu_kawa_slib_srfi13_frame84.rest = rest;
            gnu_kawa_slib_srfi13_frame84.t$Mnstart = tStart;
            gnu_kawa_slib_srfi13_frame84.t$Mnend = tEnd;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame84.lambda$Fn191, gnu_kawa_slib_srfi13_frame84.lambda$Fn192);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame84 extends ModuleBody {
        final ModuleMethod lambda$Fn191 = new ModuleMethod(this, 164, null, 0);
        final ModuleMethod lambda$Fn192 = new ModuleMethod(this, 165, null, 8194);
        Object rest;
        frame83 staticLink;
        Object t$Mnend;
        Object t$Mnstart;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 164 ? lambda191() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 165 ? lambda192(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 164) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 165) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda191() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains, this.staticLink.pattern, this.rest);
        }

        Object lambda192(Object p$Mnstart, Object p$Mnend) {
            return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, characters.char$Eq$Qu, p$Mnstart, p$Mnend, this.t$Mnstart, this.t$Mnend);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame85 extends ModuleBody {
        final ModuleMethod lambda$Fn193 = new ModuleMethod(this, 170, null, 0);
        final ModuleMethod lambda$Fn194 = new ModuleMethod(this, 171, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object pattern;
        Object text;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 170 ? lambda193() : super.apply0(moduleMethod);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 171 ? lambda194(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 170) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 171) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda193() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains$Mnci, this.text, this.maybe$Mnstarts$Plends);
        }

        Object lambda194(Object rest, Object tStart, Object tEnd) {
            frame86 gnu_kawa_slib_srfi13_frame86 = new frame86();
            gnu_kawa_slib_srfi13_frame86.staticLink = this;
            gnu_kawa_slib_srfi13_frame86.rest = rest;
            gnu_kawa_slib_srfi13_frame86.t$Mnstart = tStart;
            gnu_kawa_slib_srfi13_frame86.t$Mnend = tEnd;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame86.lambda$Fn195, gnu_kawa_slib_srfi13_frame86.lambda$Fn196);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame86 extends ModuleBody {
        final ModuleMethod lambda$Fn195 = new ModuleMethod(this, 168, null, 0);
        final ModuleMethod lambda$Fn196 = new ModuleMethod(this, 169, null, 8194);
        Object rest;
        frame85 staticLink;
        Object t$Mnend;
        Object t$Mnstart;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 168 ? lambda195() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 169 ? lambda196(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 168) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 169) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda195() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains$Mnci, this.staticLink.pattern, this.rest);
        }

        Object lambda196(Object p$Mnstart, Object p$Mnend) {
            return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, unicode.char$Mnci$Eq$Qu, p$Mnstart, p$Mnend, this.t$Mnstart, this.t$Mnend);
        }
    }

    /* compiled from: srfi13.scm */
    public class frame87 extends ModuleBody {
        final ModuleMethod lambda$Fn197;
        Object pattern;

        public frame87() {
            PropertySet moduleMethod = new ModuleMethod(this, 172, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1399");
            this.lambda$Fn197 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 172 ? lambda197(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda197(Object args) {
            return srfi13.stringParseStart$PlEnd(srfi13.make$Mnkmp$Mnrestart$Mnvector, this.pattern, args);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 172) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame88 extends ModuleBody {
        final ModuleMethod lambda$Fn198;
        final ModuleMethod lambda$Fn199;
        int patlen;
        Object f84s;

        public frame88() {
            PropertySet moduleMethod = new ModuleMethod(this, 173, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1468");
            this.lambda$Fn198 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 174, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1472");
            this.lambda$Fn199 = moduleMethod;
        }

        Object lambda198(Object args) {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnkmp$Mnpartial$Mnsearch, this.f84s, args);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 173:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 174:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 173:
                    return lambda198(obj);
                case 174:
                    return lambda199(obj) ? Boolean.TRUE : Boolean.FALSE;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda199(Object i) {
            boolean x = numbers.isInteger(i);
            if (!x) {
                return x;
            }
            x = numbers.isExact(i);
            if (!x) {
                return x;
            }
            Object apply2 = Scheme.numLEq.apply2(srfi13.Lit0, i);
            try {
                x = ((Boolean) apply2).booleanValue();
                return x ? ((Boolean) Scheme.numLss.apply2(i, Integer.valueOf(this.patlen))).booleanValue() : x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame89 extends ModuleBody {
        final ModuleMethod lambda$Fn200 = new ModuleMethod(this, 175, null, 0);
        final ModuleMethod lambda$Fn201 = new ModuleMethod(this, 176, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f85s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 175 ? lambda200() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 176 ? lambda201(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 175) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 176) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda200() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse, this.f85s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda201(Object start, Object end) {
            Object len = AddOp.$Mn.apply2(end, start);
            try {
                Object ans = strings.makeString(((Number) len).intValue());
                Object apply2 = AddOp.$Mn.apply2(len, srfi13.Lit1);
                Object i = start;
                while (Scheme.numLss.apply2(apply2, srfi13.Lit0) == Boolean.FALSE) {
                    try {
                        CharSeq charSeq = (CharSeq) ans;
                        try {
                            int intValue = ((Number) apply2).intValue();
                            Object obj = this.f85s;
                            try {
                                try {
                                    strings.stringSet$Ex(charSeq, intValue, strings.stringRef((CharSequence) obj, ((Number) i).intValue()));
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                    apply2 = AddOp.$Mn.apply2(apply2, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-set!", 2, apply2);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-set!", 1, ans);
                    }
                }
                return ans;
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "make-string", 1, len);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame8 extends ModuleBody {
        final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 18, null, 0);
        final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 19, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object f86s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 18 ? lambda21() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 19 ? lambda22(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 18) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 19) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda21() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach$Mnindex, this.f86s, this.maybe$Mnstart$Plend);
        }

        Object lambda22(Object start, Object end) {
            for (Object i = start; Scheme.numLss.apply2(i, end) != Boolean.FALSE; i = AddOp.$Pl.apply2(i, srfi13.Lit1)) {
                Scheme.applyToArgs.apply2(this.proc, i);
            }
            return Values.empty;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame90 extends ModuleBody {
        final ModuleMethod lambda$Fn202 = new ModuleMethod(this, 177, null, 0);
        final ModuleMethod lambda$Fn203 = new ModuleMethod(this, 178, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f87s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 177 ? lambda202() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 178 ? lambda203(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 177) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 178) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda202() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse$Ex, this.f87s, this.maybe$Mnstart$Plend);
        }

        Object lambda203(Object start, Object end) {
            Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
            Object obj = start;
            while (Scheme.numLEq.apply2(i, obj) == Boolean.FALSE) {
                Object obj2 = this.f87s;
                try {
                    try {
                        char ci = strings.stringRef((CharSequence) obj2, ((Number) i).intValue());
                        obj2 = this.f87s;
                        try {
                            CharSeq charSeq = (CharSeq) obj2;
                            try {
                                int intValue = ((Number) i).intValue();
                                Object obj3 = this.f87s;
                                try {
                                    try {
                                        strings.stringSet$Ex(charSeq, intValue, strings.stringRef((CharSequence) obj3, ((Number) obj).intValue()));
                                        obj2 = this.f87s;
                                        try {
                                            try {
                                                strings.stringSet$Ex((CharSeq) obj2, ((Number) obj).intValue(), ci);
                                                i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                                obj = AddOp.$Pl.apply2(obj, srfi13.Lit1);
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "string-set!", 2, obj);
                                            }
                                        } catch (ClassCastException e2) {
                                            throw new WrongType(e2, "string-set!", 1, obj2);
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, obj);
                                    }
                                } catch (ClassCastException e32) {
                                    throw new WrongType(e32, "string-ref", 1, obj3);
                                }
                            } catch (ClassCastException e322) {
                                throw new WrongType(e322, "string-set!", 2, i);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-set!", 1, obj2);
                        }
                    } catch (ClassCastException e3222) {
                        throw new WrongType(e3222, "string-ref", 2, i);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "string-ref", 1, obj2);
                }
            }
            return Values.empty;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame91 extends ModuleBody {
        final ModuleMethod lambda$Fn204 = new ModuleMethod(this, 179, null, 0);
        final ModuleMethod lambda$Fn205 = new ModuleMethod(this, 180, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f88s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 179 ? lambda204() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 180 ? lambda205(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 179) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 180) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda204() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mn$Grlist, this.f88s, this.maybe$Mnstart$Plend);
        }

        Object lambda205(Object start, Object end) {
            Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
            Object obj = LList.Empty;
            while (Scheme.numLss.apply2(i, start) == Boolean.FALSE) {
                Object i2 = AddOp.$Mn.apply2(i, srfi13.Lit1);
                Object obj2 = this.f88s;
                try {
                    try {
                        obj = lists.cons(Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())), obj);
                        i = i2;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return obj;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame92 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn206 = new ModuleMethod(this, 181, null, 0);
        final ModuleMethod lambda$Fn207 = new ModuleMethod(this, 182, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s1;
        Object s2;
        Object start1;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 181 ? lambda206() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 182 ? lambda207(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 181) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 182) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda206() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreplace, this.s2, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda207(Object start2, Object end2) {
            Object obj = this.s1;
            try {
                int slen1 = strings.stringLength((CharSequence) obj);
                Object sublen2 = AddOp.$Mn.apply2(end2, start2);
                Object alen = AddOp.$Pl.apply2(AddOp.$Mn.apply2(Integer.valueOf(slen1), AddOp.$Mn.apply2(this.end1, this.start1)), sublen2);
                try {
                    CharSequence ans = strings.makeString(((Number) alen).intValue());
                    obj = this.s1;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        Object obj2 = this.start1;
                        try {
                            srfi13.$PcStringCopy$Ex(ans, 0, charSequence, 0, ((Number) obj2).intValue());
                            Object obj3 = this.start1;
                            try {
                                int intValue = ((Number) obj3).intValue();
                                obj = this.s2;
                                try {
                                    try {
                                        try {
                                            srfi13.$PcStringCopy$Ex(ans, intValue, (CharSequence) obj, ((Number) start2).intValue(), ((Number) end2).intValue());
                                            obj3 = AddOp.$Pl.apply2(this.start1, sublen2);
                                            try {
                                                int intValue2 = ((Number) obj3).intValue();
                                                obj = this.s1;
                                                try {
                                                    charSequence = (CharSequence) obj;
                                                    obj2 = this.end1;
                                                    try {
                                                        srfi13.$PcStringCopy$Ex(ans, intValue2, charSequence, ((Number) obj2).intValue(), slen1);
                                                        return ans;
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "%string-copy!", 3, obj2);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "%string-copy!", 2, obj);
                                                }
                                            } catch (ClassCastException e3) {
                                                throw new WrongType(e3, "%string-copy!", 1, obj3);
                                            }
                                        } catch (ClassCastException e32) {
                                            throw new WrongType(e32, "%string-copy!", 4, end2);
                                        }
                                    } catch (ClassCastException e322) {
                                        throw new WrongType(e322, "%string-copy!", 3, start2);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "%string-copy!", 2, obj);
                                }
                            } catch (ClassCastException e3222) {
                                throw new WrongType(e3222, "%string-copy!", 1, obj3);
                            }
                        } catch (ClassCastException e32222) {
                            throw new WrongType(e32222, "%string-copy!", 4, obj2);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "%string-copy!", 2, obj);
                    }
                } catch (ClassCastException e322222) {
                    throw new WrongType(e322222, "make-string", 1, alen);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-length", 1, obj);
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame93 extends ModuleBody {
        final ModuleMethod lambda$Fn208 = new ModuleMethod(this, 183, null, 0);
        final ModuleMethod lambda$Fn209 = new ModuleMethod(this, 184, null, 8194);
        Object f89s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 183 ? lambda208() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 184 ? lambda209(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 183) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 184) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda208() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntokenize, this.f89s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1696, 57);
                throw e;
            }
        }

        Object lambda209(Object start, Object end) {
            Object obj;
            Object obj2 = LList.Empty;
            Object i = end;
            while (true) {
                Object apply2 = Scheme.numLss.apply2(start, i);
                try {
                    Boolean temp;
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (x) {
                        try {
                            temp = srfi13.stringIndexRight$V(this.f89s, srfi13.loc$token$Mnchars.get(), new Object[]{start, i});
                        } catch (UnboundLocationException e) {
                            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1698, 48);
                            throw e;
                        }
                    }
                    temp = x ? Boolean.TRUE : Boolean.FALSE;
                    if (temp == Boolean.FALSE) {
                        return obj2;
                    }
                    Object tend = AddOp.$Pl.apply2(srfi13.Lit1, temp);
                    try {
                        temp = srfi13.stringSkipRight$V(this.f89s, srfi13.loc$token$Mnchars.get(), new Object[]{start, temp});
                        if (temp != Boolean.FALSE) {
                            obj = this.f89s;
                            try {
                                CharSequence charSequence = (CharSequence) obj;
                                Object apply22 = AddOp.$Pl.apply2(srfi13.Lit1, temp);
                                try {
                                    try {
                                        obj2 = lists.cons(strings.substring(charSequence, ((Number) apply22).intValue(), ((Number) tend).intValue()), obj2);
                                        Boolean i2 = temp;
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "substring", 3, tend);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "substring", 2, apply22);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "substring", 1, obj);
                            }
                        }
                        obj = this.f89s;
                        try {
                            try {
                                try {
                                    return lists.cons(strings.substring((CharSequence) obj, ((Number) start).intValue(), ((Number) tend).intValue()), obj2);
                                } catch (ClassCastException e222) {
                                    throw new WrongType(e222, "substring", 3, tend);
                                }
                            } catch (ClassCastException e2222) {
                                throw new WrongType(e2222, "substring", 2, start);
                            }
                        } catch (ClassCastException e32) {
                            throw new WrongType(e32, "substring", 1, obj);
                        }
                    } catch (UnboundLocationException e4) {
                        e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BAD_VALUE_FOR_TEXT_RECEIVING, 34);
                        throw e4;
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "x", -2, apply2);
                }
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame94 extends ModuleBody {
        Object from;
        final ModuleMethod lambda$Fn211;
        final ModuleMethod lambda$Fn212 = new ModuleMethod(this, 185, null, 0);
        final ModuleMethod lambda$Fn213;
        final ModuleMethod lambda$Fn214;
        final ModuleMethod lambda$Fn215;
        LList maybe$Mnto$Plstart$Plend;
        Object f90s;

        public frame94() {
            PropertySet moduleMethod = new ModuleMethod(this, 186, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1744");
            this.lambda$Fn214 = moduleMethod;
            this.lambda$Fn213 = new ModuleMethod(this, 187, null, 8194);
            this.lambda$Fn211 = new ModuleMethod(this, 188, null, 0);
            moduleMethod = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1740");
            this.lambda$Fn215 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 185:
                    return lambda212();
                case 188:
                    return lambda211();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 187 ? lambda213(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 185:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 188:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 187) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        static boolean lambda210(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG ? lambda215(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda215(Object to, Object start, Object end) {
            Object obj;
            Object slen = AddOp.$Mn.apply2(end, start);
            Object anslen = AddOp.$Mn.apply2(to, this.from);
            try {
                if (numbers.isZero((Number) anslen)) {
                    return "";
                }
                try {
                    if (numbers.isZero((Number) slen)) {
                        return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.xsubstring, this.f90s, this.from, to, start, end});
                    } else if (Scheme.numEqu.apply2(srfi13.Lit1, slen) != Boolean.FALSE) {
                        try {
                            int intValue = ((Number) anslen).intValue();
                            obj = this.f90s;
                            try {
                                try {
                                    return strings.makeString(intValue, Char.make(strings.stringRef((CharSequence) obj, ((Number) start).intValue())));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, start);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "make-string", 1, anslen);
                        }
                    } else {
                        obj = DivideOp.$Sl.apply2(this.from, slen);
                        try {
                            double doubleValue = numbers.floor(LangObjType.coerceRealNum(obj)).doubleValue();
                            obj = DivideOp.$Sl.apply2(to, slen);
                            try {
                                if (doubleValue == numbers.floor(LangObjType.coerceRealNum(obj)).doubleValue()) {
                                    obj = this.f90s;
                                    try {
                                        CharSequence charSequence = (CharSequence) obj;
                                        Object apply2 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(this.from, slen));
                                        try {
                                            int intValue2 = ((Number) apply2).intValue();
                                            apply2 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(to, slen));
                                            try {
                                                return strings.substring(charSequence, intValue2, ((Number) apply2).intValue());
                                            } catch (ClassCastException e32) {
                                                throw new WrongType(e32, "substring", 3, apply2);
                                            }
                                        } catch (ClassCastException e322) {
                                            throw new WrongType(e322, "substring", 2, apply2);
                                        }
                                    } catch (ClassCastException e22) {
                                        throw new WrongType(e22, "substring", 1, obj);
                                    }
                                }
                                try {
                                    Object ans = strings.makeString(((Number) anslen).intValue());
                                    srfi13.$PcMultispanRepcopy$Ex(ans, srfi13.Lit0, this.f90s, this.from, to, start, end);
                                    return ans;
                                } catch (ClassCastException e3222) {
                                    throw new WrongType(e3222, "make-string", 1, anslen);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "floor", 1, obj);
                            }
                        } catch (ClassCastException e2222) {
                            throw new WrongType(e2222, "floor", 1, obj);
                        }
                    }
                } catch (ClassCastException e32222) {
                    throw new WrongType(e32222, "zero?", 1, slen);
                }
            } catch (ClassCastException e322222) {
                throw new WrongType(e322222, "zero?", 1, anslen);
            }
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda211() {
            if (lists.isPair(this.maybe$Mnto$Plstart$Plend)) {
                return call_with_values.callWithValues(this.lambda$Fn212, this.lambda$Fn213);
            }
            try {
                Object apply4 = Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), strings.string$Qu, this.f90s, srfi13.xsubstring);
                try {
                    int slen = strings.stringLength((CharSequence) apply4);
                    return misc.values(AddOp.$Pl.apply2(this.from, Integer.valueOf(slen)), srfi13.Lit0, Integer.valueOf(slen));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, apply4);
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1749, 36);
                throw e2;
            }
        }

        Object lambda212() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.xsubstring, this.f90s, lists.cdr.apply1(this.maybe$Mnto$Plstart$Plend));
        }

        Object lambda213(Object start, Object end) {
            Object to = lists.car.apply1(this.maybe$Mnto$Plstart$Plend);
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), this.lambda$Fn214, to, srfi13.xsubstring);
                return misc.values(to, start, end);
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1744, 6);
                throw e;
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 186) {
                return lambda214(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda214(Object val) {
            boolean x = numbers.isInteger(val);
            if (!x) {
                return x;
            }
            x = numbers.isExact(val);
            if (x) {
                return ((Boolean) Scheme.numLEq.apply2(this.from, val)).booleanValue();
            }
            return x;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 186) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame95 extends ModuleBody {
        final ModuleMethod lambda$Fn217 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE, null, 0);
        final ModuleMethod lambda$Fn218 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK, null, 0);
        final ModuleMethod lambda$Fn219 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY, null, 8194);
        final ModuleMethod lambda$Fn221;
        LList maybe$Mnsto$Plstart$Plend;
        Object f91s;
        Object sfrom;
        Object target;
        Object tstart;

        public frame95() {
            PropertySet moduleMethod = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1781");
            this.lambda$Fn221 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK /*190*/:
                    return lambda218();
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE /*192*/:
                    return lambda217();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY ? lambda219(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK /*190*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE /*192*/:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        static boolean lambda216(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP ? lambda221(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda221(Object sto, Object start, Object end) {
            Object obj;
            Object tocopy = AddOp.$Mn.apply2(sto, this.sfrom);
            Object tend = AddOp.$Pl.apply2(this.tstart, tocopy);
            Object slen = AddOp.$Mn.apply2(end, start);
            srfi13.checkSubstringSpec(srfi13.string$Mnxcopy$Ex, this.target, this.tstart, tend);
            try {
                boolean x = numbers.isZero((Number) tocopy);
                if (!x) {
                    try {
                        if (numbers.isZero((Number) slen)) {
                            return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.string$Mnxcopy$Ex, this.target, this.tstart, this.f91s, this.sfrom, sto, start, end});
                        } else if (Scheme.numEqu.apply2(srfi13.Lit1, slen) != Boolean.FALSE) {
                            obj = this.target;
                            r2 = this.f91s;
                            try {
                                try {
                                    return srfi13.stringFill$Ex$V(obj, Char.make(strings.stringRef((CharSequence) r2, ((Number) start).intValue())), new Object[]{this.tstart, tend});
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, start);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, r2);
                            }
                        } else {
                            r2 = DivideOp.$Sl.apply2(this.sfrom, slen);
                            try {
                                double doubleValue = numbers.floor(LangObjType.coerceRealNum(r2)).doubleValue();
                                obj = DivideOp.$Sl.apply2(sto, slen);
                                try {
                                    if (doubleValue != numbers.floor(LangObjType.coerceRealNum(obj)).doubleValue()) {
                                        return srfi13.$PcMultispanRepcopy$Ex(this.target, this.tstart, this.f91s, this.sfrom, sto, start, end);
                                    }
                                    r2 = this.target;
                                    try {
                                        CharSequence charSequence = (CharSequence) r2;
                                        obj = this.tstart;
                                        try {
                                            int intValue = ((Number) obj).intValue();
                                            Object obj2 = this.f91s;
                                            try {
                                                CharSequence charSequence2 = (CharSequence) obj2;
                                                Object apply2 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(this.sfrom, slen));
                                                try {
                                                    int intValue2 = ((Number) apply2).intValue();
                                                    apply2 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(sto, slen));
                                                    try {
                                                        return srfi13.$PcStringCopy$Ex(charSequence, intValue, charSequence2, intValue2, ((Number) apply2).intValue());
                                                    } catch (ClassCastException e3) {
                                                        throw new WrongType(e3, "%string-copy!", 4, apply2);
                                                    }
                                                } catch (ClassCastException e32) {
                                                    throw new WrongType(e32, "%string-copy!", 3, apply2);
                                                }
                                            } catch (ClassCastException e322) {
                                                throw new WrongType(e322, "%string-copy!", 2, obj2);
                                            }
                                        } catch (ClassCastException e3222) {
                                            throw new WrongType(e3222, "%string-copy!", 1, obj);
                                        }
                                    } catch (ClassCastException e22) {
                                        throw new WrongType(e22, "%string-copy!", 0, r2);
                                    }
                                } catch (ClassCastException e32222) {
                                    throw new WrongType(e32222, "floor", 1, obj);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "floor", 1, r2);
                            }
                        }
                    } catch (ClassCastException e322222) {
                        throw new WrongType(e322222, "zero?", 1, slen);
                    }
                } else if (x) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } catch (ClassCastException e3222222) {
                throw new WrongType(e3222222, "zero?", 1, tocopy);
            }
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }

        Object lambda217() {
            if (lists.isPair(this.maybe$Mnsto$Plstart$Plend)) {
                return call_with_values.callWithValues(this.lambda$Fn218, this.lambda$Fn219);
            }
            Object obj = this.f91s;
            try {
                int slen = strings.stringLength((CharSequence) obj);
                return misc.values(AddOp.$Pl.apply2(this.sfrom, Integer.valueOf(slen)), srfi13.Lit0, Integer.valueOf(slen));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, obj);
            }
        }

        Object lambda218() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnxcopy$Ex, this.f91s, lists.cdr.apply1(this.maybe$Mnsto$Plstart$Plend));
        }

        Object lambda219(Object start, Object end) {
            Object sto = lists.car.apply1(this.maybe$Mnsto$Plstart$Plend);
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn220, sto, srfi13.string$Mnxcopy$Ex);
                return misc.values(sto, start, end);
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1785, 6);
                throw e;
            }
        }

        static boolean lambda220(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }
    }

    /* compiled from: srfi13.scm */
    public class frame96 extends ModuleBody {
        Object f92final;

        public Object lambda223recur(Object lis) {
            if (!lists.isPair(lis)) {
                return this.f92final;
            }
            try {
                return lists.cons(srfi13.loc$delim.get(), lists.cons(lists.car.apply1(lis), lambda223recur(lists.cdr.apply1(lis))));
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1857, 13);
                throw e;
            }
        }
    }

    /* compiled from: srfi13.scm */
    public class frame9 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 20, null, 0);
        final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 21, null, 8194);
        LList maybe$Mnstart$Plend;
        Object f93s;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 20 ? lambda23() : super.apply0(moduleMethod);
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 21 ? lambda24(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 20) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 21) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }

        Object lambda23() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnevery, this.f93s, this.maybe$Mnstart$Plend);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda24(java.lang.Object r14, java.lang.Object r15) {
            /*
            r13 = this;
            r12 = -2;
            r11 = 2;
            r10 = 1;
            r5 = r13.criterion;
            r5 = kawa.lib.characters.isChar(r5);
            if (r5 == 0) goto L_0x0053;
        L_0x000b:
            r2 = r14;
        L_0x000c:
            r5 = kawa.standard.Scheme.numGEq;
            r6 = r5.apply2(r2, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x012b }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x012b }
            if (r4 == 0) goto L_0x0024;
        L_0x001c:
            if (r4 == 0) goto L_0x0021;
        L_0x001e:
            r5 = java.lang.Boolean.TRUE;
        L_0x0020:
            return r5;
        L_0x0021:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x0020;
        L_0x0024:
            r5 = r13.criterion;
            r5 = (gnu.text.Char) r5;	 Catch:{ ClassCastException -> 0x0134 }
            r6 = r13.f93s;
            r6 = (java.lang.CharSequence) r6;	 Catch:{ ClassCastException -> 0x013d }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0146 }
            r7 = r0;
            r7 = r7.intValue();	 Catch:{ ClassCastException -> 0x0146 }
            r6 = kawa.lib.strings.stringRef(r6, r7);
            r6 = gnu.text.Char.make(r6);
            r4 = kawa.lib.characters.isChar$Eq(r5, r6);
            if (r4 == 0) goto L_0x004b;
        L_0x0042:
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r2 = r5.apply2(r2, r6);
            goto L_0x000c;
        L_0x004b:
            if (r4 == 0) goto L_0x0050;
        L_0x004d:
            r5 = java.lang.Boolean.TRUE;
            goto L_0x0020;
        L_0x0050:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x0020;
        L_0x0053:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = gnu.kawa.slib.srfi13.loc$char$Mnset$Qu;
            r6 = r6.get();	 Catch:{ UnboundLocationException -> 0x014f }
            r7 = r13.criterion;
            r5 = r5.apply2(r6, r7);
            r6 = java.lang.Boolean.FALSE;
            if (r5 == r6) goto L_0x00b0;
        L_0x0065:
            r2 = r14;
        L_0x0066:
            r5 = kawa.standard.Scheme.numGEq;
            r6 = r5.apply2(r2, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0159 }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x0159 }
            if (r4 == 0) goto L_0x007e;
        L_0x0076:
            if (r4 == 0) goto L_0x007b;
        L_0x0078:
            r5 = java.lang.Boolean.TRUE;
            goto L_0x0020;
        L_0x007b:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x0020;
        L_0x007e:
            r7 = kawa.standard.Scheme.applyToArgs;
            r5 = gnu.kawa.slib.srfi13.loc$char$Mnset$Mncontains$Qu;
            r8 = r5.get();	 Catch:{ UnboundLocationException -> 0x0162 }
            r9 = r13.criterion;
            r5 = r13.f93s;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x016d }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0176 }
            r6 = r0;
            r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x0176 }
            r5 = kawa.lib.strings.stringRef(r5, r6);
            r5 = gnu.text.Char.make(r5);
            r4 = r7.apply3(r8, r9, r5);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x00ad;
        L_0x00a4:
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r2 = r5.apply2(r2, r6);
            goto L_0x0066;
        L_0x00ad:
            r5 = r4;
            goto L_0x0020;
        L_0x00b0:
            r5 = r13.criterion;
            r5 = kawa.lib.misc.isProcedure(r5);
            if (r5 == 0) goto L_0x0118;
        L_0x00b8:
            r5 = kawa.standard.Scheme.numEqu;
            r6 = r5.apply2(r14, r15);
            r0 = r6;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x017f }
            r5 = r0;
            r4 = r5.booleanValue();	 Catch:{ ClassCastException -> 0x017f }
            if (r4 == 0) goto L_0x00d2;
        L_0x00c8:
            if (r4 == 0) goto L_0x00ce;
        L_0x00ca:
            r5 = java.lang.Boolean.TRUE;
            goto L_0x0020;
        L_0x00ce:
            r5 = java.lang.Boolean.FALSE;
            goto L_0x0020;
        L_0x00d2:
            r2 = r14;
        L_0x00d3:
            r5 = r13.f93s;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x0188 }
            r0 = r2;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0191 }
            r6 = r0;
            r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x0191 }
            r1 = kawa.lib.strings.stringRef(r5, r6);
            r5 = gnu.kawa.functions.AddOp.$Pl;
            r6 = gnu.kawa.slib.srfi13.Lit1;
            r3 = r5.apply2(r2, r6);
            r5 = kawa.standard.Scheme.numEqu;
            r5 = r5.apply2(r3, r15);
            r6 = java.lang.Boolean.FALSE;
            if (r5 == r6) goto L_0x0103;
        L_0x00f5:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = r13.criterion;
            r7 = gnu.text.Char.make(r1);
            r5 = r5.apply2(r6, r7);
            goto L_0x0020;
        L_0x0103:
            r5 = kawa.standard.Scheme.applyToArgs;
            r6 = r13.criterion;
            r7 = gnu.text.Char.make(r1);
            r4 = r5.apply2(r6, r7);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0115;
        L_0x0113:
            r2 = r3;
            goto L_0x00d3;
        L_0x0115:
            r5 = r4;
            goto L_0x0020;
        L_0x0118:
            r5 = "Second param is neither char-set, char, or predicate procedure.";
            r6 = new java.lang.Object[r11];
            r7 = 0;
            r8 = gnu.kawa.slib.srfi13.string$Mnevery;
            r6[r7] = r8;
            r7 = r13.criterion;
            r6[r10] = r7;
            r5 = kawa.lib.misc.error$V(r5, r6);
            goto L_0x0020;
        L_0x012b:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x0134:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "char=?";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x013d:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r10, r6);
            throw r7;
        L_0x0146:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
        L_0x014f:
            r5 = move-exception;
            r6 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
            r7 = 489; // 0x1e9 float:6.85E-43 double:2.416E-321;
            r8 = 5;
            r5.setLine(r6, r7, r8);
            throw r5;
        L_0x0159:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x0162:
            r5 = move-exception;
            r6 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
            r7 = 492; // 0x1ec float:6.9E-43 double:2.43E-321;
            r8 = 9;
            r5.setLine(r6, r7, r8);
            throw r5;
        L_0x016d:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x0176:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
        L_0x017f:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "x";
            r7.<init>(r5, r8, r12, r6);
            throw r7;
        L_0x0188:
            r6 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r6, r8, r10, r5);
            throw r7;
        L_0x0191:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-ref";
            r6.<init>(r5, r7, r11, r2);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.frame9.lambda24(java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: srfi13.scm */
    public class frame extends ModuleBody {
        Object args;
        final ModuleMethod lambda$Fn1 = new ModuleMethod(this, 1, null, 0);
        final ModuleMethod lambda$Fn2;
        Object proc;
        Object f94s;
        int slen;
        Object start;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 2, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:150");
            this.lambda$Fn2 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda1() : super.apply0(moduleMethod);
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
            return moduleMethod.selector == 2 ? lambda2(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda2(Object end, Object args) {
            if (Scheme.numLEq.apply2(this.start, end) != Boolean.FALSE) {
                return misc.values(args, this.start, end);
            }
            return misc.error$V("Illegal substring START/END spec", new Object[]{this.proc, this.start, end, this.f94s});
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

        Object lambda1() {
            if (lists.isPair(this.args)) {
                Object end = lists.car.apply1(this.args);
                Object args = lists.cdr.apply1(this.args);
                boolean x = numbers.isInteger(end);
                if (x) {
                    x = numbers.isExact(end);
                    if (x) {
                    }
                } else {
                    if (x) {
                    }
                    return misc.error$V("Illegal substring END spec", new Object[]{this.proc, end, this.f94s});
                }
                return misc.values(end, args);
            }
            return misc.values(Integer.valueOf(this.slen), this.args);
        }
    }

    static {
        Object[] objArr = new Object[]{(SimpleSymbol) new SimpleSymbol("l-s-s+e2").readResolve()};
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        r4 = new Object[5];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("let-string-start+end").readResolve();
        Lit41 = simpleSymbol;
        r4[2] = simpleSymbol;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("rest").readResolve();
        Lit27 = simpleSymbol;
        r4[3] = PairWithPosition.make(simpleSymbol, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 553003);
        r4[4] = Lit27;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\f/\f7\f?\rG@\b\b", new Object[0], 9), "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b#\b\u0011\u0018\u00141\t\u0003\t\u000b\u0018\u001c\u0011\u0018\f\t+\t;\b\u0011\u0018\u0014!\t\u0013\b\u001b\u0011\u0018\f\t3\u0011\u0018$\bEC", r4, 1);
        Lit44 = new SyntaxRules(objArr, syntaxRuleArr, 9);
        objArr = new Object[]{Lit41};
        syntaxRuleArr = new SyntaxRule[2];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018,\f\u0007\f\u000f\b\f\u0017\f\u001f\f'\r/(\b\b", new Object[0], 6), "\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u0004!\t\u0003\b\u000bI\u0011\u0018\f\t\u0013\t\u001b\b#\b-+", new Object[]{Lit150, Lit47}, 1);
        syntaxRuleArr[1] = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\f\u001f\f'\f/\r70\b\b", new Object[0], 7), "\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\t\u0013\t\u0003\b\u000bI\u0011\u0018\f\t\u001b\t#\b+\b53", new Object[]{Lit150, Lit45}, 1);
        Lit42 = new SyntaxRules(objArr, syntaxRuleArr, 7);
        ModuleBody moduleBody = $instance;
        string$Mnparse$Mnstart$Plend = new ModuleMethod(moduleBody, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE, Lit45, 12291);
        $Pccheck$Mnbounds = new ModuleMethod(moduleBody, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN, Lit46, 16388);
        string$Mnparse$Mnfinal$Mnstart$Plend = new ModuleMethod(moduleBody, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION, Lit47, 12291);
        substring$Mnspec$Mnok$Qu = new ModuleMethod(moduleBody, 197, Lit48, 12291);
        check$Mnsubstring$Mnspec = new ModuleMethod(moduleBody, 198, Lit49, 16388);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 199, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:223");
        lambda$Fn5 = moduleMethod;
        substring$Slshared = new ModuleMethod(moduleBody, HttpRequestContext.HTTP_OK, Lit50, -4094);
        $Pcsubstring$Slshared = new ModuleMethod(moduleBody, ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED, Lit51, 12291);
        string$Mncopy = new ModuleMethod(moduleBody, 202, Lit52, -4095);
        string$Mnmap = new ModuleMethod(moduleBody, 203, Lit53, -4094);
        $Pcstring$Mnmap = new ModuleMethod(moduleBody, 204, Lit54, 16388);
        string$Mnmap$Ex = new ModuleMethod(moduleBody, 205, Lit55, -4094);
        $Pcstring$Mnmap$Ex = new ModuleMethod(moduleBody, 206, Lit56, 16388);
        string$Mnfold = new ModuleMethod(moduleBody, 207, Lit57, -4093);
        string$Mnfold$Mnright = new ModuleMethod(moduleBody, 208, Lit58, -4093);
        moduleMethod = new ModuleMethod(moduleBody, 209, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:377");
        lambda$Fn17 = moduleMethod;
        string$Mnunfold = new ModuleMethod(moduleBody, 210, Lit59, -4092);
        moduleMethod = new ModuleMethod(moduleBody, 211, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:422");
        lambda$Fn18 = moduleMethod;
        string$Mnunfold$Mnright = new ModuleMethod(moduleBody, 212, Lit60, -4092);
        string$Mnfor$Mneach = new ModuleMethod(moduleBody, 213, Lit61, -4094);
        string$Mnfor$Mneach$Mnindex = new ModuleMethod(moduleBody, 214, Lit62, -4094);
        string$Mnevery = new ModuleMethod(moduleBody, 215, Lit63, -4094);
        string$Mnany = new ModuleMethod(moduleBody, 216, Lit64, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 217, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:535");
        lambda$Fn27 = moduleMethod;
        string$Mntabulate = new ModuleMethod(moduleBody, 218, Lit65, 8194);
        $Pcstring$Mnprefix$Mnlength = new ModuleMethod(moduleBody, 219, Lit66, 24582);
        $Pcstring$Mnsuffix$Mnlength = new ModuleMethod(moduleBody, 220, Lit67, 24582);
        $Pcstring$Mnprefix$Mnlength$Mnci = new ModuleMethod(moduleBody, 221, Lit68, 24582);
        $Pcstring$Mnsuffix$Mnlength$Mnci = new ModuleMethod(moduleBody, 222, Lit69, 24582);
        string$Mnprefix$Mnlength = new ModuleMethod(moduleBody, 223, Lit70, -4094);
        string$Mnsuffix$Mnlength = new ModuleMethod(moduleBody, 224, Lit71, -4094);
        string$Mnprefix$Mnlength$Mnci = new ModuleMethod(moduleBody, 225, Lit72, -4094);
        string$Mnsuffix$Mnlength$Mnci = new ModuleMethod(moduleBody, 226, Lit73, -4094);
        string$Mnprefix$Qu = new ModuleMethod(moduleBody, 227, Lit74, -4094);
        string$Mnsuffix$Qu = new ModuleMethod(moduleBody, 228, Lit75, -4094);
        string$Mnprefix$Mnci$Qu = new ModuleMethod(moduleBody, 229, Lit76, -4094);
        string$Mnsuffix$Mnci$Qu = new ModuleMethod(moduleBody, 230, Lit77, -4094);
        $Pcstring$Mnprefix$Qu = new ModuleMethod(moduleBody, 231, Lit78, 24582);
        $Pcstring$Mnsuffix$Qu = new ModuleMethod(moduleBody, 232, Lit79, 24582);
        $Pcstring$Mnprefix$Mnci$Qu = new ModuleMethod(moduleBody, 233, Lit80, 24582);
        $Pcstring$Mnsuffix$Mnci$Qu = new ModuleMethod(moduleBody, 234, Lit81, 24582);
        $Pcstring$Mncompare = new ModuleMethod(moduleBody, 235, Lit82, 36873);
        $Pcstring$Mncompare$Mnci = new ModuleMethod(moduleBody, 236, Lit83, 36873);
        string$Mncompare = new ModuleMethod(moduleBody, 237, Lit84, -4091);
        string$Mncompare$Mnci = new ModuleMethod(moduleBody, 238, Lit85, -4091);
        moduleMethod = new ModuleMethod(moduleBody, 239, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:756");
        lambda$Fn72 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 240, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:758");
        lambda$Fn73 = moduleMethod;
        string$Eq = new ModuleMethod(moduleBody, LispEscapeFormat.ESCAPE_NORMAL, Lit86, -4094);
        moduleMethod = new ModuleMethod(moduleBody, LispEscapeFormat.ESCAPE_ALL, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:767");
        lambda$Fn78 = moduleMethod;
        string$Ls$Gr = new ModuleMethod(moduleBody, 243, Lit87, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 244, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:778");
        lambda$Fn83 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 245, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:779");
        lambda$Fn84 = moduleMethod;
        string$Ls = new ModuleMethod(moduleBody, 246, Lit88, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 247, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:788");
        lambda$Fn89 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 248, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:789");
        lambda$Fn90 = moduleMethod;
        string$Gr = new ModuleMethod(moduleBody, 249, Lit89, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 250, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:801");
        lambda$Fn95 = moduleMethod;
        string$Ls$Eq = new ModuleMethod(moduleBody, Telnet.WILL, Lit90, -4094);
        moduleMethod = new ModuleMethod(moduleBody, Telnet.WONT, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:810");
        lambda$Fn100 = moduleMethod;
        string$Gr$Eq = new ModuleMethod(moduleBody, Telnet.DO, Lit91, -4094);
        moduleMethod = new ModuleMethod(moduleBody, Telnet.DONT, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:820");
        lambda$Fn105 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 255, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:822");
        lambda$Fn106 = moduleMethod;
        string$Mnci$Eq = new ModuleMethod(moduleBody, 256, Lit92, -4094);
        moduleMethod = new ModuleMethod(moduleBody, InputDeviceCompat.SOURCE_KEYBOARD, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:831");
        lambda$Fn111 = moduleMethod;
        string$Mnci$Ls$Gr = new ModuleMethod(moduleBody, 258, Lit93, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 259, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:842");
        lambda$Fn116 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 260, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:843");
        lambda$Fn117 = moduleMethod;
        string$Mnci$Ls = new ModuleMethod(moduleBody, 261, Lit94, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 262, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:852");
        lambda$Fn122 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 263, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:853");
        lambda$Fn123 = moduleMethod;
        string$Mnci$Gr = new ModuleMethod(moduleBody, 264, Lit95, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 265, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:865");
        lambda$Fn128 = moduleMethod;
        string$Mnci$Ls$Eq = new ModuleMethod(moduleBody, 266, Lit96, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 267, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:874");
        lambda$Fn133 = moduleMethod;
        string$Mnci$Gr$Eq = new ModuleMethod(moduleBody, 268, Lit97, -4094);
        $Pcstring$Mnhash = new ModuleMethod(moduleBody, 269, Lit98, 20485);
        string$Mnhash = new ModuleMethod(moduleBody, 270, Lit99, -4095);
        moduleMethod = new ModuleMethod(moduleBody, 271, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:922");
        lambda$Fn138 = moduleMethod;
        string$Mnhash$Mnci = new ModuleMethod(moduleBody, 272, Lit100, -4095);
        string$Mnupcase = new ModuleMethod(moduleBody, 273, Lit101, -4095);
        string$Mnupcase$Ex = new ModuleMethod(moduleBody, 274, Lit102, -4095);
        string$Mndowncase = new ModuleMethod(moduleBody, 275, Lit103, -4095);
        string$Mndowncase$Ex = new ModuleMethod(moduleBody, 276, Lit104, -4095);
        $Pcstring$Mntitlecase$Ex = new ModuleMethod(moduleBody, 277, Lit105, 12291);
        string$Mntitlecase$Ex = new ModuleMethod(moduleBody, 278, Lit106, -4095);
        string$Mntitlecase = new ModuleMethod(moduleBody, 279, Lit107, -4095);
        string$Mntake = new ModuleMethod(moduleBody, 280, Lit108, 8194);
        string$Mntake$Mnright = new ModuleMethod(moduleBody, 281, Lit109, 8194);
        string$Mndrop = new ModuleMethod(moduleBody, 282, Lit110, 8194);
        string$Mndrop$Mnright = new ModuleMethod(moduleBody, 283, Lit111, 8194);
        string$Mntrim = new ModuleMethod(moduleBody, 284, Lit112, -4095);
        string$Mntrim$Mnright = new ModuleMethod(moduleBody, 285, Lit113, -4095);
        string$Mntrim$Mnboth = new ModuleMethod(moduleBody, 286, Lit114, -4095);
        moduleMethod = new ModuleMethod(moduleBody, 287, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1047");
        lambda$Fn163 = moduleMethod;
        string$Mnpad$Mnright = new ModuleMethod(moduleBody, 288, Lit115, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 289, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1059");
        lambda$Fn166 = moduleMethod;
        string$Mnpad = new ModuleMethod(moduleBody, 290, Lit116, -4094);
        string$Mndelete = new ModuleMethod(moduleBody, 291, Lit117, -4094);
        string$Mnfilter = new ModuleMethod(moduleBody, 292, Lit118, -4094);
        string$Mnindex = new ModuleMethod(moduleBody, 293, Lit119, -4094);
        string$Mnindex$Mnright = new ModuleMethod(moduleBody, 294, Lit120, -4094);
        string$Mnskip = new ModuleMethod(moduleBody, 295, Lit121, -4094);
        string$Mnskip$Mnright = new ModuleMethod(moduleBody, 296, Lit122, -4094);
        string$Mncount = new ModuleMethod(moduleBody, 297, Lit123, -4094);
        string$Mnfill$Ex = new ModuleMethod(moduleBody, 298, Lit124, -4094);
        string$Mncopy$Ex = new ModuleMethod(moduleBody, 299, Lit125, 20483);
        $Pcstring$Mncopy$Ex = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, Lit126, 20485);
        string$Mncontains = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_EXCEPTION, Lit127, -4094);
        string$Mncontains$Mnci = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, Lit128, -4094);
        $Pckmp$Mnsearch = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED, Lit129, 28679);
        make$Mnkmp$Mnrestart$Mnvector = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, Lit130, -4095);
        kmp$Mnstep = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, Lit131, 24582);
        string$Mnkmp$Mnpartial$Mnsearch = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, Lit132, -4092);
        string$Mnnull$Qu = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, Lit133, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnreverse = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, Lit134, -4095);
        string$Mnreverse$Ex = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, Lit135, -4095);
        reverse$Mnlist$Mn$Grstring = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, Lit136, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mn$Grlist = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, Lit137, -4095);
        string$Mnappend$Slshared = new ModuleMethod(moduleBody, ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, Lit138, -4096);
        string$Mnconcatenate$Slshared = new ModuleMethod(moduleBody, 315, Lit139, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnconcatenate = new ModuleMethod(moduleBody, 316, Lit140, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnconcatenate$Mnreverse = new ModuleMethod(moduleBody, 317, Lit141, -4095);
        string$Mnconcatenate$Mnreverse$Slshared = new ModuleMethod(moduleBody, 318, Lit142, -4095);
        $Pcfinish$Mnstring$Mnconcatenate$Mnreverse = new ModuleMethod(moduleBody, 319, Lit143, 16388);
        string$Mnreplace = new ModuleMethod(moduleBody, ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION, Lit144, -4092);
        string$Mntokenize = new ModuleMethod(moduleBody, 321, Lit145, -4095);
        moduleMethod = new ModuleMethod(moduleBody, 322, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1738");
        lambda$Fn210 = moduleMethod;
        xsubstring = new ModuleMethod(moduleBody, 323, Lit146, -4094);
        moduleMethod = new ModuleMethod(moduleBody, 324, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1779");
        lambda$Fn216 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 325, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm:1785");
        lambda$Fn220 = moduleMethod;
        string$Mnxcopy$Ex = new ModuleMethod(moduleBody, 326, Lit147, -4092);
        $Pcmultispan$Mnrepcopy$Ex = new ModuleMethod(moduleBody, 327, Lit148, 28679);
        string$Mnjoin = new ModuleMethod(moduleBody, 328, Lit149, -4095);
        $instance.run();
    }

    public srfi13() {
        ModuleInfo.register(this);
    }

    public static Object stringCopy$Ex(Object obj, int i, CharSequence charSequence) {
        return stringCopy$Ex(obj, i, charSequence, 0);
    }

    public static Object stringCopy$Ex(Object obj, int i, CharSequence charSequence, int i2) {
        return stringCopy$Ex(obj, i, charSequence, i2, charSequence.length());
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object stringParseStart$PlEnd(Object proc, Object s, Object args) {
        frame gnu_kawa_slib_srfi13_frame = new frame();
        gnu_kawa_slib_srfi13_frame.proc = proc;
        gnu_kawa_slib_srfi13_frame.f94s = s;
        if (!strings.isString(gnu_kawa_slib_srfi13_frame.f94s)) {
            misc.error$V("Non-string value", new Object[]{gnu_kawa_slib_srfi13_frame.proc, gnu_kawa_slib_srfi13_frame.f94s});
        }
        Object obj = gnu_kawa_slib_srfi13_frame.f94s;
        try {
            gnu_kawa_slib_srfi13_frame.slen = strings.stringLength((CharSequence) obj);
            if (lists.isPair(args)) {
                obj = lists.car.apply1(args);
                gnu_kawa_slib_srfi13_frame.args = lists.cdr.apply1(args);
                gnu_kawa_slib_srfi13_frame.start = obj;
                boolean x = numbers.isInteger(gnu_kawa_slib_srfi13_frame.start);
                if (x) {
                    x = numbers.isExact(gnu_kawa_slib_srfi13_frame.start);
                    if (x) {
                    }
                } else {
                    if (x) {
                    }
                    return misc.error$V("Illegal substring START spec", new Object[]{gnu_kawa_slib_srfi13_frame.proc, gnu_kawa_slib_srfi13_frame.start, gnu_kawa_slib_srfi13_frame.f94s});
                }
                return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame.lambda$Fn1, gnu_kawa_slib_srfi13_frame.lambda$Fn2);
            }
            return misc.values(LList.Empty, Lit0, Integer.valueOf(gnu_kawa_slib_srfi13_frame.slen));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE /*194*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION /*196*/:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 197:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /*201*/:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 277:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 299:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (!(obj3 instanceof CharSequence)) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object $PcCheckBounds(Object proc, CharSequence s, int start, int end) {
        if (start < 0) {
            return misc.error$V("Illegal substring START spec", new Object[]{proc, Integer.valueOf(start), s});
        } else if (start > end) {
            return misc.error$V("Illegal substring START/END spec", new Object[0]);
        } else {
            if (end <= strings.stringLength(s)) {
                return Values.empty;
            }
            return misc.error$V("Illegal substring END spec", new Object[]{proc, Integer.valueOf(end), s});
        }
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN /*195*/:
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 198:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 204:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 206:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 299:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (!(obj3 instanceof CharSequence)) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 319:
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

    public static Object stringParseFinalStart$PlEnd(Object proc, Object s, Object args) {
        frame0 gnu_kawa_slib_srfi13_frame0 = new frame0();
        gnu_kawa_slib_srfi13_frame0.proc = proc;
        gnu_kawa_slib_srfi13_frame0.f51s = s;
        gnu_kawa_slib_srfi13_frame0.args = args;
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame0.lambda$Fn3, gnu_kawa_slib_srfi13_frame0.lambda$Fn4);
    }

    public static boolean isSubstringSpecOk(Object s, Object start, Object end) {
        boolean x = strings.isString(s);
        if (!x) {
            return x;
        }
        x = numbers.isInteger(start);
        if (!x) {
            return x;
        }
        x = numbers.isExact(start);
        if (!x) {
            return x;
        }
        x = numbers.isInteger(end);
        if (!x) {
            return x;
        }
        x = numbers.isExact(end);
        if (!x) {
            return x;
        }
        Object apply2 = Scheme.numLEq.apply2(Lit0, start);
        try {
            x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x;
            }
            apply2 = Scheme.numLEq.apply2(start, end);
            try {
                x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x;
                }
                try {
                    return ((Boolean) Scheme.numLEq.apply2(end, Integer.valueOf(strings.stringLength((CharSequence) s)))).booleanValue();
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, s);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "x", -2, apply2);
        }
    }

    public static Object checkSubstringSpec(Object proc, Object s, Object start, Object end) {
        if (isSubstringSpecOk(s, start, end)) {
            return Values.empty;
        }
        return misc.error$V("Illegal substring spec.", new Object[]{proc, s, start, end});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Object $PcCheckSubstringSpec(java.lang.Object r5, java.lang.CharSequence r6, int r7, int r8) {
        /*
        r1 = 1;
        r2 = 0;
        if (r7 >= 0) goto L_0x0025;
    L_0x0004:
        r0 = r1;
    L_0x0005:
        if (r0 == 0) goto L_0x0027;
    L_0x0007:
        if (r0 == 0) goto L_0x002e;
    L_0x0009:
        r3 = "Illegal substring spec.";
        r4 = 4;
        r4 = new java.lang.Object[r4];
        r4[r2] = r5;
        r4[r1] = r6;
        r1 = 2;
        r2 = java.lang.Integer.valueOf(r7);
        r4[r1] = r2;
        r1 = 3;
        r2 = java.lang.Integer.valueOf(r8);
        r4[r1] = r2;
        r1 = kawa.lib.misc.error$V(r3, r4);
    L_0x0024:
        return r1;
    L_0x0025:
        r0 = r2;
        goto L_0x0005;
    L_0x0027:
        if (r7 <= r8) goto L_0x0031;
    L_0x0029:
        r0 = r1;
    L_0x002a:
        if (r0 == 0) goto L_0x0033;
    L_0x002c:
        if (r0 != 0) goto L_0x0009;
    L_0x002e:
        r1 = gnu.mapping.Values.empty;
        goto L_0x0024;
    L_0x0031:
        r0 = r2;
        goto L_0x002a;
    L_0x0033:
        r3 = kawa.lib.strings.stringLength(r6);
        if (r8 <= r3) goto L_0x002e;
    L_0x0039:
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.$PcCheckSubstringSpec(java.lang.Object, java.lang.CharSequence, int, int):java.lang.Object");
    }

    public static Object substring$SlShared$V(Object s, Object start, Object[] argsArray) {
        frame1 gnu_kawa_slib_srfi13_frame1 = new frame1();
        gnu_kawa_slib_srfi13_frame1.start = start;
        LList maybe$Mnend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, s, substring$Slshared);
            try {
                gnu_kawa_slib_srfi13_frame1.slen = strings.stringLength((CharSequence) s);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn5, gnu_kawa_slib_srfi13_frame1.start, substring$Slshared);
                    try {
                        CharSequence charSequence = (CharSequence) s;
                        Object obj = gnu_kawa_slib_srfi13_frame1.start;
                        try {
                            int intValue = ((Number) obj).intValue();
                            try {
                                obj = Scheme.applyToArgs.apply4(loc$$Cloptional.get(), maybe$Mnend, Integer.valueOf(gnu_kawa_slib_srfi13_frame1.slen), gnu_kawa_slib_srfi13_frame1.lambda$Fn6);
                                try {
                                    return $PcSubstring$SlShared(charSequence, intValue, ((Number) obj).intValue());
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "%substring/shared", 2, obj);
                                }
                            } catch (UnboundLocationException e2) {
                                e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 226, 10);
                                throw e2;
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "%substring/shared", 1, obj);
                        }
                    } catch (ClassCastException e32) {
                        throw new WrongType(e32, "%substring/shared", 0, s);
                    }
                } catch (UnboundLocationException e22) {
                    e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 223, 5);
                    throw e22;
                }
            } catch (ClassCastException e322) {
                throw new WrongType(e322, "string-length", 1, s);
            }
        } catch (UnboundLocationException e222) {
            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 221, 3);
            throw e222;
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case HttpRequestContext.HTTP_OK /*200*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 202:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 203:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 205:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 207:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 208:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 210:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 212:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 213:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 214:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 215:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 216:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 219:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 220:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 221:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 222:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 223:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 224:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 225:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 226:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 227:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 228:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 229:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 230:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 231:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 232:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 233:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 234:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 235:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 236:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 237:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 238:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case LispEscapeFormat.ESCAPE_NORMAL /*241*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 243:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 246:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 249:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case Telnet.WILL /*251*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case Telnet.DO /*253*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 256:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 258:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 261:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 264:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 266:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 268:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 269:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 270:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 272:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 273:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 274:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 275:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 276:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 278:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 279:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 284:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 285:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 286:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 288:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 290:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 291:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 292:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 293:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 294:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 295:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 296:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 297:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 298:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 299:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET /*302*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_EXCEPTION /*303*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN /*304*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED /*305*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED /*306*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED /*307*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED /*308*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED /*310*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED /*311*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED /*313*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_SEARCH_FAILED /*314*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 317:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 318:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION /*320*/:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 321:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 323:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 326:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 327:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 328:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 199:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 209:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 211:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 217:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 239:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 240:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case LispEscapeFormat.ESCAPE_ALL /*242*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 244:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 245:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 247:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 248:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 250:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Telnet.WONT /*252*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Telnet.DONT /*254*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 255:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case InputDeviceCompat.SOURCE_KEYBOARD /*257*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 259:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 260:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 262:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 263:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 265:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 267:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 271:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 287:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 289:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /*309*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /*312*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 315:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 316:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 322:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 324:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 325:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object $PcSubstring$SlShared(CharSequence s, int start, int end) {
        boolean x = numbers.isZero(Integer.valueOf(start));
        if (x) {
            if (end == strings.stringLength(s)) {
                return s;
            }
        } else if (x) {
            return s;
        }
        return strings.substring(s, start, end);
    }

    public static Object stringCopy$V(Object s, Object[] argsArray) {
        frame2 gnu_kawa_slib_srfi13_frame2 = new frame2();
        gnu_kawa_slib_srfi13_frame2.f53s = s;
        gnu_kawa_slib_srfi13_frame2.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame2.lambda$Fn7, gnu_kawa_slib_srfi13_frame2.lambda$Fn8);
    }

    public static Object stringMap$V(Object proc, Object s, Object[] argsArray) {
        frame3 gnu_kawa_slib_srfi13_frame3 = new frame3();
        gnu_kawa_slib_srfi13_frame3.proc = proc;
        gnu_kawa_slib_srfi13_frame3.f54s = s;
        gnu_kawa_slib_srfi13_frame3.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame3.proc, string$Mnmap);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame3.lambda$Fn9, gnu_kawa_slib_srfi13_frame3.lambda$Fn10);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 271, 3);
            throw e;
        }
    }

    public static Object $PcStringMap(Object proc, Object s, Object start, Object end) {
        Object len = AddOp.$Mn.apply2(end, start);
        try {
            Object ans = strings.makeString(((Number) len).intValue());
            Object i = AddOp.$Mn.apply2(end, Lit1);
            Object apply2 = AddOp.$Mn.apply2(len, Lit1);
            while (Scheme.numLss.apply2(apply2, Lit0) == Boolean.FALSE) {
                try {
                    CharSeq charSeq = (CharSeq) ans;
                    try {
                        int intValue = ((Number) apply2).intValue();
                        try {
                            try {
                                Object apply22 = Scheme.applyToArgs.apply2(proc, Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())));
                                try {
                                    strings.stringSet$Ex(charSeq, intValue, ((Char) apply22).charValue());
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-set!", 3, apply22);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-set!", 2, apply2);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "string-set!", 1, ans);
                }
            }
            return ans;
        } catch (ClassCastException e22222) {
            throw new WrongType(e22222, "make-string", 1, len);
        }
    }

    public static Object stringMap$Ex$V(Object proc, Object s, Object[] argsArray) {
        frame4 gnu_kawa_slib_srfi13_frame4 = new frame4();
        gnu_kawa_slib_srfi13_frame4.proc = proc;
        gnu_kawa_slib_srfi13_frame4.f55s = s;
        gnu_kawa_slib_srfi13_frame4.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame4.proc, string$Mnmap$Ex);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame4.lambda$Fn11, gnu_kawa_slib_srfi13_frame4.lambda$Fn12);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 285, 3);
            throw e;
        }
    }

    public static Object $PcStringMap$Ex(Object proc, Object s, Object start, Object end) {
        Object i = AddOp.$Mn.apply2(end, Lit1);
        while (Scheme.numLss.apply2(i, start) == Boolean.FALSE) {
            try {
                CharSeq charSeq = (CharSeq) s;
                try {
                    int intValue = ((Number) i).intValue();
                    try {
                        try {
                            Object apply2 = Scheme.applyToArgs.apply2(proc, Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())));
                            try {
                                strings.stringSet$Ex(charSeq, intValue, ((Char) apply2).charValue());
                                i = AddOp.$Mn.apply2(i, Lit1);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-set!", 3, apply2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "string-ref", 1, s);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "string-set!", 2, i);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-set!", 1, s);
            }
        }
        return Values.empty;
    }

    public static Object stringFold$V(Object kons, Object knil, Object s, Object[] argsArray) {
        frame5 gnu_kawa_slib_srfi13_frame5 = new frame5();
        gnu_kawa_slib_srfi13_frame5.kons = kons;
        gnu_kawa_slib_srfi13_frame5.knil = knil;
        gnu_kawa_slib_srfi13_frame5.f60s = s;
        gnu_kawa_slib_srfi13_frame5.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame5.kons, string$Mnfold);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame5.lambda$Fn13, gnu_kawa_slib_srfi13_frame5.lambda$Fn14);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 295, 3);
            throw e;
        }
    }

    public static Object stringFoldRight$V(Object kons, Object knil, Object s, Object[] argsArray) {
        frame6 gnu_kawa_slib_srfi13_frame6 = new frame6();
        gnu_kawa_slib_srfi13_frame6.kons = kons;
        gnu_kawa_slib_srfi13_frame6.knil = knil;
        gnu_kawa_slib_srfi13_frame6.f72s = s;
        gnu_kawa_slib_srfi13_frame6.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame6.kons, string$Mnfold$Mnright);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame6.lambda$Fn15, gnu_kawa_slib_srfi13_frame6.lambda$Fn16);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, 3);
            throw e;
        }
    }

    public static Object stringUnfold$V(Object p, Object f, Object g, Object seed, Object[] argsArray) {
        LList base$Plmake$Mnfinal = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, p, string$Mnunfold);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, f, string$Mnunfold);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, g, string$Mnunfold);
                    Procedure procedure = Scheme.applyToArgs;
                    try {
                        Object obj = loc$let$Mnoptionals$St.get();
                        try {
                            try {
                                try {
                                    try {
                                        Object valueOf;
                                        Object min;
                                        Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$base.get(), "", strings.isString(loc$base.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply3(loc$make$Mnfinal.get(), lambda$Fn17, misc.isProcedure(loc$make$Mnfinal.get()) ? Boolean.TRUE : Boolean.FALSE));
                                        Object chunks = LList.Empty;
                                        int i = 0;
                                        CharSequence makeString = strings.makeString(40);
                                        int i2 = 40;
                                        int i3 = 0;
                                        loop0:
                                        while (true) {
                                            valueOf = Integer.valueOf(i3);
                                            while (Scheme.applyToArgs.apply2(p, seed) == Boolean.FALSE) {
                                                Object c = Scheme.applyToArgs.apply2(f, seed);
                                                seed = Scheme.applyToArgs.apply2(g, seed);
                                                if (Scheme.numLss.apply2(valueOf, Integer.valueOf(i2)) != Boolean.FALSE) {
                                                    try {
                                                        try {
                                                            try {
                                                                strings.stringSet$Ex((CharSeq) makeString, ((Number) valueOf).intValue(), ((Char) c).charValue());
                                                                valueOf = AddOp.$Pl.apply2(valueOf, Lit1);
                                                            } catch (ClassCastException e) {
                                                                throw new WrongType(e, "string-set!", 3, c);
                                                            }
                                                        } catch (ClassCastException e2) {
                                                            throw new WrongType(e2, "string-set!", 2, valueOf);
                                                        }
                                                    } catch (ClassCastException e22) {
                                                        throw new WrongType(e22, "string-set!", 1, (Object) makeString);
                                                    }
                                                }
                                                int nchars2 = i2 + i;
                                                min = numbers.min(Lit2, Integer.valueOf(nchars2));
                                                try {
                                                    int chunk$Mnlen2 = ((Number) min).intValue();
                                                    CharSequence new$Mnchunk = strings.makeString(chunk$Mnlen2);
                                                    try {
                                                        try {
                                                            strings.stringSet$Ex((CharSeq) new$Mnchunk, 0, ((Char) c).charValue());
                                                            chunks = lists.cons(makeString, chunks);
                                                            i += i2;
                                                            i3 = 1;
                                                            i2 = chunk$Mnlen2;
                                                            makeString = new$Mnchunk;
                                                        } catch (ClassCastException e222) {
                                                            throw new WrongType(e222, "string-set!", 3, c);
                                                        }
                                                    } catch (ClassCastException e2222) {
                                                        throw new WrongType(e2222, "string-set!", 1, (Object) new$Mnchunk);
                                                    }
                                                } catch (ClassCastException e22222) {
                                                    throw new WrongType(e22222, "chunk-len2", -2, min);
                                                }
                                            }
                                            break loop0;
                                        }
                                        try {
                                            Object finalR = Scheme.applyToArgs.apply2(loc$make$Mnfinal.get(), seed);
                                            try {
                                                int flen = strings.stringLength((CharSequence) finalR);
                                                try {
                                                    try {
                                                        int base$Mnlen = strings.stringLength((CharSequence) loc$base.get());
                                                        min = AddOp.$Pl.apply2(Integer.valueOf(base$Mnlen + i), valueOf);
                                                        try {
                                                            int j = ((Number) min).intValue();
                                                            CharSequence ans = strings.makeString(j + flen);
                                                            try {
                                                                $PcStringCopy$Ex(ans, j, (CharSequence) finalR, 0, flen);
                                                                min = AddOp.$Mn.apply2(Integer.valueOf(j), valueOf);
                                                                try {
                                                                    j = ((Number) min).intValue();
                                                                    try {
                                                                        try {
                                                                            $PcStringCopy$Ex(ans, j, makeString, 0, ((Number) valueOf).intValue());
                                                                            j = Integer.valueOf(j);
                                                                            while (lists.isPair(chunks)) {
                                                                                Object chunk = lists.car.apply1(chunks);
                                                                                chunks = lists.cdr.apply1(chunks);
                                                                                try {
                                                                                    i2 = strings.stringLength((CharSequence) chunk);
                                                                                    j = AddOp.$Mn.apply2(j, Integer.valueOf(i2));
                                                                                    try {
                                                                                        try {
                                                                                            $PcStringCopy$Ex(ans, ((Number) j).intValue(), (CharSequence) chunk, 0, i2);
                                                                                        } catch (ClassCastException e222222) {
                                                                                            throw new WrongType(e222222, "%string-copy!", 2, chunk);
                                                                                        }
                                                                                    } catch (ClassCastException e2222222) {
                                                                                        throw new WrongType(e2222222, "%string-copy!", 1, (Object) j);
                                                                                    }
                                                                                } catch (ClassCastException e22222222) {
                                                                                    throw new WrongType(e22222222, "string-length", 1, chunk);
                                                                                }
                                                                            }
                                                                            try {
                                                                                try {
                                                                                    $PcStringCopy$Ex(ans, 0, (CharSequence) loc$base.get(), 0, base$Mnlen);
                                                                                    return procedure.apply4(obj, base$Plmake$Mnfinal, apply2, ans);
                                                                                } catch (ClassCastException e3) {
                                                                                    throw new WrongType(e3, "%string-copy!", 2, r20);
                                                                                }
                                                                            } catch (UnboundLocationException e4) {
                                                                                e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_UNABLE_TO_DOWNLOAD_FILE, 29);
                                                                                throw e4;
                                                                            }
                                                                        } catch (ClassCastException e222222222) {
                                                                            throw new WrongType(e222222222, "%string-copy!", 4, valueOf);
                                                                        }
                                                                    } catch (ClassCastException e2222222222) {
                                                                        throw new WrongType(e2222222222, "%string-copy!", 2, (Object) makeString);
                                                                    }
                                                                } catch (ClassCastException e22222222222) {
                                                                    throw new WrongType(e22222222222, "j", -2, min);
                                                                }
                                                            } catch (ClassCastException e222222222222) {
                                                                throw new WrongType(e222222222222, "%string-copy!", 2, finalR);
                                                            }
                                                        } catch (ClassCastException e2222222222222) {
                                                            throw new WrongType(e2222222222222, "j", -2, min);
                                                        }
                                                    } catch (ClassCastException e32) {
                                                        throw new WrongType(e32, "string-length", 1, r20);
                                                    }
                                                } catch (UnboundLocationException e42) {
                                                    e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_NOT_CONNECTED_TO_ROBOT, 38);
                                                    throw e42;
                                                }
                                            } catch (ClassCastException e22222222222222) {
                                                throw new WrongType(e22222222222222, "string-length", 1, finalR);
                                            }
                                        } catch (UnboundLocationException e422) {
                                            e422.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 400, 20);
                                            throw e422;
                                        }
                                    } catch (UnboundLocationException e4222) {
                                        e4222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 377, 46);
                                        throw e4222;
                                    }
                                } catch (UnboundLocationException e42222) {
                                    e42222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 377, 6);
                                    throw e42222;
                                }
                            } catch (UnboundLocationException e422222) {
                                e422222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 376, 57);
                                throw e422222;
                            }
                        } catch (UnboundLocationException e4222222) {
                            e4222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 376, 20);
                            throw e4222222;
                        }
                    } catch (UnboundLocationException e42222222) {
                        e42222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 375, 3);
                        throw e42222222;
                    }
                } catch (UnboundLocationException e422222222) {
                    e422222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 374, 3);
                    throw e422222222;
                }
            } catch (UnboundLocationException e4222222222) {
                e4222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 373, 3);
                throw e4222222222;
            }
        } catch (UnboundLocationException e42222222222) {
            e42222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 372, 3);
            throw e42222222222;
        }
    }

    static String lambda17(Object x) {
        return "";
    }

    public static Object stringUnfoldRight$V(Object p, Object f, Object g, Object seed, Object[] argsArray) {
        LList base$Plmake$Mnfinal = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            try {
                try {
                    try {
                        try {
                            Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$base.get(), "", strings.isString(loc$base.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply3(loc$make$Mnfinal.get(), lambda$Fn18, misc.isProcedure(loc$make$Mnfinal.get()) ? Boolean.TRUE : Boolean.FALSE));
                            Object chunks = LList.Empty;
                            IntNum intNum = Lit0;
                            CharSequence makeString = strings.makeString(40);
                            IntNum intNum2 = Lit3;
                            Object i = Lit3;
                            while (Scheme.applyToArgs.apply2(p, seed) == Boolean.FALSE) {
                                Object c = Scheme.applyToArgs.apply2(f, seed);
                                seed = Scheme.applyToArgs.apply2(g, seed);
                                if (Scheme.numGrt.apply2(i, Lit0) != Boolean.FALSE) {
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    try {
                                        try {
                                            try {
                                                strings.stringSet$Ex((CharSeq) makeString, ((Number) i).intValue(), ((Char) c).charValue());
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "string-set!", 3, c);
                                            }
                                        } catch (ClassCastException e2) {
                                            throw new WrongType(e2, "string-set!", 2, i);
                                        }
                                    } catch (ClassCastException e22) {
                                        throw new WrongType(e22, "string-set!", 1, (Object) makeString);
                                    }
                                }
                                Object nchars2 = AddOp.$Pl.apply2(intNum2, intNum);
                                Object chunk$Mnlen2 = numbers.min(Lit4, nchars2);
                                try {
                                    CharSequence new$Mnchunk = strings.makeString(((Number) chunk$Mnlen2).intValue());
                                    i = AddOp.$Mn.apply2(chunk$Mnlen2, Lit1);
                                    try {
                                        try {
                                            try {
                                                strings.stringSet$Ex((CharSeq) new$Mnchunk, ((Number) i).intValue(), ((Char) c).charValue());
                                                chunks = lists.cons(makeString, chunks);
                                                intNum = AddOp.$Pl.apply2(intNum, intNum2);
                                                intNum2 = chunk$Mnlen2;
                                                makeString = new$Mnchunk;
                                            } catch (ClassCastException e222) {
                                                throw new WrongType(e222, "string-set!", 3, c);
                                            }
                                        } catch (ClassCastException e2222) {
                                            throw new WrongType(e2222, "string-set!", 2, i);
                                        }
                                    } catch (ClassCastException e22222) {
                                        throw new WrongType(e22222, "string-set!", 1, (Object) new$Mnchunk);
                                    }
                                } catch (ClassCastException e222222) {
                                    throw new WrongType(e222222, "make-string", 1, chunk$Mnlen2);
                                }
                            }
                            try {
                                Object finalR = Scheme.applyToArgs.apply2(loc$make$Mnfinal.get(), seed);
                                try {
                                    int flen = strings.stringLength((CharSequence) finalR);
                                    try {
                                        try {
                                            int base$Mnlen = strings.stringLength((CharSequence) loc$base.get());
                                            Object chunk$Mnused = AddOp.$Mn.apply2(intNum2, i);
                                            Object apply22 = AddOp.$Pl.apply2(AddOp.$Pl.apply2(AddOp.$Pl.apply2(Integer.valueOf(base$Mnlen), intNum), chunk$Mnused), Integer.valueOf(flen));
                                            try {
                                                CharSequence ans = strings.makeString(((Number) apply22).intValue());
                                                try {
                                                    $PcStringCopy$Ex(ans, 0, (CharSequence) finalR, 0, flen);
                                                    try {
                                                        try {
                                                            try {
                                                                $PcStringCopy$Ex(ans, flen, makeString, ((Number) i).intValue(), intNum2.intValue());
                                                                Object j = AddOp.$Pl.apply2(Integer.valueOf(flen), chunk$Mnused);
                                                                while (lists.isPair(chunks)) {
                                                                    Object chunk = lists.car.apply1(chunks);
                                                                    chunks = lists.cdr.apply1(chunks);
                                                                    try {
                                                                        int chunk$Mnlen = strings.stringLength((CharSequence) chunk);
                                                                        try {
                                                                            try {
                                                                                $PcStringCopy$Ex(ans, ((Number) j).intValue(), (CharSequence) chunk, 0, chunk$Mnlen);
                                                                                j = AddOp.$Pl.apply2(j, Integer.valueOf(chunk$Mnlen));
                                                                            } catch (ClassCastException e2222222) {
                                                                                throw new WrongType(e2222222, "%string-copy!", 2, chunk);
                                                                            }
                                                                        } catch (ClassCastException e22222222) {
                                                                            throw new WrongType(e22222222, "%string-copy!", 1, j);
                                                                        }
                                                                    } catch (ClassCastException e222222222) {
                                                                        throw new WrongType(e222222222, "string-length", 1, chunk);
                                                                    }
                                                                }
                                                                try {
                                                                    try {
                                                                        try {
                                                                            $PcStringCopy$Ex(ans, ((Number) j).intValue(), (CharSequence) loc$base.get(), 0, base$Mnlen);
                                                                            return procedure.apply4(obj, base$Plmake$Mnfinal, apply2, ans);
                                                                        } catch (ClassCastException e3) {
                                                                            throw new WrongType(e3, "%string-copy!", 2, r21);
                                                                        }
                                                                    } catch (UnboundLocationException e4) {
                                                                        e4.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 463, 30);
                                                                        throw e4;
                                                                    }
                                                                } catch (ClassCastException e2222222222) {
                                                                    throw new WrongType(e2222222222, "%string-copy!", 1, j);
                                                                }
                                                            } catch (ClassCastException e22222222222) {
                                                                throw new WrongType(e22222222222, "%string-copy!", 4, (Object) intNum2);
                                                            }
                                                        } catch (ClassCastException e222222222222) {
                                                            throw new WrongType(e222222222222, "%string-copy!", 3, i);
                                                        }
                                                    } catch (ClassCastException e2222222222222) {
                                                        throw new WrongType(e2222222222222, "%string-copy!", 2, (Object) makeString);
                                                    }
                                                } catch (ClassCastException e22222222222222) {
                                                    throw new WrongType(e22222222222222, "%string-copy!", 2, finalR);
                                                }
                                            } catch (ClassCastException e222222222222222) {
                                                throw new WrongType(e222222222222222, "make-string", 1, apply22);
                                            }
                                        } catch (ClassCastException e32) {
                                            throw new WrongType(e32, "string-length", 1, r21);
                                        }
                                    } catch (UnboundLocationException e42) {
                                        e42.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 449, 31);
                                        throw e42;
                                    }
                                } catch (ClassCastException e2222222222222222) {
                                    throw new WrongType(e2222222222222222, "string-length", 1, finalR);
                                }
                            } catch (UnboundLocationException e422) {
                                e422.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 447, 20);
                                throw e422;
                            }
                        } catch (UnboundLocationException e4222) {
                            e4222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 422, 46);
                            throw e4222;
                        }
                    } catch (UnboundLocationException e42222) {
                        e42222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 422, 6);
                        throw e42222;
                    }
                } catch (UnboundLocationException e422222) {
                    e422222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 421, 57);
                    throw e422222;
                }
            } catch (UnboundLocationException e4222222) {
                e4222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 421, 20);
                throw e4222222;
            }
        } catch (UnboundLocationException e42222222) {
            e42222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 420, 3);
            throw e42222222;
        }
    }

    static String lambda18(Object x) {
        return "";
    }

    public static Object stringForEach$V(Object proc, Object s, Object[] argsArray) {
        frame7 gnu_kawa_slib_srfi13_frame7 = new frame7();
        gnu_kawa_slib_srfi13_frame7.proc = proc;
        gnu_kawa_slib_srfi13_frame7.f80s = s;
        gnu_kawa_slib_srfi13_frame7.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame7.proc, string$Mnfor$Mneach);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame7.lambda$Fn19, gnu_kawa_slib_srfi13_frame7.lambda$Fn20);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 468, 3);
            throw e;
        }
    }

    public static Object stringForEachIndex$V(Object proc, Object s, Object[] argsArray) {
        frame8 gnu_kawa_slib_srfi13_frame8 = new frame8();
        gnu_kawa_slib_srfi13_frame8.proc = proc;
        gnu_kawa_slib_srfi13_frame8.f86s = s;
        gnu_kawa_slib_srfi13_frame8.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame8.proc, string$Mnfor$Mneach$Mnindex);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame8.lambda$Fn21, gnu_kawa_slib_srfi13_frame8.lambda$Fn22);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 476, 3);
            throw e;
        }
    }

    public static Object stringEvery$V(Object criterion, Object s, Object[] argsArray) {
        frame9 gnu_kawa_slib_srfi13_frame9 = new frame9();
        gnu_kawa_slib_srfi13_frame9.criterion = criterion;
        gnu_kawa_slib_srfi13_frame9.f93s = s;
        gnu_kawa_slib_srfi13_frame9.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame9.lambda$Fn23, gnu_kawa_slib_srfi13_frame9.lambda$Fn24);
    }

    public static Object stringAny$V(Object criterion, Object s, Object[] argsArray) {
        frame10 gnu_kawa_slib_srfi13_frame10 = new frame10();
        gnu_kawa_slib_srfi13_frame10.criterion = criterion;
        gnu_kawa_slib_srfi13_frame10.f52s = s;
        gnu_kawa_slib_srfi13_frame10.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame10.lambda$Fn25, gnu_kawa_slib_srfi13_frame10.lambda$Fn26);
    }

    public static CharSequence stringTabulate(Object proc, int len) {
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, proc, string$Mntabulate);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn27, Integer.valueOf(len), string$Mntabulate);
                Object s = strings.makeString(len);
                int i = len - 1;
                while (i >= 0) {
                    try {
                        CharSeq charSeq = (CharSeq) s;
                        Object apply2 = Scheme.applyToArgs.apply2(proc, Integer.valueOf(i));
                        try {
                            strings.stringSet$Ex(charSeq, i, ((Char) apply2).charValue());
                            i--;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 1, s);
                    }
                }
                return s;
            } catch (UnboundLocationException e3) {
                e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 535, 3);
                throw e3;
            }
        } catch (UnboundLocationException e32) {
            e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 534, 3);
            throw e32;
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 218:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 280:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 281:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 282:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 283:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    static boolean lambda27(Object val) {
        boolean x = numbers.isInteger(val);
        if (!x) {
            return x;
        }
        x = numbers.isExact(val);
        return x ? ((Boolean) Scheme.numLEq.apply2(Lit0, val)).booleanValue() : x;
    }

    public static Object $PcStringPrefixLength(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        boolean x = false;
        Object delta = numbers.min(AddOp.$Mn.apply2(end1, start1), AddOp.$Mn.apply2(end2, start2));
        end1 = AddOp.$Pl.apply2(start1, delta);
        if (s1 == s2) {
            x = true;
        }
        if (x) {
            if (Scheme.numEqu.apply2(start1, start2) != Boolean.FALSE) {
                return delta;
            }
        } else if (x) {
            return delta;
        }
        Object i = start1;
        Object obj = start2;
        while (true) {
            Object apply2 = Scheme.numGEq.apply2(i, end1);
            try {
                x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    try {
                        try {
                            try {
                                try {
                                    if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) i).intValue())), Char.make(strings.stringRef((CharSequence) s2, ((Number) obj).intValue())))) {
                                        break;
                                    }
                                    i = AddOp.$Pl.apply2(i, Lit1);
                                    obj = AddOp.$Pl.apply2(obj, Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, obj);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s2);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-ref", 1, s1);
                    }
                } else if (x) {
                    break;
                } else {
                    i = AddOp.$Pl.apply2(i, Lit1);
                    obj = AddOp.$Pl.apply2(obj, Lit1);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "x", -2, apply2);
            }
        }
        return AddOp.$Mn.apply2(i, start1);
    }

    public static Object $PcStringSuffixLength(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        boolean x = false;
        Object delta = numbers.min(AddOp.$Mn.apply2(end1, start1), AddOp.$Mn.apply2(end2, start2));
        start1 = AddOp.$Mn.apply2(end1, delta);
        if (s1 == s2) {
            x = true;
        }
        if (x) {
            if (Scheme.numEqu.apply2(end1, end2) != Boolean.FALSE) {
                return delta;
            }
        } else if (x) {
            return delta;
        }
        Object i = AddOp.$Mn.apply2(end1, Lit1);
        Object apply2 = AddOp.$Mn.apply2(end2, Lit1);
        while (true) {
            Object apply22 = Scheme.numLss.apply2(i, start1);
            try {
                x = ((Boolean) apply22).booleanValue();
                if (!x) {
                    try {
                        try {
                            try {
                                try {
                                    if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) i).intValue())), Char.make(strings.stringRef((CharSequence) s2, ((Number) apply2).intValue())))) {
                                        break;
                                    }
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s2);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-ref", 1, s1);
                    }
                } else if (x) {
                    break;
                } else {
                    i = AddOp.$Mn.apply2(i, Lit1);
                    apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "x", -2, apply22);
            }
        }
        return AddOp.$Mn.apply2(AddOp.$Mn.apply2(end1, i), Lit1);
    }

    public static int $PcStringPrefixLengthCi(Object s1, int start1, int end1, Object s2, int start2, int end2) {
        Object min = numbers.min(Integer.valueOf(end1 - start1), Integer.valueOf(end2 - start2));
        try {
            boolean x;
            int delta = ((Number) min).intValue();
            end1 = start1 + delta;
            if (s1 == s2) {
                x = true;
            } else {
                x = false;
            }
            if (x) {
                if (start1 == start2) {
                    return delta;
                }
            } else if (x) {
                return delta;
            }
            int i = start1;
            while (true) {
                if (i >= end1) {
                    x = true;
                } else {
                    x = false;
                }
                if (!x) {
                    try {
                        try {
                            if (!unicode.isCharCi$Eq(Char.make(strings.stringRef((CharSequence) s1, i)), Char.make(strings.stringRef((CharSequence) s2, start2)))) {
                                break;
                            }
                            i++;
                            start2++;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 1, s2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s1);
                    }
                } else if (x) {
                    break;
                } else {
                    i++;
                    start2++;
                }
            }
            return i - start1;
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "delta", -2, min);
        }
    }

    public static int $PcStringSuffixLengthCi(Object s1, int start1, int end1, Object s2, int start2, int end2) {
        Object min = numbers.min(Integer.valueOf(end1 - start1), Integer.valueOf(end2 - start2));
        try {
            boolean x;
            int delta = ((Number) min).intValue();
            start1 = end1 - delta;
            if (s1 == s2) {
                x = true;
            } else {
                x = false;
            }
            if (x) {
                if (end1 == end2) {
                    return delta;
                }
            } else if (x) {
                return delta;
            }
            int i = end1 - 1;
            int j = end2 - 1;
            while (true) {
                if (i < start1) {
                    x = true;
                } else {
                    x = false;
                }
                if (!x) {
                    try {
                        try {
                            if (!unicode.isCharCi$Eq(Char.make(strings.stringRef((CharSequence) s1, i)), Char.make(strings.stringRef((CharSequence) s2, j)))) {
                                break;
                            }
                            j--;
                            i--;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 1, s2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s1);
                    }
                } else if (x) {
                    break;
                } else {
                    j--;
                    i--;
                }
            }
            return (end1 - i) - 1;
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "delta", -2, min);
        }
    }

    public static Object stringPrefixLength$V(Object s1, Object s2, Object[] argsArray) {
        frame11 gnu_kawa_slib_srfi13_frame11 = new frame11();
        gnu_kawa_slib_srfi13_frame11.s1 = s1;
        gnu_kawa_slib_srfi13_frame11.s2 = s2;
        gnu_kawa_slib_srfi13_frame11.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame11.lambda$Fn28, gnu_kawa_slib_srfi13_frame11.lambda$Fn29);
    }

    public static Object stringSuffixLength$V(Object s1, Object s2, Object[] argsArray) {
        frame13 gnu_kawa_slib_srfi13_frame13 = new frame13();
        gnu_kawa_slib_srfi13_frame13.s1 = s1;
        gnu_kawa_slib_srfi13_frame13.s2 = s2;
        gnu_kawa_slib_srfi13_frame13.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame13.lambda$Fn32, gnu_kawa_slib_srfi13_frame13.lambda$Fn33);
    }

    public static Object stringPrefixLengthCi$V(Object s1, Object s2, Object[] argsArray) {
        frame15 gnu_kawa_slib_srfi13_frame15 = new frame15();
        gnu_kawa_slib_srfi13_frame15.s1 = s1;
        gnu_kawa_slib_srfi13_frame15.s2 = s2;
        gnu_kawa_slib_srfi13_frame15.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame15.lambda$Fn36, gnu_kawa_slib_srfi13_frame15.lambda$Fn37);
    }

    public static Object stringSuffixLengthCi$V(Object s1, Object s2, Object[] argsArray) {
        frame17 gnu_kawa_slib_srfi13_frame17 = new frame17();
        gnu_kawa_slib_srfi13_frame17.s1 = s1;
        gnu_kawa_slib_srfi13_frame17.s2 = s2;
        gnu_kawa_slib_srfi13_frame17.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame17.lambda$Fn40, gnu_kawa_slib_srfi13_frame17.lambda$Fn41);
    }

    public static Object isStringPrefix$V(Object s1, Object s2, Object[] argsArray) {
        frame19 gnu_kawa_slib_srfi13_frame19 = new frame19();
        gnu_kawa_slib_srfi13_frame19.s1 = s1;
        gnu_kawa_slib_srfi13_frame19.s2 = s2;
        gnu_kawa_slib_srfi13_frame19.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame19.lambda$Fn44, gnu_kawa_slib_srfi13_frame19.lambda$Fn45);
    }

    public static Object isStringSuffix$V(Object s1, Object s2, Object[] argsArray) {
        frame21 gnu_kawa_slib_srfi13_frame21 = new frame21();
        gnu_kawa_slib_srfi13_frame21.s1 = s1;
        gnu_kawa_slib_srfi13_frame21.s2 = s2;
        gnu_kawa_slib_srfi13_frame21.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame21.lambda$Fn48, gnu_kawa_slib_srfi13_frame21.lambda$Fn49);
    }

    public static Object isStringPrefixCi$V(Object s1, Object s2, Object[] argsArray) {
        frame23 gnu_kawa_slib_srfi13_frame23 = new frame23();
        gnu_kawa_slib_srfi13_frame23.s1 = s1;
        gnu_kawa_slib_srfi13_frame23.s2 = s2;
        gnu_kawa_slib_srfi13_frame23.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame23.lambda$Fn52, gnu_kawa_slib_srfi13_frame23.lambda$Fn53);
    }

    public static Object isStringSuffixCi$V(Object s1, Object s2, Object[] argsArray) {
        frame25 gnu_kawa_slib_srfi13_frame25 = new frame25();
        gnu_kawa_slib_srfi13_frame25.s1 = s1;
        gnu_kawa_slib_srfi13_frame25.s2 = s2;
        gnu_kawa_slib_srfi13_frame25.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame25.lambda$Fn56, gnu_kawa_slib_srfi13_frame25.lambda$Fn57);
    }

    public static Object $PcStringPrefix$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                return Scheme.numEqu.apply2($PcStringPrefixLength(s1, start1, end1, s2, start2, end2), len1);
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "x", -2, apply2);
        }
    }

    public static Object $PcStringSuffix$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                return Scheme.numEqu.apply2(len1, $PcStringSuffixLength(s1, start1, end1, s2, start2, end2));
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "x", -2, apply2);
        }
    }

    public static Object $PcStringPrefixCi$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    try {
                        try {
                            try {
                                return Scheme.numEqu.apply2(len1, Integer.valueOf($PcStringPrefixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue())));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%string-prefix-length-ci", 5, end2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-prefix-length-ci", 4, start2);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "%string-prefix-length-ci", 2, end1);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "%string-prefix-length-ci", 1, start1);
                }
            }
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "x", -2, apply2);
        }
    }

    public static Object $PcStringSuffixCi$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    try {
                        try {
                            try {
                                return Scheme.numEqu.apply2(len1, Integer.valueOf($PcStringSuffixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue())));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%string-suffix-length-ci", 5, end2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-suffix-length-ci", 4, start2);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "%string-suffix-length-ci", 2, end1);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "%string-suffix-length-ci", 1, start1);
                }
            }
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "x", -2, apply2);
        }
    }

    public static Object $PcStringCompare(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2, Object proc$Ls, Object proc$Eq, Object proc$Gr) {
        Object size1 = AddOp.$Mn.apply2(end1, start1);
        Object size2 = AddOp.$Mn.apply2(end2, start2);
        Object match = $PcStringPrefixLength(s1, start1, end1, s2, start2, end2);
        if (Scheme.numEqu.apply2(match, size1) != Boolean.FALSE) {
            Procedure procedure = Scheme.applyToArgs;
            if (Scheme.numEqu.apply2(match, size2) == Boolean.FALSE) {
                proc$Eq = proc$Ls;
            }
            return procedure.apply2(proc$Eq, end1);
        }
        Procedure procedure2 = Scheme.applyToArgs;
        if (Scheme.numEqu.apply2(match, size2) == Boolean.FALSE) {
            try {
                CharSequence charSequence = (CharSequence) s1;
                Object apply2 = AddOp.$Pl.apply2(start1, match);
                try {
                    Char make = Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()));
                    try {
                        CharSequence charSequence2 = (CharSequence) s2;
                        apply2 = AddOp.$Pl.apply2(start2, match);
                        try {
                            if (characters.isChar$Ls(make, Char.make(strings.stringRef(charSequence2, ((Number) apply2).intValue())))) {
                                proc$Gr = proc$Ls;
                            }
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "string-ref", 2, apply2);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "string-ref", 1, s1);
            }
        }
        return procedure2.apply2(proc$Gr, AddOp.$Pl.apply2(match, start1));
    }

    public static Object $PcStringCompareCi(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2, Object proc$Ls, Object proc$Eq, Object proc$Gr) {
        Object size1 = AddOp.$Mn.apply2(end1, start1);
        Object size2 = AddOp.$Mn.apply2(end2, start2);
        try {
            try {
                try {
                    try {
                        int match = $PcStringPrefixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        if (Scheme.numEqu.apply2(Integer.valueOf(match), size1) != Boolean.FALSE) {
                            Procedure procedure = Scheme.applyToArgs;
                            if (Scheme.numEqu.apply2(Integer.valueOf(match), size2) == Boolean.FALSE) {
                                proc$Eq = proc$Ls;
                            }
                            return procedure.apply2(proc$Eq, end1);
                        }
                        Procedure procedure2 = Scheme.applyToArgs;
                        if (Scheme.numEqu.apply2(Integer.valueOf(match), size2) == Boolean.FALSE) {
                            try {
                                CharSequence charSequence = (CharSequence) s1;
                                Object apply2 = AddOp.$Pl.apply2(start1, Integer.valueOf(match));
                                try {
                                    Char make = Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()));
                                    try {
                                        CharSequence charSequence2 = (CharSequence) s2;
                                        apply2 = AddOp.$Pl.apply2(start2, Integer.valueOf(match));
                                        try {
                                            if (unicode.isCharCi$Ls(make, Char.make(strings.stringRef(charSequence2, ((Number) apply2).intValue())))) {
                                                proc$Gr = proc$Ls;
                                            }
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", 2, apply2);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, s2);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 1, s1);
                            }
                        }
                        return procedure2.apply2(proc$Gr, AddOp.$Pl.apply2(start1, Integer.valueOf(match)));
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "%string-prefix-length-ci", 5, end2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "%string-prefix-length-ci", 4, start2);
                }
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "%string-prefix-length-ci", 2, end1);
            }
        } catch (ClassCastException e2222222) {
            throw new WrongType(e2222222, "%string-prefix-length-ci", 1, start1);
        }
    }

    public static Object stringCompare$V(Object s1, Object s2, Object proc$Ls, Object proc$Eq, Object proc$Gr, Object[] argsArray) {
        frame27 gnu_kawa_slib_srfi13_frame27 = new frame27();
        gnu_kawa_slib_srfi13_frame27.s1 = s1;
        gnu_kawa_slib_srfi13_frame27.s2 = s2;
        gnu_kawa_slib_srfi13_frame27.proc$Ls = proc$Ls;
        gnu_kawa_slib_srfi13_frame27.proc$Eq = proc$Eq;
        gnu_kawa_slib_srfi13_frame27.proc$Gr = proc$Gr;
        gnu_kawa_slib_srfi13_frame27.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame27.proc$Ls, string$Mncompare);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame27.proc$Eq, string$Mncompare);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame27.proc$Gr, string$Mncompare);
                    return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame27.lambda$Fn60, gnu_kawa_slib_srfi13_frame27.lambda$Fn61);
                } catch (UnboundLocationException e) {
                    e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 728, 3);
                    throw e;
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 727, 3);
                throw e2;
            }
        } catch (UnboundLocationException e22) {
            e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 726, 3);
            throw e22;
        }
    }

    public static Object stringCompareCi$V(Object s1, Object s2, Object proc$Ls, Object proc$Eq, Object proc$Gr, Object[] argsArray) {
        frame29 gnu_kawa_slib_srfi13_frame29 = new frame29();
        gnu_kawa_slib_srfi13_frame29.s1 = s1;
        gnu_kawa_slib_srfi13_frame29.s2 = s2;
        gnu_kawa_slib_srfi13_frame29.proc$Ls = proc$Ls;
        gnu_kawa_slib_srfi13_frame29.proc$Eq = proc$Eq;
        gnu_kawa_slib_srfi13_frame29.proc$Gr = proc$Gr;
        gnu_kawa_slib_srfi13_frame29.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame29.proc$Ls, string$Mncompare$Mnci);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame29.proc$Eq, string$Mncompare$Mnci);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, gnu_kawa_slib_srfi13_frame29.proc$Gr, string$Mncompare$Mnci);
                    return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame29.lambda$Fn64, gnu_kawa_slib_srfi13_frame29.lambda$Fn65);
                } catch (UnboundLocationException e) {
                    e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 736, 3);
                    throw e;
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 735, 3);
                throw e2;
            }
        } catch (UnboundLocationException e22) {
            e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 734, 3);
            throw e22;
        }
    }

    public static Object string$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame31 gnu_kawa_slib_srfi13_frame31 = new frame31();
        gnu_kawa_slib_srfi13_frame31.s1 = s1;
        gnu_kawa_slib_srfi13_frame31.s2 = s2;
        gnu_kawa_slib_srfi13_frame31.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame31.lambda$Fn68, gnu_kawa_slib_srfi13_frame31.lambda$Fn69);
    }

    public static Object string$Ls$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame33 gnu_kawa_slib_srfi13_frame33 = new frame33();
        gnu_kawa_slib_srfi13_frame33.s1 = s1;
        gnu_kawa_slib_srfi13_frame33.s2 = s2;
        gnu_kawa_slib_srfi13_frame33.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame33.lambda$Fn74, gnu_kawa_slib_srfi13_frame33.lambda$Fn75);
    }

    public static Object string$Ls$V(Object s1, Object s2, Object[] argsArray) {
        frame35 gnu_kawa_slib_srfi13_frame35 = new frame35();
        gnu_kawa_slib_srfi13_frame35.s1 = s1;
        gnu_kawa_slib_srfi13_frame35.s2 = s2;
        gnu_kawa_slib_srfi13_frame35.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame35.lambda$Fn79, gnu_kawa_slib_srfi13_frame35.lambda$Fn80);
    }

    public static Object string$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame37 gnu_kawa_slib_srfi13_frame37 = new frame37();
        gnu_kawa_slib_srfi13_frame37.s1 = s1;
        gnu_kawa_slib_srfi13_frame37.s2 = s2;
        gnu_kawa_slib_srfi13_frame37.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame37.lambda$Fn85, gnu_kawa_slib_srfi13_frame37.lambda$Fn86);
    }

    public static Object string$Ls$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame39 gnu_kawa_slib_srfi13_frame39 = new frame39();
        gnu_kawa_slib_srfi13_frame39.s1 = s1;
        gnu_kawa_slib_srfi13_frame39.s2 = s2;
        gnu_kawa_slib_srfi13_frame39.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame39.lambda$Fn91, gnu_kawa_slib_srfi13_frame39.lambda$Fn92);
    }

    public static Object string$Gr$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame41 gnu_kawa_slib_srfi13_frame41 = new frame41();
        gnu_kawa_slib_srfi13_frame41.s1 = s1;
        gnu_kawa_slib_srfi13_frame41.s2 = s2;
        gnu_kawa_slib_srfi13_frame41.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame41.lambda$Fn96, gnu_kawa_slib_srfi13_frame41.lambda$Fn97);
    }

    public static Object stringCi$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame43 gnu_kawa_slib_srfi13_frame43 = new frame43();
        gnu_kawa_slib_srfi13_frame43.s1 = s1;
        gnu_kawa_slib_srfi13_frame43.s2 = s2;
        gnu_kawa_slib_srfi13_frame43.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame43.lambda$Fn101, gnu_kawa_slib_srfi13_frame43.lambda$Fn102);
    }

    public static Object stringCi$Ls$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame45 gnu_kawa_slib_srfi13_frame45 = new frame45();
        gnu_kawa_slib_srfi13_frame45.s1 = s1;
        gnu_kawa_slib_srfi13_frame45.s2 = s2;
        gnu_kawa_slib_srfi13_frame45.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame45.lambda$Fn107, gnu_kawa_slib_srfi13_frame45.lambda$Fn108);
    }

    public static Object stringCi$Ls$V(Object s1, Object s2, Object[] argsArray) {
        frame47 gnu_kawa_slib_srfi13_frame47 = new frame47();
        gnu_kawa_slib_srfi13_frame47.s1 = s1;
        gnu_kawa_slib_srfi13_frame47.s2 = s2;
        gnu_kawa_slib_srfi13_frame47.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame47.lambda$Fn112, gnu_kawa_slib_srfi13_frame47.lambda$Fn113);
    }

    public static Object stringCi$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame49 gnu_kawa_slib_srfi13_frame49 = new frame49();
        gnu_kawa_slib_srfi13_frame49.s1 = s1;
        gnu_kawa_slib_srfi13_frame49.s2 = s2;
        gnu_kawa_slib_srfi13_frame49.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame49.lambda$Fn118, gnu_kawa_slib_srfi13_frame49.lambda$Fn119);
    }

    public static Object stringCi$Ls$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame51 gnu_kawa_slib_srfi13_frame51 = new frame51();
        gnu_kawa_slib_srfi13_frame51.s1 = s1;
        gnu_kawa_slib_srfi13_frame51.s2 = s2;
        gnu_kawa_slib_srfi13_frame51.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame51.lambda$Fn124, gnu_kawa_slib_srfi13_frame51.lambda$Fn125);
    }

    public static Object stringCi$Gr$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame53 gnu_kawa_slib_srfi13_frame53 = new frame53();
        gnu_kawa_slib_srfi13_frame53.s1 = s1;
        gnu_kawa_slib_srfi13_frame53.s2 = s2;
        gnu_kawa_slib_srfi13_frame53.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame53.lambda$Fn129, gnu_kawa_slib_srfi13_frame53.lambda$Fn130);
    }

    public static Object $PcStringHash(Object s, Object char$To$Int, Object bound, Object start, Object end) {
        frame55 closureEnv = new frame55();
        closureEnv.char$Mn$Grint = char$To$Int;
        Object obj = Lit5;
        while (Scheme.numGEq.apply2(obj, bound) == Boolean.FALSE) {
            obj = AddOp.$Pl.apply2(obj, obj);
        }
        Object mask = AddOp.$Mn.apply2(obj, Lit1);
        Object obj2 = Lit0;
        obj = start;
        while (Scheme.numGEq.apply2(obj, end) == Boolean.FALSE) {
            Object i = AddOp.$Pl.apply2(obj, Lit1);
            try {
                try {
                    obj2 = BitwiseOp.and.apply2(mask, AddOp.$Pl.apply2(MultiplyOp.$St.apply2(Lit6, obj2), Scheme.applyToArgs.apply2(closureEnv.char$Mn$Grint, Char.make(strings.stringRef((CharSequence) s, ((Number) obj).intValue())))));
                    obj = i;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        return DivideOp.modulo.apply2(obj2, bound);
    }

    public static Object stringHash$V(Object s, Object[] argsArray) {
        Object apply2;
        frame56 gnu_kawa_slib_srfi13_frame56 = new frame56();
        gnu_kawa_slib_srfi13_frame56.f56s = s;
        LList maybe$Mnbound$Plstart$Plend = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            Procedure procedure2 = Scheme.applyToArgs;
            Procedure procedure3 = Scheme.applyToArgs;
            try {
                Object obj2 = loc$bound.get();
                IntNum intNum = Lit7;
                try {
                    boolean x = numbers.isInteger(loc$bound.get());
                    if (x) {
                        try {
                            x = numbers.isExact(loc$bound.get());
                            if (x) {
                                try {
                                    apply2 = Scheme.numLEq.apply2(Lit0, loc$bound.get());
                                } catch (UnboundLocationException e) {
                                    e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 909, 19);
                                    throw e;
                                }
                            }
                            apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 908, 21);
                            throw e2;
                        }
                    }
                    apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                    try {
                        Object apply22 = procedure2.apply2(procedure3.apply3(obj2, intNum, apply2), loc$rest.get());
                        try {
                            try {
                                if (numbers.isZero((Number) loc$bound.get())) {
                                    apply2 = Lit8;
                                } else {
                                    try {
                                        apply2 = loc$bound.get();
                                    } catch (UnboundLocationException e22) {
                                        e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 911, 18);
                                        throw e22;
                                    }
                                }
                                gnu_kawa_slib_srfi13_frame56.bound = apply2;
                                return procedure.apply4(obj, maybe$Mnbound$Plstart$Plend, apply22, call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame56.lambda$Fn134, gnu_kawa_slib_srfi13_frame56.lambda$Fn135));
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "zero?", 1, apply2);
                            }
                        } catch (UnboundLocationException e222) {
                            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 911, 29);
                            throw e222;
                        }
                    } catch (UnboundLocationException e2222) {
                        e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 910, 7);
                        throw e2222;
                    }
                } catch (UnboundLocationException e22222) {
                    e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 72);
                    throw e22222;
                }
            } catch (UnboundLocationException e222222) {
                e222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 42);
                throw e222222;
            }
        } catch (UnboundLocationException e2222222) {
            e2222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 3);
            throw e2222222;
        }
    }

    public static Object stringHashCi$V(Object s, Object[] argsArray) {
        Object apply2;
        frame57 gnu_kawa_slib_srfi13_frame57 = new frame57();
        gnu_kawa_slib_srfi13_frame57.f57s = s;
        LList maybe$Mnbound$Plstart$Plend = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            Procedure procedure2 = Scheme.applyToArgs;
            Procedure procedure3 = Scheme.applyToArgs;
            try {
                Object obj2 = loc$bound.get();
                IntNum intNum = Lit9;
                try {
                    boolean x = numbers.isInteger(loc$bound.get());
                    if (x) {
                        try {
                            x = numbers.isExact(loc$bound.get());
                            if (x) {
                                try {
                                    apply2 = Scheme.numLEq.apply2(Lit0, loc$bound.get());
                                } catch (UnboundLocationException e) {
                                    e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 918, 19);
                                    throw e;
                                }
                            }
                            apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 917, 21);
                            throw e2;
                        }
                    }
                    apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                    try {
                        Object apply22 = procedure2.apply2(procedure3.apply3(obj2, intNum, apply2), loc$rest.get());
                        try {
                            try {
                                if (numbers.isZero((Number) loc$bound.get())) {
                                    apply2 = Lit10;
                                } else {
                                    try {
                                        apply2 = loc$bound.get();
                                    } catch (UnboundLocationException e22) {
                                        e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 920, 18);
                                        throw e22;
                                    }
                                }
                                gnu_kawa_slib_srfi13_frame57.bound = apply2;
                                return procedure.apply4(obj, maybe$Mnbound$Plstart$Plend, apply22, call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame57.lambda$Fn136, gnu_kawa_slib_srfi13_frame57.lambda$Fn137));
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "zero?", 1, apply2);
                            }
                        } catch (UnboundLocationException e222) {
                            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 920, 29);
                            throw e222;
                        }
                    } catch (UnboundLocationException e2222) {
                        e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 919, 7);
                        throw e2222;
                    }
                } catch (UnboundLocationException e22222) {
                    e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 72);
                    throw e22222;
                }
            } catch (UnboundLocationException e222222) {
                e222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 42);
                throw e222222;
            }
        } catch (UnboundLocationException e2222222) {
            e2222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 3);
            throw e2222222;
        }
    }

    public static Object stringUpcase$V(Object s, Object[] argsArray) {
        frame58 gnu_kawa_slib_srfi13_frame58 = new frame58();
        gnu_kawa_slib_srfi13_frame58.f58s = s;
        gnu_kawa_slib_srfi13_frame58.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame58.lambda$Fn139, gnu_kawa_slib_srfi13_frame58.lambda$Fn140);
    }

    public static Object stringUpcase$Ex$V(Object s, Object[] argsArray) {
        frame59 gnu_kawa_slib_srfi13_frame59 = new frame59();
        gnu_kawa_slib_srfi13_frame59.f59s = s;
        gnu_kawa_slib_srfi13_frame59.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame59.lambda$Fn141, gnu_kawa_slib_srfi13_frame59.lambda$Fn142);
    }

    public static Object stringDowncase$V(Object s, Object[] argsArray) {
        frame60 gnu_kawa_slib_srfi13_frame60 = new frame60();
        gnu_kawa_slib_srfi13_frame60.f61s = s;
        gnu_kawa_slib_srfi13_frame60.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame60.lambda$Fn143, gnu_kawa_slib_srfi13_frame60.lambda$Fn144);
    }

    public static Object stringDowncase$Ex$V(Object s, Object[] argsArray) {
        frame61 gnu_kawa_slib_srfi13_frame61 = new frame61();
        gnu_kawa_slib_srfi13_frame61.f62s = s;
        gnu_kawa_slib_srfi13_frame61.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame61.lambda$Fn145, gnu_kawa_slib_srfi13_frame61.lambda$Fn146);
    }

    public static Object $PcStringTitlecase$Ex(Object s, Object start, Object end) {
        Object i = start;
        while (true) {
            try {
                Object temp = stringIndex$V(s, loc$char$Mncased$Qu.get(), new Object[]{i, end});
                if (temp == Boolean.FALSE) {
                    return Values.empty;
                }
                try {
                    CharSeq charSeq = (CharSeq) s;
                    try {
                        int intValue = ((Number) temp).intValue();
                        try {
                            try {
                                Object charTitlecase = unicode.charTitlecase(Char.make(strings.stringRef((CharSequence) s, ((Number) temp).intValue())));
                                try {
                                    strings.stringSet$Ex(charSeq, intValue, charTitlecase.charValue());
                                    Object i1 = AddOp.$Pl.apply2(temp, Lit1);
                                    try {
                                        Boolean temp2 = stringSkip$V(s, loc$char$Mncased$Qu.get(), new Object[]{i1, end});
                                        if (temp2 != Boolean.FALSE) {
                                            stringDowncase$Ex$V(s, new Object[]{i1, temp2});
                                            i = AddOp.$Pl.apply2(temp2, Lit1);
                                        } else {
                                            return stringDowncase$Ex$V(s, new Object[]{i1, end});
                                        }
                                    } catch (UnboundLocationException e) {
                                        e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 959, 31);
                                        throw e;
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-set!", 3, charTitlecase);
                                }
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "string-ref", 2, temp);
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "string-set!", 2, temp);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "string-set!", 1, s);
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 955, 28);
                throw e3;
            }
        }
    }

    public static Object stringTitlecase$Ex$V(Object s, Object[] argsArray) {
        frame62 gnu_kawa_slib_srfi13_frame62 = new frame62();
        gnu_kawa_slib_srfi13_frame62.f63s = s;
        gnu_kawa_slib_srfi13_frame62.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame62.lambda$Fn147, gnu_kawa_slib_srfi13_frame62.lambda$Fn148);
    }

    public static Object stringTitlecase$V(Object s, Object[] argsArray) {
        frame63 gnu_kawa_slib_srfi13_frame63 = new frame63();
        gnu_kawa_slib_srfi13_frame63.f64s = s;
        gnu_kawa_slib_srfi13_frame63.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame63.lambda$Fn149, gnu_kawa_slib_srfi13_frame63.lambda$Fn150);
    }

    public static Object stringTake(Object s, Object n) {
        frame64 gnu_kawa_slib_srfi13_frame64 = new frame64();
        gnu_kawa_slib_srfi13_frame64.f66s = s;
        gnu_kawa_slib_srfi13_frame64.f65n = n;
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, gnu_kawa_slib_srfi13_frame64.f66s, string$Mntake);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), gnu_kawa_slib_srfi13_frame64.lambda$Fn151, gnu_kawa_slib_srfi13_frame64.f65n, string$Mntake);
                Object obj = gnu_kawa_slib_srfi13_frame64.f66s;
                try {
                    CharSequence charSequence = (CharSequence) obj;
                    Object obj2 = gnu_kawa_slib_srfi13_frame64.f65n;
                    try {
                        return $PcSubstring$SlShared(charSequence, 0, ((Number) obj2).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "%substring/shared", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%substring/shared", 0, obj);
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 996, 3);
                throw e3;
            }
        } catch (UnboundLocationException e32) {
            e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 995, 3);
            throw e32;
        }
    }

    public static Object stringTakeRight(Object s, Object n) {
        frame65 gnu_kawa_slib_srfi13_frame65 = new frame65();
        gnu_kawa_slib_srfi13_frame65.f67n = n;
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, s, string$Mntake$Mnright);
            try {
                gnu_kawa_slib_srfi13_frame65.len = strings.stringLength((CharSequence) s);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), gnu_kawa_slib_srfi13_frame65.lambda$Fn152, gnu_kawa_slib_srfi13_frame65.f67n, string$Mntake$Mnright);
                    try {
                        CharSequence charSequence = (CharSequence) s;
                        Object apply2 = AddOp.$Mn.apply2(Integer.valueOf(gnu_kawa_slib_srfi13_frame65.len), gnu_kawa_slib_srfi13_frame65.f67n);
                        try {
                            return $PcSubstring$SlShared(charSequence, ((Number) apply2).intValue(), gnu_kawa_slib_srfi13_frame65.len);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 1, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 0, s);
                    }
                } catch (UnboundLocationException e3) {
                    e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1004, 5);
                    throw e3;
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "string-length", 1, s);
            }
        } catch (UnboundLocationException e32) {
            e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_CANVAS_WIDTH_ERROR, 3);
            throw e32;
        }
    }

    public static Object stringDrop(CharSequence s, Object n) {
        frame66 gnu_kawa_slib_srfi13_frame66 = new frame66();
        gnu_kawa_slib_srfi13_frame66.f68n = n;
        gnu_kawa_slib_srfi13_frame66.len = strings.stringLength(s);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), gnu_kawa_slib_srfi13_frame66.lambda$Fn153, gnu_kawa_slib_srfi13_frame66.f68n, string$Mndrop);
            Object obj = gnu_kawa_slib_srfi13_frame66.f68n;
            try {
                return $PcSubstring$SlShared(s, ((Number) obj).intValue(), gnu_kawa_slib_srfi13_frame66.len);
            } catch (ClassCastException e) {
                throw new WrongType(e, "%substring/shared", 1, obj);
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1010, 5);
            throw e2;
        }
    }

    public static Object stringDropRight(CharSequence s, Object n) {
        frame67 gnu_kawa_slib_srfi13_frame67 = new frame67();
        gnu_kawa_slib_srfi13_frame67.f69n = n;
        gnu_kawa_slib_srfi13_frame67.len = strings.stringLength(s);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), gnu_kawa_slib_srfi13_frame67.lambda$Fn154, gnu_kawa_slib_srfi13_frame67.f69n, string$Mndrop$Mnright);
            Object apply2 = AddOp.$Mn.apply2(Integer.valueOf(gnu_kawa_slib_srfi13_frame67.len), gnu_kawa_slib_srfi13_frame67.f69n);
            try {
                return $PcSubstring$SlShared(s, 0, ((Number) apply2).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "%substring/shared", 2, apply2);
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1016, 5);
            throw e2;
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 218:
                try {
                    return stringTabulate(obj, ((Number) obj2).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-tabulate", 2, obj2);
                }
            case 280:
                return stringTake(obj, obj2);
            case 281:
                return stringTakeRight(obj, obj2);
            case 282:
                try {
                    return stringDrop((CharSequence) obj, obj2);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-drop", 1, obj);
                }
            case 283:
                try {
                    return stringDropRight((CharSequence) obj, obj2);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "string-drop-right", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object stringTrim$V(Object s, Object[] argsArray) {
        frame68 gnu_kawa_slib_srfi13_frame68 = new frame68();
        gnu_kawa_slib_srfi13_frame68.f70s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame68.lambda$Fn155, gnu_kawa_slib_srfi13_frame68.lambda$Fn156));
                    } catch (UnboundLocationException e) {
                        e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e22) {
                e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 40);
                throw e22;
            }
        } catch (UnboundLocationException e222) {
            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 3);
            throw e222;
        }
    }

    public static Object stringTrimRight$V(Object s, Object[] argsArray) {
        frame69 gnu_kawa_slib_srfi13_frame69 = new frame69();
        gnu_kawa_slib_srfi13_frame69.f71s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame69.lambda$Fn157, gnu_kawa_slib_srfi13_frame69.lambda$Fn158));
                    } catch (UnboundLocationException e) {
                        e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e22) {
                e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 40);
                throw e22;
            }
        } catch (UnboundLocationException e222) {
            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 3);
            throw e222;
        }
    }

    public static Object stringTrimBoth$V(Object s, Object[] argsArray) {
        frame70 gnu_kawa_slib_srfi13_frame70 = new frame70();
        gnu_kawa_slib_srfi13_frame70.f73s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame70.lambda$Fn159, gnu_kawa_slib_srfi13_frame70.lambda$Fn160));
                    } catch (UnboundLocationException e) {
                        e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e22) {
                e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 40);
                throw e22;
            }
        } catch (UnboundLocationException e222) {
            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 3);
            throw e222;
        }
    }

    public static Object stringPadRight$V(Object s, Object n, Object[] argsArray) {
        frame71 gnu_kawa_slib_srfi13_frame71 = new frame71();
        gnu_kawa_slib_srfi13_frame71.f75s = s;
        gnu_kawa_slib_srfi13_frame71.f74n = n;
        try {
            try {
                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), LList.makeList(argsArray, 0), Scheme.applyToArgs.apply2(Invoke.make.apply3(LangPrimType.charType, Lit12, characters.isChar(LangPrimType.charType) ? Boolean.TRUE : Boolean.FALSE), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame71.lambda$Fn161, gnu_kawa_slib_srfi13_frame71.lambda$Fn162));
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1045, 63);
                throw e;
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1045, 3);
            throw e2;
        }
    }

    public static Object stringPad$V(Object s, Object n, Object[] argsArray) {
        frame72 gnu_kawa_slib_srfi13_frame72 = new frame72();
        gnu_kawa_slib_srfi13_frame72.f77s = s;
        gnu_kawa_slib_srfi13_frame72.f76n = n;
        try {
            try {
                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), LList.makeList(argsArray, 0), Scheme.applyToArgs.apply2(Invoke.make.apply3(LangPrimType.charType, Lit12, characters.isChar(LangPrimType.charType) ? Boolean.TRUE : Boolean.FALSE), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame72.lambda$Fn164, gnu_kawa_slib_srfi13_frame72.lambda$Fn165));
            } catch (UnboundLocationException e) {
                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1057, 63);
                throw e;
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1057, 3);
            throw e2;
        }
    }

    public static Object stringDelete$V(Object criterion, Object s, Object[] argsArray) {
        frame73 gnu_kawa_slib_srfi13_frame73 = new frame73();
        gnu_kawa_slib_srfi13_frame73.criterion = criterion;
        gnu_kawa_slib_srfi13_frame73.f78s = s;
        gnu_kawa_slib_srfi13_frame73.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame73.lambda$Fn167, gnu_kawa_slib_srfi13_frame73.lambda$Fn168);
    }

    public static Object stringFilter$V(Object criterion, Object s, Object[] argsArray) {
        frame75 gnu_kawa_slib_srfi13_frame75 = new frame75();
        gnu_kawa_slib_srfi13_frame75.criterion = criterion;
        gnu_kawa_slib_srfi13_frame75.f79s = s;
        gnu_kawa_slib_srfi13_frame75.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame75.lambda$Fn172, gnu_kawa_slib_srfi13_frame75.lambda$Fn173);
    }

    public static Object stringIndex$V(Object str, Object criterion, Object[] argsArray) {
        frame77 gnu_kawa_slib_srfi13_frame77 = new frame77();
        gnu_kawa_slib_srfi13_frame77.str = str;
        gnu_kawa_slib_srfi13_frame77.criterion = criterion;
        gnu_kawa_slib_srfi13_frame77.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame77.lambda$Fn177, gnu_kawa_slib_srfi13_frame77.lambda$Fn178);
    }

    public static Object stringIndexRight$V(Object str, Object criterion, Object[] argsArray) {
        frame78 gnu_kawa_slib_srfi13_frame78 = new frame78();
        gnu_kawa_slib_srfi13_frame78.str = str;
        gnu_kawa_slib_srfi13_frame78.criterion = criterion;
        gnu_kawa_slib_srfi13_frame78.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame78.lambda$Fn179, gnu_kawa_slib_srfi13_frame78.lambda$Fn180);
    }

    public static Object stringSkip$V(Object str, Object criterion, Object[] argsArray) {
        frame79 gnu_kawa_slib_srfi13_frame79 = new frame79();
        gnu_kawa_slib_srfi13_frame79.str = str;
        gnu_kawa_slib_srfi13_frame79.criterion = criterion;
        gnu_kawa_slib_srfi13_frame79.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame79.lambda$Fn181, gnu_kawa_slib_srfi13_frame79.lambda$Fn182);
    }

    public static Object stringSkipRight$V(Object str, Object criterion, Object[] argsArray) {
        frame80 gnu_kawa_slib_srfi13_frame80 = new frame80();
        gnu_kawa_slib_srfi13_frame80.str = str;
        gnu_kawa_slib_srfi13_frame80.criterion = criterion;
        gnu_kawa_slib_srfi13_frame80.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame80.lambda$Fn183, gnu_kawa_slib_srfi13_frame80.lambda$Fn184);
    }

    public static Object stringCount$V(Object s, Object criterion, Object[] argsArray) {
        frame81 gnu_kawa_slib_srfi13_frame81 = new frame81();
        gnu_kawa_slib_srfi13_frame81.f81s = s;
        gnu_kawa_slib_srfi13_frame81.criterion = criterion;
        gnu_kawa_slib_srfi13_frame81.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame81.lambda$Fn185, gnu_kawa_slib_srfi13_frame81.lambda$Fn186);
    }

    public static Object stringFill$Ex$V(Object s, Object char_, Object[] argsArray) {
        frame82 gnu_kawa_slib_srfi13_frame82 = new frame82();
        gnu_kawa_slib_srfi13_frame82.f83s = s;
        gnu_kawa_slib_srfi13_frame82.f82char = char_;
        gnu_kawa_slib_srfi13_frame82.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), characters.char$Qu, gnu_kawa_slib_srfi13_frame82.f82char, string$Mnfill$Ex);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame82.lambda$Fn187, gnu_kawa_slib_srfi13_frame82.lambda$Fn188);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1270, 3);
            throw e;
        }
    }

    public static Object stringCopy$Ex(Object to, int tstart, CharSequence from, int fstart, int fend) {
        $PcCheckBounds(string$Mncopy$Ex, from, fstart, fend);
        try {
            $PcCheckSubstringSpec(string$Mncopy$Ex, (CharSequence) to, tstart, (fend - fstart) + tstart);
            try {
                return $PcStringCopy$Ex((CharSequence) to, tstart, from, fstart, fend);
            } catch (ClassCastException e) {
                throw new WrongType(e, "%string-copy!", 0, to);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "%check-substring-spec", 1, to);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE /*194*/:
                return stringParseStart$PlEnd(obj, obj2, obj3);
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION /*196*/:
                return stringParseFinalStart$PlEnd(obj, obj2, obj3);
            case 197:
                return isSubstringSpecOk(obj, obj2, obj3) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /*201*/:
                try {
                    try {
                        try {
                            return $PcSubstring$SlShared((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "%substring/shared", 1, obj);
                }
            case 277:
                return $PcStringTitlecase$Ex(obj, obj2, obj3);
            case 299:
                try {
                    try {
                        return stringCopy$Ex(obj, ((Number) obj2).intValue(), (CharSequence) obj3);
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-copy!", 3, obj3);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "string-copy!", 2, obj2);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object $PcStringCopy$Ex(CharSequence to, int tstart, CharSequence from, int fstart, int fend) {
        int j;
        int i;
        if (fstart > tstart) {
            j = tstart;
            i = fstart;
            while (i < fend) {
                try {
                    strings.stringSet$Ex((CharSeq) to, j, strings.stringRef(from, i));
                    j++;
                    i++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-set!", 1, (Object) to);
                }
            }
            return Values.empty;
        }
        j = (tstart - 1) + (fend - fstart);
        i = fend - 1;
        while (i >= fstart) {
            try {
                strings.stringSet$Ex((CharSeq) to, j, strings.stringRef(from, i));
                j--;
                i--;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-set!", 1, (Object) to);
            }
        }
        return Values.empty;
    }

    public static Object stringContains$V(Object text, Object pattern, Object[] argsArray) {
        frame83 gnu_kawa_slib_srfi13_frame83 = new frame83();
        gnu_kawa_slib_srfi13_frame83.text = text;
        gnu_kawa_slib_srfi13_frame83.pattern = pattern;
        gnu_kawa_slib_srfi13_frame83.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame83.lambda$Fn189, gnu_kawa_slib_srfi13_frame83.lambda$Fn190);
    }

    public static Object stringContainsCi$V(Object text, Object pattern, Object[] argsArray) {
        frame85 gnu_kawa_slib_srfi13_frame85 = new frame85();
        gnu_kawa_slib_srfi13_frame85.text = text;
        gnu_kawa_slib_srfi13_frame85.pattern = pattern;
        gnu_kawa_slib_srfi13_frame85.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame85.lambda$Fn193, gnu_kawa_slib_srfi13_frame85.lambda$Fn194);
    }

    public static Object $PcKmpSearch(Object pattern, Object text, Object c$Eq, Object p$Mnstart, Object p$Mnend, Object t$Mnstart, Object t$Mnend) {
        Object plen = AddOp.$Mn.apply2(p$Mnend, p$Mnstart);
        Object rv = makeKmpRestartVector$V(pattern, new Object[]{c$Eq, p$Mnstart, p$Mnend});
        IntNum intNum = Lit0;
        Object apply2 = AddOp.$Mn.apply2(t$Mnend, t$Mnstart);
        Object ti = t$Mnstart;
        Object pj = plen;
        Object obj = intNum;
        while (Scheme.numEqu.apply2(obj, plen) == Boolean.FALSE) {
            Object apply22 = Scheme.numLEq.apply2(pj, apply2);
            try {
                boolean x = ((Boolean) apply22).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    Procedure procedure = Scheme.applyToArgs;
                    try {
                        try {
                            Char make = Char.make(strings.stringRef((CharSequence) text, ((Number) ti).intValue()));
                            try {
                                CharSequence charSequence = (CharSequence) pattern;
                                Object apply23 = AddOp.$Pl.apply2(p$Mnstart, obj);
                                try {
                                    Object pi;
                                    if (procedure.apply3(c$Eq, make, Char.make(strings.stringRef(charSequence, ((Number) apply23).intValue()))) != Boolean.FALSE) {
                                        ti = AddOp.$Pl.apply2(Lit1, ti);
                                        pi = AddOp.$Pl.apply2(Lit1, obj);
                                        apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                        pj = AddOp.$Mn.apply2(pj, Lit1);
                                        obj = pi;
                                    } else {
                                        try {
                                            try {
                                                pi = vectors.vectorRef((FVector) rv, ((Number) obj).intValue());
                                                if (Scheme.numEqu.apply2(pi, Lit13) != Boolean.FALSE) {
                                                    ti = AddOp.$Pl.apply2(ti, Lit1);
                                                    pi = Lit0;
                                                    apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                                    pj = plen;
                                                    obj = pi;
                                                } else {
                                                    pj = AddOp.$Mn.apply2(plen, pi);
                                                    obj = pi;
                                                }
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "vector-ref", 2, obj);
                                            }
                                        } catch (ClassCastException e2) {
                                            throw new WrongType(e2, "vector-ref", 1, rv);
                                        }
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 2, apply23);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 1, pattern);
                            }
                        } catch (ClassCastException e2222) {
                            throw new WrongType(e2222, "string-ref", 2, ti);
                        }
                    } catch (ClassCastException e22222) {
                        throw new WrongType(e22222, "string-ref", 1, text);
                    }
                }
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "x", -2, apply22);
            }
        }
        return AddOp.$Mn.apply2(ti, plen);
    }

    public static Object makeKmpRestartVector$V(Object pattern, Object[] argsArray) {
        frame87 gnu_kawa_slib_srfi13_frame87 = new frame87();
        gnu_kawa_slib_srfi13_frame87.pattern = pattern;
        LList maybe$Mnc$Eq$Plstart$Plend = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            try {
                try {
                    try {
                        try {
                            Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$c$Eq.get(), characters.char$Eq$Qu, misc.isProcedure(loc$c$Eq.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$start.get(), loc$end.get()), gnu_kawa_slib_srfi13_frame87.lambda$Fn197));
                            try {
                                try {
                                    Object rvlen = AddOp.$Mn.apply2(loc$end.get(), loc$start.get());
                                    try {
                                        FVector rv = vectors.makeVector(((Number) rvlen).intValue(), Lit13);
                                        if (Scheme.numGrt.apply2(rvlen, Lit0) != Boolean.FALSE) {
                                            Object rvlen$Mn1 = AddOp.$Mn.apply2(rvlen, Lit1);
                                            Object obj2 = gnu_kawa_slib_srfi13_frame87.pattern;
                                            try {
                                                CharSequence charSequence = (CharSequence) obj2;
                                                try {
                                                    Object obj3 = loc$start.get();
                                                    try {
                                                        char c0 = strings.stringRef(charSequence, ((Number) obj3).intValue());
                                                        Object i = Lit0;
                                                        Object j = Lit13;
                                                        try {
                                                            Object obj4 = loc$start.get();
                                                            while (Scheme.numLss.apply2(i, rvlen$Mn1) != Boolean.FALSE) {
                                                                Procedure procedure2;
                                                                Object obj5;
                                                                Object i1;
                                                                while (Scheme.numEqu.apply2(j, Lit13) == Boolean.FALSE) {
                                                                    procedure2 = Scheme.applyToArgs;
                                                                    try {
                                                                        obj5 = loc$c$Eq.get();
                                                                        obj2 = gnu_kawa_slib_srfi13_frame87.pattern;
                                                                        try {
                                                                            try {
                                                                                Char make = Char.make(strings.stringRef((CharSequence) obj2, ((Number) obj4).intValue()));
                                                                                obj2 = gnu_kawa_slib_srfi13_frame87.pattern;
                                                                                try {
                                                                                    charSequence = (CharSequence) obj2;
                                                                                    try {
                                                                                        obj3 = AddOp.$Pl.apply2(j, loc$start.get());
                                                                                        try {
                                                                                            if (procedure2.apply3(obj5, make, Char.make(strings.stringRef(charSequence, ((Number) obj3).intValue()))) != Boolean.FALSE) {
                                                                                                i1 = AddOp.$Pl.apply2(Lit1, i);
                                                                                                Object j1 = AddOp.$Pl.apply2(Lit1, j);
                                                                                                try {
                                                                                                    vectors.vectorSet$Ex(rv, ((Number) i1).intValue(), j1);
                                                                                                    j = j1;
                                                                                                    i = i1;
                                                                                                    obj4 = AddOp.$Pl.apply2(obj4, Lit1);
                                                                                                    break;
                                                                                                } catch (ClassCastException e) {
                                                                                                    throw new WrongType(e, "vector-set!", 2, i1);
                                                                                                }
                                                                                            }
                                                                                            try {
                                                                                                j = vectors.vectorRef(rv, ((Number) j).intValue());
                                                                                            } catch (ClassCastException e2) {
                                                                                                throw new WrongType(e2, "vector-ref", 2, j);
                                                                                            }
                                                                                        } catch (ClassCastException e22) {
                                                                                            throw new WrongType(e22, "string-ref", 2, obj3);
                                                                                        }
                                                                                    } catch (UnboundLocationException e3) {
                                                                                        e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1422, 59);
                                                                                        throw e3;
                                                                                    }
                                                                                } catch (ClassCastException e4) {
                                                                                    throw new WrongType(e4, "string-ref", 1, obj2);
                                                                                }
                                                                            } catch (ClassCastException e222) {
                                                                                throw new WrongType(e222, "string-ref", 2, obj4);
                                                                            }
                                                                        } catch (ClassCastException e42) {
                                                                            throw new WrongType(e42, "string-ref", 1, obj2);
                                                                        }
                                                                    } catch (UnboundLocationException e32) {
                                                                        e32.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1422, 7);
                                                                        throw e32;
                                                                    }
                                                                }
                                                                i1 = AddOp.$Pl.apply2(Lit1, i);
                                                                procedure2 = Scheme.applyToArgs;
                                                                try {
                                                                    obj5 = loc$c$Eq.get();
                                                                    obj2 = gnu_kawa_slib_srfi13_frame87.pattern;
                                                                    try {
                                                                        charSequence = (CharSequence) obj2;
                                                                        obj3 = AddOp.$Pl.apply2(obj4, Lit1);
                                                                        try {
                                                                            if (procedure2.apply3(obj5, Char.make(strings.stringRef(charSequence, ((Number) obj3).intValue())), Char.make(c0)) == Boolean.FALSE) {
                                                                                try {
                                                                                    vectors.vectorSet$Ex(rv, ((Number) i1).intValue(), Lit0);
                                                                                } catch (ClassCastException e2222) {
                                                                                    throw new WrongType(e2222, "vector-set!", 2, i1);
                                                                                }
                                                                            }
                                                                            j = Lit0;
                                                                            i = i1;
                                                                            obj4 = AddOp.$Pl.apply2(obj4, Lit1);
                                                                        } catch (ClassCastException e22222) {
                                                                            throw new WrongType(e22222, "string-ref", 2, obj3);
                                                                        }
                                                                    } catch (ClassCastException e422) {
                                                                        throw new WrongType(e422, "string-ref", 1, obj2);
                                                                    }
                                                                } catch (UnboundLocationException e322) {
                                                                    e322.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1418, 18);
                                                                    throw e322;
                                                                }
                                                            }
                                                        } catch (UnboundLocationException e3222) {
                                                            e3222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1410, 6);
                                                            throw e3222;
                                                        }
                                                    } catch (ClassCastException e222222) {
                                                        throw new WrongType(e222222, "string-ref", 2, obj3);
                                                    }
                                                } catch (UnboundLocationException e32222) {
                                                    e32222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1406, 27);
                                                    throw e32222;
                                                }
                                            } catch (ClassCastException e4222) {
                                                throw new WrongType(e4222, "string-ref", 1, obj2);
                                            }
                                        }
                                        return procedure.apply4(obj, maybe$Mnc$Eq$Plstart$Plend, apply2, rv);
                                    } catch (ClassCastException e2222222) {
                                        throw new WrongType(e2222222, "make-vector", 1, rvlen);
                                    }
                                } catch (UnboundLocationException e322222) {
                                    e322222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, 26);
                                    throw e322222;
                                }
                            } catch (UnboundLocationException e3222222) {
                                e3222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, 22);
                                throw e3222222;
                            }
                        } catch (UnboundLocationException e32222222) {
                            e32222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1399, 14);
                            throw e32222222;
                        }
                    } catch (UnboundLocationException e322222222) {
                        e322222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1399, 7);
                        throw e322222222;
                    }
                } catch (UnboundLocationException e3222222222) {
                    e3222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1398, 43);
                    throw e3222222222;
                }
            } catch (UnboundLocationException e32222222222) {
                e32222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1398, 20);
                throw e32222222222;
            }
        } catch (UnboundLocationException e322222222222) {
            e322222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1397, 3);
            throw e322222222222;
        }
    }

    public static Object kmpStep(Object pat, Object rv, Object c, Object i, Object c$Eq, Object p$Mnstart) {
        do {
            Procedure procedure = Scheme.applyToArgs;
            try {
                CharSequence charSequence = (CharSequence) pat;
                Object apply2 = AddOp.$Pl.apply2(i, p$Mnstart);
                try {
                    if (procedure.apply3(c$Eq, c, Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()))) != Boolean.FALSE) {
                        return AddOp.$Pl.apply2(i, Lit1);
                    }
                    try {
                        try {
                            i = vectors.vectorRef((FVector) rv, ((Number) i).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "vector-ref", 2, i);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "vector-ref", 1, rv);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "string-ref", 2, apply2);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "string-ref", 1, pat);
            }
        } while (Scheme.numEqu.apply2(i, Lit13) == Boolean.FALSE);
        return Lit0;
    }

    public static Object stringKmpPartialSearch$V(Object pat, Object rv, Object s, Object i, Object[] argsArray) {
        frame88 gnu_kawa_slib_srfi13_frame88 = new frame88();
        gnu_kawa_slib_srfi13_frame88.f84s = s;
        LList c$Eq$Plp$Mnstart$Pls$Mnstart$Pls$Mnend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), vectors.vector$Qu, rv, string$Mnkmp$Mnpartial$Mnsearch);
            Procedure procedure = Scheme.applyToArgs;
            try {
                Object obj = loc$let$Mnoptionals$St.get();
                Procedure procedure2 = Scheme.applyToArgs;
                try {
                    try {
                        Object apply3 = Scheme.applyToArgs.apply3(loc$c$Eq.get(), characters.char$Eq$Qu, misc.isProcedure(loc$c$Eq.get()) ? Boolean.TRUE : Boolean.FALSE);
                        Procedure procedure3 = Scheme.applyToArgs;
                        try {
                            Object obj2 = loc$p$Mnstart.get();
                            IntNum intNum = Lit0;
                            try {
                                Object apply2;
                                boolean x = numbers.isInteger(loc$p$Mnstart.get());
                                if (x) {
                                    try {
                                        x = numbers.isExact(loc$p$Mnstart.get());
                                        if (x) {
                                            try {
                                                apply2 = Scheme.numLEq.apply2(Lit0, loc$p$Mnstart.get());
                                            } catch (UnboundLocationException e) {
                                                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 64);
                                                throw e;
                                            }
                                        }
                                        apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                                    } catch (UnboundLocationException e2) {
                                        e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 49);
                                        throw e2;
                                    }
                                }
                                apply2 = x ? Boolean.TRUE : Boolean.FALSE;
                                try {
                                    try {
                                        Object apply32 = procedure2.apply3(apply3, procedure3.apply3(obj2, intNum, apply2), Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$s$Mnstart.get(), loc$s$Mnend.get()), gnu_kawa_slib_srfi13_frame88.lambda$Fn198));
                                        try {
                                            gnu_kawa_slib_srfi13_frame88.patlen = vectors.vectorLength((FVector) rv);
                                            try {
                                                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), gnu_kawa_slib_srfi13_frame88.lambda$Fn199, i, string$Mnkmp$Mnpartial$Mnsearch);
                                                try {
                                                    Object si = loc$s$Mnstart.get();
                                                    Object vi = i;
                                                    while (Scheme.numEqu.apply2(vi, Integer.valueOf(gnu_kawa_slib_srfi13_frame88.patlen)) == Boolean.FALSE) {
                                                        try {
                                                            if (Scheme.numEqu.apply2(si, loc$s$Mnend.get()) != Boolean.FALSE) {
                                                                break;
                                                            }
                                                            apply2 = gnu_kawa_slib_srfi13_frame88.f84s;
                                                            try {
                                                                try {
                                                                    char c = strings.stringRef((CharSequence) apply2, ((Number) si).intValue());
                                                                    si = AddOp.$Pl.apply2(si, Lit1);
                                                                    do {
                                                                        Procedure procedure4 = Scheme.applyToArgs;
                                                                        try {
                                                                            Object obj3 = loc$c$Eq.get();
                                                                            Char make = Char.make(c);
                                                                            try {
                                                                                CharSequence charSequence = (CharSequence) pat;
                                                                                try {
                                                                                    apply3 = AddOp.$Pl.apply2(vi, loc$p$Mnstart.get());
                                                                                    try {
                                                                                        if (procedure4.apply3(obj3, make, Char.make(strings.stringRef(charSequence, ((Number) apply3).intValue()))) != Boolean.FALSE) {
                                                                                            vi = AddOp.$Pl.apply2(vi, Lit1);
                                                                                            break;
                                                                                        }
                                                                                        try {
                                                                                            try {
                                                                                                vi = vectors.vectorRef((FVector) rv, ((Number) vi).intValue());
                                                                                            } catch (ClassCastException e3) {
                                                                                                throw new WrongType(e3, "vector-ref", 2, vi);
                                                                                            }
                                                                                        } catch (ClassCastException e32) {
                                                                                            throw new WrongType(e32, "vector-ref", 1, rv);
                                                                                        }
                                                                                    } catch (ClassCastException e322) {
                                                                                        throw new WrongType(e322, "string-ref", 2, apply3);
                                                                                    }
                                                                                } catch (UnboundLocationException e22) {
                                                                                    e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1484, 42);
                                                                                    throw e22;
                                                                                }
                                                                            } catch (ClassCastException e3222) {
                                                                                throw new WrongType(e3222, "string-ref", 1, pat);
                                                                            }
                                                                        } catch (UnboundLocationException e222) {
                                                                            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1484, 14);
                                                                            throw e222;
                                                                        }
                                                                    } while (Scheme.numEqu.apply2(vi, Lit13) == Boolean.FALSE);
                                                                    vi = Lit0;
                                                                } catch (ClassCastException e32222) {
                                                                    throw new WrongType(e32222, "string-ref", 2, si);
                                                                }
                                                            } catch (ClassCastException e4) {
                                                                throw new WrongType(e4, "string-ref", 1, apply2);
                                                            }
                                                        } catch (UnboundLocationException e2222) {
                                                            e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1479, 15);
                                                            throw e2222;
                                                        }
                                                    }
                                                    vi = AddOp.$Mn.apply1(si);
                                                    return procedure.apply4(obj, c$Eq$Plp$Mnstart$Pls$Mnstart$Pls$Mnend, apply32, vi);
                                                } catch (UnboundLocationException e22222) {
                                                    e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1476, 7);
                                                    throw e22222;
                                                }
                                            } catch (UnboundLocationException e222222) {
                                                e222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1472, 7);
                                                throw e222222;
                                            }
                                        } catch (ClassCastException e322222) {
                                            throw new WrongType(e322222, "vector-length", 1, rv);
                                        }
                                    } catch (UnboundLocationException e2222222) {
                                        e2222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1468, 16);
                                        throw e2222222;
                                    }
                                } catch (UnboundLocationException e22222222) {
                                    e22222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1468, 7);
                                    throw e22222222;
                                }
                            } catch (UnboundLocationException e222222222) {
                                e222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 32);
                                throw e222222222;
                            }
                        } catch (UnboundLocationException e2222222222) {
                            e2222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 6);
                            throw e2222222222;
                        }
                    } catch (UnboundLocationException e22222222222) {
                        e22222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1466, 34);
                        throw e22222222222;
                    }
                } catch (UnboundLocationException e222222222222) {
                    e222222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1466, 6);
                    throw e222222222222;
                }
            } catch (UnboundLocationException e2222222222222) {
                e2222222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1465, 3);
                throw e2222222222222;
            }
        } catch (UnboundLocationException e22222222222222) {
            e22222222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1464, 3);
            throw e22222222222222;
        }
    }

    public static boolean isStringNull(Object s) {
        try {
            return numbers.isZero(Integer.valueOf(strings.stringLength((CharSequence) s)));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    public static Object stringReverse$V(Object s, Object[] argsArray) {
        frame89 gnu_kawa_slib_srfi13_frame89 = new frame89();
        gnu_kawa_slib_srfi13_frame89.f85s = s;
        gnu_kawa_slib_srfi13_frame89.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame89.lambda$Fn200, gnu_kawa_slib_srfi13_frame89.lambda$Fn201);
    }

    public static Object stringReverse$Ex$V(Object s, Object[] argsArray) {
        frame90 gnu_kawa_slib_srfi13_frame90 = new frame90();
        gnu_kawa_slib_srfi13_frame90.f87s = s;
        gnu_kawa_slib_srfi13_frame90.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame90.lambda$Fn202, gnu_kawa_slib_srfi13_frame90.lambda$Fn203);
    }

    public static CharSequence reverseList$To$String(Object clist) {
        try {
            int len = lists.length((LList) clist);
            Object s = strings.makeString(len);
            Object valueOf = Integer.valueOf(len - 1);
            while (lists.isPair(clist)) {
                try {
                    CharSeq charSeq = (CharSeq) s;
                    try {
                        int intValue = ((Number) valueOf).intValue();
                        Object apply1 = lists.car.apply1(clist);
                        try {
                            strings.stringSet$Ex(charSeq, intValue, ((Char) apply1).charValue());
                            valueOf = AddOp.$Mn.apply2(valueOf, Lit1);
                            clist = lists.cdr.apply1(clist);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, apply1);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, valueOf);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "string-set!", 1, s);
                }
            }
            return s;
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "length", 1, clist);
        }
    }

    public static Object string$To$List$V(Object s, Object[] argsArray) {
        frame91 gnu_kawa_slib_srfi13_frame91 = new frame91();
        gnu_kawa_slib_srfi13_frame91.f88s = s;
        gnu_kawa_slib_srfi13_frame91.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame91.lambda$Fn204, gnu_kawa_slib_srfi13_frame91.lambda$Fn205);
    }

    public static Object stringAppend$SlShared$V(Object[] argsArray) {
        return stringConcatenate$SlShared(LList.makeList(argsArray, 0));
    }

    public static Object stringConcatenate$SlShared(Object strings) {
        int slen;
        Object nchars = Lit0;
        Object first = Boolean.FALSE;
        while (lists.isPair(strings)) {
            Object string = lists.car.apply1(strings);
            Object tail = lists.cdr.apply1(strings);
            try {
                slen = strings.stringLength((CharSequence) string);
                if (numbers.isZero(Integer.valueOf(slen))) {
                    strings = tail;
                } else {
                    nchars = AddOp.$Pl.apply2(nchars, Integer.valueOf(slen));
                    if (first == Boolean.FALSE) {
                        first = strings;
                    }
                    strings = tail;
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, string);
            }
        }
        try {
            if (numbers.isZero((Number) nchars)) {
                return "";
            }
            Procedure procedure = Scheme.numEqu;
            Object apply1 = lists.car.apply1(first);
            try {
                if (procedure.apply2(nchars, Integer.valueOf(strings.stringLength((CharSequence) apply1))) != Boolean.FALSE) {
                    return lists.car.apply1(first);
                }
                try {
                    Object ans = strings.makeString(((Number) nchars).intValue());
                    Object obj = Lit0;
                    strings = first;
                    while (lists.isPair(strings)) {
                        Object s = lists.car.apply1(strings);
                        try {
                            slen = strings.stringLength((CharSequence) s);
                            try {
                                try {
                                    $PcStringCopy$Ex(ans, ((Number) obj).intValue(), (CharSequence) s, 0, slen);
                                    strings = lists.cdr.apply1(strings);
                                    obj = AddOp.$Pl.apply2(obj, Integer.valueOf(slen));
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "%string-copy!", 2, s);
                                }
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "%string-copy!", 1, obj);
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "string-length", 1, s);
                        }
                    }
                    return ans;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "make-string", 1, nchars);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-length", 1, apply1);
            }
        } catch (ClassCastException e22222) {
            throw new WrongType(e22222, "zero?", 1, nchars);
        }
    }

    public static CharSequence stringConcatenate(Object strings) {
        Object total = Lit0;
        Object strings2 = strings;
        while (lists.isPair(strings2)) {
            Object strings3 = lists.cdr.apply1(strings2);
            Procedure procedure = AddOp.$Pl;
            Object apply1 = lists.car.apply1(strings2);
            try {
                total = procedure.apply2(total, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                strings2 = strings3;
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, apply1);
            }
        }
        try {
            CharSequence ans = strings.makeString(((Number) total).intValue());
            Object obj = Lit0;
            while (lists.isPair(strings)) {
                Object s = lists.car.apply1(strings);
                try {
                    int slen = strings.stringLength((CharSequence) s);
                    try {
                        try {
                            $PcStringCopy$Ex(ans, ((Number) obj).intValue(), (CharSequence) s, 0, slen);
                            obj = AddOp.$Pl.apply2(obj, Integer.valueOf(slen));
                            strings = lists.cdr.apply1(strings);
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-copy!", 2, s);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "%string-copy!", 1, obj);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "string-length", 1, s);
                }
            }
            return ans;
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "make-string", 1, total);
        }
    }

    public static Object stringConcatenateReverse$V(Object string$Mnlist, Object[] argsArray) {
        LList maybe$Mnfinal$Plend = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            Procedure procedure2 = Scheme.applyToArgs;
            try {
                try {
                    Object apply3 = Scheme.applyToArgs.apply3(loc$final.get(), "", strings.isString(loc$final.get()) ? Boolean.TRUE : Boolean.FALSE);
                    Procedure procedure3 = Scheme.applyToArgs;
                    try {
                        Object obj2 = loc$end.get();
                        try {
                            Object apply32;
                            try {
                                Integer valueOf = Integer.valueOf(strings.stringLength((CharSequence) loc$final.get()));
                                try {
                                    boolean x = numbers.isInteger(loc$end.get());
                                    if (x) {
                                        try {
                                            x = numbers.isExact(loc$end.get());
                                            if (x) {
                                                try {
                                                    try {
                                                        try {
                                                            apply32 = Scheme.numLEq.apply3(Lit0, loc$end.get(), Integer.valueOf(strings.stringLength((CharSequence) loc$final.get())));
                                                        } catch (ClassCastException e) {
                                                            throw new WrongType(e, "string-length", 1, apply32);
                                                        }
                                                    } catch (UnboundLocationException e2) {
                                                        e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1621, 36);
                                                        throw e2;
                                                    }
                                                } catch (UnboundLocationException e22) {
                                                    e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1621, 17);
                                                    throw e22;
                                                }
                                            }
                                            apply32 = x ? Boolean.TRUE : Boolean.FALSE;
                                        } catch (UnboundLocationException e222) {
                                            e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1620, 19);
                                            throw e222;
                                        }
                                    }
                                    apply32 = x ? Boolean.TRUE : Boolean.FALSE;
                                    Object apply2 = procedure2.apply2(apply3, procedure3.apply3(obj2, valueOf, apply32));
                                    Object lis = string$Mnlist;
                                    Object len = Lit0;
                                    while (lists.isPair(lis)) {
                                        Procedure procedure4 = AddOp.$Pl;
                                        apply32 = lists.car.apply1(lis);
                                        try {
                                            Object sum = procedure4.apply2(len, Integer.valueOf(strings.stringLength((CharSequence) apply32)));
                                            lis = lists.cdr.apply1(lis);
                                            len = sum;
                                        } catch (ClassCastException e3) {
                                            throw new WrongType(e3, "string-length", 1, apply32);
                                        }
                                    }
                                    try {
                                        try {
                                            return procedure.apply4(obj, maybe$Mnfinal$Plend, apply2, $PcFinishStringConcatenateReverse(len, string$Mnlist, loc$final.get(), loc$end.get()));
                                        } catch (UnboundLocationException e2222) {
                                            e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1627, 65);
                                            throw e2222;
                                        }
                                    } catch (UnboundLocationException e22222) {
                                        e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1627, 59);
                                        throw e22222;
                                    }
                                } catch (UnboundLocationException e222222) {
                                    e222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1619, 21);
                                    throw e222222;
                                }
                            } catch (ClassCastException e32) {
                                throw new WrongType(e32, "string-length", 1, apply32);
                            }
                        } catch (UnboundLocationException e2222222) {
                            e2222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1618, 28);
                            throw e2222222;
                        }
                    } catch (UnboundLocationException e22222222) {
                        e22222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1618, 8);
                        throw e22222222;
                    }
                } catch (UnboundLocationException e222222222) {
                    e222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 55);
                    throw e222222222;
                }
            } catch (UnboundLocationException e2222222222) {
                e2222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 36);
                throw e2222222222;
            }
        } catch (UnboundLocationException e22222222222) {
            e22222222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 3);
            throw e22222222222;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object stringConcatenateReverse$SlShared$V(java.lang.Object r20, java.lang.Object[] r21) {
        /*
        r9 = 0;
        r0 = r21;
        r5 = gnu.lists.LList.makeList(r0, r9);
        r10 = kawa.standard.Scheme.applyToArgs;
        r9 = loc$let$Mnoptionals$St;
        r11 = r9.get();	 Catch:{ UnboundLocationException -> 0x0146 }
        r12 = kawa.standard.Scheme.applyToArgs;
        r13 = kawa.standard.Scheme.applyToArgs;
        r9 = loc$final;
        r14 = r9.get();	 Catch:{ UnboundLocationException -> 0x0150 }
        r15 = "";
        r9 = loc$final;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x015b }
        r9 = kawa.lib.strings.isString(r9);
        if (r9 == 0) goto L_0x00c6;
    L_0x0027:
        r9 = java.lang.Boolean.TRUE;
    L_0x0029:
        r13 = r13.apply3(r14, r15, r9);
        r14 = kawa.standard.Scheme.applyToArgs;
        r9 = loc$end;
        r15 = r9.get();	 Catch:{ UnboundLocationException -> 0x0166 }
        r9 = loc$final;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x0171 }
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x017c }
        r9 = kawa.lib.strings.stringLength(r9);
        r16 = java.lang.Integer.valueOf(r9);
        r9 = loc$end;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x0186 }
        r8 = kawa.lib.numbers.isInteger(r9);
        if (r8 == 0) goto L_0x00d2;
    L_0x0051:
        r9 = loc$end;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x0191 }
        r8 = kawa.lib.numbers.isExact(r9);
        if (r8 == 0) goto L_0x00ca;
    L_0x005d:
        r17 = kawa.standard.Scheme.numLEq;
        r18 = Lit0;
        r9 = loc$end;
        r19 = r9.get();	 Catch:{ UnboundLocationException -> 0x019c }
        r9 = loc$final;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x01a7 }
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x01b2 }
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r0 = r17;
        r1 = r18;
        r2 = r19;
        r9 = r0.apply3(r1, r2, r9);
    L_0x0081:
        r0 = r16;
        r9 = r14.apply3(r15, r0, r9);
        r12 = r12.apply2(r13, r9);
        r3 = Lit0;
        r6 = java.lang.Boolean.FALSE;
    L_0x008f:
        r9 = kawa.lib.lists.isPair(r20);
        if (r9 == 0) goto L_0x00dd;
    L_0x0095:
        r9 = kawa.lib.lists.car;
        r0 = r20;
        r9 = r9.apply1(r0);
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x01bc }
        r7 = kawa.lib.strings.stringLength(r9);
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r13 = java.lang.Integer.valueOf(r7);
        r3 = r9.apply2(r3, r13);
        r9 = java.lang.Boolean.FALSE;
        if (r6 != r9) goto L_0x00bb;
    L_0x00b1:
        r9 = java.lang.Integer.valueOf(r7);
        r9 = kawa.lib.numbers.isZero(r9);
        if (r9 == 0) goto L_0x00da;
    L_0x00bb:
        r9 = kawa.lib.lists.cdr;
        r0 = r20;
        r4 = r9.apply1(r0);
        r20 = r4;
        goto L_0x008f;
    L_0x00c6:
        r9 = java.lang.Boolean.FALSE;
        goto L_0x0029;
    L_0x00ca:
        if (r8 == 0) goto L_0x00cf;
    L_0x00cc:
        r9 = java.lang.Boolean.TRUE;
        goto L_0x0081;
    L_0x00cf:
        r9 = java.lang.Boolean.FALSE;
        goto L_0x0081;
    L_0x00d2:
        if (r8 == 0) goto L_0x00d7;
    L_0x00d4:
        r9 = java.lang.Boolean.TRUE;
        goto L_0x0081;
    L_0x00d7:
        r9 = java.lang.Boolean.FALSE;
        goto L_0x0081;
    L_0x00da:
        r6 = r20;
        goto L_0x00bb;
    L_0x00dd:
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01c6 }
        r9 = r0;
        r9 = kawa.lib.numbers.isZero(r9);
        if (r9 == 0) goto L_0x0104;
    L_0x00e7:
        r9 = loc$final;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x01d0 }
        r13 = Lit0;
        r14 = 1;
        r14 = new java.lang.Object[r14];
        r15 = 0;
        r16 = loc$end;
        r16 = r16.get();	 Catch:{ UnboundLocationException -> 0x01db }
        r14[r15] = r16;
        r9 = substring$SlShared$V(r9, r13, r14);
    L_0x00ff:
        r9 = r10.apply4(r11, r5, r12, r9);
        return r9;
    L_0x0104:
        r9 = loc$end;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x01e6 }
        r9 = (java.lang.Number) r9;	 Catch:{ ClassCastException -> 0x01f1 }
        r8 = kawa.lib.numbers.isZero(r9);
        if (r8 == 0) goto L_0x0133;
    L_0x0112:
        r13 = kawa.standard.Scheme.numEqu;
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r6);
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x01fb }
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r9 = r13.apply2(r3, r9);
        r13 = java.lang.Boolean.FALSE;
        if (r9 == r13) goto L_0x0135;
    L_0x012c:
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r6);
        goto L_0x00ff;
    L_0x0133:
        if (r8 != 0) goto L_0x012c;
    L_0x0135:
        r9 = loc$final;
        r9 = r9.get();	 Catch:{ UnboundLocationException -> 0x0205 }
        r13 = loc$end;
        r13 = r13.get();	 Catch:{ UnboundLocationException -> 0x0210 }
        r9 = $PcFinishStringConcatenateReverse(r3, r6, r9, r13);
        goto L_0x00ff;
    L_0x0146:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1630; // 0x65e float:2.284E-42 double:8.053E-321;
        r12 = 3;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x0150:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1630; // 0x65e float:2.284E-42 double:8.053E-321;
        r12 = 36;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x015b:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1630; // 0x65e float:2.284E-42 double:8.053E-321;
        r12 = 55;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x0166:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1631; // 0x65f float:2.286E-42 double:8.06E-321;
        r12 = 8;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x0171:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1631; // 0x65f float:2.286E-42 double:8.06E-321;
        r12 = 28;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x017c:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0186:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1632; // 0x660 float:2.287E-42 double:8.063E-321;
        r12 = 21;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x0191:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1633; // 0x661 float:2.288E-42 double:8.07E-321;
        r12 = 19;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x019c:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1634; // 0x662 float:2.29E-42 double:8.073E-321;
        r12 = 17;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x01a7:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1634; // 0x662 float:2.29E-42 double:8.073E-321;
        r12 = 36;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x01b2:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x01bc:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x01c6:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "zero?";
        r12 = 1;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
    L_0x01d0:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1645; // 0x66d float:2.305E-42 double:8.127E-321;
        r12 = 41;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x01db:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1645; // 0x66d float:2.305E-42 double:8.127E-321;
        r12 = 49;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x01e6:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1649; // 0x671 float:2.311E-42 double:8.147E-321;
        r12 = 16;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x01f1:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "zero?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x01fb:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0205:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1652; // 0x674 float:2.315E-42 double:8.16E-321;
        r12 = 56;
        r9.setLine(r10, r11, r12);
        throw r9;
    L_0x0210:
        r9 = move-exception;
        r10 = "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm";
        r11 = 1652; // 0x674 float:2.315E-42 double:8.16E-321;
        r12 = 62;
        r9.setLine(r10, r11, r12);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.stringConcatenateReverse$SlShared$V(java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public static Object $PcFinishStringConcatenateReverse(Object len, Object string$Mnlist, Object final_, Object end) {
        Object apply2 = AddOp.$Pl.apply2(end, len);
        try {
            CharSequence ans = strings.makeString(((Number) apply2).intValue());
            try {
                try {
                    try {
                        $PcStringCopy$Ex(ans, ((Number) len).intValue(), (CharSequence) final_, 0, ((Number) end).intValue());
                        Object i = len;
                        while (lists.isPair(string$Mnlist)) {
                            Object s = lists.car.apply1(string$Mnlist);
                            Object lis = lists.cdr.apply1(string$Mnlist);
                            try {
                                int slen = strings.stringLength((CharSequence) s);
                                i = AddOp.$Mn.apply2(i, Integer.valueOf(slen));
                                try {
                                    try {
                                        $PcStringCopy$Ex(ans, ((Number) i).intValue(), (CharSequence) s, 0, slen);
                                        string$Mnlist = lis;
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "%string-copy!", 2, s);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "%string-copy!", 1, i);
                                }
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "string-length", 1, s);
                            }
                        }
                        return ans;
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "%string-copy!", 4, end);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "%string-copy!", 2, final_);
                }
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "%string-copy!", 1, len);
            }
        } catch (ClassCastException e222222) {
            throw new WrongType(e222222, "make-string", 1, apply2);
        }
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN /*195*/:
                try {
                    try {
                        try {
                            return $PcCheckBounds(obj, (CharSequence) obj2, ((Number) obj3).intValue(), ((Number) obj4).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%check-bounds", 4, obj4);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%check-bounds", 3, obj3);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "%check-bounds", 2, obj2);
                }
            case 198:
                return checkSubstringSpec(obj, obj2, obj3, obj4);
            case 204:
                return $PcStringMap(obj, obj2, obj3, obj4);
            case 206:
                return $PcStringMap$Ex(obj, obj2, obj3, obj4);
            case 299:
                try {
                    try {
                        try {
                            return stringCopy$Ex(obj, ((Number) obj2).intValue(), (CharSequence) obj3, ((Number) obj4).intValue());
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "string-copy!", 4, obj4);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "string-copy!", 3, obj3);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "string-copy!", 2, obj2);
                }
            case 319:
                return $PcFinishStringConcatenateReverse(obj, obj2, obj3, obj4);
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static Object stringReplace$V(Object s1, Object s2, Object start1, Object end1, Object[] argsArray) {
        frame92 gnu_kawa_slib_srfi13_frame92 = new frame92();
        gnu_kawa_slib_srfi13_frame92.s1 = s1;
        gnu_kawa_slib_srfi13_frame92.s2 = s2;
        gnu_kawa_slib_srfi13_frame92.start1 = start1;
        gnu_kawa_slib_srfi13_frame92.end1 = end1;
        gnu_kawa_slib_srfi13_frame92.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        checkSubstringSpec(string$Mnreplace, gnu_kawa_slib_srfi13_frame92.s1, gnu_kawa_slib_srfi13_frame92.start1, gnu_kawa_slib_srfi13_frame92.end1);
        return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame92.lambda$Fn206, gnu_kawa_slib_srfi13_frame92.lambda$Fn207);
    }

    public static Object stringTokenize$V(Object s, Object[] argsArray) {
        frame93 gnu_kawa_slib_srfi13_frame93 = new frame93();
        gnu_kawa_slib_srfi13_frame93.f89s = s;
        LList token$Mnchars$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), token$Mnchars$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$token$Mnchars.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit14), Scheme.applyToArgs.apply2(loc$char$Mnset$Qu.get(), loc$token$Mnchars.get())), loc$rest.get()), call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame93.lambda$Fn208, gnu_kawa_slib_srfi13_frame93.lambda$Fn209));
                            } catch (UnboundLocationException e) {
                                e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 75);
                                throw e;
                            }
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 61);
                            throw e2;
                        }
                    } catch (UnboundLocationException e22) {
                        e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 50);
                        throw e22;
                    }
                } catch (UnboundLocationException e222) {
                    e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 33);
                    throw e222;
                }
            } catch (UnboundLocationException e2222) {
                e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 20);
                throw e2222;
            }
        } catch (UnboundLocationException e22222) {
            e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1694, 3);
            throw e22222;
        }
    }

    public static Object xsubstring$V(Object s, Object from, Object[] argsArray) {
        frame94 gnu_kawa_slib_srfi13_frame94 = new frame94();
        gnu_kawa_slib_srfi13_frame94.f90s = s;
        gnu_kawa_slib_srfi13_frame94.from = from;
        gnu_kawa_slib_srfi13_frame94.maybe$Mnto$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn210, gnu_kawa_slib_srfi13_frame94.from, xsubstring);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame94.lambda$Fn211, gnu_kawa_slib_srfi13_frame94.lambda$Fn215);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1738, 3);
            throw e;
        }
    }

    public static Object stringXcopy$Ex$V(Object target, Object tstart, Object s, Object sfrom, Object[] argsArray) {
        frame95 gnu_kawa_slib_srfi13_frame95 = new frame95();
        gnu_kawa_slib_srfi13_frame95.target = target;
        gnu_kawa_slib_srfi13_frame95.tstart = tstart;
        gnu_kawa_slib_srfi13_frame95.f91s = s;
        gnu_kawa_slib_srfi13_frame95.sfrom = sfrom;
        gnu_kawa_slib_srfi13_frame95.maybe$Mnsto$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn216, gnu_kawa_slib_srfi13_frame95.sfrom, string$Mnxcopy$Ex);
            return call_with_values.callWithValues(gnu_kawa_slib_srfi13_frame95.lambda$Fn217, gnu_kawa_slib_srfi13_frame95.lambda$Fn221);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1779, 3);
            throw e;
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 199:
                return frame1.lambda5(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 209:
                return lambda17(obj);
            case 211:
                return lambda18(obj);
            case 217:
                return lambda27(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 239:
                return frame32.lambda72(obj);
            case 240:
                return frame32.lambda73(obj);
            case LispEscapeFormat.ESCAPE_ALL /*242*/:
                return frame34.lambda78(obj);
            case 244:
                return frame36.lambda83(obj);
            case 245:
                return frame36.lambda84(obj);
            case 247:
                return frame38.lambda89(obj);
            case 248:
                return frame38.lambda90(obj);
            case 250:
                return frame40.lambda95(obj);
            case Telnet.WONT /*252*/:
                return frame42.lambda100(obj);
            case Telnet.DONT /*254*/:
                return frame44.lambda105(obj);
            case 255:
                return frame44.lambda106(obj);
            case InputDeviceCompat.SOURCE_KEYBOARD /*257*/:
                return frame46.lambda111(obj);
            case 259:
                return frame48.lambda116(obj);
            case 260:
                return frame48.lambda117(obj);
            case 262:
                return frame50.lambda122(obj);
            case 263:
                return frame50.lambda123(obj);
            case 265:
                return frame52.lambda128(obj);
            case 267:
                return frame54.lambda133(obj);
            case 271:
                return Integer.valueOf(frame57.lambda138(obj));
            case 287:
                return frame71.lambda163(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 289:
                return frame72.lambda166(obj) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /*309*/:
                return isStringNull(obj) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /*312*/:
                return reverseList$To$String(obj);
            case 315:
                return stringConcatenate$SlShared(obj);
            case 316:
                return stringConcatenate(obj);
            case 322:
                return frame94.lambda210(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 324:
                return frame95.lambda216(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 325:
                return frame95.lambda220(obj) ? Boolean.TRUE : Boolean.FALSE;
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object $PcMultispanRepcopy$Ex(Object target, Object tstart, Object s, Object sfrom, Object sto, Object start, Object end) {
        Object slen = AddOp.$Mn.apply2(end, start);
        Object i0 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(sfrom, slen));
        Object total$Mnchars = AddOp.$Mn.apply2(sto, sfrom);
        try {
            try {
                try {
                    try {
                        try {
                            $PcStringCopy$Ex((CharSequence) target, ((Number) tstart).intValue(), (CharSequence) s, ((Number) i0).intValue(), ((Number) end).intValue());
                            Object ncopied = AddOp.$Mn.apply2(end, i0);
                            Object nspans = DivideOp.quotient.apply2(AddOp.$Mn.apply2(total$Mnchars, ncopied), slen);
                            Object i = AddOp.$Pl.apply2(tstart, ncopied);
                            while (!numbers.isZero((Number) nspans)) {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        $PcStringCopy$Ex((CharSequence) target, ((Number) i).intValue(), (CharSequence) s, ((Number) start).intValue(), ((Number) end).intValue());
                                                        i = AddOp.$Pl.apply2(i, slen);
                                                        nspans = AddOp.$Mn.apply2(nspans, Lit1);
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "%string-copy!", 4, end);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "%string-copy!", 3, start);
                                                }
                                            } catch (ClassCastException e22) {
                                                throw new WrongType(e22, "%string-copy!", 2, s);
                                            }
                                        } catch (ClassCastException e222) {
                                            throw new WrongType(e222, "%string-copy!", 1, i);
                                        }
                                    } catch (ClassCastException e2222) {
                                        throw new WrongType(e2222, "%string-copy!", 0, target);
                                    }
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "zero?", 1, nspans);
                                }
                            }
                            try {
                                CharSequence charSequence = (CharSequence) target;
                                try {
                                    int intValue = ((Number) i).intValue();
                                    try {
                                        CharSequence charSequence2 = (CharSequence) s;
                                        try {
                                            int intValue2 = ((Number) start).intValue();
                                            Object apply2 = AddOp.$Pl.apply2(start, AddOp.$Mn.apply2(total$Mnchars, AddOp.$Mn.apply2(i, tstart)));
                                            try {
                                                return $PcStringCopy$Ex(charSequence, intValue, charSequence2, intValue2, ((Number) apply2).intValue());
                                            } catch (ClassCastException e222222) {
                                                throw new WrongType(e222222, "%string-copy!", 4, apply2);
                                            }
                                        } catch (ClassCastException e2222222) {
                                            throw new WrongType(e2222222, "%string-copy!", 3, start);
                                        }
                                    } catch (ClassCastException e22222222) {
                                        throw new WrongType(e22222222, "%string-copy!", 2, s);
                                    }
                                } catch (ClassCastException e222222222) {
                                    throw new WrongType(e222222222, "%string-copy!", 1, i);
                                }
                            } catch (ClassCastException e2222222222) {
                                throw new WrongType(e2222222222, "%string-copy!", 0, target);
                            }
                        } catch (ClassCastException e22222222222) {
                            throw new WrongType(e22222222222, "%string-copy!", 4, end);
                        }
                    } catch (ClassCastException e222222222222) {
                        throw new WrongType(e222222222222, "%string-copy!", 3, i0);
                    }
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "%string-copy!", 2, s);
                }
            } catch (ClassCastException e22222222222222) {
                throw new WrongType(e22222222222222, "%string-copy!", 1, tstart);
            }
        } catch (ClassCastException e222222222222222) {
            throw new WrongType(e222222222222222, "%string-copy!", 0, target);
        }
    }

    public static Object stringJoin$V(Object strings, Object[] argsArray) {
        LList delim$Plgrammar = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.applyToArgs;
        try {
            Object obj = loc$let$Mnoptionals$St.get();
            try {
                try {
                    try {
                        Object cons;
                        Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$delim.get(), " ", strings.isString(loc$delim.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply2(loc$grammar.get(), Lit15));
                        if (lists.isPair(strings)) {
                            try {
                                Object tmp = loc$grammar.get();
                                Boolean x = Scheme.isEqv.apply2(tmp, Lit15);
                                if (x == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, Lit16) != Boolean.FALSE : x != Boolean.FALSE) {
                                    cons = lists.cons(lists.car.apply1(strings), lambda222buildit(lists.cdr.apply1(strings), LList.Empty));
                                } else if (Scheme.isEqv.apply2(tmp, Lit17) != Boolean.FALSE) {
                                    cons = lambda222buildit(strings, LList.Empty);
                                } else if (Scheme.isEqv.apply2(tmp, Lit18) != Boolean.FALSE) {
                                    try {
                                        cons = lists.cons(lists.car.apply1(strings), lambda222buildit(lists.cdr.apply1(strings), LList.list1(loc$delim.get())));
                                    } catch (UnboundLocationException e) {
                                        e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1870, 53);
                                        throw e;
                                    }
                                } else {
                                    String str = "Illegal join grammar";
                                    Object[] objArr = new Object[2];
                                    try {
                                        objArr[0] = loc$grammar.get();
                                        objArr[1] = string$Mnjoin;
                                        cons = misc.error$V(str, objArr);
                                    } catch (UnboundLocationException e2) {
                                        e2.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1873, 9);
                                        throw e2;
                                    }
                                }
                                cons = stringConcatenate(cons);
                            } catch (UnboundLocationException e22) {
                                e22.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1862, 14);
                                throw e22;
                            }
                        } else if (lists.isNull(strings)) {
                            try {
                                if (loc$grammar.get() == Lit16) {
                                    cons = misc.error$V("Empty list cannot be joined with STRICT-INFIX grammar.", new Object[]{string$Mnjoin});
                                } else {
                                    cons = "";
                                }
                            } catch (UnboundLocationException e222) {
                                e222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1880, 13);
                                throw e222;
                            }
                        } else {
                            cons = misc.error$V("STRINGS parameter not list.", new Object[]{strings, string$Mnjoin});
                        }
                        return procedure.apply4(obj, delim$Plgrammar, apply2, cons);
                    } catch (UnboundLocationException e2222) {
                        e2222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1853, 6);
                        throw e2222;
                    }
                } catch (UnboundLocationException e22222) {
                    e22222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 54);
                    throw e22222;
                }
            } catch (UnboundLocationException e222222) {
                e222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 34);
                throw e222222;
            }
        } catch (UnboundLocationException e2222222) {
            e2222222.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 3);
            throw e2222222;
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        Object obj;
        Object obj2;
        Object obj3;
        int length;
        Object[] objArr2;
        Object obj4;
        Object obj5;
        int intValue;
        int intValue2;
        Object obj6;
        Object obj7;
        int length2;
        Object[] objArr3;
        CharSequence charSequence;
        int i;
        switch (moduleMethod.selector) {
            case HttpRequestContext.HTTP_OK /*200*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return substring$SlShared$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 202:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCopy$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 203:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringMap$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 205:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringMap$Ex$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 207:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                length = objArr.length - 3;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringFold$V(obj2, obj3, obj4, objArr2);
                    }
                    objArr2[length] = objArr[length + 3];
                }
            case 208:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                length = objArr.length - 3;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringFoldRight$V(obj2, obj3, obj4, objArr2);
                    }
                    objArr2[length] = objArr[length + 3];
                }
            case 210:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                obj = objArr[3];
                length = objArr.length - 4;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringUnfold$V(obj2, obj3, obj4, obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 4];
                }
            case 212:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                obj = objArr[3];
                length = objArr.length - 4;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringUnfoldRight$V(obj2, obj3, obj4, obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 4];
                }
            case 213:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringForEach$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 214:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringForEachIndex$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 215:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringEvery$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 216:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringAny$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 219:
                return $PcStringPrefixLength(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 220:
                return $PcStringSuffixLength(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 221:
                obj5 = objArr[0];
                obj2 = objArr[1];
                try {
                    length = ((Number) obj2).intValue();
                    obj3 = objArr[2];
                    try {
                        intValue = ((Number) obj3).intValue();
                        obj3 = objArr[3];
                        obj = objArr[4];
                        try {
                            intValue2 = ((Number) obj).intValue();
                            obj6 = objArr[5];
                            try {
                                return Integer.valueOf($PcStringPrefixLengthCi(obj5, length, intValue, obj3, intValue2, ((Number) obj6).intValue()));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%string-prefix-length-ci", 6, obj6);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-prefix-length-ci", 5, obj);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "%string-prefix-length-ci", 3, obj3);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "%string-prefix-length-ci", 2, obj2);
                }
            case 222:
                obj5 = objArr[0];
                obj2 = objArr[1];
                try {
                    length = ((Number) obj2).intValue();
                    obj3 = objArr[2];
                    try {
                        intValue = ((Number) obj3).intValue();
                        obj3 = objArr[3];
                        obj = objArr[4];
                        try {
                            intValue2 = ((Number) obj).intValue();
                            obj6 = objArr[5];
                            try {
                                return Integer.valueOf($PcStringSuffixLengthCi(obj5, length, intValue, obj3, intValue2, ((Number) obj6).intValue()));
                            } catch (ClassCastException e2222) {
                                throw new WrongType(e2222, "%string-suffix-length-ci", 6, obj6);
                            }
                        } catch (ClassCastException e22222) {
                            throw new WrongType(e22222, "%string-suffix-length-ci", 5, obj);
                        }
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "%string-suffix-length-ci", 3, obj3);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "%string-suffix-length-ci", 2, obj2);
                }
            case 223:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringPrefixLength$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 224:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringSuffixLength$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 225:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringPrefixLengthCi$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 226:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringSuffixLengthCi$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 227:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return isStringPrefix$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 228:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return isStringSuffix$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 229:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return isStringPrefixCi$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 230:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return isStringSuffixCi$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 231:
                return $PcStringPrefix$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 232:
                return $PcStringSuffix$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 233:
                return $PcStringPrefixCi$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 234:
                return $PcStringSuffixCi$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 235:
                return $PcStringCompare(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            case 236:
                return $PcStringCompareCi(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            case 237:
                obj5 = objArr[0];
                obj7 = objArr[1];
                obj2 = objArr[2];
                obj3 = objArr[3];
                obj4 = objArr[4];
                length2 = objArr.length - 5;
                objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return stringCompare$V(obj5, obj7, obj2, obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 5];
                }
            case 238:
                obj5 = objArr[0];
                obj7 = objArr[1];
                obj2 = objArr[2];
                obj3 = objArr[3];
                obj4 = objArr[4];
                length2 = objArr.length - 5;
                objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return stringCompareCi$V(obj5, obj7, obj2, obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 5];
                }
            case LispEscapeFormat.ESCAPE_NORMAL /*241*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 243:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Ls$Gr$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 246:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Ls$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 249:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Gr$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case Telnet.WILL /*251*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Ls$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case Telnet.DO /*253*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$Gr$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 256:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 258:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Ls$Gr$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 261:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Ls$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 264:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Gr$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 266:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Ls$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 268:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCi$Gr$Eq$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 269:
                return $PcStringHash(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            case 270:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringHash$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 272:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringHashCi$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 273:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringUpcase$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 274:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringUpcase$Ex$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 275:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringDowncase$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 276:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringDowncase$Ex$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 278:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTitlecase$Ex$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 279:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTitlecase$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 284:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTrim$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 285:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTrimRight$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 286:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTrimBoth$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 288:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringPadRight$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 290:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringPad$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 291:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringDelete$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 292:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringFilter$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 293:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringIndex$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 294:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringIndexRight$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 295:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringSkip$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 296:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringSkipRight$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 297:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringCount$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 298:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringFill$Ex$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 299:
                intValue = objArr.length - 3;
                obj3 = objArr[0];
                obj7 = objArr[1];
                try {
                    intValue2 = ((Number) obj7).intValue();
                    obj5 = objArr[2];
                    try {
                        charSequence = (CharSequence) obj5;
                        if (intValue <= 0) {
                            return stringCopy$Ex(obj3, intValue2, charSequence);
                        }
                        i = intValue - 1;
                        obj2 = objArr[3];
                        try {
                            length2 = ((Number) obj2).intValue();
                            if (i <= 0) {
                                return stringCopy$Ex(obj3, intValue2, charSequence, length2);
                            }
                            length = i - 1;
                            obj2 = objArr[4];
                            try {
                                return stringCopy$Ex(obj3, intValue2, charSequence, length2, ((Number) obj2).intValue());
                            } catch (ClassCastException e22222222) {
                                throw new WrongType(e22222222, "string-copy!", 5, obj2);
                            }
                        } catch (ClassCastException e222222222) {
                            throw new WrongType(e222222222, "string-copy!", 4, obj2);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-copy!", 3, obj5);
                    }
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "string-copy!", 2, obj7);
                }
            case ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET /*302*/:
                obj5 = objArr[0];
                try {
                    charSequence = (CharSequence) obj5;
                    obj2 = objArr[1];
                    try {
                        intValue2 = ((Number) obj2).intValue();
                        obj7 = objArr[2];
                        try {
                            CharSequence charSequence2 = (CharSequence) obj7;
                            obj3 = objArr[3];
                            try {
                                i = ((Number) obj3).intValue();
                                obj3 = objArr[4];
                                try {
                                    return $PcStringCopy$Ex(charSequence, intValue2, charSequence2, i, ((Number) obj3).intValue());
                                } catch (ClassCastException e22222222222) {
                                    throw new WrongType(e22222222222, "%string-copy!", 5, obj3);
                                }
                            } catch (ClassCastException e222222222222) {
                                throw new WrongType(e222222222222, "%string-copy!", 4, obj3);
                            }
                        } catch (ClassCastException e2222222222222) {
                            throw new WrongType(e2222222222222, "%string-copy!", 3, obj7);
                        }
                    } catch (ClassCastException e22222222222222) {
                        throw new WrongType(e22222222222222, "%string-copy!", 2, obj2);
                    }
                } catch (ClassCastException e32) {
                    throw new WrongType(e32, "%string-copy!", 1, obj5);
                }
            case ErrorMessages.ERROR_TWITTER_EXCEPTION /*303*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringContains$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN /*304*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringContainsCi$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED /*305*/:
                return $PcKmpSearch(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            case ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED /*306*/:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return makeKmpRestartVector$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED /*307*/:
                return kmpStep(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED /*308*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                obj = objArr[3];
                length = objArr.length - 4;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringKmpPartialSearch$V(obj2, obj3, obj4, obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 4];
                }
            case ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED /*310*/:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringReverse$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED /*311*/:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringReverse$Ex$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED /*313*/:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return string$To$List$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case ErrorMessages.ERROR_TWITTER_SEARCH_FAILED /*314*/:
                return stringAppend$SlShared$V(objArr);
            case 317:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringConcatenateReverse$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 318:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringConcatenateReverse$SlShared$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION /*320*/:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                obj = objArr[3];
                length = objArr.length - 4;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringReplace$V(obj2, obj3, obj4, obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 4];
                }
            case 321:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringTokenize$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 323:
                obj2 = objArr[0];
                obj3 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return xsubstring$V(obj2, obj3, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 326:
                obj2 = objArr[0];
                obj3 = objArr[1];
                obj4 = objArr[2];
                obj = objArr[3];
                length = objArr.length - 4;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringXcopy$Ex$V(obj2, obj3, obj4, obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 4];
                }
            case 327:
                return $PcMultispanRepcopy$Ex(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            case 328:
                obj2 = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stringJoin$V(obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object lambda222buildit(Object lis, Object final_) {
        frame96 gnu_kawa_slib_srfi13_frame96 = new frame96();
        gnu_kawa_slib_srfi13_frame96.f92final = final_;
        return gnu_kawa_slib_srfi13_frame96.lambda223recur(lis);
    }
}
