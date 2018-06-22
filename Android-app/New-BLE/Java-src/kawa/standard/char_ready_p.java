package kawa.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class char_ready_p {
    public static boolean ready(Object arg1) {
        try {
            if (arg1 instanceof InputStream) {
                if (((InputStream) arg1).available() > 0) {
                    return true;
                }
                return false;
            } else if (arg1 instanceof Reader) {
                return ((Reader) arg1).ready();
            } else {
                throw new ClassCastException("invalid argument to char-ready?");
            }
        } catch (IOException e) {
            return false;
        }
    }
}
