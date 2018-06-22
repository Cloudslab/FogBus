package gnu.lists;

public class ExtPosition extends SeqPosition {
    int position = -1;

    public int getPos() {
        if (this.position < 0) {
            this.position = PositionManager.manager.register(this);
        }
        return this.position;
    }

    public void setPos(AbstractSequence seq, int ipos) {
        throw seq.unsupported("setPos");
    }

    public final boolean isAfter() {
        return (this.ipos & 1) != 0;
    }

    public void release() {
        if (this.position >= 0) {
            PositionManager.manager.release(this.position);
        }
        this.sequence = null;
    }
}
