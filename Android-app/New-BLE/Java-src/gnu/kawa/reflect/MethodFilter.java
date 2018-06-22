package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Filter;
import gnu.bytecode.ObjectType;

/* compiled from: ClassMethods */
class MethodFilter implements Filter {
    ClassType caller;
    int modifiers;
    int modmask;
    String name;
    int nlen;
    ObjectType receiver;

    public MethodFilter(String name, int modifiers, int modmask, ClassType caller, ObjectType receiver) {
        this.name = name;
        this.nlen = name.length();
        this.modifiers = modifiers;
        this.modmask = modmask;
        this.caller = caller;
        this.receiver = receiver;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean select(java.lang.Object r11) {
        /*
        r10 = this;
        r7 = 0;
        r2 = r11;
        r2 = (gnu.bytecode.Method) r2;
        r5 = r2.getName();
        r4 = r2.getModifiers();
        r6 = r10.modmask;
        r6 = r6 & r4;
        r8 = r10.modifiers;
        if (r6 != r8) goto L_0x001f;
    L_0x0013:
        r6 = r4 & 4096;
        if (r6 != 0) goto L_0x001f;
    L_0x0017:
        r6 = r10.name;
        r6 = r5.startsWith(r6);
        if (r6 != 0) goto L_0x0020;
    L_0x001f:
        return r7;
    L_0x0020:
        r3 = r5.length();
        r6 = r10.nlen;
        if (r3 == r6) goto L_0x0056;
    L_0x0028:
        r6 = r10.nlen;
        r6 = r6 + 2;
        if (r3 != r6) goto L_0x0048;
    L_0x002e:
        r6 = r10.nlen;
        r6 = r5.charAt(r6);
        r8 = 36;
        if (r6 != r8) goto L_0x0048;
    L_0x0038:
        r6 = r10.nlen;
        r6 = r6 + 1;
        r0 = r5.charAt(r6);
        r6 = 86;
        if (r0 == r6) goto L_0x0056;
    L_0x0044:
        r6 = 88;
        if (r0 == r6) goto L_0x0056;
    L_0x0048:
        r6 = r10.nlen;
        r6 = r6 + 4;
        if (r3 != r6) goto L_0x001f;
    L_0x004e:
        r6 = "$V$X";
        r6 = r5.endsWith(r6);
        if (r6 == 0) goto L_0x001f;
    L_0x0056:
        r6 = r10.receiver;
        r6 = r6 instanceof gnu.bytecode.ClassType;
        if (r6 == 0) goto L_0x0076;
    L_0x005c:
        r6 = r10.receiver;
        r6 = (gnu.bytecode.ClassType) r6;
        r1 = r6;
    L_0x0061:
        r6 = r10.caller;
        if (r6 == 0) goto L_0x0073;
    L_0x0065:
        r6 = r10.caller;
        r8 = r10.receiver;
        r9 = r2.getModifiers();
        r6 = r6.isAccessible(r1, r8, r9);
        if (r6 == 0) goto L_0x007b;
    L_0x0073:
        r6 = 1;
    L_0x0074:
        r7 = r6;
        goto L_0x001f;
    L_0x0076:
        r1 = r2.getDeclaringClass();
        goto L_0x0061;
    L_0x007b:
        r6 = r7;
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.MethodFilter.select(java.lang.Object):boolean");
    }
}
