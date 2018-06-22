package gnu.expr;

import gnu.bytecode.Field;
import gnu.bytecode.Type;

public class Literal {
    static final int CYCLIC = 4;
    static final int EMITTED = 8;
    static final int WRITING = 1;
    static final int WRITTEN = 2;
    public static final Literal nullLiteral = new Literal(null, Type.nullType);
    Type[] argTypes;
    Object[] argValues;
    public Field field;
    public int flags;
    int index;
    Literal next;
    public Type type;
    Object value;

    public final Object getValue() {
        return this.value;
    }

    void assign(LitTable litTable) {
        assign((String) null, litTable);
    }

    void assign(String name, LitTable litTable) {
        int flags = litTable.comp.immediate ? 9 : 24;
        if (name == null) {
            int i = litTable.literalsCount;
            litTable.literalsCount = i + 1;
            this.index = i;
            name = "Lit" + this.index;
        } else {
            flags |= 1;
        }
        assign(litTable.mainClass.addField(name, this.type, flags), litTable);
    }

    void assign(Field field, LitTable litTable) {
        this.next = litTable.literalsChain;
        litTable.literalsChain = this;
        this.field = field;
    }

    public Literal(Object value, LitTable litTable) {
        this(value, (String) null, litTable);
    }

    public Literal(Object value, String name, LitTable litTable) {
        this.value = value;
        litTable.literalTable.put(value, this);
        this.type = Type.make(value.getClass());
        assign(name, litTable);
    }

    public Literal(Object value, Field field, LitTable litTable) {
        this.value = value;
        litTable.literalTable.put(value, this);
        this.field = field;
        this.type = field.getType();
        this.flags = 10;
    }

    public Literal(Object value, Type type, LitTable litTable) {
        this.value = value;
        litTable.literalTable.put(value, this);
        this.type = type;
    }

    Literal(Object value, Type type) {
        this.value = value;
        this.type = type;
    }
}
