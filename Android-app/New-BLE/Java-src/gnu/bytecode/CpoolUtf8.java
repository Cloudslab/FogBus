package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolUtf8 extends CpoolEntry {
    String string;

    CpoolUtf8() {
    }

    CpoolUtf8(ConstantPool cpool, int h, String s) {
        super(cpool, h);
        this.string = s;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = this.string.hashCode();
        }
        return this.hash;
    }

    public final void intern() {
        this.string = this.string.intern();
    }

    public int getTag() {
        return 1;
    }

    public final String getString() {
        return this.string;
    }

    void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(1);
        dstr.writeUTF(this.string);
    }

    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity > 0) {
            dst.print("Utf8: ");
        }
        dst.printQuotedString(this.string);
    }
}
