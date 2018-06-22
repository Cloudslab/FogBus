package com.google.appinventor.components.runtime.util;

public final class BoundingBox {
    private double bottom;
    private double left;
    private double right;
    private double top;

    public BoundingBox(double l, double t, double r, double b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
    }

    public boolean intersectDestructively(BoundingBox bb) {
        double xmin = Math.max(this.left, bb.left);
        double xmax = Math.min(this.right, bb.right);
        double ymin = Math.max(this.top, bb.top);
        double ymax = Math.min(this.bottom, bb.bottom);
        if (xmin > xmax || ymin > ymax) {
            return false;
        }
        this.left = xmin;
        this.right = xmax;
        this.top = ymin;
        this.bottom = ymax;
        return true;
    }

    public double getLeft() {
        return this.left;
    }

    public double getTop() {
        return this.top;
    }

    public double getRight() {
        return this.right;
    }

    public double getBottom() {
        return this.bottom;
    }

    public String toString() {
        return "<BoundingBox (left = " + this.left + ", top = " + this.top + ", right = " + this.right + ", bottom = " + this.bottom + ">";
    }
}
