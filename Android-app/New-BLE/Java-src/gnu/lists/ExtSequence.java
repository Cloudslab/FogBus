package gnu.lists;

public abstract class ExtSequence extends AbstractSequence {
    public int copyPos(int ipos) {
        return ipos <= 0 ? ipos : PositionManager.manager.register(PositionManager.getPositionObject(ipos).copy());
    }

    protected void releasePos(int ipos) {
        if (ipos > 0) {
            PositionManager.manager.release(ipos);
        }
    }

    protected boolean isAfterPos(int ipos) {
        if (ipos <= 0) {
            if (ipos < 0) {
                return true;
            }
            return false;
        } else if ((PositionManager.getPositionObject(ipos).ipos & 1) == 0) {
            return false;
        } else {
            return true;
        }
    }

    protected int nextIndex(int ipos) {
        if (ipos == -1) {
            return size();
        }
        return ipos == 0 ? 0 : PositionManager.getPositionObject(ipos).nextIndex();
    }
}
