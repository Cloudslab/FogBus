package gnu.text;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SourceMessages implements SourceLocator {
    public static boolean debugStackTraceOnError = false;
    public static boolean debugStackTraceOnWarning = false;
    int current_column;
    String current_filename;
    int current_line;
    private int errorCount = 0;
    SourceError firstError;
    SourceError lastError;
    SourceError lastPrevFilename = null;
    SourceLocator locator;
    public boolean sortMessages;

    public SourceError getErrors() {
        return this.firstError;
    }

    public boolean seenErrors() {
        return this.errorCount > 0;
    }

    public boolean seenErrorsOrWarnings() {
        return this.firstError != null;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void clearErrors() {
        this.errorCount = 0;
    }

    public void clear() {
        this.lastError = null;
        this.firstError = null;
        this.errorCount = 0;
    }

    public void error(SourceError error) {
        if (error.severity == 'f') {
            this.errorCount = 1000;
        } else if (error.severity != 'w') {
            this.errorCount++;
        }
        if ((debugStackTraceOnError && (error.severity == 'e' || error.severity == 'f')) || (debugStackTraceOnWarning && error.severity == 'w')) {
            error.fakeException = new Throwable();
        }
        if (!(this.lastError == null || this.lastError.filename == null || this.lastError.filename.equals(error.filename))) {
            this.lastPrevFilename = this.lastError;
        }
        SourceError prev = this.lastPrevFilename;
        if (!this.sortMessages || error.severity == 'f') {
            prev = this.lastError;
        } else {
            while (true) {
                SourceError next;
                if (prev == null) {
                    next = this.firstError;
                } else {
                    next = prev.next;
                }
                if (next == null) {
                    break;
                }
                if (!(error.line == 0 || next.line == 0)) {
                    if (error.line >= next.line) {
                        if (error.line == next.line && error.column != 0 && next.column != 0 && error.column < next.column) {
                            break;
                        }
                    }
                    break;
                }
                prev = next;
            }
        }
        if (prev == null) {
            error.next = this.firstError;
            this.firstError = error;
        } else {
            error.next = prev.next;
            prev.next = error;
        }
        if (prev == this.lastError) {
            this.lastError = error;
        }
    }

    public void error(char severity, String filename, int line, int column, String message) {
        error(new SourceError(severity, filename, line, column, message));
    }

    public void error(char severity, SourceLocator location, String message) {
        error(new SourceError(severity, location, message));
    }

    public void error(char severity, String filename, int line, int column, String message, String code) {
        SourceError err = new SourceError(severity, filename, line, column, message);
        err.code = code;
        error(err);
    }

    public void error(char severity, SourceLocator location, String message, String code) {
        SourceError err = new SourceError(severity, location, message);
        err.code = code;
        error(err);
    }

    public void error(char severity, String message) {
        error(new SourceError(severity, this.current_filename, this.current_line, this.current_column, message));
    }

    public void error(char severity, String message, Throwable exception) {
        SourceError err = new SourceError(severity, this.current_filename, this.current_line, this.current_column, message);
        err.fakeException = exception;
        error(err);
    }

    public void error(char severity, String message, String code) {
        SourceError err = new SourceError(severity, this.current_filename, this.current_line, this.current_column, message);
        err.code = code;
        error(err);
    }

    public void printAll(PrintStream out, int max) {
        SourceError err = this.firstError;
        while (err != null) {
            max--;
            if (max >= 0) {
                err.println(out);
                err = err.next;
            } else {
                return;
            }
        }
    }

    public void printAll(PrintWriter out, int max) {
        SourceError err = this.firstError;
        while (err != null) {
            max--;
            if (max >= 0) {
                err.println(out);
                err = err.next;
            } else {
                return;
            }
        }
    }

    public String toString(int max) {
        if (this.firstError == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (SourceError err = this.firstError; err != null; err = err.next) {
            max--;
            if (max < 0) {
                break;
            }
            buffer.append(err);
            buffer.append('\n');
        }
        return buffer.toString();
    }

    public boolean checkErrors(PrintWriter out, int max) {
        if (this.firstError == null) {
            return false;
        }
        printAll(out, max);
        this.lastError = null;
        this.firstError = null;
        int saveCount = this.errorCount;
        this.errorCount = 0;
        if (saveCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkErrors(PrintStream out, int max) {
        if (this.firstError == null) {
            return false;
        }
        printAll(out, max);
        this.lastError = null;
        this.firstError = null;
        int saveCount = this.errorCount;
        this.errorCount = 0;
        if (saveCount > 0) {
            return true;
        }
        return false;
    }

    public final void setSourceLocator(SourceLocator locator) {
        if (locator == this) {
            locator = null;
        }
        this.locator = locator;
    }

    public final SourceLocator swapSourceLocator(SourceLocator locator) {
        SourceLocator save = this.locator;
        this.locator = locator;
        return save;
    }

    public final void setLocation(SourceLocator locator) {
        this.locator = null;
        this.current_line = locator.getLineNumber();
        this.current_column = locator.getColumnNumber();
        this.current_filename = locator.getFileName();
    }

    public String getPublicId() {
        return this.locator == null ? null : this.locator.getPublicId();
    }

    public String getSystemId() {
        return this.locator == null ? this.current_filename : this.locator.getSystemId();
    }

    public boolean isStableSourceLocation() {
        return false;
    }

    public final String getFileName() {
        return this.current_filename;
    }

    public final int getLineNumber() {
        return this.locator == null ? this.current_line : this.locator.getLineNumber();
    }

    public final int getColumnNumber() {
        return this.locator == null ? this.current_column : this.locator.getColumnNumber();
    }

    public void setFile(String filename) {
        this.current_filename = filename;
    }

    public void setLine(int line) {
        this.current_line = line;
    }

    public void setColumn(int column) {
        this.current_column = column;
    }

    public void setLine(String filename, int line, int column) {
        this.current_filename = filename;
        this.current_line = line;
        this.current_column = column;
    }
}
