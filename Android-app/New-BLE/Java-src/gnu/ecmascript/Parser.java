package gnu.ecmascript;

import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.lists.Sequence;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.Char;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Vector;
import kawa.standard.Scheme;

public class Parser {
    public static final Expression[] emptyArgs = new Expression[0];
    static Expression emptyStatement = new QuoteExp(Values.empty);
    public static Expression eofExpr = new QuoteExp(Sequence.eofValue);
    public int errors;
    Lexer lexer;
    InPort port;
    Object previous_token;
    Object token;

    public Parser(InPort port) {
        this.port = port;
        this.lexer = new Lexer(port);
    }

    public Expression parseConditionalExpression() throws IOException, SyntaxException {
        Expression exp1 = parseBinaryExpression(1);
        if (peekToken() != Lexer.condToken) {
            return exp1;
        }
        skipToken();
        Expression exp2 = parseAssignmentExpression();
        if (getToken() != Lexer.colonToken) {
            return syntaxError("expected ':' in conditional expression");
        }
        return new IfExp(exp1, exp2, parseAssignmentExpression());
    }

    public Expression parseAssignmentExpression() throws IOException, SyntaxException {
        Expression exp1 = parseConditionalExpression();
        Char token = peekToken();
        Expression exp2;
        if (token == Lexer.equalToken) {
            skipToken();
            exp2 = parseAssignmentExpression();
            if (!(exp1 instanceof ReferenceExp)) {
                return syntaxError("unmplemented non-symbol ihs in assignment");
            }
            Expression sex = new SetExp(((ReferenceExp) exp1).getName(), exp2);
            sex.setDefining(true);
            return sex;
        } else if (!(token instanceof Reserved)) {
            return exp1;
        } else {
            Reserved op = (Reserved) token;
            if (!op.isAssignmentOp()) {
                return exp1;
            }
            skipToken();
            exp2 = parseAssignmentExpression();
            return new ApplyExp(new QuoteExp(op.proc), new Expression[]{exp1, exp2});
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parseExpression() throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = this;
        r7 = 0;
        r1 = 0;
        r3 = 0;
    L_0x0003:
        r0 = r10.parseAssignmentExpression();
        r8 = r10.peekToken();
        r9 = gnu.ecmascript.Lexer.commaToken;
        if (r8 == r9) goto L_0x0015;
    L_0x000f:
        r2 = 1;
    L_0x0010:
        if (r1 != 0) goto L_0x0027;
    L_0x0012:
        if (r2 == 0) goto L_0x0017;
    L_0x0014:
        return r0;
    L_0x0015:
        r2 = r7;
        goto L_0x0010;
    L_0x0017:
        r8 = 2;
        r1 = new gnu.expr.Expression[r8];
    L_0x001a:
        r4 = r3 + 1;
        r1[r3] = r0;
        if (r2 == 0) goto L_0x0041;
    L_0x0020:
        r0 = new gnu.expr.BeginExp;
        r0.<init>(r1);
        r3 = r4;
        goto L_0x0014;
    L_0x0027:
        if (r2 == 0) goto L_0x0039;
    L_0x0029:
        r8 = r1.length;
        r9 = r3 + 1;
        if (r8 == r9) goto L_0x001a;
    L_0x002e:
        if (r2 == 0) goto L_0x003d;
    L_0x0030:
        r6 = r3 + 1;
    L_0x0032:
        r5 = new gnu.expr.Expression[r6];
        java.lang.System.arraycopy(r1, r7, r5, r7, r3);
        r1 = r5;
        goto L_0x001a;
    L_0x0039:
        r8 = r1.length;
        if (r8 > r3) goto L_0x001a;
    L_0x003c:
        goto L_0x002e;
    L_0x003d:
        r8 = r1.length;
        r6 = r8 * 2;
        goto L_0x0032;
    L_0x0041:
        r10.skipToken();
        r3 = r4;
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.ecmascript.Parser.parseExpression():gnu.expr.Expression");
    }

    public Object peekTokenOrLine() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object peekToken() throws IOException, SyntaxException {
        if (this.token == null) {
            this.token = this.lexer.getToken();
        }
        while (this.token == Lexer.eolToken) {
            skipToken();
            this.token = this.lexer.getToken();
        }
        return this.token;
    }

    public Object getToken() throws IOException, SyntaxException {
        Object result = peekToken();
        skipToken();
        return result;
    }

    public final void skipToken() {
        if (this.token != Lexer.eofToken) {
            this.previous_token = this.token;
            this.token = null;
        }
    }

    public void getSemicolon() throws IOException, SyntaxException {
        this.token = peekToken();
        if (this.token == Lexer.semicolonToken) {
            skipToken();
        } else if (this.token != Lexer.rbraceToken && this.token != Lexer.eofToken && this.previous_token != Lexer.eolToken) {
            syntaxError("missing ';' after expression");
        }
    }

    public Expression parsePrimaryExpression() throws IOException, SyntaxException {
        Char result = getToken();
        if (result instanceof QuoteExp) {
            return (QuoteExp) result;
        }
        if (result instanceof String) {
            return new ReferenceExp((String) result);
        }
        if (result != Lexer.lparenToken) {
            return syntaxError("unexpected token: " + result);
        }
        Expression expr = parseExpression();
        Char token = getToken();
        return token != Lexer.rparenToken ? syntaxError("expected ')' - got:" + token) : expr;
    }

    public Expression makePropertyAccessor(Expression exp, Expression prop) {
        return null;
    }

    public Expression[] parseArguments() throws IOException, SyntaxException {
        skipToken();
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
            return emptyArgs;
        }
        Vector args = new Vector(10);
        while (true) {
            args.addElement(parseAssignmentExpression());
            Char token = getToken();
            if (token == Lexer.rparenToken) {
                Expression[] exps = new Expression[args.size()];
                args.copyInto(exps);
                return exps;
            } else if (token != Lexer.commaToken) {
                syntaxError("invalid token '" + token + "' in argument list");
            }
        }
    }

