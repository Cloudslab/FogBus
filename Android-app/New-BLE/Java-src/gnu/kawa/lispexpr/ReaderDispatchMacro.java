package gnu.kawa.lispexpr;

import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderDispatchMacro extends ReaderMisc {
    Procedure procedure;

    public ReaderDispatchMacro(Procedure procedure) {
        super(5);
        this.procedure = procedure;
    }

    public Procedure getProcedure() {
        return this.procedure;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        try {
            return this.procedure.apply3(in.getPort(), Char.make(ch), IntNum.make(count));
        } catch (IOException ex) {
            throw ex;
        } catch (SyntaxException ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            in.fatal("reader macro '" + this.procedure + "' threw: " + ex3);
            return null;
        }
    }
}
