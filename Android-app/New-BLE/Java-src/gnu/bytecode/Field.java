package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class Field extends Location implements AttrContainer, Member {
    Attribute attributes;
    int flags;
    Field next;
    ClassType owner;
    java.lang.reflect.Field rfield;
    String sourceName;

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    public Field(ClassType ctype) {
        if (ctype.last_field == null) {
            ctype.fields = this;
        } else {
            ctype.last_field.next = this;
        }
        ctype.last_field = this;
        ctype.fields_count++;
        this.owner = ctype;
    }

    public final ClassType getDeclaringClass() {
        return this.owner;
    }

    public final void setStaticFlag(boolean is_static) {
        if (is_static) {
            this.flags |= 8;
        } else {
            this.flags ^= -9;
        }
    }

    public final boolean getStaticFlag() {
        return (this.flags & 8) != 0;
    }

    public final int getFlags() {
        return this.flags;
    }

    public final int getModifiers() {
        return this.flags;
    }

    void write(DataOutputStream dstr, ClassType classfile) throws IOException {
        dstr.writeShort(this.flags);
        dstr.writeShort(this.name_index);
        dstr.writeShort(this.signature_index);
        Attribute.writeAll(this, dstr);
    }

    void assign_constants(ClassType classfile) {
        ConstantPool constants = classfile.constants;
        if (this.name_index == 0 && this.name != null) {
            this.name_index = constants.addUtf8(this.name).index;
        }
        if (this.signature_index == 0 && this.type != null) {
            this.signature_index = constants.addUtf8(this.type.getSignature()).index;
        }
        Attribute.assignConstants(this, classfile);
    }

    public synchronized java.lang.reflect.Field getReflectField() throws NoSuchFieldException {
        if (this.rfield == null) {
            this.rfield = this.owner.getReflectClass().getDeclaredField(getName());
        }
        return this.rfield;
    }

    public void setSourceName(String name) {
        this.sourceName = name;
    }

    public String getSourceName() {
        if (this.sourceName == null) {
            this.sourceName = getName().intern();
        }
        return this.sourceName;
    }

    public static Field searchField(Field fields, String name) {
        while (fields != null) {
            if (fields.getSourceName() == name) {
                return fields;
            }
            fields = fields.next;
        }
        return null;
    }

    public final Field getNext() {
        return this.next;
    }

    public final void setConstantValue(Object value, ClassType ctype) {
        CpoolEntry entry;
        int i = 0;
        ConstantPool cpool = ctype.constants;
        if (cpool == null) {
            cpool = new ConstantPool();
            ctype.constants = cpool;
        }
        switch (getType().getSignature().charAt(0)) {
            case 'C':
                if (value instanceof Character) {
                    entry = cpool.addInt(((Character) value).charValue());
                    break;
                }
            case 'B':
            case 'I':
            case 'S':
                entry = cpool.addInt(((Number) value).intValue());
                break;
            case 'D':
                entry = cpool.addDouble(((Number) value).doubleValue());
                break;
            case 'F':
                entry = cpool.addFloat(((Number) value).floatValue());
                break;
            case 'J':
                entry = cpool.addLong(((Number) value).longValue());
                break;
            case 'Z':
                if (PrimType.booleanValue(value)) {
                    i = 1;
                }
                entry = cpool.addInt(i);
                break;
            default:
                entry = cpool.addString(value.toString());
                break;
        }
        new ConstantValueAttr(entry.getIndex()).addToFrontOf(this);
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append("Field:");
        sbuf.append(getDeclaringClass().getName());
        sbuf.append('.');
        sbuf.append(this.name);
        return sbuf.toString();
    }
}
