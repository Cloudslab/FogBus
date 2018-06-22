package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.URLPath;
import java.io.File;
import java.net.URL;

public class ModuleManager {
    public static final long LAST_MODIFIED_CACHE_TIME = 1000;
    static ModuleManager instance = new ModuleManager();
    private String compilationDirectory = "";
    public long lastModifiedCacheTime = 1000;
    ModuleInfo[] modules;
    int numModules;
    ModuleSet packageInfoChain;

    public void setCompilationDirectory(String path) {
        if (path == null) {
            path = "";
        }
        int plen = path.length();
        if (plen > 0) {
            char sep = File.separatorChar;
            if (path.charAt(plen - 1) != sep) {
                path = path + sep;
            }
        }
        this.compilationDirectory = path;
    }

    public String getCompilationDirectory() {
        return this.compilationDirectory;
    }

    public static ModuleManager getInstance() {
        return instance;
    }

    public synchronized ModuleInfo getModule(int index) {
        return index >= this.numModules ? null : this.modules[index];
    }

    public synchronized ModuleInfo find(Compilation comp) {
        ModuleInfo info;
        ModuleExp mexp = comp.getModule();
        ClassType ctype = mexp.classFor(comp);
        String fileName = mexp.getFileName();
        info = findWithSourcePath(ModuleInfo.absPath(fileName), fileName);
        info.setClassName(ctype.getName());
        info.exp = mexp;
        comp.minfo = info;
        info.comp = comp;
        return info;
    }

    private synchronized void add(ModuleInfo info) {
        if (this.modules == null) {
            this.modules = new ModuleInfo[10];
        } else if (this.numModules == this.modules.length) {
            ModuleInfo[] tmp = new ModuleInfo[(this.numModules * 2)];
            System.arraycopy(this.modules, 0, tmp, 0, this.numModules);
            this.modules = tmp;
        }
        ModuleInfo[] moduleInfoArr = this.modules;
        int i = this.numModules;
        this.numModules = i + 1;
        moduleInfoArr[i] = info;
    }

    public synchronized ModuleInfo searchWithClassName(String className) {
        ModuleInfo info;
        int i = this.numModules;
        do {
            i--;
            if (i < 0) {
                info = null;
                break;
            }
            info = this.modules[i];
        } while (!className.equals(info.getClassName()));
        return info;
    }

    public static synchronized ModuleInfo findWithClass(Class clas) {
        ModuleInfo info;
        synchronized (ModuleManager.class) {
            info = (ModuleInfo) ModuleInfo.mapClassToInfo.get(clas);
            if (info == null) {
                info = new ModuleInfo();
                info.setModuleClass(clas);
            }
        }
        return info;
    }

    public ModuleInfo findWithClassName(String className) {
        ModuleInfo info = searchWithClassName(className);
        if (info == null) {
            try {
                info = findWithClass(ObjectType.getContextClass(className));
            } catch (Throwable ex) {
                RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
            }
        }
        return info;
    }

    private synchronized ModuleInfo searchWithAbsSourcePath(String sourcePath) {
        ModuleInfo info;
        int i = this.numModules;
        do {
            i--;
            if (i < 0) {
                info = null;
                break;
            }
            info = this.modules[i];
        } while (!sourcePath.equals(info.getSourceAbsPathname()));
        return info;
    }

    public synchronized ModuleInfo findWithSourcePath(Path sourceAbsPath, String sourcePath) {
        ModuleInfo info;
        String sourceAbsPathname = sourceAbsPath.toString();
        info = searchWithAbsSourcePath(sourceAbsPathname);
        if (info == null) {
            info = new ModuleInfo();
            info.sourcePath = sourcePath;
            info.sourceAbsPath = sourceAbsPath;
            info.sourceAbsPathname = sourceAbsPathname;
            add(info);
        }
        return info;
    }

    public synchronized ModuleInfo findWithSourcePath(String sourcePath) {
        return findWithSourcePath(ModuleInfo.absPath(sourcePath), sourcePath);
    }

    public synchronized ModuleInfo findWithURL(URL url) {
        return findWithSourcePath(URLPath.valueOf(url), url.toExternalForm());
    }

    public synchronized void register(String moduleClass, String moduleSource, String moduleUri) {
        if (searchWithClassName(moduleClass) == null) {
            Path sourcePath = Path.valueOf(moduleSource);
            String sourceAbsPathname = sourcePath.getCanonical().toString();
            if (searchWithAbsSourcePath(sourceAbsPathname) == null) {
                ModuleInfo info = new ModuleInfo();
                if (sourcePath.isAbsolute()) {
                    info.sourceAbsPath = sourcePath;
                    info.sourceAbsPathname = sourceAbsPathname;
                } else {
                    try {
                        Class setClass = this.packageInfoChain.getClass();
                        Path sourceAbsPath = URLPath.valueOf(setClass.getClassLoader().getResource(setClass.getName().replace('.', '/') + ".class")).resolve(moduleSource);
                        info.sourceAbsPath = sourceAbsPath;
                        info.sourceAbsPathname = sourceAbsPath.toString();
                    } catch (Throwable th) {
                    }
                }
                info.setClassName(moduleClass);
                info.sourcePath = moduleSource;
                info.uri = moduleUri;
                add(info);
            }
        }
    }

    public synchronized void loadPackageInfo(String packageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String moduleSetClassName = packageName + "." + ModuleSet.MODULES_MAP;
        ModuleSet set = this.packageInfoChain;
        while (set != null) {
            set = set.getClass().getName().equals(moduleSetClassName) ? set.next : set.next;
        }
        ModuleSet instance = (ModuleSet) Class.forName(moduleSetClassName).newInstance();
        instance.next = this.packageInfoChain;
        this.packageInfoChain = instance;
        instance.register(this);
    }

    public synchronized void clear() {
        ModuleSet set = this.packageInfoChain;
        while (set != null) {
            ModuleSet next = set.next;
            set.next = null;
            set = next;
        }
        this.packageInfoChain = null;
        this.modules = null;
        this.numModules = 0;
    }
}