    public Expression makeNewExpression(Expression exp, Expression[] args) {
        if (args == null) {
            args = emptyArgs;
        }
        return new ApplyExp(null, args);
    }

    public Expression makeCallExpression(Expression exp, Expression[] args) {
        return new ApplyExp(exp, args);
    }

    public String getIdentifier() throws IOException, SyntaxException {
        Object token = getToken();
        if (token instanceof String) {
            return (String) token;
        }
        syntaxError("missing identifier");
        return "??";
    }

    public Expression parseLeftHandSideExpression() throws IOException, SyntaxException {
        int newCount = 0;
        while (peekToken() == Lexer.newToken) {
            newCount++;
            skipToken();
        }
        Expression exp = parsePrimaryExpression();
        while (true) {
            Char token = peekToken();
            if (token != Lexer.dotToken) {
                if (token != Lexer.lbracketToken) {
                    if (token != Lexer.lparenToken) {
                        break;
                    }
                    Expression[] args = parseArguments();
                    System.err.println("after parseArgs:" + peekToken());
                    if (newCount > 0) {
                        exp = makeNewExpression(exp, args);
                        newCount--;
                    } else {
                        exp = makeCallExpression(exp, args);
                    }
                } else {
                    skipToken();
                    Expression prop = parseExpression();
                    token = getToken();
                    if (token != Lexer.rbracketToken) {
                        return syntaxError("expected ']' - got:" + token);
                    }
                    exp = makePropertyAccessor(exp, prop);
                }
            } else {
                skipToken();
                exp = makePropertyAccessor(exp, new QuoteExp(getIdentifier()));
            }
        }
        while (newCount > 0) {
            exp = makeNewExpression(exp, null);
            newCount--;
        }
        return exp;
    }

    public Expression parsePostfixExpression() throws IOException, SyntaxException {
        Expression exp = parseLeftHandSideExpression();
        Reserved op = peekTokenOrLine();
        if (op != Reserved.opPlusPlus && op != Reserved.opMinusMinus) {
            return exp;
        }
        skipToken();
        return new ApplyExp(new QuoteExp(op.proc), new Expression[]{exp});
    }

    public Expression parseUnaryExpression() throws IOException, SyntaxException {
        return parsePostfixExpression();
    }

    public Expression syntaxError(String message) {
        this.errors++;
        OutPort err = OutPort.errDefault();
        String current_filename = this.port.getName();
        int current_line = this.port.getLineNumber() + 1;
        int current_column = this.port.getColumnNumber() + 1;
        if (current_line > 0) {
            if (current_filename != null) {
                err.print(current_filename);
            }
            err.print(':');
            err.print(current_line);
            if (current_column > 1) {
                err.print(':');
                err.print(current_column);
            }
            err.print(": ");
        }
        err.println(message);
        return new ErrorExp(message);
    }

    public Expression parseBinaryExpression(int prio) throws IOException, SyntaxException {
        Expression exp1 = parseUnaryExpression();
        while (true) {
            this.token = peekToken();
            if (!(this.token instanceof Reserved)) {
                break;
            }
            Reserved op = this.token;
            if (op.prio < prio) {
                break;
            }
            getToken();
            Expression exp2 = parseBinaryExpression(op.prio + 1);
            exp1 = new ApplyExp(new QuoteExp(op.proc), new Expression[]{exp1, exp2});
        }
        return exp1;
    }

