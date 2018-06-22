package gnu.mapping;

public class WrappedException extends RuntimeException {
    public WrappedException(String message) {
        super(message);
    }

    public WrappedException(Throwable e) {
        this(e.toString(), e);
    }

    public WrappedException(String message, Throwable e) {
        super(message, e);
    }

    public Throwable getException() {
        return getCause();
    }

    public String toString() {
        return getMessage();
    }

    public static RuntimeException wrapIfNeeded(Throwable ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }
        return new WrappedException(ex);
    }
}
