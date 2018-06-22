package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleInfo;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.WrappedException;
import kawa.lang.Syntax;

public class FieldLocation extends ClassMemberLocation {
    static final int CONSTANT = 4;
    static final int INDIRECT_LOCATION = 2;
    public static final int KIND_FLAGS_SET = 64;
    public static final int PROCEDURE = 16;
    static final int SETUP_DONE = 1;
    public static final int SYNTAX = 32;
    static final int VALUE_SET = 8;
    Declaration decl;
    private int flags;
    Object value;

    public boolean isIndirectLocation() {
        return (this.flags & 2) != 0;
    }

    public void setProcedure() {
        this.flags |= 84;
    }

    public void setSyntax() {
        this.flags |= 100;
    }

    void setKindFlags() {
        Field fld = getDeclaringClass().getDeclaredField(getMemberName());
        int fflags = fld.getModifiers();
        Type ftype = fld.getType();
        if (ftype.isSubtype(Compilation.typeLocation)) {
            this.flags |= 2;
        }
        if ((fflags & 16) != 0) {
            if ((this.flags & 2) == 0) {
                this.flags |= 4;
                if (ftype.isSubtype(Compilation.typeProcedure)) {
                    this.flags |= 16;
                }
                if ((ftype instanceof ClassType) && ((ClassType) ftype).isSubclass("kawa.lang.Syntax")) {
                    this.flags |= 32;
                }
            } else {
                Location loc = (Location) getFieldValue();
                if (loc instanceof FieldLocation) {
                    FieldLocation floc = (FieldLocation) loc;
                    if ((floc.flags & 64) == 0) {
                        floc.setKindFlags();
                    }
                    this.flags |= floc.flags & 52;
                    if ((floc.flags & 4) == 0) {
                        this.value = floc;
                        this.flags |= 8;
                    } else if ((floc.flags & 8) != 0) {
                        this.value = floc.value;
                        this.flags |= 8;
                    }
                } else if (loc.isConstant()) {
                    Object val = loc.get(null);
                    if (val instanceof Procedure) {
                        this.flags |= 16;
                    }
                    if (val instanceof Syntax) {
                        this.flags |= 32;
                    }
                    this.flags |= 12;
                    this.value = val;
                }
            }
        }
        this.flags |= 64;
    }

    public boolean isProcedureOrSyntax() {
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        return (this.flags & 48) != 0;
    }

    public FieldLocation(Object instance, String cname, String fname) {
        super(instance, ClassType.make(cname), fname);
    }

    public FieldLocation(Object instance, ClassType type, String mname) {
        super(instance, type, mname);
    }

    public FieldLocation(Object instance, java.lang.reflect.Field field) {
        super(instance, field);
        this.type = (ClassType) Type.make(field.getDeclaringClass());
    }

    public void setDeclaration(Declaration decl) {
        this.decl = decl;
    }

    public Field getField() {
        return this.type.getDeclaredField(this.mname);
    }

    public Type getFType() {
        return this.type.getDeclaredField(this.mname).getType();
    }

    public synchronized Declaration getDeclaration() {
        Declaration declaration;
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        Declaration d = this.decl;
        if (d == null) {
            String fname = getMemberName();
            ClassType t = getDeclaringClass();
            if (t.getDeclaredField(fname) == null) {
                declaration = null;
            } else {
                d = ModuleInfo.find(t).getModuleExp().firstDecl();
                while (d != null && (d.field == null || !d.field.getName().equals(fname))) {
                    d = d.nextDecl();
                }
                if (d == null) {
                    throw new RuntimeException("no field found for " + this);
                }
                this.decl = d;
            }
        }
        declaration = d;
        return declaration;
    }

    void setup() {
        synchronized (this) {
            if ((this.flags & 1) != 0) {
                return;
            }
            super.setup();
            if ((this.flags & 64) == 0) {
                setKindFlags();
            }
            this.flags |= 1;
        }
    }

    public Object get(Object defaultValue) {
        try {
            Location v;
            setup();
            if ((this.flags & 8) != 0) {
                v = this.value;
                if ((this.flags & 4) != 0) {
                    return v;
                }
            }
            v = getFieldValue();
            if ((this.type.getDeclaredField(this.mname).getModifiers() & 16) != 0) {
                this.flags |= 8;
                if ((this.flags & 2) == 0) {
                    this.flags |= 4;
                }
                this.value = v;
            }
            if ((this.flags & 2) == 0) {
                return v;
            }
            String unb = Location.UNBOUND;
            Location loc = v;
            String v2 = loc.get(unb);
            if (v2 == unb) {
                return defaultValue;
            }
            if (!loc.isConstant()) {
                return v2;
            }
            this.flags |= 4;
            this.value = v2;
            return v2;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    private Object getFieldValue() {
        super.setup();
        try {
            return this.rfield.get(this.instance);
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    public void set(Object newValue) {
        setup();
        if ((this.flags & 2) == 0) {
            try {
                this.rfield.set(this.instance, newValue);
            } catch (Throwable ex) {
                RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
            }
        } else {
            Object v;
            if ((this.flags & 8) != 0) {
                v = this.value;
            } else {
                this.flags |= 8;
                v = getFieldValue();
                this.value = v;
            }
            ((Location) v).set(newValue);
        }
    }

    public Object setWithSave(Object newValue) {
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 2) == 0) {
            return super.setWithSave(newValue);
        }
        Object v;
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            this.flags |= 8;
            v = getFieldValue();
            this.value = v;
        }
        return ((Location) v).setWithSave(newValue);
    }

    public void setRestore(Object oldValue) {
        if ((this.flags & 2) == 0) {
            super.setRestore(oldValue);
        } else {
            ((Location) this.value).setRestore(oldValue);
        }
    }

    public boolean isConstant() {
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 4) != 0) {
            return true;
        }
        if (!isIndirectLocation()) {
            return false;
        }
        Object v;
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            try {
                setup();
                v = getFieldValue();
                this.flags |= 8;
                this.value = v;
            } catch (Throwable th) {
                return false;
            }
        }
        return ((Location) v).isConstant();
    }

    public boolean isBound() {
        if ((this.flags & 64) == 0) {
            setKindFlags();
        }
        if ((this.flags & 4) != 0 || (this.flags & 2) == 0) {
            return true;
        }
        Object v;
        if ((this.flags & 8) != 0) {
            v = this.value;
        } else {
            try {
                setup();
                v = getFieldValue();
                this.flags |= 8;
                this.value = v;
            } catch (Throwable th) {
                return false;
            }
        }
        return ((Location) v).isBound();
    }

    public static FieldLocation make(Object instance, Declaration decl) {
        Field fld = decl.field;
        FieldLocation loc = new FieldLocation(instance, fld.getDeclaringClass(), fld.getName());
        loc.setDeclaration(decl);
        return loc;
    }

    public static FieldLocation make(Object instance, String cname, String fldName) {
        return new FieldLocation(instance, ClassType.make(cname), fldName);
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("FieldLocation[");
        if (this.instance != null) {
            sbuf.append(this.instance);
            sbuf.append(' ');
        }
        sbuf.append(this.type == null ? "(null)" : this.type.getName());
        sbuf.append('.');
        sbuf.append(this.mname);
        sbuf.append(']');
        return sbuf.toString();
    }
}
