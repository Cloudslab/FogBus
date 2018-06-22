package gnu.mapping;

public class LocationProc extends Procedure0or1 implements HasSetter {
    Location loc;

    public LocationProc(Location loc) {
        this.loc = loc;
    }

    public static LocationProc makeNamed(Symbol name, Location loc) {
        LocationProc lproc = new LocationProc(loc);
        lproc.setSymbol(name);
        return lproc;
    }

    public LocationProc(Location loc, Procedure converter) {
        this.loc = loc;
        if (converter != null) {
            pushConverter(converter);
        }
    }

    public void pushConverter(Procedure converter) {
        this.loc = ConstrainedLocation.make(this.loc, converter);
    }

    public Object apply0() throws Throwable {
        return this.loc.get();
    }

    public Object apply1(Object value) throws Throwable {
        set0(value);
        return Values.empty;
    }

    public void set0(Object value) throws Throwable {
        this.loc.set(value);
    }

    public Procedure getSetter() {
        return new Setter0(this);
    }

    public final Location getLocation() {
        return this.loc;
    }

    public String toString() {
        if (getSymbol() != null) {
            return super.toString();
        }
        return "#<location-proc " + this.loc + ">";
    }
}
