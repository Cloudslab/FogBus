package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.models.Paintable;
import gnu.kawa.models.WithTransform;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.swingviews.SwingDisplay;
import gnu.kawa.swingviews.SwingPaintable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import kawa.lib.numbers;
import kawa.standard.Scheme;

/* compiled from: swing.scm */
public class swing extends ModuleBody {
    public static final swing $instance = new swing();
    public static final Location Button = StaticFieldLocation.make("gnu.kawa.slib.gui", "Button");
    public static final Location Column = StaticFieldLocation.make("gnu.kawa.slib.gui", "Column");
    public static final Location Image = StaticFieldLocation.make("gnu.kawa.slib.gui", "Image");
    public static final Location Label = StaticFieldLocation.make("gnu.kawa.slib.gui", "Label");
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("make").readResolve());
    static final Keyword Lit1 = Keyword.make("label");
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.DrawShape>").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.WithPaint>").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("gnu.kawa.models.WithComposite").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("make-action-listener").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("fill").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("draw").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("with-paint").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("with-composite").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("composite-src-over").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("composite-src").readResolve());
    static final Keyword Lit2 = Keyword.make("image");
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("rotation").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("with-transform").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("menubar").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("menu").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("menuitem").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("polygon").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("scroll").readResolve());
    static final Keyword Lit3 = Keyword.make("default");
    static final Keyword Lit4 = Keyword.make("oncommand");
    static final Keyword Lit5 = Keyword.make("disabled");
    static final Keyword Lit6 = Keyword.make("accesskey");
    static final Keyword Lit7 = Keyword.make("w");
    static final Keyword Lit8 = Keyword.make("h");
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.FillShape>").readResolve());
    public static final Location Row = StaticFieldLocation.make("gnu.kawa.slib.gui", "Row");
    public static final Location Text = StaticFieldLocation.make("gnu.kawa.slib.gui", "Text");
    public static final Location Window = StaticFieldLocation.make("gnu.kawa.slib.gui", "Window");
    public static final Location button = StaticFieldLocation.make("gnu.kawa.slib.gui", "button");
    public static final Color color$Mnred = null;
    public static final ModuleMethod composite$Mnsrc;
    public static final ModuleMethod composite$Mnsrc$Mnover;
    public static final ModuleMethod draw;
    public static final ModuleMethod fill;
    public static final Location image$Mnheight = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnheight");
    public static final Location image$Mnread = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnread");
    public static final Location image$Mnwidth = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnwidth");
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtDrawShape$Gr = ThreadLocation.getInstance(Lit10, null);
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtFillShape$Gr = ThreadLocation.getInstance(Lit9, null);
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtWithPaint$Gr = ThreadLocation.getInstance(Lit11, null);
    static final Location loc$gnu$Dtkawa$Dtmodels$DtWithComposite = ThreadLocation.getInstance(Lit12, null);
    public static final ModuleMethod make$Mnaction$Mnlistener;
    public static final ModuleMethod menu;
    public static final ModuleMethod menubar;
    public static final ModuleMethod menuitem;
    public static final ModuleMethod polygon;
    public static final ModuleMethod rotation;
    public static final Location run$Mnapplication = StaticFieldLocation.make("gnu.kawa.slib.gui", "run$Mnapplication");
    public static final ModuleMethod scroll;
    public static final Location set$Mncontent = StaticFieldLocation.make("gnu.kawa.slib.gui", "set$Mncontent");
    public static final ModuleMethod with$Mncomposite;
    public static final ModuleMethod with$Mnpaint;
    public static final ModuleMethod with$Mntransform;

    public swing() {
        ModuleInfo.register(this);
    }

    public static Composite compositeSrc() {
        return compositeSrc(1.0f);
    }

    public static Composite compositeSrcOver() {
        return compositeSrcOver(1.0f);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        color$Mnred = Color.red;
    }

    static {
        ModuleBody moduleBody = $instance;
        make$Mnaction$Mnlistener = new ModuleMethod(moduleBody, 1, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fill = new ModuleMethod(moduleBody, 2, Lit14, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        draw = new ModuleMethod(moduleBody, 3, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        with$Mnpaint = new ModuleMethod(moduleBody, 4, Lit16, 8194);
        with$Mncomposite = new ModuleMethod(moduleBody, 5, Lit17, -4096);
        composite$Mnsrc$Mnover = new ModuleMethod(moduleBody, 6, Lit18, 4096);
        composite$Mnsrc = new ModuleMethod(moduleBody, 8, Lit19, 4096);
        rotation = new ModuleMethod(moduleBody, 10, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        with$Mntransform = new ModuleMethod(moduleBody, 11, Lit21, 8194);
        menubar = new ModuleMethod(moduleBody, 12, Lit22, -4096);
        menu = new ModuleMethod(moduleBody, 13, Lit23, -4096);
        menuitem = new ModuleMethod(moduleBody, 14, Lit24, -4096);
        polygon = new ModuleMethod(moduleBody, 15, Lit25, -4095);
        scroll = new ModuleMethod(moduleBody, 16, Lit26, -4095);
        $instance.run();
    }

    public static ActionListener makeActionListener(Object proc) {
        return SwingDisplay.makeActionListener(proc);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                if (!(obj instanceof Shape)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                if (!(obj instanceof Shape)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Paintable fill(Shape shape) {
        try {
            return (Paintable) Invoke.make.apply2(loc$$Lsgnu$Dtkawa$Dtmodels$DtFillShape$Gr.get(), shape);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/swing.scm", 19, 9);
            throw e;
        }
    }

    public static Paintable draw(Shape shape) {
        try {
            return (Paintable) Invoke.make.apply2(loc$$Lsgnu$Dtkawa$Dtmodels$DtDrawShape$Gr.get(), shape);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/swing.scm", 22, 9);
            throw e;
        }
    }

    public static Object withPaint(Color paint, Paintable pic) {
        try {
            return Invoke.make.apply3(loc$$Lsgnu$Dtkawa$Dtmodels$DtWithPaint$Gr.get(), pic, paint);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/swing.scm", 26, 10);
            throw e;
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                if (!(obj instanceof Color)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Paintable)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 11:
                if (!(obj instanceof AffineTransform)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Paintable)) {
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

    public static Object withComposite(Object... arguments) {
        try {
            return Scheme.applyToArgs.apply2(GetNamedPart.getNamedPart.apply2(loc$gnu$Dtkawa$Dtmodels$DtWithComposite.get(), Lit0), arguments);
        } catch (UnboundLocationException e) {
            e.setLine("/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/swing.scm", 29, 4);
            throw e;
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 5:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 12:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 13:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 14:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 15:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 16:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Composite compositeSrcOver(float alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 8:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static Composite compositeSrc(float alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC, alpha);
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 6:
                return compositeSrcOver();
            case 8:
                return compositeSrc();
            default:
                return super.apply0(moduleMethod);
        }
    }

    public static AffineTransform rotation(double theta) {
        return AffineTransform.getRotateInstance(theta);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return makeActionListener(obj);
            case 2:
                try {
                    return fill((Shape) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "fill", 1, obj);
                }
            case 3:
                try {
                    return draw((Shape) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "draw", 1, obj);
                }
            case 6:
                try {
                    return compositeSrcOver(((Number) obj).floatValue());
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "composite-src-over", 1, obj);
                }
            case 8:
                try {
                    return compositeSrc(((Number) obj).floatValue());
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "composite-src", 1, obj);
                }
            case 10:
                try {
                    return rotation(((Number) obj).doubleValue());
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "rotation", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static WithTransform withTransform(AffineTransform transform, Paintable pic) {
        return new WithTransform(pic, transform);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 4:
                try {
                    try {
                        return withPaint((Color) obj, (Paintable) obj2);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "with-paint", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "with-paint", 1, obj);
                }
            case 11:
                try {
                    try {
                        return withTransform((AffineTransform) obj, (Paintable) obj2);
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "with-transform", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "with-transform", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static JMenuBar menubar(Object... args) {
        JMenuBar menubar = new JMenuBar();
        for (Object arg : args) {
            menubar.add((JMenu) arg);
        }
        return menubar;
    }

    public static JMenu menu(Object... args) {
        JMenu menu = new JMenu();
        int num$Mnargs = args.length;
        int i = 0;
        while (i < num$Mnargs) {
            Keyword arg = args[i];
            boolean x = arg == Lit1;
            if (x ? i + 1 < num$Mnargs : x) {
                Object obj = args[i + 1];
                menu.setText(obj == null ? null : obj.toString());
                i += 2;
            } else {
                menu.add((JMenuItem) arg);
                i++;
            }
        }
        return menu;
    }

    public static JMenuItem menuitem$V(Object[] argsArray) {
        Object searchForKeyword = Keyword.searchForKeyword(argsArray, 0, Lit1, null);
        String label = searchForKeyword == null ? null : searchForKeyword.toString();
        Object image = Keyword.searchForKeyword(argsArray, 0, Lit2, null);
        Object defaultR = Keyword.searchForKeyword(argsArray, 0, Lit3, null);
        Object oncommand = Keyword.searchForKeyword(argsArray, 0, Lit4, null);
        Boolean disabled = Keyword.searchForKeyword(argsArray, 0, Lit5, Boolean.FALSE);
        Object accesskey = Keyword.searchForKeyword(argsArray, 0, Lit6, null);
        JMenuItem menuitem = new JMenuItem();
        if (disabled != Boolean.FALSE) {
            menuitem.setEnabled(false);
        }
        if (label != null) {
            menuitem.setText(label);
        }
        if (oncommand != null) {
            menuitem.addActionListener(makeActionListener(oncommand));
        }
        return menuitem;
    }

    public static Object polygon(Complex initial, Object... more$Mnpoints) {
        GeneralPath path = new GeneralPath();
        int n$Mnpoints = more$Mnpoints.length;
        path.moveTo(numbers.realPart(initial).doubleValue(), numbers.imagPart(initial).doubleValue());
        int i = 0;
        while (i < n$Mnpoints) {
            Object obj = more$Mnpoints[i];
            try {
                Complex pt = (Complex) obj;
                path.lineTo(numbers.realPart(pt).doubleValue(), numbers.imagPart(pt).doubleValue());
                i++;
            } catch (ClassCastException e) {
                throw new WrongType(e, "pt", -2, obj);
            }
        }
        path.closePath();
        return path;
    }

    public static JScrollPane scroll$V(Object contents, Object[] argsArray) {
        Object w = Keyword.searchForKeyword(argsArray, 0, Lit7, Boolean.FALSE);
        Object h = Keyword.searchForKeyword(argsArray, 0, Lit8, Boolean.FALSE);
        if (contents instanceof Paintable) {
            try {
                contents = new SwingPaintable((Paintable) contents);
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.kawa.swingviews.SwingPaintable.<init>(gnu.kawa.models.Paintable)", 1, contents);
            }
        }
        try {
            JScrollPane scr = new JScrollPane((Component) contents);
            try {
                try {
                    scr.setPreferredSize(new Dimension(((Number) w).intValue(), ((Number) h).intValue()));
                    return scr;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "java.awt.Dimension.<init>(int,int)", 2, h);
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "java.awt.Dimension.<init>(int,int)", 1, w);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "javax.swing.JScrollPane.<init>(java.awt.Component)", 1, contents);
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 5:
                return withComposite(objArr);
            case 12:
                return menubar(objArr);
            case 13:
                return menu(objArr);
            case 14:
                return menuitem$V(objArr);
            case 15:
                Object obj = objArr[0];
                try {
                    Complex complex = (Complex) obj;
                    int length = objArr.length - 1;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return polygon(complex, objArr2);
                        }
                        objArr2[length] = objArr[length + 1];
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "polygon", 1, obj);
                }
            case 16:
                Object obj2 = objArr[0];
                int length2 = objArr.length - 1;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return scroll$V(obj2, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 1];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
