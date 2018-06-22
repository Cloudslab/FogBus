package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.bytecode.ClassType;
import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.Consumer;
import gnu.lists.EofClass;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.InPort;
import gnu.mapping.LocationProc;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SyntaxException;
import java.io.Writer;
import kawa.standard.Scheme;
import kawa.standard.char_ready_p;
import kawa.standard.read_line;

/* compiled from: ports.scm */
public class ports extends ModuleBody {
    public static final ports $instance = new ports();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("current-input-port").readResolve());
    static final ClassType Lit1 = ClassType.make("gnu.mapping.InPort");
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("open-input-file").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("open-output-file").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("call-with-input-file").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("call-with-output-file").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("with-input-from-file").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("with-output-to-file").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("input-port?").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("output-port?").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("write-char").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("open-input-string").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("current-output-port").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("open-output-string").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("get-output-string").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("call-with-input-string").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("call-with-output-string").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("force-output").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("newline").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("eof-object?").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("char-ready?").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("write").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("display").readResolve());
    static final ClassType Lit3 = ClassType.make("gnu.mapping.OutPort");
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("input-port-read-state").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("set-port-line!").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("port-line").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("set-input-port-line-number!").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("input-port-line-number").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("port-column").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("input-port-column-number").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("default-prompter").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("set-input-port-prompter!").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("input-port-prompter").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("current-error-port").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("close-input-port").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("close-output-port").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("read").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("read-line").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("transcript-on").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("transcript-off").readResolve());
    static final Keyword Lit5 = Keyword.make("setter");
    static final IntNum Lit6 = IntNum.make(1);
    static final Char Lit7 = Char.make(10);
    static final Char Lit8 = Char.make(32);
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("trim").readResolve());
    public static final ModuleMethod call$Mnwith$Mninput$Mnfile;
    public static final ModuleMethod call$Mnwith$Mninput$Mnstring;
    public static final ModuleMethod call$Mnwith$Mnoutput$Mnfile;
    public static final ModuleMethod call$Mnwith$Mnoutput$Mnstring;
    public static final ModuleMethod char$Mnready$Qu;
    public static final ModuleMethod close$Mninput$Mnport;
    public static final ModuleMethod close$Mnoutput$Mnport;
    public static final LocationProc current$Mnerror$Mnport = null;
    public static final LocationProc current$Mninput$Mnport = null;
    public static final LocationProc current$Mnoutput$Mnport = null;
    public static final ModuleMethod default$Mnprompter;
    public static final ModuleMethod display;
    public static final ModuleMethod eof$Mnobject$Qu;
    public static final ModuleMethod force$Mnoutput;
    public static final ModuleMethod get$Mnoutput$Mnstring;
    public static final ModuleMethod input$Mnport$Mncolumn$Mnnumber;
    public static final GenericProc input$Mnport$Mnline$Mnnumber = null;
    static final ModuleMethod input$Mnport$Mnline$Mnnumber$Fn5;
    public static final GenericProc input$Mnport$Mnprompter = null;
    static final ModuleMethod input$Mnport$Mnprompter$Fn6;
    public static final ModuleMethod input$Mnport$Mnread$Mnstate;
    public static final ModuleMethod input$Mnport$Qu;
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod newline;
    public static final ModuleMethod open$Mninput$Mnfile;
    public static final ModuleMethod open$Mninput$Mnstring;
    public static final ModuleMethod open$Mnoutput$Mnfile;
    public static final ModuleMethod open$Mnoutput$Mnstring;
    public static final ModuleMethod output$Mnport$Qu;
    public static final ModuleMethod port$Mncolumn;
    public static final GenericProc port$Mnline = null;
    static final ModuleMethod port$Mnline$Fn4;
    public static final ModuleMethod read;
    public static final ModuleMethod read$Mnline;
    public static final ModuleMethod set$Mninput$Mnport$Mnline$Mnnumber$Ex;
    public static final ModuleMethod set$Mninput$Mnport$Mnprompter$Ex;
    public static final ModuleMethod set$Mnport$Mnline$Ex;
    public static final ModuleMethod transcript$Mnoff;
    public static final ModuleMethod transcript$Mnon;
    public static final ModuleMethod with$Mninput$Mnfrom$Mnfile;
    public static final ModuleMethod with$Mnoutput$Mnto$Mnfile;
    public static final ModuleMethod write;
    public static final ModuleMethod write$Mnchar;

    static {
        ModuleBody moduleBody = $instance;
        open$Mninput$Mnfile = new ModuleMethod(moduleBody, 1, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        open$Mnoutput$Mnfile = new ModuleMethod(moduleBody, 2, Lit11, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        call$Mnwith$Mninput$Mnfile = new ModuleMethod(moduleBody, 3, Lit12, 8194);
        call$Mnwith$Mnoutput$Mnfile = new ModuleMethod(moduleBody, 4, Lit13, 8194);
        with$Mninput$Mnfrom$Mnfile = new ModuleMethod(moduleBody, 5, Lit14, 8194);
        with$Mnoutput$Mnto$Mnfile = new ModuleMethod(moduleBody, 6, Lit15, 8194);
        input$Mnport$Qu = new ModuleMethod(moduleBody, 7, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        output$Mnport$Qu = new ModuleMethod(moduleBody, 8, Lit17, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn1 = new ModuleMethod(moduleBody, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn2 = new ModuleMethod(moduleBody, 10, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn3 = new ModuleMethod(moduleBody, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        write$Mnchar = new ModuleMethod(moduleBody, 12, Lit18, 8193);
        open$Mninput$Mnstring = new ModuleMethod(moduleBody, 14, Lit19, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        open$Mnoutput$Mnstring = new ModuleMethod(moduleBody, 15, Lit20, 0);
        get$Mnoutput$Mnstring = new ModuleMethod(moduleBody, 16, Lit21, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        call$Mnwith$Mninput$Mnstring = new ModuleMethod(moduleBody, 17, Lit22, 8194);
        call$Mnwith$Mnoutput$Mnstring = new ModuleMethod(moduleBody, 18, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        force$Mnoutput = new ModuleMethod(moduleBody, 19, Lit24, 4096);
        newline = new ModuleMethod(moduleBody, 21, Lit25, 4096);
        eof$Mnobject$Qu = new ModuleMethod(moduleBody, 23, Lit26, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnready$Qu = new ModuleMethod(moduleBody, 24, Lit27, 4096);
        write = new ModuleMethod(moduleBody, 26, Lit28, 8193);
        display = new ModuleMethod(moduleBody, 28, Lit29, 8193);
        input$Mnport$Mnread$Mnstate = new ModuleMethod(moduleBody, 30, Lit30, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set$Mnport$Mnline$Ex = new ModuleMethod(moduleBody, 31, Lit31, 8194);
        port$Mnline$Fn4 = new ModuleMethod(moduleBody, 32, Lit32, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set$Mninput$Mnport$Mnline$Mnnumber$Ex = new ModuleMethod(moduleBody, 33, Lit33, 8194);
        input$Mnport$Mnline$Mnnumber$Fn5 = new ModuleMethod(moduleBody, 34, Lit34, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        port$Mncolumn = new ModuleMethod(moduleBody, 35, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        input$Mnport$Mncolumn$Mnnumber = new ModuleMethod(moduleBody, 36, Lit36, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        default$Mnprompter = new ModuleMethod(moduleBody, 37, Lit37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set$Mninput$Mnport$Mnprompter$Ex = new ModuleMethod(moduleBody, 38, Lit38, 8194);
        input$Mnport$Mnprompter$Fn6 = new ModuleMethod(moduleBody, 39, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        close$Mninput$Mnport = new ModuleMethod(moduleBody, 40, Lit40, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        close$Mnoutput$Mnport = new ModuleMethod(moduleBody, 41, Lit41, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        read = new ModuleMethod(moduleBody, 42, Lit42, 4096);
        read$Mnline = new ModuleMethod(moduleBody, 44, Lit43, 8192);
        transcript$Mnon = new ModuleMethod(moduleBody, 47, Lit44, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transcript$Mnoff = new ModuleMethod(moduleBody, 48, Lit45, 0);
        $instance.run();
    }

    public ports() {
        ModuleInfo.register(this);
    }

    public static void display(Object obj) {
        display(obj, current$Mnoutput$Mnport.apply0());
    }

    public static void forceOutput() {
        forceOutput(current$Mnoutput$Mnport.apply0());
    }

    public static boolean isCharReady() {
        return isCharReady(current$Mninput$Mnport.apply0());
    }

    public static void newline() {
        newline(current$Mnoutput$Mnport.apply0());
    }

    public static Object read() {
        return read((InPort) current$Mninput$Mnport.apply0());
    }

    public static Object readLine() {
        return readLine((LineBufferedReader) current$Mninput$Mnport.apply0(), Lit9);
    }

    public static Object readLine(LineBufferedReader lineBufferedReader) {
        return readLine(lineBufferedReader, Lit9);
    }

    public static void write(Object obj) {
        write(obj, current$Mnoutput$Mnport.apply0());
    }

    public static void writeChar(Object obj) {
        writeChar(obj, OutPort.outDefault());
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        current$Mninput$Mnport = LocationProc.makeNamed(Lit0, InPort.inLocation);
        current$Mninput$Mnport.pushConverter(lambda$Fn1);
        current$Mnoutput$Mnport = LocationProc.makeNamed(Lit2, OutPort.outLocation);
        current$Mnoutput$Mnport.pushConverter(lambda$Fn2);
        current$Mnerror$Mnport = LocationProc.makeNamed(Lit4, OutPort.errLocation);
        current$Mnerror$Mnport.pushConverter(lambda$Fn3);
        port$Mnline = new GenericProc("port-line");
        GenericProc genericProc = port$Mnline;
        Object[] objArr = new Object[3];
        objArr[0] = Lit5;
        objArr[1] = set$Mnport$Mnline$Ex;
        Procedure port$Mnline = port$Mnline$Fn4;
        objArr[2] = port$Mnline$Fn4;
        genericProc.setProperties(objArr);
        input$Mnport$Mnline$Mnnumber = new GenericProc("input-port-line-number");
        genericProc = input$Mnport$Mnline$Mnnumber;
        objArr = new Object[3];
        objArr[0] = Lit5;
        objArr[1] = set$Mninput$Mnport$Mnline$Mnnumber$Ex;
        Procedure input$Mnport$Mnline$Mnnumber = input$Mnport$Mnline$Mnnumber$Fn5;
        objArr[2] = input$Mnport$Mnline$Mnnumber$Fn5;
        genericProc.setProperties(objArr);
        input$Mnport$Mnprompter = new GenericProc("input-port-prompter");
        genericProc = input$Mnport$Mnprompter;
        objArr = new Object[3];
        objArr[0] = Lit5;
        objArr[1] = set$Mninput$Mnport$Mnprompter$Ex;
        Procedure input$Mnport$Mnprompter = input$Mnport$Mnprompter$Fn6;
        objArr[2] = input$Mnport$Mnprompter$Fn6;
        genericProc.setProperties(objArr);
    }

    public static InPort openInputFile(Path name) {
        return InPort.openFile(name);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 9:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 11:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 14:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 16:
                if (!(obj instanceof CharArrayOutPort)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 18:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
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
            case 26:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 30:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 32:
                if (!(obj instanceof LineBufferedReader)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 34:
                if (!(obj instanceof LineBufferedReader)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 36:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 37:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 39:
                if (!(obj instanceof TtyInPort)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 40:
                if (!(obj instanceof InPort)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                if (!(obj instanceof OutPort)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 42:
                if (!(obj instanceof InPort)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                if (!(obj instanceof LineBufferedReader)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static OutPort openOutputFile(Path name) {
        return OutPort.openFile(name);
    }

    public static Object callWithInputFile(Path path, Procedure proc) {
        InPort port = openInputFile(path);
        try {
            Object apply1 = proc.apply1(port);
            return apply1;
        } finally {
            closeInputPort(port);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                if (Path.coerceToPathOrNull(obj) == null) {
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
            case 4:
                if (Path.coerceToPathOrNull(obj) == null) {
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
            case 5:
                if (Path.coerceToPathOrNull(obj) == null) {
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
            case 6:
                if (Path.coerceToPathOrNull(obj) == null) {
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
            case 12:
                callContext.value1 = obj;
                if (!(obj2 instanceof OutPort)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 17:
                if (!(obj instanceof CharSequence)) {
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
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 31:
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
            case 38:
                if (!(obj instanceof TtyInPort)) {
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
            case 44:
                if (!(obj instanceof LineBufferedReader)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Symbol)) {
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

    public static Object callWithOutputFile(Path path, Procedure proc) {
        OutPort port = openOutputFile(path);
        try {
            Object apply1 = proc.apply1(port);
            return apply1;
        } finally {
            closeOutputPort(port);
        }
    }

    public static Object withInputFromFile(Path pathname, Procedure proc) {
        InPort port = InPort.openFile(pathname);
        InPort save = InPort.inDefault();
        try {
            InPort.setInDefault(port);
            Object apply0 = proc.apply0();
            return apply0;
        } finally {
            InPort.setInDefault(save);
            port.close();
        }
    }

    public static Object withOutputToFile(Path path, Procedure proc) {
        OutPort port = OutPort.openFile(path);
        OutPort save = OutPort.outDefault();
        try {
            OutPort.setOutDefault(port);
            Object apply0 = proc.apply0();
            return apply0;
        } finally {
            OutPort.setOutDefault(save);
            port.close();
        }
    }

    public static boolean isInputPort(Object x) {
        return x instanceof InPort;
    }

    public static boolean isOutputPort(Object x) {
        return x instanceof OutPort;
    }

    static Object lambda1(Object arg) {
        try {
            return (InPort) arg;
        } catch (ClassCastException ex) {
            WrongType wt = WrongType.make(ex, current$Mninput$Mnport, 1, arg);
            wt.expectedType = Lit1;
            throw wt;
        }
    }

    static Object lambda2(Object arg) {
        try {
            return (OutPort) arg;
        } catch (ClassCastException ex) {
            WrongType wt = WrongType.make(ex, current$Mnoutput$Mnport, 1, arg);
            wt.expectedType = Lit3;
            throw wt;
        }
    }

    static Object lambda3(Object arg) {
        try {
            return (OutPort) arg;
        } catch (ClassCastException ex) {
            WrongType wt = WrongType.make(ex, current$Mnerror$Mnport, 1, arg);
            wt.expectedType = Lit3;
            throw wt;
        }
    }

    public static void writeChar(Object ch, OutPort port) {
        try {
            Char.print(characters.char$To$Integer((Char) ch), port);
        } catch (ClassCastException e) {
            throw new WrongType(e, "char->integer", 1, ch);
        }
    }

    public static InPort openInputString(CharSequence str) {
        return new CharArrayInPort(str == null ? null : str.toString());
    }

    public static CharArrayOutPort openOutputString() {
        return new CharArrayOutPort();
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 15:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 19:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 21:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 24:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 42:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 44:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 48:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static FString getOutputString(CharArrayOutPort output$Mnport) {
        return new FString(output$Mnport.toCharArray());
    }

    public static Object callWithInputString(CharSequence str, Procedure proc) {
        CharArrayInPort port = new CharArrayInPort(str == null ? null : str.toString());
        Object result = proc.apply1(port);
        closeInputPort(port);
        return result;
    }

    public static Object callWithOutputString(Procedure proc) {
        CharArrayOutPort port = new CharArrayOutPort();
        proc.apply1(port);
        char[] chars = port.toCharArray();
        port.close();
        return new FString(chars);
    }

    public static void forceOutput(Object port) {
        try {
            ((Writer) port).flush();
        } catch (ClassCastException e) {
            throw new WrongType(e, "java.io.Writer.flush()", 1, port);
        }
    }

    public static void newline(Object port) {
        try {
            ((OutPort) port).println();
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.mapping.OutPort.println()", 1, port);
        }
    }

    public static boolean isEofObject(Object obj) {
        return obj == EofClass.eofValue;
    }

    public static boolean isCharReady(Object port) {
        return char_ready_p.ready(port);
    }

    public static void write(Object value, Object out) {
        try {
            Scheme.writeFormat.format(value, (Consumer) out);
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.AbstractFormat.format(java.lang.Object,gnu.lists.Consumer)", 3, out);
        }
    }

    public static void display(Object value, Object out) {
        try {
            Scheme.displayFormat.format(value, (Consumer) out);
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.AbstractFormat.format(java.lang.Object,gnu.lists.Consumer)", 3, out);
        }
    }

    public static char inputPortReadState(Object port) {
        try {
            return ((InPort) port).getReadState();
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.mapping.InPort.getReadState()", 1, port);
        }
    }

    public static void setPortLine$Ex(Object port, Object line) {
        try {
            try {
                ((LineBufferedReader) port).setLineNumber(((Number) line).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.text.LineBufferedReader.setLineNumber(int)", 2, line);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "gnu.text.LineBufferedReader.setLineNumber(int)", 1, port);
        }
    }

    public static int portLine(LineBufferedReader port) {
        return port.getLineNumber();
    }

    public static void setInputPortLineNumber$Ex(Object port, Object num) {
        setPortLine$Ex(port, AddOp.$Mn.apply2(num, Lit6));
    }

    public static Object inputPortLineNumber(LineBufferedReader port) {
        return AddOp.$Pl.apply2(Lit6, port$Mnline.apply1(port));
    }

    public static int portColumn(Object port) {
        try {
            return ((LineBufferedReader) port).getColumnNumber();
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.text.LineBufferedReader.getColumnNumber()", 1, port);
        }
    }

    public static int inputPortColumnNumber(Object port) {
        return portColumn(port) + 1;
    }

    public static Object defaultPrompter(Object port) {
        char state = inputPortReadState(port);
        if (characters.isChar$Eq(Char.make(state), Lit7)) {
            return "";
        }
        String str;
        Object[] objArr = new Object[3];
        if (characters.isChar$Eq(Char.make(state), Lit8)) {
            str = "#|kawa:";
        } else {
            str = strings.stringAppend("#|", strings.makeString(1, Char.make(state)), "---:");
        }
        objArr[0] = str;
        Object apply1 = input$Mnport$Mnline$Mnnumber.apply1(port);
        try {
            objArr[1] = numbers.number$To$String((Number) apply1);
            objArr[2] = "|# ";
            return strings.stringAppend(objArr);
        } catch (ClassCastException e) {
            throw new WrongType(e, "number->string", 0, apply1);
        }
    }

    public static void setInputPortPrompter$Ex(TtyInPort port, Procedure prompter) {
        port.setPrompter(prompter);
    }

    public static Procedure inputPortPrompter(TtyInPort port) {
        return port.getPrompter();
    }

    public static Object closeInputPort(InPort port) {
        port.close();
        return Values.empty;
    }

    public static Object closeOutputPort(OutPort port) {
        port.close();
        return Values.empty;
    }

    public static Object read(InPort port) {
        LispReader lexer = new LispReader(port);
        try {
            Object result = lexer.readObject();
            if (!lexer.seenErrors()) {
                return result;
            }
            throw new SyntaxException(lexer.getMessages());
        } catch (SyntaxException ex) {
            ex.setHeader("syntax error in read:");
            throw ex;
        }
    }

    public static Object readLine(LineBufferedReader port, Symbol handling) {
        return read_line.apply(port, handling == null ? null : handling.toString());
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 3:
                try {
                    try {
                        return callWithInputFile(Path.valueOf(obj), (Procedure) obj2);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "call-with-input-file", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "call-with-input-file", 1, obj);
                }
            case 4:
                try {
                    try {
                        return callWithOutputFile(Path.valueOf(obj), (Procedure) obj2);
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "call-with-output-file", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "call-with-output-file", 1, obj);
                }
            case 5:
                try {
                    try {
                        return withInputFromFile(Path.valueOf(obj), (Procedure) obj2);
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "with-input-from-file", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "with-input-from-file", 1, obj);
                }
            case 6:
                try {
                    try {
                        return withOutputToFile(Path.valueOf(obj), (Procedure) obj2);
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "with-output-to-file", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "with-output-to-file", 1, obj);
                }
            case 12:
                try {
                    writeChar(obj, (OutPort) obj2);
                    return Values.empty;
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "write-char", 2, obj2);
                }
            case 17:
                try {
                    try {
                        return callWithInputString((CharSequence) obj, (Procedure) obj2);
                    } catch (ClassCastException e222222222) {
                        throw new WrongType(e222222222, "call-with-input-string", 2, obj2);
                    }
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "call-with-input-string", 1, obj);
                }
            case 26:
                write(obj, obj2);
                return Values.empty;
            case 28:
                display(obj, obj2);
                return Values.empty;
            case 31:
                setPortLine$Ex(obj, obj2);
                return Values.empty;
            case 33:
                setInputPortLineNumber$Ex(obj, obj2);
                return Values.empty;
            case 38:
                try {
                    try {
                        setInputPortPrompter$Ex((TtyInPort) obj, (Procedure) obj2);
                        return Values.empty;
                    } catch (ClassCastException e22222222222) {
                        throw new WrongType(e22222222222, "set-input-port-prompter!", 2, obj2);
                    }
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "set-input-port-prompter!", 1, obj);
                }
            case 44:
                try {
                    try {
                        return readLine((LineBufferedReader) obj, (Symbol) obj2);
                    } catch (ClassCastException e2222222222222) {
                        throw new WrongType(e2222222222222, "read-line", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "read-line", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static void transcriptOn(Object filename) {
        OutPort.setLogFile(filename.toString());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                try {
                    return openInputFile(Path.valueOf(obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "open-input-file", 1, obj);
                }
            case 2:
                try {
                    return openOutputFile(Path.valueOf(obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "open-output-file", 1, obj);
                }
            case 7:
                return isInputPort(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 8:
                return isOutputPort(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 9:
                return lambda1(obj);
            case 10:
                return lambda2(obj);
            case 11:
                return lambda3(obj);
            case 12:
                writeChar(obj);
                return Values.empty;
            case 14:
                try {
                    return openInputString((CharSequence) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "open-input-string", 1, obj);
                }
            case 16:
                try {
                    return getOutputString((CharArrayOutPort) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "get-output-string", 1, obj);
                }
            case 18:
                try {
                    return callWithOutputString((Procedure) obj);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "call-with-output-string", 1, obj);
                }
            case 19:
                forceOutput(obj);
                return Values.empty;
            case 21:
                newline(obj);
                return Values.empty;
            case 23:
                return isEofObject(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 24:
                return isCharReady(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 26:
                write(obj);
                return Values.empty;
            case 28:
                display(obj);
                return Values.empty;
            case 30:
                return Char.make(inputPortReadState(obj));
            case 32:
                try {
                    return Integer.valueOf(portLine((LineBufferedReader) obj));
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "port-line", 1, obj);
                }
            case 34:
                try {
                    return inputPortLineNumber((LineBufferedReader) obj);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "input-port-line-number", 1, obj);
                }
            case 35:
                return Integer.valueOf(portColumn(obj));
            case 36:
                return Integer.valueOf(inputPortColumnNumber(obj));
            case 37:
                return defaultPrompter(obj);
            case 39:
                try {
                    return inputPortPrompter((TtyInPort) obj);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "input-port-prompter", 1, obj);
                }
            case 40:
                try {
                    return closeInputPort((InPort) obj);
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "close-input-port", 1, obj);
                }
            case 41:
                try {
                    return closeOutputPort((OutPort) obj);
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "close-output-port", 1, obj);
                }
            case 42:
                try {
                    return read((InPort) obj);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "read", 1, obj);
                }
            case 44:
                try {
                    return readLine((LineBufferedReader) obj);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "read-line", 1, obj);
                }
            case 47:
                transcriptOn(obj);
                return Values.empty;
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static void transcriptOff() {
        OutPort.closeLogFile();
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 15:
                return openOutputString();
            case 19:
                forceOutput();
                return Values.empty;
            case 21:
                newline();
                return Values.empty;
            case 24:
                return isCharReady() ? Boolean.TRUE : Boolean.FALSE;
            case 42:
                return read();
            case 44:
                return readLine();
            case 48:
                transcriptOff();
                return Values.empty;
            default:
                return super.apply0(moduleMethod);
        }
    }
}
