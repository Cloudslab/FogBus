package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

class DrawableCompatLollipop {
    DrawableCompatLollipop() {
    }

    public static void setHotspot(Drawable drawable, float x, float y) {
        drawable.setHotspot(x, y);
    }

    public static void setHotspotBounds(Drawable drawable, int left, int top, int right, int bottom) {
        drawable.setHotspotBounds(left, top, right, bottom);
    }

    public static void setTint(Drawable drawable, int tint) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.setTint(drawable, tint);
        } else {
            drawable.setTint(tint);
        }
    }

    public static void setTintList(Drawable drawable, ColorStateList tint) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.setTintList(drawable, tint);
        } else {
            drawable.setTintList(tint);
        }
    }

    public static void setTintMode(Drawable drawable, Mode tintMode) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.setTintMode(drawable, tintMode);
        } else {
            drawable.setTintMode(tintMode);
        }
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        if (drawable instanceof GradientDrawable) {
            return new DrawableWrapperLollipop(drawable);
        }
        return drawable;
    }
}
