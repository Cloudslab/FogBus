package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConstantValueAttr extends Attribute {
    Object value;
    int value_index;

    public Object getValue(ConstantPool cpool) {
        if (this.value != null) {
            return this.value;
        }
        CpoolEntry entry = cpool.getPoolEntry(this.value_index);
        switch (entry.getTag()) {
            case 3:
                this.value = new Integer(((CpoolValue1) entry).value);
                break;
            case 4:
                this.value = new Float(Float.intBitsToFloat(((CpoolValue1) entry).value));
                break;
            case 5:
                this.value = new Long(((CpoolValue2) entry).value);
                break;
            case 6:
                this.value = new Double(Double.longBitsToDouble(((CpoolValue2) entry).value));
                break;
            case 8:
                this.value = ((CpoolString) entry).getString().getString();
                break;
        }
        return this.value;
    }

    public ConstantValueAttr(Object value) {
        super("ConstantValue");
        this.value = value;
    }

    public ConstantValueAttr(int index) {
        super("ConstantValue");
        this.value_index = index;
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        if (this.value_index == 0) {
            ConstantPool cpool = cl.getConstants();
            CpoolEntry entry = null;
            if (this.value instanceof String) {
                entry = cpool.addString((String) this.value);
            } else if (this.value instanceof Integer) {
                entry = cpool.addInt(((Integer) this.value).intValue());
            } else if (this.value instanceof Long) {
                entry = cpool.addLong(((Long) this.value).longValue());
            } else if (this.value instanceof Float) {
                entry = cpool.addFloat(((Float) this.value).floatValue());
            } else if (this.value instanceof Long) {
                entry = cpool.addDouble(((Double) this.value).doubleValue());
            }
            this.value_index = entry.getIndex();
        }
    }

    public final int getLength() {
        return 2;
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.value_index);
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", value: ");
        if (this.value_index == 0) {
            Object value = getValue(dst.ctype.constants);
            if (value instanceof String) {
                dst.printQuotedString((String) value);
            } else {
                dst.print(value);
            }
        } else {
            dst.printOptionalIndex(this.value_index);
            dst.ctype.constants.getPoolEntry(this.value_index).print(dst, 1);
        }
        dst.println();
    }
}
