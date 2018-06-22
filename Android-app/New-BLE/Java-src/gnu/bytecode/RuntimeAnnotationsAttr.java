package gnu.bytecode;

public class RuntimeAnnotationsAttr extends MiscAttr {
    int numEntries = u2(0);

    public RuntimeAnnotationsAttr(String name, byte[] data, AttrContainer container) {
        super(name, data, 0, data.length);
        addToFrontOf(container);
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", number of entries: ");
        dst.println(this.numEntries);
        int saveOffset = this.offset;
        this.offset = saveOffset + 2;
        for (int i = 0; i < this.numEntries; i++) {
            printAnnotation(2, dst);
        }
        this.offset = saveOffset;
    }

    public void printAnnotation(int indentation, ClassTypeWriter dst) {
        int type_index = u2();
        dst.printSpaces(indentation);
        dst.printOptionalIndex(type_index);
        dst.print('@');
        dst.printContantUtf8AsClass(type_index);
        int num_element_value_pairs = u2();
        dst.println();
        indentation += 2;
        for (int i = 0; i < num_element_value_pairs; i++) {
            int element_name_index = u2();
            dst.printSpaces(indentation);
            dst.printOptionalIndex(element_name_index);
            dst.printConstantTersely(element_name_index, 1);
            dst.print(" => ");
            printAnnotationElementValue(indentation, dst);
            dst.println();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void printAnnotationElementValue(int r14, gnu.bytecode.ClassTypeWriter r15) {
        /*
        r13 = this;
        r12 = 1;
        r8 = r13.u1();
        r10 = r15.flags;
        r10 = r10 & 8;
        if (r10 == 0) goto L_0x0021;
    L_0x000b:
        r10 = "[kind:";
        r15.print(r10);
        r10 = 65;
        if (r8 < r10) goto L_0x0026;
    L_0x0014:
        r10 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r8 > r10) goto L_0x0026;
    L_0x0018:
        r10 = (char) r8;
        r15.print(r10);
    L_0x001c:
        r10 = "] ";
        r15.print(r10);
    L_0x0021:
        r5 = 0;
        switch(r8) {
            case 64: goto L_0x00b4;
            case 66: goto L_0x002a;
            case 67: goto L_0x002a;
            case 68: goto L_0x0030;
            case 70: goto L_0x0033;
            case 73: goto L_0x002a;
            case 74: goto L_0x002d;
            case 83: goto L_0x002a;
            case 90: goto L_0x002a;
            case 91: goto L_0x00c3;
            case 99: goto L_0x00a8;
            case 101: goto L_0x006d;
            case 115: goto L_0x0036;
            default: goto L_0x0025;
        };
    L_0x0025:
        return;
    L_0x0026:
        r15.print(r8);
        goto L_0x001c;
    L_0x002a:
        if (r5 != 0) goto L_0x002d;
    L_0x002c:
        r5 = 3;
    L_0x002d:
        if (r5 != 0) goto L_0x0030;
    L_0x002f:
        r5 = 5;
    L_0x0030:
        if (r5 != 0) goto L_0x0033;
    L_0x0032:
        r5 = 6;
    L_0x0033:
        if (r5 != 0) goto L_0x0036;
    L_0x0035:
        r5 = 4;
    L_0x0036:
        if (r5 != 0) goto L_0x0039;
    L_0x0038:
        r5 = 1;
    L_0x0039:
        r3 = r13.u2();
        r4 = r15.getCpoolEntry(r3);
        r15.printOptionalIndex(r4);
        r10 = 90;
        if (r8 != r10) goto L_0x0069;
    L_0x0048:
        if (r4 == 0) goto L_0x0069;
    L_0x004a:
        r10 = r4.getTag();
        r11 = 3;
        if (r10 != r11) goto L_0x0069;
    L_0x0051:
        r0 = r4;
        r0 = (gnu.bytecode.CpoolValue1) r0;
        r10 = r0.value;
        if (r10 == 0) goto L_0x005c;
    L_0x0058:
        r10 = r0.value;
        if (r10 != r12) goto L_0x0069;
    L_0x005c:
        r10 = r0.value;
        if (r10 != 0) goto L_0x0066;
    L_0x0060:
        r10 = "false";
    L_0x0062:
        r15.print(r10);
        goto L_0x0025;
    L_0x0066:
        r10 = "true";
        goto L_0x0062;
    L_0x0069:
        r15.printConstantTersely(r3, r5);
        goto L_0x0025;
    L_0x006d:
        r9 = r13.u2();
        r2 = r13.u2();
        r10 = "enum[";
        r15.print(r10);
        r10 = r15.flags;
        r10 = r10 & 8;
        if (r10 == 0) goto L_0x0085;
    L_0x0080:
        r10 = "type:";
        r15.print(r10);
    L_0x0085:
        r15.printOptionalIndex(r9);
        r15.printContantUtf8AsClass(r9);
        r10 = r15.flags;
        r10 = r10 & 8;
        if (r10 == 0) goto L_0x00a2;
    L_0x0091:
        r10 = " value:";
        r15.print(r10);
    L_0x0096:
        r15.printOptionalIndex(r2);
        r15.printConstantTersely(r2, r12);
        r10 = "]";
        r15.print(r10);
        goto L_0x0025;
    L_0x00a2:
        r10 = 32;
        r15.print(r10);
        goto L_0x0096;
    L_0x00a8:
        r1 = r13.u2();
        r15.printOptionalIndex(r1);
        r15.printContantUtf8AsClass(r1);
        goto L_0x0025;
    L_0x00b4:
        r15.println();
        r10 = r14 + 2;
        r15.printSpaces(r10);
        r10 = r14 + 2;
        r13.printAnnotation(r10, r15);
        goto L_0x0025;
    L_0x00c3:
        r7 = r13.u2();
        r10 = "array length:";
        r15.print(r10);
        r15.print(r7);
        r6 = 0;
    L_0x00d0:
        if (r6 >= r7) goto L_0x0025;
    L_0x00d2:
        r15.println();
        r10 = r14 + 2;
        r15.printSpaces(r10);
        r15.print(r6);
        r10 = ": ";
        r15.print(r10);
        r10 = r14 + 2;
        r13.printAnnotationElementValue(r10, r15);
        r6 = r6 + 1;
        goto L_0x00d0;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.RuntimeAnnotationsAttr.printAnnotationElementValue(int, gnu.bytecode.ClassTypeWriter):void");
    }
}
