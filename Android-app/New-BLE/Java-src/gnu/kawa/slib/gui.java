package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.models.Box;
import gnu.kawa.models.Button;
import gnu.kawa.models.Display;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.Text;
import gnu.kawa.models.Window;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.xml.KAttr;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.misc;
import kawa.standard.Scheme;

/* compiled from: gui.scm */
public class gui extends ModuleBody {
    public static final gui $instance = new gui();
    public static final ModuleMethod Button;
    public static final ModuleMethod Column;
    public static final Macro Image = Macro.make(Lit12, Lit13, $instance);
    public static final ModuleMethod Label;
    static final Class Lit0 = Color.class;
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("button").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("Button").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final SyntaxRules Lit13;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("image-read").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("image-width").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("image-height").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("Label").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("Row").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("cell-spacing").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Column").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("set-content").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("Window").readResolve());
    static final SimpleSymbol Lit23;
    static final SyntaxRules Lit24;
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("::").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("<int>").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("i").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("num-args").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("arg").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("*.getWidth").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("primitive-array-get").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("<object>").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("loop").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("+").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.xml.KAttr>").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("attr").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("*.getHeight").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("getName").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("invoke").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("name").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("value").readResolve());
    static final IntNum Lit44 = IntNum.make(1);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.Row>").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.Column>").readResolve());
    static final SimpleSymbol Lit7;
    static final SyntaxRules Lit8;
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("as-color").readResolve());
    public static final ModuleMethod Row;
    public static final ModuleMethod Text;
    public static final ModuleMethod Window;
    public static final ModuleMethod as$Mncolor;
    public static final ModuleMethod button;
    public static final ModuleMethod image$Mnheight;
    public static final ModuleMethod image$Mnread;
    public static final ModuleMethod image$Mnwidth;
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtColumn$Gr = ThreadLocation.getInstance(Lit6, null);
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtRow$Gr = ThreadLocation.getInstance(Lit5, null);
    static final Location loc$$St$DtgetHeight = ThreadLocation.getInstance(Lit4, null);
    static final Location loc$$St$DtgetWidth = ThreadLocation.getInstance(Lit3, null);
    public static final Macro process$Mnkeywords = Macro.make(Lit7, Lit8, $instance);
    public static final Macro run$Mnapplication = Macro.make(Lit23, Lit24, $instance);
    public static final ModuleMethod set$Mncontent;

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("run-application").readResolve();
        Lit23 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        Object[] objArr2 = new Object[1];
        objArr2[0] = PairWithPosition.make(Lit30, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.kawa.models.Window").readResolve(), Pair.make(Pair.make(Lit31, Pair.make((SimpleSymbol) new SimpleSymbol("open").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 749575);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0003", objArr2, 0);
        Lit24 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        Object[] objArr3 = new Object[]{(SimpleSymbol) new SimpleSymbol("text-field").readResolve()};
        SyntaxRule[] syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0002", new Object[]{(SimpleSymbol) new SimpleSymbol("make").readResolve(), (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.DrawImage>").readResolve()}, 0);
        Lit13 = new SyntaxRules(objArr3, syntaxRuleArr2, 1);
        objArr3 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("process-keywords").readResolve();
        Lit7 = simpleSymbol;
        objArr3[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[1];
        objArr2 = new Object[27];
        objArr2[0] = (SimpleSymbol) new SimpleSymbol("let").readResolve();
        objArr2[1] = Lit28;
        objArr2[2] = Lit25;
        objArr2[3] = Lit26;
        objArr2[4] = (SimpleSymbol) new SimpleSymbol("field").readResolve();
        objArr2[5] = PairWithPosition.make(PairWithPosition.make(Lit39, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("length").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 16426), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 16426), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 16425);
        objArr2[6] = Lit34;
        objArr2[7] = PairWithPosition.make(PairWithPosition.make(Lit27, PairWithPosition.make(Lit25, PairWithPosition.make(Lit26, PairWithPosition.make(IntNum.make(0), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 20509), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 20503), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 20500), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 20497), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 20496);
        objArr2[8] = (SimpleSymbol) new SimpleSymbol("if").readResolve();
        objArr2[9] = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<").readResolve(), PairWithPosition.make(Lit27, PairWithPosition.make(Lit28, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 24593), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 24591), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 24588);
        objArr2[10] = Lit29;
        objArr2[11] = PairWithPosition.make(Lit32, PairWithPosition.make(Lit33, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 28710), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 28689);
        objArr2[12] = PairWithPosition.make(Lit27, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 28725);
        objArr2[13] = (SimpleSymbol) new SimpleSymbol("cond").readResolve();
        objArr2[14] = PairWithPosition.make(Lit36, PairWithPosition.make(Lit29, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<gnu.expr.Keyword>").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 32797), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 32793), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 32782);
        objArr2[15] = PairWithPosition.make(PairWithPosition.make(Lit30, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.expr.Keyword").readResolve(), Pair.make(Pair.make(Lit31, Pair.make(Lit40, LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 40970), PairWithPosition.make(Lit29, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 40995), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 40969);
        objArr2[16] = PairWithPosition.make(Lit32, PairWithPosition.make(Lit33, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45087), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45066);
        objArr2[17] = PairWithPosition.make(PairWithPosition.make(Lit35, PairWithPosition.make(Lit27, PairWithPosition.make(Lit44, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45107), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45105), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45102), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 45102);
        objArr2[18] = PairWithPosition.make(PairWithPosition.make(Lit34, PairWithPosition.make(PairWithPosition.make(Lit35, PairWithPosition.make(Lit27, PairWithPosition.make(IntNum.make(2), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49170), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49168), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49165), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49165), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49159), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 49159);
        objArr2[19] = PairWithPosition.make(Lit36, PairWithPosition.make(Lit29, PairWithPosition.make(Lit37, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 53270), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 53266), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 53255);
        objArr2[20] = (SimpleSymbol) new SimpleSymbol("let*").readResolve();
        objArr2[21] = PairWithPosition.make(PairWithPosition.make(Lit38, PairWithPosition.make(Lit25, PairWithPosition.make(Lit37, PairWithPosition.make(Lit29, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 57388), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 57367), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 57364), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 57358), PairWithPosition.make(PairWithPosition.make(Lit42, PairWithPosition.make(Lit25, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<java.lang.String>").readResolve(), PairWithPosition.make(PairWithPosition.make(Lit41, PairWithPosition.make(Lit38, PairWithPosition.make(PairWithPosition.make(Lit39, PairWithPosition.make(Lit40, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61489), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61489), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61488), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61483), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61475), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61475), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61456), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61453), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61447), PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit41, PairWithPosition.make(Lit38, PairWithPosition.make(PairWithPosition.make(Lit39, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("getObjectValue").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65564), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65564), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65563), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65558), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65550), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65550), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65543), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 65543), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 61447), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 57357);
        objArr2[22] = PairWithPosition.make(Lit42, PairWithPosition.make(Lit43, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 69666), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 69661);
        objArr2[23] = PairWithPosition.make(PairWithPosition.make(Lit34, PairWithPosition.make(PairWithPosition.make(Lit35, PairWithPosition.make(Lit27, PairWithPosition.make(Lit44, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73746), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73744), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73741), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73741), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73735), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 73735);
        objArr2[24] = (SimpleSymbol) new SimpleSymbol("else").readResolve();
        objArr2[25] = PairWithPosition.make(Lit29, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 81951);
        objArr2[26] = PairWithPosition.make(PairWithPosition.make(Lit34, PairWithPosition.make(PairWithPosition.make(Lit35, PairWithPosition.make(Lit27, PairWithPosition.make(Lit44, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86034), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86032), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86029), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86029), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86023), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 86023);
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\t\u000b\u0018,\b\u0011\u0018\u0004\u0011\u00184\u0011\u0018<\b\u0011\u0018D\u0011\u0018L\b\u0011\u0018\u0004a\b\u0011\u0018T\b\u0011\u0018\\\t\u000b\u0018d\b\u0011\u0018l©\u0011\u0018ty\t\u0013\t\u0003\u0011\u0018|\b\u0011\u0018\t\u000b\u0018\u0018\u0011\u0018i\u0011\u0018¤\u0011\u0018¬\b\t\u0013\t\u0003\u0018´\u0018¼\b\u0011\u0018Ä1\t\u001b\t\u0003\u0018Ì\u0018Ô", objArr2, 0);
        Lit8 = new SyntaxRules(objArr3, syntaxRuleArr2, 4);
        ModuleBody moduleBody = $instance;
        as$Mncolor = new ModuleMethod(moduleBody, 1, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        button = new ModuleMethod(moduleBody, 2, Lit10, -4096);
        Button = new ModuleMethod(moduleBody, 3, Lit11, -4096);
        image$Mnread = new ModuleMethod(moduleBody, 4, Lit14, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        image$Mnwidth = new ModuleMethod(moduleBody, 5, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        image$Mnheight = new ModuleMethod(moduleBody, 6, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Label = new ModuleMethod(moduleBody, 7, Lit17, -4096);
        Text = new ModuleMethod(moduleBody, 8, Lit18, -4096);
        Row = new ModuleMethod(moduleBody, 9, Lit19, -4096);
        Column = new ModuleMethod(moduleBody, 10, Lit20, -4096);
        set$Mncontent = new ModuleMethod(moduleBody, 11, Lit21, 8194);
        Window = new ModuleMethod(moduleBody, 12, Lit22, -4096);
        $instance.run();
    }

    public gui() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Color asColor(Object value) {
        if (value instanceof Color) {
            return (Color) value;
        }
        if (value instanceof Integer) {
            try {
                return new Color(((Integer) value).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Integer.intValue()", 1, value);
            }
        } else if (value instanceof IntNum) {
            return new Color(IntNum.intValue(value));
        } else {
            return (Color) SlotGet.staticField.apply2(Lit0, value.toString());
        }
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                if (!(obj instanceof BufferedImage)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                if (!(obj instanceof BufferedImage)) {
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

    static Object buttonKeyword(Button button, String name, Object value) {
        boolean z = true;
        if (name == "foreground") {
            button.setForeground(asColor(value));
            return Values.empty;
        } else if (name == "background") {
            button.setBackground(asColor(value));
            return Values.empty;
        } else if (name == "action") {
            button.setAction(value);
            return Values.empty;
        } else if (name == PropertyTypeConstants.PROPERTY_TYPE_TEXT) {
            button.setText(value == null ? null : value.toString());
            return Values.empty;
        } else if (name == "disabled") {
            try {
                if (value == Boolean.FALSE) {
                    z = false;
                }
                button.setDisabled(z);
                return Values.empty;
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.kawa.models.Button.setDisabled(boolean)", 2, value);
            }
        } else {
            return misc.error$V(Format.formatToString(0, "unknown button attribute ~s", name), new Object[0]);
        }
    }

    static Boolean buttonNonKeyword(Button button, Object arg) {
        return Boolean.TRUE;
    }

    public static Button button(Object... args) {
        Button button = new Button();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                try {
                    buttonKeyword(button, ((Keyword) arg).getName(), args[i + 1]);
                    i += 2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                }
            } else if (arg instanceof KAttr) {
                try {
                    KAttr attr = (KAttr) arg;
                    buttonKeyword(button, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, arg);
                }
            } else {
                buttonNonKeyword(button, arg);
                i++;
            }
        }
        return button;
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 3:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 7:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 8:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 9:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 10:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 12:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Button Button(Object... args) {
        Button button = new Button();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                try {
                    buttonKeyword(button, ((Keyword) arg).getName(), args[i + 1]);
                    i += 2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                }
            } else if (arg instanceof KAttr) {
                try {
                    KAttr attr = (KAttr) arg;
                    buttonKeyword(button, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, arg);
                }
            } else {
                buttonNonKeyword(button, arg);
                i++;
            }
        }
        return button;
    }

    public static BufferedImage imageRead(Path uri) {
        return ImageIO.read(uri.openInputStream());
    }

    public static int imageWidth(BufferedImage image) {
        try {
            return ((Number) Scheme.applyToArgs.apply2(loc$$St$DtgetWidth.get(), image)).intValue();
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 74, 3);
            throw e;
        }
    }

    public static int imageHeight(BufferedImage image) {
        try {
            return ((Number) Scheme.applyToArgs.apply2(loc$$St$DtgetHeight.get(), image)).intValue();
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 77, 3);
            throw e;
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return asColor(obj);
            case 4:
                try {
                    return imageRead(Path.valueOf(obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "image-read", 1, obj);
                }
            case 5:
                try {
                    return Integer.valueOf(imageWidth((BufferedImage) obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "image-width", 1, obj);
                }
            case 6:
                try {
                    return Integer.valueOf(imageHeight((BufferedImage) obj));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "image-height", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Object labelKeyword(Label instance, String name, Object value) {
        if (name == Lit1) {
            String str;
            if (value == null) {
                str = null;
            } else {
                str = value.toString();
            }
            instance.setText(str);
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown label attribute ~s", name), new Object[0]);
    }

    static void labelNonKeyword(Label instance, Object arg) {
        instance.setText(arg == null ? null : arg.toString());
    }

    public static Label Label(Object... args) {
        Label instance = new Label();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                try {
                    labelKeyword(instance, ((Keyword) arg).getName(), args[i + 1]);
                    i += 2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                }
            } else if (arg instanceof KAttr) {
                try {
                    KAttr attr = (KAttr) arg;
                    labelKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, arg);
                }
            } else {
                labelNonKeyword(instance, arg);
                i++;
            }
        }
        return instance;
    }

    static Object textKeyword(Text instance, String name, Object value) {
        if (name == Lit1) {
            String str;
            if (value == null) {
                str = null;
            } else {
                str = value.toString();
            }
            instance.setText(str);
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown text attribute ~s", name), new Object[0]);
    }

    static void textNonKeyword(Text instance, Object arg) {
        instance.setText(arg == null ? null : arg.toString());
    }

    public static Text Text(Object... args) {
        Text instance = new Text();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                try {
                    textKeyword(instance, ((Keyword) arg).getName(), args[i + 1]);
                    i += 2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                }
            } else if (arg instanceof KAttr) {
                try {
                    KAttr attr = (KAttr) arg;
                    textKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, arg);
                }
            } else {
                textNonKeyword(instance, arg);
                i++;
            }
        }
        return instance;
    }

    static Object boxKeyword(Box instance, String name, Object value) {
        if (name == Lit2) {
            instance.setCellSpacing(value);
            return Values.empty;
        }
        return misc.error$V(Format.formatToString(0, "unknown box attribute ~s", name), new Object[0]);
    }

    static Model asModel(Object arg) {
        return Display.getInstance().coerceToModel(arg);
    }

    static void boxNonKeyword(Box box, Object arg) {
        box.add(asModel(arg));
    }

    public static Object Row(Object... args) {
        try {
            Object instance = Invoke.make.apply1(loc$$Lsgnu$Dtkawa$Dtmodels$DtRow$Gr.get());
            int num$Mnargs = args.length;
            int i = 0;
            while (i < num$Mnargs) {
                Object arg = args[i];
                if (arg instanceof Keyword) {
                    try {
                        try {
                            boxKeyword((Box) instance, ((Keyword) arg).getName(), args[i + 1]);
                            i += 2;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "box-keyword", 0, instance);
                    }
                } else if (arg instanceof KAttr) {
                    try {
                        KAttr attr = (KAttr) arg;
                        try {
                            boxKeyword((Box) instance, attr.getName(), attr.getObjectValue());
                            i++;
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "box-keyword", 0, instance);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "attr", -2, arg);
                    }
                } else {
                    try {
                        boxNonKeyword((Box) instance, arg);
                        i++;
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "box-non-keyword", 0, instance);
                    }
                }
            }
            return instance;
        } catch (UnboundLocationException e3) {
            e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 146, 25);
            throw e3;
        }
    }

    public static Object Column(Object... args) {
        try {
            Object instance = Invoke.make.apply1(loc$$Lsgnu$Dtkawa$Dtmodels$DtColumn$Gr.get());
            int num$Mnargs = args.length;
            int i = 0;
            while (i < num$Mnargs) {
                Object arg = args[i];
                if (arg instanceof Keyword) {
                    try {
                        try {
                            boxKeyword((Box) instance, ((Keyword) arg).getName(), args[i + 1]);
                            i += 2;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "box-keyword", 0, instance);
                    }
                } else if (arg instanceof KAttr) {
                    try {
                        KAttr attr = (KAttr) arg;
                        try {
                            boxKeyword((Box) instance, attr.getName(), attr.getObjectValue());
                            i++;
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "box-keyword", 0, instance);
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "attr", -2, arg);
                    }
                } else {
                    try {
                        boxNonKeyword((Box) instance, arg);
                        i++;
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "box-non-keyword", 0, instance);
                    }
                }
            }
            return instance;
        } catch (UnboundLocationException e3) {
            e3.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/gui.scm", 151, 25);
            throw e3;
        }
    }

    public static void setContent(Window window, Object pane) {
        window.setContent(pane);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        if (moduleMethod.selector != 11) {
            return super.apply2(moduleMethod, obj, obj2);
        }
        try {
            setContent((Window) obj, obj2);
            return Values.empty;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-content", 1, obj);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 11) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        if (!(obj instanceof Window)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    static Object windowKeyword(Window instance, String name, Object value) {
        if (name == "title") {
            instance.setTitle(value == null ? null : value.toString());
            return Values.empty;
        } else if (name == "content") {
            instance.setContent(value);
            return Values.empty;
        } else if (name == "menubar") {
            instance.setMenuBar(value);
            return Values.empty;
        } else {
            return misc.error$V(Format.formatToString(0, "unknown window attribute ~s", name), new Object[0]);
        }
    }

    static void windowNonKeyword(Window instance, Object arg) {
        instance.setContent(arg);
    }

    public static Window Window(Object... args) {
        Window instance = Display.getInstance().makeWindow();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                try {
                    windowKeyword(instance, ((Keyword) arg).getName(), args[i + 1]);
                    i += 2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.expr.Keyword.getName()", 1, arg);
                }
            } else if (arg instanceof KAttr) {
                try {
                    KAttr attr = (KAttr) arg;
                    windowKeyword(instance, attr.getName(), attr.getObjectValue());
                    i++;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attr", -2, arg);
                }
            } else {
                windowNonKeyword(instance, arg);
                i++;
            }
        }
        return instance;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 2:
                return button(objArr);
            case 3:
                return Button(objArr);
            case 7:
                return Label(objArr);
            case 8:
                return Text(objArr);
            case 9:
                return Row(objArr);
            case 10:
                return Column(objArr);
            case 12:
                return Window(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
