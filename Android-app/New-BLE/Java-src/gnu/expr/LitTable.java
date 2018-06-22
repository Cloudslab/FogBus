package gnu.expr;

import android.support.v4.internal.view.SupportMenu;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.lists.FString;
import gnu.mapping.Symbol;
import gnu.mapping.Table2D;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;

public class LitTable implements ObjectOutput {
    static Table2D staticTable = new Table2D(100);
    Compilation comp;
    IdentityHashMap literalTable = new IdentityHashMap(100);
    Literal literalsChain;
    int literalsCount;
    ClassType mainClass;
    int stackPointer;
    Type[] typeStack = new Type[20];
    Object[] valueStack = new Object[20];

    public LitTable(Compilation comp) {
        this.comp = comp;
        this.mainClass = comp.mainClass;
    }

    public void emit() throws IOException {
        Literal init;
        for (init = this.literalsChain; init != null; init = init.next) {
            writeObject(init.value);
        }
        for (init = this.literalsChain; init != null; init = init.next) {
            emit(init, true);
        }
        this.literalTable = null;
        this.literalsCount = 0;
    }

    void push(Object value, Type type) {
        if (this.stackPointer >= this.valueStack.length) {
            Object[] newValues = new Object[(this.valueStack.length * 2)];
            Type[] newTypes = new Type[(this.typeStack.length * 2)];
            System.arraycopy(this.valueStack, 0, newValues, 0, this.stackPointer);
            System.arraycopy(this.typeStack, 0, newTypes, 0, this.stackPointer);
            this.valueStack = newValues;
            this.typeStack = newTypes;
        }
        this.valueStack[this.stackPointer] = value;
        this.typeStack[this.stackPointer] = type;
        this.stackPointer++;
    }

    void error(String msg) {
        throw new Error(msg);
    }

    public void flush() {
    }

    public void close() {
    }

    public void write(int b) throws IOException {
        error("cannot handle call to write(int) when externalizing literal");
    }

    public void writeBytes(String s) throws IOException {
        error("cannot handle call to writeBytes(String) when externalizing literal");
    }

    public void write(byte[] b) throws IOException {
        error("cannot handle call to write(byte[]) when externalizing literal");
    }

    public void write(byte[] b, int off, int len) throws IOException {
        error("cannot handle call to write(byte[],int,int) when externalizing literal");
    }

    public void writeBoolean(boolean v) {
        push(new Boolean(v), Type.booleanType);
    }

    public void writeChar(int v) {
        push(new Character((char) v), Type.charType);
    }

    public void writeByte(int v) {
        push(new Byte((byte) v), Type.byteType);
    }

    public void writeShort(int v) {
        push(new Short((short) v), Type.shortType);
    }

    public void writeInt(int v) {
        push(new Integer(v), Type.intType);
    }

    public void writeLong(long v) {
        push(new Long(v), Type.longType);
    }

    public void writeFloat(float v) {
        push(new Float(v), Type.floatType);
    }

    public void writeDouble(double v) {
        push(new Double(v), Type.doubleType);
    }

    public void writeUTF(String v) {
        push(v, Type.string_type);
    }

    public void writeChars(String v) {
        push(v, Type.string_type);
    }

