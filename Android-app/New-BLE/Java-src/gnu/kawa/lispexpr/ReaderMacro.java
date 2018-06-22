package gnu.kawa.lispexpr;

import gnu.mapping.Procedure;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderMacro extends ReaderMisc {
    Procedure procedure;

    public ReaderMacro(Procedure procedure, boolean nonTerminating) {
        super(nonTerminating ? 6 : 5);
        this.procedure = procedure;
    }

    public ReaderMacro(Procedure procedure) {
        super(5);
        this.procedure = procedure;
    }

    public boolean isNonTerminating() {
        return this.kind == 6;
    }

    public Procedure getProcedure() {
        return this.procedure;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        try {
            return this.procedure.apply2(in.getPort(), Char.make(ch));
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
