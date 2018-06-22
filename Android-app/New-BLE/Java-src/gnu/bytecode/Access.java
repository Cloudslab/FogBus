package gnu.bytecode;

public class Access {
    public static final short ABSTRACT = (short) 1024;
    public static final short ANNOTATION = (short) 8192;
    public static final short BRIDGE = (short) 64;
    public static final char CLASS_CONTEXT = 'C';
    public static final short CLASS_MODIFIERS = (short) 30257;
    public static final short ENUM = (short) 16384;
    public static final char FIELD_CONTEXT = 'F';
    public static final short FIELD_MODIFIERS = (short) 20703;
    public static final short FINAL = (short) 16;
    public static final char INNERCLASS_CONTEXT = 'I';
    public static final short INNERCLASS_MODIFIERS = (short) 30239;
    public static final short INTERFACE = (short) 512;
    public static final char METHOD_CONTEXT = 'M';
    public static final short METHOD_MODIFIERS = (short) 7679;
    public static final short NATIVE = (short) 256;
    public static final short PRIVATE = (short) 2;
    public static final short PROTECTED = (short) 4;
    public static final short PUBLIC = (short) 1;
    public static final short STATIC = (short) 8;
    public static final short STRICT = (short) 2048;
    public static final short SUPER = (short) 32;
    public static final short SYNCHRONIZED = (short) 32;
    public static final short SYNTHETIC = (short) 4096;
    public static final short TRANSIENT = (short) 128;
    public static final short VARARGS = (short) 128;
    public static final short VOLATILE = (short) 64;

    public static String toString(int flags) {
        return toString(flags, '\u0000');
    }

    public static String toString(int flags, char kind) {
        short mask = kind == CLASS_CONTEXT ? CLASS_MODIFIERS : kind == INNERCLASS_CONTEXT ? INNERCLASS_MODIFIERS : kind == FIELD_CONTEXT ? FIELD_MODIFIERS : kind == METHOD_CONTEXT ? METHOD_MODIFIERS : Short.MAX_VALUE;
        short bad_flags = (short) ((mask ^ -1) & flags);
        flags &= mask;
        StringBuffer buf = new StringBuffer();
        if ((flags & 1) != 0) {
            buf.append(" public");
        }
        if ((flags & 2) != 0) {
            buf.append(" private");
        }
        if ((flags & 4) != 0) {
            buf.append(" protected");
        }
        if ((flags & 8) != 0) {
            buf.append(" static");
        }
        if ((flags & 16) != 0) {
            buf.append(" final");
        }
        if ((flags & 32) != 0) {
            buf.append(kind == CLASS_CONTEXT ? " super" : " synchronized");
        }
        if ((flags & 64) != 0) {
            buf.append(kind == METHOD_CONTEXT ? " bridge" : " volatile");
        }
        if ((flags & 128) != 0) {
            buf.append(kind == METHOD_CONTEXT ? " varargs" : " transient");
        }
        if ((flags & 256) != 0) {
            buf.append(" native");
        }
        if ((flags & 512) != 0) {
            buf.append(" interface");
        }
        if ((flags & 1024) != 0) {
            buf.append(" abstract");
        }
        if ((flags & 2048) != 0) {
            buf.append(" strict");
        }
        if ((flags & 16384) != 0) {
            buf.append(" enum");
        }
        if ((flags & 4096) != 0) {
            buf.append(" synthetic");
        }
        if ((flags & 8192) != 0) {
            buf.append(" annotation");
        }
        if (bad_flags != (short) 0) {
            buf.append(" unknown-flags:0x");
            buf.append(Integer.toHexString(bad_flags));
        }
        return buf.toString();
    }
}