    public void writeObject(Object obj) throws IOException {
        Literal lit = findLiteral(obj);
        if ((lit.flags & 3) != 0) {
            if (!(lit.field != null || obj == null || (obj instanceof String))) {
                lit.assign(this);
            }
            if ((lit.flags & 2) == 0) {
                lit.flags |= 4;
            }
        } else {
            lit.flags |= 1;
            int oldStack = this.stackPointer;
            if ((obj instanceof FString) && ((FString) obj).size() < SupportMenu.USER_MASK) {
                push(obj.toString(), Type.string_type);
            } else if (obj instanceof Externalizable) {
                ((Externalizable) obj).writeExternal(this);
            } else if (obj instanceof Object[]) {
                Object[] arr = (Object[]) obj;
                for (Object writeObject : arr) {
                    writeObject(writeObject);
                }
            } else if (!(obj == null || (obj instanceof String) || (lit.type instanceof ArrayType))) {
                if (obj instanceof BigInteger) {
                    writeChars(obj.toString());
                } else if (obj instanceof BigDecimal) {
                    BigDecimal dec = (BigDecimal) obj;
                    writeObject(dec.unscaledValue());
                    writeInt(dec.scale());
                } else if (obj instanceof Integer) {
                    push(obj, Type.intType);
                } else if (obj instanceof Short) {
                    push(obj, Type.shortType);
                } else if (obj instanceof Byte) {
                    push(obj, Type.byteType);
                } else if (obj instanceof Long) {
                    push(obj, Type.longType);
                } else if (obj instanceof Double) {
                    push(obj, Type.doubleType);
                } else if (obj instanceof Float) {
                    push(obj, Type.floatType);
                } else if (obj instanceof Character) {
                    push(obj, Type.charType);
                } else if (obj instanceof Class) {
                    push(obj, Type.java_lang_Class_type);
                } else if (obj instanceof Pattern) {
                    Pattern pat = (Pattern) obj;
                    push(pat.pattern(), Type.string_type);
                    push(Integer.valueOf(pat.flags()), Type.intType);
                } else {
                    error(obj.getClass().getName() + " does not implement Externalizable");
                }
            }
            int nargs = this.stackPointer - oldStack;
            if (nargs == 0) {
                lit.argValues = Values.noArgs;
                lit.argTypes = Type.typeArray0;
            } else {
                lit.argValues = new Object[nargs];
                lit.argTypes = new Type[nargs];
                System.arraycopy(this.valueStack, oldStack, lit.argValues, 0, nargs);
                System.arraycopy(this.typeStack, oldStack, lit.argTypes, 0, nargs);
                this.stackPointer = oldStack;
            }
            lit.flags |= 2;
        }
        push(lit, lit.type);
    }

