package gnu.kawa.models;

import gnu.mapping.WrappedException;
import gnu.text.Path;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class DrawImage extends Model implements Paintable, Serializable {
    String description;
    BufferedImage image;
    Path src;

    public void makeView(Display display, Object where) {
        display.addImage(this, where);
    }

    void loadImage() {
        if (this.image == null) {
            try {
                this.image = ImageIO.read(this.src.openInputStream());
            } catch (Throwable ex) {
                RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
            }
        }
    }

    public DrawImage(BufferedImage image) {
        this.image = image;
    }

    public void paint(Graphics2D graphics) {
        loadImage();
        graphics.drawImage(this.image, null, null);
    }

    public Rectangle2D getBounds2D() {
        loadImage();
        return new Float(0.0f, 0.0f, (float) this.image.getWidth(), (float) this.image.getHeight());
    }

    public Paintable transform(AffineTransform tr) {
        return new WithTransform(this, tr);
    }

    public Image getImage() {
        loadImage();
        return this.image;
    }

    public Path getSrc() {
        return this.src;
    }

    public void setSrc(Path src) {
        this.src = src;
    }
}
