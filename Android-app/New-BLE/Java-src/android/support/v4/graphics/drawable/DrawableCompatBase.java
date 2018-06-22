package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;

class DrawableCompatBase {
    DrawableCompatBase() {
    }

    public static void setTint(Drawable drawable, int tint) {
        if (drawable instanceof DrawableWrapper) {
            ((DrawableWrapper) drawable).setTint(tint);
        }
    }

    public static void setTintList(Drawable drawable, ColorStateList tint) {
        if (drawable instanceof DrawableWrapper) {
            ((DrawableWrapper) drawable).setTintList(tint);
        }
    }

    public static void setTintMode(Drawable drawable, Mode tintMode) {
        if (drawable instanceof DrawableWrapper) {
            ((DrawableWrapper) drawable).setTintMode(tintMode);
        }
    }

    public static Drawable wrapForTinting(Drawable drawable) {
        if (drawable instanceof DrawableWrapperDonut) {
            return drawable;
        }
        return new DrawableWrapperDonut(drawable);
    }
}
