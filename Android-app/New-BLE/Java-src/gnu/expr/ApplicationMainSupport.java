package gnu.expr;

import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.mapping.Environment;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;

public class ApplicationMainSupport {
    public static String[] commandLineArgArray;
    public static FVector commandLineArguments;
    public static boolean processCommandLinePropertyAssignments;
    static String[][] propertyFields;

    public static void processSetProperties() {
        String[] args = commandLineArgArray;
        if (args == null) {
            processCommandLinePropertyAssignments = true;
            return;
        }
        int iarg = 0;
        while (iarg < args.length && processSetProperty(args[iarg])) {
            iarg++;
        }
        if (iarg != 0) {
            setArgs(args, iarg);
        }
    }

    public static void processArgs(String[] args) {
        int iarg = 0;
        if (processCommandLinePropertyAssignments) {
            while (iarg < args.length && processSetProperty(args[iarg])) {
                iarg++;
            }
        }
        setArgs(args, iarg);
    }

    public static void setArgs(String[] args, int arg_start) {
        int i;
        int nargs = args.length - arg_start;
        Object[] array = new Object[nargs];
        if (arg_start == 0) {
            commandLineArgArray = args;
        } else {
            String[] strings = new String[nargs];
            i = nargs;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                strings[i] = args[i + arg_start];
            }
            commandLineArgArray = strings;
        }
        i = nargs;
        while (true) {
            i--;
            if (i >= 0) {
                array[i] = new FString(args[i + arg_start]);
            } else {
                commandLineArguments = new FVector(array);
                Environment.getCurrent().put("command-line-arguments", commandLineArguments);
                return;
            }
        }
    }

    public static boolean processSetProperty(String arg) {
        int ci = arg.indexOf(61);
        if (ci <= 0) {
            return false;
        }
        String key = arg.substring(0, ci);
        String value = arg.substring(ci + 1);
        int i = 0;
        while (true) {
            String[] propertyField = propertyFields[i];
            if (propertyField == null) {
                break;
            } else if (key.equals(propertyField[0])) {
                String cname = propertyField[1];
                String fname = propertyField[2];
                try {
                    ((ThreadLocation) Class.forName(cname).getDeclaredField(fname).get(null)).setGlobal(value);
                    break;
                } catch (Throwable ex) {
                    System.err.println("error setting property " + key + " field " + cname + '.' + fname + ": " + ex);
                    System.exit(-1);
                }
            } else {
                i++;
            }
        }
        Symbol symbol = Symbol.parse(key);
        Language.getDefaultLanguage();
        Environment.getCurrent().define(symbol, null, value);
        return true;
    }

    static {
        r0 = new String[10][];
        r0[0] = new String[]{"out:doctype-system", "gnu.xml.XMLPrinter", "doctypeSystem"};
        r0[1] = new String[]{"out:doctype-public", "gnu.xml.XMLPrinter", "doctypePublic"};
        r0[2] = new String[]{"out:base", "gnu.kawa.functions.DisplayFormat", "outBase"};
        r0[3] = new String[]{"out:radix", "gnu.kawa.functions.DisplayFormat", "outRadix"};
        r0[4] = new String[]{"out:line-length", "gnu.text.PrettyWriter", "lineLengthLoc"};
        r0[5] = new String[]{"out:right-margin", "gnu.text.PrettyWriter", "lineLengthLoc"};
        r0[6] = new String[]{"out:miser-width", "gnu.text.PrettyWriter", "miserWidthLoc"};
        r0[7] = new String[]{"out:xml-indent", "gnu.xml.XMLPrinter", "indentLoc"};
        r0[8] = new String[]{"display:toolkit", "gnu.kawa.models.Display", "myDisplay"};
        r0[9] = null;
        propertyFields = r0;
    }
}
