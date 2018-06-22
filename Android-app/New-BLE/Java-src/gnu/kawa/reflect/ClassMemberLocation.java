package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.Symbol;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrappedException;
import java.lang.reflect.Field;

public abstract class ClassMemberLocation extends Location {
    Object instance;
    String mname;
    Field rfield;
    ClassType type;

    public final Object getInstance() {
        return this.instance;
    }

    public final void setInstance(Object obj) {
        this.instance = obj;
    }

    public ClassMemberLocation(Object instance, ClassType type, String mname) {
        this.instance = instance;
        this.type = type;
        this.mname = mname;
    }

    public ClassMemberLocation(Object instance, Class clas, String mname) {
        this.instance = instance;
        this.type = (ClassType) Type.make(clas);
        this.mname = mname;
    }

    public ClassMemberLocation(Object instance, Field field) {
        this.instance = instance;
        this.rfield = field;
        this.mname = field.getName();
    }

    public String getMemberName() {
        return this.mname;
    }

    public ClassType getDeclaringClass() {
        return this.type;
    }

    void setup() {
        RuntimeException uex;
        if (this.rfield == null) {
            try {
                try {
                    this.rfield = this.type.getReflectClass().getField(this.mname);
                } catch (NoSuchFieldException ex) {
                    uex = new UnboundLocationException(null, "Unbound location  - no field " + this.mname + " in " + this.type.getName());
                    uex.initCause(ex);
                    throw uex;
                }
            } catch (RuntimeException ex2) {
                uex = new UnboundLocationException(null, "Unbound location - " + ex2.toString());
                uex.initCause(ex2);
                throw uex;
            }
        }
    }

    public Field getRField() {
        Field rfld = this.rfield;
        if (rfld == null) {
            try {
                rfld = this.type.getReflectClass().getField(this.mname);
                this.rfield = rfld;
            } catch (Exception e) {
                return null;
            }
        }
        return rfld;
    }

    public Class getRClass() {
        Field rfld = this.rfield;
        if (rfld != null) {
            return rfld.getDeclaringClass();
        }
        try {
            return this.type.getReflectClass();
        } catch (Exception e) {
            return null;
        }
    }

    public Object get(Object defaultValue) {
        Field rfld = getRField();
        if (rfld != null) {
            try {
                defaultValue = rfld.get(this.instance);
            } catch (IllegalAccessException ex) {
                throw WrappedException.wrapIfNeeded(ex);
            }
        }
        return defaultValue;
    }

    public boolean isConstant() {
        return (getRField() == null || (this.rfield.getModifiers() & 16) == 0) ? false : true;
    }

    public boolean isBound() {
        return getRField() != null;
    }

    public void set(Object value) {
        setup();
        try {
            this.rfield.set(this.instance, value);
        } catch (IllegalAccessException ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    public static void define(Object instance, Field rfield, String uri, Language language, Environment env) throws IllegalAccessException {
        Symbol sym;
        Location loc;
        Location fvalue = rfield.get(instance);
        Type ftype = Type.make(rfield.getType());
        boolean isAlias = ftype.isSubtype(Compilation.typeLocation);
        boolean isProcedure = ftype.isSubtype(Compilation.typeProcedure);
        int rModifiers = rfield.getModifiers();
        boolean isFinal = (rModifiers & 16) != 0;
        Symbol fdname = (isFinal && (fvalue instanceof Named) && !isAlias) ? ((Named) fvalue).getSymbol() : Compilation.demangleName(rfield.getName(), true);
        if (fdname instanceof Symbol) {
            sym = fdname;
        } else {
            if (uri == null) {
                uri = "";
            }
            sym = Symbol.make(uri, fdname.toString().intern());
        }
        Object obj = null;
        if (isAlias && isFinal) {
            loc = fvalue;
        } else {
            if (isFinal) {
                obj = language.getEnvPropertyFor(rfield, fvalue);
            }
            if ((rModifiers & 8) != 0) {
                loc = new StaticFieldLocation(rfield);
            } else {
                loc = new FieldLocation(instance, rfield);
            }
        }
        env.addLocation(sym, obj, loc);
    }

    public static void defineAll(Object instance, Language language, Environment env) throws IllegalAccessException {
        Field[] fields = instance.getClass().getFields();
        int i = fields.length;
        while (true) {
            i--;
            if (i >= 0) {
                Field field = fields[i];
                String fname = field.getName();
                if (!(fname.startsWith(Declaration.PRIVATE_PREFIX) || fname.endsWith("$instance"))) {
                    define(instance, field, null, language, env);
                }
            } else {
                return;
            }
        }
    }
}
