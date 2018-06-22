package gnu.text;

import java.io.Writer;

public class WriterManager implements Runnable {
    public static final WriterManager instance = new WriterManager();
    WriterRef first;

    public synchronized WriterRef register(Writer port) {
        WriterRef ref;
        ref = new WriterRef(port);
        WriterRef first = this.first;
        if (first != null) {
            ref.next = first.next;
            first.prev = ref;
        }
        this.first = ref;
        return ref;
    }

    public synchronized void unregister(Object key) {
        if (key != null) {
            WriterRef ref = (WriterRef) key;
            WriterRef next = ref.next;
            WriterRef prev = ref.prev;
            if (next != null) {
                next.prev = prev;
            }
            if (prev != null) {
                prev.next = next;
            }
            if (ref == this.first) {
                this.first = next;
            }
        }
    }

    public synchronized void run() {
        for (WriterRef ref = this.first; ref != null; ref = ref.next) {
            Object port = ref.get();
            if (port != null) {
                try {
                    ((Writer) port).close();
                } catch (Exception e) {
                }
            }
        }
        this.first = null;
    }

    public boolean registerShutdownHook() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.getClass().getDeclaredMethod("addShutdownHook", new Class[]{Thread.class}).invoke(runtime, new Object[]{new Thread(this)});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
