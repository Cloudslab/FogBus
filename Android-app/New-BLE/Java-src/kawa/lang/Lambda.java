package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.SimpleSymbol;
import kawa.standard.object;

public class Lambda extends Syntax {
    public static final Keyword nameKeyword = Keyword.make("name");
    public Expression defaultDefault = QuoteExp.falseExp;
    public Object keyKeyword;
    public Object optionalKeyword;
    public Object restKeyword;

    public void setKeywords(Object optional, Object rest, Object key) {
        this.optionalKeyword = optional;
        this.restKeyword = rest;
        this.keyKeyword = key;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Expression exp = rewrite(form.getCdr(), tr);
        Translator.setLine(exp, (Object) form);
        return exp;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing formals in lambda");
        }
        int old_errors = tr.getMessages().getErrorCount();
        Expression lexp = new LambdaExp();
        Object pair = (Pair) obj;
        Translator.setLine(lexp, pair);
        rewrite(lexp, pair.getCar(), pair.getCdr(), tr, null);
        if (tr.getMessages().getErrorCount() > old_errors) {
            return new ErrorExp("bad lambda expression");
        }
        return lexp;
    }

    public void rewrite(LambdaExp lexp, Object formals, Object body, Translator tr, TemplateScope templateScopeRest) {
        rewriteFormals(lexp, formals, tr, templateScopeRest);
        if (body instanceof PairWithPosition) {
            lexp.setFile(((PairWithPosition) body).getFileName());
        }
        rewriteBody(lexp, rewriteAttrs(lexp, body, tr), tr);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteFormals(gnu.expr.LambdaExp r27, java.lang.Object r28, kawa.lang.Translator r29, kawa.lang.TemplateScope r30) {
        /*
        r26 = this;
        r23 = r27.getSymbol();
        if (r23 != 0) goto L_0x0017;
    L_0x0006:
        r7 = r27.getFileName();
        r10 = r27.getLineNumber();
        if (r7 == 0) goto L_0x0017;
    L_0x0010:
        if (r10 <= 0) goto L_0x0017;
    L_0x0012:
        r0 = r27;
        r0.setSourceLocation(r7, r10);
    L_0x0017:
        r4 = r28;
        r13 = -1;
        r18 = -1;
        r8 = -1;
    L_0x001d:
        r0 = r4 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x002b;
    L_0x0023:
        r20 = r4;
        r20 = (kawa.lang.SyntaxForm) r20;
        r4 = r20.getDatum();
    L_0x002b:
        r0 = r4 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 != 0) goto L_0x007e;
    L_0x0031:
        r0 = r4 instanceof gnu.mapping.Symbol;
        r23 = r0;
        if (r23 == 0) goto L_0x0236;
    L_0x0037:
        if (r13 >= 0) goto L_0x003d;
    L_0x0039:
        if (r8 >= 0) goto L_0x003d;
    L_0x003b:
        if (r18 < 0) goto L_0x0204;
    L_0x003d:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "dotted rest-arg after ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.optionalKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = ", ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.restKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = ", or ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.keyKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
    L_0x007d:
        return;
    L_0x007e:
        r16 = r4;
        r16 = (gnu.lists.Pair) r16;
        r17 = r16.getCar();
        r0 = r17;
        r0 = r0 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x0094;
    L_0x008e:
        r17 = (kawa.lang.SyntaxForm) r17;
        r17 = r17.getDatum();
    L_0x0094:
        r0 = r26;
        r0 = r0.optionalKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 != r1) goto L_0x0118;
    L_0x00a0:
        if (r13 < 0) goto L_0x00c9;
    L_0x00a2:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "multiple ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.optionalKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = " in parameter list";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x00c9:
        if (r18 >= 0) goto L_0x00cd;
    L_0x00cb:
        if (r8 < 0) goto L_0x010d;
    L_0x00cd:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r0 = r26;
        r0 = r0.optionalKeyword;
        r24 = r0;
        r24 = r24.toString();
        r23 = r23.append(r24);
        r24 = " after ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.restKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = " or ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.keyKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x010d:
        r13 = 0;
    L_0x010e:
        r4 = r16.getCdr();
        r4 = r16.getCdr();
        goto L_0x001d;
    L_0x0118:
        r0 = r26;
        r0 = r0.restKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 != r1) goto L_0x0183;
    L_0x0124:
        if (r18 < 0) goto L_0x014e;
    L_0x0126:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "multiple ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.restKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = " in parameter list";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x014e:
        if (r8 < 0) goto L_0x0180;
    L_0x0150:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r0 = r26;
        r0 = r0.restKeyword;
        r24 = r0;
        r24 = r24.toString();
        r23 = r23.append(r24);
        r24 = " after ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.keyKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0180:
        r18 = 0;
        goto L_0x010e;
    L_0x0183:
        r0 = r26;
        r0 = r0.keyKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 != r1) goto L_0x01bc;
    L_0x018f:
        if (r8 < 0) goto L_0x01b9;
    L_0x0191:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "multiple ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.keyKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = " in parameter list";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x01b9:
        r8 = 0;
        goto L_0x010e;
    L_0x01bc:
        r23 = r16.getCar();
        r24 = "::";
        r0 = r29;
        r1 = r23;
        r2 = r24;
        r23 = r0.matches(r1, r2);
        if (r23 == 0) goto L_0x01e2;
    L_0x01ce:
        r23 = r16.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x01e2;
    L_0x01da:
        r16 = r16.getCdr();
        r16 = (gnu.lists.Pair) r16;
        goto L_0x010e;
    L_0x01e2:
        if (r8 < 0) goto L_0x01e8;
    L_0x01e4:
        r8 = r8 + 1;
        goto L_0x010e;
    L_0x01e8:
        if (r18 < 0) goto L_0x01ee;
    L_0x01ea:
        r18 = r18 + 1;
        goto L_0x010e;
    L_0x01ee:
        if (r13 < 0) goto L_0x01f4;
    L_0x01f0:
        r13 = r13 + 1;
        goto L_0x010e;
    L_0x01f4:
        r0 = r27;
        r0 = r0.min_args;
        r23 = r0;
        r23 = r23 + 1;
        r0 = r23;
        r1 = r27;
        r1.min_args = r0;
        goto L_0x010e;
    L_0x0204:
        r18 = 1;
    L_0x0206:
        r23 = 1;
        r0 = r18;
        r1 = r23;
        if (r0 <= r1) goto L_0x0247;
    L_0x020e:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "multiple ";
        r23 = r23.append(r24);
        r0 = r26;
        r0 = r0.restKeyword;
        r24 = r0;
        r23 = r23.append(r24);
        r24 = " parameters";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0236:
        r23 = gnu.lists.LList.Empty;
        r0 = r23;
        if (r4 == r0) goto L_0x0206;
    L_0x023c:
        r23 = "misformed formals in lambda";
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0247:
        if (r13 >= 0) goto L_0x024a;
    L_0x0249:
        r13 = 0;
    L_0x024a:
        if (r18 >= 0) goto L_0x024e;
    L_0x024c:
        r18 = 0;
    L_0x024e:
        if (r8 >= 0) goto L_0x0251;
    L_0x0250:
        r8 = 0;
    L_0x0251:
        if (r18 <= 0) goto L_0x02d6;
    L_0x0253:
        r23 = -1;
        r0 = r23;
        r1 = r27;
        r1.max_args = r0;
    L_0x025b:
        r23 = r13 + r8;
        if (r23 <= 0) goto L_0x026d;
    L_0x025f:
        r23 = r13 + r8;
        r0 = r23;
        r0 = new gnu.expr.Expression[r0];
        r23 = r0;
        r0 = r23;
        r1 = r27;
        r1.defaultArgs = r0;
    L_0x026d:
        if (r8 <= 0) goto L_0x0279;
    L_0x026f:
        r0 = new gnu.expr.Keyword[r8];
        r23 = r0;
        r0 = r23;
        r1 = r27;
        r1.keywords = r0;
    L_0x0279:
        r4 = r28;
        r13 = 0;
        r8 = 0;
        r11 = 0;
    L_0x027e:
        r0 = r4 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x0290;
    L_0x0284:
        r20 = r4;
        r20 = (kawa.lang.SyntaxForm) r20;
        r4 = r20.getDatum();
        r30 = r20.getScope();
    L_0x0290:
        r21 = r30;
        r0 = r4 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 != 0) goto L_0x02ea;
    L_0x0298:
        r0 = r4 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x02aa;
    L_0x029e:
        r20 = r4;
        r20 = (kawa.lang.SyntaxForm) r20;
        r4 = r20.getDatum();
        r30 = r20.getScope();
    L_0x02aa:
        r0 = r4 instanceof gnu.mapping.Symbol;
        r23 = r0;
        if (r23 == 0) goto L_0x007d;
    L_0x02b0:
        r5 = new gnu.expr.Declaration;
        r5.<init>(r4);
        r23 = gnu.kawa.lispexpr.LangObjType.listType;
        r0 = r23;
        r5.setType(r0);
        r24 = 262144; // 0x40000 float:3.67342E-40 double:1.295163E-318;
        r0 = r24;
        r5.setFlag(r0);
        r23 = 0;
        r0 = r23;
        r5.noteValue(r0);
        r0 = r30;
        r1 = r27;
        r2 = r29;
        addParam(r5, r0, r1, r2);
        goto L_0x007d;
    L_0x02d6:
        r0 = r27;
        r0 = r0.min_args;
        r23 = r0;
        r23 = r23 + r13;
        r24 = r8 * 2;
        r23 = r23 + r24;
        r0 = r23;
        r1 = r27;
        r1.max_args = r0;
        goto L_0x025b;
    L_0x02ea:
        r16 = r4;
        r16 = (gnu.lists.Pair) r16;
        r17 = r16.getCar();
        r0 = r17;
        r0 = r0 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x0306;
    L_0x02fa:
        r20 = r17;
        r20 = (kawa.lang.SyntaxForm) r20;
        r17 = r20.getDatum();
        r21 = r20.getScope();
    L_0x0306:
        r0 = r26;
        r0 = r0.optionalKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 == r1) goto L_0x032a;
    L_0x0312:
        r0 = r26;
        r0 = r0.restKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 == r1) goto L_0x032a;
    L_0x031e:
        r0 = r26;
        r0 = r0.keyKeyword;
        r23 = r0;
        r0 = r17;
        r1 = r23;
        if (r0 != r1) goto L_0x0332;
    L_0x032a:
        r11 = r17;
    L_0x032c:
        r4 = r16.getCdr();
        goto L_0x027e;
    L_0x0332:
        r0 = r29;
        r1 = r16;
        r19 = r0.pushPositionOf(r1);
        r12 = 0;
        r0 = r26;
        r6 = r0.defaultDefault;
        r22 = 0;
        r23 = "::";
        r0 = r29;
        r1 = r17;
        r2 = r23;
        r23 = r0.matches(r1, r2);
        if (r23 == 0) goto L_0x035a;
    L_0x034f:
        r23 = "'::' must follow parameter name";
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x035a:
        r0 = r29;
        r1 = r17;
        r17 = r0.namespaceResolve(r1);
        r0 = r17;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r23 = r0;
        if (r23 == 0) goto L_0x03ec;
    L_0x036a:
        r12 = r17;
        r23 = r16.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x03ca;
    L_0x0378:
        r15 = r16.getCdr();
        r15 = (gnu.lists.Pair) r15;
        r23 = r15.getCar();
        r24 = "::";
        r0 = r29;
        r1 = r23;
        r2 = r24;
        r23 = r0.matches(r1, r2);
        if (r23 == 0) goto L_0x03ca;
    L_0x0390:
        r23 = r16.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 != 0) goto L_0x03c0;
    L_0x039c:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "'::' not followed by a type specifier (for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = "')";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x03c0:
        r15 = r15.getCdr();
        r15 = (gnu.lists.Pair) r15;
        r22 = r15;
        r16 = r15;
    L_0x03ca:
        if (r12 != 0) goto L_0x0576;
    L_0x03cc:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "parameter is neither name nor (name :: type) nor (name default): ";
        r23 = r23.append(r24);
        r0 = r23;
        r1 = r16;
        r23 = r0.append(r1);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x03ec:
        r0 = r17;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x03ca;
    L_0x03f4:
        r15 = r17;
        r15 = (gnu.lists.Pair) r15;
        r17 = r15.getCar();
        r0 = r17;
        r0 = r0 instanceof kawa.lang.SyntaxForm;
        r23 = r0;
        if (r23 == 0) goto L_0x0410;
    L_0x0404:
        r20 = r17;
        r20 = (kawa.lang.SyntaxForm) r20;
        r17 = r20.getDatum();
        r21 = r20.getScope();
    L_0x0410:
        r0 = r29;
        r1 = r17;
        r17 = r0.namespaceResolve(r1);
        r0 = r17;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r23 = r0;
        if (r23 == 0) goto L_0x03ca;
    L_0x0420:
        r23 = r15.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x03ca;
    L_0x042c:
        r12 = r17;
        r15 = r15.getCdr();
        r15 = (gnu.lists.Pair) r15;
        r23 = r15.getCar();
        r24 = "::";
        r0 = r29;
        r1 = r23;
        r2 = r24;
        r23 = r0.matches(r1, r2);
        if (r23 == 0) goto L_0x0490;
    L_0x0446:
        r23 = r15.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 != 0) goto L_0x0476;
    L_0x0452:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "'::' not followed by a type specifier (for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = "')";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0476:
        r15 = r15.getCdr();
        r15 = (gnu.lists.Pair) r15;
        r22 = r15;
        r23 = r15.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x04d2;
    L_0x048a:
        r15 = r15.getCdr();
        r15 = (gnu.lists.Pair) r15;
    L_0x0490:
        if (r15 == 0) goto L_0x04aa;
    L_0x0492:
        if (r11 == 0) goto L_0x04aa;
    L_0x0494:
        r6 = r15.getCar();
        r23 = r15.getCdr();
        r0 = r23;
        r0 = r0 instanceof gnu.lists.Pair;
        r23 = r0;
        if (r23 == 0) goto L_0x0504;
    L_0x04a4:
        r15 = r15.getCdr();
        r15 = (gnu.lists.Pair) r15;
    L_0x04aa:
        if (r15 == 0) goto L_0x03ca;
    L_0x04ac:
        if (r22 == 0) goto L_0x0536;
    L_0x04ae:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "duplicate type specifier for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = 39;
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x04d2:
        r23 = r15.getCdr();
        r24 = gnu.lists.LList.Empty;
        r0 = r23;
        r1 = r24;
        if (r0 != r1) goto L_0x04e0;
    L_0x04de:
        r15 = 0;
        goto L_0x0490;
    L_0x04e0:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "improper list in specifier for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = "')";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0504:
        r23 = r15.getCdr();
        r24 = gnu.lists.LList.Empty;
        r0 = r23;
        r1 = r24;
        if (r0 != r1) goto L_0x0512;
    L_0x0510:
        r15 = 0;
        goto L_0x04aa;
    L_0x0512:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "improper list in specifier for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = "')";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0536:
        r22 = r15;
        r23 = r15.getCdr();
        r24 = gnu.lists.LList.Empty;
        r0 = r23;
        r1 = r24;
        if (r0 == r1) goto L_0x03ca;
    L_0x0544:
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "junk at end of specifier for parameter '";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r12);
        r24 = 39;
        r23 = r23.append(r24);
        r24 = " after type ";
        r23 = r23.append(r24);
        r24 = r15.getCar();
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r29;
        r1 = r23;
        r0.syntaxError(r1);
        goto L_0x007d;
    L_0x0576:
        r0 = r26;
        r0 = r0.optionalKeyword;
        r23 = r0;
        r0 = r23;
        if (r11 == r0) goto L_0x058a;
    L_0x0580:
        r0 = r26;
        r0 = r0.keyKeyword;
        r23 = r0;
        r0 = r23;
        if (r11 != r0) goto L_0x059c;
    L_0x058a:
        r0 = r27;
        r0 = r0.defaultArgs;
        r23 = r0;
        r14 = r13 + 1;
        r24 = new gnu.expr.LangExp;
        r0 = r24;
        r0.<init>(r6);
        r23[r13] = r24;
        r13 = r14;
    L_0x059c:
        r0 = r26;
        r0 = r0.keyKeyword;
        r23 = r0;
        r0 = r23;
        if (r11 != r0) goto L_0x05c3;
    L_0x05a6:
        r0 = r27;
        r0 = r0.keywords;
        r24 = r0;
        r9 = r8 + 1;
        r0 = r12 instanceof gnu.mapping.Symbol;
        r23 = r0;
        if (r23 == 0) goto L_0x0603;
    L_0x05b4:
        r23 = r12;
        r23 = (gnu.mapping.Symbol) r23;
        r23 = r23.getName();
    L_0x05bc:
        r23 = gnu.expr.Keyword.make(r23);
        r24[r8] = r23;
        r8 = r9;
    L_0x05c3:
        r5 = new gnu.expr.Declaration;
        r5.<init>(r12);
        kawa.lang.Translator.setLine(r5, r4);
        if (r22 == 0) goto L_0x0608;
    L_0x05cd:
        r23 = new gnu.expr.LangExp;
        r0 = r23;
        r1 = r22;
        r0.<init>(r1);
        r0 = r23;
        r5.setTypeExp(r0);
        r24 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = r24;
        r5.setFlag(r0);
    L_0x05e2:
        r24 = 262144; // 0x40000 float:3.67342E-40 double:1.295163E-318;
        r0 = r24;
        r5.setFlag(r0);
        r23 = 0;
        r0 = r23;
        r5.noteValue(r0);
        r0 = r21;
        r1 = r27;
        r2 = r29;
        addParam(r5, r0, r1, r2);
        r0 = r29;
        r1 = r19;
        r0.popPositionOf(r1);
        goto L_0x032c;
    L_0x0603:
        r23 = r12.toString();
        goto L_0x05bc;
    L_0x0608:
        r0 = r26;
        r0 = r0.restKeyword;
        r23 = r0;
        r0 = r23;
        if (r11 != r0) goto L_0x05e2;
    L_0x0612:
        r23 = gnu.kawa.lispexpr.LangObjType.listType;
        r0 = r23;
        r5.setType(r0);
        goto L_0x05e2;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteFormals(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator, kawa.lang.TemplateScope):void");
    }

    private static void addParam(Declaration decl, ScopeExp templateScope, LambdaExp lexp, Translator tr) {
        if (templateScope != null) {
            decl = tr.makeRenamedAlias(decl, templateScope);
        }
        lexp.addDeclaration(decl);
        if (templateScope != null) {
            decl.context = templateScope;
        }
    }

    public Object rewriteAttrs(LambdaExp lexp, Object body, Translator tr) {
        String accessFlagName = null;
        String allocationFlagName = null;
        int accessFlag = 0;
        int allocationFlag = 0;
        SyntaxForm syntax0 = null;
        while (true) {
            SyntaxForm body2;
            if (!(body2 instanceof SyntaxForm)) {
                if (!(body2 instanceof Pair)) {
                    break;
                }
                Pair pair1 = (Pair) body2;
                Keyword attrName = Translator.stripSyntax(pair1.getCar());
                if (!tr.matches(attrName, "::")) {
                    if (!(attrName instanceof Keyword)) {
                        break;
                    }
                }
                attrName = null;
                SyntaxForm syntax1 = syntax0;
                SyntaxForm pair1_cdr = pair1.getCdr();
                while (pair1_cdr instanceof SyntaxForm) {
                    syntax1 = pair1_cdr;
                    pair1_cdr = syntax1.getDatum();
                }
                if (!(pair1_cdr instanceof Pair)) {
                    break;
                }
                Pair pair2 = (Pair) pair1_cdr;
                if (attrName == null) {
                    if (lexp.isClassMethod() && "*init*".equals(lexp.getName())) {
                        tr.error('e', "explicit return type for '*init*' method");
                    } else {
                        lexp.body = new LangExp(new Object[]{pair2, syntax1});
                    }
                } else if (attrName == object.accessKeyword) {
                    attrExpr = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr instanceof QuoteExp) {
                        attrValue = ((QuoteExp) attrExpr).getValue();
                        if ((attrValue instanceof SimpleSymbol) || (attrValue instanceof CharSequence)) {
                            if (lexp.nameDecl == null) {
                                tr.error('e', "access: not allowed for anonymous function");
                            } else {
                                value = attrValue.toString();
                                if ("private".equals(value)) {
                                    accessFlag = 16777216;
                                } else if ("protected".equals(value)) {
                                    accessFlag = Declaration.PROTECTED_ACCESS;
                                } else if ("public".equals(value)) {
                                    accessFlag = Declaration.PUBLIC_ACCESS;
                                } else if ("package".equals(value)) {
                                    accessFlag = Declaration.PACKAGE_ACCESS;
                                } else {
                                    tr.error('e', "unknown access specifier");
                                }
                                if (!(accessFlagName == null || value == null)) {
                                    tr.error('e', "duplicate access specifiers - " + accessFlagName + " and " + value);
                                }
                                accessFlagName = value;
                            }
                        }
                    }
                    tr.error('e', "access: value not a constant symbol or string");
                } else if (attrName == object.allocationKeyword) {
                    attrExpr = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr instanceof QuoteExp) {
                        attrValue = ((QuoteExp) attrExpr).getValue();
                        if ((attrValue instanceof SimpleSymbol) || (attrValue instanceof CharSequence)) {
                            if (lexp.nameDecl == null) {
                                tr.error('e', "allocation: not allowed for anonymous function");
                            } else {
                                value = attrValue.toString();
                                if ("class".equals(value) || "static".equals(value)) {
                                    allocationFlag = 2048;
                                } else if ("instance".equals(value)) {
                                    allocationFlag = 4096;
                                } else {
                                    tr.error('e', "unknown allocation specifier");
                                }
                                if (!(allocationFlagName == null || value == null)) {
                                    tr.error('e', "duplicate allocation specifiers - " + allocationFlagName + " and " + value);
                                }
                                allocationFlagName = value;
                            }
                        }
                    }
                    tr.error('e', "allocation: value not a constant symbol or string");
                } else if (attrName == object.throwsKeyword) {
                    SyntaxForm attrValue = pair2.getCar();
                    int count = Translator.listLength(attrValue);
                    if (count < 0) {
                        tr.error('e', "throws: not followed by a list");
                    } else {
                        Expression[] exps = new Expression[count];
                        SyntaxForm syntax2 = syntax1;
                        for (int i = 0; i < count; i++) {
                            while (attrValue instanceof SyntaxForm) {
                                syntax2 = attrValue;
                                attrValue = syntax2.getDatum();
                            }
                            Pair pair3 = (Pair) attrValue;
                            exps[i] = tr.rewrite_car(pair3, syntax2);
                            Translator.setLine(exps[i], (Object) pair3);
                            attrValue = pair3.getCdr();
                        }
                        lexp.setExceptions(exps);
                    }
                } else if (attrName == nameKeyword) {
                    attrExpr = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr instanceof QuoteExp) {
                        lexp.setName(((QuoteExp) attrExpr).getValue().toString());
                    }
                } else {
                    tr.error('w', "unknown procedure property " + attrName);
                }
                body2 = pair2.getCdr();
            } else {
                syntax0 = body2;
                body2 = syntax0.getDatum();
            }
        }
        accessFlag |= allocationFlag;
        if (accessFlag != 0) {
            lexp.nameDecl.setFlag((long) accessFlag);
        }
        if (syntax0 != null) {
            return SyntaxForms.fromDatumIfNeeded(body2, syntax0);
        }
        return body2;
    }

    public Object skipAttrs(LambdaExp lexp, Object body, Translator tr) {
        Pair body2;
        while (body2 instanceof Pair) {
            Pair pair = body2;
            if (!(pair.getCdr() instanceof Pair)) {
                break;
            }
            Object attrName = pair.getCar();
            if (!tr.matches(attrName, "::")) {
                if (!(attrName instanceof Keyword)) {
                    break;
                }
            }
            body2 = ((Pair) pair.getCdr()).getCdr();
        }
        return body2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rewriteBody(gnu.expr.LambdaExp r27, java.lang.Object r28, kawa.lang.Translator r29) {
        /*
        r26 = this;
        r11 = 0;
        r0 = r29;
        r0 = r0.curMethodLambda;
        r23 = r0;
        if (r23 != 0) goto L_0x0023;
    L_0x0009:
        r0 = r27;
        r0 = r0.nameDecl;
        r23 = r0;
        if (r23 == 0) goto L_0x0023;
    L_0x0011:
        r23 = r29.getModule();
        r24 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r23 = r23.getFlag(r24);
        if (r23 == 0) goto L_0x0023;
    L_0x001d:
        r0 = r27;
        r1 = r29;
        r1.curMethodLambda = r0;
    L_0x0023:
        r6 = r29.currentScope();
        r0 = r29;
        r1 = r27;
        r0.pushScope(r1);
        r15 = 0;
        r0 = r27;
        r0 = r0.keywords;
        r23 = r0;
        if (r23 != 0) goto L_0x00e1;
    L_0x0037:
        r8 = 0;
    L_0x0038:
        r0 = r27;
        r0 = r0.defaultArgs;
        r23 = r0;
        if (r23 != 0) goto L_0x00ec;
    L_0x0040:
        r12 = 0;
    L_0x0041:
        r4 = 0;
        r13 = 0;
        r5 = r27.firstDecl();
    L_0x0047:
        if (r5 == 0) goto L_0x00fb;
    L_0x0049:
        r23 = r5.isAlias();
        if (r23 == 0) goto L_0x0068;
    L_0x004f:
        r23 = kawa.lang.Translator.getOriginalRef(r5);
        r14 = r23.getBinding();
        r0 = r27;
        r0.replaceFollowing(r15, r14);
        r0 = r27;
        r14.context = r0;
        r0 = r29;
        r0.pushRenamedAlias(r5);
        r11 = r11 + 1;
        r5 = r14;
    L_0x0068:
        r19 = r5.getTypeExp();
        r0 = r19;
        r0 = r0 instanceof gnu.expr.LangExp;
        r23 = r0;
        if (r23 == 0) goto L_0x0089;
    L_0x0074:
        r19 = (gnu.expr.LangExp) r19;
        r21 = r19.getLangValue();
        r21 = (gnu.lists.Pair) r21;
        r0 = r29;
        r1 = r21;
        r23 = r0.exp2Type(r1);
        r0 = r23;
        r5.setType(r0);
    L_0x0089:
        r15 = r5;
        r0 = r27;
        r0 = r0.min_args;
        r23 = r0;
        r0 = r23;
        if (r4 < r0) goto L_0x00ce;
    L_0x0094:
        r0 = r27;
        r0 = r0.min_args;
        r23 = r0;
        r23 = r23 + r12;
        r0 = r23;
        if (r4 < r0) goto L_0x00b4;
    L_0x00a0:
        r0 = r27;
        r0 = r0.max_args;
        r23 = r0;
        if (r23 >= 0) goto L_0x00b4;
    L_0x00a8:
        r0 = r27;
        r0 = r0.min_args;
        r23 = r0;
        r23 = r23 + r12;
        r0 = r23;
        if (r4 == r0) goto L_0x00ce;
    L_0x00b4:
        r0 = r27;
        r0 = r0.defaultArgs;
        r23 = r0;
        r0 = r27;
        r0 = r0.defaultArgs;
        r24 = r0;
        r24 = r24[r13];
        r0 = r29;
        r1 = r24;
        r24 = r0.rewrite(r1);
        r23[r13] = r24;
        r13 = r13 + 1;
    L_0x00ce:
        r4 = r4 + 1;
        r0 = r29;
        r0 = r0.lexical;
        r23 = r0;
        r0 = r23;
        r0.push(r5);
        r5 = r5.nextDecl();
        goto L_0x0047;
    L_0x00e1:
        r0 = r27;
        r0 = r0.keywords;
        r23 = r0;
        r0 = r23;
        r8 = r0.length;
        goto L_0x0038;
    L_0x00ec:
        r0 = r27;
        r0 = r0.defaultArgs;
        r23 = r0;
        r0 = r23;
        r0 = r0.length;
        r23 = r0;
        r12 = r23 - r8;
        goto L_0x0041;
    L_0x00fb:
        r23 = r27.isClassMethod();
        if (r23 == 0) goto L_0x0121;
    L_0x0101:
        r0 = r27;
        r0 = r0.nameDecl;
        r23 = r0;
        r24 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r23 = r23.getFlag(r24);
        if (r23 != 0) goto L_0x0121;
    L_0x010f:
        r23 = 0;
        r24 = new gnu.expr.Declaration;
        r25 = gnu.expr.ThisExp.THIS_NAME;
        r24.<init>(r25);
        r0 = r27;
        r1 = r23;
        r2 = r24;
        r0.add(r1, r2);
    L_0x0121:
        r0 = r29;
        r0 = r0.curLambda;
        r18 = r0;
        r0 = r27;
        r1 = r29;
        r1.curLambda = r0;
        r0 = r27;
        r0 = r0.returnType;
        r17 = r0;
        r0 = r27;
        r0 = r0.body;
        r23 = r0;
        r0 = r23;
        r0 = r0 instanceof gnu.expr.LangExp;
        r23 = r0;
        if (r23 == 0) goto L_0x0175;
    L_0x0141:
        r0 = r27;
        r0 = r0.body;
        r23 = r0;
        r23 = (gnu.expr.LangExp) r23;
        r23 = r23.getLangValue();
        r23 = (java.lang.Object[]) r23;
        r20 = r23;
        r20 = (java.lang.Object[]) r20;
        r23 = 0;
        r23 = r20[r23];
        r23 = (gnu.lists.Pair) r23;
        r24 = 1;
        r24 = r20[r24];
        r24 = (kawa.lang.SyntaxForm) r24;
        r0 = r29;
        r1 = r23;
        r2 = r24;
        r19 = r0.rewrite_car(r1, r2);
        r23 = r29.getLanguage();
        r0 = r23;
        r1 = r19;
        r17 = r0.getTypeFor(r1);
    L_0x0175:
        r0 = r29;
        r1 = r28;
        r23 = r0.rewrite_body(r1);
        r0 = r23;
        r1 = r27;
        r1.body = r0;
        r0 = r18;
        r1 = r29;
        r1.curLambda = r0;
        r0 = r27;
        r0 = r0.body;
        r23 = r0;
        r0 = r23;
        r0 = r0 instanceof gnu.expr.BeginExp;
        r23 = r0;
        if (r23 == 0) goto L_0x0230;
    L_0x0197:
        r0 = r27;
        r0 = r0.body;
        r23 = r0;
        r23 = (gnu.expr.BeginExp) r23;
        r7 = r23.getExpressions();
        r9 = r7.length;
        r23 = 1;
        r0 = r23;
        if (r9 <= r0) goto L_0x0230;
    L_0x01aa:
        r23 = 0;
        r23 = r7[r23];
        r0 = r23;
        r0 = r0 instanceof gnu.expr.ReferenceExp;
        r23 = r0;
        if (r23 != 0) goto L_0x01ce;
    L_0x01b6:
        r23 = 0;
        r23 = r7[r23];
        r22 = r23.valueIfConstant();
        r0 = r22;
        r0 = r0 instanceof gnu.bytecode.Type;
        r23 = r0;
        if (r23 != 0) goto L_0x01ce;
    L_0x01c6:
        r0 = r22;
        r0 = r0 instanceof java.lang.Class;
        r23 = r0;
        if (r23 == 0) goto L_0x0230;
    L_0x01ce:
        r23 = 0;
        r16 = r7[r23];
        r9 = r9 + -1;
        r23 = 1;
        r0 = r23;
        if (r9 != r0) goto L_0x0218;
    L_0x01da:
        r23 = 1;
        r23 = r7[r23];
        r0 = r23;
        r1 = r27;
        r1.body = r0;
    L_0x01e4:
        r23 = r29.getLanguage();
        r0 = r27;
        r1 = r16;
        r2 = r23;
        r0.setCoercedReturnValue(r1, r2);
    L_0x01f1:
        r0 = r29;
        r1 = r27;
        r0.pop(r1);
        r27.countDecls();
        r0 = r29;
        r0.popRenamedAlias(r11);
        r27.countDecls();
        r0 = r29;
        r0 = r0.curMethodLambda;
        r23 = r0;
        r0 = r23;
        r1 = r27;
        if (r0 != r1) goto L_0x0217;
    L_0x020f:
        r23 = 0;
        r0 = r23;
        r1 = r29;
        r1.curMethodLambda = r0;
    L_0x0217:
        return;
    L_0x0218:
        r10 = new gnu.expr.Expression[r9];
        r23 = 1;
        r24 = 0;
        r0 = r23;
        r1 = r24;
        java.lang.System.arraycopy(r7, r0, r10, r1, r9);
        r23 = gnu.expr.BeginExp.canonicalize(r10);
        r0 = r23;
        r1 = r27;
        r1.body = r0;
        goto L_0x01e4;
    L_0x0230:
        r0 = r27;
        r1 = r17;
        r0.setCoercedReturnType(r1);
        goto L_0x01f1;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteBody(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator):void");
    }

    public void print(Consumer out) {
        out.write("#<builtin lambda>");
    }
}
