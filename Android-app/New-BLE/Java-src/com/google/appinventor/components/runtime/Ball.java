package com.google.appinventor.components.runtime;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.PaintUtil;

@SimpleObject
@DesignerComponent(category = ComponentCategory.ANIMATION, description = "<p>A round 'sprite' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>ImageSprite</code>s and other <code>Ball</code>s) and the edge of the Canvas, and move according to its property values.</p><p>For example, to have a <code>Ball</code> move 4 pixels toward the top of a <code>Canvas</code> every 500 milliseconds (half second), you would set the <code>Speed</code> property to 4 [pixels], the <code>Interval</code> property to 500 [milliseconds], the <code>Heading</code> property to 90 [degrees], and the <code>Enabled</code> property to <code>True</code>.  These and its other properties can be changed at any time.</p><p>The difference between a Ball and an <code>ImageSprite</code> is that the latter can get its appearance from an image file, while a Ball's appearance can only be changed by varying its <code>PaintColor</code> and <code>Radius</code> properties.</p>", version = 5)
public final class Ball extends Sprite {
    static final int DEFAULT_RADIUS = 5;
    private Paint paint = new Paint();
    private int paintColor;
    private int radius;

    public Ball(ComponentContainer container) {
        super(container);
        PaintColor(-16777216);
        Radius(5);
    }

    protected void onDraw(Canvas canvas) {
        if (this.visible) {
            float correctedRadius = ((float) this.radius) * this.form.deviceDensity();
            canvas.drawCircle(((float) (this.xLeft * ((double) this.form.deviceDensity()))) + correctedRadius, ((float) (this.yTop * ((double) this.form.deviceDensity()))) + correctedRadius, correctedRadius, this.paint);
        }
    }

    public int Height() {
        return this.radius * 2;
    }

    public void Height(int height) {
    }

    public void HeightPercent(int pCent) {
    }

    public int Width() {
        return this.radius * 2;
    }

    public void Width(int width) {
    }

    public void WidthPercent(int pCent) {
    }

    public boolean containsPoint(double qx, double qy) {
        double xCenter = this.xLeft + ((double) this.radius);
        double yCenter = this.yTop + ((double) this.radius);
        return ((qx - xCenter) * (qx - xCenter)) + ((qy - yCenter) * (qy - yCenter)) <= ((double) (this.radius * this.radius));
    }

    @DesignerProperty(defaultValue = "5", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Radius(int radius) {
        this.radius = radius;
        registerChange();
    }

    @SimpleProperty
    public int Radius() {
        return this.radius;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int PaintColor() {
        return this.paintColor;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void PaintColor(int argb) {
        this.paintColor = argb;
        if (argb != 0) {
            PaintUtil.changePaint(this.paint, argb);
        } else {
            PaintUtil.changePaint(this.paint, -16777216);
        }
        registerChange();
    }
}
