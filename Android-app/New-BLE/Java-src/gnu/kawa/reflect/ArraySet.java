package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArraySet extends Procedure3 implements Externalizable {
    Type element_type;

    public ArraySet(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArraySet");
        Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArraySet");
    }

    public Object apply3(Object array, Object index, Object value) {
        Array.set(array, ((Number) index).intValue(), this.element_type.coerceFromObject(value));
        return Values.empty;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.element_type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.element_type = (Type) in.readObject();
    }
}
