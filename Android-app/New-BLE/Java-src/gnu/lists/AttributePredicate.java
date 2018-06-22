package gnu.lists;

public interface AttributePredicate extends NodePredicate {
    boolean isInstance(AbstractSequence abstractSequence, int i, Object obj);
}
