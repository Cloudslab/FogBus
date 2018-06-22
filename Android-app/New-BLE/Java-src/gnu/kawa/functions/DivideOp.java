package gnu.kawa.functions;

import gnu.mapping.Procedure;

public class DivideOp extends ArithOp {
    public static final DivideOp $Sl = new DivideOp("/", 4);
    public static final DivideOp div = new DivideOp("div", 6);
    public static final DivideOp div0 = new DivideOp("div0", 6);
    public static final DivideOp idiv = new DivideOp("idiv", 7);
    public static final DivideOp mod = new DivideOp("mod", 8);
    public static final DivideOp mod0 = new DivideOp("mod0", 8);
    public static final DivideOp modulo = new DivideOp("modulo", 8);
    public static final DivideOp quotient = new DivideOp("quotient", 6);
    public static final DivideOp remainder = new DivideOp("remainder", 8);
    int rounding_mode;

    public int getRoundingMode() {
        return this.rounding_mode;
    }

    static {
        idiv.rounding_mode = 3;
        quotient.rounding_mode = 3;
        remainder.rounding_mode = 3;
        modulo.rounding_mode = 1;
        div.rounding_mode = 5;
        mod.rounding_mode = 5;
        div0.rounding_mode = 4;
        mod0.rounding_mode = 4;
    }

