package gnu.mapping;

import android.support.v4.app.FragmentTransaction;

public abstract class Procedure1 extends Procedure {
    public abstract Object apply1(Object obj) throws Throwable;

    public Procedure1(String n) {
        super(n);
    }

    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public Object apply0() throws Throwable {
        throw new WrongArguments(this, 0);
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        throw new WrongArguments(this, 2);
    }

    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        throw new WrongArguments(this, 3);
    }

    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        throw new WrongArguments(this, 4);
    }

    public Object applyN(Object[] args) throws Throwable {
        if (args.length == 1) {
            return apply1(args[0]);
        }
        throw new WrongArguments(this, args.length);
    }
}
