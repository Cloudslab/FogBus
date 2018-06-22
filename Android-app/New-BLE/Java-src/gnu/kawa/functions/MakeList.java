package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;

public class MakeList extends ProcedureN implements Inlineable {
    public static final MakeList list = new MakeList();

    static {
        list.setName("list");
    }

    public static Object list$V(Object[] args) {
        LList result = LList.Empty;
        int i = args.length;
        Object result2 = result;
        while (true) {
            i--;
            if (i < 0) {
                return result2;
            }
            Pair result3 = new Pair(args[i], result2);
        }
    }

    public Object applyN(Object[] args) {
        return list$V(args);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        compile(exp.getArgs(), 0, comp);
        target.compileFromStack(comp, exp.getType());
    }

    public static void compile(Expression[] args, int offset, Compilation comp) {
        int len = args.length - offset;
        CodeAttr code = comp.getCode();
        if (len == 0) {
            new QuoteExp(LList.Empty).compile(comp, Target.pushObject);
        } else if (len <= 4) {
            for (int i = 0; i < len; i++) {
                args[offset + i].compile(comp, Target.pushObject);
            }
            code.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("list" + len, null));
        } else {
            args[offset].compile(comp, Target.pushObject);
            code.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("list1", null));
            code.emitDup(1);
            offset++;
            len--;
            while (len >= 4) {
                args[offset].compile(comp, Target.pushObject);
                args[offset + 1].compile(comp, Target.pushObject);
                args[offset + 2].compile(comp, Target.pushObject);
                args[offset + 3].compile(comp, Target.pushObject);
                len -= 4;
                offset += 4;
                code.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("chain4", null));
            }
            while (len > 0) {
                args[offset].compile(comp, Target.pushObject);
                len--;
                offset++;
                code.emitInvokeStatic(Compilation.scmListType.getDeclaredMethod("chain1", null));
            }
            code.emitPop(1);
        }
    }

    public Type getReturnType(Expression[] args) {
        return args.length > 0 ? Compilation.typePair : LangObjType.listType;
    }
}
