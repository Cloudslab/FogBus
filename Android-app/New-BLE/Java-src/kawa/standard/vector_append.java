package kawa.standard;

import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class vector_append extends ProcedureN {
    public static final vector_append vectorAppend = new vector_append("vector-append");

    public vector_append(String name) {
        super(name);
    }

    public Object applyN(Object[] args) {
        return apply$V(args);
    }

    public static FVector apply$V(Object[] args) {
        int length = 0;
        int i = args_length;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            Object arg = args[i];
            if (arg instanceof FVector) {
                length += ((FVector) arg).size();
            } else {
                int n = LList.listLength(arg, false);
                if (n < 0) {
                    throw new WrongType(vectorAppend, i, arg, "list or vector");
                }
                length += n;
            }
        }
        Object[] result = new Object[length];
        int position = 0;
        for (LList arg2 : args) {
            LList arg22;
            int position2;
            if (arg22 instanceof FVector) {
                FVector vec = (FVector) arg22;
                int vec_length = vec.size();
                int j = 0;
                position2 = position;
                while (j < vec_length) {
                    position = position2 + 1;
                    result[position2] = vec.get(j);
                    j++;
                    position2 = position;
                }
                position = position2;
            } else if (arg22 instanceof Pair) {
                while (arg22 != LList.Empty) {
                    Pair pair = (Pair) arg22;
                    position2 = position + 1;
                    result[position] = pair.getCar();
                    arg22 = pair.getCdr();
                    position = position2;
                }
            }
        }
        return new FVector(result);
    }
}
