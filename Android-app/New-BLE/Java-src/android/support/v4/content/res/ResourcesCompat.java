package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

public class ResourcesCompat {
    public static Drawable getDrawable(Resources res, int id, Theme theme) throws NotFoundException {
        if (VERSION.SDK_INT >= 21) {
            return ResourcesCompatApi21.getDrawable(res, id, theme);
        }
        return res.getDrawable(id);
    }

    public static Drawable getDrawableForDensity(Resources res, int id, int density, Theme theme) throws NotFoundException {
        int version = VERSION.SDK_INT;
        if (version >= 21) {
            return ResourcesCompatApi21.getDrawableForDensity(res, id, density, theme);
        }
        if (version >= 15) {
            return ResourcesCompatIcsMr1.getDrawableForDensity(res, id, density);
        }
        return res.getDrawable(id);
    }
}
