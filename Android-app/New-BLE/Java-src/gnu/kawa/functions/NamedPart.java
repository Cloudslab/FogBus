package gnu.kawa.functions;

import android.support.v4.app.FragmentTransaction;
import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.CallContext;
import gnu.mapping.HasSetter;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import kawa.standard.Scheme;

/* compiled from: GetNamedPart */
class NamedPart extends ProcedureN implements HasSetter, Externalizable {
    Object container;
    char kind;
    Object member;
    MethodProc methods;

    public NamedPart(Object container, Object member, char kind) {
        this.container = container;
        this.member = member;
        this.kind = kind;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateNamedPart");
    }

    public NamedPart(Object container, String mname, char kind, MethodProc methods) {
        this(container, mname, kind);
        this.methods = methods;
    }

    public int numArgs() {
        if (this.kind == Access.INNERCLASS_CONTEXT || this.kind == Access.CLASS_CONTEXT) {
            return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        }
        if (this.kind == 'D') {
            return 4096;
        }
        return -4096;
    }

    public void apply(CallContext ctx) throws Throwable {
        apply(ctx.getArgs(), ctx);
    }

    public void apply(Object[] args, CallContext ctx) throws Throwable {
        if (this.kind == 'S') {
            this.methods.checkN(args, ctx);
        } else if (this.kind == Access.METHOD_CONTEXT) {
            int nargs = args.length;
            Object[] xargs = new Object[(nargs + 1)];
            xargs[0] = this.container;
            System.arraycopy(args, 0, xargs, 1, nargs);
            this.methods.checkN(xargs, ctx);
        } else {
            ctx.writeValue(applyN(args));
        }
    }

    public Object applyN(Object[] args) throws Throwable {
        Object[] xargs;
        switch (this.kind) {
            case 'C':
                return Convert.as.apply2(this.container, args[0]);
            case 'D':
                String fname = this.member.toString().substring(1);
                if (args.length == 0) {
                    return SlotGet.staticField((ClassType) this.container, fname);
                }
                return SlotGet.field(((Type) this.container).coerceFromObject(args[0]), fname);
            case 'I':
                return Scheme.instanceOf.apply2(args[0], this.container);
            case 'M':
                xargs = new Object[(args.length + 1)];
                xargs[0] = this.container;
                System.arraycopy(args, 0, xargs, 1, args.length);
                return this.methods.applyN(xargs);
            case 'N':
                xargs = new Object[(args.length + 1)];
                xargs[0] = this.container;
                System.arraycopy(args, 0, xargs, 1, args.length);
                return Invoke.make.applyN(xargs);
            case 'S':
                return this.methods.applyN(args);
            default:
                throw new Error("unknown part " + this.member + " in " + this.container);
        }
    }

    public Procedure getSetter() {
        if (this.kind == 'D') {
            return new NamedPartSetter(this);
        }
        throw new RuntimeException("procedure '" + getName() + "' has no setter");
    }

    public void set0(Object value) throws Throwable {
        switch (this.kind) {
            case 'D':
                SlotSet.setStaticField((ClassType) this.container, this.member.toString().substring(1), value);
                return;
            default:
                throw new Error("invalid setter for " + this);
        }
    }

    public void set1(Object object, Object value) throws Throwable {
        switch (this.kind) {
            case 'D':
                SlotSet.setField(((Type) this.container).coerceFromObject(object), this.member.toString().substring(1), value);
                return;
            default:
                throw new Error("invalid setter for " + this);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.container);
        out.writeObject(this.member);
        out.writeChar(this.kind);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.kind = in.readChar();
        this.container = (Procedure) in.readObject();
        this.member = (Procedure) in.readObject();
    }
}
