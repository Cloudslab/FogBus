package gnu.commonlisp.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.reflect.FieldLocation;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import java.lang.reflect.Field;
import kawa.lang.Syntax;

public abstract class Lisp2 extends LispLanguage {
    public static final LList FALSE = LList.Empty;
    public static final Symbol TRUE = Namespace.getDefault().getSymbol("t");
    public static final Expression nilExpr = new QuoteExp(FALSE);

    public boolean isTrue(Object value) {
        return value != FALSE;
    }

    public Object booleanObject(boolean b) {
        return b ? TRUE : FALSE;
    }

    public void emitPushBoolean(boolean value, CodeAttr code) {
        if (value) {
            code.emitGetStatic(ClassType.make("gnu.commonlisp.lang.Lisp2").getDeclaredField("TRUE"));
        } else {
            code.emitGetStatic(Compilation.scmListType.getDeclaredField("Empty"));
        }
    }

    public Object noValue() {
        return FALSE;
    }

    public boolean hasSeparateFunctionNamespace() {
        return true;
    }

    public boolean selfEvaluatingSymbol(Object obj) {
        return (obj instanceof Keyword) || obj == TRUE || obj == FALSE;
    }

    public Object getEnvPropertyFor(Field fld, Object value) {
        if (Compilation.typeProcedure.getReflectClass().isAssignableFrom(fld.getType()) || (value instanceof Syntax)) {
            return EnvironmentKey.FUNCTION;
        }
        return null;
    }

    public int getNamespaceOf(Declaration decl) {
        if (decl.isAlias()) {
            return 3;
        }
        return decl.isProcedureDecl() ? 2 : 1;
    }

    public static Object asSymbol(String name) {
        if (name == "nil") {
            return FALSE;
        }
        return Environment.getCurrent().getSymbol(name);
    }

    protected Symbol fromLangSymbol(Object obj) {
        if (obj == LList.Empty) {
            return this.environ.getSymbol("nil");
        }
        return super.fromLangSymbol(obj);
    }

    public static Object getString(String name) {
        return new FString(name);
    }

    public static Object getString(Symbol symbol) {
        return getString(symbol.getName());
    }

    protected void defun(String name, Object value) {
        this.environ.define(getSymbol(name), EnvironmentKey.FUNCTION, value);
        if (value instanceof Named) {
            Named n = (Named) value;
            if (n.getName() == null) {
                n.setName(name);
            }
        }
    }

    protected void defun(Symbol sym, Object value) {
        this.environ.define(sym, EnvironmentKey.FUNCTION, value);
        if (value instanceof Procedure) {
            Procedure n = (Procedure) value;
            if (n.getSymbol() == null) {
                n.setSymbol(sym);
            }
        }
    }

    private void defun(Procedure proc) {
        defun(proc.getName(), (Object) proc);
    }

    protected void importLocation(Location loc) {
        Symbol name = ((NamedLocation) loc).getKeySymbol();
        if (!this.environ.isBound(name, EnvironmentKey.FUNCTION)) {
            loc = loc.getBase();
            if ((loc instanceof FieldLocation) && ((FieldLocation) loc).isProcedureOrSyntax()) {
                this.environ.addLocation(name, EnvironmentKey.FUNCTION, loc);
                return;
            }
            Object val = loc.get(null);
            if (val == null) {
                return;
            }
            if ((val instanceof Procedure) || (val instanceof Syntax)) {
                defun(name, val);
            } else if (val instanceof LangObjType) {
                defun(name, ((LangObjType) val).getConstructor());
            } else {
                define(name.getName(), val);
            }
        }
    }

    public ReadTable createReadTable() {
        ReadTable tab = new Lisp2ReadTable();
        tab.initialize();
        tab.setInitialColonIsKeyword(true);
        return tab;
    }
}
