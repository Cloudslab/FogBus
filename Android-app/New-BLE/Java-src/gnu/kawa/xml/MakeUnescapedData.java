package gnu.kawa.xml;

import gnu.lists.UnescapedData;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;

public class MakeUnescapedData extends Procedure1 {
    public static final MakeUnescapedData unescapedData = new MakeUnescapedData();

    public MakeUnescapedData() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.xml.CompileXmlFunctions:validateApplyMakeUnescapedData");
    }

    public Object apply1(Object arg) {
        return new UnescapedData(arg == null ? "" : arg.toString());
    }
}
