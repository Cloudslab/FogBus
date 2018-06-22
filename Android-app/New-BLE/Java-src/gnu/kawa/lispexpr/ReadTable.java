package gnu.kawa.lispexpr;

import android.support.v4.media.TransportMediator;
import gnu.bytecode.Type;
import gnu.expr.Language;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.util.RangeTable;
import gnu.mapping.Environment;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.ThreadLocation;

public class ReadTable extends RangeTable {
    public static final int CONSTITUENT = 2;
    public static final int ILLEGAL = 0;
    public static final int MULTIPLE_ESCAPE = 4;
    public static final int NON_TERMINATING_MACRO = 6;
    public static final int SINGLE_ESCAPE = 3;
    public static final int TERMINATING_MACRO = 5;
    public static final int WHITESPACE = 1;
    static final ThreadLocation current = new ThreadLocation("read-table");
    public static int defaultBracketMode = -1;
    Environment ctorTable = null;
    protected boolean finalColonIsKeyword;
    protected boolean hexEscapeAfterBackslash = true;
    protected boolean initialColonIsKeyword;
    public char postfixLookupOperator = LispReader.TOKEN_ESCAPE_CHAR;

    public void setInitialColonIsKeyword(boolean whenInitial) {
        this.initialColonIsKeyword = whenInitial;
    }

    public void setFinalColonIsKeyword(boolean whenFinal) {
        this.finalColonIsKeyword = whenFinal;
    }

    public void initialize() {
        ReadTableEntry entry = ReadTableEntry.getWhitespaceInstance();
        set(32, entry);
        set(9, entry);
        set(10, entry);
        set(13, entry);
        set(12, entry);
        set(124, ReadTableEntry.getMultipleEscapeInstance());
        set(92, ReadTableEntry.getSingleEscapeInstance());
        set(48, 57, ReadTableEntry.getDigitInstance());
        entry = ReadTableEntry.getConstituentInstance();
        set(97, 122, entry);
        set(65, 90, entry);
        set(33, entry);
        set(36, entry);
        set(37, entry);
        set(38, entry);
        set(42, entry);
        set(43, entry);
        set(45, entry);
        set(46, entry);
        set(47, entry);
        set(61, entry);
        set(62, entry);
        set(63, entry);
        set(64, entry);
        set(94, entry);
        set(95, entry);
        set(123, ReadTableEntry.brace);
        set(TransportMediator.KEYCODE_MEDIA_PLAY, entry);
        set(TransportMediator.KEYCODE_MEDIA_PAUSE, entry);
        set(8, entry);
        set(58, new ReaderColon());
        set(34, new ReaderString());
        set(35, ReaderDispatch.create(this));
        set(59, ReaderIgnoreRestOfLine.getInstance());
        set(40, ReaderParens.getInstance('(', ')'));
        set(39, new ReaderQuote(makeSymbol(LispLanguage.quote_sym)));
        set(96, new ReaderQuote(makeSymbol(LispLanguage.quasiquote_sym)));
        set(44, new ReaderQuote(makeSymbol(LispLanguage.unquote_sym), '@', makeSymbol(LispLanguage.unquotesplicing_sym)));
        setBracketMode();
    }

    public static ReadTable createInitial() {
        ReadTable tab = new ReadTable();
        tab.initialize();
        return tab;
    }

    public void setBracketMode(int mode) {
        if (mode <= 0) {
            ReadTableEntry token = ReadTableEntry.getConstituentInstance();
            set(60, token);
            if (mode < 0) {
                set(91, token);
                set(93, token);
            }
        } else {
            set(60, new ReaderTypespec());
        }
        if (mode >= 0) {
            set(91, ReaderParens.getInstance('[', ']'));
            remove(93);
        }
    }

    public void setBracketMode() {
        setBracketMode(defaultBracketMode);
    }

    void initCtorTable() {
        if (this.ctorTable == null) {
            this.ctorTable = Environment.make();
        }
    }

    public synchronized void putReaderCtor(String key, Procedure proc) {
        initCtorTable();
        this.ctorTable.put(key, (Object) proc);
    }

    public synchronized void putReaderCtor(String key, Type type) {
        initCtorTable();
        this.ctorTable.put(key, (Object) type);
    }

    public synchronized void putReaderCtorFld(String key, String cname, String fname) {
        initCtorTable();
        StaticFieldLocation.define(this.ctorTable, this.ctorTable.getSymbol(key), null, cname, fname);
    }

    public synchronized Object getReaderCtor(String key) {
        initCtorTable();
        return this.ctorTable.get(key, null);
    }

    public static ReadTable getCurrent() {
        ReadTable table = (ReadTable) current.get(null);
        if (table == null) {
            Language language = Language.getDefaultLanguage();
            if (language instanceof LispLanguage) {
                table = ((LispLanguage) language).defaultReadTable;
            } else {
                table = createInitial();
            }
            current.set(table);
        }
        return table;
    }

    public static void setCurrent(ReadTable rt) {
        current.set(rt);
    }

    public ReadTableEntry lookup(int ch) {
        ReadTableEntry entry = (ReadTableEntry) lookup(ch, null);
        if (entry != null || ch < 0 || ch >= 65536) {
            return entry;
        }
        if (Character.isDigit((char) ch)) {
            entry = (ReadTableEntry) lookup(48, null);
        } else if (Character.isLowerCase((char) ch)) {
            entry = (ReadTableEntry) lookup(97, null);
        } else if (Character.isLetter((char) ch)) {
            entry = (ReadTableEntry) lookup(65, null);
        } else if (Character.isWhitespace((char) ch)) {
            entry = (ReadTableEntry) lookup(32, null);
        }
        if (entry == null && ch >= 128) {
            entry = ReadTableEntry.getConstituentInstance();
        }
        if (entry == null) {
            return ReadTableEntry.getIllegalInstance();
        }
        return entry;
    }

    protected Object makeSymbol(String name) {
        return Namespace.EmptyNamespace.getSymbol(name.intern());
    }
}
