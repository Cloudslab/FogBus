package gnu.mapping;

public abstract class Procedure0 extends Procedure {
    public abstract Object apply0() throws Throwable;

    public Procedure0(String n) {
        super(n);
    }

    public int numArgs() {
        return 0;
    }

    public Object apply1(Object arg1) {
        throw new WrongArguments(this, 1);
    }

    public Object apply2(Object arg1, Object arg2) {
        throw new WrongArguments(this, 2);
    }

    public Object apply3(Object arg1, Object arg2, Object arg3) {
        throw new WrongArguments(this, 3);
    }

    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) {
        throw new WrongArguments(this, 4);
    }

    public Object applyN(Object[] args) throws Throwable {
        if (args.length == 0) {
            return apply0();
        }
        throw new WrongArguments(this, args.length);
    }
}
