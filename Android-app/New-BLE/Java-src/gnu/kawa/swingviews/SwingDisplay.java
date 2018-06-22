package gnu.kawa.swingviews;

import gnu.kawa.models.Box;
import gnu.kawa.models.Button;
import gnu.kawa.models.Display;
import gnu.kawa.models.DrawImage;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.Paintable;
import gnu.kawa.models.Spacer;
import gnu.kawa.models.Text;
import gnu.kawa.models.Window;
import gnu.mapping.Procedure;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.WeakHashMap;
import javax.swing.Box.Filler;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class SwingDisplay extends Display {
    private static WeakHashMap documents = null;
    static final SwingDisplay instance = new SwingDisplay();

    public static Display getInstance() {
        return instance;
    }

    public Window makeWindow() {
        SwingFrame window = new SwingFrame(null, null, null);
        window.display = this;
        return window;
    }

    public void addButton(Button model, Object where) {
        addView(new SwingButton(model), where);
    }

    public void addLabel(Label model, Object where) {
        addView(new SwingLabel(model), where);
    }

    public void addImage(DrawImage model, Object where) {
        addView(new JLabel(new ImageIcon(model.getImage())), where);
    }

    public void addText(Text model, Object where) {
        addView(new JTextField(getSwingDocument(model), model.getText(), 50), where);
    }

    static synchronized Document getSwingDocument(Text model) {
        Document existing;
        synchronized (SwingDisplay.class) {
            if (documents == null) {
                documents = new WeakHashMap();
            }
            Object existing2 = documents.get(model);
            if (existing2 != null) {
                existing = (Document) existing2;
            } else {
                Document doc = new PlainDocument(new SwingContent(model.buffer));
                documents.put(model, doc);
                existing = doc;
            }
        }
        return existing;
    }

    public void addBox(Box model, Object where) {
        addView(new SwingBox(model, this), where);
    }

    public void addSpacer(Spacer model, Object where) {
        addView(new Filler(model.getMinimumSize(), model.getPreferredSize(), model.getMaximumSize()), where);
    }

    public void addView(Object view, Object where) {
        ((Container) where).add((Component) view);
    }

    public static ActionListener makeActionListener(Object command) {
        if (command instanceof ActionListener) {
            return (ActionListener) command;
        }
        return new ProcActionListener((Procedure) command);
    }

    public Model coerceToModel(Object component) {
        if (component instanceof Component) {
            return new ComponentModel((Component) component);
        }
        if (component instanceof Paintable) {
            return new ComponentModel(new SwingPaintable((Paintable) component));
        }
        return super.coerceToModel(component);
    }
}
