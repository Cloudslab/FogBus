package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArrayGet extends Procedure2 implements Externalizable {
    Type element_type;

    public ArrayGet(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayGet");
        Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArrayGet");
    }

    public Object apply2(Object array, Object index) {
        return this.element_type.coerceToObject(Array.get(array, ((Number) index).intValue()));
    }

    public boolean isSideEffectFree() {
        return true;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.element_type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.element_type = (Type) in.readObject();
    }
}
