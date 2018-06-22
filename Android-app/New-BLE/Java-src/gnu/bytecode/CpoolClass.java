package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class CpoolClass extends CpoolEntry {
    ObjectType clas;
    CpoolUtf8 name;

    CpoolClass() {
    }

    CpoolClass(ConstantPool cpool, int hash, CpoolUtf8 n) {
        super(cpool, hash);
        this.name = n;
    }

    public int getTag() {
        return 7;
    }

    public final CpoolUtf8 getName() {
        return this.name;
    }

    public final String getStringName() {
        return this.name.string;
    }

    public final String getClassName() {
        return this.name.string.replace('/', '.');
    }

    public final ObjectType getClassType() {
        ObjectType otype = this.clas;
        if (otype != null) {
            return otype;
        }
        String name = this.name.string;
        if (name.charAt(0) == '[') {
            otype = (ObjectType) Type.signatureToType(name);
        } else {
            otype = ClassType.make(name.replace('/', '.'));
        }
        this.clas = otype;
        return otype;
    }

    static final int hashCode(CpoolUtf8 name) {
        return name.hashCode() ^ 3855;
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.name);
        }
        return this.hash;
    }

    void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(7);
        dstr.writeShort(this.name.index);
    }

    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity == 1) {
            dst.print("Class ");
        } else if (verbosity > 1) {
            dst.print("Class name: ");
            dst.printOptionalIndex(this.name);
        }
        String str = this.name.string;
        int nlen = str.length();
        if (nlen <= 1 || str.charAt(0) != '[') {
            dst.print(str.replace('/', '.'));
        } else {
            Type.printSignature(str, 0, nlen, dst);
        }
    }
}
