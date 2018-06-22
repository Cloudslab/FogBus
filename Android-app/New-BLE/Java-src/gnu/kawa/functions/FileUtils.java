package gnu.kawa.functions;

import android.support.v4.media.TransportMediator;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File createTempFile(String format) throws IOException {
        String prefix;
        String suffix;
        if (format == null) {
            format = "kawa~d.tmp";
        }
        int tilde = format.indexOf(TransportMediator.KEYCODE_MEDIA_PLAY);
        File directory = null;
        if (tilde < 0) {
            prefix = format;
            suffix = ".tmp";
        } else {
            prefix = format.substring(0, tilde);
            suffix = format.substring(tilde + 2);
        }
        int sep = prefix.indexOf(File.separatorChar);
        if (sep >= 0) {
            directory = new File(prefix.substring(0, sep));
            prefix = prefix.substring(sep + 1);
        }
        return File.createTempFile(prefix, suffix, directory);
    }
}
