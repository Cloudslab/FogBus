package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class ArrayNew extends Procedure1 implements Externalizable {
    Type element_type;

    public ArrayNew(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayNew");
        Procedure.compilerKey.set(this, "*gnu.kawa.reflect.CompileArrays:getForArrayNew");
    }

    public boolean isSideEffectFree() {
        return true;
    }

    public Object apply1(Object count) {
        return Array.newInstance(this.element_type.getImplementationType().getReflectClass(), ((Number) count).intValue());
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.element_type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.element_type = (Type) in.readObject();
    }
}
