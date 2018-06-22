package gnu.mapping;

import gnu.text.SourceLocator;

public class UnboundLocationException extends RuntimeException {
    int column;
    String filename;
    int line;
    Location location;
    public Object symbol;

    public UnboundLocationException(Object symbol) {
        this.symbol = symbol;
    }

    public UnboundLocationException(Object symbol, String filename, int line, int column) {
        this.symbol = symbol;
        this.filename = filename;
        this.line = line;
        this.column = column;
    }

    public UnboundLocationException(Object symbol, SourceLocator location) {
        this.symbol = symbol;
        if (location != null) {
            this.filename = location.getFileName();
            this.line = location.getLineNumber();
            this.column = location.getColumnNumber();
        }
    }

    public UnboundLocationException(Location loc) {
        this.location = loc;
    }

    public UnboundLocationException(Object symbol, String message) {
        super(message);
        this.symbol = symbol;
    }

    public void setLine(String filename, int line, int column) {
        this.filename = filename;
        this.line = line;
        this.column = column;
    }

    public String getMessage() {
        String msg = super.getMessage();
        if (msg != null) {
            return msg;
        }
        Symbol name;
        StringBuffer sbuf = new StringBuffer();
        if (this.filename != null || this.line > 0) {
            if (this.filename != null) {
                sbuf.append(this.filename);
            }
            if (this.line >= 0) {
                sbuf.append(':');
                sbuf.append(this.line);
                if (this.column > 0) {
                    sbuf.append(':');
                    sbuf.append(this.column);
                }
            }
            sbuf.append(": ");
        }
        if (this.location == null) {
            name = null;
        } else {
            name = this.location.getKeySymbol();
        }
        if (name != null) {
            sbuf.append("unbound location ");
            sbuf.append(name);
            Object property = this.location.getKeyProperty();
            if (property != null) {
                sbuf.append(" (property ");
                sbuf.append(property);
                sbuf.append(')');
            }
        } else if (this.symbol != null) {
            sbuf.append("unbound location ");
            sbuf.append(this.symbol);
        } else {
            sbuf.append("unbound location");
        }
        return sbuf.toString();
    }

    public String toString() {
        return getMessage();
    }
}
