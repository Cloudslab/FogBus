package gnu.expr;

import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.LocationEnumeration;
import gnu.mapping.NamedLocation;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public class BuiltinEnvironment extends Environment {
    static final BuiltinEnvironment instance = new BuiltinEnvironment();

    static {
        instance.setName("language-builtins");
    }

    private BuiltinEnvironment() {
    }

    public static BuiltinEnvironment getInstance() {
        return instance;
    }

    public Environment getLangEnvironment() {
        Language lang = Language.getDefaultLanguage();
        return lang == null ? null : lang.getLangEnvironment();
    }

    public NamedLocation lookup(Symbol name, Object property, int hash) {
        if (property == ThreadLocation.ANONYMOUS) {
            return null;
        }
        Language lang = Language.getDefaultLanguage();
        if (lang != null) {
            return lang.lookupBuiltin(name, property, hash);
        }
        return null;
    }

    public NamedLocation getLocation(Symbol key, Object property, int hash, boolean create) {
        throw new RuntimeException();
    }

    public void define(Symbol key, Object property, Object newValue) {
        throw new RuntimeException();
    }

    public LocationEnumeration enumerateLocations() {
        return getLangEnvironment().enumerateLocations();
    }

    public LocationEnumeration enumerateAllLocations() {
        return getLangEnvironment().enumerateAllLocations();
    }

    protected boolean hasMoreElements(LocationEnumeration it) {
        throw new RuntimeException();
    }

    public NamedLocation addLocation(Symbol name, Object prop, Location loc) {
        throw new RuntimeException();
    }
}
