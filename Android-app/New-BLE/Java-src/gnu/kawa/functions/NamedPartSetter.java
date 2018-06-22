package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.mapping.Setter;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* compiled from: GetNamedPart */
class NamedPartSetter extends Setter implements Externalizable {
    public NamedPartSetter(NamedPart getter) {
        super(getter);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateNamedPartSetter");
    }

    public int numArgs() {
        if (((NamedPart) this.getter).kind == 'D') {
            return 8193;
        }
        return -4096;
    }

    Procedure getGetter() {
        return this.getter;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getter);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.getter = (Procedure) in.readObject();
    }
}
