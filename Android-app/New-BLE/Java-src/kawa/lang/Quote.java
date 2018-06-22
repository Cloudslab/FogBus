package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.IdentityHashMap;

public class Quote extends Syntax {
    private static final Object CYCLE = new String("(cycle)");
    protected static final int QUOTE_DEPTH = -1;
    private static final Object WORKING = new String("(working)");
    static final Method appendMethod = quoteType.getDeclaredMethod("append$V", 1);
    static final Method consXMethod = quoteType.getDeclaredMethod("consX$V", 1);
    static final Method makePairMethod = Compilation.typePair.getDeclaredMethod("make", 2);
    static final Method makeVectorMethod = ClassType.make("gnu.lists.FVector").getDeclaredMethod("make", 1);
    public static final Quote plainQuote = new Quote(LispLanguage.quote_sym, false);
    public static final Quote quasiQuote = new Quote(LispLanguage.quasiquote_sym, true);
    static final ClassType quoteType = ClassType.make("kawa.lang.Quote");
    static final Method vectorAppendMethod = ClassType.make("kawa.standard.vector_append").getDeclaredMethod("apply$V", 1);
    protected boolean isQuasi;

    public Quote(String name, boolean isQuasi) {
        super(name);
        this.isQuasi = isQuasi;
    }

    protected Object expand(Object template, int depth, Translator tr) {
        return expand(template, depth, null, new IdentityHashMap(), tr);
    }

    public static Object quote(Object obj, Translator tr) {
        return plainQuote.expand(obj, -1, tr);
    }

    public static Object quote(Object obj) {
        return plainQuote.expand(obj, -1, (Translator) Compilation.getCurrent());
    }

    protected Expression coerceExpression(Object val, Translator tr) {
        return val instanceof Expression ? (Expression) val : leaf(val, tr);
    }

    protected Expression leaf(Object val, Translator tr) {
        return new QuoteExp(val);
    }

    protected boolean expandColonForms() {
        return true;
    }

