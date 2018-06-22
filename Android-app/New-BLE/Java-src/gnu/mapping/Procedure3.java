package gnu.mapping;

public abstract class Procedure3 extends Procedure {
    public abstract Object apply3(Object obj, Object obj2, Object obj3) throws Throwable;

    public Procedure3(String n) {
        super(n);
    }

    public int numArgs() {
        return 12291;
    }

    public Object apply0() {
        throw new WrongArguments(this, 0);
    }

    public Object apply1(Object arg1) {
        throw new WrongArguments(this, 1);
    }

    public Object apply2(Object arg1, Object arg2) {
        throw new WrongArguments(this, 2);
    }

    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) {
        throw new WrongArguments(this, 4);
    }

    public Object applyN(Object[] args) throws Throwable {
        if (args.length == 3) {
            return apply3(args[0], args[1], args[2]);
        }
        throw new WrongArguments(this, args.length);
    }
}
