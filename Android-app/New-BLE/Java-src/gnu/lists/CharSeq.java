package gnu.lists;

import java.io.IOException;

public interface CharSeq extends CharSequence, Sequence {
    char charAt(int i);

    void consume(int i, int i2, Consumer consumer);

    void fill(char c);

    void fill(int i, int i2, char c);

    void getChars(int i, int i2, char[] cArr, int i3);

    int length();

    void setCharAt(int i, char c);

    CharSequence subSequence(int i, int i2);

    String toString();

    void writeTo(int i, int i2, Appendable appendable) throws IOException;

    void writeTo(Appendable appendable) throws IOException;
}
