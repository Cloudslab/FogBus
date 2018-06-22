package gnu.kawa.xslt;

import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.Focus;
import gnu.kawa.xml.KDocument;
import gnu.lists.TreeList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.xquery.lang.XQParser;
import gnu.xquery.lang.XQResolveNames;
import gnu.xquery.lang.XQuery;
import java.io.IOException;

public class XSLT extends XQuery {
    public static XSLT instance;
    public static Symbol nullMode = Symbol.make(null, "");

    public String getName() {
        return "XSLT";
    }

    public XSLT() {
        instance = this;
        ModuleBody.setMainPrintValues(true);
    }

    public static XSLT getXsltInstance() {
        if (instance == null) {
            XSLT xslt = new XSLT();
        }
        return instance;
    }

    public Lexer getLexer(InPort inp, SourceMessages messages) {
        return new XslTranslator(inp, messages, this);
    }

    public boolean parse(Compilation comp, int options) throws IOException, SyntaxException {
        Compilation.defaultCallConvention = 2;
        ((XslTranslator) comp.lexer).parse(comp);
        comp.setState(4);
        XQParser xqparser = new XQParser(null, comp.getMessages(), this);
        XQResolveNames resolver = new XQResolveNames(comp);
        resolver.functionNamespacePath = xqparser.functionNamespacePath;
        resolver.parser = xqparser;
        resolver.resolveModule(comp.mainLambda);
        return true;
    }

    public static void registerEnvironment() {
        Language.setDefaults(new XSLT());
    }

    public static void defineCallTemplate(Symbol name, double priority, Procedure template) {
    }

    public static void defineApplyTemplate(String pattern, double priority, Symbol mode, Procedure template) {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable.getTemplateTable(mode).enter(pattern, priority, template);
    }

    public static void defineTemplate(Symbol name, String pattern, double priority, Symbol mode, Procedure template) {
        if (name != null) {
            defineCallTemplate(name, priority, template);
        }
        if (pattern != null) {
            defineApplyTemplate(pattern, priority, mode, template);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void process(gnu.lists.TreeList r12, gnu.kawa.xml.Focus r13, gnu.mapping.CallContext r14) throws java.lang.Throwable {
        /*
        r6 = r14.consumer;
    L_0x0002:
        r2 = r13.ipos;
        r3 = r12.getNextKind(r2);
        switch(r3) {
            case 29: goto L_0x0079;
            case 30: goto L_0x000b;
            case 31: goto L_0x000b;
            case 32: goto L_0x000b;
            case 33: goto L_0x0013;
            case 34: goto L_0x000c;
            case 35: goto L_0x004f;
            case 36: goto L_0x008e;
            case 37: goto L_0x008e;
            default: goto L_0x000b;
        };
    L_0x000b:
        return;
    L_0x000c:
        r2 = r12.firstChildPos(r2);
    L_0x0010:
        r13.ipos = r2;
        goto L_0x0002;
    L_0x0013:
        r8 = r13.getNextTypeObject();
        r4 = r13.getNextTypeName();
        r9 = gnu.kawa.xslt.TemplateTable.nullModeTable;
        r7 = r9.find(r4);
        if (r7 == 0) goto L_0x0035;
    L_0x0023:
        r7.check0(r14);
        r14.runUntilDone();
    L_0x0029:
        r9 = r2 >>> 1;
        r9 = r12.nextDataIndex(r9);
        r2 = r9 << 1;
        r13.gotoNext();
        goto L_0x0010;
    L_0x0035:
        r6.startElement(r8);
        r0 = r12.firstAttributePos(r2);
        if (r0 != 0) goto L_0x0042;
    L_0x003e:
        r0 = r12.firstChildPos(r2);
    L_0x0042:
        r13.push(r12, r0);
        process(r12, r13, r14);
        r13.pop();
        r6.endElement();
        goto L_0x0029;
    L_0x004f:
        r8 = r13.getNextTypeObject();
        r4 = r13.getNextTypeName();
        r9 = gnu.kawa.xslt.TemplateTable.nullModeTable;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "@";
        r10 = r10.append(r11);
        r10 = r10.append(r4);
        r10 = r10.toString();
        r7 = r9.find(r10);
        if (r7 == 0) goto L_0x0079;
    L_0x0072:
        r7.check0(r14);
        r14.runUntilDone();
        goto L_0x0010;
    L_0x0079:
        r1 = r2 >>> 1;
        r9 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5 = r12.nextNodeIndex(r1, r9);
        if (r1 != r5) goto L_0x0088;
    L_0x0084:
        r5 = r12.nextDataIndex(r1);
    L_0x0088:
        r12.consumeIRange(r1, r5, r6);
        r2 = r5 << 1;
        goto L_0x0010;
    L_0x008e:
        r9 = r2 >>> 1;
        r9 = r12.nextDataIndex(r9);
        r2 = r9 << 1;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xslt.XSLT.process(gnu.lists.TreeList, gnu.kawa.xml.Focus, gnu.mapping.CallContext):void");
    }

    public static void runStylesheet() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        ApplicationMainSupport.processSetProperties();
        String[] args = ApplicationMainSupport.commandLineArgArray;
        for (String arg : args) {
            KDocument doc = Document.parse(arg);
            Focus pos = Focus.getCurrent();
            pos.push(doc.sequence, doc.ipos);
            process((TreeList) doc.sequence, pos, ctx);
        }
    }

    public static void applyTemplates(String select, Symbol mode) throws Throwable {
        if (mode == null) {
            mode = nullMode;
        }
        TemplateTable table = TemplateTable.getTemplateTable(mode);
        CallContext ctx = CallContext.getInstance();
        Focus pos = Focus.getCurrent();
        TreeList doc = pos.sequence;
        pos.push(doc, doc.firstChildPos(pos.ipos));
        process(doc, pos, ctx);
        pos.pop();
    }
}
