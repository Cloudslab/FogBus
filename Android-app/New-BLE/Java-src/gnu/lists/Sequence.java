package gnu.lists;

import java.util.Enumeration;
import java.util.List;

public interface Sequence extends List, Consumable {
    public static final int ATTRIBUTE_VALUE = 35;
    public static final int BOOLEAN_VALUE = 27;
    public static final int CDATA_VALUE = 31;
    public static final int CHAR_VALUE = 29;
    public static final int COMMENT_VALUE = 36;
    public static final int DOCUMENT_VALUE = 34;
    public static final int DOUBLE_VALUE = 26;
    public static final int ELEMENT_VALUE = 33;
    public static final int EOF_VALUE = 0;
    public static final int FLOAT_VALUE = 25;
    public static final int INT_S16_VALUE = 20;
    public static final int INT_S32_VALUE = 22;
    public static final int INT_S64_VALUE = 24;
    public static final int INT_S8_VALUE = 18;
    public static final int INT_U16_VALUE = 19;
    public static final int INT_U32_VALUE = 21;
    public static final int INT_U64_VALUE = 23;
    public static final int INT_U8_VALUE = 17;
    public static final int OBJECT_VALUE = 32;
    public static final int PRIM_VALUE = 16;
    public static final int PROCESSING_INSTRUCTION_VALUE = 37;
    public static final int TEXT_BYTE_VALUE = 28;
    public static final Object eofValue = EofClass.eofValue;

    Enumeration elements();

    void fill(Object obj);

    Object get(int i);

    boolean isEmpty();

    Object set(int i, Object obj);

    int size();
}
