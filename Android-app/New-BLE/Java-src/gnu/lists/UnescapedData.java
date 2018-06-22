package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UnescapedData implements CharSequence, Externalizable {
    String data;

    public UnescapedData(String data) {
        this.data = data;
    }

    public final String getData() {
        return this.data;
    }

    public final String toString() {
        return this.data;
    }

    public final boolean equals(Object other) {
        return (other instanceof UnescapedData) && this.data.equals(other.toString());
    }

    public final int hashCode() {
        return this.data == null ? 0 : this.data.hashCode();
    }

    public int length() {
        return this.data.length();
    }

    public char charAt(int index) {
        return this.data.charAt(index);
    }

    public CharSequence subSequence(int start, int end) {
        return new UnescapedData(this.data.substring(start, end));
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.data);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.data = (String) in.readObject();
    }
}
