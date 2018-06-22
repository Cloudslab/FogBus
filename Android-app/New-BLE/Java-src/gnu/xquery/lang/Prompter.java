package gnu.xquery.lang;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

/* compiled from: XQuery */
class Prompter extends Procedure1 {
    Prompter() {
    }

    public Object apply1(Object arg) {
        InPort port = (InPort) arg;
        int line = port.getLineNumber() + 1;
        char state = port.readState;
        if (state == '\n') {
            state = ' ';
        }
        if (state == '<') {
            return "<!--" + line + "-->";
        }
        if (state == ':') {
            return "-(:" + line + "c:) ";
        }
        return "(: " + line + state + ":) ";
    }
}
