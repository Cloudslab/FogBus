package gnu.expr;

/* compiled from: BlockExp */
class BlockExitException extends RuntimeException {
    ExitExp exit;
    Object result;

    public BlockExitException(ExitExp exit, Object result) {
        this.exit = exit;
        this.result = result;
    }
}
