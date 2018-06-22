package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

public class Ev3BinaryParser {
    private static byte PRIMPAR_1_BYTE = (byte) 1;
    private static byte PRIMPAR_2_BYTES = (byte) 2;
    private static byte PRIMPAR_4_BYTES = (byte) 3;
    private static byte PRIMPAR_ADDR = (byte) 8;
    private static byte PRIMPAR_BYTES = (byte) 7;
    private static byte PRIMPAR_CONST = (byte) 0;
    private static byte PRIMPAR_CONST_SIGN = (byte) 32;
    private static byte PRIMPAR_GLOBAL = (byte) 32;
    private static byte PRIMPAR_HANDLE = (byte) 16;
    private static byte PRIMPAR_INDEX = (byte) 31;
    private static byte PRIMPAR_LOCAL = (byte) 0;
    private static byte PRIMPAR_LONG = Byte.MIN_VALUE;
    private static byte PRIMPAR_SHORT = (byte) 0;
    private static byte PRIMPAR_STRING = (byte) 4;
    private static byte PRIMPAR_STRING_OLD = (byte) 0;
    private static byte PRIMPAR_VALUE = Opcode.MOVEF_F;
    private static byte PRIMPAR_VARIABEL = Opcode.JR;

    private static class FormatLiteral {
        public int size;
        public char symbol;

        public FormatLiteral(char symbol, int size) {
            this.symbol = symbol;
            this.size = size;
        }
    }

