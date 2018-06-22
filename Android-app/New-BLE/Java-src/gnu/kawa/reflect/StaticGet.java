package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure0;

public class StaticGet extends Procedure0 implements Inlineable {
    ClassType ctype;
    Field field;
    String fname;
    java.lang.reflect.Field reflectField;

    StaticGet(Class clas, String fname) {
        this.ctype = (ClassType) Type.make(clas);
        this.fname = fname;
    }

    public StaticGet(ClassType ctype, String name, Type ftype, int flags) {
        this.ctype = ctype;
        this.fname = name;
        this.field = ctype.getField(name);
        if (this.field == null) {
            this.field = ctype.addField(name, ftype, flags);
        }
    }

    public Object apply0() {
        if (this.reflectField == null) {
            Class clas = this.ctype.getReflectClass();
            try {
                this.reflectField = clas.getField(this.fname);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("no such field " + this.fname + " in " + clas.getName());
            }
        }
        try {
            return this.reflectField.get(null);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("illegal access for field " + this.fname);
        }
    }

    private Field getField() {
        if (this.field == null) {
            this.field = this.ctype.getField(this.fname);
            if (this.field == null) {
                this.field = this.ctype.addField(this.fname, Type.make(this.reflectField.getType()), this.reflectField.getModifiers());
            }
        }
        return this.field;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        getField();
        comp.getCode().emitGetStatic(this.field);
        target.compileFromStack(comp, this.field.getType());
    }

    public Type getReturnType(Expression[] args) {
        return getField().getType();
    }
}
