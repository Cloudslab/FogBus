package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.xml.Document;
import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KComment;
import gnu.kawa.xml.KDocument;
import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KProcessingInstruction;
import gnu.kawa.xml.OutputAsXML;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;

/* compiled from: XML.scm */
public class XML extends ModuleBody {
    public static final XML $instance = new XML();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("parse-xml-from-url").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("element-name").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("attribute-name").readResolve());
    public static OutputAsXML as$Mnxml;
    public static final ModuleMethod attribute$Mnname;
    public static final Class comment = KComment.class;
    public static final ModuleMethod element$Mnname;
    public static final ModuleMethod parse$Mnxml$Mnfrom$Mnurl;
    public static final Class processing$Mninstruction = KProcessingInstruction.class;

    static {
        ModuleBody moduleBody = $instance;
        parse$Mnxml$Mnfrom$Mnurl = new ModuleMethod(moduleBody, 1, Lit0, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        element$Mnname = new ModuleMethod(moduleBody, 2, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        attribute$Mnname = new ModuleMethod(moduleBody, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public XML() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        as$Mnxml = new OutputAsXML();
    }

    public static KDocument parseXmlFromUrl(Object url) {
        return Document.parse(url);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                if (!(obj instanceof KElement)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                if (!(obj instanceof KAttr)) {
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

    public static Symbol elementName(KElement element) {
        return element.getNodeSymbol();
    }

    public static Symbol attributeName(KAttr attr) {
        return attr.getNodeSymbol();
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return parseXmlFromUrl(obj);
            case 2:
                try {
                    return elementName((KElement) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "element-name", 1, obj);
                }
            case 3:
                try {
                    return attributeName((KAttr) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "attribute-name", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
