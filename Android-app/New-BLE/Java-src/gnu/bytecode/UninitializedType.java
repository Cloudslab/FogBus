package gnu.bytecode;

public class UninitializedType extends ObjectType {
    ClassType ctype;
    Label label;

    UninitializedType(ClassType ctype) {
        super(ctype.getName());
        setSignature(ctype.getSignature());
        this.ctype = ctype;
    }

    UninitializedType(ClassType ctype, Label label) {
        this(ctype);
        this.label = label;
    }

    static UninitializedType uninitializedThis(ClassType ctype) {
        return new UninitializedType(ctype);
    }

    public Type getImplementationType() {
        return this.ctype;
    }

    public String toString() {
        return "Uninitialized<" + this.ctype.getName() + '>';
    }
}
