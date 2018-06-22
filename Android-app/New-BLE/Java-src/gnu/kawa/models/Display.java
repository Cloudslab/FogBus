package gnu.kawa.models;

import gnu.lists.FString;
import gnu.mapping.ThreadLocation;
import gnu.mapping.WrappedException;
import java.awt.Dimension;
import java.awt.geom.Dimension2D;

public abstract class Display {
    public static ThreadLocation myDisplay = new ThreadLocation("my-display");

    public abstract void addBox(Box box, Object obj);

    public abstract void addButton(Button button, Object obj);

    public abstract void addImage(DrawImage drawImage, Object obj);

    public abstract void addLabel(Label label, Object obj);

    public abstract void addView(Object obj, Object obj2);

    public abstract Window makeWindow();

    public static Display getInstance() {
        Object d = myDisplay.get(null);
        if (d instanceof Display) {
            return (Display) d;
        }
        String name = d == null ? "swing" : d.toString();
        Class[] noClasses = new Class[0];
        while (true) {
            int comma = name.indexOf(44);
            String rest = null;
            if (comma >= 0) {
                rest = name.substring(comma + 1);
                name = name.substring(0, comma);
            }
            if (name.equals("swing")) {
                name = "gnu.kawa.swingviews.SwingDisplay";
            } else if (name.equals("swt")) {
                name = "gnu.kawa.swtviews.SwtDisplay";
            } else if (name.equals("echo2")) {
                name = "gnu.kawa.echo2.Echo2Display";
            }
            try {
                return (Display) Class.forName(name).getDeclaredMethod("getInstance", noClasses).invoke(null, new Object[0]);
            } catch (ClassNotFoundException e) {
                if (rest == null) {
                    throw new RuntimeException("no display toolkit: " + d);
                }
                name = rest;
            } catch (Throwable ex) {
                RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
            }
        }
    }

    public void addText(Text model, Object where) {
        throw new Error("makeView called on Text");
    }

    public void addSpacer(Spacer model, Object where) {
        throw new Error("makeView called on Spacer");
    }

    public static Dimension asDimension(Dimension2D dim) {
        if ((dim instanceof Dimension) || dim == null) {
            return (Dimension) dim;
        }
        return new Dimension((int) (dim.getWidth() + 0.5d), (int) (dim.getHeight() + 0.5d));
    }

    public Model coerceToModel(Object component) {
        if ((component instanceof FString) || (component instanceof String)) {
            return new Label(component.toString());
        }
        return (Model) component;
    }
}
