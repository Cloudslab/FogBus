package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class EnclosingMethodAttr extends Attribute {
    int class_index;
    Method method;
    int method_index;

    public EnclosingMethodAttr(ClassType cl) {
        super("EnclosingMethod");
        addToFrontOf(cl);
    }

    public EnclosingMethodAttr(int class_index, int method_index, ClassType ctype) {
        this(ctype);
        this.class_index = class_index;
        this.method_index = method_index;
    }

    public static EnclosingMethodAttr getFirstEnclosingMethod(Attribute attr) {
        while (attr != null && !(attr instanceof EnclosingMethodAttr)) {
            attr = attr.next;
        }
        return (EnclosingMethodAttr) attr;
    }

    public int getLength() {
        return 4;
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        if (this.method != null) {
            ConstantPool constants = cl.getConstants();
            this.class_index = constants.addClass(this.method.getDeclaringClass()).getIndex();
            this.method_index = constants.addNameAndType(this.method).getIndex();
        }
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.class_index);
        dstr.writeShort(this.method_index);
    }

    public void print(ClassTypeWriter dst) {
        ConstantPool constants = this.container.getConstants();
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.println(getLength());
        dst.print("  class: ");
        dst.printOptionalIndex(this.class_index);
        dst.print(((CpoolClass) constants.getForced(this.class_index, 7)).getStringName());
        dst.print(", method: ");
        dst.printOptionalIndex(this.method_index);
        constants.getForced(this.method_index, 12).print(dst, 0);
        dst.println();
    }
}
