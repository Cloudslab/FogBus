package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.expr.Declaration;
import gnu.mapping.Environment;
import gnu.mapping.Symbol;
import java.lang.reflect.Field;
import kawa.lang.Macro;

public class StaticFieldLocation extends FieldLocation {
    public StaticFieldLocation(String cname, String fname) {
        super(null, ClassType.make(cname), fname);
    }

    public StaticFieldLocation(ClassType type, String mname) {
        super(null, type, mname);
    }

    public StaticFieldLocation(Field field) {
        super(null, field);
    }

    public Object get(Object defaultValue) {
        Object val = super.get(defaultValue);
        if (val instanceof Macro) {
            getDeclaration();
        }
        return val;
    }

    public static StaticFieldLocation define(Environment environ, Symbol sym, Object property, String cname, String fname) {
        StaticFieldLocation loc = new StaticFieldLocation(cname, fname);
        environ.addLocation(sym, property, loc);
        return loc;
    }

    public static StaticFieldLocation make(Declaration decl) {
        gnu.bytecode.Field fld = decl.field;
        StaticFieldLocation loc = new StaticFieldLocation(fld.getDeclaringClass(), fld.getName());
        loc.setDeclaration(decl);
        return loc;
    }

    public static StaticFieldLocation make(String cname, String fldName) {
        return new StaticFieldLocation(cname, fldName);
    }
}
