package gnu.kawa.functions;

import android.support.v4.app.FragmentTransaction;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class GetNamedInstancePart extends ProcedureN implements Externalizable, HasSetter {
    boolean isField;
    String pname;

    public GetNamedInstancePart() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateGetNamedInstancePart");
    }

    public GetNamedInstancePart(String name) {
        this();
        setPartName(name);
    }

    public void setPartName(String name) {
        setName("get-instance-part:" + name);
        if (name.length() <= 1 || name.charAt(0) != '.') {
            this.isField = false;
            this.pname = name;
            return;
        }
        this.isField = true;
        this.pname = name.substring(1);
    }

    public int numArgs() {
        return this.isField ? FragmentTransaction.TRANSIT_FRAGMENT_OPEN : -4095;
    }

    public Object applyN(Object[] args) throws Throwable {
        Procedure.checkArgCount(this, args.length);
        if (this.isField) {
            return SlotGet.field(args[0], this.pname);
        }
        Object[] xargs = new Object[(args.length + 1)];
        xargs[0] = args[0];
        xargs[1] = this.pname;
        System.arraycopy(args, 1, xargs, 2, args.length - 1);
        return Invoke.invoke.applyN(xargs);
    }

    public Procedure getSetter() {
        if (this.isField) {
            return new SetNamedInstancePart(this.pname);
        }
        throw new RuntimeException("no setter for instance method call");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.isField ? "." + this.pname : this.pname);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setPartName((String) in.readObject());
    }
}
