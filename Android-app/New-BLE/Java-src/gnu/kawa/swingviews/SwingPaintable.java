package gnu.kawa.swingviews;

import gnu.kawa.models.Paintable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class SwingPaintable extends JPanel {
    Dimension dim;
    Paintable paintable;

    public SwingPaintable(Paintable paintable) {
        this.paintable = paintable;
        Rectangle2D rect = paintable.getBounds2D();
        this.dim = new Dimension((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()));
    }

    public void paint(Graphics g) {
        this.paintable.paint((Graphics2D) g);
    }

    public Dimension getPreferredSize() {
        return this.dim;
    }
}
