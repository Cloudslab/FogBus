package gnu.xml;

import gnu.kawa.xml.KNode;
import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import java.math.BigDecimal;

public class TextUtils {
    public static String asString(Object node) {
        if (node == Values.empty || node == null) {
            return "";
        }
        if (node instanceof Values) {
            throw new ClassCastException();
        }
        StringBuffer sbuf = new StringBuffer(100);
        stringValue(node, sbuf);
        return sbuf.toString();
    }

    public static String stringValue(Object node) {
        StringBuffer sbuf = new StringBuffer(100);
        if (node instanceof Values) {
            TreeList tlist = (TreeList) node;
            int index = 0;
            while (true) {
                int kind = tlist.getNextKind(index);
                if (kind == 0) {
                    break;
                }
                if (kind == 32) {
                    stringValue(tlist.getPosNext(index), sbuf);
                } else {
                    tlist.stringValue(tlist.posToDataIndex(index), sbuf);
                }
                index = tlist.nextPos(index);
            }
        } else {
            stringValue(node, sbuf);
        }
        return sbuf.toString();
    }

    public static void stringValue(Object node, StringBuffer sbuf) {
        if (node instanceof KNode) {
            KNode pos = (KNode) node;
            NodeTree tlist = pos.sequence;
            tlist.stringValue(tlist.posToDataIndex(pos.ipos), sbuf);
            return;
        }
        Values formatDecimal;
        if (node instanceof BigDecimal) {
            formatDecimal = XMLPrinter.formatDecimal((BigDecimal) node);
        } else if ((node instanceof Double) || (node instanceof DFloNum)) {
            formatDecimal = XMLPrinter.formatDouble(((Number) node).doubleValue());
        } else if (node instanceof Float) {
            formatDecimal = XMLPrinter.formatFloat(((Number) node).floatValue());
        }
        if (formatDecimal != null && formatDecimal != Values.empty) {
            sbuf.append(formatDecimal);
        }
    }

    public static void textValue(Object arg, Consumer out) {
        if (arg == null) {
            return;
        }
        if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
            String str;
            if (arg instanceof String) {
                str = (String) arg;
            } else {
                StringBuffer sbuf = new StringBuffer();
                if (arg instanceof Values) {
                    Object[] vals = ((Values) arg).getValues();
                    for (int i = 0; i < vals.length; i++) {
                        if (i > 0) {
                            sbuf.append(' ');
                        }
                        stringValue(vals[i], sbuf);
                    }
                } else {
                    stringValue(arg, sbuf);
                }
                str = sbuf.toString();
            }
            out.write(str);
        }
    }

    public static String replaceWhitespace(String str, boolean collapse) {
        int prevSpace;
        StringBuilder sbuf = null;
        int len = str.length();
        if (collapse) {
            prevSpace = 1;
        } else {
            prevSpace = 0;
        }
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            char ch = str.charAt(i);
            int isSpace = ch == ' ' ? 1 : (ch == '\t' || ch == '\r' || ch == '\n') ? 2 : 0;
            if (sbuf == null && (isSpace == 2 || ((isSpace == 1 && prevSpace > 0 && collapse) || (isSpace == 1 && i2 == len && collapse)))) {
                sbuf = new StringBuilder();
                int k = prevSpace > 0 ? i2 - 2 : i2 - 1;
                for (int j = 0; j < k; j++) {
                    sbuf.append(str.charAt(j));
                }
                ch = ' ';
            }
            if (collapse) {
                if (prevSpace > 0 && isSpace == 0) {
                    if (sbuf != null && sbuf.length() > 0) {
                        sbuf.append(' ');
                    }
                    prevSpace = 0;
                } else if (isSpace == 2 || (isSpace == 1 && prevSpace > 0)) {
                    prevSpace = 2;
                } else if (isSpace > 0) {
                    prevSpace = 1;
                } else {
                    prevSpace = 0;
                }
                if (prevSpace > 0) {
                    i = i2;
                }
            }
            if (sbuf != null) {
                sbuf.append(ch);
            }
            i = i2;
        }
        if (sbuf != null) {
            return sbuf.toString();
        }
        return str;
    }
}
