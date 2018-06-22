package gnu.lists;

public class GeneralArray extends AbstractSequence implements Array {
    static final int[] zeros = new int[8];
    SimpleVector base;
    int[] dimensions;
    int[] lowBounds;
    int offset;
    boolean simple = true;
    int[] strides;

    public static Array makeSimple(int[] lowBounds, int[] dimensions, SimpleVector base) {
        int d = dimensions.length;
        if (lowBounds == null) {
            lowBounds = zeros;
            if (d > lowBounds.length) {
                lowBounds = new int[d];
            }
        }
        if (d == 1 && lowBounds[0] == 0) {
            return base;
        }
        AbstractSequence array = new GeneralArray();
        int[] strides = new int[d];
        int n = 1;
        int i = d;
        while (true) {
            i--;
            if (i >= 0) {
                strides[i] = n;
                n *= dimensions[i];
            } else {
                array.strides = strides;
                array.dimensions = dimensions;
                array.lowBounds = lowBounds;
                array.base = base;
                return array;
            }
        }
    }

    public GeneralArray(int[] dimensions) {
        int total = 1;
        int rank = dimensions.length;
        if (rank <= zeros.length) {
            this.lowBounds = zeros;
        } else {
            this.lowBounds = new int[rank];
        }
        int[] strides = new int[rank];
        int i = rank;
        while (true) {
            i--;
            if (i >= 0) {
                strides[i] = total;
                total *= dimensions[i];
            } else {
                this.base = new FVector(total);
                this.dimensions = dimensions;
                this.offset = 0;
                return;
            }
        }
    }

    public int rank() {
        return this.dimensions.length;
    }

    public int getEffectiveIndex(int[] indexes) {
        int result = this.offset;
        int i = this.dimensions.length;
        while (true) {
            i--;
            if (i >= 0) {
                int index = indexes[i];
                int low = this.lowBounds[i];
                if (index < low) {
                    break;
                }
                index -= low;
                if (index >= this.dimensions[i]) {
                    break;
                }
                result += this.strides[i] * index;
            } else {
                return result;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public Object get(int index) {
        return getRowMajor(index);
    }

    public int createPos(int index, boolean isAfter) {
        int total = this.offset;
        int i = this.dimensions.length;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            int dim = this.dimensions[i];
            int cur = index % dim;
            index /= dim;
            total += this.strides[i] * cur;
        }
        return (isAfter ? 1 : 0) | (total << 1);
    }

    public Object getRowMajor(int index) {
        if (this.simple) {
            return this.base.get(index);
        }
        int total = this.offset;
        int i = this.dimensions.length;
        while (true) {
            i--;
            if (i < 0) {
                return this.base.get(total);
            }
            int dim = this.dimensions[i];
            int cur = index % dim;
            index /= dim;
            total += this.strides[i] * cur;
        }
    }

    public Object get(int[] indexes) {
        return this.base.get(getEffectiveIndex(indexes));
    }

    public Object set(int[] indexes, Object value) {
        return this.base.set(getEffectiveIndex(indexes), value);
    }

    public int size() {
        int total = 1;
        int i = this.dimensions.length;
        while (true) {
            i--;
            if (i < 0) {
                return total;
            }
            total *= this.dimensions[i];
        }
    }

    public int getLowBound(int dim) {
        return this.lowBounds[dim];
    }

    public int getSize(int dim) {
        return this.dimensions[dim];
    }

    public Array transpose(int[] lowBounds, int[] dimensions, int offset0, int[] factors) {
        GeneralArray array = (dimensions.length == 1 && lowBounds[0] == 0) ? new GeneralArray1() : new GeneralArray();
        array.offset = offset0;
        array.strides = factors;
        array.dimensions = dimensions;
        array.lowBounds = lowBounds;
        array.base = this.base;
        array.simple = false;
        return array;
    }

    public static void toString(Array array, StringBuffer sbuf) {
        sbuf.append("#<array");
        int r = array.rank();
        for (int i = 0; i < r; i++) {
            sbuf.append(' ');
            int lo = array.getLowBound(i);
            int sz = array.getSize(i);
            if (lo != 0) {
                sbuf.append(lo);
                sbuf.append(':');
            }
            sbuf.append(lo + sz);
        }
        sbuf.append('>');
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        toString(this, sbuf);
        return sbuf.toString();
    }
}