    public Expression parseIfStatement() throws IOException, SyntaxException {
        skipToken();
        Char token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Expression test_part = parseExpression();
        token = getToken();
        if (token != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token);
        }
        Expression else_part;
        Expression then_part = parseStatement();
        if (peekToken() == Lexer.elseToken) {
            skipToken();
            else_part = parseStatement();
        } else {
            else_part = null;
        }
        return new IfExp(test_part, then_part, else_part);
    }

    public Expression buildLoop(Expression init, Expression test, Expression incr, Expression body) {
        if (init != null) {
            return new BeginExp(new Expression[]{init, buildLoop(null, test, incr, body)});
        }
        throw new Error("not implemented - buildLoop");
    }

    public Expression parseWhileStatement() throws IOException, SyntaxException {
        skipToken();
        Char token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Expression test_part = parseExpression();
        token = getToken();
        if (token != Lexer.rparenToken) {
            return syntaxError("expected ')' - got:" + token);
        }
        return buildLoop(null, test_part, null, parseStatement());
    }

    public Expression parseFunctionDefinition() throws IOException, SyntaxException {
        skipToken();
        Object name = getIdentifier();
        Char token = getToken();
        if (token != Lexer.lparenToken) {
            return syntaxError("expected '(' - got:" + token);
        }
        Vector args = new Vector(10);
        if (peekToken() == Lexer.rparenToken) {
            skipToken();
        } else {
            while (true) {
                args.addElement(getIdentifier());
                token = getToken();
                if (token == Lexer.rparenToken) {
                    break;
                } else if (token != Lexer.commaToken) {
                    syntaxError("invalid token '" + token + "' in argument list");
                }
            }
        }
        Expression lexp = new LambdaExp(parseBlock());
        lexp.setName(name);
        Expression sexp = new SetExp(name, lexp);
        sexp.setDefining(true);
        return sexp;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression parseBlock() throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r9 = this;
        r8 = 0;
        r0 = 0;
        r6 = r9.getToken();
        r7 = gnu.ecmascript.Lexer.lbraceToken;
        if (r6 == r7) goto L_0x0011;
    L_0x000a:
        r6 = "extened '{'";
        r6 = r9.syntaxError(r6);
    L_0x0010:
        return r6;
    L_0x0011:
        r2 = 0;
    L_0x0012:
        r6 = r9.peekToken();
        r9.token = r6;
        r6 = r9.token;
        r7 = gnu.ecmascript.Lexer.rbraceToken;
        if (r6 != r7) goto L_0x0034;
    L_0x001e:
        r9.skipToken();
        if (r0 != 0) goto L_0x0026;
    L_0x0023:
        r6 = emptyStatement;
        goto L_0x0010;
    L_0x0026:
        r1 = 1;
    L_0x0027:
        if (r0 != 0) goto L_0x0036;
    L_0x0029:
        r6 = 2;
        r0 = new gnu.expr.Expression[r6];
    L_0x002c:
        if (r1 == 0) goto L_0x004d;
    L_0x002e:
        r6 = new gnu.expr.BeginExp;
        r6.<init>(r0);
        goto L_0x0010;
    L_0x0034:
        r1 = 0;
        goto L_0x0027;
    L_0x0036:
        if (r1 == 0) goto L_0x0045;
    L_0x0038:
        r6 = r0.length;
        if (r6 == r2) goto L_0x002c;
    L_0x003b:
        if (r1 == 0) goto L_0x0049;
    L_0x003d:
        r5 = r2;
    L_0x003e:
        r4 = new gnu.expr.Expression[r5];
        java.lang.System.arraycopy(r0, r8, r4, r8, r2);
        r0 = r4;
        goto L_0x002c;
    L_0x0045:
        r6 = r0.length;
        if (r6 > r2) goto L_0x002c;
    L_0x0048:
        goto L_0x003b;
    L_0x0049:
        r6 = r0.length;
        r5 = r6 * 2;
        goto L_0x003e;
    L_0x004d:
        r3 = r2 + 1;
        r6 = r9.parseStatement();
        r0[r2] = r6;
        r2 = r3;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.ecmascript.Parser.parseBlock():gnu.expr.Expression");
    }

    public Expression parseStatement() throws IOException, SyntaxException {
        Char token = peekToken();
        if (token instanceof Reserved) {
            switch (((Reserved) token).prio) {
                case 31:
                    return parseIfStatement();
                case 32:
                    return parseWhileStatement();
                case 41:
                    return parseFunctionDefinition();
            }
        }
        if (token == Lexer.eofToken) {
            return eofExpr;
        }
        if (token == Lexer.semicolonToken) {
            skipToken();
            return emptyStatement;
        } else if (token == Lexer.lbraceToken) {
            return parseBlock();
        } else {
            Expression exp = parseExpression();
            getSemicolon();
            return exp;
        }
    }

    public static void main(String[] args) {
        Language language = new Scheme();
        InPort inp = InPort.inDefault();
        if (inp instanceof TtyInPort) {
            ((TtyInPort) inp).setPrompter(new Prompter());
        }
        Parser parser = new Parser(inp);
        OutPort out = OutPort.outDefault();
        while (true) {
            try {
                Expression expr = parser.parseStatement();
                if (expr != eofExpr) {
                    out.print("[Expression: ");
                    expr.print(out);
                    out.println("]");
                    Object result = expr.eval(Environment.user());
                    out.print("result: ");
                    out.print(result);
                    out.println();
                } else {
                    return;
                }
            } catch (Throwable ex) {
                System.err.println("caught exception:" + ex);
                ex.printStackTrace(System.err);
                return;
            }
        }
    }
}
