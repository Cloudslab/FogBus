package gnu.expr;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.Type;
import gnu.kawa.functions.AppendValues;
import java.util.HashSet;

public class FindTailCalls extends ExpExpVisitor<Expression> {
    public static void findTailCalls(Expression exp, Compilation comp) {
        FindTailCalls visitor = new FindTailCalls();
        visitor.setContext(comp);
        visitor.visit(exp, exp);
    }

    protected Expression visitExpression(Expression exp, Expression returnContinuation) {
        return (Expression) super.visitExpression(exp, exp);
    }

    public Expression[] visitExps(Expression[] exps) {
        int n = exps.length;
        for (int i = 0; i < n; i++) {
            Expression expi = exps[i];
            exps[i] = (Expression) visit(expi, expi);
        }
        return exps;
    }

    protected Expression visitApplyExp(ApplyExp exp, Expression returnContinuation) {
        boolean inTailContext;
        if (returnContinuation == this.currentLambda.body) {
            inTailContext = true;
        } else {
            inTailContext = false;
        }
        if (inTailContext) {
            exp.setTailCall(true);
        }
        exp.context = this.currentLambda;
        LambdaExp lexp = null;
        if (exp.func instanceof ReferenceExp) {
            Declaration binding = Declaration.followAliases(exp.func.binding);
            if (binding != null) {
                if (!binding.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
                    exp.nextCall = binding.firstCall;
                    binding.firstCall = exp;
                }
                Compilation comp = getCompilation();
                binding.setCanCall();
                if (!comp.mustCompile) {
                    binding.setCanRead();
                }
                Expression value = binding.getValue();
                if (value instanceof LambdaExp) {
                    lexp = (LambdaExp) value;
                }
            }
        } else if ((exp.func instanceof LambdaExp) && !(exp.func instanceof ClassExp)) {
            lexp = exp.func;
            visitLambdaExp(lexp, false);
            lexp.setCanCall(true);
        } else if (!(exp.func instanceof QuoteExp) || ((QuoteExp) exp.func).getValue() != AppendValues.appendValues) {
            exp.func = visitExpression(exp.func, exp.func);
        }
        if (!(lexp == null || lexp.returnContinuation == returnContinuation || (lexp == this.currentLambda && inTailContext))) {
            if (inTailContext) {
                if (lexp.tailCallers == null) {
                    lexp.tailCallers = new HashSet();
                }
                lexp.tailCallers.add(this.currentLambda);
            } else if (lexp.returnContinuation == null) {
                lexp.returnContinuation = returnContinuation;
                lexp.inlineHome = this.currentLambda;
            } else {
                lexp.returnContinuation = LambdaExp.unknownContinuation;
                lexp.inlineHome = null;
            }
        }
        exp.args = visitExps(exp.args);
        return exp;
    }

    protected Expression visitBlockExp(BlockExp exp, Expression returnContinuation) {
        exp.body = (Expression) exp.body.visit(this, returnContinuation);
        if (exp.exitBody != null) {
            exp.exitBody = (Expression) exp.exitBody.visit(this, exp.exitBody);
        }
        return exp;
    }

    protected Expression visitBeginExp(BeginExp exp, Expression returnContinuation) {
        int n = exp.length - 1;
        int i = 0;
        while (i <= n) {
            exp.exps[i] = (Expression) exp.exps[i].visit(this, i == n ? returnContinuation : exp.exps[i]);
            i++;
        }
        return exp;
    }

