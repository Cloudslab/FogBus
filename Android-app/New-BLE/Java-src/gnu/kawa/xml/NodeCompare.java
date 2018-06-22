package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.SeqPosition;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import gnu.mapping.WrongType;

public class NodeCompare extends Procedure2 {
    public static final NodeCompare $Eq = make("is", 8);
    public static final NodeCompare $Gr = make(">>", 16);
    public static final NodeCompare $Ls = make("<<", 4);
    public static final NodeCompare $Ne = make("isnot", 20);
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int TRUE_IF_EQU = 8;
    static final int TRUE_IF_GRT = 16;
    static final int TRUE_IF_LSS = 4;
    int flags;

    public static NodeCompare make(String name, int flags) {
        NodeCompare proc = new NodeCompare();
        proc.setName(name);
        proc.flags = flags;
        return proc;
    }

    public Object apply2(Object arg1, Object arg2) {
        if (arg1 == null || arg2 == null) {
            return null;
        }
        if (arg1 == Values.empty) {
            return arg1;
        }
        if (arg2 == Values.empty) {
            return arg2;
        }
        AbstractSequence seq1;
        int ipos1;
        AbstractSequence seq2;
        int ipos2;
        int comp;
        if (arg1 instanceof AbstractSequence) {
            seq1 = (AbstractSequence) arg1;
            ipos1 = seq1.startPos();
        } else {
            try {
                SeqPosition spos = (SeqPosition) arg1;
                seq1 = spos.sequence;
                ipos1 = spos.getPos();
            } catch (ClassCastException ex) {
                throw WrongType.make(ex, (Procedure) this, 1, arg1);
            }
        }
        if (arg2 instanceof AbstractSequence) {
            seq2 = (AbstractSequence) arg2;
            ipos2 = seq2.startPos();
        } else {
            try {
                spos = (SeqPosition) arg2;
                seq2 = spos.sequence;
                ipos2 = spos.getPos();
            } catch (ClassCastException ex2) {
                throw WrongType.make(ex2, (Procedure) this, 2, arg2);
            }
        }
        if (seq1 == seq2) {
            comp = seq1.compare(ipos1, ipos2);
        } else if (this == $Eq) {
            return Boolean.FALSE;
        } else {
            if (this == $Ne) {
                return Boolean.TRUE;
            }
            comp = seq1.stableCompare(seq2);
        }
        if (((1 << (comp + 3)) & this.flags) != 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
