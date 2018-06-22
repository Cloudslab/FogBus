package gnu.kawa.models;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.Serializable;

public class Spacer extends Model implements Viewable, Serializable {
    Dimension2D maxSize;
    Dimension2D minSize;
    Dimension2D preferredSize;

    public Dimension2D getMinimumSize2D() {
        return this.minSize;
    }

    public Dimension2D getPreferredSize2D() {
        return this.preferredSize;
    }

    public Dimension2D getMaximumSize2D() {
        return this.maxSize;
    }

    public Dimension getMinimumSize() {
        return Display.asDimension(this.minSize);
    }

    public Dimension getPreferredSize() {
        return Display.asDimension(this.preferredSize);
    }

    public Dimension getMaximumSize() {
        return Display.asDimension(this.maxSize);
    }

    public boolean isRigid() {
        if (this.minSize == this.maxSize) {
            return true;
        }
        if (this.minSize == null || this.maxSize == null || this.minSize.getWidth() != this.maxSize.getWidth() || this.minSize.getHeight() != this.maxSize.getHeight()) {
            return false;
        }
        return true;
    }

    public static Spacer rigidArea(Dimension2D d) {
        Spacer spacer = new Spacer();
        spacer.minSize = d;
        spacer.maxSize = d;
        spacer.preferredSize = d;
        return spacer;
    }

    public static Spacer rigidArea(int width, int height) {
        return rigidArea(new Dimension(width, height));
    }

    public void makeView(Display display, Object where) {
        display.addSpacer(this, where);
    }
}
