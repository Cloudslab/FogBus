package gnu.bytecode;

import android.support.v4.internal.view.SupportMenu;
import java.io.DataOutputStream;
import java.io.IOException;

public class ExceptionsAttr extends Attribute {
    short[] exception_table;
    ClassType[] exceptions;

    public ExceptionsAttr(Method meth) {
        super("Exceptions");
        addToFrontOf(meth);
    }

    public void setExceptions(short[] indices, ClassType cl) {
        this.exception_table = indices;
        this.exceptions = new ClassType[indices.length];
        ConstantPool cp = cl.getConstants();
        for (int i = indices.length - 1; i >= 0; i--) {
            this.exceptions[i] = (ClassType) ((CpoolClass) cp.getPoolEntry(indices[i])).getClassType();
        }
    }

    public void setExceptions(ClassType[] excep_types) {
        this.exceptions = excep_types;
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        ConstantPool cp = cl.getConstants();
        int count = this.exceptions.length;
        this.exception_table = new short[count];
        for (int i = count - 1; i >= 0; i--) {
            this.exception_table[i] = (short) cp.addClass(this.exceptions[i]).index;
        }
    }

    public final int getLength() {
        return ((this.exceptions == null ? 0 : this.exceptions.length) * 2) + 2;
    }

    public final ClassType[] getExceptions() {
        return this.exceptions;
    }

    public void write(DataOutputStream dstr) throws IOException {
        int count = this.exceptions.length;
        dstr.writeShort(count);
        for (int i = 0; i < count; i++) {
            dstr.writeShort(this.exception_table[i]);
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", count: ");
        int count = this.exceptions.length;
        dst.println(count);
        for (int i = 0; i < count; i++) {
            int catch_type_index = this.exception_table[i] & SupportMenu.USER_MASK;
            dst.print("  ");
            dst.printOptionalIndex(catch_type_index);
            dst.printConstantTersely(catch_type_index, 7);
            dst.println();
        }
    }
}
