package gnu.kawa.lispexpr;

import gnu.lists.PairWithPosition;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderQuote extends ReadTableEntry {
    Object magicSymbol;
    Object magicSymbol2;
    char next;

    public ReaderQuote(Object magicSymbol) {
        this.magicSymbol = magicSymbol;
    }

    public ReaderQuote(Object magicSymbol, char next, Object magicSymbol2) {
        this.next = next;
        this.magicSymbol = magicSymbol;
        this.magicSymbol2 = magicSymbol2;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        String file = reader.getName();
        int line1 = reader.getLineNumber() + 1;
        int column1 = reader.getColumnNumber() + 1;
        Object magic = this.magicSymbol;
        if (this.next != '\u0000') {
            char ch2 = reader.read();
            if (ch2 == this.next) {
                magic = this.magicSymbol2;
            } else if (ch2 >= '\u0000') {
                reader.unread(ch2);
            }
        }
        return PairWithPosition.make(magic, PairWithPosition.make(reader.readObject(), reader.makeNil(), file, reader.getLineNumber() + 1, reader.getColumnNumber() + 1), file, line1, column1);
    }
}