    public DivideOp(String name, int op) {
        super(name, op);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set(this, "*gnu.kawa.functions.CompileArith:forDiv");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object applyN(java.lang.Object[] r33) throws java.lang.Throwable {
        /*
        r32 = this;
        r0 = r33;
        r0 = r0.length;
        r20 = r0;
        if (r20 != 0) goto L_0x000c;
    L_0x0007:
        r24 = gnu.math.IntNum.one();
    L_0x000b:
        return r24;
    L_0x000c:
        r29 = 0;
        r27 = r33[r29];
        r27 = (java.lang.Number) r27;
        r29 = 1;
        r0 = r20;
        r1 = r29;
        if (r0 != r1) goto L_0x0029;
    L_0x001a:
        r29 = gnu.math.IntNum.one();
        r0 = r32;
        r1 = r29;
        r2 = r27;
        r24 = r0.apply2(r1, r2);
        goto L_0x000b;
    L_0x0029:
        r7 = gnu.kawa.functions.Arithmetic.classifyValue(r27);
        r9 = 1;
    L_0x002e:
        r0 = r20;
        if (r9 >= r0) goto L_0x02ba;
    L_0x0032:
        r4 = r33[r9];
        r8 = gnu.kawa.functions.Arithmetic.classifyValue(r4);
        if (r7 >= r8) goto L_0x003b;
    L_0x003a:
        r7 = r8;
    L_0x003b:
        r28 = r7;
        r29 = 4;
        r0 = r29;
        if (r7 >= r0) goto L_0x0066;
    L_0x0043:
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 4: goto L_0x00b5;
            case 5: goto L_0x00b5;
            default: goto L_0x004c;
        };
    L_0x004c:
        r0 = r32;
        r0 = r0.rounding_mode;
        r29 = r0;
        r30 = 3;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x00b9;
    L_0x005a:
        r29 = 1;
        r0 = r29;
        if (r7 == r0) goto L_0x0066;
    L_0x0060:
        r29 = 2;
        r0 = r29;
        if (r7 != r0) goto L_0x00b9;
    L_0x0066:
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        r30 = 5;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x00bc;
    L_0x0074:
        r29 = 10;
        r0 = r29;
        if (r7 > r0) goto L_0x00bc;
    L_0x007a:
        r28 = 10;
        r29 = 8;
        r0 = r29;
        if (r7 == r0) goto L_0x008a;
    L_0x0082:
        r29 = 7;
        r0 = r29;
        if (r7 == r0) goto L_0x008a;
    L_0x0088:
        r7 = 9;
    L_0x008a:
        switch(r28) {
            case 1: goto L_0x00df;
            case 2: goto L_0x0102;
            case 3: goto L_0x008d;
            case 4: goto L_0x011d;
            case 5: goto L_0x0163;
            case 6: goto L_0x008d;
            case 7: goto L_0x008d;
            case 8: goto L_0x008d;
            case 9: goto L_0x01c1;
            default: goto L_0x008d;
        };
    L_0x008d:
        r24 = gnu.kawa.functions.Arithmetic.asNumeric(r27);
        r25 = gnu.kawa.functions.Arithmetic.asNumeric(r4);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        r30 = 8;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x0222;
    L_0x00a3:
        r29 = r25.isZero();
        if (r29 == 0) goto L_0x0222;
    L_0x00a9:
        r29 = r25.isExact();
        if (r29 != 0) goto L_0x000b;
    L_0x00af:
        r24 = r24.toInexact();
        goto L_0x000b;
    L_0x00b5:
        r7 = 4;
        r28 = r7;
        goto L_0x0066;
    L_0x00b9:
        r28 = 4;
        goto L_0x0066;
    L_0x00bc:
        r29 = 8;
        r0 = r28;
        r1 = r29;
        if (r0 == r1) goto L_0x00cc;
    L_0x00c4:
        r29 = 7;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x008a;
    L_0x00cc:
        r28 = 9;
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        r30 = 7;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x008a;
    L_0x00dc:
        r7 = r28;
        goto L_0x008a;
    L_0x00df:
        r14 = gnu.kawa.functions.Arithmetic.asInt(r27);
        r15 = gnu.kawa.functions.Arithmetic.asInt(r4);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 8: goto L_0x0100;
            default: goto L_0x00f0;
        };
    L_0x00f0:
        r14 = r14 / r15;
    L_0x00f1:
        r27 = java.lang.Integer.valueOf(r14);
    L_0x00f5:
        r0 = r28;
        if (r7 == r0) goto L_0x00fc;
    L_0x00f9:
        switch(r7) {
            case 1: goto L_0x028c;
            case 2: goto L_0x0296;
            case 3: goto L_0x02b4;
            case 4: goto L_0x00fc;
            case 5: goto L_0x00fc;
            case 6: goto L_0x00fc;
            case 7: goto L_0x02a0;
            case 8: goto L_0x02aa;
            default: goto L_0x00fc;
        };
    L_0x00fc:
        r9 = r9 + 1;
        goto L_0x002e;
    L_0x0100:
        r14 = r14 % r15;
        goto L_0x00f1;
    L_0x0102:
        r16 = gnu.kawa.functions.Arithmetic.asLong(r27);
        r18 = gnu.kawa.functions.Arithmetic.asLong(r4);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 8: goto L_0x011a;
            default: goto L_0x0113;
        };
    L_0x0113:
        r16 = r16 / r18;
    L_0x0115:
        r27 = java.lang.Long.valueOf(r16);
        goto L_0x00f5;
    L_0x011a:
        r16 = r16 % r18;
        goto L_0x0115;
    L_0x011d:
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 4: goto L_0x0127;
            case 5: goto L_0x0126;
            case 6: goto L_0x013f;
            case 7: goto L_0x013f;
            case 8: goto L_0x0150;
            default: goto L_0x0126;
        };
    L_0x0126:
        goto L_0x00f5;
    L_0x0127:
        r29 = gnu.kawa.functions.Arithmetic.asIntNum(r27);
        r30 = gnu.kawa.functions.Arithmetic.asIntNum(r4);
        r27 = gnu.math.RatNum.make(r29, r30);
        r0 = r27;
        r0 = r0 instanceof gnu.math.IntNum;
        r29 = r0;
        if (r29 == 0) goto L_0x0161;
    L_0x013b:
        r7 = 4;
    L_0x013c:
        r28 = r7;
        goto L_0x00f5;
    L_0x013f:
        r29 = gnu.kawa.functions.Arithmetic.asIntNum(r27);
        r30 = gnu.kawa.functions.Arithmetic.asIntNum(r4);
        r31 = r32.getRoundingMode();
        r27 = gnu.math.IntNum.quotient(r29, r30, r31);
        goto L_0x00f5;
    L_0x0150:
        r29 = gnu.kawa.functions.Arithmetic.asIntNum(r27);
        r30 = gnu.kawa.functions.Arithmetic.asIntNum(r4);
        r31 = r32.getRoundingMode();
        r27 = gnu.math.IntNum.remainder(r29, r30, r31);
        goto L_0x00f5;
    L_0x0161:
        r7 = 6;
        goto L_0x013c;
    L_0x0163:
        r5 = gnu.kawa.functions.Arithmetic.asBigDecimal(r27);
        r6 = gnu.kawa.functions.Arithmetic.asBigDecimal(r4);
        r22 = 0;
        r29 = r32.getRoundingMode();
        switch(r29) {
            case 1: goto L_0x018c;
            case 2: goto L_0x018f;
            case 3: goto L_0x0192;
            case 4: goto L_0x0174;
            case 5: goto L_0x0195;
            default: goto L_0x0174;
        };
    L_0x0174:
        r23 = java.math.RoundingMode.HALF_EVEN;
    L_0x0176:
        r21 = new java.math.MathContext;
        r21.<init>(r22, r23);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 4: goto L_0x0186;
            case 5: goto L_0x0184;
            case 6: goto L_0x01a1;
            case 7: goto L_0x01a9;
            case 8: goto L_0x01b9;
            default: goto L_0x0184;
        };
    L_0x0184:
        goto L_0x00f5;
    L_0x0186:
        r27 = r5.divide(r6);
        goto L_0x00f5;
    L_0x018c:
        r23 = java.math.RoundingMode.FLOOR;
        goto L_0x0176;
    L_0x018f:
        r23 = java.math.RoundingMode.CEILING;
        goto L_0x0176;
    L_0x0192:
        r23 = java.math.RoundingMode.DOWN;
        goto L_0x0176;
    L_0x0195:
        r29 = r6.signum();
        if (r29 >= 0) goto L_0x019e;
    L_0x019b:
        r23 = java.math.RoundingMode.CEILING;
    L_0x019d:
        goto L_0x0174;
    L_0x019e:
        r23 = java.math.RoundingMode.FLOOR;
        goto L_0x019d;
    L_0x01a1:
        r0 = r21;
        r27 = r5.divideToIntegralValue(r6, r0);
        goto L_0x00f5;
    L_0x01a9:
        r0 = r21;
        r29 = r5.divideToIntegralValue(r6, r0);
        r27 = r29.toBigInteger();
        r28 = 3;
        r7 = r28;
        goto L_0x00f5;
    L_0x01b9:
        r0 = r21;
        r27 = r5.remainder(r6, r0);
        goto L_0x00f5;
    L_0x01c1:
        r10 = gnu.kawa.functions.Arithmetic.asDouble(r27);
        r12 = gnu.kawa.functions.Arithmetic.asDouble(r4);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 4: goto L_0x01d4;
            case 5: goto L_0x01d4;
            case 6: goto L_0x01dc;
            case 7: goto L_0x01f0;
            case 8: goto L_0x0204;
            default: goto L_0x01d2;
        };
    L_0x01d2:
        goto L_0x00f5;
    L_0x01d4:
        r30 = r10 / r12;
        r27 = gnu.math.DFloNum.make(r30);
        goto L_0x00f5;
    L_0x01dc:
        r30 = r10 / r12;
        r29 = r32.getRoundingMode();
        r0 = r30;
        r2 = r29;
        r30 = gnu.math.RealNum.toInt(r0, r2);
        r27 = java.lang.Double.valueOf(r30);
        goto L_0x00f5;
    L_0x01f0:
        r30 = r10 / r12;
        r29 = r32.getRoundingMode();
        r0 = r30;
        r2 = r29;
        r27 = gnu.math.RealNum.toExactInt(r0, r2);
        r28 = 4;
        r7 = r28;
        goto L_0x00f5;
    L_0x0204:
        r30 = 0;
        r29 = (r12 > r30 ? 1 : (r12 == r30 ? 0 : -1));
        if (r29 == 0) goto L_0x021c;
    L_0x020a:
        r30 = r10 / r12;
        r29 = r32.getRoundingMode();
        r0 = r30;
        r2 = r29;
        r30 = gnu.math.RealNum.toInt(r0, r2);
        r30 = r30 * r12;
        r10 = r10 - r30;
    L_0x021c:
        r27 = gnu.math.DFloNum.make(r10);
        goto L_0x00f5;
    L_0x0222:
        r26 = r24.div(r25);
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        r30 = 8;
        r0 = r29;
        r1 = r30;
        if (r0 != r1) goto L_0x0252;
    L_0x0234:
        r26 = (gnu.math.RealNum) r26;
        r29 = r32.getRoundingMode();
        r0 = r26;
        r1 = r29;
        r29 = r0.toInt(r1);
        r0 = r29;
        r1 = r25;
        r29 = r0.mul(r1);
        r0 = r24;
        r1 = r29;
        r26 = r0.sub(r1);
    L_0x0252:
        r0 = r32;
        r0 = r0.op;
        r29 = r0;
        switch(r29) {
            case 5: goto L_0x0286;
            case 6: goto L_0x0274;
            case 7: goto L_0x025f;
            default: goto L_0x025b;
        };
    L_0x025b:
        r27 = r26;
        goto L_0x00f5;
    L_0x025f:
        r26 = (gnu.math.RealNum) r26;
        r0 = r32;
        r0 = r0.rounding_mode;
        r29 = r0;
        r0 = r26;
        r1 = r29;
        r27 = r0.toExactInt(r1);
        r7 = 4;
        r28 = r7;
        goto L_0x00f5;
    L_0x0274:
        r26 = (gnu.math.RealNum) r26;
        r0 = r32;
        r0 = r0.rounding_mode;
        r29 = r0;
        r0 = r26;
        r1 = r29;
        r27 = r0.toInt(r1);
        goto L_0x00f5;
    L_0x0286:
        r27 = r26.toInexact();
        goto L_0x00f5;
    L_0x028c:
        r29 = r27.intValue();
        r27 = java.lang.Integer.valueOf(r29);
        goto L_0x00fc;
    L_0x0296:
        r30 = r27.longValue();
        r27 = java.lang.Long.valueOf(r30);
        goto L_0x00fc;
    L_0x02a0:
        r29 = r27.floatValue();
        r27 = java.lang.Float.valueOf(r29);
        goto L_0x00fc;
    L_0x02aa:
        r30 = r27.doubleValue();
        r27 = java.lang.Double.valueOf(r30);
        goto L_0x00fc;
    L_0x02b4:
        r27 = gnu.kawa.functions.Arithmetic.asBigInteger(r27);
        goto L_0x00fc;
    L_0x02ba:
        r24 = r27;
        goto L_0x000b;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.DivideOp.applyN(java.lang.Object[]):java.lang.Object");
    }

    public int numArgs() {
        return this.op == 4 ? -4095 : 8194;
    }
}
