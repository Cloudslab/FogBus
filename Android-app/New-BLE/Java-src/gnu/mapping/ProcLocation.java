package gnu.mapping;

public class ProcLocation extends Location {
    Object[] args;
    Procedure proc;

    public ProcLocation(Procedure proc, Object[] args) {
        this.proc = proc;
        this.args = args;
    }

    public Object get(Object defaultValue) {
        try {
            return this.proc.applyN(this.args);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            WrappedException wrappedException = new WrappedException(ex3);
        }
    }

    public void set(Object value) {
        int len = this.args.length;
        Object[] xargs = new Object[(len + 1)];
        xargs[len] = value;
        System.arraycopy(this.args, 0, xargs, 0, len);
        try {
            this.proc.setN(xargs);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            WrappedException wrappedException = new WrappedException(ex3);
        }
    }

    public boolean isBound() {
        return true;
    }
}
