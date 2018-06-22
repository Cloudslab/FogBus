package gnu.mapping;

public abstract class ProcedureN extends Procedure {
    public static final Object[] noArgs = new Object[0];

    public abstract Object applyN(Object[] objArr) throws Throwable;

    public ProcedureN(String n) {
        super(n);
    }

    public Object apply0() throws Throwable {
        return applyN(noArgs);
    }

    public Object apply1(Object arg1) throws Throwable {
        return applyN(new Object[]{arg1});
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        return applyN(new Object[]{arg1, arg2});
    }

    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        return applyN(new Object[]{arg1, arg2, arg3});
    }

    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        return applyN(new Object[]{arg1, arg2, arg3, arg4});
    }
}
