package gnu.mapping;

public class PropertyKey<T> {
    String name;

    public PropertyKey(String name) {
        this.name = name;
    }

    public T get(PropertySet container, T defaultValue) {
        return container.getProperty(this, defaultValue);
    }

    public final T get(PropertySet container) {
        return get(container, null);
    }

    public void set(PropertySet container, T value) {
        container.setProperty(this, value);
    }
}