    protected Expression visitFluidLetExp(FluidLetExp exp, Expression returnContinuation) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            decl.setCanRead(true);
            if (decl.base != null) {
                decl.base.setCanRead(true);
            }
        }
        visitLetDecls(exp);
        exp.body = (Expression) exp.body.visit(this, exp.body);
        postVisitDecls(exp);
        return exp;
    }

    void visitLetDecls(LetExp exp) {
        Declaration decl = exp.firstDecl();
        int n = exp.inits.length;
        int i = 0;
        while (i < n) {
            Expression init = visitSetExp(decl, exp.inits[i]);
            if (init == QuoteExp.undefined_exp) {
                Expression value = decl.getValue();
                if ((value instanceof LambdaExp) || (value != init && (value instanceof QuoteExp))) {
                    init = value;
                }
            }
            exp.inits[i] = init;
            i++;
            decl = decl.nextDecl();
        }
    }

    protected Expression visitLetExp(LetExp exp, Expression returnContinuation) {
        visitLetDecls(exp);
        exp.body = (Expression) exp.body.visit(this, returnContinuation);
        postVisitDecls(exp);
        return exp;
    }

    public void postVisitDecls(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            Expression value = decl.getValue();
            if (value instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) value;
                if (decl.getCanRead()) {
                    lexp.setCanRead(true);
                }
                if (decl.getCanCall()) {
                    lexp.setCanCall(true);
                }
            }
            if (decl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) && (value instanceof ReferenceExp)) {
                Declaration context = ((ReferenceExp) value).contextDecl();
                if (context != null && context.isPrivate()) {
                    context.setFlag(524288);
                }
            }
        }
    }

    protected Expression visitIfExp(IfExp exp, Expression returnContinuation) {
        exp.test = (Expression) exp.test.visit(this, exp.test);
        exp.then_clause = (Expression) exp.then_clause.visit(this, returnContinuation);
        Expression else_clause = exp.else_clause;
        if (else_clause != null) {
            exp.else_clause = (Expression) else_clause.visit(this, returnContinuation);
        }
        return exp;
    }

    protected Expression visitLambdaExp(LambdaExp exp, Expression returnContinuation) {
        visitLambdaExp(exp, true);
        return exp;
    }

    final void visitLambdaExp(LambdaExp exp, boolean canRead) {
        LambdaExp parent = this.currentLambda;
        this.currentLambda = exp;
        if (canRead) {
            exp.setCanRead(true);
        }
        try {
            if (exp.defaultArgs != null) {
                exp.defaultArgs = visitExps(exp.defaultArgs);
            }
            if (this.exitValue == null && exp.body != null) {
                exp.body = (Expression) exp.body.visit(this, exp.getInlineOnly() ? exp : exp.body);
            }
            this.currentLambda = parent;
            postVisitDecls(exp);
        } catch (Throwable th) {
            this.currentLambda = parent;
        }
    }

    protected Expression visitClassExp(ClassExp exp, Expression returnContinuation) {
        LambdaExp parent = this.currentLambda;
        this.currentLambda = exp;
        try {
            for (LambdaExp child = exp.firstChild; child != null && this.exitValue == null; child = child.nextSibling) {
                visitLambdaExp(child, false);
            }
            this.currentLambda = parent;
            return exp;
        } catch (Throwable th) {
            this.currentLambda = parent;
        }
    }

    protected Expression visitReferenceExp(ReferenceExp exp, Expression returnContinuation) {
        Declaration decl = Declaration.followAliases(exp.binding);
        if (decl != null) {
            Type type = decl.type;
            if (type != null && type.isVoid()) {
                return QuoteExp.voidExp;
            }
            decl.setCanRead(true);
        }
        Declaration ctx = exp.contextDecl();
        if (ctx == null) {
            return exp;
        }
        ctx.setCanRead(true);
        return exp;
    }

    final Expression visitSetExp(Declaration decl, Expression value) {
        if (decl == null || decl.getValue() != value || !(value instanceof LambdaExp) || (value instanceof ClassExp) || decl.isPublic()) {
            return (Expression) value.visit(this, value);
        }
        LambdaExp lexp = (LambdaExp) value;
        visitLambdaExp(lexp, false);
        return lexp;
    }

    protected Expression visitSetExp(SetExp exp, Expression returnContinuation) {
        Declaration decl = exp.binding;
        if (decl != null && decl.isAlias()) {
            if (exp.isDefining()) {
                exp.new_value = (Expression) exp.new_value.visit(this, exp.new_value);
                return exp;
            }
            decl = Declaration.followAliases(decl);
        }
        Declaration ctx = exp.contextDecl();
        if (ctx != null) {
            ctx.setCanRead(true);
        }
        Expression value = visitSetExp(decl, exp.new_value);
        if (decl != null && (decl.context instanceof LetExp) && value == decl.getValue() && ((value instanceof LambdaExp) || (value instanceof QuoteExp))) {
            return QuoteExp.voidExp;
        }
        exp.new_value = value;
        return exp;
    }

    protected Expression visitTryExp(TryExp exp, Expression returnContinuation) {
        exp.try_clause = (Expression) exp.try_clause.visit(this, exp.finally_clause == null ? returnContinuation : exp.try_clause);
        CatchClause catch_clause = exp.catch_clauses;
        while (this.exitValue == null && catch_clause != null) {
            catch_clause.body = (Expression) catch_clause.body.visit(this, exp.finally_clause == null ? returnContinuation : catch_clause.body);
            catch_clause = catch_clause.getNext();
        }
        Expression finally_clause = exp.finally_clause;
        if (finally_clause != null) {
            exp.finally_clause = (Expression) finally_clause.visit(this, finally_clause);
        }
        return exp;
    }

    protected Expression visitSynchronizedExp(SynchronizedExp exp, Expression returnContinuation) {
        exp.object = (Expression) exp.object.visit(this, exp.object);
        exp.body = (Expression) exp.body.visit(this, exp.body);
        return exp;
    }
}
