package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.LetExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.xml.ChildAxis;
import gnu.kawa.xml.CoerceNodes;
import gnu.kawa.xml.DescendantAxis;
import gnu.kawa.xml.DescendantOrSelfAxis;
import gnu.kawa.xml.NodeSetType;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.SortNodes;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.xquery.lang.XQuery;

public class CompileMisc {
    static final Method castMethod = typeXDataType.getDeclaredMethod("cast", 1);
    static final Method castableMethod = typeXDataType.getDeclaredMethod("castable", 1);
    static final ClassType typeTuples = ClassType.make("gnu.xquery.util.OrderedTuples");
    static final ClassType typeXDataType = ClassType.make("gnu.kawa.xml.XDataType");

    public static Expression validateCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded != exp) {
            return folded;
        }
        Expression exp2;
        if ((((Compare) proc).flags & 32) == 0) {
            exp2 = new ApplyExp(ClassType.make("gnu.xquery.util.Compare").getDeclaredMethod("apply", 4), new Expression[]{new QuoteExp(IntNum.make(cproc.flags)), exp.getArg(0), exp.getArg(1), QuoteExp.nullExp});
        }
        if (exp2.getTypeRaw() == null) {
            exp2.setType(XDataType.booleanType);
        }
        return exp2;
    }

    public static Expression validateBooleanValue(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg = args[0];
            Type type = arg.getType();
            if (type == XDataType.booleanType) {
                return arg;
            }
            if (type == null) {
                exp.setType(XDataType.booleanType);
            }
            if (arg instanceof QuoteExp) {
                try {
                    return BooleanValue.booleanValue(((QuoteExp) arg).getValue()) ? XQuery.trueExp : XQuery.falseExp;
                } catch (Throwable th) {
                    String message = "cannot convert to a boolean";
                    visitor.getMessages().error('e', message);
                    return new ErrorExp(message);
                }
            }
        }
        return exp;
    }

    public static Expression validateArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyValuesFilter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ValuesFilter vproc = (ValuesFilter) proc;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        Expression exp2 = args[1];
        if (!(exp2 instanceof LambdaExp)) {
            return exp;
        }
        LambdaExp lexp2 = (LambdaExp) exp2;
        if (lexp2.min_args != 3 || lexp2.max_args != 3) {
            return exp;
        }
        exp.setType(args[0].getType());
        Compilation parser = visitor.getCompilation();
        Declaration dotArg = lexp2.firstDecl();
        Declaration posArg = dotArg.nextDecl();
        Declaration lastArg = posArg.nextDecl();
        lexp2.setInlineOnly(true);
        lexp2.returnContinuation = exp;
        lexp2.inlineHome = visitor.getCurrentLambda();
        lexp2.remove(posArg, lastArg);
        lexp2.min_args = 2;
        lexp2.max_args = 2;
        if (!lastArg.getCanRead() && vproc.kind != 'R') {
            return exp;
        }
        Type seqType;
        Method sizeMethod;
        parser.letStart();
        Expression seq = args[0];
        if (vproc.kind == 'P') {
            seqType = seq.getType();
            sizeMethod = Compilation.typeValues.getDeclaredMethod("countValues", 1);
        } else {
            seqType = SortNodes.typeSortedNodes;
            Expression applyExp = new ApplyExp(SortNodes.sortNodes, new Expression[]{seq});
            sizeMethod = CoerceNodes.typeNodes.getDeclaredMethod("size", 0);
            seq = applyExp;
        }
        Declaration sequence = parser.letVariable("sequence", seqType, seq);
        parser.letEnter();
        Expression pred = lexp2.body;
        if (lexp2.body.getType() != XDataType.booleanType) {
            pred = new ApplyExp(ValuesFilter.matchesMethod, new Expression[]{pred, new ReferenceExp(posArg)});
        }
        if (vproc.kind == 'R') {
            Declaration declaration = new Declaration(null, Type.intType);
            Expression init = new ApplyExp(AddOp.$Mn, new Expression[]{new ReferenceExp(lastArg), new ReferenceExp(declaration)});
            Expression init2 = new ApplyExp(AddOp.$Pl, new Expression[]{init, new QuoteExp(IntNum.one())});
            Expression let = new LetExp(new Expression[]{init2});
            lexp2.replaceFollowing(dotArg, declaration);
            let.add(posArg);
            let.body = pred;
            pred = let;
        }
        lexp2.body = new IfExp(pred, new ReferenceExp(dotArg), QuoteExp.voidExp);
        ApplyExp doMap = new ApplyExp(ValuesMap.valuesMapWithPos, new Expression[]{lexp2, new ReferenceExp(sequence)});
        doMap.setType(dotArg.getType());
        lexp2.returnContinuation = doMap;
        Expression lastInit = new ApplyExp(sizeMethod, new Expression[]{new ReferenceExp(sequence)});
        LetExp let2 = new LetExp(new Expression[]{lastInit});
        let2.add(lastArg);
        let2.body = gnu.kawa.functions.CompileMisc.validateApplyValuesMap(doMap, visitor, required, ValuesMap.valuesMapWithPos);
        return parser.letDone(let2);
    }

    public static Expression validateApplyRelativeStep(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        Expression exp1 = args[0];
        Expression exp2 = args[1];
        Compilation comp = visitor.getCompilation();
        if ((exp2 instanceof LambdaExp) && comp.mustCompile) {
            LambdaExp lexp2 = (LambdaExp) exp2;
            if (lexp2.min_args == 3 && lexp2.max_args == 3) {
                lexp2.setInlineOnly(true);
                lexp2.returnContinuation = exp;
                lexp2.inlineHome = visitor.getCurrentLambda();
                exp2 = lexp2.body;
                Declaration posArg = lexp2.firstDecl().nextDecl();
                Declaration lastArg = posArg.nextDecl();
                posArg.setNext(lastArg.nextDecl());
                lastArg.setNext(null);
                lexp2.min_args = 2;
                lexp2.max_args = 2;
                Type type1 = exp1.getType();
                if (type1 == null || NodeType.anyNodeTest.compare(type1) != -3) {
                    Type rtype = exp.getTypeRaw();
                    if (rtype == null || rtype == Type.pointer_type) {
                        Type rtypePrime = OccurrenceType.itemPrimeType(exp2.getType());
                        if (NodeType.anyNodeTest.compare(rtypePrime) >= 0) {
                            rtype = NodeSetType.getInstance(rtypePrime);
                        } else {
                            rtype = OccurrenceType.getInstance(rtypePrime, 0, -1);
                        }
                        exp.setType(rtype);
                    }
                    if (lastArg.getCanRead()) {
                        Type typeNodes = CoerceNodes.typeNodes;
                        comp.letStart();
                        Declaration sequence = comp.letVariable(null, typeNodes, new ApplyExp(CoerceNodes.coerceNodes, new Expression[]{exp1}));
                        comp.letEnter();
                        Expression lastInit = new ApplyExp(typeNodes.getDeclaredMethod("size", 0), new Expression[]{new ReferenceExp(sequence)});
                        LetExp lastLet = new LetExp(new Expression[]{lastInit});
                        lastLet.addDeclaration(lastArg);
                        lastLet.body = new ApplyExp(exp.getFunction(), new Expression[]{new ReferenceExp(sequence), lexp2});
                        return comp.letDone(lastLet);
                    }
                    Expression result = exp;
                    if (exp2 instanceof ApplyExp) {
                        Expression aexp2 = (ApplyExp) exp2;
                        if (aexp2.getFunction().valueIfConstant() instanceof ValuesFilter) {
                            Expression vexp2 = aexp2.getArgs()[1];
                            if (vexp2 instanceof LambdaExp) {
                                LambdaExp lvexp2 = (LambdaExp) vexp2;
                                Declaration dot2 = lvexp2.firstDecl();
                                if (dot2 != null) {
                                    Declaration pos2 = dot2.nextDecl();
                                    if (pos2 != null && pos2.nextDecl() == null && !pos2.getCanRead() && ClassType.make("java.lang.Number").compare(lvexp2.body.getType()) == -3) {
                                        exp2 = aexp2.getArg(0);
                                        lexp2.body = exp2;
                                        aexp2.setArg(0, exp);
                                        result = aexp2;
                                    }
                                }
                            }
                        }
                    }
                    if (!(exp1 instanceof ApplyExp) || !(exp2 instanceof ApplyExp)) {
                        return result;
                    }
                    ApplyExp aexp1 = (ApplyExp) exp1;
                    ApplyExp aexp22 = (ApplyExp) exp2;
                    RelativeStep p1 = aexp1.getFunction().valueIfConstant();
                    Object p2 = aexp22.getFunction().valueIfConstant();
                    if (p1 != RelativeStep.relativeStep || !(p2 instanceof ChildAxis) || aexp1.getArgCount() != 2) {
                        return result;
                    }
                    Expression exp12 = aexp1.getArg(1);
                    if (!(exp12 instanceof LambdaExp)) {
                        return result;
                    }
                    LambdaExp lexp12 = (LambdaExp) exp12;
                    if (!(lexp12.body instanceof ApplyExp) || ((ApplyExp) lexp12.body).getFunction().valueIfConstant() != DescendantOrSelfAxis.anyNode) {
                        return result;
                    }
                    exp.setArg(0, aexp1.getArg(0));
                    aexp22.setFunction(new QuoteExp(DescendantAxis.make(((ChildAxis) p2).getNodePredicate())));
                    return result;
                }
                String message = "step input is " + visitor.getCompilation().getLanguage().formatType(type1) + " - not a node sequence";
                visitor.getMessages().error('e', message);
                return new ErrorExp(message);
            }
        }
        return exp;
    }

    public static Expression validateApplyOrderedMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length <= 2) {
            return exp;
        }
        Expression[] rargs = new Expression[(args.length - 1)];
        System.arraycopy(args, 1, rargs, 0, rargs.length);
        xargs = new Expression[2];
        Method makeTupleMethod = typeTuples.getDeclaredMethod("make$V", 2);
        xargs[0] = args[0];
        xargs[1] = new ApplyExp(makeTupleMethod, rargs);
        return new ApplyExp(proc, xargs);
    }

    public static void compileOrderedMap(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Variable consumer = code.pushScope().addVariable(code, typeTuples, null);
        args[1].compile(comp, Target.pushValue(typeTuples));
        code.emitStore(consumer);
        args[0].compile(comp, new ConsumerTarget(consumer));
        Method mm = typeTuples.getDeclaredMethod("run$X", 1);
        code.emitLoad(consumer);
        PrimProcedure.compileInvoke(comp, mm, target, exp.isTailCall(), 182, Type.pointer_type);
        code.popScope();
    }

    public static Expression validateApplyCastAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        exp = CompileReflect.inlineClassName(exp, 0, visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 2 && (args[0] instanceof QuoteExp) && (((QuoteExp) args[0]).getValue() instanceof XDataType)) {
            return new ApplyExp(castMethod, args);
        }
        return exp;
    }

    public static Expression validateApplyCastableAs(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        exp = CompileReflect.inlineClassName(exp, 1, visitor);
        Expression[] args = exp.getArgs();
        if (args.length != 2 || !(args[1] instanceof QuoteExp) || !(((QuoteExp) args[1]).getValue() instanceof XDataType)) {
            return exp;
        }
        return new ApplyExp(castableMethod, new Expression[]{args[1], args[0]});
    }
}