    public static byte[] pack(String format, Object... values) throws IllegalArgumentException {
        int i;
        String[] formatTokens = format.split("(?<=\\D)");
        FormatLiteral[] literals = new FormatLiteral[formatTokens.length];
        int index = 0;
        int bufferCapacity = 0;
        for (i = 0; i < formatTokens.length; i++) {
            String token = formatTokens[i];
            char symbol = token.charAt(token.length() - 1);
            int size = 1;
            boolean sizeSpecified = false;
            if (token.length() != 1) {
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                sizeSpecified = true;
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case 'B':
                    bufferCapacity += size;
                    index++;
                    break;
                case 'F':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'H':
                    bufferCapacity += size * 2;
                    index++;
                    break;
                case 'I':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'L':
                    bufferCapacity += size * 8;
                    index++;
                    break;
                case 'S':
                    if (!sizeSpecified) {
                        bufferCapacity += ((String) values[index]).length() + 1;
                        index++;
                        break;
                    }
                    throw new IllegalArgumentException("Illegal format string");
                case 'b':
                    bufferCapacity += size;
                    index += size;
                    break;
                case 'f':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'h':
                    bufferCapacity += size * 2;
                    index += size;
                    break;
                case 'i':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'l':
                    bufferCapacity += size * 8;
                    index += size;
                    break;
                case 's':
                    if (size == ((String) values[index]).length()) {
                        bufferCapacity += size;
                        index++;
                        break;
                    }
                    throw new IllegalArgumentException("Illegal format string");
                case 'x':
                    bufferCapacity += size;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
            literals[i] = new FormatLiteral(symbol, size);
        }
        if (index != values.length) {
            throw new IllegalArgumentException("Illegal format string");
        }
        index = 0;
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (FormatLiteral literal : literals) {
            switch (literal.symbol) {
                case 'B':
                    buffer.put((byte[]) values[index]);
                    index++;
                    break;
                case 'F':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putFloat(((float[]) values[index])[i]);
                    }
                    index++;
                    break;
                case 'H':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putShort(((short[]) values[index])[i]);
                    }
                    index++;
                    break;
                case 'I':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putInt(((int[]) values[index])[i]);
                    }
                    index++;
                    break;
                case 'L':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putLong(((long[]) values[index])[i]);
                    }
                    index++;
                    break;
                case 'S':
                    try {
                        buffer.put(((String) values[index]).getBytes("US-ASCII"));
                        buffer.put((byte) 0);
                        index++;
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                case 'b':
                    for (i = 0; i < literal.size; i++) {
                        buffer.put(((Byte) values[index]).byteValue());
                        index++;
                    }
                    break;
                case 'f':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putFloat(((Float) values[index]).floatValue());
                        index++;
                    }
                    break;
                case 'h':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putShort(((Short) values[index]).shortValue());
                        index++;
                    }
                    break;
                case 'i':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putInt(((Integer) values[index]).intValue());
                        index++;
                    }
                    break;
                case 'l':
                    for (i = 0; i < literal.size; i++) {
                        buffer.putLong(((Long) values[index]).longValue());
                        index++;
                    }
                    break;
                case 's':
                    try {
                        buffer.put(((String) values[index]).getBytes("US-ASCII"));
                        index++;
                        break;
                    } catch (UnsupportedEncodingException e2) {
                        throw new IllegalArgumentException();
                    }
                case 'x':
                    for (i = 0; i < literal.size; i++) {
                        buffer.put((byte) 0);
                    }
                    break;
                default:
                    break;
            }
        }
        return buffer.array();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object[] unpack(java.lang.String r24, byte[] r25) throws java.lang.IllegalArgumentException {
        /*
        r22 = "(?<=\\D)";
        r0 = r24;
        r1 = r22;
        r10 = r0.split(r1);
        r7 = new java.util.ArrayList;
        r7.<init>();
        r4 = java.nio.ByteBuffer.wrap(r25);
        r22 = java.nio.ByteOrder.LITTLE_ENDIAN;
        r0 = r22;
        r4.order(r0);
        r2 = r10;
        r14 = r2.length;
        r12 = 0;
    L_0x001d:
        if (r12 >= r14) goto L_0x01b9;
    L_0x001f:
        r21 = r2[r12];
        r18 = 0;
        r17 = 1;
        r22 = r21.length();
        r22 = r22 + -1;
        r20 = r21.charAt(r22);
        r22 = r21.length();
        r23 = 1;
        r0 = r22;
        r1 = r23;
        if (r0 <= r1) goto L_0x005d;
    L_0x003b:
        r18 = 1;
        r22 = 0;
        r23 = r21.length();
        r23 = r23 + -1;
        r22 = r21.substring(r22, r23);
        r17 = java.lang.Integer.parseInt(r22);
        r22 = 1;
        r0 = r17;
        r1 = r22;
        if (r0 >= r1) goto L_0x005d;
    L_0x0055:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "Illegal format string";
        r22.<init>(r23);
        throw r22;
    L_0x005d:
        switch(r20) {
            case 36: goto L_0x01a1;
            case 66: goto L_0x0088;
            case 70: goto L_0x0139;
            case 72: goto L_0x00b0;
            case 73: goto L_0x00e2;
            case 76: goto L_0x010d;
            case 83: goto L_0x0176;
            case 98: goto L_0x0073;
            case 102: goto L_0x0124;
            case 104: goto L_0x009b;
            case 105: goto L_0x00cd;
            case 108: goto L_0x00f8;
            case 115: goto L_0x0150;
            case 120: goto L_0x0068;
            default: goto L_0x0060;
        };
    L_0x0060:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "Illegal format string";
        r22.<init>(r23);
        throw r22;
    L_0x0068:
        r11 = 0;
    L_0x0069:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x006d:
        r4.get();
        r11 = r11 + 1;
        goto L_0x0069;
    L_0x0073:
        r11 = 0;
    L_0x0074:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x0078:
        r22 = r4.get();
        r22 = java.lang.Byte.valueOf(r22);
        r0 = r22;
        r7.add(r0);
        r11 = r11 + 1;
        goto L_0x0074;
    L_0x0088:
        r0 = r17;
        r5 = new byte[r0];
        r22 = 0;
        r0 = r22;
        r1 = r17;
        r4.get(r5, r0, r1);
        r7.add(r5);
    L_0x0098:
        r12 = r12 + 1;
        goto L_0x001d;
    L_0x009b:
        r11 = 0;
    L_0x009c:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x00a0:
        r22 = r4.getShort();
        r22 = java.lang.Short.valueOf(r22);
        r0 = r22;
        r7.add(r0);
        r11 = r11 + 1;
        goto L_0x009c;
    L_0x00b0:
        r0 = r17;
        r0 = new short[r0];
        r16 = r0;
        r11 = 0;
    L_0x00b7:
        r0 = r17;
        if (r11 >= r0) goto L_0x00c7;
    L_0x00bb:
        r22 = r4.getShort();
        r16[r11] = r22;
        r22 = r11 + 1;
        r0 = r22;
        r11 = (short) r0;
        goto L_0x00b7;
    L_0x00c7:
        r0 = r16;
        r7.add(r0);
        goto L_0x0098;
    L_0x00cd:
        r11 = 0;
    L_0x00ce:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x00d2:
        r22 = r4.getInt();
        r22 = java.lang.Integer.valueOf(r22);
        r0 = r22;
        r7.add(r0);
        r11 = r11 + 1;
        goto L_0x00ce;
    L_0x00e2:
        r0 = r17;
        r13 = new int[r0];
        r11 = 0;
    L_0x00e7:
        r0 = r17;
        if (r11 >= r0) goto L_0x00f4;
    L_0x00eb:
        r22 = r4.getInt();
        r13[r11] = r22;
        r11 = r11 + 1;
        goto L_0x00e7;
    L_0x00f4:
        r7.add(r13);
        goto L_0x0098;
    L_0x00f8:
        r11 = 0;
    L_0x00f9:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x00fd:
        r22 = r4.getLong();
        r22 = java.lang.Long.valueOf(r22);
        r0 = r22;
        r7.add(r0);
        r11 = r11 + 1;
        goto L_0x00f9;
    L_0x010d:
        r0 = r17;
        r15 = new long[r0];
        r11 = 0;
    L_0x0112:
        r0 = r17;
        if (r11 >= r0) goto L_0x011f;
    L_0x0116:
        r22 = r4.getLong();
        r15[r11] = r22;
        r11 = r11 + 1;
        goto L_0x0112;
    L_0x011f:
        r7.add(r15);
        goto L_0x0098;
    L_0x0124:
        r11 = 0;
    L_0x0125:
        r0 = r17;
        if (r11 >= r0) goto L_0x0098;
    L_0x0129:
        r22 = r4.getFloat();
        r22 = java.lang.Float.valueOf(r22);
        r0 = r22;
        r7.add(r0);
        r11 = r11 + 1;
        goto L_0x0125;
    L_0x0139:
        r0 = r17;
        r9 = new float[r0];
        r11 = 0;
    L_0x013e:
        r0 = r17;
        if (r11 >= r0) goto L_0x014b;
    L_0x0142:
        r22 = r4.getFloat();
        r9[r11] = r22;
        r11 = r11 + 1;
        goto L_0x013e;
    L_0x014b:
        r7.add(r9);
        goto L_0x0098;
    L_0x0150:
        r0 = r17;
        r6 = new byte[r0];
        r22 = 0;
        r0 = r22;
        r1 = r17;
        r4.get(r6, r0, r1);
        r22 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x016f }
        r23 = "US-ASCII";
        r0 = r22;
        r1 = r23;
        r0.<init>(r6, r1);	 Catch:{ UnsupportedEncodingException -> 0x016f }
        r0 = r22;
        r7.add(r0);	 Catch:{ UnsupportedEncodingException -> 0x016f }
        goto L_0x0098;
    L_0x016f:
        r8 = move-exception;
        r22 = new java.lang.IllegalArgumentException;
        r22.<init>();
        throw r22;
    L_0x0176:
        if (r18 == 0) goto L_0x0180;
    L_0x0178:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "Illegal format string";
        r22.<init>(r23);
        throw r22;
    L_0x0180:
        r19 = new java.lang.StringBuffer;
        r19.<init>();
    L_0x0185:
        r3 = r4.get();
        if (r3 == 0) goto L_0x0196;
    L_0x018b:
        r0 = (char) r3;
        r22 = r0;
        r0 = r19;
        r1 = r22;
        r0.append(r1);
        goto L_0x0185;
    L_0x0196:
        r22 = r19.toString();
        r0 = r22;
        r7.add(r0);
        goto L_0x0098;
    L_0x01a1:
        if (r18 == 0) goto L_0x01ab;
    L_0x01a3:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "Illegal format string";
        r22.<init>(r23);
        throw r22;
    L_0x01ab:
        r22 = r4.hasRemaining();
        if (r22 == 0) goto L_0x0060;
    L_0x01b1:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "Illegal format string";
        r22.<init>(r23);
        throw r22;
    L_0x01b9:
        r22 = r7.toArray();
        return r22;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.Ev3BinaryParser.unpack(java.lang.String, byte[]):java.lang.Object[]");
    }

    public static byte[] encodeLC0(byte v) {
        if (v < (byte) -31 || v > (byte) 31) {
            throw new IllegalArgumentException("Encoded value must be in range [0, 127]");
        }
        return new byte[]{(byte) (PRIMPAR_VALUE & v)};
    }

    public static byte[] encodeLC1(byte v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_1_BYTE), (byte) (v & 255)};
    }

    public static byte[] encodeLC2(short v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_2_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255)};
    }

    public static byte[] encodeLC4(int v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_4_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255), (byte) ((v >>> 16) & 255), (byte) ((v >>> 24) & 255)};
    }

    public static byte[] encodeLV0(int i) {
        return new byte[]{(byte) ((((PRIMPAR_INDEX & i) | PRIMPAR_SHORT) | PRIMPAR_VARIABEL) | PRIMPAR_LOCAL)};
    }

    public static byte[] encodeLV1(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_LOCAL) | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeLV2(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_LOCAL) | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeLV4(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_LOCAL) | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeGV0(int i) {
        return new byte[]{(byte) ((((PRIMPAR_INDEX & i) | PRIMPAR_SHORT) | PRIMPAR_VARIABEL) | PRIMPAR_GLOBAL)};
    }

    public static byte[] encodeGV1(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_GLOBAL) | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeGV2(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_GLOBAL) | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeGV4(int i) {
        return new byte[]{(byte) (((PRIMPAR_LONG | PRIMPAR_VARIABEL) | PRIMPAR_GLOBAL) | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeSystemCommand(byte command, boolean needReply, Object... parameters) {
        int bufferCapacity = 2;
        for (Object obj : parameters) {
            if (obj instanceof Byte) {
                bufferCapacity++;
            } else if (obj instanceof Short) {
                bufferCapacity += 2;
            } else if (obj instanceof Integer) {
                bufferCapacity += 4;
            } else if (obj instanceof String) {
                bufferCapacity += ((String) obj).length() + 1;
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? (byte) 1 : (byte) -127);
        buffer.put(command);
        for (Object obj2 : parameters) {
            if (obj2 instanceof Byte) {
                buffer.put(((Byte) obj2).byteValue());
            } else if (obj2 instanceof Short) {
                buffer.putShort(((Short) obj2).shortValue());
            } else if (obj2 instanceof Integer) {
                buffer.putInt(((Integer) obj2).intValue());
            } else if (obj2 instanceof String) {
                try {
                    buffer.put(((String) obj2).getBytes("US-ASCII"));
                    buffer.put((byte) 0);
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException("Non-ASCII string encoding is not supported");
                }
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        return buffer.array();
    }

    public static byte[] encodeDirectCommand(byte opcode, boolean needReply, int globalAllocation, int localAllocation, String paramFormat, Object... parameters) {
        if (globalAllocation < 0 || globalAllocation > 1023 || localAllocation < 0 || localAllocation > 63 || paramFormat.length() != parameters.length) {
            throw new IllegalArgumentException();
        }
        ArrayList<byte[]> payloads = new ArrayList();
        for (int i = 0; i < paramFormat.length(); i++) {
            char letter = paramFormat.charAt(i);
            Object obj = parameters[i];
            switch (letter) {
                case 'c':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= (byte) 31 && ((Byte) obj).byteValue() >= (byte) -31) {
                            payloads.add(encodeLC0(((Byte) obj).byteValue()));
                            break;
                        }
                        payloads.add(encodeLC1(((Byte) obj).byteValue()));
                        break;
                    } else if (obj instanceof Short) {
                        payloads.add(encodeLC2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeLC4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'g':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= (byte) 31 && ((Byte) obj).byteValue() >= (byte) -31) {
                            payloads.add(encodeGV0(((Byte) obj).byteValue()));
                            break;
                        }
                        payloads.add(encodeGV1(((Byte) obj).byteValue()));
                        break;
                    } else if (obj instanceof Short) {
                        payloads.add(encodeGV2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeGV4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'l':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= (byte) 31 && ((Byte) obj).byteValue() >= (byte) -31) {
                            payloads.add(encodeLV0(((Byte) obj).byteValue()));
                            break;
                        }
                        payloads.add(encodeLV1(((Byte) obj).byteValue()));
                        break;
                    } else if (obj instanceof Short) {
                        payloads.add(encodeLV2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeLV4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 's':
                    if (obj instanceof String) {
                        try {
                            payloads.add((((String) obj) + '\u0000').getBytes("US-ASCII"));
                            break;
                        } catch (UnsupportedEncodingException e) {
                            throw new IllegalArgumentException();
                        }
                    }
                    throw new IllegalArgumentException();
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
        }
        int bufferCapacity = 4;
        Iterator i$ = payloads.iterator();
        while (i$.hasNext()) {
            bufferCapacity += ((byte[]) i$.next()).length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? (byte) 0 : Byte.MIN_VALUE);
        buffer.put(new byte[]{(byte) (globalAllocation & 255), (byte) (((globalAllocation >>> 8) & 3) | (localAllocation << 2))});
        buffer.put(opcode);
        i$ = payloads.iterator();
        while (i$.hasNext()) {
            buffer.put((byte[]) i$.next());
        }
        return buffer.array();
    }
}
