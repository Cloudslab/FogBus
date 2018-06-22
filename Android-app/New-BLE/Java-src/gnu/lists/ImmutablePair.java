package gnu.lists;

public class ImmutablePair extends Pair {
    public ImmutablePair(Object carval, Object cdrval) {
        this.car = carval;
        this.cdr = cdrval;
    }

    public void setCar(Object car) {
        throw new UnsupportedOperationException("cannot modify read-only pair");
    }

    public void setCdr(Object cdr) {
        throw new UnsupportedOperationException("cannot modify read-only pair");
    }
}
