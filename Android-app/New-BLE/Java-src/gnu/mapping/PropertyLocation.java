package gnu.mapping;

import gnu.lists.LList;
import gnu.lists.Pair;

public class PropertyLocation extends Location {
    Pair pair;

    public final Object get(Object defaultValue) {
        return this.pair.getCar();
    }

    public boolean isBound() {
        return true;
    }

    public final void set(Object newValue) {
        this.pair.setCar(newValue);
    }

    public static Object getPropertyList(Object symbol, Environment env) {
        return env.get(Symbol.PLIST, symbol, LList.Empty);
    }

    public static Object getPropertyList(Object symbol) {
        return Environment.getCurrent().get(Symbol.PLIST, symbol, LList.Empty);
    }

    public static void setPropertyList(Object symbol, Object plist, Environment env) {
        synchronized (env) {
            Location lloc = env.lookup(Symbol.PLIST, symbol);
            if (symbol instanceof Symbol) {
                Pair pair;
                Object property;
                Symbol sym = (Symbol) symbol;
                Object p = lloc.get(LList.Empty);
                while (p instanceof Pair) {
                    pair = (Pair) p;
                    property = pair.getCar();
                    if (plistGet(plist, property, null) != null) {
                        env.remove(sym, property);
                    }
                    p = ((Pair) pair.getCdr()).getCdr();
                }
                p = plist;
                while (p instanceof Pair) {
                    PropertyLocation ploc;
                    Pair valuePair;
                    pair = (Pair) p;
                    property = pair.getCar();
                    Location loc = env.lookup(sym, property);
                    if (loc != null) {
                        loc = loc.getBase();
                        if (loc instanceof PropertyLocation) {
                            ploc = (PropertyLocation) loc;
                            valuePair = (Pair) pair.getCdr();
                            ploc.pair = valuePair;
                            p = valuePair.getCdr();
                        }
                    }
                    ploc = new PropertyLocation();
                    env.addLocation(sym, property, ploc);
                    valuePair = (Pair) pair.getCdr();
                    ploc.pair = valuePair;
                    p = valuePair.getCdr();
                }
            }
            lloc.set(plist);
        }
    }

    public static void setPropertyList(Object symbol, Object plist) {
        setPropertyList(symbol, plist, Environment.getCurrent());
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue, Environment env) {
        if (!(symbol instanceof Symbol)) {
            if (!(symbol instanceof String)) {
                return plistGet(env.get(Symbol.PLIST, symbol, LList.Empty), property, defaultValue);
            }
            symbol = Namespace.getDefaultSymbol((String) symbol);
        }
        return env.get((Symbol) symbol, property, defaultValue);
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue) {
        return getProperty(symbol, property, defaultValue, Environment.getCurrent());
    }

    public static void putProperty(Object symbol, Object property, Object newValue, Environment env) {
        Location lloc;
        if (!(symbol instanceof Symbol)) {
            if (symbol instanceof String) {
                symbol = Namespace.getDefaultSymbol((String) symbol);
            } else {
                lloc = env.getLocation(Symbol.PLIST, symbol);
                lloc.set(plistPut(lloc.get(LList.Empty), property, newValue));
                return;
            }
        }
        Location loc = env.lookup((Symbol) symbol, property);
        if (loc != null) {
            loc = loc.getBase();
            if (loc instanceof PropertyLocation) {
                ((PropertyLocation) loc).set(newValue);
                return;
            }
        }
        lloc = env.getLocation(Symbol.PLIST, symbol);
        Pair pair = new Pair(newValue, lloc.get(LList.Empty));
        lloc.set(new Pair(property, pair));
        PropertyLocation ploc = new PropertyLocation();
        ploc.pair = pair;
        env.addLocation((Symbol) symbol, property, ploc);
    }

    public static void putProperty(Object symbol, Object property, Object newValue) {
        putProperty(symbol, property, newValue, Environment.getCurrent());
    }

    public static boolean removeProperty(Object symbol, Object property, Environment env) {
        Location ploc = env.lookup(Symbol.PLIST, symbol);
        if (ploc == null) {
            return false;
        }
        Pair plist = ploc.get(LList.Empty);
        if (!(plist instanceof Pair)) {
            return false;
        }
        Pair pair = plist;
        Pair prev = null;
        while (pair.getCar() != property) {
            Pair next = pair.getCdr();
            if (!(next instanceof Pair)) {
                return false;
            }
            prev = pair;
            pair = next;
        }
        Object tail = ((Pair) pair.getCdr()).getCdr();
        if (prev == null) {
            ploc.set(tail);
        } else {
            prev.setCdr(tail);
        }
        if (symbol instanceof Symbol) {
            env.remove((Symbol) symbol, property);
        }
        return true;
    }

    public static boolean removeProperty(Object symbol, Object property) {
        return removeProperty(symbol, property, Environment.getCurrent());
    }

    public static Object plistGet(Object plist, Object prop, Object dfault) {
        while (plist instanceof Pair) {
            Pair pair = (Pair) plist;
            if (pair.getCar() == prop) {
                return ((Pair) pair.getCdr()).getCar();
            }
        }
        return dfault;
    }

    public static Object plistPut(Object plist, Object prop, Object value) {
        Pair p = plist;
        while (p instanceof Pair) {
            Pair pair = p;
            Pair next = (Pair) pair.getCdr();
            if (pair.getCar() == prop) {
                next.setCar(value);
                return plist;
            }
            p = next.getCdr();
        }
        return new Pair(prop, new Pair(value, plist));
    }

    public static Object plistRemove(Object plist, Object prop) {
        Pair prev = null;
        Pair p = plist;
        while (p instanceof Pair) {
            Pair pair = p;
            Pair next = (Pair) pair.getCdr();
            p = next.getCdr();
            if (pair.getCar() != prop) {
                prev = next;
            } else if (prev == null) {
                return p;
            } else {
                prev.setCdr(p);
                return plist;
            }
        }
        return plist;
    }
}
