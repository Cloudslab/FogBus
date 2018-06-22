package gnu.kawa.lispexpr;

public class ReaderMisc extends ReadTableEntry {
    int kind;

    public ReaderMisc(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return this.kind;
    }
}