    public static Symbol makeSymbol(Namespace ns, Object local) {
        String name;
        if (local instanceof CharSequence) {
            name = ((CharSequence) local).toString();
        } else {
            name = (String) local;
        }
        return ns.getSymbol(name.intern());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    java.lang.Object expand_pair(gnu.lists.Pair r39, int r40, kawa.lang.SyntaxForm r41, java.lang.Object r42, kawa.lang.Translator r43) {
        /*
        r38 = this;
        r25 = r39;
    L_0x0002:
        r30 = r25;
        r3 = r38.expandColonForms();
        if (r3 == 0) goto L_0x0129;
    L_0x000a:
        r0 = r25;
        r1 = r39;
        if (r0 != r1) goto L_0x0129;
    L_0x0010:
        r3 = r25.getCar();
        r5 = gnu.kawa.lispexpr.LispLanguage.lookup_sym;
        r0 = r43;
        r1 = r41;
        r3 = r0.matches(r3, r1, r5);
        if (r3 == 0) goto L_0x0129;
    L_0x0020:
        r3 = r25.getCdr();
        r3 = r3 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x0129;
    L_0x0028:
        r23 = r25.getCdr();
        r23 = (gnu.lists.Pair) r23;
        r0 = r23;
        r3 = r0 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x0129;
    L_0x0034:
        r24 = r23.getCdr();
        r24 = (gnu.lists.Pair) r24;
        r0 = r24;
        r3 = r0 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x0129;
    L_0x0040:
        r3 = r24.getCdr();
        r5 = gnu.lists.LList.Empty;
        if (r3 != r5) goto L_0x0129;
    L_0x0048:
        r3 = 0;
        r0 = r43;
        r1 = r23;
        r28 = r0.rewrite_car(r1, r3);
        r3 = 0;
        r0 = r43;
        r1 = r24;
        r29 = r0.rewrite_car(r1, r3);
        r0 = r43;
        r1 = r28;
        r20 = r0.namespaceResolvePrefix(r1);
        r0 = r43;
        r1 = r20;
        r2 = r29;
        r35 = r0.namespaceResolve(r1, r2);
        if (r35 == 0) goto L_0x0078;
    L_0x006e:
        r13 = r35;
        r4 = r30;
        r3 = r13;
    L_0x0073:
        r0 = r39;
        if (r0 != r4) goto L_0x0327;
    L_0x0077:
        return r3;
    L_0x0078:
        if (r20 == 0) goto L_0x009e;
    L_0x007a:
        r3 = 1;
        r0 = r40;
        if (r0 != r3) goto L_0x009e;
    L_0x007f:
        r13 = new gnu.expr.ApplyExp;
        r3 = quoteType;
        r5 = "makeSymbol";
        r6 = 2;
        r3 = r3.getDeclaredMethod(r5, r6);
        r5 = 2;
        r5 = new gnu.expr.Expression[r5];
        r6 = 0;
        r7 = gnu.expr.QuoteExp.getInstance(r20);
        r5[r6] = r7;
        r6 = 1;
        r5[r6] = r29;
        r13.<init>(r3, r5);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x009e:
        r0 = r28;
        r3 = r0 instanceof gnu.expr.ReferenceExp;
        if (r3 == 0) goto L_0x00dd;
    L_0x00a4:
        r0 = r29;
        r3 = r0 instanceof gnu.expr.QuoteExp;
        if (r3 == 0) goto L_0x00dd;
    L_0x00aa:
        r3 = r43.getGlobalEnvironment();
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r28 = (gnu.expr.ReferenceExp) r28;
        r6 = r28.getName();
        r5 = r5.append(r6);
        r6 = 58;
        r5 = r5.append(r6);
        r29 = (gnu.expr.QuoteExp) r29;
        r6 = r29.getValue();
        r6 = r6.toString();
        r5 = r5.append(r6);
        r5 = r5.toString();
        r13 = r3.getSymbol(r5);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x00dd:
        r14 = gnu.kawa.functions.CompileNamedPart.combineName(r28, r29);
        if (r14 == 0) goto L_0x00ef;
    L_0x00e3:
        r3 = r43.getGlobalEnvironment();
        r13 = r3.getSymbol(r14);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x00ef:
        r0 = r43;
        r1 = r25;
        r32 = r0.pushPositionOf(r1);
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "'";
        r5 = r5.append(r6);
        r6 = r23.getCar();
        r5 = r5.append(r6);
        r6 = "' is not a valid prefix";
        r5 = r5.append(r6);
        r5 = r5.toString();
        r0 = r43;
        r0.error(r3, r5);
        r0 = r43;
        r1 = r32;
        r0.popPositionOf(r1);
        r13 = r35;
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x0129:
        if (r40 >= 0) goto L_0x014b;
    L_0x012b:
        r3 = 1;
        r0 = r40;
        if (r0 != r3) goto L_0x02a6;
    L_0x0130:
        r3 = r25.getCar();
        r3 = r3 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x02a6;
    L_0x0138:
        r15 = r25.getCar();
        r34 = r41;
    L_0x013e:
        r3 = r15 instanceof kawa.lang.SyntaxForm;
        if (r3 == 0) goto L_0x01f1;
    L_0x0142:
        r34 = r15;
        r34 = (kawa.lang.SyntaxForm) r34;
        r15 = r34.getDatum();
        goto L_0x013e;
    L_0x014b:
        r3 = r25.getCar();
        r5 = "quasiquote";
        r0 = r43;
        r1 = r41;
        r3 = r0.matches(r3, r1, r5);
        if (r3 == 0) goto L_0x015e;
    L_0x015b:
        r40 = r40 + 1;
        goto L_0x012b;
    L_0x015e:
        r3 = r25.getCar();
        r5 = "unquote";
        r0 = r43;
        r1 = r41;
        r3 = r0.matches(r3, r1, r5);
        if (r3 == 0) goto L_0x01bc;
    L_0x016e:
        r40 = r40 + -1;
        r3 = r25.getCdr();
        r3 = r3 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x0186;
    L_0x0178:
        r26 = r25.getCdr();
        r26 = (gnu.lists.Pair) r26;
        r3 = r26.getCdr();
        r5 = gnu.lists.LList.Empty;
        if (r3 == r5) goto L_0x01ab;
    L_0x0186:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = "invalid used of ";
        r3 = r3.append(r5);
        r5 = r25.getCar();
        r3 = r3.append(r5);
        r5 = " in quasiquote template";
        r3 = r3.append(r5);
        r3 = r3.toString();
        r0 = r43;
        r3 = r0.syntaxError(r3);
        goto L_0x0077;
    L_0x01ab:
        if (r40 != 0) goto L_0x012b;
    L_0x01ad:
        r0 = r43;
        r1 = r26;
        r2 = r41;
        r13 = r0.rewrite_car(r1, r2);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x01bc:
        r3 = r25.getCar();
        r5 = "unquote-splicing";
        r0 = r43;
        r1 = r41;
        r3 = r0.matches(r3, r1, r5);
        if (r3 == 0) goto L_0x012b;
    L_0x01cc:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = "invalid used of ";
        r3 = r3.append(r5);
        r5 = r25.getCar();
        r3 = r3.append(r5);
        r5 = " in quasiquote template";
        r3 = r3.append(r5);
        r3 = r3.toString();
        r0 = r43;
        r3 = r0.syntaxError(r3);
        goto L_0x0077;
    L_0x01f1:
        r33 = -1;
        r3 = r15 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x020e;
    L_0x01f7:
        r3 = r15;
        r3 = (gnu.lists.Pair) r3;
        r21 = r3.getCar();
        r3 = "unquote";
        r0 = r43;
        r1 = r21;
        r2 = r34;
        r3 = r0.matches(r1, r2, r3);
        if (r3 == 0) goto L_0x026d;
    L_0x020c:
        r33 = 0;
    L_0x020e:
        if (r33 < 0) goto L_0x02a6;
    L_0x0210:
        r15 = (gnu.lists.Pair) r15;
        r15 = r15.getCdr();
        r37 = new java.util.Vector;
        r37.<init>();
        r13 = 0;
    L_0x021c:
        r3 = r15 instanceof kawa.lang.SyntaxForm;
        if (r3 == 0) goto L_0x0228;
    L_0x0220:
        r34 = r15;
        r34 = (kawa.lang.SyntaxForm) r34;
        r15 = r34.getDatum();
    L_0x0228:
        r3 = gnu.lists.LList.Empty;
        if (r15 != r3) goto L_0x027e;
    L_0x022c:
        r3 = r37.size();
        r17 = r3 + 1;
        r4 = r25.getCdr();
        r5 = 1;
        r3 = r38;
        r6 = r41;
        r7 = r42;
        r8 = r43;
        r13 = r3.expand(r4, r5, r6, r7, r8);
        r3 = 1;
        r0 = r17;
        if (r0 <= r3) goto L_0x0268;
    L_0x0248:
        r0 = r17;
        r11 = new gnu.expr.Expression[r0];
        r0 = r37;
        r0.copyInto(r11);
        r3 = r17 + -1;
        r0 = r38;
        r1 = r43;
        r5 = r0.coerceExpression(r13, r1);
        r11[r3] = r5;
        if (r33 != 0) goto L_0x02a3;
    L_0x025f:
        r16 = consXMethod;
    L_0x0261:
        r13 = new gnu.expr.ApplyExp;
        r0 = r16;
        r13.<init>(r0, r11);
    L_0x0268:
        r4 = r25;
        r3 = r13;
        goto L_0x0073;
    L_0x026d:
        r3 = "unquote-splicing";
        r0 = r43;
        r1 = r21;
        r2 = r34;
        r3 = r0.matches(r1, r2, r3);
        if (r3 == 0) goto L_0x020e;
    L_0x027b:
        r33 = 1;
        goto L_0x020e;
    L_0x027e:
        r3 = r15 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x0299;
    L_0x0282:
        r3 = r15;
        r3 = (gnu.lists.Pair) r3;
        r0 = r43;
        r1 = r34;
        r3 = r0.rewrite_car(r3, r1);
        r0 = r37;
        r0.addElement(r3);
        r15 = (gnu.lists.Pair) r15;
        r15 = r15.getCdr();
        goto L_0x021c;
    L_0x0299:
        r3 = "improper list argument to unquote";
        r0 = r43;
        r3 = r0.syntaxError(r3);
        goto L_0x0077;
    L_0x02a3:
        r16 = appendMethod;
        goto L_0x0261;
    L_0x02a6:
        r4 = r25.getCar();
        r3 = r38;
        r5 = r40;
        r6 = r41;
        r7 = r42;
        r8 = r43;
        r12 = r3.expand(r4, r5, r6, r7, r8);
        r3 = r25.getCar();
        if (r12 != r3) goto L_0x02dd;
    L_0x02be:
        r4 = r25.getCdr();
        r3 = r4 instanceof gnu.lists.Pair;
        if (r3 == 0) goto L_0x02cc;
    L_0x02c6:
        r25 = r4;
        r25 = (gnu.lists.Pair) r25;
        goto L_0x0002;
    L_0x02cc:
        r3 = r38;
        r5 = r40;
        r6 = r41;
        r7 = r42;
        r8 = r43;
        r13 = r3.expand(r4, r5, r6, r7, r8);
        r3 = r13;
        goto L_0x0073;
    L_0x02dd:
        r6 = r25.getCdr();
        r5 = r38;
        r7 = r40;
        r8 = r41;
        r9 = r42;
        r10 = r43;
        r13 = r5.expand(r6, r7, r8, r9, r10);
        r3 = r12 instanceof gnu.expr.Expression;
        if (r3 != 0) goto L_0x02f7;
    L_0x02f3:
        r3 = r13 instanceof gnu.expr.Expression;
        if (r3 == 0) goto L_0x031c;
    L_0x02f7:
        r3 = 2;
        r11 = new gnu.expr.Expression[r3];
        r3 = 0;
        r0 = r38;
        r1 = r43;
        r5 = r0.coerceExpression(r12, r1);
        r11[r3] = r5;
        r3 = 1;
        r0 = r38;
        r1 = r43;
        r5 = r0.coerceExpression(r13, r1);
        r11[r3] = r5;
        r13 = new gnu.expr.ApplyExp;
        r3 = makePairMethod;
        r13.<init>(r3, r11);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x031c:
        r0 = r25;
        r13 = kawa.lang.Translator.makePair(r0, r12, r13);
        r4 = r30;
        r3 = r13;
        goto L_0x0073;
    L_0x0327:
        r22 = r39;
        r5 = 20;
        r0 = new gnu.lists.Pair[r5];
        r27 = r0;
        r18 = 0;
    L_0x0331:
        r0 = r27;
        r5 = r0.length;
        r0 = r18;
        if (r0 < r5) goto L_0x034b;
    L_0x0338:
        r5 = r18 * 2;
        r0 = new gnu.lists.Pair[r5];
        r36 = r0;
        r5 = 0;
        r6 = 0;
        r0 = r27;
        r1 = r36;
        r2 = r18;
        java.lang.System.arraycopy(r0, r5, r1, r6, r2);
        r27 = r36;
    L_0x034b:
        r19 = r18 + 1;
        r27[r18] = r22;
        r5 = r22.getCdr();
        if (r5 != r4) goto L_0x0370;
    L_0x0355:
        r5 = r3 instanceof gnu.expr.Expression;
        if (r5 == 0) goto L_0x0379;
    L_0x0359:
        r31 = gnu.lists.LList.Empty;
    L_0x035b:
        r18 = r19;
    L_0x035d:
        r18 = r18 + -1;
        if (r18 < 0) goto L_0x037c;
    L_0x0361:
        r22 = r27[r18];
        r5 = r22.getCar();
        r0 = r22;
        r1 = r31;
        r31 = kawa.lang.Translator.makePair(r0, r5, r1);
        goto L_0x035d;
    L_0x0370:
        r22 = r22.getCdr();
        r22 = (gnu.lists.Pair) r22;
        r18 = r19;
        goto L_0x0331;
    L_0x0379:
        r31 = r3;
        goto L_0x035b;
    L_0x037c:
        r5 = r3 instanceof gnu.expr.Expression;
        if (r5 == 0) goto L_0x03bb;
    L_0x0380:
        r5 = 2;
        r11 = new gnu.expr.Expression[r5];
        r5 = 1;
        r3 = (gnu.expr.Expression) r3;
        r11[r5] = r3;
        r3 = 1;
        r0 = r18;
        if (r0 != r3) goto L_0x03a5;
    L_0x038d:
        r3 = 0;
        r5 = r39.getCar();
        r0 = r38;
        r1 = r43;
        r5 = r0.leaf(r5, r1);
        r11[r3] = r5;
        r3 = new gnu.expr.ApplyExp;
        r5 = makePairMethod;
        r3.<init>(r5, r11);
        goto L_0x0077;
    L_0x03a5:
        r3 = 0;
        r0 = r38;
        r1 = r31;
        r2 = r43;
        r5 = r0.leaf(r1, r2);
        r11[r3] = r5;
        r3 = new gnu.expr.ApplyExp;
        r5 = appendMethod;
        r3.<init>(r5, r11);
        goto L_0x0077;
    L_0x03bb:
        r3 = r31;
        goto L_0x0077;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Quote.expand_pair(gnu.lists.Pair, int, kawa.lang.SyntaxForm, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    Object expand(Object template, int depth, SyntaxForm syntax, Object seen, Translator tr) {
        IdentityHashMap map = (IdentityHashMap) seen;
        Object old = map.get(template);
        if (old == WORKING) {
            map.put(template, CYCLE);
            return old;
        } else if (old == CYCLE || old != null) {
            return old;
        } else {
            if (template instanceof Pair) {
                old = expand_pair((Pair) template, depth, syntax, seen, tr);
            } else if (template instanceof SyntaxForm) {
                syntax = (SyntaxForm) template;
                old = expand(syntax.getDatum(), depth, syntax, seen, tr);
            } else if (template instanceof FVector) {
                int i;
                FVector fVector;
                FVector vector = (FVector) template;
                int n = vector.size();
                Object[] buffer = new Object[n];
                byte[] state = new byte[n];
                byte max_state = (byte) 0;
                for (i = 0; i < n; i++) {
                    Pair element = vector.get(i);
                    int element_depth = depth;
                    if ((element instanceof Pair) && depth > -1) {
                        Pair pair = element;
                        if (tr.matches(pair.getCar(), syntax, LispLanguage.unquotesplicing_sym)) {
                            element_depth--;
                            if (element_depth == 0) {
                                if (pair.getCdr() instanceof Pair) {
                                    Pair pair_cdr = (Pair) pair.getCdr();
                                    if (pair_cdr.getCdr() == LList.Empty) {
                                        buffer[i] = tr.rewrite_car(pair_cdr, syntax);
                                        state[i] = (byte) 3;
                                        if (state[i] > max_state) {
                                            max_state = state[i];
                                        }
                                    }
                                }
                                return tr.syntaxError("invalid used of " + pair.getCar() + " in quasiquote template");
                            }
                        }
                    }
                    buffer[i] = expand(element, element_depth, syntax, seen, tr);
                    if (buffer[i] == element) {
                        state[i] = (byte) 0;
                    } else if (buffer[i] instanceof Expression) {
                        state[i] = (byte) 2;
                    } else {
                        state[i] = (byte) 1;
                    }
                    if (state[i] > max_state) {
                        max_state = state[i];
                    }
                }
                if (max_state == (byte) 0) {
                    fVector = vector;
                } else if (max_state == (byte) 1) {
                    r0 = new FVector(buffer);
                } else {
                    Expression[] args = new Expression[n];
                    for (i = 0; i < n; i++) {
                        if (state[i] == (byte) 3) {
                            args[i] = (Expression) buffer[i];
                        } else if (max_state < (byte) 3) {
                            args[i] = coerceExpression(buffer[i], tr);
                        } else if (state[i] < (byte) 2) {
                            args[i] = leaf(new FVector(new Object[]{buffer[i]}), tr);
                        } else {
                            args[i] = makeInvokeMakeVector(new Expression[]{(Expression) buffer[i]});
                        }
                    }
                    if (max_state < (byte) 3) {
                        fVector = makeInvokeMakeVector(args);
                    } else {
                        r0 = new ApplyExp(vectorAppendMethod, args);
                    }
                }
                FVector old2 = fVector;
            } else {
                old = template;
            }
            if (template != old && map.get(template) == CYCLE) {
                tr.error('e', "cycle in non-literal data");
            }
            map.put(template, old);
            return old;
        }
    }

    private static ApplyExp makeInvokeMakeVector(Expression[] args) {
        return new ApplyExp(makeVectorMethod, args);
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            if (pair.getCdr() == LList.Empty) {
                return coerceExpression(expand(pair.getCar(), this.isQuasi ? 1 : -1, tr), tr);
            }
        }
        return tr.syntaxError("wrong number of arguments to quote");
    }

    public static Object consX$V(Object[] args) {
        return LList.consX(args);
    }

    public static Object append$V(Object[] args) {
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        int i = count - 1;
        Object obj = args[count - 1];
        while (true) {
            i--;
            if (i < 0) {
                return obj;
            }
            SyntaxForm list = args[i];
            Pair last = null;
            SyntaxForm syntax = null;
            Object obj2 = null;
            while (true) {
                if (list instanceof SyntaxForm) {
                    syntax = list;
                    list = syntax.getDatum();
                } else if (list == LList.Empty) {
                    break;
                } else {
                    Pair pair;
                    Pair list_pair = (Pair) list;
                    Object car = list_pair.getCar();
                    if (!(syntax == null || (car instanceof SyntaxForm))) {
                        car = SyntaxForms.makeForm(car, syntax.getScope());
                    }
                    Pair new_pair = new Pair(car, null);
                    if (last == null) {
                        pair = new_pair;
                    } else {
                        last.setCdr(new_pair);
                        pair = obj2;
                    }
                    last = new_pair;
                    list = list_pair.getCdr();
                    obj2 = pair;
                }
            }
            if (last != null) {
                last.setCdr(obj);
            } else {
                obj2 = obj;
            }
            obj = obj2;
        }
    }
}
