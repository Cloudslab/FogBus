package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import java.lang.reflect.Array;

/* compiled from: Setter */
class SetArray extends Procedure2 {
    Object array;
    Type elementType;

    public SetArray(Object array, Language language) {
        this.elementType = language.getTypeFor(array.getClass().getComponentType());
        this.array = array;
    }

    public Object apply2(Object index, Object value) {
        Array.set(this.array, ((Number) index).intValue(), this.elementType.coerceFromObject(value));
        return Values.empty;
    }
}
