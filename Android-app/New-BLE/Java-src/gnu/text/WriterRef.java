package gnu.text;

import java.io.Writer;
import java.lang.ref.WeakReference;

/* compiled from: WriterManager */
class WriterRef extends WeakReference {
    WriterRef next;
    WriterRef prev;

    public WriterRef(Writer wr) {
        super(wr);
    }
}
