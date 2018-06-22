package gnu.bytecode;

import android.support.v4.internal.view.SupportMenu;
import java.io.DataOutputStream;
import java.io.IOException;

public class InnerClassesAttr extends Attribute {
    int count;
    short[] data;

    public InnerClassesAttr(ClassType cl) {
        super("InnerClasses");
        addToFrontOf(cl);
    }

    public InnerClassesAttr(short[] data, ClassType cl) {
        this(cl);
        this.count = (short) (data.length >> 2);
        this.data = data;
    }

    public static InnerClassesAttr getFirstInnerClasses(Attribute attr) {
        while (attr != null && !(attr instanceof InnerClassesAttr)) {
            attr = attr.next;
        }
        return (InnerClassesAttr) attr;
    }

    void addClass(CpoolClass centry, ClassType owner) {
        short s = (short) 0;
        int i = this.count;
        this.count = i + 1;
        int i2 = i * 4;
        if (this.data == null) {
            this.data = new short[16];
        } else if (i2 >= this.data.length) {
            short[] tmp = new short[(i2 * 2)];
            System.arraycopy(this.data, 0, tmp, 0, i2);
            this.data = tmp;
        }
        ConstantPool constants = owner.constants;
        ClassType clas = (ClassType) centry.getClassType();
        String name = clas.getSimpleName();
        int name_index = (name == null || name.length() == 0) ? 0 : constants.addUtf8(name).index;
        this.data[i2] = (short) centry.index;
        ObjectType outer = clas.getDeclaringClass();
        short[] sArr = this.data;
        int i3 = i2 + 1;
        if (outer != null) {
            s = (short) constants.addClass(outer).index;
        }
        sArr[i3] = s;
        this.data[i2 + 2] = (short) name_index;
        this.data[i2 + 3] = (short) (clas.getModifiers() & -33);
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
    }

    public int getLength() {
        return (this.count * 8) + 2;
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.count);
        for (int i = 0; i < this.count; i++) {
            dstr.writeShort(this.data[i * 4]);
            dstr.writeShort(this.data[(i * 4) + 1]);
            dstr.writeShort(this.data[(i * 4) + 2]);
            dstr.writeShort(this.data[(i * 4) + 3]);
        }
    }

    public void print(ClassTypeWriter dst) {
        ClassType ctype = this.container;
        ConstantPool constants = this.data == null ? null : ctype.getConstants();
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", count: ");
        dst.println(this.count);
        for (int i = 0; i < this.count; i++) {
            int index;
            String name;
            int inner_index = constants == null ? 0 : this.data[i * 4] & SupportMenu.USER_MASK;
            CpoolClass centry = (constants == null || inner_index == 0) ? null : constants.getForcedClass(inner_index);
            ClassType clas = (centry == null || !(centry.clas instanceof ClassType)) ? null : (ClassType) centry.clas;
            dst.print(' ');
            int access = (inner_index != 0 || clas == null) ? this.data[(i * 4) + 3] & SupportMenu.USER_MASK : clas.getModifiers();
            dst.print(Access.toString(access, Access.INNERCLASS_CONTEXT));
            dst.print(' ');
            if (inner_index != 0 || clas == null) {
                index = this.data[(i * 4) + 2] & SupportMenu.USER_MASK;
                if (constants == null || index == 0) {
                    name = "(Anonymous)";
                } else {
                    dst.printOptionalIndex(index);
                    name = ((CpoolUtf8) constants.getForced(index, 1)).string;
                }
            } else {
                name = clas.getSimpleName();
            }
            dst.print(name);
            dst.print(" = ");
            if (centry != null) {
                name = centry.getClassName();
            } else {
                name = "(Unknown)";
            }
            dst.print(name);
            dst.print("; ");
            if (inner_index != 0 || clas == null) {
                index = this.data[(i * 4) + 1] & SupportMenu.USER_MASK;
                if (index == 0) {
                    dst.print("not a member");
                } else {
                    dst.print("member of ");
                    dst.print(((CpoolClass) constants.getForced(index, 7)).getStringName());
                }
            } else {
                String iname = clas.getName();
                int dot = iname.lastIndexOf(46);
                if (dot > 0) {
                    iname = iname.substring(dot + 1);
                }
                int start = iname.lastIndexOf(36) + 1;
                if (start < iname.length()) {
                    char ch = iname.charAt(start);
                    if (ch >= '0' && ch <= '9') {
                        dst.print("not a member");
                    }
                }
                dst.print("member of ");
                dst.print(ctype.getName());
            }
            dst.println();
        }
    }
}
