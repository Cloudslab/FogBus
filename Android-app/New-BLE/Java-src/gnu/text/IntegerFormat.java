package gnu.text;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.FieldPosition;

public class IntegerFormat extends ReportFormat {
    public static final int MIN_DIGITS = 64;
    public static final int PAD_RIGHT = 16;
    public static final int SHOW_BASE = 8;
    public static final int SHOW_GROUPS = 1;
    public static final int SHOW_PLUS = 2;
    public static final int SHOW_SPACE = 4;
    public static final int UPPERCASE = 32;
    public int base = 10;
    public int commaChar = 44;
    public int commaInterval = 3;
    public int flags = 0;
    public int minWidth = 1;
    public int padChar = 32;

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        return format((Object) args, start, dst, fpos);
    }

    public int format(Object arg, int start, Writer dst, FieldPosition fpos) throws IOException {
        Object[] args = arg instanceof Object[] ? (Object[]) arg : null;
        int minWidth = ReportFormat.getParam(this.minWidth, 1, args, start);
        if (this.minWidth == -1610612736) {
            start++;
        }
        char padChar = ReportFormat.getParam(this.padChar, ' ', args, start);
        if (this.padChar == -1610612736) {
            start++;
        }
        char commaChar = ReportFormat.getParam(this.commaChar, ',', args, start);
        if (this.commaChar == -1610612736) {
            start++;
        }
        int commaInterval = ReportFormat.getParam(this.commaInterval, 3, args, start);
        if (this.commaInterval == -1610612736) {
            start++;
        }
        boolean printCommas = (this.flags & 1) != 0;
        boolean padRight = (this.flags & 16) != 0;
        boolean padInternal = padChar == '0';
        if (args != null) {
            if (start >= args.length) {
                dst.write("#<missing format argument>");
                return start;
            }
            arg = args[start];
        }
        String sarg = convertToIntegerString(arg, this.base);
        if (sarg != null) {
            int ndigits;
            char sarg0 = sarg.charAt(0);
            boolean neg = sarg0 == '-';
            int slen = sarg.length();
            if (neg) {
                ndigits = slen - 1;
            } else {
                ndigits = slen;
            }
            int unpadded_len = ndigits + (printCommas ? (ndigits - 1) / commaInterval : 0);
            if (neg || (this.flags & 6) != 0) {
                unpadded_len++;
            }
            if ((this.flags & 8) != 0) {
                if (this.base == 16) {
                    unpadded_len += 2;
                } else if (this.base == 8 && sarg0 != '0') {
                    unpadded_len++;
                }
            }
            if ((this.flags & 64) != 0) {
                unpadded_len = ndigits;
                if (slen == 1 && sarg0 == '0' && minWidth == 0) {
                    slen = 0;
                }
            }
            if (!(padRight || padInternal)) {
                while (minWidth > unpadded_len) {
                    dst.write(padChar);
                    minWidth--;
                }
            }
            int i = 0;
            if (neg) {
                dst.write(45);
                i = 0 + 1;
                slen--;
            } else if ((this.flags & 2) != 0) {
                dst.write(43);
            } else if ((this.flags & 4) != 0) {
                dst.write(32);
            }
            boolean uppercase = this.base > 10 && (this.flags & 32) != 0;
            if ((this.flags & 8) != 0) {
                if (this.base == 16) {
                    dst.write(48);
                    dst.write(uppercase ? 88 : 120);
                } else if (this.base == 8 && sarg0 != '0') {
                    dst.write(48);
                }
            }
            if (padInternal) {
                while (minWidth > unpadded_len) {
                    dst.write(padChar);
                    minWidth--;
                }
            }
            int i2 = i;
            while (slen != 0) {
                i = i2 + 1;
                char ch = sarg.charAt(i2);
                if (uppercase) {
                    ch = Character.toUpperCase(ch);
                }
                dst.write(ch);
                slen--;
                if (printCommas && slen > 0 && slen % commaInterval == 0) {
                    dst.write(commaChar);
                }
                i2 = i;
            }
            if (padRight) {
                for (minWidth = 
/*
Method generation error in method: gnu.text.IntegerFormat.format(java.lang.Object, int, java.io.Writer, java.text.FieldPosition):int
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r9_6 'minWidth' int) = (r9_3 'minWidth' int), (r9_4 'minWidth' int) binds: {(r9_4 'minWidth' int)=B:126:0x0278, (r9_3 'minWidth' int)=B:90:0x01da} in method: gnu.text.IntegerFormat.format(java.lang.Object, int, java.io.Writer, java.text.FieldPosition):int
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:118)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:118)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:57)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 25 more

*/

                public String convertToIntegerString(Object x, int radix) {
                    if (!(x instanceof Number)) {
                        return null;
                    }
                    if (x instanceof BigInteger) {
                        return ((BigInteger) x).toString(radix);
                    }
                    return Long.toString(((Number) x).longValue(), radix);
                }
            }
