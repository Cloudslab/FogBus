package kawa.lang;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Vector;

public class Record {
    public String getTypeName() {
        return getClass().getName();
    }

    public static boolean isRecord(Object obj) {
        return obj instanceof Record;
    }

    public int hashCode() {
        Field[] fields = getClass().getFields();
        int hash = 12345;
        for (Field field : fields) {
            try {
                Object value = field.get(this);
                if (value != null) {
                    hash ^= value.hashCode();
                }
            } catch (IllegalAccessException e) {
            }
        }
        return hash;
    }

    static Field getField(Class clas, String fname) throws NoSuchFieldException {
        gnu.bytecode.Field fld = ((ClassType) Type.make(clas)).getFields();
        while (fld != null) {
            if ((fld.getModifiers() & 9) == 1 && fld.getSourceName().equals(fname)) {
                return fld.getReflectField();
            }
            fld = fld.getNext();
        }
        throw new NoSuchFieldException();
    }

    public Object get(String fname, Object defaultValue) {
        Class clas = getClass();
        try {
            return getField(clas, fname).get(this);
        } catch (NoSuchFieldException e) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        } catch (IllegalAccessException e2) {
            throw new GenericError("illegal access for field " + fname);
        }
    }

    public Object put(String fname, Object value) {
        return set1(this, fname, value);
    }

    public static Object set1(Object record, String fname, Object value) {
        Class clas = record.getClass();
        try {
            Field fld = getField(clas, fname);
            Object old = fld.get(record);
            fld.set(record, value);
            return old;
        } catch (NoSuchFieldException e) {
            throw new GenericError("no such field " + fname + " in " + clas.getName());
        } catch (IllegalAccessException e2) {
            throw new GenericError("illegal access for field " + fname);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Class thisClass = getClass();
        if (obj == null || obj.getClass() != thisClass) {
            return false;
        }
        for (gnu.bytecode.Field fld = ((ClassType) Type.make(thisClass)).getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                try {
                    Field field = fld.getReflectField();
                    if (!field.get(this).equals(field.get(obj))) {
                        return false;
                    }
                } catch (Throwable ex) {
                    throw new WrappedException(ex);
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(HttpRequestContext.HTTP_OK);
        buf.append("#<");
        buf.append(getTypeName());
        for (gnu.bytecode.Field fld = ((ClassType) Type.make(getClass())).getFields(); fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                Object obj;
                try {
                    obj = fld.getReflectField().get(this);
                } catch (Exception e) {
                    obj = "#<illegal-access>";
                }
                buf.append(' ');
                buf.append(fld.getSourceName());
                buf.append(": ");
                buf.append(obj);
            }
        }
        buf.append(">");
        return buf.toString();
    }

    public void print(PrintWriter ps) {
        ps.print(toString());
    }

    public static ClassType makeRecordType(String name, LList fnames) {
        ClassType superClass = ClassType.make("kawa.lang.Record");
        String mangledName = Compilation.mangleNameIfNeeded(name);
        ClassType clas = new ClassType(mangledName);
        clas.setSuper(superClass);
        clas.setModifiers(33);
        Method constructor = clas.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
        Method superConstructor = superClass.addMethod("<init>", Type.typeArray0, Type.voidType, 1);
        CodeAttr code = constructor.startCode();
        code.emitPushThis();
        code.emitInvokeSpecial(superConstructor);
        code.emitReturn();
        if (!name.equals(mangledName)) {
            code = clas.addMethod("getTypeName", Type.typeArray0, Compilation.typeString, 1).startCode();
            code.emitPushString(name);
            code.emitReturn();
        }
        while (fnames != LList.Empty) {
            Pair pair = (Pair) fnames;
            String fname = pair.getCar().toString();
            clas.addField(Compilation.mangleNameIfNeeded(fname), Type.pointer_type, 1).setSourceName(fname.intern());
            fnames = (LList) pair.getCdr();
        }
        byte[][] arrays = new byte[1][];
        String[] names = new String[]{mangledName};
        arrays[0] = clas.writeToArray();
        try {
            Type.registerTypeForClass(new ArrayClassLoader(names, arrays).loadClass(mangledName), clas);
            return clas;
        } catch (ClassNotFoundException ex) {
            throw new InternalError(ex.toString());
        }
    }

    public static LList typeFieldNames(Class clas) {
        LList list = LList.Empty;
        Vector vec = new Vector(100);
        for (gnu.bytecode.Field field = ((ClassType) Type.make(clas)).getFields(); field != null; field = field.getNext()) {
            if ((field.getModifiers() & 9) == 1) {
                vec.addElement(Symbol.valueOf(field.getSourceName()));
            }
        }
        int i = vec.size();
        LList list2 = list;
        while (true) {
            i--;
            if (i < 0) {
                return list2;
            }
            list2 = new Pair(vec.elementAt(i), list2);
        }
    }

    public static LList typeFieldNames(ClassType ctype) {
        return typeFieldNames(ctype.getReflectClass());
    }
}
