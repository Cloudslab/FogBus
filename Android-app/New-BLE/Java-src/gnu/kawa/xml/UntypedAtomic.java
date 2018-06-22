package gnu.kawa.xml;

public class UntypedAtomic {
    String text;

    public String toString() {
        return this.text;
    }

    public UntypedAtomic(String text) {
        this.text = text;
    }

    public int hashCode() {
        return this.text.hashCode();
    }

    public boolean equals(Object arg) {
        return (arg instanceof UntypedAtomic) && this.text.equals(((UntypedAtomic) arg).text);
    }
}
