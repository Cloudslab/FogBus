package gnu.bytecode;

import com.google.appinventor.components.common.PropertyTypeConstants;
import java.io.DataOutputStream;
import java.io.IOException;
import kawa.Telnet;

public class StackMapTableAttr extends MiscAttr {
    public static boolean compressStackMapTable = true;
    int countLocals;
    int countStack;
    int[] encodedLocals;
    int[] encodedStack;
    int numEntries;
    int prevPosition = -1;

    public StackMapTableAttr() {
        super("StackMapTable", null, 0, 0);
        put2(0);
    }

    public StackMapTableAttr(byte[] data, CodeAttr code) {
        super("StackMapTable", data, 0, data.length);
        addToFrontOf(code);
        this.numEntries = u2(0);
    }

    public Method getMethod() {
        return ((CodeAttr) this.container).getMethod();
    }

    public void write(DataOutputStream dstr) throws IOException {
        put2(0, this.numEntries);
        super.write(dstr);
    }

    void emitVerificationType(int encoding) {
        int tag = encoding & 255;
        put1(tag);
        if (tag >= 7) {
            put2(encoding >> 8);
        }
    }

    int encodeVerificationType(Type type, CodeAttr code) {
        if (type == null) {
            return 0;
        }
        if (type instanceof UninitializedType) {
            Label label = ((UninitializedType) type).label;
            if (label == null) {
                return 6;
            }
            return (label.position << 8) | 8;
        }
        type = type.getImplementationType();
        if (type instanceof PrimType) {
            switch (type.signature.charAt(0)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    return 1;
                case 'D':
                    return 3;
                case 'F':
                    return 2;
                case 'J':
                    return 4;
                default:
                    return 0;
            }
        } else if (type == Type.nullType) {
            return 5;
        } else {
            return (code.getConstants().addClass((ObjectType) type).index << 8) | 7;
        }
    }

    public void emitStackMapEntry(Label label, CodeAttr code) {
        int curLocalsCount;
        int offset_delta = (label.position - this.prevPosition) - 1;
        int rawLocalsCount = label.localTypes.length;
        if (rawLocalsCount > this.encodedLocals.length) {
            Object tmp = new int[(this.encodedLocals.length + rawLocalsCount)];
            System.arraycopy(this.encodedLocals, 0, tmp, 0, this.countLocals);
            this.encodedLocals = tmp;
        }
        int rawStackCount = label.stackTypes.length;
        if (rawStackCount > this.encodedStack.length) {
            tmp = new int[(this.encodedStack.length + rawStackCount)];
            System.arraycopy(this.encodedStack, 0, tmp, 0, this.countStack);
            this.encodedStack = tmp;
        }
        int unchangedLocals = 0;
        int i = 0;
        int curLocalsCount2 = 0;
        while (i < rawLocalsCount) {
            int prevType = this.encodedLocals[curLocalsCount2];
            int nextType = encodeVerificationType(label.localTypes[i], code);
            if (prevType == nextType && unchangedLocals == curLocalsCount2) {
                unchangedLocals = curLocalsCount2 + 1;
            }
            curLocalsCount = curLocalsCount2 + 1;
            this.encodedLocals[curLocalsCount2] = nextType;
            if (nextType == 3 || nextType == 4) {
                i++;
            }
            i++;
            curLocalsCount2 = curLocalsCount;
        }
        curLocalsCount = curLocalsCount2;
        while (curLocalsCount > 0 && this.encodedLocals[curLocalsCount - 1] == 0) {
            curLocalsCount--;
        }
        i = 0;
        int curStackCount = 0;
        while (i < rawStackCount) {
            prevType = this.encodedStack[curStackCount];
            Type t = label.stackTypes[i];
            if (t == Type.voidType) {
                i++;
                t = label.stackTypes[i];
            }
            int curStackCount2 = curStackCount + 1;
            this.encodedStack[curStackCount] = encodeVerificationType(t, code);
            i++;
            curStackCount = curStackCount2;
        }
        int localsDelta = curLocalsCount - this.countLocals;
        if (compressStackMapTable && localsDelta == 0 && curLocalsCount == unchangedLocals && curStackCount <= 1) {
            if (curStackCount != 0) {
                if (offset_delta <= 63) {
                    put1(offset_delta + 64);
                } else {
                    put1(247);
                    put2(offset_delta);
                }
                emitVerificationType(this.encodedStack[0]);
            } else if (offset_delta <= 63) {
                put1(offset_delta);
            } else {
                put1(Telnet.WILL);
                put2(offset_delta);
            }
        } else if (compressStackMapTable && curStackCount == 0 && curLocalsCount < this.countLocals && unchangedLocals == curLocalsCount && localsDelta >= -3) {
            put1(localsDelta + Telnet.WILL);
            put2(offset_delta);
        } else if (compressStackMapTable && curStackCount == 0 && this.countLocals == unchangedLocals && localsDelta <= 3) {
            put1(localsDelta + Telnet.WILL);
            put2(offset_delta);
            for (i = 0; i < localsDelta; i++) {
                emitVerificationType(this.encodedLocals[unchangedLocals + i]);
            }
        } else {
            put1(255);
            put2(offset_delta);
            put2(curLocalsCount);
            for (i = 0; i < curLocalsCount; i++) {
                emitVerificationType(this.encodedLocals[i]);
            }
            put2(curStackCount);
            for (i = 0; i < curStackCount; i++) {
                emitVerificationType(this.encodedStack[i]);
            }
        }
        this.countLocals = curLocalsCount;
        this.countStack = curStackCount;
        this.prevPosition = label.position;
        this.numEntries++;
    }

