package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Attribute {
    AttrContainer container;
    String name;
    int name_index;
    Attribute next;

    public abstract int getLength();

    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public final AttrContainer getContainer() {
        return this.container;
    }

    public final void setContainer(AttrContainer container) {
        this.container = container;
    }

    public final Attribute getNext() {
        return this.next;
    }

    public final void setNext(Attribute next) {
        this.next = next;
    }

    public void addToFrontOf(AttrContainer container) {
        setContainer(container);
        setNext(container.getAttributes());
        container.setAttributes(this);
    }

    public final boolean isSkipped() {
        return this.name_index < 0;
    }

    public final void setSkipped(boolean skip) {
        this.name_index = skip ? -1 : 0;
    }

    public final void setSkipped() {
        this.name_index = -1;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name.intern();
    }

    public final int getNameIndex() {
        return this.name_index;
    }

    public final void setNameIndex(int index) {
        this.name_index = index;
    }

    public Attribute(String name) {
        this.name = name;
    }

    public static Attribute get(AttrContainer container, String name) {
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            if (attr.getName() == name) {
                return attr;
            }
        }
        return null;
    }

    public void assignConstants(ClassType cl) {
        if (this.name_index == 0) {
            this.name_index = cl.getConstants().addUtf8(this.name).getIndex();
        }
    }

    public static void assignConstants(AttrContainer container, ClassType cl) {
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            if (!attr.isSkipped()) {
                attr.assignConstants(cl);
            }
        }
    }

    public static int getLengthAll(AttrContainer container) {
        int length = 0;
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            if (!attr.isSkipped()) {
                length += attr.getLength() + 6;
            }
        }
        return length;
    }

    public static int count(AttrContainer container) {
        int count = 0;
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            if (!attr.isSkipped()) {
                count++;
            }
        }
        return count;
    }

    public static void writeAll(AttrContainer container, DataOutputStream dstr) throws IOException {
        dstr.writeShort(count(container));
        for (Attribute attr = container.getAttributes(); attr != null; attr = attr.next) {
            if (!attr.isSkipped()) {
                if (attr.name_index == 0) {
                    throw new Error("Attribute.writeAll called without assignConstants");
                }
                dstr.writeShort(attr.name_index);
                dstr.writeInt(attr.getLength());
                attr.write(dstr);
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.println(getLength());
    }
}
