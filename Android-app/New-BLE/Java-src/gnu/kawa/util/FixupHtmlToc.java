package gnu.kawa.util;

import java.io.File;

public class FixupHtmlToc {
    static FileInfo[] argFiles;

    public static void main(String[] args) {
        try {
            int i;
            argFiles = new FileInfo[args.length];
            for (i = 0; i < args.length; i++) {
                FileInfo info = FileInfo.find(new File(args[i]));
                info.writeNeeded = true;
                argFiles[i] = info;
            }
            for (i = 0; i < args.length; i++) {
                argFiles[i].scan();
                argFiles[i].write();
            }
        } catch (Throwable ex) {
            System.err.println("caught " + ex);
            ex.printStackTrace();
        }
    }
}
