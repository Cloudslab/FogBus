package kawa;

import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import kawa.ReplDocument.DocumentCloseListener;

public class GuiConsole extends JFrame implements ActionListener, DocumentCloseListener {
    private static String CLOSE = "Close";
    private static String EXIT = "Exit";
    private static String NEW = "New";
    private static String NEW_SHARED = "New (Shared)";
    private static String PURGE_MESSAGE = "Purge Buffer";
    static int window_number = 0;
    ReplDocument document;
    ReplPane pane;

    class C03771 extends WindowAdapter {
        C03771() {
        }

        public void windowClosing(WindowEvent e) {
            GuiConsole.this.close();
        }
    }

    public static void main(String[] args) {
        repl.noConsole = false;
        int iArg = repl.processArgs(args, 0, args.length);
        repl.getLanguage();
        repl.setArgs(args, iArg);
        repl.checkInitFile();
        GuiConsole guiConsole = new GuiConsole();
    }

    public GuiConsole() {
        this(Language.getDefaultLanguage(), Environment.getCurrent(), false);
    }

    public GuiConsole(ReplDocument doc) {
        super("Kawa");
        init(doc);
    }

    void init(ReplDocument doc) {
        this.document = doc;
        this.document.addDocumentCloseListener(this);
        this.pane = new ReplPane(this.document);
        window_number++;
        setLayout(new BorderLayout(0, 0));
        add("Center", new JScrollPane(this.pane));
        setupMenus();
        setLocation(window_number * 100, window_number * 50);
        setSize(700, 500);
        setVisible(true);
    }

    public GuiConsole(Language language, Environment penvironment, boolean shared) {
        super("Kawa");
        repl.getLanguage();
        init(new ReplDocument(language, penvironment, shared));
    }

    public void closed(ReplDocument doc) {
        close();
    }

    void close() {
        this.document.removeDocumentCloseListener(this);
        dispose();
    }

    private void setupMenus() {
        WindowListener windowExitCmd = new C03771();
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu utilitiesMenu = new Menu("Utilities");
        menubar.add(fileMenu);
        menubar.add(utilitiesMenu);
        MenuItem menuItem = new MenuItem(NEW);
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);
        menuItem = new MenuItem(NEW_SHARED);
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);
        menuItem = new MenuItem(CLOSE);
        menuItem.addActionListener(this);
        fileMenu.add(menuItem);
        menuItem = new MenuItem(EXIT);
        menuItem.addActionListener(this);
        addWindowListener(windowExitCmd);
        fileMenu.add(menuItem);
        menuItem = new MenuItem(PURGE_MESSAGE);
        menuItem.addActionListener(this);
        utilitiesMenu.add(menuItem);
        setMenuBar(menubar);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        GuiConsole guiConsole;
        if (cmd.equals(NEW)) {
            guiConsole = new GuiConsole(this.document.language, Environment.getGlobal(), false);
        } else if (cmd.equals(NEW_SHARED)) {
            guiConsole = new GuiConsole(this.document.language, this.document.environment, true);
        } else if (cmd.equals(EXIT)) {
            System.exit(0);
        } else if (cmd.equals(CLOSE)) {
            close();
        } else if (cmd.equals(PURGE_MESSAGE)) {
            this.pane.document.deleteOldText();
        } else {
            OutPort.outDefault().println("Unknown menu action: " + cmd);
        }
    }
}
