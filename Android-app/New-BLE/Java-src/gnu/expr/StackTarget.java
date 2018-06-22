package gnu.expr;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.OccurrenceType;
import gnu.mapping.Values;

public class StackTarget extends Target {
    Type type;

    public StackTarget(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public static Target getInstance(Type type) {
        if (type.isVoid()) {
            return Target.Ignore;
        }
        return type == Type.pointer_type ? Target.pushObject : new StackTarget(type);
    }

    protected boolean compileFromStack0(Compilation comp, Type stackType) {
        return compileFromStack0(comp, stackType, this.type);
    }

    static boolean compileFromStack0(Compilation comp, Type stackType, Type type) {
        if (type == stackType) {
            return true;
        }
        CodeAttr code = comp.getCode();
        if (stackType.isVoid()) {
            comp.compileConstant(Values.empty);
            stackType = Type.pointer_type;
        } else if ((stackType instanceof PrimType) && (type instanceof PrimType)) {
            code.emitConvert(stackType, type);
            return true;
        }
        if (!(stackType instanceof ArrayType)) {
            type.emitConvertFromPrimitive(stackType, code);
            stackType = code.topType();
        } else if (type == Type.pointer_type || "java.lang.Cloneable".equals(type.getName())) {
            return true;
        }
        if (CodeAttr.castNeeded(stackType.getImplementationType(), type.getImplementationType())) {
            return false;
        }
        return true;
    }

    public static void convert(Compilation comp, Type stackType, Type targetType) {
        if (!compileFromStack0(comp, stackType, targetType)) {
            emitCoerceFromObject(targetType, comp);
        }
    }

    protected static void emitCoerceFromObject(Type type, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (type instanceof OccurrenceType) {
            comp.compileConstant(type, Target.pushObject);
            code.emitSwap();
            code.emitInvokeVirtual(ClassType.make("gnu.bytecode.Type").getDeclaredMethod("coerceFromObject", 1));
            return;
        }
        comp.usedClass(type);
        type.emitCoerceFromObject(code);
    }

    public void compileFromStack(Compilation comp, Type stackType) {
        if (!compileFromStack0(comp, stackType)) {
            emitCoerceFromObject(this.type, comp);
        }
    }
}
