package gnu.kawa.lispexpr;

import gnu.kawa.util.RangeTable;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderDispatch extends ReadTableEntry {
    int kind;
    RangeTable table;

    public int getKind() {
        return this.kind;
    }

    public void set(int key, Object value) {
        this.table.set(key, key, value);
    }

    public ReadTableEntry lookup(int key) {
        return (ReadTableEntry) this.table.lookup(key, null);
    }

    public ReaderDispatch() {
        this.table = new RangeTable();
        this.kind = 5;
    }

    public ReaderDispatch(boolean nonTerminating) {
        this.table = new RangeTable();
        this.kind = nonTerminating ? 6 : 5;
    }

    public static ReaderDispatch create(ReadTable rtable) {
        ReaderDispatch tab = new ReaderDispatch();
        ReaderDispatchMisc entry = ReaderDispatchMisc.getInstance();
        tab.set(58, entry);
        tab.set(66, entry);
        tab.set(68, entry);
        tab.set(69, entry);
        tab.set(70, entry);
        tab.set(73, entry);
        tab.set(79, entry);
        tab.set(82, entry);
        tab.set(83, entry);
        tab.set(84, entry);
        tab.set(85, entry);
        tab.set(88, entry);
        tab.set(124, entry);
        tab.set(59, entry);
        tab.set(33, entry);
        tab.set(92, entry);
        tab.set(61, entry);
        tab.set(35, entry);
        tab.set(47, entry);
        tab.set(39, new ReaderQuote(rtable.makeSymbol("function")));
        tab.set(40, new ReaderVector(')'));
        tab.set(60, new ReaderXmlElement());
        return tab;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        count = -1;
        while (true) {
            ch = in.read();
            if (ch < 0) {
                in.eofError("unexpected EOF after " + ((char) ch));
            }
            if (ch > 65536) {
                break;
            }
            int digit = Character.digit((char) ch, 10);
            if (digit < 0) {
                break;
            } else if (count < 0) {
                count = digit;
            } else {
                count = (count * 10) + digit;
            }
        }
        ch = Character.toUpperCase((char) ch);
        ReadTableEntry entry = (ReadTableEntry) this.table.lookup(ch, null);
        if (entry != null) {
            return entry.read(in, ch, count);
        }
        in.error('e', in.getName(), in.getLineNumber() + 1, in.getColumnNumber(), "invalid dispatch character '" + ((char) ch) + '\'');
        return Values.empty;
    }
}