    void printVerificationType(int encoding, ClassTypeWriter dst) {
        int tag = encoding & 255;
        switch (tag) {
            case 0:
                dst.print("top/unavailable");
                return;
            case 1:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_INTEGER);
                return;
            case 2:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_FLOAT);
                return;
            case 3:
                dst.print("double");
                return;
            case 4:
                dst.print("long");
                return;
            case 5:
                dst.print("null");
                return;
            case 6:
                dst.print("uninitialized this");
                return;
            case 7:
                int index = encoding >> 8;
                dst.printOptionalIndex(index);
                dst.printConstantTersely(index, 7);
                return;
            case 8:
                int offset = encoding >> 8;
                dst.print("uninitialized object created at ");
                dst.print(offset);
                return;
            default:
                dst.print("<bad verification type tag " + tag + '>');
                return;
        }
    }

    int extractVerificationType(int startOffset, int tag) {
        if (tag == 7 || tag == 8) {
            return tag | (u2(startOffset + 1) << 8);
        }
        return tag;
    }

    static int[] reallocBuffer(int[] buffer, int needed) {
        if (buffer == null) {
            return new int[(needed + 10)];
        }
        if (needed <= buffer.length) {
            return buffer;
        }
        int[] tmp = new int[(needed + 10)];
        System.arraycopy(buffer, 0, tmp, 0, buffer.length);
        return tmp;
    }

    int extractVerificationTypes(int startOffset, int count, int startIndex, int[] buffer) {
        int offset = startOffset;
        int startIndex2 = startIndex;
        while (true) {
            count--;
            if (count < 0) {
                return offset;
            }
            int encoding;
            if (offset >= this.dataLength) {
                encoding = -1;
            } else {
                int tag = this.data[offset];
                encoding = extractVerificationType(offset, tag);
                int i = (tag == 7 || tag == 8) ? 3 : 1;
                offset += i;
            }
            startIndex = startIndex2 + 1;
            buffer[startIndex2] = encoding;
            startIndex2 = startIndex;
        }
    }

    void printVerificationTypes(int[] encodings, int startIndex, int count, ClassTypeWriter dst) {
        int regno = 0;
        for (int i = 0; i < startIndex + count; i++) {
            int encoding = encodings[i];
            int tag = encoding & 255;
            if (i >= startIndex) {
                dst.print("  ");
                if (regno < 100) {
                    if (regno < 10) {
                        dst.print(' ');
                    }
                    dst.print(' ');
                }
                dst.print(regno);
                dst.print(": ");
                printVerificationType(encoding, dst);
                dst.println();
            }
            regno++;
            if (tag == 3 || tag == 4) {
                regno++;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", number of entries: ");
        dst.println(this.numEntries);
        int ipos = 2;
        int pc_offset = -1;
        Method method = getMethod();
        int[] encodedTypes = null;
        int curLocals = (method.getStaticFlag() ? 0 : 1) + method.arg_types.length;
        int i = 0;
        while (i < this.numEntries) {
            if (ipos < this.dataLength) {
                int ipos2 = ipos + 1;
                int tag = u1(ipos);
                pc_offset++;
                if (tag <= 127) {
                    pc_offset += tag & 63;
                    ipos = ipos2;
                } else {
                    if (ipos2 + 1 < this.dataLength) {
                        pc_offset += u2(ipos2);
                        ipos = ipos2 + 2;
                    } else {
                        return;
                    }
                }
                dst.print("  offset: ");
                dst.print(pc_offset);
                if (tag <= 63) {
                    dst.println(" - same_frame");
                } else if (tag <= 127 || tag == 247) {
                    dst.println(tag <= 127 ? " - same_locals_1_stack_item_frame" : " - same_locals_1_stack_item_frame_extended");
                    encodedTypes = reallocBuffer(encodedTypes, 1);
                    ipos = extractVerificationTypes(ipos, 1, 0, encodedTypes);
                    printVerificationTypes(encodedTypes, 0, 1, dst);
                } else if (tag <= 246) {
                    dst.print(" - tag reserved for future use - ");
                    dst.println(tag);
                    return;
                } else if (tag <= 250) {
                    count = 251 - tag;
                    dst.print(" - chop_frame - undefine ");
                    dst.print(count);
                    dst.println(" locals");
                    curLocals -= count;
                } else if (tag == 251) {
                    dst.println(" - same_frame_extended");
                } else if (tag <= 254) {
                    count = tag - 251;
                    dst.print(" - append_frame - define ");
                    dst.print(count);
                    dst.println(" more locals");
                    encodedTypes = reallocBuffer(encodedTypes, curLocals + count);
                    ipos = extractVerificationTypes(ipos, count, curLocals, encodedTypes);
                    printVerificationTypes(encodedTypes, curLocals, count, dst);
                    curLocals += count;
                } else {
                    if (ipos + 1 < this.dataLength) {
                        int num_locals = u2(ipos);
                        ipos += 2;
                        dst.print(" - full_frame.  Locals count: ");
                        dst.println(num_locals);
                        encodedTypes = reallocBuffer(encodedTypes, num_locals);
                        ipos = extractVerificationTypes(ipos, num_locals, 0, encodedTypes);
                        printVerificationTypes(encodedTypes, 0, num_locals, dst);
                        curLocals = num_locals;
                        if (ipos + 1 < this.dataLength) {
                            int num_stack = u2(ipos);
                            ipos += 2;
                            dst.print("    (end of locals)");
                            int nspaces = Integer.toString(pc_offset).length();
                            while (true) {
                                nspaces--;
                                if (nspaces < 0) {
                                    break;
                                }
                                dst.print(' ');
                            }
                            dst.print("       Stack count: ");
                            dst.println(num_stack);
                            encodedTypes = reallocBuffer(encodedTypes, num_stack);
                            ipos = extractVerificationTypes(ipos, num_stack, 0, encodedTypes);
                            printVerificationTypes(encodedTypes, 0, num_stack, dst);
                            int curStack = num_stack;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                if (ipos < 0) {
                    dst.println("<ERROR - missing data>");
                    return;
                }
                i++;
            } else {
                return;
            }
        }
    }
}
