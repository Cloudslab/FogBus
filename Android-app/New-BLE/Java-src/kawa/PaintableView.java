package kawa;

import gnu.kawa.models.Paintable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;
import javax.swing.text.View;

/* compiled from: ReplPane */
class PaintableView extends View {
    Rectangle2D bounds;
    Paintable f8p;

    public PaintableView(Element elem, Paintable paintable) {
        super(elem);
        this.f8p = paintable;
        this.bounds = paintable.getBounds2D();
    }

    public void paint(Graphics g, Shape a) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = a.getBounds();
        AffineTransform saveTransform = g2.getTransform();
        Paint savePaint = g2.getPaint();
        try {
            g2.translate(bounds.x, bounds.y);
            g2.setPaint(Color.BLACK);
            this.f8p.paint(g2);
        } finally {
            g2.setTransform(saveTransform);
            g2.setPaint(savePaint);
        }
    }

    public float getAlignment(int axis) {
        switch (axis) {
            case 1:
                return 1.0f;
            default:
                return super.getAlignment(axis);
        }
    }

    public float getPreferredSpan(int axis) {
        switch (axis) {
            case 0:
                return (float) this.bounds.getWidth();
            case 1:
                return (float) this.bounds.getHeight();
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    public Shape modelToView(int pos, Shape a, Bias b) throws BadLocationException {
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        if (pos < p0 || pos > p1) {
            throw new BadLocationException(pos + " not in range " + p0 + "," + p1, pos);
        }
        Rectangle r = a.getBounds();
        if (pos == p1) {
            r.x += r.width;
        }
        r.width = 0;
        return r;
    }

    public int viewToModel(float x, float y, Shape a, Bias[] bias) {
        Rectangle alloc = (Rectangle) a;
        if (x < ((float) (alloc.x + (alloc.width / 2)))) {
            bias[0] = Bias.Forward;
            return getStartOffset();
        }
        bias[0] = Bias.Backward;
        return getEndOffset();
    }
}
