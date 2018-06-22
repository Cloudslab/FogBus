package gnu.bytecode;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import gnu.math.DateTime;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CodeAttr extends Attribute implements AttrContainer {
    public static final int DONT_USE_JSR = 2;
    static final int FIXUP_CASE = 3;
    static final int FIXUP_DEFINE = 1;
    static final int FIXUP_DELETE3 = 8;
    static final int FIXUP_GOTO = 4;
    static final int FIXUP_JSR = 5;
    static final int FIXUP_LINE_NUMBER = 15;
    static final int FIXUP_LINE_PC = 14;
    static final int FIXUP_MOVE = 9;
    static final int FIXUP_MOVE_TO_END = 10;
    static final int FIXUP_NONE = 0;
    static final int FIXUP_SWITCH = 2;
    static final int FIXUP_TRANSFER = 6;
    static final int FIXUP_TRANSFER2 = 7;
    static final int FIXUP_TRY = 11;
    static final int FIXUP_TRY_END = 12;
    static final int FIXUP_TRY_HANDLER = 13;
    public static final int GENERATE_STACK_MAP_TABLE = 1;
    public static boolean instructionLineMode = false;
    int PC;
    int SP;
    Attribute attributes;
    byte[] code;
    ExitableBlock currentExitableBlock;
    short[] exception_table;
    int exception_table_length;
    int exitableBlockLevel;
    int fixup_count;
    Label[] fixup_labels;
    int[] fixup_offsets;
    int flags;
    IfState if_stack;
    LineNumbersAttr lines;
    Type[] local_types;
    public LocalVarsAttr locals = this;
    private int max_locals;
    private int max_stack;
    Label previousLabel;
    SourceDebugExtAttr sourceDbgExt;
    public StackMapTableAttr stackMap;
    public Type[] stack_types;
    TryState try_stack = this;
    private boolean unreachable_here;
    boolean[] varsSetInCurrentBlock;

    public final Attribute getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    boolean useJsr() {
        return (this.flags & 2) == 0;
    }

    public final void fixupChain(Label here, Label target) {
        fixupAdd(9, 0, target);
        here.defineRaw(this);
    }

    public final void fixupAdd(int kind, Label label) {
        fixupAdd(kind, this.PC, label);
    }

    final void fixupAdd(int kind, int offset, Label label) {
        if (!(label == null || kind == 1 || kind == 0 || kind == 2 || kind == 11)) {
            label.needsStackMapEntry = true;
        }
        int count = this.fixup_count;
        if (count == 0) {
            this.fixup_offsets = new int[30];
            this.fixup_labels = new Label[30];
        } else if (this.fixup_count == this.fixup_offsets.length) {
            int new_length = count * 2;
            Label[] new_labels = new Label[new_length];
            System.arraycopy(this.fixup_labels, 0, new_labels, 0, count);
            this.fixup_labels = new_labels;
            int[] new_offsets = new int[new_length];
            System.arraycopy(this.fixup_offsets, 0, new_offsets, 0, count);
            this.fixup_offsets = new_offsets;
        }
        this.fixup_offsets[count] = (offset << 4) | kind;
        this.fixup_labels[count] = label;
        this.fixup_count = count + 1;
    }

    private final int fixupOffset(int index) {
        return this.fixup_offsets[index] >> 4;
    }

    private final int fixupKind(int index) {
        return this.fixup_offsets[index] & 15;
    }

    public final Method getMethod() {
        return (Method) getContainer();
    }

    public final int getPC() {
        return this.PC;
    }

    public final int getSP() {
        return this.SP;
    }

    public final ConstantPool getConstants() {
        return getMethod().classfile.constants;
    }

    public final boolean reachableHere() {
        return !this.unreachable_here;
    }

    public final void setReachable(boolean val) {
        this.unreachable_here = !val;
    }

    public final void setUnreachable() {
        this.unreachable_here = true;
    }

    public int getMaxStack() {
        return this.max_stack;
    }

    public int getMaxLocals() {
        return this.max_locals;
    }

    public void setMaxStack(int n) {
        this.max_stack = n;
    }

    public void setMaxLocals(int n) {
        this.max_locals = n;
    }

    public byte[] getCode() {
        return this.code;
    }

    public void setCode(byte[] code) {
        this.code = code;
        this.PC = code.length;
    }

    public void setCodeLength(int len) {
        this.PC = len;
    }

    public int getCodeLength() {
        return this.PC;
    }

    public CodeAttr(Method meth) {
        super("Code");
        addToFrontOf(meth);
        if (meth.getDeclaringClass().getClassfileMajorVersion() >= (short) 50) {
            this.flags |= 3;
        }
    }

    public final void reserve(int bytes) {
        if (this.code == null) {
            this.code = new byte[(bytes + 100)];
        } else if (this.PC + bytes > this.code.length) {
            byte[] new_code = new byte[((this.code.length * 2) + bytes)];
            System.arraycopy(this.code, 0, new_code, 0, this.PC);
            this.code = new_code;
        }
    }

    byte invert_opcode(byte opcode) {
        int iopcode = opcode & 255;
        if ((iopcode >= 153 && iopcode <= 166) || (iopcode >= 198 && iopcode <= 199)) {
            return (byte) (iopcode ^ 1);
        }
        throw new Error("unknown opcode to invert_opcode");
    }

    public final void put1(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put2(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        bArr = this.code;
        i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put4(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        bArr = this.code;
        i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 16);
        bArr = this.code;
        i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        bArr = this.code;
        i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void putIndex2(CpoolEntry cnst) {
        put2(cnst.index);
    }

    public final void putLineNumber(String filename, int linenumber) {
        if (filename != null) {
            getMethod().classfile.setSourceFile(filename);
        }
        putLineNumber(linenumber);
    }

    public final void putLineNumber(int linenumber) {
        if (this.sourceDbgExt != null) {
            linenumber = this.sourceDbgExt.fixLine(linenumber);
        }
        fixupAdd(14, null);
        fixupAdd(15, linenumber, null);
    }

    void noteParamTypes() {
        Type type;
        int offset;
        Method method = getMethod();
        int offset2 = 0;
        if ((method.access_flags & 8) == 0) {
            type = method.classfile;
            if ("<init>".equals(method.getName()) && !"java.lang.Object".equals(type.getName())) {
                type = UninitializedType.uninitializedThis((ClassType) type);
            }
            offset = 0 + 1;
            noteVarType(0, type);
            offset2 = offset;
        }
        int arg_count = method.arg_types.length;
        int i = 0;
        offset = offset2;
        while (i < arg_count) {
            type = method.arg_types[i];
            offset2 = offset + 1;
            noteVarType(offset, type);
            int size = type.getSizeInWords();
            while (true) {
                size--;
                if (size <= 0) {
                    break;
                }
                offset2++;
            }
            i++;
            offset = offset2;
        }
        if ((this.flags & 1) != 0) {
            this.stackMap = new StackMapTableAttr();
            int[] encodedLocals = new int[(offset + 20)];
            i = 0;
            int count = 0;
            while (i < offset) {
                int encoded = this.stackMap.encodeVerificationType(this.local_types[i], this);
                int count2 = count + 1;
                encodedLocals[count] = encoded;
                int tag = encoded & 255;
                if (tag == 3 || tag == 4) {
                    i++;
                }
                i++;
                count = count2;
            }
            this.stackMap.encodedLocals = encodedLocals;
            this.stackMap.countLocals = count;
            this.stackMap.encodedStack = new int[10];
            this.stackMap.countStack = 0;
        }
    }

    public void noteVarType(int offset, Type type) {
        int size = type.getSizeInWords();
        if (this.local_types == null) {
            this.local_types = new Type[((offset + size) + 20)];
        } else if (offset + size > this.local_types.length) {
            Type[] new_array = new Type[((offset + size) * 2)];
            System.arraycopy(this.local_types, 0, new_array, 0, this.local_types.length);
            this.local_types = new_array;
        }
        this.local_types[offset] = type;
        if (this.varsSetInCurrentBlock == null) {
            this.varsSetInCurrentBlock = new boolean[this.local_types.length];
        } else if (this.varsSetInCurrentBlock.length <= offset) {
            boolean[] tmp = new boolean[this.local_types.length];
            System.arraycopy(this.varsSetInCurrentBlock, 0, tmp, 0, this.varsSetInCurrentBlock.length);
            this.varsSetInCurrentBlock = tmp;
        }
        this.varsSetInCurrentBlock[offset] = true;
        if (offset > 0) {
            Type prev = this.local_types[offset - 1];
            if (prev != null && prev.getSizeInWords() == 2) {
                this.local_types[offset - 1] = null;
            }
        }
        while (true) {
            size--;
            if (size > 0) {
                offset++;
                this.local_types[offset] = null;
            } else {
                return;
            }
        }
    }

    public final void setTypes(Label label) {
        setTypes(label.localTypes, label.stackTypes);
    }

    public final void setTypes(Type[] labelLocals, Type[] labelStack) {
        int i;
        int usedStack = labelStack.length;
        int usedLocals = labelLocals.length;
        if (this.local_types != null) {
            if (usedLocals > 0) {
                System.arraycopy(labelLocals, 0, this.local_types, 0, usedLocals);
            }
            for (i = usedLocals; i < this.local_types.length; i++) {
                this.local_types[i] = null;
            }
        }
        if (this.stack_types == null || usedStack > this.stack_types.length) {
            this.stack_types = new Type[usedStack];
        } else {
            for (i = usedStack; i < this.stack_types.length; i++) {
                this.stack_types[i] = null;
            }
        }
        System.arraycopy(labelStack, 0, this.stack_types, 0, usedStack);
        this.SP = usedStack;
    }

    public final void pushType(Type type) {
        if (type.size == 0) {
            throw new Error("pushing void type onto stack");
        }
        Type[] typeArr;
        int i;
        if (this.stack_types == null || this.stack_types.length == 0) {
            this.stack_types = new Type[20];
        } else if (this.SP + 1 >= this.stack_types.length) {
            Type[] new_array = new Type[(this.stack_types.length * 2)];
            System.arraycopy(this.stack_types, 0, new_array, 0, this.SP);
            this.stack_types = new_array;
        }
        if (type.size == 8) {
            typeArr = this.stack_types;
            i = this.SP;
            this.SP = i + 1;
            typeArr[i] = Type.voidType;
        }
        typeArr = this.stack_types;
        i = this.SP;
        this.SP = i + 1;
        typeArr[i] = type;
        if (this.SP > this.max_stack) {
            this.max_stack = this.SP;
        }
    }

    public final Type popType() {
        if (this.SP <= 0) {
            throw new Error("popType called with empty stack " + getMethod());
        }
        Type[] typeArr = this.stack_types;
        int i = this.SP - 1;
        this.SP = i;
        Type type = typeArr[i];
        if (type.size != 8 || popType().isVoid()) {
            return type;
        }
        throw new Error("missing void type on stack");
    }

    public final Type topType() {
        return this.stack_types[this.SP - 1];
    }

    public void emitPop(int nvalues) {
        while (nvalues > 0) {
            reserve(1);
            if (popType().size > 4) {
                put1(88);
            } else if (nvalues > 1) {
                if (popType().size > 4) {
                    put1(87);
                    reserve(1);
                }
                put1(88);
                nvalues--;
            } else {
                put1(87);
            }
            nvalues--;
        }
    }

    public Label getLabel() {
        Label label = new Label();
        label.defineRaw(this);
        return label;
    }

    public void emitSwap() {
        reserve(1);
        Type type1 = popType();
        Type type2 = popType();
        if (type1.size > 4 || type2.size > 4) {
            pushType(type2);
            pushType(type1);
            emitDupX();
            emitPop(1);
            return;
        }
        pushType(type1);
        put1(95);
        pushType(type2);
    }

    public void emitDup() {
        reserve(1);
        Type type = topType();
        put1(type.size <= 4 ? 89 : 92);
        pushType(type);
    }

    public void emitDupX() {
        reserve(1);
        Type type = popType();
        Type skipedType = popType();
        if (skipedType.size <= 4) {
            put1(type.size <= 4 ? 90 : 93);
        } else {
            put1(type.size <= 4 ? 91 : 94);
        }
        pushType(type);
        pushType(skipedType);
        pushType(type);
    }

    public void emitDup(int size, int offset) {
        if (size != 0) {
            int kind;
            reserve(1);
            Type copied1 = popType();
            Type copied2 = null;
            if (size == 1) {
                if (copied1.size > 4) {
                    throw new Error("using dup for 2-word type");
                }
            } else if (size != 2) {
                throw new Error("invalid size to emitDup");
            } else if (copied1.size <= 4) {
                copied2 = popType();
                if (copied2.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            }
            Type skipped1 = null;
            Type skipped2 = null;
            if (offset == 0) {
                kind = size == 1 ? 89 : 92;
            } else if (offset == 1) {
                kind = size == 1 ? 90 : 93;
                skipped1 = popType();
                if (skipped1.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            } else if (offset == 2) {
                kind = size == 1 ? 91 : 94;
                skipped1 = popType();
                if (skipped1.size <= 4) {
                    skipped2 = popType();
                    if (skipped2.size > 4) {
                        throw new Error("dup will cause invalid types on stack");
                    }
                }
            } else {
                throw new Error("emitDup:  invalid offset");
            }
            put1(kind);
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
            if (skipped2 != null) {
                pushType(skipped2);
            }
            if (skipped1 != null) {
                pushType(skipped1);
            }
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
        }
    }

    public void emitDup(int size) {
        emitDup(size, 0);
    }

    public void emitDup(Type type) {
        emitDup(type.size > 4 ? 2 : 1, 0);
    }

    public void enterScope(Scope scope) {
        scope.setStartPC(this);
        this.locals.enterScope(scope);
    }

    public Scope pushScope() {
        Scope scope = new Scope();
        if (this.locals == null) {
            this.locals = new LocalVarsAttr(getMethod());
        }
        enterScope(scope);
        if (this.locals.parameter_scope == null) {
            this.locals.parameter_scope = scope;
        }
        return scope;
    }

    public Scope getCurrentScope() {
        return this.locals.current_scope;
    }

    public Scope popScope() {
        Scope scope = this.locals.current_scope;
        this.locals.current_scope = scope.parent;
        scope.freeLocals(this);
        scope.end = getLabel();
        return scope;
    }

    public Variable getArg(int index) {
        return this.locals.parameter_scope.getVariable(index);
    }

    public Variable lookup(String name) {
        for (Scope scope = this.locals.current_scope; scope != null; scope = scope.parent) {
            Variable var = scope.lookup(name);
            if (var != null) {
                return var;
            }
        }
        return null;
    }

    public Variable addLocal(Type type) {
        return this.locals.current_scope.addVariable(this, type, null);
    }

    public Variable addLocal(Type type, String name) {
        return this.locals.current_scope.addVariable(this, type, name);
    }

    public void addParamLocals() {
        Method method = getMethod();
        if ((method.access_flags & 8) == 0) {
            addLocal(method.classfile).setParameter(true);
        }
        for (Type addLocal : method.arg_types) {
            addLocal(addLocal).setParameter(true);
        }
    }

    public final void emitPushConstant(int val, Type type) {
        switch (type.getSignature().charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                emitPushInt(val);
                return;
            case 'D':
                emitPushDouble((double) val);
                return;
            case 'F':
                emitPushFloat((float) val);
                return;
            case 'J':
                emitPushLong((long) val);
                return;
            default:
                throw new Error("bad type to emitPushConstant");
        }
    }

    public final void emitPushConstant(CpoolEntry cnst) {
        reserve(3);
        int index = cnst.index;
        if (cnst instanceof CpoolValue2) {
            put1(20);
            put2(index);
        } else if (index < 256) {
            put1(18);
            put1(index);
        } else {
            put1(19);
            put2(index);
        }
    }

    public final void emitPushInt(int i) {
        reserve(3);
        if (i >= -1 && i <= 5) {
            put1(i + 3);
        } else if (i >= -128 && i < 128) {
            put1(16);
            put1(i);
        } else if (i < -32768 || i >= 32768) {
            emitPushConstant(getConstants().addInt(i));
        } else {
            put1(17);
            put2(i);
        }
        pushType(Type.intType);
    }

    public void emitPushLong(long i) {
        if (i == 0 || i == 1) {
            reserve(1);
            put1(((int) i) + 9);
        } else if (((long) ((int) i)) == i) {
            emitPushInt((int) i);
            reserve(1);
            popType();
            put1(133);
        } else {
            emitPushConstant(getConstants().addLong(i));
        }
        pushType(Type.longType);
    }

    public void emitPushFloat(float x) {
        int xi = (int) x;
        if (((float) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addFloat(x));
        } else if (xi < 0 || xi > 2) {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(134);
        } else {
            reserve(1);
            put1(xi + 11);
            if (xi == 0 && Float.floatToIntBits(x) != 0) {
                reserve(1);
                put1(118);
            }
        }
        pushType(Type.floatType);
    }

    public void emitPushDouble(double x) {
        int xi = (int) x;
        if (((double) xi) != x || xi < -128 || xi >= 128) {
            emitPushConstant(getConstants().addDouble(x));
        } else if (xi == 0 || xi == 1) {
            reserve(1);
            put1(xi + 14);
            if (xi == 0 && Double.doubleToLongBits(x) != 0) {
                reserve(1);
                put1(119);
            }
        } else {
            emitPushInt(xi);
            reserve(1);
            popType();
            put1(135);
        }
        pushType(Type.doubleType);
    }

    public static final String calculateSplit(String str) {
        int strLength = str.length();
        StringBuffer sbuf = new StringBuffer(20);
        int segmentStart = 0;
        int byteLength = 0;
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int bytes = ch >= 'ࠀ' ? 3 : (ch >= '' || ch == '\u0000') ? 2 : 1;
            if (byteLength + bytes > SupportMenu.USER_MASK) {
                sbuf.append((char) (i - segmentStart));
                segmentStart = i;
                byteLength = 0;
            }
            byteLength += bytes;
        }
        sbuf.append((char) (strLength - segmentStart));
        return sbuf.toString();
    }

    public final void emitPushString(String str) {
        if (str == null) {
            emitPushNull();
            return;
        }
        int length = str.length();
        String segments = calculateSplit(str);
        int numSegments = segments.length();
        if (numSegments <= 1) {
            emitPushConstant(getConstants().addString(str));
            pushType(Type.javalangStringType);
            return;
        }
        if (numSegments == 2) {
            int firstSegment = segments.charAt(0);
            emitPushString(str.substring(0, firstSegment));
            emitPushString(str.substring(firstSegment));
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("concat", 1));
        } else {
            Type sbufType = ClassType.make("java.lang.StringBuffer");
            emitNew(sbufType);
            emitDup(sbufType);
            emitPushInt(length);
            emitInvokeSpecial(sbufType.getDeclaredMethod("<init>", new Type[]{Type.intType}));
            Method appendMethod = sbufType.getDeclaredMethod("append", new Type[]{Type.javalangStringType});
            int segStart = 0;
            for (int seg = 0; seg < numSegments; seg++) {
                emitDup(sbufType);
                int segEnd = segStart + segments.charAt(seg);
                emitPushString(str.substring(segStart, segEnd));
                emitInvokeVirtual(appendMethod);
                segStart = segEnd;
            }
            emitInvokeVirtual(Type.toString_method);
        }
        if (str == str.intern()) {
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("intern", 0));
        }
    }

    public final void emitPushClass(ObjectType ctype) {
        emitPushConstant(getConstants().addClass(ctype));
        pushType(Type.javalangClassType);
    }

    public void emitPushNull() {
        reserve(1);
        put1(1);
        pushType(Type.nullType);
    }

    public void emitPushDefaultValue(Type type) {
        type = type.getImplementationType();
        if (type instanceof PrimType) {
            emitPushConstant(0, type);
        } else {
            emitPushNull();
        }
    }

    public void emitStoreDefaultValue(Variable var) {
        emitPushDefaultValue(var.getType());
        emitStore(var);
    }

    public final void emitPushThis() {
        emitLoad(this.locals.used[0]);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void emitPushPrimArray(java.lang.Object r17, gnu.bytecode.ArrayType r18) {
        /*
        r16 = this;
        r4 = r18.getComponentType();
        r7 = java.lang.reflect.Array.getLength(r17);
        r0 = r16;
        r0.emitPushInt(r7);
        r0 = r16;
        r0.emitNewArray(r4);
        r11 = r4.getSignature();
        r12 = 0;
        r10 = r11.charAt(r12);
        r6 = 0;
    L_0x001c:
        if (r6 >= r7) goto L_0x00df;
    L_0x001e:
        r8 = 0;
        r5 = 0;
        r2 = 0;
        switch(r10) {
            case 66: goto L_0x007c;
            case 67: goto L_0x006c;
            case 68: goto L_0x00b2;
            case 70: goto L_0x00a2;
            case 73: goto L_0x004c;
            case 74: goto L_0x003d;
            case 83: goto L_0x005c;
            case 90: goto L_0x008c;
            default: goto L_0x0026;
        };
    L_0x0026:
        r0 = r16;
        r1 = r18;
        r0.emitDup(r1);
        r0 = r16;
        r0.emitPushInt(r6);
        switch(r10) {
            case 66: goto L_0x00c2;
            case 67: goto L_0x00c2;
            case 68: goto L_0x00d8;
            case 70: goto L_0x00d1;
            case 73: goto L_0x00c2;
            case 74: goto L_0x00ca;
            case 83: goto L_0x00c2;
            case 90: goto L_0x00c2;
            default: goto L_0x0035;
        };
    L_0x0035:
        r0 = r16;
        r0.emitArrayStore(r4);
    L_0x003a:
        r6 = r6 + 1;
        goto L_0x001c;
    L_0x003d:
        r11 = r17;
        r11 = (long[]) r11;
        r11 = (long[]) r11;
        r8 = r11[r6];
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x004b:
        goto L_0x003a;
    L_0x004c:
        r11 = r17;
        r11 = (int[]) r11;
        r11 = (int[]) r11;
        r11 = r11[r6];
        r8 = (long) r11;
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x005b:
        goto L_0x003a;
    L_0x005c:
        r11 = r17;
        r11 = (short[]) r11;
        r11 = (short[]) r11;
        r11 = r11[r6];
        r8 = (long) r11;
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x006b:
        goto L_0x003a;
    L_0x006c:
        r11 = r17;
        r11 = (char[]) r11;
        r11 = (char[]) r11;
        r11 = r11[r6];
        r8 = (long) r11;
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x007b:
        goto L_0x003a;
    L_0x007c:
        r11 = r17;
        r11 = (byte[]) r11;
        r11 = (byte[]) r11;
        r11 = r11[r6];
        r8 = (long) r11;
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x008b:
        goto L_0x003a;
    L_0x008c:
        r11 = r17;
        r11 = (boolean[]) r11;
        r11 = (boolean[]) r11;
        r11 = r11[r6];
        if (r11 == 0) goto L_0x009f;
    L_0x0096:
        r8 = 1;
    L_0x0098:
        r12 = 0;
        r11 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x009e:
        goto L_0x003a;
    L_0x009f:
        r8 = 0;
        goto L_0x0098;
    L_0x00a2:
        r11 = r17;
        r11 = (float[]) r11;
        r11 = (float[]) r11;
        r5 = r11[r6];
        r12 = (double) r5;
        r14 = 0;
        r11 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x00b1:
        goto L_0x003a;
    L_0x00b2:
        r11 = r17;
        r11 = (double[]) r11;
        r11 = (double[]) r11;
        r2 = r11[r6];
        r12 = 0;
        r11 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r11 != 0) goto L_0x0026;
    L_0x00c0:
        goto L_0x003a;
    L_0x00c2:
        r11 = (int) r8;
        r0 = r16;
        r0.emitPushInt(r11);
        goto L_0x0035;
    L_0x00ca:
        r0 = r16;
        r0.emitPushLong(r8);
        goto L_0x0035;
    L_0x00d1:
        r0 = r16;
        r0.emitPushFloat(r5);
        goto L_0x0035;
    L_0x00d8:
        r0 = r16;
        r0.emitPushDouble(r2);
        goto L_0x0035;
    L_0x00df:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.emitPushPrimArray(java.lang.Object, gnu.bytecode.ArrayType):void");
    }

    void emitNewArray(int type_code) {
        reserve(2);
        put1(188);
        put1(type_code);
    }

    public final void emitArrayLength() {
        if (popType() instanceof ArrayType) {
            reserve(1);
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK);
            pushType(Type.intType);
            return;
        }
        throw new Error("non-array type in emitArrayLength");
    }

    private int adjustTypedOp(char sig) {
        switch (sig) {
            case 'B':
            case 'Z':
                return 5;
            case 'C':
                return 6;
            case 'D':
                return 3;
            case 'F':
                return 2;
            case 'I':
                return 0;
            case 'J':
                return 1;
            case 'S':
                return 7;
            default:
                return 4;
        }
    }

    private int adjustTypedOp(Type type) {
        return adjustTypedOp(type.getSignature().charAt(0));
    }

    private void emitTypedOp(int op, Type type) {
        reserve(1);
        put1(adjustTypedOp(type) + op);
    }

    private void emitTypedOp(int op, char sig) {
        reserve(1);
        put1(adjustTypedOp(sig) + op);
    }

    public void emitArrayStore(Type element_type) {
        popType();
        popType();
        popType();
        emitTypedOp(79, element_type);
    }

    public void emitArrayStore() {
        popType();
        popType();
        emitTypedOp(79, ((ArrayType) popType().getImplementationType()).getComponentType());
    }

    public void emitArrayLoad(Type element_type) {
        popType();
        popType();
        emitTypedOp(46, element_type);
        pushType(element_type);
    }

    public void emitArrayLoad() {
        popType();
        Type elementType = ((ArrayType) popType().getImplementationType()).getComponentType();
        emitTypedOp(46, elementType);
        pushType(elementType);
    }

    public void emitNew(ClassType type) {
        reserve(3);
        Label label = new Label(this);
        label.defineRaw(this);
        put1(187);
        putIndex2(getConstants().addClass((ObjectType) type));
        pushType(new UninitializedType(type, label));
    }

    public void emitNewArray(Type element_type, int dims) {
        if (popType().promote() != Type.intType) {
            throw new Error("non-int dim. spec. in emitNewArray");
        }
        if (element_type instanceof PrimType) {
            int code;
            switch (element_type.getSignature().charAt(0)) {
                case 'B':
                    code = 8;
                    break;
                case 'C':
                    code = 5;
                    break;
                case 'D':
                    code = 7;
                    break;
                case 'F':
                    code = 6;
                    break;
                case 'I':
                    code = 10;
                    break;
                case 'J':
                    code = 11;
                    break;
                case 'S':
                    code = 9;
                    break;
                case 'Z':
                    code = 4;
                    break;
                default:
                    throw new Error("bad PrimType in emitNewArray");
            }
            emitNewArray(code);
        } else if (element_type instanceof ObjectType) {
            reserve(3);
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG);
            putIndex2(getConstants().addClass((ObjectType) element_type));
        } else if (element_type instanceof ArrayType) {
            reserve(4);
            put1(197);
            putIndex2(getConstants().addClass(new ArrayType(element_type)));
            if (dims < 1 || dims > 255) {
                throw new Error("dims out of range in emitNewArray");
            }
            put1(dims);
            do {
                dims--;
                if (dims > 0) {
                }
            } while (popType().promote() == Type.intType);
            throw new Error("non-int dim. spec. in emitNewArray");
        } else {
            throw new Error("unimplemented type in emitNewArray");
        }
        pushType(new ArrayType(element_type));
    }

    public void emitNewArray(Type element_type) {
        emitNewArray(element_type, 1);
    }

    private void emitBinop(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 == type2 && (type1 instanceof PrimType)) {
            emitTypedOp(base_code, type1);
            pushType(type1_raw);
            return;
        }
        throw new Error("non-matching or bad types in binary operation");
    }

    private void emitBinop(int base_code, char sig) {
        popType();
        popType();
        emitTypedOp(base_code, sig);
        pushType(Type.signatureToPrimitive(sig));
    }

    public void emitBinop(int base_code, Type type) {
        popType();
        popType();
        emitTypedOp(base_code, type);
        pushType(type);
    }

    public final void emitAdd(char sig) {
        emitBinop(96, sig);
    }

    public final void emitAdd(PrimType type) {
        emitBinop(96, (Type) type);
    }

    public final void emitAdd() {
        emitBinop(96);
    }

    public final void emitSub(char sig) {
        emitBinop(100, sig);
    }

    public final void emitSub(PrimType type) {
        emitBinop(100, (Type) type);
    }

    public final void emitSub() {
        emitBinop(100);
    }

    public final void emitMul() {
        emitBinop(104);
    }

    public final void emitDiv() {
        emitBinop(108);
    }

    public final void emitRem() {
        emitBinop(DateTime.TIME_MASK);
    }

    public final void emitAnd() {
        emitBinop(TransportMediator.KEYCODE_MEDIA_PLAY);
    }

    public final void emitIOr() {
        emitBinop(128);
    }

    public final void emitXOr() {
        emitBinop(TransportMediator.KEYCODE_MEDIA_RECORD);
    }

    public final void emitShl() {
        emitShift(120);
    }

    public final void emitShr() {
        emitShift(122);
    }

    public final void emitUshr() {
        emitShift(124);
    }

    private void emitShift(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != Type.intType && type1 != Type.longType) {
            throw new Error("the value shifted must be an int or a long");
        } else if (type2 != Type.intType) {
            throw new Error("the amount of shift must be an int");
        } else {
            emitTypedOp(base_code, type1);
            pushType(type1_raw);
        }
    }

    public final void emitNot(Type type) {
        emitPushConstant(1, type);
        emitAdd();
        emitPushConstant(1, type);
        emitAnd();
    }

    public void emitPrimop(int opcode, int arg_count, Type retType) {
        reserve(1);
        while (true) {
            arg_count--;
            if (arg_count >= 0) {
                popType();
            } else {
                put1(opcode);
                pushType(retType);
                return;
            }
        }
    }

    void emitMaybeWide(int opcode, int index) {
        if (index >= 256) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(opcode);
            put2(index);
            return;
        }
        put1(opcode);
        put1(index);
    }

    public final void emitLoad(Variable var) {
        if (var.dead()) {
            throw new Error("attempting to push dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to load from unassigned variable " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        reserve(4);
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1(((kind * 4) + 26) + offset);
        } else {
            emitMaybeWide(kind + 21, offset);
        }
        pushType(var.getType());
    }

    public void emitStore(Variable var) {
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to store in unassigned " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        noteVarType(offset, type);
        reserve(4);
        popType();
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1(((kind * 4) + 59) + offset);
        } else {
            emitMaybeWide(kind + 54, offset);
        }
    }

    public void emitInc(Variable var, short inc) {
        if (var.dead()) {
            throw new Error("attempting to increment dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to increment unassigned variable" + var.getName() + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        reserve(6);
        if (var.getType().getImplementationType().promote() != Type.intType) {
            throw new Error("attempting to increment non-int variable");
        }
        boolean wide = offset > 255 || inc > (short) 255 || inc < (short) -256;
        if (wide) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(132);
            put2(offset);
            put2(inc);
            return;
        }
        put1(132);
        put1(offset);
        put1(inc);
    }

    private final void emitFieldop(Field field, int opcode) {
        reserve(3);
        put1(opcode);
        putIndex2(getConstants().addFieldRef(field));
    }

    public final void emitGetStatic(Field field) {
        pushType(field.type);
        emitFieldop(field, 178);
    }

    public final void emitGetField(Field field) {
        popType();
        pushType(field.type);
        emitFieldop(field, 180);
    }

    public final void emitPutStatic(Field field) {
        popType();
        emitFieldop(field, 179);
    }

    public final void emitPutField(Field field) {
        popType();
        popType();
        emitFieldop(field, 181);
    }

    private int words(Type[] types) {
        int res = 0;
        int i = types.length;
        while (true) {
            i--;
            if (i < 0) {
                return res;
            }
            if (types[i].size > 4) {
                res += 2;
            } else {
                res++;
            }
        }
    }

    public void emitInvokeMethod(Method method, int opcode) {
        boolean is_invokestatic;
        boolean is_init;
        boolean z = true;
        reserve(opcode == 185 ? 5 : 3);
        int arg_count = method.arg_types.length;
        if (opcode == 184) {
            is_invokestatic = true;
        } else {
            is_invokestatic = false;
        }
        if (opcode == 183 && "<init>".equals(method.getName())) {
            is_init = true;
        } else {
            is_init = false;
        }
        if ((method.access_flags & 8) == 0) {
            z = false;
        }
        if (is_invokestatic != z) {
            throw new Error("emitInvokeXxx static flag mis-match method.flags=" + method.access_flags);
        }
        Type t;
        if (!(is_invokestatic || is_init)) {
            arg_count++;
        }
        put1(opcode);
        putIndex2(getConstants().addMethodRef(method));
        if (opcode == 185) {
            put1(words(method.arg_types) + 1);
            put1(0);
        }
        do {
            arg_count--;
            if (arg_count >= 0) {
                t = popType();
            } else {
                if (is_init) {
                    t = popType();
                    if (t instanceof UninitializedType) {
                        int i;
                        ClassType ctype = ((UninitializedType) t).ctype;
                        for (i = 0; i < this.SP; i++) {
                            if (this.stack_types[i] == t) {
                                this.stack_types[i] = ctype;
                            }
                        }
                        Variable[] used = this.locals.used;
                        i = used == null ? 0 : used.length;
                        while (true) {
                            i--;
                            if (i < 0) {
                                break;
                            }
                            Variable var = used[i];
                            if (var != null && var.type == t) {
                                var.type = ctype;
                            }
                        }
                        i = this.local_types == null ? 0 : this.local_types.length;
                        while (true) {
                            i--;
                            if (i < 0) {
                                break;
                            } else if (this.local_types[i] == t) {
                                this.local_types[i] = ctype;
                            }
                        }
                    } else {
                        throw new Error("calling <init> on already-initialized object");
                    }
                }
                if (method.return_type.size != 0) {
                    pushType(method.return_type);
                    return;
                }
                return;
            }
        } while (!(t instanceof UninitializedType));
        throw new Error("passing " + t + " as parameter");
    }

    public void emitInvoke(Method method) {
        int opcode;
        if ((method.access_flags & 8) != 0) {
            opcode = 184;
        } else if (method.classfile.isInterface()) {
            opcode = 185;
        } else if ("<init>".equals(method.getName())) {
            opcode = 183;
        } else {
            opcode = 182;
        }
        emitInvokeMethod(method, opcode);
    }

    public void emitInvokeVirtual(Method method) {
        emitInvokeMethod(method, 182);
    }

    public void emitInvokeSpecial(Method method) {
        emitInvokeMethod(method, 183);
    }

    public void emitInvokeStatic(Method method) {
        emitInvokeMethod(method, 184);
    }

    public void emitInvokeInterface(Method method) {
        emitInvokeMethod(method, 185);
    }

    final void emitTransfer(Label label, int opcode) {
        label.setTypes(this);
        fixupAdd(6, label);
        put1(opcode);
        this.PC += 2;
    }

    public final void emitGoto(Label label) {
        label.setTypes(this);
        fixupAdd(4, label);
        reserve(3);
        put1(YaVersion.YOUNG_ANDROID_VERSION);
        this.PC += 2;
        setUnreachable();
    }

    public final void emitJsr(Label label) {
        fixupAdd(5, label);
        reserve(3);
        put1(168);
        this.PC += 2;
    }

    public ExitableBlock startExitableBlock(Type resultType, boolean runFinallyBlocks) {
        ExitableBlock bl = new ExitableBlock(resultType, this, runFinallyBlocks);
        bl.outer = this.currentExitableBlock;
        this.currentExitableBlock = bl;
        return bl;
    }

    public void endExitableBlock() {
        ExitableBlock bl = this.currentExitableBlock;
        bl.finish();
        this.currentExitableBlock = bl.outer;
    }

    public final void emitGotoIfCompare1(Label label, int opcode) {
        popType();
        reserve(3);
        emitTransfer(label, opcode);
    }

    public final void emitGotoIfIntEqZero(Label label) {
        emitGotoIfCompare1(label, 153);
    }

    public final void emitGotoIfIntNeZero(Label label) {
        emitGotoIfCompare1(label, 154);
    }

    public final void emitGotoIfIntLtZero(Label label) {
        emitGotoIfCompare1(label, 155);
    }

    public final void emitGotoIfIntGeZero(Label label) {
        emitGotoIfCompare1(label, 156);
    }

    public final void emitGotoIfIntGtZero(Label label) {
        emitGotoIfCompare1(label, 157);
    }

    public final void emitGotoIfIntLeZero(Label label) {
        emitGotoIfCompare1(label, 158);
    }

    public final void emitGotoIfCompare2(Label label, int logop) {
        boolean cmpg = false;
        if (logop < 153 || logop > 158) {
            throw new Error("emitGotoIfCompare2: logop must be one of ifeq...ifle");
        }
        Type type2 = popType().promote();
        Type type1 = popType().promote();
        reserve(4);
        char sig1 = type1.getSignature().charAt(0);
        char sig2 = type2.getSignature().charAt(0);
        if (logop == 155 || logop == 158) {
            cmpg = true;
        }
        if (sig1 == Access.INNERCLASS_CONTEXT && sig2 == Access.INNERCLASS_CONTEXT) {
            logop += 6;
        } else if (sig1 == 'J' && sig2 == 'J') {
            put1(148);
        } else if (sig1 == Access.FIELD_CONTEXT && sig2 == Access.FIELD_CONTEXT) {
            put1(cmpg ? 149 : 150);
        } else if (sig1 == 'D' && sig2 == 'D') {
            put1(cmpg ? 151 : 152);
        } else if ((sig1 == 'L' || sig1 == '[') && ((sig2 == 'L' || sig2 == '[') && logop <= 154)) {
            logop += 12;
        } else {
            throw new Error("invalid types to emitGotoIfCompare2");
        }
        emitTransfer(label, logop);
    }

    public final void emitGotoIfEq(Label label, boolean invert) {
        emitGotoIfCompare2(label, invert ? 154 : 153);
    }

    public final void emitGotoIfEq(Label label) {
        emitGotoIfCompare2(label, 153);
    }

    public final void emitGotoIfNE(Label label) {
        emitGotoIfCompare2(label, 154);
    }

    public final void emitGotoIfLt(Label label) {
        emitGotoIfCompare2(label, 155);
    }

    public final void emitGotoIfGe(Label label) {
        emitGotoIfCompare2(label, 156);
    }

    public final void emitGotoIfGt(Label label) {
        emitGotoIfCompare2(label, 157);
    }

    public final void emitGotoIfLe(Label label) {
        emitGotoIfCompare2(label, 158);
    }

    public final void emitIfCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType().promote() != Type.intType) {
            throw new Error("non-int type to emitIfCompare1");
        }
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntNotZero() {
        emitIfCompare1(153);
    }

    public final void emitIfIntEqZero() {
        emitIfCompare1(154);
    }

    public final void emitIfIntLEqZero() {
        emitIfCompare1(157);
    }

    public final void emitIfRefCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType() instanceof ObjectType) {
            reserve(3);
            emitTransfer(new_if.end_label, opcode);
            new_if.start_stack_size = this.SP;
            return;
        }
        throw new Error("non-ref type to emitIfRefCompare1");
    }

    public final void emitIfNotNull() {
        emitIfRefCompare1(198);
    }

    public final void emitIfNull() {
        emitIfRefCompare1(199);
    }

    public final void emitIfIntCompare(int opcode) {
        IfState new_if = new IfState(this);
        popType();
        popType();
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntLt() {
        emitIfIntCompare(162);
    }

    public final void emitIfNEq() {
        IfState new_if = new IfState(this);
        emitGotoIfEq(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfEq() {
        IfState new_if = new IfState(this);
        emitGotoIfNE(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLt() {
        IfState new_if = new IfState(this);
        emitGotoIfGe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGe() {
        IfState new_if = new IfState(this);
        emitGotoIfLt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGt() {
        IfState new_if = new IfState(this);
        emitGotoIfLe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLe() {
        IfState new_if = new IfState(this);
        emitGotoIfGt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public void emitRet(Variable var) {
        int offset = var.offset;
        if (offset < 256) {
            reserve(2);
            put1(169);
            put1(offset);
            return;
        }
        reserve(4);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
        put1(169);
        put2(offset);
    }

    public final void emitThen() {
        this.if_stack.start_stack_size = this.SP;
    }

    public final void emitIfThen() {
        IfState ifState = new IfState(this, null);
    }

    public final void emitElse() {
        Label else_label = this.if_stack.end_label;
        if (reachableHere()) {
            Label end_label = new Label(this);
            this.if_stack.end_label = end_label;
            int growth = this.SP - this.if_stack.start_stack_size;
            this.if_stack.stack_growth = growth;
            if (growth > 0) {
                this.if_stack.then_stacked_types = new Type[growth];
                System.arraycopy(this.stack_types, this.if_stack.start_stack_size, this.if_stack.then_stacked_types, 0, growth);
            } else {
                this.if_stack.then_stacked_types = new Type[0];
            }
            emitGoto(end_label);
        } else {
            this.if_stack.end_label = null;
        }
        while (this.SP > this.if_stack.start_stack_size) {
            popType();
        }
        this.SP = this.if_stack.start_stack_size;
        if (else_label != null) {
            else_label.define(this);
        }
        this.if_stack.doing_else = true;
    }

    public final void emitFi() {
        boolean make_unreachable = false;
        if (this.if_stack.doing_else) {
            if (this.if_stack.then_stacked_types != null) {
                int then_clause_stack_size = this.if_stack.start_stack_size + this.if_stack.stack_growth;
                if (!reachableHere()) {
                    if (this.if_stack.stack_growth > 0) {
                        System.arraycopy(this.if_stack.then_stacked_types, 0, this.stack_types, this.if_stack.start_stack_size, this.if_stack.stack_growth);
                    }
                    this.SP = then_clause_stack_size;
                } else if (this.SP != then_clause_stack_size) {
                    throw new Error("at PC " + this.PC + ": SP at end of 'then' was " + then_clause_stack_size + " while SP at end of 'else' was " + this.SP);
                }
            } else if (this.unreachable_here) {
                make_unreachable = true;
            }
        } else if (reachableHere() && this.SP != this.if_stack.start_stack_size) {
            throw new Error("at PC " + this.PC + " then clause grows stack with no else clause");
        }
        if (this.if_stack.end_label != null) {
            this.if_stack.end_label.define(this);
        }
        if (make_unreachable) {
            setUnreachable();
        }
        this.if_stack = this.if_stack.previous;
    }

    public final void emitConvert(Type from, Type to) {
        String to_sig = to.getSignature();
        String from_sig = from.getSignature();
        int op = -1;
        if (to_sig.length() == 1 || from_sig.length() == 1) {
            char to_sig0 = to_sig.charAt(0);
            char charAt = from_sig.charAt(0);
            if (charAt != to_sig0) {
                if (from.size < 4) {
                    charAt = Access.INNERCLASS_CONTEXT;
                }
                if (to.size < 4) {
                    emitConvert(from, Type.intType);
                    charAt = Access.INNERCLASS_CONTEXT;
                }
                if (charAt != to_sig0) {
                    switch (charAt) {
                        case 'D':
                            switch (to_sig0) {
                                case 'F':
                                    op = 144;
                                    break;
                                case 'I':
                                    op = 142;
                                    break;
                                case 'J':
                                    op = 143;
                                    break;
                                default:
                                    break;
                            }
                        case 'F':
                            switch (to_sig0) {
                                case 'D':
                                    op = 141;
                                    break;
                                case 'I':
                                    op = 139;
                                    break;
                                case 'J':
                                    op = 140;
                                    break;
                                default:
                                    break;
                            }
                        case 'I':
                            switch (to_sig0) {
                                case 'B':
                                    op = 145;
                                    break;
                                case 'C':
                                    op = 146;
                                    break;
                                case 'D':
                                    op = 135;
                                    break;
                                case 'F':
                                    op = 134;
                                    break;
                                case 'J':
                                    op = 133;
                                    break;
                                case 'S':
                                    op = 147;
                                    break;
                                default:
                                    break;
                            }
                        case 'J':
                            switch (to_sig0) {
                                case 'D':
                                    op = 138;
                                    break;
                                case 'F':
                                    op = 137;
                                    break;
                                case 'I':
                                    op = 136;
                                    break;
                                default:
                                    break;
                            }
                    }
                }
                return;
            }
            return;
        }
        if (op < 0) {
            throw new Error("unsupported CodeAttr.emitConvert");
        }
        reserve(1);
        popType();
        put1(op);
        pushType(to);
    }

    private void emitCheckcast(Type type, int opcode) {
        reserve(3);
        popType();
        put1(opcode);
        if (type instanceof ObjectType) {
            putIndex2(getConstants().addClass((ObjectType) type));
            return;
        }
        throw new Error("unimplemented type " + type + " in emitCheckcast/emitInstanceof");
    }

    public static boolean castNeeded(Type top, Type required) {
        if (top instanceof UninitializedType) {
            top = ((UninitializedType) top).getImplementationType();
        }
        while (top != required) {
            if ((required instanceof ClassType) && (top instanceof ClassType) && ((ClassType) top).isSubclass((ClassType) required)) {
                return false;
            }
            if (!(required instanceof ArrayType) || !(top instanceof ArrayType)) {
                return true;
            }
            required = ((ArrayType) required).getComponentType();
            top = ((ArrayType) top).getComponentType();
        }
        return false;
    }

    public void emitCheckcast(Type type) {
        if (castNeeded(topType(), type)) {
            emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
            pushType(type);
        }
    }

    public void emitInstanceof(Type type) {
        emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP);
        pushType(Type.booleanType);
    }

    public final void emitThrow() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY);
        setUnreachable();
    }

    public final void emitMonitorEnter() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE);
    }

    public final void emitMonitorExit() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN);
    }

    public final void emitReturn() {
        if (this.try_stack != null) {
            Error error = new Error();
        }
        emitRawReturn();
    }

    final void emitRawReturn() {
        if (getMethod().getReturnType().size == 0) {
            reserve(1);
            put1(177);
        } else {
            emitTypedOp(172, popType().promote());
        }
        setUnreachable();
    }

    public void addHandler(int start_pc, int end_pc, int handler_pc, int catch_type) {
        int index = this.exception_table_length * 4;
        if (this.exception_table == null) {
            this.exception_table = new short[20];
        } else if (this.exception_table.length <= index) {
            short[] new_table = new short[(this.exception_table.length * 2)];
            System.arraycopy(this.exception_table, 0, new_table, 0, index);
            this.exception_table = new_table;
        }
        int i = index + 1;
        this.exception_table[index] = (short) start_pc;
        index = i + 1;
        this.exception_table[i] = (short) end_pc;
        i = index + 1;
        this.exception_table[index] = (short) handler_pc;
        index = i + 1;
        this.exception_table[i] = (short) catch_type;
        this.exception_table_length++;
    }

    public void addHandler(Label start_try, Label end_try, ClassType catch_type) {
        int catch_type_index;
        Type handler_class;
        ConstantPool constants = getConstants();
        if (catch_type == null) {
            catch_type_index = 0;
        } else {
            catch_type_index = constants.addClass((ObjectType) catch_type).index;
        }
        fixupAdd(11, start_try);
        fixupAdd(12, catch_type_index, end_try);
        Label handler = new Label();
        handler.localTypes = start_try.localTypes;
        handler.stackTypes = new Type[1];
        if (catch_type == null) {
            handler_class = Type.javalangThrowableType;
        } else {
            handler_class = catch_type;
        }
        handler.stackTypes[0] = handler_class;
        setTypes(handler);
        fixupAdd(13, 0, handler);
    }

    public void emitWithCleanupStart() {
        int savedSP = this.SP;
        this.SP = 0;
        emitTryStart(false, null);
        this.SP = savedSP;
    }

    public void emitWithCleanupCatch(Variable catchVar) {
        Type[] savedTypes;
        emitTryEnd();
        if (this.SP > 0) {
            savedTypes = new Type[this.SP];
            System.arraycopy(this.stack_types, 0, savedTypes, 0, this.SP);
            this.SP = 0;
        } else {
            savedTypes = null;
        }
        this.try_stack.savedTypes = savedTypes;
        this.try_stack.saved_result = catchVar;
        int save_SP = this.SP;
        emitCatchStart(catchVar);
    }

    public void emitWithCleanupDone() {
        Variable catchVar = this.try_stack.saved_result;
        this.try_stack.saved_result = null;
        if (catchVar != null) {
            emitLoad(catchVar);
        }
        emitThrow();
        emitCatchEnd();
        Type[] savedTypes = this.try_stack.savedTypes;
        emitTryCatchEnd();
        if (savedTypes != null) {
            this.SP = savedTypes.length;
            if (this.SP >= this.stack_types.length) {
                this.stack_types = savedTypes;
                return;
            } else {
                System.arraycopy(savedTypes, 0, this.stack_types, 0, this.SP);
                return;
            }
        }
        this.SP = 0;
    }

    public void emitTryStart(boolean has_finally, Type result_type) {
        Type[] startLocals;
        if (result_type != null && result_type.isVoid()) {
            result_type = null;
        }
        Variable[] savedStack = null;
        if (result_type != null || this.SP > 0) {
            pushScope();
        }
        if (this.SP > 0) {
            savedStack = new Variable[this.SP];
            int i = 0;
            while (this.SP > 0) {
                Variable var = addLocal(topType());
                emitStore(var);
                int i2 = i + 1;
                savedStack[i] = var;
                i = i2;
            }
        }
        TryState try_state = new TryState(this);
        try_state.savedStack = savedStack;
        int usedLocals = this.local_types == null ? 0 : this.local_types.length;
        while (usedLocals > 0 && this.local_types[usedLocals - 1] == null) {
            usedLocals--;
        }
        if (usedLocals == 0) {
            startLocals = Type.typeArray0;
        } else {
            startLocals = new Type[usedLocals];
            System.arraycopy(this.local_types, 0, startLocals, 0, usedLocals);
        }
        try_state.start_try.localTypes = startLocals;
        if (result_type != null) {
            try_state.saved_result = addLocal(result_type);
        }
        if (has_finally) {
            try_state.finally_subr = new Label();
        }
    }

    public void emitTryEnd() {
        emitTryEnd(false);
    }

    private void emitTryEnd(boolean fromFinally) {
        if (!this.try_stack.tryClauseDone) {
            this.try_stack.tryClauseDone = true;
            if (this.try_stack.finally_subr != null) {
                this.try_stack.exception = addLocal(Type.javalangThrowableType);
            }
            gotoFinallyOrEnd(fromFinally);
            this.try_stack.end_try = getLabel();
        }
    }

    public void emitCatchStart(Variable var) {
        emitTryEnd(false);
        setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        ClassType type = var == null ? null : (ClassType) var.getType();
        this.try_stack.try_type = type;
        addHandler(this.try_stack.start_try, this.try_stack.end_try, type);
        if (var != null) {
            emitStore(var);
        }
    }

    public void emitCatchEnd() {
        gotoFinallyOrEnd(false);
        this.try_stack.try_type = null;
    }

    private void gotoFinallyOrEnd(boolean fromFinally) {
        if (reachableHere()) {
            if (this.try_stack.saved_result != null) {
                emitStore(this.try_stack.saved_result);
            }
            if (this.try_stack.end_label == null) {
                this.try_stack.end_label = new Label();
            }
            if (this.try_stack.finally_subr == null || useJsr()) {
                if (this.try_stack.finally_subr != null) {
                    emitJsr(this.try_stack.finally_subr);
                }
                emitGoto(this.try_stack.end_label);
                return;
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(0);
            }
            emitPushNull();
            if (!fromFinally) {
                emitGoto(this.try_stack.finally_subr);
            }
        }
    }

    public void emitFinallyStart() {
        emitTryEnd(true);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        this.try_stack.end_try = getLabel();
        pushScope();
        if (useJsr()) {
            this.SP = 0;
            emitCatchStart(null);
            emitStore(this.try_stack.exception);
            emitJsr(this.try_stack.finally_subr);
            emitLoad(this.try_stack.exception);
            emitThrow();
        } else {
            setUnreachable();
            int fragment_cookie = beginFragment(new Label(this));
            addHandler(this.try_stack.start_try, this.try_stack.end_try, Type.javalangThrowableType);
            if (this.try_stack.saved_result != null) {
                emitStoreDefaultValue(this.try_stack.saved_result);
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(-1);
                emitSwap();
            }
            emitGoto(this.try_stack.finally_subr);
            endFragment(fragment_cookie);
        }
        this.try_stack.finally_subr.define(this);
        if (useJsr()) {
            Type ret_addr_type = Type.objectType;
            this.try_stack.finally_ret_addr = addLocal(ret_addr_type);
            pushType(ret_addr_type);
            emitStore(this.try_stack.finally_ret_addr);
        }
    }

    public void emitFinallyEnd() {
        if (useJsr()) {
            emitRet(this.try_stack.finally_ret_addr);
        } else if (this.try_stack.end_label == null && this.try_stack.exitCases == null) {
            emitThrow();
        } else {
            emitStore(this.try_stack.exception);
            emitLoad(this.try_stack.exception);
            emitIfNotNull();
            emitLoad(this.try_stack.exception);
            emitThrow();
            emitElse();
            ExitableBlock exit = this.try_stack.exitCases;
            if (exit != null) {
                SwitchState sw = startSwitch();
                while (exit != null) {
                    ExitableBlock next = exit.nextCase;
                    exit.nextCase = null;
                    exit.currentTryState = null;
                    TryState nextTry = TryState.outerHandler(this.try_stack.previous, exit.initialTryState);
                    if (nextTry == exit.initialTryState) {
                        sw.addCaseGoto(exit.switchCase, this, exit.endLabel);
                    } else {
                        sw.addCase(exit.switchCase, this);
                        exit.exit(nextTry);
                    }
                    exit = next;
                }
                this.try_stack.exitCases = null;
                sw.addDefault(this);
                sw.finish(this);
            }
            emitFi();
            setUnreachable();
        }
        popScope();
        this.try_stack.finally_subr = null;
    }

    public void emitTryCatchEnd() {
        if (this.try_stack.finally_subr != null) {
            emitFinallyEnd();
        }
        Variable[] vars = this.try_stack.savedStack;
        if (this.try_stack.end_label == null) {
            setUnreachable();
        } else {
            setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
            this.try_stack.end_label.define(this);
            if (vars != null) {
                int i = vars.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    Variable v = vars[i];
                    if (v != null) {
                        emitLoad(v);
                    }
                }
            }
            if (this.try_stack.saved_result != null) {
                emitLoad(this.try_stack.saved_result);
            }
        }
        if (!(this.try_stack.saved_result == null && vars == null)) {
            popScope();
        }
        this.try_stack = this.try_stack.previous;
    }

    public final TryState getCurrentTry() {
        return this.try_stack;
    }

    public final boolean isInTry() {
        return this.try_stack != null;
    }

    public SwitchState startSwitch() {
        SwitchState sw = new SwitchState(this);
        sw.switchValuePushed(this);
        return sw;
    }

    public void emitTailCall(boolean pop_args, Scope scope) {
        if (pop_args) {
            int arg_slots;
            int i;
            Method meth = getMethod();
            if ((meth.access_flags & 8) != 0) {
                arg_slots = 0;
            } else {
                arg_slots = 1;
            }
            int i2 = meth.arg_types.length;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                if (meth.arg_types[i2].size > 4) {
                    i = 2;
                } else {
                    i = 1;
                }
                arg_slots += i;
            }
            i2 = meth.arg_types.length;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                if (meth.arg_types[i2].size > 4) {
                    i = 2;
                } else {
                    i = 1;
                }
                arg_slots -= i;
                emitStore(this.locals.used[arg_slots]);
            }
        }
        emitGoto(scope.start);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processFixups() {
        /*
        r32 = this;
        r0 = r32;
        r0 = r0.fixup_count;
        r29 = r0;
        if (r29 > 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r5 = 0;
        r0 = r32;
        r8 = r0.fixup_count;
        r29 = 9;
        r30 = 0;
        r31 = 0;
        r0 = r32;
        r1 = r29;
        r2 = r30;
        r3 = r31;
        r0.fixupAdd(r1, r2, r3);
        r7 = 0;
    L_0x0020:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r19 = r29[r7];
        r9 = r19 & 15;
        r19 = r19 >> 4;
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r10 = r29[r7];
        switch(r9) {
            case 0: goto L_0x0041;
            case 1: goto L_0x0047;
            case 2: goto L_0x0052;
            case 3: goto L_0x0041;
            case 4: goto L_0x0055;
            case 5: goto L_0x008c;
            case 6: goto L_0x009e;
            case 7: goto L_0x0037;
            case 8: goto L_0x0041;
            case 9: goto L_0x00c4;
            case 10: goto L_0x00b0;
            case 11: goto L_0x003f;
            case 12: goto L_0x0037;
            case 13: goto L_0x0037;
            case 14: goto L_0x0044;
            default: goto L_0x0037;
        };
    L_0x0037:
        r29 = new java.lang.Error;
        r30 = "unexpected fixup";
        r29.<init>(r30);
        throw r29;
    L_0x003f:
        r7 = r7 + 2;
    L_0x0041:
        r7 = r7 + 1;
        goto L_0x0020;
    L_0x0044:
        r7 = r7 + 1;
        goto L_0x0041;
    L_0x0047:
        r0 = r10.position;
        r29 = r0;
        r29 = r29 + r5;
        r0 = r29;
        r10.position = r0;
        goto L_0x0041;
    L_0x0052:
        r5 = r5 + 3;
        goto L_0x0041;
    L_0x0055:
        r0 = r10.first_fixup;
        r29 = r0;
        r30 = r7 + 1;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x008c;
    L_0x0061:
        r29 = r7 + 1;
        r0 = r32;
        r1 = r29;
        r29 = r0.fixupOffset(r1);
        r30 = r19 + 3;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x008c;
    L_0x0073:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r30 = r19 << 4;
        r30 = r30 | 8;
        r29[r7] = r30;
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r30 = 0;
        r29[r7] = r30;
        r5 = r5 + -3;
        goto L_0x0041;
    L_0x008c:
        r0 = r32;
        r0 = r0.PC;
        r29 = r0;
        r30 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r0 = r29;
        r1 = r30;
        if (r0 < r1) goto L_0x0041;
    L_0x009b:
        r5 = r5 + 2;
        goto L_0x0041;
    L_0x009e:
        r0 = r32;
        r0 = r0.PC;
        r29 = r0;
        r30 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r0 = r29;
        r1 = r30;
        if (r0 < r1) goto L_0x0041;
    L_0x00ad:
        r5 = r5 + 5;
        goto L_0x0041;
    L_0x00b0:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r0 = r32;
        r0 = r0.fixup_labels;
        r30 = r0;
        r31 = r7 + 1;
        r30 = r30[r31];
        r29[r8] = r30;
        r8 = r19;
    L_0x00c4:
        r29 = r7 + 1;
        r0 = r32;
        r0 = r0.fixup_count;
        r30 = r0;
        r0 = r29;
        r1 = r30;
        if (r0 < r1) goto L_0x0129;
    L_0x00d2:
        r0 = r32;
        r4 = r0.PC;
    L_0x00d6:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r30 = r4 << 4;
        r30 = r30 | 9;
        r29[r7] = r30;
        if (r10 != 0) goto L_0x0142;
    L_0x00e4:
        r0 = r32;
        r15 = r0.PC;
        r5 = 0;
        r7 = 0;
    L_0x00ea:
        r0 = r32;
        r0 = r0.fixup_count;
        r29 = r0;
        r0 = r29;
        if (r7 >= r0) goto L_0x0225;
    L_0x00f4:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r19 = r29[r7];
        r9 = r19 & 15;
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r10 = r29[r7];
        if (r10 == 0) goto L_0x0150;
    L_0x0108:
        r0 = r10.position;
        r29 = r0;
        if (r29 >= 0) goto L_0x0150;
    L_0x010e:
        r29 = new java.lang.Error;
        r30 = new java.lang.StringBuilder;
        r30.<init>();
        r31 = "undefined label ";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r10);
        r30 = r30.toString();
        r29.<init>(r30);
        throw r29;
    L_0x0129:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r30 = r7 + 1;
        r29 = r29[r30];
        r0 = r29;
        r0 = r0.first_fixup;
        r29 = r0;
        r0 = r32;
        r1 = r29;
        r4 = r0.fixupOffset(r1);
        goto L_0x00d6;
    L_0x0142:
        r7 = r10.first_fixup;
        r0 = r32;
        r18 = r0.fixupOffset(r7);
        r29 = r4 + r5;
        r5 = r29 - r18;
        goto L_0x0020;
    L_0x0150:
        if (r10 == 0) goto L_0x01ab;
    L_0x0152:
        r29 = 4;
        r0 = r29;
        if (r9 < r0) goto L_0x01ab;
    L_0x0158:
        r29 = 7;
        r0 = r29;
        if (r9 > r0) goto L_0x01ab;
    L_0x015e:
        r0 = r10.first_fixup;
        r29 = r0;
        r29 = r29 + 1;
        r0 = r32;
        r0 = r0.fixup_count;
        r30 = r0;
        r0 = r29;
        r1 = r30;
        if (r0 >= r1) goto L_0x01ab;
    L_0x0170:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r0 = r10.first_fixup;
        r30 = r0;
        r30 = r30 + 1;
        r29 = r29[r30];
        r0 = r32;
        r0 = r0.fixup_offsets;
        r30 = r0;
        r0 = r10.first_fixup;
        r31 = r0;
        r30 = r30[r31];
        r30 = r30 & 15;
        r30 = r30 | 4;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x01ab;
    L_0x0194:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r0 = r10.first_fixup;
        r30 = r0;
        r30 = r30 + 1;
        r10 = r29[r30];
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r29[r7] = r10;
        goto L_0x0150;
    L_0x01ab:
        r19 = r19 >> 4;
        switch(r9) {
            case 0: goto L_0x01ca;
            case 1: goto L_0x01d6;
            case 2: goto L_0x01dd;
            case 3: goto L_0x01ca;
            case 4: goto L_0x01e8;
            case 5: goto L_0x01e8;
            case 6: goto L_0x01e8;
            case 7: goto L_0x01b0;
            case 8: goto L_0x01d1;
            case 9: goto L_0x0223;
            case 10: goto L_0x01b0;
            case 11: goto L_0x01b8;
            case 12: goto L_0x01b0;
            case 13: goto L_0x01b0;
            case 14: goto L_0x01ce;
            default: goto L_0x01b0;
        };
    L_0x01b0:
        r29 = new java.lang.Error;
        r30 = "unexpected fixup";
        r29.<init>(r30);
        throw r29;
    L_0x01b8:
        r7 = r7 + 2;
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r29 = r29[r7];
        r30 = r19 + r5;
        r0 = r30;
        r1 = r29;
        r1.position = r0;
    L_0x01ca:
        r7 = r7 + 1;
        goto L_0x00ea;
    L_0x01ce:
        r7 = r7 + 1;
        goto L_0x01ca;
    L_0x01d1:
        r5 = r5 + -3;
        r15 = r15 + -3;
        goto L_0x01ca;
    L_0x01d6:
        r29 = r19 + r5;
        r0 = r29;
        r10.position = r0;
        goto L_0x01ca;
    L_0x01dd:
        r29 = r19 + r5;
        r29 = 3 - r29;
        r24 = r29 & 3;
        r5 = r5 + r24;
        r15 = r15 + r24;
        goto L_0x01ca;
    L_0x01e8:
        r0 = r10.position;
        r29 = r0;
        r30 = r19 + r5;
        r27 = r29 - r30;
        r0 = r27;
        r0 = (short) r0;
        r29 = r0;
        r0 = r29;
        r1 = r27;
        if (r0 != r1) goto L_0x0208;
    L_0x01fb:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r30 = r19 << 4;
        r30 = r30 | 7;
        r29[r7] = r30;
        goto L_0x01ca;
    L_0x0208:
        r29 = 6;
        r0 = r29;
        if (r9 != r0) goto L_0x021d;
    L_0x020e:
        r29 = 5;
    L_0x0210:
        r5 = r5 + r29;
        r29 = 6;
        r0 = r29;
        if (r9 != r0) goto L_0x0220;
    L_0x0218:
        r29 = 5;
    L_0x021a:
        r15 = r15 + r29;
        goto L_0x01ca;
    L_0x021d:
        r29 = 2;
        goto L_0x0210;
    L_0x0220:
        r29 = 2;
        goto L_0x021a;
    L_0x0223:
        if (r10 != 0) goto L_0x0257;
    L_0x0225:
        r12 = new byte[r15];
        r26 = -1;
        r13 = 0;
        r16 = 0;
        r29 = 0;
        r0 = r32;
        r1 = r29;
        r17 = r0.fixupOffset(r1);
        r20 = -1;
        r25 = 0;
        r21 = 0;
        r22 = r21;
        r14 = r13;
    L_0x023f:
        r0 = r22;
        r1 = r17;
        if (r0 >= r1) goto L_0x0265;
    L_0x0245:
        r13 = r14 + 1;
        r0 = r32;
        r0 = r0.code;
        r29 = r0;
        r21 = r22 + 1;
        r29 = r29[r22];
        r12[r14] = r29;
        r22 = r21;
        r14 = r13;
        goto L_0x023f;
    L_0x0257:
        r7 = r10.first_fixup;
        r0 = r32;
        r18 = r0.fixupOffset(r7);
        r29 = r19 + r5;
        r5 = r29 - r18;
        goto L_0x00ea;
    L_0x0265:
        r0 = r32;
        r0 = r0.fixup_offsets;
        r29 = r0;
        r29 = r29[r16];
        r9 = r29 & 15;
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r10 = r29[r16];
        if (r25 == 0) goto L_0x0294;
    L_0x0279:
        r0 = r25;
        r0 = r0.position;
        r29 = r0;
        r0 = r29;
        if (r0 >= r14) goto L_0x0294;
    L_0x0283:
        r0 = r32;
        r0 = r0.stackMap;
        r29 = r0;
        r0 = r29;
        r1 = r25;
        r2 = r32;
        r0.emitStackMapEntry(r1, r2);
        r25 = 0;
    L_0x0294:
        if (r25 == 0) goto L_0x02a8;
    L_0x0296:
        r0 = r25;
        r0 = r0.position;
        r29 = r0;
        r0 = r29;
        if (r0 <= r14) goto L_0x02a8;
    L_0x02a0:
        r29 = new java.lang.Error;
        r30 = "labels out of order";
        r29.<init>(r30);
        throw r29;
    L_0x02a8:
        switch(r9) {
            case 0: goto L_0x02b3;
            case 1: goto L_0x02c5;
            case 2: goto L_0x0386;
            case 3: goto L_0x02ab;
            case 4: goto L_0x0318;
            case 5: goto L_0x0318;
            case 6: goto L_0x0318;
            case 7: goto L_0x02eb;
            case 8: goto L_0x02e7;
            case 9: goto L_0x04b4;
            case 10: goto L_0x02ab;
            case 11: goto L_0x042b;
            case 12: goto L_0x02ab;
            case 13: goto L_0x02ab;
            case 14: goto L_0x047d;
            default: goto L_0x02ab;
        };
    L_0x02ab:
        r29 = new java.lang.Error;
        r30 = "unexpected fixup";
        r29.<init>(r30);
        throw r29;
    L_0x02b3:
        r21 = r22;
        r13 = r14;
    L_0x02b6:
        r16 = r16 + 1;
        r0 = r32;
        r1 = r16;
        r17 = r0.fixupOffset(r1);
        r22 = r21;
        r14 = r13;
        goto L_0x023f;
    L_0x02c5:
        r0 = r32;
        r0 = r0.stackMap;
        r29 = r0;
        if (r29 == 0) goto L_0x0526;
    L_0x02cd:
        if (r10 == 0) goto L_0x0526;
    L_0x02cf:
        r0 = r10.stackTypes;
        r29 = r0;
        if (r29 == 0) goto L_0x0526;
    L_0x02d5:
        r0 = r10.needsStackMapEntry;
        r29 = r0;
        if (r29 == 0) goto L_0x0526;
    L_0x02db:
        r0 = r32;
        r1 = r25;
        r25 = r0.mergeLabels(r1, r10);
        r21 = r22;
        r13 = r14;
        goto L_0x02b6;
    L_0x02e7:
        r21 = r22 + 3;
        r13 = r14;
        goto L_0x02b6;
    L_0x02eb:
        r0 = r10.position;
        r29 = r0;
        r5 = r29 - r14;
        r13 = r14 + 1;
        r0 = r32;
        r0 = r0.code;
        r29 = r0;
        r29 = r29[r22];
        r12[r14] = r29;
        r14 = r13 + 1;
        r29 = r5 >> 8;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r13] = r29;
        r13 = r14 + 1;
        r0 = r5 & 255;
        r29 = r0;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r14] = r29;
        r21 = r22 + 3;
        goto L_0x02b6;
    L_0x0318:
        r0 = r10.position;
        r29 = r0;
        r5 = r29 - r14;
        r0 = r32;
        r0 = r0.code;
        r29 = r0;
        r23 = r29[r22];
        r29 = 6;
        r0 = r29;
        if (r9 != r0) goto L_0x037d;
    L_0x032c:
        r0 = r32;
        r1 = r23;
        r23 = r0.invert_opcode(r1);
        r13 = r14 + 1;
        r12[r14] = r23;
        r14 = r13 + 1;
        r29 = 0;
        r12[r13] = r29;
        r13 = r14 + 1;
        r29 = 8;
        r12[r14] = r29;
        r23 = -56;
    L_0x0346:
        r14 = r13 + 1;
        r12[r13] = r23;
        r13 = r14 + 1;
        r29 = r5 >> 24;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r14] = r29;
        r14 = r13 + 1;
        r29 = r5 >> 16;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r13] = r29;
        r13 = r14 + 1;
        r29 = r5 >> 8;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r14] = r29;
        r14 = r13 + 1;
        r0 = r5 & 255;
        r29 = r0;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r13] = r29;
        r21 = r22 + 3;
        r13 = r14;
        goto L_0x02b6;
    L_0x037d:
        r29 = r23 + 33;
        r0 = r29;
        r0 = (byte) r0;
        r23 = r0;
        r13 = r14;
        goto L_0x0346;
    L_0x0386:
        r29 = 3 - r14;
        r24 = r29 & 3;
        r28 = r14;
        r13 = r14 + 1;
        r0 = r32;
        r0 = r0.code;
        r29 = r0;
        r21 = r22 + 1;
        r29 = r29[r22];
        r12[r14] = r29;
        r14 = r13;
    L_0x039b:
        r24 = r24 + -1;
        if (r24 < 0) goto L_0x0523;
    L_0x039f:
        r13 = r14 + 1;
        r29 = 0;
        r12[r14] = r29;
        r14 = r13;
        goto L_0x039b;
    L_0x03a7:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r29 = r29[r16];
        r0 = r29;
        r0 = r0.position;
        r29 = r0;
        r5 = r29 - r28;
        r13 = r14 + 1;
        r29 = r5 >> 24;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r14] = r29;
        r14 = r13 + 1;
        r29 = r5 >> 16;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r13] = r29;
        r13 = r14 + 1;
        r29 = r5 >> 8;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r14] = r29;
        r14 = r13 + 1;
        r0 = r5 & 255;
        r29 = r0;
        r0 = r29;
        r0 = (byte) r0;
        r29 = r0;
        r12[r13] = r29;
        r21 = r22 + 4;
        r13 = r14;
    L_0x03e8:
        r0 = r32;
        r0 = r0.fixup_count;
        r29 = r0;
        r0 = r16;
        r1 = r29;
        if (r0 >= r1) goto L_0x02b6;
    L_0x03f4:
        r29 = r16 + 1;
        r0 = r32;
        r1 = r29;
        r29 = r0.fixupKind(r1);
        r30 = 3;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x02b6;
    L_0x0406:
        r16 = r16 + 1;
        r0 = r32;
        r1 = r16;
        r19 = r0.fixupOffset(r1);
        r22 = r21;
        r14 = r13;
    L_0x0413:
        r0 = r22;
        r1 = r19;
        if (r0 >= r1) goto L_0x03a7;
    L_0x0419:
        r13 = r14 + 1;
        r0 = r32;
        r0 = r0.code;
        r29 = r0;
        r21 = r22 + 1;
        r29 = r29[r22];
        r12[r14] = r29;
        r22 = r21;
        r14 = r13;
        goto L_0x0413;
    L_0x042b:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r30 = r16 + 2;
        r10 = r29[r30];
        r29 = r16 + 1;
        r0 = r32;
        r1 = r29;
        r6 = r0.fixupOffset(r1);
        r0 = r32;
        r0 = r0.stackMap;
        r29 = r0;
        if (r29 == 0) goto L_0x044f;
    L_0x0447:
        r0 = r32;
        r1 = r25;
        r25 = r0.mergeLabels(r1, r10);
    L_0x044f:
        r0 = r32;
        r0 = r0.fixup_labels;
        r29 = r0;
        r29 = r29[r16];
        r0 = r29;
        r0 = r0.position;
        r29 = r0;
        r0 = r32;
        r0 = r0.fixup_labels;
        r30 = r0;
        r31 = r16 + 1;
        r30 = r30[r31];
        r0 = r30;
        r0 = r0.position;
        r30 = r0;
        r0 = r32;
        r1 = r29;
        r2 = r30;
        r0.addHandler(r1, r2, r14, r6);
        r16 = r16 + 2;
        r21 = r22;
        r13 = r14;
        goto L_0x02b6;
    L_0x047d:
        r0 = r32;
        r0 = r0.lines;
        r29 = r0;
        if (r29 != 0) goto L_0x0494;
    L_0x0485:
        r29 = new gnu.bytecode.LineNumbersAttr;
        r0 = r29;
        r1 = r32;
        r0.<init>(r1);
        r0 = r29;
        r1 = r32;
        r1.lines = r0;
    L_0x0494:
        r16 = r16 + 1;
        r0 = r32;
        r1 = r16;
        r11 = r0.fixupOffset(r1);
        r0 = r26;
        if (r11 == r0) goto L_0x04ad;
    L_0x04a2:
        r0 = r32;
        r0 = r0.lines;
        r29 = r0;
        r0 = r29;
        r0.put(r11, r14);
    L_0x04ad:
        r26 = r11;
        r21 = r22;
        r13 = r14;
        goto L_0x02b6;
    L_0x04b4:
        if (r10 != 0) goto L_0x04df;
    L_0x04b6:
        if (r15 == r14) goto L_0x04fd;
    L_0x04b8:
        r29 = new java.lang.Error;
        r30 = new java.lang.StringBuilder;
        r30.<init>();
        r31 = "PC confusion new_pc:";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r14);
        r31 = " new_size:";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r15);
        r30 = r30.toString();
        r29.<init>(r30);
        throw r29;
    L_0x04df:
        r0 = r10.first_fixup;
        r16 = r0;
        r0 = r32;
        r1 = r16;
        r21 = r0.fixupOffset(r1);
        r17 = r21;
        r0 = r10.position;
        r29 = r0;
        r0 = r29;
        if (r0 == r14) goto L_0x051f;
    L_0x04f5:
        r29 = new java.lang.Error;
        r30 = "bad pc";
        r29.<init>(r30);
        throw r29;
    L_0x04fd:
        r0 = r32;
        r0.PC = r15;
        r0 = r32;
        r0.code = r12;
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.fixup_count = r0;
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.fixup_labels = r0;
        r29 = 0;
        r0 = r29;
        r1 = r32;
        r1.fixup_offsets = r0;
        goto L_0x0008;
    L_0x051f:
        r22 = r21;
        goto L_0x023f;
    L_0x0523:
        r13 = r14;
        goto L_0x03e8;
    L_0x0526:
        r21 = r22;
        r13 = r14;
        goto L_0x02b6;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.processFixups():void");
    }

    private Label mergeLabels(Label oldLabel, Label newLabel) {
        if (oldLabel != null) {
            newLabel.setTypes(oldLabel);
        }
        return newLabel;
    }

    public void assignConstants(ClassType cl) {
        if (!(this.locals == null || this.locals.container != null || this.locals.isEmpty())) {
            this.locals.addToFrontOf(this);
        }
        processFixups();
        if (this.stackMap != null && this.stackMap.numEntries > 0) {
            this.stackMap.addToFrontOf(this);
        }
        if (instructionLineMode) {
            if (this.lines == null) {
                this.lines = new LineNumbersAttr(this);
            }
            this.lines.linenumber_count = 0;
            int codeLen = getCodeLength();
            for (int i = 0; i < codeLen; i++) {
                this.lines.put(i, i);
            }
        }
        super.assignConstants(cl);
        Attribute.assignConstants(this, cl);
    }

    public final int getLength() {
        return ((getCodeLength() + 12) + (this.exception_table_length * 8)) + Attribute.getLengthAll(this);
    }

    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.max_stack);
        dstr.writeShort(this.max_locals);
        dstr.writeInt(this.PC);
        dstr.write(this.code, 0, this.PC);
        dstr.writeShort(this.exception_table_length);
        int count = this.exception_table_length;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                dstr.writeShort(this.exception_table[i]);
                dstr.writeShort(this.exception_table[i + 1]);
                dstr.writeShort(this.exception_table[i + 2]);
                dstr.writeShort(this.exception_table[i + 3]);
                i += 4;
            } else {
                Attribute.writeAll(this, dstr);
                return;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", max_stack:");
        dst.print(this.max_stack);
        dst.print(", max_locals:");
        dst.print(this.max_locals);
        dst.print(", code_length:");
        int length = getCodeLength();
        dst.println(length);
        disAssemble(dst, 0, length);
        if (this.exception_table_length > 0) {
            dst.print("Exceptions (count: ");
            dst.print(this.exception_table_length);
            dst.println("):");
            int count = this.exception_table_length;
            int i = 0;
            while (true) {
                count--;
                if (count < 0) {
                    break;
                }
                dst.print("  start: ");
                dst.print(this.exception_table[i] & SupportMenu.USER_MASK);
                dst.print(", end: ");
                dst.print(this.exception_table[i + 1] & SupportMenu.USER_MASK);
                dst.print(", handler: ");
                dst.print(this.exception_table[i + 2] & SupportMenu.USER_MASK);
                dst.print(", type: ");
                int catch_type_index = this.exception_table[i + 3] & SupportMenu.USER_MASK;
                if (catch_type_index == 0) {
                    dst.print("0 /* finally */");
                } else {
                    dst.printOptionalIndex(catch_type_index);
                    dst.printConstantTersely(catch_type_index, 7);
                }
                dst.println();
                i += 4;
            }
        }
        dst.printAttributes(this);
    }

    public void disAssemble(ClassTypeWriter dst, int start, int limit) {
        boolean wide = false;
        int i = start;
        while (i < limit) {
            int index;
            int i2 = i + 1;
            int oldpc = i;
            int op = this.code[oldpc] & 255;
            String str = Integer.toString(oldpc);
            int printConstant = 0;
            int j = str.length();
            while (true) {
                j++;
                if (j > 3) {
                    break;
                }
                dst.print(' ');
            }
            dst.print(str);
            dst.print(": ");
            int constant;
            if (op < 120) {
                if (op < 87) {
                    if (op < 3) {
                        print("nop;aconst_null;iconst_m1;", op, dst);
                        i = i2;
                    } else if (op < 9) {
                        dst.print("iconst_");
                        dst.print(op - 3);
                        i = i2;
                    } else if (op < 16) {
                        char typ;
                        if (op < 11) {
                            typ = 'l';
                            op -= 9;
                        } else if (op < 14) {
                            typ = 'f';
                            op -= 11;
                        } else {
                            typ = 'd';
                            op -= 14;
                        }
                        dst.print(typ);
                        dst.print("const_");
                        dst.print(op);
                        i = i2;
                    } else if (op >= 21) {
                        String load_or_store;
                        if (op < 54) {
                            load_or_store = "load";
                        } else {
                            load_or_store = "store";
                            op -= 33;
                        }
                        if (op < 26) {
                            index = -1;
                            op -= 21;
                        } else if (op < 46) {
                            op -= 26;
                            index = op % 4;
                            op >>= 2;
                        } else {
                            index = -2;
                            op -= 46;
                        }
                        dst.print("ilfdabcs".charAt(op));
                        if (index == -2) {
                            dst.write(97);
                        }
                        dst.print(load_or_store);
                        if (index >= 0) {
                            dst.write(95);
                            dst.print(index);
                        } else if (index == -1) {
                            if (wide) {
                                index = readUnsignedShort(i2);
                                i2 += 2;
                            } else {
                                index = this.code[i2] & 255;
                                i2++;
                            }
                            wide = false;
                            dst.print(' ');
                            dst.print(index);
                        }
                        i = i2;
                    } else if (op < 18) {
                        print("bipush ;sipush ;", op - 16, dst);
                        if (op == 16) {
                            i = i2 + 1;
                            constant = this.code[i2];
                            i2 = i;
                        } else {
                            constant = (short) readUnsignedShort(i2);
                            i2 += 2;
                        }
                        dst.print(constant);
                        i = i2;
                    } else {
                        printConstant = op == 18 ? 1 : 2;
                        print("ldc;ldc_w;ldc2_w;", op - 18, dst);
                        i = i2;
                    }
                } else if (op < 96) {
                    print("pop;pop2;dup;dup_x1;dup_x2;dup2;dup2_x1;dup2_x2;swap;", op - 87, dst);
                    i = i2;
                } else {
                    dst.print("ilfda".charAt((op - 96) % 4));
                    print("add;sub;mul;div;rem;neg;", (op - 96) >> 2, dst);
                    i = i2;
                }
            } else if (op < 170) {
                if (op < 132) {
                    dst.print((op & 1) == 0 ? 'i' : 'l');
                    print("shl;shr;ushr;and;or;xor;", (op - 120) >> 1, dst);
                    i = i2;
                } else if (op == 132) {
                    int var_index;
                    dst.print("iinc");
                    if (wide) {
                        var_index = readUnsignedShort(i2);
                        i2 += 2;
                        constant = (short) readUnsignedShort(i2);
                        i2 += 2;
                        wide = false;
                    } else {
                        i = i2 + 1;
                        var_index = this.code[i2] & 255;
                        i2 = i + 1;
                        constant = this.code[i];
                    }
                    dst.print(' ');
                    dst.print(var_index);
                    dst.print(' ');
                    dst.print(constant);
                    i = i2;
                } else if (op < 148) {
                    dst.print("ilfdi".charAt((op - 133) / 3));
                    dst.print('2');
                    dst.print("lfdifdildilfbcs".charAt(op - 133));
                    i = i2;
                } else if (op < 153) {
                    print("lcmp;fcmpl;fcmpg;dcmpl;dcmpg;", op - 148, dst);
                    i = i2;
                } else if (op < 169) {
                    if (op < 159) {
                        dst.print("if");
                        print("eq;ne;lt;ge;gt;le;", op - 153, dst);
                    } else if (op < 167) {
                        if (op < 165) {
                            dst.print("if_icmp");
                        } else {
                            dst.print("if_acmp");
                            op -= 6;
                        }
                        print("eq;ne;lt;ge;gt;le;", op - 159, dst);
                    } else {
                        print("goto;jsr;", op - 167, dst);
                    }
                    delta = (short) readUnsignedShort(i2);
                    i2 += 2;
                    dst.print(' ');
                    dst.print(oldpc + delta);
                    i = i2;
                } else {
                    dst.print("ret ");
                    if (wide) {
                        index = readUnsignedShort(i2) + 2;
                    } else {
                        index = this.code[i2] & 255;
                        i2++;
                    }
                    wide = false;
                    dst.print(index);
                    i = i2;
                }
            } else if (op < 172) {
                if (this.fixup_count <= 0) {
                    i2 = (i2 + 3) & -4;
                }
                int code_offset = readInt(i2);
                i2 += 4;
                if (op != 170) {
                    dst.print("lookupswitch");
                    int npairs = readInt(i2);
                    i2 += 4;
                    dst.print(" npairs: ");
                    dst.print(npairs);
                    dst.print(" default: ");
                    dst.print(oldpc + code_offset);
                    while (true) {
                        npairs--;
                        if (npairs < 0) {
                            break;
                        }
                        int match = readInt(i2);
                        i2 += 4;
                        code_offset = readInt(i2);
                        i2 += 4;
                        dst.println();
                        dst.print("  ");
                        dst.print(match);
                        dst.print(": ");
                        dst.print(oldpc + code_offset);
                    }
                } else {
                    dst.print("tableswitch");
                    int low = readInt(i2);
                    i2 += 4;
                    int high = readInt(i2);
                    i2 += 4;
                    dst.print(" low: ");
                    dst.print(low);
                    dst.print(" high: ");
                    dst.print(high);
                    dst.print(" default: ");
                    dst.print(oldpc + code_offset);
                    while (low <= high) {
                        code_offset = readInt(i2);
                        i2 += 4;
                        dst.println();
                        dst.print("  ");
                        dst.print(low);
                        dst.print(": ");
                        dst.print(oldpc + code_offset);
                        low++;
                    }
                }
                i = i2;
            } else if (op < 178) {
                if (op < 177) {
                    dst.print("ilfda".charAt(op - 172));
                }
                dst.print("return");
                i = i2;
            } else if (op < 182) {
                print("getstatic;putstatic;getfield;putfield;", op - 178, dst);
                printConstant = 2;
                i = i2;
            } else if (op < 185) {
                dst.print("invoke");
                print("virtual;special;static;", op - 182, dst);
                printConstant = 2;
                i = i2;
            } else if (op == 185) {
                dst.print("invokeinterface (");
                index = readUnsignedShort(i2);
                i2 += 2;
                int args = this.code[i2] & 255;
                i2 += 2;
                dst.print(args + " args)");
                dst.printConstantOperand(index);
                i = i2;
            } else {
                if (op < 196) {
                    print("186;new;newarray;anewarray;arraylength;athrow;checkcast;instanceof;monitorenter;monitorexit;", op - 186, dst);
                    if (op == 187 || op == 189 || op == 192 || op == 193) {
                        printConstant = 2;
                        i = i2;
                    } else if (op == 188) {
                        i = i2 + 1;
                        int type = this.code[i2];
                        dst.print(' ');
                        if (type < 4 || type > 11) {
                            dst.print(type);
                        } else {
                            print("boolean;char;float;double;byte;short;int;long;", type - 4, dst);
                        }
                    }
                } else if (op == 196) {
                    dst.print("wide");
                    wide = true;
                    i = i2;
                } else if (op == 197) {
                    dst.print("multianewarray");
                    index = readUnsignedShort(i2);
                    i2 += 2;
                    dst.printConstantOperand(index);
                    i = i2 + 1;
                    int dims = this.code[i2] & 255;
                    dst.print(' ');
                    dst.print(dims);
                } else if (op < 200) {
                    print("ifnull;ifnonnull;", op - 198, dst);
                    delta = (short) readUnsignedShort(i2);
                    i2 += 2;
                    dst.print(' ');
                    dst.print(oldpc + delta);
                    i = i2;
                } else if (op < 202) {
                    print("goto_w;jsr_w;", op - 200, dst);
                    delta = readInt(i2);
                    i2 += 4;
                    dst.print(' ');
                    dst.print(oldpc + delta);
                    i = i2;
                } else {
                    dst.print(op);
                }
                i = i2;
            }
            if (printConstant > 0) {
                if (printConstant == 1) {
                    i2 = i + 1;
                    index = this.code[i] & 255;
                } else {
                    index = readUnsignedShort(i);
                    i2 = i + 2;
                }
                dst.printConstantOperand(index);
            } else {
                i2 = i;
            }
            dst.println();
            i = i2;
        }
    }

    private int readUnsignedShort(int offset) {
        return ((this.code[offset] & 255) << 8) | (this.code[offset + 1] & 255);
    }

    private int readInt(int offset) {
        return (readUnsignedShort(offset) << 16) | readUnsignedShort(offset + 2);
    }

    private void print(String str, int i, PrintWriter dst) {
        int last = 0;
        int pos = -1;
        while (i >= 0) {
            last = pos + 1;
            pos = str.indexOf(59, last);
            i--;
        }
        dst.write(str, last, pos - last);
    }

    public int beginFragment(Label after) {
        return beginFragment(new Label(), after);
    }

    public int beginFragment(Label start, Label after) {
        int i = this.fixup_count;
        fixupAdd(10, after);
        start.define(this);
        return i;
    }

    public void endFragment(int cookie) {
        this.fixup_offsets[cookie] = (this.fixup_count << 4) | 10;
        Label after = this.fixup_labels[cookie];
        fixupAdd(9, 0, null);
        after.define(this);
    }
}
