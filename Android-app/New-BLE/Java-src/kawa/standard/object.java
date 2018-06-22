package kawa.standard;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;

public class object extends Syntax {
    public static final Keyword accessKeyword = Keyword.make("access");
    public static final Keyword allocationKeyword = Keyword.make("allocation");
    public static final Keyword classNameKeyword = Keyword.make("class-name");
    static final Symbol coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    static final Keyword initKeyword = Keyword.make("init");
    static final Keyword init_formKeyword = Keyword.make("init-form");
    static final Keyword init_keywordKeyword = Keyword.make("init-keyword");
    static final Keyword init_valueKeyword = Keyword.make("init-value");
    static final Keyword initformKeyword = Keyword.make("initform");
    public static final Keyword interfaceKeyword = Keyword.make("interface");
    public static final object objectSyntax = new object(SchemeCompilation.lambda);
    public static final Keyword throwsKeyword = Keyword.make("throws");
    static final Keyword typeKeyword = Keyword.make("type");
    Lambda lambda;

    static {
        objectSyntax.setName("object");
    }

    public object(Lambda lambda) {
        this.lambda = lambda;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        if (!(form.getCdr() instanceof Pair)) {
            return tr.syntaxError("missing superclass specification in object");
        }
        Pair pair = (Pair) form.getCdr();
        Expression oexp = new ObjectExp();
        if (pair.getCar() instanceof FString) {
            if (!(pair.getCdr() instanceof Pair)) {
                return tr.syntaxError("missing superclass specification after object class name");
            }
            pair = (Pair) pair.getCdr();
        }
        Object[] saved = scanClassDef(pair, oexp, tr);
        if (saved == null) {
            return oexp;
        }
        rewriteClassDef(saved, tr);
        return oexp;
    }

