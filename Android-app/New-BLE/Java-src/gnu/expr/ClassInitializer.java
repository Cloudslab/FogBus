package gnu.expr;

import gnu.bytecode.CodeAttr;

public class ClassInitializer extends Initializer {
    ClassExp cexp;

    public ClassInitializer(ClassExp cexp, Compilation comp) {
        this.field = cexp.allocFieldFor(comp);
        cexp.compileMembers(comp);
        this.cexp = cexp;
        if (this.field.getStaticFlag()) {
            this.next = comp.clinitChain;
            comp.clinitChain = this;
            return;
        }
        LambdaExp heapLambda = cexp.getOwningLambda();
        this.next = heapLambda.initChain;
        heapLambda.initChain = this;
    }

    public void emit(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!this.field.getStaticFlag()) {
            code.emitPushThis();
        }
        this.cexp.compilePushClass(comp, Target.pushValue(Compilation.typeClassType));
        if (this.field.getStaticFlag()) {
            code.emitPutStatic(this.field);
        } else {
            code.emitPutField(this.field);
        }
    }
}
