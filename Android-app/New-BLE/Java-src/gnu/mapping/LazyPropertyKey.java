package gnu.mapping;

public class LazyPropertyKey<T> extends PropertyKey<T> {
    public LazyPropertyKey(String name) {
        super(name);
    }

    public T get(PropertySet container, T defaultValue) {
        T raw = container.getProperty(this, defaultValue);
        if (!(raw instanceof String)) {
            return raw;
        }
        String str = (String) raw;
        int cstart = str.charAt(0) == '*' ? 1 : 0;
        int colon = str.indexOf(58);
        if (colon <= cstart || colon >= str.length() - 1) {
            throw new RuntimeException("lazy property " + this + " must have the form \"ClassName:fieldName\" or \"ClassName:staticMethodName\"");
        }
        String cname = str.substring(cstart, colon);
        String mname = str.substring(colon + 1);
        try {
            T result;
            Class clas = Class.forName(cname, true, container.getClass().getClassLoader());
            if (cstart == 0) {
                result = clas.getField(mname).get(null);
            } else {
                result = clas.getDeclaredMethod(mname, new Class[]{Object.class}).invoke(null, new Object[]{container});
            }
            container.setProperty(this, result);
            return result;
        } catch (Throwable ex) {
            RuntimeException runtimeException = new RuntimeException("lazy property " + this + " has specifier \"" + str + "\" but there is no such " + (cstart == 0 ? "field" : "method"), ex);
        }
    }

    public void set(PropertySet container, String specifier) {
        container.setProperty(this, specifier);
    }
}
