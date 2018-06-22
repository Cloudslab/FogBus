package gnu.bytecode;

import java.util.List;
import java.util.Vector;

public class ObjectType extends Type {
    static final int ADD_ENCLOSING_DONE = 8;
    static final int ADD_FIELDS_DONE = 1;
    static final int ADD_MEMBERCLASSES_DONE = 4;
    static final int ADD_METHODS_DONE = 2;
    static final int EXISTING_CLASS = 16;
    static final int HAS_OUTER_LINK = 32;
    public int flags;

    protected ObjectType() {
        this.size = 4;
    }

    public ObjectType(String name) {
        this.this_name = name;
        this.size = 4;
    }

    public final boolean isExisting() {
        boolean z = false;
        Type t = getImplementationType();
        if (t instanceof ArrayType) {
            t = ((ArrayType) t).getComponentType();
        }
        if (t != this) {
            if (!(t instanceof ObjectType) || ((ObjectType) t).isExisting()) {
                z = true;
            }
            return z;
        } else if ((this.flags & 16) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public final void setExisting(boolean existing) {
        if (existing) {
            this.flags |= 16;
        } else {
            this.flags &= -17;
        }
    }

    public String getInternalName() {
        return getName().replace('.', '/');
    }

    public static Class getContextClass(String cname) throws ClassNotFoundException {
        try {
            return Class.forName(cname, false, ObjectType.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(cname, false, getThreadContextClassLoader());
            } catch (ClassNotFoundException e2) {
                return Class.forName(cname, false, getContextClassLoader());
            }
        }
    }

    public static ClassLoader getThreadContextClassLoader() {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (SecurityException e) {
            return ObjectType.class.getClassLoader();
        }
    }

    public static ClassLoader getContextClassLoader() {
        try {
            return ClassLoader.getSystemClassLoader();
        } catch (SecurityException e) {
            return ObjectType.class.getClassLoader();
        }
    }

    public Class getReflectClass() {
        try {
            if (this.reflectClass == null) {
                this.reflectClass = getContextClass(getInternalName().replace('/', '.'));
            }
            this.flags |= 16;
        } catch (ClassNotFoundException ex) {
            if ((this.flags & 16) != 0) {
                RuntimeException rex = new RuntimeException("no such class: " + getName());
                rex.initCause(ex);
                throw rex;
            }
        }
        return this.reflectClass;
    }

    public Type getImplementationType() {
        if (this == nullType) {
            return objectType;
        }
        return this == toStringType ? javalangStringType : this;
    }

    public Type promote() {
        return this == nullType ? objectType : this;
    }

    public boolean isInstance(Object obj) {
        if (this == nullType) {
            return obj == null;
        } else {
            return super.isInstance(obj);
        }
    }

    public Field getField(String name, int mask) {
        return null;
    }

    public Method getMethod(String name, Type[] arg_types) {
        return Type.objectType.getMethod(name, arg_types);
    }

    public final int getMethods(Filter filter, int searchSupers, Vector result, String context) {
        return Type.objectType.getMethods(filter, searchSupers, result, context);
    }

    public int getMethods(Filter filter, int searchSupers, List<Method> result) {
        return Type.objectType.getMethods(filter, searchSupers, result);
    }

    public int compare(Type other) {
        return other == nullType ? 0 : -1;
    }

    public Object coerceFromObject(Object obj) {
        if (obj == null) {
            return obj;
        }
        if (this == Type.toStringType) {
            return obj.toString();
        }
        Class clas = getReflectClass();
        Class objClass = obj.getClass();
        if (clas.isAssignableFrom(objClass)) {
            return obj;
        }
        throw new ClassCastException("don't know how to coerce " + objClass.getName() + " to " + getName());
    }

    public void emitCoerceFromObject(CodeAttr code) {
        if (this == Type.toStringType) {
            code.emitDup();
            code.emitIfNull();
            code.emitPop(1);
            code.emitPushNull();
            code.emitElse();
            code.emitInvokeVirtual(Type.toString_method);
            code.emitFi();
        } else if (this != Type.objectType) {
            code.emitCheckcast(this);
        }
    }
}