    public Literal findLiteral(Object value) {
        if (value == null) {
            return Literal.nullLiteral;
        }
        Literal literal = (Literal) this.literalTable.get(value);
        if (literal != null) {
            return literal;
        }
        if (this.comp.immediate) {
            return new Literal(value, this);
        }
        Class valueClass = value.getClass();
        Type valueType = Type.make(valueClass);
        synchronized (staticTable) {
            literal = (Literal) staticTable.get(value, null, null);
            if ((literal == null || literal.value != value) && (valueType instanceof ClassType)) {
                Class fldClass = valueClass;
                ClassType fldType = (ClassType) valueType;
                while (staticTable.get(fldClass, Boolean.TRUE, null) == null) {
                    staticTable.put(fldClass, Boolean.TRUE, fldClass);
                    for (Field fld = fldType.getFields(); fld != null; fld = fld.getNext()) {
                        if ((fld.getModifiers() & 25) == 25) {
                            try {
                                Object litValue = fld.getReflectField().get(null);
                                if (litValue != null && fldClass.isInstance(litValue)) {
                                    Literal lit = new Literal(litValue, fld, this);
                                    staticTable.put(litValue, null, lit);
                                    if (value == litValue) {
                                        literal = lit;
                                    }
                                }
                            } catch (Throwable ex) {
                                error("caught " + ex + " getting static field " + fld);
                            }
                        }
                    }
                    fldClass = fldClass.getSuperclass();
                    if (fldClass == null) {
                        break;
                    }
                    fldType = (ClassType) Type.make(fldClass);
                }
            }
        }
        if (literal == null) {
            return new Literal(value, valueType, this);
        }
        this.literalTable.put(value, literal);
        return literal;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    gnu.bytecode.Method getMethod(gnu.bytecode.ClassType r39, java.lang.String r40, gnu.expr.Literal r41, boolean r42) {
        /*
        r38 = this;
        r0 = r41;
        r7 = r0.argTypes;
        r26 = r39.getDeclaredMethods();
        r6 = r7.length;
        r15 = 0;
        r16 = 0;
        r5 = 0;
        r14 = 0;
    L_0x000e:
        if (r26 == 0) goto L_0x0144;
    L_0x0010:
        r33 = r26.getName();
        r0 = r40;
        r1 = r33;
        r33 = r0.equals(r1);
        if (r33 != 0) goto L_0x0023;
    L_0x001e:
        r26 = r26.getNext();
        goto L_0x000e;
    L_0x0023:
        r27 = r26.getStaticFlag();
        r0 = r42;
        r1 = r27;
        if (r0 != r1) goto L_0x001e;
    L_0x002d:
        r12 = 0;
        r25 = r26.getParameterTypes();
        r21 = 0;
        r22 = 0;
    L_0x0037:
        r0 = r21;
        if (r0 != r6) goto L_0x009a;
    L_0x003b:
        r0 = r25;
        r0 = r0.length;
        r33 = r0;
        r0 = r22;
        r1 = r33;
        if (r0 != r1) goto L_0x009a;
    L_0x0046:
        if (r15 == 0) goto L_0x0054;
    L_0x0048:
        r34 = 0;
        r33 = (r16 > r34 ? 1 : (r16 == r34 ? 0 : -1));
        if (r33 == 0) goto L_0x005b;
    L_0x004e:
        r34 = 0;
        r33 = (r12 > r34 ? 1 : (r12 == r34 ? 0 : -1));
        if (r33 != 0) goto L_0x005b;
    L_0x0054:
        r15 = r26;
        r14 = r25;
        r16 = r12;
        goto L_0x001e;
    L_0x005b:
        r34 = 0;
        r33 = (r12 > r34 ? 1 : (r12 == r34 ? 0 : -1));
        if (r33 != 0) goto L_0x001e;
    L_0x0061:
        r28 = 0;
        r29 = 0;
        r24 = r6;
    L_0x0067:
        r24 = r24 + -1;
        if (r24 < 0) goto L_0x007f;
    L_0x006b:
        r33 = r14[r24];
        r34 = r25[r24];
        r18 = r33.compare(r34);
        r33 = 1;
        r0 = r18;
        r1 = r33;
        if (r0 == r1) goto L_0x008b;
    L_0x007b:
        r29 = 1;
        if (r28 == 0) goto L_0x008b;
    L_0x007f:
        if (r28 == 0) goto L_0x0085;
    L_0x0081:
        r15 = r26;
        r14 = r25;
    L_0x0085:
        if (r28 == 0) goto L_0x0098;
    L_0x0087:
        if (r29 == 0) goto L_0x0098;
    L_0x0089:
        r5 = 1;
    L_0x008a:
        goto L_0x001e;
    L_0x008b:
        r33 = -1;
        r0 = r18;
        r1 = r33;
        if (r0 == r1) goto L_0x0067;
    L_0x0093:
        r28 = 1;
        if (r29 == 0) goto L_0x0067;
    L_0x0097:
        goto L_0x007f;
    L_0x0098:
        r5 = 0;
        goto L_0x008a;
    L_0x009a:
        r0 = r21;
        if (r0 == r6) goto L_0x001e;
    L_0x009e:
        r0 = r25;
        r0 = r0.length;
        r33 = r0;
        r0 = r22;
        r1 = r33;
        if (r0 == r1) goto L_0x001e;
    L_0x00a9:
        r4 = r7[r21];
        r30 = r25[r22];
        r0 = r30;
        r33 = r4.isSubtype(r0);
        if (r33 == 0) goto L_0x00bb;
    L_0x00b5:
        r21 = r21 + 1;
        r22 = r22 + 1;
        goto L_0x0037;
    L_0x00bb:
        r0 = r30;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r33 = r0;
        if (r33 == 0) goto L_0x001e;
    L_0x00c3:
        r33 = 64;
        r0 = r22;
        r1 = r33;
        if (r0 >= r1) goto L_0x001e;
    L_0x00cb:
        r33 = gnu.bytecode.Type.intType;
        r0 = r33;
        if (r4 == r0) goto L_0x00d7;
    L_0x00d1:
        r33 = gnu.bytecode.Type.shortType;
        r0 = r33;
        if (r4 != r0) goto L_0x001e;
    L_0x00d7:
        r0 = r41;
        r0 = r0.argValues;
        r33 = r0;
        r33 = r33[r21];
        r33 = (java.lang.Number) r33;
        r19 = r33.intValue();
        if (r19 >= 0) goto L_0x00f7;
    L_0x00e7:
        r33 = r39.getName();
        r34 = "gnu.math.IntNum";
        r33 = r33.equals(r34);
        if (r33 == 0) goto L_0x00f7;
    L_0x00f3:
        r33 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r19 = r19 - r33;
    L_0x00f7:
        r30 = (gnu.bytecode.ArrayType) r30;
        r20 = r30.getComponentType();
        if (r19 < 0) goto L_0x001e;
    L_0x00ff:
        r33 = r21 + r19;
        r0 = r33;
        if (r0 >= r6) goto L_0x001e;
    L_0x0105:
        r24 = r19;
    L_0x0107:
        r24 = r24 + -1;
        if (r24 < 0) goto L_0x0135;
    L_0x010b:
        r33 = r21 + r24;
        r33 = r33 + 1;
        r31 = r7[r33];
        r0 = r20;
        r0 = r0 instanceof gnu.bytecode.PrimType;
        r33 = r0;
        if (r33 == 0) goto L_0x0129;
    L_0x0119:
        r33 = r20.getSignature();
        r34 = r31.getSignature();
        r0 = r33;
        r1 = r34;
        if (r0 == r1) goto L_0x0107;
    L_0x0127:
        goto L_0x001e;
    L_0x0129:
        r0 = r31;
        r1 = r20;
        r33 = r0.isSubtype(r1);
        if (r33 != 0) goto L_0x0107;
    L_0x0133:
        goto L_0x001e;
    L_0x0135:
        r21 = r21 + r19;
        r33 = 1;
        r33 = r33 << r22;
        r0 = r33;
        r0 = (long) r0;
        r34 = r0;
        r12 = r12 | r34;
        goto L_0x00b5;
    L_0x0144:
        if (r5 == 0) goto L_0x0148;
    L_0x0146:
        r15 = 0;
    L_0x0147:
        return r15;
    L_0x0148:
        r34 = 0;
        r33 = (r16 > r34 ? 1 : (r16 == r34 ? 0 : -1));
        if (r33 == 0) goto L_0x0147;
    L_0x014e:
        r0 = r14.length;
        r33 = r0;
        r0 = r33;
        r9 = new java.lang.Object[r0];
        r0 = r14.length;
        r33 = r0;
        r0 = r33;
        r0 = new gnu.bytecode.Type[r0];
        r32 = r0;
        r21 = 0;
        r22 = 0;
    L_0x0162:
        r0 = r21;
        if (r0 != r6) goto L_0x0171;
    L_0x0166:
        r0 = r41;
        r0.argValues = r9;
        r0 = r32;
        r1 = r41;
        r1.argTypes = r0;
        goto L_0x0147;
    L_0x0171:
        r30 = r14[r22];
        r33 = 1;
        r33 = r33 << r22;
        r0 = r33;
        r0 = (long) r0;
        r34 = r0;
        r34 = r34 & r16;
        r36 = 0;
        r33 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1));
        if (r33 != 0) goto L_0x019d;
    L_0x0184:
        r0 = r41;
        r0 = r0.argValues;
        r33 = r0;
        r33 = r33[r21];
        r9[r22] = r33;
        r0 = r41;
        r0 = r0.argTypes;
        r33 = r0;
        r33 = r33[r21];
        r32[r22] = r33;
    L_0x0198:
        r21 = r21 + 1;
        r22 = r22 + 1;
        goto L_0x0162;
    L_0x019d:
        r0 = r41;
        r0 = r0.argValues;
        r33 = r0;
        r33 = r33[r21];
        r33 = (java.lang.Number) r33;
        r19 = r33.intValue();
        r33 = r39.getName();
        r34 = "gnu.math.IntNum";
        r23 = r33.equals(r34);
        if (r23 == 0) goto L_0x01bb;
    L_0x01b7:
        r33 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r19 = r19 - r33;
    L_0x01bb:
        r33 = r30;
        r33 = (gnu.bytecode.ArrayType) r33;
        r20 = r33.getComponentType();
        r32[r22] = r30;
        r33 = r20.getReflectClass();
        r0 = r33;
        r1 = r19;
        r33 = java.lang.reflect.Array.newInstance(r0, r1);
        r9[r22] = r33;
        r0 = r41;
        r8 = r0.argValues;
        if (r23 == 0) goto L_0x01f6;
    L_0x01d9:
        r33 = r9[r22];
        r33 = (int[]) r33;
        r10 = r33;
        r10 = (int[]) r10;
        r24 = r19;
    L_0x01e3:
        if (r24 <= 0) goto L_0x020e;
    L_0x01e5:
        r34 = r19 - r24;
        r33 = r21 + r24;
        r33 = r8[r33];
        r33 = (java.lang.Integer) r33;
        r33 = r33.intValue();
        r10[r34] = r33;
        r24 = r24 + -1;
        goto L_0x01e3;
    L_0x01f6:
        r24 = r19;
    L_0x01f8:
        r24 = r24 + -1;
        if (r24 < 0) goto L_0x020e;
    L_0x01fc:
        r33 = r9[r22];
        r34 = r21 + 1;
        r34 = r34 + r24;
        r34 = r8[r34];
        r0 = r33;
        r1 = r24;
        r2 = r34;
        java.lang.reflect.Array.set(r0, r1, r2);
        goto L_0x01f8;
    L_0x020e:
        r11 = new gnu.expr.Literal;
        r33 = r9[r22];
        r0 = r33;
        r1 = r30;
        r11.<init>(r0, r1);
        r0 = r20;
        r0 = r0 instanceof gnu.bytecode.ObjectType;
        r33 = r0;
        if (r33 == 0) goto L_0x022b;
    L_0x0221:
        r33 = r9[r22];
        r33 = (java.lang.Object[]) r33;
        r33 = (java.lang.Object[]) r33;
        r0 = r33;
        r11.argValues = r0;
    L_0x022b:
        r9[r22] = r11;
        r21 = r21 + r19;
        goto L_0x0198;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LitTable.getMethod(gnu.bytecode.ClassType, java.lang.String, gnu.expr.Literal, boolean):gnu.bytecode.Method");
    }

    void putArgs(Literal literal, CodeAttr code) {
        Type[] argTypes = literal.argTypes;
        int len = argTypes.length;
        for (int i = 0; i < len; i++) {
            Object value = literal.argValues[i];
            if (value instanceof Literal) {
                emit((Literal) value, false);
            } else {
                this.comp.compileConstant(value, new StackTarget(argTypes[i]));
            }
        }
    }

    private void store(Literal literal, boolean ignore, CodeAttr code) {
        if (literal.field != null) {
            if (!ignore) {
                code.emitDup(literal.type);
            }
            code.emitPutStatic(literal.field);
        }
        literal.flags |= 8;
    }

    void emit(Literal literal, boolean ignore) {
        CodeAttr code = this.comp.getCode();
        if (literal.value == null) {
            if (!ignore) {
                code.emitPushNull();
            }
        } else if (literal.value instanceof String) {
            if (!ignore) {
                code.emitPushString(literal.value.toString());
            }
        } else if ((literal.flags & 8) != 0) {
            if (!ignore) {
                code.emitGetStatic(literal.field);
            }
        } else if (literal.value instanceof Object[]) {
            int len = literal.argValues.length;
            Type elementType = ((ArrayType) literal.type).getComponentType();
            code.emitPushInt(len);
            code.emitNewArray(elementType);
            store(literal, ignore, code);
            for (int i = 0; i < len; i++) {
                Literal el = literal.argValues[i];
                if (el.value != null) {
                    code.emitDup(elementType);
                    code.emitPushInt(i);
                    emit(el, false);
                    code.emitArrayStore(elementType);
                }
            }
        } else if (literal.type instanceof ArrayType) {
            code.emitPushPrimArray(literal.value, (ArrayType) literal.type);
            store(literal, ignore, code);
        } else if (literal.value instanceof Class) {
            Class clas = literal.value;
            if (clas.isPrimitive()) {
                String cname = clas.getName();
                if (cname.equals("int")) {
                    cname = PropertyTypeConstants.PROPERTY_TYPE_INTEGER;
                }
                code.emitGetStatic(ClassType.make("java.lang." + Character.toUpperCase(cname.charAt(0)) + cname.substring(1)).getDeclaredField("TYPE"));
            } else {
                this.comp.loadClassRef((ObjectType) Type.make(clas));
            }
            store(literal, ignore, code);
        } else if (!(literal.value instanceof ClassType) || ((ClassType) literal.value).isExisting()) {
            Type type = (ClassType) literal.type;
            boolean useDefaultInit = (literal.flags & 4) != 0;
            Method method = null;
            boolean makeStatic = false;
            if (!useDefaultInit) {
                if (!(literal.value instanceof Symbol)) {
                    method = getMethod(type, "valueOf", literal, true);
                }
                if (method == null && !(literal.value instanceof Values)) {
                    String mname = "make";
                    if (literal.value instanceof Pattern) {
                        mname = "compile";
                    }
                    method = getMethod(type, mname, literal, true);
                }
                if (method != null) {
                    makeStatic = true;
                } else if (literal.argTypes.length > 0) {
                    method = getMethod(type, "<init>", literal, false);
                }
                if (method == null) {
                    useDefaultInit = true;
                }
            }
            if (useDefaultInit) {
                method = getMethod(type, "set", literal, false);
            }
            if (method == null && literal.argTypes.length > 0) {
                error("no method to construct " + literal.type);
            }
            if (makeStatic) {
                putArgs(literal, code);
                code.emitInvokeStatic(method);
            } else if (useDefaultInit) {
                code.emitNew(type);
                code.emitDup(type);
                code.emitInvokeSpecial(type.getDeclaredMethod("<init>", 0));
            } else {
                code.emitNew(type);
                code.emitDup(type);
                putArgs(literal, code);
                code.emitInvokeSpecial(method);
            }
            Method resolveMethod = (makeStatic || (literal.value instanceof Values)) ? null : type.getDeclaredMethod("readResolve", 0);
            if (resolveMethod != null) {
                code.emitInvokeVirtual(resolveMethod);
                type.emitCoerceFromObject(code);
            }
            boolean z = ignore && (!useDefaultInit || method == null);
            store(literal, z, code);
            if (useDefaultInit && method != null) {
                if (!ignore) {
                    code.emitDup(type);
                }
                putArgs(literal, code);
                code.emitInvokeVirtual(method);
            }
        } else {
            this.comp.loadClassRef((ClassType) literal.value);
            Method meth = Compilation.typeType.getDeclaredMethod("valueOf", 1);
            if (meth == null) {
                meth = Compilation.typeType.getDeclaredMethod("make", 1);
            }
            code.emitInvokeStatic(meth);
            code.emitCheckcast(Compilation.typeClassType);
            store(literal, ignore, code);
        }
    }
}
