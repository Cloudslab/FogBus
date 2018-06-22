package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class EofClass implements Externalizable {
    public static final EofClass eofValue = new EofClass();

    public final String toString() {
        return "#!eof";
    }

    public void writeExternal(ObjectOutput out) throws IOException {
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public Object readResolve() throws ObjectStreamException {
        return Sequence.eofValue;
    }
}
