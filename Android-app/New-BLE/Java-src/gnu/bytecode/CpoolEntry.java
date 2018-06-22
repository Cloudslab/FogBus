package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CpoolEntry {
    int hash;
    public int index;
    CpoolEntry next;

    public abstract int getTag();

    public abstract void print(ClassTypeWriter classTypeWriter, int i);

    abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public int getIndex() {
        return this.index;
    }

    public int hashCode() {
        return this.hash;
    }

    void add_hashed(ConstantPool cpool) {
        CpoolEntry[] hashTab = cpool.hashTab;
        int index = (this.hash & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) % hashTab.length;
        this.next = hashTab[index];
        hashTab[index] = this;
    }

    protected CpoolEntry() {
    }

    public CpoolEntry(ConstantPool cpool, int h) {
        this.hash = h;
        if (cpool.locked) {
            throw new Error("adding new entry to locked contant pool");
        }
        int i = cpool.count + 1;
        cpool.count = i;
        this.index = i;
        if (cpool.pool == null) {
            cpool.pool = new CpoolEntry[60];
        } else if (this.index >= cpool.pool.length) {
            int old_size = cpool.pool.length;
            CpoolEntry[] new_pool = new CpoolEntry[(cpool.pool.length * 2)];
            for (int i2 = 0; i2 < old_size; i2++) {
                new_pool[i2] = cpool.pool[i2];
            }
            cpool.pool = new_pool;
        }
        if (cpool.hashTab == null || ((double) this.index) >= 0.6d * ((double) cpool.hashTab.length)) {
            cpool.rehash();
        }
        cpool.pool[this.index] = this;
        add_hashed(cpool);
    }
}
