package gnu.expr;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.KeyPair;
import gnu.mapping.Symbol;
import gnu.text.SourceLocator;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

public class FindCapturedVars extends ExpExpVisitor<Void> {
    int backJumpPossible = 0;
    ModuleExp currentModule = null;
    Hashtable unknownDecls = null;

    public static void findCapturedVars(Expression exp, Compilation comp) {
        FindCapturedVars visitor = new FindCapturedVars();
        visitor.setContext(comp);
        exp.visit(visitor, null);
    }

    protected Expression visitApplyExp(ApplyExp exp, Void ignored) {
        int oldBackJumpPossible = this.backJumpPossible;
        boolean skipFunc = false;
        boolean skipArgs = false;
        Declaration decl;
        Expression value;
        if ((exp.func instanceof ReferenceExp) && Compilation.defaultCallConvention <= 1) {
            decl = Declaration.followAliases(((ReferenceExp) exp.func).binding);
            if (!(decl == null || !(decl.context instanceof ModuleExp) || decl.isPublic() || decl.getFlag(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM))) {
                value = decl.getValue();
                if ((value instanceof LambdaExp) && !((LambdaExp) value).getNeedsClosureEnv()) {
                    skipFunc = true;
                }
            }
        } else if ((exp.func instanceof QuoteExp) && exp.getArgCount() > 0) {
            PrimProcedure val = ((QuoteExp) exp.func).getValue();
            Expression arg0 = exp.getArg(0);
            if ((val instanceof PrimProcedure) && (arg0 instanceof ReferenceExp)) {
                PrimProcedure pproc = val;
                decl = Declaration.followAliases(((ReferenceExp) arg0).binding);
                if (!(decl == null || !(decl.context instanceof ModuleExp) || decl.getFlag(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM))) {
                    value = decl.getValue();
                    if (value instanceof ClassExp) {
                        Expression[] args = exp.getArgs();
                        if (!((LambdaExp) value).getNeedsClosureEnv()) {
                            exp.nextCall = decl.firstCall;
                            decl.firstCall = exp;
                            for (int i = 1; i < args.length; i++) {
                                args[i].visit(this, ignored);
                            }
                            skipArgs = true;
                            skipFunc = true;
                        }
                    }
                }
            }
        }
        if (!skipFunc) {
            exp.func = (Expression) exp.func.visit(this, ignored);
        }
        if (this.exitValue == null && !skipArgs) {
            exp.args = visitExps(exp.args, ignored);
        }
        if (this.backJumpPossible > oldBackJumpPossible) {
            exp.setFlag(8);
        }
        return exp;
    }

    public void visitDefaultArgs(LambdaExp exp, Void ignored) {
        if (exp.defaultArgs != null) {
            super.visitDefaultArgs(exp, ignored);
            Declaration param = exp.firstDecl();
            while (param != null) {
                if (param.isSimple()) {
                    param = param.nextDecl();
                } else {
                    exp.setFlag(true, 512);
                    return;
                }
            }
        }
    }

    protected Expression visitClassExp(ClassExp exp, Void ignored) {
        Expression ret = (Expression) super.visitClassExp(exp, ignored);
        if (!exp.explicitInit && !exp.instanceType.isInterface()) {
            Compilation.getConstructor(exp.instanceType, exp);
        } else if (exp.getNeedsClosureEnv()) {
            for (LambdaExp child = exp.firstChild; child != null; child = child.nextSibling) {
                if ("*init*".equals(child.getName())) {
                    child.setNeedsStaticLink(true);
                }
            }
        }
        if (exp.isSimple() && exp.getNeedsClosureEnv() && exp.nameDecl != null && exp.nameDecl.getType() == Compilation.typeClass) {
            exp.nameDecl.setType(Compilation.typeClassType);
        }
        return ret;
    }

    protected Expression visitModuleExp(ModuleExp exp, Void ignored) {
        ModuleExp saveModule = this.currentModule;
        Hashtable saveDecls = this.unknownDecls;
        this.currentModule = exp;
        this.unknownDecls = null;
        try {
            Expression visitLambdaExp = visitLambdaExp((LambdaExp) exp, ignored);
            return visitLambdaExp;
        } finally {
            this.currentModule = saveModule;
            this.unknownDecls = saveDecls;
        }
    }

    void maybeWarnNoDeclarationSeen(Object name, Compilation comp, SourceLocator location) {
        if (comp.warnUndefinedVariable()) {
            comp.error('w', "no declaration seen for " + name, location);
        }
    }