    public Object[] scanClassDef(Pair pair, ClassExp oexp, Translator tr) {
        tr.mustCompileHere();
        Object superlist = pair.getCar();
        Pair components = pair.getCdr();
        Object classNamePair = null;
        LambdaExp method_list = null;
        LambdaExp last_method = null;
        long classAccessFlag = 0;
        Vector vector = new Vector(20);
        Pair obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                obj = ((SyntaxForm) obj).getDatum();
            }
            if (obj instanceof Pair) {
                Object savedPos2;
                pair = obj;
                Pair pair_car = pair.getCar();
                while (pair_car instanceof SyntaxForm) {
                    pair_car = ((SyntaxForm) pair_car).getDatum();
                }
                obj = pair.getCdr();
                Object savedPos1 = tr.pushPositionOf(pair);
                if (pair_car instanceof Keyword) {
                    while (obj instanceof SyntaxForm) {
                        obj = ((SyntaxForm) obj).getDatum();
                    }
                    if (obj instanceof Pair) {
                        if (pair_car == interfaceKeyword) {
                            if (obj.getCar() == Boolean.FALSE) {
                                oexp.setFlag(65536);
                            } else {
                                oexp.setFlag(32768);
                            }
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        } else if (pair_car == classNameKeyword) {
                            if (classNamePair != null) {
                                tr.error('e', "duplicate class-name specifiers");
                            }
                            classNamePair = obj;
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        } else if (pair_car == accessKeyword) {
                            savedPos2 = tr.pushPositionOf(obj);
                            classAccessFlag = addAccessFlags(obj.getCar(), classAccessFlag, Declaration.CLASS_ACCESS_FLAGS, "class", tr);
                            if (oexp.nameDecl == null) {
                                tr.error('e', "access specifier for anonymous class");
                            }
                            tr.popPositionOf(savedPos2);
                            obj = obj.getCdr();
                            tr.popPositionOf(savedPos1);
                        }
                    }
                }
                if (pair_car instanceof Pair) {
                    LList pair2 = pair_car;
                    pair_car = pair2.getCar();
                    while (pair_car instanceof SyntaxForm) {
                        pair_car = ((SyntaxForm) pair_car).getDatum();
                    }
                    if ((pair_car instanceof String) || (pair_car instanceof Symbol) || (pair_car instanceof Keyword)) {
                        Declaration decl;
                        LList lList;
                        Pair typePair = null;
                        Pair sname = pair_car;
                        int allocationFlag = 0;
                        long accessFlag = 0;
                        if (sname instanceof Keyword) {
                            decl = null;
                            lList = pair2;
                        } else {
                            decl = oexp.addDeclaration((Object) sname);
                            decl.setSimple(false);
                            decl.setFlag(1048576);
                            Translator.setLine(decl, (Object) pair2);
                            lList = pair2.getCdr();
                        }
                        int nKeywords = 0;
                        boolean seenInit = false;
                        Pair initPair = null;
                        while (lList != LList.Empty) {
                            Pair pair3 = lList;
                            while (pair3 instanceof SyntaxForm) {
                                pair3 = ((SyntaxForm) pair3).getDatum();
                            }
                            pair = pair3;
                            Pair keyPair = pair;
                            Symbol key = pair.getCar();
                            while (key instanceof SyntaxForm) {
                                key = ((SyntaxForm) key).getDatum();
                            }
                            savedPos2 = tr.pushPositionOf(pair);
                            lList = pair.getCdr();
                            if ((key == coloncolon || (key instanceof Keyword)) && (lList instanceof Pair)) {
                                nKeywords++;
                                pair = (Pair) lList;
                                Object value = pair.getCar();
                                lList = pair.getCdr();
                                if (key == coloncolon || key == typeKeyword) {
                                    typePair = pair;
                                } else if (key == allocationKeyword) {
                                    if (allocationFlag != 0) {
                                        tr.error('e', "duplicate allocation: specification");
                                    }
                                    if (matches(value, "class", tr) || matches(value, "static", tr)) {
                                        allocationFlag = 2048;
                                    } else if (matches(value, "instance", tr)) {
                                        allocationFlag = 4096;
                                    } else {
                                        tr.error('e', "unknown allocation kind '" + value + "'");
                                    }
                                } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                                    if (seenInit) {
                                        tr.error('e', "duplicate initialization");
                                    }
                                    seenInit = true;
                                    if (key != initKeyword) {
                                        initPair = pair;
                                    }
                                } else if (key == init_keywordKeyword) {
                                    if (!(value instanceof Keyword)) {
                                        tr.error('e', "invalid 'init-keyword' - not a keyword");
                                    } else if (((Keyword) value).getName() != sname.toString()) {
                                        tr.error('w', "init-keyword option ignored");
                                    }
                                } else if (key == accessKeyword) {
                                    Object savedPos3 = tr.pushPositionOf(pair);
                                    accessFlag = addAccessFlags(value, accessFlag, Declaration.FIELD_ACCESS_FLAGS, "field", tr);
                                    tr.popPositionOf(savedPos3);
                                } else {
                                    tr.error('w', "unknown slot keyword '" + key + "'");
                                }
                            } else if (lList != LList.Empty || seenInit) {
                                if ((lList instanceof Pair) && nKeywords == 0 && !seenInit && typePair == null) {
                                    pair = (Pair) lList;
                                    if (pair.getCdr() == LList.Empty) {
                                        typePair = keyPair;
                                        initPair = pair;
                                        lList = pair.getCdr();
                                        seenInit = true;
                                    }
                                }
                                lList = null;
                                break;
                            } else {
                                initPair = keyPair;
                                seenInit = true;
                            }
                            tr.popPositionOf(savedPos2);
                        }
                        if (lList != LList.Empty) {
                            tr.error('e', "invalid argument list for slot '" + sname + '\'' + " args:" + (lList == null ? "null" : lList.getClass().getName()));
                            return null;
                        }
                        if (seenInit) {
                            Object obj2 = decl != null ? decl : allocationFlag == 2048 ? Boolean.TRUE : Boolean.FALSE;
                            vector.addElement(obj2);
                            vector.addElement(initPair);
                        }
                        if (decl != null) {
                            if (typePair != null) {
                                decl.setType(tr.exp2Type(typePair));
                            }
                            if (allocationFlag != 0) {
                                decl.setFlag((long) allocationFlag);
                            }
                            if (accessFlag != 0) {
                                decl.setFlag(accessFlag);
                            }
                            decl.setCanRead(true);
                            decl.setCanWrite(true);
                        } else if (!seenInit) {
                            tr.error('e', "missing field name");
                            return null;
                        }
                    } else if (pair_car instanceof Pair) {
                        Pair mpair = pair_car;
                        Object mname = mpair.getCar();
                        if ((mname instanceof String) || (mname instanceof Symbol)) {
                            LambdaExp lexp = new LambdaExp();
                            Translator.setLine(oexp.addMethod(lexp, mname), (Object) mpair);
                            if (last_method == null) {
                                method_list = lexp;
                            } else {
                                last_method.nextSibling = lexp;
                            }
                            last_method = lexp;
                        } else {
                            tr.error('e', "missing method name");
                            return null;
                        }
                    } else {
                        tr.error('e', "invalid field/method definition");
                    }
                    tr.popPositionOf(savedPos1);
                } else {
                    tr.error('e', "object member not a list");
                    return null;
                }
            }
            tr.error('e', "object member not a list");
            return null;
        }
        if (classAccessFlag != 0) {
            oexp.nameDecl.setFlag(classAccessFlag);
        }
        return new Object[]{oexp, components, vector, method_list, superlist, classNamePair};
    }

    public void rewriteClassDef(Object[] saved, Translator tr) {
        int i;
        Throwable th;
        ScopeExp oexp = saved[0];
        SyntaxForm components = saved[1];
        Vector inits = saved[2];
        LambdaExp method_list = saved[3];
        Pair superlist = saved[4];
        Object classNamePair = saved[5];
        oexp.firstChild = method_list;
        int num_supers = Translator.listLength(superlist);
        if (num_supers < 0) {
            tr.error('e', "object superclass specification not a list");
            num_supers = 0;
        }
        Expression[] supers = new Expression[num_supers];
        for (i = 0; i < num_supers; i++) {
            while (superlist instanceof SyntaxForm) {
                superlist = ((SyntaxForm) superlist).getDatum();
            }
            Pair superpair = superlist;
            supers[i] = tr.rewrite_car(superpair, false);
            if (supers[i] instanceof ReferenceExp) {
                Declaration decl = Declaration.followAliases(((ReferenceExp) supers[i]).getBinding());
                if (decl != null) {
                    Expression svalue = decl.getValue();
                    if (svalue instanceof ClassExp) {
                        ((ClassExp) svalue).setFlag(131072);
                    }
                }
            }
            superlist = superpair.getCdr();
        }
        if (classNamePair != null) {
            Object classNameVal = tr.rewrite_car((Pair) classNamePair, false).valueIfConstant();
            if (classNameVal instanceof CharSequence) {
                String classNameSpecifier = classNameVal.toString();
                if (classNameSpecifier.length() > 0) {
                    oexp.classNameSpecifier = classNameSpecifier;
                }
            }
            Object savedPos = tr.pushPositionOf(classNamePair);
            tr.error('e', "class-name specifier must be a non-empty string literal");
            tr.popPositionOf(savedPos);
        }
        oexp.supers = supers;
        oexp.setTypes(tr);
        int len = inits.size();
        for (i = 0; i < len; i += 2) {
            Object init = inits.elementAt(i + 1);
            if (init != null) {
                rewriteInit(inits.elementAt(i), oexp, (Pair) init, tr, null);
            }
        }
        tr.push(oexp);
        LambdaExp meth = method_list;
        int init_index = 0;
        SyntaxForm componentsSyntax = null;
        SyntaxForm obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                componentsSyntax = obj;
                obj = componentsSyntax.getDatum();
            }
            Pair pair = (Pair) obj;
            Object savedPos1 = tr.pushPositionOf(pair);
            SyntaxForm pair_car = pair.getCar();
            SyntaxForm memberSyntax = componentsSyntax;
            while (pair_car instanceof SyntaxForm) {
                memberSyntax = pair_car;
                pair_car = memberSyntax.getDatum();
            }
            obj = pair.getCdr();
            if ((pair_car instanceof Keyword) && (obj instanceof Pair)) {
                obj = ((Pair) obj).getCdr();
                tr.popPositionOf(savedPos1);
            } else {
                pair = (Pair) pair_car;
                Object pair_car2 = pair.getCar();
                SyntaxForm memberCarSyntax = memberSyntax;
                while (pair_car2 instanceof SyntaxForm) {
                    memberCarSyntax = (SyntaxForm) pair_car2;
                    pair_car2 = memberCarSyntax.getDatum();
                }
                if ((pair_car2 instanceof String) || (pair_car2 instanceof Symbol) || (pair_car2 instanceof Keyword)) {
                    Object type = null;
                    int nKeywords = 0;
                    Object args = pair_car2 instanceof Keyword ? pair : pair.getCdr();
                    Pair initPair = null;
                    SyntaxForm initSyntax = null;
                    while (args != LList.Empty) {
                        while (args instanceof SyntaxForm) {
                            memberSyntax = (SyntaxForm) args;
                            args = memberSyntax.getDatum();
                        }
                        pair = (Pair) args;
                        Symbol key = pair.getCar();
                        while (key instanceof SyntaxForm) {
                            key = ((SyntaxForm) key).getDatum();
                        }
                        Object savedPos2 = tr.pushPositionOf(pair);
                        LList args2 = pair.getCdr();
                        if ((key == coloncolon || (key instanceof Keyword)) && (args2 instanceof Pair)) {
                            nKeywords++;
                            pair = (Pair) args2;
                            Object value = pair.getCar();
                            args = pair.getCdr();
                            if (key == coloncolon || key == typeKeyword) {
                                type = value;
                            } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                                initPair = pair;
                                initSyntax = memberSyntax;
                            }
                        } else if (args2 != LList.Empty || initPair != null) {
                            if ((args2 instanceof Pair) && nKeywords == 0 && initPair == null && r50 == null) {
                                pair = (Pair) args2;
                                if (pair.getCdr() == LList.Empty) {
                                    Symbol symbol = key;
                                    initPair = pair;
                                    initSyntax = memberSyntax;
                                    args = pair.getCdr();
                                }
                            }
                            break;
                        } else {
                            initPair = pair;
                            initSyntax = memberSyntax;
                        }
                        tr.popPositionOf(savedPos2);
                    }
                    if (initPair != null) {
                        int init_index2 = init_index + 1;
                        Boolean d = inits.elementAt(init_index);
                        if (d instanceof Declaration) {
                            boolean isStatic = ((Declaration) d).getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                        } else {
                            try {
                                if (d == Boolean.TRUE) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                init_index = init_index2;
                            }
                        }
                        init_index = init_index2 + 1;
                        if (inits.elementAt(init_index2) == null) {
                            rewriteInit(d, oexp, initPair, tr, initSyntax);
                        }
                    }
                } else {
                    try {
                        if (pair_car2 instanceof Pair) {
                            ScopeExp save_scope = tr.currentScope();
                            if (memberSyntax != null) {
                                tr.setCurrentScope(memberSyntax.getScope());
                            }
                            if ("*init*".equals(meth.getName())) {
                                meth.setReturnType(Type.voidType);
                            }
                            Translator.setLine((Expression) meth, (Object) pair);
                            LambdaExp saveLambda = tr.curMethodLambda;
                            tr.curMethodLambda = meth;
                            Lambda lambda = this.lambda;
                            Object cdr = ((Pair) pair_car2).getCdr();
                            Object cdr2 = pair.getCdr();
                            TemplateScope scope = (memberCarSyntax == null || (memberSyntax != null && memberCarSyntax.getScope() == memberSyntax.getScope())) ? null : memberCarSyntax.getScope();
                            lambda.rewrite(meth, cdr, cdr2, tr, scope);
                            tr.curMethodLambda = saveLambda;
                            if (memberSyntax != null) {
                                tr.setCurrentScope(save_scope);
                            }
                            meth = meth.nextSibling;
                        } else {
                            tr.syntaxError("invalid field/method definition");
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                tr.popPositionOf(savedPos1);
            }
        }
        if (oexp.initMethod != null) {
            oexp.initMethod.outer = oexp;
        }
        if (oexp.clinitMethod != null) {
            oexp.clinitMethod.outer = oexp;
        }
        tr.pop(oexp);
        oexp.declareParts(tr);
        return;
        tr.popPositionOf(savedPos1);
        throw th;
    }

    private static void rewriteInit(Object d, ClassExp oexp, Pair initPair, Translator tr, SyntaxForm initSyntax) {
        boolean isStatic = d instanceof Declaration ? ((Declaration) d).getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) : d == Boolean.TRUE;
        LambdaExp initMethod = isStatic ? oexp.clinitMethod : oexp.initMethod;
        if (initMethod == null) {
            initMethod = new LambdaExp(new BeginExp());
            initMethod.setClassMethod(true);
            initMethod.setReturnType(Type.voidType);
            if (isStatic) {
                initMethod.setName("$clinit$");
                oexp.clinitMethod = initMethod;
            } else {
                initMethod.setName("$finit$");
                oexp.initMethod = initMethod;
                initMethod.add(null, new Declaration(ThisExp.THIS_NAME));
            }
            initMethod.nextSibling = oexp.firstChild;
            oexp.firstChild = initMethod;
        }
        tr.push((ScopeExp) initMethod);
        LambdaExp saveLambda = tr.curMethodLambda;
        tr.curMethodLambda = initMethod;
        Expression initValue = tr.rewrite_car(initPair, initSyntax);
        if (d instanceof Declaration) {
            Declaration decl = (Declaration) d;
            Expression sexp = new SetExp(decl, initValue);
            sexp.setLocation(decl);
            decl.noteValue(null);
            initValue = sexp;
        } else {
            initValue = Compilation.makeCoercion(initValue, new QuoteExp(Type.voidType));
        }
        ((BeginExp) initMethod.body).add(initValue);
        tr.curMethodLambda = saveLambda;
        tr.pop(initMethod);
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else if (!(exp instanceof Pair)) {
            return false;
        } else {
            Object qvalue = tr.matchQuoted((Pair) exp);
            if (!(qvalue instanceof SimpleSymbol)) {
                return false;
            }
            value = qvalue.toString();
        }
        if (tag == null || tag.equals(value)) {
            return true;
        }
        return false;
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if (((-1 ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair p = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        LList cdr = pair.getCdr();
        if (cdr == LList.Empty || icar == 0) {
            return icar;
        }
        if (cdr instanceof Pair) {
            long icdr = matchAccess2((Pair) cdr, tr);
            if (icdr != 0) {
                return icar | icdr;
            }
        }
        return 0;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216;
        }
        if ("protected".equals(value)) {
            return 33554432;
        }
        if ("public".equals(value)) {
            return 67108864;
        }
        if ("package".equals(value)) {
            return 134217728;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0;
    }
}
