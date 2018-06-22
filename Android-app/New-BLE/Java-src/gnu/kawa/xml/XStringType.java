package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.util.regex.Pattern;

public class XStringType extends XDataType {
    public static final XStringType ENTITYType = new XStringType("ENTITY", NCNameType, 47, null);
    public static final XStringType IDREFType = new XStringType("IDREF", NCNameType, 46, null);
    public static final XStringType IDType = new XStringType("ID", NCNameType, 45, null);
    public static final XStringType NCNameType = new XStringType("NCName", NameType, 44, null);
    public static final XStringType NMTOKENType = new XStringType("NMTOKEN", tokenType, 42, "\\c+");
    public static final XStringType NameType = new XStringType("Name", tokenType, 43, null);
    static ClassType XStringType = ClassType.make("gnu.kawa.xml.XString");
    public static final XStringType languageType = new XStringType("language", tokenType, 41, "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*");
    public static final XStringType normalizedStringType = new XStringType("normalizedString", stringType, 39, null);
    public static final XStringType tokenType = new XStringType("token", normalizedStringType, 40, null);
    Pattern pattern;

    public XStringType(String name, XDataType base, int typeCode, String pattern) {
        super(name, XStringType, typeCode);
        this.baseType = base;
        if (pattern != null) {
            this.pattern = Pattern.compile(pattern);
        }
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof XString)) {
            return false;
        }
        for (XDataType objType = ((XString) obj).getStringType(); objType != null; objType = objType.baseType) {
            if (objType == this) {
                return true;
            }
        }
        return false;
    }

    public String matches(String value) {
        boolean status;
        switch (this.typeCode) {
            case 39:
            case 40:
                boolean collapse;
                if (this.typeCode != 39) {
                    collapse = true;
                } else {
                    collapse = false;
                }
                if (value == TextUtils.replaceWhitespace(value, collapse)) {
                    status = true;
                } else {
                    status = false;
                }
                break;
            case 42:
                status = XName.isNmToken(value);
                break;
            case 43:
                status = XName.isName(value);
                break;
            case 44:
            case 45:
            case 46:
            case 47:
                status = XName.isNCName(value);
                break;
            default:
                if (this.pattern != null && !this.pattern.matcher(value).matches()) {
                    status = false;
                    break;
                }
                status = true;
                break;
                break;
        }
        if (status) {
            return null;
        }
        return "not a valid XML " + getName();
    }

    public Object valueOf(String value) {
        value = TextUtils.replaceWhitespace(value, this != normalizedStringType);
        if (matches(value) == null) {
            return new XString(value, this);
        }
        throw new ClassCastException("cannot cast " + value + " to " + this.name);
    }

    public Object cast(Object value) {
        if (value instanceof XString) {
            XString xvalue = (XString) value;
            if (xvalue.getStringType() == this) {
                return xvalue;
            }
        }
        return valueOf((String) stringType.cast(value));
    }

    public static XString makeNCName(String value) {
        return (XString) NCNameType.valueOf(value);
    }
}
