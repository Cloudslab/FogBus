package gnu.kawa.xml;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.lists.NodePredicate;
import gnu.mapping.Procedure;

public class CompileXmlFunctions {
    public static Expression validateApplyMakeUnescapedData(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1 && (args[0] instanceof QuoteExp)) {
            return new QuoteExp(((MakeUnescapedData) proc).apply1(((QuoteExp) args[0]).getValue()));
        }
        return exp;
    }

    public static Expression validateApplyTreeScanner(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        NodePredicate type = ((TreeScanner) proc).type;
        if (exp.getTypeRaw() == null && (type instanceof Type)) {
            exp.setType(NodeSetType.getInstance((Type) type));
        }
        return exp;
    }
}
