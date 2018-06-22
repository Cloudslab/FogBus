package gnu.text;

import org.xml.sax.Locator;

public interface SourceLocator extends Locator {
    int getColumnNumber();

    String getFileName();

    int getLineNumber();

    String getPublicId();

    String getSystemId();

    boolean isStableSourceLocation();
}
