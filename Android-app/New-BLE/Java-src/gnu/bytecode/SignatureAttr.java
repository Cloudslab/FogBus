package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public class SignatureAttr extends Attribute {
    String signature;
    int signature_index;

    public final String getSignature() {
        return this.signature;
    }

    protected void setSignature(String sig) {
        this.signature = sig;
        this.signature_index = 0;
    }

    public SignatureAttr(String signature) {
        super("Signature");
        this.signature = signature;
    }

    public SignatureAttr(int index, Member owner) {
        super("Signature");
        this.signature = ((CpoolUtf8) (owner instanceof ClassType ? (ClassType) owner : owner.getDeclaringClass()).constants.getForced(index, 1)).string;
        this.signature_index = index;
    }

    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
        if (this.signature_index == 0) {
            this.signature_index = cl.getConstants().addUtf8(this.signature).getIndex();
        }
    }

    public final int getLength() {
        return 2;
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.signature_index);
    }

    public void print(ClassTypeWriter dst) {
        super.print(dst);
        dst.print("  ");
        dst.printOptionalIndex(this.signature_index);
        dst.print('\"');
        dst.print(getSignature());
        dst.println('\"');
    }
}
