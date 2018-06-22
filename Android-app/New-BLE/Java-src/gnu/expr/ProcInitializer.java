package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.mapping.Environment;
import gnu.mapping.PropertySet;
import gnu.mapping.Symbol;

public class ProcInitializer extends Initializer {
    LambdaExp proc;

    public ProcInitializer(LambdaExp lexp, Compilation comp, Field field) {
        this.field = field;
        this.proc = lexp;
        LambdaExp heapLambda = field.getStaticFlag() ? comp.getModule() : lexp.getOwningLambda();
        if ((heapLambda instanceof ModuleExp) && comp.isStatic()) {
            this.next = comp.clinitChain;
            comp.clinitChain = this;
            return;
        }
        this.next = heapLambda.initChain;
        heapLambda.initChain = this;
    }

    public static void emitLoadModuleMethod(LambdaExp proc, Compilation comp) {
        String initName;
        Declaration pdecl = proc.nameDecl;
        Object pname = pdecl == null ? proc.getName() : pdecl.getSymbol();
        ModuleMethod oldproc = null;
        if (!(!comp.immediate || pname == null || pdecl == null)) {
            Symbol sym;
            Environment env = Environment.getCurrent();
            if (pname instanceof Symbol) {
                sym = (Symbol) pname;
            } else {
                sym = Symbol.make("", pname.toString().intern());
            }
            ModuleMethod old = env.get(sym, comp.getLanguage().getEnvPropertyFor(proc.nameDecl), null);
            if (old instanceof ModuleMethod) {
                oldproc = old;
            }
        }
        CodeAttr code = comp.getCode();
        ClassType procClass = Compilation.typeModuleMethod;
        if (oldproc == null) {
            code.emitNew(procClass);
            code.emitDup(1);
            initName = "<init>";
        } else {
            comp.compileConstant(oldproc, Target.pushValue(procClass));
            initName = "init";
        }
        Method initModuleMethod = procClass.getDeclaredMethod(initName, 4);
        LambdaExp owning = proc.getNeedsClosureEnv() ? proc.getOwningLambda() : comp.getModule();
        if ((owning instanceof ClassExp) && owning.staticLinkField != null) {
            code.emitLoad(code.getCurrentScope().getVariable(1));
        } else if (!(owning instanceof ModuleExp) || (comp.moduleClass == comp.mainClass && !comp.method.getStaticFlag())) {
            code.emitPushThis();
        } else {
            if (comp.moduleInstanceVar == null) {
                comp.moduleInstanceVar = code.locals.current_scope.addVariable(code, comp.moduleClass, "$instance");
                if (comp.moduleClass == comp.mainClass || comp.isStatic()) {
                    code.emitGetStatic(comp.moduleInstanceMainField);
                } else {
                    code.emitNew(comp.moduleClass);
                    code.emitDup(comp.moduleClass);
                    code.emitInvokeSpecial(comp.moduleClass.constructor);
                    comp.moduleInstanceMainField = comp.moduleClass.addField("$main", comp.mainClass, 0);
                    code.emitDup(comp.moduleClass);
                    code.emitPushThis();
                    code.emitPutField(comp.moduleInstanceMainField);
                }
                code.emitStore(comp.moduleInstanceVar);
            }
            code.emitLoad(comp.moduleInstanceVar);
        }
        code.emitPushInt(proc.getSelectorValue(comp));
        comp.compileConstant(pname, Target.pushObject);
        code.emitPushInt(((proc.keywords == null ? proc.max_args : -1) << 12) | proc.min_args);
        code.emitInvoke(initModuleMethod);
        if (proc.properties != null) {
            int len = proc.properties.length;
            for (int i = 0; i < len; i += 2) {
                Symbol key = proc.properties[i];
                if (!(key == null || key == PropertySet.nameKey)) {
                    Object val = proc.properties[i + 1];
                    code.emitDup(1);
                    comp.compileConstant(key);
                    Target target = Target.pushObject;
                    if (val instanceof Expression) {
                        ((Expression) val).compile(comp, target);
                    } else {
                        comp.compileConstant(val, target);
                    }
                    code.emitInvokeVirtual(ClassType.make("gnu.mapping.PropertySet").getDeclaredMethod("setProperty", 2));
                }
            }
        }
    }

    public void emit(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!this.field.getStaticFlag()) {
            code.emitPushThis();
        }
        emitLoadModuleMethod(this.proc, comp);
        if (this.field.getStaticFlag()) {
            code.emitPutStatic(this.field);
        } else {
            code.emitPutField(this.field);
        }
    }

    public void reportError(String message, Compilation comp) {
        String saveFile = comp.getFileName();
        int saveLine = comp.getLineNumber();
        int saveColumn = comp.getColumnNumber();
        comp.setLocation(this.proc);
        String name = this.proc.getName();
        StringBuffer sbuf = new StringBuffer(message);
        if (name == null) {
            sbuf.append("unnamed procedure");
        } else {
            sbuf.append("procedure ");
            sbuf.append(name);
        }
        comp.error('e', sbuf.toString());
        comp.setLine(saveFile, saveLine, saveColumn);
    }
}
