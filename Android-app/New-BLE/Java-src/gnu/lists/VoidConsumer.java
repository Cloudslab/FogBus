package gnu.lists;

public class VoidConsumer extends FilterConsumer {
    public static VoidConsumer instance = new VoidConsumer();

    public static VoidConsumer getInstance() {
        return instance;
    }

    public VoidConsumer() {
        super(null);
        this.skipping = true;
    }

    public boolean ignoring() {
        return true;
    }
}