    protected Expression visitFluidLetExp(FluidLetExp exp, Void ignored) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.base == null) {
                Object name = decl.getSymbol();
                Declaration bind = allocUnboundDecl(name, false);
                maybeWarnNoDeclarationSeen(name, this.comp, exp);
                capture(bind);
                decl.base = bind;
            }
        }
        return (Expression) super.visitLetExp(exp, ignored);
    }

    protected Expression visitLetExp(LetExp exp, Void ignored) {
        if (exp.body instanceof BeginExp) {
            Expression[] inits = exp.inits;
            int len = inits.length;
            Expression[] exps = ((BeginExp) exp.body).exps;
            int init_index = 0;
            Declaration decl = exp.firstDecl();
            for (int begin_index = 0; begin_index < exps.length && init_index < len; begin_index++) {
                Expression st = exps[begin_index];
                if (st instanceof SetExp) {
                    SetExp set = (SetExp) st;
                    if (set.binding == decl && inits[init_index] == QuoteExp.nullExp && set.isDefining()) {
                        Expression new_value = set.new_value;
                        if (((new_value instanceof QuoteExp) || (new_value instanceof LambdaExp)) && decl.getValue() == new_value) {
                            inits[init_index] = new_value;
                            exps[begin_index] = QuoteExp.voidExp;
                        }
                        init_index++;
                        decl = decl.nextDecl();
                    }
                }
            }
        }
        return (Expression) super.visitLetExp(exp, ignored);
    }

    static Expression checkInlineable(LambdaExp current, Set<LambdaExp> seen) {
        if (current.returnContinuation == LambdaExp.unknownContinuation) {
            return current.returnContinuation;
        }
        if (seen.contains(current)) {
            return current.returnContinuation;
        }
        if (current.getCanRead() || current.isClassMethod() || current.min_args != current.max_args) {
            current.returnContinuation = LambdaExp.unknownContinuation;
            return LambdaExp.unknownContinuation;
        }
        seen.add(current);
        Expression r = current.returnContinuation;
        if (current.tailCallers != null) {
            for (LambdaExp p : current.tailCallers) {
                LambdaExp p2;
                Expression t = checkInlineable(p2, seen);
                if (t == LambdaExp.unknownContinuation) {
                    if (r == null || r == p2.body) {
                        r = p2.body;
                        current.inlineHome = p2;
                    } else {
                        current.returnContinuation = LambdaExp.unknownContinuation;
                        return t;
                    }
                } else if (r == null) {
                    r = t;
                    if (current.inlineHome == null) {
                        if (!current.nestedIn(p2)) {
                            p2 = p2.inlineHome;
                        }
                        current.inlineHome = p2;
                    }
                } else if ((t != null && r != t) || current.getFlag(32)) {
                    current.returnContinuation = LambdaExp.unknownContinuation;
                    return LambdaExp.unknownContinuation;
                }
            }
        }
        return r;
    }

    protected Expression visitLambdaExp(LambdaExp exp, Void ignored) {
        if (checkInlineable(exp, new LinkedHashSet()) != LambdaExp.unknownContinuation && (!(exp.outer instanceof ModuleExp) || exp.nameDecl == null)) {
            exp.setInlineOnly(true);
            this.backJumpPossible++;
        }
        return (Expression) super.visitLambdaExp(exp, ignored);
    }

    public void capture(Declaration decl) {
        if (!decl.getCanRead() && !decl.getCanCall()) {
            return;
        }
        if (decl.field != null && decl.field.getStaticFlag()) {
            return;
        }
        if (!this.comp.immediate || !decl.hasConstantValue()) {
            LambdaExp curLambda = getCurrentLambda();
            ScopeExp sc = decl.getContext();
            if (sc == null) {
                throw new Error("null context for " + decl + " curL:" + curLambda);
            }
            LambdaExp declLambda = sc.currentLambda();
            LambdaExp oldParent = null;
            LambdaExp chain = null;
            while (curLambda != declLambda && curLambda.getInlineOnly()) {
                LambdaExp curParent = curLambda.outerLambda();
                if (curParent != oldParent) {
                    chain = curParent.firstChild;
                    oldParent = curParent;
                }
                if (chain == null || curLambda.inlineHome == null) {
                    curLambda.setCanCall(false);
                    return;
                } else {
                    curLambda = curLambda.getCaller();
                    chain = chain.nextSibling;
                }
            }
            if (this.comp.usingCPStyle()) {
                if (curLambda instanceof ModuleExp) {
                    return;
                }
            } else if (curLambda == declLambda) {
                return;
            }
            Expression value = decl.getValue();
            LambdaExp declValue;
            if (value == null || !(value instanceof LambdaExp)) {
                declValue = null;
            } else {
                declValue = (LambdaExp) value;
                if (!declValue.getInlineOnly()) {
                    if (declValue.isHandlingTailCalls()) {
                        declValue = null;
                    } else if (declValue == curLambda && !decl.getCanRead()) {
                        return;
                    }
                }
                return;
            }
            if (decl.getFlag(65536)) {
                LambdaExp parent = curLambda;
                while (parent != declLambda) {
                    if (parent.nameDecl != null && parent.nameDecl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
                        decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                        break;
                    }
                    parent = parent.outerLambda();
                }
            }
            if (decl.base != null) {
                decl.base.setCanRead(true);
                capture(decl.base);
            } else if (decl.getCanRead() || decl.getCanCall() || declValue == null) {
                if (!decl.isStatic()) {
                    LambdaExp heapLambda = curLambda;
                    if (!decl.isFluid()) {
                        heapLambda.setImportsLexVars();
                    }
                    LambdaExp outer = heapLambda.outerLambda();
                    while (outer != declLambda && outer != null) {
                        heapLambda = outer;
                        if (!decl.getCanRead() && declValue == outer) {
                            break;
                        }
                        Declaration heapDecl = heapLambda.nameDecl;
                        if (heapDecl != null && heapDecl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
                            this.comp.error('e', "static " + heapLambda.getName() + " references non-static " + decl.getName());
                        }
                        if ((heapLambda instanceof ClassExp) && heapLambda.getName() != null && ((ClassExp) heapLambda).isSimple()) {
                            this.comp.error('w', heapLambda.nameDecl, "simple class ", " requiring lexical link (because of reference to " + decl.getName() + ") - use define-class instead");
                        }
                        heapLambda.setNeedsStaticLink();
                        outer = heapLambda.outerLambda();
                    }
                }
                if (declLambda == null) {
                    System.err.println("null declLambda for " + decl + " curL:" + curLambda);
                    for (ScopeExp c = decl.context; c != null; c = c.outer) {
                        System.err.println("- context:" + c);
                    }
                }
                declLambda.capture(decl);
            }
        }
    }

    Declaration allocUnboundDecl(Object name, boolean function) {
        Declaration decl;
        Object key = name;
        if (function && (name instanceof Symbol)) {
            if (getCompilation().getLanguage().hasSeparateFunctionNamespace()) {
                key = new KeyPair((Symbol) name, EnvironmentKey.FUNCTION);
            } else {
                function = false;
            }
        }
        if (this.unknownDecls == null) {
            this.unknownDecls = new Hashtable(100);
            decl = null;
        } else {
            decl = (Declaration) this.unknownDecls.get(key);
        }
        if (decl == null) {
            decl = this.currentModule.addDeclaration(name);
            decl.setSimple(false);
            decl.setPrivate(true);
            if (function) {
                decl.setProcedureDecl(true);
            }
            if (this.currentModule.isStatic()) {
                decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
            }
            decl.setCanRead(true);
            decl.setCanWrite(true);
            decl.setFlag(327680);
            decl.setIndirectBinding(true);
            this.unknownDecls.put(key, decl);
        }
        return decl;
    }

    protected Expression visitReferenceExp(ReferenceExp exp, Void ignored) {
        Declaration decl = exp.getBinding();
        if (decl == null) {
            decl = allocUnboundDecl(exp.getSymbol(), exp.isProcedureName());
            exp.setBinding(decl);
        }
        if (decl.getFlag(65536) && this.comp.resolve(exp.getSymbol(), exp.isProcedureName()) == null) {
            maybeWarnNoDeclarationSeen(exp.getSymbol(), this.comp, exp);
        }
        capture(exp.contextDecl(), decl);
        return exp;
    }

    void capture(Declaration containing, Declaration decl) {
        if (decl.isAlias() && (decl.value instanceof ReferenceExp)) {
            ReferenceExp rexp = decl.value;
            Declaration orig = rexp.binding;
            if (orig != null && (containing == null || !orig.needsContext())) {
                capture(rexp.contextDecl(), orig);
                return;
            }
        }
        while (decl.isFluid() && (decl.context instanceof FluidLetExp)) {
            decl = decl.base;
        }
        if (containing == null || !decl.needsContext()) {
            capture(decl);
        } else {
            capture(containing);
        }
    }

    protected Expression visitThisExp(ThisExp exp, Void ignored) {
        if (!exp.isForContext()) {
            return visitReferenceExp((ReferenceExp) exp, ignored);
        }
        getCurrentLambda().setImportsLexVars();
        return exp;
    }

    protected Expression visitSetExp(SetExp exp, Void ignored) {
        Declaration decl = exp.binding;
        if (decl == null) {
            decl = allocUnboundDecl(exp.getSymbol(), exp.isFuncDef());
            exp.binding = decl;
        }
        if (!decl.ignorable()) {
            if (!exp.isDefining()) {
                decl = Declaration.followAliases(decl);
            }
            capture(exp.contextDecl(), decl);
        }
        return (Expression) super.visitSetExp(exp, ignored);
    }
}
