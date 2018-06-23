package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.reflect.OccurrenceType;

public class ConsumerTarget extends Target {
    Variable consumer;
    boolean isContextTarget;

    public ConsumerTarget(Variable consumer) {
        this.consumer = consumer;
    }

    public Variable getConsumerVariable() {
        return this.consumer;
    }

    public final boolean isContextTarget() {
        return this.isContextTarget;
    }

    public static Target makeContextTarget(Compilation comp) {
        CodeAttr code = comp.getCode();
        comp.loadCallContext();
        code.emitGetField(Compilation.typeCallContext.getDeclaredField("consumer"));
        Variable result = code.getCurrentScope().addVariable(code, Compilation.typeConsumer, "$result");
        code.emitStore(result);
        ConsumerTarget target = new ConsumerTarget(result);
        target.isContextTarget = true;
        return target;
    }

    public static void compileUsingConsumer(Expression exp, Compilation comp, Target target) {
        if ((target instanceof ConsumerTarget) || (target instanceof IgnoreTarget)) {
            exp.compile(comp, target);
            return;
        }
        ClassType typeValues = Compilation.typeValues;
        compileUsingConsumer(exp, comp, target, typeValues.getDeclaredMethod("make", 0), typeValues.getDeclaredMethod("canonicalize", 0));
    }

    public static void compileUsingConsumer(Expression exp, Compilation comp, Target target, Method makeMethod, Method resultMethod) {
        Type ctype;
        CodeAttr code = comp.getCode();
        Scope scope = code.pushScope();
        if (makeMethod.getName() == "<init>") {
            Type cltype = makeMethod.getDeclaringClass();
            ctype = cltype;
            code.emitNew(cltype);
            code.emitDup(ctype);
            code.emitInvoke(makeMethod);
        } else {
            ctype = makeMethod.getReturnType();
            code.emitInvokeStatic(makeMethod);
        }
        Variable consumer = scope.addVariable(code, ctype, null);
        Target ctarget = new ConsumerTarget(consumer);
        code.emitStore(consumer);
        exp.compile(comp, ctarget);
        code.emitLoad(consumer);
        if (resultMethod != null) {
            code.emitInvoke(resultMethod);
        }
        code.popScope();
        if (resultMethod != null) {
            ctype = resultMethod.getReturnType();
        }
        target.compileFromStack(comp, ctype);
    }

    public void compileFromStack(Compilation comp, Type stackType) {
        compileFromStack(comp, stackType, -1);
    }

    void compileFromStack(Compilation comp, Type stackType, int consumerPushed) {
        char sig;
        CodeAttr code = comp.getCode();
        String methodName = null;
        Method method = null;
        Type methodArg = null;
        boolean islong = false;
        stackType = stackType.getImplementationType();
        if (stackType instanceof PrimType) {
            sig = stackType.getSignature().charAt(0);
            switch (sig) {
                case 'B':
                case 'I':
                case 'S':
                    methodName = "writeInt";
                    methodArg = Type.intType;
                    break;
                case 'C':
                    methodName = "append";
                    methodArg = Type.charType;
                    break;
                case 'D':
                    methodName = "writeDouble";
                    methodArg = Type.doubleType;
                    islong = true;
                    break;
                case 'F':
                    methodName = "writeFloat";
                    methodArg = Type.floatType;
                    break;
                case 'J':
                    methodName = "writeLong";
                    methodArg = Type.longType;
                    islong = true;
                    break;
                case 'V':
                    return;
                case 'Z':
                    methodName = "writeBoolean";
                    methodArg = Type.booleanType;
                    break;
            }
        }
        sig = '\u0000';
        if (consumerPushed == 1 || OccurrenceType.itemCountIsOne(stackType)) {
            methodName = "writeObject";
            methodArg = Type.pointer_type;
        } else {
            method = Compilation.typeValues.getDeclaredMethod("writeValues", 2);
            code.emitLoad(this.consumer);
            if (consumerPushed == 0) {
                code.emitSwap();
            }
            code.emitInvokeStatic(method);
            return;
        }
        if (consumerPushed < 0) {
            if (islong) {
                code.pushScope();
                Variable temp = code.addLocal(stackType);
                code.emitStore(temp);
                code.emitLoad(this.consumer);
                code.emitLoad(temp);
                code.popScope();
            } else {
                code.emitLoad(this.consumer);
                code.emitSwap();
            }
        }
        if (null == null && methodName != null) {
            method = Compilation.typeConsumer.getDeclaredMethod(methodName, new Type[]{methodArg});
        }
        if (method != null) {
            code.emitInvokeInterface(method);
        }
        if (sig == Access.CLASS_CONTEXT) {
            code.emitPop(1);
        }
    }

    public boolean compileWrite(Expression exp, Compilation comp) {
        Type implType = exp.getType().getImplementationType();
        if ((!(implType instanceof PrimType) || implType.isVoid()) && !OccurrenceType.itemCountIsOne(implType)) {
            return false;
        }
        comp.getCode().emitLoad(this.consumer);
        exp.compile(comp, StackTarget.getInstance(implType));
        compileFromStack(comp, implType, 1);
        return true;
    }

    public Type getType() {
        return Compilation.scmSequenceType;
    }
}
