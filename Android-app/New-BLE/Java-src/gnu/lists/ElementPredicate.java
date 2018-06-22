package gnu.lists;

public interface ElementPredicate extends NodePredicate {
    boolean isInstance(AbstractSequence abstractSequence, int i, Object obj);
}
