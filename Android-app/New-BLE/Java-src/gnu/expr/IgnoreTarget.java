package gnu.expr;

import gnu.bytecode.Type;

public class IgnoreTarget extends Target {
    public Type getType() {
        return Type.voidType;
    }

    public void compileFromStack(Compilation comp, Type stackType) {
        if (!stackType.isVoid()) {
            comp.getCode().emitPop(1);
        }
    }
}
