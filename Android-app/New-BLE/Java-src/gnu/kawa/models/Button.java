package gnu.kawa.models;

import com.google.appinventor.components.common.PropertyTypeConstants;
import java.awt.Color;

public class Button extends Model {
    Object action;
    Color background;
    boolean disabled;
    Color foreground;
    String text;
    Object width;

    public void makeView(Display display, Object where) {
        display.addButton(this, where);
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        notifyListeners(PropertyTypeConstants.PROPERTY_TYPE_TEXT);
    }

    public Object getAction() {
        return this.action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Color getForeground() {
        return this.foreground;
    }

    public void setForeground(Color fg) {
        this.foreground = fg;
        notifyListeners("foreground");
    }

    public Color getBackground() {
        return this.background;
    }

    public void setBackground(Color bg) {
        this.background = bg;
        notifyListeners("background");
    }
}
