package gnu.expr;

import gnu.bytecode.Type;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.UnboundLocationException;
import gnu.text.SourceLocator;

public class ReferenceExp extends AccessExp {
    public static final int DONT_DEREFERENCE = 2;
    public static final int PREFER_BINDING2 = 8;
    public static final int PROCEDURE_NAME = 4;
    public static final int TYPE_NAME = 16;
    static int counter;
    int id;

    public final boolean getDontDereference() {
        return (this.flags & 2) != 0;
    }

    public final void setDontDereference(boolean setting) {
        setFlag(setting, 2);
    }

    public final boolean isUnknown() {
        return Declaration.isUnknown(this.binding);
    }

    public final boolean isProcedureName() {
        return (this.flags & 4) != 0;
    }

    public final void setProcedureName(boolean setting) {
        setFlag(setting, 4);
    }

    public ReferenceExp(Object symbol) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.symbol = symbol;
    }

    public ReferenceExp(Object symbol, Declaration binding) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.symbol = symbol;
        this.binding = binding;
    }

    public ReferenceExp(Declaration binding) {
        this(binding.getSymbol(), binding);
    }

    protected boolean mustCompile() {
        return false;
    }

    public final Object valueIfConstant() {
        if (this.binding != null) {
            Expression dvalue = this.binding.getValue();
            if (dvalue != null) {
                return dvalue.valueIfConstant();
            }
        }
        return null;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object value;
        Object property = null;
        if (this.binding != null && this.binding.isAlias() && !getDontDereference() && (this.binding.value instanceof ReferenceExp)) {
            ReferenceExp rexp = this.binding.value;
            if (rexp.getDontDereference() && rexp.binding != null) {
                Expression v = rexp.binding.getValue();
                if ((v instanceof QuoteExp) || (v instanceof ReferenceExp) || (v instanceof LambdaExp)) {
                    v.apply(ctx);
                    return;
                }
            }
            value = this.binding.value.eval(ctx);
        } else if (this.binding != null && this.binding.field != null && this.binding.field.getDeclaringClass().isExisting() && (!getDontDereference() || this.binding.isIndirectBinding())) {
            try {
                value = this.binding.field.getReflectField().get(this.binding.field.getStaticFlag() ? null : contextDecl().getValue().eval(ctx));
            } catch (Exception ex) {
                throw new UnboundLocationException("exception evaluating " + this.symbol + " from " + this.binding.field + " - " + ex, (SourceLocator) this);
            }
        } else if (this.binding != null && (((this.binding.value instanceof QuoteExp) || (this.binding.value instanceof LambdaExp)) && this.binding.value != QuoteExp.undefined_exp && (!getDontDereference() || this.binding.isIndirectBinding()))) {
            value = this.binding.value.eval(ctx);
        } else if (this.binding == null || ((this.binding.context instanceof ModuleExp) && !this.binding.isPrivate())) {
            Environment env = Environment.getCurrent();
            Object sym = this.symbol instanceof Symbol ? (Symbol) this.symbol : env.getSymbol(this.symbol.toString());
            if (getFlag(8) && isProcedureName()) {
                property = EnvironmentKey.FUNCTION;
            }
            if (getDontDereference()) {
                value = env.getLocation((Symbol) sym, property);
            } else {
                String unb = Location.UNBOUND;
                String value2 = env.get(sym, property, unb);
                if (value2 == unb) {
                    throw new UnboundLocationException(sym, (SourceLocator) this);
                }
            }
            ctx.writeValue(value);
            return;
        } else {
            value = ctx.evalFrames[ScopeExp.nesting(this.binding.context)][this.binding.evalIndex];
        }
        if (!getDontDereference() && this.binding.isIndirectBinding()) {
            value = ((Location) value).get();
        }
        ctx.writeValue(value);
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof ConsumerTarget) || !((ConsumerTarget) target).compileWrite(this, comp)) {
            this.binding.load(this, this.flags, comp, target);
        }
    }

    protected Expression deepCopy(IdentityHashTable mapper) {
        ReferenceExp copy = new ReferenceExp(mapper.get(this.symbol, this.symbol), (Declaration) mapper.get(this.binding, this.binding));
        copy.flags = getFlags();
        return copy;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitReferenceExp(this, d);
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        decl = this.binding;
        if (decl != null && !decl.getFlag(65536)) {
            decl = Declaration.followAliases(decl);
            if (!decl.isIndirectBinding()) {
                Expression dval = decl.getValue();
                if (dval != null) {
                    return dval.validateApply(exp, visitor, required, decl);
                }
            }
        } else if (getSymbol() instanceof Symbol) {
            Object fval = Environment.getCurrent().getFunction((Symbol) getSymbol(), null);
            if (fval instanceof Procedure) {
                return new QuoteExp(fval).validateApply(exp, visitor, required, null);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public void print(OutPort ps) {
        ps.print("(Ref/");
        ps.print(this.id);
        if (this.symbol != null && (this.binding == null || this.symbol.toString() != this.binding.getName())) {
            ps.print('/');
            ps.print(this.symbol);
        }
        if (this.binding != null) {
            ps.print('/');
            ps.print(this.binding);
        }
        ps.print(")");
    }

    public Type getType() {
        Declaration decl = this.binding;
        if (decl == null || decl.isFluid()) {
            return Type.pointer_type;
        }
        if (getDontDereference()) {
            return Compilation.typeLocation;
        }
        decl = Declaration.followAliases(decl);
        Type type = decl.getType();
        if (type == null || type == Type.pointer_type) {
            Expression value = decl.getValue();
            if (!(value == null || value == QuoteExp.undefined_exp)) {
                Expression save = decl.value;
                decl.value = null;
                type = value.getType();
                decl.value = save;
            }
        }
        if (type == Type.toStringType) {
            return Type.javalangStringType;
        }
        return type;
    }

    public boolean isSingleValue() {
        if (this.binding == null || !this.binding.getFlag(262144)) {
            return super.isSingleValue();
        }
        return true;
    }

    public boolean side_effects() {
        return this.binding == null || !this.binding.isLexical();
    }

    public String toString() {
        return "RefExp/" + this.symbol + '/' + this.id + '/';
    }
}
