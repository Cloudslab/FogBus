package gnu.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Options {
    public static final int BOOLEAN_OPTION = 1;
    public static final int STRING_OPTION = 2;
    public static final String UNKNOWN = "unknown option name";
    OptionInfo first;
    HashMap<String, OptionInfo> infoTable;
    OptionInfo last;
    Options previous;
    HashMap<String, Object> valueTable;

    public static final class OptionInfo {
        Object defaultValue;
        String documentation;
        String key;
        int kind;
        OptionInfo next;
    }

    public Options(Options previous) {
        this.previous = previous;
    }

    public OptionInfo add(String key, int kind, String documentation) {
        return add(key, kind, null, documentation);
    }

    public OptionInfo add(String key, int kind, Object defaultValue, String documentation) {
        if (this.infoTable == null) {
            this.infoTable = new HashMap();
        } else if (this.infoTable.get(key) != null) {
            throw new RuntimeException("duplicate option key: " + key);
        }
        OptionInfo info = new OptionInfo();
        info.key = key;
        info.kind = kind;
        info.defaultValue = defaultValue;
        info.documentation = documentation;
        if (this.first == null) {
            this.first = info;
        } else {
            this.last.next = info;
        }
        this.last = info;
        this.infoTable.put(key, info);
        return info;
    }

    static Object valueOf(OptionInfo info, String argument) {
        if ((info.kind & 1) == 0) {
            return argument;
        }
        if (argument == null || argument.equals("1") || argument.equals("on") || argument.equals("yes") || argument.equals("true")) {
            return Boolean.TRUE;
        }
        if (argument.equals("0") || argument.equals("off") || argument.equals("no") || argument.equals("false")) {
            return Boolean.FALSE;
        }
        return null;
    }

    private void error(String message, SourceMessages messages) {
        if (messages == null) {
            throw new RuntimeException(message);
        }
        messages.error('e', message);
    }

    public void set(String key, Object value) {
        set(key, value, null);
    }

    public void set(String key, Object value, SourceMessages messages) {
        OptionInfo info = getInfo(key);
        if (info == null) {
            error("invalid option key: " + key, messages);
            return;
        }
        if ((info.kind & 1) != 0) {
            if (value instanceof String) {
                value = valueOf(info, (String) value);
            }
            if (!(value instanceof Boolean)) {
                error("value for option " + key + " must be boolean or yes/no/true/false/on/off/1/0", messages);
                return;
            }
        } else if (value == null) {
            value = "";
        }
        if (this.valueTable == null) {
            this.valueTable = new HashMap();
        }
        this.valueTable.put(key, value);
    }

    public void reset(String key, Object oldValue) {
        if (this.valueTable == null) {
            this.valueTable = new HashMap();
        }
        if (oldValue == null) {
            this.valueTable.remove(key);
        } else {
            this.valueTable.put(key, oldValue);
        }
    }

    public String set(String key, String argument) {
        OptionInfo info = getInfo(key);
        if (info == null) {
            return UNKNOWN;
        }
        Object value = valueOf(info, argument);
        if (value == null && (info.kind & 1) != 0) {
            return "value of option " + key + " must be yes/no/true/false/on/off/1/0";
        }
        if (this.valueTable == null) {
            this.valueTable = new HashMap();
        }
        this.valueTable.put(key, value);
        return null;
    }

    public OptionInfo getInfo(String key) {
        OptionInfo info = this.infoTable == null ? null : (OptionInfo) this.infoTable.get(key);
        if (info == null && this.previous != null) {
            info = this.previous.getInfo(key);
        }
        return info;
    }

    public Object get(String key, Object defaultValue) {
        OptionInfo info = getInfo(key);
        if (info != null) {
            return get(info, defaultValue);
        }
        throw new RuntimeException("invalid option key: " + key);
    }

    public Object get(OptionInfo key, Object defaultValue) {
        for (Options options = this; options != null; options = options.previous) {
            OptionInfo info = key;
            while (true) {
                Object val = options.valueTable == null ? null : options.valueTable.get(info.key);
                if (val == null) {
                    if (!(info.defaultValue instanceof OptionInfo)) {
                        break;
                    }
                    info = info.defaultValue;
                } else {
                    return val;
                }
            }
            if (info.defaultValue != null) {
                defaultValue = info.defaultValue;
            }
        }
        return defaultValue;
    }

    public Object get(OptionInfo key) {
        return get(key, null);
    }

    public Object getLocal(String key) {
        return this.valueTable == null ? null : this.valueTable.get(key);
    }

    public boolean getBoolean(String key) {
        return ((Boolean) get(key, Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return ((Boolean) get(key, defaultValue ? Boolean.TRUE : Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(OptionInfo key, boolean defaultValue) {
        return ((Boolean) get(key, defaultValue ? Boolean.TRUE : Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(OptionInfo key) {
        Object value = get(key, null);
        return value == null ? false : ((Boolean) value).booleanValue();
    }

    public void pushOptionValues(Vector options) {
        int len = options.size();
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            String key = (String) options.elementAt(i);
            i = i2 + 1;
            options.setElementAt(options.elementAt(i2), i2);
            i2 = i + 1;
            set(key, options.elementAt(i));
            i = i2;
        }
    }

    public void popOptionValues(Vector options) {
        int i = options.size();
        while (true) {
            i -= 3;
            if (i >= 0) {
                String key = (String) options.elementAt(i);
                Object oldValue = options.elementAt(i + 1);
                options.setElementAt(null, i + 1);
                reset(key, oldValue);
            } else {
                return;
            }
        }
    }

    public ArrayList<String> keys() {
        ArrayList<String> allKeys = new ArrayList();
        for (Options options = this; options != null; options = options.previous) {
            if (options.infoTable != null) {
                for (String k : options.infoTable.keySet()) {
                    if (!allKeys.contains(k)) {
                        allKeys.add(k);
                    }
                }
            }
        }
        return allKeys;
    }

    public String getDoc(String key) {
        OptionInfo info = getInfo(key);
        if (key == null) {
            return null;
        }
        return info.documentation;
    }
}
