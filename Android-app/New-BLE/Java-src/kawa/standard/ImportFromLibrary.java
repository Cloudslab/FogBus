package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class ImportFromLibrary extends Syntax {
    private static final String BUILTIN = "";
    private static final String MISSING = null;
    static final String[][] SRFI97Map;
    public static final ImportFromLibrary instance = new ImportFromLibrary();
    public String[] classPrefixPath = new String[]{"", "kawa.lib."};

    static {
        r0 = new String[48][];
        r0[0] = new String[]{"1", "lists", "gnu.kawa.slib.srfi1"};
        r0[1] = new String[]{"2", "and-let*", "gnu.kawa.slib.srfi2"};
        r0[2] = new String[]{"5", "let", MISSING};
        r0[3] = new String[]{"6", "basic-string-ports", ""};
        r0[4] = new String[]{"8", "receive", ""};
        r0[5] = new String[]{"9", "records", ""};
        r0[6] = new String[]{"11", "let-values", ""};
        r0[7] = new String[]{"13", "strings", "gnu.kawa.slib.srfi13"};
        r0[8] = new String[]{"14", "char-sets", MISSING};
        r0[9] = new String[]{"16", "case-lambda", ""};
        r0[10] = new String[]{"17", "generalized-set!", ""};
        r0[11] = new String[]{"18", "multithreading", MISSING};
        r0[12] = new String[]{"19", "time", MISSING};
        r0[13] = new String[]{"21", "real-time-multithreading", MISSING};
        r0[14] = new String[]{"23", "error", ""};
        r0[15] = new String[]{"25", "multi-dimensional-arrays", ""};
        r0[16] = new String[]{"26", "cut", ""};
        r0[17] = new String[]{"27", "random-bits", MISSING};
        r0[18] = new String[]{"28", "basic-format-strings", ""};
        r0[19] = new String[]{"29", "localization", MISSING};
        r0[20] = new String[]{"31", "rec", MISSING};
        r0[21] = new String[]{"38", "with-shared-structure", MISSING};
        r0[22] = new String[]{"39", "parameters", ""};
        r0[23] = new String[]{"41", "streams", MISSING};
        r0[24] = new String[]{"42", "eager-comprehensions", MISSING};
        r0[25] = new String[]{"43", "vectors", MISSING};
        r0[26] = new String[]{"44", "collections", MISSING};
        r0[27] = new String[]{"45", "lazy", MISSING};
        r0[28] = new String[]{"46", "syntax-rules", MISSING};
        r0[29] = new String[]{"47", "arrays", MISSING};
        r0[30] = new String[]{"48", "intermediate-format-strings", MISSING};
        r0[31] = new String[]{"51", "rest-values", MISSING};
        r0[32] = new String[]{"54", "cat", MISSING};
        r0[33] = new String[]{"57", "records", MISSING};
        r0[34] = new String[]{"59", "vicinities", MISSING};
        r0[35] = new String[]{"60", "integer-bits", MISSING};
        r0[36] = new String[]{"61", "cond", MISSING};
        r0[37] = new String[]{"63", "arrays", MISSING};
        r0[38] = new String[]{"64", "testing", "gnu.kawa.slib.testing"};
        r0[39] = new String[]{"66", "octet-vectors", MISSING};
        r0[40] = new String[]{"67", "compare-procedures", MISSING};
        r0[41] = new String[]{"69", "basic-hash-tables", "gnu.kawa.slib.srfi69"};
        r0[42] = new String[]{"71", "let", MISSING};
        r0[43] = new String[]{"74", "blobs", MISSING};
        r0[44] = new String[]{"78", "lightweight-testing", MISSING};
        r0[45] = new String[]{"86", "mu-and-nu", MISSING};
        r0[46] = new String[]{"87", "case", MISSING};
        r0[47] = new String[]{"95", "sorting-and-merging", "kawa.lib.srfi95"};
        SRFI97Map = r0;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Procedure mapper = null;
        Pair args = st.getCdr();
        if (!(args instanceof Pair)) {
            return false;
        }
        Pair pair = args;
        Pair libref = pair.getCar();
        if (LList.listLength(libref, false) <= 0) {
            tr.error('e', "expected <library reference> which must be a list");
            return false;
        }
        int i;
        Object rest = pair.getCdr();
        if ((rest instanceof Pair) && (((Pair) rest).getCar() instanceof Procedure)) {
            mapper = (Procedure) ((Pair) rest).getCar();
        }
        Object versionSpec = null;
        String sourcePath = null;
        StringBuffer sbuf = new StringBuffer();
        while (libref instanceof Pair) {
            pair = libref;
            String car = pair.getCar();
            Pair cdr = pair.getCdr();
            if (car instanceof Pair) {
                if (versionSpec != null) {
                    tr.error('e', "duplicate version reference - was " + versionSpec);
                }
                versionSpec = car;
                System.err.println("import version " + car);
            } else if (car instanceof String) {
                if (cdr instanceof Pair) {
                    tr.error('e', "source specifier must be last elemnt in library reference");
                }
                sourcePath = car;
            } else {
                if (sbuf.length() > 0) {
                    sbuf.append('.');
                }
                sbuf.append(Compilation.mangleNameIfNeeded(car.toString()));
            }
            libref = cdr;
        }
        ModuleInfo minfo = null;
        if (sourcePath != null) {
            minfo = require.lookupModuleFromSourcePath(sourcePath, defs);
            if (minfo == null) {
                tr.error('e', "malformed URL: " + sourcePath);
                return false;
            }
        }
        String lname = sbuf.toString();
        if (lname.startsWith("srfi.")) {
            String srfiName;
            String demangled = Compilation.demangleName(lname.substring(5));
            int dot = demangled.indexOf(46);
            if (dot < 0) {
                srfiName = null;
                dot = demangled.length();
            } else {
                srfiName = demangled.substring(dot + 1);
            }
            String srfiNumber = null;
            if (dot >= 2 || demangled.charAt(0) == ':') {
                for (i = 1; i != dot; i++) {
                    if (Character.digit(demangled.charAt(i), 10) < 0) {
                        break;
                    }
                }
                srfiNumber = demangled.substring(1, dot);
            }
            if (srfiNumber == null) {
                tr.error('e', "SRFI library reference must have the form: (srfi :NNN [name])");
                return false;
            }
            int srfiIndex = SRFI97Map.length;
            do {
                srfiIndex--;
                if (srfiIndex < 0) {
                    tr.error('e', "unknown SRFI number '" + srfiNumber + "' in SRFI library reference");
                    return false;
                }
            } while (!SRFI97Map[srfiIndex][0].equals(srfiNumber));
            String srfiNameExpected = SRFI97Map[srfiIndex][1];
            String srfiClass = SRFI97Map[srfiIndex][2];
            if (!(srfiName == null || srfiName.equals(srfiNameExpected))) {
                tr.error('w', "the name of SRFI " + srfiNumber + " should be '" + srfiNameExpected + '\'');
            }
            if (srfiClass == "") {
                return true;
            }
            if (srfiClass == MISSING) {
                tr.error('e', "sorry - Kawa does not support SRFI " + srfiNumber + " (" + srfiNameExpected + ')');
                return false;
            }
            lname = srfiClass;
        }
        for (String str : this.classPrefixPath) {
            try {
                minfo = ModuleManager.getInstance().findWithClassName(str + lname);
            } catch (Exception e) {
            }
        }
        if (minfo == null) {
            tr.error('e', "unknown class " + lname);
            return false;
        }
        require.importDefinitions(null, minfo, mapper, forms, defs, tr);
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
