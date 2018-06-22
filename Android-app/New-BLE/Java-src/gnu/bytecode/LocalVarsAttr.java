package gnu.bytecode;

import android.support.v4.os.EnvironmentCompat;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVarsAttr extends Attribute {
    public Scope current_scope;
    private Method method;
    Scope parameter_scope;
    Variable[] used;

    public LocalVarsAttr(CodeAttr code) {
        super("LocalVariableTable");
        addToFrontOf(code);
        this.method = (Method) code.getContainer();
    }

    public LocalVarsAttr(Method method) {
        super("LocalVariableTable");
        CodeAttr code = method.code;
        this.method = method;
    }

    public final Method getMethod() {
        return this.method;
    }

    public VarEnumerator allVars() {
        return new VarEnumerator(this.parameter_scope);
    }

    public void enterScope(Scope scope) {
        scope.linkChild(this.current_scope);
        this.current_scope = scope;
        CodeAttr code = this.method.getCode();
        for (Variable var = scope.firstVar(); var != null; var = var.nextVar()) {
            if (var.isSimple()) {
                if (!var.isAssigned()) {
                    var.allocateLocal(code);
                } else if (this.used[var.offset] == null) {
                    this.used[var.offset] = var;
                } else if (this.used[var.offset] != var) {
                    throw new Error("inconsistent local variable assignments for " + var + " != " + this.used[var.offset]);
                }
            }
        }
    }

    public void preserveVariablesUpto(Scope scope) {
        for (Scope cur = this.current_scope; cur != scope; cur = cur.parent) {
            cur.preserved = true;
        }
    }

    public final boolean isEmpty() {
        VarEnumerator vars = allVars();
        while (true) {
            Variable var = vars.nextVar();
            if (var == null) {
                return true;
            }
            if (var.isSimple() && var.name != null) {
                return false;
            }
        }
    }

    public final int getCount() {
        int local_variable_count = 0;
        VarEnumerator vars = allVars();
        while (true) {
            Variable var = vars.nextVar();
            if (var == null) {
                return local_variable_count;
            }
            if (var.shouldEmit()) {
                local_variable_count++;
            }
        }
    }

    public final int getLength() {
        return (getCount() * 10) + 2;
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        VarEnumerator vars = allVars();
        while (true) {
            Variable var = vars.nextVar();
            if (var == null) {
                return;
            }
            if (var.isSimple() && var.name != null) {
                if (var.name_index == 0) {
                    var.name_index = cl.getConstants().addUtf8(var.getName()).index;
                }
                if (var.signature_index == 0) {
                    var.signature_index = cl.getConstants().addUtf8(var.getType().getSignature()).index;
                }
            }
        }
    }

    public void write(DataOutputStream dstr) throws IOException {
        VarEnumerator vars = allVars();
        dstr.writeShort(getCount());
        vars.reset();
        while (true) {
            Variable var = vars.nextVar();
            if (var == null) {
                return;
            }
            if (var.shouldEmit()) {
                Scope scope = var.scope;
                int start_pc = scope.start.position;
                int end_pc = scope.end.position;
                dstr.writeShort(start_pc);
                dstr.writeShort(end_pc - start_pc);
                dstr.writeShort(var.name_index);
                dstr.writeShort(var.signature_index);
                dstr.writeShort(var.offset);
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        VarEnumerator vars = allVars();
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", count: ");
        dst.println(getCount());
        vars.reset();
        while (true) {
            Variable var = vars.nextVar();
            if (var == null) {
                return;
            }
            if (var.isSimple() && var.name != null) {
                dst.print("  slot#");
                dst.print(var.offset);
                dst.print(": name: ");
                dst.printOptionalIndex(var.name_index);
                dst.print(var.getName());
                dst.print(", type: ");
                dst.printOptionalIndex(var.signature_index);
                dst.printSignature(var.getType());
                dst.print(" (pc: ");
                Scope scope = var.scope;
                if (!(scope == null || scope.start == null || scope.end == null)) {
                    int start_pc = scope.start.position;
                    if (start_pc >= 0) {
                        int end_pc = scope.end.position;
                        if (end_pc >= 0) {
                            dst.print(start_pc);
                            dst.print(" length: ");
                            dst.print(end_pc - start_pc);
                            dst.println(')');
                        }
                    }
                }
                dst.print(EnvironmentCompat.MEDIA_UNKNOWN);
                dst.println(')');
            }
        }
    }
}
