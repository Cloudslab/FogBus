package gnu.mapping;

public interface HasNamedParts {
    Object get(String str);

    boolean isConstant(String str);
}
