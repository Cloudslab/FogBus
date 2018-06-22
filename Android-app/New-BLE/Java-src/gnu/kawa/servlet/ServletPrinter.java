package gnu.kawa.servlet;

import gnu.kawa.xml.HttpPrinter;
import java.io.IOException;

public class ServletPrinter extends HttpPrinter {
    HttpRequestContext hctx;

    public ServletPrinter(HttpRequestContext hctx, int bufSize) throws IOException {
        super(new HttpOutputStream(hctx, bufSize));
        this.hctx = hctx;
    }

    public void addHeader(String label, String value) {
        if (label.equalsIgnoreCase("Content-type")) {
            this.sawContentType = value;
            this.hctx.setContentType(value);
        } else if (label.equalsIgnoreCase("Status")) {
            int lval = value.length();
            int code = 0;
            int i = 0;
            while (i < lval) {
                if (i >= lval) {
                    this.hctx.statusCode = code;
                    return;
                }
                char ch = value.charAt(i);
                int digit = Character.digit(ch, 10);
                if (digit >= 0) {
                    code = (code * 10) + digit;
                    i++;
                } else {
                    if (ch == ' ') {
                        i++;
                    }
                    this.hctx.statusCode = code;
                    this.hctx.statusReasonPhrase = value.substring(i);
                    return;
                }
            }
        } else {
            this.hctx.setResponseHeader(label, value);
        }
    }

    public void printHeaders() {
    }

    public boolean reset(boolean headersAlso) {
        return ((HttpOutputStream) this.ostream).reset() & super.reset(headersAlso);
    }
}
