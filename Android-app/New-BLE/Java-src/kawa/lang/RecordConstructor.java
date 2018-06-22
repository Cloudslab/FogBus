package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;

public class RecordConstructor extends ProcedureN {
    Field[] fields;
    ClassType type;

    public RecordConstructor(ClassType type, Field[] fields) {
        this.type = type;
        this.fields = fields;
    }

    public RecordConstructor(Class clas, Field[] fields) {
        this((ClassType) Type.make(clas), fields);
    }

    public RecordConstructor(Class clas) {
        init((ClassType) Type.make(clas));
    }

    public RecordConstructor(ClassType type) {
        init(type);
    }

    private void init(ClassType type) {
        Field fld;
        this.type = type;
        Field list = type.getFields();
        int count = 0;
        for (fld = list; fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                count++;
            }
        }
        this.fields = new Field[count];
        fld = list;
        int i = 0;
        while (fld != null) {
            int i2;
            if ((fld.getModifiers() & 9) == 1) {
                i2 = i + 1;
                this.fields[i] = fld;
            } else {
                i2 = i;
            }
            fld = fld.getNext();
            i = i2;
        }
    }

    public RecordConstructor(Class clas, Object fieldsList) {
        this((ClassType) Type.make(clas), fieldsList);
    }

    public RecordConstructor(ClassType type, Object fieldsList) {
        this.type = type;
        if (fieldsList == null) {
            init(type);
            return;
        }
        int nfields = LList.listLength(fieldsList, false);
        this.fields = new Field[nfields];
        Field list = type.getFields();
        int i = 0;
        while (i < nfields) {
            Pair fieldsList2;
            Pair pair = fieldsList2;
            String fname = pair.getCar().toString();
            Field fld = list;
            while (fld != null) {
                if (fld.getSourceName() == fname) {
                    this.fields[i] = fld;
                    fieldsList2 = pair.getCdr();
                    i++;
                } else {
                    fld = fld.getNext();
                }
            }
            throw new RuntimeException("no such field " + fname + " in " + type.getName());
        }
    }

    public int numArgs() {
        int nargs = this.fields.length;
        return (nargs << 12) | nargs;
    }

    public String getName() {
        return this.type.getName() + " constructor";
    }

    public Object applyN(Object[] args) {
        try {
            Object obj = this.type.getReflectClass().newInstance();
            if (args.length != this.fields.length) {
                throw new WrongArguments(this, args.length);
            }
            int i = 0;
            while (i < args.length) {
                Field fld = this.fields[i];
                try {
                    fld.getReflectField().set(obj, args[i]);
                    i++;
                } catch (Exception ex) {
                    throw new WrappedException("illegal access for field " + fld.getName(), ex);
                }
            }
            return obj;
        } catch (InstantiationException ex2) {
            throw new GenericError(ex2.toString());
        } catch (IllegalAccessException ex3) {
            throw new GenericError(ex3.toString());
        }
    }
}
