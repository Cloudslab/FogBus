package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.SyntaxForm;

public class IfFeature {
    public static boolean testFeature(Object form) {
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof String) || (form instanceof SimpleSymbol)) {
            return hasFeature(form.toString());
        }
        return false;
    }

    public static boolean hasFeature(String name) {
        if (name == "kawa" || name == "srfi-0" || name == "srfi-4" || name == "srfi-6" || name == "srfi-8" || name == "srfi-9" || name == "srfi-11" || name == "srfi-16" || name == "srfi-17" || name == "srfi-23" || name == "srfi-25" || name == "srfi-26" || name == "srfi-28" || name == "srfi-30" || name == "srfi-39") {
            return true;
        }
        if (name == "in-http-server" || name == "in-servlet") {
            int mflags = ModuleContext.getContext().getFlags();
            if (name == "in-http-server") {
                if ((ModuleContext.IN_HTTP_SERVER & mflags) == 0) {
                    return false;
                }
                return true;
            } else if (name == "in-servlet") {
                if ((ModuleContext.IN_SERVLET & mflags) == 0) {
                    return false;
                }
                return true;
            }
        }
        Declaration decl = Compilation.getCurrent().lookup(("%provide%" + name).intern(), -1);
        if (decl == null || decl.getFlag(65536)) {
            return false;
        }
        return true;
    }
}
