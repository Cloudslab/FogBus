package kawa.standard;

import gnu.mapping.Environment;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.text.SyntaxException;
import java.io.FileNotFoundException;
import kawa.Shell;

public class load extends Procedure1 {
    public static final load load = new load("load", false);
    public static final load loadRelative = new load("load-relative", true);
    boolean relative;

    public load(String name, boolean relative) {
        super(name);
        this.relative = relative;
    }

    public final Object apply1(Object arg1) throws Throwable {
        return apply2(arg1, Environment.getCurrent());
    }

    public final Object apply2(Object name, Object arg2) throws Throwable {
        try {
            Environment env = (Environment) arg2;
            Path path = Path.valueOf(name);
            if (this.relative) {
                Path curPath = (Path) Shell.currentLoadPath.get();
                if (curPath != null) {
                    path = curPath.resolve(path);
                }
            }
            Shell.runFile(path.openInputStream(), path, env, true, 0);
            return Values.empty;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("cannot load " + e.getMessage());
        } catch (SyntaxException ex) {
            throw new RuntimeException("load: errors while compiling '" + name + "':\n" + ex.getMessages().toString(20));
        }
    }
}
