package gnu.lists;

public interface PositionConsumer {
    void consume(SeqPosition seqPosition);

    void writePosition(AbstractSequence abstractSequence, int i);
}
