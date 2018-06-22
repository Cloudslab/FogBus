package gnu.kawa.functions;

import gnu.lists.Array;
import gnu.lists.FVector;
import gnu.lists.GeneralArray;
import gnu.lists.SimpleVector;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class Arrays {
    static final int[] shapeStrides = new int[]{2, 1};
    static final int[] zeros2 = new int[2];

    public static Array shape(Object[] vals) {
        if ((vals.length & 1) != 0) {
            throw new RuntimeException("shape: not an even number of arguments");
        }
        return new FVector(vals).transpose(zeros2, new int[]{vals.length >> 1, 2}, 0, shapeStrides);
    }

    public static Array make(Array shape, Object value) {
        int rank = shape.getSize(0);
        int[] dimensions = new int[rank];
        int[] lowBounds = null;
        int total = 1;
        int i = rank;
        while (true) {
            i--;
            if (i < 0) {
                return GeneralArray.makeSimple(lowBounds, dimensions, new FVector(total, value));
            }
            int lo = ((Number) shape.getRowMajor(i * 2)).intValue();
            int size = ((Number) shape.getRowMajor((i * 2) + 1)).intValue() - lo;
            dimensions[i] = size;
            if (lo != 0) {
                if (lowBounds == null) {
                    lowBounds = new int[rank];
                }
                lowBounds[i] = lo;
            }
            total *= size;
        }
    }

    public static Array makeSimple(Array shape, SimpleVector base) {
        int rank = shape.getSize(0);
        int[] dimensions = new int[rank];
        int[] lowBounds = null;
        int i = rank;
        while (true) {
            i--;
            if (i < 0) {
                return GeneralArray.makeSimple(lowBounds, dimensions, base);
            }
            int lo = ((Number) shape.getRowMajor(i * 2)).intValue();
            dimensions[i] = ((Number) shape.getRowMajor((i * 2) + 1)).intValue() - lo;
            if (lo != 0) {
                if (lowBounds == null) {
                    lowBounds = new int[rank];
                }
                lowBounds[i] = lo;
            }
        }
    }

    public static int effectiveIndex(Array array, Procedure proc, Object[] args, int[] work) throws Throwable {
        Values mapval = proc.applyN(args);
        if (mapval instanceof Values) {
            Values mapvals = mapval;
            int i = 0;
            int j = 0;
            while (true) {
                i = mapvals.nextPos(i);
                if (i == 0) {
                    break;
                }
                work[j] = ((Number) mapvals.getPosPrevious(i)).intValue();
                j++;
            }
        } else {
            work[0] = ((Number) mapval).intValue();
        }
        return array.getEffectiveIndex(work);
    }

    public static Array shareArray(Array array, Array shape, Procedure proc) throws Throwable {
        int offset0;
        int rank = shape.getSize(0);
        Object[] args = new Object[rank];
        int[] dimensions = new int[rank];
        int[] lowBounds = new int[rank];
        boolean empty = false;
        int i = rank;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            Object low = shape.getRowMajor(i * 2);
            args[i] = low;
            int lo = ((Number) low).intValue();
            lowBounds[i] = lo;
            int size = ((Number) shape.getRowMajor((i * 2) + 1)).intValue() - lo;
            dimensions[i] = size;
            if (size <= 0) {
                empty = true;
            }
        }
        int arank = array.rank();
        int[] offsets = new int[rank];
        if (!empty) {
            int[] work = new int[arank];
            offset0 = effectiveIndex(array, proc, args, work);
            i = rank;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                size = dimensions[i];
                lo = lowBounds[i];
                if (size <= 1) {
                    offsets[i] = 0;
                } else {
                    low = args[i];
                    args[i] = IntNum.make(lo + 1);
                    offsets[i] = effectiveIndex(array, proc, args, work) - offset0;
                    args[i] = low;
                }
            }
        } else {
            offset0 = 0;
        }
        return array.transpose(lowBounds, dimensions, offset0, offsets);
    }
}
